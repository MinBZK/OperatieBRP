#!/usr/bin/env bash
echo "Running production build..."
ng build --prod

echo "Building Docker image..."
docker build -t brp/beheer-ng2 .