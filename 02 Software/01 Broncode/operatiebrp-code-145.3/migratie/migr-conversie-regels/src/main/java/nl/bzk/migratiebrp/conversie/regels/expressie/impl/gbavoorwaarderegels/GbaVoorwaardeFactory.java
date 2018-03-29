/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;

/**
 * Maakt de correcte vertaler aan.
 */
public interface GbaVoorwaardeFactory {

    /**
     * Geef correcte vertaler terug.
     * @param delen delen van de voorwaarde
     * @param brpType vertaalde element
     * @param verzameling betreft vergelijking met verzameling
     * @param vertaler vertaling van rubriekgegevens
     * @return een vertaler
     * @throws GbaVoorwaardeOnvertaalbaarExceptie Voorwaarde niet te vertalen
     */
    static GbaVoorwaardeVertaler geefVertaler(final String[] delen, final BrpType brpType, final boolean verzameling, final RubriekVertaler vertaler)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final GbaVoorwaardeVertaler resultaat;
        if (brpType.isInverse()) {
            resultaat = new GbaVoorwaardeVertalerInverse(brpType);
        } else {
            switch (delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]) {
                case "GA1":
                    resultaat = new GbaVoorwaardeVertalerGA1(delen, brpType, verzameling, vertaler);
                    break;
                case "OGA1":
                    resultaat = new GbaVoorwaardeVertalerOGA1(delen, brpType, verzameling, vertaler);
                    break;
                case "OGAA":
                    resultaat = new GbaVoorwaardeVertalerOGAA(delen, brpType, verzameling, vertaler);
                    break;
                default:
                    throw new GbaVoorwaardeOnvertaalbaarExceptie(
                            String.format("operator nog niet ondersteund: %s", delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]));
            }
        }
        return resultaat;
    }
}
