/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;
import nl.bzk.brp.domein.brm.persistent.PersistentRegelimplementatiesituatie;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.Verantwoordelijke;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link RegelGedrag} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface RegelGedragRepository extends JpaRepository<PersistentRegelimplementatiesituatie, Integer> {

    /**
     * Zoekt actief bedrijfsregel gedrag dat voldoen aan de opgegeven criteria.
     * Een gedrag waarbij een criterium niet ingevuld is voldoet ook aan dat criterium.
     * Dus alleen gedrag waarbij een criterium wel is ingevuld maar met een andere waarde dan in het filter worden NIET
     * teruggegeven. Dit geldt ook als een filter criterium leeg is. In dat geval worden er dus alleen gedrag
     * teruggegeven waarbij dat criterium ook leeg is.
     *
     * @param soortBericht de berichtsoort waarop gefilterd moet worden
     * @param verantwoordelijke de verantwoordelijke waarop gefilterd moet worden, of null indien niet bekend
     * @param isOpschorting opschortingsvlag waarop gefilterd moet worden, of null indien niet bekend
     * @param redenOpschorting reden van de opschorting waarop gefilterd moet worden, of null indien niet bekend
     * @return actief bedrijfsregel gedrag dat voldoet aan de opgegeven criteria.
     */
    @Query("SELECT gedrag FROM PersistentRegelimplementatiesituatie gedrag JOIN gedrag.regelimplementatie impl WHERE gedrag.indicatieActief = true"
        + " AND impl.soortBericht = ?1"
        + " AND impl.regel.soort = nl.bzk.brp.domein.brm.SoortRegel.BEDRIJFSREGEL"
        + " AND (gedrag.bijhoudingsverantwoordelijkheid = ?2 OR gedrag.bijhoudingsverantwoordelijkheid = null)"
        + " AND (gedrag.indicatieOpgeschort = ?3 OR gedrag.indicatieOpgeschort = null)"
        + " AND (gedrag.redenOpschorting = ?4 OR gedrag.redenOpschorting = null)")
            List<Regelimplementatiesituatie> zoekActiefBedrijfsregelGedrag(SoortBericht soortBericht,
                    Verantwoordelijke verantwoordelijke, Boolean isOpschorting, RedenOpschorting redenOpschorting);

}
