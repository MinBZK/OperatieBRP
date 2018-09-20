/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import nl.bzk.brp.bevraging.business.service.BerichtIdGenerator;
import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de {@link BerichtIdGenerator} interface die een uniek id genereert.
 *
 * Deze implementatie is nog niet thread-safe en ook nog niet 'persistant', dus na elke (her)start begint de
 * teller weer van vooraf aan. Dit dient uiteraard nog wel verzorgd te worden.
 */
@Service
public class StandaardBerichtIdGenerator implements BerichtIdGenerator {

    private int id = 0;

    @Override
    public long volgendeId() {
        // TODO: tim - Id generatie dient nog uniek te worden gemaakt over servers heen, persist en threadsafe.
        return ++id;
    }

}
