/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Superinterface voor alle constante expressies. De expressie na
 * evaluatie is gelijk aan this.
 */
public interface Literal extends Expressie {

    @Override
    default boolean isConstanteWaarde() {
        // Per definitie true.
        return true;
    }

    @Override
    default Expressie evalueer(final Context context) {
        // De evaluatie van een constante is - per definitie - gelijk aan zichzelf.
        return this;
    }

    @Override
    default Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_LITERAL;
    }

}
