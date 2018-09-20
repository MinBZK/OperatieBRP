/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.HisMaterieel;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public abstract class HisMaterieelGenerator<A, H extends HisMaterieel> {

    public List<H> generateHisMaterieels(final A actueel, final Integer dataanvHuidig) {
        Date tsReg = RandomUtil.getTimestamp(dataanvHuidig);
        int aantalRecordsExclusiefHuidig = 1 + getNextAantalExclusiefHuidig(actueel) * 2;
        List<H> hisMaterieels = new ArrayList<H>(aantalRecordsExclusiefHuidig);

        H hisActueel = generateHisMaterieel(actueel);
        hisActueel.setDataanvgel(dataanvHuidig);
        hisActueel.setTsreg(tsReg);
        hisMaterieels.add(hisActueel);

        for (int i = 0; i < aantalRecordsExclusiefHuidig; i++) {
            Date tsRegVroeger =
                RandomUtil.getPastTimestamp(tsReg.getTime() - RandomUtil.DAG_IN_MS, getMaxTerugInMs());
            int brpDatumAanvangGeldigheid = GenUtil.naarBrpDatum(tsRegVroeger);
            int brpDatumEindeGeldigheid = hisMaterieels.get(i).getDataanvgel();

            H hisMaterieel = generateHisMaterieel(actueel);
            hisMaterieel.setDataanvgel(brpDatumAanvangGeldigheid);
            hisMaterieel.setDateindegel(brpDatumEindeGeldigheid);
            hisMaterieel.setTsreg(tsReg);
            hisMaterieels.add(hisMaterieel);

            i++;

            H hisVervallen = generateHisMaterieel(actueel);
            hisVervallen.setDataanvgel(brpDatumAanvangGeldigheid);
            hisVervallen.setDateindegel(brpDatumEindeGeldigheid);
            hisVervallen.setTsreg(tsRegVroeger);
            hisVervallen.setTsverval(tsReg);
            hisVervallen.setActieverval(RandomUtil.getActie());
            hisMaterieels.add(hisVervallen);

            tsReg = tsRegVroeger;
        }
        return hisMaterieels;
    }

    protected abstract int getNextAantalExclusiefHuidig(final A actueel);

    protected abstract long getMaxTerugInMs();

    public abstract H generateHisMaterieel(final A actueel);

}
