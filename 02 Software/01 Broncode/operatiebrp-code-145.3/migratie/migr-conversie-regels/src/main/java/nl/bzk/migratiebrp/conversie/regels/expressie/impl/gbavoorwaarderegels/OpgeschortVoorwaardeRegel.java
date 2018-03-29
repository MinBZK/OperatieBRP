/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkEenOperator;
import org.springframework.stereotype.Component;

/**
 * Vertaald de KNV 07.67.10 en KNV 07.67.20 opgeschort regel.
 */
@Component
public class OpgeschortVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    private static final String REGEX_PATROON = "^(KV|KNV)\\ 07\\.67\\.(1|2)0.*";
    private static final int VOLGORDE = 100;

    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public OpgeschortVoorwaardeRegel(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public final Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(" ");
        final Expressie result;
        try {
            final BrpType brpType = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType("07.67.20")[0];
            switch (delen[0]) {
                case "KV":
                    result = new ElementWaarde(new Criterium(brpType.getType(), new OngelijkEenOperator(), "\"A\""));
                    break;
                case "KNV":
                    result = new ElementWaarde(new Criterium(brpType.getType(), new GelijkEenOperator(), "\"A\""));
                    break;
                default:
                    throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
            }
        } catch (GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), groe);
        }

        return result;
    }
}
