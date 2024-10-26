import cv2
import face_recognition
import pickle
import sys
import numpy as np

def read_image(image_path):
    try:
        frame = cv2.imread(image_path)
        if frame is None:
            raise ValueError("Error: Could not read frame from file.")
        return frame
    except Exception as e:
        print(e)
        return None

# Load encodings from file
with open("pickles/2.pkl", "rb") as f:
    encodings = pickle.load(f)

# Read frame
frame = read_image("frames/frame1.jpeg")
if frame is None:
    sys.exit(1)

rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

# Find face locations and encodings using CNN model for more accuracy
face_locations = face_recognition.face_locations(rgb_frame)
face_encodings = face_recognition.face_encodings(rgb_frame, face_locations)

# Define a lower threshold for face matching
threshold = 0.45

# Compare faces
for face_encoding in face_encodings:
    found_match = False
    for person_name, stored_encodings in encodings.items():
        for stored_encoding, filename in stored_encodings:
            results = face_recognition.compare_faces([stored_encoding], face_encoding)
            if True in results:
                print(f"Best match: {person_name}, Image: {filename}")
                found_match = True
                break
        if found_match:
            break
    if not found_match:
        print("No match found")

print("Completed face recognition on clear frames.")
