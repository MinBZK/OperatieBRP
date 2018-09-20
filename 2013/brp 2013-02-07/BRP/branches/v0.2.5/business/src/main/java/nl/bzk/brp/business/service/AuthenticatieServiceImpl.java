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
import nl.bzk.brp.model.attribuuttype.Serial;
import nl.bzk.brp.model.attribuuttype.Signature;
import nl.bzk.brp.model.attribuuttype.Subject;
import nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

/** Implementatie van de AuthenticatieService. */
@Service
public class AuthenticatieServiceImpl implements AuthenticatieService {

    @Inject
    private AuthenticatieMiddelRepository authenticatieMiddelRepository;

    /** {@inheritDoc} */
    @Override
    public List<AuthenticatieMiddelModel> haalAuthenticatieMiddelenOp(final Short partijId,
        final X509Certificate certificaat)
    {
        return authenticatieMiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(partijId,
            new Subject(certificaat.getSubjectDN().getName()), new Serial(certificaat.getSerialNumber()),
            new Signature(Hex.encodeHexString(certificaat.getSignature())));
    }

}
