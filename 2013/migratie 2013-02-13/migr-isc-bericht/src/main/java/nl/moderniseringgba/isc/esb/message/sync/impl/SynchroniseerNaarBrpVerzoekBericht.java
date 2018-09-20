/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.sync.AbstractSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchroniseerNaarBrpVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;

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
public final class SynchroniseerNaarBrpVerzoekBericht extends AbstractSyncBericht {

    private static final long serialVersionUID = 1L;

    private SynchroniseerNaarBrpVerzoekType synchroniseerNaarBrpVerzoekType;

    // ****************************** Constructors ******************************

    /**
     * Default constructor.
     */
    public SynchroniseerNaarBrpVerzoekBericht() {
        synchroniseerNaarBrpVerzoekType = new SynchroniseerNaarBrpVerzoekType();
    }

    /**
     * Convenient constructor.
     * 
     * @param lo3BerichtAsTeletexString
     *            De teletext representatie van het Lo3 bericht.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final String lo3BerichtAsTeletexString) {
        this();
        setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
    }

    /**
     * Convenient constructor.
     * 
     * @param lo3BerichtAsTeletexString
     *            De teletext representatie van het Lo3 bericht.
     * @param aNummerTeVervangenPl
     *            Het A-nummer van de te vervangen PL.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final String lo3BerichtAsTeletexString, final Long aNummerTeVervangenPl) {
        this();
        setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
        setANummerTeVervangenPl(aNummerTeVervangenPl);
    }

    /**
     * Convenient constructor.
     * 
     * @param persoonslijst
     *            De LO3 PL.
     * @param aNummerTeVervangenPl
     *            Het A-nummer van de te vervangen PL.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final Lo3Persoonslijst persoonslijst, final Long aNummerTeVervangenPl) {
        this();
        setLo3Persoonslijst(persoonslijst);
        setANummerTeVervangenPl(aNummerTeVervangenPl);
    }

    /**
     * Convenient constructor.
     * 
     * @param synchroniseerNaarBrpVerzoekType
     *            Het synchronisatieverzoek type.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final SynchroniseerNaarBrpVerzoekType synchroniseerNaarBrpVerzoekType) {
        this.synchroniseerNaarBrpVerzoekType = synchroniseerNaarBrpVerzoekType;
    }

    // ****************************** SyncBericht methodes ******************************

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            synchroniseerNaarBrpVerzoekType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, SynchroniseerNaarBrpVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een SynchroniseerNaarBrpVerzoek bericht.", e);
        }
    }

    // ****************************** Bericht methodes ******************************

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoekType));
    }

    @Override
    public String getBerichtType() {
        return "SynchroniseerNaarBrpVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "syncStore";
    }

    // ****************************** Public methodes ******************************

    /**
     * De inhoud van een LO3 PL bericht (in Teletex formaat).
     * 
     * @return Geeft het Lo3bericht als teletext string terug.
     */
    public String getLo3BerichtAsTeletexString() {
        return synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString();
    }

    /**
     * De inhoud van een LO3 PL bericht (in Teletex formaat).
     * 
     * @param lo3BerichtAsTeletexString
     *            de inhoud, mag niet null zijn en moet beginnen met de berichtlengte
     */
    public void setLo3BerichtAsTeletexString(final String lo3BerichtAsTeletexString) {
        synchroniseerNaarBrpVerzoekType.setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
    }

    /**
     * Geeft het A-nummer van de te vervangen PL terug.
     * 
     * @return Het A-nummer van de te vervangen PL.
     */
    public Long getANummerTeVervangenPl() {
        return asLong(synchroniseerNaarBrpVerzoekType.getANummerTeVervangenPl());
    }

    /**
     * Zet het A-nummer van de te vervangen persoonslijst.
     * 
     * @param aNummerTeVervangenPl
     *            Het te zetten A-nummer.
     */
    public void setANummerTeVervangenPl(final Long aNummerTeVervangenPl) {
        synchroniseerNaarBrpVerzoekType.setANummerTeVervangenPl(asString(aNummerTeVervangenPl));
    }

    /**
     * Geeft de Lo3Persoonslijst terug.
     * 
     * @return De Lo3Persoonslijst
     * 
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString());
    }

    /**
     * Zet de Lo3persoonslijst.
     * 
     * @param lo3Persoonslijst
     *            De te zetten Lo3Persoonslijst.
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        synchroniseerNaarBrpVerzoekType.setLo3BerichtAsTeletexString(asString(lo3Persoonslijst));
    }

    // ****************************** Object methodes ******************************

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SynchroniseerNaarBrpVerzoekBericht)) {
            return false;
        }
        final SynchroniseerNaarBrpVerzoekBericht castOther = (SynchroniseerNaarBrpVerzoekBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(synchroniseerNaarBrpVerzoekType.getANummerTeVervangenPl(),
                        castOther.synchroniseerNaarBrpVerzoekType.getANummerTeVervangenPl())
                .append(synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString(),
                        castOther.synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode())
                .append(synchroniseerNaarBrpVerzoekType.getANummerTeVervangenPl())
                .append(synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(synchroniseerNaarBrpVerzoekType.getANummerTeVervangenPl())
                .append(synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString()).toString();
    }

}
