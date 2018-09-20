/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.validatie.constraint.BRBY0444;


/**
 * BRBY0444: Datum einde huwelijk of partnerschap mag niet in de toekomst liggen.
 */
public class BRBY0444Validator implements ConstraintValidator<BRBY0444, DatumEvtDeelsOnbekendAttribuut> {

    @Override
    public final void initialize(final BRBY0444 constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final DatumEvtDeelsOnbekendAttribuut value, final ConstraintValidatorContext context) {
        boolean resultaat = true;
        if (value != null && value.heeftWaarde()) {
            resultaat = DatumAttribuut.vandaag().naOfOp(value);
        }
        return resultaat;
    }
}
