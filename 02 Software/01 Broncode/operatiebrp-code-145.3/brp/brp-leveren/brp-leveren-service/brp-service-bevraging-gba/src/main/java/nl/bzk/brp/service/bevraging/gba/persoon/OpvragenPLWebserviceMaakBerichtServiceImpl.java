/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;


import java.util.Collections;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.gba.generiek.AbstractMaakBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.gba.generiek.ValideerGbaAutorisatieService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoon;
import org.springframework.stereotype.Service;


/**
 * Implementatie {@link nl.bzk.brp.service.bevraging.algemeen.Bevraging.MaakBerichtService} voor GBA
 * Opvragen PL.
 */
@Service
public final class OpvragenPLWebserviceMaakBerichtServiceImpl extends AbstractMaakBerichtServiceImpl<OpvragenPLWebserviceVerzoek> {

    /**
     * Constructor.
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     * @param valideerZoekPersoonZoekCriteriaService validatie service voor de zoek criteria in brp
     * @param valideerGbaAutorisatieService valideer gba autorisatie service
     * @param zoekPersoonOphalenPersoonService zoek persoon ophalen service
     */
    @Inject
    public OpvragenPLWebserviceMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService,
                                                      final PartijService partijService,
                                                      final ValideerZoekCriteriaService<OpvragenPLWebserviceVerzoek>
                                                                 valideerZoekPersoonZoekCriteriaService,
                                                      final ValideerGbaAutorisatieService valideerGbaAutorisatieService,
                                                      final ZoekPersoon.OphalenPersoonService<OpvragenPLWebserviceVerzoek>
                                                                 zoekPersoonOphalenPersoonService) {
        super(leveringsautorisatieService,
                partijService,
                valideerZoekPersoonZoekCriteriaService,
                valideerGbaAutorisatieService,
                zoekPersoonOphalenPersoonService);
    }

    @Override
    protected String getDienstSpecifiekeLoggingString() {
        return "Zoek Persoon via Webservice (GBA)";
    }

    @Override
    protected Set<Melding> valideerDienstSpecifiek(final OpvragenPLWebserviceVerzoek verzoek, final Autorisatiebundel autorisatiebundel) {
        final Set<Melding> resultaat = valideerZoekCriteriaService.valideerZoekCriteria(verzoek, autorisatiebundel);
        return Collections.unmodifiableSet(resultaat);
    }
}
