/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.core.JsonFactory;


/**
 * Deze klasse zorgt voor het (de-)serialiseren van persoon his volledig objecten van en naar json strings.
 */
public final class PersoonHisVolledigStringSerializer extends PersoonHisVolledigSmileSerializer implements
    PersoonHisVolledigSerializer
{
    /**
     * De default constructor voor deze klasse, die ervoor zorgt dat de JsonFactory overschreven wordt in de smile serializer.
     */
    public PersoonHisVolledigStringSerializer() {
        super(new JsonFactory(), new PersoonHisVolledigMappingConfiguratieModule(), new IdPropertyFilterProvider());
    }
}
