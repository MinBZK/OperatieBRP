/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.adres;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVraag;
import org.springframework.stereotype.Service;

@Service
class AdresvraagVerzoekCriteriaValidator implements ValideerZoekCriteriaService<AdresvraagVerzoek> {

    private final ValideerZoekCriteriaService<ZoekPersoonOpAdresVraag> validator;

    @Inject
    AdresvraagVerzoekCriteriaValidator(ValideerZoekCriteriaService<ZoekPersoonOpAdresVraag> validator) {
        this.validator = validator;
    }

    @Override
    public Set<Melding> valideerZoekCriteria(final AdresvraagVerzoek bevragingVerzoek, final Autorisatiebundel autorisatiebundel) {
        return validator.valideerZoekCriteria(bevragingVerzoek, autorisatiebundel);
    }
}
