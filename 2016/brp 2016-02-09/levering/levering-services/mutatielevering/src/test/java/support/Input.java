/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.w3c.dom.ls.LSInput;

public class Input implements LSInput {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private String publicId;

    private String systemId;

    private final BufferedInputStream inputStream;

    public Input(final String publicId, final String sysId, final InputStream input) {
        this.publicId = publicId;
        this.systemId = sysId;
        this.inputStream = new BufferedInputStream(input);
    }

    public final String getPublicId() {
        return publicId;
    }

    public final void setPublicId(final String publicId) {
        this.publicId = publicId;
    }

    public final String getBaseURI() {
        return null;
    }

    public final InputStream getByteStream() {
        return null;
    }

    public final boolean getCertifiedText() {
        return false;
    }

    public final Reader getCharacterStream() {
        return null;
    }

    public final String getEncoding() {
        return null;
    }

    public final String getStringData() {
        synchronized (inputStream) {
            try {
                final byte[] input = new byte[inputStream.available()];
                inputStream.read(input);
                return new String(input);
            } catch (final IOException e) {
                LOGGER.error("Exception: {}", e.getMessage(), e);
                return null;
            }
        }
    }

    public final void setBaseURI(final String baseURI) {
    }

    public final void setByteStream(final InputStream byteStream) {
    }

    public final void setCertifiedText(final boolean certifiedText) {
    }

    public final void setCharacterStream(final Reader characterStream) {
    }

    public final void setEncoding(final String encoding) {
    }

    public final void setStringData(final String stringData) {
    }

    public final String getSystemId() {
        return systemId;
    }

    public final void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

}
