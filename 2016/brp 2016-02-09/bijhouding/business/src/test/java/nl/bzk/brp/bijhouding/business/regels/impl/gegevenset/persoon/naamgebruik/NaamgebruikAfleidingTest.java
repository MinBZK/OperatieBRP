/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * VR00009a: Afgeleide registratie Naamgebruik
 */
public class NaamgebruikAfleidingTest {

    public static final DatumEvtDeelsOnbekendAttribuut DATUM_AANVANG_GELDIGHEID =
            new DatumEvtDeelsOnbekendAttribuut(20120101);
    private NaamgebruikAfleiding naamgebruikAfleiding;
    private PersoonHisVolledigImpl persoon;

    @Before
    public void init() {
        persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final ActieModel actie = creeerActie();
        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00009a, naamgebruikAfleiding.getRegel());
    }

    @Test
    public void testLeidAf() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .predicaat("testP")
                .adellijkeTitel("testA")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN)
                .voornamenNaamgebruik("Paul Kees")
                .geslachtsnaamstamNaamgebruik("Klaassem")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110606)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN)
                .scheidingstekenNaamgebruik("-")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(3, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Pietersen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfNaamgebruikNogLeeg() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        Assert.assertEquals(0, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertNull(persoon.getPersoonNaamgebruikHistorie().getActueleRecord());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Pietersen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfOverruledNietAlgoritmischeAfgeleid() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .predicaat("testP")
                .adellijkeTitel("testA")
                .eindeRecord()

                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN)
                .voornamenNaamgebruik("Paul Kees")
                .geslachtsnaamstamNaamgebruik("Klaassem")
                .eindeRecord()

                .nieuwNaamgebruikRecord(actie)
                .indicatieNaamgebruikAfgeleid(false)
                .naamgebruik(Naamgebruik.EIGEN)
                .scheidingstekenNaamgebruik(" ")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()

                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfOverruledWelAlgoritmischeAfgeleid() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .predicaat("testP")
                .adellijkeTitel("testA")
                .eindeRecord()

                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN)
                .voornamenNaamgebruik("Paul Kees")
                .geslachtsnaamstamNaamgebruik("Klaassem")
                .eindeRecord()

                .nieuwNaamgebruikRecord(actie)
                .indicatieNaamgebruikAfgeleid(true)
                .eindeRecord()

                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Pietersen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfMetPredicaatEnAdellijketitel() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .predicaat("testP")
                .adellijkeTitel("testA")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .naamgebruik(Naamgebruik.EIGEN)
                .scheidingstekenNaamgebruik(" ")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("testA",
            persoon.getPersoonNaamgebruikHistorie().getActueleRecord()
                    .getAdellijkeTitelNaamgebruik().getWaarde().getCode().getWaarde());
        Assert.assertEquals("testP",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord()
                                    .getPredicaatNaamgebruik().getWaarde().getCode().getWaarde());
    }

    @Test
    public void testLeidAfGeenPartnerWelPartnerNaamgebruik() {
        final List<Naamgebruik> wijzeNaamgebruiken = Arrays.asList(
                Naamgebruik.PARTNER, Naamgebruik.PARTNER_EIGEN, Naamgebruik.EIGEN_PARTNER);
        for (final Naamgebruik wijzeGebruikGeslachtsnaam : wijzeNaamgebruiken) {
            final ActieModel actie = creeerActie();
            persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwSamengesteldeNaamRecord(actie)
                    .voornamen("Paul Kees")
                    .geslachtsnaamstam("Pietersen")
                    .eindeRecord()
                    .nieuwNaamgebruikRecord(20110101)
                    .adellijkeTitelNaamgebruik("testA")
                    .predicaatNaamgebruik("testP")
                    .naamgebruik(wijzeGebruikGeslachtsnaam)
                    .scheidingstekenNaamgebruik(" ")
                    .voornamenNaamgebruik("Paul Kees")
                    .voorvoegselNaamgebruik("van")
                    .geslachtsnaamstamNaamgebruik("Jansen")
                    .eindeRecord()
                    .build();

            ReflectionTestUtils.setField(persoon, "iD", 1);

            naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

            naamgebruikAfleiding.leidAf();
        }
    }

    @Test
    public void testLeidAfNaamgebruikPartner() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.PARTNER)
                .scheidingstekenNaamgebruik(" ")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        geefPersoonEenPartner(persoon, actie, 20110101);

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Partnersen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfNaamgebruikPartnerEigen() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .voorvoegsel((VoorvoegselAttribuut) null)
                .scheidingsteken("")
                .geslachtsnaamstam("Pietersen")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.PARTNER_EIGEN)
                .scheidingstekenNaamgebruik(" ")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        geefPersoonEenPartner(persoon, actie, 20110101);

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Partnersen-Pietersen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfNaamgebruikEigenPartner() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN_PARTNER)
                .scheidingstekenNaamgebruik("-")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        geefPersoonEenPartner(persoon, actie, 20110101);

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Pietersen-de Partnersen",
                            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                                    .getWaarde());
    }

    @Test
    public void testLeidAfNietActueelHuwelijk() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN_PARTNER)
                .scheidingstekenNaamgebruik("-")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        geefPersoonEenPartner(persoon, actie, 20110101, SoortPersoon.INGESCHREVENE, 2, false);

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Pietersen",
                persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                .getWaarde());
    }

    @Test
    public void testLeidAfTweeHuwelijken() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(actie)
                .voornamen("Paul Kees")
                .geslachtsnaamstam("Pietersen")
                .eindeRecord()
                .nieuwNaamgebruikRecord(20110101)
                .adellijkeTitelNaamgebruik("testA")
                .predicaatNaamgebruik("testP")
                .naamgebruik(Naamgebruik.EIGEN_PARTNER)
                .scheidingstekenNaamgebruik("-")
                .voornamenNaamgebruik("Paul Kees")
                .voorvoegselNaamgebruik("van")
                .geslachtsnaamstamNaamgebruik("Jansen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        geefPersoonEenPartner(persoon, actie, 20110101, SoortPersoon.INGESCHREVENE, 2, true);
        geefPersoonEenPartner(persoon, actie, 20100101, SoortPersoon.INGESCHREVENE, 2, true);
        geefPersoonEenPartner(persoon, actie, 20120101, SoortPersoon.INGESCHREVENE, 3, true, "Meest Recent");

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
                persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Pietersen-de Meest Recent",
                persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                .getWaarde());
    }

    @Test
    public void testLeidAfVoorPersoonMetNieuweNietIngeschrevenPartner() {
        final ActieModel actie = creeerActie();
        persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwSamengesteldeNaamRecord(actie)
            .voornamen("Paul Kees")
            .geslachtsnaamstam("Pietersen")
            .eindeRecord()
            .nieuwNaamgebruikRecord(20110101)
            .adellijkeTitelNaamgebruik("testA")
            .predicaatNaamgebruik("testP")
            .naamgebruik(Naamgebruik.PARTNER)
            .scheidingstekenNaamgebruik(" ")
            .voornamenNaamgebruik("Paul Kees")
            .voorvoegselNaamgebruik("van")
            .geslachtsnaamstamNaamgebruik("Jansen")
            .eindeRecord()
            .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        naamgebruikAfleiding = new NaamgebruikAfleiding(persoon, actie);

        geefPersoonEenPartner(persoon, actie, 20110101, SoortPersoon.NIET_INGESCHREVENE, null, true);

        Assert.assertEquals(1, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Jansen",
            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                   .getWaarde());

        naamgebruikAfleiding.leidAf();

        Assert.assertEquals(2, persoon.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals("Partnersen",
            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik()
                   .getWaarde());
    }

    /**
     * Geeft een persoon een partner betrokkenheid via een huwelijk met een partner persoon.
     *
     * @param persoonZoektPartner de persoon die een partner krijgt
     * @param actie de actie
     * @return de persoon his volledig impl van de partner
     */
    private PersoonHisVolledigImpl geefPersoonEenPartner(final PersoonHisVolledigImpl persoonZoektPartner,
            final ActieModel actie, final int huwelijksDatum)
    {
        return geefPersoonEenPartner(persoonZoektPartner, actie, huwelijksDatum, SoortPersoon.INGESCHREVENE, 2, true);
    }

    private PersoonHisVolledigImpl geefPersoonEenPartner(final PersoonHisVolledigImpl persoonZoektPartner,
        final ActieModel actie, final int huwelijksDatum, final SoortPersoon soortPersoon, final Integer id, final boolean actueelHuwelijk)
    {
        return geefPersoonEenPartner(persoonZoektPartner, actie, huwelijksDatum, soortPersoon, id, actueelHuwelijk, "Partnersen");
    }

    private PersoonHisVolledigImpl geefPersoonEenPartner(final PersoonHisVolledigImpl persoonZoektPartner,
            final ActieModel actie, final int huwelijksDatum, final SoortPersoon soortPersoon, final Integer id,
            final boolean actueelHuwelijk, final String geslachtsnaamPartner)
    {
        final PersoonHisVolledigImpl partner = new PersoonHisVolledigImplBuilder(soortPersoon)
            .nieuwSamengesteldeNaamRecord(actie)
            .voornamen("Partner")
            .geslachtsnaamstam(geslachtsnaamPartner)
            .scheidingsteken(" ")
            .voorvoegsel("de")
            .eindeRecord()
            .build();

        ReflectionTestUtils.setField(partner, "iD", id);

        final HuwelijkHisVolledigImpl relatie;
        relatie = new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(huwelijksDatum).datumAanvang(huwelijksDatum).eindeRecord().build();
        if (!actueelHuwelijk) {
            final ActieModel actieModel = relatie.getRelatieHistorie().getActueleRecord().getVerantwoordingInhoud();
            relatie.getRelatieHistorie().verval(actieModel, DatumTijdAttribuut.nu());
        }

        relatie.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, persoonZoektPartner));
        relatie.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, partner));

        persoonZoektPartner.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, persoonZoektPartner));
        partner.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, partner));

        // Extra dummy betrokkenheid voor coverage
        persoonZoektPartner.getBetrokkenheden().add(new KindHisVolledigImpl(null, persoonZoektPartner));

        return partner;
    }

    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                                      null, null), null, DATUM_AANVANG_GELDIGHEID, null, DatumTijdAttribuut.nu(), null);
    }
}
