import json
import re
from pathlib import Path

from pypdf import PdfReader


ROOT = Path(__file__).resolve().parents[1]
PDF = next((p for p in ROOT.glob("*.pdf") if p.name.startswith("3.2PETS3")), None)
if PDF is None:
    PDF = next(ROOT.glob("*.pdf"))
OUT = ROOT / "pets3-words.official.json"

POS_RE = re.compile(
    r"\b(?:abbr\.|adj\.|adv\.|conj\.|int\.|n\.|num\.|prep\.|pron\.|v\.|vi\.|vt\.|aux\.|art\.|a\.)"
)
ENTRY_RE = re.compile(r"^\s*(\d{1,5})\s+(.+)$")
SKIP_RE = re.compile(r"^(?:序号\s+单词\s+注音\s+释义|公众号：|[,{}Qq0-9榰\0\s]+)$")

CORE_HINTS = {
    "ability", "able", "about", "above", "abroad", "accept", "accident",
    "achieve", "across", "act", "action", "active", "activity", "actual",
    "add", "address", "adult", "advance", "advantage", "advice", "advise",
    "afford", "afraid", "after", "again", "against", "age", "ago", "agree",
    "agreement", "ahead", "aid", "aim", "air", "airport", "allow",
    "almost", "alone", "along", "already", "also", "although", "always",
    "among", "amount", "ancient", "and", "animal", "another", "answer",
    "appear", "apply", "area", "around", "arrange", "arrive", "article",
    "ask", "attend", "attention", "available", "avoid", "balance",
    "beautiful", "because", "become", "before", "begin", "believe",
    "benefit", "better", "between", "business", "career", "cause",
    "challenge", "chance", "change", "choice", "city", "class", "college",
    "common", "communicate", "community", "company", "compare", "complete",
    "condition", "consider", "continue", "control", "cost", "course",
    "culture", "customer", "data", "database", "decision", "develop",
    "difference", "difficult", "direction", "discover", "discuss",
    "education", "effect", "effort", "energy", "environment", "example",
    "experience", "explain", "family", "foreign", "friend", "government",
    "habit", "health", "help", "important", "improve", "include",
    "increase", "information", "international", "job", "knowledge",
    "language", "learn", "library", "life", "local", "manage", "method",
    "modern", "necessary", "network", "notice", "opinion", "opportunity",
    "plan", "popular", "practice", "prepare", "problem", "process",
    "product", "project", "protect", "provide", "public", "purpose",
    "quality", "question", "realize", "reason", "receive", "reduce",
    "relationship", "report", "require", "research", "resource", "result",
    "review", "rule", "school", "service", "skill", "society", "solution",
    "standard", "student", "study", "suggest", "support", "system",
    "teacher", "technology", "therefore", "time", "traffic", "training",
    "travel", "understand", "use", "various", "volunteer", "whether",
    "work", "worker", "writing",
}

REFERENCE_RULES = [
    ("500个考过10次", 120, "S2", "exam_high_frequency_500"),
    ("700个核心词汇", 100, "S2", "core_700"),
    ("高频词汇", 85, "S2", "high_frequency"),
    ("主谓双宾高频动词", 70, "S3", "verb_pattern"),
    ("主谓宾补高频动词", 70, "S3", "verb_pattern"),
    ("不规则动词", 45, "S3", "irregular_verbs"),
    ("动词的-ed", 35, "S3", "irregular_ed"),
    ("非谓语动词", 35, "S3", "grammar_structure"),
    ("词汇魔力", 20, "S3", "memory_textbook"),
    ("4400个大纲词汇", 5, "S1", "syllabus_4400"),
]

TOKEN_RE = re.compile(r"[A-Za-z][A-Za-z'-]*")


def clean_line(line: str) -> str:
    line = line.replace("\x00", " ")
    line = re.sub(r"\s+", " ", line).strip()
    return line


def parse_entry(block: str):
    block = re.sub(r"公众号：.*$", "", block).strip()
    match = ENTRY_RE.match(block)
    if not match:
        return None
    number = int(match.group(1))
    body = match.group(2).strip()
    parts = body.split(maxsplit=1)
    if len(parts) < 2:
        return None
    word, rest = parts
    pos_match = POS_RE.search(rest)
    if pos_match:
        phonetic = rest[: pos_match.start()].strip()
        meaning = rest[pos_match.start() :].strip()
    else:
        rest_parts = rest.split(maxsplit=1)
        phonetic = rest_parts[0] if rest_parts else ""
        meaning = rest_parts[1] if len(rest_parts) > 1 else ""
    pos_match2 = POS_RE.search(meaning)
    meaning = re.sub(r"公众号：.*$", "", meaning).strip()
    pos = pos_match2.group(0) if pos_match2 else ""
    layer = 1 if word.lower() in CORE_HINTS else 4
    return {
        "number": number,
        "word": word,
        "phonetic": phonetic,
        "meaning": meaning,
        "pos": pos,
        "example": "",
        "layer": layer,
        "theme": "syllabus",
        "sourceType": "syllabus_pdf",
        "sourceName": PDF.name,
        "sourceRank": "S1",
        "verified": True,
        "tag": f"day-{min(70, max(1, (number - 1) // 30 + 1))}",
    }


def extract_blocks():
    reader = PdfReader(str(PDF))
    blocks = []
    current = ""
    for page in reader.pages:
        text = page.extract_text() or ""
        for raw in text.splitlines():
            line = clean_line(raw)
            if not line or SKIP_RE.match(line):
                continue
            if ENTRY_RE.match(line):
                if current:
                    blocks.append(current)
                current = line
            elif current:
                current += " " + line
    if current:
        blocks.append(current)
    return blocks


def text_from_pdf(pdf: Path) -> str:
    try:
      reader = PdfReader(str(pdf))
    except Exception:
      return ""
    chunks = []
    for page in reader.pages:
        try:
            chunks.append(page.extract_text() or "")
        except Exception:
            continue
    return "\n".join(chunks)


def reference_rule_for(pdf: Path):
    for marker, weight, rank, source_type in REFERENCE_RULES:
        if marker in pdf.name:
            return weight, rank, source_type
    return 10, "S3", "reference_pdf"


def score_words(words):
    word_map = {item["word"].lower(): item for item in words}
    scores = {key: 0 for key in word_map}
    hits = {key: [] for key in word_map}
    sources = []
    for pdf in sorted(ROOT.glob("*.pdf"), key=lambda p: p.name):
        weight, rank, source_type = reference_rule_for(pdf)
        sources.append({
            "sourceRank": rank,
            "sourceType": source_type,
            "sourceName": pdf.name,
            "weight": weight,
        })
        text = text_from_pdf(pdf)
        tokens = [token.lower().strip("-'") for token in TOKEN_RE.findall(text)]
        seen_in_pdf = set()
        for token in tokens:
            if token in word_map and token not in seen_in_pdf:
                scores[token] += weight
                hits[token].append(pdf.name)
                seen_in_pdf.add(token)
    for key, item in word_map.items():
        if key in CORE_HINTS:
            scores[key] += 60
        # Earlier positions in the syllabus order get a small tie-break bonus.
        scores[key] += max(0, 4500 - item["number"]) / 4500
    return scores, hits, sources


def assign_layers(words, scores, hits):
    ranked = sorted(
        words,
        key=lambda item: (
            -scores.get(item["word"].lower(), 0),
            item["number"],
            item["word"],
        ),
    )
    for index, item in enumerate(ranked):
        key = item["word"].lower()
        if index < 800:
            layer = 1
        elif index < 1400:
            layer = 2
        elif index < 1800:
            layer = 3
        else:
            layer = 4
        item["layer"] = layer
        item["rank"] = index + 1
        item["frequencyScore"] = round(scores.get(key, 0), 3)
        item["referenceHits"] = hits.get(key, [])[:8]
        item["tag"] = f"day-{min(70, max(1, index // 20 + 1))}"
    return sorted(ranked, key=lambda item: item["number"])


def main():
    words = []
    seen = set()
    for block in extract_blocks():
        entry = parse_entry(block)
        if not entry:
            continue
        key = entry["word"].lower()
        if key in seen:
            continue
        seen.add(key)
        words.append(entry)

    scores, hits, sources = score_words(words)
    words = assign_layers(words, scores, hits)

    payload = {
        "app": "pets3-70-day-plan",
        "wordbank": "pets3-official-vetted",
        "version": 3,
        "status": "extracted-and-layered",
        "notes": "Extracted from the PETS3 complete vocabulary PDF and layered with weighted references from all same-directory PDFs. Layer targets: 800 core, 600 high-frequency, 400 general, remaining lookup/low-frequency.",
        "layering": {
            "method": "weighted_pdf_references",
            "targets": {"layer1": 800, "layer2": 600, "layer3": 400, "layer4": "remaining"},
            "warning": "This is reference-weighted layering, not official frequency ranking. Improve it further with real past-paper corpus statistics."
        },
        "sources": [
            {
                "sourceRank": "S1",
                "sourceType": "syllabus_pdf",
                "sourceName": PDF.name,
                "usage": "Primary vocabulary source selected by the user.",
            }
        ] + sources,
        "words": words,
    }
    OUT.write_text(json.dumps(payload, ensure_ascii=False, indent=2), encoding="utf-8")
    print(json.dumps({"pdf": PDF.name, "words": len(words), "out": str(OUT)}, ensure_ascii=False))


if __name__ == "__main__":
    main()
