/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DienstbundelGroep repository.
 */
@Master
@Transactional(propagation = Propagation.MANDATORY)
public interface DienstbundelGroepRepository extends ReadWriteRepository<DienstbundelGroep, Integer> {

    /**
     * Zoekt de dienstbundelgroepen op basis van de dienstbundel.
     * @param dienstbundel De dienstbundel waarop de groepen worden gezocht
     * @param pageable De pageable waarin de resultaten komen
     * @return De gevonden dienstbundelgroepen
     */
    Page<DienstbundelGroep> findByDienstbundel(Dienstbundel dienstbundel, Pageable pageable);

    /**
     * Verwijder een dienstbundel groep op basis van het id.
     * @param id Het id van de te verwijderen dienstbundel groep
     * @return Aantal verwijderde records
     */
    Integer removeById(Integer id);
}
