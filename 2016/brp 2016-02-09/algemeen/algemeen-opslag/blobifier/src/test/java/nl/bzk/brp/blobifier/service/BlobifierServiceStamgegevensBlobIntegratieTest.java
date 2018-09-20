/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import org.junit.Test;

public class BlobifierServiceStamgegevensBlobIntegratieTest extends AbstractDBUnitIntegratieTest {

    @Inject
    private BlobifierService blobifierService;

    @Test
    public void leesAlsCacheBestaatEnControleerOfStamgegevenGevuldZijn() throws IOException {
        final int persoonId = 2;

        //Haal cache op
        final PersoonHisVolledig persoonUitCache = blobifierService.leesBlob(persoonId);

        // Alleen de code is gevuld, niet de naam. We serialiseren namelijk alleen nog logische
        // identiteit attributen (= codes) en niet meer alle data van stamgegevens.
        final PersoonNationaliteitHisVolledig nationaliteitHisVolledig = persoonUitCache.getNationaliteiten().iterator().next();

        assertNotNull(nationaliteitHisVolledig.getNationaliteit().getWaarde().getCode());
        assertNull(nationaliteitHisVolledig.getNationaliteit().getWaarde().getNaam());
    }

}
