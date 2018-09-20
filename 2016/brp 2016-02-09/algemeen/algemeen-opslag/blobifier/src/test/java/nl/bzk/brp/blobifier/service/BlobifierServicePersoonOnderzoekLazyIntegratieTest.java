/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Test;

public class BlobifierServicePersoonOnderzoekLazyIntegratieTest extends AbstractDBUnitIntegratieTest {

    @Inject
    private BlobifierService blobifierService;

    @Test
    public void leesAlsCacheBestaatEnControleerOfBetrokkenenLazyGeladenWorden() {
        final int persoonId = 1;
        blobifierService.blobify(persoonId, true);

        //Haal cache op
        final PersoonHisVolledig persoonUitCache = blobifierService.leesBlob(persoonId);

        assertNotNull(getEersteBetrokkene(persoonUitCache).getPersoonIdentificatienummersHistorie());
        assertNotNull(getEersteBetrokkene(persoonUitCache).getPersoonGeboorteHistorie());

        assertEquals(2, persoonUitCache.getOnderzoeken().iterator().next().getOnderzoek().getPersonenInOnderzoek().size());
    }

    private PersoonHisVolledig getEersteBetrokkene(final PersoonHisVolledig persoonUitCache) {
        return persoonUitCache.getOnderzoeken().iterator().next().getOnderzoek().getPersonenInOnderzoek().iterator().next().getPersoon();
    }

}
