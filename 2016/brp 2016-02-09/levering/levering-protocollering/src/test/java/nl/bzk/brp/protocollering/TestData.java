/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;


public final class TestData {

    private TestData() {
    }

    public static ProtocolleringOpdracht geefTestProtocolleringOpdracht() {
        final Integer toegangLeveringsautorisatieId = 1;
        final Integer dienstId = 2;
        final DatumTijdAttribuut datumTijdKlaarzettenLevering = DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1, 1, 1, 1);
        final DatumTijdAttribuut peilmomentFormeelResultaat = DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1, 1, 1, 1);
        final DatumAttribuut peilmomentMaterieelResultaat = DatumTijdAttribuut.bouwDatumTijd(2012, 0, 0).naarDatum();
        final Long administratieveHandelingId = 24L;
        final SoortSynchronisatieAttribuut soortSynchronisatie =
                new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT);

        final LeveringModel levering = new LeveringModel(toegangLeveringsautorisatieId, dienstId,
                datumTijdKlaarzettenLevering, null,
                peilmomentMaterieelResultaat, peilmomentMaterieelResultaat,
                peilmomentFormeelResultaat, peilmomentFormeelResultaat,
                administratieveHandelingId, soortSynchronisatie);

        final LeveringPersoonModel leveringPersoon1 = new LeveringPersoonModel(levering, 1);
        final LeveringPersoonModel leveringPersoon2 = new LeveringPersoonModel(levering, 2);
        final Set<LeveringPersoonModel> leveringPersonen = new HashSet<>();
        leveringPersonen.add(leveringPersoon1);
        leveringPersonen.add(leveringPersoon2);

        return new ProtocolleringOpdracht(levering, leveringPersonen);
    }

}
