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
    public HisPersadres generateHisMaterieel(final Persadres actueel) {
        Persadres persAdres = PersAdresUtil.generatePersAdres(actueel.getPers());
        HisPersadres his = new HisPersadres();
        his.setPersadres(actueel);
        his.setFunctieadres(persAdres.getFunctieadres());
        his.setDataanvadresh(persAdres.getDataanvadresh());
        his.setLand(persAdres.getLand());
        his.setAangadresh(persAdres.getAangadresh());
        his.setAdresseerbaarobject(persAdres.getAdresseerbaarobject());
        his.setAfgekortenor(persAdres.getAfgekortenor());
        his.setBladresregel1(persAdres.getBladresregel1());
        his.setBladresregel2(persAdres.getBladresregel2());
        his.setBladresregel3(persAdres.getBladresregel3());
        his.setBladresregel4(persAdres.getBladresregel4());
        his.setBladresregel5(persAdres.getBladresregel5());
        his.setBladresregel6(persAdres.getBladresregel6());
        his.setDatvertrekuitnederland(persAdres.getDatvertrekuitnederland());
        his.setGemdeel(persAdres.getGemdeel());
        his.setHuisletter(persAdres.getHuisletter());
        his.setHuisnr(persAdres.getHuisnr());
        his.setHuisnrtoevoeging(persAdres.getHuisnrtoevoeging());
        his.setIdentcodenraand(persAdres.getIdentcodenraand());
        his.setLocoms(persAdres.getLocoms());
        his.setLoctovadres(persAdres.getLoctovadres());
        his.setNor(persAdres.getNor());
        his.setPartij(persAdres.getPartij());
        his.setPlaats(persAdres.getPlaats());
        his.setPostcode(persAdres.getPostcode());
        his.setRdnwijzadres(persAdres.getRdnwijzadres());
        return his;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

}
