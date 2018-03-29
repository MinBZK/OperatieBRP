Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1339
@sleutelwoorden     Controleer Persoonsselectie


Narrative:
Dienst alleen mogelijk indien geen verstrekkingsbeperking voor de verzoekende partij aanwezig is

Scenario:   2. GeefDetailsPersoon service voor persoon zonder verstrekkingsbeperking op partij Gemeente Utrecht
            LT: R1339_LT06
            Partij.indverstrbeperkingmogelijk = False
            Verwacht resultaat: Geslaagd zonder melding

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Verstrekkingsbeperking
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoonVerstrekkingsBeperking'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

And is het laatste bericht geprotocolleerd met de gegevens:
|veld                       |waarde
|toeganglevsautorisatie     |1
|dienst                     |1
|tsklaarzettenlev           |.*
|dataanvmaterieleperioderes |null
|dateindematerieleperioderes|morgen
|tsaanvformeleperioderes    |null
|tseindeformeleperioderes   |.*
|admhnd                     |null
|srtsynchronisatie          |null

And zijn de laatst geprotocolleerde personen:
|pers             |tslaatstewijzpers
|1                |.*

Scenario:   3. GeefDetailsPersoon service voor persoon met volledige verstrekkingsbeperking op partij KUC033-PartijVerstrekkingsbeperking
            LT: R1339_LT05, R1342_LT01
            Partij.indverstrbeperkingmogelijk = True
            Verwacht resultaat:
            Foutmelding: Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Verstrekkingsbeperking
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoonVerstrekkingsBeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|434587977

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                          |
| R1339 | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |

And is het laatste bericht niet geprotocolleerd

Scenario:   4. GeefDetailsPersoon service voor persoon zonder volledige verstrekkingsbeperking op partij Gemeente Utrecht
            LT: R1339_LT06, R1342_LT03
            Partij.indverstrbeperkingmogelijk = False
            Verwacht resultaat: Geslaagd zonder meldingen

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Verstrekkingsbeperking
Given persoonsbeelden uit specials:specials/Libby-gebdat-deels-onbekend1_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoonVerstrekkingsBeperking'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|422531881

Then heeft het antwoordbericht verwerking Geslaagd

And is het laatste bericht geprotocolleerd met de gegevens:
|veld                       |waarde
|toeganglevsautorisatie     |1
|dienst                     |1
|tsklaarzettenlev           |.*
|dataanvmaterieleperioderes |null
|dateindematerieleperioderes|morgen
|tsaanvformeleperioderes    |null
|tseindeformeleperioderes   |.*
|admhnd                     |null
|srtsynchronisatie          |null

And zijn de laatst geprotocolleerde personen:
|pers             |tslaatstewijzpers
|1                |.*
