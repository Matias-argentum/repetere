import edge_tts
import tempfile
import os

async def generate_speech_bytes(text: str, voice: str) -> bytes:
    # crea un archivo temporal para que edge-tts escriba el MP3 completo
    with tempfile.NamedTemporaryFile(delete=False, suffix=".mp3") as tmp_file:
        tmp_path = tmp_file.name

    try:
        # genera el audio y lo guarda en el archivo temporal de forma asíncrona
        # rate le baje la velocidad al audio
        communicate = edge_tts.Communicate(text, voice, rate="-10%")
        #communicate = edge_tts.Communicate(text, voice)
        await communicate.save(tmp_path)

        # lee los bytes reales del archivo MP3
        with open(tmp_path, "rb") as f:
            audio_bytes = f.read()

        return audio_bytes

    finally:
        # elimina el archivo temporal del disco
        if os.path.exists(tmp_path):
            os.remove(tmp_path)