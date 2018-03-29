/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis;

import nl.bzk.brp.service.algemeen.request.OIN;

/**
 * Interface voor het zetten van de OINs voor XML verzoeken.
 */
public interface OinStubService {

    void setOINs(OIN oin);

}
