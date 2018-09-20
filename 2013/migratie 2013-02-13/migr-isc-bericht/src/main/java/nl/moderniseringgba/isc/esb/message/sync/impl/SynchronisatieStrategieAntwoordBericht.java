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
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchronisatieStrategieAntwoordType;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Search response bericht.
 */
public final class SynchronisatieStrategieAntwoordBericht extends AbstractSyncBericht implements SyncBericht,
        Serializable {

    private static final long serialVersionUID = 1L;

    private SynchronisatieStrategieAntwoordType synchronisatieStrategieAntwoordType;

    /**
     * Default constructor.
     */
    public SynchronisatieStrategieAntwoordBericht() {
        synchronisatieStrategieAntwoordType = new SynchronisatieStrategieAntwoordType();
    }

    /**
     * Convenient constructor.
     * 
     * @param synchronisatieStrategieAntwoordType
     *            Het search response type {@link SynchronisatieStrategieAntwoordType}
     */
    public SynchronisatieStrategieAntwoordBericht(
            final SynchronisatieStrategieAntwoordType synchronisatieStrategieAntwoordType) {
        this.synchronisatieStrategieAntwoordType = synchronisatieStrategieAntwoordType;
    }

    /**
     * Convenience constructor (status = FOUT).
     * 
     * @param correlationId
     *            correlatieId
     * @param toelichting
     *            toelichting
     */
    public SynchronisatieStrategieAntwoordBericht(final String correlationId, final String toelichting) {
        this();
        setStatus(StatusType.FOUT);
        setToelichting(toelichting);
        setCorrelationId(correlationId);
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} op het bericht terug.
     * 
     * @return De status {@link StatusType} op het bericht.
     */
    public StatusType getStatus() {
        return synchronisatieStrategieAntwoordType.getStatus();
    }

    /**
     * Zet status.
     * 
     * @param status
     *            status
     * @throws NullPointerException
     *             als status null is
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }
        synchronisatieStrategieAntwoordType.setStatus(status);
    }

    /**
     * Geeft het resultaat op het bericht.
     * 
     * @return Het resultaat op het bericht.
     */
    public SearchResultaatType getResultaat() {
        return synchronisatieStrategieAntwoordType.getResultaat();
    }

    /**
     * Zet het resultaat op het bericht.
     * 
     * @param resultaat
     *            Het te zetten resultaat {@link SearchResultaatType}
     */
    public void setResultaat(final SearchResultaatType resultaat) {
        synchronisatieStrategieAntwoordType.setResultaat(resultaat);
    }

    /**
     * Geeft de toelichting op het bericht.
     * 
     * @return De toelichting op het bericht.
     */
    public String getToelichting() {
        return synchronisatieStrategieAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        synchronisatieStrategieAntwoordType.setToelichting(toelichting);
    }

    /**
     * Geeft de Lo3Persoonslijst op het bericht terug.
     * 
     * @return De Lo3Persoonslijst op het bericht.
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(synchronisatieStrategieAntwoordType.getLo3Pl());
    }

    /**
     * Zet de Lo3Persoonslijst op het bericht.
     * 
     * @param lo3Persoonslijst
     *            De te zetten Lo3Persoonslijst.
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        synchronisatieStrategieAntwoordType.setLo3Pl(asString(lo3Persoonslijst));
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "SynchronisatieStrategieAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createSynchronisatieStrategieAntwoord(synchronisatieStrategieAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            synchronisatieStrategieAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, SynchronisatieStrategieAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException("Onbekende fout tijdens het unmarshallen van een Search bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SynchronisatieStrategieAntwoordBericht)) {
            return false;
        }
        final SynchronisatieStrategieAntwoordBericht castOther = (SynchronisatieStrategieAntwoordBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(synchronisatieStrategieAntwoordType.getStatus(),
                        castOther.synchronisatieStrategieAntwoordType.getStatus())
                .append(synchronisatieStrategieAntwoordType.getResultaat(),
                        castOther.synchronisatieStrategieAntwoordType.getResultaat())
                .append(synchronisatieStrategieAntwoordType.getToelichting(),
                        castOther.synchronisatieStrategieAntwoordType.getToelichting())
                .append(synchronisatieStrategieAntwoordType.getLo3Pl(),
                        castOther.synchronisatieStrategieAntwoordType.getLo3Pl()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode())
                .append(synchronisatieStrategieAntwoordType.getStatus())
                .append(synchronisatieStrategieAntwoordType.getResultaat())
                .append(synchronisatieStrategieAntwoordType.getToelichting())
                .append(synchronisatieStrategieAntwoordType.getLo3Pl()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(synchronisatieStrategieAntwoordType.getStatus())
                .append(synchronisatieStrategieAntwoordType.getResultaat())
                .append(synchronisatieStrategieAntwoordType.getToelichting())
                .append(synchronisatieStrategieAntwoordType.getLo3Pl()).toString();
    }

}
