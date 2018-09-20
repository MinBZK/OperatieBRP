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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Blokkering antwoord.
 */
public final class BlokkeringAntwoordBericht extends AbstractSyncBericht implements SyncBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";
    private static final String PERSOONSAANDUIDING_ELEMENT = "persoonsaanduiding";
    private static final String PROCESS_ID_ELEMENT = "processId";
    private static final String GEMEENTE_NAAR_ELEMENT = "gemeenteNaar";

    private BlokkeringAntwoordType blokkeringAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringAntwoordBericht() {
        blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param blokkeringAntwoordType
     *            het blokkeringAntwoord type
     */
    public BlokkeringAntwoordBericht(final BlokkeringAntwoordType blokkeringAntwoordType) {
        this.blokkeringAntwoordType = blokkeringAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "BlokkeringAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createBlokkeringAntwoord(blokkeringAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            blokkeringAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, BlokkeringAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een BlokkeringAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return blokkeringAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        blokkeringAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return blokkeringAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        blokkeringAntwoordType.setToelichting(toelichting);
    }

    /**
     * Geeft de persoonsaanduiding op het bericht terug.
     * 
     * @return De persoonsaanduiding op het bericht.
     */
    public PersoonsaanduidingType getPersoonsaanduiding() {
        return blokkeringAntwoordType.getPersoonsaanduiding();
    }

    /**
     * Zet de persoonsaanduiding op het bericht.
     * 
     * @param persoonsaanduiding
     *            De te zetten persoonsaanduiding.
     */
    public void setToelichting(final PersoonsaanduidingType persoonsaanduiding) {
        blokkeringAntwoordType.setPersoonsaanduiding(persoonsaanduiding);
    }

    /**
     * Geeft het process id op het bericht terug.
     * 
     * @return Het process id op het bericht.
     */
    public String getProcessId() {
        return blokkeringAntwoordType.getProcessId();
    }

    /**
     * Zet het process id op het bericht.
     * 
     * @param processId
     *            Het te zetten processId.
     */
    public void setProcessId(final String processId) {
        blokkeringAntwoordType.setProcessId(processId);
    }

    /**
     * Geeft de gemeente naar op het bericht terug.
     * 
     * @return De gemeente naar op het bericht.
     */
    public String getGemeenteNaar() {
        return blokkeringAntwoordType.getGemeenteNaar();
    }

    /**
     * Zet de gemeente naar op het bericht.
     * 
     * @param gemeenteNaar
     *            De te zetten gemeente naar.
     */
    public void setGemeenteNAar(final String gemeenteNaar) {
        blokkeringAntwoordType.setGemeenteNaar(gemeenteNaar);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlokkeringAntwoordBericht)) {
            return false;
        }
        final BlokkeringAntwoordBericht castOther = (BlokkeringAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(blokkeringAntwoordType.getStatus(), castOther.getStatus())
                .append(blokkeringAntwoordType.getToelichting(), castOther.getToelichting())
                .append(blokkeringAntwoordType.getPersoonsaanduiding(), castOther.getPersoonsaanduiding())
                .append(blokkeringAntwoordType.getProcessId(), castOther.getProcessId())
                .append(blokkeringAntwoordType.getGemeenteNaar(), castOther.getGemeenteNaar()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(blokkeringAntwoordType.getStatus())
                .append(blokkeringAntwoordType.getToelichting()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, blokkeringAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, blokkeringAntwoordType.getToelichting())
                .append(PERSOONSAANDUIDING_ELEMENT, blokkeringAntwoordType.getPersoonsaanduiding())
                .append(PROCESS_ID_ELEMENT, blokkeringAntwoordType.getProcessId())
                .append(GEMEENTE_NAAR_ELEMENT, blokkeringAntwoordType.getGemeenteNaar()).toString();
    }
}
