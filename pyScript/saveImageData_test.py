import cv2
import face_recognition
import os
import pickle
import numpy as np

base_image_dir = "images"

for folder_name in os.listdir(base_image_dir):
    folder_path = os.path.join(base_image_dir, folder_name)
    
    if os.path.isdir(folder_path):
        encodings = {}
        for filename in os.listdir(folder_path):
            if filename.endswith(".jpg") or filename.endswith(".jpeg") or filename.endswith(".png"):
                img_path = os.path.join(folder_path, filename)
                img = cv2.imread(img_path)
                if img is None:
                    print(f"Error loading image {img_path}")
                    continue

                rgb_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                
                face_locations = face_recognition.face_locations(rgb_img)
                face_enc = face_recognition.face_encodings(rgb_img, face_locations)
                
                if face_enc:
                    person_name = folder_name
                    if person_name not in encodings:
                        encodings[person_name] = []
                    encodings[person_name].append((face_enc[0], filename))  # Store encoding with image name
                    print(f"Encoding complete for {filename} in {folder_name}")

        with open(os.path.join("pickles", f"{folder_name}.pkl"), "wb") as f:
            pickle.dump(encodings, f)
        print(f"Pickle file created for {folder_name}")

print("All done!")
