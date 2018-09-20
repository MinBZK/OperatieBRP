docker kill isc_console
docker rm -v isc_console
docker run -d --name isc_console --link isc --link isc_db -p 8080:8080 %*
