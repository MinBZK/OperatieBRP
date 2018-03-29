Handleiding BRP end-to-end testen.

Deze handleiding beschijft hoe de BRP end-to-end testen opgezet zijn en hoe de testen gedraaid kunnen worden.

De BRP e2e testen zijn complexe integratietesten waarbij doorgaans twee of meerdere componenten een rol spelen. De focus van de ligt op het
integratie vlak en in mindere mate in het functionele vlak. De e2e-test test de interactie van een component met de integratielaag.
Dit zijn dan ook de testen die we in de API testen (zie brp-api-test) niet kunnen testen omdat daar stubs worden gebruikt voor de integratielaag.

Als testcontainer wordt gebruikt gemaakt van Docker. De testsoftware zelf managed de Docker containers (start / stop) en abstraheert dit
tot een geisoleerde test-omgeving waarbinnen een aantal componenten leven. De testen zijn uitgeschreven BDD stories (JBehave).
Steps worden vertaald naar aanroepen op de omgeving en resultaten worden gecontroleerd.

# Voorwaarden voor het draaien van de test

1) Docker / Maven is geinstalleerd (zie algemene omschijving)
2) De software van het project is gebouwd (mvn clean install -DskipTests uitgevoerd op de project-root)
2) De docker images zijn geinstalleerd (mvn clean install -Pdocker uitgevoerd op de distributie folder)
3) brp-docker-test/src/main/userproperties/brpe2e.properties is gekopieerd naar $USER_HOME (OS afhankelijk)
    De property DOCKER_IP is aangepast naar het IP waarde de docker interface op draait (zie output ipconfig of ifconfig)

    voorbeeld ifconfig:
    docker0   Link encap:Ethernet  HWaddr 02:42:04:3e:af:7c
              inet addr:172.17.0.1  Bcast:0.0.0.0  Mask:255.255.0.0
              UP BROADCAST MULTICAST  MTU:1500  Metric:1
              RX packets:0 errors:0 dropped:0 overruns:0 frame:0
              TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
              collisions:0 txqueuelen:0
              RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)



# Test uitvoer

De test-runners (JUnit) zijn te vinden in brp-docker-test/src/test/java/nl/bzk/brp/dockertest/jbehave/jenkins/ en eindigen met *EndToEndIT.
Resultaten zijn na de run te vinden in brp-docker-test/target/jbehave.

Vanuit de IDE zijn deze testen met rechtermuis>run te starten.

Vanuit de commandline worden te testen gestart met Maven:
mvn verify -Dit.test=SynchronisatieEndToEndIT

OF voor alle testen:
mvn verify -Dit.test=*EndToEndIT
mvn verify -Dit.test=EndToEndSuite


# Parallel
Het is mogelijk testen parallel uit de voeren om zodoende de test-feedback loop te verkorten. Dit stelt wel significant zwaardere eisen
aan de hardware van het systeem, met name geheugen.

Voor het starten van de 3 parallele tests voer het volgende commando uit:
mvn clean verify -Dit.test="**/jbehave/jenkins/*IT.java" -U -Dparallel=classes -DthreadCount=3 -DperCoreThreadCount=false