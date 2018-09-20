/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import java.util.List;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.sync.AbstractSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Dit bericht wordt verstuurd om een LO3 Persoonslijst (serialized) te valideren op pre-condities, te converteren naar
 * een BRP-persoon en vervolgens op te slaan in de BRP database. Dit bericht wordt beantwoord met een
 * SynchroniseerNaarBrpAntwoordBericht.
 * 
 * @see SynchroniseerNaarBrpAntwoordBericht
 */
public final class SynchroniseerNaarBrpAntwoordBericht extends AbstractSyncBericht {

    private static final long serialVersionUID = 1L;

    private SynchroniseerNaarBrpAntwoordType synchroniseerNaarBrpAntwoordType;

    // ****************************** Constructors ******************************

    /**
     * Default constructor (status Ok).
     */
    public SynchroniseerNaarBrpAntwoordBericht() {
        synchroniseerNaarBrpAntwoordType = new SynchroniseerNaarBrpAntwoordType();
        setStatus(StatusType.OK);
    }

    /**
     * 
     * Convenient constructor: Maakt een SynchroniseerNaarBrpAntwoordBericht met Status.OK.
     * 
     * @param correlationId
     *            Het te zetten correlatie ID.
     */
    public SynchroniseerNaarBrpAntwoordBericht(final String correlationId) {
        synchroniseerNaarBrpAntwoordType = new SynchroniseerNaarBrpAntwoordType();
        setStatus(StatusType.OK);
        setCorrelationId(correlationId);
    }

    /**
     * 
     * Convenient constructor: Maakt een SynchroniseerNaarBrpAntwoordBericht met Status.OK.
     * 
     * @param correlationId
     *            Het te zetten correlatie ID.
     * @param logging
     *            De te zetten logging.
     */
    public SynchroniseerNaarBrpAntwoordBericht(final String correlationId, final List<LogRegel> logging) {
        synchroniseerNaarBrpAntwoordType = new SynchroniseerNaarBrpAntwoordType();
        setStatus(StatusType.OK);
        setCorrelationId(correlationId);
        setLogging(logging);
    }

    /**
     * Convenient constructor: Maakt een SynchroniseerNaarBrpAntwoordBericht met Status.OK.
     * 
     * @param correlationId
     *            Het te zetten correlatie ID.
     * @param status
     *            De te zetten status {@link StatusType}
     * @param foutmelding
     *            De te zetten foutmelding.
     */
    public SynchroniseerNaarBrpAntwoordBericht(
            final String correlationId,
            final StatusType status,
            final String foutmelding) {
        this(correlationId);
        setStatus(status);
        setFoutmelding(foutmelding);
    }

    /**
     * Convenient constructor: Maakt een SynchroniseerNaarBrpAntwoordBericht met Status.OK.
     * 
     * @param correlationId
     *            Het te zetten correlatie ID.
     * @param status
     *            De te zetten status {@link StatusType}
     * @param foutmelding
     *            De te zetten foutmelding.
     * @param logging
     *            De te zetten logging.
     */
    public SynchroniseerNaarBrpAntwoordBericht(
            final String correlationId,
            final StatusType status,
            final String foutmelding,
            final List<LogRegel> logging) {
        this(correlationId, status, foutmelding);
        setLogging(logging);
    }

    /**
     * Convenient constructor.
     * 
     * @param synchroniseerBrpAntwoordType
     *            Het synchronisatieantwoord type.
     */
    public SynchroniseerNaarBrpAntwoordBericht(final SynchroniseerNaarBrpAntwoordType synchroniseerBrpAntwoordType) {
        synchroniseerNaarBrpAntwoordType = synchroniseerBrpAntwoordType;
    }

    // ****************************** SyncBericht methodes ******************************

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            synchroniseerNaarBrpAntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, SynchroniseerNaarBrpAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een SynchroniseerNaarBrpAntwoord bericht.", e);
        }
    }

    // ****************************** Bericht methodes ******************************

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createSynchroniseerNaarBrpAntwoord(synchroniseerNaarBrpAntwoordType));
    }

    @Override
    public String getBerichtType() {
        return "SynchroniseerNaarBrpAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    // ****************************** Public methodes ******************************

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return synchroniseerNaarBrpAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        synchroniseerNaarBrpAntwoordType.setStatus(status);
    }

    /**
     * Geeft de foutmelding op het bericht terug.
     * 
     * @return De foutmelding op het bericht.
     */
    public String getFoutmelding() {
        return synchroniseerNaarBrpAntwoordType.getFoutmelding();
    }

    /**
     * Zet de foutmelding op het bericht.
     * 
     * @param foutmelding
     *            De te zetten foutmelding.
     */
    public void setFoutmelding(final String foutmelding) {
        synchroniseerNaarBrpAntwoordType.setFoutmelding(foutmelding);
    }

    /**
     * Geeft de logregels op het bericht terug.
     * 
     * @return De logregels op het bericht.
     */
    public List<LogRegel> getLogging() {
        return asLogRegelList(synchroniseerNaarBrpAntwoordType.getLogging());
    }

    /**
     * Zet de logregels op het bericht.
     * 
     * @param logRegels
     *            De te zetten logregels.
     */
    public void setLogging(final List<LogRegel> logRegels) {
        synchroniseerNaarBrpAntwoordType.setLogging(asLogRegelType(logRegels));
    }

    // ****************************** Object methodes ******************************

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SynchroniseerNaarBrpAntwoordBericht)) {
            return false;
        }
        final SynchroniseerNaarBrpAntwoordBericht castOther = (SynchroniseerNaarBrpAntwoordBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(synchroniseerNaarBrpAntwoordType.getStatus(),
                        castOther.synchroniseerNaarBrpAntwoordType.getStatus())
                .append(synchroniseerNaarBrpAntwoordType.getFoutmelding(),
                        castOther.synchroniseerNaarBrpAntwoordType.getFoutmelding())
                .append(getLogging(), castOther.getLogging()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode())
                .append(synchroniseerNaarBrpAntwoordType.getStatus())
                .append(synchroniseerNaarBrpAntwoordType.getFoutmelding()).append(getLogging()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("status", synchroniseerNaarBrpAntwoordType.getStatus())
                .append("foutmelding", synchroniseerNaarBrpAntwoordType.getFoutmelding())
                .append("logging", getLogging()).toString();
    }

}
