/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.Persgeslnaamcomp;


public class HisPersgeslnaamcompGenerator extends HistorieGenerator<Persgeslnaamcomp, HisPersgeslnaamcomp> {

    @Override
    protected int getNextAantalExclusiefHuidig(final Persgeslnaamcomp actueel) {
        return RandomService.random.nextInt(1);
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    protected HisPersgeslnaamcomp generateHisMaterieel(final Persgeslnaamcomp actueel) {
        return new HisPersgeslnaamcomp(actueel);
    }

}
