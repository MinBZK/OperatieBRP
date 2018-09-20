/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;


/**
 * Abstracte class bedoeld voor het testen van de binding van antwoordberichten op vraag/informatie berichten. Deze
 * class biedt helper methodes om testdata aan te maken welke veelal gebruikt kan worden in de tests en helper methodes
 * voor het opbouwen van het verwachte antwoord.
 */
public abstract class AbstractVraagBerichtBindingUitIntegratieTest<T> extends AbstractBindingUitIntegratieTest<T> {

    protected BerichtResultaatGroepBericht maakBerichtResultaat(final SoortMelding hoogsteMeldingNiveau) {
        final BerichtResultaatGroepBericht resultaat = new BerichtResultaatGroepBericht();
        resultaat.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        resultaat.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(hoogsteMeldingNiveau));
        return resultaat;
    }

    protected PersoonHisVolledigImpl maakAntwoordPersoon() {
        return TestPersoonAntwoordPersoon.maakAntwoordPersoon();
    }

    protected void voegKindBetrokkenheidToeVoorAntwoordPersoon(final PersoonHisVolledigImpl persoon) {
        TestPersoonAntwoordPersoon.voegKindBetrokkenheidToeAanPersoon(persoon);
    }

    protected void voegOuderBetrokkenheidToeVoorAntwoordPersoon(final PersoonHisVolledigImpl persoon) {
        TestPersoonAntwoordPersoon.voegOuderBetrokkenheidToeAanPersoon(persoon);
    }

    protected void voegPartnerBetrokkenheidToeVoorAntwoordPersoon(final PersoonHisVolledigImpl persoon) {
        TestPersoonAntwoordPersoon.voegPartnerBetrokkenheidToeAanPersoon(persoon);
    }

    protected PersoonHisVolledigImpl maakAntwoordPersoon(final boolean defaultMagGeleverdWordenVoorAttributen) {
        return TestPersoonAntwoordPersoon.maakAntwoordPersoon(defaultMagGeleverdWordenVoorAttributen);
    }

    protected void voegKindBetrokkenheidToeVoorAntwoordPersoon(final PersoonHisVolledigImpl persoon,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        TestPersoonAntwoordPersoon.voegKindBetrokkenheidToeAanPersoon(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    protected void voegOuderBetrokkenheidToeVoorAntwoordPersoon(final PersoonHisVolledigImpl persoon,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        TestPersoonAntwoordPersoon.voegOuderBetrokkenheidToeAanPersoon(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    protected void voegPartnerBetrokkenheidToeVoorAntwoordPersoon(final PersoonHisVolledigImpl persoon,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        TestPersoonAntwoordPersoon.voegPartnerBetrokkenheidToeAanPersoon(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     * Bouwt en retourneert een verwacht antwoord bericht met opgeven tijdstip van registratie en een melding, waarbij
     * de melding de opgegeven melding code, regelcode en melding omschrijving heeft. Het antwoordbericht bevat verder
     * de content zoals opgenomen in het bestan
     * opgegeven door de 'verwachtAntwoordBerichtBestandsNaam'.
     *
     * @param verwachtAntwoordBerichtBestandsNaam de bestandsnaam van het bestand met daarin de verwachte content van
     *                                            het antwoord.
     * @return het verwachte antwoordbericht.
     */
    protected String bouwVerwachteAntwoordBericht(final String verwachtAntwoordBerichtBestandsNaam) {
        final StringBuilder verwachteWaarde = new StringBuilder();
        bouwAntwoordElement(verwachteWaarde, verwachtAntwoordBerichtBestandsNaam);
        return verwachteWaarde.toString();
    }

    protected void zetObjectSleutels(final PersoonHisVolledigView persoonHisVolledigView) {
        persoonHisVolledigView.setObjectSleutel("1234");
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
            final RelatieHisVolledig relatie = betrokkenheidHisVolledigView.getRelatie();
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                if (((BetrokkenheidHisVolledigView) betrokkenheidHisVolledig).isZichtbaar()) {
                    ((PersoonHisVolledigView) betrokkenheidHisVolledig.getPersoon()).setObjectSleutel("1234");
                }
            }
        }

    }
}
