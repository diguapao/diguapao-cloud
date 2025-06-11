# start_model.py

from fastapi import FastAPI, HTTPException
from diffusers import DiffusionPipeline
import torch
import uvicorn
import threading

app = FastAPI()
pipe = None

def load_model():
    global pipe
    try:
        print("Loading model...")
        # 在笔者的机器上，模型下载后位于：C:\Users\Administrator\.cache\huggingface\hub
        pipe = DiffusionPipeline.from_pretrained(
            "stabilityai/stable-diffusion-xl-base-1.0",
            torch_dtype=torch.float16,
            use_safetensors=True,
            variant="fp16"
        )
        # 根据你是否有 CUDA 或使用 CPU 设置设备
        device = "cuda" if torch.cuda.is_available() else "cpu"
        pipe.to(device)
        print(f"Model loaded on {device}")
    except Exception as e:
        print(f"Failed to load model: {e}")
        raise

@app.on_event("startup")
async def startup_event():
    load_model()

@app.get("/generate")
async def generate_image(prompt: str):
    if pipe is None:
        raise HTTPException(status_code=500, detail="Model not loaded")

    print(f"Generating image for prompt: {prompt}")
    image = pipe(prompt).images[0]
    image_path = "output.png"
    image.save(image_path)
    return {"message": f"Image saved as {image_path}"}

@app.get("/health")
async def health_check():
    return {"status": "running", "model_loaded": pipe is not None}

if __name__ == "__main__":
    # 启动 FastAPI 服务
    uvicorn.run(app, host="127.0.0.1", port=8000)

