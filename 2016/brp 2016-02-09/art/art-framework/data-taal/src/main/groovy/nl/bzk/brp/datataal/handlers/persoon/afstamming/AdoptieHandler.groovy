package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.handlers.persoon.WijzigNamenAbility
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 *
 */
class AdoptieHandler extends AbstractAfstammingHandler implements WijzigNamenAbility, WijzigOudersAbility {
    final ActieModel ouderActie

    AdoptieHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b) {
        super(attr, b)

        this.ouderActie = maakActie(SoortActie.REGISTRATIE_OUDER)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.
            voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, this.ouderActie, 1))
    }
}
