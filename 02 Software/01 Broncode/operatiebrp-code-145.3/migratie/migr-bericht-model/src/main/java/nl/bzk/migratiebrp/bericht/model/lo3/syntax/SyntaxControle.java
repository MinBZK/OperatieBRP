/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.syntax;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Interface voor syntaxtcontrole (enum zijn niet te stubben en wordt testen van units
 * een stuk lastiger. Interface is wel te stubben en dus eenvoudiger om te testen.
 */
public interface SyntaxControle {

    /**
     * Controleert de inhoud van een LO3 bericht.
     * @param categorieWaarden De lijst van Lo3CategorieWaarden om te controleren
     * @throws BerichtInhoudException Wordt gegooid indien de inhoud niet correct is
     */
    void controleerInhoud(List<Lo3CategorieWaarde> categorieWaarden) throws BerichtInhoudException;
}
