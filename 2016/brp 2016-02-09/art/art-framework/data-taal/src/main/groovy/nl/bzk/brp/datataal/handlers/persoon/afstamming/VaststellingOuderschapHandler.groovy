package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.handlers.persoon.WijzigNamenAbility
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder

/**
 * Handler voor het behandelen van een gebeurtenis "actualiseerOuderschap".
 */
class VaststellingOuderschapHandler extends AbstractAfstammingHandler implements WijzigNamenAbility {
    final ActieModel ouderActie

    VaststellingOuderschapHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)

        ouderActie = maakActie(SoortActie.REGISTRATIE_OUDER)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, ouderActie, 1))
    }

    /**
     * Zoekt de niet adresgevende ouder en laat deze (en de groepen ouderschap
     * en ouderlijkgezag) vervallen.
     *
     * @return
     */
    void nooitOuderGeweest(PersoonHisVolledigImpl ouder) {
        PersoonHisVolledigImpl persoon = builder.hisVolledigImpl

        def familie = persoon.kindBetrokkenheid.relatie

        def vader = familie.ouderBetrokkenheden.find { it.ouderOuderschapHistorie.actueleRecord?.indicatieOuderUitWieKindIsGeboren == null && it.persoon?.ID == ouder.ID }

        if (vader) {
            vader.ouderOuderschapHistorie?.vervalGeheleHistorie(ouderActie, ouderActie.tijdstipRegistratie)
            vader.ouderOuderlijkGezagHistorie?.vervalGeheleHistorie(ouderActie, ouderActie.tijdstipRegistratie)
            vader.betrokkenheidHistorie.verval(ouderActie, ouderActie.tijdstipRegistratie)
        }
    }

    /**
     * Voegt de opgegeven ouder toe aan de familierechtelijke betrekking
     * indien er nog geen 2 ouderbetrokkenheden zijn.
     *
     * @param ouder de persoon die als ouder toegevoegd moet worden
     */
    void ouder(PersoonHisVolledigImpl ouder) {
        PersoonHisVolledigImpl persoon = builder.hisVolledigImpl

        def familie = persoon.kindBetrokkenheid.relatie

        if (familie.ouderBetrokkenheden.size() > 1) {
            throw new IllegalStateException('Er zijn reeds twee ouders vastgelegd voor deze persoon')
        }

        if (ouder?.isIngeschrevene()) {
            ouder.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(ouder, ouderActie, 2))
        }

        familie.betrokkenheden << new OuderHisVolledigImplBuilder(familie, ouder)
            .metVerantwoording(ouderActie)
            .nieuwOuderschapRecord(ouderActie)
            .indicatieOuder(Ja.J)
            .eindeRecord().build()
    }
}
