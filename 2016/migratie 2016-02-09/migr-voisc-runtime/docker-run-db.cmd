docker kill voisc_db
docker rm -v voisc_db
docker run -d --name voisc_db %*
