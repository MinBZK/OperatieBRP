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
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Notificatie antwoord.
 */
public final class NotificatieAntwoordBericht extends AbstractBrpBericht implements BrpBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private NotificatieAntwoordType notificatieAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public NotificatieAntwoordBericht() {
        notificatieAntwoordType = new NotificatieAntwoordType();
        notificatieAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param notificatieAntwoordType
     *            het notificatieAntwoord type
     */
    public NotificatieAntwoordBericht(final NotificatieAntwoordType notificatieAntwoordType) {
        this.notificatieAntwoordType = notificatieAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "NotificatieAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createNotificatieAntwoord(notificatieAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            notificatieAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, NotificatieAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een NotificatieAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return notificatieAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        notificatieAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return notificatieAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        notificatieAntwoordType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NotificatieAntwoordBericht)) {
            return false;
        }
        final NotificatieAntwoordBericht castOther = (NotificatieAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(notificatieAntwoordType.getStatus(), castOther.getStatus())
                .append(notificatieAntwoordType.getToelichting(), castOther.getToelichting()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(notificatieAntwoordType.getStatus())
                .append(notificatieAntwoordType.getToelichting()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, notificatieAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, notificatieAntwoordType.getToelichting()).toString();
    }
}
