#!/bin/bash
TOMCAT="/opt/tomcat/"
PUBLIC_HTML="/var/www/html"
DEPLOYMENT_RAPPORT_DIR="$PUBLIC_HTML/brp-autodeploy"
VERSIE_RAPPORT_HTML="versies.html"
VERSIE_RAPPORT_TXT="versies.txt"
DEPLOYMENT_DATUM=`date +"%_d %B %Y %T"`


DEPLOYMENT_INFO="Laatste deployment gedaan op: $DEPLOYMENT_DATUM"


tomcat_status=$(/usr/bin/java -jar /opt/auto-deploy/share/cmdline-jmxclient-0.10.3.jar - localhost:1099 Catalina:type=Service stateName 2>&1)

if [[ $tomcat_status == *STARTED* ]]
then
    bijhouding_versie="`curl -s --connect-timeout 2 http://localhost:8080/bijhouding/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    bevraging_versie="`curl -s --connect-timeout 2 http://localhost:8080/bevraging/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    mutatielevering_versie="`curl -s --connect-timeout 2 http://localhost:8080/mutatielevering/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    verzending_versie="`curl -s --connect-timeout 2 http://localhost:8080/verzending/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    afnemerindicaties_versie="`curl -s --connect-timeout 2 http://localhost:8080/afnemerindicaties/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    synchronisatie_versie="`curl -s --connect-timeout 2 http://localhost:8080/synchronisatie/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    afnemer_versie="`curl -s --connect-timeout 2 http://localhost:8080/brp-kennisgeving-ontvanger/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    protocollering_versie="`curl -s --connect-timeout 2 http://localhost:8080/protocollering/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"
    routeringcentrale_versie="`curl -s --connect-timeout 2 http://localhost:8080/routering-centrale/versie.html | grep "<td>Versie:</td><td>" | awk -F "</*td>" '{print $4}'`"

    BIJHOUDING_VERSIE_TEKST="Bijhouding versie: $bijhouding_versie"
    BEVRAGING_VERSIE_TEKST="Bevraging versie: $bevraging_versie"
    MUTATIELEVERING_VERSIE_TEKST="Mutatielevering versie: $mutatielevering_versie"
    VERZENDING_VERSIE_TEKST="Verstrekking versie: $verzending_versie"
    SYNCHRONISATIE_VERSIE_TEKST="Synchronisatie versie: $synchronisatie_versie"
    AFNEMERINDICATIES_VERSIE_TEKST="Afnemerindicaties versie: $afnemerindicaties_versie"
    AFNEMERVOORBEELD_VERSIE_TEKST="Afnemer-voorbeeld versie: $afnemer_versie"
    PROTOCOLLERING_VERSIE_TEKST="Protocollering versie: $protocollering_versie"
    ROUTERINGCENTRALE_VERSIE_TEKST="Routeringcentrale versie: $routeringcentrale_versie"

    echo "<html><body><h1>Deployment versies</h1>" > "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    echo "$DEPLOYMENT_INFO" > "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
    echo "<p>$DEPLOYMENT_INFO</p>" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"

    if [ -n "$bijhouding_versie" ]; then
        echo -e "\n$BIJHOUDING_VERSIE_TEKST"
        echo -e "\n$BIJHOUDING_VERSIE_TEKST"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$BIJHOUDING_VERSIE_TEKST</p>" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$bevraging_versie" ]; then
        echo -e "\n$BEVRAGING_VERSIE_TEKST"
         echo -e "\n$BEVRAGING_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$BEVRAGING_VERSIE_TEKST</p>"   >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$mutatielevering_versie" ]; then
        echo -e "\n$MUTATIELEVERING_VERSIE_TEKST"
         echo -e "\n$MUTATIELEVERING_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$MUTATIELEVERING_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$verstrekking_versie" ]; then
        echo -e "\n$VERSTREKKING_VERSIE_TEKST"
        echo -e "\n$VERSTREKKING_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$VERSTREKKING_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$synchronisatie_versie" ]; then
        echo -e "\n$SYNCHRONISATIE_VERSIE_TEKST"
        echo -e "\n$SYNCHRONISATIE_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$SYNCHRONISATIE_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$afnemerindicaties_versie" ]; then
        echo -e "\n$AFNEMERINDICATIES_VERSIE_TEKST"
        echo -e "\n$AFNEMERINDICATIES_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$AFNEMERINDICATIES_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$afnemer_versie" ]; then
        echo -e "\n$AFNEMERVOORBEELD_VERSIE_TEKST"
        echo -e "\n$AFNEMERVOORBEELD_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$AFNEMERVOORBEELD_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$protocollering_versie" ]; then
        echo -e "\n$PROTOCOLLERING_VERSIE_TEKST"
        echo -e "\n$PROTOCOLLERING_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$PROTOCOLLERING_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi

    if [ -n "$routeringcentrale_versie" ]; then
        echo -e "\n$ROUTERINGCENTRALE_VERSIE_TEKST"
        echo -e "\n$ROUTERINGCENTRALE_VERSIE_TEKST" >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_TXT"
        echo "<p>$ROUTERINGCENTRALE_VERSIE_TEKST</p>"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
    fi
   echo "</body></html"  >> "$DEPLOYMENT_RAPPORT_DIR/$VERSIE_RAPPORT_HTML"
else
   echo -e "\nTomcat is niet gestart, geen nieuw deployment versie rapport weggeschreven!!\n"
fi
