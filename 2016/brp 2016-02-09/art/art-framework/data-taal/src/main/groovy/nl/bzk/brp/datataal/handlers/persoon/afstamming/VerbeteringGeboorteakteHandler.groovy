package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel

/**
 *
 */
class VerbeteringGeboorteakteHandler extends AbstractAfstammingHandler {
    private final ActieModel wijzigingActie

    /**
     * Constructor.
     *
     * @param attr
     * @param b persoonHisVolledig builder
     */
    VerbeteringGeboorteakteHandler(final GebeurtenisAttributen attr, final def Object b) {
        super(attr, b)

        wijzigingActie = maakActie(SoortActie.REGISTRATIE_GEBOORTE)
    }

    @Override
    ActieModel getOuderActie() {
        return wijzigingActie
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.
            voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, wijzigingActie, 1))
    }

    def op(def datum) {
        if (!datum) {
            throw new IllegalArgumentException("datum is verplicht")
        }

        [te: { String opgegevenPlaats = null ->
            [gemeente: { def opgegevenGemeente = null ->
                def actueleRecord = builder.hisVolledigImpl.persoonGeboorteHistorie.actueleRecord

                def dat = bepaalDatum(datum)

                def plaats
                if (!opgegevenPlaats) {
                    plaats = actueleRecord.woonplaatsnaamGeboorte.waarde
                } else {
                    plaats = opgegevenPlaats
                }

                def gemeente
                if (!opgegevenGemeente) {
                    gemeente = actueleRecord.gemeenteGeboorte.waarde
                } else {
                    gemeente = bepaalGemeente(opgegevenGemeente)
                }

                builder.nieuwGeboorteRecord(wijzigingActie)
                    .datumGeboorte(dat)
                    .woonplaatsnaamGeboorte(plaats)
                    .gemeenteGeboorte(gemeente)
                    .landGebiedGeboorte(referentieData.nederland)
                    .eindeRecord()
            }]
        }]
    }
}
