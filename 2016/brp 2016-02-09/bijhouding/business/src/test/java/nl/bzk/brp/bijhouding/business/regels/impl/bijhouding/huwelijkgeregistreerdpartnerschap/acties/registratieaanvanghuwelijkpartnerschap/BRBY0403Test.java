/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratieaanvanghuwelijkpartnerschap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AttribuutAdministratieTestConfig.class })
public class BRBY0403Test {

    private final BRBY0403 brby0403 = new BRBY0403();

    @Test
    public void testBeidePartnersNietOnderCuratele() {
        final PersoonBericht manBericht =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 1111, null, 19840404, null, null, null, null, null);
        manBericht.setIdentificerendeSleutel("technischeSleutel=1111");
        final PersoonBericht vrouwBericht =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 2222, null, 19850404, null, null, null, null, null);
        vrouwBericht.setIdentificerendeSleutel("technischeSleutel=2222");
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijk(manBericht, vrouwBericht);

        final Map<String, PersoonView> bestaandeBetrokkenen = maakBestaandeSituatie(null, null);

        final List<BerichtEntiteit> meldingen = brby0403.voerRegelUit(null, huwelijk, null, bestaandeBetrokkenen);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testManOnderCuratele() {
        final PersoonBericht manBericht =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 1111, null, 19840404, null, null, null, null, null);
        manBericht.setIdentificerendeSleutel("technischeSleutel=1111");
        final PersoonBericht vrouwBericht =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 2222, null, 19850404, null, null, null, null, null);
        vrouwBericht.setIdentificerendeSleutel("technischeSleutel=2222");
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijk(manBericht, vrouwBericht);

        final Map<String, PersoonView> bestaandeBetrokkenen = maakBestaandeSituatie(
                SoortIndicatie.INDICATIE_ONDER_CURATELE, null);

        final List<BerichtEntiteit> meldingen = brby0403.voerRegelUit(null, huwelijk, null, bestaandeBetrokkenen);
        Assert.assertTrue(meldingen.size() == 1);
        Assert.assertEquals(manBericht, meldingen.get(0));
    }

    @Test
    public void testManEnVrouwOnderCuratele() {
        final PersoonBericht manBericht =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 1111, null, 19840404, null, null, null, null, null);
        manBericht.setIdentificerendeSleutel("technischeSleutel=1111");
        final PersoonBericht vrouwBericht =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 2222, null, 19850404, null, null, null, null, null);
        vrouwBericht.setIdentificerendeSleutel("technischeSleutel=2222");
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijk(manBericht, vrouwBericht);

        final Map<String, PersoonView> bestaandeBetrokkenen = maakBestaandeSituatie(
                SoortIndicatie.INDICATIE_ONDER_CURATELE,
                SoortIndicatie.INDICATIE_ONDER_CURATELE);

        final List<BerichtEntiteit> meldingen = brby0403.voerRegelUit(null, huwelijk, null, bestaandeBetrokkenen);
        Assert.assertTrue(meldingen.size() == 2);
        Assert.assertEquals(manBericht, meldingen.get(0));
        Assert.assertEquals(vrouwBericht, meldingen.get(1));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0403, brby0403.getRegel());
    }

    /**
     * Maakt een standaard huwelijk.
     *
     * @param persoon1 persoon 1
     * @param persoon2 persoon 2
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijk(final PersoonBericht persoon1,
                                                                  final PersoonBericht persoon2)
    {
        return new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie().
                voegPartnerToe(persoon1).
                voegPartnerToe(persoon2).
                setDatumAanvang(20010404).
                getRelatie();
    }


    /**
     * Maakt een bestaande situatie.
     *
     * @param manIndicatie   indicatie van man
     * @param vrouwIndicatie indicatie van vrouw
     * @return bestaande situatie
     */
    private Map<String, PersoonView> maakBestaandeSituatie(final SoortIndicatie manIndicatie,
                                                                      final SoortIndicatie vrouwIndicatie)
    {
        final ActieModel actieModel = creeerActie();

        final PersoonView manModel = maakBestaandPersoon(actieModel, manIndicatie);
        final PersoonView vrouwModel = maakBestaandPersoon(actieModel, vrouwIndicatie);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("technischeSleutel=1111", manModel);
        bestaandeBetrokkenen.put("technischeSleutel=2222", vrouwModel);

        return bestaandeBetrokkenen;
    }

    /**
     * Maakt een PersoonView.
     *
     * @param actieModel actieModel
     * @param indicatie  indicatie
     * @return PersoonView
     */
    private PersoonView maakBestaandPersoon(final ActieModel actieModel, final SoortIndicatie soortIndicatie) {
        final PersoonHisVolledigImpl persoonHisVol =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        if (soortIndicatie != null) {
            final PersoonIndicatieOnderCurateleHisVolledigImpl indicatie =
                    new PersoonIndicatieOnderCurateleHisVolledigImplBuilder()
                            .nieuwStandaardRecord(actieModel).waarde(Ja.J).eindeRecord().build();
            persoonHisVol.setIndicatieOnderCuratele(indicatie);
        }

        return new PersoonView(persoonHisVol);
    }

    /**
     * Creeert een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND), null,
                        null, null), null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);
    }
}
