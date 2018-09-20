/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.afnemerindicatie;

import java.util.Set;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;


/**
 * Interface die ervoor zorgt dat AfnemerIndicatie objecten van en naar json geserialiseerd kunnen worden.
 */
public interface AfnemerIndicatieSerializer {

    /**
     * Serialiseert een set van afnemerindicatie his volledig naar een json byte array.
     *
     * @param persoonHisVolledig De set van afnemerindicatie his volledig.
     * @return De json als byte array.
     */
    byte[] serialiseer(final Set<PersoonAfnemerindicatieHisVolledigImpl> persoonHisVolledig);

    /**
     * Deserialiseert een json byte array naar een set van afnemerindicatie his volledig.
     *
     * @param bytes De json als byte array.
     * @return De set van afnemerindicatie his volledig.
     */
    Set<PersoonAfnemerindicatieHisVolledigImpl> deserialiseer(final byte[] bytes);

}
