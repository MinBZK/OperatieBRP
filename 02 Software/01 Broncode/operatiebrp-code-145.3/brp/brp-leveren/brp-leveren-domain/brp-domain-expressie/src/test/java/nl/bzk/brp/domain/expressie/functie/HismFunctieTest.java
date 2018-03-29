/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

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
public class HismFunctieTest {

    private final Persoonslijst persoon = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_HISTORIE;
    private final Persoonslijst persoon_mutlev = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_MUTLEV_HISTORIE;

    @Test
    public void testRecordsVanMaterieleGroep() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(HISM(Persoon.Adres.Standaard))", persoon), "2");
        final LijstExpressie evalueer = TestUtils.evalueer("HISM(Persoon.Adres.Standaard)", persoon).alsLijst();
        for (Expressie expressie : evalueer) {
            Assert.isTrue(expressie instanceof MetaRecordLiteral);
            MetaRecordLiteral metaRecordLiteral = (MetaRecordLiteral) expressie;
            Assert.isNull(metaRecordLiteral.getMetaRecord().getActieVerval());
            Assert.isNull(metaRecordLiteral.getMetaRecord().getActieVervalTbvLeveringMutaties());
            Assert.isTrue(!persoon.isActueel(metaRecordLiteral.getMetaRecord()));
        }
    }

    @Test
    public void testRecordsVanMaterieleGroepMutlev() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("AANTAL(HISM(Persoon.Adres.Standaard))", persoon_mutlev), "2");
        final LijstExpressie evalueer = TestUtils.evalueer("HISM(Persoon.Adres.Standaard)", persoon_mutlev).alsLijst();
        for (Expressie expressie : evalueer) {
            Assert.isTrue(expressie instanceof MetaRecordLiteral);
            MetaRecordLiteral metaRecordLiteral = (MetaRecordLiteral) expressie;
            Assert.isNull(metaRecordLiteral.getMetaRecord().getActieVerval());
            Assert.isNull(metaRecordLiteral.getMetaRecord().getActieVervalTbvLeveringMutaties());
            Assert.isTrue(!persoon.isActueel(metaRecordLiteral.getMetaRecord()));
        }
    }

    @Test
    public void testAttribuutOpvragen() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISM(Persoon.Adres.Woonplaatsnaam)", persoon), "{\"Purmerend\", \"Rijswijk\"}");
        TestUtils.assertEqual(TestUtils.evalueer("HISM(Persoon.Adres.Woonplaatsnaam)", persoon_mutlev), "{\"Purmerend\", \"Rijswijk\"}");
    }

    @Test
    public void testNietAanwezigAttribuutOpvragen() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISM(Persoon.Adres.Huisletter)", persoon), "{}");
        TestUtils.assertEqual(TestUtils.evalueer("HISM(Persoon.Adres.Huisletter)", persoon_mutlev), "{}");
    }

    @Test
    public void testGeenFormeleHistorie() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISM(Persoon.Geboorte)", persoon), "{}");
    }

    @Test
    public void testGeenFormeleHistorieAttributen() throws ExpressieException {
        TestUtils.assertEqual(TestUtils.evalueer("HISM(Persoon.Geboorte.LandGebiedCode)", persoon), "{}");
    }

}
