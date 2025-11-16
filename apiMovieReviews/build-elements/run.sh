#!/bin/sh
echo "Entrypoint sh script started"
exec java -cp "app:app/lib/*" ${JAVA_OPTS} ${APPLICATIONCLASS} ${@}