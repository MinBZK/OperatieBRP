/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.Bsn;
import org.apache.commons.lang.StringUtils;


/**
 * Burgerservicenummer voorschrift.
 *
 * Als de cijfers van het Burgerservicenummer van links naar rechts worden aangegeven met s[0] t/m s[8] dan gelden
 * volgende condities:
 * (9*s0)+(8*s1)+(7*s2)+...+(2*s7)+(-1*s8) is deelbaar door 11.
 *
 * @brp.bedrijfsregel BRAL0012
 */
public class BsnValidator implements ConstraintValidator<Bsn, String> {

    private static final int BSN_LENGTE    = 9;
    private static final int ELF = 11;

    @Override
    public void initialize(final Bsn constraintAnnotation) {
        // Niets om te initialiseren
    }

    @Override
    public boolean isValid(final String waarde, final ConstraintValidatorContext context) {
        boolean resultaat;

        if (waarde == null || waarde.length() == 0) {
            resultaat = true;
        } else {
            resultaat = isGeldigeBsn(waarde);
        }

        return resultaat;
    }

    /**
     * Controlleert of de waarde een geldige BSN nummer is.
     *
     * @param waarde de bsn
     * @return true als het een geldige bsn
     */
    private boolean isGeldigeBsn(final String waarde) {
        boolean resultaat = false;

        if (waarde.length() == BSN_LENGTE && StringUtils.isNumeric(waarde)) {
            int som = 0;

            for (int i = 0; i < waarde.length() - 1; i++) {
                som += (Character.getNumericValue(waarde.charAt(i)) * (BSN_LENGTE - i));
            }

            som += (Character.getNumericValue(waarde.charAt(waarde.length() - 1)) * -1);

            if (som % ELF == 0) {
                resultaat = true;
            }
        }

        return resultaat;
    }
}
