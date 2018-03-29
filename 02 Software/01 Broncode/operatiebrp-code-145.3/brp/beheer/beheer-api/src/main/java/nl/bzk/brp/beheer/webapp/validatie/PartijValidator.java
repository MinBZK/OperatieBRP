/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Partij business regels.
 */
@Component
public final class PartijValidator extends GenericValidator<Partij> {

    private static final String VELD_NAAM = "naam";

    /**
     * Default constructor.
     */
    public PartijValidator() {
        super(Partij.class);
    }

    /**
     * Valideer de partij business regels.
     * @param target de te valideren partij
     * @param errors het errors object om errors in te registreren
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        final Partij partij = (Partij) target;
        ValidatieUtils.valideerVerplichtVeld(errors, partij.getNaam(), VELD_NAAM);
        super.validate(target, errors);
    }
}
