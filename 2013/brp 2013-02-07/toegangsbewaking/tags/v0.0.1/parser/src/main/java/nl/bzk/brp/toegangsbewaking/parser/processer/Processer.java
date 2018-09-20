/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.processer;

import nl.bzk.brp.toegangsbewaking.parser.ParseTree;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;


/**
 * Generieke interface die door specifieke implementaties dient te worden geimplementeerd. Een processor implementatie
 * kan op basis van een parse tree een specifieke filtering uitvoeren of filter genereren.
 */
public interface Processer<T> {

    /**
     * Verwerkt de opgegeven {@link ParseTree} en genereert op basis daarvan een voor de processer specifiek object,
     * wat bijvoorbeeld een where-clause snippet kan zijn, of een filter object.
     *
     * @param parseTree de parse tree die verwerkt dient te worden.
     * @return een specifiek object dat gebruikt wordt als filter.
     * @throws ParserException indien er fouten optreden in de verwerking.
     */
    T process(final ParseTree parseTree) throws ParserException;

}
