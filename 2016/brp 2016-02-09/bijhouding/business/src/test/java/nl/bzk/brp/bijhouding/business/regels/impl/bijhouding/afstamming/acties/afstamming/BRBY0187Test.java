/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.BetrokkenheidView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0187Test {

    private static final String      MOEDER_IDENTIFICERENDE_SLEUTEL = "123";

    @InjectMocks
    private final BRBY0187           brby0187                       = new BRBY0187();
    private PersoonBericht           berichtMoeder;
    private Map<String, PersoonView> bestaandeBetrokkenen;
    private ActieBericht             actie;

    @Before
    public void init() {
        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();

        final PersoonHisVolledigImpl moeder =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101)
                    .geslachtsaanduiding(Geslachtsaanduiding.VROUW).eindeRecord().build();

        final PersoonHisVolledigImpl broer =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwGeboorteRecord(20060409)
                    .datumGeboorte(20060409).eindeRecord().build();

        final PersoonHisVolledigImpl zus =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwGeboorteRecord(20090730)
                    .datumGeboorte(20090730).eindeRecord().build();

        final OuderHisVolledigImpl ouderBetr =
            new OuderHisVolledigImplBuilder(familie, moeder)
                    .nieuwOuderschapRecord(DatumAttribuut.vandaag().getWaarde(), null, DatumAttribuut.vandaag().getWaarde())
                    .eindeRecord().build();
        final KindHisVolledigImpl broerBetr = new KindHisVolledigImplBuilder(familie, broer).build();
        final KindHisVolledigImpl zusBetr = new KindHisVolledigImplBuilder(familie, zus).build();

        familie.getBetrokkenheden().add(ouderBetr);
        familie.getBetrokkenheden().add(broerBetr);
        familie.getBetrokkenheden().add(zusBetr);

        moeder.getBetrokkenheden().add(ouderBetr);

        bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(MOEDER_IDENTIFICERENDE_SLEUTEL, new PersoonView(moeder));

        berichtMoeder = new PersoonBericht();
        berichtMoeder.setIdentificerendeSleutel(MOEDER_IDENTIFICERENDE_SLEUTEL);

        actie = maakStandaardActie();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0187, brby0187.getRegel());
    }

    @Test
    public void testKindExactJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20120730
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20090730);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());

    }

    @Test
    public void testKind9MaandenJongerDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20120730
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20120730);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testKindExact9MaandenJongerDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20100601 (== exact 306 dgn)
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20100601);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);

        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testKind9MaandenMinEenDagJongerDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20100531 (== 305 dgn)
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20100531);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);

        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
        Assert.assertEquals(nieuweSituatie.getKindBetrokkenheid().getPersoon(), objectenDieDeRegelOvertreden.get(0));
    }

    @Test
    public void testKindIetsOuderDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20090531
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20090531);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);

        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
        Assert.assertEquals(nieuweSituatie.getKindBetrokkenheid().getPersoon(), objectenDieDeRegelOvertreden.get(0));
    }

    @Test
    public void testKind9MaandenOuderDanJongsteKind() throws Exception {
        // jongste kind is 20090730, kind is 20080831
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20080831);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);

        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testKindZonderDatumGeboorte() throws Exception {
        // jongste kind is 20090730, kind is 20080831
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20080831);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);

        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testRelatieZonderMoeder() throws Exception {
        // Er is WEL een ouder, maar deze ouder is GEEN adresgevend. ==> geen 'Moeder'
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20100531);
        // zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testKindZonderGeboorteDatum() throws Exception {
        // jongste kind is 20090730, kind is 20100531 (== 305 dgn)
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20080831);

        zetOuderMoederAlsOuwkiv(nieuweSituatie);
        verwijderKindDatumGeboorte();

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testZonderBestaandeBetrokkenen() throws Exception {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20100531);
        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        bestaandeBetrokkenen.clear();
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testMoederZonderKinderen() throws Exception {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20100531);

        zetOuderMoederAlsOuwkiv(nieuweSituatie);
        // wijzig de ouder rol, zodat we kunnen testen met een ouder zonder kinderen
        wijzigOuderRol();

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        // Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testKindZonderGeboorte() throws Exception {
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = maakNieuwSituatieMetKind(20100531);
        nieuweSituatie.getKindBetrokkenheid().getPersoon().setGeboorte(null);

        zetOuderMoederAlsOuwkiv(nieuweSituatie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
            brby0187.voerRegelUit(null, nieuweSituatie, actie, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    /**
     * Zet moeder als indicatie ouder uit wie kind is voortgekomen.
     *
     * @param nieuweSituatie de nieuwe situatie
     */
    private void zetOuderMoederAlsOuwkiv(final FamilierechtelijkeBetrekkingBericht nieuweSituatie) {
        // zoek binnen deze relatie, en zet de ouder met bsn: MOEDER_BSN and IndicatieOuderUitWieKindIsVoortgekomen.
        for (final OuderBericht betr : nieuweSituatie.getOuderBetrokkenheden()) {
            if (betr.getPersoon().getIdentificerendeSleutel().equals(MOEDER_IDENTIFICERENDE_SLEUTEL)) {
                betr.setOuderschap(new OuderOuderschapGroepBericht());
                betr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);
            }
        }
    }

    private void verwijderKindDatumGeboorte() {
        final PersoonView moeder = bestaandeBetrokkenen.get(MOEDER_IDENTIFICERENDE_SLEUTEL);
        for (final BetrokkenheidView moederBetrokkenheid : moeder.getBetrokkenheden()) {
            if (moederBetrokkenheid.getRelatie().getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
                && moederBetrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.OUDER)
            {
                for (final BetrokkenheidView kindBetrokkenheid : moederBetrokkenheid.getRelatie().getBetrokkenheden()) {
                    if (kindBetrokkenheid.getPersoon().getGeboorte() != null) {
                        ReflectionTestUtils.setField(kindBetrokkenheid.getPersoon().getGeboorte(), "datumGeboorte", null);
                        return;
                    }
                }
            }
        }
    }

    private void wijzigOuderRol() {
        final PersoonView moeder = bestaandeBetrokkenen.get(MOEDER_IDENTIFICERENDE_SLEUTEL);
        for (final BetrokkenheidView moederBetrokkenheid : moeder.getBetrokkenheden()) {
            ReflectionTestUtils.setField(moederBetrokkenheid.getRol(), "waarde", SoortBetrokkenheid.DUMMY);
        }
    }

    /**
     * Maakt een nieuwe situatie met kind.
     *
     * @param geboorteDatum de geboorte datum van het te maken kind
     * @return het familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht maakNieuwSituatieMetKind(final Integer geboorteDatum) {
        final PersoonBericht nieuwKind =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 666666666, Geslachtsaanduiding.MAN, geboorteDatum,
                    StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), "kind", "van", "Houten");

        return new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>().bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtMoeder).voegKindToe(nieuwKind).getRelatie();
    }

    /**
     * Maakt een standaard actie (registratie naamgebruik).
     *
     * @return het actie bericht
     */
    private ActieBericht maakStandaardActie() {
        final PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new BurgerservicenummerAttribuut("123"));

        final PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        final PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        final List<PersoonAdresBericht> adressen = new ArrayList<>();
        adressen.add(persoonAdres);

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(adressen);
        persoon.setIdentificatienummers(pin);

        final ActieBericht actieBericht = new ActieRegistratieNaamgebruikBericht();
        final Integer datumAanvangGeldigheid = 20010101;
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        actieBericht.setRootObject(persoon);

        return actieBericht;
    }

}
