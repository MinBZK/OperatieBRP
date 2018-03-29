/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.util.Date;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.register.client.PartijService;

public class TestPartijService implements PartijService {
    private final PartijRegister register;

    public TestPartijService(final PartijRegister register) {
        this.register = register;
    }

    @Override
    public PartijRegister geefRegister() {
        return register;
    }

    @Override
    public void refreshRegister() {
    }

    @Override
    public void clearRegister() {
    }

    @Override
    public String getLaatsteBericht() {
        return null;
    }

    @Override
    public Date getLaatsteOntvangst() {
        return null;
    }

    @Override
    public String getRegisterAsString() {
        return null;
    }

}
