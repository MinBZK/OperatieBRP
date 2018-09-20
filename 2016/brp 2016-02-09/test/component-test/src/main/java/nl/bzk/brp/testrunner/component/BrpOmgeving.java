/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import nl.bzk.brp.testrunner.component.services.gbasync.GbaSyncHelper;
import nl.bzk.brp.testrunner.component.util.CacheHelper;
import nl.bzk.brp.testrunner.component.util.HandelingHelper;
import nl.bzk.brp.testrunner.component.util.LeveringautorisatieHelper;
import nl.bzk.brp.testrunner.component.util.PersoonDslHelper;
import nl.bzk.brp.testrunner.omgeving.Omgeving;

/**
 */
public interface BrpOmgeving extends Omgeving {
    void wachtTotFunctioneelBeschikbaar() throws InterruptedException;

    BrpDatabase database();

    CacheHelper cache();

    HandelingHelper handeling();

    LeveringautorisatieHelper leveringautorisaties();

    PersoonDslHelper persoonDsl();

    Synchronisatie synchronisatie();

    GbaSyncHelper gba();

    RouteringCentrale routering();

    OnderhoudAfnemerindicatie afnemerindicaties();

    /**
     * @return geeft het relateren component terug.
     */
    Relateren relateren();
}
