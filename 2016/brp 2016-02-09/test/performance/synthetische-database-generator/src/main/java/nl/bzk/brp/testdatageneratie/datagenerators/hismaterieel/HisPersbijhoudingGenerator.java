/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel;

import nl.bzk.brp.testdatageneratie.domain.kern.Bijhaard;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhouding;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.Constanten;


/**
 * His persbijhaard generator.
 */
public final class HisPersbijhoudingGenerator extends HisMaterieelGenerator<Pers, HisPersbijhouding> {

    private static final HisPersbijhoudingGenerator INSTANCE = new HisPersbijhoudingGenerator();

    /**
     * Instantieert His persbijhaard generator.
     */
    private HisPersbijhoudingGenerator() {
    }

    public static HisPersbijhoudingGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    protected int getNextAantalExclusiefHuidig(final Pers actueel) {
        return actueel.getBijhaard() == Bijhaard.NIET_INGEZETENE.ordinal() ? 1 : 0;
    }

    @Override
    protected long getMaxTerugInMs() {
        return 2L * 20 * 365 * 24 * 60 * 60 * Constanten.DUIZEND;
    }

    @Override
    public HisPersbijhouding generateHisMaterieel(final Pers actueel, final boolean newCopy) {
        HisPersbijhouding his = new HisPersbijhouding();
        his.setPers(actueel.getId());
        his.setBijhaard(actueel.getBijhaard());
        his.setBijhpartij(actueel.getBijhpartij());
        his.setNaderebijhaard(actueel.getNaderebijhaard());
        his.setIndonverwdocaanw(actueel.getIndonverwdocaanw());
        return his;
    }

}
