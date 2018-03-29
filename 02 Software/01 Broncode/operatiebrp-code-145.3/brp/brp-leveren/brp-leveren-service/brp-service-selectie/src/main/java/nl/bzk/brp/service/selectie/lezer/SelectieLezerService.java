/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;

/**
 * SelectieLezerService.
 */
public interface SelectieLezerService {


    /**
     * Start het lezen van blobs uit de database.
     *
     * @param selectie de selectie
     * @throws SelectieException fout bij lezen
     */
    void startLezen(Selectie selectie) throws SelectieException;
}
