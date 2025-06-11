__all__ = ["add", "subtract"]

def add(a, b):
    return a + b
def subtract(a, b):
    return a - b
def multiply(a, b):
    return a * b
def divide(a, b):
    if (b):
        return a / b
    else:
        print("error")

if __name__ == "__main__":
    print(multiply(3, 4))
    print(divide(3, 4))
else:
    print(__name__)
