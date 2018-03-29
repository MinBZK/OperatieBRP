/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
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
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een nationaliteit betreft.
 */
@Component
public final class NationaliteitVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = "^(04|54)\\.05\\.10.*";
    private static final int VOLGORDE = 500;

    private final StandaardVoorwaardeRegelUtil standaardVoorwaardeRegelUtil;
    private final GbaRubriekNaarBrpTypeVertaler vertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public NationaliteitVoorwaardeRegel(
            final ConversietabelFactory conversieTabelFactory,
            final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        vertaler = gbaRubriekNaarBrpTypeVertaler;
        standaardVoorwaardeRegelUtil = new StandaardVoorwaardeRegelUtil(gbaRubriekNaarBrpTypeVertaler, waarde ->
                conversieTabelFactory.createNationaliteitConversietabel().converteerNaarBrp(new Lo3NationaliteitCode(waarde)).getWaarde());
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        if (delen[GbaVoorwaardeConstanten.DEEL_REST].contains("0499")) {
            final String geschoondeRest =
                    delen[GbaVoorwaardeConstanten.DEEL_REST].replace("OFVGL 0499", "")
                            .replace("ENVGL 0499", "")
                            .replace("0499 OFVGL", "")
                            .replace("0499 ENVGL", "")
                            .replace("0499", "")
                            .trim();

            final BrpType[] brpType;
            try {
                brpType = vertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK] + ".staatloos");
            } catch (final GbaRubriekOnbekendExceptie ex) {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), ex);
            }

            if (geschoondeRest.length() > 0) {
                resultaat = verwerkExpressieMetStaatloosAlsDeel(delen, geschoondeRest, brpType, voorwaardeRegel);
            } else {
                resultaat = verwerkAlleenStaatloosExpressie(delen, brpType, voorwaardeRegel);
            }
        } else {
            resultaat = standaardVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel);
        }
        return resultaat;
    }

    private Expressie verwerkExpressieMetStaatloosAlsDeel(
            final String[] delen,
            final String geschoondeRest,
            final BrpType[] brpType,
            final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final StringBuilder builder = new StringBuilder(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
        builder.append(" ").append(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]).append(" ").append(geschoondeRest);
        final Expressie restExpressie = standaardVoorwaardeRegelUtil.getBrpExpressie(new RubriekWaarde(builder.toString()));
        final Expressie staatloosExpressie;
        switch (delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]) {
            case "OGA1":
                staatloosExpressie = new ElementWaarde(new Criterium(brpType[0].getType(), new KNVOperator(), null));
                resultaat = new OfWaarde(restExpressie, staatloosExpressie);
                break;
            case "OGAA":
                staatloosExpressie = new ElementWaarde(new Criterium(brpType[0].getType(), new KNVOperator(), null));
                resultaat = new EnWaarde(restExpressie, staatloosExpressie);
                break;
            case "GA1":
                staatloosExpressie = new ElementWaarde(new Criterium(brpType[0].getType(), new KVOperator(), null));
                resultaat = new OfWaarde(restExpressie, staatloosExpressie);
                break;
            case "GAA":
                staatloosExpressie = new ElementWaarde(new Criterium(brpType[0].getType(), new KVOperator(), null));
                resultaat = new EnWaarde(restExpressie, staatloosExpressie);
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }
        return resultaat;
    }

    private Expressie verwerkAlleenStaatloosExpressie(final String[] delen, final BrpType[] brpType, final RubriekWaarde voorwaardeRegel)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        switch (delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]) {
            case "OGA1":
            case "OGAA":
                resultaat = new ElementWaarde(new Criterium(brpType[0].getType(), new KNVOperator(), null));
                break;
            case "GA1":
            case "GAA":
                resultaat = new ElementWaarde(new Criterium(brpType[0].getType(), new KVOperator(), null));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }
        return resultaat;
    }
}
