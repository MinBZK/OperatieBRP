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
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de RegistratieOverlijdenUitvoerder. */
@RunWith(MockitoJUnitRunner.class)
public class RegistratieOverlijdenUitvoerderTest extends AbstractStapTest {

    private RegistratieOverlijdenUitvoerder           registratieOverlijdenUitvoerder;

    @Mock
    private AanschrijvingGroepAfleiding     aanschrijvingGroepAfleiding;

    @Mock
    private PersoonRepository              persoonRepository;

    @Mock
    private ActieRepository                   actieRepository;

    private ActieModel actieModel = null;
    @Before
    public void init() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(maakPersoon()));

        Mockito.when(aanschrijvingGroepAfleiding.executeer(
            Matchers.any(RootObject.class), Matchers.any(RootObject.class), Matchers.any(Actie.class)))
            .thenReturn(null);

        registratieOverlijdenUitvoerder = new RegistratieOverlijdenUitvoerder();
        ReflectionTestUtils.setField(registratieOverlijdenUitvoerder, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(registratieOverlijdenUitvoerder, "actieRepository", actieRepository);
        actieModel = new ActieModel(new ActieBericht());
        Mockito.when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(actieModel);
    }

    @Test
    public void testVoerUitZonderFouten() {
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        List<Melding> meldingen = registratieOverlijdenUitvoerder.voerUit(actie, bc);

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
        Assert.assertEquals(0, bc.getBijPersonen().size());
    }



    @Test
    public void testMetOnbekendeBsn() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
            .thenReturn(null);
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("999999999"));
        List<Melding> meldingen = registratieOverlijdenUitvoerder.voerUit(actie, bc);
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

        ActieBericht actie = new ActieBericht();
        Integer datumAanvangGeldigheid = new Integer(1);
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        return actie;
    }

    private PersoonGeslachtsnaamcomponentBericht maakGeslachtsNaam(final String scheidingsteken,
            final String voorvoegsel, final String naam)
    {
        PersoonGeslachtsnaamcomponentBericht gnaam = new PersoonGeslachtsnaamcomponentBericht();
        gnaam.setGegevens(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        gnaam.getGegevens().setScheidingsteken(new Scheidingsteken(scheidingsteken));
        gnaam.getGegevens().setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        gnaam.getGegevens().setGeslachtsnaamcomponent(new Geslachtsnaamcomponent(naam));
        return gnaam;
    }

    private PersoonBericht maakPersoon() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456782"));
        persoon.setAanschrijving(new PersoonAanschrijvingGroepBericht());
        persoon.getAanschrijving().setGebruikGeslachtsnaam(WijzeGebruikGeslachtsnaam.EIGEN);
        persoon.getAanschrijving().setGeslachtsnaam(new Geslachtsnaamcomponent("Petereson"));
        persoon.getAanschrijving().setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Nee);
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoon.getGeslachtsnaamcomponenten().add(maakGeslachtsNaam(",", "van der", "Wiel"));
        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.getSamengesteldeNaam().setGeslachtsnaam(new Geslachtsnaamcomponent("Wieltjes"));
        persoon.getSamengesteldeNaam().setVoorvoegsel(new Voorvoegsel("geen"));
        persoon.setOverlijden(new PersoonOverlijdenGroepBericht());
        persoon.getOverlijden().setDatumOverlijden(new Datum(20120308));
        persoon.setOpschorting(new PersoonOpschortingGroepBericht());
        persoon.getOpschorting().setRedenOpschorting(RedenOpschorting.OVERLIJDEN);
        return persoon;

    }
}
