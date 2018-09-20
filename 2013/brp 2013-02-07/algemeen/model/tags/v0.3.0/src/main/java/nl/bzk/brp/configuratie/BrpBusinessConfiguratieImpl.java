/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.configuratie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/** Implementatie van {@link BrpBusinessConfiguratie}. */
@Service
public class BrpBusinessConfiguratieImpl implements BrpBusinessConfiguratie {

    @Value("${brp.database.timeout.seconden}")
    private int databaseTimeOut;

    /** {@inheritDoc} */
    @Override
    public int getDatabaseTimeOutProperty() {
        return databaseTimeOut;
    }
}
