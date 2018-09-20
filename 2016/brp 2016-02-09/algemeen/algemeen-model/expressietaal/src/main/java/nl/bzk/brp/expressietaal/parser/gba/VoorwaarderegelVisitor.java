/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.gba;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.AttribuutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.FunctieExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.PeriodeLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeEnExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeNietExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeOfExpressie;
import nl.bzk.brp.expressietaal.parser.antlr.gba.GBAVoorwaarderegelBaseVisitor;
import nl.bzk.brp.expressietaal.parser.antlr.gba.GBAVoorwaarderegelParser;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;

/**
 * Visitor voor parse trees van GBA-voorwaarderegels.
 */
public class VoorwaarderegelVisitor extends GBAVoorwaarderegelBaseVisitor<Expressie> {

    /**
     * GBA-rubrieknummer voor afnemerindicatie.
     */
    public static final String GBA_RUBRIEK_AFNEMERINDICATIE = "14.40.10";
    /**
     * GBA-rubrieknummer voor indicatie geheim.
     */
    public static final String GBA_RUBRIEK_INDICATIE_GEHEIM = "07.70.10";

    @Override
    public final Expressie visitNumrubrieknummer(final GBAVoorwaarderegelParser.NumrubrieknummerContext ctx) {
        final ExpressieAttribuut attr = GbaRubrieken.getNumRubriek(ctx.getText());
        Expressie expressie;
        if (attr != null) {
            expressie = new AttribuutExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT, attr);
        } else {
            expressie = NullValue.getInstance();
        }
        return expressie;
    }

    @Override
    public final Expressie visitAlfanumrubrieknummer(final GBAVoorwaarderegelParser.AlfanumrubrieknummerContext ctx) {
        final ExpressieAttribuut attr = GbaRubrieken.getAlfanumRubriek(ctx.getText());
        Expressie expressie;
        if (attr != null) {
            expressie = new AttribuutExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT, attr);
        } else {
            expressie = NullValue.getInstance();
        }
        return expressie;
    }

    @Override
    public final Expressie visitDatrubrieknummer(final GBAVoorwaarderegelParser.DatrubrieknummerContext ctx) {
        final ExpressieAttribuut attr = GbaRubrieken.getDatRubriek(ctx.getText());
        Expressie expressie;
        if (attr != null) {
            expressie = new AttribuutExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT, attr);
        } else {
            expressie = NullValue.getInstance();
        }
        return expressie;
    }

    @Override
    public final Expressie visitGetal(final GBAVoorwaarderegelParser.GetalContext ctx) {
        final int value = Integer.parseInt(ctx.getText());
        return new GetalLiteralExpressie(value);
    }

    @Override
    public final Expressie visitTekst(final GBAVoorwaarderegelParser.TekstContext ctx) {
        final String value;
        if (ctx.WILDCARD() != null) {
            value = ctx.string().getText() + "*";
        } else {
            value = ctx.string().getText();
        }
        return new StringLiteralExpressie(value);
    }

    @Override
    public final Expressie visitDatumconstante(final GBAVoorwaarderegelParser.DatumconstanteContext ctx) {
        final int datum = Integer.parseInt(ctx.getText());
        return new DatumLiteralExpressie(datum);
    }

    @Override
    public final Expressie visitSelectiedatum(final GBAVoorwaarderegelParser.SelectiedatumContext ctx) {
        return FunctieExpressie.maakFunctieaanroep(Keyword.VANDAAG);
    }

    @Override
    public final Expressie visitVandaagdatum(final GBAVoorwaarderegelParser.VandaagdatumContext ctx) {
        return FunctieExpressie.maakFunctieaanroep(Keyword.VANDAAG);
    }

    @Override
    public final Expressie visitPeriodeJaar(final GBAVoorwaarderegelParser.PeriodeJaarContext ctx) {
        return new PeriodeLiteralExpressie(Integer.parseInt(ctx.getText()) * DatumLiteralExpressie.JAARFACTOR);
    }

    @Override
    public final Expressie visitPeriodeMaand(final GBAVoorwaarderegelParser.PeriodeMaandContext ctx) {
        return new PeriodeLiteralExpressie(Integer.parseInt(ctx.getText()) * DatumLiteralExpressie.MAANDFACTOR);
    }

    @Override
    public final Expressie visitPeriodeDag(final GBAVoorwaarderegelParser.PeriodeDagContext ctx) {
        return new PeriodeLiteralExpressie(Integer.parseInt(ctx.getText()));
    }

    @Override
    public final Expressie visitNumrubriekwaarde(final GBAVoorwaarderegelParser.NumrubriekwaardeContext ctx) {
        final Expressie expressie;
        if (ctx.numrubriekterm().size() == 1) {
            expressie = visit(ctx.numrubriekterm(0));
        } else if (ctx.numrubriekterm().size() > 1) {
            final List<Expressie> waarden = new ArrayList<Expressie>();
            for (int i = 0; i < ctx.numrubriekterm().size(); i++) {
                waarden.add(visit(ctx.numrubriekterm(i)));
            }
            expressie = new LijstExpressie(waarden);
        } else {
            expressie = NullValue.getInstance();
        }
        return expressie;
    }

    @Override
    public final Expressie visitNumVergelijking(final GBAVoorwaarderegelParser.NumVergelijkingContext ctx) {
        Expressie expressie;
        final Expressie rubriek = visit(ctx.numrubrieknummer());
        final TxtOp txtOp = GbaOperatoren.findTxtOp(ctx.txtop().getText());
        final LogOpVgl logOpVgl = VergelijkingBouwer.bepaalDefaultLogOpVgl(txtOp);
        final Expressie waarde;
        if (ctx.numrubriekwaarde() != null) {
            waarde = visit(ctx.numrubriekwaarde());
        } else {
            waarde = visit(ctx.alfanumrubrieknummer());
        }

        if (ctx.numrubrieknummer().getText().equals(GBA_RUBRIEK_INDICATIE_GEHEIM)) {
            // In principe geldt dit alleen voor een vergelijking van de GBA-indicatie 'geheim' met de waarden 2, 4, 6
            // 7. Andere combinaties komen in de GBA-voorwaarderegels niet voor.

            expressie =
                new AttribuutExpressie(
                    VergelijkingBouwer.DEFAULT_LOKALE_VARIABELE,
                    ExpressieAttribuut.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING);

            if (txtOp == TxtOp.OGAA || txtOp == TxtOp.OGA1) {
                expressie = new LogischeNietExpressie(expressie);
            }
        } else if (ctx.numrubrieknummer().getText().equals(GBA_RUBRIEK_AFNEMERINDICATIE)) {
            expressie = BooleanLiteralExpressie.WAAR;
        } else {
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, txtOp, waarde, logOpVgl);
        }
        return expressie;
    }

    @Override
    public final Expressie visitAlfanumrubriekwaarde(final GBAVoorwaarderegelParser.AlfanumrubriekwaardeContext ctx) {
        final Expressie expressie;
        if (ctx.alfanumrubriekterm().size() == 1) {
            expressie = visit(ctx.alfanumrubriekterm(0));
        } else if (ctx.alfanumrubriekterm().size() > 1) {
            final List<Expressie> waarden = new ArrayList<Expressie>();
            for (int i = 0; i < ctx.alfanumrubriekterm().size(); i++) {
                waarden.add(visit(ctx.alfanumrubriekterm(i)));
            }
            expressie = new LijstExpressie(waarden);
        } else {
            expressie = NullValue.getInstance();
        }
        return expressie;
    }

    @Override
    public final Expressie visitAlfanumVergelijking(final GBAVoorwaarderegelParser.AlfanumVergelijkingContext ctx) {
        final Expressie rubriek = visit(ctx.alfanumrubrieknummer());
        final TxtOp txtOp = GbaOperatoren.findTxtOp(ctx.txtop().getText());
        final LogOpVgl logOpVgl = VergelijkingBouwer.bepaalDefaultLogOpVgl(txtOp);
        final Expressie waarde;
        waarde = visit(ctx.alfanumrubriekwaarde());
        return VergelijkingBouwer.maakVergelijking(rubriek, txtOp, waarde, logOpVgl);
    }

    @Override
    public final Expressie visitDatrubriekwaarde(final GBAVoorwaarderegelParser.DatrubriekwaardeContext ctx) {
        final Expressie expressie;
        final Expressie datum = visit(ctx.datum());

        if (ctx.matop() != null) {
            final PeriodeLiteralExpressie periode = (PeriodeLiteralExpressie) visit(ctx.periode());
            final GBAVoorwaarderegelParser.MatopContext matopContext = ctx.matop();
            expressie = VergelijkingBouwer.maakDatumBerekening(datum, periode, matopContext.OP_PLUS() != null);
        } else {
            expressie = datum;
        }
        return expressie;
    }

    @Override
    public final Expressie visitDatVergelijking(final GBAVoorwaarderegelParser.DatVergelijkingContext ctx) {
        final Expressie expressie;
        final Expressie rubriek = visit(ctx.datrubrieknummer());
        final Expressie waarde = visit(ctx.datrubriekwaarde());
        final RelOp relOp = GbaOperatoren.findRelOp(ctx.relop().getText());
        if (relOp != RelOp.NONE) {
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, relOp, waarde);
        } else {
            final TxtOp txtOp = GbaOperatoren.findTxtOp(ctx.relop().getText());
            final LogOpVgl logOpVgl = VergelijkingBouwer.bepaalDefaultLogOpVgl(txtOp);
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, txtOp, waarde, logOpVgl);
        }
        return expressie;
    }

    @Override
    public final Expressie visitSelDatrubriekwaarde(final GBAVoorwaarderegelParser.SelDatrubriekwaardeContext ctx) {
        Expressie expressie;
        if (ctx.datrubriekwaarde() != null) {
            expressie = visit(ctx.datrubriekwaarde());
        } else {
            final PeriodeLiteralExpressie periode = (PeriodeLiteralExpressie) visit(ctx.periode());
            expressie = VergelijkingBouwer.maakDatumNuExpressie(periode, ctx.matop().OP_PLUS() != null,
                ctx.periode().periodeJaar() != null);
        }
        return expressie;
    }

    @Override
    public final Expressie visitVandaagvergelijking(final GBAVoorwaarderegelParser.VandaagvergelijkingContext ctx) {
        Expressie expressie;
        Expressie rubriek = visit(ctx.datrubrieknummer());
        final PeriodeLiteralExpressie periode = (PeriodeLiteralExpressie) visit(ctx.periode());

        final Expressie waarde = VergelijkingBouwer.maakDatumNuExpressie(periode, ctx.matop().OP_PLUS() != null,
            ctx.periode().periodeJaar() != null);
        if (rubriek.getType(null) == ExpressieType.DATUM && waarde instanceof FunctieExpressie
            && ((FunctieExpressie) waarde).getKeyword() == Keyword.JAAR)
        {
            rubriek = FunctieExpressie.maakFunctieaanroep(Keyword.JAAR, rubriek);
        }

        final RelOp relOp = GbaOperatoren.findRelOp(ctx.relop().getText());
        if (relOp != RelOp.NONE) {
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, relOp, waarde);
        } else {
            final TxtOp txtOp = GbaOperatoren.findTxtOp(ctx.relop().getText());
            final LogOpVgl logOpVgl = VergelijkingBouwer.bepaalDefaultLogOpVgl(txtOp);
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, txtOp, waarde, logOpVgl);
        }
        return expressie;
    }


    @Override
    public final Expressie visitSelectievergelijking(final GBAVoorwaarderegelParser.SelectievergelijkingContext ctx) {
        final Expressie expressie;
        final Expressie waarde = visit(ctx.selDatrubriekwaarde());
        Expressie rubriek = visit(ctx.selDatrubrieknummer());

        if (rubriek.getType(null) == ExpressieType.DATUM && waarde instanceof FunctieExpressie
            && ((FunctieExpressie) waarde).getKeyword() == Keyword.JAAR)
        {
            rubriek = FunctieExpressie.maakFunctieaanroep(Keyword.JAAR, rubriek);
        }

        final RelOp relOp = GbaOperatoren.findRelOp(ctx.relop().getText());
        if (relOp != RelOp.NONE) {
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, relOp, waarde);
        } else {
            final TxtOp txtOp = GbaOperatoren.findTxtOp(ctx.relop().getText());
            final LogOpVgl logOpVgl = VergelijkingBouwer.bepaalDefaultLogOpVgl(txtOp);
            expressie = VergelijkingBouwer.maakVergelijking(rubriek, txtOp, waarde, logOpVgl);
        }
        return expressie;
    }

    @Override
    public final Expressie visitVoorkomenvraag(final GBAVoorwaarderegelParser.VoorkomenvraagContext ctx) {
        final boolean moetVoorkomen = ctx.vrkop().OP_KV() != null;
        return VergelijkingBouwer.maakControleOpVoorkomen(ctx.rubrieknummer().getText(), moetVoorkomen);
    }

    @Override
    public final Expressie visitVoorwaarde(final GBAVoorwaarderegelParser.VoorwaardeContext ctx) {
        final Expressie expressie;
        final Expressie linkerOperand = visit(ctx.term());
        if (ctx.logopvwd() != null) {
            if (ctx.logopvwd().OP_ENVWD() != null) {
                expressie = new LogischeEnExpressie(linkerOperand, visit(ctx.voorwaarde()));
            } else {
                expressie = new LogischeOfExpressie(linkerOperand, visit(ctx.voorwaarde()));
            }
        } else {
            expressie = linkerOperand;
        }
        return expressie;
    }

    @Override
    public final Expressie visitHaakjes(final GBAVoorwaarderegelParser.HaakjesContext ctx) {
        return visit(ctx.voorwaarde());
    }
}

