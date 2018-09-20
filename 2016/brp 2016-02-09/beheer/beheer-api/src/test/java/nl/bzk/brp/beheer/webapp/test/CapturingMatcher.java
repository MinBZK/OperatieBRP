/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.test;

import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONArray;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class CapturingMatcher<T> extends BaseMatcher<T> {

    private final List<T> values = new ArrayList<>();

    @Override
    public boolean matches(final Object item) {
        if (item instanceof JSONArray) {
            for (final Object value : (JSONArray) item) {
                values.add((T) value);
            }
        } else {
            values.add((T) item);
        }
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("<capturing>");
    }

    public T getValue() {
        return values.isEmpty() ? null : values.get(values.size() - 1);
    }

    public List<T> getValues() {
        return values;
    }
}
