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

mvn versions:set -DnewVersion=$brp_version -DgenerateBackupPoms=false
mvn -f brp-parent/pom.xml versions:set -DnewVersion=$brp_version -DgenerateBackupPoms=false
mvn -f build/code-stijl-regels/pom.xml versions:set -DnewVersion=$brp_version -DgenerateBackupPoms=false
mvn -f build/brp-test-properties/pom.xml versions:set -DnewVersion=$brp_version -DgenerateBackupPoms=false

sed -i "s/<algemeen\.versie>.*<\/algemeen\.versie>/<algemeen.versie>$alg_version<\/algemeen.versie>/" brp-parent/pom.xml
sed -i "s/<migratie\.versie>.*<\/migratie\.versie>/<migratie.versie>$migr_version<\/migratie.versie>/" brp-parent/pom.xml
