/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Test;

/**
 * Unit test voor {@link GeefMedebewonersProtocolleerBerichtServiceImpl}.
 */
public class GeefMedebewonersProtocolleerBerichtServiceImplTest {

    private final GeefMedebewonersProtocolleerBerichtServiceImpl service = new GeefMedebewonersProtocolleerBerichtServiceImpl();
    private final GeefMedebewonersVerzoek verzoek = new GeefMedebewonersVerzoek();

    @Test
    public void vultDatumAanvangMaterielePeriodeResultaatMetPeilmomentMaterieelAlsAanwezig() {
        verzoek.getParameters().setPeilmomentMaterieel("2016-11-25");

        final Integer resultaat = service.bepaalDatumAanvangMaterielePeriodeResultaat(verzoek);

        assertThat(resultaat, is(20161125));
    }

    @Test
    public void vultDatumAanvangMaterielePeriodeResultaatMetVandaagAlsPeilmomentMaterieelNietAanwezig() {
        final Integer resultaat = service.bepaalDatumAanvangMaterielePeriodeResultaat(verzoek);

        assertThat(resultaat, is(DatumUtil.vandaag()));
    }

    @Test
    public void vultDatumEindeMaterielePeriodeResultaatMetPeilmomentMaterieelPlusEenDagAlsAanwezig() {
        verzoek.getParameters().setPeilmomentMaterieel("2016-11-25");

        final Integer resultaat = service.bepaalDatumEindeMaterielePeriodeResultaat(verzoek);

        assertThat(resultaat, is(20161126));
    }

    @Test
    public void vultDatumEindeMaterielePeriodeResultaatMetVandaagPlusEenDagAlsPeilmomentMaterieelNietAanwezig() {
        final Integer resultaat = service.bepaalDatumEindeMaterielePeriodeResultaat(verzoek);

        assertThat(resultaat, is(DatumUtil.datumRondVandaag(-1)));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaat() throws Exception {
        final ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = service
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(verzoek);

        assertThat(datumTijdAanvangFormelePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumTijdEindeFormelePeriodeResultaat() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = service
                .bepaalDatumTijdEindeFormelePeriodeResultaat(verzoek, now);

        assertThat(datumTijdAanvangFormelePeriodeResultaat, is(now));
    }
}
