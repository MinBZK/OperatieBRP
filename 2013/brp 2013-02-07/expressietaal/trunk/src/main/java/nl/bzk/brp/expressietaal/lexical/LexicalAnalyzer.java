/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical;

import nl.bzk.brp.expressietaal.lexical.tokens.TokenStack;


/**
 * Interface voor lexical analyzers. De taak van een lexical analyzer is om een string om te zetten in een lijst van
 * tokens.
 */
public interface LexicalAnalyzer {

    /**
     * Zet een string om in een lijst van tokens.
     *
     * @param source De om te zetten string.
     * @return Stack (lijst) van tokens; null, indien een fout is opgetreden.
     */
    TokenStack tokenize(String source);
}
