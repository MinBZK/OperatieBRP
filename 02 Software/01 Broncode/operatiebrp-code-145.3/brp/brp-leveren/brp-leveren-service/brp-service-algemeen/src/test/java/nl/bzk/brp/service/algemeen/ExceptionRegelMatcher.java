/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import com.google.common.collect.Iterables;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher om regels binnen meldingen te checken binnen tests.
 */
public final class ExceptionRegelMatcher extends TypeSafeMatcher<StapMeldingException> {
    private Regel regel;

    public ExceptionRegelMatcher(final Regel regel) {
        this.regel = regel;
    }

    @Override
    protected boolean matchesSafely(final StapMeldingException exceptie) {
        return exceptie.getMeldingen().contains(new Melding(regel));
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("expects regel ").appendValue(regel);
    }

    @Override
    protected void describeMismatchSafely(final StapMeldingException exceptie, final Description omschrijving) {
        omschrijving.appendText("was ").appendValue(Iterables.getOnlyElement(exceptie.getMeldingen()).getRegel());
    }
}
