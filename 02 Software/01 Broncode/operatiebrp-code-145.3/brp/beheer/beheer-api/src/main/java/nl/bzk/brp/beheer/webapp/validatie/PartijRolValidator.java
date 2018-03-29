/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Partijrol business regels.
 */
@Component
public final class PartijRolValidator extends GenericValidator<PartijRol> {

    private static final String VELD_ROL = "rol";

    /**
     * Default constructor.
     */
    public PartijRolValidator() {
        super(PartijRol.class);
    }

    /**
     * Valideer de partijrol business regels.
     * @param target de te valideren partijrol
     * @param errors het errors object om errors in te registreren
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        final PartijRol partijRol = (PartijRol) target;
        ValidatieUtils.valideerVerplichtVeld(errors, partijRol.getRol(), VELD_ROL);
        super.validate(target, errors);
    }
}
