/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.Postcode;

import org.apache.commons.lang.StringUtils;


/**
 * Postcode voorschrift.
 *
 * Indien niet leeg is, moet de postcode voldoen aan [0-9]{4}[A-Z]{2}
 * Letop dat deze in hoofdletters moeten zijn. Het wordt NIET geuppercased.
 *
 * @brp.bedrijfsregel BRAL0102
 */
public class PostcodeValidator implements ConstraintValidator<Postcode, String> {
	

    @Override
    public void initialize(final Postcode constraintAnnotation) {
        // Niets om te initialiseren
    }

    @Override
    public boolean isValid(final String waarde, final ConstraintValidatorContext context) {
        boolean resultaat;

        if (waarde == null) { // "" is geen geldige postcode (assuming) 
            resultaat = true;
        } else {
            resultaat = isGeldigePostcode(waarde);
        }

        return resultaat;
    }

    private boolean isGeldigePostcode(final String postcode) {
    	boolean retval = false;
		if (postcode.length() == 6) {
			try {
				if (Integer.parseInt(postcode.substring(0, 4)) > 1000) {
    				String code = postcode.substring(5, 6);
    				retval = StringUtils.isAllUpperCase(code) && StringUtils.isAlpha(code);    					
				}
			} catch (NumberFormatException e ) {
				; // geen getal
			}
		}
    	return retval;
    }

}
