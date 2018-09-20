/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring data JPA repository voor {@link nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel}.
 */
public interface AuthenticatiemiddelRepository extends JpaRepository<Authenticatiemiddel, Integer> {

    /**
     * Zoekt authenticatiemiddelen op voor een partij met het daarbij behorende certificaat.
     *
     * @param partijId        De id van de partij waarvoor een authenticatie middel wordt gezocht.
     * @param subject         Certificaatsubject attribuut van het x509 certificaat.
     * @param serial          Certificaatserial attribuut van het x509 certificaat.
     * @param publiekeSleutel PubliekeSleutel attribuut van het x509 certificaat.
     * @return Een authenticatiemiddel indien aanwezig.
     */
    @Query("SELECT AuthMiddel FROM Authenticatiemiddel AuthMiddel WHERE AuthMiddel.partij.id = ?1 "
                   + "AND AuthMiddel.certificaatTbvOndertekening.subject = ?2 "
                   + "AND AuthMiddel.certificaatTbvOndertekening.serial = ?3 "
                   + "AND AuthMiddel.certificaatTbvOndertekening.signature = ?4 "
                   + "AND AuthMiddel.authenticatiemiddelStatusHis = 'A'")
    List<Authenticatiemiddel> zoekAuthMiddelenVoorPartijMetCertificaat(final Short partijId,
                                                                       final Certificaatsubject subject,
                                                                       final Certificaatserial serial,
                                                                       final PubliekeSleutel publiekeSleutel);
}
