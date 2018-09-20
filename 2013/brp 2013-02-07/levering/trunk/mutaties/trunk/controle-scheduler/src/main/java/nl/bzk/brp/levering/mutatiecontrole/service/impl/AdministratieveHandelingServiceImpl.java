/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.mutatiecontrole.jms.AdministratieveHandelingVerwerker;
import nl.bzk.brp.levering.mutatiecontrole.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.mutatiecontrole.service.AdministratieveHandelingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * De Class AdministratieveHandelingServiceImpl vormt de implementatie van de interface AdministratieveHandelingService.
 */
@Service
public class AdministratieveHandelingServiceImpl implements AdministratieveHandelingService {

    private static Logger                     logger = LoggerFactory
                                                             .getLogger(AdministratieveHandelingServiceImpl.class);

    @Inject
    private AdministratieveHandelingRepository       administratieveHandelingRepository;

    @Inject
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.levering.mutatiecontrole.service.AdministratieveHandelingService#
     * plaatsOnverwerkteAdministratieveHandelingenOpQueue()
     */
    @Override
    public void plaatsOnverwerkteAdministratieveHandelingenOpQueue() {

        List<BigInteger> onverwerkteAdministratieveHandelingen = haalOnverwerkteAdministratieveHandelingenOp();
        plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);

        logger.info(onverwerkteAdministratieveHandelingen.size()
            + " onverwerkte administratieve handelingen opgeruimd "
            + "en op de JMS queue gezet tbv mutaties zodat deze verwerkt kunnen worden.");
    }

    /**
     * Plaats de identifiers van de Administratieve Handelingen op de JMS queue.
     *
     * @param onverwerkteAdministratieveHandelingen de onverwerkte administratieve handelingen
     */
    private void plaatsAdministratieveHandelingenOpQueue(final List<BigInteger> onverwerkteAdministratieveHandelingen) {
        administratieveHandelingVerwerker
            .plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);
    }

    /**
     * Haal onverwerkte administratieve handelingen op uit de Administratieve Handeling Repository.
     *
     * @return lijst met Big Integers die de identifiers voorstellen van de Administratieve Handelingen
     */
    private List<BigInteger> haalOnverwerkteAdministratieveHandelingenOp() {
        return administratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp();
    }

}
