- Build de migr-init projecten (mvn clean install)
- Zet de zip op de plek waar je initiele vulling wil draaien (GBAV database en de queue naar sync voor initiele vulling moet beschikbaar/bereikbaar zijn).
- Pak de zip uit.
- Zorg dat de volgende queues bestaan binnen HornetQ (Waar de sync-runtime ook naar kijkt):

   <queue name="init.vulling.naarbrp">
      <entry name="queues/init.vulling.naarbrp"/>
   </queue>

   <queue name="init.vulling.naarbrp.response">
      <entry name="queues/init.vulling.naarbrp.response"/>
   </queue>

- Maak een config.properties aan op basis van example-config.properties.

- Run het sh script alleen om de initiele vulling tabel binnen de GBAV database aan te maken (./createInitVulling.sh).

- Run het sh script om de initiele vulling te doen (./runInitVulling.sh).

- Om de tabel te vullen met PLen vanuit excel bestanden run ./createExcelVulling.sh. Pas zo nodig de excel directory aan (absoluut pad)
  Hierna kan de initiele vulling als normaal worden gestart (./runInitVulling.sh) 
