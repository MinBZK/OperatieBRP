/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.validator.CustomSpELValidator;


/**
 * Conditioneel validatie veld.
 * <p/>
 * wanneerVeldVoldoetAanRegel bepaald wanneer danMoetVeldVoldoenAanRegel moet voldoen.
 * <p/>
 * de regels worden gedefinieerd in Spring Expression Language (http://static.springsource.org/spring/docs/3.0.x/reference/expressions.html)
 */
@Documented
@Constraint(validatedBy = CustomSpELValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomSpEL {

    /**
     * Wanneer veld voldoet aan regel.
     */
    String wanneerVeldVoldoetAanRegel();

    /**
     * Dan moet veld voldoen aan regel.
     */
    String danMoetVeldVoldoenAanRegel();

    /**
     * Standaard validatie bericht.
     */
    String message() default "";

    /**
     * Standaard validatie groep.
     */
    Class<?>[] groups() default { };

    /**
     * Verplicht veld voor de validatie framework.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * De bedrijfsregel code.
     */
    Regel code();

    /**
     * Standaard regel effect BR (blocker).
     */
    SoortMelding soortMelding() default SoortMelding.FOUT;
}
