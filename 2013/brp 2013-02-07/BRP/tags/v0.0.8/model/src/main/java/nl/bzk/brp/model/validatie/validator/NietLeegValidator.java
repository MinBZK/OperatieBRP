/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.NietLeeg;
import org.apache.commons.lang.StringUtils;


/**
 * Valideert of veld niet Null is.
 *
 */
public class NietLeegValidator implements ConstraintValidator<NietLeeg, String> {

    @Override
    public void initialize(final NietLeeg constraintAnnotation) {
        // Niets om te initialiseren
    }

    @Override
    public boolean isValid(final String waarde, final ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isNotBlank(waarde);
    }
}
