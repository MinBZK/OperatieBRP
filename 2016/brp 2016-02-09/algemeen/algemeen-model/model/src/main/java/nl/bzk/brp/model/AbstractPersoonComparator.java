/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;


/**
 * Abstracte voor persooncomparators.
 */
public abstract class AbstractPersoonComparator {

    /**
     * Bepaal het historie record dat gebruikt moet worden voor de sortering van meerdere objecten met een dergelijke groep, bijvoorbeeld meerdere persoon
     * / verificaties. Als er een actueel record is: gebruik dat. Anders: gebruik het meest recente vervallen record (meest recente tijdstip registratie).
     *
     * @param historie de historie set
     * @return het record dat de basis is voor de sortering.
     */
    public <T extends FormeelHistorisch & FormeelVerantwoordbaar> T getTeGebruikenHistorieRecord(
        final FormeleHistorieSet<T> historie)
    {
        T record = null;
        if (historie.heeftActueelRecord()) {
            record = historie.getActueleRecord();
        } else {
            T meestRecenteRecord = null;
            for (T eenRecord : historie.getHistorie()) {
                if (meestRecenteRecord == null
                    || eenRecord.getFormeleHistorie().getTijdstipRegistratie()
                    .na(meestRecenteRecord.getFormeleHistorie().getTijdstipRegistratie()))
                {
                    meestRecenteRecord = eenRecord;
                }
            }
            record = meestRecenteRecord;
        }
        return record;
    }

}
