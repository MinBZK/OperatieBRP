/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Abstracte klasse voor comparators die op id moeten sorteren.
 */
public abstract class AbstractIdComparator {

    /**
     * Vergelijkt 2 modelIdentificeerbaar object met elkaar. De vergelijking wordt gedaan op id.
     *
     * @param modelIdentificeerbaar1 De eerste modelIdentificeerbaar.
     * @param modelIdentificeerbaar2 De tweede modelIdentificeerbaar.
     * @return retourneert een negatief getal als eerste waarde lager is dan tweede, 0 als beide waardes gelijk of positief getal indien tweede waarde
     *         lager dan eerste waarde.
     */
    protected int vergelijkModelIdentificeerbaar(final ModelIdentificeerbaar modelIdentificeerbaar1, final ModelIdentificeerbaar modelIdentificeerbaar2) {
        if (modelIdentificeerbaar1 == null || modelIdentificeerbaar2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }

        final Number id1 = modelIdentificeerbaar1.getID();
        final Number id2 = modelIdentificeerbaar2.getID();
        return vergelijkIds(id1, id2);
    }

    /**
     * Vergelijkt 2 id's met elkaar.
     *
     * @param id1 Het eerste id.
     * @param id2 Het tweede id.
     * @return De vergelijking van de id's.
     */
    protected int vergelijkIds(final Number id1, final Number id2) {
        final int resultaat;
        if (id1 == null && id2 == null) {
            resultaat = 0;
        } else if (id1 != null && id2 == null) {
            resultaat = -1;
        } else if (id1 == null) {
            resultaat = 1;
        } else {
            resultaat = Long.compare(id1.longValue(), id2.longValue());
        }
        return resultaat;
    }

}
