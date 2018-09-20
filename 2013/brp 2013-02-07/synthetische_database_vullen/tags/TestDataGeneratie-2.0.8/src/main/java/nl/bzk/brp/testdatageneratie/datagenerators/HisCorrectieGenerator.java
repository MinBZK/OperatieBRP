/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.domain.kern.His;

public class HisCorrectieGenerator {

    public static final long MAX_BACK = 10L * 365 * RandomService.DAG_IN_MS;

    public static His creeerHisCorrectie(final His hisNieuw, final His hisBasis) {

        // Creeer voorlopende actie
        hisNieuw.setActieByActieverval(hisBasis.getActieByActieinh());
        hisNieuw.setActieByActieinh(RandomService.getActie());

        // Creeer voorlopend record met eerdere tijdstippen registratie en verval
        hisNieuw.setTsverval(hisBasis.getTsreg());
        hisNieuw.setTsreg(RandomService.getPastTimestamp(hisNieuw.getTsverval().getTime(), MAX_BACK));

        return hisNieuw;
    }
}
