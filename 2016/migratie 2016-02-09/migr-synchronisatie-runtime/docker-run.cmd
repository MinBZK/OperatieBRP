docker kill synchronisatie
docker rm -v synchronisatie
docker run -d --name synchronisatie --link brp_db --link routering --link brp_routering:brproutering -p 1799:1799 -e JMX_HOST=192.168.99.100 -e JMX_PORT=1799 %*
