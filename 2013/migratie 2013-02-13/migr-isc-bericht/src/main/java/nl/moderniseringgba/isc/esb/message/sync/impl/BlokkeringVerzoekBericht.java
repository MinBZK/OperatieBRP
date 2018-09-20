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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Blokkering bericht: aanbrengen persoonsaanduiding in BRP.
 */
public final class BlokkeringVerzoekBericht extends AbstractSyncBericht implements SyncBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String A_NUMMER_ELEMENT = "aNummer";
    private static final String PERSOONSAANDUIDING_ELEMENT = "persoonsaanduiding";
    private static final String PROCESS_ID_ELEMENT = "processId";
    private static final String GEMEENTE_NAAR_ELEMENT = "gemeenteNaar";
    private static final String GEMEENTE_REGISTRATIE_ELEMENT = "gemeenteRegistratie";

    private BlokkeringVerzoekType blokkeringVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringVerzoekBericht() {
        blokkeringVerzoekType = new BlokkeringVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param blokkeringVerzoekType
     *            het blokkeringVerzoek type
     */
    public BlokkeringVerzoekBericht(final BlokkeringVerzoekType blokkeringVerzoekType) {
        this.blokkeringVerzoekType = blokkeringVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "BlokkeringVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createBlokkeringVerzoek(blokkeringVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            blokkeringVerzoekType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, BlokkeringVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een BlokkeringVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer dat op het bericht staat.
     * 
     * @return Het A-nummer dat op het bericht staat.
     */
    public String getANummer() {
        return blokkeringVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        blokkeringVerzoekType.setANummer(aNummer);
    }

    /**
     * @return de persoonsaanduiding
     */
    public PersoonsaanduidingType getPersoonsaanduiding() {
        return blokkeringVerzoekType.getPersoonsaanduiding();
    }

    /**
     * @param persoonsaanduiding
     *            de persoonsaanduiding
     */
    public void setPersoonsaanduiding(final PersoonsaanduidingType persoonsaanduiding) {
        blokkeringVerzoekType.setPersoonsaanduiding(persoonsaanduiding);
    }

    /**
     * Geeft het process ID dat op het bericht staat.
     * 
     * @return Het process ID
     */
    public String getProcessId() {
        return blokkeringVerzoekType.getProcessId();
    }

    /**
     * Zet het proces ID op het bericht.
     * 
     * @param processId
     *            Het te zetten proces ID.
     */
    public void setProcessId(final String processId) {
        blokkeringVerzoekType.setProcessId(processId);
    }

    /**
     * Geeft de gemeenteNaar die op het bericht staat.
     * 
     * @return De gemeenteNaar
     */
    public String getGemeenteNaar() {
        return blokkeringVerzoekType.getGemeenteNaar();
    }

    /**
     * Zet de gemeenteNaar op het bericht.
     * 
     * @param gemeenteNaar
     *            De te zetten gemeenteNaar.
     */
    public void setGemeenteNaar(final String gemeenteNaar) {
        blokkeringVerzoekType.setGemeenteNaar(gemeenteNaar);
    }

    /**
     * Geeft de gemeenteRegistratie die op het bericht staat.
     * 
     * @return De gemeenteRegistratie
     */
    public String getGemeenteRegistratie() {
        return blokkeringVerzoekType.getGemeenteRegistratie();
    }

    /**
     * Zet de gemeenteRegistratie op het bericht.
     * 
     * @param gemeenteRegistratie
     *            De te zetten gemeenteRegistratie.
     */
    public void setGemeenteRegistratie(final String gemeenteRegistratie) {
        blokkeringVerzoekType.setGemeenteRegistratie(gemeenteRegistratie);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlokkeringVerzoekBericht)) {
            return false;
        }
        final BlokkeringVerzoekBericht castOther = (BlokkeringVerzoekBericht) other;
        return new EqualsBuilder().append(blokkeringVerzoekType.getANummer(), castOther.getANummer())
                .append(blokkeringVerzoekType.getPersoonsaanduiding(), castOther.getPersoonsaanduiding())
                .append(blokkeringVerzoekType.getProcessId(), castOther.getProcessId())
                .append(blokkeringVerzoekType.getGemeenteNaar(), castOther.getGemeenteNaar())
                .append(blokkeringVerzoekType.getGemeenteRegistratie(), castOther.getGemeenteRegistratie())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(blokkeringVerzoekType.getANummer())
                .append(blokkeringVerzoekType.getPersoonsaanduiding()).append(blokkeringVerzoekType.getProcessId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(A_NUMMER_ELEMENT, blokkeringVerzoekType.getANummer())
                .append(PERSOONSAANDUIDING_ELEMENT, blokkeringVerzoekType.getPersoonsaanduiding())
                .append(PROCESS_ID_ELEMENT, blokkeringVerzoekType.getProcessId())
                .append(GEMEENTE_NAAR_ELEMENT, blokkeringVerzoekType.getGemeenteNaar())
                .append(GEMEENTE_REGISTRATIE_ELEMENT, blokkeringVerzoekType.getGemeenteRegistratie()).toString();
    }

}
