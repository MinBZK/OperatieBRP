/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service;

import nl.bzk.brp.levering.verzending.context.BerichtContext;

/**
 * Interface voor het uitvoeren van de verzend stappen.
 */
public interface StappenService {

    /**
     * Method waar de stappen uitgevoerd worden.
     *
     * @param berichtContext de context voor de stappen
     * @throws Exception als er iets fout gaat
     */
    void voerStappenUit(BerichtContext berichtContext) throws Exception;
}
