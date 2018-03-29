/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonslijstServiceImplTest {

    private final long PERSID = 1;

    @InjectMocks
    private PersoonslijstServiceImpl persoonslijstService;

    @Mock
    private PersoonCacheRepository persoonCacheRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testPersoonBestaatNiet() {
        final Persoonslijst persoonslijst = persoonslijstService.getById(1);
        Assert.assertNull(persoonslijst);
    }

    /**
     * Als de persooncache aanwezig is wordt op basis van de blob geleverd.
     */
    @Test
    public void testPersoonCacheCompleet() throws BlobException {

        final Persoon persoon = maakPersoon();
        final PersoonAfnemerindicatie persoonAfnemerindicatie = new PersoonAfnemerindicatie(persoon, new Partij("testpartij", "000002"),
                new Leveringsautorisatie(Stelsel.BRP, false));
        persoonAfnemerindicatie.setId(1L);
        final List<PersoonAfnemerindicatie> afnemerindicatieList = Lists.newArrayList(
                persoonAfnemerindicatie
        );

        //er een cache
        final PersoonCache persoonCache = new PersoonCache(persoon, (short) 1);
        persoonCache.setPersoonHistorieVolledigGegevens(Blobber.toJsonBytes(Blobber.maakBlob(persoon)));
        persoonCache.setAfnemerindicatieGegevens(Blobber.toJsonBytes(Blobber.maakBlob(afnemerindicatieList)));
        Mockito.when(persoonCacheRepository.haalPersoonCacheOp(PERSID)).thenReturn(persoonCache);

        final Persoonslijst persoonslijst = persoonslijstService.getById(PERSID);

        Assert.assertNotNull(persoonslijst);

        Mockito.verify(persoonCacheRepository).haalPersoonCacheOp(PERSID);
    }

    @Test
    public void testByIdsVoorZoeken() throws BlobException, StapException {
        final Persoon persoon = maakPersoon();
        final PersoonAfnemerindicatie persoonAfnemerindicatie = new PersoonAfnemerindicatie(persoon, new Partij("testpartij", "000002"),
                new Leveringsautorisatie(Stelsel.BRP, false));
        persoonAfnemerindicatie.setId(1L);
        final List<PersoonAfnemerindicatie> afnemerindicatieList = Lists.newArrayList(
                persoonAfnemerindicatie
        );
        Set<Long> persIds = Sets.newHashSet((long) PERSID);

        //er een cache
        final PersoonCache persoonCache = new PersoonCache(persoon, (short) 1);
        persoonCache.setPersoonHistorieVolledigGegevens(Blobber.toJsonBytes(Blobber.maakBlob(persoon)));
        persoonCache.setAfnemerindicatieGegevens(Blobber.toJsonBytes(Blobber.maakBlob(afnemerindicatieList)));
        Mockito.when(persoonCacheRepository.haalPersoonCachesOp(persIds)).thenReturn(Lists.newArrayList(persoonCache));

        List<Persoonslijst> persoonslijstLijst = persoonslijstService.getByIdsVoorZoeken(persIds);
        Assert.assertEquals(1, persoonslijstLijst.size());

        Mockito.verify(persoonCacheRepository).haalPersoonCachesOp(persIds);
    }

    @Test
    public void testByIdsVoorZoekenMinderBlobsGevondenDanGevraagd() throws BlobException, StapException {
        expectedException.expect(StapException.class);
        expectedException.expectMessage("minder blobs gevonden dan gevraagd, mag niet voorkomen");

        final Persoon persoon = maakPersoon();
        final PersoonAfnemerindicatie persoonAfnemerindicatie = new PersoonAfnemerindicatie(persoon, new Partij("testpartij", "000002"),
                new Leveringsautorisatie(Stelsel.BRP, false));
        persoonAfnemerindicatie.setId(1L);
        final List<PersoonAfnemerindicatie> afnemerindicatieList = Lists.newArrayList(
                persoonAfnemerindicatie
        );
        //er een cache
        final PersoonCache persoonCache = new PersoonCache(persoon, (short) 1);
        persoonCache.setPersoonHistorieVolledigGegevens(Blobber.toJsonBytes(Blobber.maakBlob(persoon)));
        persoonCache.setAfnemerindicatieGegevens(Blobber.toJsonBytes(Blobber.maakBlob(afnemerindicatieList)));
        Mockito.when(persoonCacheRepository.haalPersoonCacheOp(PERSID)).thenReturn(persoonCache);

        persoonslijstService.getByIdsVoorZoeken(Sets.newHashSet((long) PERSID));
    }

    private Persoon maakPersoon() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(PERSID);

        final Partij partij = new Partij("xxx", "000012");
        final Timestamp tsReg = new Timestamp(System.currentTimeMillis());
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(partij, SoortAdministratieveHandeling
                .AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, tsReg);
        administratieveHandeling.setId(1L);
        final BRPActie actieInhoud = new BRPActie(SoortActie.BEEINDIGING_VOORNAAM, administratieveHandeling, partij, tsReg);
        actieInhoud.setId(1L);
        administratieveHandeling.addActie(actieInhoud);

        final PersoonAfgeleidAdministratiefHistorie persoonAfgeleidAdministratiefHistorie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, persoon, administratieveHandeling, tsReg);
        persoonAfgeleidAdministratiefHistorie.setId(1L);
        persoonAfgeleidAdministratiefHistorie.setActieInhoud(actieInhoud);
        persoon.getPersoonAfgeleidAdministratiefHistorieSet().add(persoonAfgeleidAdministratiefHistorie);
        return persoon;
    }
}
