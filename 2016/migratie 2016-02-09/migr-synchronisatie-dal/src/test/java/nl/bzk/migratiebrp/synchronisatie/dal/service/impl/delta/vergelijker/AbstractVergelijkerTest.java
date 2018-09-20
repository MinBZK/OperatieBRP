/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.TestPersonen;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import nl.bzk.migratiebrp.test.common.reader.ExcelReader;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Abstract class ter ondersteuning van de vergelijker testen.
 */
public abstract class AbstractVergelijkerTest extends AbstractDatabaseTest {

    protected static final String SQL_DATA_BRP_STAMGEGEVENS_KERN_XML = "/sql/data/brpStamgegevens-kern.xml";
    protected static final String SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML = "/sql/data/brpStamgegevens-autaut.xml";
    protected static final String SQL_DATA_BRP_STAMGEGEVENS_CONV_XML = "/sql/data/brpStamgegevens-conv.xml";
    protected static final String SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML = "/sql/data/brpStamgegevens-verconv.xml";
    static final String VELD_NATIONALITEIT = "persoonNationaliteitSet";
    static final String VELD_NATIONALITEIT_HISTORIE = "persoonNationaliteitHistorieSet";
    static final String VELD_VOORNAMEN = "voornamen";
    static final String VELD_VOORNAMEN_NAAMGEBRUIK = "voornamenNaamgebruik";
    static final String VELD_ONDERZOEK = "onderzoek";
    static final String MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN = "Meer verschillen dan verwacht gekregen";
    static final String AANPASSING_HERKOMST_NIET_GEVONDEN = "Verwachte aanpassing herkomst niet gevonden";
    static final String GELIJKE_HERKOMST_NIET_GEVONDEN = "Verwachte gelijke herkomst niet gevonden";
    static final String VERWIJDERDE_HERKOMST_NIET_GEVONDEN = "Verwachte verwijderde herkomst niet gevonden";
    static final String AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST = "Aantal aanpassingen herkomst niet juist";

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Inject
    private PreconditiesService preconditieService;
    @Inject
    private Lo3SyntaxControle syntaxControle;
    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    private ExcelReaderDecorator reader;

    @Before
    public void setUp() throws Exception {
        Logging.initContext();
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final Resource testdataFolder = resolver.getResource(getTestDataFolderName());
        reader = new ExcelReaderDecorator(testdataFolder);
    }

    /**
     * Geef de waarde van test data folder name.
     *
     * @return test data folder name
     */
    protected abstract String getTestDataFolderName();

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    List<BrpPersoonslijst> converteerNaarBrpPersoon(final List<Lo3Lg01BerichtWaarde> excelWaarden) throws BerichtSyntaxException,
        OngeldigePersoonslijstException
    {
        final List<BrpPersoonslijst> brpPersoonlijsten = new ArrayList<>();
        for (final Lo3Lg01BerichtWaarde berichtWaarde : excelWaarden) {
            final List<Lo3CategorieWaarde> syntax = syntaxControle.controleer(berichtWaarde.getLo3CategorieWaardeList());
            final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
            final Lo3Persoonslijst lo3 = parser.parse(syntax);
            final Lo3Persoonslijst checkedPl = preconditieService.verwerk(lo3);

            brpPersoonlijsten.add(converteerLo3NaarBrpService.converteerLo3Persoonslijst(checkedPl));
        }
        return brpPersoonlijsten;
    }

    private Pair<Persoon, Set<Onderzoek>> mapNaarBrpPersoon(final BrpPersoonslijst brpPl) {
        final Persoon kluizenaar = new Persoon(SoortPersoon.INGESCHREVENE);
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(kluizenaar);
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("Referentie", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), "<geen bericht>", true);
        final SyncParameters syncParams = new SyncParameters();
        syncParams.setInitieleVulling(true);
        new PersoonMapper(dynamischeStamtabelRepository, syncParams, onderzoekMapper).mapVanMigratie(brpPl, kluizenaar, lo3Bericht);
        return Pair.of(kluizenaar, onderzoekMapper.getOnderzoekSet());
    }

    protected TestPersonen getTestPersonen(final String name) throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException,
        ExcelAdapterException, Lo3SyntaxException
    {
        final List<Lo3Lg01BerichtWaarde> excelWaarden = reader.getInputLo3(name);
        final List<BrpPersoonslijst> brpPersoonslijsten = converteerNaarBrpPersoon(excelWaarden);

        final Pair<Persoon, Set<Onderzoek>> dbPersoon = mapNaarBrpPersoon(brpPersoonslijsten.get(0));
        final Pair<Persoon, Set<Onderzoek>> kluizenaar = mapNaarBrpPersoon(brpPersoonslijsten.get(1));

        return new TestPersonen(dbPersoon.getLeft(), dbPersoon.getRight(), kluizenaar.getLeft(), kluizenaar.getRight());
    }

    class ExcelReaderDecorator extends PathMatchingResourcePatternResolver {
        private final ExcelReader excelReader;
        private final Resource dataFolder;

        public ExcelReaderDecorator(final Resource dataFolder) {
            excelReader = new ExcelReader();
            this.dataFolder = dataFolder;
        }

        public List<Lo3Lg01BerichtWaarde> getInputLo3(final String filename) throws IOException, ExcelAdapterException, Lo3SyntaxException {
            final Set<Resource> files = doFindMatchingFileSystemResources(dataFolder.getFile(), filename + ".xls");
            return excelReader.readFileAsLo3CategorieWaarde(files.iterator().next().getFile());
        }
    }
}
