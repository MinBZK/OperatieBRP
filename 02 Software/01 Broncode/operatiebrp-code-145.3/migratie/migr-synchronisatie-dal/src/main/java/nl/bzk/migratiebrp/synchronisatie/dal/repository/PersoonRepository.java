/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;

/**
 * CRUD-functionaliteit voor Persoon-entity.
 */
public interface PersoonRepository {
    /**
     * Zoek een persoon op o.b.v. het administratienummer en soort persoon.
     * @param administratienummer Het administratienummer, mag niet null zijn
     * @param soortPersoon het soort persoons dat gezocht wordt, mag niet null zijn
     * @param indicatieFoutiefOpgeschortUitsluiten indicator of we foutief opgeschorte persoonlijsten met opschort reden 'F' en 'W' meenemen in het resultaat of
     * negeren
     * @return De persoon of personen met het meegegeven administratienummer en soortpersoon, of null als deze persoon niet is gevonden.
     * @throws NullPointerException als administratienummer of soortPersoon null is
     */
    List<Persoon> findByAdministratienummer(String administratienummer, SoortPersoon soortPersoon, boolean indicatieFoutiefOpgeschortUitsluiten);

    /**
     * Zoek een persoon op o.b.v. het administratienummer en soort persoon.
     * @param burgerServiceNummer Het burgerServiceNummer, mag niet null zijn
     * @return De persoon of personen met het meegegeven burgerservicenummer, of null als deze persoon niet is gevonden.
     * @throws NullPointerException als burgerServiceNummer null is
     */
    List<Persoon> findByBurgerServiceNummer(String burgerServiceNummer);

    /**
     * Zoek een persoon op o.b.v. het administratienummer en soort persoon.
     * @param technischeSleutel de technische sleutel, mag niet null zijn
     * @param soortPersoon het soort persoons dat gezocht wordt, mag niet null zijn
     * @param indicatieFoutiefOpgeschortUitsluiten indicator of we foutief opgeschorte persoonlijsten met opschort reden 'F' en 'W' meenemen in het resultaat of
     * negeren
     * @return De persoon of personen met de meegegeven technische sleutel en soortpersoon, of null als deze persoon niet is gevonden.
     * @throws NullPointerException als administratienummer of soortPersoon null is
     */
    List<Persoon> findByTechnischeSleutel(Long technischeSleutel, SoortPersoon soortPersoon, boolean indicatieFoutiefOpgeschortUitsluiten);

    /**
     * Zoek een persoon op o.b.v. het historische administratienummer (datum einde geldigheid is wel ingevuld en datum
     * vervallen niet) en soort persoon.
     * @param administratienummer Het administratienummer, mag niet null zijn
     * @param soortPersoon het soort persoons dat gezocht wordt, mag niet null zijn
     * @return De persoon of personen met het meegegeven administratienummer en soortpersoon, of null als deze persoon niet is gevonden.
     * @throws NullPointerException als administratienummer of soortPersoon null is
     */
    List<Persoon> findByAdministratienummerHistorisch(String administratienummer, SoortPersoon soortPersoon);

    /**
     * Detached een persoon van de persitence context, hierdoor worden wijzigen op deze persoon (inclusief delete) niet
     * naar de database geschreven.
     * @param persoon De persoon die gedetached moet worden
     */
    void detach(Persoon persoon);

    /**
     * Verwijderd een persoon inclusief persoongroepen en historie uit de database.
     * @param persoon de persoon die verwijderd moet worden
     */
    void remove(Persoon persoon);

    /**
     * Slaat de meegegeven persoon op de database.
     * @param persoon de persoon entiteit die moet worden opgeslagen in de database
     */
    void save(Persoon persoon);

    /**
     * Zoekt op basis van de meegegeven gegevens een persoon op in de database.
     * @param persoon de persoon entiteit die de zoekgegevens bevat
     * @return de lijst van persoon entiteit die gevonden zijn in de database
     */
    List<Persoon> zoekPersoon(Persoon persoon);

    /**
     * Zoekt op basis van de meegegeven actuele gegevens personen in de database.
     * @param administratienummer a-nummer
     * @param burgerservicenummer bsn
     * @param geslachtsnaamstam geslachtsnaam
     * @param postcode postcode
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpActueleGegevens(String administratienummer, String burgerservicenummer, String geslachtsnaamstam, String postcode);

    /**
     * Zoekt op basis van de meegegeven historische gegevens personen in de database.
     * @param administratienummer a-nummer
     * @param burgerservicenummer bsn
     * @param geslachtsnaamstam geslachtsnaam
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpHistorischeGegevens(String administratienummer, String burgerservicenummer, String geslachtsnaamstam);

    /**
     * Zoek een persoon op o.b.v. het historisch burgerservicenummer en soort persoon; foutief opgeschort met opschort reden 'F' en 'W' uitgesloten.
     * @param burgerservicenummer Het burgerservicenummer, mag niet null zijn
     * @param soortPersoon het soort persoon dat gezocht wordt, mag niet null zijn
     * @return Lijst van gevonden personen
     */
    List<Persoon> findByBurgerservicenummerHistorisch(String burgerservicenummer, SoortPersoon soortPersoon);

    /**
     * Zoek een persoon op o.b.v. het volgende administratienummer en soort persoon; foutief opgeschort met opschort reden 'F' en 'W' uitgesloten.
     * @param administratienummer Het administratienummer, mag niet null zijn
     * @param soortPersoon het soort persoon dat gezocht wordt, mag niet null zijn
     * @return Lijst van gevonden personen
     */
    List<Persoon> findByVolgendeAnummer(String administratienummer, SoortPersoon soortPersoon);

    /**
     * Zoek een persoon op o.b.v. het historisch volgende administratienummer en soort persoon; foutief opgeschort met opschort reden 'F' en 'W' uitgesloten.
     * @param administratienummer Het administratienummer, mag niet null zijn
     * @param soortPersoon het soort persoon dat gezocht wordt, mag niet null zijn
     * @return Lijst van gevonden personen
     */
    List<Persoon> findByVolgendeAnummerHistorisch(String administratienummer, SoortPersoon soortPersoon);

    /**
     * Zoek een persoon op o.b.v. het vorige administratienummer en soort persoon; foutief opgeschort met opschort reden 'F' en 'W' uitgesloten.
     * @param administratienummer Het administratienummer, mag niet null zijn
     * @param soortPersoon het soort persoon dat gezocht wordt, mag niet null zijn
     * @return Lijst van gevonden personen
     */
    List<Persoon> findByVorigeAnummer(String administratienummer, SoortPersoon soortPersoon);

    /**
     * Zoek een persoon op o.b.v. het historisch vorige administratienummer en soort persoon; foutief opgeschort met opschort reden 'F' en 'W' uitgesloten.
     * @param administratienummer Het administratienummer, mag niet null zijn
     * @param soortPersoon het soort persoon dat gezocht wordt, mag niet null zijn
     * @return Lijst van gevonden personen
     */
    List<Persoon> findByVorigeAnummerHistorisch(String administratienummer, SoortPersoon soortPersoon);
}
