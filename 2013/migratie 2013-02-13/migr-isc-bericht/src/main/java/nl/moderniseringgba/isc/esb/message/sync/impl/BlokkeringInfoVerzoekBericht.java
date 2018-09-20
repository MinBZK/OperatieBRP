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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Opvragen blokkering info bericht: opvragen status van de persoonsaanduiding in BRP.
 */
public final class BlokkeringInfoVerzoekBericht extends AbstractSyncBericht implements SyncBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String A_NUMMER_ELEMENT = "aNummer";

    private BlokkeringInfoVerzoekType blokkeringInfoVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringInfoVerzoekBericht() {
        blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param blokkeringInfoVerzoekType
     *            het blokkeringInfoVerzoek type
     */
    public BlokkeringInfoVerzoekBericht(final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType) {
        this.blokkeringInfoVerzoekType = blokkeringInfoVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "BlokkeringInfoVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createBlokkeringInfoVerzoek(blokkeringInfoVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            blokkeringInfoVerzoekType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, BlokkeringInfoVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een BlokkeringInfoVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer dat op het bericht staat.
     * 
     * @return Het A-nummer dat op het bericht staat.
     */
    public String getANummer() {
        return blokkeringInfoVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        blokkeringInfoVerzoekType.setANummer(aNummer);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlokkeringInfoVerzoekBericht)) {
            return false;
        }
        final BlokkeringInfoVerzoekBericht castOther = (BlokkeringInfoVerzoekBericht) other;
        return new EqualsBuilder().append(blokkeringInfoVerzoekType.getANummer(), castOther.getANummer()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(blokkeringInfoVerzoekType.getANummer()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(A_NUMMER_ELEMENT, blokkeringInfoVerzoekType.getANummer()).toString();
    }

}
