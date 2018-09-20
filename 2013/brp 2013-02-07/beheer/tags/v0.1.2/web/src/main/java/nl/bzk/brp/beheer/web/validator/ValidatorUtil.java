/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.validation.Errors;


/**
 * Valideert de form bindings met JSR-303.
 *
 */
public final class ValidatorUtil {

    /**
     * private constructor.
     */
    private ValidatorUtil() {

    }

    /**
     * Valideer de opgegeven object, the object moet de ModelAttribute object
     * zijn.
     *
     * @param result
     *            the object waar de error berichten worden opgeslagen
     * @param object
     *            the ModelAttribute object
     * @param fields
     *            bijvoorbeeld partij.naam
     * @return true of false
     */
    public static boolean isValid(final Errors result, final Object object, final String[] fields) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (String field : fields) {
            Set<ConstraintViolation<Object>> violations = validator.validateProperty(object, field, Default.class);

            updateResult(violations, result);
        }

        return !result.hasErrors();
    }

    /**
     *
     * @param violations overtredingen
     * @param result resultaten
     */
    private static void updateResult(final Set<ConstraintViolation<Object>> violations, final Errors result) {

        for (ConstraintViolation<Object> v : violations) {
            Path path = v.getPropertyPath();
            String propertyName = "";
            if (path != null) {
                for (Node n : path) {
                    propertyName += n.getName() + ".";
                }
                propertyName = propertyName.substring(0, propertyName.length() - 1);
            }
            String constraintName = v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            if (propertyName == null || "".equals(propertyName)) {
                result.reject(constraintName, v.getMessage());
            } else {
                result.rejectValue(propertyName, constraintName, v.getMessage());
            }
        }
    }

}
