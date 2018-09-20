/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import org.junit.Test;

public class PersoonOpschortingHistorieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private  PersoonOpschortingHistorieRepository persoonOpschortingHistorieRepository;

    @Test
    public void testHaalActueleDatumOpschortingOp() {
        Integer opschortingsDatum = persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(2001L);

        Assert.assertEquals(Integer.valueOf(20120101), opschortingsDatum);
    }

    @Test
    public void testHaalActueleDatumOpschortingOpGeenOpschorting() {
        Integer opschortingsDatum = persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(0L);

        Assert.assertNull(opschortingsDatum);
    }
}
