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
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.SynchronisatieSignaalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Bericht om de synchronisatie van een PL van BRP naar GBA-v te starten.
 */
public final class SynchronisatieSignaalBericht extends AbstractBrpBericht implements BrpBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String A_NUMMER_ELEMENT = "aNummer";

    private SynchronisatieSignaalType synchronisatieSignaalType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public SynchronisatieSignaalBericht() {
        synchronisatieSignaalType = new SynchronisatieSignaalType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param synchronisatieSignaalType
     *            het synchronisatieSignaal type
     */
    public SynchronisatieSignaalBericht(final SynchronisatieSignaalType synchronisatieSignaalType) {
        this.synchronisatieSignaalType = synchronisatieSignaalType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "SynchronisatieSignaal";
    }

    @Override
    public String getStartCyclus() {
        return "uc201";
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createSynchronisatieSignaal(synchronisatieSignaalType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            synchronisatieSignaalType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, SynchronisatieSignaalType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een BlokkeringVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * @return the aNummer
     */
    public String getANummer() {
        return synchronisatieSignaalType.getANummer();
    }

    /**
     * @param aNummer
     *            the aNummer to set
     */
    public void setANummer(final String aNummer) {
        synchronisatieSignaalType.setANummer(aNummer);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SynchronisatieSignaalBericht)) {
            return false;
        }
        final SynchronisatieSignaalBericht castOther = (SynchronisatieSignaalBericht) other;
        return new EqualsBuilder().append(synchronisatieSignaalType.getANummer(), castOther.getANummer()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(synchronisatieSignaalType.getANummer()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(A_NUMMER_ELEMENT, synchronisatieSignaalType.getANummer()).toString();
    }

}
