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
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KVOperator;
import org.springframework.stereotype.Component;

/**
 * Voorwaarde regel om een voorwaarde op gepriviligeerde te doen.
 */
@Component
public final class ProbasVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX = "^04\\.82\\.30\\s*(O)?GA(1|A).*(?i:proba).*";
    private static final int VOLGORDE = 400;

    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Constructor.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public ProbasVoorwaardeRegel(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX);
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType[] brpType;
        try {
            brpType = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType("probas");
        } catch (final GbaRubriekOnbekendExceptie e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), e);
        }

        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        final String operator = delen[GbaVoorwaardeConstanten.DEEL_OPERATOR];

        final Expressie result;
        switch (operator.toUpperCase()) {
            case "OGA1":
            case "OGAA":
                result = new ElementWaarde(new Criterium(brpType[0].getType(), new KNVOperator(), null));
                break;
            case "GA1":
            case "GAA":
                result = new ElementWaarde(new Criterium(brpType[0].getType(), new KVOperator(), "J"));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }

        return result;
    }
}
