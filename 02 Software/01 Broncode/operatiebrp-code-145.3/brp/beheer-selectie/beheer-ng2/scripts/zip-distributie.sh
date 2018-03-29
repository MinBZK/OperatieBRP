#!/usr/bin/env bash

echo "Installing NPM modules..."
npm install

echo "Running production build..."
ng build --prod

echo "Zipping distribution..."
gulp zip