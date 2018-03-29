/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

import com.google.common.collect.Sets;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import org.springframework.util.Assert;

/**
 * API interface naar bevraging.
 */
public interface BevragingApiService {

    /**
     * Assert dat de keys geldig zijn.
     *
     * @param keyset een set met keys.
     */
    static void assertGeenOnbekendeKeys(final Set<String> keyset) {
        Assert.isTrue(keyset.isEmpty(), "Ongeldige keys: " + keyset);
    }

    /**
     * Assert dat de dienst gevuld is.
     *
     * @param dienstId een dienstId
     */
    static void assertDienstGevuld(final String dienstId) {
        Assert.notNull(dienstId, "DienstIdentificatie moet gevuld zijn");
    }

    /**
     * @return de API interface voor geef details persoon
     */
    GeefDetailsPersoonApiService getGeefDetailsPersoonApiService();

    /**
     * @return de API interface voor de zoek persoon
     */
    ZoekPersoonApiService getZoekPersoonApiService();

    /**
     * @return de API interace voor geef medebewoners
     */
    GeefMedebewonersApiService getGeefMedebewonersApiService();

    /**
     * De verzoekinterface voor de dienst Zoek Persoon.
     */
    interface ZoekPersoonApiService {

        /**
         * Alle mogelijke keys voor zoek persoon.
         */
        Set<String> KEYS_ZOEKEN = Sets.union(VerzoekService.GENERIEKE_KEYS, Sets.newHashSet(
            BevragingKeys.DIENST_ID, BevragingKeys.ROL_NAAM, BevragingKeys.ZOEK_PERSOON_PEILMOMENT_MATERIEEL,
            BevragingKeys.SCOPING_ELEMENTEN, BevragingKeys.ZOEK_PERSOON_ZOEKBEREIK));

        /**
         * Voert het technische API verzoek 'Zoek Persoon' uit.
         *
         * @param map              tabel met params.
         * @param zoekCriteriaList lijst met zoekcriteria
         */
        void verzoekZoekPersoon(Map<String, String> map, LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList);

        /**
         * Voert het technische API verzoek 'Zoek Persoon Op Adres' uit.
         *
         * @param map              tabel met params.
         * @param zoekCriteriaList lijst met zoekcriteria
         */
        void verzoekZoekPersoonOpAdres(Map<String, String> map, LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList);


        /**
         * Voert het XML verzoek 'Zoek Persoon' uit.
         *
         * @param xmlVerzoek file met het XML verzoek.
         */
        void zoekPersoonMetXml(XmlVerzoek xmlVerzoek);

        /**
         * Voert het XML verzoek 'Zoek Persoon Op Adres' uit.
         *
         * @param xmlVerzoek file met het XML verzoek.
         */
        void zoekPersoonOpAdresMetXml(XmlVerzoek xmlVerzoek);
    }

    /**
     * De verzoekinterface voor de dienst GeefDetailsPersoon.
     */
    interface GeefDetailsPersoonApiService {

        /**
         * Alle mogelijke keys voor geef details persoon.
         */
        Set<String> KEYS_GEEF_DETAILS = Sets.union(VerzoekService.GENERIEKE_KEYS, Sets.newHashSet(
            BevragingKeys.BSN, BevragingKeys.OBJECT_SLEUTEL, BevragingKeys.DIENST_ID, BevragingKeys.ROL_NAAM, BevragingKeys.HISTORIEVORM,
            BevragingKeys.GEEF_DETAILS_PEILMOMENT_MATERIEEL_RESULTAAT,
            BevragingKeys.GEEF_DETAILS_PEILMOMENT_FORMEEL_RESULTAAT, BevragingKeys.SCOPING_ELEMENTEN, BevragingKeys.ANR, BevragingKeys.VERANTWOORDING));

        /**
         * Voert het technisch API verzoek 'Geef Details Persoon' uit.
         *
         * @param map tabel met params.
         */
        void verzoek(Map<String, String> map);


        /**
         * Voert het XML verzoek 'Geef Details Persoon' uit.
         *
         * @param xmlVerzoek file met het XML verzoek.
         */
        void verzoekMetXml(XmlVerzoek xmlVerzoek);

    }

    /**
     * De verzoekinterface voor de dienst Geef Medebewoners.
     */
    interface GeefMedebewonersApiService {

        /**
         * Key voor burgerservicenummer.
         */
        String BURGERSERVICENUMMER                = "burgerservicenummer";
        /**
         * Key voor administratienummer.
         */
        String ADMINISTRATIENUMMER                = "administratienummer";
        /**
         * Key voor objectSleutel.
         */
        String OBJECT_SLEUTEL                     = "objectSleutel";
        /**
         * Key voor gemeenteCode.
         */
        String GEMEENTE_CODE                      = "gemeenteCode";
        /**
         * Key voor afgekorteNaamOpenbareRuimte.
         */
        String AFGEKORTE_NAAM_OPENBARE_RUIMTE     = "afgekorteNaamOpenbareRuimte";
        /**
         * Key voor huisnummer.
         */
        String HUISNUMMER                         = "huisnummer";
        /**
         * Key voor huisletter.
         */
        String HUISLETTER                         = "huisletter";
        /**
         * Key voor huisnummertoevoeging.
         */
        String HUISNUMMERTOEVOEGING               = "huisnummertoevoeging";
        /**
         * Key voor locatieTenOpzichteVanAdres.
         */
        String LOCATIE_TEN_OPZICHTE_VAN_ADRES     = "locatieTenOpzichteVanAdres";
        /**
         * Key voor postcode.
         */
        String POSTCODE                           = "postcode";
        /**
         * Key voor woonplaatsnaam.
         */
        String WOONPLAATSNAAM                     = "woonplaatsnaam";
        /**
         * Key voor identificatiecodeNummeraanduiding (BAG).
         */
        String IDENTIFICATIECODE_NUMMERAANDUIDING = "identificatiecodeNummeraanduiding";
        /**
         * Key voor identificatiecodeAdresseerbaarObject (Adres).
         */
        String IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT = "identificatiecodeAdresseerbaarObject";

        /**
         * Alle mogelijke keys voor geef medebewoners.
         */
        Set<String> KEYS_GEEF_MEDEBEWONERS = Sets.union(VerzoekService.GENERIEKE_KEYS, Sets.newHashSet(
            BevragingKeys.DIENST_ID, BevragingKeys.ROL_NAAM,
            BevragingKeys.ZOEK_PERSOON_PEILMOMENT_MATERIEEL,
            BURGERSERVICENUMMER,
            ADMINISTRATIENUMMER,
            OBJECT_SLEUTEL,
            GEMEENTE_CODE,
            AFGEKORTE_NAAM_OPENBARE_RUIMTE,
            HUISNUMMER,
            HUISLETTER,
            HUISNUMMERTOEVOEGING,
            LOCATIE_TEN_OPZICHTE_VAN_ADRES,
            POSTCODE,
            WOONPLAATSNAAM,
            IDENTIFICATIECODE_NUMMERAANDUIDING,
            IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT)
        );

        /**
         * Voert het technisch API verzoek 'Geef Medebewoners' uit.
         *
         * @param map tabel met params.
         */
        void verzoek(Map<String, String> map);


        /**
         * Voert het XML verzoek 'Geef Medebewoners' uit.
         *
         * @param xmlVerzoek file met het XML verzoek.
         */
        void verzoekMetXml(XmlVerzoek xmlVerzoek);
    }
}
