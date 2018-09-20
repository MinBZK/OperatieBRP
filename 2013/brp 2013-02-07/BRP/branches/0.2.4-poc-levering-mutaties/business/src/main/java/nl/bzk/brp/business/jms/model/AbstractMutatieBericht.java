/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.model;

import java.util.Collection;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;

/**
 * Uitgaand mutatie bericht.
 */
public abstract class AbstractMutatieBericht extends BerichtBericht {

    private DatumTijd tijdAdministratieveHandeling;

    public DatumTijd getTijdAdministratieveHandeling() {
        return this.tijdAdministratieveHandeling;
    }

    public void setTijdAdministratieveHandeling(final DatumTijd tijdAdministratieveHandeling) {
        this.tijdAdministratieveHandeling = tijdAdministratieveHandeling;
    }

    /**
     * Geeft het soort mutatie.
     * @return
     */
    abstract Soortmutatie getSoortMutatie();

    @Override
    public Collection<String> getReadBsnLocks() {
        return null;
    }

    @Override
    public Collection<String> getWriteBsnLocks() {
        return null;
    }

    @Override
    public Soortbericht getSoortBericht() {
        return null;
    }
}
