/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverblijfstitel;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersverblijfstitelGenerator extends HisMaterieelGenerator<Pers, HisPersverblijfstitel> {

    private static final HisPersverblijfstitelGenerator instance = new HisPersverblijfstitelGenerator();

    private HisPersverblijfstitelGenerator() {
    }

    public static HisPersverblijfstitelGenerator getInstance() {
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
    public HisPersverblijfstitel generateHisMaterieel(final Pers actueel) {
        HisPersverblijfstitel his = new HisPersverblijfstitel();
        his.setPers(actueel);
        his.setVerblijfstitel(actueel.getVerblijfstitel());
        his.setDataanvverblijfstitel(actueel.getDataanvverblijfstitel());
        his.setDatvoorzeindeverblijfstitel(actueel.getDatvoorzeindeverblijfstitel());
        return his;
    }

}
