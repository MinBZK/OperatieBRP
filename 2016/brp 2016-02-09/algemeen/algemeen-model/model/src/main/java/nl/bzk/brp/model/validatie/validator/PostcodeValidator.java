/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.validatie.constraint.Postcode;
import org.apache.commons.lang.StringUtils;


/**
 * Postcode voorschrift.
 * <p/>
 * Indien niet leeg is, moet de postcode voldoen aan [0-9]{4}[A-Z]{2} Letop dat deze in hoofdletters moeten zijn. Het wordt NIET geuppercased.
 *
 * @brp.bedrijfsregel BRAL2024
 */
public class PostcodeValidator implements ConstraintValidator<Postcode, PostcodeAttribuut> {

    private static final int POSTCODE_LENGTE         = 6;
    private static final int LAAGSTE_POSTCODE_NUMMER = 1000;
    private static final int POSTCODE_NUMMER_VAN     = 0;
    private static final int POSTCODE_NUMMER_TOT     = 4;
    private static final int POSTCODE_LETTER_VAN     = 4;
    private static final int POSTCODE_LETTER_TOT     = 6;

    @Override
    public final void initialize(final Postcode constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final PostcodeAttribuut postcode, final ConstraintValidatorContext context) {
        final boolean resultaat;

        // "" is geen geldige postcode (assuming)
        if (postcode == null) {
            resultaat = true;
        } else {
            resultaat = isGeldigePostcode(postcode);
        }

        return resultaat;
    }

    /**
     * Controlleer postcode formaat.
     *
     * @param postcode de Postcode
     * @return true als het een geldige postcode is
     */
    private boolean isGeldigePostcode(final PostcodeAttribuut postcode) {
        boolean retval = false;
        final String waarde = postcode.getWaarde();
        if (waarde.length() == POSTCODE_LENGTE) {
            final String nummer = waarde.substring(POSTCODE_NUMMER_VAN, POSTCODE_NUMMER_TOT);
            if (StringUtils.isNumeric(nummer) && Integer.parseInt(nummer) >= LAAGSTE_POSTCODE_NUMMER) {
                final String code = waarde.substring(POSTCODE_LETTER_VAN, POSTCODE_LETTER_TOT);
                retval = StringUtils.isAllUpperCase(code) && StringUtils.isAlpha(code);
            }
        }
        return retval;
    }

}
