docker kill brp_db
docker rm -v brp_db
docker run -d --name brp_db %*
