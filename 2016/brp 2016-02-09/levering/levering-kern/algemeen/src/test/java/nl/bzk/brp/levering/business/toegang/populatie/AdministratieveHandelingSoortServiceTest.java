/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Test;


public class AdministratieveHandelingSoortServiceTest {

    private final AdministratieveHandelingSoortService administratieveHandelingSoortService =
        new AdministratieveHandelingSoortServiceImpl();

    @Test
    public final void testIsPlaatsingAfnemerIndicatieJa() {
        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE),
            null, null, null);

        final boolean resultaat =
            administratieveHandelingSoortService.isPlaatsingAfnemerIndicatie(administratieveHandelingModel);

        assertTrue(resultaat);
    }

    @Test
    public final void testIsPlaatsingAfnemerIndicatieNee() {
        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT),
                null, null, null);

        final boolean resultaat =
                administratieveHandelingSoortService.isPlaatsingAfnemerIndicatie(administratieveHandelingModel);

        assertFalse(resultaat);
    }

    @Test
    public final void isVerwijderingAfnemerIndicatie() {
        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE),
                null, null, null);

        final boolean resultaat =
                administratieveHandelingSoortService.isVerwijderingAfnemerIndicatie(administratieveHandelingModel);

        assertTrue(resultaat);
    }

    @Test
    public final void isVerwijderingAfnemerIndicatieNee() {
        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT),
                null, null, null);

        final boolean resultaat =
                administratieveHandelingSoortService.isVerwijderingAfnemerIndicatie(administratieveHandelingModel);

        assertFalse(resultaat);
    }

}
