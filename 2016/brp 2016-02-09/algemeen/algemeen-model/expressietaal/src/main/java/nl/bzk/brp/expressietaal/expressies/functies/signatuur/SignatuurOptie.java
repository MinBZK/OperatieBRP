/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies.signatuur;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;

/**
 * Signatuur van een functie met verschillende signaturen.
 */
public class SignatuurOptie implements Signatuur {

    private List<Signatuur> signaturen;

    /**
     * Constructor.
     *
     * @param aSignaturen Alle mogelijke signaturen.
     */
    public SignatuurOptie(final List<Signatuur> aSignaturen) {
        if (aSignaturen != null) {
            this.signaturen = aSignaturen;
        } else {
            this.signaturen = new ArrayList<Signatuur>();
        }
    }

    /**
     * Constructor.
     *
     * @param aSignaturen Alle mogelijke signaturen.
     */
    public SignatuurOptie(final Signatuur... aSignaturen) {
        this.signaturen = new ArrayList<Signatuur>();
        if (aSignaturen != null) {
            for (Signatuur s : aSignaturen) {
                this.signaturen.add(s);
            }
        }
    }

    @Override
    public final boolean matchArgumenten(final List<Expressie> argumenten, final Context context) {
        boolean matchGevonden = false;
        for (Signatuur s : signaturen) {
            if (s.matchArgumenten(argumenten, context)) {
                matchGevonden = true;
                break;
            }
        }
        return matchGevonden;
    }
}
