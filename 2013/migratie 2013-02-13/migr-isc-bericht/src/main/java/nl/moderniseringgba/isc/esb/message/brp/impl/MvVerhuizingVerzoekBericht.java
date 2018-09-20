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
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeenten;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvVerhuizingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * MvVerhuisbericht.
 */
public final class MvVerhuizingVerzoekBericht extends AbstractBrpBericht implements BrpBericht, Serializable {
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

    private MvVerhuizingVerzoekType mvVerhuizingVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public MvVerhuizingVerzoekBericht() {
        mvVerhuizingVerzoekType = new MvVerhuizingVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param verhuizingVerzoekType
     *            het verhuizingVerzoek type
     */
    public MvVerhuizingVerzoekBericht(final MvVerhuizingVerzoekType verhuizingVerzoekType) {
        this.mvVerhuizingVerzoekType = verhuizingVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "MvVerhuizingVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(mvVerhuizingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        mvVerhuizingVerzoekType.setIscGemeenten(setBrpGemeente(mvVerhuizingVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(mvVerhuizingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        mvVerhuizingVerzoekType.setIscGemeenten(setLo3Gemeente(mvVerhuizingVerzoekType.getIscGemeenten(), gemeente));
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createMvVerhuizingVerzoek(mvVerhuizingVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            mvVerhuizingVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, MvVerhuizingVerzoekType.class)
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
        return mvVerhuizingVerzoekType.getAfzender();
    }

    /**
     * Zet de afzender op het bericht.
     * 
     * @param afzender
     *            De te zetten afzender
     */
    public void setAfzender(final String afzender) {
        mvVerhuizingVerzoekType.setAfzender(afzender);
    }

    /**
     * Geeft het A-nummer op het bericht terug.
     * 
     * @return Het A-nummer op het bericht.
     */
    public String getANummer() {
        return mvVerhuizingVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        mvVerhuizingVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het BSN op het bericht terug.
     * 
     * @return Het BSN op het bericht.
     */
    public String getBsn() {
        return mvVerhuizingVerzoekType.getBurgerservicenummer();
    }

    /**
     * Zet het BSN op het bericht.
     * 
     * @param bsn
     *            Het te zetten BSN.
     */
    public void setBsn(final String bsn) {
        mvVerhuizingVerzoekType.setBurgerservicenummer(bsn);
    }

    /**
     * Geeft de huidige gemeente op het bericht terug.
     * 
     * @return De huidige gemeente op het bericht.
     */
    public String getHuidigeGemeente() {
        return mvVerhuizingVerzoekType.getHuidigeGemeente();
    }

    /**
     * Zet de huidige gemeente op het bericht.
     * 
     * @param huidigeGemeente
     *            De te zetten huidige gemeente.
     */
    public void setHuidigeGemeente(final String huidigeGemeente) {
        mvVerhuizingVerzoekType.setHuidigeGemeente(huidigeGemeente);
    }

    /**
     * Geeft de nieuwe gemeente op het bericht terug.
     * 
     * @return De nieuwe gemeente op het bericht.
     */
    public String getNieuweGemeente() {
        return mvVerhuizingVerzoekType.getNieuweGemeente();
    }

    /**
     * Geeft de IscGemeenten {@link IscGemeenten} op het bericht terug.
     * 
     * @return De IscGemeenten {@link IscGemeenten} op het bericht.
     */
    public IscGemeenten getIscGemeenten() {
        if (mvVerhuizingVerzoekType.getIscGemeenten() == null) {
            return null;
        } else {
            return mvVerhuizingVerzoekType.getIscGemeenten();
        }
    }

    /**
     * Zet de IscGemeenten {@link IscGemeente} op het bericht.
     * 
     * @param iscGemeenten
     *            De te zetten IscGemeenten {@link IscGemeenten}
     */
    public void setIscGemeenten(final IscGemeenten iscGemeenten) {
        if (iscGemeenten == null) {
            mvVerhuizingVerzoekType.setIscGemeenten(null);
        } else {
            mvVerhuizingVerzoekType.setIscGemeenten(iscGemeenten);
        }
    }

    /**
     * Zet de nieuwe gemeente op het bericht.
     * 
     * @param nieuweGemeente
     *            De te zetten nieuwe gemeente.
     */
    public void setNieuweGemeente(final String nieuweGemeente) {
        mvVerhuizingVerzoekType.setNieuweGemeente(nieuweGemeente);
    }

    /**
     * Geeft de datum van inschrijving op het bericht terug.
     * 
     * @return De datum van inschrijving op het bericht.
     */
    public String getDatumInschrijving() {
        if (mvVerhuizingVerzoekType.getDatumInschrijving() == null) {
            return null;
        } else {
            return mvVerhuizingVerzoekType.getDatumInschrijving().toString();
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
            mvVerhuizingVerzoekType.setDatumInschrijving(null);
        } else {
            mvVerhuizingVerzoekType.setDatumInschrijving(new BigInteger(datumInschrijving));
        }
    }

    /**
     * Geeft het adresseerbaar object op het bericht terug.
     * 
     * @return Het adresseerbaar object op het bericht.
     */
    public String getAdresseerbaarObject() {
        return mvVerhuizingVerzoekType.getAdresseerbaarObject();
    }

    /**
     * Zet het adresseerbaar object op het bericht.
     * 
     * @param adresseerbaarObject
     *            Het te zetten adresseerbaar object.
     */
    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        mvVerhuizingVerzoekType.setAdresseerbaarObject(adresseerbaarObject);
    }

    /**
     * Geeft het id nummer aanduiding op het bericht terug.
     * 
     * @return Het id nummer aanduiding op het bericht.
     */
    public String getIdNummerAanduiding() {
        return mvVerhuizingVerzoekType.getIdNummeraanduiding();
    }

    /**
     * Zet het id nummer aanduiding op het bericht.
     * 
     * @param idNummerAanduiding
     *            Het te zetten id nummer aanduiding.
     */
    public void setIdNummerAanduiding(final String idNummerAanduiding) {
        mvVerhuizingVerzoekType.setIdNummeraanduiding(idNummerAanduiding);
    }

    /**
     * Geeft de naam van de openbare ruimte op het bericht terug.
     * 
     * @return De naam van de openbare ruimte.
     */
    public String getNaamOpenbareRuimte() {
        return mvVerhuizingVerzoekType.getNaamOpenbareRuimte();
    }

    /**
     * Zet de naam van de openbare ruimte op het bericht.
     * 
     * @param naamOpenbareRuimte
     *            De te zetten naam van de openbare ruimte.
     */
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        mvVerhuizingVerzoekType.setNaamOpenbareRuimte(naamOpenbareRuimte);
    }

    /**
     * Geeft de afgekorte naam van de openbare ruimte op het bericht terug.
     * 
     * @return De afgekorte naam van de openbare ruimte op het bericht.
     */
    public String getAfgekorteNaamOpenbareRuimte() {
        return mvVerhuizingVerzoekType.getAfgekorteNaamOpenbareRuimte();
    }

    /**
     * Zet de afgekorte naam van de openbare ruimte op het bericht.
     * 
     * @param afgekorteNaamOpenbareRuimte
     *            De te zetten afgekorte naam van de openbare ruimte.
     */
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        mvVerhuizingVerzoekType.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
    }

    /**
     * Geeft het huisnummer op het bericht terug.
     * 
     * @return Het huisnummer op het bericht.
     */
    public String getHuisnummer() {
        return mvVerhuizingVerzoekType.getHuisnummer();
    }

    /**
     * Zet het huisnummer op het bericht.
     * 
     * @param huisnummer
     *            Het te zetten huisnummer.
     */
    public void setHuisnummer(final String huisnummer) {
        mvVerhuizingVerzoekType.setHuisnummer(huisnummer);
    }

    /**
     * Geeft de huisletter op het bericht terug.
     * 
     * @return De huisletter op het bericht.
     */
    public String getHuisletter() {
        return mvVerhuizingVerzoekType.getHuisletter();
    }

    /**
     * Zet de huisletter op het bericht.
     * 
     * @param huisletter
     *            De te zetten huisletter.
     */
    public void setHuisletter(final String huisletter) {
        mvVerhuizingVerzoekType.setHuisletter(huisletter);
    }

    /**
     * Geeft de huisnummertoevoeging op het bericht terug.
     * 
     * @return De huisnummertoevoeging op het bericht.
     */
    public String getHuisnummerToevoeging() {
        return mvVerhuizingVerzoekType.getHuisnummertoevoeging();
    }

    /**
     * Zet de huisnummertoevoeging op het bericht.
     * 
     * @param huisnummerToevoeging
     *            De te zetten huisnummertoevoeging.
     */
    public void setHuisnummerToevoeging(final String huisnummerToevoeging) {
        mvVerhuizingVerzoekType.setHuisnummertoevoeging(huisnummerToevoeging);
    }

    /**
     * Geeft de postcode op het bericht terug.
     * 
     * @return De postcode op het bericht.
     */
    public String getPostcode() {
        return mvVerhuizingVerzoekType.getPostcode();
    }

    /**
     * Zet de postcode op het bericht.
     * 
     * @param postcode
     *            De te zetten postcode.
     */
    public void setPostcode(final String postcode) {
        mvVerhuizingVerzoekType.setPostcode(postcode);
    }

    /**
     * Geeft de woonplaats op het bericht terug.
     * 
     * @return De woonplaats op het bericht.
     */
    public String getWoonplaats() {
        return mvVerhuizingVerzoekType.getWoonplaats();
    }

    /**
     * Zet de woonplaats op het bericht.
     * 
     * @param woonplaats
     *            De te zetten woonplaats.
     */
    public void setWoonplaats(final String woonplaats) {
        mvVerhuizingVerzoekType.setWoonplaats(woonplaats);
    }

    /**
     * Geeft de locatie t.o.v. het adres op het bericht terug.
     * 
     * @return De locatie t.o.v. het adres op het bericht.
     */
    public String getLocatieTovAdres() {
        return mvVerhuizingVerzoekType.getLocatieTovAdres();
    }

    /**
     * Zet de locatie t.o.v. het adres op het bericht.
     * 
     * @param locatieTovAdres
     *            De te zetten locatie t.o.v. het adres.
     */
    public void setLocatieTovAdres(final String locatieTovAdres) {
        mvVerhuizingVerzoekType.setLocatieTovAdres(locatieTovAdres);
    }

    /**
     * Geeft de locatie omschrijving op het bericht terug.
     * 
     * @return De locatie omschrijving op het bericht.
     */
    public String getLocatieOmschrijving() {
        return mvVerhuizingVerzoekType.getLocatieOmschrijving();
    }

    /**
     * Zet de locatie omschrijving op het bericht.
     * 
     * @param locatieOmschrijving
     *            De te zetten locatie omschrijving.
     */
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        mvVerhuizingVerzoekType.setLocatieOmschrijving(locatieOmschrijving);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MvVerhuizingVerzoekBericht)) {
            return false;
        }
        final MvVerhuizingVerzoekBericht castOther = (MvVerhuizingVerzoekBericht) other;
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
