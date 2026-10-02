# imagen ligera de pythom
FROM python:3.12-slim

#  Configurar el directorio de trabajo dentro del contenedor
WORKDIR /app

# Prevenir que Python genere archivos .pyc y forzar logs en tiempo real
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# Instalar dependencias del sistema necesarias para audio/S3 (ffmpeg, curl, etc.)
RUN apt-get update && apt-get install -y --no-install-recommends \
    ffmpeg \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copiar únicamente requirements.txt para aprovechar el caché de Docker
COPY requirements.txt .

# Instalar dependencias de Python
RUN pip install --no-cache-dir -r requirements.txt

# Copiar el código fuente de la aplicación
COPY app/ ./app

# Exponer el puerto donde corre FastAPI
EXPOSE 8000

# Comando de arranque del servidor Uvicorn
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
