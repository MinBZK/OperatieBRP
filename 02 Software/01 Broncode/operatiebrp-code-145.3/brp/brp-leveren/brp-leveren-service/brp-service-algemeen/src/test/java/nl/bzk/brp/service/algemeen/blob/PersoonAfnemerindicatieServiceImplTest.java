/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersoonAfnemerindicatieServiceImplTest {

    private static final Persoonslijst PERSOONSLIJST = new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L);
    @InjectMocks
    private PersoonAfnemerindicatieServiceImpl service;
    @Mock
    private AfnemerindicatieRepository afnemerindicatieRepository;
    @Mock
    private PersoonslijstService persoonslijstService;

    @Test
    public void testPlaatsing() throws Exception {
        BrpNu.set();
        final Partij partij = TestPartijBuilder.maakBuilder().metId(456).metCode("000123").build();

        final long persoonId = 1;
        final int leveringsautorisatieId = 2;
        final int dienstId = 3;
        final int datumAanvangMaterielePeriode = DatumUtil.vandaag();
        final int datumEindeVolgen = DatumUtil.vandaag();

        Mockito.when(persoonslijstService.getById(persoonId)).thenReturn(PERSOONSLIJST);

        service.plaatsAfnemerindicatie(
                new AfnemerindicatieParameters(persoonId, PERSOONSLIJST.getPersoonLockVersie(), PERSOONSLIJST.getAfnemerindicatieLockVersie()),
                partij,
                leveringsautorisatieId, dienstId, datumAanvangMaterielePeriode,
                datumEindeVolgen, BrpNu.get().getDatum());

        Mockito.verify(afnemerindicatieRepository).plaatsAfnemerindicatie(persoonId, partij.getId(), leveringsautorisatieId,
                dienstId, datumEindeVolgen, datumAanvangMaterielePeriode, BrpNu.get().getDatum());
    }

    @Test
    public void testVerwijderen() throws BlobException {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(20).metCode("000123").build();
        final MetaObject.Builder builder = TestBuilders.maakIngeschrevenPersoon();
        final int leveringsAutorisatieId = 10;
        builder.metObject(TestBuilders.maakAfnemerindicatie(leveringsAutorisatieId, partij.getCode()));
        final Persoonslijst persoonslijst = new Persoonslijst(builder.build(), 0L);
        Mockito.when(persoonslijstService.getById(PERSOONSLIJST.getId())).thenReturn(persoonslijst);

        final int dienstId = 1;
        service.verwijderAfnemerindicatie(
                new AfnemerindicatieParameters(persoonslijst.getId(), persoonslijst.getPersoonLockVersie(), persoonslijst.getAfnemerindicatieLockVersie()),
                partij,
                dienstId, leveringsAutorisatieId);

        Mockito.verify(afnemerindicatieRepository).verwijderAfnemerindicatie(PERSOONSLIJST.getId(), partij.getId(), leveringsAutorisatieId, dienstId);
    }

}
