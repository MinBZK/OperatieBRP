/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import java.io.Serializable;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.sync.AbstractSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Deblokkering bericht antwoord.
 */
public final class DeblokkeringAntwoordBericht extends AbstractSyncBericht implements SyncBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private DeblokkeringAntwoordType deblokkeringAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public DeblokkeringAntwoordBericht() {
        deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param deblokkeringAntwoordType
     *            het deblokkeringAntwoord type
     */
    public DeblokkeringAntwoordBericht(final DeblokkeringAntwoordType deblokkeringAntwoordType) {
        this.deblokkeringAntwoordType = deblokkeringAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "DeblokkeringAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createDeblokkeringAntwoord(deblokkeringAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            deblokkeringAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, DeblokkeringAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een DeblokkeringAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return deblokkeringAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        deblokkeringAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return deblokkeringAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        deblokkeringAntwoordType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeblokkeringAntwoordBericht)) {
            return false;
        }
        final DeblokkeringAntwoordBericht castOther = (DeblokkeringAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(deblokkeringAntwoordType.getStatus(), castOther.getStatus())
                .append(deblokkeringAntwoordType.getToelichting(), castOther.getToelichting()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(deblokkeringAntwoordType.getStatus())
                .append(deblokkeringAntwoordType.getToelichting()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, deblokkeringAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, deblokkeringAntwoordType.getToelichting()).toString();
    }
}
