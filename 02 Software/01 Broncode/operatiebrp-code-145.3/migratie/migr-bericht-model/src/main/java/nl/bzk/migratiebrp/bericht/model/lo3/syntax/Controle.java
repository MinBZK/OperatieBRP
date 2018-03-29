/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.syntax;

import java.util.Map.Entry;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Enkele syntax controle
 */
@FunctionalInterface
public interface Controle {

    /**
     * Controleer.
     * @param element de LO3 elementen definitie en de te controleren waarde
     * @throws BerichtInhoudException als de waarde niet voldoet
     */
    void controleer(Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException;
}
