/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.validatie.constraint.GeenSpatie;


/**
 * Het attribuut mag geen spatie(s) bevatten (In dit geval voornaam).
 *
 * @brp.bedrijfsregel BRAL0501
 */
public class GeenSpatieValidator implements ConstraintValidator<GeenSpatie, Object> {

    @Override
    public void initialize(final GeenSpatie constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        if (waarde != null) {
            if (waarde instanceof VoornaamAttribuut) {
                final VoornaamAttribuut voornaam = (VoornaamAttribuut) waarde;
                return voornaam.getWaarde().indexOf(" ") == -1;
            } else {
                throw new IllegalArgumentException("Niet gesupport class " + waarde);
            }
        }
        return true;
    }
}
