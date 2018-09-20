docker kill routering_db
docker rm -v routering_db
docker run -d --name routering_db %*
