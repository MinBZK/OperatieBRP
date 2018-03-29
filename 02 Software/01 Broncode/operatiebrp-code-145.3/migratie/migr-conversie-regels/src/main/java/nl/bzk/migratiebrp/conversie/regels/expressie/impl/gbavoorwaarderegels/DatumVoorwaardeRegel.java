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
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GroterAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GroterEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GroterGelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GroterGelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KleinerAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KleinerEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KleinerGelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KleinerGelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Operator;
import org.springframework.stereotype.Component;

/**
 * Voorwaarde regel voor een datum rubriek. Deze class doet ook de data en periode berekeningen
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
    private static final int START_OFFSET = 1;
    private static final int EIND_OFFSET = 3;
    private static final int JAAR_LENGTE = 4;
    private static final int DATUM_LENGTE = 8;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public DatumVoorwaardeRegel(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public final Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        final String[] waardeDelen = delen[GbaVoorwaardeConstanten.DEEL_REST].split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);
        try {
            final int periodeLengte = bepaalPeriodeLengte(waardeDelen);
            final BrpType[] brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
            resultaat = maakBrpExpressie(delen, periodeLengte, brpTypen);
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(
                    "Formattering voorwaarderegel is ongeldig (index=" + aioobe.getMessage() + ")." + voorwaardeRegel, aioobe);
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), groe);
        }

        return resultaat;
    }

    private int bepaalPeriodeLengte(final String[] waardeDelen) {
        int periodeLengte = -1;
        if (waardeDelen.length > 1) {
            periodeLengte = waardeDelen[DEEL_PERIODE].length();
        }
        return periodeLengte;
    }

    private Expressie maakBrpExpressie(final String[] delen, final int periodeLengte, final BrpType[] brpTypen)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        if (brpTypen.length == 1) {
            resultaat = verwerkVoorwaarde(brpTypen[0], delen, periodeLengte);
        } else if (brpTypen.length > 1) {
            resultaat = verwerkVoorwaardeMeerdereBrpTypen(delen, periodeLengte, brpTypen);
        } else {
            throw new GbaVoorwaardeOnvertaalbaarExceptie("Geen vertaling LO3 rubriek naar BRP aanwezig");
        }
        return resultaat;
    }

    private Expressie verwerkVoorwaardeMeerdereBrpTypen(final String[] delen, final int periodeLengte, final BrpType[] brpTypen)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final List<Expressie> expressies = new ArrayList<>();
        for (final BrpType brpType : brpTypen) {
            if (brpType.isInverse()) {
                final Expressie vorigeExpressie = expressies.remove(expressies.size() - 1);
                expressies.add(new EnWaarde(vorigeExpressie, new ElementWaarde(new Criterium(brpType.getType(), new KNVOperator(), null))));
            } else {
                expressies.add(verwerkVoorwaarde(brpType, delen, periodeLengte));
            }
        }
        resultaat = new OfWaarde(expressies.toArray(new Expressie[expressies.size()]));
        return resultaat;
    }

    private Expressie verwerkVoorwaarde(final BrpType brpType, final String[] delen, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        if (delen[GbaVoorwaardeConstanten.DEEL_OPERATOR].endsWith("A")) {
            resultaat = verwerkAlle(brpType, delen, periodeLengte);
        } else {
            resultaat = verwerkEen(brpType, delen, periodeLengte);
        }
        return resultaat;
    }

    private Expressie verwerkEen(final BrpType brpType, final String[] delen, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final GBAOperator operator = GBAOperator.valueOf(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);
        if (brpType.isLijst()) {
            resultaat = verwerkEenTrue(brpType, delen, operator, periodeLengte);
        } else {
            resultaat = verwerkEenFalse(brpType, delen, operator, periodeLengte);
        }
        return resultaat;
    }

    private Expressie verwerkAlle(final BrpType brpType, final String[] delen, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final GBAOperator operator = GBAOperator.valueOf(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);
        if (brpType.isLijst()) {
            resultaat = verwerkAlleTrue(brpType, delen, operator, periodeLengte);
        } else {
            resultaat = verwerkEenFalse(brpType, delen, operator, periodeLengte);
        }
        return resultaat;
    }

    /* verwerk EEN vergelijking wel lijst geen periodeberekening. */
    private Expressie verwerkEenTrue(final BrpType brpType, final String[] delen, final GBAOperator operator, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        return new ElementWaarde(
                new Criterium(brpType.getType(), operator.getOperator(), maakVoorwaardeOp(delen[GbaVoorwaardeConstanten.DEEL_REST], periodeLengte), true));
    }

    /* verwerk ALLE vergelijking wel lijst geen periodeberekening. */
    private Expressie verwerkAlleTrue(final BrpType brpType, final String[] delen, final GBAOperator operator, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        return new ElementWaarde(
                new Criterium(brpType.getType(), operator.getOperator(), maakVoorwaardeOp(delen[GbaVoorwaardeConstanten.DEEL_REST], periodeLengte), true));
    }

    /* verwerk EEN vergelijking, geen lijst, geen periode berekening. */
    private Expressie verwerkEenFalse(final BrpType brpType, final String[] delen, final GBAOperator operator, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        if (delen[GbaVoorwaardeConstanten.DEEL_REST].matches("\\d{2}\\.\\d{2}\\.\\d{2}.*")) {
            resultaat = verwerkEenFalseRubrieken(brpType, delen, operator, periodeLengte);
        } else {
            resultaat = verwerkEenFalseWaarde(brpType, delen, operator, periodeLengte);
        }
        return resultaat;
    }

    private Expressie verwerkEenFalseRubrieken(final BrpType brpType, final String[] delen, final GBAOperator operator,
                                               final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final BrpType[] brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_REST].split(
                GbaVoorwaardeConstanten.SPLIT_CHARACTER)[0]);

        if (brpTypen.length != 1) {
            final List<Expressie> expressies = new ArrayList<>();
            for (final BrpType restBrpType : brpTypen) {
                expressies.add(bouwDeelExpressieOp(brpType, delen, operator, periodeLengte, restBrpType));
            }
            if (operator.name().endsWith("A")) {
                resultaat = new EnWaarde(expressies.toArray(new Expressie[expressies.size()]));
            } else {
                resultaat = new OfWaarde(expressies.toArray(new Expressie[expressies.size()]));
            }
        } else {
            final BrpType restBrpType = brpTypen[0];
            resultaat = bouwDeelExpressieOp(brpType, delen, operator, periodeLengte, restBrpType);
        }
        return resultaat;
    }

    private Expressie verwerkEenFalseWaarde(final BrpType brpType, final String[] delen, final GBAOperator operator,
                                            final int periodeLengte) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        return new ElementWaarde(new Criterium(brpType.getType(), operator.getOperator(),
                maakVoorwaardeOp(delen[GbaVoorwaardeConstanten.DEEL_REST], periodeLengte), true));
    }

    private Expressie bouwDeelExpressieOp(final BrpType brpType, final String[] delen, final GBAOperator operator, final int periodeLengte, final BrpType
            restBrpType) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie expressie;
        if (restBrpType.isLijst()) {
            expressie =
                    new ElementWaarde(
                            new Criterium(
                                    maakVoorwaardeOp(delen[GbaVoorwaardeConstanten.DEEL_REST], periodeLengte, restBrpType.getType()),
                                    operator.getInverseOperator(),
                                    brpType.getType()));
        } else {
            expressie =
                    new ElementWaarde(
                            new Criterium(
                                    brpType.getType(),
                                    operator.getOperator(),
                                    maakVoorwaardeOp(delen[GbaVoorwaardeConstanten.DEEL_REST], periodeLengte, restBrpType.getType()), true));
        }
        return expressie;
    }

    private String maakVoorwaardeOp(final String waardedeel, final int periodeLengte)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        return maakVoorwaardeOp(waardedeel, periodeLengte, null);
    }

    private String maakVoorwaardeOp(final String waardedeel, final int periodeLengte, final String brpType)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final String resultaat;
        final String[] waardeDelen = waardedeel.split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);
        if (waardeDelen.length > 1) {
            final StringBuilder result = new StringBuilder();
            if (brpType == null) {
                result.append(maakDatumOp(waardeDelen[DEEL_DATUM]));
            } else {
                result.append(brpType);
            }
            result.append(' ').append(waardeDelen[DEEL_MATOP]).append(' ');
            result.append(maakPeriodeOp(periodeLengte, waardeDelen[DEEL_PERIODE]));
            resultaat = result.toString();
        } else {
            if (brpType == null) {
                resultaat = maakDatumOp(waardedeel);
            } else {
                resultaat = brpType;
            }
        }
        return resultaat;
    }

    private String maakDatumOp(final String gbaDatum) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final StringBuilder resultBuilder = new StringBuilder();
        if (null != gbaDatum) {
            if (gbaDatum.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                maakDatumOpRubrieken(gbaDatum, resultBuilder);
            } else {
                maakDatumOpWaarde(gbaDatum, resultBuilder);
            }
        }
        return resultBuilder.toString();
    }

    private void maakDatumOpRubrieken(final String gbaDatum, final StringBuilder resultBuilder)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        switch (gbaDatum) {
            case "19.89.30":
                resultBuilder.append("VANDAAG()");
                break;
            case "19.89.20":
                resultBuilder.append("SELECTIE_DATUM()");
                break;
            default:
                maakDatumRubriekenStandaard(gbaDatum, resultBuilder);
        }
    }

    private void maakDatumRubriekenStandaard(final String gbaDatum, final StringBuilder resultBuilder)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType[] brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(gbaDatum);
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

    private void maakDatumOpWaarde(final String gbaDatum, final StringBuilder resultBuilder) {
        if (gbaDatum.startsWith("\\")) {
            resultBuilder.append("\\");
        }
        final StringBuilder datumBuilder = new StringBuilder();
        final String schoneGbaDatum = gbaDatum.replaceAll("^\\\\", "").replaceAll("\\*$", "");
        if ("00000000".equals(schoneGbaDatum)) {
            datumBuilder.append("?/?/0");
        } else if (DATUM_LENGTE > schoneGbaDatum.length()) {
            datumBuilder.append(schoneGbaDatum);
            if (datumBuilder.length() > DATUM_SEPARATOR_INVOEG_POSITIE_1) {
                datumBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_1, DATUM_SEPARATOR_TEKEN);
            }
            if (datumBuilder.length() > DATUM_SEPARATOR_INVOEG_POSITIE_2) {
                datumBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_2, DATUM_SEPARATOR_TEKEN);
            }
        } else {
            datumBuilder.append(schoneGbaDatum);
            datumBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_1, DATUM_SEPARATOR_TEKEN);
            datumBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_2, DATUM_SEPARATOR_TEKEN);

            // Vervangen 00 (dag) door ?
            if ("00".equals(datumBuilder.substring(DATUM_SEPARATOR_INVOEG_POSITIE_2 + START_OFFSET, DATUM_SEPARATOR_INVOEG_POSITIE_2 + EIND_OFFSET))) {
                datumBuilder.replace(DATUM_SEPARATOR_INVOEG_POSITIE_2 + START_OFFSET, DATUM_SEPARATOR_INVOEG_POSITIE_2 + EIND_OFFSET, "?");
            }
            // Vervangen 00 (maand) door ?
            if ("00".equals(datumBuilder.substring(DATUM_SEPARATOR_INVOEG_POSITIE_1 + START_OFFSET, DATUM_SEPARATOR_INVOEG_POSITIE_1 + EIND_OFFSET))) {
                datumBuilder.replace(DATUM_SEPARATOR_INVOEG_POSITIE_1 + START_OFFSET, DATUM_SEPARATOR_INVOEG_POSITIE_1 + EIND_OFFSET, "?");
            }
            // Vervangen 0000 (jaar) door ?
            if ("0000".equals(datumBuilder.substring(0, JAAR_LENGTE))) {
                datumBuilder.replace(0, JAAR_LENGTE, "?");
            }
        }
        resultBuilder.append(datumBuilder.toString());
        if (gbaDatum.endsWith("*")) {
            resultBuilder.append("*");
        }
    }

    private String maakPeriodeOp(final int periodeLengte, final String gbaPeriode) {
        final String periode;
        switch (periodeLengte) {
            case PERIODE_LENGTE_JAAR:
                periode = gbaPeriode + "????";
                break;
            case PERIODE_LENGTE_JAAR_MAAND:
                periode = gbaPeriode + "??";
                break;
            default:
                periode = gbaPeriode;
        }
        final StringBuilder resultBuilder = new StringBuilder(periode);
        resultBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_1, DATUM_SEPARATOR_TEKEN);
        resultBuilder.insert(DATUM_SEPARATOR_INVOEG_POSITIE_2, DATUM_SEPARATOR_TEKEN);
        resultBuilder.insert(0, "^");
        final String result = resultBuilder.toString();
        return result.replaceAll("0+([0-9])", "$1").replaceAll("\\?\\?", "\\?");
    }

    /**
     * GBAOperator enum voor gebruik binnen de voorwaarde subclassen.
     */
    protected enum GBAOperator {
        /**
         * Gelijk aan 1 operator.
         */
        GA1(new GelijkEenOperator()),
        /**
         * Ongelijka aan 1 operator.
         */
        OGA1(new OngelijkEenOperator()),
        /**
         * Ongelijk aan alle operator.
         */
        GAA(new GelijkAlleOperator()),
        /**
         * Ongelijk aan alle operator.
         */
        OGAA(new OngelijkAlleOperator()),
        /**
         * Groter dan 1 operator.
         */
        GD1(new GroterEenOperator(), new KleinerEenOperator()),
        /**
         * kleiner dan 1 operator.
         */
        KD1(new KleinerEenOperator(), new GroterEenOperator()),
        /**
         * Groter dan of gelijk aan 1 operator.
         */
        GDOG1(new GroterGelijkEenOperator(), new KleinerGelijkEenOperator()),
        /**
         * Kleiner dan of gelijk aan 1 operator.
         */
        KDOG1(new KleinerGelijkEenOperator(), new GroterGelijkEenOperator()),
        /**
         * Groter dan alle operator.
         */
        GDA(new GroterAlleOperator(), new KleinerAlleOperator()),
        /**
         * kleiner dan all operator.
         */
        KDA(new KleinerAlleOperator(), new GroterAlleOperator()),
        /**
         * Groter dan of gelijk aan alle operator.
         */
        GDOGA(new GroterGelijkAlleOperator(), new KleinerGelijkAlleOperator()),
        /**
         * Kleiner dan of gelijk aan alle operator.
         */
        KDOGA(new KleinerGelijkAlleOperator(), new GroterGelijkAlleOperator());

        private final Operator brpOperator;
        private final Operator inverseOperator;

        /**
         * Constructor.
         * @param operator De brp operator
         */
        GBAOperator(final Operator operator) {
            this.brpOperator = operator;
            this.inverseOperator = operator;
        }

        /**
         * Constructor.
         * @param operator De brp operator
         * @param inverseOperator inverse operator
         */
        GBAOperator(final Operator operator, final Operator inverseOperator) {
            this.brpOperator = operator;
            this.inverseOperator = inverseOperator;
        }

        /**
         * Geeft de waarde van de operator.
         * @return de operator voor de BRP expressie
         */
        public Operator getOperator() {
            return brpOperator;
        }

        /**
         * Geeft de waarde van de inverse operator.
         * @return de operator voor de BRP expressie
         */
        public Operator getInverseOperator() {
            return inverseOperator;
        }
    }
}
