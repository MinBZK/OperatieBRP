/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import nl.bzk.brp.testdatageneratie.domain.kern.His;

public final class HisCorrectieUtil {

    public static final long MAX_BACK = 10L * 365 * RandomUtil.DAG_IN_MS;

    public static His creeerHisCorrectie(final His hisNieuw, final His hisBasis) {

        // Creeer voorlopende actie
        hisNieuw.setActieByActieverval(hisBasis.getActieByActieinh());
        hisNieuw.setActieByActieinh(RandomUtil.getActie());

        // Creeer voorlopend record met eerdere tijdstippen registratie en verval
        hisNieuw.setTsverval(hisBasis.getTsreg());
        hisNieuw.setTsreg(RandomUtil.getPastTimestamp(hisNieuw.getTsverval().getTime(), MAX_BACK));

        return hisNieuw;
    }
}
