/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import org.w3c.dom.ls.LSInput;

/**
 * Klasse die wordt gebruikt om de XML schema's te resolven.
 */
public final class SimpleLSInput implements LSInput {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private String publicId;

    private String systemId;

    private BufferedInputStream inputStream;

    /**
     * Constructor.
     * @param publicId de publieke identifier
     * @param sysId de systeem identifier
     * @param input de input
     */
    public SimpleLSInput(final String publicId, final String sysId, final InputStream input) {
        this.publicId = publicId;
        systemId = sysId;
        inputStream = new BufferedInputStream(input);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getPublicId()
     */
    @Override
    public String getPublicId() {
        return publicId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setPublicId(java.lang.String)
     */
    @Override
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getBaseURI()
     */
    @Override
    public String getBaseURI() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getByteStream()
     */
    @Override
    public InputStream getByteStream() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getCertifiedText()
     */
    @Override
    public boolean getCertifiedText() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getCharacterStream()
     */
    @Override
    public Reader getCharacterStream() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getEncoding()
     */
    @Override
    public String getEncoding() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getStringData()
     */
    @Override
    public String getStringData() {
        synchronized (inputStream) {
            try {
                final byte[] input = new byte[inputStream.available()];
                int aantalBytesRead = inputStream.read(input);
                if (aantalBytesRead != input.length) {
                    LOGGER.debug("Aantal bytes gelezen ongelijk aan aantal bytes verwacht");
                }
                return new String(input, EncodingConstants.CHARSET);
            } catch (final IOException e) {
                LOGGER.debug("Fout tijdens opvragen data", e);
                return null;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setBaseURI(java.lang.String)
     */
    @Override
    public void setBaseURI(final String baseURI) {
        // Niet geimplementeerd
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setByteStream(java.io.InputStream)
     */
    @Override
    public void setByteStream(final InputStream byteStream) {
        // Niet geimplementeerd
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setCertifiedText(boolean)
     */
    @Override
    public void setCertifiedText(final boolean certifiedText) {
        // Niet geimplementeerd
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setCharacterStream(java.io.Reader)
     */
    @Override
    public void setCharacterStream(final Reader characterStream) {
        // Niet geimplementeerd
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setEncoding(java.lang.String)
     */
    @Override
    public void setEncoding(final String encoding) {
        // Niet geimplementeerd
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setStringData(java.lang.String)
     */
    @Override
    public void setStringData(final String stringData) {
        // Niet geimplementeerd
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#getSystemId()
     */
    @Override
    public String getSystemId() {
        return systemId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.w3c.dom.ls.LSInput#setSystemId(java.lang.String)
     */
    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    /**
     * Geef de waarde van input stream.
     * @return input stream
     */
    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    /**
     * Zet de waarde van input stream.
     * @param inputStream input stream
     */
    public void setInputStream(final BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }

}
