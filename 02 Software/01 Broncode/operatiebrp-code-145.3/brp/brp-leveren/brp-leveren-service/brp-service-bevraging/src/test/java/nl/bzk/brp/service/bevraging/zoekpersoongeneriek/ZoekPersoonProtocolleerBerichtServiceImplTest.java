/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import org.junit.Test;

/**
 * Unit test voor {@link ZoekPersoonProtocolleerBerichtServiceImpl}.
 */
public class ZoekPersoonProtocolleerBerichtServiceImplTest {

    private final ZoekPersoonProtocolleerBerichtServiceImpl<ZoekPersoonVerzoek> zoekPersoonProtocolleerBerichtService = new ZoekPersoonProtocolleerBerichtServiceImpl<>();
    private final AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();

    @Test
    public void bepaalDatumTijdEindeFormelePeriodeResultaat() throws Exception {
        final ZonedDateTime datumTijdKlaarzettenBericht = DatumUtil.nuAlsZonedDateTime();
        final ZonedDateTime datumTijdEindeFormelePeriodeResultaat = zoekPersoonProtocolleerBerichtService
                .bepaalDatumTijdEindeFormelePeriodeResultaat(bevragingVerzoek, datumTijdKlaarzettenBericht);

        assertThat(datumTijdEindeFormelePeriodeResultaat, is(datumTijdKlaarzettenBericht));
    }

    @Test
    public void bepaalDatumAanvangMaterielePeriodeResultaat() throws Exception {
        final Integer datumAanvangMaterielePeriodeResultaat = zoekPersoonProtocolleerBerichtService.bepaalDatumAanvangMaterielePeriodeResultaat(null);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumEindeMaterielePeriodeResultaat() throws Exception {
        final Integer datumEindeMaterielePeriodeResultaat = zoekPersoonProtocolleerBerichtService
                .bepaalDatumEindeMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumEindeMaterielePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaat() throws Exception {
        final ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = zoekPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        assertThat(datumTijdAanvangFormelePeriodeResultaat, is(nullValue()));
    }

}