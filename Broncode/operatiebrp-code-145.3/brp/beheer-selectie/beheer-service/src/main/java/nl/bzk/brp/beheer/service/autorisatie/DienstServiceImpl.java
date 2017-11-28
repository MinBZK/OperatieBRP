/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.autorisatie;

import javax.inject.Inject;
import nl.bzk.brp.beheer.service.dal.DienstRepository;
import org.springframework.stereotype.Service;

/**
 */
@Service
class DienstServiceImpl implements DienstService {

    private final DienstRepository dienstRepository;

    @Inject
    DienstServiceImpl(DienstRepository dienstRepository) {
        this.dienstRepository = dienstRepository;
    }

    @Override
    public DienstDTO getDienst(Integer id) {
        return new DienstDTO(dienstRepository.findDienstById(id));
    }
}
