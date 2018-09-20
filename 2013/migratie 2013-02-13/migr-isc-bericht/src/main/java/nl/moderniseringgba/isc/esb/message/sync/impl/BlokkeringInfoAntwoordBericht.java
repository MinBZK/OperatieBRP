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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Blokkering info antwoord.
 */
public final class BlokkeringInfoAntwoordBericht extends AbstractSyncBericht implements SyncBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PERSOONSAANDUIDING_ELEMENT = "persoonsaanduiding";
    private static final String PROCESS_ID_ELEMENT = "processId";
    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private BlokkeringInfoAntwoordType blokkeringInfoAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringInfoAntwoordBericht() {
        blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param blokkeringInfoAntwoordType
     *            het blokkeringInfoAntwoord type
     */
    public BlokkeringInfoAntwoordBericht(final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType) {
        this.blokkeringInfoAntwoordType = blokkeringInfoAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "BlokkeringInfoAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createBlokkeringInfoAntwoord(blokkeringInfoAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            blokkeringInfoAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, BlokkeringInfoAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een BlokkeringInfoAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de persoonsaanduiding {@link PersoonsaanduidingType} op het bericht terug.
     * 
     * @return De persoonsaanduiding {@link PersoonsaanduidingType} op het bericht.
     */
    public PersoonsaanduidingType getPersoonsaanduiding() {
        return blokkeringInfoAntwoordType.getPersoonsaanduiding();
    }

    /**
     * Zet de persoonsaanduiding {@link PersoonsaanduidingType} op het bericht.
     * 
     * @param persoonsaanduiding
     *            De te zetten persoonsaanduiding {@link PersoonsaanduidingType} op het bericht.
     */
    public void setPersoonsaanduiding(final PersoonsaanduidingType persoonsaanduiding) {
        blokkeringInfoAntwoordType.setPersoonsaanduiding(persoonsaanduiding);
    }

    /**
     * Geeft het proces ID van het bericht terug.
     * 
     * @return Het proces ID van het bericht.
     */
    public String getProcessId() {
        return blokkeringInfoAntwoordType.getProcessId();
    }

    /**
     * Zet het proces ID op het bericht.
     * 
     * @param processId
     *            Het te zetten proces ID.
     */
    public void setProcessId(final String processId) {
        blokkeringInfoAntwoordType.setProcessId(processId);
    }

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return blokkeringInfoAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        blokkeringInfoAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return blokkeringInfoAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        blokkeringInfoAntwoordType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlokkeringInfoAntwoordBericht)) {
            return false;
        }
        final BlokkeringInfoAntwoordBericht castOther = (BlokkeringInfoAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(blokkeringInfoAntwoordType.getPersoonsaanduiding(), castOther.getPersoonsaanduiding())
                .append(blokkeringInfoAntwoordType.getProcessId(), castOther.getProcessId())
                .append(blokkeringInfoAntwoordType.getStatus(), castOther.getStatus())
                .append(blokkeringInfoAntwoordType.getToelichting(), castOther.getToelichting()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode())
                .append(blokkeringInfoAntwoordType.getPersoonsaanduiding())
                .append(blokkeringInfoAntwoordType.getProcessId()).append(blokkeringInfoAntwoordType.getStatus())
                .append(blokkeringInfoAntwoordType.getToelichting()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(PERSOONSAANDUIDING_ELEMENT, blokkeringInfoAntwoordType.getPersoonsaanduiding())
                .append(PROCESS_ID_ELEMENT, blokkeringInfoAntwoordType.getProcessId())
                .append(STATUS_ELEMENT, blokkeringInfoAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, blokkeringInfoAntwoordType.getToelichting()).toString();
    }
}
