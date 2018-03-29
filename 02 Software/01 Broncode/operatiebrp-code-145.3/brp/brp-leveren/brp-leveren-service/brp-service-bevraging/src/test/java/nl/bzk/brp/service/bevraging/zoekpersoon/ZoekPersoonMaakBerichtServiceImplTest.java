/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoon;

import static org.mockito.Mockito.verify;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonMaakBerichtServiceImplTest {

    @Mock
    private ValideerZoekCriteriaService<ZoekPersoonVraag> valideerZoekPersoonZoekCriteriaService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieValidatieService;
    @Mock
    private PartijService partijService;
    @InjectMocks
    private ZoekPersoonMaakBerichtServiceImpl
            maakZoekPersoonBerichtService =
            new ZoekPersoonMaakBerichtServiceImpl(leveringsautorisatieValidatieService, partijService);

    @Test
    public void testHappyFlow() throws StapException, AutorisatieException {
        final ZoekPersoonVerzoek zoekPersoonVerzoek = new ZoekPersoonVerzoek();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, null);

        maakZoekPersoonBerichtService.valideerDienstSpecifiek(zoekPersoonVerzoek, autorisatiebundel);

        verify(valideerZoekPersoonZoekCriteriaService).valideerZoekCriteria(zoekPersoonVerzoek, autorisatiebundel);
    }
}