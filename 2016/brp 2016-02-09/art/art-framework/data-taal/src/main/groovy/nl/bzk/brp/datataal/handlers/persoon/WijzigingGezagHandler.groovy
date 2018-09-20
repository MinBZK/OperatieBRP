package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider

import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder

/**
 * Handler voor het behandelen van een gebeurtenis "derdeHeeftGezag".
 */
class WijzigingGezagHandler extends AbstractGebeurtenisHandler {
    private final ActieModel actieModel = maakActie(SoortActie.REGISTRATIE_GEZAG)

    WijzigingGezagHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)
    }

    boolean ja = true
    boolean nee = false

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, actieModel, 1))
    }

    def derdeHeeftGezag(Boolean indicatie) {
        if (indicatie) {
            PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder indicatieBuilder = new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder(builder.hisVolledigImpl)
            indicatieBuilder.nieuwStandaardRecord(actieModel).waarde(Ja.J).eindeRecord()
            builder.voegPersoonIndicatieDerdeHeeftGezagToe(indicatieBuilder.build())
        } else {
            if (builder.hisVolledigImpl.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.actueleRecord) {
                builder.hisVolledigImpl.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.beeindig(actieModel, actieModel)
            }
        }
    }

    def ouderlijkGezag(Integer bsn, Boolean indicatie) {
        def ouder = builder.hisVolledigImpl.kindBetrokkenheid.relatie.ouderBetrokkenheden.find { bsn == it.persoon?.persoonIdentificatienummersHistorie?.actueleRecord?.burgerservicenummer?.waarde }

        pasOuderlijkGezagAan(ouder, indicatie)
    }

    def ouderlijkGezag(PersoonHisVolledigImpl persoon, Boolean indicatie) {
        def ouder = builder.hisVolledigImpl.kindBetrokkenheid.relatie.ouderBetrokkenheden.find { persoon.ID == it.persoon?.ID }

        pasOuderlijkGezagAan(ouder, indicatie)
    }

    private void pasOuderlijkGezagAan(OuderHisVolledigImpl ouder, Boolean indicatie) {
        if (ouder) {
            def ouderlijkgezag = new HisOuderOuderlijkGezagModel(ouder, new OuderOuderlijkGezagGroepModel(new JaNeeAttribuut(indicatie)), actieModel, actieModel)
            ouder.ouderOuderlijkGezagHistorie.voegToe(ouderlijkgezag)

            // TODO
            // ouder.persoon.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(ouder.persoon, actieModel, 2))
        }
    }
}
