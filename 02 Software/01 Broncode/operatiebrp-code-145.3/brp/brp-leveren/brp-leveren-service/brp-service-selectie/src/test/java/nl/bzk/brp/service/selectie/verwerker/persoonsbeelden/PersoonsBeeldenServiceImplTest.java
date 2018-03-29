/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.persoonsbeelden;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * PersoonsBeeldenServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonsBeeldenServiceImplTest {

    @Mock
    private ConfiguratieService configuratieService;

    @Mock
    private PersoonslijstService persoonslijstService;

    @InjectMocks
    private PersoonsBeeldenServiceImpl persoonsBeeldenService;

    @Test
    public void happyFlow() throws SelectieException, BlobException {
        Mockito.when(configuratieService.getVerwerkerPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdMaakPersoonsBeeldMin()).thenReturn(10L);

        Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);

        Mockito.when(persoonslijstService.maak(Mockito.any(), Mockito.any(), Mockito.anyLong())).thenReturn(persoonslijst);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieRunId(1);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatyBerichts = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        final String dummy = "dummy";
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens(new String(Base64.getEncoder().encode(dummy.getBytes())));
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        selectieAutorisatyBerichts.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatyBerichts);

        final Collection<Persoonslijst> persoonslijstQueue = persoonsBeeldenService.maakPersoonsBeelden(selectieTaak);
    }

    @Test(expected = SelectieException.class)
    public void happyFoutFlow() throws SelectieException, BlobException {
        Mockito.when(configuratieService.getVerwerkerPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdMaakPersoonsBeeldMin()).thenReturn(0L);

        Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        Mockito.when(persoonslijstService.maak(Mockito.any(), Mockito.any(), Mockito.anyLong())).thenReturn(persoonslijst);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieRunId(1);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatyBerichts = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        final String dummy = "dummy";
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens(new String(Base64.getEncoder().encode(dummy.getBytes())));
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        selectieAutorisatyBerichts.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatyBerichts);

        final Collection<Persoonslijst> persoonslijstQueue = persoonsBeeldenService.maakPersoonsBeelden(selectieTaak);

    }
}


