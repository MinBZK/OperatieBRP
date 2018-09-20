/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

// Generated from GBAVoorwaarderegel.g4 by ANTLR 4.1

package nl.bzk.brp.expressietaal.parser.antlr.gba;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GBAVoorwaarderegelParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GBAVoorwaarderegelVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#vandaagvergelijking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVandaagvergelijking(@NotNull GBAVoorwaarderegelParser.VandaagvergelijkingContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#periodeJaar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodeJaar(@NotNull GBAVoorwaarderegelParser.PeriodeJaarContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#vandaagdatum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVandaagdatum(@NotNull GBAVoorwaarderegelParser.VandaagdatumContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#relop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelop(@NotNull GBAVoorwaarderegelParser.RelopContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#datum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatum(@NotNull GBAVoorwaarderegelParser.DatumContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#periodeMaand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodeMaand(@NotNull GBAVoorwaarderegelParser.PeriodeMaandContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#numrubrieknummer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumrubrieknummer(@NotNull GBAVoorwaarderegelParser.NumrubrieknummerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#numrubriekwaarde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumrubriekwaarde(@NotNull GBAVoorwaarderegelParser.NumrubriekwaardeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#matop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatop(@NotNull GBAVoorwaarderegelParser.MatopContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#haakjes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHaakjes(@NotNull GBAVoorwaarderegelParser.HaakjesContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#getal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetal(@NotNull GBAVoorwaarderegelParser.GetalContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#periode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriode(@NotNull GBAVoorwaarderegelParser.PeriodeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#tekst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTekst(@NotNull GBAVoorwaarderegelParser.TekstContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#datrubrieknummer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatrubrieknummer(@NotNull GBAVoorwaarderegelParser.DatrubrieknummerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#voorkomenvraag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoorkomenvraag(@NotNull GBAVoorwaarderegelParser.VoorkomenvraagContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#datVergelijking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatVergelijking(@NotNull GBAVoorwaarderegelParser.DatVergelijkingContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#voorwaarde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoorwaarde(@NotNull GBAVoorwaarderegelParser.VoorwaardeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#selDatrubrieknummer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelDatrubrieknummer(@NotNull GBAVoorwaarderegelParser.SelDatrubrieknummerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#datrubriekwaarde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatrubriekwaarde(@NotNull GBAVoorwaarderegelParser.DatrubriekwaardeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#vrkop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVrkop(@NotNull GBAVoorwaarderegelParser.VrkopContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#alfanumVergelijking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlfanumVergelijking(@NotNull GBAVoorwaarderegelParser.AlfanumVergelijkingContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#alfanumrubriekwaarde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlfanumrubriekwaarde(@NotNull GBAVoorwaarderegelParser.AlfanumrubriekwaardeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#numVergelijking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumVergelijking(@NotNull GBAVoorwaarderegelParser.NumVergelijkingContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#alfanumrubriekterm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlfanumrubriekterm(@NotNull GBAVoorwaarderegelParser.AlfanumrubriektermContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#rubrieknummer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRubrieknummer(@NotNull GBAVoorwaarderegelParser.RubrieknummerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#periodeDag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodeDag(@NotNull GBAVoorwaarderegelParser.PeriodeDagContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#selectievergelijking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectievergelijking(@NotNull GBAVoorwaarderegelParser.SelectievergelijkingContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#vergelijking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVergelijking(@NotNull GBAVoorwaarderegelParser.VergelijkingContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#selectiedatum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectiedatum(@NotNull GBAVoorwaarderegelParser.SelectiedatumContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#txtop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTxtop(@NotNull GBAVoorwaarderegelParser.TxtopContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#logopvgl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogopvgl(@NotNull GBAVoorwaarderegelParser.LogopvglContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(@NotNull GBAVoorwaarderegelParser.TermContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#logopvwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogopvwd(@NotNull GBAVoorwaarderegelParser.LogopvwdContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#selDatrubriekwaarde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelDatrubriekwaarde(@NotNull GBAVoorwaarderegelParser.SelDatrubriekwaardeContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#alfanumrubrieknummer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlfanumrubrieknummer(@NotNull GBAVoorwaarderegelParser.AlfanumrubrieknummerContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(@NotNull GBAVoorwaarderegelParser.StringContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#numrubriekterm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumrubriekterm(@NotNull GBAVoorwaarderegelParser.NumrubriektermContext ctx);

	/**
	 * Visit a parse tree produced by {@link GBAVoorwaarderegelParser#datumconstante}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatumconstante(@NotNull GBAVoorwaarderegelParser.DatumconstanteContext ctx);
}