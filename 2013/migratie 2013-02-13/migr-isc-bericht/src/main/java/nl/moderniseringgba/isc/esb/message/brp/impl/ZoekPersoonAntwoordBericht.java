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
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Zoek Persoon bericht: ga na of de persoon uniek kan worden geidentificeerd in BRP.
 */
public final class ZoekPersoonAntwoordBericht extends AbstractBrpBericht implements BrpBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";
    private static final String GEVONDEN_PERSONEN_ELEMENT = "gevondenPersonen";

    private ZoekPersoonAntwoordType zoekPersoonAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ZoekPersoonAntwoordBericht() {
        zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param zoekPersoonAntwoordType
     *            het zoekPersoonAntwoord type
     */
    public ZoekPersoonAntwoordBericht(final ZoekPersoonAntwoordType zoekPersoonAntwoordType) {
        this.zoekPersoonAntwoordType = zoekPersoonAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "ZoekPersoonAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createZoekPersoonAntwoord(zoekPersoonAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            zoekPersoonAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, ZoekPersoonAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ZoekPersoonAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        zoekPersoonAntwoordType.setStatus(status);
    }

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return zoekPersoonAntwoordType.getStatus();
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return zoekPersoonAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        zoekPersoonAntwoordType.setToelichting(toelichting);
    }

    /**
     * @return het aantal gevonden personen
     */
    public int getAantalGevondenPersonen() {
        if (zoekPersoonAntwoordType.getGevondenPersonen() == null
                || zoekPersoonAntwoordType.getGevondenPersonen().getGevondenPersoon() == null) {
            return 0;
        } else {
            return zoekPersoonAntwoordType.getGevondenPersonen().getGevondenPersoon().size();
        }
    }

    /**
     * @return het anummer van de uniek gevonden persoon
     */
    public String getSingleAnummer() {
        if (getAantalGevondenPersonen() != 1) {
            return null;
        }
        return zoekPersoonAntwoordType.getGevondenPersonen().getGevondenPersoon().get(0).getANummer();

    }

    /**
     * @return de gemeente van de uniek gevonden persoon
     */
    public String getSingleGemeente() {
        if (getAantalGevondenPersonen() != 1) {
            return null;
        }
        return zoekPersoonAntwoordType.getGevondenPersonen().getGevondenPersoon().get(0).getBijhoudingsgemeente();

    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ZoekPersoonAntwoordBericht)) {
            return false;
        }
        final ZoekPersoonAntwoordBericht castOther = (ZoekPersoonAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(zoekPersoonAntwoordType.getStatus(), castOther.getStatus())
                .append(zoekPersoonAntwoordType.getToelichting(), castOther.getToelichting())
                .append(getAantalGevondenPersonen(), castOther.getAantalGevondenPersonen()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(zoekPersoonAntwoordType.getStatus())
                .append(zoekPersoonAntwoordType.getToelichting())
                .append(zoekPersoonAntwoordType.getGevondenPersonen()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, zoekPersoonAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, zoekPersoonAntwoordType.getToelichting())
                .append(GEVONDEN_PERSONEN_ELEMENT, zoekPersoonAntwoordType.getGevondenPersonen()).toString();
    }

}
