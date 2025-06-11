# stop_model.py

import os

try:
    with open("model.pid", "r") as f:
        pid = int(f.read())
    os.kill(pid, 9)
    os.remove("model.pid")
    print("Model service stopped.")
except FileNotFoundError:
    print("PID file not found. Service may not be running.")
except ProcessLookupError:
    print("No process found with the given PID.")