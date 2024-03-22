#!/bin/sh
gd=docker
gr=$(groups "$USER" | grep -o -w "\b$gd\b")

if [ "$gd" = "$gr" ]; then
    cd $(dirname $0)/.. || exit 1
    cd movieReviews
    DOCKER_BUILDKIT=1 docker build -f build-elements/Dockerfile -t serv/data_service .
    cd ..
    cd apiMovieReviews
    DOCKER_BUILDKIT=1 docker build -f build-elements/Dockerfile -t serv/api_service .
    cd ..
else
    echo "User $USER is not a member of the Docker group."
    exit 1
fi