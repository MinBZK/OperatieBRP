/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0001;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0134Test {

    private static final DatumEvtDeelsOnbekendAttribuut DATUM_AANVANG_GELDIGHEID =
        new DatumEvtDeelsOnbekendAttribuut(20120101);
    private static final Integer                        BROER_BSN                = 444444444;
    private static final Integer                        MOEDER_BSN               = 111111111;
    private static final Integer                        OPA_BSN                  = 333333333;
    private static final Integer                        VADER_BSN                = 222222222;
    private static final String                         ID                       = "iD";

    private BRBY0134 brby0134 = new BRBY0134();

    @Mock
    private BRBY0001 brby0001;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        brby0134 = new BRBY0134();

        ReflectionTestUtils.setField(brby0134, "brby0001", brby0001);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0134, brby0134.getRegel());
    }

    @Test
    public void testVoerRegelUitZonderVerwantschap() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht();

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        final PersoonView persoonViewMoeder = new PersoonView(maakMoeder(false, false));
        final PersoonView persoonViewVader = new PersoonView(maakVader(false, false));
        bestaandeBetrokkenen.put("" + MOEDER_BSN, persoonViewMoeder);
        bestaandeBetrokkenen.put("" + VADER_BSN, persoonViewVader);

        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
            Matchers.any(PersoonView.class))).thenReturn(false);

        final List<BerichtEntiteit> resultaat = brby0134.voerRegelUit(null, nieuweSituatie, maakActie(),
            bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetNietIngeschrevene() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie
            = maakFamilierechtelijkeBetrekkingBerichtMetNietIngeschrevene();

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("" + MOEDER_BSN, new PersoonView(maakMoeder(false, false)));
        bestaandeBetrokkenen.put("" + VADER_BSN, new PersoonView(maakVader(false, false)));

        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
            Matchers.any(PersoonView.class))).thenReturn(false);

        final List<BerichtEntiteit> resultaat = brby0134.voerRegelUit(null, nieuweSituatie, maakActie(),
            bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetVerwantschap() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht();

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("" + MOEDER_BSN, new PersoonView(maakMoeder(true, false)));
        bestaandeBetrokkenen.put("" + VADER_BSN, new PersoonView(maakVader(true, false)));

        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
                Matchers.any(PersoonView.class))).thenReturn(true);

        final List<BerichtEntiteit> resultaat = brby0134.voerRegelUit(null, nieuweSituatie, maakActie(),
                bestaandeBetrokkenen);

        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testVoerRegelUitUitzonderingEerderKind() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht();

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("" + MOEDER_BSN, new PersoonView(maakMoeder(false, true)));
        bestaandeBetrokkenen.put("" + VADER_BSN, new PersoonView(maakVader(false, true)));

        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
                Matchers.any(PersoonView.class))).thenReturn(false);

        final List<BerichtEntiteit> resultaat = brby0134.voerRegelUit(null, nieuweSituatie, maakActie(),
                bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een familierechtelijke betrekking bericht.
     *
     * @return het familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht maakFamilierechtelijkeBetrekkingBericht() {
        return new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>()
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(maakMoederBericht())
                .voegOuderToe(maakVaderBericht(SoortPersoon.INGESCHREVENE))
                .voegKindToe(maakKindBericht())
                .getRelatie();
    }

    /**
     * Maakt een familierechtelijke betrekking bericht.
     *
     * @return het familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht maakFamilierechtelijkeBetrekkingBerichtMetNietIngeschrevene() {
        return new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>()
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(maakMoederBericht())
                .voegOuderToe(maakVaderBericht(SoortPersoon.NIET_INGESCHREVENE))
                .voegKindToe(maakKindBericht())
                .getRelatie();
    }

    /**
     * Maakt een moeder persoon bericht.
     *
     * @return het persoon bericht
     */
    private PersoonBericht maakMoederBericht() {
        final PersoonBericht berichtMoeder = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, MOEDER_BSN,
                Geslachtsaanduiding.VROUW, 19360408,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "moeder", "van", "Houten");
        berichtMoeder.setCommunicatieID("id.moeder.pers");
        berichtMoeder.getIdentificatienummers().setCommunicatieID("id.moeder.pers.ids");
        berichtMoeder.getGeboorte().setCommunicatieID("id.moeder.pers.geboorte");
        berichtMoeder.getGeslachtsaanduiding().setCommunicatieID("id.moeder.pers.geslacht");
        return berichtMoeder;
    }

    /**
     * Maakt een vader persoon bericht.
     *
     * @return het persoon bericht
     */
    private PersoonBericht maakVaderBericht(final SoortPersoon soortPersoon) {
        final PersoonBericht berichtVader = PersoonBuilder.bouwPersoon(soortPersoon, VADER_BSN,
                Geslachtsaanduiding.MAN, 19261225,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "vader", "van", "Houten");
        berichtVader.setCommunicatieID("id.moeder.pers");
        berichtVader.getIdentificatienummers().setCommunicatieID("id.vader.pers.ids");
        berichtVader.getGeboorte().setCommunicatieID("id.vader.pers.geboorte");
        berichtVader.getGeslachtsaanduiding().setCommunicatieID("id.vader.pers.geslacht");
        return berichtVader;
    }

    /**
     * Maakt een kind persoon bericht.
     *
     * @return het persoon bericht
     */
    private PersoonBericht maakKindBericht() {
        final PersoonBericht berichtKind = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 666666666,
                Geslachtsaanduiding.MAN, 20120730,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "kind", "van", "Houten");
        berichtKind.setCommunicatieID("id.moeder.pers");
        berichtKind.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        berichtKind.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        berichtKind.getGeslachtsaanduiding().setCommunicatieID("id.kind.pers.geslacht");
        return berichtKind;
    }

    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel maakActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                        null, null), null, DATUM_AANVANG_GELDIGHEID, null, DatumTijdAttribuut.nu(), null);
    }

    /**
     * Maakt een moeder.
     *
     * @return de moeder als persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakMoeder(final Boolean heeftFamilieRechtelijkeBetrekking,
                                              final Boolean heeftEerderKind)
    {
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(maakActie())
                .voornamen("Mama")
                .geslachtsnaamstam("Moedersen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(moeder, ID, MOEDER_BSN);

        if (heeftFamilieRechtelijkeBetrekking) {
            final FamilierechtelijkeBetrekkingHisVolledigImpl relatie
                = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            relatie.getBetrokkenheden().add(new KindHisVolledigImpl(relatie, maakOpa()));
            moeder.getBetrokkenheden().add(new KindHisVolledigImpl(relatie, maakOpa()));
        }

        if (heeftEerderKind) {
            final FamilierechtelijkeBetrekkingHisVolledigImpl relatie
                = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            relatie.getBetrokkenheden().add(new OuderHisVolledigImpl(relatie, maakEerderKind()));
            moeder.getBetrokkenheden().add(new OuderHisVolledigImpl(relatie, maakEerderKind()));
        }

        return moeder;
    }

    /**
     * Maakt een vader.
     *
     * @return de vader als persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakVader(final Boolean heeftFamilieRechtelijkeBetrekking,
                                             final Boolean heeftEerderKind)
    {
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(maakActie())
                .voornamen("Papa")
                .geslachtsnaamstam("Vadersen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(vader, ID, VADER_BSN);

        if (heeftFamilieRechtelijkeBetrekking) {
            final FamilierechtelijkeBetrekkingHisVolledigImpl relatie
                = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            relatie.getBetrokkenheden().add(new KindHisVolledigImpl(relatie, maakOpa()));
            vader.getBetrokkenheden().add(new KindHisVolledigImpl(relatie, maakOpa()));
        }

        if (heeftEerderKind) {
            final FamilierechtelijkeBetrekkingHisVolledigImpl relatie
                = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            relatie.getBetrokkenheden().add(new OuderHisVolledigImpl(relatie, maakEerderKind()));
            vader.getBetrokkenheden().add(new OuderHisVolledigImpl(relatie, maakEerderKind()));
        }

        return vader;
    }

    /**
     * Maakt een gezamenlijke vader voor vader en moeder (opa van het kind).
     *
     * @return de opa als persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakOpa() {
        final PersoonHisVolledigImpl opa = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(maakActie())
                .voornamen("Opa")
                .geslachtsnaamstam("Vadersen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(opa, ID, OPA_BSN);

        return opa;
    }

    /**
     * Maakt een gezamenlijke vader voor vader en moeder (opa van het kind).
     *
     * @return de opa als persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakEerderKind() {
        final PersoonHisVolledigImpl oudereBroer = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(maakActie())
                .voornamen("OudereBroer")
                .geslachtsnaamstam("Vadersen")
                .eindeRecord()
                .build();

        ReflectionTestUtils.setField(oudereBroer, ID, BROER_BSN);

        return oudereBroer;
    }
}
