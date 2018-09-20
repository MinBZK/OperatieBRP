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
import nl.moderniseringgba.isc.esb.message.sync.generated.AntwoordFormaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.LeesUitBrpVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Lees uit BRP verzoek bericht.
 */
public final class LeesUitBrpVerzoekBericht extends AbstractSyncBericht implements SyncBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private LeesUitBrpVerzoekType leesUitBrpVerzoekType;

    /**
     * Default constructor.
     */
    public LeesUitBrpVerzoekBericht() {
        leesUitBrpVerzoekType = new LeesUitBrpVerzoekType();
        leesUitBrpVerzoekType.setAntwoordFormaat(AntwoordFormaatType.LO_3);
    }

    /**
     * Convenience constructor.
     * 
     * @param leesUitBrpVerzoekType
     *            Het lees uit BRP verzoek type {@link LeesUitBrpVerzoekType}
     */
    public LeesUitBrpVerzoekBericht(final LeesUitBrpVerzoekType leesUitBrpVerzoekType) {
        this.leesUitBrpVerzoekType = leesUitBrpVerzoekType;
        if (leesUitBrpVerzoekType.getAntwoordFormaat() == null) {
            leesUitBrpVerzoekType.setAntwoordFormaat(AntwoordFormaatType.LO_3);
        }
    }

    /**
     * Convenience constructor.
     * 
     * @param aNummer
     *            anummer
     */
    public LeesUitBrpVerzoekBericht(final Long aNummer) {
        this();
        leesUitBrpVerzoekType.setANummer(asString(aNummer));
    }

    /**
     * Convenience constructor.
     * 
     * @param aNummer
     *            anummer
     */
    public LeesUitBrpVerzoekBericht(final String aNummer) {
        this();
        leesUitBrpVerzoekType.setANummer(aNummer);
    }

    /**
     * Convenience constructor.
     * 
     * @param aNummer
     *            anummer
     * @param antwoordFormaat
     *            antwoordFormaat (Lo3 | Brp)
     */
    public LeesUitBrpVerzoekBericht(final String aNummer, final AntwoordFormaatType antwoordFormaat) {
        this(aNummer);
        leesUitBrpVerzoekType.setAntwoordFormaat(antwoordFormaat);
    }

    /**
     * Convenience constructor.
     * 
     * @param anummer
     *            anummer
     * @param antwoordFormaat
     *            (Lo3 | Brp)
     */
    public LeesUitBrpVerzoekBericht(final long anummer, final AntwoordFormaatType antwoordFormaat) {
        this(asString(anummer), antwoordFormaat);
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer op het bericht terug.
     * 
     * @return Het A-nummer op het bericht.
     */
    public Long getANummer() {
        return asLong(leesUitBrpVerzoekType.getANummer());
    }

    /**
     * Geeft het gewenste antwoordFormaat terug.
     * 
     * @return Het AntwoordFormaatType.
     */
    public AntwoordFormaatType getAntwoordFormaat() {
        return leesUitBrpVerzoekType.getAntwoordFormaat();
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "LeesUitBrpVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "syncLeesUitBrpVerzoek";
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createLeesUitBrpVerzoek(leesUitBrpVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            leesUitBrpVerzoekType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, LeesUitBrpVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een LeesUitBrpVerzoekBericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LeesUitBrpVerzoekBericht)) {
            return false;
        }
        final LeesUitBrpVerzoekBericht castOther = (LeesUitBrpVerzoekBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(leesUitBrpVerzoekType.getANummer(), castOther.leesUitBrpVerzoekType.getANummer())
                .append(leesUitBrpVerzoekType.getAntwoordFormaat(),
                        castOther.leesUitBrpVerzoekType.getAntwoordFormaat()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(leesUitBrpVerzoekType.getANummer())
                .append(leesUitBrpVerzoekType.getAntwoordFormaat()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(leesUitBrpVerzoekType.getANummer()).append(leesUitBrpVerzoekType.getAntwoordFormaat())
                .toString();
    }

}
