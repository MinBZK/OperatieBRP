/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;
import nl.bzk.brp.service.algemeen.request.BrpVerzoekElement;
import nl.bzk.brp.service.algemeen.request.BrpVerzoekParameter;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;

/**
 * Het {@link BevragingVerzoek} specifiek voor GeefMedebewoners.
 */
public final class GeefMedebewonersVerzoek extends VerzoekBasis implements BevragingVerzoek {

    private final GeefMedebewonersParameters parameters = new GeefMedebewonersParameters();
    private final Identificatiecriteria identificatiecriteria = new Identificatiecriteria();

    @Override
    public GeefMedebewonersParameters getParameters() {
        return parameters;
    }

    /**
     * Gets identificatiecriteria.
     * @return identificatiecriteria identificatiecriteria
     */
    public Identificatiecriteria getIdentificatiecriteria() {
        return identificatiecriteria;
    }


    /**
     * Bepaal of er gezocht moet worden naar medebewoners met gelijke identificatiecode adresseerbaar object..
     * @return true als ja, false als nee
     */
    boolean geefMedebewonersGelijkeIDcodeAdresseerbaarObject() {
        return identificatiecriteria.identificatiecodeAdresseerbaarObject != null;
    }


    /**
     * Bepaal of er gezocht moet worden naar medebewoners met gelijke BAG.
     * @return true als ja, false als nee
     */
    boolean geefMedebewonersGelijkeBAG() {
        return identificatiecriteria.identificatiecodeNummeraanduiding != null;
    }

    /**
     * Bepaal of er gezocht moet worden naar medebewoners met gelijk adres.
     * @return true als ja, false als nee
     */
    boolean geefMedebewonersGelijkAdres() {
        boolean zoekenOpGelijkAdres = identificatiecriteria.gemeenteCode != null;
        zoekenOpGelijkAdres |= identificatiecriteria.afgekorteNaamOpenbareRuimte != null;
        zoekenOpGelijkAdres |= identificatiecriteria.huisnummer != null;
        zoekenOpGelijkAdres |= identificatiecriteria.huisletter != null;
        zoekenOpGelijkAdres |= identificatiecriteria.huisnummertoevoeging != null;
        zoekenOpGelijkAdres |= identificatiecriteria.locatieTenOpzichteVanAdres != null;
        zoekenOpGelijkAdres |= identificatiecriteria.postcode != null;
        zoekenOpGelijkAdres |= identificatiecriteria.woonplaatsnaam != null;
        return zoekenOpGelijkAdres;
    }

    /**
     * Bepaal of er gezocht moet worden naar medebewoners van een bepaalde persoon.
     * @return true als ja, false als nee
     */
    boolean geefMedebewonersVanPersoon() {
        return identificatiecriteria.burgerservicenummer != null
                || identificatiecriteria.administratienummer != null
                || identificatiecriteria.objectSleutel != null;
    }

    /**
     * GeefMedebewonersParameters.
     */
    public static final class GeefMedebewonersParameters extends Parameters {
        private String peilmomentMaterieel;

        /**
         * Gets peilmoment materieel.
         * @return the peilmoment materieel
         */
        public String getPeilmomentMaterieel() {
            return peilmomentMaterieel;
        }

        /**
         * Sets peilmoment materieel.
         * @param peilmomentMaterieel the peilmoment materieel
         */
        public void setPeilmomentMaterieel(final String peilmomentMaterieel) {
            this.peilmomentMaterieel = peilmomentMaterieel;
        }
    }

    /**
     * Identificatiecriteria.
     */
    @BrpVerzoekElement(element = "identificatiecriteria")
    public static final class Identificatiecriteria extends BerichtGegevens {
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, type = ZoekCriteriaType.PERSOON)
        private String burgerservicenummer;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, type = ZoekCriteriaType.PERSOON)
        private String administratienummer;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_OBJECTSLEUTEL, type = ZoekCriteriaType.PERSOON)
        private String objectSleutel;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_GEMEENTECODE, type = ZoekCriteriaType.ADRES)
        private String gemeenteCode;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE, type = ZoekCriteriaType.ADRES)
        private String afgekorteNaamOpenbareRuimte;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_HUISNUMMER, type = ZoekCriteriaType.ADRES)
        private String huisnummer;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_HUISLETTER, type = ZoekCriteriaType.ADRES)
        private String huisletter;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING, type = ZoekCriteriaType.ADRES)
        private String huisnummertoevoeging;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, type = ZoekCriteriaType.ADRES)
        private String locatieTenOpzichteVanAdres;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_POSTCODE, type = ZoekCriteriaType.ADRES)
        private String postcode;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_WOONPLAATSNAAM, type = ZoekCriteriaType.ADRES)
        private String woonplaatsnaam;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT, type = ZoekCriteriaType.ADRES)
        private String identificatiecodeAdresseerbaarObject;
        @BrpVerzoekParameter
        @ZoekCriteriaElement(element = Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING, type = ZoekCriteriaType.BAG)
        private String identificatiecodeNummeraanduiding;

        /**
         * Gets administratienummer.
         * @return the administratienummer
         */
        public String getAdministratienummer() {
            return administratienummer;
        }

        /**
         * Sets administratienummer.
         * @param administratienummer the administratienummer
         */
        public void setAdministratienummer(final String administratienummer) {
            this.administratienummer = administratienummer;
        }

        /**
         * Gets burgerservicenummer.
         * @return the burgerservicenummer
         */
        public String getBurgerservicenummer() {
            return burgerservicenummer;
        }

        /**
         * Sets burgerservicenummer.
         * @param burgerservicenummer the burgerservicenummer
         */
        public void setBurgerservicenummer(final String burgerservicenummer) {
            this.burgerservicenummer = burgerservicenummer;
        }

        /**
         * Gets object sleutel.
         * @return the object sleutel
         */
        public String getObjectSleutel() {
            return objectSleutel;
        }

        /**
         * Sets object sleutel.
         * @param objectSleutel the object sleutel
         */
        public void setObjectSleutel(final String objectSleutel) {
            this.objectSleutel = objectSleutel;
        }

        /**
         * Gets gemeente code.
         * @return the gemeente code
         */
        public String getGemeenteCode() {
            return gemeenteCode;
        }

        /**
         * Sets gemeente code.
         * @param gemeenteCode the gemeente code
         */
        public void setGemeenteCode(final String gemeenteCode) {
            this.gemeenteCode = gemeenteCode;
        }

        /**
         * Gets afgekorte naam openbare ruimte.
         * @return the afgekorte naam openbare ruimte
         */
        public String getAfgekorteNaamOpenbareRuimte() {
            return afgekorteNaamOpenbareRuimte;
        }

        /**
         * Sets afgekorte naam openbare ruimte.
         * @param afgekorteNaamOpenbareRuimte the afgekorte naam openbare ruimte
         */
        public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
            this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        }

        /**
         * Gets huisnummer.
         * @return the huisnummer
         */
        public String getHuisnummer() {
            return huisnummer;
        }

        /**
         * Sets huisnummer.
         * @param huisnummer the huisnummer
         */
        public void setHuisnummer(final String huisnummer) {
            this.huisnummer = huisnummer;
        }

        /**
         * Gets huisletter.
         * @return the huisletter
         */
        public String getHuisletter() {
            return huisletter;
        }

        /**
         * Sets huisletter.
         * @param huisletter the huisletter
         */
        public void setHuisletter(final String huisletter) {
            this.huisletter = huisletter;
        }

        /**
         * Gets huisnummertoevoeging.
         * @return the huisnummertoevoeging
         */
        public String getHuisnummertoevoeging() {
            return huisnummertoevoeging;
        }

        /**
         * Sets huisnummertoevoeging.
         * @param huisnummertoevoeging the huisnummertoevoeging
         */
        public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
            this.huisnummertoevoeging = huisnummertoevoeging;
        }

        /**
         * Gets locatie ten opzichte van adres.
         * @return the locatie ten opzichte van adres
         */
        public String getLocatieTenOpzichteVanAdres() {
            return locatieTenOpzichteVanAdres;
        }

        /**
         * Sets locatie ten opzichte van adres.
         * @param locatieTenOpzichteVanAdres the locatie ten opzichte van adres
         */
        public void setLocatieTenOpzichteVanAdres(final String locatieTenOpzichteVanAdres) {
            this.locatieTenOpzichteVanAdres = locatieTenOpzichteVanAdres;
        }

        /**
         * Gets postcode.
         * @return the postcode
         */
        public String getPostcode() {
            return postcode;
        }

        /**
         * Sets postcode.
         * @param postcode the postcode
         */
        public void setPostcode(final String postcode) {
            this.postcode = postcode;
        }

        /**
         * Gets woonplaatsnaam.
         * @return the woonplaatsnaam
         */
        public String getWoonplaatsnaam() {
            return woonplaatsnaam;
        }

        /**
         * Sets woonplaatsnaam.
         * @param woonplaatsnaam the woonplaatsnaam
         */
        public void setWoonplaatsnaam(final String woonplaatsnaam) {
            this.woonplaatsnaam = woonplaatsnaam;
        }

        /**
         * Gets identificatiecode nummeraanduiding.
         * @return the identificatiecode nummeraanduiding
         */
        public String getIdentificatiecodeNummeraanduiding() {
            return identificatiecodeNummeraanduiding;
        }

        /**
         * Sets identificatiecode nummeraanduiding.
         * @param identificatiecodeNummeraanduiding the identificatiecode nummeraanduiding
         */
        public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
            this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        }

        public String getIdentificatiecodeAdresseerbaarObject() {
            return identificatiecodeAdresseerbaarObject;
        }

        public void setIdentificatiecodeAdresseerbaarObject(String identificatiecodeAdresseerbaarObject) {
            this.identificatiecodeAdresseerbaarObject = identificatiecodeAdresseerbaarObject;
        }
    }

}
