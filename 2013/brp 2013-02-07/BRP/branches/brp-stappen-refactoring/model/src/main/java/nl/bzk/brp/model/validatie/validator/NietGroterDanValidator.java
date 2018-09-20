/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.validatie.constraint.NietGroterDan;


/**
 * De waarde van datum/tijdontlening van de Actie mag niet na de waarde van datum/tijdregistratie van de Actie liggen.
 *
 * @brp.bedrijfsregel BRAL9010
 */
public class NietGroterDanValidator implements ConstraintValidator<NietGroterDan, Object> {

    private String veld;
    private String nietGroterDanVeld;

    @Override
    public void initialize(final NietGroterDan constraintAnnotation) {
        veld = constraintAnnotation.veld();
        nietGroterDanVeld = constraintAnnotation.nietGroterDanVeld();
    }

    @Override
    public boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        boolean resultaat;

        try {
            Object veldObject = ValidatorUtil.haalWaardeOp(waarde, veld);
            Object nietGroterDanVeldObject = ValidatorUtil.haalWaardeOp(waarde, nietGroterDanVeld);

            if (veldObject == null && nietGroterDanVeldObject != null) {
                resultaat = false;
            } else if (veldObject != null && nietGroterDanVeldObject == null) {
                resultaat = false;
            } else if (veldObject == null && nietGroterDanVeldObject == null) {
                resultaat = true;
            } else if (veldObject instanceof DatumTijd && nietGroterDanVeldObject instanceof DatumTijd) {
                if (((DatumTijd) veldObject).na(((DatumTijd) nietGroterDanVeldObject))) {
                    resultaat = false;
                } else {
                    resultaat = true;
                }
            } else {
                throw new IllegalArgumentException("Een van de twee objecten is niet van het type DatumTijd");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return resultaat;
    }
}
