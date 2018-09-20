/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhgem;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersbijhgemGenerator extends HisMaterieelGenerator<Persadres, HisPersbijhgem> {

    private static final HisPersbijhgemGenerator instance = new HisPersbijhgemGenerator();

    private HisPersbijhgemGenerator() {
    }

    public static HisPersbijhgemGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Persadres persadres) {
        return RandomUtil.random.nextInt(2);
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersbijhgem generateHisMaterieel(final Persadres persadres) {
        HisPersbijhgem his = new HisPersbijhgem();
        his.setPers(persadres.getPers());
        his.setPartij(RandomUtil.getPartijByBijhgem());
        his.setDatinschringem(RandomUtil.randomDate());
        his.setIndonverwdocaanw(persadres.getPers().getIndonverwdocaanw());
        return his;
    }

}
