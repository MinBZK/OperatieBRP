/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.afnemerclient.service.impl;

import nl.bzk.brp.afnemerclient.service.VerstuurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerstuurServiceImpl implements VerstuurService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerstuurServiceImpl.class);

    @Value("${verstuurder.aantalberichten}")
    private int berichtenPerInterval;

    @Override
    public void verstuurBericht() {
        for(int i=0; i<berichtenPerInterval; i++){
            LOGGER.debug("Verstuur bericht " + i);
        }
    }
}
