import cv2
import face_recognition
import pickle
import sys

def read_image(image_path):
    try:
        frame = cv2.imread(image_path)
        if frame is None:
            raise ValueError("Error: Could not read frame from file.")
        return frame
    except Exception as e:
        print(e)
        return None

def is_face_clear(face_landmarks):
    if face_landmarks:
        return True 
    return False

with open(sys.argv[2], "rb") as f:
    encodings = pickle.load(f)

frame = read_image(sys.argv[1])
if frame is None:
    sys.exit(1)

rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

face_locations = face_recognition.face_locations(rgb_frame)
face_encodings = face_recognition.face_encodings(rgb_frame, face_locations)
face_landmarks_list = face_recognition.face_landmarks(rgb_frame, face_locations)

for i, face_encoding in enumerate(face_encodings):
    face_landmarks = face_landmarks_list[i]
    if is_face_clear(face_landmarks):
        for name, stored_encoding in encodings.items():
            result = face_recognition.compare_faces([stored_encoding], face_encoding)
            if result[0]:
                print(name)

# print("Completed face recognition on clear frames.")
