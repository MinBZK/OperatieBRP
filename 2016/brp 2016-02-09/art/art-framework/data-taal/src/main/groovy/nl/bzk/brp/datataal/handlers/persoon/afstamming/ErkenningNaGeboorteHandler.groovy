package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.handlers.persoon.WijzigNamenAbility
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder

/**
 * Handler voor het behandelen van een gebeurtenis "actualiseerOuderschap".
 */
class ErkenningNaGeboorteHandler extends AbstractAfstammingHandler implements WijzigNamenAbility {
    final ActieModel ouderActie

    ErkenningNaGeboorteHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)

        admhnd.soort.waarde = SoortAdministratieveHandeling.ERKENNING_NA_GEBOORTE
        ouderActie = maakActie(SoortActie.REGISTRATIE_OUDER)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, ouderActie, 1))
    }

    def door(PersoonHisVolledigImpl vader) {
        PersoonHisVolledigImpl persoon = builder.hisVolledigImpl

        def familie = persoon.kindBetrokkenheid.relatie
        def ouderBetr = familie.ouderBetrokkenheden.find { it.ouderOuderschapHistorie.actueleRecord?.indicatieOuderUitWieKindIsGeboren == null && it.persoon?.ID == vader.ID }

        if (!ouderBetr && familie.ouderBetrokkenheden.size() >= 2) {
            // erkenner niet als betrokkene bekend *en* er zijn 2 andere ouder betrokkenheden
            throw new IllegalStateException('Er zijn reeds twee ouders vastgelegd voor deze persoon')
        } else if (ouderBetr) {
            ouderBetr.ouderOuderschapHistorie.beeindig(ouderActie, ouderActie)
            ouderBetr.ouderOuderlijkGezagHistorie.beeindig(ouderActie, ouderActie)
            ouderBetr.betrokkenheidHistorie.verval(ouderActie, ouderActie.tijdstipRegistratie)
        }

        if (vader?.isIngeschrevene()) {
            vader.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(vader, ouderActie, 2))
        }

        familie << new OuderHisVolledigImplBuilder(familie, vader)
            .metVerantwoording(ouderActie)
            .nieuwOuderschapRecord(ouderActie)
            .indicatieOuder(Ja.J)
            .eindeRecord().build()
    }
}
