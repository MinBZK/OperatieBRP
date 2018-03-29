/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.berekening.Berekening;
import nl.bzk.brp.domain.expressie.berekening.BerekeningFactory;

/**
 * Representeert expressies van numerieke operatoren.
 */
public final class RekenOperator extends AbstractBinaireOperator {

    private OperatorType operatorType;

    /**
     * Constructor.
     * @param operandLinks Linkeroperand van de operator.
     * @param operandRechts Rechteroperand van de operator.
     * @param operatorType het type van de vergelijkingsoperator
     */
    public RekenOperator(final Expressie operandLinks, final Expressie operandRechts, final OperatorType operatorType) {
        super(operandLinks, operandRechts);
        this.operatorType = operatorType;
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
                                       final Expressie berekendeOperandRechts,
                                       final Context context) {
        final Expressie resultaat;
        final ExpressieType typeOperandLinks = berekendeOperandLinks.getType(context);
        final ExpressieType typeOperandRechts = berekendeOperandRechts.getType(context);
        if (isOperandNull(berekendeOperandLinks, berekendeOperandRechts)
                || !berekendeOperandLinks.isConstanteWaarde() || !berekendeOperandRechts.isConstanteWaarde()) {
            resultaat = NullLiteral.INSTANCE;
        } else if (typeOperandLinks == ExpressieType.LIJST && typeOperandRechts != ExpressieType.LIJST) {

            final List<Expressie> tempEval = Lists.newLinkedList();
            for (Expressie expressie : berekendeOperandLinks.alsLijst()) {
                tempEval.add(pasOperatorToe(expressie, berekendeOperandRechts, context));
            }
            resultaat = new LijstExpressie(tempEval);
        } else {
            final Berekening<Expressie, Expressie> function = BerekeningFactory.get(typeOperandLinks, typeOperandRechts, operatorType);
            if (function == null) {
                throw new ExpressieRuntimeException(String.format("De berekening '%s %s %s' " +
                        "wordt niet ondersteund", typeOperandLinks, operatorType, typeOperandRechts));
            }
            resultaat = function.apply(berekendeOperandLinks, berekendeOperandRechts);
        }
        return resultaat;
    }

    @Override
    public ExpressieType getType(final Context context) {
        final ExpressieType result;

        final ExpressieType typeLinks = getOperandLinks().getType(context);
        final ExpressieType typeRechts = getOperandRechts().getType(context);

        if (isTypeLinksGetalEnTypeRechtsGetal(typeLinks, typeRechts)) {
            result = ExpressieType.GETAL;
        } else if (isTypeLinksDatumEnTypeRechtsGetal(typeLinks, typeRechts) || isTypeLinksDatumEnTypeRechtsPeriode(typeLinks, typeRechts)) {
            result = ExpressieType.DATUM;
        } else if (isTypeLinksPeriodeEnTypeRechtsPeriode(typeLinks, typeRechts)) {
            result = ExpressieType.PERIODE;
        } else {
            result = ExpressieType.ONBEKEND_TYPE;
        }
        return result;
    }

    private boolean isTypeLinksPeriodeEnTypeRechtsPeriode(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.PERIODE && typeRechts == ExpressieType.PERIODE;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return operatorType.getPrioriteit();
    }

    @Override
    public String operatorAlsString() {
        return operatorType.toString();
    }

    private boolean isTypeLinksDatumEnTypeRechtsPeriode(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.DATUM && typeRechts == ExpressieType.PERIODE;
    }

    private boolean isTypeLinksDatumEnTypeRechtsGetal(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.DATUM && typeRechts == ExpressieType.GETAL;
    }

    private boolean isTypeLinksGetalEnTypeRechtsGetal(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.GETAL && typeRechts == ExpressieType.GETAL;
    }

    private boolean isOperandNull(final Expressie berekendeOperandLinks, final Expressie berekendeOperandRechts) {
        return berekendeOperandLinks.isNull() || berekendeOperandRechts.isNull();
    }
}
