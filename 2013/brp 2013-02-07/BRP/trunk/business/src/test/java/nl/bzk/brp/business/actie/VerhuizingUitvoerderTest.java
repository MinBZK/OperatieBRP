/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.actie.validatie.VerhuisActieValidator;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieBinnengemeentelijkeVerhuizingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieIntergemeentelijkeVerhuizingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/** Unit test voor de VerhuizingUitvoerder. */
@RunWith(MockitoJUnitRunner.class)
public class VerhuizingUitvoerderTest extends AbstractStapTest {

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Mock
    private VerhuisActieValidator verhuisActieValidator;

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private ActieRepository actieRepository;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private AdministratieveHandelingModel administratieveHandelingModel;

    @InjectMocks
    private final AbstractActieUitvoerder verhuizingUitvoerder = new VerhuizingUitvoerder();

    @Before
    public void init() {
        administratieveHandelingModel =
            new AdministratieveHandelingModel(new HandelingRegistratieIntergemeentelijkeVerhuizingBericht());
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
            new PersoonModel(new PersoonBericht()));

        ActieModel actie = new ActieModel(new ActieRegistratieAdresBericht(), null);
        Mockito.when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(actie);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();

        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(gegevens);

        PersoonAdresModel persoonAdres =
            new PersoonAdresModel(persoonAdresBericht, new PersoonModel(new PersoonBericht()));
        Mockito.when(
            persoonAdresRepository.opslaanNieuwPersoonAdres(Matchers.any(PersoonAdresModel.class),
                Matchers.any(ActieModel.class),
                Matchers.any(Datum.class))).thenReturn(persoonAdres);

        Mockito.when(referentieDataRepository.vindLandOpCode(Matchers.eq(BrpConstanten.NL_LAND_CODE)))
               .thenReturn(StatischeObjecttypeBuilder.LAND_NEDERLAND);
    }

    @Test
    public void testVoerUitZonderFouten() {
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        verhuizingUitvoerder.voerUit(actie, bc, administratieveHandelingModel);

        Mockito.verify(actieRepository, Mockito.times(1)).opslaanNieuwActie(Matchers.any(ActieModel.class));

        Mockito.verify(persoonAdresRepository, Mockito.times(1)).opslaanNieuwPersoonAdres(
            Matchers.notNull(PersoonAdresModel.class), Matchers.any(ActieModel.class),
            Matchers.eq(actie.getDatumAanvangGeldigheid()));

        Mockito.verify(persoonRepository, Mockito.times(1)).werkbijBijhoudingsgemeente(
            Matchers.eq(new Burgerservicenummer("123")),
            Matchers.notNull(PersoonBijhoudingsgemeenteGroepModel.class), Matchers.any(ActieModel.class),
            Matchers.eq(actie.getDatumAanvangGeldigheid()));
        // 1 hoofd persoon bijgehouden.
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
    }

    @Test
    public void testMetOnbekendeReferentieExceptieUitDAL() {
        ActieBericht actie = maakStandaardActie();
        Mockito.doThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "0013", null))
               .when(persoonAdresRepository)
               .opslaanNieuwPersoonAdres(Matchers.any(PersoonAdresModel.class),
                   Matchers.any(ActieModel.class),
                   Matchers.any(Datum.class));

        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SoortMelding.FOUT, result.get(0).getSoort());
    }

    @Test
    public void testVerhuizendePersoonBSNNietBekendInDB() {
        ActieBericht actie = maakStandaardActie();
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123"))).thenReturn(null);
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SoortMelding.FOUT, result.get(0).getSoort());
    }

    /** Check BRPUC05108 */
    @Test
    public void testJuisteDatumAanvangGeldigheidMeegegevenAanDAL() {
        ActieBericht actie = maakStandaardActie();
        actie.setDatumAanvangGeldigheid(new Datum(20120505));
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext(), administratieveHandelingModel);
        ArgumentCaptor<Datum> argumentCaptor = ArgumentCaptor.forClass(Datum.class);
        Mockito.verify(persoonAdresRepository).opslaanNieuwPersoonAdres(
            Matchers.any(PersoonAdresModel.class),
            Matchers.any(ActieModel.class),
            argumentCaptor.capture());
        Assert.assertNull(result);
        Assert.assertEquals(actie.getDatumAanvangGeldigheid(), argumentCaptor.getValue());
    }

    /**
     * Instantieert een nieuwe {@link ActieBericht}, met een {@link PersoonBericht} als rootobject waarvan alleen een
     * {@link PersoonAdresBericht} is gezet.
     *
     * @return een nieuwe ActieBericht instantie.
     */
    private ActieBericht maakStandaardActie() {
        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new Burgerservicenummer("123"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(adressen);
        persoon.setIdentificatienummers(pin);

        ActieBericht actie = new ActieRegistratieAdresBericht();
        Integer datumAanvangGeldigheid = 1;
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        AdministratieveHandelingBericht admin = new HandelingRegistratieBinnengemeentelijkeVerhuizingBericht();
        admin.setActies(Arrays.asList(actie));

        actie.setAdministratieveHandeling(admin);

        return actie;
    }

}
