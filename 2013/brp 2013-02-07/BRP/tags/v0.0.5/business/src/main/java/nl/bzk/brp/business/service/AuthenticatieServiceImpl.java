/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.security.cert.X509Certificate;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.dataaccess.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.dataaccess.repository.PartijRepository;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.operationeel.aut.PersistentAuthenticatieMiddel;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

/** Implementatie van de AuthenticatieService. */
@Service
public class AuthenticatieServiceImpl implements AuthenticatieService {

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private AuthenticatieMiddelRepository authenticatieMiddelRepository;

    /** {@inheritDoc} */
    @Override
    public List<PersistentAuthenticatieMiddel> haalAuthenticatieMiddelenOp(final Integer partijId,
        final X509Certificate certificaat)
    {
        final Partij partij = partijRepository.findOne(partijId);

        return authenticatieMiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(partij,
            certificaat.getSubjectDN().getName(), certificaat.getSerialNumber(),
            Hex.encodeHexString(certificaat.getSignature()));
    }

}
