/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;

/**
 * CRUD-functionaliteit voor Persoon-entity.
 */
public interface PersoonRepository {
    /**
     * Zoek een persoon op o.b.v. het administratienummer en soort persoon.
     *
     * @param administratienummer
     *            Het administratienummer, mag niet null zijn
     * @param soortPersoon
     *            het soort persoons dat gezocht wordt, mag niet null zijn
     * @param indicatieFoutiefOpgeschortUitsluiten
     *            indicator of we foutief opgeschorte persoonlijsten met opschort reden 'F' en 'W' meenemen in het
     *            resultaat of negeren
     * @return De persoon of personen met het meegegeven administratienummer en soortpersoon, of null als deze persoon
     *         niet is gevonden.
     * @throws NullPointerException
     *             als administratienummer of soortPersoon null is
     */
    List<Persoon> findByAdministratienummer(long administratienummer, SoortPersoon soortPersoon, boolean indicatieFoutiefOpgeschortUitsluiten);

    /**
     * Zoek een persoon op o.b.v. het administratienummer en soort persoon.
     *
     * @param technischeSleutel
     *            de technische sleutel, mag niet null zijn
     * @param soortPersoon
     *            het soort persoons dat gezocht wordt, mag niet null zijn
     * @return De persoon of personen met de meegegeven technische sleutel en soortpersoon, of null als deze persoon
     *         niet is gevonden.
     * @throws NullPointerException
     *             als administratienummer of soortPersoon null is
     */
    List<Persoon> findByTechnischeSleutel(Long technischeSleutel, SoortPersoon soortPersoon);

    /**
     * Zoek een persoon op o.b.v. het historische administratienummer (datum einde geldigheid is wel ingevuld en datum
     * vervallen niet) en soort persoon.
     *
     * @param administratienummer
     *            Het administratienummer, mag niet null zijn
     * @param soortPersoon
     *            het soort persoons dat gezocht wordt, mag niet null zijn
     * @return De persoon of personen met het meegegeven administratienummer en soortpersoon, of null als deze persoon
     *         niet is gevonden.
     * @throws NullPointerException
     *             als administratienummer of soortPersoon null is
     */
    List<Persoon> findByAdministratienummerHistorisch(Long administratienummer, SoortPersoon soortPersoon);

    /**
     * Zoek de onderzoeken waarbij de gegeven persoon direct betrokken is.
     *
     * @param persoon
     *            De persoon die direct bij de Onderzoeken betrokken moet zijn.
     * @return De onderzoeken waar de persoon direct bij betrokken is.
     */
    List<Onderzoek> findOnderzoekenVoorPersoon(Persoon persoon);

    /**
     * Detached een persoon van de persitence context, hierdoor worden wijzigen op deze persoon (inclusief delete) niet
     * naar de database geschreven.
     *
     * @param persoon
     *            De persoon die gedetached moet worden
     */
    void detach(Persoon persoon);

    /**
     * Verwijderd een persoon inclusief persoongroepen en historie uit de database.
     *
     * @param persoon
     *            de persoon die verwijderd moet worden
     */
    void remove(Persoon persoon);

    /**
     * Slaat de meegegeven persoon op de database.
     *
     * @param persoon
     *            de persoon entiteit die moet worden opgeslagen in de database
     */
    void save(Persoon persoon);

    /**
     * Zoekt op basis van de meegegeven gegevens een persoon op in de database.
     *
     * @param persoon
     *            de persoon entiteit die de zoekgegevens bevat
     * @return de lijst van persoon entiteit die gevonden zijn in de database
     */
    List<Persoon> zoekPersoon(Persoon persoon);

    /**
     * Zoekt op basis van de meegegeven actuele gegevens personen in de database.
     *
     * @param administratienummer
     *            a-nummer
     * @param burgerservicenummer
     *            bsn
     * @param geslachtsnaamstam
     *            geslachtsnaam
     * @param postcode
     *            postcode
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpActueleGegevens(Long administratienummer, Integer burgerservicenummer, String geslachtsnaamstam, String postcode);

    /**
     * Zoekt op basis van de meegegeven historische gegevens personen in de database.
     *
     * @param administratienummer
     *            a-nummer
     * @param burgerservicenummer
     *            bsn
     * @param geslachtsnaamstam
     *            geslachtsnaam
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpHistorischeGegevens(Long administratienummer, Integer burgerservicenummer, String geslachtsnaamstam);

    /**
     * Verwijder de 'blob' cache van de persoon uit de database.
     *
     * @param persoon
     *            de persoon waarvan de blob verwijderd moet worden
     */
    void removeCache(Persoon persoon);
}
