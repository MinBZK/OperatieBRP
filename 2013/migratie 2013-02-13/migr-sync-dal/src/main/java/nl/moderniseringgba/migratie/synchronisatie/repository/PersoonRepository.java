/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import java.math.BigDecimal;
import java.util.List;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;

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
     * @return De persoon of personen met het meegegeven administratienummer en soortpersoon, of null als deze persoon
     *         niet is gevonden.
     * @throws NullPointerException
     *             als administratienummer of soortPersoon null is
     */
    List<Persoon> findByAdministratienummer(BigDecimal administratienummer, SoortPersoon soortPersoon);

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
    List<Persoon> findByAdministratienummerHistorisch(BigDecimal administratienummer, SoortPersoon soortPersoon);

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
     * Verwijderd van een persoon de persoongroepen en historie uit de database maar NIET de persoon zelf.
     * 
     * @param persoon
     *            de persoon waarvan de groepen en historie verwijders moet worden.
     */
    void removeGroepenEnHistorie(Persoon persoon);

    /**
     * Slaat de meegegeven persoon op de database.
     * 
     * @param persoon
     *            de persoon entiteit die moet worden opgeslagen in de database
     * @return de persoon entiteit die opgeslagen is in de database
     */
    Persoon save(Persoon persoon);
}
