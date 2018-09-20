/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.register.client.GemeenteService;

public class TestGemeenteService implements GemeenteService {
    private final GemeenteRegister register;

    public TestGemeenteService(final GemeenteRegister register) {
        this.register = register;
    }

    @Override
    public GemeenteRegister geefRegister() {
        return register;
    }

    @Override
    public void refreshRegister() {
    }

    @Override
    public void clearRegister() {
    }

}
