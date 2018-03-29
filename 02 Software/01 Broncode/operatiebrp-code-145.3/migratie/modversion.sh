#! /bin/sh

while getopts a:b:m: opt; do
    case $opt in
        a) alg_version="$OPTARG" >&2
        ;;
        b) brp_version="$OPTARG" >&2
        ;;
        m) migr_version="$OPTARG" >&2
        ;;
        \?) echo "Ongeldige optie -$OPTARG" Alleen a algemeen b brp m migratie zijn geldig >&2
        ;;
    esac
done

if [ -z $alg_version ];
    then echo geef -a [alg_versie] mee && exit 1;
fi;
if [ -z $brp_version ];
    then echo geef -b [brp_versie] mee && exit 1;
fi;
if [ -z $migr_version ];
    then echo geef -m [migr_versie] mee && exit 1;
fi;

mvn versions:set -DnewVersion=$migr_version -DgenerateBackupPoms=false
mvn -f migr/pom.xml versions:set -DnewVersion=$migr_version -DgenerateBackupPoms=false
mvn -f migr-extern/pom.xml versions:set -DnewVersion=$migr_version -DgenerateBackupPoms=false

sed -i "s/<alg\.version>.*<\/alg\.version>/<alg.version>$alg_version<\/alg.version>/" migr-tools-deployer/deployer/docker-base.pom.xml
sed -i "s/<alg\.compose\.version>.*<\/alg\.compose\.version>/<alg.compose.version>$alg_version<\/alg.compose.version>/" migr-tools-deployer/deployer/docker-base.pom.xml
sed -i "s/<migratie\.version>.*<\/migratie\.version>/<migratie.version>$migr_version<\/migratie.version>/" migr-tools-deployer/deployer/docker-base.pom.xml
sed -i "s/<brp\.version>.*<\/brp\.version>/<brp.version>$brp_version<\/brp.version>/" migr-tools-deployer/deployer/docker-base.pom.xml

sed -i "s/<algemeen\.version>.*<\/algemeen\.version>/<algemeen.version>$alg_version<\/algemeen.version>/" migr-tools-deployer/deployer/deploy-properties.pom.xml
sed -i "s/<migratie\.version>.*<\/migratie\.version>/<migratie.version>$migr_version<\/migratie.version>/" migr-tools-deployer/deployer/deploy-properties.pom.xml
sed -i "s/<brp\.version>.*<\/brp\.version>/<brp.version>$brp_version<\/brp.version>/" migr-tools-deployer/deployer/deploy-properties.pom.xml

sed -i "s/<alg\.integratie\.version>.*<\/alg\.integratie\.version>/<alg.integratie.version>$alg_version<\/alg.integratie.version>/" migr-test-isc/regressie-base.pom.xml

export vervang_alg='1h;1!H;${;g;s/<version>.*<relativePath/<version>'$alg_version'<\/version>\n    <relativePath/p;}'
sed -n "$vervang_alg" migr/pom.xml > migr/pom.nieuw
mv migr/pom.nieuw migr/pom.xml

export vervang_migr='1h;1!H;${;g;s/<version>.*<relativePath/<version>'$migr_version'<\/version>\n    <relativePath/p;}'
for file in `ls migr-test-isc/*pom.xml`; do
    sed -n "$vervang_migr" $file > migr-test-isc/pom.temp
    mv migr-test-isc/pom.temp $file
done
