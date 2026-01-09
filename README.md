# EcoPoints Recycling Tracker

Rastreador en Java para hogares: registra eventos de reciclaje, calcula EcoPoints y genera reportes comunitarios. Los datos se guardan en `households.ser` (No necesitas crearlo).

## Requisitos
- JDK 11+ (verificar con `java -version`)

## Clonar
```bash
git clone https://github.com/JestebanPedraza/ecopoints-recyclingtracker.git
cd EcoPonitsRecyclingTracker
```

## Compilar y ejecutar (Windows)
```powershell
javac -cp .\classes -d .\classes *.java
java -cp classes EcoPointsRecyclingTracker
```

También se puede abrir el proyecto en un IDE (VS Code, IntelliJ) y ejecutar la clase `EcoPointsRecyclingTracker`.

Uso: la aplicación es por consola y ofrece opciones para registrar hogares, registrar eventos de reciclaje, ver reportes y guardar datos.
