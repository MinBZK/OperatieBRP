/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieStandaardGroepBericht;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

public class AdministratieveHandelingDeltaPredikaatTest {

    private static final String ID = "iD";

    /**
     * - ActieInhoud hoort bij de Administratieve handeling en ActieAanpassingGeldigheid is leeg
     */
    @Test
    public void testActieInhoudIsOnderdeelVanHandelingEnActieGeldigheidIsLeeg() {
        final AdministratieveHandelingModel handelingVoorPredikaat =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
                        null, null, null);
        ReflectionTestUtils.setField(handelingVoorPredikaat, ID, 1L);

        final AdministratieveHandelingModel handeling2 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(
                        SoortAdministratieveHandeling.AANVANG_ONDERZOEK,
                        null, null, null);
        ReflectionTestUtils.setField(handeling2, ID, 2L);

        final ActieModel actieVoorPredikaat = VerantwoordingTestUtil
                .maakActie(handelingVoorPredikaat, SoortActie.BEEINDIGING_OUDERSCHAP, null, null, null, null, null, null);

        final ActieModel actieBijHandeling2 = VerantwoordingTestUtil
                .maakActie(handeling2, SoortActie.BEEINDIGING_OUDERSCHAP, null, null, null, null, null, null);

        //TRUE
        final FormeelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidLeeg =
                Mockito.mock(FormeelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidLeeg.getVerantwoordingInhoud()).thenReturn(actieVoorPredikaat);

        //FALSE
        final MaterieelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNietLeeg =
                Mockito.mock(MaterieelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNietLeeg.getVerantwoordingInhoud()).thenReturn(actieVoorPredikaat);
        Mockito.when(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNietLeeg.getVerantwoordingAanpassingGeldigheid()).thenReturn(actieBijHandeling2);


        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(1L);

        final List<FormeelVerantwoordbaar<ActieModel>> historie = new ArrayList<>();
        historie.add(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidLeeg);
        historie.add(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNietLeeg);

        Assert.assertEquals(2, historie.size());
        CollectionUtils.filter(historie, predikaat);

        Assert.assertEquals(1, historie.size());

        Assert.assertEquals(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidLeeg, historie.get(0));
    }

    /**
     * - ActieAanpassinGeldigheid hoort bij de Administratieve handeling
     */
    @Test
    public void actieGeldigheidIsOnderdeelVanHandeling() {
        final AdministratieveHandelingModel handelingVoorPredikaat =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
                        null, null, null);
        ReflectionTestUtils.setField(handelingVoorPredikaat, ID, 1L);

        final AdministratieveHandelingModel handeling2 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(
                        SoortAdministratieveHandeling.AANVANG_ONDERZOEK,
                        null, null, null);
        ReflectionTestUtils.setField(handeling2, ID, 2L);

        final ActieModel actieVoorPredikaat = VerantwoordingTestUtil
                .maakActie(handelingVoorPredikaat, SoortActie.BEEINDIGING_OUDERSCHAP, null, null, null, null, null, null);

        final ActieModel actieBijHandeling2 = VerantwoordingTestUtil
                .maakActie(handeling2, SoortActie.BEEINDIGING_OUDERSCHAP, null, null, null, null, null, null);

        //TRUE
        final MaterieelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieGeldigheidGekoppeldAanHandeling =
                Mockito.mock(MaterieelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieGeldigheidGekoppeldAanHandeling.getVerantwoordingInhoud()).thenReturn(actieBijHandeling2);
        Mockito.when(groepsVoorkomenMetActieGeldigheidGekoppeldAanHandeling.getVerantwoordingAanpassingGeldigheid()).thenReturn(actieVoorPredikaat);

        //TRUE
        final MaterieelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieInhoudNietGekoppeldAanHandelingEnActieGeldigheidWel =
                Mockito.mock(MaterieelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieInhoudNietGekoppeldAanHandelingEnActieGeldigheidWel.getVerantwoordingInhoud()).thenReturn(actieBijHandeling2);
        Mockito.when(groepsVoorkomenMetActieInhoudNietGekoppeldAanHandelingEnActieGeldigheidWel.getVerantwoordingAanpassingGeldigheid())
            .thenReturn(actieVoorPredikaat);

        //FALSE
        final MaterieelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNiet =
                Mockito.mock(MaterieelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNiet.getVerantwoordingInhoud()).thenReturn(actieVoorPredikaat);
        Mockito.when(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNiet.getVerantwoordingAanpassingGeldigheid())
            .thenReturn(actieBijHandeling2);


        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(1L);

        final List<FormeelVerantwoordbaar<ActieModel>> historie = new ArrayList<>();
        historie.add(groepsVoorkomenMetActieGeldigheidGekoppeldAanHandeling);
        historie.add(groepsVoorkomenMetActieInhoudNietGekoppeldAanHandelingEnActieGeldigheidWel);
        historie.add(groepsVoorkomenMetActieInhoudGekoppeldAanHandelingEnActieGeldigheidNiet);

        Assert.assertEquals(3, historie.size());

        CollectionUtils.filter(historie, predikaat);

        Assert.assertEquals(2, historie.size());

        Assert.assertEquals(groepsVoorkomenMetActieGeldigheidGekoppeldAanHandeling, historie.get(0));
        Assert.assertEquals(groepsVoorkomenMetActieInhoudNietGekoppeldAanHandelingEnActieGeldigheidWel, historie.get(1));
    }

    /**
     * - ActieVerval hoort bij de Admninistratieve handeling
     */
    @Test
    public void testActieVervalIsOnderdeelVanHandeling() {
        final AdministratieveHandelingModel handelingVoorPredikaat =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
                        null, null, null);
        ReflectionTestUtils.setField(handelingVoorPredikaat, ID, 1L);

        final AdministratieveHandelingModel handeling2 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(SoortAdministratieveHandeling.AANVANG_ONDERZOEK, null, null, null);
        ReflectionTestUtils.setField(handeling2, ID, 2L);

        final AdministratieveHandelingModel handeling3 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                                                                    null, null, null);
        ReflectionTestUtils.setField(handeling3, ID, 3L);

        final ActieModel actieVoorPredikaat = VerantwoordingTestUtil
                .maakActie(handelingVoorPredikaat, SoortActie.BEEINDIGING_OUDERSCHAP, null, null, null, null, null, null);

        final ActieModel actieBijHandeling2 = VerantwoordingTestUtil
                .maakActie(handeling2, SoortActie.BEEINDIGING_OUDERSCHAP, null, null, null, null, null, null);

        final ActieModel actieBijHandeling3 = VerantwoordingTestUtil
                .maakActie(handeling3, SoortActie.REGISTRATIE_VOORNAAM, null, null, null, null, null, null);

        //TRUE
        final MaterieelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieVervalGekoppeldAanHandeling = Mockito.mock(MaterieelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieVervalGekoppeldAanHandeling.getVerantwoordingVerval()).thenReturn(actieVoorPredikaat);
        Mockito.when(groepsVoorkomenMetActieVervalGekoppeldAanHandeling.getVerantwoordingInhoud()).thenReturn(actieBijHandeling3);
        Mockito.when(groepsVoorkomenMetActieVervalGekoppeldAanHandeling.getVerantwoordingAanpassingGeldigheid()).thenReturn(
                actieBijHandeling2);

        //FALSE
        final MaterieelVerantwoordbaar<ActieModel> groepsVoorkomenMetActieVervalNietGekoppeldAanHandeling =
                Mockito.mock(MaterieelVerantwoordbaar.class);
        Mockito.when(groepsVoorkomenMetActieVervalNietGekoppeldAanHandeling.getVerantwoordingVerval()).thenReturn(actieBijHandeling2);
        Mockito.when(groepsVoorkomenMetActieVervalNietGekoppeldAanHandeling.getVerantwoordingInhoud()).thenReturn(actieVoorPredikaat);
        Mockito.when(groepsVoorkomenMetActieVervalNietGekoppeldAanHandeling.getVerantwoordingAanpassingGeldigheid()).thenReturn(actieBijHandeling3);

        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(1L);

        final List<FormeelVerantwoordbaar<ActieModel>> historie = new ArrayList<>();
        historie.add(groepsVoorkomenMetActieVervalGekoppeldAanHandeling);
        historie.add(groepsVoorkomenMetActieVervalNietGekoppeldAanHandeling);

        Assert.assertEquals(2, historie.size());

        CollectionUtils.filter(historie, predikaat);

        Assert.assertEquals(1, historie.size());

        Assert.assertEquals(groepsVoorkomenMetActieVervalGekoppeldAanHandeling, historie.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerantwoordingDoorIetsAndersDanDienstenOfActies() {
        class AndereVerantwoording implements VerantwoordingsEntiteit {

        }

        class AndereVerantwoordingGroep implements FormeelVerantwoordbaar<AndereVerantwoording> {

            private AndereVerantwoording verantwoordingInhoud;
            private AndereVerantwoording verantwoordingVerval;

            @Override
            public AndereVerantwoording getVerantwoordingInhoud() {
                return verantwoordingInhoud;
            }

            @Override
            public AndereVerantwoording getVerantwoordingVerval() {
                return verantwoordingVerval;
            }

            @Override
            public void setVerantwoordingInhoud(final AndereVerantwoording verantwoodingInhoud) {
            }

            @Override
            public void setVerantwoordingVerval(final AndereVerantwoording verantwoodingVerval) {
            }
        }

        new AdministratieveHandelingDeltaPredikaat(1L).evaluate(new AndereVerantwoordingGroep());
    }

    @Test
    public void testVerantwoordingDoorDiensten() {
        final HisPersoonAfnemerindicatieModel formeelVerantwoordbaar =
            new HisPersoonAfnemerindicatieModel(null, new PersoonAfnemerindicatieStandaardGroepBericht(),
                                                TestDienstBuilder.maker().maak(),
                                                DatumTijdAttribuut.nu());

        final boolean resultaat = new AdministratieveHandelingDeltaPredikaat(1L).evaluate(formeelVerantwoordbaar);
        Assert.assertFalse("Voorkomens verantwoord d.m.v. diensten moeten altijd false retourneren.", resultaat);
    }
}
