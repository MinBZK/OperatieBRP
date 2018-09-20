/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersmigratie;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersmigratieGenerator extends HisMaterieelGenerator<Pers, HisPersmigratie> {

    private static final HisPersmigratieGenerator instance = new HisPersmigratieGenerator();

    private HisPersmigratieGenerator() {
    }

    public static HisPersmigratieGenerator getInstance() {
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
    public HisPersmigratie generateHisMaterieel(final Pers actueel, final boolean newCopy) {
        HisPersmigratie his = new HisPersmigratie();
        his.setPers(actueel.getId());
        his.setDataanvgel(actueel.getDatinschr());

        Integer landgebiedId = null;
        while (landgebiedId == null) {
            landgebiedId = RandomUtil.getLandgebiedBuitenlandsAdres().getId();
        }

        his.setLandgebiedmigratie(landgebiedId);
        if (landgebiedId != 229) {
            his.setSrtmigratie((short) SoortMigratie.EMIGRATIE.ordinal());
        } else {
            his.setSrtmigratie((short) SoortMigratie.IMMIGRATIE.ordinal());
        }

        return his;
    }

}
