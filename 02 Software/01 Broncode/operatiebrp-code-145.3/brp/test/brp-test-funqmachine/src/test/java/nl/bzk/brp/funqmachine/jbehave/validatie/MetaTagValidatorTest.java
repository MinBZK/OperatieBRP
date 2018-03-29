/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.validatie;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MetaTagValidatorTest {

    @Test
    public void testGeldigeMetaTags() {
        final Map<String, Boolean> metaTagAndExpected = new HashMap<>();
        metaTagAndExpected.put("@auteur", true);
        metaTagAndExpected.put("@Auteur", false);
        metaTagAndExpected.put("@regels", true);
        metaTagAndExpected.put("@sprintnummer", true);
        metaTagAndExpected.put("@epic", true);
        metaTagAndExpected.put("@jiraIssue", true);
        metaTagAndExpected.put("@sleutelwoorden", true);
        metaTagAndExpected.put("@soapEndpoint", true);
        metaTagAndExpected.put("@soapNamespace", true);
        metaTagAndExpected.put("@component", true);
        metaTagAndExpected.put("@usecase", true);
        metaTagAndExpected.put("@overig", false);

        final Validator<MetaTag> validator = new MetaTagValidator();
        for (final Map.Entry<String, Boolean> entry : metaTagAndExpected.entrySet()) {
            final boolean isValide = validator.valideer(new MetaTag(entry.getKey()));
            assertEquals(String.format("Metatag %s verwacht %b", entry.getKey(), entry.getValue()), entry.getValue(), isValide);
        }
    }

    @Test
    public void testStatusWaarde() {
        final Map<String, Boolean> statusWaardeAndExpected = new HashMap<>();
        statusWaardeAndExpected.put("Onderhanden", true);
        statusWaardeAndExpected.put("onderhanden", true);
        statusWaardeAndExpected.put("Bug", true);
        statusWaardeAndExpected.put("bug", true);
        statusWaardeAndExpected.put("Backlog", true);
        statusWaardeAndExpected.put("backlog", true);
        statusWaardeAndExpected.put("Review", true);
        statusWaardeAndExpected.put("review", true);
        statusWaardeAndExpected.put("Klaar", true);
        statusWaardeAndExpected.put("klaar", true);
        statusWaardeAndExpected.put("Uitgeschakeld", true);
        statusWaardeAndExpected.put("uitgeschakeld", true);
        statusWaardeAndExpected.put("Bezig", false);
        statusWaardeAndExpected.put("bezig", false);
        final Validator<MetaTag> validator = new MetaTagValidator();
        for (final Map.Entry<String, Boolean> entry : statusWaardeAndExpected.entrySet()) {
            final boolean isValide = validator.valideer(new MetaTag("@status " + entry.getKey()));
            assertEquals(String.format("Metatag status met waarde %s verwacht %b", entry.getKey(), entry.getValue()), entry.getValue(), isValide);
        }

    }
}
