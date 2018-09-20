/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.brp.bevraging.mapping.BetrokkenheidModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonAdresHisModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonAdresModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonGeslachtsnaamcomponentGroepModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonIndicatieModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonNationaliteitModelMapper;
import nl.bzk.brp.bevraging.mapping.PersoonVoornaamModelMapper;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.copy.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.copy.model.objecttype.operationeel.RelatieModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class PersoonsLijstJdbcServiceImpl implements PersoonsLijstJdbcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonsLijstJdbcServiceImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Inject
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<nl.bzk.copy.model.objecttype.operationeel.PersoonModel> mapper = new PersoonModelMapper();

    private static final String QUERY_GET_PERSOON = "SELECT \n" +
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

    private static final String QUERY_GET_GERELATEERDE = "SELECT \n" +
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
            "LEFT JOIN kern.betr b ON b.pers = kp.id\n" +
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
            "WHERE b.id = ?";

    private final String QUERY_GET_VOORNAAM = "SELECT\n" +
            "  *\n" +
            "FROM kern.persvoornaam kpv\n" +
            "WHERE kpv.pers = ?";

    private static final String QUERY_GET_ADRESSEN = "SELECT\n" +
            "  l.id AS land_id, l.code AS land_code, l.naam AS land_naam, l.iso31661alpha2 AS land_iso31661alpha2, l.dataanvgel AS land_dataanvgel, l.dateindegel AS land_dateindegel,\n" +
            "  p.id AS plaats_id, p.code AS plaats_code, p.naam AS plaats_naam, p.dataanvgel AS plaats_dataanvgel, p.dateindegel AS plaats_dateindegel,\n" +
            "  pa.id AS partij_id, pa.naam AS partij_naam, pa.srt AS partij_srt, pa.code AS partij_code, pa.voortzettendegem AS partij_voortzettendegem, pa.onderdeelvan AS partij_onderdeelvan, pa.gemstatushis AS partij_gemstatushis, pa.dateinde AS partij_dateinde, pa.dataanv AS partij_dataanv, pa.sector AS partij_sector, pa.partijstatushis AS partij_partijstatushis,\n" +
            "  rdn.id AS reden_id, rdn.code AS reden_code, rdn.naam AS reden_naam,\n" +
            "  kpa.*\n" +
            "FROM kern.persadres kpa\n" +
            "LEFT JOIN kern.partij pa ON pa.id = kpa.gem\n" +
            "LEFT JOIN kern.plaats p ON p.id = kpa.wpl\n" +
            "LEFT JOIN kern.land l ON l.id = kpa.land\n" +
            "LEFT JOIN kern.rdnwijzadres rdn ON rdn.id = kpa.rdnwijz\n" +
            "WHERE kpa.pers = ?";

    private static final String QUERY_GET_ADRES_HISTORIE = "SELECT\n" +
            "  l.id AS land_id, l.code AS land_code, l.naam AS land_naam, l.iso31661alpha2 AS land_iso31661alpha2, l.dataanvgel AS land_dataanvgel, l.dateindegel AS land_dateindegel,\n" +
            "  p.id AS plaats_id, p.code AS plaats_code, p.naam AS plaats_naam, p.dataanvgel AS plaats_dataanvgel, p.dateindegel AS plaats_dateindegel,\n" +
            "  pa.id AS partij_id, pa.naam AS partij_naam, pa.srt AS partij_srt, pa.code AS partij_code, pa.voortzettendegem AS partij_voortzettendegem, pa.onderdeelvan AS partij_onderdeelvan, pa.gemstatushis AS partij_gemstatushis, pa.dateinde AS partij_dateinde, pa.dataanv AS partij_dataanv, pa.sector AS partij_sector, pa.partijstatushis AS partij_partijstatushis,\n" +
            "  rdn.id AS reden_id, rdn.code AS reden_code, rdn.naam AS reden_naam,\n" +
            "  act.id AS actieaanpgel_id, act.tijdstipontlening AS actieaanpgel_tijdstipontlening, act.tijdstipreg AS actieaanpgel_tijdstipreg, act.srt AS actieaanpgel_srt,\n" +
            "  partij.id AS actieaanpgel_partij_id, partij.naam AS actieaanpgel_partij_naam, partij.dataanv AS actieaanpgel_partij_dataanv, partij.dateinde AS actieaanpgel_partij_dateinde, partij.code AS actieaanpgel_partij_code,\n" +
            "  actieinh.id AS actieinh_id, actieinh.tijdstipontlening AS actieinh_tijdstipontlening, actieinh.tijdstipreg AS actieinh_tijdstipreg, actieinh.srt AS actieinh_srt,\n" +
            "  actieinh_partij.id AS actieinh_partij_id, actieinh_partij.naam AS actieinh_partij_naam, actieinh_partij.dataanv AS actieinh_partij_dataanv, actieinh_partij.dateinde AS actieinh_partij_dateinde, actieinh_partij.code AS actieinh_partij_code,\n" +
            "  actieverval.id AS actieverval_id, actieverval.tijdstipontlening AS actieverval_tijdstipontlening, actieverval.tijdstipreg AS actieverval_tijdstipreg, actieverval.srt AS actieverval_srt,\n" +
            "  actieverval_partij.id AS actieverval_partij_id, actieverval_partij.naam AS actieverval_partij_naam, actieverval_partij.dataanv AS actieverval_partij_dataanv, actieverval_partij.dateinde AS actieverval_partij_dateinde, actieverval_partij.code AS actieverval_partij_code,\n" +
            "  khpa.*\n" +
            "FROM kern.his_persadres khpa\n" +
            "LEFT JOIN kern.partij pa ON pa.id = khpa.gem\n" +
            "LEFT JOIN kern.plaats p ON p.id = khpa.wpl\n" +
            "LEFT JOIN kern.land l ON l.id = khpa.land\n" +
            "LEFT JOIN kern.rdnwijzadres rdn ON rdn.id = khpa.rdnwijz\n" +
            "LEFT JOIN kern.actie act ON act.id = khpa.actieaanpgel\n" +
            "LEFT JOIN kern.Partij partij ON partij.id = act.partij\n" +
            "LEFT JOIN kern.actie actieinh ON actieinh.id = khpa.actieinh\n" +
            "LEFT JOIN kern.Partij actieinh_partij ON actieinh_partij.id = actieinh.partij\n" +
            "LEFT JOIN kern.actie actieverval ON actieverval.id = khpa.actieverval\n" +
            "LEFT JOIN kern.Partij actieverval_partij ON actieverval_partij.id = actieverval.partij\n" +
            "WHERE khpa.PersAdres = ?";

    private static final String QUERY_GET_GESLACHTSNAAM = "SELECT\n" +
            "  pred.id AS predikaat_geslnaamcomp_id, pred.code AS predikaat_geslnaamcomp_code, pred.naammannelijk AS predikaat_geslnaamcomp_naammannelijk, pred.naamvrouwelijk AS predikaat_geslnaamcomp_naamvrouwelijk,\n" +
            "  adel.id AS titel_geslnaamcomp_id, adel.code AS titel_geslnaamcomp_code, adel.naammannelijk AS titel_geslnaamcomp_naammannelijk, adel.naamvrouwelijk AS titel_geslnaamcomp_naamvrouwelijk,\n" +
            "  kpg.*\n" +
            "FROM kern.persgeslnaamcomp kpg\n" +
            "LEFT JOIN kern.predikaat pred ON pred.id = kpg.predikaat\n" +
            "LEFT JOIN kern.adellijketitel adel ON adel.id = kpg.adellijketitel\n" +
            "WHERE kpg.pers = ?;";

    private static final String QUERY_GET_NATIONALITEITEN = "SELECT\n" +
            "  nat.id AS nation_id, nat.naam AS nation_naam, nat.nationcode AS nation_nationcode,\n" +
            "  verk.id AS verkrijgen_id, verk.code AS verkrijgen_code, verk.oms AS verkrijgen_oms,\n" +
            "  verl.id AS verlies_id, verl.oms AS verlies_oms,\n" +
            "  kpn.*\n" +
            "FROM kern.persnation kpn\n" +
            "LEFT JOIN kern.nation nat ON nat.id = kpn.nation\n" +
            "LEFT JOIN kern.rdnverknlnation verk ON verk.id = kpn.rdnverk\n" +
            "LEFT JOIN kern.rdnverliesnlnation verl ON verl.id = kpn.rdnverlies\n" +
            "WHERE kpn.pers = ?;";

    private static final String QUERY_GET_INDICATIE = "SELECT\n" +
            "  *\n" +
            "FROM kern.persindicatie kpi\n" +
            "WHERE kpi.pers = ?";

    private static final String QUERY_GET_BETROKKENHEDEN = "SELECT \n" +
            "  aala.id AS land_aanvang_id, aala.code AS land_aanvang_code, aala.naam AS land_aanvang_naam, aala.iso31661alpha2 AS land_aanvang_iso31661alpha2, aala.dataanvgel AS land_aanvang_dataanvgel, aala.dateindegel AS land_aanvang_dateindegel,\n" +
            "  aapl.id AS plaats_aanvang_id, aapl.code AS plaats_aanvang_code, aapl.naam AS plaats_aanvang_naam, aapl.dataanvgel AS plaats_aanvang_dataanvgel, aapl.dateindegel AS plaats_aanvang_dateindegel,\n" +
            "  aapa.id AS partij_aanvang_id, aapa.naam AS partij_aanvang_naam, aapa.srt AS partij_aanvang_srt, aapa.code AS partij_aanvang_code, aapa.voortzettendegem AS partij_aanvang_voortzettendegem, aapa.onderdeelvan AS partij_aanvang_onderdeelvan, aapa.gemstatushis AS partij_aanvang_gemstatushis, aapa.dateinde AS partij_aanvang_dateinde, aapa.dataanv AS partij_aanvang_dataanv, aapa.sector AS partij_aanvang_sector, aapa.partijstatushis AS partij_aanvang_partijstatushis,\n" +
            "\n" +
            "  eila.id AS land_einde_id, eila.code AS land_einde_code, eila.naam AS land_einde_naam, eila.iso31661alpha2 AS land_einde_iso31661alpha2, eila.dataanvgel AS land_einde_dataanvgel, eila.dateindegel AS land_einde_dateindegel,\n" +
            "  eipl.id AS plaats_einde_id, eipl.code AS plaats_einde_code, eipl.naam AS plaats_einde_naam, eipl.dataanvgel AS plaats_einde_dataanvgel, eipl.dateindegel AS plaats_einde_dateindegel,\n" +
            "  eipa.id AS partij_einde_id, eipa.naam AS partij_einde_naam, eipa.srt AS partij_einde_srt, eipa.code AS partij_einde_code, eipa.voortzettendegem AS partij_einde_voortzettendegem, eipa.onderdeelvan AS partij_einde_onderdeelvan, eipa.gemstatushis AS partij_einde_gemstatushis, eipa.dateinde AS partij_einde_dateinde, eipa.dataanv AS partij_einde_dataanv, eipa.sector AS partij_einde_sector, eipa.partijstatushis AS partij_einde_partijstatushis,\n" +
            "\n" +
            "  eire.id AS rdneinde_relatie_id, eire.code AS rdneinde_relatie_code, eire.oms AS rdneinde_relatie_oms,\n" +
            "  rel.id AS relatie_id, rel.dataanv AS relatie_dataanv, rel.gemaanv AS relatie_gemaanv, rel.wplaanv AS relatie_wplaanv, rel.blplaatsaanv AS relatie_blplaatsaanv, rel.blregioaanv AS relatie_blregioaanv, rel.landaanv AS relatie_landaanv, rel.omslocaanv AS relatie_omslocaanv, rel.rdneinde AS relatie_rdneinde, rel.dateinde AS relatie_dateinde, rel.gemeinde AS relatie_gemeinde, rel.wpleinde AS relatie_wpleinde, rel.blplaatseinde AS relatie_blplaatseinde, rel.blregioeinde AS relatie_blregioeinde, rel.landeinde AS relatie_landeinde, rel.omsloceinde AS relatie_omsloceinde, rel.relatiestatushis AS relatie_relatiestatushis, rel.srt AS relatie_srt,\n" +
            "  bp.*\n" +
            "FROM kern.betr bp\n" +
            "JOIN kern.relatie rel ON bp.relatie = rel.id\n" +
            "LEFT JOIN kern.land aala ON rel.landaanv = aala.id\n" +
            "LEFT JOIN kern.plaats aapl ON rel.wplaanv = aapl.id\n" +
            "LEFT JOIN kern.partij aapa ON rel.gemaanv = aapa.id\n" +
            "LEFT JOIN kern.land eila ON rel.landeinde = eila.id\n" +
            "LEFT JOIN kern.plaats eipl ON rel.wpleinde = eipl.id\n" +
            "LEFT JOIN kern.partij eipa ON rel.gemeinde = eipa.id\n" +
            "LEFT JOIN kern.rdnbeeindrelatie eire ON rel.rdneinde = eire.id\n" +
            "WHERE bp.pers = ?;";

    private static final String QUERY_JOIN_GERELATEERDEN = "SELECT \n" +
            "  aala.id AS land_aanvang_id, aala.code AS land_aanvang_code, aala.naam AS land_aanvang_naam, aala.iso31661alpha2 AS land_aanvang_iso31661alpha2, aala.dataanvgel AS land_aanvang_dataanvgel, aala.dateindegel AS land_aanvang_dateindegel,\n" +
            "  aapl.id AS plaats_aanvang_id, aapl.code AS plaats_aanvang_code, aapl.naam AS plaats_aanvang_naam, aapl.dataanvgel AS plaats_aanvang_dataanvgel, aapl.dateindegel AS plaats_aanvang_dateindegel,\n" +
            "  aapa.id AS partij_aanvang_id, aapa.naam AS partij_aanvang_naam, aapa.srt AS partij_aanvang_srt, aapa.code AS partij_aanvang_code, aapa.voortzettendegem AS partij_aanvang_voortzettendegem, aapa.onderdeelvan AS partij_aanvang_onderdeelvan, aapa.gemstatushis AS partij_aanvang_gemstatushis, aapa.dateinde AS partij_aanvang_dateinde, aapa.dataanv AS partij_aanvang_dataanv, aapa.sector AS partij_aanvang_sector, aapa.partijstatushis AS partij_aanvang_partijstatushis,\n" +
            "\n" +
            "  eila.id AS land_einde_id, eila.code AS land_einde_code, eila.naam AS land_einde_naam, eila.iso31661alpha2 AS land_einde_iso31661alpha2, eila.dataanvgel AS land_einde_dataanvgel, eila.dateindegel AS land_einde_dateindegel,\n" +
            "  eipl.id AS plaats_einde_id, eipl.code AS plaats_einde_code, eipl.naam AS plaats_einde_naam, eipl.dataanvgel AS plaats_einde_dataanvgel, eipl.dateindegel AS plaats_einde_dateindegel,\n" +
            "  eipa.id AS partij_einde_id, eipa.naam AS partij_einde_naam, eipa.srt AS partij_einde_srt, eipa.code AS partij_einde_code, eipa.voortzettendegem AS partij_einde_voortzettendegem, eipa.onderdeelvan AS partij_einde_onderdeelvan, eipa.gemstatushis AS partij_einde_gemstatushis, eipa.dateinde AS partij_einde_dateinde, eipa.dataanv AS partij_einde_dataanv, eipa.sector AS partij_einde_sector, eipa.partijstatushis AS partij_einde_partijstatushis,\n" +
            "\n" +
            "  eire.id AS rdneinde_relatie_id, eire.code AS rdneinde_relatie_code, eire.oms AS rdneinde_relatie_oms,\n" +
            "  rel.id AS relatie_id, rel.dataanv AS relatie_dataanv, rel.gemaanv AS relatie_gemaanv, rel.wplaanv AS relatie_wplaanv, rel.blplaatsaanv AS relatie_blplaatsaanv, rel.blregioaanv AS relatie_blregioaanv, rel.landaanv AS relatie_landaanv, rel.omslocaanv AS relatie_omslocaanv, rel.rdneinde AS relatie_rdneinde, rel.dateinde AS relatie_dateinde, rel.gemeinde AS relatie_gemeinde, rel.wpleinde AS relatie_wpleinde, rel.blplaatseinde AS relatie_blplaatseinde, rel.blregioeinde AS relatie_blregioeinde, rel.landeinde AS relatie_landeinde, rel.omsloceinde AS relatie_omsloceinde, rel.relatiestatushis AS relatie_relatiestatushis, rel.srt AS relatie_srt,\n" +
            "  bg.*\n" +
            "FROM kern.betr bp\n" +
            "JOIN kern.relatie rel ON bp.relatie = rel.id\n" +
            "LEFT JOIN kern.land aala ON rel.landaanv = aala.id\n" +
            "LEFT JOIN kern.plaats aapl ON rel.wplaanv = aapl.id\n" +
            "LEFT JOIN kern.partij aapa ON rel.gemaanv = aapa.id\n" +
            "LEFT JOIN kern.land eila ON rel.landeinde = eila.id\n" +
            "LEFT JOIN kern.plaats eipl ON rel.wpleinde = eipl.id\n" +
            "LEFT JOIN kern.partij eipa ON rel.gemeinde = eipa.id\n" +
            "LEFT JOIN kern.rdnbeeindrelatie eire ON rel.rdneinde = eire.id\n" +
            "LEFT OUTER JOIN kern.betr bg ON bg.relatie = rel.id\n" +
//            "LEFT OUTER JOIN kern.pers g ON bg.pers = g.id\n" +
            "WHERE bp.pers = ? AND bg.pers != ?";

    @Override
    public PersoonsLijst getPersoonsLijst(final Integer bsn) {
        return getPersoonsLijst(bsn, false);
    }

    @Override
    public PersoonsLijst getPersoonsLijstMetHistorie(final Integer bsn) {
        return getPersoonsLijst(bsn, true);
    }

    private PersoonsLijst getPersoonsLijst(final Integer bsn, boolean metHistorie) {

        try {
            PersoonModel model =
                    jdbcTemplate.queryForObject(QUERY_GET_PERSOON, mapper, bsn);

            model.setNationaliteiten(getPersoonNationaliteiten(model));
            model.setIndicaties(getPersoonIndicaties(model));
            model.setGeslachtsnaamcomponenten(getPersoonGeslachtsnaamcomponenten(model));
            model.setBetrokkenheden(getPersoonBetrokkenheden(model));
            model.setAdressen(getPersoonAdressen(model, metHistorie));
            model.setPersoonVoornaam(getPersoonVoornaam(model));

            PersoonsLijst pl = new PersoonsLijst(model);
            LOGGER.info("Persoonslijst: {}", pl.toString());

            return pl;
        } catch (DataAccessException e) {
            LOGGER.error("BSN '{}': {}", bsn, e.getMessage());
        }
        return null;
    }

    private Set<PersoonNationaliteitModel> getPersoonNationaliteiten(
            final PersoonModel model)
    {
        List<PersoonNationaliteitModel> result =
                jdbcTemplate.query(QUERY_GET_NATIONALITEITEN, new PersoonNationaliteitModelMapper(), model.getId());

        for (PersoonNationaliteitModel nation : result) {
            nation.setPersoon(model);
        }

        return new HashSet<PersoonNationaliteitModel>(result);
    }

    private Set<PersoonIndicatieModel> getPersoonIndicaties(
            final PersoonModel model)
    {
        List<PersoonIndicatieModel> result =
                jdbcTemplate.query(QUERY_GET_INDICATIE, new PersoonIndicatieModelMapper(), model.getId());

        for (PersoonIndicatieModel indicatie : result) {
            indicatie.setPersoon(model);
        }

        return new HashSet<PersoonIndicatieModel>(result);
    }

    private Set<PersoonGeslachtsnaamcomponentModel> getPersoonGeslachtsnaamcomponenten(
            final PersoonModel model)
    {
        List<PersoonGeslachtsnaamcomponentModel> result = jdbcTemplate
                .query(QUERY_GET_GESLACHTSNAAM, new PersoonGeslachtsnaamcomponentGroepModelMapper(), model.getId());

        for (PersoonGeslachtsnaamcomponentModel geslacht : result) {
            geslacht.setPersoon(model);
        }

        return new HashSet<PersoonGeslachtsnaamcomponentModel>(result);
    }

    private Set<BetrokkenheidModel> getPersoonBetrokkenheden (
            final PersoonModel model)
    {
        List<BetrokkenheidModel> betrokkenheden =
                jdbcTemplate.query(QUERY_GET_BETROKKENHEDEN, new BetrokkenheidModelMapper(), model.getId());

        List<BetrokkenheidModel> gerelateerden = jdbcTemplate.query(QUERY_JOIN_GERELATEERDEN, new BetrokkenheidModelMapper(), model.getId(), model.getId());

        for(BetrokkenheidModel betrokkenheid : betrokkenheden) {
            betrokkenheid.setBetrokkene(model);

            RelatieModel relatie = betrokkenheid.getRelatie();
            Set<BetrokkenheidModel> groep = relatie.getBetrokkenheden();

            groep.clear();
            groep.add(betrokkenheid);

            for(BetrokkenheidModel gerelateerde : gerelateerden) {
                if (gerelateerde.getRelatie().getId().equals(relatie.getId())) {
                    gerelateerde.setRelatie(relatie);
                    groep.add(gerelateerde);

                    gerelateerde.setBetrokkene( jdbcTemplate.queryForObject(QUERY_GET_GERELATEERDE, new PersoonModelMapper(), gerelateerde.getId()) );
                }
            }
        }

        return new HashSet<BetrokkenheidModel>(betrokkenheden);
    }

    private Set<PersoonAdresModel> getPersoonAdressen (
            final nl.bzk.copy.model.objecttype.operationeel.PersoonModel model,
            boolean metHistory)
    {
        List<PersoonAdresModel> result =
                jdbcTemplate.query(QUERY_GET_ADRESSEN, new PersoonAdresModelMapper(), model.getId());

        for (PersoonAdresModel adres : result) {
            adres.setPersoon(model);
            if (metHistory) {
                final List<PersoonAdresHisModel> persoonAdresHistorie = getPersoonAdresHistorie(adres);
                if (persoonAdresHistorie != null && persoonAdresHistorie.size() > 0) {
                    adres.setHistorie(persoonAdresHistorie);
                }
            }
        }

        return new HashSet<PersoonAdresModel>(result);
    }

    private List<PersoonAdresHisModel> getPersoonAdresHistorie(
            final PersoonAdresModel model)
    {
        List<PersoonAdresHisModel> result =
                jdbcTemplate.query(QUERY_GET_ADRES_HISTORIE, new PersoonAdresHisModelMapper(model), model.getId());

        return result;
    }

    private Set<PersoonVoornaamModel> getPersoonVoornaam(
            final nl.bzk.copy.model.objecttype.operationeel.PersoonModel model)
    {
        List<PersoonVoornaamModel> result = jdbcTemplate.query(QUERY_GET_VOORNAAM, new PersoonVoornaamModelMapper(),
                                                               model.getId());

        for (PersoonVoornaamModel voornaam : result) {
            voornaam.setPersoon(model);
        }

        return new HashSet<PersoonVoornaamModel>(result);
    }


}
