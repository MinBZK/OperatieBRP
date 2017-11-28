/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Datumdeel;
import nl.bzk.brp.domain.expressie.DatumtijdLiteral;
import nl.bzk.brp.domain.expressie.PeriodeLiteral;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;

/**
 * Parser util tbv van datum en datumtijd waarden.
 */
final class DatumParserUtil {

    private static final int MIN_TIJDWAARDE = 0;
    private static final int MAX_SECONDE_MINUUT = 59;
    private static final int MAX_UUR = 23;
    /**
     * Minimum jaartal dat nog als correct beschouwd wordt.
     */
    private static final int MINIMUM_JAARTAL = 1800;
    /**
     * Maximum jaartal dat nog als correct beschouwd wordt.
     */
    private static final int MAXIMUM_JAARTAL = 3000;

    /**
     * Private constructor voor utility class.
     */
    private DatumParserUtil() {
    }

    /**
     * Maakt een {@link PeriodeLiteral} obv de parseerdata.
     *
     * @param ctx De date literal context
     * @return Datumexpressie.
     */
    static Expressie visitPeriodLiteral(final BRPExpressietaalParser.PeriodLiteralContext ctx) {
        final Function<String, Datumdeel> periodeFuctie = s -> DatumLiteral.ONBEKEND_DATUMDEEL_STRING.equals(s)
                ? Datumdeel.ONBEKEND_DATUMDEEL
                : Datumdeel.valueOf(Integer.parseInt(s));
        final Datumdeel jaar = periodeFuctie.apply(ctx.relativeYear().getText());
        final Datumdeel maand = periodeFuctie.apply(ctx.relativeMonth().getText());
        final Datumdeel dag = periodeFuctie.apply(ctx.relativeDay().getText());
        return new PeriodeLiteral(jaar, maand, dag);
    }

    /**
     * Maakt een {@link DatumLiteral} obv de parseerdata.
     *
     * @param ctx De date literal context
     * @return Datumexpressie.
     */
    static Expressie visitDateLiteral(final BRPExpressietaalParser.DateLiteralContext ctx) {
        final Datumdeel jaar = getJaar(ctx.year());
        final Datumdeel maand = getMaand(ctx.month());
        final Datumdeel dag = getDag(ctx.day(), jaar, maand);
        return new DatumLiteral(jaar, maand, dag);
    }

    /**
     * Maakt een {@link DatumtijdLiteral} obv de parseerdata.
     *
     * @param ctx datum tijd context
     * @return Datumtijd expressie
     */
    static Expressie visitDateTimeLiteral(final BRPExpressietaalParser.DateTimeLiteralContext ctx) {
        final Datumdeel jaar = getJaar(ctx.year());
        final Datumdeel maand = getMaand(ctx.month());
        final Datumdeel dag = getDag(ctx.day(), jaar, maand);
        final int uur = getUur(ctx.hour());
        final int minuut = getMinuut(ctx.minute());
        final int seconde = getSeconde(ctx.second());
        return new DatumtijdLiteral(jaar, maand, dag, uur, minuut, seconde);
    }


    private static Datumdeel getJaar(final BRPExpressietaalParser.YearContext jaarCtx) {
        final Datumdeel datumdeel;
        if (DatumLiteral.ONBEKEND_DATUMDEEL_STRING.equals(jaarCtx.getText())) {
            datumdeel = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            final int jaarWaarde = Integer.parseInt(jaarCtx.getText());
            if (jaarWaarde != 0 && !(jaarWaarde >= MINIMUM_JAARTAL
                    && jaarWaarde < MAXIMUM_JAARTAL)) {
                throw new ExpressieParseException("Jaar incorrect: " + jaarWaarde);
            }
            datumdeel = Datumdeel.valueOf(jaarWaarde);
        }
        return datumdeel;
    }

    private static Datumdeel getMaand(final BRPExpressietaalParser.MonthContext maandCtx) {
        final Datumdeel datumdeel;
        if (DatumLiteral.ONBEKEND_DATUMDEEL_STRING.equals(maandCtx.getText())) {
            datumdeel = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            final int maandWaarde;
            if (maandCtx.monthName() != null) {
                maandWaarde = MaandnummerParserUtil.maandnaamNaarMaandnummer(maandCtx.monthName().getText());
            } else {
                maandWaarde = Integer.parseInt(maandCtx.getText());
                if (maandWaarde != 0 && !(maandWaarde >= Month.JANUARY.getValue() && maandWaarde <= Month.DECEMBER.getValue())) {
                    throw new ExpressieParseException("Maand incorrect: " + maandWaarde);
                }
            }
            datumdeel = Datumdeel.valueOf(maandWaarde);
        }
        return datumdeel;
    }

    private static Datumdeel getDag(final BRPExpressietaalParser.DayContext dagCtx, final Datumdeel jaar,
                                    final Datumdeel maand) {
        final Datumdeel datumdeel;
        if (DatumLiteral.ONBEKEND_DATUMDEEL_STRING.equals(dagCtx.getText())) {
            datumdeel = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            final int dagWaarde = Integer.parseInt(dagCtx.getText());

            if (dagWaarde != 0) {
                final BooleanSupplier geldigBinnenRange = () ->
                        ChronoField.DAY_OF_MONTH.range().isValidIntValue(dagWaarde);
                final BooleanSupplier jaarEnMaandBekend = () ->
                        jaar != Datumdeel.ONBEKEND_DATUMDEEL && jaar.getWaarde() != 0
                                && maand != Datumdeel.ONBEKEND_DATUMDEEL && maand.getWaarde() != 0;
                final BooleanSupplier geldigBinnenMaandRange = () ->
                        DatumLiteral.dagenInMaand(jaar.getWaarde(), maand.getWaarde()) >= dagWaarde;
                if (!geldigBinnenRange.getAsBoolean() || jaarEnMaandBekend.getAsBoolean() && !geldigBinnenMaandRange.getAsBoolean()) {
                    throw new ExpressieParseException("Dag incorrect: " + dagWaarde);
                }
            }
            datumdeel = Datumdeel.valueOf(dagWaarde);
        }
        return datumdeel;
    }

    private static int getUur(final BRPExpressietaalParser.HourContext uurCtx) {
        final int uurWaarde = Integer.parseInt(uurCtx.getText());
        if (uurWaarde < MIN_TIJDWAARDE || uurWaarde > MAX_UUR) {
            throw new ExpressieParseException("Uur incorrect: " + uurWaarde);
        }
        return uurWaarde;
    }


    private static int getMinuut(final BRPExpressietaalParser.MinuteContext minuutContext) {
        final int minuutwaarde = Integer.parseInt(minuutContext.getText());
        if (minuutwaarde < MIN_TIJDWAARDE || minuutwaarde > MAX_SECONDE_MINUUT) {
            throw new ExpressieParseException("Minuut incorrect: " + minuutwaarde);
        }
        return minuutwaarde;
    }

    private static int getSeconde(final BRPExpressietaalParser.SecondContext secondeContext) {
        final int secondeWaarde = Integer.parseInt(secondeContext.getText());
        if (secondeWaarde < MIN_TIJDWAARDE || secondeWaarde > MAX_SECONDE_MINUUT) {
            throw new ExpressieParseException("Seconde incorrect: " + secondeWaarde);
        }
        return secondeWaarde;
    }
}
