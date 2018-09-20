/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.Bijhaard;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhaard;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;


public class HisPersbijhaardGenerator extends HisMaterieelGenerator<Pers, HisPersbijhaard> {

    private static final HisPersbijhaardGenerator instance = new HisPersbijhaardGenerator();

    private HisPersbijhaardGenerator() {
    }

    public static HisPersbijhaardGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return actueel.getBijhaard() == Bijhaard.MINISTER? 1: 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersbijhaard generateHisMaterieel(final Pers actueel) {
        HisPersbijhaard his = new HisPersbijhaard();
        his.setPers(actueel);
        his.setBijhaard(actueel.getBijhaard());
        return his;
    }

}
