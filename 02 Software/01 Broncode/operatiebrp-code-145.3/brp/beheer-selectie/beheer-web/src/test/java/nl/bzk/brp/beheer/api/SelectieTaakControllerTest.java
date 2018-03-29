/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.api;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.time.LocalDate;
import nl.bzk.brp.beheer.service.selectie.SelectiePeriodeDTO;
import nl.bzk.brp.beheer.service.selectie.SelectieTaakDTO;
import nl.bzk.brp.beheer.service.selectie.SelectieTaakService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * UT voor {@link SelectieTaakController}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SelectieTaakDTO.class)
public class SelectieTaakControllerTest {

    @InjectMocks
    private SelectieTaakController selectieTaakController;
    @Mock
    private SelectieTaakService selectieTaakService;

    @Test
    public void getSelectieTaken() {
        LocalDate now = LocalDate.now();
        SelectiePeriodeDTO selectiePeriode = new SelectiePeriodeDTO(now, now);
        selectieTaakController.getSelectieTakenBinnenPeriode(selectiePeriode);

        verify(selectieTaakService).getSelectieTaken(selectiePeriode);
    }

    @Test
    public void slaSelectieTaakOp() {
        SelectieTaakDTO selectieTaak = mock(SelectieTaakDTO.class);
        selectieTaakController.slaSelectieTaakOp(selectieTaak);

        verify(selectieTaakService).slaSelectieTaakOp(selectieTaak);
    }

}