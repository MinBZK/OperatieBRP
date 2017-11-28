/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.validation;

import java.time.Period;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.beheer.service.selectie.SelectiePeriodeDTO;

/**
 * Constraint validator voor selectieperiode.
 */
final class SelectiePeriodeValidator implements ConstraintValidator<SelectiePeriode, SelectiePeriodeDTO> {

    private static final int MAXIMALE_SELECTIEPERIODE_MAANDEN = 12;

    public void initialize(SelectiePeriode constraintAnnotation) {
        // Niets te initialiseren.
    }

    public boolean isValid(SelectiePeriodeDTO periode, ConstraintValidatorContext constraintContext) {
        boolean valid = periode.isValid();
        valid = valid && Period.between(periode.getBeginDatum(), periode.getEindDatum()).toTotalMonths() < MAXIMALE_SELECTIEPERIODE_MAANDEN;
        return valid;
    }
}
