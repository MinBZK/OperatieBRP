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
 * Voorwaarde regel KV rubriek en KNV rubriek. Uitgezonderd de rubriek 07.67.10
 *
 */
@Component
public class KomtVoorVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    private static final String REGEX_PATROON = "^(KV|KNV).*";
    private static final int VOLGORDE = 110;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public KomtVoorVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.split(" ");
        final StringBuilder result = new StringBuilder();
        try {
            final BrpType[] brpTypen = vertaalRubriekNaarBrpTypen(delen[1]);
            if (brpTypen.length == 1) {
                verwerkVoorwaarde(delen, brpTypen[0], result, voorwaardeRegel);
            } else {
                final StringBuilder voorwaardeBuilder = new StringBuilder();
                for (final BrpType brpType : brpTypen) {
                    voorwaardeBuilder.append('(');
                    verwerkVoorwaarde(delen, brpType, voorwaardeBuilder, voorwaardeRegel);
                    switch (delen[0]) {
                        case "KV":
                            voorwaardeBuilder.append(") OF ");
                            break;
                        case "KNV":
                            voorwaardeBuilder.append(") EN ");
                            break;
                        default:
                            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
                    }
                }
                result.append(voorwaardeBuilder.toString().replaceAll("\\ (OF|EN)\\ $", ""));
            }
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, groe);
        }

        return result.toString();
    }

    private BrpType[] vertaalRubriekNaarBrpTypen(final String rubriek) throws GbaRubriekOnbekendExceptie {
        final BrpType[] result;
        if (rubriek.matches("04\\.05\\.10")) {
            // Uitzondering voor nationaliteit
            result = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(rubriek + ".voorkomens");
        } else {
            result = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(rubriek);
        }
        return result;
    }

    private void verwerkVoorwaarde(final String[] delen, final BrpType brpType, final StringBuilder result, final String voorwaardeRegel)
        throws GbaVoorwaardeOnvertaalbaarExceptie
    {
        final String selWaarde = delen[0] + brpType.isLijst();
        switch (selWaarde) {
            case "KVfalse":
                result.append(String.format("NIET IS_NULL(%s)", brpType.getType()));
                break;
            case "KNVfalse":
                result.append(String.format("IS_NULL(%s)", brpType.getType()));
                break;
            case "KVtrue":
                result.append(String.format("ER_IS(%s, v, NIET IS_NULL(v))", brpType.getType()));
                break;
            case "KNVtrue":
                result.append(String.format("ALLE(%s, v, IS_NULL(v))", brpType.getType()));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
        }
    }
}
