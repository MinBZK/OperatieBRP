/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;


/**
 * Interface die er voor zorgt dat PersoonHisVolledig objecten van en naar json geserialiseerd kunnen worden.
 */
public interface PersoonHisVolledigSerializer {

    /**
     * Serialiseert een persoon his volledig naar een json byte array.
     *
     * @param persoonHisVolledig De persoon his volledig.
     * @return De json als byte array.
     */
    byte[] serialiseer(final PersoonHisVolledigImpl persoonHisVolledig);

    /**
     * Deserialiseert een json byte array naar een persoon his volledig.
     *
     * @param bytes De json als byte array.
     * @return De persoon his volledig.
     */
    PersoonHisVolledigImpl deserialiseer(final byte[] bytes);

}
