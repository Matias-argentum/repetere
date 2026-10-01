from minio import Minio
from minio.error import S3Error
import io
import os
from dotenv import load_dotenv

load_dotenv()

client = Minio(
    os.getenv("MINIO_ENDPOINT", "localhost:9000"),
    access_key=os.getenv("MINIO_ACCESS_KEY", "minioadmin"),
    secret_key=os.getenv("MINIO_SECRET_KEY", "minioadmin"),
    secure=os.getenv("MINIO_SECURE", "false").lower() == "true"
)

bucket_name = os.getenv("MINIO_BUCKET_NAME", "repetere-audios")

if not client.bucket_exists(bucket_name):
    client.make_bucket(bucket_name)

def object_exists(object_name: str) -> bool:
    try:
        client.stat_object(bucket_name, object_name)
        return True
    except S3Error as e:
        if e.code == "NoSuchKey":
            return False
        raise e

def upload_audio_bytes(object_name: str, audio_bytes: bytes):
    audio_stream = io.BytesIO(audio_bytes)
    # pasa len(audio_bytes) para que MinIO sepa el tamaño exacto
    client.put_object(
        bucket_name=bucket_name,
        object_name=object_name,
        data=audio_stream,
        length=len(audio_bytes),
        content_type="audio/mpeg"
    )