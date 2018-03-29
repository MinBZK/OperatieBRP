/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.Sets;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakMutatiehandelingServiceImplTest {

    private static final long ADMINISTRATIEVE_HANDELING_ID = 1L;

    @InjectMocks
    private MaakMutatiehandelingServiceImpl service;

    @Mock
    private PersoonslijstService persoonslijstService;

    @Test
    public void testHapppyflow() throws StapException {

        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(ADMINISTRATIEVE_HANDELING_ID);
        handelingVoorPublicatie.setBijgehoudenPersonen(Sets.newHashSet(1L, 2L));

        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetHandelingen(1);
        final Persoonslijst persoonslijst2 = TestBuilders.maakPersoonMetHandelingen(2);

        Mockito.when(persoonslijstService.getById(1)).thenReturn(persoonslijst);
        Mockito.when(persoonslijstService.getById(2)).thenReturn(persoonslijst2);

        final Mutatiehandeling mutatiehandeling = service.maakHandeling(handelingVoorPublicatie);

        Mockito.verify(persoonslijstService).getById(1L);
        Mockito.verify(persoonslijstService).getById(2L);

        Assert.assertEquals(ADMINISTRATIEVE_HANDELING_ID, mutatiehandeling.getAdministratieveHandelingId());
        Assert.assertNotNull(mutatiehandeling.getPersonen());
        Assert.assertNotNull(mutatiehandeling.getPersoonsgegevensMap());
    }
}
