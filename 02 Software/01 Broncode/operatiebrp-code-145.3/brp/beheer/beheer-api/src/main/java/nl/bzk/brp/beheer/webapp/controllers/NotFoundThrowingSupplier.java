/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

/**
 * Represents a supplier of results that can throw a NotFoundException.
 *
 * @param <T>
 *            the type of results supplied by this supplier
 */
@FunctionalInterface
public interface NotFoundThrowingSupplier<T> {
    /**
     * Gets a result.
     * 
     * @return result
     * @throws ErrorHandler.NotFoundException
     *             NotFoundException
     */
    T get() throws ErrorHandler.NotFoundException;
}
