/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.jenkins;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaAttribuutLiteral;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.tooling.apitest.service.basis.impl.BasisConfiguratie;
import nl.bzk.brp.tooling.apitest.service.dataaccess.BlobDirectory;
import nl.bzk.brp.tooling.apitest.service.dataaccess.PersoonDataStubService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

@RunWith(Parameterized.class)
@ContextConfiguration(classes = BasisConfiguratie.class)
public class IT_PDT_ExpressieEvaluatie {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private TestContextManager testContextManager;

    @Autowired
    private PersoonDataStubService persoonDataStubService;
    @Autowired
    private PersoonslijstService persoonslijstService;
    @Parameterized.Parameter(value = 0)
    public Path blobDirectory;

    private static Expressie max1ActueelExpressie;


    public IT_PDT_ExpressieEvaluatie() throws ExpressieException, IOException {

    }

    @Parameterized.Parameters
    public static Collection<Path> data() throws Exception {
        Collection<Path> params = new ArrayList<>();
        FileVisitor<Path> blobDirectoryVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                if (dir.toString().endsWith("_xls/")) {
                    params.add(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        };

        final Path path = BlobDirectory.resolveJarPath("oranje:/");
        try (final FileSystem fileSystem = FileSystems.newFileSystem(path, null)) {
            Files.walkFileTree(fileSystem.getPath("/"), blobDirectoryVisitor);
        }
        return params;
    }

    @BeforeClass
    public static void beforeClass() throws ExpressieException {
        max1ActueelExpressie = ExpressieParser.parse("Persoon.Bijhouding.DatumAanvangGeldigheid WAARBIJ attribuutalswaarde = ONWAAR");
    }

    @Before
    public void voorTest() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
    }

    /**
     * Evalueer PDT expressies op personen uit blobdirectories.
     */
    @Test
    public void evalueerPdtExpressies() throws Exception {
        String dir = blobDirectory.toString();
        dir = dir.substring(1, dir.length() - 1);
        persoonDataStubService.laadPersonen(Lists.newArrayList("oranje:" + dir));
        final Set<Long> persoonIds = persoonDataStubService.geefAllePersoonIds();
        persoonIds.stream().map(persoonslijstService::getById).
                collect(Collectors.toList()).forEach(this::eval);

    }


    private void eval(final Persoonslijst persoonslijst)  {
        final Consumer<Persoonslijst> max1ActueelConsumer = (p) -> {
            try {
                final LijstExpressie lijst = (LijstExpressie) BRPExpressies.evalueerMetSelectieDatum(max1ActueelExpressie, p, DatumUtil.vandaag());

                if (lijst.size() > 1) {
                    for (Expressie expressie : lijst) {
                        MetaAttribuutLiteral literal = (MetaAttribuutLiteral) expressie;
                        LOGGER.debug("actueel voorkomen = {}", literal.getMetaAttribuut().getParentRecord().getVoorkomensleutel());
                    }
                    Assert.fail("Lijst bevat teveel actuele waarden: " + lijst.toString());

                }

            } catch (ExpressieException e) {
                throw new AssertionError("Fout bij evaluatie specialcase", e);
            }
        };
        final Consumer<Persoonslijst> naderePopulatieBeperkingConsumer = (p) -> {
            try {
                for (Expressie naderepopulatiebeperking : PDTExpressieHelper.INSTANCE.parsedDienstbundelExpressies) {
                    Assert.assertTrue(BRPExpressies.evalueerMetSelectieDatum(naderepopulatiebeperking, p, DatumUtil.vandaag()) instanceof BooleanLiteral);
                }
            } catch (ExpressieException e) {
                throw new AssertionError("Fout bij evaluatie nadere populatiebeperking", e);
            }
        };
        final Consumer<Persoonslijst> attenderingscriteriumConsumer = (p) -> {
            for (Expressie attenderingcriterium : PDTExpressieHelper.INSTANCE.parsedDienstExpressiesList) {
                BRPExpressies.evalueerAttenderingsCriterium(attenderingcriterium, null, p);
            }
        };

        final Consumer<Persoonslijst> consumeAll = max1ActueelConsumer.andThen(naderePopulatieBeperkingConsumer).andThen(attenderingscriteriumConsumer);
        consumeAll.accept(persoonslijst);

        final Persoonslijst vorigeHandeling = persoonslijst.beeldVan().vorigeHandeling();
        if (vorigeHandeling != null) {
            LOGGER.debug(vorigeHandeling.toStringVolledig());
            LOGGER.debug("acties laatste handeling {}", persoonslijst.getAdministratieveHandeling().getActies().toString());
            LOGGER.debug("acties vorige handeling {}", vorigeHandeling.getAdministratieveHandeling().getActies().toString());
            consumeAll.accept(vorigeHandeling);

        }
    }
}
