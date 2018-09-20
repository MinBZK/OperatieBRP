@echo off

echo Laden initiele vulling autorisatie ...
docker run -ti --rm=true --name iv_naarbrp --link gbav_db --link iv_routering migr-init-naarbrp ./createInitVullingAut.sh

echo Uitvoeren initiele vulling autorisatie ...
docker run -ti --rm=true --name iv_naarbrp --link gbav_db --link iv_routering migr-init-naarbrp ./runInitVullingAut.sh
