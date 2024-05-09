import sys
import requests
from PIL import Image
import io

# Check if an argument is provided
if len(sys.argv) > 1:
    input_text = sys.argv[1]
else:
    input_text = "Default input"  # Fallback input if none is provided

API_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-xl-base-1.0"
headers = {"Authorization": "Bearer ****api key*******"}

def query(payload):
    response = requests.post(API_URL, headers=headers, json=payload)
    return response.content

image_bytes = query({
    "inputs": input_text,
})
print(input_text)
image = Image.open(io.BytesIO(image_bytes))
image.save("C:\\Users\\User\\IdeaProjects\\ProjectHOpe\\src\\main\\resources\\assets\\image.jpg")
image.show()
# Save the image to a file
