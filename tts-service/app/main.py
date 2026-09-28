import asyncio
from fastapi import FastAPI
from app.schemas import AudioGenerationRequest, AudioGenerationResponse, CardAudioResult
from app.tts import generate_speech_bytes
from app.storage import object_exists, upload_audio_bytes

app = FastAPI(title="TTS Service", version="1.0.0")

@app.get("/")
def read_root():
    return {"message": "TTS Service Running!"}

# funcion para procesar una sola card
async def process_single_card(card, minio_lang_folder_path: str, voice: str) -> CardAudioResult:
    word_key = f"{minio_lang_folder_path}/{card.word_hash}.mp3"
    sentence_key = f"{minio_lang_folder_path}/{card.sentence_hash}.mp3"

    # 1. Palabra
    word_reused = object_exists(word_key)
    if not word_reused:
        word_bytes = await generate_speech_bytes(card.word, voice)
        upload_audio_bytes(word_key, word_bytes)

    # 2. Frase
    sentence_reused = object_exists(sentence_key)
    if not sentence_reused:
        sentence_bytes = await generate_speech_bytes(card.sentence, voice)
        upload_audio_bytes(sentence_key, sentence_bytes)

    return CardAudioResult(
        id=card.id,
        word_audio_key=word_key,
        sentence_audio_key=sentence_key,
        word_reused=word_reused,
        sentence_reused=sentence_reused
    )

# endpoint
@app.post("/api/v1/audio/generate", response_model=AudioGenerationResponse)
async def generate_audios(payload: AudioGenerationRequest):
    # crea una lista de tareas: una invocacion por cada card recibida
    tasks = [
        process_single_card(card, payload.minio_lang_folder_path, payload.voice)
        for card in payload.cards
    ]

    # ejecuta todas las cards en paralelo y espera a que terminen
    results = await asyncio.gather(*tasks)

    return AudioGenerationResponse(cards=results)