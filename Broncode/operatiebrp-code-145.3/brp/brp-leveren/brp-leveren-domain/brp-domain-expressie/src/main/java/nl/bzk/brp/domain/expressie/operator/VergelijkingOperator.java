/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieLogger;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.vergelijker.Vergelijker;
import nl.bzk.brp.domain.expressie.vergelijker.VergelijkerFactory;

/**
 * Representeert expressies van vergelijkingsoperatoren.
 */
public final class VergelijkingOperator extends AbstractBinaireOperator {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final OperatorType operator;

    /**
     * Constructor.
     *
     * @param operandLinks  Linkerterm van de operator.
     * @param operandRechts Rechterterm van de operator.
     * @param operator      de operator in de vergelijking
     */
    public VergelijkingOperator(final Expressie operandLinks, final Expressie operandRechts, final OperatorType operator) {
        super(operandLinks, operandRechts);
        this.operator = operator;
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
                                       final Expressie berekendeOperandRechts,
                                       final Context context) {
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("Vergelijk {} met {}", berekendeOperandLinks, berekendeOperandRechts);
        }
        if (berekendeOperandLinks instanceof LijstExpressie ^ berekendeOperandRechts instanceof LijstExpressie) {
            //vergelijking is mogelijk als de lijst één waarde bevat
            return bepaalLijstResultaat(berekendeOperandLinks, berekendeOperandRechts, context);
        }

        // waarden zijn qua type gelijk
        final ExpressieType typeLinks = berekendeOperandLinks.getType(context);
        final ExpressieType typeRechts = berekendeOperandRechts.getType(context);
        final Vergelijker<Expressie, Expressie> vergelijker = VergelijkerFactory.get(typeLinks, typeRechts, operator);
        return vergelijker.apply(berekendeOperandLinks, berekendeOperandRechts);
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public String operatorAlsString() {
        return operator.toString();
    }

    @Override
    public Prioriteit getPrioriteit() {
        return operator.getPrioriteit();
    }

    private Expressie bepaalLijstResultaat(final Expressie berekendeOperandLinks, final Expressie berekendeOperandRechts, final Context context) {
        final Expressie resultaat;
        if (berekendeOperandLinks instanceof LijstExpressie) {
            final LijstExpressie lijstExpressie = (LijstExpressie) berekendeOperandLinks;
            final Expressie operandLinks;
            if (lijstExpressie.isEmpty()) {
                operandLinks = NullLiteral.INSTANCE;
            } else {
                operandLinks = lijstExpressie.geefEnkeleWaarde();
            }
            resultaat = pasOperatorToe(operandLinks, berekendeOperandRechts, context);
        } else {
            final LijstExpressie lijstExpressie = (LijstExpressie) berekendeOperandRechts;
            final Expressie operandRechts;
            if (lijstExpressie.isEmpty()) {
                operandRechts = NullLiteral.INSTANCE;
            } else {
                operandRechts = lijstExpressie.geefEnkeleWaarde();
            }
            resultaat = pasOperatorToe(berekendeOperandLinks, operandRechts, context);
        }
        return resultaat;
    }
}
