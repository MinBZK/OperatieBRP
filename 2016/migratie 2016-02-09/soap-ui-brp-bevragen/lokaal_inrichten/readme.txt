In unzip de Certificaten.zip naar /opt/ca. Daar staan de certificaten die je nodig hebt voor soap-ui en de tomcat.

De apache-tomcat-7.0.47(brp).zip bevat de configuratie van BRP zodat je alleen nog de volgende wars moet bouwen en deployen:
ah-notificatie-bezemwagen.war
ah-notificatie-verwerker.war
bevraging.war
brp.war
kennisgeving-verstuurder.war
routering-centrale.war

De keystores binnen tomcat worden gebruikt uit de /opt/ca die boven uitgepakt is.

Maak een brp database en voer de volgende query's uit om de partij geautoriseerd te krijgen:
Voeg de partij voor bijhouding:
- DB SUBJECT: [CN=reinder, L=Den Haag, ST=Zuid-Holland, C=NL, EMAILADDRESS=support@modernodam.nl, OU=Modernodam, O=Programma mGBA]
- DB SERIAL: [17987823793400428271]
- DB SIGNATURE: [85c5c382b39a362f3946d18187e00d02d4e2e56fc73a5abe755ee6006a186730a1ff762cdcc317d0b676f1dc4c3ec68af19684cec648b752db25b8e44e7ec245682a352cd0ea8413b7fbc23434113ba5d9ca471f839e6d09acd3da97faaf098594f2a7d5fd4aceb3bf9342ba6ead7e11a87578512825352910678e352dd393c1b74469f7e8e66652835d619f991fb10a52acc9039859b5236a2e2485dfbdc3a7ec233c15ca2f9a06189fb5e3aefed97e99fdc8d148f26bcd9e837f653619c452aac652061fbff58ad02f77b6c3948f1c9729daeedad52c82cb143eb505d4a00fa2b2d33e81bc2730b76461dc8e863f3fb65504df7d5c419f65c927515a41e378490acb649a1789b5e58d23910824d2d513548cebd987b3dbc5571f57eef3819a399ca4ad587d4ce9c206ce9965301ee02399c244ab59b251826955eedb5416dff156162406543fd9b5d1a86eba9308bae31977a9ed86bb42d1b39ca84d4604291bfbe846026cf5774a1efb94b4263cbcc61d112522250911d5f29dc47dfff8e77ea7524071ea11fee5f802d30e614cca4b4fd3493877a7012b527898754c6c56d28d646f8066aef352daced98e029e5374078ae176cfef418576781f60f0cda8c7f707fe8b54383dde85a76bab41205e694a3f99a507bb27c5b4a202b5b45cc48ac7f5edd517f1844daf3518e711ea7403a9e4b8ad0398e59b11e36fffa4e5da]

INSERT INTO autaut.certificaat (id, subject, serial, signature, partij) VALUES ((select coalesce(max(id), 0) + 1 from autaut.certificaat), 'CN=reinder, L=Den Haag, ST=Zuid-Holland, C=NL, EMAILADDRESS=support@modernodam.nl, OU=Modernodam, O=Programma mGBA', 17987823793400428271, '85c5c382b39a362f3946d18187e00d02d4e2e56fc73a5abe755ee6006a186730a1ff762cdcc317d0b676f1dc4c3ec68af19684cec648b752db25b8e44e7ec245682a352cd0ea8413b7fbc23434113ba5d9ca471f839e6d09acd3da97faaf098594f2a7d5fd4aceb3bf9342ba6ead7e11a87578512825352910678e352dd393c1b74469f7e8e66652835d619f991fb10a52acc9039859b5236a2e2485dfbdc3a7ec233c15ca2f9a06189fb5e3aefed97e99fdc8d148f26bcd9e837f653619c452aac652061fbff58ad02f77b6c3948f1c9729daeedad52c82cb143eb505d4a00fa2b2d33e81bc2730b76461dc8e863f3fb65504df7d5c419f65c927515a41e378490acb649a1789b5e58d23910824d2d513548cebd987b3dbc5571f57eef3819a399ca4ad587d4ce9c206ce9965301ee02399c244ab59b251826955eedb5416dff156162406543fd9b5d1a86eba9308bae31977a9ed86bb42d1b39ca84d4604291bfbe846026cf5774a1efb94b4263cbcc61d112522250911d5f29dc47dfff8e77ea7524071ea11fee5f802d30e614cca4b4fd3493877a7012b527898754c6c56d28d646f8066aef352daced98e029e5374078ae176cfef418576781f60f0cda8c7f707fe8b54383dde85a76bab41205e694a3f99a507bb27c5b4a202b5b45cc48ac7f5edd517f1844daf3518e711ea7403a9e4b8ad0398e59b11e36fffa4e5da', 20);

INSERT INTO autaut.authenticatiemiddel (id, partij, rol, certificaat) VALUES ((select coalesce(max(id), 0) + 1 from autaut.authenticatiemiddel), 20, 1, (select max(id) from autaut.certificaat));


/*********************************************************************************************************/
Om de database in de juiste stand te krijgen voor het leveren van LO3 berichten moet je voorlopig het volgende doen:

//Zet alle afleverwijze op lo3 netwerk:
insert into autaut.afleverwijze (id, toegangabonnement, kanaal, uri, datingang, dateinde)
            select
                        1000 + ta.id,
                        ta.id,    
                        3,
                        '/' || ta.partij,
                        20130101,
                        null
            from autaut.toegangabonnement ta
            order by ta.id

Voer in het project: brp-db/src/main/resources/madelief1
De sql 41-autaut-abonnementen.sql en 43-autaut-hisabonnementen.sql uit.

