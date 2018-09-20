/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Test;

public class SearchTest extends SyncTest {
    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie1a.xml" })
    @Test
    public void testSituatie1a() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, null, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.TOEVOEGEN, result.getResultaat());
        Assert.assertNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie1b.xml", "SearchSituatie1bHist.xml" })
    @Test
    public void testSituatie1b() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, null, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.NEGEREN, result.getResultaat());
        Assert.assertNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie2.xml", "SearchSituatie2Hist.xml" })
    @Test
    public void testSituatie2() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, null, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.VERVANGEN, result.getResultaat());
        Assert.assertNotNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie1a.xml" })
    @Test
    public void testSituatie3a() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, 4324324324L, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.TOEVOEGEN, result.getResultaat());
        Assert.assertNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie1b.xml", "SearchSituatie1bHist.xml" })
    @Test
    public void testSituatie3b() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, 4324324324L, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.NEGEREN, result.getResultaat());
        Assert.assertNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie2.xml", "SearchSituatie2Hist.xml" })
    @Test
    public void testSituatie4() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, 4324324324L, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.VERVANGEN, result.getResultaat());
        Assert.assertNotNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie5.xml", "SearchSituatie5Hist.xml",
            "SearchSituatie1a.xml" })
    @Test
    public void testSituatie5a() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, 4324324324L, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.ONDUIDELIJK, result.getResultaat());
        Assert.assertNotNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie5.xml", "SearchSituatie5Hist.xml",
            "SearchSituatie1a.xml" })
    @Test
    public void testSituatie5b() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, 4324324324L, 333444555L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.VERVANGEN, result.getResultaat());
        Assert.assertNotNull(result.getLo3Persoonslijst());
    }

    @InsertBefore({ "/sql/data/dbunitStamgegevens.xml", "SearchSituatie5.xml", "SearchSituatie5Hist.xml",
            "SearchSituatie2.xml", "SearchSituatie2Hist.xml" })
    @Test
    public void testSituatie6() {
        final SynchronisatieStrategieVerzoekBericht bericht = new SynchronisatieStrategieVerzoekBericht(1234567890L, 4324324324L, 123456789L);

        final SynchronisatieStrategieAntwoordBericht result = (SynchronisatieStrategieAntwoordBericht) handleBericht(bericht);
        System.out.println(result.format());

        Assert.assertEquals(StatusType.OK, result.getStatus());
        Assert.assertEquals(SearchResultaatType.VERVANGEN, result.getResultaat());
        Assert.assertNotNull(result.getLo3Persoonslijst());
    }
}
