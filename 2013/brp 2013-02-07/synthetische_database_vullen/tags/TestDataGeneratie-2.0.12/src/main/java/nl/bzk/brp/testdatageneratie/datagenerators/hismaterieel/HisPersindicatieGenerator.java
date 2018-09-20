/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;


public class HisPersindicatieGenerator extends HistorieGenerator<Persindicatie, HisPersindicatie>{

    private static final HisPersindicatieGenerator instance = new HisPersindicatieGenerator();

    private HisPersindicatieGenerator() {
    }

    public static HisPersindicatieGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Persindicatie actueel) {
        return isFractie(3)? 1: 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersindicatie generateHisMaterieel(final Persindicatie actueel) {
        HisPersindicatie his = new HisPersindicatie();
        his.setPersindicatie(actueel);
        his.setWaarde(actueel.getWaarde());
        return his;
    }

}
