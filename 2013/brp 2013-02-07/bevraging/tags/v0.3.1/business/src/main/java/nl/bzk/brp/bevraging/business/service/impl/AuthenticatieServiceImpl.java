/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.antwoord.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.service.AuthenticatieService;
import nl.bzk.brp.bevraging.business.service.exception.MeerdereAuthenticatieMiddelenExceptie;
import nl.bzk.brp.bevraging.domein.repository.CertificaatRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.kern.Rol;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementatie van de {@link AuthenticatieService} welke het Authenticatiemiddel, behorende bij het identificerende
 * certificaat, vindt in de database.
 */
@Service
public class AuthenticatieServiceImpl implements AuthenticatieService {

    @Inject
    private CertificaatRepository certificaatRepository;

    @Inject
    private DomeinObjectFactory   domeinObjectFactory;

    private static final Logger   LOGGER = LoggerFactory.getLogger(AuthenticatieServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public AuthenticatieMiddelDTO zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(final Long serial,
            final byte[] signature, final String subject)
    {
        // Voorlopig is de bevraging altijd in de rol van afnemer. In latere versies zal de gewenste rol moeten worden
        // meegegeven.
        Rol gezochteRol = Rol.AFNEMER;
        Authenticatiemiddel authenticatiemiddel = null;

        try {
            authenticatiemiddel =
                certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(serial, subject, domeinObjectFactory
                        .createCertificaat().converteerSignatureNaarString(signature), gezochteRol);
        } catch (IncorrectResultSizeDataAccessException e) {
            LOGGER.error(String.format("Meerdere Authenticatiemiddelen gevonden voor certificaat en rol, maar dat is "
                + "niet toegestaan. Gezocht op rol '%s' en op certificaat 'serial: %s - subject: %s", gezochteRol,
                    serial, subject));
            throw new MeerdereAuthenticatieMiddelenExceptie(e);
        } catch (DecoderException e) {
            LOGGER.error("Het zoeken van de partij heeft een technische fout opgeleverd.");
            throw new IllegalArgumentException(e);
        }

        return bouwAuthenticatieMiddelDTO(authenticatiemiddel);
    }

    /**
     * Bouwt een {@link AuthenticatieMiddelDTO} instantie op basis van de opgegeven {@link Authenticatiemiddel}. Indien
     * het opgegeven <code>Authenticatiemiddel</code> gelijk is aan <code>null</code>, dan zal er ook <code>null</code>
     * worden geretourneerd.
     *
     *
     * @param authenticatiemiddel het authenticatie middel op basis waarvan de DTO moet worden opgebouwd.
     * @return een DTO versie van het opgegeen Authenticatiemiddel.
     */
    private AuthenticatieMiddelDTO bouwAuthenticatieMiddelDTO(final Authenticatiemiddel authenticatiemiddel) {
        AuthenticatieMiddelDTO authenticatieMiddelDTO = null;

        if (authenticatiemiddel != null) {
            authenticatieMiddelDTO =
                new AuthenticatieMiddelDTO(authenticatiemiddel.getID(), authenticatiemiddel.getPartij().getID());
        }

        return authenticatieMiddelDTO;
    }

}
