/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.common.collect.Iterables;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link AbstractMaakBerichtServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractMaakBerichtServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieValidatieService;
    @Mock
    private PartijService partijService;
    @InjectMocks
    private final TestAbstractMaakBerichtServiceImpl service = new TestAbstractMaakBerichtServiceImpl(leveringsautorisatieValidatieService, partijService);

    @Test
    public void autorisatieFaalt() throws Exception {
        Mockito.doThrow(AutorisatieException.class).when(leveringsautorisatieValidatieService).controleerAutorisatie(Mockito.any());
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.getStuurgegevens().setZendendePartijCode("01234");
        verzoek.getParameters().setLeveringsAutorisatieId("1");
        verzoek.setOin(new OIN("12345", "12345"));
        verzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        verzoek.getParameters().setDienstIdentificatie("2");
        verzoek.getParameters().setRolNaam("rol");

        final BevragingResultaat zoekPersoonResultaat = service.voerStappenUit(verzoek);

        assertThat(zoekPersoonResultaat.getMeldingen().isEmpty(), is(false));
        assertThat(Iterables.getOnlyElement(zoekPersoonResultaat.getMeldingen()).getRegel(), is(Regel.R2343));
        assertThat(zoekPersoonResultaat.getAutorisatiebundel(), is(nullValue()));
    }

    private class TestAbstractMaakBerichtServiceImpl extends AbstractMaakBerichtServiceImpl<BevragingVerzoek, BevragingResultaat> {

        /**
         * @param leveringsautorisatieService de leveringsautorisatie validatieservice
         * @param partijService de partij service
         */
        public TestAbstractMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService,
                                                  final PartijService partijService) {
            super(leveringsautorisatieService, partijService);
        }

        @Override
        protected BevragingResultaat maakResultaatObject() {
            return new BevragingResultaat();
        }

        @Override
        protected void voerDienstSpecifiekeStappenUit(final BevragingVerzoek verzoek, final BevragingResultaat resultaat) throws StapException {

        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }

        @Override
        protected String getDienstSpecifiekeLoggingString() {
            return "test";
        }
    }
}