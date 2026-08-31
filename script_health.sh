#!/usr/bin/env bash

# -----------CREADO POR CLAUDE---------------
# 
# Uso: ./monitor_containers.sh [intervalo_segundos]
# Sistema Operativo: Linux - uso: chmod +x script_health.sh ; bash script_health.sh
# Por defecto revisa cada 5 segundos.

INTERVAL=${1:-5}

while true; do
  clear
  echo "=== Estado de contenedores - $(date '+%Y-%m-%d %H:%M:%S') ==="
  echo

  containers=$(docker ps -a --format '{{.Names}}')

  for c in $containers; do
    running=$(docker inspect --format='{{.State.Running}}' "$c" 2>/dev/null)
    health=$(docker inspect --format='{{if .State.Health}}{{.State.Health.Status}}{{else}}sin_healthcheck{{end}}' "$c" 2>/dev/null)

    if [ "$running" == "true" ]; then
      if [ "$health" == "unhealthy" ]; then
        echo "❌ $c -> CORRIENDO pero UNHEALTHY"
        last_log=$(docker inspect --format='{{range .State.Health.Log}}{{.Output}}{{end}}' "$c" 2>/dev/null | tail -n 3)
        echo "   Error: $last_log"
      else
        echo "✅ $c -> SI ($health)"
      fi
    else
      exitcode=$(docker inspect --format='{{.State.ExitCode}}' "$c" 2>/dev/null)
      error=$(docker inspect --format='{{.State.Error}}' "$c" 2>/dev/null)
      echo "❌ $c -> NO está corriendo (ExitCode: $exitcode)"
      if [ -n "$error" ]; then
        echo "   Error: $error"
      else
        echo "   Últimos logs:"
        docker logs --tail 5 "$c" 2>&1 | sed 's/^/   /'
      fi
    fi
    echo
  done

  sleep "$INTERVAL"
done