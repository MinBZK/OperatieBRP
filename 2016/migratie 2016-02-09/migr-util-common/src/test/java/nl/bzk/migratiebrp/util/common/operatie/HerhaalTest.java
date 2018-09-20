/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.operatie;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.Assert;
import org.junit.Test;

public class HerhaalTest {

    public static final String MESSAGE_1 = "message_1";
    public static final String MESSAGE_2 = "message_2";
    private final AtomicInteger teller = new AtomicInteger(0);

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            teller.incrementAndGet();
            throw new IllegalStateException("Test exception");
        }
    };

    private final Callable<Integer> callable = new Callable<Integer>() {
        @Override
        public Integer call() throws InterruptedException {
            final int count = teller.incrementAndGet();
            if (count == 11) {
                return 42;
            } else {
                throw new InterruptedException("Test exception");
            }
        }
    };

    @Test
    public void testAltijdFaalStandaard() {
        teller.set(0);
        try {
            Herhaal.herhaalOperatie(runnable);
            Assert.fail("Operatie faalt altijd");
        } catch (final HerhaalException e) {
            Assert.assertEquals(Herhaal.STANDAARD_MAXIMUM_AANTAL_POGINGEN, teller.get());
        }
    }

    @Test
    public void testAltijdFaalLineair() {
        final Herhaal herhaal = new Herhaal(10, 5, Herhaal.Strategie.LINEAIR);
        teller.set(0);
        try {
            herhaal.herhaal(runnable);
            Assert.fail("Operatie faalt altijd");
        } catch (final HerhaalException e) {
            Assert.assertEquals(5, teller.get());
            Assert.assertEquals("Herhaalde operatie gefaald na 5 pogingen. Laatste fout: Test exception", e.getMessage());
        }
    }

    @Test
    public void testCallableFaalRandom() {
        final Herhaal herhaal = new Herhaal(100, 10, Herhaal.Strategie.WILLEKEURIG);
        teller.set(0);
        try {
            final int result = herhaal.herhaal(callable);
            Assert.fail("Operatie faalt altijd de eerste 10 keer");
            Assert.assertEquals(42, result);
        } catch (final HerhaalException e) {
            Assert.assertEquals(10, teller.get());
            Assert.assertEquals(10, e.getPogingExcepties().size());
        }
    }

    @Test
    public void testCallableFaalStandaard() {
        teller.set(0);
        try {
            final int result = Herhaal.herhaalOperatie(callable);
            Assert.fail("Operatie faalt altijd de eerste 10 keer");
            Assert.assertEquals(42, result);
        } catch (final HerhaalException e) {
            Assert.assertEquals(10, teller.get());
        }
    }

    @Test
    public void testCallableOkNa11Exponentieel() {
        final Herhaal herhaal = new Herhaal(1, 11, Herhaal.Strategie.EXPONENTIEEL);
        teller.set(0);
        try {
            final int result = herhaal.herhaal(callable);
            Assert.assertEquals(11, teller.get());
            Assert.assertEquals(42, result);
        } catch (final HerhaalException e) {
            Assert.fail("Operatie moet 11de keer werken");
        }
    }

    @Test
    public void testHerhaalExceptionPrintToStream() {
        final Exception e1 = new IllegalArgumentException(MESSAGE_1);
        final Exception e2 = new IllegalStateException(MESSAGE_2);
        final List<Exception> exceptionList = new ArrayList<>();
        exceptionList.add(e1);
        exceptionList.add(e2);
        final HerhaalException herhaalException = new HerhaalException(exceptionList);
        final ByteArrayOutputStream streamBytes = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(streamBytes);
        herhaalException.printStackTrace(printStream);
        printStream.close();
        final String printedText = new String(streamBytes.toByteArray());
        Assert.assertTrue(printedText.contains(MESSAGE_1));
        Assert.assertTrue(printedText.contains(MESSAGE_2));
        Assert.assertTrue(printedText.contains("HerhaalException"));
        Assert.assertTrue(printedText.contains("IllegalArgumentException"));
        Assert.assertTrue(printedText.contains("IllegalStateException"));
    }

    @Test
    public void testHerhaalExceptionPrintToWriter() {
        final Exception e1 = new IllegalArgumentException(MESSAGE_1);
        final Exception e2 = new IllegalStateException(MESSAGE_2);
        final List<Exception> exceptionList = new ArrayList<>();
        exceptionList.add(e1);
        exceptionList.add(e2);
        final HerhaalException herhaalException = new HerhaalException(exceptionList);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        herhaalException.printStackTrace(printWriter);
        printWriter.close();
        final String printedText = stringWriter.toString();
        Assert.assertTrue(printedText.contains(MESSAGE_1));
        Assert.assertTrue(printedText.contains(MESSAGE_2));
        Assert.assertTrue(printedText.contains("HerhaalException"));
        Assert.assertTrue(printedText.contains("IllegalArgumentException"));
        Assert.assertTrue(printedText.contains("IllegalStateException"));
    }
}
