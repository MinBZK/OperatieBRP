/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementRegister;

/**
 * Deze class bevat een lijst met alle implementaties van RelatieElement. Deze wordt gebruikt door
 * {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface}.
 */
public final class RelatieRegister implements XmlElementRegister {

    /**
     * De lijst met implementaties van {@link RelatieElement}.
     */
    private static final List<Class<?>> RELATIE_ELEMENTEN =
            Arrays.asList(
                    new Class<?>[]{HuwelijkElement.class,
                            GeregistreerdPartnerschapElement.class});

    @Override
    public List<Class<?>> getImplementaties() {
        return Collections.unmodifiableList(RELATIE_ELEMENTEN);
    }
}
