/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.test.util.ReflectionTestUtils;

public final class AdministratieveHandelingTestBouwer {

    private AdministratieveHandelingTestBouwer() {
    }

    public static AdministratieveHandelingModel getTestAdministratieveHandeling() {
        final Partij gemeente = TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(34).maak();

        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
                new PartijAttribuut(gemeente),
                new OntleningstoelichtingAttribuut("Test"), DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1));
        ReflectionTestUtils.setField(administratieveHandelingModel, "iD", 123456L);

        return administratieveHandelingModel;
    }
}
