#!/bin/sh
gd=docker
gr=$(groups "$USER" | grep -o -w "\b$gd\b")

if [ "$gd" = "$gr" ]; then
    cd $(dirname $0)/.. || exit 1
    docker-compose stop
else
    echo "User $USER is not a member of the Docker group."
    exit 1
fi