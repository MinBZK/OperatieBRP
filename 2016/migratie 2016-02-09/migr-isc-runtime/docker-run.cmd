docker kill isc
docker rm -v isc
docker run -d --name isc --link isc_db --link routering  -p 1099:1099 -e JMX_HOST=192.168.99.100 -e JMX_PORT=1099 %*
