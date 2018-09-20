/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.SoortSynchronisatieBepaler;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public final class SoortSynchronisatieBepalerImpl implements SoortSynchronisatieBepaler {

    private static final List<SoortAdministratieveHandeling> HANDELINGEN_MET_VOLLEDIGBERICHT =
        Arrays.asList(SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_OVERIG);

    @Override
    public SoortSynchronisatie bepaalSoortSynchronisatie(final Populatie populatie, final SoortDienst soortDienst,
        final SoortAdministratieveHandeling soortAdmHandeling)
    {
        if (HANDELINGEN_MET_VOLLEDIGBERICHT.contains(soortAdmHandeling)
            || SoortDienst.ATTENDERING.equals(soortDienst)
            || populatie == null
            || Populatie.BETREEDT.equals(populatie))
        {
            return SoortSynchronisatie.VOLLEDIGBERICHT;
        } else {
            return SoortSynchronisatie.MUTATIEBERICHT;
        }
    }

    @Override
    public List<SoortAdministratieveHandeling> geefHandelingenMetVolledigBericht() {
        return HANDELINGEN_MET_VOLLEDIGBERICHT;
    }
}
