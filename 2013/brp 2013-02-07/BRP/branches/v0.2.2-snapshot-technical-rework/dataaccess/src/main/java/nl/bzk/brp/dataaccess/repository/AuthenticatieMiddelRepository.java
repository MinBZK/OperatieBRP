/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.attribuuttype.Certificaatserial;
import nl.bzk.brp.model.attribuuttype.PubliekeSleutel;
import nl.bzk.brp.model.attribuuttype.Certificaatsubject;
import nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/** Spring data JPA repository voor {@link nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel}. */
public interface AuthenticatieMiddelRepository extends JpaRepository<AuthenticatieMiddelModel, Integer> {

    /**
     * Zoekt authenticatiemiddelen op voor een partij met het daarbij behorende certificaat.
     *
     * @param partijId De id van de partij waarvoor een authenticatie middel wordt gezocht.
     * @param subject Subject attribuut van het x509 certificaat.
     * @param serial Serial attribuut van het x509 certificaat.
     * @param signature Signature attribuut van het x509 certificaat.
     * @return Een authenticatiemiddel indien aanwezig.
     */
    @Query("SELECT AuthMiddel FROM AuthenticatieMiddelModel AuthMiddel WHERE AuthMiddel.partij.id = ?1 "
        + "AND AuthMiddel.ondertekeningsCertificaat.subject = ?2 "
        + "AND AuthMiddel.ondertekeningsCertificaat.serial = ?3 "
        + "AND AuthMiddel.ondertekeningsCertificaat.signature = ?4 "
        + "AND AuthMiddel.statushistorie = 'A'")
    List<AuthenticatieMiddelModel> zoekAuthMiddelenVoorPartijMetCertificaat(final Short partijId, final Certificaatsubject subject,
        final Certificaatserial serial, final PubliekeSleutel signature);

}
