/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.CollectieMetUniekeWaarden;


/**
 * Validator die controleert of elementen in een Collectie uniek zijn. Dit wordt gedaan op basis van de
 * {@link Object#equals(Object)} methode.
 */
public class CollectieMetUniekeWaardenValidator implements
        ConstraintValidator<CollectieMetUniekeWaarden, Collection<? extends Object>>
{

    @Override
    public void initialize(final CollectieMetUniekeWaarden constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Collection<? extends Object> collectie, final ConstraintValidatorContext context) {
        boolean isValid = true;
        if (collectie != null && collectie.size() > 1) {
            for (Object object1 : collectie) {
                if (isValid) {
                    int aantalGelijk = 0;
                    for (Object object2 : collectie) {
                        if (object1.equals(object2)) {
                            aantalGelijk++;
                        }
                        //Eentje is zoiezo gelijk.
                        if (aantalGelijk > 1) {
                            isValid = false;
                            break;
                        }
                    }
                }
            }
        }
        return isValid;
    }
}
