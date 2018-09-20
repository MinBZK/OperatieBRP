/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.security.cert.X509Certificate;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.dataaccess.repository.AuthenticatiemiddelRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

/** Implementatie van de AuthenticatieService. */
@Service
public class AuthenticatieServiceImpl implements AuthenticatieService {

    @Inject
    private AuthenticatiemiddelRepository authenticatiemiddelRepository;

    /** {@inheritDoc} */
    @Override
    public List<Authenticatiemiddel> haalAuthenticatieMiddelenOp(final Short partijId,
        final X509Certificate certificaat)
    {
        return authenticatiemiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(partijId,
            new Certificaatsubject(certificaat.getSubjectDN().getName()),
            new Certificaatserial(certificaat.getSerialNumber().toString()),
            new PubliekeSleutel(Hex.encodeHexString(certificaat.getSignature())));
    }

}
