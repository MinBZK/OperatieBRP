/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import nl.bzk.brp.service.selectie.algemeen.Selectie;

/**
 * SelectieService.
 */
public interface SelectieService {

    /**
     * Bepaalt de te draaien selectie.
     *
     * @return een {@link Selectie}
     */
    Selectie bepaalSelectie();

    /**
     * Markeert het einde van de selectierun.
     *
     * @param selectie de lopende selectie
     */
    void eindeSelectie(Selectie selectie);
}
