/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.brp.model.beheer.kern.Partij;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Partij business regels.
 */
@Component
public final class PartijValidator implements Validator {

    private static final String VELD_NAAM = "naam";
    private static final String VELD_CODE = "code";
    private static final String VELD_DATUM_INGANG = "datumIngang";
    private static final String VELD_DATUM_EINDE = "datumEinde";

    @Override
    public boolean supports(final Class<?> clazz) {
        return Partij.class.isAssignableFrom(clazz);
    }

    /**
     * Valideer de partij business regels.
     *
     * @param target de te valideren partij
     * @param errors het errors object om errors in te registreren
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        final Partij partij = (Partij) target;
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, partij.getNaam(), VELD_NAAM);
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, partij.getCode(), VELD_CODE);
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, partij.getDatumIngang(), VELD_DATUM_INGANG);
        ValidatieUtils.valideerDatumLigtNaDatum(errors, partij.getDatumIngang(), partij.getDatumEinde(), VELD_DATUM_INGANG, VELD_DATUM_EINDE);
    }
}
