/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractIndicatieEnNationaliteitSluitenElkaarUitRegelTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;

/**
 * Unit test voor de {@link BRAL2013} bedrijfsregel.
 */
public class BRAL2013Test extends AbstractIndicatieEnNationaliteitSluitenElkaarUitRegelTest {

    @Override
    protected AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel getRegelKlasse() {
        return new BRAL2013();
    }

    @Override
    protected Regel getVerwachtteRegel() {
        return Regel.BRAL2013;
    }

    @Override
    protected SoortIndicatie getIndicatie() {
        return SoortIndicatie.INDICATIE_STAATLOOS;
    }

}
