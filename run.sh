#!/bin/bash

# WareSense Startup Script

echo "=========================================="
echo "🚀 Starting WareSense WMS..."
echo "=========================================="

# 1. Configure Java 17
JAVA_VER=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
if [[ "$JAVA_VER" != "17"* ]]; then
    # Try to find Homebrew version
    if [ -d "/opt/homebrew/opt/openjdk@17" ]; then
        export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
        export PATH="$JAVA_HOME/bin:$PATH"
        echo "✅  Switched to OpenJDK 17: $(java -version 2>&1 | head -n 1)"
    elif [ -d "/usr/local/opt/openjdk@17" ]; then # Intel Mac check
        export JAVA_HOME="/usr/local/opt/openjdk@17"
        export PATH="$JAVA_HOME/bin:$PATH"
        echo "✅  Switched to OpenJDK 17: $(java -version 2>&1 | head -n 1)"
    else
        echo "⚠️  Java 17 not found! Current: $JAVA_VER. Proceeding anyway, but build may fail."
    fi
else
    echo "✅  Java 17 is active."
fi

# 2. Start Docker Database
echo "------------------------------------------"
echo "🐘 Checks & MongoDB (Docker)..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "🐳  Docker is not running. Attempting to start Docker Desktop..."
    
    if [ -d "/Applications/Docker.app" ]; then
        open /Applications/Docker.app
        echo "⏳  Waiting for Docker to initialize (this may take a minute so be patient)..."
        
        # Wait loop
        n=0
        until [ $n -ge 20 ]
        do
            docker info > /dev/null 2>&1 && break
            n=$((n+1))
            echo -n "."
            sleep 3
        done
        echo ""
        
        if ! docker info > /dev/null 2>&1; then
            echo "❌  Docker failed to start automatically. Please start it manually and try again."
            exit 1
        else
             echo "✅  Docker is now running!"
        fi
    else
        echo "❌  Docker Desktop not found in /Applications. Please install/start it manually."
        exit 1
    fi
fi

# Start Containers
echo "🔄  Spinning up Database..."
docker-compose up -d
if [ $? -eq 0 ]; then
    echo "✅  Database container is running."
else
    echo "❌  Failed to start Database."
    exit 1
fi

# 3. Build Project
echo "------------------------------------------"
echo "🔨  Building Project (Skipping Tests)..."
mvn clean install -DskipTests
if [ $? -ne 0 ]; then
    echo "❌  Build Failed! Check errors above."
    exit 1
else
    echo "✅  Build Success!"
fi

# 4. Run Application
echo "------------------------------------------"
echo "🟢  Starting Application..." 
echo "👉  API Documentation: http://localhost:8080/swagger-ui/index.html"
echo "------------------------------------------"

mvn spring-boot:run
