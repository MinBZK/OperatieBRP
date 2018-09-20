/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.ls.LSInput;

/**
 * Klasse die wordt gebruikt om de XML schema's te resolven.
 * 
 */
public final class SimpleLSInput implements LSInput {

    private String publicId;

    private String systemId;

    private BufferedInputStream inputStream;

    /**
     * Constructor.
     * 
     * @param publicId
     *            de publieke identifier
     * @param sysId
     *            de systeem identifier
     * @param input
     *            de input
     */
    public SimpleLSInput(final String publicId, final String sysId, final InputStream input) {
        this.publicId = publicId;
        systemId = sysId;
        inputStream = new BufferedInputStream(input);
    }

    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public InputStream getByteStream() {
        return null;
    }

    @Override
    public boolean getCertifiedText() {
        return false;
    }

    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public String getStringData() {
        synchronized (inputStream) {
            try {
                final byte[] input = new byte[inputStream.available()];
                inputStream.read(input);
                return new String(input);
            } catch (final IOException e) {
                return null;
            }
        }
    }

    @Override
    public void setBaseURI(final String baseURI) {
    }

    @Override
    public void setByteStream(final InputStream byteStream) {
    }

    @Override
    public void setCertifiedText(final boolean certifiedText) {
    }

    @Override
    public void setCharacterStream(final Reader characterStream) {
    }

    @Override
    public void setEncoding(final String encoding) {
    }

    @Override
    public void setStringData(final String stringData) {
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(final BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }

}
