/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DienstbundelGroepAttribuut repository.
 */
@Master
@Transactional(propagation = Propagation.MANDATORY)
public interface DienstbundelGroepAttribuutRepository extends ReadWriteRepository<DienstbundelGroepAttribuut, Integer> {

    /**
     * Zoek alle gekoppelde attributen voor een dienstbundelgroep.
     *
     * @param dienstbundelGroep
     *            De dienstbundelgroep waarop wordt gezocht
     * @return Lijst van gekoppelde attributen.
     */
    List<DienstbundelGroepAttribuut> findByDienstbundelGroep(DienstbundelGroep dienstbundelGroep);

    /**
     * Zoek alle gekoppelde attributen voor een dienstbundelgroep.
     *
     * @param dienstbundelGroep
     *            De dienstbundelgroep waarop wordt gezocht
     * @param elementId
     *            Het id van het attribuut
     * @return Lijst van gekoppelde attributen.
     */
    DienstbundelGroepAttribuut findByDienstbundelGroepAndElementId(DienstbundelGroep dienstbundelGroep, Integer elementId);

    /**
     * Zoek alle gekoppelde attributen voor een dienstbundelgroep.
     *
     * @param dienstbundelGroep
     *            De dienstbundelgroep waarop wordt gezocht
     * @param pageable
     *            De pageable waar het resultaat in komt
     * @return Lijst van gekoppelde attributen.
     */
    Page<DienstbundelGroepAttribuut> findByDienstbundelGroep(DienstbundelGroep dienstbundelGroep, Pageable pageable);

}
