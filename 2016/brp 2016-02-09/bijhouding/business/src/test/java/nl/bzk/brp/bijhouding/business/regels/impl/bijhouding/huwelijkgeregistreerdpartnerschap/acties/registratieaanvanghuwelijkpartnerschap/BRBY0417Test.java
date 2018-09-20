/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratieaanvanghuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AttribuutAdministratieTestConfig.class })
public class BRBY0417Test {

    private static final String BSN_PERSOON_1 = "123456789";
    private static final String BSN_PERSOON_2 = "123456780";
    private BRBY0417 brby0417;

    @Before
    public void init() {
        brby0417 = new BRBY0417();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0417, brby0417.getRegel());
    }

    @Test
    public void testGeenTechnischeSleutel() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(null, null);

        final List<BerichtEntiteit> resultaat = brby0417.voerRegelUit(null, relatieBericht, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenPersonen() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, false, false);

        final List<BerichtEntiteit> resultaat = brby0417.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testNietGehuwdePersonen() {
        // 0 personen gehuwd
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, false);

        final List<BerichtEntiteit> resultaat = brby0417.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testAlGehuwdePersonen() {
        // 2 personen gehuwd
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true);

        final List<BerichtEntiteit> resultaat = brby0417.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(2, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertTrue(resultaat.get(1) instanceof PersoonBericht);
    }

    @Test
    public void testAlGehuwdePersoon() {
        // 1 persoon gehuwd
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenenMetAfwijkendGehuwd(BSN_PERSOON_1, BSN_PERSOON_2, true, true);

        final List<BerichtEntiteit> resultaat = brby0417.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertEquals(BSN_PERSOON_1, ((PersoonBericht) resultaat.get(0)).getIdentificerendeSleutel());
    }

    /**
     * Maakt een relatie bericht voor huwelijk geregistreerd partnerschap.
     *
     * @param bsn1 bsn persoon 1
     * @param bsn2 bsn persoon 2
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakRelatie(final String bsn1, final String bsn2) {
        final PersoonBericht persoon1 = maakPersoonBericht(bsn1);
        final PersoonBericht persoon2 = maakPersoonBericht(bsn2);

        final PartnerBericht partner1 = new PartnerBericht();
        partner1.setPersoon(persoon1);

        final PartnerBericht partner2 = new PartnerBericht();
        partner2.setPersoon(persoon2);

        final List<BetrokkenheidBericht> partners = new ArrayList<>();
        partners.add(partner1);
        partners.add(partner2);

        final RelatieStandaardGroepBericht relatieGegevens = new RelatieStandaardGroepBericht();
        relatieGegevens.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20120303));

        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = new HuwelijkBericht();
        relatieBericht.setStandaard(relatieGegevens);
        relatieBericht.setBetrokkenheden(partners);

        return relatieBericht;
    }

    /**
     * Maakt een persoon bericht op basis van een bsn
     *
     * @param bsn bsn
     * @return persoon bericht
     */
    private PersoonBericht maakPersoonBericht(final String bsn) {
        final PersoonIdentificatienummersGroepBericht idGroep = new PersoonIdentificatienummersGroepBericht();
        if (bsn != null) {
            idGroep.setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
        }

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(idGroep);
        persoon.setIdentificerendeSleutel(bsn);

        return persoon;
    }

    /**
     * Maak bestaande betrokkenen.
     *
     * @param bsn1               bsn persoon 1
     * @param bsn2               bsn persoon 2
     * @param zijnIngeschrevenen personen zijn ingeschrevenen
     * @param zijnAlGehuwd       personen zijn al gehuwd
     * @return map met betrokkenen
     */
    private Map<String, PersoonView> maakBestaandeBetrokkenen(final String bsn1, final String bsn2,
                                                                         final boolean zijnIngeschrevenen,
                                                                         final boolean zijnAlGehuwd)
    {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();

        final PersoonView persoonView1 = maakPersoonView(zijnIngeschrevenen, zijnAlGehuwd);
        final PersoonView persoonView2 = maakPersoonView(zijnIngeschrevenen, zijnAlGehuwd);

        bestaandeBetrokkenen.put(bsn1, persoonView1);
        bestaandeBetrokkenen.put(bsn2, persoonView2);

        return bestaandeBetrokkenen;
    }

    /**
     * Maak bestaande betrokkenen waarbij gehuwde status afwijkt.
     *
     * @param bsn1               bsn persoon 1
     * @param bsn2               bsn persoon 2
     * @param zijnIngeschrevenen personen zijn ingeschrevenen
     * @param zijnAlGehuwd       personen zijn al gehuwd
     * @return map met betrokkenen
     */
    private Map<String, PersoonView> maakBestaandeBetrokkenenMetAfwijkendGehuwd(final String bsn1,
                                                                                           final String bsn2,
                                                                                           final boolean
                                                                                                   zijnIngeschrevenen,
                                                                                           final boolean zijnAlGehuwd)
    {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();

        final PersoonView persoonView1 = maakPersoonView(zijnIngeschrevenen, zijnAlGehuwd);
        final PersoonView persoonView2 = maakPersoonView(zijnIngeschrevenen, !zijnAlGehuwd);

        bestaandeBetrokkenen.put(bsn1, persoonView1);
        bestaandeBetrokkenen.put(bsn2, persoonView2);

        return bestaandeBetrokkenen;
    }

    /**
     * Maakt een persoon his volledig view op basis van een bsn. De status ingeschreven kan via een boolean worden
     * meegegeven. Ook kan de persoon partner zijn in een huwelijk of geregistreerd partnerschap via een boolean.
     *
     * @param isIngeschrevene persoon is ingeschrevene
     * @param isAlGehuwd      persoon is al gehuwd
     * @return persoon his volledig view
     */
    private PersoonView maakPersoonView(final boolean isIngeschrevene,
                                                   final boolean isAlGehuwd)
    {
        SoortPersoon soortPersoon = null;
        if (isIngeschrevene) {
            soortPersoon = SoortPersoon.INGESCHREVENE;
        }

        final PersoonHisVolledigImpl persoonHisVolledigImpl = new PersoonHisVolledigImplBuilder(soortPersoon)
                .build();

        if (isAlGehuwd) {
            final HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijkHisVolledig =
                    new HuwelijkHisVolledigImplBuilder()
                            .nieuwStandaardRecord(20110101)
                            .eindeRecord()
                            .build();

            final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = new HashSet<>();
            final BetrokkenheidHisVolledigImpl partnerBetrokkenheid =
                    new PartnerHisVolledigImpl(huwelijkHisVolledig, persoonHisVolledigImpl);
            betrokkenheden.add(partnerBetrokkenheid);
            persoonHisVolledigImpl.setBetrokkenheden(betrokkenheden);
        }

        return new PersoonView(persoonHisVolledigImpl);
    }
}
