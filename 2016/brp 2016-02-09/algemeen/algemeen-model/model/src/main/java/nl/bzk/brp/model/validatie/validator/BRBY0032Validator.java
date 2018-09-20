/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.constraint.BRBY0032;


/**
 * BRBY0032: Positieve duur geldigheid.
 * <p/>
 * Datum einde geldigheid van de Actie moet, indien gevuld, liggen na Datum aanvang geldigheid van de Actie.
 *
 * @brp.bedrijfsregel BRBY0032
 */
public class BRBY0032Validator implements ConstraintValidator<BRBY0032, Actie> {

    @Override
    public final void initialize(final BRBY0032 constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final Actie actie, final ConstraintValidatorContext context) {
        return !(actie.getDatumAanvangGeldigheid() != null && actie.getDatumEindeGeldigheid() != null && !actie
            .getDatumEindeGeldigheid().na(actie.getDatumAanvangGeldigheid()));
    }
}
