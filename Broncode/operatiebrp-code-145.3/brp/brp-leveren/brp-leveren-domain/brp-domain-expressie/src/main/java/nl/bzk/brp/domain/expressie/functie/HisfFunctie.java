/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;

/**
 * De functie HISF(attribuutid) levert een lijst met alle in de materiele en formele
 * historie voorkomende attribuutwaarden van het opgegeven attribuut.
 *
 * De functie HISF(groep) levert een lijst met alle in de materiele en formele
 * historie voorkomende {@link MetaRecord}s
 *
 * De functie geeft altijd een lijst terug (en dus nooit een NULL-waarde).
 * De lijst kan leeg zijn, maar zal geen NULL-waarden bevatten.
 */
@Component
@FunctieKeyword("HISF")
final class HisfFunctie extends AbstractHisFuctie {

    @Override
    protected boolean magRecordTonen(final MetaRecord metaRecord, final Persoonslijst persoonslijst) {
        return !persoonslijst.isActueel(metaRecord);
    }
}
