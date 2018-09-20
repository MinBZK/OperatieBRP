/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;

/**
 * Abstract implementatie van de standaardregel. Aangezien binnen de standaardconversie ook een aantal waarden van de
 * rubrieken door middel van conversietabellen moet worden vertaald, geeft deze abstract een extentiepunt om de
 * individuele waarde eventueel te vertalen door een conversietabel. Daarnaast moet de volgorde en het filter nog worden
 * geimplementeerd.
 */
public abstract class AbstractStandaardVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {
    private static final String VERGELIJKING_ONGELIJK = "(NIET(%1$s %2$s %3$s) OF IS_NULL(%1$s))";
    private static final String VERGELIJKING_ONGELIJK_LIJST = "(NIET (%1$s %2$s {%3$s}) OF IS_NULL(%1$s))";
    private static final String PATTERN_WILDCARD = "/*";
    private static final String OPERATOR_NOG_NIET_ONDERSTEUND = "operator nog niet ondersteund: %s";
    private static final String OPERATOR_GELIJK = "=";
    private static final String OPERATOR_IN = "IN";
    private static final String OPERATOR_ONGELIJK_AAN_ALLE = "OGAA";
    private static final String OPERATOR_ONGELIJK_AAN_EEN = "OGA1";
    private static final String OPERATOR_GELIJK_AAN_EEN = "GA1";

    /**
     * Maakt nieuwe AbstractVoorwaardeRegel aan.
     *
     * @param volgorde
     *            volgorde waaring regel moet worden uitgevoerd
     * @param regexPatroon
     *            reguliere expressie waaraan regel moet voldoen
     */
    public AbstractStandaardVoorwaardeRegel(final int volgorde, final String regexPatroon) {
        super(volgorde, regexPatroon);
    }

    @Override
    public final String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.split(SPLIT_CHARACTER, DEEL_AANTAL);
        final StringBuilder result = new StringBuilder();
        try {
            final BrpType[] brpTypen = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(delen[DEEL_RUBRIEK]);
            final boolean verzameling = delen[DEEL_REST].contains("OFVGL") || delen[DEEL_REST].contains("ENVGL");
            if (brpTypen.length == 1) {
                verwerkVoorwaarde(delen, result, brpTypen[0], verzameling, voorwaardeRegel);
            } else {
                result.append('(');
                for (final BrpType brpType : brpTypen) {
                    verwerkVoorwaarde(delen, result, brpType, verzameling, voorwaardeRegel);
                    switch (delen[DEEL_OPERATOR]) {
                        case OPERATOR_GELIJK_AAN_EEN:
                            result.append(" OF ");
                            break;
                        case OPERATOR_ONGELIJK_AAN_EEN:
                        case OPERATOR_ONGELIJK_AAN_ALLE:
                            result.append(" EN ");
                            break;
                        default:
                            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
                    }
                }
                result.append(')');
            }
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, groe);
        }
        return result.toString().replaceAll("\\ (OF|EN)\\ \\)$", "\\)");
    }

    private void verwerkVoorwaarde(
        final String[] delen,
        final StringBuilder result,
        final BrpType brpType,
        final boolean verzameling,
        final String voorwaardeRegel) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        switch (delen[DEEL_OPERATOR]) {
            case OPERATOR_GELIJK_AAN_EEN:
                verwerkGA1(result, delen, brpType, verzameling);
                break;
            case OPERATOR_ONGELIJK_AAN_EEN:
                verwerkOGA1(result, delen, brpType, verzameling);
                break;
            case OPERATOR_ONGELIJK_AAN_ALLE:
                verwerkOGAA(result, delen, brpType, verzameling);
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(String.format(OPERATOR_NOG_NIET_ONDERSTEUND, voorwaardeRegel));
        }
    }

    private void verwerkGA1(final StringBuilder result, final String[] delen, final BrpType brpType, final boolean waardeLijst)
        throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        if (brpType.isLijst()) {
            if (waardeLijst) {
                result.append(String.format(
                    "ER_IS(%1$s, v, v %2$s {%3$s})",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_IN, delen[DEEL_REST]),
                    maakLijstVanWaarden(delen[DEEL_REST])));
            } else {
                result.append(String.format(
                    "ER_IS(%1$s, v, v %2$s %3$s)",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_GELIJK, delen[DEEL_REST]),
                    vertaalWaardeVanRubriekOfAndereRubriek(delen[DEEL_REST])));
            }
        } else {
            if (waardeLijst) {
                result.append(String.format(
                    "%1$s %2$s {%3$s}",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_IN, delen[DEEL_REST]),
                    maakLijstVanWaarden(delen[DEEL_REST])));
            } else {
                result.append(String.format(
                    "%1$s %2$s %3$s",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_GELIJK, delen[DEEL_REST]),
                    vertaalWaardeVanRubriekOfAndereRubriek(delen[DEEL_REST])));
            }
        }
    }

    private void verwerkOGA1(final StringBuilder result, final String[] delen, final BrpType brpType, final boolean waardeLijst)
        throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        if (brpType.isLijst()) {
            if (waardeLijst) {
                // Expressie uitbreiden met aantal vanwege uitzondering in LO3 dat bij geen voorkomende rubrieken TRUE
                // wordt teruggeggeven.
                result.append(String.format(
                    "(ER_IS(%1$s, v, NIET(v %2$s {%3$s})) OF AANTAL(%1$s) = 0)",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_IN, delen[DEEL_REST]),
                    maakLijstVanWaarden(delen[DEEL_REST])));
            } else {
                // Expressie uitbreiden met aantal vanwege uitzondering in LO3 dat bij geen voorkomende rubrieken TRUE
                // wordt teruggeggeven.
                result.append(String.format(
                    "(ER_IS(%1$s, v, NIET(v %2$s %3$s)) OF AANTAL(%1$s) = 0)",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_GELIJK, delen[DEEL_REST]),
                    vertaalWaardeVanRubriekOfAndereRubriek(delen[DEEL_REST])));
            }
        } else {
            if (waardeLijst) {
                result.append(String.format(
                    VERGELIJKING_ONGELIJK_LIJST,
                    brpType.getType(),
                    bepaalOperator(OPERATOR_IN, delen[DEEL_REST]),
                    maakLijstVanWaarden(delen[DEEL_REST])));
            } else {
                result.append(String.format(
                    VERGELIJKING_ONGELIJK,
                    brpType.getType(),
                    bepaalOperator(OPERATOR_GELIJK, delen[DEEL_REST]),
                    vertaalWaardeVanRubriekOfAndereRubriek(delen[DEEL_REST])));
            }
        }
    }

    private void verwerkOGAA(final StringBuilder result, final String[] delen, final BrpType brpType, final boolean waardeLijst)
        throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        if (brpType.isLijst()) {
            if (waardeLijst) {
                result.append(String.format(
                    "ALLE(%1$s, v, NIET(v %2$s {%3$s}))",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_IN, delen[DEEL_REST]),
                    maakLijstVanWaarden(delen[DEEL_REST])));
            } else {
                result.append(String.format(
                    "ALLE(%1$s, v, NIET(v %2$s %3$s))",
                    brpType.getType(),
                    bepaalOperator(OPERATOR_GELIJK, delen[DEEL_REST]),
                    vertaalWaardeVanRubriekOfAndereRubriek(delen[DEEL_REST])));
            }
        } else {
            if (waardeLijst) {
                result.append(String.format(
                    VERGELIJKING_ONGELIJK_LIJST,
                    brpType.getType(),
                    bepaalOperator(OPERATOR_IN, delen[DEEL_REST]),
                    maakLijstVanWaarden(delen[DEEL_REST])));
            } else {
                result.append(String.format(
                    VERGELIJKING_ONGELIJK,
                    brpType.getType(),
                    bepaalOperator(OPERATOR_GELIJK, delen[DEEL_REST]),
                    vertaalWaardeVanRubriekOfAndereRubriek(delen[DEEL_REST])));
            }
        }
    }

    private String bepaalOperator(final String operator, final String waarden) {
        String result = operator;
        if (OPERATOR_GELIJK.equals(operator) && waarden.contains(PATTERN_WILDCARD)) {
            result = "%=";
        } else if (OPERATOR_IN.equals(operator) && waarden.contains(PATTERN_WILDCARD)) {
            result = "IN%";
        }
        return result;
    }

    private String maakLijstVanWaarden(final String waarden) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] losseWaarden = waarden.replaceAll(" OFVGL ", SPLIT_CHARACTER).replaceAll(" ENVGL ", SPLIT_CHARACTER).split(SPLIT_CHARACTER);
        final StringBuilder lijst = new StringBuilder();
        for (final String losseWaarde : losseWaarden) {
            final String waarde = vertaalWaardeVanRubriekOfAndereRubriek(losseWaarde);
            if (waarde != null && !"".equals(waarde)) {
                lijst.append(waarde);
                lijst.append(", ");
            }
        }
        return lijst.toString().replaceAll(",\\ $", "");
    }

    private String vertaalWaardeVanRubriekOfAndereRubriek(final String ruweWaarde) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        if (ruweWaarde.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            final BrpType[] brpTypen = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(ruweWaarde);
            if (brpTypen.length != 1) {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(
                    "Een vergelijking met LO3 rubriek die meerdere BRP expressies oplevert wordt niet ondersteund.");
            } else {
                if (brpTypen[0].isLijst()) {
                    throw new GbaVoorwaardeOnvertaalbaarExceptie(
                        "Een vergelijking met andere rubriek (die een lijst oplevert) wordt in deze situatie niet ondersteund.");
                } else {
                    return brpTypen[0].getType();
                }
            }
        }

        return vertaalWaardeVanRubriek(ruweWaarde);
    }

    /**
     * Extentie punt om de waarde van een rubriek te vertalen. Default impl haalt de voorloopnullen weg en vertaal /*
     * naar *.
     *
     * @param ruweWaarde
     *            de te vertalen rubriekwaarde
     * @return de vertaalde rubriekwaarde
     */
    protected abstract String vertaalWaardeVanRubriek(final String ruweWaarde);

}
