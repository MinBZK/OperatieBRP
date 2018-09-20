/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.operatie;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * Exception voor als herhaalde operaties blijven falen.
 */
public final class HerhaalException extends Exception {
    private static final long serialVersionUID = 1L;

    private static final String EARLIER_CAUSES = "Earlier causes:";

    private final List<Exception> pogingExcepties;

    /**
     * Creëer een HerhaalExceptie met een lijst van alle excepties van de gefaalde pogingen.
     * 
     * @param pogingExcepties
     *            De excepties van de gefaalde pogingen.
     */
    public HerhaalException(final List<Exception> pogingExcepties) {
        super(MessageFormat.format(
            "Herhaalde operatie gefaald na {0} pogingen. Laatste fout: {1}",
            pogingExcepties.size(),
            pogingExcepties.get(pogingExcepties.size() - 1).getMessage()), pogingExcepties.get(pogingExcepties.size() - 1));
        this.pogingExcepties = pogingExcepties;
    }

    /**
     * De lijst van excepties die tijdens alle pogingen zijn opgetreden.
     * 
     * @return De lijst van excepties die tijdens alle pogingen zijn opgetreden.
     */
    public List<Exception> getPogingExcepties() {
        return Collections.unmodifiableList(pogingExcepties);
    }

    @Override
    public void printStackTrace(final PrintStream s) {
        // This method not fully unit-tested, since output depends on the exact testing approach
        // and would not be reliable with different compilers or test runners.
        super.printStackTrace(s);
        s.println(EARLIER_CAUSES);
        for (int i = pogingExcepties.size() - 2; i >= 0; i--) {
            pogingExcepties.get(i).printStackTrace(s);
        }
    }

    @Override
    public void printStackTrace(final PrintWriter s) {
        // This method not fully unit-tested, since output depends on the exact testing approach
        // and would not be reliable with different compilers or test runners.
        super.printStackTrace(s);
        s.println(EARLIER_CAUSES);
        for (int i = pogingExcepties.size() - 2; i >= 0; i--) {
            pogingExcepties.get(i).printStackTrace(s);
        }
    }
}
