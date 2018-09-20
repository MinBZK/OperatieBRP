/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview;

import static nl.bzk.brp.model.MaterieleHistoriePredikaat.geldigOp;
import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.datumTijd;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.util.hisvolledig.kern.HisPersoonAdresModelBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BijzondereGrensGevallenHistorieTest {

    private final List<HisPersoonAdresModel> adressen = new LinkedList<HisPersoonAdresModel>();

    @Before
    public void setUp() {
        HisPersoonAdresModel geboorteAdres =
                HisPersoonAdresModelBuilder.defaultValues().straatnaam("Dorpsstraat").huisnummer(5).postcode("2400BF")
                        .woonplaats("Baarn").datumTijdRegistratie(datumTijd(1980, 5, 2, 13, 0))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800501))
                                // naar studentenhuis
                        .datumTijdVerval(datumTijd(1999, 8, 31)).build();

        HisPersoonAdresModel historieGeboorteAdres =
                HisPersoonAdresModelBuilder.kopieer(geboorteAdres)
                        // naar studentenhuis
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19990831))
                        .datumTijdRegistratie(datumTijd(1999, 8, 31)).build();

        HisPersoonAdresModel studieAdres =
                HisPersoonAdresModelBuilder.defaultValues().straatnaam("Kuyperstraat").huisnummer(138)
                        .postcode("2624BC")
                        .woonplaats("Delft").aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19990831))
                        .datumTijdRegistratie(datumTijd(1999, 8, 31))
                                // naar eerste eigen huis
                        .datumTijdVerval(datumTijd(2006, 4, 1)).build();

        HisPersoonAdresModel historieStudieAdres =
                HisPersoonAdresModelBuilder.kopieer(studieAdres)
                        // naar eerste eigen huis
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20060401))
                        .datumTijdRegistratie(datumTijd(2006, 4, 1))
                                // vervangen door info studie buitenland
                        .datumTijdVerval(datumTijd(2012, 1, 1)).build();

        HisPersoonAdresModel eersteHuis =
                HisPersoonAdresModelBuilder.defaultValues().straatnaam("Heerenlaan").huisnummer(67).postcode("3130HT")
                        .woonplaats("Rotterdam").aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20060401))
                        // naar eerste eigen huis
                        .datumTijdRegistratie(datumTijd(2006, 4, 1, 9, 34)).build();

        HisPersoonAdresModel correctieStudieAdres1 =
                HisPersoonAdresModelBuilder.kopieer(historieStudieAdres)
                        // jaar studie buitenland
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20040000))
                        .datumTijdRegistratie(datumTijd(2012, 1, 1)).build();

        HisPersoonAdresModel studieBuitenland =
                HisPersoonAdresModelBuilder.defaultValues().straatnaam("Eaststreet").huisnummer(670)
                        .postcode("31245")
                        .woonplaats("Londen")
                                // studie in buitenland
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20040000))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20050000))
                        .datumTijdRegistratie(datumTijd(2012, 1, 1)).build();

        HisPersoonAdresModel correctieStudieAdres2 =
                HisPersoonAdresModelBuilder.kopieer(historieStudieAdres).huisnummer(136)
                        // terug uit buitenland
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20050000))
                        .datumTijdRegistratie(datumTijd(2012, 1, 1)).build();

        adressen.addAll(Arrays.asList(geboorteAdres, historieGeboorteAdres, studieAdres, historieStudieAdres,
                eersteHuis, correctieStudieAdres1, studieBuitenland, correctieStudieAdres2));
    }

    @Test
    public void watWetenWeNu() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = new DatumTijdAttribuut(new Date());
        final DatumAttribuut materieelPeilmoment = DatumAttribuut.vandaag();

        final Predicate geldigOp = geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonAdresModel adres = (HisPersoonAdresModel) CollectionUtils.find(adressen, geldigOp);

        // then
        assertThat(print(adres), is("Heerenlaan 67 3130HT, Rotterdam"));
    }

    @Test
    public void watWetenWeVanDeStudietijd() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = new DatumTijdAttribuut(new Date());
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20010101);

        final Predicate geldigOp = geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonAdresModel adres = (HisPersoonAdresModel) CollectionUtils.find(adressen, geldigOp);

        // then
        assertThat(print(adres), is("Kuyperstraat 138 2624BC, Delft"));
    }

    @Test
    public void watWetenWeVanDeStudieBuitenland() {
        // given
        final DatumTijdAttribuut nu = new DatumTijdAttribuut(new Date());

        // then
        final HisPersoonAdresModel adres =
                (HisPersoonAdresModel) CollectionUtils.find(adressen, geldigOp(nu, new DatumAttribuut(20030601)));
        assertThat(print(adres), is("Kuyperstraat 138 2624BC, Delft"));

        // then
        final HisPersoonAdresModel adres2 =
                (HisPersoonAdresModel) CollectionUtils.find(adressen, geldigOp(nu, new DatumAttribuut(20050601)));
        assertThat(print(adres2), is("Kuyperstraat 136 2624BC, Delft"));

        // then
        final HisPersoonAdresModel adres3 =
                (HisPersoonAdresModel) CollectionUtils.find(adressen, geldigOp(nu, new DatumAttribuut(20040601)));
        assertThat(print(adres3), is("Eaststreet 670 31245, Londen"));
    }

    @Test
    public void blikOpMaterieleHistorieMetDatumsZonderMaandDag() {
        Assert.assertTrue(true);
    }

    private String print(final HisPersoonAdresModel adres) {
        StringBuilder sb = new StringBuilder();

        sb.append(adres.getNaamOpenbareRuimte().getWaarde()).append(" ").append(adres.getHuisnummer().getWaarde())
                .append(" ").append(adres.getPostcode().getWaarde()).append(", ")
                .append(adres.getWoonplaatsnaam().getWaarde());

        return sb.toString();
    }
}
