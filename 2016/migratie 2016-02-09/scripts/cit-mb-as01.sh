sudo su - appserver -c "cd /opt/migr-voisc-mailbox;
./stopMailbox.sh;
rm -r /opt/migr-voisc-mailbox/data;
./runMailbox.sh"
