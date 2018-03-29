/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import com.google.common.collect.Lists;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Wa11BerichtFilter;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link Wa11Formatter}.
 */
public class Wa11FormatterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final VulBerichtFilter  filter     = new VulBerichtFilter();
    private final Wa11BerichtFilter wa11filter = new Wa11BerichtFilter(filter);
    private final Wa11Formatter     subject    = new Wa11Formatter();

    @Test
    public void test() {
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(buildLo3Persoonslijst());

        final List<Lo3CategorieWaarde> gefilterdeCategorieen =
            wa11filter.filter(
                null,
                null,
                null,
                null,
                categorieen,
                Arrays.asList("01.01.10", "01.02.10", "01.02.20", "01.02.30", "01.02.40", "01.03.10", "01.03.20", "01.03.30", "07.80.10"));
        LOGGER.info("Gefilterde categorieen: " + gefilterdeCategorieen);

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil
            .UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", null,
                SoortAdministratieveHandeling
                .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", 19470307),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", 19470307)
                ))
        .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);


        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, "3450924321")
                .metActieInhoud(actieInhoud)
                .metDatumAanvangGeldigheid(19770101)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, "9539040545")
                .metActieInhoud(actieInhoudOud)
                .metActieAanpassingGeldigheid(actieInhoud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "Anummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
        }

        final IdentificatienummerMutatie resultaat = new IdentificatienummerMutatie(persoon, administratievehandeling);

        final String wa11 = subject.maakPlatteTekst(null, resultaat, categorieen, gefilterdeCategorieen);
        LOGGER.info(wa11);

        final StringBuilder verwachteWa11 = new StringBuilder();
        // Header (bevat *NIEUW* a-nummer)
        verwachteWa11.append("00000000");
        verwachteWa11.append("Wa11");
        verwachteWa11.append("3450924321");
        verwachteWa11.append("19770101");
        // Bericht lengte
        verwachteWa11.append("00089");

        // Categorie 01 + lengte (totale lengte: 89)
        verwachteWa11.append("01").append("084");
        // Element 01.10 + lengte + waarde (totale lengte: 17)
        verwachteWa11.append("0110").append("010").append("9539040545");
        // Element 02.10 + lengte + waarde (totale lengte: 14)
        verwachteWa11.append("0210").append("007").append("Scarlet");
        // Element 02.40 + lengte + waarde (totale lengte: 16)
        verwachteWa11.append("0240").append("009").append("Simpspoon");
        // Element 03.10 + lengte + waarde (totale lengte: 15)
        verwachteWa11.append("0310").append("008").append("19770101");
        // Element 03.20 + lengte + waarde (totale lengte: 11)
        verwachteWa11.append("0320").append("004").append("0518");
        // Element 03.30 + lengte + waarde (totale lengte: 11)
        verwachteWa11.append("0330").append("004").append("6030");

        Assert.assertEquals(verwachteWa11.toString(), wa11);
    }

    public Lo3Persoonslijst buildLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());

        return builder.build();
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Persoon("3450924321", null, "Scarlet", null, null, "Simpspoon", 19770101, "0518", "6030", "V", "9539040545", null, "E"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19770101),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, 20140101, "F", 19770202, "0518", 0, 1, 19770102123014000L, true),
                null,
                new Lo3Historie(null, new Lo3Datum(0), new Lo3Datum(0)),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }

}
