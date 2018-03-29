/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie.it;

import java.io.StringReader;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.delivery.synchronisatie.SynchronisatieWebServiceImpl;
import nl.bzk.brp.delivery.synchronisatie.SynchroniseerStamgegevenCallbackImpl;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import nl.bzk.brp.service.synchronisatie.stamgegeven.BepaalStamgegevenResultaat;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenBerichtFactory;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test test alle synchroniseer stamgegevens berichten.
 */
@ContextConfiguration(classes = SynchroniseerStamgegevenAntwoordBerichtWriterCompleetIntegratieTest.SynchonisatieTestConfiguratie.class)
public class SynchroniseerStamgegevenAntwoordBerichtWriterCompleetIntegratieTest extends AbstractDataAccessTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private DataSource dataSource;

    @Inject
    private StamTabelService stamTabelService;

    @Inject
    private SynchroniseerStamgegevenBerichtFactory maakAntwoordBerichtService;

    @Inject
    private SchemaValidatorService schemaValidatorService;

    @Inject
    private PartijService partijService;

    @Test
    public void testAllStamgegevensBerichten() throws Exception {
        final String referentienummer = "88409eeb-1aa5-43fc-8614-43055123a165";
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getStuurgegevens().setZendendePartijCode(String.valueOf(Partij.PARTIJ_CODE_BRP));
        verzoek.getStuurgegevens().setReferentieNummer(referentienummer);
        final BepaalStamgegevenResultaat resultaat = new BepaalStamgegevenResultaat(verzoek);
        resultaat.setBrpPartij(partijService.geefBrpPartij());
        for (ObjectElement objectElement : ElementHelper.getObjecten()) {
            LOGGER.info("Stamgegeven: " + objectElement.getElementNaam());
            if (objectElement.getSoortInhoud() != null && !("D".equals(objectElement.getSoortInhoud())) && objectElement.inBericht()) {
                final StamtabelGegevens stamgegevens = stamTabelService.geefStamgegevens(objectElement.getNaam() + StamtabelGegevens.TABEL_POSTFIX);
                resultaat.setStamgegevens(stamgegevens);
                final SynchroniseerStamgegevenBericht bericht = maakAntwoordBerichtService.maakBericht(resultaat);
                SynchroniseerStamgegevenCallbackImpl callback = new SynchroniseerStamgegevenCallbackImpl();
                callback.verwerkResultaat(bericht);
                final String xml = callback.getResultaat();
                try {
                    schemaValidatorService.valideer(new StreamSource(new StringReader(xml)), SynchronisatieWebServiceImpl.SCHEMA);
                } catch (Exception e) {
                    throw new IllegalArgumentException(objectElement.getNaam());
                }
            }
        }
    }

    @Import(DataAccessTestConfiguratie.class)
    @ImportResource({
            "classpath:levering-kern-algemeen.xml",
            "classpath:synchronisatie-beans.xml",
            "classpath:delivery-algemeen-beans.xml",
            "classpath:maakbericht-service-beans.xml"
    })
    public static class SynchonisatieTestConfiguratie {

        @Bean
        ArchiefService maakArchiefService() {
            return Mockito.mock(ArchiefService.class);
        }
        @Bean
        PlaatsAfnemerBerichtService maakPlaatsAfnemerBerichtService() {
            return Mockito.mock(PlaatsAfnemerBerichtService.class);
        }

        @Bean(name = "jmxdomein")
        public String maakJMXDomein() {
            return "jxm";
        }
    }
}
