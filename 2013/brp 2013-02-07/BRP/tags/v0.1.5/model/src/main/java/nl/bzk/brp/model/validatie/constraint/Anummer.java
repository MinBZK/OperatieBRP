/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.model.validatie.validator.AnummerValidator;


/**
 * Administratienummer voorschrift.
 *
 * @brp.bedrijfsregel BRAL0001
 */
@Documented
@Constraint(validatedBy = AnummerValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Anummer {

    //SoortMelding soortMelding() default null;

    //MeldingCode code() default MeldingCode.BRAL0001;

    /** Standaard validatie bericht. */
    String message() default "{BRAL0001}";

    /** Standaard validatie groep. */
    Class<?>[] groups() default { };

    /** Verplicht veld voor de validatie framework. */
    Class<? extends Payload>[] payload() default { };

    /** De bedrijfsregel code. */
    MeldingCode code() default MeldingCode.BRAL0001;

    /** Regel effect BR (blocker). */
    SoortMelding soortMelding() default SoortMelding.FOUT_ONOVERRULEBAAR;
}
