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
import nl.bzk.brp.business.bedrijfsregels.impl.aanschrijving.AanschrijvingGroepAfleiding;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieOverlijdenNederlandBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Unit test voor de RegistratieOverlijdenUitvoerder.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistratieOverlijdenUitvoerderTest extends AbstractStapTest {

    @Mock
    private AanschrijvingGroepAfleiding aanschrijvingGroepAfleiding;

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private ActieRepository actieRepository;

    @InjectMocks
    private RegistratieOverlijdenUitvoerder registratieOverlijdenUitvoerder = new RegistratieOverlijdenUitvoerder();

    private ActieModel actieModel = null;

    private AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(
        new HandelingRegistratieOverlijdenNederlandBericht());

    @Before
    public void init() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
            new PersoonModel(maakPersoon()));

        Mockito.when(aanschrijvingGroepAfleiding.executeer(
            Matchers.any(RootObject.class), Matchers.any(RootObject.class), Matchers.any(Actie.class)))
               .thenReturn(null);

        actieModel = new ActieModel(new ActieRegistratieOverlijdenBericht(), null);
        Mockito.when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(actieModel);
    }

    @Test
    public void testVoerUitZonderFouten() {
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        List<Melding> meldingen = registratieOverlijdenUitvoerder.voerUit(actie, bc, administratieveHandelingModel);

        Mockito.verify(actieRepository, Mockito.times(1)).opslaanNieuwActie(Matchers.any(ActieModel.class));

        Mockito.verify(persoonRepository, Mockito.times(1)).werkbijOverlijden(
            Matchers.any(PersoonModel.class),
            Matchers.any(PersoonOverlijdenGroepBericht.class),
            Matchers.any(PersoonOpschortingGroepBericht.class),
            Matchers.any(ActieModel.class),
            Matchers.any(Datum.class));

        // TODO assert ....
        Assert.assertEquals(0, meldingen.size());
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
    }


    @Test
    public void testMetOnbekendeBsn() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
               .thenReturn(null);
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("999999999"));
        List<Melding> meldingen = registratieOverlijdenUitvoerder.voerUit(actie, bc, administratieveHandelingModel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(0, bc.getHoofdPersonen().size());
        // onbekende BSN
        Assert.assertEquals(MeldingCode.ALG0001, meldingen.get(0).getCode());
    }

    /**
     * Instantieert een nieuwe {@link ActieBericht}, met een {@link PersoonBericht} als rootobject die alleen de
     * benodigde groepen in zich heeft (identificatienrs, aanschrijving)
     *
     * @return een nieuwe ActieBericht instantie.
     */
    private ActieBericht maakStandaardActie() {
        PersoonBericht persoon = maakPersoon();

        ActieBericht actie = new ActieRegistratieOverlijdenBericht();
        Integer datumAanvangGeldigheid = 1;
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        AdministratieveHandelingBericht admin = new HandelingRegistratieOverlijdenNederlandBericht();
        admin.setActies(Arrays.asList(actie));

        actie.setAdministratieveHandeling(admin);

        return actie;
    }

    private PersoonGeslachtsnaamcomponentBericht maakGeslachtsNaam(final String scheidingsteken,
                                                                   final String voorvoegsel, final String naam)
    {
        PersoonGeslachtsnaamcomponentBericht gnaam = new PersoonGeslachtsnaamcomponentBericht();
        gnaam.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        gnaam.getStandaard().setScheidingsteken(new Scheidingsteken(scheidingsteken));
        gnaam.getStandaard().setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        gnaam.getStandaard().setNaam(new Geslachtsnaamcomponent(naam));
        return gnaam;
    }

    private PersoonBericht maakPersoon() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456782"));
        persoon.setAanschrijving(new PersoonAanschrijvingGroepBericht());
        persoon.getAanschrijving().setNaamgebruik(WijzeGebruikGeslachtsnaam.EIGEN);
        persoon.getAanschrijving().setGeslachtsnaamAanschrijving(new Geslachtsnaam("Petereson"));
        persoon.getAanschrijving().setIndicatieAanschrijvingAlgoritmischAfgeleid(JaNee.NEE);
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(maakGeslachtsNaam(",", "van der", "Wiel"));
        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.getSamengesteldeNaam().setGeslachtsnaam(new Geslachtsnaam("Wieltjes"));
        persoon.getSamengesteldeNaam().setVoorvoegsel(new Voorvoegsel("geen"));
        persoon.setOverlijden(new PersoonOverlijdenGroepBericht());
        persoon.getOverlijden().setDatumOverlijden(new Datum(20120308));
        persoon.setOpschorting(new PersoonOpschortingGroepBericht());
        persoon.getOpschorting().setRedenOpschortingBijhouding(RedenOpschorting.OVERLIJDEN);
        return persoon;

    }
}
