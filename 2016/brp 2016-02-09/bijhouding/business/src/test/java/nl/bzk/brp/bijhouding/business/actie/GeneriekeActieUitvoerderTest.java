/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;


import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de generieke methodes van alle actie uitvoerders; dus de methodes geimplementeerd door
 * {@link AbstractActieUitvoerder}.
 */
public class GeneriekeActieUitvoerderTest {

    private GeneriekeActieUitvoerder actieUitvoerder;
    private BijhoudingBerichtContext context;
    private ActieBericht             actieBericht;

    private static final String IDENTIFICERENDE_SLEUTEL_PREFIX = "technischeSleutel=";

    @Before
    public void init() {
        actieUitvoerder = new GeneriekeActieUitvoerder();
        actieBericht = bouwActieBericht();
        context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);

        actieUitvoerder.setActieBericht(actieBericht);
        actieUitvoerder.setContext(context);
    }

    @Test
    public void testStandaardGettersEnSetters() {
        actieUitvoerder = new GeneriekeActieUitvoerder();
        final ActieModel actieModel = bouwActieModel();

        Assert.assertNull(actieUitvoerder.getActieBericht());
        Assert.assertNull(actieUitvoerder.getContext());
        Assert.assertNull(actieUitvoerder.getActieModel());

        actieUitvoerder.setContext(context);
        actieUitvoerder.setActieBericht(actieBericht);
        actieUitvoerder.setActieModel(actieModel);

        Assert.assertSame(actieBericht, actieUitvoerder.getActieBericht());
        Assert.assertSame(context, actieUitvoerder.getContext());
        Assert.assertSame(actieModel, actieUitvoerder.getActieModel());
    }

    @Test
    public void testRootObjectModelBepaling() {
        context.voegBestaandBijhoudingsRootObjectToe(IDENTIFICERENDE_SLEUTEL_PREFIX + "123", bouwPersoonVolledig(1));
        context.voegBestaandBijhoudingsRootObjectToe(IDENTIFICERENDE_SLEUTEL_PREFIX + "456", bouwPersoonVolledig(2));
        context.voegAangemaaktBijhoudingsRootObjectToe("pers1", bouwPersoonVolledig(3));
        context.voegAangemaaktBijhoudingsRootObjectToe("pers2", bouwPersoonVolledig(4));

        // Test met identificerende sleutel
        ((PersoonBericht) actieBericht.getRootObject())
                .setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_PREFIX + "123");
        actieUitvoerder.voerActieUit();
        Assert.assertEquals(Integer.valueOf(1), actieUitvoerder.getModelRootObject().getID());

        // Test zonder identificerende sleutel, maar wel een referentie id
        ((PersoonBericht) actieBericht.getRootObject()).setIdentificerendeSleutel(null);
        actieBericht.getRootObject().setReferentieID("pers1");
        actieUitvoerder.voerActieUit();
        Assert.assertEquals(Integer.valueOf(3), actieUitvoerder.getModelRootObject().getID());

        // Test zonder technische sleutel, zonder referentie id, maar met een communicatie id
        actieBericht.getRootObject().setObjectSleutel(null);
        actieBericht.getRootObject().setReferentieID(null);
        actieBericht.getRootObject().setCommunicatieID("pers3");
        try {
            actieUitvoerder.voerActieUit();
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            //prima
        }
    }

    @Test
    public void testGetSoortAdmHand() {
        actieUitvoerder.setActieModel(bouwActieModel());

        Assert.assertEquals(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK,
                actieUitvoerder.getSoortAdmHand());
    }

    @Test
    public void testToevoegenRegels() {
        Assert.assertNotNull(actieUitvoerder.getVerwerkingsRegels());
        Assert.assertTrue(actieUitvoerder.getVerwerkingsRegels().isEmpty());
        Assert.assertNotNull(actieUitvoerder.getAfleidingsRegels());
        Assert.assertTrue(actieUitvoerder.getAfleidingsRegels().isEmpty());

        actieUitvoerder.voegVerwerkingsregelToe(Mockito.mock(Verwerkingsregel.class));
        Assert.assertEquals(1, actieUitvoerder.getVerwerkingsRegels().size());
        Assert.assertEquals(0, actieUitvoerder.getAfleidingsRegels().size());

        actieUitvoerder.voegVerwerkingsregelsToe(Arrays.asList(Mockito.mock(Verwerkingsregel.class),
                Mockito.mock(Verwerkingsregel.class)));
        Assert.assertEquals(3, actieUitvoerder.getVerwerkingsRegels().size());
        Assert.assertEquals(0, actieUitvoerder.getAfleidingsRegels().size());

        actieUitvoerder.voegAfleidingsregelToe(Mockito.mock(Afleidingsregel.class));
        Assert.assertEquals(3, actieUitvoerder.getVerwerkingsRegels().size());
        Assert.assertEquals(1, actieUitvoerder.getAfleidingsRegels().size());
    }

    @Test
    public void testIsSoortAdmHandeling() {
        actieUitvoerder.setActieModel(bouwActieModel());

        Assert.assertFalse(actieUitvoerder.isSoortAdmHand());
        Assert.assertFalse(actieUitvoerder.isSoortAdmHand(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE));
        Assert.assertFalse(actieUitvoerder.isSoortAdmHand(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE,
                SoortAdministratieveHandeling.BETWISTING_VAN_STAAT));
        Assert.assertTrue(actieUitvoerder.isSoortAdmHand(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE,
                SoortAdministratieveHandeling.BETWISTING_VAN_STAAT,
                SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK));
        Assert.assertTrue(actieUitvoerder.isSoortAdmHand(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK));
    }

    @Test
    public void testGeneriekeActieUitvoering() {
        context.voegBestaandBijhoudingsRootObjectToe(IDENTIFICERENDE_SLEUTEL_PREFIX + "123", bouwPersoonVolledig(1));
        actieUitvoerder.setActieModel(bouwActieModel());
        ((PersoonBericht) actieBericht.getRootObject())
                .setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_PREFIX + "123");

        final Verwerkingsregel verwerkingsregel1 = Mockito.mock(Verwerkingsregel.class);
        final Verwerkingsregel verwerkingsregel2 = Mockito.mock(Verwerkingsregel.class);
        final Afleidingsregel afleidingsregel = Mockito.mock(Afleidingsregel.class);
        Mockito.when(verwerkingsregel2.getAfleidingsregels()).thenReturn(Arrays.asList(afleidingsregel));

        actieUitvoerder.voegVerwerkingsregelToe(verwerkingsregel1);
        actieUitvoerder.voegVerwerkingsregelToe(verwerkingsregel2);

        final List<Afleidingsregel> afleidingsregels = actieUitvoerder.voerActieUit();

        Assert.assertNotNull(afleidingsregels);
        Assert.assertEquals(1, afleidingsregels.size());
        Assert.assertSame(afleidingsregel, afleidingsregels.get(0));
        Mockito.verify(verwerkingsregel1, Mockito.times(1)).verrijkBericht();
        Mockito.verify(verwerkingsregel2, Mockito.times(1)).verrijkBericht();
        Mockito.verify(verwerkingsregel1, Mockito.times(1)).neemBerichtDataOverInModel();
        Mockito.verify(verwerkingsregel2, Mockito.times(1)).neemBerichtDataOverInModel();
    }

    /**
     * Instantieert en retourneert een nieuwe {@link ActieBericht} met daar in een {@link PersoonBericht} object met
     * enkele standaard velden ingevuld.
     *
     * @return een actie bericht met persoon.
     */
    private ActieBericht bouwActieBericht() {
        final ActieBericht bericht = new ActieRegistratieAdresBericht();

        final PersoonBericht persoon = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 100000009, Geslachtsaanduiding.MAN, 20060325,
                StatischeObjecttypeBuilder.GEMEENTE_DEN_HAAG.getWaarde(), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde(),
                "Test", "de", "Tester");

        bericht.setRootObject(persoon);
        return bericht;
    }

    /**
     * Instantieert en retourneert een nieuwe {@link PersoonHisVolledig} met enkel de soort en id ingevuld.
     *
     * @param id de id van de persoon die de geretourneerde persoon dient te hebben.
     * @return een nieuwe PersoonHisVolledig met opgegeven id.
     */
    private PersoonHisVolledig bouwPersoonVolledig(final int id) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        ReflectionTestUtils.setField(persoon, "iD", id);
        return persoon;
    }

    /**
     * Instantieert en retourneert een nieuwe {@link ActieModel} instantie met een standaard administratieve handeling.
     *
     * @return een actie model met administratieve handeling.
     */
    private ActieModel bouwActieModel() {
        final AdministratieveHandelingModel admHandeling =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK),
                        null, null, null);

        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES), admHandeling, null, null, null, null, null);
    }

    /**
     * Interne klasse die de {@link AbstractActieUitvoerder} klasse extends en verder enkel methodes toevoegt ten
     * behoeve van tests.
     */
    private final class GeneriekeActieUitvoerder extends AbstractActieUitvoerder<PersoonBericht, PersoonHisVolledig> {

        @Override
        protected void verzamelVerwerkingsregels() {

        }

        /**
         * Retourneert de verwerkingsregels.
         *
         * @return de verwerkingsregels.
         */
        public List<Verwerkingsregel> getVerwerkingsRegels() {
            return (List<Verwerkingsregel>) ReflectionTestUtils.getField(this, "verwerkingsregels");
        }

        /**
         * Retourneert de afleidingsregels.
         *
         * @return de afleidingsregels.
         */
        public List<Afleidingsregel> getAfleidingsRegels() {
            return (List<Afleidingsregel>) ReflectionTestUtils.getField(this, "afleidingsregels");
        }
    }
}
