@echo off

echo Laden initiele vulling personen ...
docker run -ti --rm=true --name iv_naarbrp --link gbav_db --link iv_routering migr-init-naarbrp ./createInitVulling.sh

echo Uitvoeren initiele vulling personen ...
docker run -ti --rm=true --name iv_naarbrp --link gbav_db --link iv_routering migr-init-naarbrp ./runInitVulling.sh

echo Uitvoeren terugconversie initiele vulling personen ...
docker run -ti --rm=true --name iv_naarlo3 --link brpdb --link iv_routering migr-init-naarlo3
