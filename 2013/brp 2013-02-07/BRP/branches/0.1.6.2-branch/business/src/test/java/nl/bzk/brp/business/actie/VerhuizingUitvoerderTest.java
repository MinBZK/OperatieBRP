/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.actie.validatie.VerhuisActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.PersoonAdresMdlRepository;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataMdlRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresMdlHistorieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsGemeenteGroepModel;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de VerhuizingUitvoerder. */
@RunWith(MockitoJUnitRunner.class)
public class VerhuizingUitvoerderTest extends AbstractStapTest {

    private AbstractActieUitvoerder           verhuizingUitvoerder;

    @Mock
    private PersoonAdresMdlRepository         persoonAdresRepository;
    @Mock
    private VerhuisActieValidator             verhuisActieValidator;
    @Mock
    private PersoonMdlRepository              persoonRepository;
    @Mock
    private ActieRepository                   actieRepository;
    @Mock
    private PersoonAdresMdlHistorieRepository persoonAdresHistorieRepository;
    @Mock
    private ReferentieDataMdlRepository       referentieDataRepository;

    @Before
    public void init() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                new PersoonModel(new PersoonBericht()));

        verhuizingUitvoerder = new VerhuizingUitvoerder();
        ReflectionTestUtils.setField(verhuizingUitvoerder, "persoonAdresRepository", persoonAdresRepository);
        ReflectionTestUtils.setField(verhuizingUitvoerder, "verhuisActieValidator", verhuisActieValidator);
        ReflectionTestUtils.setField(verhuizingUitvoerder, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(verhuizingUitvoerder, "actieRepository", actieRepository);
        ReflectionTestUtils.setField(verhuizingUitvoerder, "persoonAdresHistorieRepository",
                persoonAdresHistorieRepository);
        ActieModel actie = new ActieModel(new ActieBericht());
        Mockito.when(actieRepository.save(Matchers.any(ActieModel.class))).thenReturn(actie);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();

        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setGegevens(gegevens);

        PersoonAdresModel persoonAdres =
            new PersoonAdresModel(persoonAdresBericht, new PersoonModel(new PersoonBericht()));
        Mockito.when(
                persoonAdresRepository.opslaanNieuwPersoonAdres(Matchers.any(PersoonAdresModel.class),
                        Matchers.any(Datum.class), Matchers.any(Datum.class), Matchers.any(Date.class))).thenReturn(
                persoonAdres);

        Mockito.when(referentieDataRepository.vindLandOpCode(Matchers.eq(new LandCode("6030")))).thenReturn(new Land());
    }

    @Test
    public void testVoerUitZonderFouten() {
        ActieBericht actie = maakStandaardActie();
        BerichtContext bc = bouwBerichtContext();
        verhuizingUitvoerder.voerUit(actie, bc);

        Mockito.verify(actieRepository, Mockito.times(1)).save(Matchers.any(ActieModel.class));

        Mockito.verify(persoonAdresRepository, Mockito.times(1)).opslaanNieuwPersoonAdres(
                Matchers.notNull(PersoonAdresModel.class), Matchers.eq(actie.getDatumAanvangGeldigheid()),
                (Datum) Matchers.eq(null), Matchers.any(Date.class));

        Mockito.verify(persoonRepository, Mockito.times(1)).werkbijBijhoudingsGemeente(
                Matchers.eq(new Burgerservicenummer("abc")),
                Matchers.notNull(PersoonBijhoudingsGemeenteGroepModel.class), Matchers.any(ActieModel.class),
                Matchers.eq(actie.getDatumAanvangGeldigheid()), Matchers.any(DatumTijd.class));
        // 1 hoofd persoon bijgehouden.
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
        Assert.assertEquals(0, bc.getBijPersonen().size());
    }

    @Test
    public void testMetOnbekendeReferentieExceptieUitDAL() {
        ActieBericht actie = maakStandaardActie();
        Mockito.doThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "0013", null))
                .when(persoonAdresRepository)
                .opslaanNieuwPersoonAdres(Matchers.any(PersoonAdresModel.class), Matchers.any(Datum.class),
                        Matchers.any(Datum.class), Matchers.any(Date.class));

        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, result.get(0).getSoort());
    }

    @Test
    public void testVerhuizendePersoonBSNNietBekendInDB() {
        ActieBericht actie = maakStandaardActie();
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("abc"))).thenReturn(null);
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext());
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, result.get(0).getSoort());
    }

    /** Check BRPUC05108 */
    @Test
    public void testJuisteDatumAanvangGeldigheidMeegegevenAanDAL() {
        ActieBericht actie = maakStandaardActie();
        actie.setDatumAanvangGeldigheid(new Datum(20120505));
        List<Melding> result = verhuizingUitvoerder.voerUit(actie, bouwBerichtContext());
        ArgumentCaptor<Datum> argumentCaptor = ArgumentCaptor.forClass(Datum.class);
        Mockito.verify(persoonAdresRepository).opslaanNieuwPersoonAdres(Matchers.any(PersoonAdresModel.class),
                argumentCaptor.capture(), Matchers.any(Datum.class), Matchers.any(Date.class));
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
        PersoonIdentificatieNummersGroepBericht pin = new PersoonIdentificatieNummersGroepBericht();
        pin.setBurgerServiceNummer(new Burgerservicenummer("abc"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(adressen);
        persoon.setIdentificatieNummers(pin);

        ActieBericht actie = new ActieBericht();
        Integer datumAanvangGeldigheid = new Integer(1);
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        return actie;
    }

}
