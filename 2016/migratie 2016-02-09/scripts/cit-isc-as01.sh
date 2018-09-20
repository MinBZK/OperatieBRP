sudo su jboss -c "cd /opt/jboss/bin;
./shutdown.sh -S -u admin -p admin;
./run.sh -g ISC -u 239.255.100.100 -b 0.0.0.0 -Djboss.messaging.ServerPeerID=1 -Djboss.messaging.groupname=QAPostOffice > output.txt &"
