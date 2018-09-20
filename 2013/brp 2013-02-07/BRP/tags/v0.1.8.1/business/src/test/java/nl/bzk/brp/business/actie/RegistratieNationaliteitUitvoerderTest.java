/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonNationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractActieModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class RegistratieNationaliteitUitvoerderTest extends AbstractStapTest {

    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private ReferentieDataRepository referentieDataRepository;
    @Mock
    private PersoonNationaliteitRepository persoonNatRepository;

    @Mock
    private ActieRepository                        actieRepository;

    private AbstractActieUitvoerder                registratieNationaliteitUitvoerder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        registratieNationaliteitUitvoerder = new RegistratieNationaliteitUitvoerder();
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "referentieDataRepository",
                referentieDataRepository);
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "persoonNatRepository",
                persoonNatRepository);
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "actieRepository", actieRepository);
        Mockito.when(actieRepository.save(Matchers.any(ActieModel.class))).thenReturn(
                new ActieModel(new AbstractActieModel() {
                }));

    }

    public ActieBericht maakActie() {
        final ActieBericht actie = new ActieBericht();
        actie.setDatumAanvangGeldigheid(new Datum(19830404));

        PersoonIdentificatienummersGroepBericht identificatienummers = new PersoonIdentificatienummersGroepBericht();
        identificatienummers.setBurgerservicenummer(new Burgerservicenummer("123456789"));

        PersoonBericht persoon = new PersoonBericht();

        Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setNationaliteitcode(new Nationaliteitcode((short) 2));

        PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
        persoonNationaliteit.setNationaliteit(nationaliteit);
        persoonNationaliteit.setGegevens(new PersoonNationaliteitStandaardGroepBericht());

        List<PersoonNationaliteitBericht> nationaliteiten = new ArrayList<PersoonNationaliteitBericht>();
        nationaliteiten.add(persoonNationaliteit);

        persoon.setIdentificatienummers(identificatienummers);
        persoon.setNationaliteiten(nationaliteiten);

        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        return actie;
    }

    @Test
    public void testRegistratieNationaliteitPersoonNietGevonden() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789")))
                .thenReturn(null);
        List<Melding> meldingen = registratieNationaliteitUitvoerder.verwerkActie(maakActie(), bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.ALG0001, meldingen.get(0).getCode());
    }

    @Test
    public void testNationaliteitNietGevonden() {
        Mockito.when(referentieDataRepository.vindNationaliteitOpCode((Nationaliteitcode) Matchers.anyObject()))
                .thenThrow(
                        new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                                "foo", new NoResultException()));
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                new PersoonModel(new AbstractPersoonModel() {
                }));
        List<Melding> meldingen = registratieNationaliteitUitvoerder.verwerkActie(maakActie(), bouwBerichtContext());
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testRegistratieNationaliteitNormaleFlow() {
        PersoonModel persoonModel = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoonModel, "id", 12);

        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setGegevens(new PersoonNationaliteitStandaardGroepBericht());

        PersoonNationaliteitModel ppersNation = new PersoonNationaliteitModel(persoonNationaliteitBericht, persoonModel);

        Nationaliteit nationaliteit = new Nationaliteit();
        nationaliteit.setNationaliteitcode(new Nationaliteitcode((short) 2));
        Mockito.when(referentieDataRepository.vindNationaliteitOpCode((Nationaliteitcode) Matchers.anyObject()))
                .thenReturn(nationaliteit);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"))).thenReturn(
                persoonModel);
        Mockito.when(persoonNatRepository
                .voegNationaliteit(
                    Matchers.any(PersoonModel.class),  Matchers.any(PersoonNationaliteitModel.class),
                    Matchers.any(ActieModel.class), Matchers.any(Datum.class)))
                .thenReturn(persoonModel);

        ActieBericht actie = maakActie();
        BerichtContext bc = bouwBerichtContext();
        registratieNationaliteitUitvoerder.verwerkActie(actie, bc);

        ArgumentCaptor<PersoonModel> argument2 = ArgumentCaptor.forClass(PersoonModel.class);

        Mockito.verify(actieRepository, Mockito.times(1)).save(Matchers.any(ActieModel.class));
        Mockito.verify(persoonNatRepository, Mockito.times(1)).voegNationaliteit(
                argument2.capture(), Matchers.any(PersoonNationaliteitModel.class),
                Matchers.any(ActieModel.class), (Datum) Matchers.anyObject());


        Assert.assertEquals(12L, argument2.getValue().getId().longValue());
        // kan de rest van de persoon niet testen, omdatde mock een 'leeg' persoonModel terug geeft.
        // Wij gaan vanuit dat de historie ook al wordt bijgehouden. (verzorgd in de DAL), dus hoeven we hier ook niet
        // meer te testen.

        // 1 hoofd persoon bijgehouden.
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
        Assert.assertEquals(0, bc.getBijPersonen().size());

    }

    @Test
    public void testRegistratieNationaliteitGeenNationaliteitenOpgegeven() {
        ActieBericht actie = maakActie();
        Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        ReflectionTestUtils.setField(persoon, "nationaliteiten", null);
        registratieNationaliteitUitvoerder.verwerkActie(actie, bouwBerichtContext());
    }
}
