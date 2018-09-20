/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;


public class HisPersgeslachtsaandGenerator extends HistorieGenerator<Pers, HisPersgeslachtsaand> {

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return RandomService.isFractie(10000)? 1: 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    protected HisPersgeslachtsaand generateHisMaterieel(final Pers actueel) {
        return new HisPersgeslachtsaand(actueel);
    }

}
