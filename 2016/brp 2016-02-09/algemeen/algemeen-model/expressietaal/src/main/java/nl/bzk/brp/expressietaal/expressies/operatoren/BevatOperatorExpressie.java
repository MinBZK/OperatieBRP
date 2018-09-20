/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert een expressie die controleert op de aanwezigheid van een bepaald element in een lijst.
 */
public final class BevatOperatorExpressie extends AbstractBinaireOperatorExpressie {

    private final boolean wildcard;

    /**
     * Constructor.
     *
     * @param element  Linkerterm van de operator: het te zoeken element.
     * @param lijst    Rechterterm van de operator: de te doorzoeken lijst.
     * @param wildcard TRUE als de vergelijking met wildcards (reguliere expressies) gedaan moet worden; anders FALSE.
     */
    public BevatOperatorExpressie(final Expressie element, final Expressie lijst, final boolean wildcard)
    {
        super(element, lijst);
        this.wildcard = wildcard;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public int getPrioriteit() {
        return PRIORITEIT_BEVAT;
    }

    @Override
    public Expressie optimaliseer(final Context context) {
        final Expressie geoptimaliseerdeOperandLinks = getOperandLinks().optimaliseer(context);
        final Expressie geoptimaliseerdeOperandRechts = getOperandRechts().optimaliseer(context);
        final Expressie resultaat;
        if (geoptimaliseerdeOperandLinks.isConstanteWaarde() && geoptimaliseerdeOperandRechts.isConstanteWaarde()) {
            resultaat = pasOperatorToe(geoptimaliseerdeOperandLinks, geoptimaliseerdeOperandRechts, context);
        } else {
            resultaat = this;
        }
        return resultaat;
    }

    @Override
    public Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
        final Expressie berekendeOperandRechts,
        final Context context)
    {
        final Expressie result;
        if (berekendeOperandLinks.isNull(context) || berekendeOperandRechts.isNull(context)) {
            result = NullValue.getInstance();
        } else if (berekendeOperandLinks.isConstanteWaarde() && berekendeOperandRechts.isLijstExpressie()
            && berekendeOperandRechts.isConstanteWaarde())
        {
            result = lijstBevat(berekendeOperandLinks, (LijstExpressie) berekendeOperandRechts);
        } else if (berekendeOperandRechts.isLijstExpressie()
            || berekendeOperandRechts.getType(context) == ExpressieType.ONBEKEND_TYPE)
        {
            result = new BevatOperatorExpressie(berekendeOperandLinks, berekendeOperandRechts, wildcard);
        } else {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        }
        return result;
    }

    /**
     * Controleert of een lijst een bepaalde waarde/wildcard bevat.
     *
     * @param berekendeOperandLinks  De berekende operand links.
     * @param berekendeOperandRechts De berekende operand recht.
     * @return Het resultaat als expressie.
     */
    private Expressie lijstBevat(final Expressie berekendeOperandLinks, final LijstExpressie berekendeOperandRechts) {
        final Expressie result;
        Expressie simpleExp = null;
        final LijstExpressie list = berekendeOperandRechts;
        for (Expressie e : list.getElementen()) {
            final Expressie vergelijking;
            if (wildcard) {
                vergelijking = new WildcardOperatorExpressie(berekendeOperandLinks, e);
            } else {
                vergelijking = new GelijkheidsoperatorExpressie(berekendeOperandLinks, e);
            }
            if (simpleExp == null) {
                simpleExp = vergelijking;
            } else {
                simpleExp = new LogischeOfExpressie(vergelijking, simpleExp);
            }
        }
        if (simpleExp != null) {
            result = simpleExp.evalueer(null);
        } else {
            result = BooleanLiteralExpressie.ONWAAR;
        }
        return result;
    }

    @Override
    public String operatorAlsString() {
        final String string;
        if (wildcard) {
            string = DefaultKeywordMapping.getSyntax(Keyword.IN_WILDCARD);
        } else {
            string = DefaultKeywordMapping.getSyntax(Keyword.IN);
        }
        return string;
    }
}
