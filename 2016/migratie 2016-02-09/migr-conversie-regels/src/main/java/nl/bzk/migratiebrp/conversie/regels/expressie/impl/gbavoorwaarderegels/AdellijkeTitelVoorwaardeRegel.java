/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.springframework.stereotype.Component;

/**
 * Conversie van de adelijke titel en predicaat naar brp expressie.
 *
 */
@Component
public final class AdellijkeTitelVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    private static final String REGEX_PATROON = ".*(01|02|03|05|09|51|52|53|55|59)\\.02\\.20.*";
    private static final int VOLGORDE = 100;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public AdellijkeTitelVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        String result;
        try {
            final BrpType[] brpTypen = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType("01.02.20");
            final String expressieAdellijkeTitel = brpTypen[0].getType();
            final String expressiePredicaat = brpTypen[1].getType();
            if (voorwaardeRegel.matches("^KV\\ +01\\.02\\.20")) {
                result = String.format("(NIET IS_NULL(%1$s) OF NIET IS_NULL(%2$s))", expressieAdellijkeTitel, expressiePredicaat);
            } else if (voorwaardeRegel.matches("^KNV\\ +01\\.02\\.20")) {
                result = String.format("(IS_NULL(%1$s) EN IS_NULL(%2$s))", expressieAdellijkeTitel, expressiePredicaat);
            } else {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(String.format("Kan voorwaarde [%s] niet vertalen", voorwaardeRegel));
            }
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, groe);
        }
        return result;
    }
}
