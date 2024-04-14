#!/bin/sh
cd $(dirname $0)/.. || exit 1
cd movieReviews
DOCKER_BUILDKIT=1 docker build -f build-elements/Dockerfile -t serv/data_service .
cd ..
cd apiMovieReviews
DOCKER_BUILDKIT=1 docker build -f build-elements/Dockerfile -t serv/api_service .
cd ..
