Meta:
@auteur             dihoe
@status             Klaar
@usecase            LV.1.MB
@regels             R1980hh
@sleutelwoorden     Maak BRP bericht

Narrative:

Een BRP bericht bevat geen lege containers.

Dat wil zeggen dat als een bericht een container zou bevatten die geen enkel object bevat, deze container niet moet worden aangemaakt
(dan wel moet worden weggefilterd uit het resultaat)


Scenario:   1. half-wees wordt geboren en verhuist, lege container bronnen
            Logische testgeval: R1980_02
            Verwacht resultaat:
            - lege container "bronnen/" wordt niet getoond in groep "actie"

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'

Given de personen 218720105 zijn verwijderd
Given de standaardpersoon UC_Kruimeltje met bsn 218720105 en anr 8293054738 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Kruimeltje = Persoon.metBsn(218720105)
nieuweGebeurtenissenVoor(UC_Kruimeltje) {
    verhuizing(partij: 'Gemeente Utrecht', aanvang: {vandaag(0)}) {
                naarGemeente 'Utrecht',
                    straat: 'Kerkstraat', nummer: 31, postcode: '2020AX', woonplaats: "Utrecht"
        }
}
slaOp(UC_Kruimeltje)

When voor persoon 218720105 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 0 groepen 'bronnen'

Scenario: 1.2 half-wees wordt geboren en verhuist, lege container bronnen
              Logische testgeval: R1980_02
              Verwacht resultaat:
              - lege container "bronnen/" wordt niet getoond in groep "actie"

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 27.2_R1980_synchroniseer_persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 0 groepen 'bronnen'