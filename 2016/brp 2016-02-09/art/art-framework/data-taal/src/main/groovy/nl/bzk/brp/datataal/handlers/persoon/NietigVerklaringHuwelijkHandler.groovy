package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledigBasis
import nl.bzk.brp.model.operationeel.kern.ActieModel

/**
 * Handler voor het nietig verklaren van het huwelijk
 */
class NietigVerklaringHuwelijkHandler extends AbstractGebeurtenisHandler {
    protected ActieModel actie

    /**
     * Constructor.
     *
     * @param m
     * @param builder
     */
    NietigVerklaringHuwelijkHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)

        actie = maakActie(SoortActie.VERVAL_HUWELIJK_GEREGISTREERD_PARTNERSCHAP)
    }

    @Override
    void startGebeurtenis() {


        if (builder.hisVolledigImpl.huwelijkGeregistreerdPartnerschappen.isEmpty()) {
            throw new IllegalArgumentException('Nietig verklaren huwelijk is niet mogelijk als persoon geen huwelijk heeft')
        }
        def huwelijk = builder.hisVolledigImpl.huwelijkGeregistreerdPartnerschappen.find {
            it instanceof HuwelijkHisVolledigBasis
        }
        if(!huwelijk) {
            throw new IllegalArgumentException('Nietig verklaren huwelijk is niet mogelijk als persoon geen huwelijk heeft')
        }

        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl,
            actie, 1))

        //identiteit/standaard van huwelijk groep vervalt
        huwelijk.relatieHistorie.verval(actie, actie.tijdstipRegistratie)

        //identiteit van partnerbetrokkenheden in relatie vervallen ook
        huwelijk.betrokkenheden.each {
            it.betrokkenheidHistorie.verval(actie, actie.tijdstipRegistratie)
        }
    }

    @Override
    void eindeGebeurtenis() {



    }
}
