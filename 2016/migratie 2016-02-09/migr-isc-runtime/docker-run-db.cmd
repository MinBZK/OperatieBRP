docker kill isc_db
docker rm -v isc_db
docker run -d --name isc_db %*
