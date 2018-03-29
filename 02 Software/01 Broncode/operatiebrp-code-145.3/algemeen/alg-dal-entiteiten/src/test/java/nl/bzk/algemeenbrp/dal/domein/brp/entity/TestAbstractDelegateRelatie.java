/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

/**
 * Class voor het testen van AbstractDelegateRelatie. Deze class wordt gebruikt door de GettenAndSetterTest doordat deze
 * in het entity package zit en dmv reflectie wordt getest. Deze class dus niet verwijderen.
 */
public class TestAbstractDelegateRelatie extends AbstractDelegateRelatie {

    /**
     * Initialiseert TestAbstractDelegateRelatie.
     *
     * @param delegate de delegate waar de getter en setter methodes naar moeten delegeren
     */
    protected TestAbstractDelegateRelatie(final Relatie delegate) {
        super(delegate);
    }
}
