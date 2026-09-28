from fastapi import FastAPI

app = FastAPI(title="TTS Service", version="1.0.0")

@app.get("/")
def read_root():
    return {"message": "Hola, pruebo fastAPI!"}