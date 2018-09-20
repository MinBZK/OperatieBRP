docker kill brp_routering
docker rm -v brp_routering
docker run -d --name brp_routering %*
