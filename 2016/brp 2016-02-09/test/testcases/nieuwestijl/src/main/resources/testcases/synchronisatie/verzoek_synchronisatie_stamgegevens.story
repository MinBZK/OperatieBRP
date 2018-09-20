Narrative:
De nieuwe Element tabel zal worden gebruikt om verwijzingen te communiceren naar afnemers. Bijvoorbeeld om aan te geven welke gegevens
in onderzoek staan (waaronder ook onderzoek naar ontbrekende gegevens, die dus zelf niet in het bericht staan). Mogelijk gaan afnemers
in de toekomst dit ook gebruiken om zelf te verwijzen (bijvoorbeeld voor terugmelding of scoping van bevraging). Het is dus noodzakelijk
dat de afnemers de nieuwe Elementtabel kennen zodat ze die verwijzingen kunnen interpreteren.

Echter de nieuwe Element tabel bevat ook veel inhoud die voor de afnemers niet relevant is. Dat betreft zowel kolommen (o.a. technische
verwijzingen naar de database) als rijen (gegevens die geen onderdeel van een levering kunnen zijn). Verwerkingsregel VR00111 beschrijft
de juiste inperking en sortering bij het leveren van de inhoud van de nieuwe Element tabel aan Afnemers. Daarnaast is in BRLV0024 de
opsomming van potentieel te leveren stamtabellen uitgebreid met de tabel Element.

Alleen een aantal willekeurige elementen worden gecontroleerd aangezien het wat ver gaat om 1500 elementen te gaan controleren.

R1331	BRLV0024	Er moet een bestaande soort stamgegeven worden opgegeven
R1332	VR00111	Synchronisatielevering elementtabel

Scenario: Succesvol uitvoeren geefSynchronisatiePersoon

Meta:
@status Klaar
@auteur rohar
@regels BRLV0024, R1331, VR00111, R1332
@sleutelwoorden         synchronisatie

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand verzoek_synchronisatie_stamgegevens.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht voor xpath /soap:Body/brp:lvg_synGeefSynchronisatieStamgegeven_R/brp:stamgegevens/brp:elementTabel/brp:element[brp:naam='Persoon']
de platgeslagen waarde <brp:element brp:objecttype="Element"><brp:naam>Persoon</brp:naam><brp:soortNaam>Objecttype</brp:soortNaam><brp:datumAanvangGeldigheid>2012-01-01</brp:datumAanvangGeldigheid></brp:element>
Then heeft het antwoordbericht voor xpath /soap:Body/brp:lvg_synGeefSynchronisatieStamgegeven_R/brp:stamgegevens/brp:elementTabel/brp:element[brp:naam='Persoon.AfgeleidAdministratief']
de platgeslagen waarde <brp:element brp:objecttype="Element"><brp:naam>Persoon.AfgeleidAdministratief</brp:naam><brp:soortNaam>Groep</brp:soortNaam><brp:datumAanvangGeldigheid>2012-01-01</brp:datumAanvangGeldigheid></brp:element>
Then heeft het antwoordbericht voor xpath /soap:Body/brp:lvg_synGeefSynchronisatieStamgegeven_R/brp:stamgegevens/brp:elementTabel/brp:element[brp:naam='Persoon.AfgeleidAdministratief.TijdstipRegistratie']
de platgeslagen waarde <brp:element brp:objecttype="Element"><brp:naam>Persoon.AfgeleidAdministratief.TijdstipRegistratie</brp:naam><brp:soortNaam>Attribuut</brp:soortNaam><brp:datumAanvangGeldigheid>2012-01-01</brp:datumAanvangGeldigheid></brp:element>
