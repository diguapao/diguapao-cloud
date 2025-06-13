import torch
from diffusers import StableDiffusionXLPipeline

# from transformers import BitsAndBytesConfig
# quantization_config = BitsAndBytesConfig(load_in_8bit=True)

# ===========================
# 1. 设置显存优化参数
# ===========================
torch_dtype = torch.float16  # FP16 半精度
variant = "fp16"
enable_vae_slicing = True    # VAE 分块处理
enable_sequential_cpu_offload = True  # 顺序 CPU 卸载

# ===========================
# 2. 加载 Stable Diffusion XL 模型（显式指定 XPU）
# ===========================
model_id = "stabilityai/stable-diffusion-xl-base-1.0"

pipe = StableDiffusionXLPipeline.from_pretrained(
    model_id,
    torch_dtype=torch_dtype,
    variant=variant,
    use_safetensors=True,
    device="xpu",  # 显式指定 XPU
)

# ===========================
# 3. 应用显存优化策略
# ===========================
if enable_vae_slicing:
    pipe.enable_vae_slicing()  # 降低显存占用

if enable_sequential_cpu_offload:
    pipe.enable_sequential_cpu_offload()  # 将非活跃模块移至 CPU

# ===========================
# 4. 生成图像
# ===========================
prompt = "A futuristic city skyline at sunset, high resolution, detailed architecture"
negative_prompt = "low quality, blurry, cartoon style"

image = pipe(
    prompt=prompt,
    negative_prompt=negative_prompt,
    num_inference_steps=30,
    guidance_scale=7.5,
    width=1024,
    height=1024,
).images[0]

image.save("generated_image.png")
print("图像生成完成并保存为 generated_image.png")