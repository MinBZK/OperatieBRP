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
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Deblokkering bericht: verwijderen persoonsaanduiding in BRP.
 */
public final class DeblokkeringVerzoekBericht extends AbstractSyncBericht implements SyncBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String ANUMMER_ELEMENT = "aNummer";
    private static final String PROCESS_ID_ELEMENT = "processId";

    private DeblokkeringVerzoekType deblokkeringVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public DeblokkeringVerzoekBericht() {
        deblokkeringVerzoekType = new DeblokkeringVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param deblokkeringVerzoekType
     *            het deblokkeringVerzoek type
     */
    public DeblokkeringVerzoekBericht(final DeblokkeringVerzoekType deblokkeringVerzoekType) {
        this.deblokkeringVerzoekType = deblokkeringVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "DeblokkeringVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createDeblokkeringVerzoek(deblokkeringVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            deblokkeringVerzoekType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, DeblokkeringVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een DeblokkeringVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer dat op het bericht staat.
     * 
     * @return Het A-nummer dat op het bericht staat.
     */
    public String getANummer() {
        return deblokkeringVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        deblokkeringVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het process ID dat op het bericht staat.
     * 
     * @return Het process ID
     */
    public String getProcessId() {
        return deblokkeringVerzoekType.getProcessId();
    }

    /**
     * Zet het proces ID op het bericht.
     * 
     * @param processId
     *            Het te zetten proces ID.
     */
    public void setProcessId(final String processId) {
        deblokkeringVerzoekType.setProcessId(processId);
    }

    /**
     * Geeft de registratiegemeente van de blokkering dat op het bericht staat.
     * 
     * @return De registratiegemeente van de blokkering
     */
    public String getGemeenteRegistratie() {
        return deblokkeringVerzoekType.getGemeenteRegistratie();
    }

    /**
     * Zet de registratiegemeente van de blokkering op het bericht.
     * 
     * @param gemeenteRegistratie
     *            De te zetten registratiegemeente.
     */
    public void setGemeenteRegistratie(final String gemeenteRegistratie) {
        deblokkeringVerzoekType.setGemeenteRegistratie(gemeenteRegistratie);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeblokkeringVerzoekBericht)) {
            return false;
        }
        final DeblokkeringVerzoekBericht castOther = (DeblokkeringVerzoekBericht) other;
        return new EqualsBuilder().append(deblokkeringVerzoekType.getANummer(), castOther.getANummer())
                .append(deblokkeringVerzoekType.getProcessId(), castOther.getProcessId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(deblokkeringVerzoekType.getANummer())
                .append(deblokkeringVerzoekType.getProcessId()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(ANUMMER_ELEMENT, deblokkeringVerzoekType.getANummer())
                .append(PROCESS_ID_ELEMENT, deblokkeringVerzoekType.getProcessId()).toString();
    }

}
