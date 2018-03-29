/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

public class BrpNuTest {

    private static final ZonedDateTime zdt =
            ZonedDateTime.of(2017, 12, 05, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);

    @Test
    public void testGet() {
        BrpNu.set(zdt);
        assertEquals(zdt, BrpNu.get().getDatum());
    }

    @Test
    public void testGet_BrpNuNietGezet() throws InterruptedException {
        Exception exception = null;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(() -> {
            BrpNu.get();
            return null;
        });
        try {
            future.get();
        } catch (ExecutionException e) {
            exception = e;
        }
        executor.shutdown();
        executor.awaitTermination(500, TimeUnit.MILLISECONDS);

        Assert.assertTrue(exception.getCause() instanceof NullPointerException);

    }

    @Test
    public void testSetZonderParam() {
        BrpNu.set();
        assertNotNull(BrpNu.get());
    }

    @Test
    public void testDatumAlsInteger() {
        BrpNu.set(zdt);
        assertEquals(20171205, BrpNu.get().alsIntegerDatumNederland().intValue());
    }
}
