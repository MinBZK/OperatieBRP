/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.kern;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;


/**
 * Het adres zoals gedefinieerd in artikel 1.1. van de Wet BRP.
 */
public interface PersoonAdres {

    /**
     * @return De Nederlandse gemeente waarin het adresseerbaar object zich bevindt
     */
    Partij getGemeente();

    /**
     * @return De numerieke aanduiding zoals deze door het gemeentebestuur aan het object is toegekend dan wel een door
     *         of namens het bevoegde gemeentelijke orgaan ten aanzien van een adresseerbaar object toegekende nummering
     */
    Integer getHuisNummer();

    /**
     * @return Een door het bevoegde gemeentelijke orgaan toegekende benaming toegekend aan een als openbare ruimte
     *         aangewezen buitenruimte die binnen één woonplaats is gelegen
     */
    String getNaamOpenbareRuimte();

    /**
     * @return De code behorende bij een bepaalde combinatie van een naam van een woonplaats, naam van een openbare
     *         ruimte en een huisnummer
     */
    String getPostcode();

    /**
     * @return Een door de gemeenteraad als zodanig aangewezen gedeelte van het gemeentelijk grondgebied, waarbinnen
     *         het adresseerbare object is gelegen.
     */
    Plaats getWoonplaats();

    /**
     * @return De op basis van de NEN5822:2002 standaard of volgens de BOCO norm ingekorte naam van de Openbare ruimte
     */
    String getAfgekorteNaamOpenbareRuimte();

    /**
     * @return Aanduiding die een niet unieke straatnaam in een gemeente uniek maakt
     */
    String getGemeenteDeel();

    /**
     * @return Een door het bevoegde gemeentelijke orgaan ten aanzien van een adresseerbaar object toegekende toevoeging
     *         aan een huisnummer in de vorm van een alfanumeriek teken
     */
    String getHuisletter();

    /**
     * @return Een door het bevoegde gemeentelijke orgaan ten aanzien van een adresseerbaar object toegekende nadere
     *         toevoeging aan een huisnummer of een combinatie van huisnummer en huisletter
     */
    String getHuisnummertoevoeging();

    /**
     * @return Geeft aan waar het adres zich bevindt ten opzichte van het geregistreerde adres.
     */
    String getLocatieTOVAdres();

    /**
     * @return Aanduiding van het soort adres
     */
    FunctieAdres getSoort();

}
