/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import org.springframework.stereotype.Component;

/**
 * Globaal toegangspunt voor externe parameters voor synchronisatie functies.
 */
@Component
public class SyncParameters {

    private boolean initieleVulling;

    /**
     * Zet de 'initieleVulling' parameter.
     * 
     * @param initieleVulling
     *            de 'initieleVulling' parameter
     */
    public final void setInitieleVulling(final boolean initieleVulling) {
        this.initieleVulling = initieleVulling;
    }

    /**
     * De 'initieleVulling' parameter.
     * 
     * @return de 'initieleVulling' parameter
     */
    public final boolean isInitieleVulling() {
        return initieleVulling;
    }
}
