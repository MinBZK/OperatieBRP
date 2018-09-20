/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;


public class HisPersvoornaamGenerator extends HistorieGenerator<Persvoornaam, HisPersvoornaam> {

    private static final HisPersvoornaamGenerator instance = new HisPersvoornaamGenerator();

    private HisPersvoornaamGenerator() {
    }

    public static HisPersvoornaamGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Persvoornaam actueel) {
        return 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersvoornaam generateHisMaterieel(final Persvoornaam actueel) {
        HisPersvoornaam his = new HisPersvoornaam();
        his.setPersvoornaam(actueel);
        his.setDataanvgel(actueel.getPers().getDatinschr());
        his.setNaam(actueel.getNaam());
        return his;
    }

}
