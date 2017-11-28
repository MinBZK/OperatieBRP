/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoon;


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
final class ZoekPersoonMaakBerichtServiceImpl extends AbstractZoekPersoonMaakBerichtServiceImpl<ZoekPersoonVerzoek> {
    private ValideerZoekCriteriaService<ZoekPersoonVraag> valideerZoekPersoonZoekCriteriaService;
    private ZoekPersoon.OphalenPersoonService<ZoekPersoonVerzoek> zoekPersoonOphalenPersoonService;

    /**
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     */
    @Inject
    ZoekPersoonMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService, final PartijService partijService) {
        super(leveringsautorisatieService, partijService);
    }

    @Override
    protected String getDienstSpecifiekeLoggingString() {
        return "Zoek Persoon";
    }

    @Override
    protected ZoekPersoon.OphalenPersoonService<ZoekPersoonVerzoek> getOphalenPersoonService() {
        return zoekPersoonOphalenPersoonService;
    }

    @Override
    protected Set<Melding> valideerDienstSpecifiek(final ZoekPersoonVerzoek verzoek, final Autorisatiebundel autorisatiebundel) {
        return valideerZoekPersoonZoekCriteriaService.valideerZoekCriteria(verzoek, autorisatiebundel);
    }

    /**
     * @param valideerZoekPersoonZoekCriteriaService valideerZoekPersoonZoekCriteriaService
     */
    @Inject
    void setValideerZoekPersoonZoekCriteriaService(
            final ValideerZoekCriteriaService<ZoekPersoonVraag> valideerZoekPersoonZoekCriteriaService) {
        this.valideerZoekPersoonZoekCriteriaService = valideerZoekPersoonZoekCriteriaService;
    }

    /**
     * @param zoekPersoonOphalenPersoonService zoekPersoonOphalenPersoonService
     */
    @Inject
    public void setZoekPersoonOphalenPersoonService(
            final ZoekPersoon.OphalenPersoonService<ZoekPersoonVerzoek> zoekPersoonOphalenPersoonService) {
        this.zoekPersoonOphalenPersoonService = zoekPersoonOphalenPersoonService;
    }
}
