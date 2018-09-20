/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import org.apache.commons.lang.StringUtils;


/**
 * Conditioneel verplicht veld afhankelijk van de waarde in een andere veld.
 *
 * @brp.bedrijfsregel BRAL2032
 */
public class ConditioneelVerplichtVeldValidator implements ConstraintValidator<ConditioneelVerplichtVeld, Object> {

    private String naamVerplichtVeld;
    private String naamAfhankelijkVeld;
    private String bevatWaarde;
    private boolean verplichtNull;

    @Override
    public void initialize(final ConditioneelVerplichtVeld constraintAnnotation) {
        naamVerplichtVeld = constraintAnnotation.naamVerplichtVeld();
        naamAfhankelijkVeld = constraintAnnotation.naamAfhankelijkVeld();
        bevatWaarde = constraintAnnotation.waardeAfhankelijkVeld();
        verplichtNull = constraintAnnotation.verplichtNull();
    }

    @Override
    public boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        boolean resultaat;

        try {
            Object verplichtVeld = ValidatorUtil.haalWaardeOp(waarde, naamVerplichtVeld);
            Object afhankelijkVeld = ValidatorUtil.haalWaardeOp(waarde, naamAfhankelijkVeld);

            // Indien afhankelijkveld de opgegeven waarde bevat, dan controleren of verplichteveld ook echt gevuld is.
            if (afhankelijkVeld != null && afhankelijkVeld.toString().equals(bevatWaarde)) {
                if (verplichtNull) {
                    // verplicht WEL null.
                    resultaat = (verplichtVeld == null);
                } else {
                    // verplicht NIET null.
                    resultaat =
                            verplichtVeld != null
                                && !(verplichtVeld instanceof String && StringUtils.isBlank((String) verplichtVeld));
                }
            } else {
                resultaat = true;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return resultaat;
    }
}
