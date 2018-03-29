/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;


public final class TestData {

    private TestData() {
    }

    public static ProtocolleringOpdracht geefTestProtocolleringOpdracht(final ZonedDateTime datumAanvangFormeelResultaat,
                                                                        final List<LeveringPersoon> leveringPersonen) {
        final Integer toegangLeveringsautorisatieId = 1;
        final Integer dienstId = 2;
        final ZonedDateTime datumTijdKlaarzettenLevering = LocalDate.of(2010, 1, 1).atTime(1, 1, 1).atZone(DatumUtil.BRP_ZONE_ID);
        final ZonedDateTime datumTijdEindFormelePeriodeResultaat = LocalDate.of(2000, 1, 1).atTime(1, 1, 1).atZone(DatumUtil.BRP_ZONE_ID);
        final Integer peilmomentMaterieelResultaat = 20120000;
        final Long administratieveHandelingId = 24L;
        final SoortSynchronisatie soortSynchronisatie = SoortSynchronisatie.VOLLEDIG_BERICHT;

        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setToegangLeveringsautorisatieId(toegangLeveringsautorisatieId);
        protocolleringOpdracht.setDienstId(dienstId);
        protocolleringOpdracht.setDatumTijdKlaarzettenLevering(datumTijdKlaarzettenLevering);
        protocolleringOpdracht.setDatumAanvangMaterielePeriodeResultaat(peilmomentMaterieelResultaat);
        protocolleringOpdracht.setDatumEindeMaterielePeriodeResultaat(peilmomentMaterieelResultaat);
        protocolleringOpdracht.setDatumTijdAanvangFormelePeriodeResultaat(datumAanvangFormeelResultaat);
        protocolleringOpdracht.setDatumTijdEindeFormelePeriodeResultaat(datumTijdEindFormelePeriodeResultaat);
        protocolleringOpdracht.setSoortSynchronisatie(soortSynchronisatie);
        protocolleringOpdracht.setAdministratieveHandelingId(administratieveHandelingId);
        protocolleringOpdracht.setGeleverdePersonen(leveringPersonen);
        return protocolleringOpdracht;
    }

    public static List<LeveringPersoon> geefTestLeveringPersoon() {
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final LeveringPersoon leveringPersoon1 = new LeveringPersoon(1L, nu);
        final LeveringPersoon leveringPersoon2 = new LeveringPersoon(2L, nu);
        final List<LeveringPersoon> leveringPersonen = new ArrayList<>();
        leveringPersonen.add(leveringPersoon1);
        leveringPersonen.add(leveringPersoon2);
        return leveringPersonen;
    }
}
