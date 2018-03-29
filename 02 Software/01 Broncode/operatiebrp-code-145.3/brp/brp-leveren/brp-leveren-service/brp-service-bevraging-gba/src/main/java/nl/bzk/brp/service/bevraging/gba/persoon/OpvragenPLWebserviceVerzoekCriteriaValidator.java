/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVraag;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import org.springframework.stereotype.Service;

@Service
class OpvragenPLWebserviceVerzoekCriteriaValidator implements ValideerZoekCriteriaService<OpvragenPLWebserviceVerzoek> {

    private final ValideerZoekCriteriaService<ZoekPersoonVraag> validator;

    @Inject
    OpvragenPLWebserviceVerzoekCriteriaValidator(ValideerZoekCriteriaService<ZoekPersoonVraag> validator) {
        this.validator = validator;
    }

    @Override
    public Set<Melding> valideerZoekCriteria(final OpvragenPLWebserviceVerzoek verzoek, final Autorisatiebundel autorisatiebundel) {
        return validator.valideerZoekCriteria(verzoek, autorisatiebundel);
    }
}
