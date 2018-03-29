/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import java.util.function.BiFunction;
import nl.bzk.brp.domain.expressie.Expressie;

/**
 * Interface voor het vergelijken van {@link Expressie}s.
 * Middels deze interface wordt een onkoppeling gemaakt tussen hoe iets
 * inhoudelijk vergeleken wordt en hetgeen dat vergeleken wordt of de vergelijking uitvoert.
 * Met andere woorden, eenliteral heeft geen kennis van hoe het vergeleken wordt
 * en de operatoren weten wel dat er vergeleken wordt, maar inhoudelijk niet op welke wijze.
 *
 * @param <T> het type Expressie dat vergeleken wordt
 * @param <U> het type Expressie dat vergeleken wordt
 */
@FunctionalInterface
public interface Vergelijker<T extends Expressie, U extends Expressie> extends BiFunction<T, U, Expressie> {

}
