Meta:
@status         Onderhanden
@sleutelwoorden diana55
@jiraIssue      TEAMBRP-4514
@regels         BRBY0002,R1281

Narrative: De vader van Oliver was echtgenoot van de moeder van Oliver,
           echter dit Huwelijk of Geregistreerd partnerschap is vóór de geboortedatum ontbonden
           vanwege het overlijden (van de vader) terwijl Oliver is geboren binnen 306 dagen
           na de ontbinding van het Huwelijk
           (met andere woorden: datum geboorte - 306 dagen <= datum einde relatie < datum geboorte).

Scenario: Succesvol uitvoeren bepaalKandidaatVader

Given de personen 461473161,314856377,310359065 zijn verwijderd
Given de standaardpersoon Oliver met bsn 310359065 en anr 7260876562 zonder extra gebeurtenissen

Given administratieve handeling van type overlijdenInNederland , met de acties registratieOverlijden
And testdata uit bestand bepaal_kandidaat_vader_01.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek van bericht bhg_bvgBepaalKandidaatVader
And testdata uit bestand bepaal_kandidaat_vader_02.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep 		            | attribuut             | verwachteWaardes              |
| identificatienummers      | burgerservicenummer   | 461473161                     |
