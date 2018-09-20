/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.gba;

import java.util.Dictionary;
import java.util.Hashtable;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.operatoren.GelijkheidsoperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.GroterDanOfGelijkAanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.GroterDanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.KleinerDanOfGelijkAanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.KleinerDanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeNietExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.OngelijkheidsoperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.WildcardOperatorExpressie;

/**
 * Utility class voor GBA-operatoren.
 */
public final class GbaOperatoren {
    private static final Dictionary<String, TxtOp> TXT_OPERATORS = getTxtOperators();
    private static final Dictionary<String, RelOp> REL_OPERATORS = getRelOperators();

    /**
     * Constructor. Private voor utility class.
     */
    private GbaOperatoren() {
    }

    /**
     * Geeft dictionary met alle vergelijkingsoperatoren voor testen op (on)gelijkheid.
     *
     * @return Dictionary met operatoren.
     */
    private static Dictionary<String, TxtOp> getTxtOperators() {
        final Dictionary<String, TxtOp> result = new Hashtable<String, TxtOp>();
        result.put("GA1", TxtOp.GA1);
        result.put("GAA", TxtOp.GA1);
        result.put("OGA1", TxtOp.OGA1);
        result.put("OGAA", TxtOp.OGAA);
        return result;
    }

    /**
     * Geeft dictionary met alle vergelijkingsoperatoren.
     *
     * @return Dictionary met operatoren.
     */
    private static Dictionary<String, RelOp> getRelOperators() {
        final Dictionary<String, RelOp> result = new Hashtable<String, RelOp>();
        result.put("GD1", RelOp.GD1);
        result.put("GDA", RelOp.GDA);
        result.put("KD1", RelOp.KD1);
        result.put("KDA", RelOp.KDA);
        result.put("GDOG1", RelOp.GDOG1);
        result.put("GDOGA", RelOp.GDOGA);
        result.put("KDOG1", RelOp.KDOG1);
        result.put("KDOGA", RelOp.KDOGA);
        return result;
    }

    /**
     * Zoekt een operator uit een voorwaarderegel in de lijst met bekende operatoren.
     *
     * @param operator De te zoeken operator.
     * @return Gevonden operator of TxtOp.NONE.
     */
    public static TxtOp findTxtOp(final String operator) {
        TxtOp op = TXT_OPERATORS.get(operator);
        if (op == null) {
            op = TxtOp.NONE;
        }
        return op;
    }

    /**
     * Zoekt een operator uit een voorwaarderegel in de lijst met bekende operatoren.
     *
     * @param operator De te zoeken operator.
     * @return Gevonden operator of RelOp.NONE.
     */
    public static RelOp findRelOp(final String operator) {
        RelOp op = REL_OPERATORS.get(operator);
        if (op == null) {
            op = RelOp.NONE;
        }
        return op;
    }

    /**
     * Maakt een operatorexpressie die overeenkomt met de gegeven GBA-operator.
     *
     * @param linkerOperand  Linkeroperand van de operator.
     * @param txtOp          Operator uit GBA-voorwaarderegel.
     * @param rechterOperand Rechteroperand van de operator.
     * @return Expressie die overeenkomt met de gegeven GBA-operator.
     */
    public static Expressie maakOperatorExpressie(final Expressie linkerOperand, final TxtOp txtOp,
                                                  final Expressie rechterOperand)
    {
        Expressie result;
        switch (txtOp) {
            case GA1:
            case GAA:
                if (rechterOperand.isConstanteWaarde(ExpressieType.STRING)
                    && (rechterOperand.alsString().contains("*")
                    || rechterOperand.alsString().contains("?")))
                {
                    result = new WildcardOperatorExpressie(linkerOperand, rechterOperand);
                } else {
                    result = new GelijkheidsoperatorExpressie(linkerOperand, rechterOperand);
                }
                break;
            case OGA1:
            case OGAA:
                if (rechterOperand.isConstanteWaarde(ExpressieType.STRING)
                    && (rechterOperand.alsString().contains("*")
                    || rechterOperand.alsString().contains("?")))
                {
                    result = new LogischeNietExpressie(new WildcardOperatorExpressie(linkerOperand, rechterOperand));
                } else {
                    result = new OngelijkheidsoperatorExpressie(linkerOperand, rechterOperand);
                }
                break;
            default:
                result = NullValue.getInstance();
                break;
        }
        return result;
    }

    /**
     * Maakt een operatorexpressie die overeenkomt met de gegeven GBA-operator.
     *
     * @param linkerOperand  Linkeroperand van de operator.
     * @param relOp          Operator uit GBA-voorwaarderegel.
     * @param rechterOperand Rechteroperand van de operator.
     * @return Expressie die overeenkomt met de gegeven GBA-operator.
     */
    public static Expressie maakOperatorExpressie(final Expressie linkerOperand, final RelOp relOp,
                                                  final Expressie rechterOperand)
    {
        Expressie result;
        switch (relOp) {
            case GD1:
            case GDA:
                result = new GroterDanOperatorExpressie(linkerOperand, rechterOperand);
                break;
            case KD1:
            case KDA:
                result = new KleinerDanOperatorExpressie(linkerOperand, rechterOperand);
                break;
            case GDOG1:
            case GDOGA:
                result = new GroterDanOfGelijkAanOperatorExpressie(linkerOperand, rechterOperand);
                break;
            case KDOG1:
            case KDOGA:
                result = new KleinerDanOfGelijkAanOperatorExpressie(linkerOperand, rechterOperand);
                break;
            default:
                result = NullValue.getInstance();
                break;
        }
        return result;
    }
}
