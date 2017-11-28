/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dienst repository.
 */
@Master
@Transactional(propagation = Propagation.MANDATORY)
public interface DienstRepository extends ReadWriteRepository<Dienst, Integer> {

}
