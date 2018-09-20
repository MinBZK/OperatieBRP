/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.utils.PersAdresUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersAdresGenerator extends HistorieGenerator<Persadres, HisPersadres> {

    private static final HisPersAdresGenerator instance = new HisPersAdresGenerator();

    private HisPersAdresGenerator() {
    }

    public static HisPersAdresGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Persadres persadres) {
        return RandomUtil.random.nextInt(6);
    }

    @Override
    protected HisPersadres generateHisMaterieel(final Persadres persadres) {
        HisPersadres hisPersadres = new HisPersadres(PersAdresUtil.generatePersAdres(persadres.getPers()));
        hisPersadres.setPersadres(persadres);
        return hisPersadres;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

}
