# start_model.py
import os
import time
import torch
import uvicorn
import threading

from PIL import Image
from fastapi.responses import Response
from fastapi import FastAPI, HTTPException
from diffusers import DiffusionPipeline, BitsAndBytesConfig, StableDiffusionXLPipeline, StableDiffusionImg2ImgPipeline

# ===========================
# 1. 设置显存优化参数
# ===========================
variant = "fp16"
torch_dtype = torch.float16  # FP16 半精度
enable_vae_slicing = True  # VAE 分块处理
enable_sequential_cpu_offload = True  # 顺序 CPU 卸载
quantization_config = BitsAndBytesConfig(load_in_8bit=True)  # 启用 8-bit 量化，需安装 bitsandbytes（需支持 XPU）以降低显存占用，需要安装依赖：pip install bitsandbytes

# ===========================
# 2. 加载 Stable Diffusion XL 模型（显式指定 XPU）
# ===========================
model_id = "stabilityai/stable-diffusion-xl-base-1.0"

app = FastAPI()
pipe = None

print('torch.cuda.is_available：' + str(torch.cuda.is_available()))
print('torch.cpu.is_available：' + str(torch.cpu.is_available()))
print('torch.xpu.is_available：' + str(torch.xpu.is_available()))  # torch.xpu is the API for Intel GPU support


def load_model():
    global pipe
    try:
        print("Loading model...")
        # 在笔者的机器上，模型下载后位于：C:\Users\Administrator\.cache\huggingface\hub
        # pipe = DiffusionPipeline.from_pretrained(
        # pipe = StableDiffusionXLPipeline.from_pretrained(
        pipe = StableDiffusionImg2ImgPipeline.from_pretrained(
            model_id,
            torch_dtype=torch_dtype,
            variant=variant,
            use_safetensors=True,
            quantization_config=quantization_config,
        )

        # 根据你是否有 CUDA 或使用 CPU 或者 XPU 设置设备
        global pipe_device
        pipe_device = "cuda" if torch.cuda.is_available() else "xpu"
        pipe_device = "xpu" if torch.xpu.is_available() else "cpu"
        print(f"Model loaded on {pipe_device}")

        # ===========================
        # 3. 应用显存优化策略
        # ===========================
        if enable_vae_slicing:
            pipe.enable_vae_slicing()  # 降低显存占用

        # if enable_sequential_cpu_offload:
        #     pipe.enable_sequential_cpu_offload()  # 将非活跃模块移至 CPU


    except Exception as e:
        print(f"Failed to load model: {e}")
        raise


@app.on_event("startup")
async def startup_event():
    load_model()


@app.get("/generate")
async def generate_image(width: int, height: int, steps: int, seed: int,
                         guidance_scale: float,
                         num_inference_steps: int,
                         negative_prompt: str,
                         prompt: str,
                         ):
    if pipe is None:
        raise HTTPException(status_code=500, detail="Model not loaded")

    print(f"Generating image for prompt: {prompt}")
    output_dir = ("E:/DiGuaPao/gitee/diguapao-cloud/examples-collection/examples-java/examples-ai/LangChain4j-AI/src/main/resources/script/py_output_img/")

    # 假设reference_image是你想要用作参考的PIL Image
    reference_image = Image.open("E:/DiGuaPao/gitee/diguapao-cloud/examples-collection/examples-java/examples-ai/LangChain4j-AI/src/main/resources/script/temp.jpg").convert("RGB")
    reference_image = reference_image.resize((750, 1000))  # 调整大小以匹配目标尺寸
    generator = torch.Generator(device=pipe_device).manual_seed(seed)

    image = pipe(
        # width=width,
        # height=height,
        steps=steps,
        # seed=seed,
        guidance_scale=guidance_scale,
        num_inference_steps=num_inference_steps,
        negative_prompt=negative_prompt,
        prompt=prompt,
        image=reference_image,
        strength=0.75,  # 控制参考图像的影响程度，值越小影响越大
        generator=generator,
    )

    image_paths = []
    for i, image in enumerate(image.images):
        timestamp = str(int(time.time()))
        image_path = os.path.join(output_dir, f"{timestamp}_output_{i}.png")
        image.save(image_path)
        image_paths.append(image_path)

    # 返回图片
    with open(image_paths[0], "rb") as image_file:
        image_bytes = image_file.read()
    return Response(content=image_bytes, media_type="image/jpeg")


@app.get("/health")
async def health_check():
    return {"status": "running", "model_loaded": pipe is not None}


if __name__ == "__main__":
    # 启动 FastAPI 服务
    uvicorn.run(app, host="127.0.0.1", port=8000)
