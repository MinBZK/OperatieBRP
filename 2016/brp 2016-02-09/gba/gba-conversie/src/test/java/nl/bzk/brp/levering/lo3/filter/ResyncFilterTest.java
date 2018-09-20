/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

public class ResyncFilterTest {

    private static final String ID = "iD";
    private static final String GEMEENTE_518 = "518";
    private static final String LAND_6030 = "6030";
    private static final String I = "I";
    private static final String A = "A";
    private static final String P = "P";
    private static final String ROTTERDAM = "Rotterdam";
    private static final String HIS_VOLLEDIG_IMPL = "hisVolledigImpl";
    private static final String GESLACHTSNAAM = "geslachtsnaam";
    private static final String RUBRIEK_REISDOCUMENT = "12.35.10";

    private final Partij partij = PersoonHisVolledigUtil.maakPartij();
    private final ActieModel actieInitieleVulling =
            PersoonHisVolledigUtil.maakActie(1L, SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, SoortActie.CONVERSIE_G_B_A, 19200101, partij);
    private final ActieModel actieSynchronisatie =
            PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_OVERIG, SoortActie.CONVERSIE_G_B_A, 20140711, partij);

    @Test
    public void test() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        voegGeboorteToe(builder, actieInitieleVulling);
        voegVerhuizingToe(builder, actieInitieleVulling);
        voegReisdocumentToe(builder, actieSynchronisatie);

        final PersoonHisVolledig persoon = builder.build();

        final List<String> resyncRubrieken = ResyncBerichtFilter.bepaalRubrieken(persoon, null, actieSynchronisatie.getAdministratieveHandeling());
        // Zie RubriekenMap
        Assert.assertTrue(resyncRubrieken.contains("12.35."));
        Assert.assertTrue(resyncRubrieken.contains("12.8"));

        final List<String> filterRubriekenMetReisdocument = Arrays.asList(RUBRIEK_REISDOCUMENT);
        final List<String> filterRubriekenZonderReisdocument = Arrays.asList("01.01.10");

        Assert.assertTrue(ResyncBerichtFilter.bevatRubrieken(filterRubriekenMetReisdocument, resyncRubrieken));
        Assert.assertFalse(ResyncBerichtFilter.bevatRubrieken(filterRubriekenZonderReisdocument, resyncRubrieken));
    }

    @Test
    public void testFallback() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        voegGeboorteToe(builder, actieInitieleVulling);
        voegVerhuizingToe(builder, actieInitieleVulling);
        voegProbasToe(builder, actieSynchronisatie);

        final PersoonHisVolledig persoon = builder.build();

        final List<String> resyncRubrieken = ResyncBerichtFilter.bepaalRubrieken(persoon, null, actieSynchronisatie.getAdministratieveHandeling());
        // Zie RubriekenMap
        Assert.assertNull(resyncRubrieken);

        final List<String> filterRubrieken = Arrays.asList(RUBRIEK_REISDOCUMENT);
        Assert.assertTrue(ResyncBerichtFilter.bevatRubrieken(filterRubrieken, resyncRubrieken));
    }

    private void voegGeboorteToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieGeboorte) {
        // Geboorte
        builder.voegPersoonAdresToe(
            new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(actieGeboorte)
                                                    .aangeverAdreshouding(I)
                                                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                                                    .gemeente(Short.valueOf(GEMEENTE_518))
                                                    .gemeentedeel("deel vd gemeente")
                                                    .huisletter(A)
                                                    .huisnummer(10)
                                                    .huisnummertoevoeging(A)
                                                    .landGebied((short) 6030)
                                                    .naamOpenbareRuimte("naam")
                                                    .postcode("2245HJ")
                                                    .redenWijziging(P)
                                                    .soort(FunctieAdres.WOONADRES)
                                                    .woonplaatsnaam(ROTTERDAM)
                                                    .eindeRecord()
                                                    .build());
        builder.nieuwGeboorteRecord(actieGeboorte)
               .datumGeboorte(actieGeboorte.getDatumAanvangGeldigheid())
               .gemeenteGeboorte(Short.valueOf(GEMEENTE_518))
               .landGebiedGeboorte(Short.valueOf(LAND_6030))
               .eindeRecord();
        builder.nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        builder.nieuwIdentificatienummersRecord(actieGeboorte).administratienummer(1234567890L).burgerservicenummer(123456789).eindeRecord();
        builder.nieuwInschrijvingRecord(actieGeboorte)
               .datumInschrijving(actieGeboorte.getDatumAanvangGeldigheid())
               .versienummer(1L)
               .datumtijdstempel(new Date(123))
               .eindeRecord();
        // TODO: uitzoeken waarom het hier fout gaat
        // builder.nieuwNaamgebruikRecord(actieGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN)
        // .eindeRecord();
        builder.nieuwAfgeleidAdministratiefRecord(actieGeboorte)
               .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
               .tijdstipLaatsteWijziging(actieGeboorte.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieGeboorte.getTijdstipRegistratie())
               .eindeRecord();
        builder.nieuwSamengesteldeNaamRecord(actieGeboorte)
               .geslachtsnaamstam(GESLACHTSNAAM)
               .voorvoegsel("de")
               .voornamen("Voornaam1 Voornaam2")
               .scheidingsteken(" ")
               .eindeRecord();
    }

    private void voegVerhuizingToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieVerhuizing) {
        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, HIS_VOLLEDIG_IMPL);

        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();

        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, HIS_VOLLEDIG_IMPL, adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actieVerhuizing)
                    .aangeverAdreshouding(I)
                    .datumAanvangAdreshouding(actieVerhuizing.getDatumAanvangGeldigheid())
                    .gemeente(Short.valueOf(GEMEENTE_518))
                    .huisnummer(46)
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("Pippelingstraat")
                    .postcode("2522HT")
                    .redenWijziging(P)
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam(ROTTERDAM)
                    .eindeRecord();
    }

    private void voegReisdocumentToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieReisdocument) {
        // Reisdocument
        builder.voegPersoonReisdocumentToe(
            new PersoonReisdocumentHisVolledigImplBuilder(
                new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut("PP"), null, null, null),
                true).nieuwStandaardRecord(actieReisdocument).eindeRecord().build());

        builder.nieuwAfgeleidAdministratiefRecord(actieReisdocument)
               .tijdstipLaatsteWijziging(actieReisdocument.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieReisdocument.getTijdstipRegistratie())
               .eindeRecord();
    }

    private void voegProbasToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieProbas) {
        // Reisdocument
        builder.voegPersoonIndicatieBijzondereVerblijfsrechtelijkePositieToe(
            new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder().nieuwStandaardRecord(actieProbas)
                                                                                             .waarde(Ja.J)
                                                                                             .eindeRecord()
                                                                                             .build());

        builder.nieuwAfgeleidAdministratiefRecord(actieProbas)
               .tijdstipLaatsteWijziging(actieProbas.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieProbas.getTijdstipRegistratie())
               .eindeRecord();
    }
}
