/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;

/**
 * Interface van de repositorie voor het ophalen van expressies.
 */
public interface Lo3FilterRubriekRepository {

    /**
     * Haalt alle LO3 filter rubrieken op die aan een dienstbundel gekoppeld zijn.
     *
     * @param dienstbundel
     *            De dienstbundel
     * @return De lijst van lo3 filter rubrieken.
     */
    List<String> haalLo3FilterRubriekenVoorDienstbundel(final Dienstbundel dienstbundel);

}
