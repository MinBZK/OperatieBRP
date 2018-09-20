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
import nl.bzk.brp.business.actie.validatie.CorrectieAdresNLActieValidator;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtenIds;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresNederlandBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class CorrectieAdresNLUitvoerderTest {

    @Mock
    private CorrectieAdresNLActieValidator correctieAdresNLActieValidator;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private ActieRepository actieRepository;

    private AdministratieveHandelingModel administratieveHandelingModel;

    @InjectMocks
    private final AbstractActieUitvoerder correctieAdresNLUitvoerder = new CorrectieAdresNLUitvoerder();

    @Before
    public void init() {
        initMocks(this);

        ActieModel actie = new ActieModel(new ActieCorrectieAdresBericht(), null);
        administratieveHandelingModel =
                new AdministratieveHandelingModel(new HandelingCorrectieAdresNederlandBericht());
        when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(new PersoonBericht()));
        when(actieRepository.opslaanNieuwActie(Matchers.any(ActieModel.class))).thenReturn(actie);
    }

    @Test
    public void testVoerUitZonderFouten() {
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        correctieAdresNLUitvoerder.voerUit(actie, bc, administratieveHandelingModel);

        verify(actieRepository, times(1)).opslaanNieuwActie(Matchers.any(ActieModel.class));

        verify(persoonRepository, times(1)).findByBurgerservicenummer(
                Matchers.any(Burgerservicenummer.class));

        verify(persoonAdresRepository, times(1)).voerCorrectieAdresUit(
                Matchers.notNull(PersoonAdresModel.class), Matchers.any(ActieModel.class), Matchers.any(Datum.class),
                Matchers.any(Datum.class));

        // 1 hoofd persoon bijgehouden.
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
    }

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

        ActieBericht actie = new ActieCorrectieAdresBericht();
        Integer datumAanvangGeldigheid = 1;
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setAdministratieveHandeling(new HandelingCorrectieAdresNederlandBericht());
        AdministratieveHandelingBericht admin = new HandelingCorrectieAdresNederlandBericht();
        admin.setActies(Arrays.asList(actie));

        actie.setAdministratieveHandeling(admin);

        return actie;
    }

    protected BerichtContext bouwBerichtContext() {
        BerichtenIds ids = new BerichtenIds(2L, 3L);
        Partij partij = Mockito.mock(Partij.class);
        when(partij.getID()).thenReturn((short) 5);

        return new BerichtContext(ids, partij, "ref");
    }
}
