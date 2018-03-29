/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import java.io.File;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieResultaatWriterImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieResultaatWriterFactoryImplTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private SelectieFileService selectieFileService;

    @Test
    public void testHappyFlow() throws Exception {
        Path path = folder.newFolder().toPath();
        Mockito.when(selectieFileService.getSelectietaakResultaatPath(Mockito.anyInt(), Mockito.anyInt())).thenReturn(path);
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        final SelectieKenmerken selectieKenmerken = SelectieKenmerken.builder()
                .metDienst(dienst)
                .metLeveringsautorisatie(autorisatiebundel.getLeveringsautorisatie())
                .metSelectietaakId(1)
                .metDatumUitvoer(DatumUtil.vandaag())
                .metPeilmomentMaterieelResultaat(DatumUtil.vandaag())
                .metPeilmomentFormeelResultaat(Timestamp.from(DatumUtil.nuAlsZonedDateTime().toInstant()))
                .metSoortSelectie(SoortSelectie.STANDAARD_SELECTIE)
                .metSoortSelectieresultaatSet("Resultaatset personen")//TODO ROOD-1943 geen enum?
                .metSoortSelectieresultaatVolgnummer(1)
                .build();
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(new Partij("1", "000002"))
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .eindeStuurgegevens()
                .metParameters().metDienst(dienst).metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT).eindeParameters()
                .build();
        final SelectieResultaatBericht bericht = new SelectieResultaatBericht(basisBerichtGegevens, selectieKenmerken);

        SelectieResultaatBerichtWriter.BrpPersoonWriter selectieResultaatWriter =
                new SelectieResultaatBerichtWriter.BrpPersoonWriter(File.createTempFile("bla", "bla").toPath(), bericht);
        selectieResultaatWriter.voegPersoonToe("dummy");
        selectieResultaatWriter.eindeBericht();

    }


}
