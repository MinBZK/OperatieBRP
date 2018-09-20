/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingVerblijfsrechtCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import org.junit.Assert;
import org.junit.Test;
import support.ReflectionUtils;

/**
 * Test voor {@link BrpMapperUtil}: simple type mapping.
 */
public class BrpMapperUtilSpecifiekTest {

    public static final String Q = "Q";
    public static final String X = "X";
    public static final String I = "I";
    public static final String E = "E";
    public static final String WX_001 = "WX001";
    public static final String PD = "PD";

    @Test
    public void mapBrpAanduidingBijHuisnummerCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpAanduidingBijHuisnummerCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpAanduidingBijHuisnummerCode(new LocatieTenOpzichteVanAdresAttribuut(null), null));

        Assert.assertEquals(
            "by",
            BrpMapperUtil.mapBrpAanduidingBijHuisnummerCode(new LocatieTenOpzichteVanAdresAttribuut(LocatieTenOpzichteVanAdres.BY), null).getWaarde());
    }

    @Test
    public void mapBrpAanduidingInhoudingOfVermissingReisdocumentCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(null, null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(new AanduidingInhoudingVermissingReisdocumentAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                new AanduidingInhoudingVermissingReisdocumentAttribuut(new AanduidingInhoudingVermissingReisdocument(null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                new AanduidingInhoudingVermissingReisdocumentAttribuut(
                    new AanduidingInhoudingVermissingReisdocument(new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(null), null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                new AanduidingInhoudingVermissingReisdocumentAttribuut(
                    new AanduidingInhoudingVermissingReisdocument(new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(""), null)),
                null));

        Assert.assertEquals(
            Character.valueOf('Q'),
            BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                new AanduidingInhoudingVermissingReisdocumentAttribuut(
                    new AanduidingInhoudingVermissingReisdocument(new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(Q), null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpAangeverCode() throws ReflectiveOperationException {
        Assert.assertNull(BrpMapperUtil.mapBrpAangeverCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpAangeverCode(new AangeverAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpAangeverCode(new AangeverAttribuut(ReflectionUtils.instantiate(Aangever.class, null, null, null)), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAangeverCode(
                new AangeverAttribuut(ReflectionUtils.instantiate(Aangever.class, new AangeverCodeAttribuut(null), null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAangeverCode(
                new AangeverAttribuut(ReflectionUtils.instantiate(Aangever.class, new AangeverCodeAttribuut(""), null, null)),
                null));

        Assert.assertEquals(
            Character.valueOf('P'),
            BrpMapperUtil.mapBrpAangeverCode(
                new AangeverAttribuut(ReflectionUtils.instantiate(Aangever.class, new AangeverCodeAttribuut("P"), null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpAdellijkeTitelCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpAdellijkeTitelCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpAdellijkeTitelCode(new AdellijkeTitelAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpAdellijkeTitelCode(new AdellijkeTitelAttribuut(new AdellijkeTitel(null, null, null)), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpAdellijkeTitelCode(
                new AdellijkeTitelAttribuut(new AdellijkeTitel(new AdellijkeTitelCodeAttribuut(null), null, null)),
                null));

        Assert.assertEquals(
            X,
            BrpMapperUtil.mapBrpAdellijkeTitelCode(new AdellijkeTitelAttribuut(new AdellijkeTitel(new AdellijkeTitelCodeAttribuut(X), null, null)), null)
                         .getWaarde());
    }

    @Test
    public void mapBrpBijhoudingsaardCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpBijhoudingsaardCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpBijhoudingsaardCode(new BijhoudingsaardAttribuut(null), null));

        Assert.assertEquals(I, BrpMapperUtil.mapBrpBijhoudingsaardCode(new BijhoudingsaardAttribuut(Bijhoudingsaard.INGEZETENE), null).getWaarde());
    }

    @Test
    public void mapBrpGemeenteCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpGemeenteCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpGemeenteCode(new GemeenteAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpGemeenteCode(new GemeenteAttribuut(new Gemeente(null, null, null, null, null, null)), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpGemeenteCode(new GemeenteAttribuut(new Gemeente(null, new GemeenteCodeAttribuut(null), null, null, null, null)), null));

        Assert.assertEquals(
            Short.valueOf((short) 517),
            BrpMapperUtil.mapBrpGemeenteCode(
                new GemeenteAttribuut(new Gemeente(null, new GemeenteCodeAttribuut((short) 517), null, null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpGeslachtsaanduidingCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpGeslachtsaanduidingCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpGeslachtsaanduidingCode(new GeslachtsaanduidingAttribuut(null), null));

        Assert.assertEquals(
            "V",
            BrpMapperUtil.mapBrpGeslachtsaanduidingCode(new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.VROUW), null).getWaarde());
    }

    @Test
    public void mapBrpLandOfGebiedCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpLandOfGebiedCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpLandOfGebiedCode(new LandGebiedAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpLandOfGebiedCode(new LandGebiedAttribuut(new LandGebied(null, null, null, null, null)), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                new LandGebiedAttribuut(new LandGebied(new LandGebiedCodeAttribuut(null), null, null, null, null)),
                null));

        Assert.assertEquals(
            Short.valueOf((short) 12),
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                new LandGebiedAttribuut(new LandGebied(new LandGebiedCodeAttribuut((short) 12), null, null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpNaamgebruikCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpNaamgebruikCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpNaamgebruikCode(new NaamgebruikAttribuut(null), null));

        Assert.assertEquals(E, BrpMapperUtil.mapBrpNaamgebruikCode(new NaamgebruikAttribuut(Naamgebruik.EIGEN), null).getWaarde());
    }

    @Test
    public void mapBrpNadereBijhoudingsaardCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpNadereBijhoudingsaardCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpNadereBijhoudingsaardCode(new NadereBijhoudingsaardAttribuut(null), null));

        Assert.assertEquals(
            E,
            BrpMapperUtil.mapBrpNadereBijhoudingsaardCode(new NadereBijhoudingsaardAttribuut(NadereBijhoudingsaard.EMIGRATIE), null).getWaarde());
    }

    @Test
    public void mapBrpNationaliteitCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpNationaliteitCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpNationaliteitCode(new NationaliteitAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpNationaliteitCode(new NationaliteitAttribuut(new Nationaliteit(null, null, null, null)), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpNationaliteitCode(
                new NationaliteitAttribuut(new Nationaliteit(new NationaliteitcodeAttribuut((Short) null), null, null, null)),
                null));

        Assert.assertEquals(
            Short.valueOf((short) 27),
            BrpMapperUtil.mapBrpNationaliteitCode(
                new NationaliteitAttribuut(new Nationaliteit(new NationaliteitcodeAttribuut((short) 27), null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpPartijCode() throws ReflectiveOperationException {
        Assert.assertNull(BrpMapperUtil.mapBrpPartijCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpPartijCode(new PartijAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpPartijCode(
                new PartijAttribuut(new Partij(null, null, null, null, null, null, null, null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpPartijCode(
                new PartijAttribuut(new Partij(null, null, new PartijCodeAttribuut(null), null, null, null, null, null, null)),
                null));

        Assert.assertEquals(
            Integer.valueOf(123),
            BrpMapperUtil.mapBrpPartijCode(
                new PartijAttribuut(new Partij( null, null, new PartijCodeAttribuut(123), null, null, null, null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpPredikaatCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpPredicaatCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpPredicaatCode(new PredicaatAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpPredicaatCode(new PredicaatAttribuut(new Predicaat(null, null, null)), null));
        Assert.assertNull(BrpMapperUtil.mapBrpPredicaatCode(new PredicaatAttribuut(new Predicaat(new PredicaatCodeAttribuut(null), null, null)), null));

        Assert.assertEquals(
            X,
            BrpMapperUtil.mapBrpPredicaatCode(new PredicaatAttribuut(new Predicaat(new PredicaatCodeAttribuut(X), null, null)), null).getWaarde());
    }

    @Test
    public void mapBrpRedenVerkrijgingNederlandschapCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(new RedenVerkrijgingNLNationaliteitAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(
                new RedenVerkrijgingNLNationaliteitAttribuut(new RedenVerkrijgingNLNationaliteit(null, null, null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(
                new RedenVerkrijgingNLNationaliteitAttribuut(
                    new RedenVerkrijgingNLNationaliteit(new RedenVerkrijgingCodeAttribuut(null), null, null, null)),
                null));

        Assert.assertEquals(
            Short.valueOf((short) 39),
            BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(
                new RedenVerkrijgingNLNationaliteitAttribuut(
                    new RedenVerkrijgingNLNationaliteit(new RedenVerkrijgingCodeAttribuut((short) 39), null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpRedenVerliesNederlanderschapCode() throws ReflectiveOperationException {
        Assert.assertNull(BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(new RedenVerliesNLNationaliteitAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(
                new RedenVerliesNLNationaliteitAttribuut(ReflectionUtils.instantiate(RedenVerliesNLNationaliteit.class, null, null, null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(
                new RedenVerliesNLNationaliteitAttribuut(
                    ReflectionUtils.instantiate(RedenVerliesNLNationaliteit.class, new RedenVerliesCodeAttribuut(null), null, null, null)),
                null));

        Assert.assertEquals(
            Short.valueOf((short) 39),
            BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(
                new RedenVerliesNLNationaliteitAttribuut(
                    ReflectionUtils.instantiate(RedenVerliesNLNationaliteit.class, new RedenVerliesCodeAttribuut((short) 39), null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpRedenWijzigingVerblijfCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(new RedenWijzigingVerblijfAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(null, null)), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(
                new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(new RedenWijzigingVerblijfCodeAttribuut(null), null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(
                new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(new RedenWijzigingVerblijfCodeAttribuut(""), null)),
                null));

        Assert.assertEquals(
            Character.valueOf('Q'),
            BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(
                new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(new RedenWijzigingVerblijfCodeAttribuut(Q), null)),
                null).getWaarde());

    }

    @Test
    public void mapBrpReisdocumentAutoriteitVanAfgifteCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpReisdocumentAutoriteitVanAfgifteCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpReisdocumentAutoriteitVanAfgifteCode(new AutoriteitVanAfgifteReisdocumentCodeAttribuut(null), null));

        Assert.assertEquals(
            WX_001,
            BrpMapperUtil.mapBrpReisdocumentAutoriteitVanAfgifteCode(new AutoriteitVanAfgifteReisdocumentCodeAttribuut(WX_001), null).getWaarde());
    }

    @Test
    public void mapBrpSoortActieCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpSoortActieCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpSoortActieCode(new SoortActieAttribuut(null), null));

        Assert.assertEquals(
            "Registratie kind",
            BrpMapperUtil.mapBrpSoortActieCode(new SoortActieAttribuut(SoortActie.REGISTRATIE_KIND), null).getWaarde());
    }

    @Test
    public void mapBrpSoortAdresCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpSoortAdresCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpSoortAdresCode(new FunctieAdresAttribuut(null), null));

        Assert.assertEquals("B", BrpMapperUtil.mapBrpSoortAdresCode(new FunctieAdresAttribuut(FunctieAdres.BRIEFADRES), null).getWaarde());
    }

    @Test
    public void mapBrpSoortMigratieCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpSoortMigratieCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpSoortMigratieCode(new SoortMigratieAttribuut(null), null));

        Assert.assertEquals(I, BrpMapperUtil.mapBrpSoortMigratieCode(new SoortMigratieAttribuut(SoortMigratie.IMMIGRATIE), null).getWaarde());
    }

    @Test
    public void mapBrpSoortNederlandsReisdocumentCode() {
        Assert.assertNull(BrpMapperUtil.mapBrpSoortNederlandsReisdocumentCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpSoortNederlandsReisdocumentCode(new SoortNederlandsReisdocumentAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpSoortNederlandsReisdocumentCode(
                new SoortNederlandsReisdocumentAttribuut(new SoortNederlandsReisdocument(null, null, null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpSoortNederlandsReisdocumentCode(
                new SoortNederlandsReisdocumentAttribuut(
                    new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut(null), null, null, null)),
                null));

        Assert.assertEquals(
            PD,
            BrpMapperUtil.mapBrpSoortNederlandsReisdocumentCode(
                new SoortNederlandsReisdocumentAttribuut(
                    new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut(PD), null, null, null)),
                null).getWaarde());
    }

    @Test
    public void mapBrpVerblijfsrechtCode() throws ReflectiveOperationException {
        Assert.assertNull(BrpMapperUtil.mapBrpVerblijfsrechtCode(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpVerblijfsrechtCode(new AanduidingVerblijfsrechtAttribuut(null), null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpVerblijfsrechtCode(
                new AanduidingVerblijfsrechtAttribuut(ReflectionUtils.instantiate(AanduidingVerblijfsrecht.class, null, null, null, null)),
                null));
        Assert.assertNull(
            BrpMapperUtil.mapBrpVerblijfsrechtCode(
                new AanduidingVerblijfsrechtAttribuut(
                    ReflectionUtils.instantiate(AanduidingVerblijfsrecht.class, new AanduidingVerblijfsrechtCodeAttribuut(null), null, null, null)),
                null));

        Assert.assertEquals(
            Short.valueOf((short) 6),
            BrpMapperUtil.mapBrpVerblijfsrechtCode(
                new AanduidingVerblijfsrechtAttribuut(
                    ReflectionUtils.instantiate(AanduidingVerblijfsrecht.class, new AanduidingVerblijfsrechtCodeAttribuut((short) 6), null, null, null)),
                null).getWaarde());
    }
}
