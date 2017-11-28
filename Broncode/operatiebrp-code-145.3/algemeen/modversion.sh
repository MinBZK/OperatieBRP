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

mvn versions:set -DnewVersion=$alg_version -DgenerateBackupPoms=false
mvn -f alg/pom.xml versions:set -DnewVersion=$alg_version -DgenerateBackupPoms=false
