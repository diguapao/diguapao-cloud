python -m pip install --upgrade pip
pip install fastapi uvicorn diffusers safetensors accelerate transformers bitsandbytes
pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/xpu
pip install --pre torch torchvision torchaudio --index-url https://download.pytorch.org/whl/nightly/xpu

# 更多参见：https://pytorch.ac.cn/tutorials/prototype/inductor_windows.html

