/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.Arrays;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkEenLijstOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkEenOperator;

/**
 * Vertalen van voorwaarden met GA1.
 */
public class GbaVoorwaardeVertalerGA1 implements GbaVoorwaardeVertaler {

    private final String[] delen;
    private final BrpType brpType;
    private final boolean verzameling;
    private final RubriekVertaler vertaler;

    /**
     * Constructor.
     * @param delen delen van de voorwaarde
     * @param brpType vertaalde element
     * @param verzameling betreft vergelijking met verzameling
     * @param vertaler vertaling van rubriekgegevens
     */
    public GbaVoorwaardeVertalerGA1(final String[] delen, final BrpType brpType, final boolean verzameling, final RubriekVertaler vertaler) {
        this.delen = Arrays.copyOf(delen, delen.length);
        this.brpType = brpType;
        this.verzameling = verzameling;
        this.vertaler = vertaler;
    }

    @Override
    public final Expressie verwerk() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        if (verzameling) {
            resultaat =
                    new ElementWaarde(
                            new Criterium(
                                    brpType.getType(),
                                    new GelijkEenLijstOperator(),
                                    vertaler.maakLijstVanWaarden(delen[GbaVoorwaardeConstanten.DEEL_REST])));
        } else {
            resultaat =
                    new ElementWaarde(
                            new Criterium(
                                    brpType.getType(),
                                    new GelijkEenOperator(),
                                    vertaler.vertaalWaardeVanRubriekOfAndereRubriek(delen[GbaVoorwaardeConstanten.DEEL_REST])));
        }
        return resultaat;
    }

}
