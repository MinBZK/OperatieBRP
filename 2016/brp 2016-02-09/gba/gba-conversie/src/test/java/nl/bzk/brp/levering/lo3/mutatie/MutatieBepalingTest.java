/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mutatie;

import java.util.Date;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.mutatie.MutatieConverteerder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/mutatieconverteerder-test-context.xml")
public class MutatieBepalingTest {

    @Autowired
    private MutatieConverteerder subject;
    private Partij partij;
    private ActieModel actieGeboorte;
    private PersoonHisVolledigImplBuilder builder;

    @Before
    public void setupPersoon() {
        partij = PersoonHisVolledigUtil.maakPartij();

        actieGeboorte =
                PersoonHisVolledigUtil.maakActie(
                    1L,
                    SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND,
                    SoortActie.REGISTRATIE_GEBOORTE,
                    19200101,
                    partij);
        builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        voegGeboorteToe(builder, actieGeboorte);
    }

    private void toonCategorieen(final List<Lo3CategorieWaarde> categorieen) {
        System.out.println("CATEGORIEEN (" + (categorieen == null ? "0" : categorieen.size()) + ")");
        if (categorieen != null) {
            for (final Lo3CategorieWaarde categorie : categorieen) {
                System.out.println("Categorie: " + categorie.getCategorie());
                for (final Map.Entry<Lo3ElementEnum, String> element : categorie.getElementen().entrySet()) {
                    System.out.println(" - " + element.getKey() + ": " + element.getValue());
                }
            }
        }
    }

    @Test
    public void testOverlijden() {
        final ActieModel actieVerhuizen =
                PersoonHisVolledigUtil.maakActie(
                    2L,
                    SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK,
                    SoortActie.REGISTRATIE_ADRES,
                    19470307,
                    partij);
        voegVerhuizingToe(builder, actieVerhuizen);

        final ActieModel actieOverlijden =
                PersoonHisVolledigUtil.maakActie(
                    3L,
                    SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND,
                    SoortActie.REGISTRATIE_OVERLIJDEN,
                    20140711,
                    partij);
        voegOverlijdenToe(builder, actieOverlijden);

        final PersoonHisVolledig persoon = builder.build();
        PersoonHisVolledigUtil.maakVerantwoording(persoon, actieGeboorte, actieVerhuizen, actieOverlijden);
        final List<Lo3CategorieWaarde> categorieen =
                subject.converteer(persoon, null, actieOverlijden.getAdministratieveHandeling(), new ConversieCache());
        toonCategorieen(categorieen);
    }

    @Test
    public void testVerhuizen() {
        final ActieModel actieVerhuizen =
                PersoonHisVolledigUtil.maakActie(
                    2L,
                    SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK,
                    SoortActie.REGISTRATIE_ADRES,
                    19470307,
                    partij);
        voegVerhuizingToe(builder, actieVerhuizen);
        final PersoonHisVolledig persoon = builder.build();
        PersoonHisVolledigUtil.maakVerantwoording(persoon, actieGeboorte, actieVerhuizen);
        final List<Lo3CategorieWaarde> categorieen = subject.converteer(persoon, null, actieVerhuizen.getAdministratieveHandeling(), new ConversieCache());
        toonCategorieen(categorieen);
    }

    private void voegGeboorteToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieGeboorte) {
        // Geboorte
        builder.voegPersoonAdresToe(
            new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(actieGeboorte)
                                                    .aangeverAdreshouding("I")
                                                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                                                    .gemeente(Short.valueOf((short) 518))
                                                    .gemeentedeel("deel vd gemeente")
                                                    .huisletter("A")
                                                    .huisnummer(10)
                                                    .huisnummertoevoeging("A")
                                                    .landGebied((short) 6030)
                                                    .naamOpenbareRuimte("Naam")
                                                    .postcode("2245HJ")
                                                    .redenWijziging("P")
                                                    .soort(FunctieAdres.WOONADRES)
                                                    .woonplaatsnaam("Rotterdam")
                                                    .eindeRecord()
                                                    .build());
        builder.nieuwGeboorteRecord(actieGeboorte)
               .datumGeboorte(actieGeboorte.getDatumAanvangGeldigheid())
               .gemeenteGeboorte(new Short((short) 6030))
               .landGebiedGeboorte(new Short((short) 6030))
               .eindeRecord();
        builder.nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        builder.nieuwIdentificatienummersRecord(actieGeboorte).administratienummer(1234567890L).burgerservicenummer(123456789).eindeRecord();
        builder.nieuwInschrijvingRecord(actieGeboorte)
               .datumInschrijving(actieGeboorte.getDatumAanvangGeldigheid())
               .versienummer(1L)
               .datumtijdstempel(new Date(123))
               .eindeRecord();
        builder.nieuwNaamgebruikRecord(actieGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord();
        builder.nieuwAfgeleidAdministratiefRecord(actieGeboorte)
               .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
               .tijdstipLaatsteWijziging(actieGeboorte.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieGeboorte.getTijdstipRegistratie())
               .eindeRecord();
        builder.nieuwSamengesteldeNaamRecord(actieGeboorte)
               .geslachtsnaamstam("geslachtsnaam")
               .voorvoegsel("de")
               .voornamen("Voornaam1 Voornaam2")
               .scheidingsteken(" ")
               .eindeRecord();
    }

    private void voegVerhuizingToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieVerhuizing) {
        final String hisVolledigImplVeldNaam = "hisVolledigImpl";
        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, hisVolledigImplVeldNaam);

        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();

        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, hisVolledigImplVeldNaam, adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actieVerhuizing)
                    .aangeverAdreshouding("I")
                    .datumAanvangAdreshouding(actieVerhuizing.getDatumAanvangGeldigheid())
                    .gemeente(Short.valueOf((short) 518))
                    .huisnummer(46)
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("Pippelingstraat")
                    .postcode("2522HT")
                    .redenWijziging("P")
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam("Rotterdam")
                    .eindeRecord();
    }

    private void voegOverlijdenToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieOverlijden) {
        // Overlijden
        builder.nieuwOverlijdenRecord(actieOverlijden)
               .datumOverlijden(actieOverlijden.getDatumAanvangGeldigheid())
               .gemeenteOverlijden(Short.valueOf((short) 518))
               .landGebiedOverlijden(Short.valueOf((short) 6030))
               .eindeRecord();
        builder.nieuwAfgeleidAdministratiefRecord(actieOverlijden)
               .tijdstipLaatsteWijziging(actieOverlijden.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieOverlijden.getTijdstipRegistratie())
               .eindeRecord();
    }

}
