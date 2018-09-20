/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractUc220Test extends AbstractSynchronisatieServiceIntegratieTest {

    @Inject
    @Named("synchroniseerNaarBrpService")
    private SynchronisatieBerichtService<SynchroniseerNaarBrpVerzoekBericht, SynchroniseerNaarBrpAntwoordBericht> subject;

    @Inject
    @Named("syncParameters")
    private SyncParameters syncParameters;

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();
    private final Lo3PersoonslijstParser lo3PersoonslijstParser = new Lo3PersoonslijstParser();

    @Before
    public void setupSyncParameters() {
        syncParameters.setInitieleVulling(false);
    }

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    /**
     * Geef de waarde van subject.
     *
     * @return subject
     */
    protected SynchronisatieBerichtService<SynchroniseerNaarBrpVerzoekBericht, SynchroniseerNaarBrpAntwoordBericht> getSubject() {
        return subject;
    }

    SynchroniseerNaarBrpVerzoekBericht maakVerzoekObvExcel(final String resource) {
        try (InputStream is = Uc22011c40t030Test.class.getResourceAsStream(resource)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource '" + resource + "' niet gevonden.");
            }
            final List<ExcelData> excelData = excelAdapter.leesExcelBestand(is);
            Assert.assertEquals(1, excelData.size());
            final Lo3Persoonslijst lo3Persoonslijst = lo3PersoonslijstParser.parse(excelData.get(0).getCategorieLijst());

            final SynchroniseerNaarBrpVerzoekBericht result = new SynchroniseerNaarBrpVerzoekBericht();
            result.setMessageId(resource);
            result.setLo3Persoonslijst(lo3Persoonslijst);

            return result;

        } catch (final
            IOException
            | ExcelAdapterException
            | Lo3SyntaxException e)
        {
            throw new IllegalArgumentException("Kan excel niet omzetten naar sync verzoek.", e);
        }
    }
}
