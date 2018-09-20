/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal;

import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;

/**
 * Deze bouwer wordt gebruikt om lijst-expressies op te bouwen. Deze expressies worden onder andere gebruikt in het
 * gegevensfilter. Het zijn expressies die achter elkaar geplakt staan met komma's er tussen.
 */
public class LijstExpressieBouwer {

    private static final String EXPRESSIE_SCHEIDINGSTEKEN = ",";

    private static final String EXPRESSIE_START_LIJST = "{";

    private static final String EXPRESSIE_EINDE_LIJST = "}";

    private final StringBuffer totaleExpressie = new StringBuffer(EXPRESSIE_START_LIJST);

    /**
     * Voegt een expressiedeel toe.
     *
     * @param expressieDeel Het expressiedeel.
     */
    public final void voegExpressieDeelToe(final String expressieDeel) {
        final boolean erZijnExpressieDelenAanwezig = !totaleExpressie.toString().equals(EXPRESSIE_START_LIJST);
        if (erZijnExpressieDelenAanwezig) {
            totaleExpressie.append(EXPRESSIE_SCHEIDINGSTEKEN);
        }
        totaleExpressie.append(expressieDeel);
    }

    /**
     * Voegt expressiedelen toe.
     *
     * @param expressieDelen De expressiedelen.
     */
    public final void voegExpressieDelenToe(final List<String> expressieDelen) {
        for (final String expressieDeel : expressieDelen) {
            voegExpressieDeelToe(expressieDeel);
        }
    }

    /**
     * Voegt expressiedelen toe.
     *
     * @param expressieDelen De expressiedelen.
     */
    public final void voegExpressietekstDelenToe(final List<ExpressietekstAttribuut> expressieDelen) {
        for (final ExpressietekstAttribuut expressieDeel : expressieDelen) {
            voegExpressieDeelToe(expressieDeel.getWaarde());
        }
    }

    /**
     * Geeft de totale expressie als string.
     *
     * @return De totale expressie.
     */
    public final String geefTotaleExpressie() {
        return totaleExpressie.toString() + EXPRESSIE_EINDE_LIJST;
    }

    /**
     * Geeft de totale expressie als geparsde expressie.
     *
     * @return De geparsde expressie.
     * @throws ExpressieExceptie de expressie exceptie
     */
    public final Expressie geefTotaleGeparsdeExpressie() throws ExpressieExceptie {
        final ParserResultaat parserResultaat = BRPExpressies.parse(geefTotaleExpressie());
        if (!parserResultaat.succes()) {
            throw new ExpressieExceptie("Het is niet gelukt de expressie te parsen: " + geefTotaleExpressie() + " Foutmelding: "
                                            + parserResultaat.getFoutmelding());
        }
        return parserResultaat.getExpressie();
    }

}
