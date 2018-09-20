/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Correlatie;

/**
 * Bericht binnen de isc-runtime.
 *
 * <ul>
 * <li>Headers geven bericht headers aan (die ook op het JMS bericht staan of meegestuurd worden).</li>
 * <li>Attributes zijn verwerkende properties (die tijdens de afhandeling worden gebruikt om stuur informatie op te
 * slaan).</li>
 * </ul>
 */
public final class Message {

    /** Bericht attribuut waarin de indicatie cache (Boolean) wordt bewaard. */
    public static final String ATTRIBUTE_INDICATIE_CACHE = "iscIndicatieCache";

    /** Bericht attribuut waarin het mig_berichten.id (Long) wordt bewaard. */
    public static final String ATTRIBUTE_BERICHT_ID = "iscBerichtId";

    /** Bericht attribuut waarin het mig_berichten.naam (String) wordt bewaard. */
    public static final String ATTRIBUTE_BERICHT_TYPE = "iscBerichtType";

    /** Bericht attribuut waarin de te starten (obv bericht) cyclus wordt bewaard. */
    public static final String ATTRIBUTE_CYCLUS = "iscCyclus";

    /** Bericht attribuut waarin het bericht (nl.bzk.isc.esb.message.Bericht) wordt bewaard. */
    public static final String ATTRIBUTE_BERICHT = "iscBericht";

    /** Bericht attribuut waarin de ProcessData wordt bewaard. */
    public static final String ATTRIBUTE_CORRELATIE = "iscCorrelatie";

    /** Bericht attribuut waarin het process instance id wordt bewaard. */
    public static final String ATTRIBUTE_PROCESS_INSTANCE_ID = "iscProcessInstanceId";

    /** Bericht attribuut waarin het process instance id wordt bewaard. */
    public static final String ATTRIBUTE_ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";

    /**
     * Bericht attribuut waarin de process instance id van het procesgebonden, 'out-of-order' gecorreleerde bericht
     * wordt bewaard.
     */
    public static final String ATTRIBUTE_GECORRELEERD_BERICHT = "iscGecorreleerdBericht";

    private final Properties headers = new Properties();
    private final Map<String, Object> attributes = new HashMap<>();
    private String content;

    /**
     * Zet de header.
     *
     * @param key
     *            key
     * @param value
     *            value
     */
    public void setHeader(final String key, final String value) {
        if (value == null) {
            headers.remove(key);
        } else {
            headers.setProperty(key, value);
        }
    }

    /**
     * Geef de waarde van header.
     *
     * @param key
     *            key
     * @return header
     */
    public String getHeader(final String key) {
        return headers.getProperty(key);
    }

    /**
     * Geef de headers.
     *
     * @return headers
     */
    public Properties getHeaders() {
        return headers;
    }

    /**
     * Zet de attribute.
     *
     * @param key
     *            key
     * @param value
     *            value
     */
    public void setAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

    /**
     * Geef de waarde van property.
     *
     * @param key
     *            key
     * @return property
     */
    public Object getAttribute(final String key) {
        return attributes.get(key);
    }

    /**
     * Geef de attributen.
     *
     * @return attributen
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Geef de waarde van content.
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Zet de waarde van content.
     *
     * @param content
     *            content
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** Convenience methods voor properties ********************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Haal het (door JMS doorgegeven) message-id op.
     *
     * @return message-id
     */
    public String getMessageId() {
        return getHeader(JMSConstants.BERICHT_REFERENTIE);
    }

    /**
     * Set het (door JMS door te geven) message-id.
     *
     * @param messageId
     *            message-id
     */
    public void setMessageId(final String messageId) {
        setHeader(JMSConstants.BERICHT_REFERENTIE, messageId);
    }

    /**
     * Haal het (door JMS doorgegeven) correlatie-id op.
     *
     * @return correlatie-id
     */
    public String getCorrelatieId() {
        return getHeader(JMSConstants.CORRELATIE_REFERENTIE);
    }

    /**
     * Set het (door JMS door te geven) correlatie-id.
     *
     * @param correlatieId
     *            correlatie-id
     */
    public void setCorrelatieId(final String correlatieId) {
        setHeader(JMSConstants.CORRELATIE_REFERENTIE, correlatieId);
    }

    /**
     * Haal de (door JMS doorgegeven) originator (verzendende partij) op.
     *
     * @return originator
     */
    public String getOriginator() {
        return getHeader(JMSConstants.BERICHT_ORIGINATOR);
    }

    /**
     * Set de (door JMS door te geven) originator (verzendende partij).
     *
     * @param originator
     *            originator (verzendende partij)
     */
    public void setOriginator(final String originator) {
        setHeader(JMSConstants.BERICHT_ORIGINATOR, originator);
    }

    /**
     * Haal het (door JMS doorgegeven) recipient (ontvangende partij) op.
     *
     * @return recipient
     */
    public String getRecipient() {
        return getHeader(JMSConstants.BERICHT_RECIPIENT);
    }

    /**
     * Set het (door JMS door te geven) recipient (ontvangende partij).
     *
     * @param recipient
     *            recipient (ontvangende partij)
     */
    public void setRecipient(final String recipient) {
        setHeader(JMSConstants.BERICHT_RECIPIENT, recipient);
    }

    /**
     * Haal het (door JMS doorgegeven) MsSequenceNumber op.
     *
     * @return MsSequenceNumber
     */
    public Long getMsSequenceNumber() {
        final String msSequenceNr = getHeader(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER);
        return msSequenceNr == null ? null : Long.valueOf(msSequenceNr);
    }

    /**
     * Haal het (door JMS doorgegeven) MsSequenceNumber op.
     *
     * @return MsSequenceNumber
     */
    public String getAdministratieveHandelingId() {
        return getHeader(JMSConstants.BERICHT_ADMINISTRATIEVE_HANDELING_ID);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** Convenience methods voor attributes ********************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Voeg alle gegeven attributen toe.
     *
     * @param attributesToSet
     *            attributen
     */
    public void setAllAttributes(final Map<String, Object> attributesToSet) {
        attributes.putAll(attributesToSet);
    }

    /**
     * Haal het bericht id op.
     *
     * @return bericht id
     */
    public Long getBerichtId() {
        return (Long) getAttribute(ATTRIBUTE_BERICHT_ID);
    }

    /**
     * Zet het bericht id.
     *
     * @param berichtId
     *            bericht id
     */
    public void setBerichtId(final Long berichtId) {
        setAttribute(ATTRIBUTE_BERICHT_ID, berichtId);
    }

    /**
     * Haal het geparsde bericht op.
     *
     * @return bericht
     */
    public Bericht getBericht() {
        return (Bericht) getAttribute(ATTRIBUTE_BERICHT);
    }

    /**
     * Zet het geparsde bericht.
     *
     * @param bericht
     *            bericht
     */
    public void setBericht(final Bericht bericht) {
        setAttribute(ATTRIBUTE_BERICHT, bericht);
    }

    /**
     * Haal het bericht type op.
     *
     * @return bericht type
     */
    public String getBerichtType() {
        return (String) getAttribute(ATTRIBUTE_BERICHT_TYPE);
    }

    /**
     * Zet het bericht type.
     *
     * @param berichtType
     *            bericht type
     */
    public void setBerichtType(final String berichtType) {
        setAttribute(ATTRIBUTE_BERICHT_TYPE, berichtType);
    }

    /**
     * Haal de gecorreleerde proces data op.
     *
     * @return correlatie
     */
    public Correlatie getCorrelatie() {
        return (Correlatie) getAttribute(ATTRIBUTE_CORRELATIE);
    }

    /**
     * Zet de gecorreleerde proces data.
     *
     *
     * @param correlatie
     *            correlatie
     */
    public void setCorrelatie(final Correlatie correlatie) {
        setAttribute(ATTRIBUTE_CORRELATIE, correlatie);
    }

    /**
     * Haal de process instance id op.
     *
     * @return process instance id
     */
    public Long getProcessInstanceId() {
        return (Long) getAttribute(ATTRIBUTE_PROCESS_INSTANCE_ID);
    }

    /**
     * Zet de process instance id.
     *
     *
     * @param processInstanceId
     *            process instance id
     */
    public void setProcessInstanceId(final long processInstanceId) {
        setAttribute(ATTRIBUTE_PROCESS_INSTANCE_ID, processInstanceId);
    }

}
