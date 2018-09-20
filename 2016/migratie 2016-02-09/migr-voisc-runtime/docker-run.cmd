docker kill voisc
docker rm -v voisc
docker run -d --name voisc --link voisc_db --link routering --link mailbox -p 1399:1399 -e JMX_HOST=192.168.99.100 -e JMX_PORT=1399 %*
