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
import nl.moderniseringgba.isc.esb.message.brp.BrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingAntwoordType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Verhuis antwoord.
 */
public final class VerhuizingAntwoordBericht extends AbstractBrpBericht implements BrpAntwoordBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private VerhuizingAntwoordType verhuizingAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VerhuizingAntwoordBericht() {
        verhuizingAntwoordType = new VerhuizingAntwoordType();
        verhuizingAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param verhuizingAntwoordType
     *            het verhuizingAntwoord type
     */
    public VerhuizingAntwoordBericht(final VerhuizingAntwoordType verhuizingAntwoordType) {
        this.verhuizingAntwoordType = verhuizingAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "VerhuizingAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createVerhuizingAntwoord(verhuizingAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            verhuizingAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, VerhuizingAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een VerhuizingAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return verhuizingAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    @Override
    public void setStatus(final StatusType status) {
        verhuizingAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return verhuizingAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    @Override
    public void setToelichting(final String toelichting) {
        verhuizingAntwoordType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VerhuizingAntwoordBericht)) {
            return false;
        }
        final VerhuizingAntwoordBericht castOther = (VerhuizingAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(verhuizingAntwoordType.getStatus(), castOther.getStatus())
                .append(verhuizingAntwoordType.getToelichting(), castOther.getToelichting()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(verhuizingAntwoordType.getStatus())
                .append(verhuizingAntwoordType.getToelichting()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, verhuizingAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, verhuizingAntwoordType.getToelichting()).toString();
    }
}
