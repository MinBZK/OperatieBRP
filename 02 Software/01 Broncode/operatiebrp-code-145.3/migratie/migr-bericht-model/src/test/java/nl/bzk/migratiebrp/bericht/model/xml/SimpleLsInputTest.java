/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class SimpleLsInputTest {

    private static final String BASE_URL = "http://www.w3.org/2001/XMLSchema";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String PUBLIC_ID = "BZKBRP";
    private static final String SYSTEM_ID = "BRP";
    private static final String STRING_DATA = "data";
    private static final InputStream INPUT_STREAM = new ByteArrayInputStream(STRING_DATA.getBytes());

    @Test
    public void testSetters() {

        final SimpleLSInput simpleLsInput = new SimpleLSInput(null, null, null);
        Assert.assertNull(simpleLsInput.getStringData());

        simpleLsInput.setBaseURI(BASE_URL);
        simpleLsInput.setByteStream(INPUT_STREAM);
        simpleLsInput.setCertifiedText(true);
        simpleLsInput.setCharacterStream(null);
        simpleLsInput.setEncoding(CHARACTER_ENCODING);
        simpleLsInput.setInputStream(new BufferedInputStream(INPUT_STREAM));
        simpleLsInput.setPublicId(PUBLIC_ID);
        simpleLsInput.setStringData(STRING_DATA);
        simpleLsInput.setSystemId(SYSTEM_ID);

        Assert.assertNull(simpleLsInput.getBaseURI());
        Assert.assertNull(simpleLsInput.getByteStream());
        Assert.assertEquals(simpleLsInput.getCertifiedText(), false);
        Assert.assertNull(simpleLsInput.getCharacterStream());
        Assert.assertNull(simpleLsInput.getEncoding());
        Assert.assertNotNull(simpleLsInput.getInputStream());
        Assert.assertEquals(simpleLsInput.getPublicId(), PUBLIC_ID);
        Assert.assertEquals(simpleLsInput.getStringData(), STRING_DATA);
        Assert.assertEquals(simpleLsInput.getSystemId(), SYSTEM_ID);
    }
}
