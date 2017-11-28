/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abonnement LO3 Filter Rubriek repository.
 */
@Master
@Transactional(propagation = Propagation.MANDATORY)
public interface DienstbundelLo3RubriekRepository extends ReadWriteRepository<DienstbundelLo3Rubriek, Integer> {

    /**
     * Zoekt de dienstbundel LO3 rubriek op basis van de dienstbundel en rubriek.
     *
     * @param dienstbundel
     *            De dienstbundel waarop wordt gezocht
     * @return De lijst met gevonden dienstbundel LO3 rubriek of null indien deze niet gevonden wordt
     */
    List<DienstbundelLo3Rubriek> findByDienstbundel(Dienstbundel dienstbundel);

    /**
     * Zoekt de dienstbundel LO3 rubriek op basis van de dienstbundel en rubriek.
     *
     * @param dienstbundel
     *            De dienstbundel waarop wordt gezocht
     * @param rubriek
     *            De LO3 conversie rubriek waarop wordt gezocht
     * @return De gevonden dienstbundel LO3 rubriek of null indien deze niet gevonden wordt
     */
    DienstbundelLo3Rubriek findByDienstbundelAndLo3Rubriek(Dienstbundel dienstbundel, Lo3Rubriek rubriek);

    /**
     * Verwijder dienstbundel lo3 rubrieken op basis van het id.
     *
     * @param id
     *            Het id van het te verwijderen dienstbundel LO3 rubriek
     * @return id van de verwijderde dienstbundel LO3 rubriek
     */
    Integer deleteById(Integer id);
}
