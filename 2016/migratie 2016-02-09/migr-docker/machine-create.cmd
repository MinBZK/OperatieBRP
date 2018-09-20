docker-machine stop migratie
docker-machine rm migratie -f

docker-machine create migratie --driver virtualbox --virtualbox-memory 16384 --virtualbox-cpu-count 4 --virtualbox-boot2docker-url https://github.com/boot2docker/boot2docker/releases/download/v1.9.1/boot2docker.iso
rem Aanpassen nameserver in virtualbox om verbinding te kunnen maken met fac-reg.modernodam.nl
docker-machine ssh migratie "echo ""echo 'nameserver 192.168.202.11' > /etc/resolv.conf"""" | sudo tee /var/lib/boot2docker/bootlocal.sh > /dev/null"
docker-machine stop migratie

call machine-start.cmd
