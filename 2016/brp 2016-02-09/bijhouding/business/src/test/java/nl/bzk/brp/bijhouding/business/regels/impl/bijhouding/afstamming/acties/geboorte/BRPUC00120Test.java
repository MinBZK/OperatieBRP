/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
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
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRPUC00120Test {

    private static final DatumEvtDeelsOnbekendAttribuut DATUM_AANVANG_GELDIGHEID =
            new DatumEvtDeelsOnbekendAttribuut(20120101);
    private static final Integer MOEDER_BSN = 111111111;
    private static final boolean HEEFT_ADRES = true;
    private static final boolean HEEFT_GEEN_ADRES = false;

    private BRPUC00120 brpuc00120;

    @Before
    public void init() {
        brpuc00120 = new BRPUC00120();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRPUC00120, brpuc00120.getRegel());
    }

    @Test
    public void testVoerRegelUit() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht(true);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("ts" + MOEDER_BSN, new PersoonView(maakMoeder(20110101, HEEFT_ADRES)));

        final List<BerichtEntiteit> resultaat =
            brpuc00120.voerRegelUit(null, nieuweSituatie, maakActie(), bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitAanvangAdresHoudingLaterDanDatumGeboorteKind() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht(true);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("ts" + MOEDER_BSN, new PersoonView(maakMoeder(20130101, HEEFT_ADRES)));

        final List<BerichtEntiteit> resultaat =
            brpuc00120.voerRegelUit(null, nieuweSituatie, maakActie(), bestaandeBetrokkenen);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertEquals(nieuweSituatie.getKindBetrokkenheid().getPersoon(), resultaat.get(0));
    }

    @Test
    public void testVoerRegelUitGeenOuwkiv() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht(false);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("ts" + MOEDER_BSN, new PersoonView(maakMoeder(20130101, HEEFT_ADRES)));

        final List<BerichtEntiteit> resultaat =
            brpuc00120.voerRegelUit(null, nieuweSituatie, maakActie(), bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetLegeBestaandeBetrokkenen() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht(true);

        final List<BerichtEntiteit> resultaat =
            brpuc00120.voerRegelUit(null, nieuweSituatie, maakActie(), new HashMap<String, PersoonView>());

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitGeenOuwkivAdres() {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakFamilierechtelijkeBetrekkingBericht(true);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("ts" + MOEDER_BSN, new PersoonView(maakMoeder(20130101, HEEFT_GEEN_ADRES)));

        final List<BerichtEntiteit> resultaat =
            brpuc00120.voerRegelUit(null, nieuweSituatie, maakActie(), bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een familierechtelijke betrekking bericht.
     *
     * @return het familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht maakFamilierechtelijkeBetrekkingBericht(final Boolean isOuwkiv) {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
            relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(maakMoederBericht())
                    .voegKindToe(maakKindBericht()).getRelatie();

        if (isOuwkiv) {
            nieuweSituatie.getOuderBetrokkenheden().iterator().next().getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);
        }

        return nieuweSituatie;
    }

    /**
     * Maakt een moeder voor de bestaande betrokkenen met een adres met instelbare aanvang adreshouding.
     *
     * @param aanvangAdresHouding de aanvang adres houding van het adres
     * @return de moeder als persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakMoeder(final Integer aanvangAdresHouding, final boolean heeftAdres) {
        final PersoonHisVolledigImpl moeder =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwSamengesteldeNaamRecord(maakActie())
                    .voornamen("Mama").geslachtsnaamstam("Moedersen").eindeRecord().build();

        if (heeftAdres) {
            final PersoonAdresHisVolledigImpl adres =
                new PersoonAdresHisVolledigImplBuilder(moeder).nieuwStandaardRecord(20110101, null, 20110101)
                        .datumAanvangAdreshouding(aanvangAdresHouding)
                        .gemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde()).naamOpenbareRuimte("nor")
                        .huisnummer(1).eindeRecord().build();

            moeder.getAdressen().add(adres);
        }
        return moeder;
    }

    /**
     * Maakt een moeder persoon bericht.
     *
     * @return het persoon bericht
     */
    private PersoonBericht maakMoederBericht() {
        final PersoonBericht berichtMoeder =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, MOEDER_BSN, Geslachtsaanduiding.VROUW, 19360408,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), "moeder", "van", "Houten");
        berichtMoeder.setIdentificerendeSleutel("ts" + MOEDER_BSN);
        berichtMoeder.setCommunicatieID("id.moeder.pers");
        berichtMoeder.getIdentificatienummers().setCommunicatieID("id.moeder.pers.ids");
        berichtMoeder.getGeboorte().setCommunicatieID("id.moeder.pers.geboorte");
        berichtMoeder.getGeslachtsaanduiding().setCommunicatieID("id.moeder.pers.geslacht");
        return berichtMoeder;
    }

    /**
     * Maakt een kind persoon bericht.
     *
     * @return het persoon bericht
     */
    private PersoonBericht maakKindBericht() {
        final PersoonBericht k =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 666666666, Geslachtsaanduiding.MAN, 20120730,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), "kind", "van", "Houten");
        k.setCommunicatieID("id.moeder.pers");
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        k.getGeslachtsaanduiding().setCommunicatieID("id.kind.pers.geslacht");
        return k;
    }

    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel maakActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null, null, null), null,
                DATUM_AANVANG_GELDIGHEID, null, DatumTijdAttribuut.nu(), null);
    }
}
