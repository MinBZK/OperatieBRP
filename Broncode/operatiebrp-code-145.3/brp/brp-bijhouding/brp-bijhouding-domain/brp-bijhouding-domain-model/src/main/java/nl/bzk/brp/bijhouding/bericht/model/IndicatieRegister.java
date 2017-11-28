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
 * Deze class bevat een lijst met alle implementaties van IndicatieElement. Deze wordt gebruikt door
 * {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface}.
 */
public final class IndicatieRegister implements XmlElementRegister {

    /**
     * De lijst met implementaties van {@link IndicatieElement}.
     */
    private static final List<Class<?>> INDICATIE_ELEMENTEN =
            Arrays.asList(
                    new Class<?>[]{VolledigeVerstrekkingsbeperkingIndicatieElement.class, BijzondereVerblijfsrechtelijkePositieIndicatieElement.class,
                            StaatloosIndicatieElement.class});

    @Override
    public List<Class<?>> getImplementaties() {
        return Collections.unmodifiableList(INDICATIE_ELEMENTEN);
    }
}
