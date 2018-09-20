docker kill mailbox
docker rm -v mailbox
docker run -d --name mailbox -p 1599:1599 -p 1212:1212 -e JMX_HOST=192.168.99.100 -e JMX_PORT=1599 %*
