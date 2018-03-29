/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.validator;

import java.util.function.Supplier;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.Literal;
import nl.bzk.brp.domain.expressie.NullLiteral;

/**
 * Hulpklasse voor het doen van validatie op operands.
 */
public final class OperandValidator {

    /**
     * Valideert of een operand een {@link Literal} is.
     */
    public static final Validator IS_LITERAL = expressie -> !(expressie instanceof Literal)
            ? "Operand moet een literal zijn" : null;
    /**
     * Valideert of een operand geen {@link NullLiteral} is.
     */
    public static final Validator IS_NIET_NULL = expressie -> expressie == NullLiteral.INSTANCE
            ? "Operand mag niet NULL zijn" : null;
    /**
     * Valideert of een operand een {@link LijstExpressie} is.
     */
    public static final Validator IS_COMPOSITIE = expressie -> !(expressie instanceof LijstExpressie)
            ? "Operand moet een LijstExpressie zijn" : null;
    /**
     * Valideert of een operand een homogene {@link LijstExpressie} is.
     */
    public static final Validator IS_HOMOGENE_COMPOSITIE = expressie -> !expressie.alsLijst().isHomogeen()
            ? "LijstExpressie moet homogeen zijn." : null;
    /**
     * Valideert of een operand {@link Expressie#isConstanteWaarde() constant} is.
     */
    public static final Validator IS_CONSTANT = expressie -> !expressie.isConstanteWaarde()
            ? "Operand moet constant zijn" : null;
    /**
     * Valideert of een {@link LijstExpressie} niet leeg is.
     */
    public static final Validator IS_NIET_LEGE_COMPOSITIE = expressie -> expressie.alsLijst().isEmpty()
            ? "LijstExpressie mag niet leeg zijn" : null;
    /**
     * Valideert of een {@link LijstExpressie} geen {@link NullLiteral} waarden bevat.
     */
    public static final Validator IS_NON_NULL_COMPOSITIE = expressie -> {
        for (final Expressie element : expressie.alsLijst()) {
            if (element == NullLiteral.INSTANCE) {
                return "LijstExpressie mag geen null bevatten";
            }
        }
        return null;
    };

    private final Validator[] validators;

    /**
     * Constructor.
     *
     * @param validators lijst van validators.
     */
    public OperandValidator(final Validator... validators) {
        this.validators = validators;
    }

    /**
     * Voert de validatie uit op alle {@link Validator} instanties.
     * Indien één validator een melding teruggeeft stopt de iteratie en wordt een {@link ExpressieRuntimeException}
     * fout gegooid.
     *
     * @param expressie de te valideren expressie
     * @param melding   de prefix van de foutmelding
     */
    public void valideer(final Expressie expressie, final Supplier<String> melding) {
        for (Validator validator : validators) {
            final String validatiemelding = validator.apply(expressie);
            if (validatiemelding != null) {
                throw new ExpressieRuntimeException(melding.get() + " : " + validatiemelding);
            }
        }
    }

}
