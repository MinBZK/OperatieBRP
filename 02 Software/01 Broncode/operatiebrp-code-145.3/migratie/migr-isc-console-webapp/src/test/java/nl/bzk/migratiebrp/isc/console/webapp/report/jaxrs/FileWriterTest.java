/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.report.jaxrs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class FileWriterTest {

    private final FileWriter subject = new FileWriter();

    @Test
    public void testIsWriteable() {
        Assert.assertTrue(subject.isWriteable(File.class, null, null, null));
        Assert.assertFalse(subject.isWriteable(String.class, null, null, null));
    }

    @Test
    public void testGetSize() {
        final File file = Mockito.mock(File.class);
        Mockito.when(file.length()).thenReturn(150L);

        Assert.assertEquals(150L, subject.getSize(file, File.class, null, null, null));
    }

    @Test
    public void tetWriteTo() throws Exception {
        final File file = File.createTempFile("test", null);
        final FileOutputStream fos = new FileOutputStream(file);
        fos.write(100);
        fos.write(200);
        fos.close();
        file.deleteOnExit();

        final OutputStream output = Mockito.mock(OutputStream.class);

        subject.writeTo(file, File.class, null, null, null, null, output);

        final InOrder inOrder = Mockito.inOrder(output);
        inOrder.verify(output).write(100);
        inOrder.verify(output).write(200);
        inOrder.verifyNoMoreInteractions();

    }
}
