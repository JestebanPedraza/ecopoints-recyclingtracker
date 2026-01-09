# EcoPoints Recycling Tracker

Rastreador en Java para hogares: registra eventos de reciclaje, calcula EcoPoints y genera reportes comunitarios. Los datos se guardan en `households.ser` (ignorado por Git).

## Requisitos
- Git
- JDK 11+ (verificar con `java -version`)

## Clonar
```bash
git clone <REPO_URL>
cd EcoPonitsRecyclingTracker
```

## Compilar y ejecutar (Windows)
```powershell
javac -d classes *.java
java -cp classes EcoPointsRecyclingTracker
```

También se puede abrir el proyecto en un IDE (VS Code, IntelliJ) y ejecutar la clase `EcoPointsRecyclingTracker`.

Uso: la aplicación es por consola y ofrece opciones para registrar hogares, registrar eventos de reciclaje, ver reportes y guardar datos.
