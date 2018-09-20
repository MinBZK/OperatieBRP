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
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonOpschortingHistorieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private  PersoonOpschortingHistorieRepository persoonOpschortingHistorieRepository;

    @Test
    public void testHaalActueleDatumOpschortingOp() {
        PersoonModel pers = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(pers, "id", 2001);
        Datum opschortingsDatum = persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(pers);

        Assert.assertEquals(Integer.valueOf(20120101), opschortingsDatum.getWaarde());
    }

    @Test
    public void testHaalActueleDatumOpschortingOpGeenOpschorting() {
        PersoonModel pers = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(pers, "id", 0);
        Datum opschortingsDatum = persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(pers);

        Assert.assertNull(opschortingsDatum);
    }
}
