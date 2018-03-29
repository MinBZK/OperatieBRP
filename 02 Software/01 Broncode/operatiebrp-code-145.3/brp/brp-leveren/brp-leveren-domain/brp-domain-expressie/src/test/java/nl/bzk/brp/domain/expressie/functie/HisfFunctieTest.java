/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaRecordLiteral;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 */
public class HisfFunctieTest {

    private final Persoonslijst persoon = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_HISTORIE;
    private final Persoonslijst persoon_mutlev = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_MUTLEV_HISTORIE;

    @Test
    public void testRecordsVanFormeleGroep() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(HISF(Persoon.Geboorte))", persoon), "2");
        final LijstExpressie evalueer = TestUtils.evalueer("HISF(Persoon.Geboorte)", persoon).alsLijst();
        for (Expressie expressie : evalueer) {
            Assert.isTrue(expressie instanceof MetaRecordLiteral);
            MetaRecordLiteral metaRecordLiteral = (MetaRecordLiteral) expressie;
            Assert.notNull(metaRecordLiteral.getMetaRecord().getActieVerval());
            Assert.isNull(metaRecordLiteral.getMetaRecord().getActieVervalTbvLeveringMutaties());
            Assert.isTrue(!persoon.isActueel(metaRecordLiteral.getMetaRecord()));
        }
    }

    @Test
    public void testRecordsVanFormeleGroepMutlev() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(HISF(Persoon.Geboorte))", persoon_mutlev), "2");
    }

    @Test
    public void testRecordsVanMaterieleGroep() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(HISF(Persoon.Adres.Standaard))", persoon), "4");
        final LijstExpressie evalueer = TestUtils.evalueer("HISF(Persoon.Adres.Standaard)", persoon).alsLijst();
        for (Expressie expressie : evalueer) {
            Assert.isTrue(expressie instanceof MetaRecordLiteral);
            MetaRecordLiteral metaRecordLiteral = (MetaRecordLiteral) expressie;
            Assert.isTrue(metaRecordLiteral.getMetaRecord().getActieVerval() != null
                    || metaRecordLiteral.getMetaRecord().getActieAanpassingGeldigheid() != null);
            Assert.isNull(metaRecordLiteral.getMetaRecord().getActieVervalTbvLeveringMutaties());
            Assert.isTrue(!persoon.isActueel(metaRecordLiteral.getMetaRecord()));
        }
    }


    @Test
    public void testRecordsVanMaterieleGroepMutlev() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(HISF(Persoon.Adres.Standaard))", persoon_mutlev), "4");
        final LijstExpressie evalueer = TestUtils.evalueer("HISF(Persoon.Adres.Standaard)", persoon_mutlev).alsLijst();
        for (Expressie expressie : evalueer) {
            Assert.isTrue(expressie instanceof MetaRecordLiteral);
            MetaRecordLiteral metaRecordLiteral = (MetaRecordLiteral) expressie;
            Assert.isTrue(
                    metaRecordLiteral.getMetaRecord().getActieVervalTbvLeveringMutaties() != null || metaRecordLiteral.getMetaRecord().getActieVerval() != null
                            || metaRecordLiteral.getMetaRecord().getActieAanpassingGeldigheid() != null);
            Assert.isTrue(!persoon.isActueel(metaRecordLiteral.getMetaRecord()));
        }
    }

    @Test
    public void testFormeleHistorieAttributen() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISF(Persoon.Geboorte.LandGebiedCode)", persoon), "{\"222\", \"333\"}");
        TestUtils.assertEqual(TestUtils.evalueer("HISF(Persoon.Geboorte.LandGebiedCode)", persoon_mutlev), "{\"222\", \"333\"}");
    }

    @Test
    public void testMaterieleHistorieAttributen() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISF(Persoon.Adres.Woonplaatsnaam)", persoon), "{\"Purmerend\", \"Purmerend\", \"Rijswijk\", \"Rijswijk\"}");
        TestUtils.assertEqual(TestUtils.evalueer("HISF(Persoon.Adres.Woonplaatsnaam)", persoon_mutlev), "{\"Purmerend\", \"Purmerend\", \"Rijswijk\", "
                + "\"Rijswijk\"}");
    }

    @Test
    public void testNietAanwezigAttribuutOpvragen() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISF(Persoon.Adres.Huisletter)", persoon), "{}");
        TestUtils.assertEqual(TestUtils.evalueer("HISF(Persoon.Adres.Huisletter)", persoon_mutlev), "{}");

        TestUtils.assertEqual(TestUtils.evalueer("MAP(HISF(Persoon.Adres.Standaard), x, x)", persoon), "{1@Persoon.Adres.Standaard, 2@Persoon.Adres.Standaard, "
                + "3@Persoon.Adres.Standaard, 4@Persoon.Adres.Standaard}");
        TestUtils.assertEqual(TestUtils.evalueer("MAP(HISF(Persoon.Adres.Standaard), x, x.Huisletter)", persoon), "{NULL, NULL, NULL, NULL}");

    }

    @Test
    public void testNadereAanduidingVervalAttribuut() throws ExpressieException {
        TestUtils.testEvaluatie("HISF(Persoon.Geboorte.LandGebiedCode)", "{\"14\"}",
                TestBuilders.maakPersoonMetNadereAanduidingVerval(1, NadereAanduidingVerval.O));
        TestUtils.testEvaluatie("HISF(Persoon.Geboorte.LandGebiedCode)", "{\"14\"}",
                TestBuilders.maakPersoonMetNadereAanduidingVerval(1, NadereAanduidingVerval.S));
    }

}
