import sys
import numpy as np
import onnxruntime as ort
import joblib
import requests

def load_label_encoder():
    return joblib.load('C:\\Users\\BOUZIDI MALEK\\Downloads\\new_country_encoder.pkl')

def fetch_image_url(country_name):
    access_key = 'heVtL1Pcp0sXmgjewrzu5cH9xVFureZYNeCkHEag1FA'
    url = f'https://api.unsplash.com/search/photos?query={country_name}&client_id={access_key}'
    response = requests.get(url)
    data = response.json()
    if data['results']:
        return data['results'][0]['urls']['regular']  # Return the URL of the first image
    return None

def main(country, year, month, day):
    model_path = "C:\\Users\\BOUZIDI MALEK\\Downloads\\model.onnx"
    session = ort.InferenceSession(model_path)
    input_name = session.get_inputs()[0].name

    le_country = load_label_encoder()
    user_country_code = le_country.transform([country])[0]
    input_data = np.array([[year, month, day, user_country_code]], dtype=np.float32)

    outputs = session.run(None, {input_name: input_data})
    probabilities = outputs[1][0]

    if isinstance(probabilities, dict):
        probabilities = np.array([probabilities[k] for k in sorted(probabilities.keys())])
    else:
        probabilities = np.array(probabilities)

    top_indices = np.argsort(probabilities)[-10:][::-1]
    top_country_names = le_country.inverse_transform(top_indices)
    top_probabilities = probabilities[top_indices]

    print("Top 10 Country Predictions and their Probabilities:")
    for name, prob in zip(top_country_names, top_probabilities):
        print(f"{name}: {prob:.4f}")
        image_url = fetch_image_url(name)
        if image_url:
            print(f"Image URL for {name}: {image_url}")
        else:
            print(f"No image found for {name}")

if __name__ == "__main__":
    main(*sys.argv[1:])  # Expects four arguments: country, year, month, day
