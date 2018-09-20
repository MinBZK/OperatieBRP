/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersgeslachtsaandGenerator extends HisMaterieelGenerator<Pers, HisPersgeslachtsaand> {

    private static final HisPersgeslachtsaandGenerator instance = new HisPersgeslachtsaandGenerator();

    private HisPersgeslachtsaandGenerator() {
    }

    public static HisPersgeslachtsaandGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return RandomUtil.isFractie(10000)? 1: 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersgeslachtsaand generateHisMaterieel(final Pers actueel, final boolean newCopy) {
        HisPersgeslachtsaand his = new HisPersgeslachtsaand();
        his.setPers(actueel.getId());
        his.setGeslachtsaand(actueel.getGeslachtsaand());
        return his;
    }

}
