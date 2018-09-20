/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.AbstractBrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsaanduidingCodeS;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Zoek Persoon bericht: ga na of de persoon uniek kan worden geidentificeerd in BRP.
 */
public final class ZoekPersoonVerzoekBericht extends AbstractBrpBericht implements BrpBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String ANUMMER_ELEMENT = "aNummer";
    private static final String BSN_ELEMENT = "burgerservicenummer";
    private static final String GESLACHTSNAAM_ELEMENT = "geslachtsnaam";
    private static final String GEBOORTEDATUM_ELEMENT = "geboortedatum";
    private static final String GESLACHTSAANDUIDING_ELEMENT = "geslachtsaanduiding";
    private static final String VOORNAMEN_ELEMENT = "voornamen";
    private static final String POSTCODE_ELEMENT = "postcode";
    private static final String BIJHOUDINGSGEMEENTE_ELEMENT = "bijhoudingsgemeente";

    private ZoekPersoonVerzoekType zoekPersoonVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ZoekPersoonVerzoekBericht() {
        zoekPersoonVerzoekType = new ZoekPersoonVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param zoekPersoonVerzoekType
     *            het zoekPersoonVerzoek type
     */
    public ZoekPersoonVerzoekBericht(final ZoekPersoonVerzoekType zoekPersoonVerzoekType) {
        this.zoekPersoonVerzoekType = zoekPersoonVerzoekType;
    }

    /* ************************************************************************************************************* */
    @Override
    public String getBerichtType() {
        return "ZoekPersoonVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createZoekPersoonVerzoek(zoekPersoonVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            zoekPersoonVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, ZoekPersoonVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ZoekPersoonVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer op het bericht terug.
     * 
     * @return Het A-nummer op het bericht.
     */
    public String getANummer() {
        return zoekPersoonVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        zoekPersoonVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het BSN op het bericht terug.
     * 
     * @return Het BSN op het bericht.
     */
    public String getBsn() {
        return zoekPersoonVerzoekType.getBurgerservicenummer();
    }

    /**
     * Zet het BSN op het bericht.
     * 
     * @param bsn
     *            Het te zetten BSN.
     */
    public void setBsn(final String bsn) {
        zoekPersoonVerzoekType.setBurgerservicenummer(bsn);
    }

    /**
     * Geeft de geslachtsnaam op het bericht terug.
     * 
     * @return De geslachtsnaam op het bericht.
     */
    public String getGeslachtsnaam() {
        return zoekPersoonVerzoekType.getGeslachtsnaam();
    }

    /**
     * Zet de geslachtsnaam op het bericht.
     * 
     * @param geslachtsnaam
     *            De te zetten geslachtsnaam.
     */
    public void setGeslachtsnaam(final String geslachtsnaam) {
        zoekPersoonVerzoekType.setGeslachtsnaam(geslachtsnaam);
    }

    /**
     * Geeft de geboortedatum {@link BrpDatum} op het bericht terug.
     * 
     * @return De geboortedatum {@link BrpDatum} op het bericht.
     */
    public BrpDatum getGeboorteDatum() {
        if (zoekPersoonVerzoekType.getGeboortedatum() == null) {
            return null;
        } else {
            return new BrpDatum(zoekPersoonVerzoekType.getGeboortedatum().intValue());
        }
    }

    /**
     * Zet de geboortedatum {@link BrpDatum} op het bericht.
     * 
     * @param geboorteDatum
     *            De te zetten geboortedatum {@link BrpDatum}
     */
    public void setGeboorteDatum(final BrpDatum geboorteDatum) {
        if (geboorteDatum == null) {
            zoekPersoonVerzoekType.setGeboortedatum(null);
        } else {
            zoekPersoonVerzoekType.setGeboortedatum(new BigInteger(String.valueOf(geboorteDatum.getDatum())));
        }
    }

    /**
     * Geeft de geslachtsaanduiding {@link GeslachtsaanduidingCodeS} op het bericht terug.
     * 
     * @return De geslachtsaanduiding {@link GeslachtsaanduidingCodeS} op het bericht.
     */
    public GeslachtsaanduidingCodeS getGeslachtsaanduiding() {
        return zoekPersoonVerzoekType.getGeslachtsaanduiding();
    }

    /**
     * Zet de geslachtsaanduiding {@link GeslachtsaanduidingCodeS} op het bericht.
     * 
     * @param geslachtsaanduiding
     *            De te zetten geslachtsaanduiding {@link GeslachtsaanduidingCodeS}.
     */
    public void setGeslachtsaanduiding(final GeslachtsaanduidingCodeS geslachtsaanduiding) {
        zoekPersoonVerzoekType.setGeslachtsaanduiding(geslachtsaanduiding);
    }

    /**
     * Geeft de voornamen op het bericht terug.
     * 
     * @return De voornamen op het bericht.
     */
    public String getVoornamen() {
        return zoekPersoonVerzoekType.getVoornamen();
    }

    /**
     * Zet de voornamen op het bericht.
     * 
     * @param voornamen
     *            De te zetten voornamen.
     */
    public void setVoornamen(final String voornamen) {
        zoekPersoonVerzoekType.setVoornamen(voornamen);
    }

    /**
     * Geeft de postcode op het bericht terug.
     * 
     * @return De postcode op het bericht.
     */
    public String getPostcode() {
        return zoekPersoonVerzoekType.getPostcode();
    }

    /**
     * Zet de postcode op het bericht.
     * 
     * @param postcode
     *            De te zetten postcode.
     */
    public void setPostcode(final String postcode) {
        zoekPersoonVerzoekType.setPostcode(postcode);
    }

    /**
     * Geeft de bijhoudingsgemeente op het bericht terug.
     * 
     * @return De bijhoudingsgemeente op het bericht.
     */
    public BrpGemeenteCode getBijhoudingsGemeente() {
        if (zoekPersoonVerzoekType.getBijhoudingsgemeente() == null) {
            return null;
        } else {
            return new BrpGemeenteCode(new BigDecimal(zoekPersoonVerzoekType.getBijhoudingsgemeente()));
        }
    }

    /**
     * Zet de bijhoudingsgemeente op het bericht.
     * 
     * @param bijhoudingsGemeente
     *            De te zetten bijhoudingsgemeente.
     */
    public void setBijhoudingsGemeente(final BrpGemeenteCode bijhoudingsGemeente) {
        if (bijhoudingsGemeente == null) {
            zoekPersoonVerzoekType.setBijhoudingsgemeente(null);
        } else {
            zoekPersoonVerzoekType.setBijhoudingsgemeente(bijhoudingsGemeente.getFormattedStringCode());
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ZoekPersoonVerzoekBericht)) {
            return false;
        }
        final ZoekPersoonVerzoekBericht castOther = (ZoekPersoonVerzoekBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(getANummer(), castOther.getANummer())
                .append(getBsn(), castOther.getBsn()).append(getGeslachtsnaam(), castOther.getGeslachtsnaam())
                .append(getGeboorteDatum(), castOther.getGeboorteDatum())
                .append(getGeslachtsaanduiding(), castOther.getGeslachtsaanduiding())
                .append(getVoornamen(), castOther.getVoornamen()).append(getPostcode(), castOther.getPostcode())
                .append(getBijhoudingsGemeente(), castOther.getBijhoudingsGemeente()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(getANummer()).append(getBsn())
                .append(getGeslachtsnaam()).append(getGeboorteDatum()).append(getGeslachtsaanduiding())
                .append(getVoornamen()).append(getPostcode()).append(getBijhoudingsGemeente()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(ANUMMER_ELEMENT, getANummer()).append(BSN_ELEMENT, getBsn())
                .append(GESLACHTSNAAM_ELEMENT, getGeslachtsnaam()).append(GEBOORTEDATUM_ELEMENT, getGeboorteDatum())
                .append(GESLACHTSAANDUIDING_ELEMENT, getGeslachtsaanduiding())
                .append(VOORNAMEN_ELEMENT, getVoornamen()).append(POSTCODE_ELEMENT, getPostcode())
                .append(BIJHOUDINGSGEMEENTE_ELEMENT, getBijhoudingsGemeente()).toString();
    }

}
