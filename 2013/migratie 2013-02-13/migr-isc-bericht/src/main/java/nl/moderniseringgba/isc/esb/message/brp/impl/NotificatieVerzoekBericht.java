/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.Serializable;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.AbstractBrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Notificatie bericht.
 */
public final class NotificatieVerzoekBericht extends AbstractBrpBericht implements BrpBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String NOTIFICATIE_ELEMENT = "notificatie";

    private NotificatieVerzoekType notificatieVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public NotificatieVerzoekBericht() {
        notificatieVerzoekType = new NotificatieVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param notificatieVerzoekType
     *            het notificatieVerzoek type
     */
    public NotificatieVerzoekBericht(final NotificatieVerzoekType notificatieVerzoekType) {
        this.notificatieVerzoekType = notificatieVerzoekType;
    }

    /**
     * Convenience constructor.
     * 
     * @param correlationId
     *            correlation id
     * @param notificatie
     *            notificatie
     */
    public NotificatieVerzoekBericht(final String correlationId, final String notificatie) {
        this();
        notificatieVerzoekType.setNotificatie(notificatie);
        setCorrelationId(correlationId);
    }

    @Override
    public String getBerichtType() {
        return "NotificatieVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createNotificatieVerzoek(notificatieVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            notificatieVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, NotificatieVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een NotificatieVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de notificatie die op het bericht staat.
     * 
     * @return De notificatie op het bericht.
     */
    public String getNotificatie() {
        return notificatieVerzoekType.getNotificatie();
    }

    /**
     * Zet de notificatie op het bericht.
     * 
     * @param notificatie
     *            De te zetten notificatie.
     */
    public void setNotificatie(final String notificatie) {
        notificatieVerzoekType.setNotificatie(notificatie);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NotificatieVerzoekBericht)) {
            return false;
        }
        final NotificatieVerzoekBericht castOther = (NotificatieVerzoekBericht) other;
        return new EqualsBuilder().append(notificatieVerzoekType.getNotificatie(), castOther.getNotificatie())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(notificatieVerzoekType.getNotificatie()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(NOTIFICATIE_ELEMENT, notificatieVerzoekType.getNotificatie()).toString();
    }

}
