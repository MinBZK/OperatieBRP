- Build de migr-init projecten (mvn clean install)
- Zet de zip op de plek waar je vulling van de LO3 queue wil draaien (BRP database en de queue naar sync voor LO3 vulling moet beschikbaar/bereikbaar zijn).
- Pak de zip uit.
- Zorg dat de volgende queues bestaan binnen HornetQ (Waar de sync-runtime ook naar kijkt):

   <queue name="init.vulling.naarlo3">
      <entry name="queues/init.vulling.naarlo3"/>
   </queue>

   <queue name="init.vulling.naarlo3.response">
      <entry name="queues/init.vulling.naarlo3.response"/>
   </queue>

- Maak een config.properties aan op basis van example-config.properties.

- Run het sh script om de initiele vulling te doen (./runLo3QueueVulling.sh).
