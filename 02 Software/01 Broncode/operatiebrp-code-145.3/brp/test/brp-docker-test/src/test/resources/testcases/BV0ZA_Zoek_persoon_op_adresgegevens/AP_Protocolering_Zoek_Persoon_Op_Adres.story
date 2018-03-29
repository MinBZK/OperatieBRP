Meta:
@status             Klaar
@usecase            BV.0.ZA
@regels             R1613, R1614, R1615, R1617, R1618, R1619, R1620, R2236
@sleutelwoorden     Zoek Persoon op adresgegevens

Narrative:
Testen op protocoleringsregels

Scenario: 1.    Jan wordt gezocht op adresgegevens
            LT: R1613_LT04, R1617_LT08, R1618_LT03, R1619_LT06, R1620_LT03, R2236_LT01
                Verwacht resultaat:
                - Correct geprotocoleerd

!-- R1613_LT04            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
!-- R1613_LT04            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht)
!-- R1613_LT04            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
!-- R1613_LT04            Leveringsaantekening.Datum materieel selectie = 'leeg'
!-- R1617_LT08            Leveringsaantekening.Datum aanvang materiële periode resultaat = Leeg
!-- R1618_LT03            Leveringsaantekening.Datum einde materiële periode resultaat = Leeg
!-- R1619_LT06           Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg'
!-- R1620_LT03            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd systeem van ophalen gegevens
!-- R1613_LT04            Leveringsaantekening.Administratieve handeling = 'leeg'
!-- R1613_LT04            Leveringsaantekening.Soort synchronisatie = 'leeg'
!-- R1613_LT04            Leveringsaantekening.Scope patroon = 'leeg'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/9._Zoek_Persoon_op_Adres_Story_8.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1613_LT04
!-- Then in prot heeft select * from prot.levsaantek where id=(select levsaantek from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801)) de volgende gegevens:
Then is er voor leveringsautorisatie ZoekPersoonAdres en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde    |
| bsn                         | 606417801 |
| dataanvmaterieleperioderes  | NULL      |
| dateindematerieleperioderes | NULL      |
| tsaanvformeleperioderes     | NULL      |
| admhnd                      | NULL      |
| srtsynchronisatie           | NULL      |
| scopepatroon                | NULL      |

Then is er voor leveringsautorisatie ZoekPersoonAdres en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                        |
| bsn                         | 606417801                     |
| soortDienst                 | Zoek persoon op adresgegevens |
| dataanvmaterieleperioderes  | NULL                          |
| dateindematerieleperioderes | NULL                          |
| tsaanvformeleperioderes     | NULL                          |
| tseindeformeleperioderes    | NU                            |
