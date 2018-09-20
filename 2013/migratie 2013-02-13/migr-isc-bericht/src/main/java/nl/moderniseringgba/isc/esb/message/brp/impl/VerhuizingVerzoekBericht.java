/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.AbstractBrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.BrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Verhuisbericht.
 */
public final class VerhuizingVerzoekBericht extends AbstractBrpBericht implements BrpVerzoekBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String AFZENDER_ELEMENT = "afzender";
    private static final String A_NUMMER_ELEMENT = "aNummer";
    private static final String BURGERSERVICENUMMER_ELEMENT = "bsn";
    private static final String HUIDIGE_GEMEENTE_ELEMENT = "huidigeGemeente";
    private static final String NIEUWE_GEMEENTE_ELEMENT = "nieuweGemeente";
    private static final String DATUM_INSCHRIJVING_ELEMENT = "datumInschrijving";
    private static final String ADRESSEERBAAR_OBJECT_ELEMENT = "adresseerbaarObject";
    private static final String ID_NUMMERAANDUIDING_ELEMENT = "idNummeraanduiding";
    private static final String NAAM_OPENBARE_RUIMTE_ELEMENT = "naamOpenbareRuimte";
    private static final String AFGEKORTE_NAAM_OPENBARE_RUIMTE_ELEMENT = "afgekorteNaamOpenbareRuimte";
    private static final String HUISNUMMER_ELEMENT = "huisnummer";
    private static final String HUISLETTER_ELEMENT = "huisletter";
    private static final String HUISNUMMERTOEVOEGING_ELEMENT = "huisnummertoevoeging";
    private static final String POSTCODE_ELEMENT = "postcode";
    private static final String WOONPLAATS_ELEMENT = "woonplaats";
    private static final String LOCATIE_TOV_ADRES_ELEMENT = "locatieTovAdres";
    private static final String LOCATIE_OMSCHRIJVING_ELEMENT = "locatieOmschrijving";

    private VerhuizingVerzoekType verhuizingVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VerhuizingVerzoekBericht() {
        verhuizingVerzoekType = new VerhuizingVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param verhuizingVerzoekType
     *            het verhuizingVerzoek type
     */
    public VerhuizingVerzoekBericht(final VerhuizingVerzoekType verhuizingVerzoekType) {
        this.verhuizingVerzoekType = verhuizingVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "VerhuizingVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc302";
    }

    @Override
    public VerhuizingAntwoordBericht maakAntwoordBericht() {
        final VerhuizingAntwoordBericht antwoord = new VerhuizingAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(verhuizingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        verhuizingVerzoekType.setIscGemeenten(setBrpGemeente(verhuizingVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(verhuizingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        verhuizingVerzoekType.setIscGemeenten(setLo3Gemeente(verhuizingVerzoekType.getIscGemeenten(), gemeente));
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createVerhuizingVerzoek(verhuizingVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            verhuizingVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, VerhuizingVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een VerhuizingVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de afzender op het bericht terug.
     * 
     * @return De afzender op het bericht.
     */
    public String getAfzender() {
        return verhuizingVerzoekType.getAfzender();
    }

    /**
     * Zet de afzender op het bericht.
     * 
     * @param afzender
     *            De te zetten afzender
     */
    public void setAfzender(final String afzender) {
        verhuizingVerzoekType.setAfzender(afzender);
    }

    /**
     * Geeft het A-nummer op het bericht terug.
     * 
     * @return Het A-nummer op het bericht.
     */
    public String getANummer() {
        return verhuizingVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        verhuizingVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het BSN op het bericht terug.
     * 
     * @return Het BSN op het bericht.
     */
    public String getBsn() {
        return verhuizingVerzoekType.getBurgerservicenummer();
    }

    /**
     * Zet het BSN op het bericht.
     * 
     * @param bsn
     *            Het te zetten BSN.
     */
    public void setBsn(final String bsn) {
        verhuizingVerzoekType.setBurgerservicenummer(bsn);
    }

    /**
     * Geeft de huidige gemeente op het bericht terug.
     * 
     * @return De huidige gemeente op het bericht.
     */
    public String getHuidigeGemeente() {
        return verhuizingVerzoekType.getHuidigeGemeente();
    }

    /**
     * Zet de huidige gemeente op het bericht.
     * 
     * @param huidigeGemeente
     *            De te zetten huidige gemeente.
     */
    public void setHuidigeGemeente(final String huidigeGemeente) {
        verhuizingVerzoekType.setHuidigeGemeente(huidigeGemeente);
    }

    /**
     * Geeft de nieuwe gemeente op het bericht terug.
     * 
     * @return De nieuwe gemeente op het bericht.
     */
    public String getNieuweGemeente() {
        return verhuizingVerzoekType.getNieuweGemeente();
    }

    /**
     * Zet de nieuwe gemeente op het bericht.
     * 
     * @param nieuweGemeente
     *            De te zetten nieuwe gemeente.
     */
    public void setNieuweGemeente(final String nieuweGemeente) {
        verhuizingVerzoekType.setNieuweGemeente(nieuweGemeente);
    }

    /**
     * Geeft de datum van inschrijving op het bericht terug.
     * 
     * @return De datum van inschrijving op het bericht.
     */
    public String getDatumInschrijving() {
        if (verhuizingVerzoekType.getDatumInschrijving() == null) {
            return null;
        } else {
            return verhuizingVerzoekType.getDatumInschrijving().toString();
        }
    }

    /**
     * Zet de datum van inschrijving op het bericht.
     * 
     * @param datumInschrijving
     *            De te zetten datum van inschrijving.
     */
    public void setDatumInschrijving(final String datumInschrijving) {
        if (datumInschrijving == null || "".equals(datumInschrijving)) {
            verhuizingVerzoekType.setDatumInschrijving(null);
        } else {
            verhuizingVerzoekType.setDatumInschrijving(new BigInteger(datumInschrijving));
        }
    }

    /**
     * Geeft het adresseerbaar object op het bericht terug.
     * 
     * @return Het adresseerbaar object op het bericht.
     */
    public String getAdresseerbaarObject() {
        return verhuizingVerzoekType.getAdresseerbaarObject();
    }

    /**
     * Zet het adresseerbaar object op het bericht.
     * 
     * @param adresseerbaarObject
     *            Het te zetten adresseerbaar object.
     */
    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        verhuizingVerzoekType.setAdresseerbaarObject(adresseerbaarObject);
    }

    /**
     * Geeft het id nummer aanduiding op het bericht terug.
     * 
     * @return Het id nummer aanduiding op het bericht.
     */
    public String getIdNummerAanduiding() {
        return verhuizingVerzoekType.getIdNummeraanduiding();
    }

    /**
     * Zet het id nummer aanduiding op het bericht.
     * 
     * @param idNummerAanduiding
     *            Het te zetten id nummer aanduiding.
     */
    public void setIdNummerAanduiding(final String idNummerAanduiding) {
        verhuizingVerzoekType.setIdNummeraanduiding(idNummerAanduiding);
    }

    /**
     * Geeft de naam van de openbare ruimte op het bericht terug.
     * 
     * @return De naam van de openbare ruimte.
     */
    public String getNaamOpenbareRuimte() {
        return verhuizingVerzoekType.getNaamOpenbareRuimte();
    }

    /**
     * Zet de naam van de openbare ruimte op het bericht.
     * 
     * @param naamOpenbareRuimte
     *            De te zetten naam van de openbare ruimte.
     */
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        verhuizingVerzoekType.setNaamOpenbareRuimte(naamOpenbareRuimte);
    }

    /**
     * Geeft de afgekorte naam van de openbare ruimte op het bericht terug.
     * 
     * @return De afgekorte naam van de openbare ruimte op het bericht.
     */
    public String getAfgekorteNaamOpenbareRuimte() {
        return verhuizingVerzoekType.getAfgekorteNaamOpenbareRuimte();
    }

    /**
     * Zet de afgekorte naam van de openbare ruimte op het bericht.
     * 
     * @param afgekorteNaamOpenbareRuimte
     *            De te zetten afgekorte naam van de openbare ruimte.
     */
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        verhuizingVerzoekType.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
    }

    /**
     * Geeft het huisnummer op het bericht terug.
     * 
     * @return Het huisnummer op het bericht.
     */
    public String getHuisnummer() {
        return verhuizingVerzoekType.getHuisnummer();
    }

    /**
     * Zet het huisnummer op het bericht.
     * 
     * @param huisnummer
     *            Het te zetten huisnummer.
     */
    public void setHuisnummer(final String huisnummer) {
        verhuizingVerzoekType.setHuisnummer(huisnummer);
    }

    /**
     * Geeft de huisletter op het bericht terug.
     * 
     * @return De huisletter op het bericht.
     */
    public String getHuisletter() {
        return verhuizingVerzoekType.getHuisletter();
    }

    /**
     * Zet de huisletter op het bericht.
     * 
     * @param huisletter
     *            De te zetten huisletter.
     */
    public void setHuisletter(final String huisletter) {
        verhuizingVerzoekType.setHuisletter(huisletter);
    }

    /**
     * Geeft de huisnummertoevoeging op het bericht terug.
     * 
     * @return De huisnummertoevoeging op het bericht.
     */
    public String getHuisnummerToevoeging() {
        return verhuizingVerzoekType.getHuisnummertoevoeging();
    }

    /**
     * Zet de huisnummertoevoeging op het bericht.
     * 
     * @param huisnummerToevoeging
     *            De te zetten huisnummertoevoeging.
     */
    public void setHuisnummerToevoeging(final String huisnummerToevoeging) {
        verhuizingVerzoekType.setHuisnummertoevoeging(huisnummerToevoeging);
    }

    /**
     * Geeft de postcode op het bericht terug.
     * 
     * @return De postcode op het bericht.
     */
    public String getPostcode() {
        return verhuizingVerzoekType.getPostcode();
    }

    /**
     * Zet de postcode op het bericht.
     * 
     * @param postcode
     *            De te zetten postcode.
     */
    public void setPostcode(final String postcode) {
        verhuizingVerzoekType.setPostcode(postcode);
    }

    /**
     * Geeft de woonplaats op het bericht terug.
     * 
     * @return De woonplaats op het bericht.
     */
    public String getWoonplaats() {
        return verhuizingVerzoekType.getWoonplaats();
    }

    /**
     * Zet de woonplaats op het bericht.
     * 
     * @param woonplaats
     *            De te zetten woonplaats.
     */
    public void setWoonplaats(final String woonplaats) {
        verhuizingVerzoekType.setWoonplaats(woonplaats);
    }

    /**
     * Geeft de locatie t.o.v. het adres op het bericht terug.
     * 
     * @return De locatie t.o.v. het adres op het bericht.
     */
    public String getLocatieTovAdres() {
        return verhuizingVerzoekType.getLocatieTovAdres();
    }

    /**
     * Zet de locatie t.o.v. het adres op het bericht.
     * 
     * @param locatieTovAdres
     *            De te zetten locatie t.o.v. het adres.
     */
    public void setLocatieTovAdres(final String locatieTovAdres) {
        verhuizingVerzoekType.setLocatieTovAdres(locatieTovAdres);
    }

    /**
     * Geeft de locatie omschrijving op het bericht terug.
     * 
     * @return De locatie omschrijving op het bericht.
     */
    public String getLocatieOmschrijving() {
        return verhuizingVerzoekType.getLocatieOmschrijving();
    }

    /**
     * Zet de locatie omschrijving op het bericht.
     * 
     * @param locatieOmschrijving
     *            De te zetten locatie omschrijving.
     */
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        verhuizingVerzoekType.setLocatieOmschrijving(locatieOmschrijving);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VerhuizingVerzoekBericht)) {
            return false;
        }
        final VerhuizingVerzoekBericht castOther = (VerhuizingVerzoekBericht) other;
        return new EqualsBuilder().append(getAfzender(), castOther.getAfzender())
                .append(getANummer(), castOther.getANummer()).append(getBsn(), castOther.getBsn())
                .append(getHuidigeGemeente(), castOther.getHuidigeGemeente())
                .append(getNieuweGemeente(), castOther.getNieuweGemeente())
                .append(getDatumInschrijving(), castOther.getDatumInschrijving())
                .append(getAdresseerbaarObject(), castOther.getAdresseerbaarObject())
                .append(getIdNummerAanduiding(), castOther.getIdNummerAanduiding())
                .append(getNaamOpenbareRuimte(), castOther.getNaamOpenbareRuimte())
                .append(getAfgekorteNaamOpenbareRuimte(), castOther.getAfgekorteNaamOpenbareRuimte())
                .append(getHuisnummer(), castOther.getHuisnummer())
                .append(getHuisletter(), castOther.getHuisletter())
                .append(getHuisnummerToevoeging(), castOther.getHuisnummerToevoeging())
                .append(getPostcode(), castOther.getPostcode()).append(getWoonplaats(), castOther.getWoonplaats())
                .append(getLocatieTovAdres(), castOther.getLocatieTovAdres())
                .append(getLocatieOmschrijving(), castOther.getLocatieOmschrijving()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getAfzender()).append(getANummer()).append(getBsn())
                .append(getHuidigeGemeente()).append(getNieuweGemeente()).append(getDatumInschrijving())
                .append(getAdresseerbaarObject()).append(getIdNummerAanduiding()).append(getNaamOpenbareRuimte())
                .append(getAfgekorteNaamOpenbareRuimte()).append(getHuisnummer()).append(getHuisletter())
                .append(getHuisnummerToevoeging()).append(getPostcode()).append(getWoonplaats())
                .append(getLocatieTovAdres()).append(getLocatieOmschrijving()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(AFZENDER_ELEMENT, getAfzender()).append(A_NUMMER_ELEMENT, getANummer())
                .append(BURGERSERVICENUMMER_ELEMENT, getBsn()).append(HUIDIGE_GEMEENTE_ELEMENT, getHuidigeGemeente())
                .append(NIEUWE_GEMEENTE_ELEMENT, getNieuweGemeente())
                .append(DATUM_INSCHRIJVING_ELEMENT, getDatumInschrijving())
                .append(ADRESSEERBAAR_OBJECT_ELEMENT, getAdresseerbaarObject())
                .append(ID_NUMMERAANDUIDING_ELEMENT, getIdNummerAanduiding())
                .append(NAAM_OPENBARE_RUIMTE_ELEMENT, getNaamOpenbareRuimte())
                .append(AFGEKORTE_NAAM_OPENBARE_RUIMTE_ELEMENT, getAfgekorteNaamOpenbareRuimte())
                .append(HUISNUMMER_ELEMENT, getHuisnummer()).append(HUISLETTER_ELEMENT, getHuisletter())
                .append(HUISNUMMERTOEVOEGING_ELEMENT, getHuisnummerToevoeging())
                .append(POSTCODE_ELEMENT, getPostcode()).append(WOONPLAATS_ELEMENT, getWoonplaats())
                .append(LOCATIE_TOV_ADRES_ELEMENT, getLocatieTovAdres())
                .append(LOCATIE_OMSCHRIJVING_ELEMENT, getLocatieOmschrijving()).toString();
    }

}
