/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersaanschr;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersaanschrGenerator extends HistorieGenerator<Pers, HisPersaanschr> {

    private static final HisPersaanschrGenerator instance = new HisPersaanschrGenerator();

    private HisPersaanschrGenerator() {
    }

    public static HisPersaanschrGenerator getInstance() {
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
    public HisPersaanschr generateHisMaterieel(final Pers actueel) {
        HisPersaanschr his = new HisPersaanschr();
        his.setPers(actueel);
        his.setGeslnaamaanschr(actueel.getGeslnaamaanschr());
        his.setIndaanschralgoritmischafgele(actueel.getIndaanschralgoritmischafgele());
        his.setPredikaat(actueel.getPredikaatByPredikaat());
        his.setWijzegebruikgeslnaam(actueel.getWijzegebruikgeslnaam());
        his.setIndaanschrmetadellijketitels(actueel.getIndaanschrmetadellijketitels());
        his.setVoornamenaanschr(actueel.getVoornamenaanschr());
        his.setVoorvoegselaanschr(actueel.getVoorvoegselaanschr());
        his.setScheidingstekenaanschr(actueel.getScheidingstekenaanschr());
        return his;
    }

}
