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
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.validator.BsnValidator;


/**
 * Burgerservicenummer voorschrift.
 *
 * @brp.bedrijfsregel BRAL0012
 */
@Documented
@Constraint(validatedBy = BsnValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Bsn {

    /** Standaard validatie bericht. */
    String message() default "{BRAL0012}";

    /** Standaard validatie groep. */
    Class<?>[] groups() default { };

    /** Verplicht veld voor de validatie framework. */
    Class<? extends Payload>[] payload() default { };

    /** De bedrijfsregel code. */
    MeldingCode code() default MeldingCode.BRAL0012;

    /** Regel effect BR (blocker). */
    SoortMelding soortMelding() default SoortMelding.FOUT;
}
