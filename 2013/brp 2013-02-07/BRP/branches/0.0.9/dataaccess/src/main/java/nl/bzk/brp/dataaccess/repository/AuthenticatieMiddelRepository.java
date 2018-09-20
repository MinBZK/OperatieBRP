/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.math.BigInteger;
import java.util.List;

import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.operationeel.aut.PersistentAuthenticatieMiddel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Spring data JPA repository voor {@link nl.bzk.brp.model.operationeel.aut.PersistentAuthenticatieMiddel}.
 */
public interface AuthenticatieMiddelRepository extends JpaRepository<PersistentAuthenticatieMiddel, Integer> {

    /**
     * Zoekt authenticatiemiddelen op voor een partij met het daarbij behorende certificaat.
     *
     * @param partij De partij waarvoor een authenticatie middel wordt gezocht.
     * @param subject Subject attribuut van het x509 certificaat.
     * @param serial Serial attribuut van het x509 certificaat.
     * @param signature Signature attribuut van het x509 certificaat.
     * @return Een authenticatiemiddel indien aanwezig.
     */
    @Query("SELECT AuthMiddel FROM PersistentAuthenticatieMiddel AuthMiddel "
         + "WHERE AuthMiddel.partij = ?1 "
         + "AND AuthMiddel.ondertekeningsCertificaat.subject = ?2 "
         + "AND AuthMiddel.ondertekeningsCertificaat.serial = ?3 "
         + "AND AuthMiddel.ondertekeningsCertificaat.signature = ?4 "
         + "AND AuthMiddel.statushistorie = 'A'")
    List<PersistentAuthenticatieMiddel> zoekAuthMiddelenVoorPartijMetCertificaat(final Partij partij,
            final String subject, final BigInteger serial, final String signature);

}
