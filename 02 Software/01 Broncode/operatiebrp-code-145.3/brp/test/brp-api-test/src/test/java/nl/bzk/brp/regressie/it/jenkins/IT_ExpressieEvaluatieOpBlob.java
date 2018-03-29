/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.jenkins;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaAttribuutLiteral;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.tooling.apitest.service.basis.impl.BasisConfiguratie;
import nl.bzk.brp.tooling.apitest.service.dataaccess.PersoonDataStubService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

@ContextConfiguration(classes = BasisConfiguratie.class)
public class IT_ExpressieEvaluatieOpBlob {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private PersoonDataStubService persoonDataStubService;
    @Autowired
    private PersoonslijstService    persoonslijstService;

    private Persoonslijst persoonslijst;
    private Set<MetaAttribuut> alleAttributen;
    private Set<MetaAttribuut> actueleAttributen;

    @Before
    public void voorTest() throws Exception {
        final TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
        persoonDataStubService.laadPersonen(Lists.newArrayList("specials:specials/Anne_Bakker_GBA_Bijhouding_xls"));
        persoonslijst = persoonslijstService.getById(1);
//        LOGGER.debug(persoonslijst.toStringVolledig());

        //verzamel alle attributen en alle actuele attributen
        alleAttributen = Sets.newHashSet();
        actueleAttributen = Sets.newHashSet();
        for (AttribuutElement attribuutElement : persoonslijst.getModelIndex().geefAttribuutElementen()) {
            for (MetaAttribuut metaAttribuut : persoonslijst.getModelIndex().geefAttributen(attribuutElement)) {
                alleAttributen.add(metaAttribuut);
                if (persoonslijst.isActueel(metaAttribuut.getParentRecord())) {
                    actueleAttributen.add(metaAttribuut);
                }
            }
        }
    }

    /**
     * Test dat alle actuele attributen op de persoonslijst aangewezen kunnen worden middels
     * de expressietaal. Test ook dat historische attributen niet aangewezen kunnen worden.
     */
    @Test
    public void testAanwijzenActueelAttribuutObvElementNaam() throws Exception {
        final Set<MetaAttribuut> actueleAttributenKopie = Sets.newHashSet(actueleAttributen);
        for (AttribuutElement attribuutElement : persoonslijst.getModelIndex().geefAttribuutElementen()) {
            final String expressieString = String.format("%s WAARBIJ %s = ONWAAR",
                    attribuutElement.getElement().getElementWaarde().getNaam(),
                    ExpressieTaalConstanten.ATTRIBUUTALSWAARDE);
            LOGGER.debug("expressie = {}", expressieString);
            final Expressie parse = ExpressieParser.parse(expressieString);
            final LijstExpressie resultaat = (LijstExpressie) BRPExpressies.evalueer(parse, persoonslijst);
            LOGGER.debug("resultaat = {}", resultaat);

            for (Expressie expressie : resultaat) {
                MetaAttribuutLiteral literal = (MetaAttribuutLiteral) expressie;
                Assert.assertTrue(actueleAttributen.contains(literal.getMetaAttribuut()));
                actueleAttributenKopie.remove(literal.getMetaAttribuut());
            }

        }
        Assert.assertTrue("Alle actuele attributen zijn aangewezen", actueleAttributenKopie.isEmpty());
    }

    @Test
    public void testAanwijzenActueelAttribuutObvGroepVariabele() throws Exception {
        final Set<MetaAttribuut> actueleAttributenKopie = Sets.newHashSet(actueleAttributen);
        for (AttribuutElement attribuutElement : persoonslijst.getModelIndex().geefAttribuutElementen()) {
            final String expressieString = String.format("MAP(%s, g, g.%s) WAARBIJ %s = ONWAAR",
                    attribuutElement.getGroep().getElement().getNaam(),
                    attribuutElement.getElement().getElementWaarde().getIdentiteitExpressie(),
                    ExpressieTaalConstanten.ATTRIBUUTALSWAARDE);
            LOGGER.debug("expressie = {}", expressieString);
            final Expressie parse = ExpressieParser.parse(expressieString);
            final LijstExpressie resultaat = (LijstExpressie) BRPExpressies.evalueer(parse, persoonslijst);
            LOGGER.debug("resultaat = {}", resultaat);

            for (Expressie expressie : resultaat) {
                MetaAttribuutLiteral literal = (MetaAttribuutLiteral) expressie;
                Assert.assertTrue(actueleAttributen.contains(literal.getMetaAttribuut()));
                actueleAttributenKopie.remove(literal.getMetaAttribuut());
            }

        }
        Assert.assertTrue("Alle actuele attributen zijn aangewezen", actueleAttributenKopie.isEmpty());
    }

    @Test
    public void testGewijzigd() throws ExpressieException {

        final Collection<Actie> acties = persoonslijst.getAdministratieveHandeling().getActies();
        final Collection<MetaRecord> recordsGewijzigd = Sets.newHashSet();
        persoonslijst.getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (acties.contains(record.getActieInhoud())
                        || acties.contains(record.getActieAanpassingGeldigheid())) {
                    recordsGewijzigd.add(record);
                }
            }
        });

        Assert.assertFalse(recordsGewijzigd.isEmpty());


        final Persoonslijst persoonslijstOud = this.persoonslijst.beeldVan().vorigeHandeling();
        Assert.assertTrue(
                "Persoon object is gewijzigd",
                BRPExpressies.evalueerAttenderingsCriterium(ExpressieParser.parse("GEWIJZIGD(oud, nieuw)"),
                persoonslijst, persoonslijstOud).alsBoolean());


        for (MetaRecord metaRecord : recordsGewijzigd) {

            {
                //check objecten gewijzigd
                final String objectNaam = metaRecord.getParentGroep().getParentObject().getObjectElement().getNaam();
                LOGGER.debug("Check object {} gewijzigd", objectNaam);
                Assert.assertTrue(
                        String.format("Object %s is gewijzigd", objectNaam),
                        BRPExpressies.evalueerAttenderingsCriterium(ExpressieParser.parse(String.format("GEWIJZIGD(oud, nieuw, [%s])",
                                objectNaam)),
                                persoonslijst, persoonslijstOud).alsBoolean());
            }

            {

                for (AttribuutElement attribuutElement : metaRecord.getAttributen().keySet()) {

                    if (attribuutElement.isVerantwoording()) {
                        continue;
                    }

                    final LijstExpressie resultaatOud =
                            (LijstExpressie) BRPExpressies.evalueer(ExpressieParser.parse(attribuutElement.getNaam()), persoonslijstOud);
                    final LijstExpressie resultaatNieuw =
                            (LijstExpressie) BRPExpressies.evalueer(ExpressieParser.parse(attribuutElement.getNaam()), persoonslijst);

                    final List<String> resultaatOudSorted = resultaatOud.getElementen().stream().map(Expressie::alsString).sorted().collect(Collectors.toList());
                    final List<String> resultaatNieuwSorted = resultaatNieuw.getElementen().stream().map(Expressie::alsString).sorted().collect(Collectors.toList());

                    LOGGER.debug(resultaatOudSorted.toString());
                    LOGGER.debug(resultaatNieuwSorted.toString());

                    //check attributen gewijzigd
                    LOGGER.debug("Check attribuut {} gewijzigd", attribuutElement.getNaam());
                    Assert.assertTrue(
                            String.format("Attribuut %s is gewijzigd", attribuutElement.getNaam()),
                            BRPExpressies.evalueerAttenderingsCriterium(ExpressieParser.parse(String.format("GEWIJZIGD(oud, nieuw, [%s])",
                                    attribuutElement.getNaam())),
                                    persoonslijst, persoonslijstOud).alsBoolean() != resultaatOudSorted.equals(resultaatNieuwSorted));
                }

            }
        }
    }
}
