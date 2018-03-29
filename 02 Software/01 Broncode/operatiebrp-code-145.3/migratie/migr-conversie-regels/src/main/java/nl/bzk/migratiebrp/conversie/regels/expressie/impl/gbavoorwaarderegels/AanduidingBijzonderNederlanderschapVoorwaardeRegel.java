/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.EnWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KVOperator;
import org.springframework.stereotype.Component;

/**
 * Voorwaarde regel conversie voor aanduiding bijzonder nederlanderschap.
 */
@Component
public class AanduidingBijzonderNederlanderschapVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String QUOTES_STRING = "\"";
    private static final String LEGE_STRING = "";
    private static final String REGEX = "^04\\.65\\.10.*";
    private static final int VOLGORDE = 400;

    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Constructor.
     * @param gbaRubriekNaarBrpTypeVertaler rubriek vertaler
     */
    @Inject
    public AanduidingBijzonderNederlanderschapVoorwaardeRegel(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX);
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public final Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);

        if (delen.length == GbaVoorwaardeConstanten.DEEL_AANTAL) {
            final BrpType brpType = bepaalBrpType(voorwaardeRegel, delen[GbaVoorwaardeConstanten.DEEL_REST]);
            return maakExpressie(voorwaardeRegel, delen[GbaVoorwaardeConstanten.DEEL_OPERATOR], brpType);
        } else {

            try {
                final List<Expressie> expressies = new ArrayList<>();
                for (final BrpType brpType : gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK])) {
                    expressies.add(maakExpressie(voorwaardeRegel, "OGA1", brpType));
                }
                return new EnWaarde(expressies.toArray(new Expressie[expressies.size()]));
            } catch (GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), gbaRubriekOnbekendExceptie);
            }
        }
    }

    private BrpType bepaalBrpType(RubriekWaarde voorwaardeRegel, String s) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType brpType;
        try {
            if ("B".equals(s.replaceAll(QUOTES_STRING, LEGE_STRING))) {
                brpType = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType("04.65.10.b")[0];
            } else if ("V".equals(s.replaceAll(QUOTES_STRING, LEGE_STRING))) {
                brpType = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType("04.65.10.v")[0];
            } else {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
            }
        } catch (final GbaRubriekOnbekendExceptie e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), e);
        }
        return brpType;
    }

    private Expressie maakExpressie(RubriekWaarde voorwaardeRegel, String s, BrpType brpType) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie result;
        switch (s) {
            case "GA1":
            case "GAA":
                result = new ElementWaarde(new Criterium(brpType.getType(), new KVOperator(), "J"));
                break;
            case "OGA1":
            case "OGAA":
                result = new ElementWaarde(new Criterium(brpType.getType(), new KNVOperator(), null));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }
        return result;
    }
}
