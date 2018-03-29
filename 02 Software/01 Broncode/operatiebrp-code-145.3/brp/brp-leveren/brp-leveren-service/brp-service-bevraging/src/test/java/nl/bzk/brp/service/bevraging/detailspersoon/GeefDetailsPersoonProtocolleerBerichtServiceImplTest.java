/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import org.junit.Test;

/**
 * Unit test voor {@link GeefDetailsPersoonProtocolleerBerichtServiceImpl}.
 */
public class GeefDetailsPersoonProtocolleerBerichtServiceImplTest {

    private final GeefDetailsPersoonProtocolleerBerichtServiceImpl geefDetailsPersoonProtocolleerBerichtService
            = new GeefDetailsPersoonProtocolleerBerichtServiceImpl();
    private final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();

    @Test
    public void testScopingParameters() throws Exception {
        bevragingVerzoek.getScopingElementen().getElementen().add("Persoon.Adres.Huisnummer");
        bevragingVerzoek.getScopingElementen().getElementen().add("Persoon.Adres.Woonplaatsnaam");
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();

        geefDetailsPersoonProtocolleerBerichtService.vulScopingParameters(bevragingVerzoek, protocolleringOpdracht);

        assertThat(protocolleringOpdracht.getScopingAttributen(), containsInAnyOrder(3271, 3282));
    }

    @Test
    public void testLegeScopingParameters() throws Exception {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();

        geefDetailsPersoonProtocolleerBerichtService.vulScopingParameters(bevragingVerzoek, protocolleringOpdracht);

        assertThat(protocolleringOpdracht.getScopingAttributen(), is((nullValue())));
    }

    @Test
    public void bepaalDatumAanvangMaterielePeriodeResultaatBijHistorieVormGeen() throws Exception {
        bevragingVerzoek.getParameters().setPeilMomentMaterieelResultaat("2016-08-19");
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.GEEN);

        final Integer datumAanvangMaterielePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumAanvangMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(20160819));
    }

    @Test
    public void bepaalDatumAanvangMaterielePeriodeResultaatBijHistorieVormGeenEnLeegPeilMoment() throws Exception {
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.GEEN);

        final Integer datumAanvangMaterielePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumAanvangMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(DatumUtil.vandaag()));
    }

    @Test
    public void bepaalDatumAanvangMaterielePeriodeResultaatBijAndereHistorieVorm() throws Exception {
        bevragingVerzoek.getParameters().setPeilMomentMaterieelResultaat("2016-08-19");
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.MATERIEEL_FORMEEL);

        final Integer datumAanvangMaterielePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumAanvangMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumAanvangMaterielePeriodeResultaatBijOntbrekendeHistorieParameters() throws Exception {
        final Integer datumAanvangMaterielePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumAanvangMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumEindeMaterielePeriodeResultaat() throws Exception {
        bevragingVerzoek.getParameters().setPeilMomentMaterieelResultaat("2016-08-19");

        final Integer datumAanvangMaterielePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumEindeMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(20160820));
    }

    @Test
    public void bepaalDatumEindeMaterielePeriodeResultaatBijOntbrekendeHistorieParameters() throws Exception {
        final Integer datumAanvangMaterielePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumEindeMaterielePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangMaterielePeriodeResultaat, is(DatumUtil.datumRondVandaag(-1)));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaatBijHistorieVormGeen() throws Exception {
        final ZonedDateTime peilMomentFormeelResultaat = ZonedDateTime.of(2016, 8, 19, 11, 45, 0, 0, DatumUtil.BRP_ZONE_ID);
        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat(peilMomentFormeelResultaat.format(DateTimeFormatter.ISO_DATE_TIME));
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.GEEN);

        final ZonedDateTime datumAanvangFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangFormelePeriodeResultaat, is(peilMomentFormeelResultaat));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaatBijHistorieVormMaterieel() throws Exception {
        final ZonedDateTime peilMomentFormeelResultaat = ZonedDateTime.of(2016, 8, 19, 11, 45, 0, 0, DatumUtil.BRP_ZONE_ID);

        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat(peilMomentFormeelResultaat.format(DateTimeFormatter.ISO_DATE_TIME));
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.MATERIEEL);

        final ZonedDateTime datumAanvangFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangFormelePeriodeResultaat, is(peilMomentFormeelResultaat));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaatBijHistorieVormGeenGeenPeilmoment() throws Exception {
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.GEEN);

        final ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTimeInNederland();
        assertThat(datumTijdAanvangFormelePeriodeResultaat.isEqual(nu) || datumTijdAanvangFormelePeriodeResultaat.isBefore(nu), is(true));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaatBijHistorieVormMaterieelGeenPeilmoment() throws Exception {
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.MATERIEEL);

        final ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTimeInNederland();
        assertThat(datumTijdAanvangFormelePeriodeResultaat.isEqual(nu) || datumTijdAanvangFormelePeriodeResultaat.isBefore(nu), is(true));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaatBijAndereHistorieVorm() throws Exception {
        bevragingVerzoek.getParameters().setHistorieVorm(HistorieVorm.MATERIEEL_FORMEEL);

        final ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTimeInNederland();
        assertThat(datumTijdAanvangFormelePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumTijdAanvangFormelePeriodeResultaatBijOntbrekendeHistorieParameters() throws Exception {
        final ZonedDateTime datumAanvangFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdAanvangFormelePeriodeResultaat(bevragingVerzoek);

        assertThat(datumAanvangFormelePeriodeResultaat, is(nullValue()));
    }

    @Test
    public void bepaalDatumTijdEindeFormelePeriodeResultaatBijAanwezigeHistorieParameters() throws Exception {
        final ZonedDateTime peilMomentFormeelResultaat = ZonedDateTime.of(2016, 8, 19, 11, 45, 0, 0, DatumUtil.BRP_ZONE_ID);

        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat(peilMomentFormeelResultaat.format(DateTimeFormatter.ISO_DATE_TIME));
        final ZonedDateTime datumTijdKlaarzettenBericht = DatumUtil.nuAlsZonedDateTime();
        final ZonedDateTime datumTijdEindeFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdEindeFormelePeriodeResultaat(bevragingVerzoek, datumTijdKlaarzettenBericht);

        assertThat(datumTijdEindeFormelePeriodeResultaat, is(peilMomentFormeelResultaat));
    }

    @Test
    public void bepaalDatumTijdEindeFormelePeriodeResultaatBijOntbrekendeHistorieParameters() throws Exception {
        final ZonedDateTime datumTijdKlaarzettenBericht = DatumUtil.nuAlsZonedDateTime();
        final ZonedDateTime datumTijdEindeFormelePeriodeResultaat = geefDetailsPersoonProtocolleerBerichtService
                .bepaalDatumTijdEindeFormelePeriodeResultaat(bevragingVerzoek, datumTijdKlaarzettenBericht);

        assertThat(datumTijdEindeFormelePeriodeResultaat, is(datumTijdKlaarzettenBericht));
    }
}
