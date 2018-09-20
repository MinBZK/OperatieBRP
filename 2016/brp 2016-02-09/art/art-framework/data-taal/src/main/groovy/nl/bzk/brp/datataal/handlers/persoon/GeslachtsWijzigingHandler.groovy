package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel

/**
 *
 */
class GeslachtsWijzigingHandler extends AbstractGebeurtenisHandler {

    private final ActieModel wijzigingActie

    /**
     * Constructor.
     *
     * @param attr
     * @param b persoonHisVolledig builder
     */
    GeslachtsWijzigingHandler(final GebeurtenisAttributen attr, final def Object b) {
        super(attr, b)
        wijzigingActie = maakActie(SoortActie.REGISTRATIE_GESLACHTSAANDUIDING)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, wijzigingActie, 1))
    }

    def geslacht(String geslacht) {
        assert Geslachtsaanduiding.values().collect { it.name() }.contains(geslacht)

        builder.nieuwGeslachtsaanduidingRecord(wijzigingActie).geslachtsaanduiding(Geslachtsaanduiding.valueOf(geslacht)).eindeRecord()
    }
}
