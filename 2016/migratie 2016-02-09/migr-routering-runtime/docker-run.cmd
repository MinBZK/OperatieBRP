docker kill routering
docker rm -v routering
docker run -d --name routering --link routering_db -p 1199:1199 -e JMX_HOST=192.168.99.100 -e JMX_PORT=1199 %*
