/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.ber.AbstractBerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/** Unit tests voor de {@link BerichtUtils} klasse. */
public class BerichtUtilsTest {

    @Test
    public void testBepaalPersonenUitActie() {
        final ActieBericht actie = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };

        // Test zonder root objecten
        Assert.assertTrue(BerichtUtils.bepaalPersonenUitActie(actie).isEmpty());

        // Test met persoon als root object
        actie.setRootObject(new PersoonBericht());
        Assert.assertFalse(BerichtUtils.bepaalPersonenUitActie(actie).isEmpty());
        Assert.assertEquals(1, BerichtUtils.bepaalPersonenUitActie(actie).size());

        // Test met fam. rechtelijkebetrekking relatie als root object
        final FamilierechtelijkeBetrekkingBericht bericht = new FamilierechtelijkeBetrekkingBericht();
        bericht.setBetrokkenheden(Arrays.asList(new KindBericht(), new OuderBericht(), new OuderBericht()));
        for (final BetrokkenheidBericht betrokkenheidBericht : bericht.getBetrokkenheden()) {
            betrokkenheidBericht.setPersoon(new PersoonBericht());
        }
        actie.setRootObject(bericht);
        Assert.assertFalse(BerichtUtils.bepaalPersonenUitActie(actie).isEmpty());
        Assert.assertEquals(1, BerichtUtils.bepaalPersonenUitActie(actie).size());

        // Test met huwelijk relatie als root object
        actie.setRootObject(new HuwelijkBericht());
        Assert.assertTrue(BerichtUtils.bepaalPersonenUitActie(actie).isEmpty());
        Assert.assertEquals(0, BerichtUtils.bepaalPersonenUitActie(actie).size());
    }

    @Test
    public void testIsBerichtPrevalidatieAan() {
        // Test met null als bericht
        AbstractBerichtBericht bericht = null;
        Assert.assertFalse(BerichtUtils.isBerichtPrevalidatieAan(bericht));

        // Test met null voor parameters
        bericht = new AbstractBerichtBericht() {
        };
        bericht.setParameters(null);
        Assert.assertFalse(BerichtUtils.isBerichtPrevalidatieAan(bericht));

        // Test met null voor verwerkingswijze
        bericht = bouwBerichtMetVerwerkingswijze(null);
        Assert.assertFalse(BerichtUtils.isBerichtPrevalidatieAan(bericht));

        // Test met verschillende verwerkingswijzes
        bericht = bouwBerichtMetVerwerkingswijze(Verwerkingswijze.BIJHOUDING);
        Assert.assertFalse(BerichtUtils.isBerichtPrevalidatieAan(bericht));
        bericht = bouwBerichtMetVerwerkingswijze(Verwerkingswijze.PREVALIDATIE);
        Assert.assertTrue(BerichtUtils.isBerichtPrevalidatieAan(bericht));
    }

    @Test
    public void testIsHuwelijksberichtMetHandmatigFiatterendePartners() {
        RegistreerHuwelijkGeregistreerdPartnerschapBericht bhgBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht iscBericht = new RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht();

        final PartijAttribuut partijAttribuutUit = new PartijAttribuut(new Partij(null, null, null, null, null, null, null, JaNeeAttribuut.NEE, null));
        final PartijAttribuut partijAttribuutAan = new PartijAttribuut(new Partij(null, null, null, null, null, null, null, JaNeeAttribuut.JA, null));
        final HisPersoonBijhoudingModel actueleBijhouding = Mockito.mock(HisPersoonBijhoudingModel.class);
        Mockito.when(actueleBijhouding.getBijhoudingspartij()).thenReturn(partijAttribuutUit);
        final MaterieleHistorieSet<HisPersoonBijhoudingModel> materieleHistorieSet = Mockito.mock(MaterieleHistorieSet.class);
        Mockito.when(materieleHistorieSet.getActueleRecord()).thenReturn(actueleBijhouding);
        final PersoonHisVolledigImpl persoonHisVolledig = Mockito.mock(PersoonHisVolledigImpl.class);
        Mockito.when(persoonHisVolledig.getPersoonBijhoudingHistorie()).thenReturn(materieleHistorieSet);
        final Map<String, HisVolledigRootObject> bestaandeBijhoudingsRootObjecten = new HashMap<>();
        bestaandeBijhoudingsRootObjecten.put("1", persoonHisVolledig);
        final BijhoudingBerichtContext berichtContext = Mockito.mock(BijhoudingBerichtContext.class);
        Mockito.when(berichtContext.getBestaandeBijhoudingsRootObjecten()).thenReturn(bestaandeBijhoudingsRootObjecten);

        // BHG huwelijk, autofiat uit
        Assert.assertTrue(BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(bhgBericht, berichtContext));

        // ISC huwelijk, autofiat uit
        Assert.assertTrue(BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(iscBericht, berichtContext));

        // BHG huwelijk, autofiat aan
        Mockito.when(actueleBijhouding.getBijhoudingspartij()).thenReturn(partijAttribuutAan);
        Assert.assertFalse(BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(bhgBericht, berichtContext));

        // ISC huwelijk, autofiat aan
        Assert.assertFalse(BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(iscBericht, berichtContext));

        // BHG huwelijk, geen actuele bijhouder
        Mockito.when(materieleHistorieSet.getActueleRecord()).thenReturn(null);
        // TODO POC Bijhouding Klopt het dat er false geretourneerd moet worden als er geen actuele bijhouder is?
        Assert.assertFalse(BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(bhgBericht, berichtContext));

        // ISC huwelijk, geen actuele bijhouder
        // TODO POC Bijhouding Klopt het dat er false geretourneerd moet worden als er geen actuele bijhouder is?
        Assert.assertFalse(BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(iscBericht, berichtContext));
    }

    /**
     * Bouwt een {@link AbstractBerichtBericht} instantie met parameters met daarin de verwerkingswijze gezet
     * naar de opgegeven verwerkingswijze.
     *
     * @param verwerkingswijze de verwerkingswijze die gebruikt moet worden.
     * @return een {@link AbstractBerichtBericht} instantie.
     */
    private AbstractBerichtBericht bouwBerichtMetVerwerkingswijze(final Verwerkingswijze verwerkingswijze) {
        final AbstractBerichtBericht bericht = new AbstractBerichtBericht() {
        };
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setVerwerkingswijze(new VerwerkingswijzeAttribuut(verwerkingswijze));
        return bericht;
    }
}
