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
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchronisatieStrategieVerzoekType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Search bericht: 3.5 Onderzoek PL situatie in BRP.
 */
public final class SynchronisatieStrategieVerzoekBericht extends AbstractSyncBericht implements SyncBericht,
        Serializable {

    private static final long serialVersionUID = 1L;

    private SynchronisatieStrategieVerzoekType synchronisatieStrategieVerzoekType;

    /**
     * Default constructor.
     */
    public SynchronisatieStrategieVerzoekBericht() {
        synchronisatieStrategieVerzoekType = new SynchronisatieStrategieVerzoekType();
    }

    /**
     * Convenient constructor.
     * 
     * @param synchronisatieStrategieVerzoekType
     *            Het synchronisatie verzoek type {@link SynchronisatieStrategieVerzoekType}
     */
    public SynchronisatieStrategieVerzoekBericht(
            final SynchronisatieStrategieVerzoekType synchronisatieStrategieVerzoekType) {
        this.synchronisatieStrategieVerzoekType = synchronisatieStrategieVerzoekType;
    }

    /**
     * Convenience constructor.
     * 
     * @param aNummer
     *            a-nummer (LO3-rubriek 01.01.10)
     * @param vorigANummer
     *            vorig a-nummer (LO3-rubriek 01.20.10)
     * @param bsn
     *            bsn (LO3 rubriek 01.01.20)
     */
    public SynchronisatieStrategieVerzoekBericht(final Long aNummer, final Long vorigANummer, final Long bsn) {
        this();
        setANummer(aNummer);
        setVorigANummer(vorigANummer);
        setBsn(bsn);
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer op het bericht terug.
     * 
     * @return Het A-nummer op het bericht.
     */
    public Long getANummer() {
        return asLong(synchronisatieStrategieVerzoekType.getANummer());
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final Long aNummer) {
        synchronisatieStrategieVerzoekType.setANummer(asString(aNummer));
    }

    /**
     * Geeft het vorige A-nummer op het bericht terug.
     * 
     * @return Het vorige A-nummer op het bericht.
     */
    public Long getVorigANummer() {
        return asLong(synchronisatieStrategieVerzoekType.getVorigANummer());
    }

    /**
     * Zet het vorige A-nummer op het bericht.
     * 
     * @param vorigANummer
     *            Het te zetten A-nummer.
     */
    public void setVorigANummer(final Long vorigANummer) {
        synchronisatieStrategieVerzoekType.setVorigANummer(asString(vorigANummer));
    }

    /**
     * Geeft het BSN op het bericht terug.
     * 
     * @return Het BSN op het bericht.
     */
    public Long getBsn() {
        return asLong(synchronisatieStrategieVerzoekType.getBsn());
    }

    /**
     * Zet het BSN op het bericht.
     * 
     * @param bsn
     *            Het te zetten BSN.
     */
    public void setBsn(final Long bsn) {
        synchronisatieStrategieVerzoekType.setBsn(asString(bsn));
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "SynchronisatieStrategieVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "syncSynchronisatieStrategieVerzoek";
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createSynchronisatieStrategieVerzoek(synchronisatieStrategieVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            synchronisatieStrategieVerzoekType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, SynchronisatieStrategieVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een SynchronisatieStrategieVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SynchronisatieStrategieVerzoekBericht)) {
            return false;
        }
        final SynchronisatieStrategieVerzoekBericht castOther = (SynchronisatieStrategieVerzoekBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(synchronisatieStrategieVerzoekType.getANummer(),
                        castOther.synchronisatieStrategieVerzoekType.getANummer())
                .append(synchronisatieStrategieVerzoekType.getVorigANummer(),
                        castOther.synchronisatieStrategieVerzoekType.getVorigANummer())
                .append(synchronisatieStrategieVerzoekType.getBsn(),
                        castOther.synchronisatieStrategieVerzoekType.getBsn()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode())
                .append(synchronisatieStrategieVerzoekType.getANummer())
                .append(synchronisatieStrategieVerzoekType.getVorigANummer())
                .append(synchronisatieStrategieVerzoekType.getBsn()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(synchronisatieStrategieVerzoekType.getANummer())
                .append(synchronisatieStrategieVerzoekType.getVorigANummer())
                .append(synchronisatieStrategieVerzoekType.getBsn()).toString();
    }

}
