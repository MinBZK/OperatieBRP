package nl.bzk.brp.datataal.handlers.persoon.afstamming

import groovy.transform.SelfType
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder

/**
 *
 */
@SelfType(AbstractAfstammingHandler)
trait WijzigOudersAbility {
    def onbekend = null

    def ouders(Map<String, PersoonHisVolledigImpl> oudersMap) {
        valideerMapKeys(oudersMap, ['moeder', 'vader'], "Ouders kunnen alleen 'moeder' en 'vader' bevatten")

        def kind = builder.hisVolledigImpl.kindBetrokkenheid as KindHisVolledigImpl
        def familie = kind.relatie

        // bestaande ouder betrokkenheden vervallen
        familie.ouderBetrokkenheden.each { OuderHisVolledigImpl ouderBetr ->
            ouderBetr.ouderOuderschapHistorie.beeindig(ouderActie, ouderActie)
            ouderBetr.ouderOuderlijkGezagHistorie.beeindig(ouderActie, ouderActie)
            ouderBetr.betrokkenheidHistorie.verval(ouderActie, ouderActie.tijdstipRegistratie)
            if (ouderBetr.persoon?.isIngeschrevene()) {
                ouderBetr.persoon.persoonAfgeleidAdministratiefHistorie?.
                    voegToe(GegevensAfleider.maakAfgeleidAdministratief(ouderBetr.persoon, ouderActie, 3))
            }
        }

        def moeder = oudersMap.moeder as PersoonHisVolledigImpl
        def vader  = oudersMap.vader as PersoonHisVolledigImpl

        // moeder
        if (oudersMap.containsKey('moeder')) {
            if (moeder?.isIngeschrevene()) {
                moeder.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(moeder, ouderActie, 2))
            }
            familie.betrokkenheden << new OuderHisVolledigImplBuilder(familie, moeder)
                .metVerantwoording(ouderActie)
                .nieuwOuderschapRecord(ouderActie)
                .indicatieOuder(Ja.J)
                .eindeRecord()
                .build()
        }

        // vader
        if (oudersMap.containsKey('vader')) {
            if (vader?.isIngeschrevene()) {
                vader.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(vader, ouderActie, 2))
            }
            familie.betrokkenheden << new OuderHisVolledigImplBuilder(familie, vader)
                .metVerantwoording(ouderActie)
                .nieuwOuderschapRecord(ouderActie)
                .indicatieOuder(Ja.J)
                .eindeRecord().build()
        }
    }
}
