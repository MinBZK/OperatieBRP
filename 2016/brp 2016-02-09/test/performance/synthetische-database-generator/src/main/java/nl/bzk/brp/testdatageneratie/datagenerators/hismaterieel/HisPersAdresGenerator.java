/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import java.util.List;

import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Landgebied;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.utils.PersAdresUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisPersAdresGenerator extends HisMaterieelGenerator<Persadres, HisPersadres> {

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
    public HisPersadres generateHisMaterieel(final Persadres actueel, final boolean newCopy) {
        Persadres currentAdres;
        if (newCopy) {
            Pers dummyPers = new Pers();
            dummyPers.setId(actueel.getPers());
            currentAdres = PersAdresUtil.generatePersAdres(dummyPers);
        } else {
            currentAdres = actueel;
        }
        HisPersadres his = new HisPersadres();
        his.setPersadres(actueel.getId()); // dit is de enige plek om de goede id te krijgen. currentAddres maybe fake
        his.setSrt(currentAdres.getSrt());
        his.setDataanvadresh(currentAdres.getDataanvadresh());
        if (currentAdres.getLandgebied() != null) {
            his.setLandgebied(currentAdres.getLandgebied());
        } else {
            his.setLandgebied(MetaRepo.get(Landgebied.class, Locatie.LAND_CODE_NL).getId());
        }

        his.setAangadresh(currentAdres.getAangadresh());
        his.setIdentcodeadresseerbaarobject(currentAdres.getIdentcodeadresseerbaarobject());
        his.setAfgekortenor(currentAdres.getAfgekortenor());
        his.setBladresregel1(currentAdres.getBladresregel1());
        his.setBladresregel2(currentAdres.getBladresregel2());
        his.setBladresregel3(currentAdres.getBladresregel3());
        his.setBladresregel4(currentAdres.getBladresregel4());
        his.setBladresregel5(currentAdres.getBladresregel5());
        his.setBladresregel6(currentAdres.getBladresregel6());
//        his.setDatvertrekuitnederland(currentAdres.getDatvertrekuitnederland());
        his.setHuisletter(currentAdres.getHuisletter());
        his.setHuisnr(currentAdres.getHuisnr());
        his.setHuisnrtoevoeging(currentAdres.getHuisnrtoevoeging());
        his.setIdentcodenraand(currentAdres.getIdentcodenraand());
        his.setLocoms(currentAdres.getLocoms());
        his.setLoctenopzichtevanadres(currentAdres.getLoctenopzichtevanadres());
        his.setNor(currentAdres.getNor());
        his.setGem(currentAdres.getGem());
        his.setGemdeel(currentAdres.getGemdeel());
        his.setWplnaam(currentAdres.getWplnaam());
        his.setPostcode(currentAdres.getPostcode());
        his.setRdnwijz(currentAdres.getRdnwijz());
        return his;
    }


    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    protected void postProcessHisRecords(final List<HisPersadres> records) {
        //BRBY0525: dataanvadresh moet gelijk zijn aan dataanvgel.
        for (HisPersadres record : records) {
            record.setDataanvadresh(record.getDataanvgel());
        }
    }
}
