/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.SoortBijhouding;
import nl.bzk.brp.domein.autaut.persistent.PersistentBijhoudingsautorisatie;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link BijhoudingsAutorisatie} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface BijhoudingsAutorisatieRepository extends JpaRepository<PersistentBijhoudingsautorisatie, Integer> {

    /**
     * Selecteer alle bijhoudingsautorisaties die van toepassing zijn op bijhoudendePartij of de soort partij
     * waartoe bijhoudendepartij toebehoort.
     *
     * @param soortBijhouding De soort bijhouding. {@link SoortBijhouding}
     * @param bijhoudendePartij De partij die de bijhouding doet.
     * @param soortPartij Soort partij dat de bijhouding doet.
     * @param perDatum Datum per wanneer het besluit ingegaan is. Meestal huidige datum.
     * @return Lijst met van toepassing zijnde bijhoudingsautorisaties.
     */
    @Query("SELECT bha FROM PersistentBijhoudingsautorisatie bha JOIN bha.bijhoudingsautorisatiebesluit ab"
        + " WHERE bha.toestand = nl.bzk.brp.domein.autaut.Toestand.DEFINITIEF"
        + " AND bha.bijhoudingsautorisatieStatusHis = 'A'" + " AND bha.soortBijhouding = ?1"
        + " AND (bha.geautoriseerdePartij = ?2 OR bha.geautoriseerdeSoortPartij = ?3)"
        + " AND ab.toestand = nl.bzk.brp.domein.autaut.Toestand.DEFINITIEF"
        + " AND ab.autorisatiebesluitStatusHis = 'A'" + " AND ab.indicatieIngetrokken = false"
        + " AND ab.datumBesluit <= ?4"
        + " AND ab.soort = nl.bzk.brp.domein.autaut.SoortAutorisatiebesluit.BIJHOUDINGSAUTORISATIE")
    List<Bijhoudingsautorisatie> zoekBijhoudingsAutorisaties(final SoortBijhouding soortBijhouding,
        final Partij bijhoudendePartij, final SoortPartij soortPartij, final Integer perDatum);
}
