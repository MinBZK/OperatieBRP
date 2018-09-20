/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Persnation;


public class HisPersnationGenerator extends HisMaterieelGenerator<Persnation, HisPersnation> {

    private static final HisPersnationGenerator instance = new HisPersnationGenerator();

    private HisPersnationGenerator() {
    }

    public static HisPersnationGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Persnation actueel) {
        return actueel.getRdnverlies() == null? 0: 1;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * 1000;
    }

    @Override
    public HisPersnation generateHisMaterieel(final Persnation actueel, final boolean newCopy) {
        HisPersnation his = new HisPersnation();
        his.setPersnation(actueel.getId());
        his.setRdnverlies(actueel.getRdnverlies());
        his.setRdnverk(actueel.getRdnverk());
        return his;
    }

}
