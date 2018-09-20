/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.math.BigInteger;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.AutenticatieService;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.aut.Certificaat;
import nl.bzk.brp.bevraging.domein.repository.CertificaatRepository;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Implementatie van autenticatieService welke de partij vind in de database.
 * {@inheritDoc}
 */

@Service
public class AutenticatieServiceImpl implements AutenticatieService {

    @Inject
    private CertificaatRepository certificaatRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AutenticatieServiceImpl.class);

    /**
     * {@inheritDoc}
     */

    @Override
    public Partij zoekPartijMetOndertekeningCertificaat(final BigInteger serial, final byte[] signature,
                                                        final String subject)
    {
        try {
            return certificaatRepository.zoekPartijObBasisvanCertificaat(serial, subject, Certificaat
                    .signatureFromStringToByteArray(signature));
        } catch (DecoderException e) {
            LOG.error("Het zoeken van de partij heeft een technische out opgelevert");
            throw new IllegalArgumentException(e);
        }
    }

}
