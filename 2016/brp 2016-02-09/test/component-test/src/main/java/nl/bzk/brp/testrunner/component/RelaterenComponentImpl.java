/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import nl.bzk.brp.testrunner.omgeving.Link;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;

/**
 * Implementatie voor relateren component tbv tests.
 */
@LogischeNaam(ComponentNamen.RELATEREN)
@Link(value = DatabaseComponentImpl.class)
public final class RelaterenComponentImpl extends AbstractComponent implements Relateren {

    /**
     * Constructor voor het RelaterenComponent.
     *
     * @param omgeving de omgeving waar binnen dit component geregistreerd moet worden.
     */
    public RelaterenComponentImpl(final Omgeving omgeving) {
        super(omgeving);
    }

    @Override
    protected void doStop() {

    }

    @Override
    protected void doStart() {

    }

    @Override
    public void relateren() {
        // TODO implementeren
        System.out.println("Relateren wordt hier aangeroepen");
    }
}
