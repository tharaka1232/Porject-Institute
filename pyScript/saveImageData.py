import cv2
import face_recognition
import os
import pickle

base_image_dir = "images"

for folder_name in os.listdir(base_image_dir):
    folder_path = os.path.join(base_image_dir, folder_name)
    
    if os.path.isdir(folder_path):
        encodings = {}
        for filename in os.listdir(folder_path):

            if filename.endswith(".jpg") or filename.endswith(".jpeg") or filename.endswith(".png"):
                img_path = os.path.join(folder_path, filename)
                img = cv2.imread(img_path)
                rgb_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                face_enc = face_recognition.face_encodings(rgb_img)
                
                if face_enc:
                    encodings[filename] = face_enc[0]
                    print(f"Encoding complete for {filename} in {folder_name}")
        
        with open("pickles/" + folder_name + ".pkl", "wb") as f:
            pickle.dump(encodings, f)
        # print(f"Pickle file created for {folder_name}")

