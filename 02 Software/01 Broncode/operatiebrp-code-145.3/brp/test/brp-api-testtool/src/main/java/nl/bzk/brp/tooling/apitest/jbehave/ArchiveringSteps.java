/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;
import org.springframework.expression.ParseException;

/**
 * Steps voor het controleren dat archivering correct verloopt.
 */
public final class ArchiveringSteps extends Steps {

    /**
     * @throws ParseException fout parse
     */
    @Then("is het verzoek correct gearchiveerd")
    public void verzoekIsCorrectGearchiveerd() throws ParseException {
        StoryController.getOmgeving().getArchiveringControleService().assertLaatsteVerzoekCorrectGearchiveerd();
    }
}
