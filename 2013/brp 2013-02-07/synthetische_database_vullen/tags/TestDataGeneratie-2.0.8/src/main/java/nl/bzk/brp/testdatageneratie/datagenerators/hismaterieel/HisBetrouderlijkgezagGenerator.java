/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisBetrouderlijkgezag;


public class HisBetrouderlijkgezagGenerator extends HistorieGenerator<Betr, HisBetrouderlijkgezag> {

    @Override
    protected int getNextAantalExclusiefHuidig(final Betr betr) {
        return RandomService.random.nextInt(3);
    }

    @Override
    protected HisBetrouderlijkgezag generateHisMaterieel(final Betr betr) {
        return new HisBetrouderlijkgezag(betr);
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 10 * 365 * 24 * 60 * 60 * 1000;
    }

}
