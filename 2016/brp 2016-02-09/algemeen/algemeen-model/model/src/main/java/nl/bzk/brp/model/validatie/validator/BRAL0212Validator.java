/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.validatie.constraint.BRAL0212;
import org.apache.commons.lang.StringUtils;


/**
 * Het attribuut Scheidingsteken moet een waarde hebben als ook Voorvoegsel een waarde heeft, anders mag Scheidingsteken geen waarde hebben.
 *
 * @brp.bedrijfsregel BRAL0212
 */
public class BRAL0212Validator implements ConstraintValidator<BRAL0212, Object> {

    @Override
    public final void initialize(final BRAL0212 constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        final Object voorvoegsel;
        final Object scheidingsteken;
        try {
            voorvoegsel = ValidatorUtil.haalWaardeOp(waarde, "voorvoegsel");
            scheidingsteken = ValidatorUtil.haalWaardeOp(waarde, "scheidingsteken");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        }

        // Beide velden moeten leeg zijn of beide moet gevuld zijn.
        return StringUtils.isBlank((String) voorvoegsel) == (scheidingsteken == null || "".equals(scheidingsteken));
    }
}
