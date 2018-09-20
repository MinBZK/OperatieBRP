/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.validatie.constraint.NietGroterDan;


/**
 * De waarde van datum/tijdontlening van de Actie mag niet na de waarde van datum/tijdregistratie van de Actie liggen.
 *
 * @brp.bedrijfsregel BRAL9010
 */
public final class NietGroterDanValidator implements ConstraintValidator<NietGroterDan, Object> {

    private String veld;
    private String nietGroterDanVeld;

    @Override
    public void initialize(final NietGroterDan constraintAnnotation) {
        veld = constraintAnnotation.veld();
        nietGroterDanVeld = constraintAnnotation.nietGroterDanVeld();
    }

    @Override
    public boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        final boolean resultaat;

        final Object veldObject;
        final Object nietGroterDanVeldObject;
        try {
            veldObject = ValidatorUtil.haalWaardeOp(waarde, veld);
            nietGroterDanVeldObject = ValidatorUtil.haalWaardeOp(waarde, nietGroterDanVeld);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        }

        if (veldObject == null && nietGroterDanVeldObject != null) {
            resultaat = false;
        } else if (veldObject != null && nietGroterDanVeldObject == null) {
            resultaat = false;
        } else if (veldObject == null && nietGroterDanVeldObject == null) {
            resultaat = true;
        } else if (veldObject instanceof DatumTijdAttribuut
            && nietGroterDanVeldObject instanceof DatumTijdAttribuut)
        {
            resultaat = !((DatumTijdAttribuut) veldObject).na((DatumTijdAttribuut) nietGroterDanVeldObject);
        } else {
            throw new IllegalArgumentException("Een van de twee objecten is niet van het type DatumTijd");
        }

        return resultaat;
    }
}
