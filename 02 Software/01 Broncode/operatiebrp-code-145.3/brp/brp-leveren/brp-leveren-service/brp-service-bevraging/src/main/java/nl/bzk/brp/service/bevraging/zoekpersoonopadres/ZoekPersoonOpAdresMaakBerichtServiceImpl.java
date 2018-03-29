/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoonopadres;


import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonMaakBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoon;
import org.springframework.stereotype.Service;


/**
 * Implementatie {@link nl.bzk.brp.service.bevraging.algemeen.Bevraging.MaakBerichtService} voor Zoek Persoon.
 */
@Service
final class ZoekPersoonOpAdresMaakBerichtServiceImpl extends AbstractZoekPersoonMaakBerichtServiceImpl<ZoekPersoonOpAdresVerzoek> {
    private ValideerZoekCriteriaService<ZoekPersoonOpAdresVraag> valideerZoekPersoonOpAdresZoekCriteriaService;
    private ZoekPersoon.OphalenPersoonService<ZoekPersoonOpAdresVerzoek> zoekPersoonOpAdresOphalenPersoonService;

    /**
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     */
    @Inject
    ZoekPersoonOpAdresMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService, final PartijService partijService) {
        super(leveringsautorisatieService, partijService);
    }

    @Override
    protected String getDienstSpecifiekeLoggingString() {
        return "Zoek Persoon Op Adres";
    }

    @Override
    protected ZoekPersoon.OphalenPersoonService<ZoekPersoonOpAdresVerzoek> getOphalenPersoonService() {
        return zoekPersoonOpAdresOphalenPersoonService;
    }

    @Override
    protected Set<Melding> valideerDienstSpecifiek(final ZoekPersoonOpAdresVerzoek verzoek, final Autorisatiebundel autorisatiebundel) {
        return valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(verzoek, autorisatiebundel);
    }

    @Inject
    void setValideerZoekPersoonOpAdresZoekCriteriaService(
            final ValideerZoekCriteriaService<ZoekPersoonOpAdresVraag> valideerZoekPersoonOpAdresZoekCriteriaService) {
        this.valideerZoekPersoonOpAdresZoekCriteriaService = valideerZoekPersoonOpAdresZoekCriteriaService;
    }

    @Inject
    void setZoekPersoonOpAdresOphalenPersoonService(
            final ZoekPersoon.OphalenPersoonService<ZoekPersoonOpAdresVerzoek> zoekPersoonOpAdresOphalenPersoonService) {
        this.zoekPersoonOpAdresOphalenPersoonService = zoekPersoonOpAdresOphalenPersoonService;
    }
}
