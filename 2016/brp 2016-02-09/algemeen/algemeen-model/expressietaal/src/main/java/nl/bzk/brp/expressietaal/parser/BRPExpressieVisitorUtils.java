/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.literals.DateTimeLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.Datumdeel;
import nl.bzk.brp.expressietaal.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.expressietaal.util.DatumUtils;

/**
 * Utilities voor de BRPExpressieVisitor.
 */
public final class BRPExpressieVisitorUtils {

    private static final int AANTAL_MAANDEN = 12;

    private static final int MIN_TIJDWAARDE = 0;
    private static final int MAX_SECONDE_MINUUT    = 59;
    private static final int MAX_UUR    = 23;

    /**
     * Private constructor voor utility class.
     */
    private BRPExpressieVisitorUtils() {

    }

    /**
     * Maakt een datumexpressie op basis van tags uit de grammatica.
     *
     * @param jaarCtx       Jaar-tag.
     * @param maandCtx      Maand-tag.
     * @param dagCtx        Dag-tag.
     * @param errorListener Error listener voor het melden van fouten.
     * @return Datumexpressie.
     */
    public static Expressie maakDatumExpressie(final BRPExpressietaalParser.YearContext jaarCtx,
        final BRPExpressietaalParser.MonthContext maandCtx,
        final BRPExpressietaalParser.DayContext dagCtx,
        final BRPExpressietaalErrorListener errorListener)
    {
        final Datumdeel jaar = getJaar(jaarCtx, errorListener);
        final Datumdeel maand = getMaand(maandCtx, jaar, errorListener);
        final Datumdeel dag = getDag(dagCtx, jaar, maand, errorListener);
        return new DatumLiteralExpressie(jaar, maand, dag);
    }

    /**
     * @param ctx datum tijd context
     * @param errorListener Error listener voor het melden van fouten.
     * @return Datumtijd expressie
     */
    public static Expressie maakDatumTijdExpressie(final BRPExpressietaalParser.DateTimeLiteralContext ctx,
        final BRPExpressietaalErrorListener errorListener)
    {

        final Datumdeel jaar = getJaar(ctx.year(), errorListener);
        final Datumdeel maand = getMaand(ctx.month(), jaar, errorListener);
        final Datumdeel dag = getDag(ctx.day(), jaar, maand, errorListener);
        final Datumdeel uur = getUur(ctx.hour(), errorListener);
        final Datumdeel minuut = getMinuut(ctx.minute(), errorListener);
        final Datumdeel seconde = getSeconde(ctx.second(), errorListener);
        return new DateTimeLiteralExpressie(jaar, maand, dag, uur, minuut, seconde);
    }


    private static Datumdeel getJaar(final BRPExpressietaalParser.YearContext jaarCtx, final BRPExpressietaalErrorListener errorListener) {
        if (jaarCtx == null) {
            return Datumdeel.ONBEKEND_DATUMDEEL;
        }
        final int jaarWaarde = Integer.parseInt(jaarCtx.getText());
        final Datumdeel jaar;

        if (jaarWaarde >= DatumLiteralExpressie.MINIMUM_JAARTAL
            && jaarWaarde < DatumLiteralExpressie.MAXIMUM_JAARTAL)
        {
            jaar = new Datumdeel(jaarWaarde);
        } else {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_JAARTAL, jaarCtx.getText(),
                    jaarCtx.getStart().getStartIndex()));
            jaar = Datumdeel.ONBEKEND_DATUMDEEL;
        }
        return jaar;
    }


    private static Datumdeel getMaand(final BRPExpressietaalParser.MonthContext maandCtx, final Datumdeel jaar,
        final BRPExpressietaalErrorListener errorListener)
    {
        if (jaar == Datumdeel.ONBEKEND_DATUMDEEL || maandCtx == null) {
            return Datumdeel.ONBEKEND_DATUMDEEL;
        }
        final Datumdeel maand;
        final int maandWaarde;
        if (maandCtx.monthName() != null) {
            final Keyword k = DefaultKeywordMapping.zoekKeyword(maandCtx.monthName().getText());
            if (k != null) {
                maandWaarde = ParserUtils.maandnaamNaarMaandnummer(k);
            } else {
                maandWaarde = 0;
            }
        } else {
            maandWaarde = Integer.parseInt(maandCtx.getText());
        }
        if (maandWaarde >= 1 && maandWaarde <= AANTAL_MAANDEN) {
            maand = new Datumdeel(maandWaarde);
        } else {
            maand = Datumdeel.ONBEKEND_DATUMDEEL;
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECTE_MAAND, maandCtx.getText(),
                    maandCtx.getStart().getStartIndex()));
        }
        return maand;
    }

    private static Datumdeel getDag(final BRPExpressietaalParser.DayContext dagCtx, final Datumdeel jaar,
        final Datumdeel maand, final BRPExpressietaalErrorListener errorListener)
    {

        if (jaar == Datumdeel.ONBEKEND_DATUMDEEL || jaar.getWaarde() == 0 || maand.getWaarde() == 0 || dagCtx == null) {
            return Datumdeel.ONBEKEND_DATUMDEEL;
        }
        final int dagWaarde = Integer.parseInt(dagCtx.getText());
        final Datumdeel dag;
        if (dagWaarde < 1 || dagWaarde > DatumUtils.dagenInMaand(jaar.getWaarde(),
            maand.getWaarde()))
        {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_DAGNUMMER,
                    dagCtx.getText(),
                    dagCtx.getStart().getStartIndex()));
            dag = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            dag = new Datumdeel(dagWaarde);
        }
        return dag;
    }


    private static Datumdeel getUur(final BRPExpressietaalParser.HourContext uurCtx, final BRPExpressietaalErrorListener errorListener) {

        if (uurCtx == null) {
            return  Datumdeel.ONBEKEND_DATUMDEEL;
        }
        final int uurWaarde = Integer.parseInt(uurCtx.getText());
        final Datumdeel uur;
        if (uurWaarde < MIN_TIJDWAARDE || uurWaarde > MAX_UUR) {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_UURNUMMER,
                    uurCtx.getText(),
                    uurCtx.getStart().getStartIndex()));
            uur = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            uur = new Datumdeel(uurWaarde);
        }
        return uur;
    }


    private static Datumdeel getMinuut(final BRPExpressietaalParser.MinuteContext minuutContext, final BRPExpressietaalErrorListener errorListener) {

        if (minuutContext == null) {
            return  Datumdeel.ONBEKEND_DATUMDEEL;
        }
        final int uurWaarde = Integer.parseInt(minuutContext.getText());
        final Datumdeel uur;
        if (uurWaarde < MIN_TIJDWAARDE || uurWaarde > MAX_SECONDE_MINUUT) {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_MINUUTNUMMER,
                    minuutContext.getText(),
                    minuutContext.getStart().getStartIndex()));
            uur = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            uur = new Datumdeel(uurWaarde);
        }
        return uur;
    }

    private static Datumdeel getSeconde(final BRPExpressietaalParser.SecondContext secondeContext, final BRPExpressietaalErrorListener errorListener) {
        if (secondeContext == null) {
            return  Datumdeel.ONBEKEND_DATUMDEEL;
        }
        final int uurWaarde = Integer.parseInt(secondeContext.getText());
        final Datumdeel uur;
        if (uurWaarde < MIN_TIJDWAARDE || uurWaarde > MAX_SECONDE_MINUUT) {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_SECONDENUMMER,
                    secondeContext.getText(),
                    secondeContext.getStart().getStartIndex()));
            uur = Datumdeel.ONBEKEND_DATUMDEEL;
        } else {
            uur = new Datumdeel(uurWaarde);
        }
        return uur;
    }

}
