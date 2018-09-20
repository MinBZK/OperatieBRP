package groovy.runtime.metaclass

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.model.basis.HistorieEntiteit
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Groovy Extension Module voor klasses in {@link nl.bzk.brp.model.hisvolledig} packages.
 */
class ModelHisVolledigExtensions {
    /**
     * Is een persoon een ingeschreven persoon?
     * @param self
     * @return {@code true} als persoon.soort de waarde {@link SoortPersoon#INGESCHREVENE} heeft
     */
    static boolean isIngeschrevene(PersoonHisVolledigImpl self) {
        return SoortPersoon.INGESCHREVENE == self.soort?.waarde
    }

    /**
     * Geeft alle voorkomens van 'identificerende' groepen.
     *
     * @param self
     * @return een lijst van voorkomens
     * @see nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView#getIdentificerendeHistorieRecords()
     */
    static Set<HistorieEntiteit> getIdentificerendeHistorieRecords(PersoonHisVolledigImpl self) {
        final Set<HistorieEntiteit> resultaat = new HashSet<>()
        resultaat.addAll(self.persoonIdentificatienummersHistorie.getHistorie())
        resultaat.addAll(self.persoonSamengesteldeNaamHistorie.getHistorie())
        resultaat.addAll(self.persoonGeboorteHistorie.getHistorie())
        resultaat.addAll(self.persoonGeslachtsaanduidingHistorie.getHistorie())
        return resultaat
    }

    /**
     * Geeft alle voorkomens van alle groepen op een PERSOONSLIJST.
     *
     * @param self
     * @return een lijst van voorkomens
     * @see nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView#getTotaleLijstVanHisElementenOpPersoonsLijst()
     */
    static List<HistorieEntiteit> getVoorkomens(PersoonHisVolledigImpl self) {
        final List<HistorieEntiteit> resultaat = []

        resultaat.addAll(self.persoonAfgeleidAdministratiefHistorie.getHistorie())
        resultaat.addAll(self.persoonIdentificatienummersHistorie.getHistorie())
        resultaat.addAll(self.persoonSamengesteldeNaamHistorie.getHistorie())
        resultaat.addAll(self.persoonGeboorteHistorie.getHistorie())
        resultaat.addAll(self.persoonGeslachtsaanduidingHistorie.getHistorie())
        resultaat.addAll(self.persoonInschrijvingHistorie.getHistorie())
        resultaat.addAll(self.persoonNummerverwijzingHistorie.getHistorie())
        resultaat.addAll(self.persoonBijhoudingHistorie.getHistorie())
        resultaat.addAll(self.persoonOverlijdenHistorie.getHistorie())
        resultaat.addAll(self.persoonNaamgebruikHistorie.getHistorie())
        resultaat.addAll(self.persoonMigratieHistorie.getHistorie())
        resultaat.addAll(self.persoonVerblijfsrechtHistorie.getHistorie())
        resultaat.addAll(self.persoonUitsluitingKiesrechtHistorie.getHistorie())
        resultaat.addAll(self.persoonDeelnameEUVerkiezingenHistorie.getHistorie())
        resultaat.addAll(self.persoonPersoonskaartHistorie.getHistorie())

        self.adressen.each { resultaat.addAll(it.persoonAdresHistorie.historie) }
        self.geslachtsnaamcomponenten.each { resultaat.addAll( it.persoonGeslachtsnaamcomponentHistorie.historie) }
        self.indicaties.each { resultaat.addAll(it.persoonIndicatieHistorie.historie) }
        self.nationaliteiten.each { resultaat.addAll(it.persoonNationaliteitHistorie.historie) }
        self.reisdocumenten.each { resultaat.addAll(it.persoonReisdocumentHistorie.historie) }
        self.voornamen.each { resultaat.addAll(it.persoonVoornaamHistorie.historie) }
        self.verificaties.each { resultaat.addAll(it.persoonVerificatieHistorie.historie) }

        self.afnemerindicaties.each { resultaat.addAll(it.persoonAfnemerindicatieHistorie.historie) }
        self.onderzoeken.each { resultaat.addAll( it.persoonOnderzoekHistorie.historie) }
        self.verstrekkingsbeperkingen.each { resultaat.addAll(it.persoonVerstrekkingsbeperkingHistorie.historie) }

        self.betrokkenheden.each { BetrokkenheidHisVolledigImpl betr ->
            // relatie historie
            resultaat.addAll(betr.relatie?.relatieHistorie?.historie)

            betr?.relatie?.betrokkenheden?.each { BetrokkenheidHisVolledigImpl gerelateerde ->
                if (gerelateerde != betr) {
                    // gegevens van gerelateerde personen
                    if (gerelateerde.persoon && gerelateerde.persoon.ID != self.ID) {
                        resultaat.addAll(gerelateerde.persoon.identificerendeHistorieRecords)
                        resultaat.addAll(gerelateerde.persoon.persoonAfgeleidAdministratiefHistorie.historie)

                        if (gerelateerde instanceof PartnerHisVolledigImpl) {
                            resultaat.addAll(gerelateerde.persoon.persoonNaamgebruikHistorie.historie)
                        }
                    }

                    // betrokkenheid historie
                    resultaat.addAll(gerelateerde.betrokkenheidHistorie.historie)
                    if (gerelateerde instanceof OuderHisVolledigImpl) {
                        resultaat.addAll(gerelateerde.getOuderOuderlijkGezagHistorie().historie)
                        resultaat.addAll(gerelateerde.getOuderOuderschapHistorie().historie)
                    }
                }
            }
        }

        resultaat.flatten()
    }

    /**
     *
     * @param self
     * @param betrokkenheid
     * @return
     */
    static RelatieHisVolledigImpl leftShift(RelatieHisVolledigImpl self, BetrokkenheidHisVolledigImpl betrokkenheid) {
        self.betrokkenheden.add(betrokkenheid)
        return self
    }
}
