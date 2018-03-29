/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.jenkins;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IT_Usecase.class,
        IT_Conversielevering.class,
        IT_PDT_Attenderingscriterium.class,
        IT_PDT_ExpressieEvaluatie.class,
        IT_PDT_NaderePopulatiebeperking.class,
        IT_ExpressieEvaluatieOpBlob.class
})
public class BrpApiTestSuite {
}
