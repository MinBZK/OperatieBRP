/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
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
import nl.bzk.brp.dataaccess.repository.jpa.historie.ObjectTypeHistorieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;
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
    private PersoonRepository                  persoonRepository;
    @Mock
    private ReferentieDataRepository           referentieDataRepository;
    @Mock
    private PersoonNationaliteitRepository     persoonNationaliteitRepository;
    @Mock
    private ObjectTypeHistorieRepository<HisPersoonNationaliteit,
            PersistentPersoonNationaliteit> persoonNationaliteitHistorieRepository;

    @Mock
    private ActieRepository actieRepository;

    private AbstractActieUitvoerder         registratieNationaliteitUitvoerder;



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        registratieNationaliteitUitvoerder = new RegistratieNationaliteitUitvoerder();
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "persoonRepository", persoonRepository);
        ReflectionTestUtils
            .setField(registratieNationaliteitUitvoerder, "referentieDataRepository", referentieDataRepository);
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "persoonNationaliteitRepository",
            persoonNationaliteitRepository);
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "persoonNationaliteitHistorieRepository",
            persoonNationaliteitHistorieRepository);
        ReflectionTestUtils.setField(registratieNationaliteitUitvoerder, "actieRepository", actieRepository);
        Mockito.when(actieRepository.save(Mockito.any(PersistentActie.class))).thenReturn(new PersistentActie());


    }

    public BRPActie maakActie() {
        final BRPActie actie = new BRPActie();
        actie.setDatumAanvangGeldigheid(19830404);
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");
        PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit();
        Nationaliteit nationaliteit = new Nationaliteit();
        ReflectionTestUtils.setField(nationaliteit, "code", "NL");
        persoonNationaliteit.setNationaliteit(nationaliteit);
        persoon.getNationaliteiten().add(persoonNationaliteit);
        actie.setRootObjecten(new ArrayList<RootObject>());
        actie.voegRootObjectToe(persoon);
        return actie;
    }

    @Test
    public void testRegistratieNationaliteitPersoonNietGevonden() {
        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(null);
        List<Melding> meldingen = registratieNationaliteitUitvoerder.verwerkActie(maakActie(), bouwBerichtContext());
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.size() == 1);
        Assert.assertEquals(MeldingCode.ALG0001, meldingen.get(0).getCode());
    }

    @Test
    public void testNationaliteitNietGevonden() {
        Mockito.when(referentieDataRepository.vindNationaliteitOpCode(Matchers.anyString())).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                "foo",
                new NoResultException()));
        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(new PersistentPersoon());
        List<Melding> meldingen = registratieNationaliteitUitvoerder.verwerkActie(maakActie(), bouwBerichtContext());
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertTrue(meldingen.size() == 1);
    }

    @Test
    public void testRegistratieNationaliteitNormaleFlow() {
        PersistentPersoon persistentPersoon = new PersistentPersoon();
        PersistentPersoonNationaliteit ppersNation = new PersistentPersoonNationaliteit();
        ppersNation.setPers(persistentPersoon);
        persistentPersoon.setId(12L);
        Nationaliteit nationaliteit = new Nationaliteit();
        ReflectionTestUtils.setField(nationaliteit, "code", "NL");
        Mockito.when(referentieDataRepository.vindNationaliteitOpCode(Matchers.anyString())).thenReturn(nationaliteit);
        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(persistentPersoon);
        Mockito.when(persoonNationaliteitRepository.save(Matchers.any(PersistentPersoonNationaliteit.class)))
               .thenReturn(ppersNation);
        BRPActie actie = maakActie();
        BerichtContext bc = bouwBerichtContext();
        registratieNationaliteitUitvoerder.verwerkActie(actie, bc);
        ArgumentCaptor<PersistentPersoonNationaliteit> argument1 =
            ArgumentCaptor.forClass(PersistentPersoonNationaliteit.class);
        ArgumentCaptor<PersistentPersoonNationaliteit> argument2 =
            ArgumentCaptor.forClass(PersistentPersoonNationaliteit.class);

        Mockito.verify(actieRepository, Mockito.times(1)).save(Mockito.any(PersistentActie.class));

        Mockito.verify(persoonNationaliteitRepository, Mockito.times(1)).save(argument1.capture());

        Mockito.verify(persoonNationaliteitHistorieRepository, Mockito.times(1)).persisteerHistorie(
            argument2.capture(),
            Matchers.any(PersistentPersoon.class),
            Matchers.any(PersistentActie.class),
            Matchers.anyInt(),
            Matchers.anyInt());

        Assert.assertEquals("NL", argument1.getValue().getNationaliteit().getCode());
        Assert.assertEquals(12L, argument1.getValue().getPers().getId().longValue());
        Assert.assertEquals(12L, argument2.getValue().getPers().getId().longValue());

        // 1 hoofd persoon bijgehouden.
        Assert.assertEquals(1, bc.getHoofdPersonen().size());
        Assert.assertEquals(0, bc.getBijPersonen().size());

    }

    /** Verifieert dat de historie ook wordt opgeslagen. */
    @Test
    public void testRegistratieNationaliteitNormaleFlowMetOpslagHistorie() {
        PersistentPersoon persistentPersoon = new PersistentPersoon();
        persistentPersoon.setId(12L);
        Nationaliteit nationaliteit = new Nationaliteit();
        ReflectionTestUtils.setField(nationaliteit, "code", "NL");
        Mockito.when(referentieDataRepository.vindNationaliteitOpCode(Matchers.anyString())).thenReturn(nationaliteit);
        Mockito.when(persoonRepository.findByBurgerservicenummer("123456789")).thenReturn(persistentPersoon);
        BRPActie actie = maakActie();
        PersistentPersoonNationaliteit persnation = new PersistentPersoonNationaliteit();
        Mockito.when(persoonNationaliteitRepository.save(Matchers.any(PersistentPersoonNationaliteit.class)))
               .thenReturn(persnation);
        registratieNationaliteitUitvoerder.verwerkActie(actie, bouwBerichtContext());
        Mockito.verify(persoonNationaliteitHistorieRepository, Mockito.times(1)).persisteerHistorie(
            Matchers.any(PersistentPersoonNationaliteit.class), Matchers.any(PersistentPersoon.class),
            Matchers.any(PersistentActie.class), Matchers.eq(actie.getDatumAanvangGeldigheid()),
            Matchers.isNull(Integer.class));
    }

    @Test
    public void testRegistratieNationaliteitGeenNationaliteitenOpgegeven() {
        BRPActie actie = maakActie();
        Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        ReflectionTestUtils.setField(persoon, "nationaliteiten", null);
        registratieNationaliteitUitvoerder.verwerkActie(actie, bouwBerichtContext());
    }
}


