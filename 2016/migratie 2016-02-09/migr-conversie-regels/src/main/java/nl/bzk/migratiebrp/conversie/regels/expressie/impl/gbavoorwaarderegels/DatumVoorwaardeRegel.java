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
 * Voorwaarde regel voor een datum rubriek. Deze class doet ook de data en periode berekeningen
 *
 */
@Component
public class DatumVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * regex patroon checked of de categorie voorkomt in de lijst. Lijst is afkomstig uit LO 3.8 datrubrieknummer
     */
    private static final String REGEX_PATROON =
            "^(01\\.03\\.10|01\\.82\\.20|01\\.83\\.20|01\\.83\\.30|01\\.85\\.10|01\\.86\\.10|02\\.03\\.10|02\\.62\\.10|"
                    + "02\\.82\\.20|02\\.83\\.20|02\\.83\\.30|02\\.85\\.10|02\\.86\\.10|03\\.03\\.10|03\\.62\\.10|03\\.82\\.20|"
                    + "03\\.83\\.20|03\\.83\\.30|03\\.85\\.10|03\\.86\\.10|04\\.82\\.20|04\\.83\\.20|04\\.83\\.30|04\\.85\\.10|"
                    + "04\\.86\\.10|05\\.03\\.10|05\\.06\\.10|05\\.07\\.10|05\\.82\\.20|05\\.83\\.20|05\\.83\\.30|05\\.85\\.10|"
                    + "05\\.86\\.10|06\\.08\\.10|06\\.82\\.20|06\\.83\\.20|06\\.83\\.30|06\\.85\\.10|06\\.86\\.10|07\\.66\\.20|"
                    + "07\\.67\\.10|07\\.68\\.10|07\\.71\\.10|08\\.09\\.20|08\\.10\\.30|08\\.13\\.20|08\\.14\\.20|08\\.83\\.20|"
                    + "08\\.83\\.30|08\\.85\\.10|08\\.86\\.10|09\\.03\\.10|09\\.82\\.20|09\\.83\\.20|09\\.83\\.30|09\\.85\\.10|"
                    + "09\\.86\\.10|10\\.39\\.20|10\\.39\\.30|10\\.83\\.20|10\\.83\\.30|10\\.85\\.10|10\\.86\\.10|11\\.82\\.20|"
                    + "11\\.83\\.20|11\\.83\\.30|11\\.85\\.10|11\\.86\\.10|12\\.35\\.30|12\\.35\\.50|12\\.35\\.60|12\\.82\\.20|"
                    + "12\\.83\\.20|12\\.83\\.30|12\\.85\\.10|12\\.86\\.10|13\\.31\\.20|13\\.31\\.30|13\\.38\\.20|13\\.82\\.20|"
                    + "14\\.85\\.10|51\\.03\\.10|51\\.82\\.20|51\\.83\\.20|51\\.83\\.30|51\\.85\\.10|51\\.86\\.10|52\\.03\\.10|"
                    + "52\\.62\\.10|52\\.82\\.20|52\\.83\\.20|52\\.83\\.30|52\\.85\\.10|52\\.86\\.10|53\\.03\\.10|53\\.62\\.10|"
                    + "53\\.82\\.20|53\\.83\\.20|53\\.83\\.30|53\\.85\\.10|53\\.86\\.10|54\\.82\\.20|54\\.83\\.20|54\\.83\\.30|"
                    + "54\\.85\\.10|54\\.86\\.10|55\\.03\\.10|55\\.06\\.10|55\\.07\\.10|55\\.82\\.20|55\\.83\\.20|55\\.83\\.30|"
                    + "55\\.85\\.10|55\\.86\\.10|56\\.08\\.10|56\\.82\\.20|56\\.83\\.20|56\\.83\\.30|56\\.85\\.10|56\\.86\\.10|"
                    + "58\\.09\\.20|58\\.10\\.30|58\\.13\\.20|58\\.14\\.20|58\\.83\\.20|58\\.83\\.30|58\\.85\\.10|58\\.86\\.10|"
                    + "59\\.03\\.10|59\\.82\\.20|59\\.83\\.20|59\\.83\\.30|59\\.85\\.10|59\\.86\\.10|60\\.39\\.20|60\\.39\\.30|"
                    + "60\\.83\\.20|60\\.83\\.30|60\\.85\\.10|60\\.86\\.10|61\\.82\\.20|61\\.83\\.20|61\\.83\\.30|61\\.85\\.10|"
                    + "61\\.86\\.10|64\\.85\\.10).*";
    private static final int VOLGORDE = 300;
    private static final int DEEL_DATUM = 0;
    private static final int DEEL_MATOP = 1;
    private static final int DEEL_PERIODE = 2;
    private static final int DATUM_SEPARATOR_INVOEG_POSITIE_1 = 4;
    private static final int DATUM_SEPARATOR_INVOEG_POSITIE_2 = 7;
    private static final int PERIODE_LENGTE_JAAR = 4;
    private static final int PERIODE_LENGTE_JAAR_MAAND = 6;
    private static final String DATUM_SEPARATOR_TEKEN = "/";

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public DatumVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final StringBuilder result = new StringBuilder();
        final String[] delen = voorwaardeRegel.split(SPLIT_CHARACTER, DEEL_AANTAL);
        final boolean periodeBerekening = delen[DEEL_REST].contains("-") || delen[DEEL_REST].contains("+");
        final String[] waardeDelen = delen[DEEL_REST].split(SPLIT_CHARACTER);
        int periodeLengte = -1;
        try {
            if (waardeDelen.length > 1) {
                periodeLengte = waardeDelen[DEEL_PERIODE].length();
            }
            final BrpType[] brpTypen = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(delen[DEEL_RUBRIEK]);
            if (brpTypen.length == 1) {
                verwerkVoorwaarde(brpTypen[0], delen, result, periodeBerekening, periodeLengte);
            } else if (brpTypen.length > 1) {
                result.append('(');
                final StringBuilder voorwaardeBuilder = new StringBuilder();
                for (final BrpType brpType : brpTypen) {
                    voorwaardeBuilder.append('(');
                    verwerkVoorwaarde(brpType, delen, voorwaardeBuilder, periodeBerekening, periodeLengte);
                    voorwaardeBuilder.append(") OF ");
                }
                result.append(voorwaardeBuilder.toString().replaceAll("\\ OF\\ $", ""));
                result.append(')');
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(
                "Formattering voorwaarderegel is ongeldig (index=" + aioobe.getMessage() + ")." + voorwaardeRegel,
                aioobe);
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, groe);
        }

        return result.toString();
    }

    private void verwerkVoorwaarde(
        final BrpType brpType,
        final String[] delen,
        final StringBuilder result,
        final boolean periodeBerekening,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        if (delen[DEEL_OPERATOR].contains("A")) {
            verwerkAlle(result, brpType, delen, periodeBerekening, periodeLengte);
        } else {
            verwerkEen(result, brpType, delen, periodeBerekening, periodeLengte);
        }
    }

    private void verwerkEen(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final boolean periodeBerekening,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        final String operator = Operator.valueOf(delen[DEEL_OPERATOR]).getOperatorString();
        if (brpType.isLijst()) {
            if (periodeBerekening) {
                verwerkEenTrueTrue(result, brpType, delen, operator, periodeLengte);
            } else {
                verwerkEenTrueFalse(result, brpType, delen, operator, periodeLengte);
            }
        } else {
            if (periodeBerekening) {
                verwerkEenFalseTrue(result, brpType, delen, operator, periodeLengte);
            } else {
                verwerkEenFalseFalse(result, brpType, delen, operator, periodeLengte);
            }
        }
    }

    private void verwerkAlle(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final boolean periodeBerekening,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        final String operator = Operator.valueOf(delen[DEEL_OPERATOR]).getOperatorString();
        if (brpType.isLijst()) {
            if (periodeBerekening) {
                verwerkAlleTrueTrue(result, brpType, delen, operator, periodeLengte);
            } else {
                verwerkAlleTrueFalse(result, brpType, delen, operator, periodeLengte);
            }
        } else {
            if (periodeBerekening) {
                verwerkEenFalseTrue(result, brpType, delen, operator, periodeLengte);
            } else {
                verwerkEenFalseFalse(result, brpType, delen, operator, periodeLengte);
            }
        }
    }

    private void verwerkEenTrueTrue(final StringBuilder result, final BrpType brpType, final String[] delen, final String operator, final int periodeLengte)
        throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        switch (periodeLengte) {
            case PERIODE_LENGTE_JAAR:
                result.append(String.format("ER_IS(%1$s, v, JAAR(v) %2$s JAAR(%3$s))", brpType.getType(), operator, maakVoorwaardeOp(delen, periodeLengte)));
                break;
            case PERIODE_LENGTE_JAAR_MAAND:
                switch (operator) {
                    case GROTER:
                    case GROTER_GELIJK:
                        result.append(String.format(
                            "ER_IS(%1$s, v, ((JAAR(v) > JAAR(%3$s)) OF (JAAR(v) = JAAR(%3$s) EN MAAND(v) %2$s MAAND(%3$s))))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                        break;
                    case KLEINER:
                    case KLEINER_GELIJK:
                        result.append(String.format(
                            "ER_IS(%1$s, v, ((JAAR(v) < JAAR(%3$s)) OF (JAAR(v) = JAAR(%3$s) EN MAAND(v) %2$s MAAND(%3$s))))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                        break;
                    default:
                        result.append(String.format(
                            "ER_IS(%1$s, v, (JAAR(v) %2$s JAAR(%3$s)) EN (MAAND(v) %2$s MAAND(%3$s)))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                }
                break;
            default:
                verwerkEenTrueFalse(result, brpType, delen, operator, periodeLengte);
        }
    }

    private void verwerkAlleTrueTrue(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final String operator,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        switch (periodeLengte) {
            case PERIODE_LENGTE_JAAR:
                result.append(String.format("ALLE(%1$s, v, JAAR(v) %2$s JAAR(%3$s))", brpType.getType(), operator, maakVoorwaardeOp(delen, periodeLengte)));
                break;
            case PERIODE_LENGTE_JAAR_MAAND:
                switch (operator) {
                    case GROTER:
                    case GROTER_GELIJK:
                        result.append(String.format(
                            "ALLE(%1$s, v, ((JAAR(v) > JAAR(%3$s)) OF (JAAR(v) = JAAR(%3$s) EN MAAND(v) %2$s MAAND(%3$s))))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                        break;
                    case KLEINER:
                    case KLEINER_GELIJK:
                        result.append(String.format(
                            "ALLE(%1$s, v, ((JAAR(v) < JAAR(%3$s)) OF (JAAR(v) = JAAR(%3$s) EN MAAND(v) %2$s MAAND(%3$s))))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                        break;
                    default:
                        result.append(String.format(
                            "ALLE(%1$s, v, (JAAR(v) %2$s JAAR(%3$s)) EN (MAAND(v) %2$s MAAND(%3$s)))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                }
                break;
            default:
                verwerkAlleTrueFalse(result, brpType, delen, operator, periodeLengte);
        }
    }

    private void verwerkEenFalseTrue(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final String operator,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        switch (periodeLengte) {
            case PERIODE_LENGTE_JAAR:
                result.append(String.format("JAAR(%1$s) %2$s JAAR(%3$s)", brpType.getType(), operator, maakVoorwaardeOp(delen, periodeLengte)));
                break;
            case PERIODE_LENGTE_JAAR_MAAND:
                switch (operator) {
                    case GROTER:
                    case GROTER_GELIJK:
                        result.append(String.format(
                            "((JAAR(%1$s) > JAAR(%3$s)) OF (JAAR(%1$s) = JAAR(%3$s) EN MAAND(%1$s) %2$s MAAND(%3$s)))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                        break;
                    case KLEINER:
                    case KLEINER_GELIJK:
                        result.append(String.format(
                            "((JAAR(%1$s) < JAAR(%3$s)) OF (JAAR(%1$s) = JAAR(%3$s) EN MAAND(%1$s) %2$s MAAND(%3$s)))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                        break;
                    default:
                        result.append(String.format(
                            "(JAAR(%1$s) %2$s JAAR(%3$s)) EN (MAAND(%1$s) %2$s MAAND(%3$s))",
                            brpType.getType(),
                            operator,
                            maakVoorwaardeOp(delen, periodeLengte)));
                }
                break;
            default:
                verwerkEenFalseFalse(result, brpType, delen, operator, periodeLengte);
        }
    }

    /* verwerk EEN vergelijking wel lijst geen periodeberekening. */
    private void verwerkEenTrueFalse(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final String operator,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        result.append(String.format("ER_IS(%1$s, v, v %2$s %3$s)", brpType.getType(), operator, maakVoorwaardeOp(delen, periodeLengte)));
    }

    /* verwerk ALLE vergelijking wel lijst geen periodeberekening. */
    private void verwerkAlleTrueFalse(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final String operator,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        result.append(String.format("ALLE(%1$s, v, v %2$s %3$s)", brpType.getType(), operator, maakVoorwaardeOp(delen, periodeLengte)));
    }

    /* verwerk EEN vergelijking, geen lijst, geen periode berekening. */
    private void verwerkEenFalseFalse(
        final StringBuilder result,
        final BrpType brpType,
        final String[] delen,
        final String operator,
        final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie
    {
        if (delen[DEEL_REST].matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            final BrpType[] brpTypen = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(delen[DEEL_REST]);

            if (brpTypen.length != 1) {
                result.append('(');
                final StringBuilder voorwaardeBuilder = new StringBuilder();
                for (final BrpType restBrpType : brpTypen) {
                    voorwaardeBuilder.append('(');
                    if (restBrpType.isLijst()) {
                        voorwaardeBuilder.append(String.format("ER_IS(%3$s, v, %1$s %2$s v)", brpType.getType(), operator, restBrpType.getType()));
                    } else {
                        voorwaardeBuilder.append(String.format("%1$s %2$s %3$s", brpType.getType(), operator, restBrpType.getType()));
                    }
                    if (operator.endsWith("A")) {
                        voorwaardeBuilder.append(") EN ");
                    } else {
                        voorwaardeBuilder.append(") OF ");
                    }
                }
                result.append(voorwaardeBuilder.toString().replaceAll("\\ (OF|EN)\\ $", ""));
                result.append(')');
            } else {
                final BrpType restBrpType = brpTypen[0];
                if (restBrpType.isLijst()) {
                    result.append(String.format("ER_IS(%3$s, v, %1$s %2$s v)", brpType.getType(), operator, restBrpType.getType()));
                } else {
                    result.append(String.format("%1$s %2$s %3$s", brpType.getType(), operator, restBrpType.getType()));
                }
            }
        } else {
            result.append(String.format("%1$s %2$s %3$s", brpType.getType(), operator, maakVoorwaardeOp(delen, periodeLengte)));
        }
    }

    private String maakVoorwaardeOp(final String[] delen, final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] waardeDelen = delen[DEEL_REST].split(SPLIT_CHARACTER);
        if (waardeDelen.length > 1) {
            final StringBuilder result = new StringBuilder(maakDatumOp(waardeDelen[DEEL_DATUM]));
            result.append(' ').append(waardeDelen[DEEL_MATOP]).append(' ');
            result.append(maakPeriodeOp(periodeLengte, waardeDelen[DEEL_PERIODE]));
            return result.toString();
        } else {
            return maakDatumOp(delen[DEEL_REST]);
        }
    }

    private String maakDatumOp(final String gbaDatum) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final StringBuilder resultBuilder = new StringBuilder();
        if (null != gbaDatum) {
            if (gbaDatum.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                switch (gbaDatum) {
                    case "19.89.30":
                        resultBuilder.append("VANDAAG()");
                        break;
                    case "19.89.20":
                        resultBuilder.append("SELECTIE_DATUM()");
                        break;
                    default:
                        final BrpType[] brpTypen = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(gbaDatum);
                        if (brpTypen.length != 1) {
                            throw new GbaVoorwaardeOnvertaalbaarExceptie(
                                "Een vergelijking met LO3 rubriek die meerdere BRP expressies oplevert wordt niet ondersteund.");
                        } else {
                            final BrpType restBrpType = brpTypen[0];
                            if (restBrpType.isLijst()) {
                                throw new GbaVoorwaardeOnvertaalbaarExceptie(
                                    "Een vergelijking met andere rubriek (die een lijst oplevert) wordt in deze situatie niet ondersteund.");
                            } else {
                                resultBuilder.append(restBrpType.getType());
                            }
                        }
                }
            } else {
                resultBuilder.append(gbaDatum);
                resultBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_1, DATUM_SEPARATOR_TEKEN);
                resultBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_2, DATUM_SEPARATOR_TEKEN);

                // Vervangen 00 (dag) door ?
                if ("00".equals(resultBuilder.substring(DATUM_SEPARATOR_INVOEG_POSITIE_2 + 1, DATUM_SEPARATOR_INVOEG_POSITIE_2 + 3))) {
                    resultBuilder.replace(DATUM_SEPARATOR_INVOEG_POSITIE_2 + 1, DATUM_SEPARATOR_INVOEG_POSITIE_2 + 3, "?");
                }
                // Vervangen 00 (maand) door ?
                if ("00".equals(resultBuilder.substring(DATUM_SEPARATOR_INVOEG_POSITIE_1 + 1, DATUM_SEPARATOR_INVOEG_POSITIE_1 + 3))) {
                    resultBuilder.replace(DATUM_SEPARATOR_INVOEG_POSITIE_1 + 1, DATUM_SEPARATOR_INVOEG_POSITIE_1 + 3, "?");
                }
                // Vervangen 0000 (jaar) door ?
                if ("0000".equals(resultBuilder.substring(0, 4))) {
                    resultBuilder.replace(0, 4, "?");
                }

            }
        }
        return resultBuilder.toString();
    }

    private String maakPeriodeOp(final int periodeLengte, final String gbaPeriode) {
        String periode;
        switch (periodeLengte) {
            case PERIODE_LENGTE_JAAR:
                periode = gbaPeriode + "0000";
                break;
            case PERIODE_LENGTE_JAAR_MAAND:
                periode = gbaPeriode + "00";
                break;
            default:
                periode = gbaPeriode;
        }
        final StringBuilder resultBuilder = new StringBuilder(periode);
        resultBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_1, DATUM_SEPARATOR_TEKEN);
        resultBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_2, DATUM_SEPARATOR_TEKEN);
        resultBuilder.insert(0, "^");
        final String result = resultBuilder.toString();
        return result.replaceAll("^\\^0+", "\\^").replaceAll("^\\^\\/", "\\^0\\/").replaceAll("\\/0+\\/", "\\/0\\/").replaceAll("\\/0+$", "\\/0");

    }
}
