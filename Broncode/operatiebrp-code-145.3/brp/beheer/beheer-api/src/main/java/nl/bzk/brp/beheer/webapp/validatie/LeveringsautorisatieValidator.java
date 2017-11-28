/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.LeveringsautorisatieModule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Abonnement businessregels.
 */
@Component
public final class LeveringsautorisatieValidator extends GenericValidator<Leveringsautorisatie> {

    /**
     * Default constructor.
     */
    public LeveringsautorisatieValidator() {
        super(Leveringsautorisatie.class);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final Leveringsautorisatie leveringsautorisatie = (Leveringsautorisatie) target;
        ValidatieUtils.valideerVerplichtVeld(errors, leveringsautorisatie.getNaam(), LeveringsautorisatieModule.NAAM);
        ValidatieUtils.valideerVerplichtVeld(errors, leveringsautorisatie.getStelsel(), LeveringsautorisatieModule.STELSEL);
        ValidatieUtils.valideerVerplichtVeld(errors, leveringsautorisatie.getProtocolleringsniveau(), LeveringsautorisatieModule.PROTOCOLLERINGSNIVEAU);
        super.validate(target, errors);
    }

}
