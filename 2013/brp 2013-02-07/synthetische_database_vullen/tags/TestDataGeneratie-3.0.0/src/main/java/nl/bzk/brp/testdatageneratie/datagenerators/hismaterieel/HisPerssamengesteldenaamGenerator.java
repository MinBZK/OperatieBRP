/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPerssamengesteldenaamGenerator extends HisMaterieelGenerator<Pers, HisPerssamengesteldenaam> {

    private static final HisPerssamengesteldenaamGenerator instance = new HisPerssamengesteldenaamGenerator();

    private HisPerssamengesteldenaamGenerator() {
    }

    public static HisPerssamengesteldenaamGenerator getInstance() {
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
    public HisPerssamengesteldenaam generateHisMaterieel(final Pers actueel) {
        HisPerssamengesteldenaam his = new HisPerssamengesteldenaam();
        his.setPers(actueel);
        his.setGeslnaam(actueel.getGeslnaam());
        his.setIndalgoritmischafgeleid(actueel.getIndalgoritmischafgeleid());
        his.setPredikaat(actueel.getPredikaatByPredikaat());
        his.setAdellijketitel(actueel.getAdellijketitelByAdellijketitel());
        his.setIndnreeks(actueel.getIndnreeks());
        his.setVoornamen(actueel.getVoornamen());
        his.setVoorvoegsel(actueel.getVoorvoegsel());
        his.setScheidingsteken(actueel.getScheidingsteken());
        return his;
    }

}
