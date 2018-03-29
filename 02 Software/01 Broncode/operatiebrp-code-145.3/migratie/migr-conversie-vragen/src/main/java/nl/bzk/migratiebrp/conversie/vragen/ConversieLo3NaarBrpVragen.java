/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Conversie van Lo3 naar BRP voor vragen (uc1004).
 */
public interface ConversieLo3NaarBrpVragen {

    /**
     * Vertaald de lo3 zoekcriteria naar een brp zoekcriteria.
     * @param lo3zoekCriteria Te vertalen zoek criteria
     * @return het vertaalde zoek criteria
     * @throws ConversieExceptie vraag kan niet worden geconverteerd.
     */
    List<ZoekCriterium> converteer(final List<Lo3CategorieWaarde> lo3zoekCriteria);
}
