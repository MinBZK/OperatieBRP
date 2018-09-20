/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

/**
 * Abstracte basis klasse voor alle binding tests.
 */
abstract class AbstractBindingTest<T> {

    protected static final String NAMESPACE_BRP = "http://www.bprbzk.nl/BRP/0001";

    /**
     * Retourneert de class van het object dat gemarshalled en geunmarshalled moet worden.
     *
     * @return de class van het object dat gemarshalled en geunmarshalled moet worden.
     */
    protected abstract Class<T> getBindingClass();

}
