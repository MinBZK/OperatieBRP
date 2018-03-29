#!/usr/bin/env bash
#
# BRP DEPLOY TOOL script
# Script om Tomcat in te richten voor het gebruik van BRP applicaties. Vanuit Jenkins zijn configuratie bestanden,
# bibliotheken, keystores, certificaten, property bestanden en applicatie bestanden in een directory op de server gezet.
# Vanuit deze directory (zie de variabele SOURCE_DIR), wordt de op de server aanwezige Tomcat installatie ingericht.
# Daarnaast kan dit script Tomcat aansturen als convenience methode.
#
# Versie v2.0.4
#
# Syntax:
# ./brp-deploytool.sh [optie] [argument]
#
# Opties:
# -h Toont de help pagina
# -t [argument] om Tomcat instructies te geven om te starten, stoppen enz.
# -d Doet een (re)deploy van de door Jenkins aangeleverde WAR bestanden
#
# Voorbeeld voor standaard redeploy:
# ./brp-deploytool.sh -d

# Stel de script naam vast
declare -r SCRIPT_NAME=$(basename "$BASH_SOURCE" .sh)

# Definieer een aantal variableen die gebruikt worden om de Tomcat locatie, bibliotheek locatie,
# configuratie locatie en website locatie vast te leggen.
TOMCAT="/opt/tomcat"
TOMCAT_LIB="/opt/tomcat/lib"
TOMCAT_CONFIG="$TOMCAT_LIB"
TOMCAT_BIN="/usr/bin/sudo /etc/init.d/tomcat7"
TOMCAT_STOP="$TOMCAT_BIN stop"
TOMCAT_START="$TOMCAT_BIN start"
PUBLIC_HTML="/var/www/html"

# Locatie waar door Jenkins de gebruikte bestanden worden klaargezet
SOURCE_DIR="/tmp/jenkins"

#maximale wachttijd voordat Tomcat gekilled wordt bij het stoppen
MAXWAIT_BEFORE_KILL=20
#maximale wachttijd voor het opstarten van Tomcat
MAXWAIT_BEFORE_START=500

## Beeindig het script, default met exit code 1.
einde() {
    echo -e "$1" >&2
    logger "$1"
    exit ${2-1}
}

# help / gebruik melding
declare -r HELP_MSG="\nGebruik: $SCRIPT_NAME [OPTIE] [ARGUMENT]
  -h             toon deze informatie

  -t [ARGUMENT]  Voer een tomcat instructie uit.
                 Opties voor het argument zijn:
                    status  - geeft de status van tomcat
                    start   - start tomcat
                    stop    - stopt tomcat
                    restart - herstart
                    pid     - geef het proces id van de lopende tomcat

  -d             (re)deploy (stop tomcat, kopieer applicaties en configuratie en start tomcat)
"

## Laat de gebruikwijze zien en beeindig met status 2
gebruikwijze() {
    declare status=2
    if [[ "$1" =~ ^[0-9]+$ ]]; then
        status=$1
        shift
    fi
    einde "${1}$HELP_MSG" $status
}

# Zoek het process id van Tomcat op in de lopende processen.
tomcat_pid() {
    echo `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
}

# Gebruik JMX om na te gaan in welke opstart staat Tomcat is.
tomcat_startup_status() {
    echo `/usr/bin/java -jar /opt/auto-deploy/share/cmdline-jmxclient-0.10.3.jar - localhost:1099 Catalina:type=Service stateName 2>&1`
}

# Ruim Tomcat applicaties en work (cache) directory op.
tomcat_clean() {
   # Verwijder de uitgepakte directories en leeg de work directory
   echo -e "[INFO] verwijder oude deployment directories en work directory (OOK WAR FILES WORDEN VERWIJDERD!!!)"
   rm -rf $TOMCAT/webapps/*
   rm -f $TOMCAT/lib/*.properties
   rm -f $TOMCAT/lib/*.xml
   rm -f $TOMCAT/logs/*.log
   rm -f $TOMCAT/logs/*.txt
   rm -f $TOMCAT/logs/*.gz
   rm -f $TOMCAT/logs/*.out*
   rm -rf $TOMCAT/work/Catalina/localhost/*
   rm -rf /tmp/activemq-data/*
}

# Functie om Tomcat te stoppen. Wacht een tijdje en schiet dan Tomcat de lucht uit.
tomcat_stop() {
    #Bepaal de process id van Tomcat
    pid=$(tomcat_pid)

    # Stop tomcat
    echo "[INFO] Tomcat (pid=$pid) wordt gestopt..."
    $TOMCAT_STOP

    # Wacht tot Tomcat volledig is gestopt
    count=0
    if [ -n "$pid" ]
    then
        until [ `ps -p $pid | grep -c $pid` = '0' ] || [ $count -gt $MAXWAIT_BEFORE_KILL ]
        do
         let wachttijd=$MAXWAIT_BEFORE_KILL-$count
         echo -e "[DEBUG] Wachten totdat Tomcat gestopt is (nog $wachttijd seconden)"
         sleep 1
         let count=$count+1
        done

        if [[ "$count"  -gt "$MAXWAIT_BEFORE_KILL" ]]
        then
           logger "[WARN] Stoppen van Tomcat duurt te lang, voer nu een KILL -9 uit"
           echo -n -e "[WARN] Te lang wachten... Tomcat wordt nu bruut om zeep geholpen!"
           kill -9 $pid
        fi
    fi
    echo "[INFO] Tomcat is gestopt."
}

# Functie om Tomcat te starten en te wachten totdat die is opgestart. Gebruikt JMX om opstart status na te gaan.
tomcat_start() {
    # Opstarten van Tomcat
    echo "[INFO] Tomcat wordt gestart..."
    $TOMCAT_START
    sleep 2
    # Wacht tot Tomcat volledig is opgestart
    tomcat_opgestart=false
    wachttijd=5

    until [ $tomcat_opgestart == true ] || [ $wachttijd -gt $MAXWAIT_BEFORE_START ]
    do
      tomcat_status=$(tomcat_startup_status)

      if [[ $tomcat_status == *STARTING* ]]
      then
        echo "[DEBUG] Tomcat is aan het opstarten..... Wachttijd nu $wachttijd seconden"
      elif [[ $tomcat_status == *STARTED* ]]
      then
        tomcat_opgestart=true
        echo "[INFO] Tomcat volledig opgestart! (Opstarttijd was $wachttijd seconden)"
      else
        echo "[ERROR] Onverwacht gedrag gevonden."
        echo "[ERROR] Status: $tomcat_status."
      fi
      sleep 5
      let wachttijd=$wachttijd+5
    done
    tail -n 10 $TOMCAT/logs/catalina.out

}

# Deploy de applicaties naar Tomcat. Kopieer de applicaties, de bibliotheken, de configuratie bestanden en site.
tomcat_deploy() {

    # Kopieer nieuwe war's
    echo "[INFO] applicaties worden vervangen..."
    cp -rvf $SOURCE_DIR/*.war $TOMCAT/webapps

    # Als de copy slag niet gelukt is dan stoppen met de deployment
    if [ $? -ne 0 ]; then
        logger "[ERROR] BRP applicatie auto deployment is mislukt!!!!"
        einde "[ERROR] Vervangen van BRP applicaties is mislukt" 1
        # END Script eindigd hier
    fi

    echo "[INFO] Kopieer configuratie bestanden"
    if [ -d "$SOURCE_DIR/configuratie" ]; then
        cp -rvf $SOURCE_DIR/configuratie/*.* $TOMCAT_CONFIG
    else
        echo "[ERROR] Geen configuratie directory gevonden."
    fi

    if [ -d "$SOURCE_DIR/keystores" ]; then
        cp -rvf $SOURCE_DIR/keystores/*.jks $TOMCAT_CONFIG
    else
        echo "[ERROR] Geen keystores directory gevonden."
    fi

    echo "[INFO] Kopieer deployment versie informatie"
    if [ -d "$SOURCE_DIR/site" ]; then
        cp -rvf $SOURCE_DIR/site/* $PUBLIC_HTML/brp-autodeploy
    else
        echo "[WARN] Geen site directory gevonden, dus geen deployment informatie gekopieerd."
    fi

    echo "[INFO] Kopieer postgres driver"
    if [ -d "$SOURCE_DIR/driver" ]; then
        cp -vf $SOURCE_DIR/driver/* $TOMCAT/lib
    else
        echo "[WARN] Geen driver directory gevonden, dus geen drivers gekopieerd."
    fi
}

# Functie om Tomcat commando's te geven.
tomcat_commando() {
        pid=$(tomcat_pid)
        case $1 in
          start)
            if [ -z $pid ]; then
                tomcat_start
            else
                echo "Er is al een Tomcat gestart (pid: $pid)"
            fi
          ;;
          stop)
            if [ ! -z $pid ]; then
                tomcat_stop
            else
                echo "Er is geen Tomcat proces gevonden om te stoppen"
            fi
          ;;
          restart)
            echo -e "[INFO] Alleen herstart van tomcat, geen nieuwe versies worden gedeployed en geen redeployment/clean!"
            tomcat_stop
            tomcat_start
          ;;
          status)

            if [ -z $pid ] ; then
                tomcat_startup_status
            else
                echo "Tomcat is gestopt"
            fi
          ;;
          pid)
            echo -e "Tomcat processid: $pid"
          ;;

        esac
        exit 0;
}

# De functie die de alle taken voor de deployment naar Tomcat uitvoert.
deploy() {
    logger "Start deployment van BRP applicaties via Jenkins"
    tomcat_stop
    tomcat_clean
    tomcat_deploy
    tomcat_start
}


# Controleer dat er wel een optie is meegegeven, anders weten we niet wat we moeten doen.
(( $# )) || gebruikwijze "FOUT! Geen optie meegegeven." 1

# Controleer of omgeving bestaat
if [ ! -d $TOMCAT ]; then
   logger "[ERROR] Opgegeven omgeving ($TOMCAT) is niet aanwezig, deployment script kan niet worden gebruikt."
   einde "[ERROR] Kan Tomcat niet vinden ($TOMCAT), deployment script wordt niet uitgevoerd." 1
   # EIND Script stopt hier, zonder tomcat op de juiste locatie heeft het verder geen zin :(
fi

if [ ! -d $SOURCE_DIR ]; then
   logger "[ERROR] Geconfigureerde bron directory ($SOURCE_DIR) is niet aanwezig, deployment script kan niet worden gebruikt."
   einde "[ERROR] Geconfigureerde bron directory ($SOURCE_DIR) is niet aanwezig, deployment script kan niet worden gebruikt." 3

fi

while getopts "ht:d" opt; do

    case $opt in
        h)
            gebruikwijze 0
            ;;
        t)
            OPDRACHT=$OPTARG
            [[ ! $OPDRACHT =~ start|stop|status|restart|pid ]] && {
              einde "[ERROR] Geen valide opdracht voor Tomcat meegegeven" 4
            }

            tomcat_commando $OPDRACHT
            ;;
        d)
            echo "[INFO] start deployment van $SOURCE_DIR naar $TOMCAT"
            deploy
            echo "[INFO] Deployment is beeindigd."
            logger "[INFO] BRP applicaties gedeployed"

            echo -e "\n\n====================================================="
            echo -e "Versie deployment rapport\n"
            echo -e `$SOURCE_DIR/bin/versie-rapport.sh`
            echo -e "\n=====================================================\n"
            ;;
        \?)
            gebruikwijze "Onbekend argument/optie: $opt $OPTARG \n"
            ;;
    esac


done

shift $(($OPTIND - 1))


exit $?
