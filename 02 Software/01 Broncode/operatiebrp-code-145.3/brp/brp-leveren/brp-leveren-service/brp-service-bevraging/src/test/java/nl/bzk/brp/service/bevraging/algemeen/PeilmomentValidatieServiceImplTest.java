/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.request.DatumService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PeilmomentValidatieServiceImplTest {

    private static final String TOEKOMST = "toekomst";
    private static final String VERLEDEN = "verleden";

    @InjectMocks
    private PeilmomentValidatieServiceImpl peilmomentValidatieService;

    @Mock
    private DatumService datumService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMaterieelPeilmomentInVerleden() throws Exception {
        mockMaterieel(VERLEDEN);

        peilmomentValidatieService.valideerMaterieel(VERLEDEN);
    }

    @Test
    public void testMaterieelPeilmomentInToekomst() throws Exception {
        mockMaterieel(TOEKOMST);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2295));

        peilmomentValidatieService.valideerMaterieel(TOEKOMST);
    }

    @Test
    public void testMaterieelInVerledenFormeelInVerleden() throws Exception {
        mockMaterieel(VERLEDEN);
        mockFormeel(VERLEDEN);

        peilmomentValidatieService.valideerFormeelEnMaterieel(VERLEDEN, TOEKOMST);
    }

    @Test
    public void testMaterieelInVerledenFormeelInToekomst() throws Exception {
        mockMaterieel(VERLEDEN);
        mockFormeel(TOEKOMST);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2222));

        peilmomentValidatieService.valideerFormeelEnMaterieel(TOEKOMST, VERLEDEN);
    }

    @Test
    public void testMaterieelInToekomstFormeelInVerleden() throws Exception {
        mockMaterieel(TOEKOMST);
        mockFormeel(VERLEDEN);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2295));

        peilmomentValidatieService.valideerFormeelEnMaterieel(VERLEDEN, TOEKOMST);
    }

    //niet gespecificeerd, maar formeel heeft voorrang
    @Test
    public void testMaterieelInToekomstFormeelInToekomst() throws Exception {
        mockMaterieel(TOEKOMST);
        mockFormeel(TOEKOMST);
        thrown.expect(new ExceptionRegelMatcher(Regel.R2222));

        peilmomentValidatieService.valideerFormeelEnMaterieel(TOEKOMST, TOEKOMST);
    }


    private void mockFormeel(String moment) throws Exception {
        if (VERLEDEN.equals(moment)) {
            Mockito.when(datumService.parseDateTime(Mockito.anyString())).thenReturn(ZonedDateTime.now().minusYears(1L));
        } else {
            Mockito.when(datumService.parseDateTime(Mockito.anyString())).thenReturn(ZonedDateTime.now().plusYears(1L));
        }
    }

    private void mockMaterieel(String moment) throws Exception {
        if (VERLEDEN.equals(moment)) {
            Mockito.when(datumService.parseDate(Mockito.anyString())).thenReturn(LocalDate.now().minusYears(1L));
        } else {
            Mockito.when(datumService.parseDate(Mockito.anyString())).thenReturn(LocalDate.now().plusYears(1L));
        }
    }
}
