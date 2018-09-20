/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.Voornaam;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 */
@ContextConfiguration("classpath:test-context.xml")
public class PersoonModelMapperTest extends AbstractJUnit4SpringContextTests {

    private JdbcTemplate jdbcTemplate;

    private PersoonModelMapper mapper = new PersoonModelMapper();
    String SQL = "SELECT\n" +
            "  pred.id AS predikaat_persoon_id, pred.code AS predikaat_persoon_code, pred.naammannelijk AS predikaat_persoon_naammannelijk, pred.naamvrouwelijk AS predikaat_persoon_naamvrouwelijk,\n" +
            "  adel.id AS titel_persoon_id, adel.code AS titel_persoon_code, adel.naammannelijk AS titel_persoon_naammannelijk, adel.naamvrouwelijk AS titel_persoon_naamvrouwelijk,\n" +
            "\n" +
            "  gbl.id AS land_geboorte_id, gbl.code AS land_geboorte_code, gbl.naam AS land_geboorte_naam, gbl.iso31661alpha2 AS land_geboorte_iso31661alpha2, gbl.dataanvgel AS land_geboorte_dataanvgel, gbl.dateindegel AS land_geboorte_dateindegel,\n" +
            "  gbp.id AS plaats_geboorte_id, gbp.code AS plaats_geboorte_code, gbp.naam AS plaats_geboorte_naam, gbp.dataanvgel AS plaats_geboorte_dataanvgel, gbp.dateindegel AS plaats_geboorte_dateindegel,\n" +
            "  gbpa.id AS partij_geboorte_id, gbpa.naam AS partij_geboorte_naam, gbpa.srt AS partij_geboorte_srt, gbpa.code AS partij_geboorte_code, gbpa.voortzettendegem AS partij_geboorte_voortzettendegem, gbpa.onderdeelvan AS partij_geboorte_onderdeelvan, gbpa.gemstatushis AS partij_geboorte_gemstatushis, gbpa.dateinde AS partij_geboorte_dateinde, gbpa.dataanv AS partij_geboorte_dataanv, gbpa.sector AS partij_geboorte_sector, gbpa.partijstatushis AS partij_geboorte_partijstatushis,\n" +
            "\n" +
            "  aapr.id AS predikaat_aanschrijving_id, aapr.code AS predikaat_aanschrijving_code, aapr.naammannelijk AS predikaat_aanschrijving_naammannelijk, aapr.naamvrouwelijk AS predikaat_aanschrijving_naamvrouwelijk,\n" +
            "  aaat.id AS titel_aanschrijving_id, aaat.code AS titel_aanschrijving_code, aaat.naammannelijk AS titel_aanschrijving_naammannelijk, aaat.naamvrouwelijk AS titel_aanschrijving_naamvrouwelijk,\n" +
            "\n" +
            "  bhpa.id AS partij_bijhouding_id, bhpa.naam AS partij_bijhouding_naam, bhpa.srt AS partij_bijhouding_srt, bhpa.code AS partij_bijhouding_code, bhpa.voortzettendegem AS partij_bijhouding_voortzettendegem, bhpa.onderdeelvan AS partij_bijhouding_onderdeelvan, bhpa.gemstatushis AS partij_bijhouding_gemstatushis, bhpa.dateinde AS partij_bijhouding_dateinde, bhpa.dataanv AS partij_bijhouding_dataanv, bhpa.sector AS partij_bijhouding_sector, bhpa.partijstatushis AS partij_bijhouding_partijstatushis,\n" +
            "\n" +
            "  ovl.id AS land_overlijden_id, ovl.code AS land_overlijden_code, ovl.naam AS land_overlijden_naam, ovl.iso31661alpha2 AS land_overlijden_iso31661alpha2, ovl.dataanvgel AS land_overlijden_dataanvgel, ovl.dateindegel AS land_overlijden_dateindegel,\n" +
            "  ovp.id AS plaats_overlijden_id, ovp.code AS plaats_overlijden_code, ovp.naam AS plaats_overlijden_naam, ovp.dataanvgel AS plaats_overlijden_dataanvgel, ovp.dateindegel AS plaats_overlijden_dateindegel,\n" +
            "  ovpa.id AS partij_overlijden_id, ovpa.naam AS partij_overlijden_naam, ovpa.srt AS partij_overlijden_srt, ovpa.code AS partij_overlijden_code, ovpa.voortzettendegem AS partij_overlijden_voortzettendegem, ovpa.onderdeelvan AS partij_overlijden_onderdeelvan, ovpa.gemstatushis AS partij_overlijden_gemstatushis, ovpa.dateinde AS partij_overlijden_dateinde, ovpa.dataanv AS partij_overlijden_dataanv, ovpa.sector AS partij_overlijden_sector, ovpa.partijstatushis AS partij_overlijden_partijstatushis,\n" +
            "\n" +
            "  vblr.id AS verblijfsrecht_id, vblr.oms AS verblijfsrecht_oms, vblr.dataanvgel AS verblijfsrecht_dataanvgel, vblr.dateindegel AS verblijfsrecht_dateindegel,\n" +
            "\n" +
            "  iml.id AS land_immigratie_id, iml.code AS land_immigratie_code, iml.naam AS land_immigratie_naam, iml.iso31661alpha2 AS land_immigratie_iso31661alpha2, iml.dataanvgel AS land_immigratie_dataanvgel, iml.dateindegel AS land_immigratie_dateindegel,\n" +
            "  pkp.id AS partij_persoonskaart_id, pkp.naam AS partij_persoonskaart_naam, pkp.srt AS partij_persoonskaart_srt, pkp.code AS partij_persoonskaart_code, pkp.voortzettendegem AS partij_persoonskaart_voortzettendegem, pkp.onderdeelvan AS partij_persoonskaart_onderdeelvan, pkp.gemstatushis AS partij_persoonskaart_gemstatushis, pkp.dateinde AS partij_persoonskaart_dateinde, pkp.dataanv AS partij_persoonskaart_dataanv, pkp.sector AS partij_persoonskaart_sector, pkp.partijstatushis AS partij_persoonskaart_partijstatushis,\n" +
            "\n" +
            "  kp.*\n" +
            "FROM kern.pers kp\n" +
            "LEFT JOIN kern.predikaat pred ON pred.id = kp.predikaat\n" +
            "LEFT JOIN kern.adellijketitel adel ON adel.id = kp.adellijketitel\n" +
            "LEFT JOIN kern.partij gbpa ON gbpa.id = kp.gemgeboorte\n" +
            "LEFT JOIN kern.plaats gbp ON gbp.id = kp.wplGeboorte\n" +
            "LEFT JOIN kern.land gbl ON gbl.id = kp.landgeboorte\n" +
            "LEFT JOIN kern.predikaat aapr ON aapr.id = kp.predikaataanschr\n" +
            "LEFT JOIN kern.adellijketitel aaat ON aaat.id = kp.adellijketitelaanschr\n" +
            "LEFT JOIN kern.partij bhpa ON bhpa.id = kp.bijhgem\n" +
            "LEFT JOIN kern.partij ovpa ON ovpa.id = kp.gemoverlijden\n" +
            "LEFT JOIN kern.plaats ovp ON ovp.id = kp.wploverlijden\n" +
            "LEFT JOIN kern.land ovl ON ovl.id = kp.landoverlijden\n" +
            "LEFT JOIN kern.verblijfsr vblr ON vblr.id = kp.verblijfsr\n" +
            "LEFT JOIN kern.land iml ON iml.id = kp.landvanwaargevestigd\n" +
            "LEFT JOIN kern.partij pkp ON pkp.id = kp.gempk\n" +
            "WHERE kp.bsn = ?";

    @Inject
    public void setDataSource(DataSource source) {
        this.jdbcTemplate = new JdbcTemplate(source);
    }

    @Test
    public void mappingShouldBeSuccessful() throws Exception {
        // given
        Integer bsn = 123456789;

        // when
        PersoonModel result = jdbcTemplate.queryForObject(SQL, mapper, bsn);

        //then
        assertThat(result, notNullValue());
        assertThat(result.getGeboorte().getGemeenteGeboorte().getNaam(), equalTo(new Naam("Almere")));
        assertThat(result.getGeboorte().getDatumGeboorte(), equalTo(new Datum(18890426)));
//        assertThat(result.getGeboorte().getLandGeboorte().getNaam(), equalTo(new Naam("Nederland")));

        assertThat(result.getGeslachtsaanduiding().getGeslachtsaanduiding(), equalTo(Geslachtsaanduiding.MAN));

        assertThat(result.getSamengesteldeNaam().getAdellijkeTitel().getNaamMannelijk(), equalTo(new Naam("prins")));
        assertThat(result.getSamengesteldeNaam().getPredikaat(), not(nullValue()));
        assertThat(result.getSamengesteldeNaam().getGeslachtsnaam(),
                   equalTo(new Geslachtsnaamcomponent("Wittgenstein")));

        assertThat(result.getAanschrijving().getGeslachtsnaam(),
                   equalTo(new Geslachtsnaamcomponent("Wittgensteintje")));
        assertThat(result.getAanschrijving().getVoornamen(), equalTo(new Voornaam("Teun")));

        assertThat(result.getBijhoudingsgemeente().getBijhoudingsgemeente().getNaam(), equalTo(new Naam("Almere")));
    }

}
