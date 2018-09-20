/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonOpschortingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonOpschortingHistorieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private HistoriePersoonOpschortingRepository historiePersoonOpschortingRepository;

    @Test
    public void testHaalActueleDatumOpschortingOp() {
        PersoonModel pers = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(pers, "iD", 2001);
        Datum opschortingsDatum = historiePersoonOpschortingRepository.haalOpActueleDatumOpschorting(pers);

        Assert.assertEquals(Integer.valueOf(20120101), opschortingsDatum.getWaarde());
    }

    @Test
    public void testHaalActueleDatumOpschortingOpGeenOpschorting() {
        PersoonModel pers = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(pers, "iD", 0);
        Datum opschortingsDatum = historiePersoonOpschortingRepository.haalOpActueleDatumOpschorting(pers);

        Assert.assertNull(opschortingsDatum);
    }
}
