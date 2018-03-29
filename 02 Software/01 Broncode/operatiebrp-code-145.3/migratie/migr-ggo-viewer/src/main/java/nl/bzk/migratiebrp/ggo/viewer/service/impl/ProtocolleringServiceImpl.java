/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import javax.inject.Inject;
import nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity.Protocollering;
import nl.bzk.migratiebrp.ggo.viewer.repository.ProtocolleringRepository;
import nl.bzk.migratiebrp.ggo.viewer.service.ProtocolleringService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de ProtocolleringService.
 */
@Service
public final class ProtocolleringServiceImpl implements ProtocolleringService {

    @Inject
    private ProtocolleringRepository protocolleringRepository;

    @Transactional(value = "viewerTransactionManager", propagation = Propagation.REQUIRED)
    @Override
    public Protocollering persisteerProtocollering(final Protocollering protocollering) {
        return protocolleringRepository.save(protocollering);
    }
}
