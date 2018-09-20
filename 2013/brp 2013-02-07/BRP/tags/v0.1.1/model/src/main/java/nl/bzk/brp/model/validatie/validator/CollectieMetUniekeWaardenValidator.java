/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.CollectieMetUniekeWaarden;

/**
 * Validator die controleert of elementen in een Collection uniek zijn. Dit wordt gedaan op basis van de
 * {@link Comparable} interface die de elementen moeten implementeren.
 */
public class CollectieMetUniekeWaardenValidator implements ConstraintValidator<CollectieMetUniekeWaarden, List<? extends Comparable>> {

    @Override
    public void initialize(final CollectieMetUniekeWaarden constraintAnnotation) {

    }

    @Override
    public boolean isValid(final List<? extends Comparable> list, final ConstraintValidatorContext context) {
        boolean isValid = true;
        if (list != null && list.size() > 1) {
            for (int teControlereIndex = 0; teControlereIndex < list.size(); teControlereIndex++) {
                if (isValid) {
                    Comparable teControlerenComparable = list.get(teControlereIndex);
                    int aantalGelijk = 0;
                    for (Comparable comparable : list) {
                        if (teControlerenComparable.compareTo(comparable) == 0) {
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
