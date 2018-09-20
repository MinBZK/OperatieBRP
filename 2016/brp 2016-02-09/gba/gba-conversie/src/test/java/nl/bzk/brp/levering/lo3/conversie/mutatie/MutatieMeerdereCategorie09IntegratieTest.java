/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import support.PersoonHisVolledigUtil;

/**
 * Kind.
 */
public class MutatieMeerdereCategorie09IntegratieTest extends AbstractMutatieConverteerderIntegratieTest {

    @Test
    public void test() {
        final ActieModel actieKinderenGeboorte =
                PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        // Kind01
        final PersoonHisVolledigImplBuilder kind01Builder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);
        kind01Builder.nieuwGeboorteRecord(actieKinderenGeboorte)
                     .datumGeboorte(actieKinderenGeboorte.getDatumAanvangGeldigheid())
                     .gemeenteGeboorte((short) 518)
                     .landGebiedGeboorte((short) 6030)
                     .eindeRecord();
        kind01Builder.nieuwGeslachtsaanduidingRecord(actieKinderenGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        kind01Builder.nieuwIdentificatienummersRecord(actieKinderenGeboorte).administratienummer(1231231234L).burgerservicenummer(345345345).eindeRecord();
        kind01Builder.nieuwInschrijvingRecord(actieKinderenGeboorte)
                     .datumInschrijving(actieKinderenGeboorte.getDatumAanvangGeldigheid())
                     .versienummer(1L)
                     .datumtijdstempel(new Date(123456))
                     .eindeRecord();
        kind01Builder.nieuwNaamgebruikRecord(actieKinderenGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord();
        kind01Builder.nieuwAfgeleidAdministratiefRecord(actieKinderenGeboorte)
                     .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
                     .tijdstipLaatsteWijziging(actieKinderenGeboorte.getTijdstipRegistratie())
                     .tijdstipLaatsteWijzigingGBASystematiek(actieKinderenGeboorte.getTijdstipRegistratie())
                     .eindeRecord();
        kind01Builder.nieuwSamengesteldeNaamRecord(actieKinderenGeboorte)
                     .geslachtsnaamstam("Trommelen")
                     .voorvoegsel("van")
                     .voornamen("Pimmetje")
                     .scheidingsteken(" ")
                     .eindeRecord();

        final PersoonHisVolledigImpl kind01 = kind01Builder.build();
        final KindHisVolledigImplBuilder gerelateerdeKind01BetrokkendheidBuilder = new KindHisVolledigImplBuilder(null, kind01);
        final KindHisVolledigImpl gerelateerdeKind01Betrokkendheid = gerelateerdeKind01BetrokkendheidBuilder.build();

        final FamilierechtelijkeBetrekkingHisVolledigImplBuilder relatie01Builder = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder();
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie01 = relatie01Builder.build();
        relatie01.getBetrokkenheden().add(gerelateerdeKind01Betrokkendheid);

        final OuderHisVolledigImplBuilder mijnOuderBetrokkenheid01Builder = new OuderHisVolledigImplBuilder(relatie01, null);
        builder.voegBetrokkenheidToe(mijnOuderBetrokkenheid01Builder.build());

        // Kind02
        final PersoonHisVolledigImplBuilder kind02Builder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);
        kind02Builder.nieuwGeboorteRecord(actieKinderenGeboorte)
                     .datumGeboorte(actieKinderenGeboorte.getDatumAanvangGeldigheid())
                     .gemeenteGeboorte((short) 518)
                     .landGebiedGeboorte((short) 6030)
                     .eindeRecord();
        kind02Builder.nieuwGeslachtsaanduidingRecord(actieKinderenGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        kind02Builder.nieuwIdentificatienummersRecord(actieKinderenGeboorte).administratienummer(1231231235L).burgerservicenummer(345345346).eindeRecord();
        kind02Builder.nieuwInschrijvingRecord(actieKinderenGeboorte)
                     .datumInschrijving(actieKinderenGeboorte.getDatumAanvangGeldigheid())
                     .versienummer(1L)
                     .datumtijdstempel(new Date(123456))
                     .eindeRecord();
        kind02Builder.nieuwNaamgebruikRecord(actieKinderenGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord();
        kind02Builder.nieuwAfgeleidAdministratiefRecord(actieKinderenGeboorte)
                     .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
                     .tijdstipLaatsteWijziging(actieKinderenGeboorte.getTijdstipRegistratie())
                     .tijdstipLaatsteWijzigingGBASystematiek(actieKinderenGeboorte.getTijdstipRegistratie())
                     .eindeRecord();
        kind02Builder.nieuwSamengesteldeNaamRecord(actieKinderenGeboorte)
                     .geslachtsnaamstam("Trommelen")
                     .voorvoegsel("van")
                     .voornamen("Pommetje")
                     .scheidingsteken(" ")
                     .eindeRecord();

        final PersoonHisVolledigImpl kind02 = kind02Builder.build();
        final KindHisVolledigImplBuilder gerelateerdeKind02BetrokkendheidBuilder = new KindHisVolledigImplBuilder(null, kind02);
        final KindHisVolledigImpl gerelateerdeKind02Betrokkendheid = gerelateerdeKind02BetrokkendheidBuilder.build();

        final FamilierechtelijkeBetrekkingHisVolledigImplBuilder relatie02Builder = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder();
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie02 = relatie02Builder.build();
        relatie02.getBetrokkenheden().add(gerelateerdeKind02Betrokkendheid);

        final OuderHisVolledigImplBuilder mijnOuderBetrokkenheid02Builder = new OuderHisVolledigImplBuilder(relatie02, null);
        builder.voegBetrokkenheidToe(mijnOuderBetrokkenheid02Builder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actieKinderenGeboorte);

        Assert.assertEquals(4, resultaat.size());
    }

}
