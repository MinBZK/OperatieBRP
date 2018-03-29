/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Sets;
import java.util.Date;
import org.junit.Test;

public class SelectieJobRunStatusTest {

    @Test
    public void test_isStopped() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        selectieJobRunStatus.setEindeDatum(new Date());
        assertTrue(selectieJobRunStatus.isStopped());
    }

    @Test
    public void test_incrementAndGetVerwerkTaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(1, selectieJobRunStatus.incrementEnGetVerwerkTaken());
    }

    @Test
    public void test_incrementAndGetVerwerkKlaarTaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(1, selectieJobRunStatus.incrementEnGetVerwerkKlaarTaken());
    }

    @Test
    public void test_GetVerwerkTakenKlaarCount() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(0, selectieJobRunStatus.getVerwerkTakenKlaarCount());
        selectieJobRunStatus.incrementEnGetVerwerkKlaarTaken();
        assertEquals(1, selectieJobRunStatus.getVerwerkTakenKlaarCount());

    }

    @Test
    public void test_GetSelectieTaakCount() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(0, selectieJobRunStatus.getSelectieTaakCount());
        selectieJobRunStatus.incrementEnGetVerwerkTaken();
        assertEquals(1, selectieJobRunStatus.getSelectieTaakCount());
    }

    @Test
    public void test_incrementAndGetSchrijfTaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(99, selectieJobRunStatus.incrementEnGetSchrijfTaken(99));
        assertEquals(100, selectieJobRunStatus.incrementEnGetSchrijfTaken(1));
    }

    @Test
    public void test_incrementAndGetSchrijfKlaarTaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(1, selectieJobRunStatus.incrementEnGetSchrijfKlaarTaken());
    }

    @Test
    public void test_GetSchrijfTaakKlaarCount() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(0, selectieJobRunStatus.getSchrijfTakenKlaarCount());
        selectieJobRunStatus.incrementEnGetSchrijfKlaarTaken();
        assertEquals(1, selectieJobRunStatus.getSchrijfTakenKlaarCount());
    }

    @Test
    public void test_Startdatum() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();
        final Date date = new Date();
        assertNull(selectieJobRunStatus.getStartDatum());
        selectieJobRunStatus.setStartDatum(date);
        assertEquals(date, selectieJobRunStatus.getStartDatum());
    }

    @Test
    public void test_Einddatum() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();
        final Date date = new Date();
        assertNull(selectieJobRunStatus.getEindeDatum());

        selectieJobRunStatus.setEindeDatum(date);
        assertEquals(date, selectieJobRunStatus.getEindeDatum());
    }

    @Test
    public void test_TotaalAantalSelectieTaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(0, selectieJobRunStatus.getTotaalAantalSelectieTaken());
        selectieJobRunStatus.setTotaalAantalSelectieTaken(99);
        assertEquals(99, selectieJobRunStatus.getTotaalAantalSelectieTaken());
    }

    @Test
    public void test_incrementAndGetResultaatSchrijfTaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(99, selectieJobRunStatus.getSelectieResultaatSchrijfTaakIncrement(99));
        assertEquals(100, selectieJobRunStatus.getSelectieResultaatSchrijfTaakIncrement(1));
    }

    @Test
    public void test_getSelectieResultaatSchrijfTaakCount() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(0, selectieJobRunStatus.getSelectieResultaatSchrijfTaakCount());
    }

    @Test
    public void test_addandGetAantalPlaatsAfnemerindicatietaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(99, selectieJobRunStatus.addEnGetAantalPlaatsAfnemerindicatieTaken(99));
        assertEquals(100, selectieJobRunStatus.addEnGetAantalPlaatsAfnemerindicatieTaken(1));
        assertEquals(100, selectieJobRunStatus.getAantalPlaatsAfnemerindicatieTaken());
    }

    @Test
    public void test_moetStoppen(){
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertFalse(selectieJobRunStatus.moetStoppen());
        selectieJobRunStatus.setMoetStoppen(true);
        assertTrue(selectieJobRunStatus.moetStoppen());
    }

    @Test
    public void test_selectieRunId(){
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertNull(selectieJobRunStatus.getSelectieRunId());
        selectieJobRunStatus.setSelectieRunId(1);
        assertEquals(1, selectieJobRunStatus.getSelectieRunId().intValue());
    }

    @Test
    public void test_error(){
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertFalse(selectieJobRunStatus.isError());
        selectieJobRunStatus.setError(true);
        assertTrue(selectieJobRunStatus.isError());
    }

    @Test
    public void test_incrementAfnemerindicatieTaakVerwerkt(){
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertTrue(selectieJobRunStatus.getAfnemerindicatieTaakNaarAantalVerwerktePersonen().isEmpty());
        selectieJobRunStatus.incrementAfnemerindicatieTaakVerwerkt(1);
        assertTrue(selectieJobRunStatus.getAfnemerindicatieTaakNaarAantalVerwerktePersonen().containsKey(1));
        assertEquals(1, selectieJobRunStatus.getAfnemerindicatieTaakNaarAantalVerwerktePersonen().get(1).get());
        assertEquals(1, selectieJobRunStatus.getAantalPlaatsAfnemerindicatieKlaarTaken());
    }

    @Test
    public void test_verwerktePersonenPerAfnemertaak(){
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertTrue(selectieJobRunStatus.getAfnemerindicatieTaakNaarAantalVerwerktePersonen().isEmpty());
        assertEquals(0, selectieJobRunStatus.getVerwerktePersonenPerAfnemerindicatieTaak(1).intValue());
        selectieJobRunStatus.incrementAfnemerindicatieTaakVerwerkt(1);
        assertTrue(selectieJobRunStatus.getAfnemerindicatieTaakNaarAantalVerwerktePersonen().containsKey(1));
        assertEquals(1, selectieJobRunStatus.getVerwerktePersonenPerAfnemerindicatieTaak(1).intValue());
    }


    @Test
    public void test_OngeldigeSelectietaken() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertEquals(0, selectieJobRunStatus.getOngeldigeSelectietaken().size());
        selectieJobRunStatus.voegOngeldigeSelectietakenToe(null);
        assertEquals(0, selectieJobRunStatus.getOngeldigeSelectietaken().size());
        selectieJobRunStatus.voegOngeldigeSelectietakenToe(Sets.newHashSet(1, 2));
        assertEquals(2, selectieJobRunStatus.getOngeldigeSelectietaken().size());
    }

    @Test
    public void schrijvenKlaar() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertTrue(selectieJobRunStatus.schrijvenKlaar());

        selectieJobRunStatus.incrementEnGetSchrijfKlaarTaken();
        assertFalse(selectieJobRunStatus.schrijvenKlaar());
    }

    @Test
    public void klaar() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        assertTrue(selectieJobRunStatus.klaar());

        selectieJobRunStatus.incrementEnGetSelectieResultaatSchrijfTakenIncrement();
        assertFalse(selectieJobRunStatus.klaar());
    }
}