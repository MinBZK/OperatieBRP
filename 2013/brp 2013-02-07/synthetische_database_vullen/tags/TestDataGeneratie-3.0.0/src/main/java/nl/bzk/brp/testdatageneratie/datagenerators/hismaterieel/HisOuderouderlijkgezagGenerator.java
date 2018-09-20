/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisOuderouderlijkgezagGenerator extends HisMaterieelGenerator<Betr, HisOuderouderlijkgezag> {

    private static final HisOuderouderlijkgezagGenerator instance = new HisOuderouderlijkgezagGenerator();

    private HisOuderouderlijkgezagGenerator() {
    }

    public static HisOuderouderlijkgezagGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Betr betr) {
        return RandomUtil.random.nextInt(3);
    }

    @Override
    public HisOuderouderlijkgezag generateHisMaterieel(final Betr betr) {
        HisOuderouderlijkgezag his = new HisOuderouderlijkgezag();
        his.setBetr(betr);
        his.setIndouderheeftgezag(betr.getIndouderheeftgezag());
        return his;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

}
