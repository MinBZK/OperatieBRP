#!/bin/sh

# Substitute env vars
envsubst '$API_URL' \
< /etc/nginx/conf.d/frontend.conf.template \
> /etc/nginx/conf.d/default.conf

# Start server
nginx -g 'daemon off;'
