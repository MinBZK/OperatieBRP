/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Object dat een bericht beschrijft dat gearchiveerd kan worden. Het bevat de voor een bericht belangrijke informatie
 * en bepaalt middels de (@link #toString} methode hoe het bericht uiteindelijk wordt gearchiveerd/gerepresenteerd.
 */
public class ArchiveringBericht {

    private static final Logger LOGGER                     = LoggerFactory.getLogger(ArchiveringBericht.class);

    /** Globale naam waaronder het (archiverings) id van het ingaande bericht wordt bijgehouden in de context. */
    public static final String  BERICHT_ARCHIVERING_IN_ID  = ArchiveringBericht.class.getPackage().toString()
                                                               + ".berichtarchivering.IN";
    /** Globale naam waaronder het (archiverings) id van het uitgaande bericht wordt bijgehouden in de context. */
    public static final String  BERICHT_ARCHIVERING_UIT_ID = ArchiveringBericht.class.getPackage().toString()
                                                               + ".berichtarchivering.UIT";

    private final String        kop;
    private String              adres;
    private String              contentType;
    private String              encoding;
    private String              httpMethode;
    private Object              header;
    private final StringBuilder payload;
    private Integer             responseCode;

    /**
     * Standaard constructor die op basis van het opgegeven bericht reeds een groot aantal van de velden zet. Daarnaast
     * wordt ook de 'kop' van het te archiveren bericht gezet middels de opgegeven string.
     *
     * @param bericht het bericht waarvoor dit archiveringsbericht gebouwd dient te worden.
     * @param kop de kop die getoond moet worden bovenaan het te archiveren bericht.
     */
    public ArchiveringBericht(final Message bericht, final String kop) {
        this.kop = kop;
        payload = new StringBuilder();
        vulStandaardVelden(bericht);
    }

    /**
     * Vult de standaard velden (die onafhankelijk van het bericht altijd gezet worden) op basis van het opgegeven
     * bericht.
     *
     * @param bericht het bericht dat gearchiveerd dient te worden.
     */
    private void vulStandaardVelden(final Message bericht) {
        if (bericht == null) {
            throw new IllegalArgumentException("Bericht mag niet null zijn.");
        }

        Integer berichtResponseCode = (Integer) bericht.get(Message.RESPONSE_CODE);
        if (berichtResponseCode != null) {
            responseCode = berichtResponseCode;
        }

        String berichtEncoding = (String) bericht.get(Message.ENCODING);
        if (berichtEncoding != null) {
            encoding = berichtEncoding;
        }

        String berichtHttpMethode = (String) bericht.get(Message.HTTP_REQUEST_METHOD);
        if (berichtHttpMethode != null) {
            httpMethode = berichtHttpMethode;
        }

        String berichtContentType = (String) bericht.get(Message.CONTENT_TYPE);
        if (berichtContentType != null) {
            contentType = berichtContentType;
        }

        Object berichtHeaders = bericht.get(Message.PROTOCOL_HEADERS);
        if (berichtHeaders != null) {
            header = berichtHeaders;
        }
    }

    /**
     * Zet de content/payload van het te archiveren bericht naar de content/payload uit de opgegeven outputstream. Deze
     * content wordt, indien het (valide) XML is, nog netjes geindenteerd.
     *
     * @param outputStream de outputstream die de payload/content van het bericht bevat.
     * @throws IOException indien er problemen zijn bij het ophalen/lezen van de payload van de stream.
     */
    public void vulPayload(final CachedOutputStream outputStream) throws IOException {
        if (contentType != null && contentType.indexOf("xml") >= 0 && outputStream.size() > 0) {
            try {
                vulXmlPayload(outputStream);
            } catch (TransformerException e) {
                LOGGER.info("Fout bij opstellen archiveringsbericht vanwege transformatie fout in XML bericht. "
                    + "Bericht wordt 'plain' opgeslagen nu en niet meer geindenteerd");
                vulPlainPayload(outputStream);
            }
        } else {
            vulPlainPayload(outputStream);
        }
    }

    /**
     * Zet de content/payload van het te archiveren bericht naar de content/payload uit de opgegeven outputstream. Deze
     * content wordt rechtstreeks overgenomen, zonder formattering of indentering.
     *
     * @param outputStream de outputstream die de payload/content van het bericht bevat.
     * @throws IOException indien er problemen zijn bij het ophalen/lezen van de payload van de stream.
     */
    private void vulPlainPayload(final CachedOutputStream outputStream) throws IOException {
        if (StringUtils.isEmpty(encoding)) {
            outputStream.writeCacheTo(payload);
        } else {
            outputStream.writeCacheTo(payload, encoding);
        }
    }

    /**
     * Zet de content/payload van het te archiveren bericht naar de content/payload uit de opgegeven outputstream. Deze
     * content wordt nog netjes geindenteerd.
     *
     * @param outputStream de outputstream die de payload/content van het bericht bevat.
     * @throws IOException indien er problemen zijn bij het ophalen/lezen van de payload van de stream.
     * @throws TransformerException indien de input geen valide XML is (of andere transformatie fouten oplevert).
     */
    private void vulXmlPayload(final CachedOutputStream outputStream) throws TransformerException, IOException {
        Transformer serializer = XMLUtils.newTransformer(2);
        // Setup indenting to "pretty print"
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StringWriter swriter = new StringWriter();
        serializer.transform(new StreamSource(outputStream.getInputStream()), new StreamResult(swriter));
        payload.append(swriter.toString());
    }

    /**
     * Getter methode voor de kop van het bericht.
     *
     * @return de kop van het bericht.
     */
    public String getKop() {
        return kop;
    }

    /**
     * Getter methode voor het adres van het bericht.
     *
     * @return het adres van het bericht.
     */
    public String getAdres() {
        return adres;
    }

    /**
     * Getter methode voor het contenttype van het bericht.
     *
     * @return het contenttype van het bericht.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Getter methode voor de encoding van het bericht.
     *
     * @return de encoding van het bericht.
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Getter methode voor de HTTP-methode van het bericht.
     *
     * @return de HTTP-methode van het bericht.
     */
    public String getHttpMethode() {
        return httpMethode;
    }

    /**
     * Getter methode voor de header van het bericht.
     *
     * @return de header van het bericht.
     */
    public Object getHeader() {
        return header;
    }

    /**
     * Getter methode voor de content/payload van het bericht.
     *
     * @return de content/payload van het bericht.
     */
    public StringBuilder getPayload() {
        return payload;
    }

    /**
     * Getter methode voor de responsecode van het bericht.
     *
     * @return de responsecode van het bericht.
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * Setter methode om het adres te zetten.
     *
     * @param adres het adres dat gezet dient te worden.
     */
    public void setAdres(final String adres) {
        this.adres = adres;
    }

    /**
     * Geeft een textuele representatie van het te archiveren bericht.
     *
     * @return een textuele representatie van het te archiveren bericht.
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(kop).append("\n--------------------------------------");
        if (adres != null && adres.length() > 0) {
            buffer.append("\nAdres: ");
            buffer.append(adres);
        }
        if (responseCode != null) {
            buffer.append("\nResponse-Code: ");
            buffer.append(responseCode);
        }
        if (encoding != null && encoding.length() > 0) {
            buffer.append("\nEncoding: ");
            buffer.append(encoding);
        }
        if (httpMethode != null && httpMethode.length() > 0) {
            buffer.append("\nHttp-Method: ");
            buffer.append(httpMethode);
        }
        buffer.append("\nContent-Type: ");
        buffer.append(contentType);
        buffer.append("\nHeaders: ");
        buffer.append(header);
        if (payload.length() > 0) {
            buffer.append("\nPayload: ");
            buffer.append(payload);
        }
        buffer.append("\n--------------------------------------");
        return buffer.toString();
    }

}
