/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validatie-annotatie voor selectieperiode.
 */
@Target( { METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SelectiePeriodeValidator.class)
@Documented
public @interface SelectiePeriode {
    /**
     * De default validatiemelding.
     * @return de validatiemelding
     */
    String message() default "{nl.bzk.brp.beheer.service.validation.SelectiePeriode.message}";

    /**
     * Geef de groepen.
     * @return de groepen
     */
    Class<?>[] groups() default {};

    /**
     * Geef de payload.
     * @return de payload
     */
    Class<? extends Payload>[] payload() default {};
}
