/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository;

import java.util.List;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.SoortBijhouding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de {@link BijhoudingsAutorisatie} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface BijhoudingsAutorisatieRepository extends JpaRepository<BijhoudingsAutorisatie, Long> {

    /**
     * Selecteer alle bijhoudingsautorisaties die van toepassing zijn op bijhoudendePartij of de soort partij
     * waartoe bijhoudendepartij toebehoort.
     *
     * @param soortBijhouding De soort bijhouding. {@link SoortBijhouding}
     * @param bijhoudendePartij De partij die de bijhouding doet.
     * @param soortPartij Soort partij dat de bijhouding doet.
     * @return Lijst met van toepassing zijnde bijhoudingsautorisaties.
     */
    @Query("SELECT bha FROM BijhoudingsAutorisatie bha JOIN bha.autorisatieBesluit ab"
           + " WHERE bha.toestand = nl.bzk.brp.bevraging.domein.aut.Toestand.DEFINITIEF"
           + " AND bha.statusHistorie = nl.bzk.brp.bevraging.domein.StatusHistorie.A"
           + " AND bha.soortBijhouding = ?1"
           + " AND (bha.geautoriseerdePartij = ?2 OR bha.geautoriseerdeSoortPartij = ?3)"
           + " AND ab.toestand = nl.bzk.brp.bevraging.domein.aut.Toestand.DEFINITIEF"
           + " AND ab.autorisatieBesluitStatusHistorie = nl.bzk.brp.bevraging.domein.StatusHistorie.A"
           + " AND ab.ingetrokken = false"
           + " AND ab.soort = nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit.BIJHOUDINGSAUTORISATIE")
    List<BijhoudingsAutorisatie> zoekBijhoudingsAutorisaties(final SoortBijhouding soortBijhouding,
                                                             final Partij bijhoudendePartij,
                                                             final SoortPartij soortPartij);
}
