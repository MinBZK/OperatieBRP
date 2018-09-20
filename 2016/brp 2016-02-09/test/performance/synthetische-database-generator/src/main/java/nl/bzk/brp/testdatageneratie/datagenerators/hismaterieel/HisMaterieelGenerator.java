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


/**
 * His materieel generator.
 */
public abstract class HisMaterieelGenerator<A, H extends HisMaterieel> {

    /**
     * Genereert his materieels.
     *
     * @param actueel actueel
     * @param dataanvHuidig dataanv huidig
     * @return the list
     */
    public List<H> generateHisMaterieels(final A actueel, final Integer dataanvHuidig) {
        Date tsReg = RandomUtil.getTimestamp(dataanvHuidig);
        int aantalRecordsExclusiefHuidig = 1 + getNextAantalExclusiefHuidig(actueel);
        // * 2;  <-- TODO: dit stond op *2 maar klopt dat wel?
        List<H> hisMaterieels = new ArrayList<H>(aantalRecordsExclusiefHuidig);

        H hisActueel = generateHisMaterieel(actueel, false);
        hisActueel.setDataanvgel(dataanvHuidig);
        hisActueel.setTsreg(tsReg);
        long actieActueel = RandomUtil.getActie();
        hisActueel.setActieinh(actieActueel);
        hisMaterieels.add(hisActueel);

        for (int i = 0; i < aantalRecordsExclusiefHuidig; i++) {
            Date tsRegVroeger =
                RandomUtil.getPastTimestamp(tsReg.getTime() - RandomUtil.DAG_IN_MS, getMaxTerugInMs());
            int brpDatumAanvangGeldigheid = GenUtil.naarBrpDatum(tsRegVroeger);
            int brpDatumEindeGeldigheid = hisMaterieels.get(i).getDataanvgel();

            H hisMaterieel = generateHisMaterieel(actueel, true);
            hisMaterieel.setDataanvgel(brpDatumAanvangGeldigheid);
            hisMaterieel.setDateindegel(brpDatumEindeGeldigheid);
            hisMaterieel.setTsreg(tsReg);
            long actieEerder = RandomUtil.getActie();
            hisMaterieel.setActieinh(actieEerder);
            hisMaterieel.setActieaanpgel(actieActueel);
            hisMaterieels.add(hisMaterieel);

            i++;

            H hisVervallen = generateHisMaterieel(actueel, true);
            hisVervallen.setDataanvgel(brpDatumAanvangGeldigheid);
            hisVervallen.setDateindegel(brpDatumEindeGeldigheid);
            hisVervallen.setTsreg(tsRegVroeger);
            hisVervallen.setTsverval(tsReg);
            hisVervallen.setActieverval(actieEerder);
            hisMaterieel.setActieinh(RandomUtil.getActie());
            hisMaterieels.add(hisVervallen);

            tsReg = tsRegVroeger;
        }

        postProcessHisRecords(hisMaterieels);
        return hisMaterieels;
    }

    /**
     * Geeft next aantal exclusief huidig.
     *
     * @param actueel actueel
     * @return next aantal exclusief huidig
     */
    protected abstract int getNextAantalExclusiefHuidig(final A actueel);

    /**
     * Geeft max terug in ms.
     *
     * @return max terug in ms
     */
    protected abstract long getMaxTerugInMs();

    /**
     * Genereert his materieel.
     *
     * @param actueel actueel
     * @param newCopy new copy
     * @return the h
     */
    public abstract H generateHisMaterieel(final A actueel, final boolean newCopy);

    /**
     * Voer eventueel post processing uit op de his set.
     * Bijvoorbeeld bij his_PersAdres -> dataanvgel moet gelijk zijn aan dataanvadresh. (BRBY0525)
     * @param records
     */
    protected void postProcessHisRecords(final List<H> records) {

    }

}
