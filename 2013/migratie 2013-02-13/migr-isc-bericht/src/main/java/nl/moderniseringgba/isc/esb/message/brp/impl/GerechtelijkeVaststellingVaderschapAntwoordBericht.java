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
import nl.moderniseringgba.isc.esb.message.brp.generated.GerechtelijkeVaststellingVaderschapAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Gerechtelijke vaststelling vaderschap antwoord.
 */
public final class GerechtelijkeVaststellingVaderschapAntwoordBericht extends AbstractBrpBericht implements
        BrpAntwoordBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private GerechtelijkeVaststellingVaderschapAntwoordType gerechtelijkeVaststellingVaderschapAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public GerechtelijkeVaststellingVaderschapAntwoordBericht() {
        gerechtelijkeVaststellingVaderschapAntwoordType = new GerechtelijkeVaststellingVaderschapAntwoordType();
        gerechtelijkeVaststellingVaderschapAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een gerechtelijke vaststelling
     * vaderschap te maken.
     * 
     * @param type
     *            het gerechtelijkeVaststellingVaderschapAntwoord Type
     */
    public GerechtelijkeVaststellingVaderschapAntwoordBericht(
            final GerechtelijkeVaststellingVaderschapAntwoordType type) {
        gerechtelijkeVaststellingVaderschapAntwoordType = type;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "GerechtelijkeVaststellingVaderschapAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createGerechtelijkeVaststellingVaderschapAntwoord(gerechtelijkeVaststellingVaderschapAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            gerechtelijkeVaststellingVaderschapAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, GerechtelijkeVaststellingVaderschapAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een gerechtelijke vaststelling vaderschap "
                            + "Antwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return gerechtelijkeVaststellingVaderschapAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    @Override
    public void setStatus(final StatusType status) {
        gerechtelijkeVaststellingVaderschapAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return gerechtelijkeVaststellingVaderschapAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    @Override
    public void setToelichting(final String toelichting) {
        gerechtelijkeVaststellingVaderschapAntwoordType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GerechtelijkeVaststellingVaderschapAntwoordBericht)) {
            return false;
        }
        final GerechtelijkeVaststellingVaderschapAntwoordBericht castOther =
                (GerechtelijkeVaststellingVaderschapAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(getStatus(), castOther.getStatus())
                .append(getToelichting(), castOther.getToelichting()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(getStatus()).append(getToelichting())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, getStatus()).append(TOELICHTING_ELEMENT, getToelichting()).toString();
    }
}
