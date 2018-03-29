/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.List;
import nl.bzk.brp.service.algemeen.StapException;

/**
 * VerwerkPersoonService. Maakt persoon deel berichten voor standaard selectie en volledige
 * synchronisatiegegevens voor selectie afnemerindicaties.
 */
interface VerwerkPersoonService {
    /**
     * @param opdracht opdracht
     * @return lijst met resultaten
     * @throws StapException fout bij verwerken
     */
    List<VerwerkPersoonResultaat> verwerk(MaakSelectieResultaatOpdracht opdracht) throws StapException;
}
