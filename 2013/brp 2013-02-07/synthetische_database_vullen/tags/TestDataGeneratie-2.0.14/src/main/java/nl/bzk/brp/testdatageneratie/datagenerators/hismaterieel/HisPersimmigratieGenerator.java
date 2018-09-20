/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersimmigratie;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersimmigratieGenerator extends HisMaterieelGenerator<Pers, HisPersimmigratie> {

    private static final HisPersimmigratieGenerator instance = new HisPersimmigratieGenerator();

    private HisPersimmigratieGenerator() {
    }

    public static HisPersimmigratieGenerator getInstance() {
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
    public HisPersimmigratie generateHisMaterieel(final Pers actueel) {
        HisPersimmigratie his = new HisPersimmigratie();
        his.setPers(actueel);
        his.setDatvestiginginnederland(actueel.getDatvestiginginnederland());
        his.setLand(actueel.getLandByLandvanwaargevestigd());
        return his;
    }

}
