/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import org.apache.commons.lang.StringUtils;


/**
 * Conditioneel verplicht veld afhankelijk van de waarde in een andere veld.
 *
 * @brp.bedrijfsregel BRAL2032
 */
public final class ConditioneelVerplichtVeldValidator implements ConstraintValidator<ConditioneelVerplichtVeld, Object> {

    private String  naamVerplichtVeld;
    private String  naamAfhankelijkVeld;
    private String  bevatWaarde;
    private boolean verplichtNull;

    @Override
    public void initialize(final ConditioneelVerplichtVeld constraintAnnotation) {
        this.naamVerplichtVeld = constraintAnnotation.naamVerplichtVeld();
        this.naamAfhankelijkVeld = constraintAnnotation.naamAfhankelijkVeld();
        this.bevatWaarde = constraintAnnotation.waardeAfhankelijkVeld();
        this.verplichtNull = constraintAnnotation.verplichtNull();
    }

    @Override
    public boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        final boolean resultaat;

        final Object verplichtVeld;
        final Object afhankelijkVeld;
        try {
            verplichtVeld = ValidatorUtil.haalWaardeOp(waarde, this.naamVerplichtVeld);
            afhankelijkVeld = ValidatorUtil.haalWaardeOp(waarde, this.naamAfhankelijkVeld);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        }

        // Indien afhankelijkveld de opgegeven waarde bevat, dan controleren of verplichteveld ook echt gevuld is.
        // uitgebreidt met: als bevatWaarde == null, dan wil zeggen dat ELK waarde in de afhangkelijkveld is
        // goed genoeg om de validatie te triggeren.

        if (afhankelijkVeld != null && (this.bevatWaarde.equals(ConditioneelVerplichtVeld.WILDCARD_WAARDE_CODE)
            || afhankelijkVeld.toString().equals(this.bevatWaarde)))
        {
            if (this.verplichtNull) {
                // verplicht WEL null.
                resultaat = verplichtVeld == null;
            } else {
                // verplicht NIET null.
                resultaat = verplichtVeld != null
                    && !(verplichtVeld instanceof String && StringUtils.isBlank((String) verplichtVeld));
            }
        } else {
            resultaat = true;
        }
        return resultaat;
    }
}
