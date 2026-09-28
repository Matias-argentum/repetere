from pydantic import BaseModel
from typing import List

# datos que llegan desde Java
class CardInput(BaseModel):
    id: int
    word: str
    word_hash: str
    sentence: str
    sentence_hash: str

class AudioGenerationRequest(BaseModel):
    deck_id: int
    minio_lang_folder_path: str
    voice: str
    cards: List[CardInput]

# datos que van a salir hacia Java

class CardAudioResult(BaseModel):
    id: int
    word_audio_key: str
    word_reused: bool
    sentence_reused: bool


class AudioGenerationResponse(BaseModel):
    cards: List[CardAudioResult]