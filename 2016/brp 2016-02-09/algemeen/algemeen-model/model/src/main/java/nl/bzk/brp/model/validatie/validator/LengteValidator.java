/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.basis.AbstractAttribuut;
import nl.bzk.brp.model.validatie.constraint.Lengte;


/**
 * Valideert de lengte van een String AttribuutType.
 */
public class LengteValidator implements ConstraintValidator<Lengte, Object> {

    private int min;
    private int max;

    @Override
    public void initialize(final Lengte parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        final boolean isValide;

        if (object == null) {
            isValide = true;
        } else if (object instanceof AbstractAttribuut) {
            final Object waarde = ((AbstractAttribuut<?>) object).getWaarde();

            if (waarde == null) {
                isValide = true;
            } else if (waarde instanceof String) {
                final int length = ((String) waarde).length();

                isValide = length >= min && length <= max;
            } else {
                throw new IllegalArgumentException("De basis type moet van het type String zijn.");
            }
        } else {
            throw new IllegalArgumentException("De ge-annoteerde object een subtype zijn van AbstractAttribuut.");
        }
        return isValide;
    }

    /**
     * Valideert de parameters.
     */
    private void validateParameters() {
        if (min < 0) {
            throw new IllegalArgumentException("De min parameter kan niet negatief zijn.");
        }
        if (max < 0) {
            throw new IllegalArgumentException("De max parameter kan niet negatief zijn.");
        }
        if (max < min) {
            throw new IllegalArgumentException("De lengte kan niet be negatief zijn.");
        }
    }
}
