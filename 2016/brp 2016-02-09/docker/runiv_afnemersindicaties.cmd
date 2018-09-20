@echo off

echo Laden initiele vulling afnemersindicaties ...
docker run -ti --rm=true --name iv_naarbrp --link gbav_db --link iv_routering migr-init-naarbrp ./createInitVullingAfnInd.sh

echo Uitvoeren initiele vulling afnemersindicaties ...
docker run -ti --rm=true --name iv_naarbrp --link gbav_db --link iv_routering migr-init-naarbrp ./runInitVullingAfnInd.sh
