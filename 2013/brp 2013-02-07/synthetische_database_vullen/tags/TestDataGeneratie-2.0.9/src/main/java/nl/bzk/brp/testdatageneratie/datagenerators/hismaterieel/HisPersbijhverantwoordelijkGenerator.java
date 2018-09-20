/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhverantwoordelijk;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Verantwoordelijke;


public class HisPersbijhverantwoordelijkGenerator extends HistorieGenerator<Pers, HisPersbijhverantwoordelijk> {

    private static final HisPersbijhverantwoordelijkGenerator instance = new HisPersbijhverantwoordelijkGenerator();

    private HisPersbijhverantwoordelijkGenerator() {
    }

    public static HisPersbijhverantwoordelijkGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return actueel.getVerantwoordelijke() == Verantwoordelijke.MINISTER? 1: 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    protected HisPersbijhverantwoordelijk generateHisMaterieel(final Pers actueel) {
        return new HisPersbijhverantwoordelijk(actueel);
    }

}
