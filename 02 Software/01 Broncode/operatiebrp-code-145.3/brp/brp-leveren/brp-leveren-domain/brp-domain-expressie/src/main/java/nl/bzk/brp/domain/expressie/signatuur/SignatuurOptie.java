/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.signatuur;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;

/**
 * Signatuur van een functie met verschillende signaturen.
 */
public final class SignatuurOptie implements Signatuur {

    private final List<Signatuur> signaturen = Lists.newArrayList();

    /**
     * Constructor.
     *
     * @param aSignaturen Alle mogelijke signaturen.
     */
    public SignatuurOptie(final Signatuur... aSignaturen) {
        Collections.addAll(this.signaturen, aSignaturen);
    }

    @Override
    public boolean test(final List<Expressie> argumenten, final Context context) {
        boolean matchGevonden = false;
        for (Signatuur s : signaturen) {
            if (s.test(argumenten, context)) {
                matchGevonden = true;
                break;
            }
        }
        return matchGevonden;
    }
}
