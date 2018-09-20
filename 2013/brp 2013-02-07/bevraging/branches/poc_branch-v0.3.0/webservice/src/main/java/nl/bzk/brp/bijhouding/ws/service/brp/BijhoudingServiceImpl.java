/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws.service.brp;

import javax.jws.WebService;

import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingResultaat;
import nl.bzk.brp.poc.business.dto.verzoek.Bijhouding;

@WebService
public class BijhoudingServiceImpl implements BijhoudingService {

    @Override
    public BijhoudingResultaat bijhouden(final Bijhouding bijhouding) {   
        return new BijhoudingResultaat("Dit is een POC-je");
    }
}
