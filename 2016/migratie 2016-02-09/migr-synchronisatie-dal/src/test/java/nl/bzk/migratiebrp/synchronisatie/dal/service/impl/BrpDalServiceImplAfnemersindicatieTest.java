/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.AfnemersindicatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpDalServiceImplAfnemersindicatieTest {

    // Params
    private static final Long ADMINISTRATIENUMMER = 123456789L;
    private static final long DATUMTIJD_REGISTRATIE = 19920101145000L;
    private static final Timestamp TIMESTAMP_REGISTRATIE = new Timestamp(BrpDatumTijd.fromDatumTijd(DATUMTIJD_REGISTRATIE, null).getJavaDate().getTime());
    private static final Integer PARTIJ_CODE = 800007;

    @Mock
    private PersoonRepository persoonRepositoryMock;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepositoryMock;
    @Mock
    private LeveringsautorisatieRepository leveringsautorisatieRepositoryMock;
    @Mock
    private AfnemersindicatieRepository afnemersindicatieRepositoryMock;

    @InjectMocks
    private BrpDalServiceImpl service;

    @Before
    public void defaultSetup() {
        Logging.initContext();
    }

    @Test
    public void testPersisteerAfnemersIndicatie() {
        final Persoon persoon = Mockito.mock(Persoon.class);
        final Partij partij = Mockito.mock(Partij.class);
        final PartijRol partijRol = Mockito.mock(PartijRol.class);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        final List<Leveringsautorisatie> leveringsautorisaties = Collections.singletonList(leveringsautorisatie);

        Mockito.when(persoonRepositoryMock.findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false)).thenReturn(
            Collections.singletonList(persoon));
        Mockito.when(dynamischeStamtabelRepositoryMock.findPartijByCode(PARTIJ_CODE)).thenReturn(partij);
        Mockito.when(dynamischeStamtabelRepositoryMock.getPartijRolByPartij(partij, Rol.AFNEMER)).thenReturn(partijRol);
        Mockito.when(leveringsautorisatieRepositoryMock.findLeveringsautorisatiesVoorPartij(partijRol)).thenReturn(leveringsautorisaties);

        // Execute
        final BrpAfnemersindicaties brpAfnemersindicaties = maakBrpAfnemersindicaties();
        service.persisteerAfnemersindicaties(brpAfnemersindicaties);

        // Verify
        Mockito.verify(persoonRepositoryMock).findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false);
        Mockito.verify(dynamischeStamtabelRepositoryMock).findPartijByCode(PARTIJ_CODE);
        Mockito.verify(dynamischeStamtabelRepositoryMock).getPartijRolByPartij(partij, Rol.AFNEMER);
        Mockito.verify(leveringsautorisatieRepositoryMock).findLeveringsautorisatiesVoorPartij(partijRol);

        final ArgumentCaptor<PersoonAfnemerindicatie> afnemerindicatieCaptor = ArgumentCaptor.forClass(PersoonAfnemerindicatie.class);
        Mockito.verify(afnemersindicatieRepositoryMock).save(afnemerindicatieCaptor.capture());

        // End
        Mockito.verifyNoMoreInteractions(
            persoonRepositoryMock,
            dynamischeStamtabelRepositoryMock,
            leveringsautorisatieRepositoryMock,
            afnemersindicatieRepositoryMock);

        // Check saved instance
        final PersoonAfnemerindicatie persoonAfnemerindicatie = afnemerindicatieCaptor.getValue();
        Assert.assertEquals(persoon, persoonAfnemerindicatie.getPersoon());
        Assert.assertEquals(partij, persoonAfnemerindicatie.getAfnemer());
        Assert.assertEquals(leveringsautorisatie, persoonAfnemerindicatie.getLeveringsautorisatie());
        Assert.assertEquals(null, persoonAfnemerindicatie.getDatumAanvangMaterielePeriode());
        Assert.assertEquals(null, persoonAfnemerindicatie.getDatumEindeVolgen());
    }

    private BrpAfnemersindicaties maakBrpAfnemersindicaties() {
        final BrpAfnemersindicatieInhoud inhoud = new BrpAfnemersindicatieInhoud(null, null, false);
        final BrpHistorie historie = new BrpHistorie(null, null, BrpDatumTijd.fromDatumTijd(DATUMTIJD_REGISTRATIE, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MIGRATIEVOORZIENING, BrpDatumTijd.fromDatumTijd(
                    DATUMTIJD_REGISTRATIE,
                    null), null, null, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, 0, -1));
        final BrpGroep<BrpAfnemersindicatieInhoud> categorie = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);

        final BrpStapel<BrpAfnemersindicatieInhoud> stapel = new BrpStapel<>(Collections.singletonList(categorie));

        return new BrpAfnemersindicaties(ADMINISTRATIENUMMER, Collections.singletonList(new BrpAfnemersindicatie(
            new BrpPartijCode(PARTIJ_CODE),
            stapel,
            null)));
    }

    @Test
    public void testPersisteerAfnemersindicatiesGeenPersoon() {
        Mockito.when(persoonRepositoryMock.findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false)).thenReturn(null);

        // Execute
        final BrpAfnemersindicaties brpAfnemersindicaties = maakBrpAfnemersindicaties();
        service.persisteerAfnemersindicaties(brpAfnemersindicaties);

        Mockito.verifyNoMoreInteractions(dynamischeStamtabelRepositoryMock, afnemersindicatieRepositoryMock);

        // Expect logging (AFN009)
        final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
        Assert.assertEquals(1, logRegels.size());
        Assert.assertEquals(SoortMeldingCode.AFN009, logRegels.iterator().next().getSoortMeldingCode());
    }

    @Test
    public void testPersisteerAfnemersindicatiesGeenPartij() {
        final Persoon persoon = Mockito.mock(Persoon.class);

        Mockito.when(persoonRepositoryMock.findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false)).thenReturn(
            Collections.singletonList(persoon));
        Mockito.when(dynamischeStamtabelRepositoryMock.findPartijByCode(PARTIJ_CODE)).thenReturn(null);

        // Execute
        final BrpAfnemersindicaties brpAfnemersindicaties = maakBrpAfnemersindicaties();
        service.persisteerAfnemersindicaties(brpAfnemersindicaties);

        // Verify
        Mockito.verify(persoonRepositoryMock).findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false);
        Mockito.verify(dynamischeStamtabelRepositoryMock).findPartijByCode(PARTIJ_CODE);
        Mockito.verifyNoMoreInteractions(persoonRepositoryMock, dynamischeStamtabelRepositoryMock, afnemersindicatieRepositoryMock);

        // Expect logging (AFN010)
        final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
        Assert.assertEquals(1, logRegels.size());
        Assert.assertEquals(SoortMeldingCode.AFN010, logRegels.iterator().next().getSoortMeldingCode());

    }

    @Test(expected = IllegalStateException.class)
    public void testBevraagAfnemersindicatiesGeenPersoon() {
        // Execute
        service.bevraagAfnemersindicaties(ADMINISTRATIENUMMER);
    }

    @Test
    public void testBevraagAfnemersindicatiesGeenAfnemersindicaties() {
        final Persoon persoon = Mockito.mock(Persoon.class);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ADMINISTRATIENUMMER);

        Mockito.when(persoonRepositoryMock.findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false)).thenReturn(
            Collections.singletonList(persoon));

        // Execute
        service.bevraagAfnemersindicaties(ADMINISTRATIENUMMER);

        // Execute
        final BrpAfnemersindicaties result = service.bevraagAfnemersindicaties(ADMINISTRATIENUMMER);

        // Verify
        Assert.assertEquals(ADMINISTRATIENUMMER, result.getAdministratienummer());
        Assert.assertEquals(0, result.getAfnemersindicaties().size());
    }

    @Test
    public void testBevraagAfnemersindicaties() {
        // Persoon
        final Persoon persoon = Mockito.mock(Persoon.class);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ADMINISTRATIENUMMER);

        Mockito.when(persoonRepositoryMock.findByAdministratienummer(ADMINISTRATIENUMMER, SoortPersoon.INGESCHREVENE, false)).thenReturn(
            Collections.singletonList(persoon));

        // Afnemer
        final Partij partij = Mockito.mock(Partij.class);
        Mockito.when(partij.getCode()).thenReturn(PARTIJ_CODE);

        // Leveringsautorisatie
        final Leveringsautorisatie leveringsautorisatie = Mockito.mock(Leveringsautorisatie.class);
        Mockito.when(leveringsautorisatie.getNaam()).thenReturn("Lev-aut-naam");

        // Afnemersindicatie
        final PersoonAfnemerindicatie persoonAfnemerindicatie = Mockito.mock(PersoonAfnemerindicatie.class);
        Mockito.when(persoonAfnemerindicatie.getPersoon()).thenReturn(persoon);
        Mockito.when(persoonAfnemerindicatie.getAfnemer()).thenReturn(partij);
        Mockito.when(persoonAfnemerindicatie.getLeveringsautorisatie()).thenReturn(leveringsautorisatie);

        Mockito.when(afnemersindicatieRepositoryMock.findByPersoon(persoon)).thenReturn(Collections.singletonList(persoonAfnemerindicatie));

        final PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie = Mockito.mock(PersoonAfnemerindicatieHistorie.class);
        Mockito.when(persoonAfnemerindicatieHistorie.getDatumAanvangMaterielePeriode()).thenReturn(0);
        Mockito.when(persoonAfnemerindicatieHistorie.getDatumTijdRegistratie()).thenReturn(TIMESTAMP_REGISTRATIE);

        Mockito.when(persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorieSet()).thenReturn(Collections.singleton(persoonAfnemerindicatieHistorie));

        // Execute
        final BrpAfnemersindicaties result = service.bevraagAfnemersindicaties(ADMINISTRATIENUMMER);

        // Verify
        Assert.assertEquals(ADMINISTRATIENUMMER, result.getAdministratienummer());
        Assert.assertEquals(1, result.getAfnemersindicaties().size());
        Assert.assertEquals(PARTIJ_CODE, result.getAfnemersindicaties().get(0).getPartijCode().getWaarde());
        Assert.assertEquals("Lev-aut-naam", result.getAfnemersindicaties().get(0).getLeveringautorisatie());

        final BrpStapel<BrpAfnemersindicatieInhoud> stapel = result.getAfnemersindicaties().get(0).getAfnemersindicatieStapel();
        Assert.assertEquals(1, stapel.size());
        final BrpGroep<BrpAfnemersindicatieInhoud> groep = stapel.get(0);
        Assert.assertNotNull(groep);
        Assert.assertNull(groep.getActieInhoud());
    }
}
