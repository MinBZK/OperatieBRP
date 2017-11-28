/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.function.Predicate;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * Factory voor maken van een predicaat om MetaRecords te filteren.
 */
@FunctionalInterface
interface MetaRecordFilterFactory {

    /**
     * Maakt het predikaat om MetaRecords te filteren.
     * @param berichtgegevens de berichtgegevens
     * @return een Predicate
     */
    Predicate<MetaRecord> maakRecordfilters(Berichtgegevens berichtgegevens);
}
