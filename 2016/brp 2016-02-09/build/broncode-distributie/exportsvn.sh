#!/bin/bash
#
# Dit script aanroepen met een datum in het formaat "2013-03-25"
SVN_LOC=https://www.modernodam.nl/svn/brp-code

if [ -z "$1" ]
  then
    echo "Roep dit script aan met een datum in het formaat yyyy-mm-dd."
    exit 1
fi

DATUM=$1
DIRECTORY=broncode-$1

if [ -d "$DIRECTORY" ]; then
  rm -rf $DIRECTORY
  echo "Bestaande folder '$DIRECTORY' verwijderd."
fi

if [ -f "$DIRECTORY.tar" ]; then
  rm $DIRECTORY.tar
  echo "Bestaande '$DIRECTORY.tar' verwijderd."
fi

mkdir $DIRECTORY
echo "Nieuwe (lege) folder $DIRECTORY aangemaakt."

echo "Export SVN van datum $1 en locatie $SVN_LOC"

set -x

#Maven parent POM voor BRP projecten
svn export $SVN_LOC/parent/trunk@{$DATUM} $DIRECTORY/parent > /dev/null

#Generatoren en Metaregister
svn export $SVN_LOC/generatie/trunk/generatoren@{$DATUM} $DIRECTORY/generatie/generatoren > /dev/null
svn export $SVN_LOC/generatie/trunk/metaregister@{$DATUM} $DIRECTORY/generatie/metaregister > /dev/null

#Algemene bibliotheken
svn export $SVN_LOC/algemeen/algemeen-bom/trunk@{$DATUM} $DIRECTORY/algemeen-bom > /dev/null
svn export $SVN_LOC/algemeen/algemeen-model/trunk@{$DATUM} $DIRECTORY/algemeen-model > /dev/null
svn export $SVN_LOC/algemeen/algemeen-opslag/trunk@{$DATUM} $DIRECTORY/algemeen-opslag > /dev/null
svn export $SVN_LOC/algemeen/algemeen-kern/trunk@{$DATUM} $DIRECTORY/algemeen-kern > /dev/null
svn export $SVN_LOC/algemeen/algemeen-webservice/trunk@{$DATUM} $DIRECTORY/algemeen-webservice > /dev/null
svn export $SVN_LOC/algemeen/algemeen-koppelvlak/trunk@{$DATUM} $DIRECTORY/algemeen-koppelvlak > /dev/null

#Automatische Regressie Test componenten
svn export $SVN_LOC/art/art-basis/trunk@{$DATUM} $DIRECTORY/art-basis > /dev/null
svn export $SVN_LOC/art/art-framework/trunk@{$DATUM} $DIRECTORY/art-framework > /dev/null
svn export $SVN_LOC/art/art-data/trunk@{$DATUM} $DIRECTORY/art-data > /dev/null
svn export $SVN_LOC/art/art-bijhouding/trunk@{$DATUM} $DIRECTORY/art-bijhouding > /dev/null
svn export $SVN_LOC/art/art-ondersteuning/trunk@{$DATUM} $DIRECTORY/art-utils > /dev/null
svn export $SVN_LOC/art/art-rapportage/trunk@{$DATUM} $DIRECTORY/art-rapportage > /dev/null
svn export $SVN_LOC/utils/art-archetype/trunk/@{$DATUM} $DIRECTORY/art-archetype > /dev/null
svn export $SVN_LOC/test/testcases/trunk/@{$DATUM} $DIRECTORY/test/testcases > /dev/null

#Bijhouding componenten
svn export $SVN_LOC/bijhouding/trunk@{$DATUM} $DIRECTORY/bijhouding-service > /dev/null

#Bevraging componenten
svn export $SVN_LOC/bevraging/trunk@{$DATUM} $DIRECTORY/bevraging-service > /dev/null

#Levering componenten
svn export $SVN_LOC/levering/levering-bom/trunk@{$DATUM} $DIRECTORY/levering-bom > /dev/null
svn export $SVN_LOC/levering/levering-kern/trunk@{$DATUM} $DIRECTORY/levering-kern > /dev/null
svn export $SVN_LOC/levering/levering-protocollering/trunk@{$DATUM} $DIRECTORY/levering-protocollering > /dev/null

#Levering services
svn export $SVN_LOC/levering/levering-services/trunk@{$DATUM} $DIRECTORY/levering-services > /dev/null

#Levering routes
svn export $SVN_LOC/levering/levering-routering/trunk@{$DATUM} $DIRECTORY/levering-routering > /dev/null
svn export $SVN_LOC/levering/selecties/trunk@{$DATUM} $DIRECTORY/levering-selecties > /dev/null
#svn export $SVN_LOC/levering/levering-lo3/trunk@{$1} $DIRECTORY/levering-lo3 > /dev/null

#Performance tools
svn export $SVN_LOC/test/performance/trunk@{$DATUM} $DIRECTORY/test/performance > /dev/null
svn export $SVN_LOC/utils/performance-test-runner/trunk@{$DATUM} $DIRECTORY/utils/performance-test-runner > /dev/null

#Beheer tools
svn export $SVN_LOC/beheer/blobify/trunk@{$DATUM} $DIRECTORY/beheer/blobify > /dev/null
svn export $SVN_LOC/utils/brp-jvm-beheer/trunk@{$DATUM} $DIRECTORY/tools-brp-jvm-beheer > /dev/null

#Build utilities
svn export $SVN_LOC/utils/bedrijfsregels/bedrijfsregel-javadoc-taglet/trunk@{$DATUM} $DIRECTORY/build-taglet > /dev/null
svn export $SVN_LOC/utils/bedrijfsregels/bedrijfsregel-rapportage/trunk@{$DATUM} $DIRECTORY/tools-bedrijfsregelsrapportage > /dev/null
svn export $SVN_LOC/build/code-stijl-regels/trunk@{$DATUM} $DIRECTORY/build-utils > /dev/null
svn export $SVN_LOC/build/distributie/trunk@{$DATUM} $DIRECTORY/distributie > /dev/null
svn export $SVN_LOC/build/brp-modules/trunk@{$DATUM} $DIRECTORY/brp-modules > /dev/null
svn export $SVN_LOC/build/broncode-distributie/trunk@{$DATUM} $DIRECTORY/broncode-distributie > /dev/null
svn export $SVN_LOC/test/deployment-jenkins/trunk@{$DATUM} $DIRECTORY/tools-deployment > /dev/null
svn export $SVN_LOC/test/deployment-art-omgeving/trunk@{$DATUM} $DIRECTORY/art-omgeving > /dev/null

#Proeftuin
svn export $SVN_LOC/test/proeftuin-database-generator/trunk@{$DATUM} $DIRECTORY/testdata-whitebox > /dev/null

#Tools
svn export $SVN_LOC/utils/technische-sleutel-webapp/trunk@{$DATUM} $DIRECTORY/tools-technische-sleutel-webapp > /dev/null


# Nog niet meegenomen in broncode-distributie
# svn export $SVN_LOC/utils/stamtabel-generator/trunk@{$1} $DIRECTORY/utils/stamtabel-generator > /dev/null


set +x

if [ -d "$DIRECTORY/testdata-whitebox/src/main/resources/scenarios/VoorLeveranciersOpWhitebox" ]; then
    rm -rf $DIRECTORY/testdata-whitebox/src/main/resources/scenarios/VoorLeveranciersOpWhitebox
    echo "VoorLeveranciersOpWhitebox folder uit testdata-whitebox verwijderd."
fi

if [ -d "$DIRECTORY/test/performance/webservice-runner/src/main/database" ]; then
    rm $DIRECTORY/test/performance/webservice-runner/src/main/database/brp-*.zip
    echo "Database(s) uit webservice-runner verwijderd."
fi

if [ -d "$DIRECTORY/distributie/src/main/database-dump" ]; then
    rm $DIRECTORY/distributie/src/main/database-dump/whitebox*.*
    echo "Database dump(s) uit distributie verwijderd."
fi

tar -czf $DIRECTORY.tar $DIRECTORY/

exit 0
