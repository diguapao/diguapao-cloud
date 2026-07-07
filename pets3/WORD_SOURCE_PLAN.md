# PETS3 词库来源规范

## 目标

把当前项目词库升级为可追溯、可替换、可统计的 PETS3 核心词库。正式词条应优先来自：

1. PETS3 考试大纲词汇
2. 近年真题阅读、完形、写作语料统计
3. 权威教材词表

## 来源优先级

### S1 官方大纲

优先使用中国教育考试网发布的 PETS 第三级考试大纲，以及高等教育出版社出版的大纲/配套资源。该层用于确定“考试要求词汇”的边界。

字段建议：

- `sourceType`: `syllabus`
- `sourceName`: `PETS 第三级考试大纲`
- `sourceRank`: `S1`

### S2 真题语料统计

从近年真题阅读、完形、写作任务中抽取词频，只保存词条、频次、出现题型、年份范围和主题，不保存真题原文。

字段建议：

- `sourceType`: `past_exam_corpus`
- `sourceName`: `PETS3 past papers corpus`
- `sourceRank`: `S2`
- `frequency`: 出现次数
- `sections`: `reading`, `cloze`, `writing`

### S3 权威教材

使用高等教育出版社等权威 PETS3 教材词表进行补充和例句校正。教材全文、完整词表如受版权保护，不直接复制进仓库；只录入自己合法整理出的词条和来源标记。

字段建议：

- `sourceType`: `textbook`
- `sourceName`: 教材名
- `sourceRank`: `S3`

## 四层分级规则

| 层级 | 数量目标 | 判定建议 |
| --- | ---: | --- |
| 第一层核心词 | 800 | 大纲词 + 真题高频，阅读反复出现 |
| 第二层高频词 | 600 | 大纲词 + 阅读/作文常见 |
| 第三层一般词 | 400 | 大纲词中较少出现，但建议掌握 |
| 第四层低频词 | 400+ | 考前查漏补缺 |

## 正式词条 JSON 字段

```json
{
  "word": "provide",
  "meaning": "提供",
  "pos": "v.",
  "example": "The school provides useful information for students.",
  "layer": 1,
  "theme": "education",
  "frequency": 18,
  "sections": ["reading", "writing"],
  "sourceType": "syllabus",
  "sourceName": "PETS 第三级考试大纲",
  "sourceRank": "S1",
  "verified": true
}
```

## 实施步骤

1. 先建立 `pets3-words.official.json`，只收录可追溯词条。
2. 把当前 HTML 内置词库标记为 `draft`，不再当成正式来源。
3. 对真题材料只做词频统计，不保存题目原文。
4. 生成 `core-1400`：优先按 `S1 + S2 frequency` 排序，再用 `S3` 补齐释义、词性、例句。
5. 页面加载正式词库时展示来源层级；未验证词显示为“待核验”。

## 当前分层实现

当前 `pets3-words.official.json` 由 `scripts/extract_pets3_words.py` 从同目录 PDF 生成：

- 基础词表：`3.2PETS3词汇完整版带音标-顺序版.pdf`
- 分层参考：同目录所有 `*.pdf`
- 权重最高：`500个考过10次的高频词`、`700个核心词汇`、`高频词汇`
- 辅助参考：高频动词结构、不规则动词、非谓语结构、词汇速记等 PDF

分层目标：

- 第一层：800 个核心词
- 第二层：600 个高频词
- 第三层：400 个一般词
- 第四层：剩余查阅词

注意：这是“同目录资料加权分层”，不是官方公布的频率排名。后续如果加入真实 PETS3 真题语料统计，可以继续重排。
