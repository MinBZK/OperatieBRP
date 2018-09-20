/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.brp.beheer.webapp.configuratie.json.modules.LeveringsautorisatieModule;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Abonnement businessregels.
 */
@Component
public final class LeveringsautorisatieValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return Leveringsautorisatie.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final Leveringsautorisatie leveringsautorisatie = (Leveringsautorisatie) target;
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, leveringsautorisatie.getNaam(), LeveringsautorisatieModule.NAAM);
        ValidatieUtils.valideerVerplichtVeldAttribuut(errors, leveringsautorisatie.getDatumIngang(), LeveringsautorisatieModule.DATUM_INGANG);
        ValidatieUtils.valideerVerplichtVeld(errors, leveringsautorisatie.getProtocolleringsniveau(), LeveringsautorisatieModule.PROTOCOLLERINGSNIVEAU);
        ValidatieUtils.valideerDatumLigtNaDatum(
            errors,
            leveringsautorisatie.getDatumIngang(),
            leveringsautorisatie.getDatumEinde(),
            LeveringsautorisatieModule.DATUM_INGANG,
            LeveringsautorisatieModule.DATUM_EINDE);
    }

}
