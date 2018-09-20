/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersopschorting;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;


public class HisPersopschortingGenerator extends HistorieGenerator<Pers, HisPersopschorting> {

    private static final HisPersopschortingGenerator instance = new HisPersopschortingGenerator();

    private HisPersopschortingGenerator() {
    }

    public static HisPersopschortingGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersopschorting generateHisMaterieel(final Pers actueel) {
        HisPersopschorting his = new HisPersopschorting();
        his.setPers(actueel);
        his.setRdnopschorting(actueel.getRdnopschorting());
        return his;
    }

}
