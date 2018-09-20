/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersids;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersidsGenerator extends HisMaterieelGenerator<Pers, HisPersids> {

    private static final HisPersidsGenerator instance = new HisPersidsGenerator();

    private HisPersidsGenerator() {
    }

    public static HisPersidsGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return RandomUtil.random.nextInt(2);
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersids generateHisMaterieel(final Pers actueel) {
        HisPersids his = new HisPersids();
        his.setPers(actueel);
        his.setBsn(actueel.getBsn());
        his.setAnr(actueel.getAnr());
        return his;
    }

}
