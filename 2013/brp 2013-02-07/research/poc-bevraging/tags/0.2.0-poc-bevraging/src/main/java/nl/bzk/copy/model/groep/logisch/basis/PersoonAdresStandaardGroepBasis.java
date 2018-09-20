/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.logisch.basis;

import nl.bzk.copy.model.attribuuttype.*;
import nl.bzk.copy.model.basis.Groep;
import nl.bzk.copy.model.objecttype.operationeel.statisch.*;


/**
 * .
 */
public interface PersoonAdresStandaardGroepBasis extends Groep {

    /**
     * Geeft de functie terug van het adres terug.
     *
     * @return soort adres
     */
    FunctieAdres getSoort();

    /**
     * Geeft de datum van aanvang adreshouding terug.
     *
     * @return datum
     */
    Datum getDatumAanvangAdreshouding();

    /**
     * Geeft de reden van wijziging adres terug.
     *
     * @return reden wijziging adres
     */
    RedenWijzigingAdres getRedenWijziging();

    /**
     * Geeft de identiteit terug van aangever adreshouding.
     *
     * @return aangever adreshouding identiteit
     */
    AangeverAdreshoudingIdentiteit getAangeverAdresHouding();

    /**
     * Geeft het adresseerbaar object terug.
     *
     * @return adresseerbaar object
     */
    Adresseerbaarobject getAdresseerbaarObject();

    /**
     * Geeft de identificatiecode nummeraanduiding terug van het adres.
     *
     * @return identificatiecode nummeraanduiding
     */
    IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding();

    /**
     * Geeft de gemeente terug.
     *
     * @return partij
     */
    Partij getGemeente();

    /**
     * Geeft het adres terug.
     *
     * @return naam openbare ruimte
     */
    NaamOpenbareRuimte getNaamOpenbareRuimte();

    /**
     * Geeft de postcode terug.
     *
     * @return postcode.
     */
    Postcode getPostcode();

    /**
     * Geeft de afgekorte naam terug van  het adres.
     *
     * @return afgekorte naam openbare ruimte
     */
    AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte();

    /**
     * Geeft de gemeentedeel terug.
     *
     * @return gemeentedeel
     */
    Gemeentedeel getGemeentedeel();

    /**
     * Geeft de huisnummer terug.
     *
     * @return huisnummer
     */
    Huisnummer getHuisnummer();

    /**
     * Geeft de huisletter terug.
     *
     * @return huisletter
     */
    Huisletter getHuisletter();

    /**
     * Geeft de huisnummertoevoeging terug.
     *
     * @return huisnummertoevoegin
     */
    Huisnummertoevoeging getHuisnummertoevoeging();

    /**
     * Geeft de woonplaats terug.
     *
     * @return plaats
     */
    Plaats getWoonplaats();

    /**
     * Geeft de locatie tov adres terug.
     *
     * @return LocatieTovAdres
     */
    LocatieTovAdres getLocatietovAdres();

    /**
     * Geeft de locatie omschrijving terug.
     *
     * @return locatie omschrijving
     */
    LocatieOmschrijving getLocatieOmschrijving();

    /**
     * Geeft de buitenlandsadresregel terug.
     *
     * @return adresregel
     */
    Adresregel getBuitenlandsAdresRegel1();

    /**
     * Geeft de buitenlandsadresregel terug.
     *
     * @return adresregel
     */
    Adresregel getBuitenlandsAdresRegel2();

    /**
     * Geeft de buitenlandsadresregel terug.
     *
     * @return adresregel
     */
    Adresregel getBuitenlandsAdresRegel3();

    /**
     * Geeft de buitenlandsadresregel terug.
     *
     * @return adresregel
     */
    Adresregel getBuitenlandsAdresRegel4();

    /**
     * Geeft de buitenlandsadresregel terug.
     *
     * @return adresregel
     */
    Adresregel getBuitenlandsAdresRegel5();

    /**
     * Geeft de buitenlandsadresregel terug.
     *
     * @return adresregel
     */
    Adresregel getBuitenlandsAdresRegel6();

    /**
     * Geeft het land terug.
     *
     * @return land
     */
    Land getLand();

    /**
     * Geeft de vertrek datum uit Nederland terug.
     *
     * @return datum
     */
    Datum getDatumVertrekUitNederland();

    // technisch
    // AttribuutType<Integer> getStatus();
}
