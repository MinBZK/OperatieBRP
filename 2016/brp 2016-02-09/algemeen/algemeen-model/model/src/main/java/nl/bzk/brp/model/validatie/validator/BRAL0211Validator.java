/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.validatie.constraint.BRAL0211;


/**
 * De lengte van de concatenatie van alle actuele exemplaren van Voornaam van een Persoon gescheiden door spaties, mag niet langer dan 200 karakters zijn.
 *
 * @brp.bedrijfsregel BRAL0211
 */
public class BRAL0211Validator implements ConstraintValidator<BRAL0211, Collection<? extends PersoonVoornaam>> {

    private static final int MAXSIZE = 200;

    @Override
    public final void initialize(final BRAL0211 constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final Collection<? extends PersoonVoornaam> collectie,
        final ConstraintValidatorContext context)
    {
        final StringBuilder geconcateneerdeNamen = new StringBuilder();

        if (collectie != null && collectie.size() > 1) {
            for (final PersoonVoornaam voornaam : collectie) {
                if (voornaam != null && voornaam.getStandaard() != null && voornaam.getStandaard().getNaam() != null
                    && voornaam.getStandaard().getNaam().getWaarde() != null)
                {
                    geconcateneerdeNamen.append(" ").append(voornaam.getStandaard().getNaam().getWaarde());
                }
            }
        }

        // -1 vanwege de extra spatie
        return geconcateneerdeNamen.length() - 1 <= MAXSIZE;
    }
}
