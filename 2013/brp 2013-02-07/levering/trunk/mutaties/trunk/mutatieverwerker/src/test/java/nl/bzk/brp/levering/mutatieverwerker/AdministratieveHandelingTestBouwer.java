/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker;

import java.util.Date;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ontleningstoelichting;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.test.util.ReflectionTestUtils;

public final class AdministratieveHandelingTestBouwer {

    private AdministratieveHandelingTestBouwer() {
    }

    public static AdministratieveHandelingModel getTestAdministratieveHandeling() {
        final Partij gemeente = getTestGemeente();

        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
                SoortAdministratieveHandeling.INSCHRIJVING_DOOR_GEBOORTE,
                gemeente,
                new DatumTijd(new Date()), new Ontleningstoelichting("Test"), new DatumTijd(new Date()));
        ReflectionTestUtils.setField(administratieveHandelingModel, "iD", 123456L);

        return administratieveHandelingModel;
    }

    private static Partij getTestGemeente() {
        return new Partij(new NaamEnumeratiewaarde("gem"), SoortPartij.GEMEENTE, new GemeenteCode((short) 34), null,
                null, null, null, null, StatusHistorie.A, null);
    }

}
