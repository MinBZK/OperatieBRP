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
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapDoorMoederAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Ontkenning vaderschap door moeder antwoord.
 */
public final class OntkenningVaderschapDoorMoederAntwoordBericht extends AbstractBrpBericht implements
        BrpAntwoordBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private OntkenningVaderschapDoorMoederAntwoordType ontkenningVaderschapDoorMoederAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public OntkenningVaderschapDoorMoederAntwoordBericht() {
        ontkenningVaderschapDoorMoederAntwoordType = new OntkenningVaderschapDoorMoederAntwoordType();
        ontkenningVaderschapDoorMoederAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een ontkenning vaderschap door
     * de moeder te maken.
     * 
     * @param type
     *            het ontkenningVaderschapAntwoord type
     */
    public OntkenningVaderschapDoorMoederAntwoordBericht(final OntkenningVaderschapDoorMoederAntwoordType type) {
        ontkenningVaderschapDoorMoederAntwoordType = type;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "OntkenningVaderschapDoorMoederAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createOntkenningVaderschapDoorMoederAntwoord(ontkenningVaderschapDoorMoederAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            ontkenningVaderschapDoorMoederAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, OntkenningVaderschapDoorMoederAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een Ontkenning vaderschap door moeder "
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
        return ontkenningVaderschapDoorMoederAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    @Override
    public void setStatus(final StatusType status) {
        ontkenningVaderschapDoorMoederAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return ontkenningVaderschapDoorMoederAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    @Override
    public void setToelichting(final String toelichting) {
        ontkenningVaderschapDoorMoederAntwoordType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OntkenningVaderschapDoorMoederAntwoordBericht)) {
            return false;
        }
        final OntkenningVaderschapDoorMoederAntwoordBericht castOther =
                (OntkenningVaderschapDoorMoederAntwoordBericht) other;
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
