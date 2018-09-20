/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderschap;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class HisOuderouderschapGenerator extends HisMaterieelGenerator<Betr, HisOuderouderschap> {

    private static final HisOuderouderschapGenerator instance = new HisOuderouderschapGenerator();

    private HisOuderouderschapGenerator() {
    }

    public static HisOuderouderschapGenerator getInstance() {
        return instance;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Betr betr) {
        return RandomUtil.random.nextInt(2);
    }

    @Override
    public HisOuderouderschap generateHisMaterieel(final Betr betr) {
        HisOuderouderschap his = new HisOuderouderschap();
        his.setBetr(betr);
        return his;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

}
