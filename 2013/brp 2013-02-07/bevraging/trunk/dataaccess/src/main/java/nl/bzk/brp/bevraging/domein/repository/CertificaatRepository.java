/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.math.BigInteger;

import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.autaut.Certificaat;
import nl.bzk.brp.domein.autaut.persistent.PersistentCertificaat;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link Certificaat} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface CertificaatRepository extends JpaRepository<PersistentCertificaat, Long> {

    /**
     * Haalt een certificaat op op basis van de serial, subject en signature.
     *
     * @param serial de serial van het certificaat.
     * @param subject het subject van het certificaat.
     * @param signature de signature van het certificaat.
     * @return het certificaat met opgegegeven waardes.
     */
    Certificaat findBySerialAndSubjectAndSignature(BigInteger serial, String subject, String signature);

    /**
     * Zoekt een partij op basis van het certificaat, welke wordt geindentificeerd met de opgegeven
     * serial, subject en signature.
     *
     * @param serial de serial van het certificaat.
     * @param subject het subject van het certificaat.
     * @param signature de signature van het certificaat.
     * @return de partij behorende bij het certificaat.
     */
    @Query("SELECT am.partij FROM PersistentAuthenticatiemiddel am" + " WHERE am.certificaatTbvOndertekening ="
        + " (SELECT c FROM PersistentCertificaat c WHERE c.subject=?2 AND c.signature=?3 AND c.serial=?1)")
    Partij zoekPartijOpBasisVanCertificaat(Long serial, String subject, String signature);

    /**
     * Zoekt het authenticatiemiddel op basis van het certificaat, welke wordt geindentificeerd met de opgegeven
     * serial, subject en signature. Daarnaast mag het alleen een authenticatiemiddel retourneren voor de gezochte
     * rol. Merk op dat indien de rol op het authenticatiemiddel leeg is, dit betekent dat dit voor alle rollen
     * geldt en dus dan geldig is voor elke gezochte rol.
     *
     * @param serial de serial van het certificaat.
     * @param subject het subject van het certificaat.
     * @param signature de signature van het certificaat.
     * @param gezochteRol de rol waarvoor het authenticatiemiddel moet gelden.
     * @return het authenticatiemiddel behorende bij het certificaat.
     */
    @Query("SELECT am FROM PersistentAuthenticatiemiddel am LEFT JOIN FETCH am.partij"
        + " WHERE am.certificaatTbvOndertekening ="
        + " (SELECT c FROM PersistentCertificaat c WHERE c.subject=?2 AND c.signature=?3 AND c.serial=?1)"
        + " AND ((am.rol IS NULL) OR (am.rol = ?4))")
    Authenticatiemiddel zoekAuthenticatieMiddelOpBasisvanCertificaat(Long serial, String subject, String signature,
        Rol gezochteRol);

}
