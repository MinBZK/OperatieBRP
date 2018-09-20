/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
class ObjectGeslachtnaamComponentMapper extends AbstractObjectMapper<PersoonGeslachtsnaamcomponentHisVolledigImpl> {

    /**
     *
     * @param mappedObject
     */
    protected ObjectGeslachtnaamComponentMapper(final PersoonGeslachtsnaamcomponentHisVolledigImpl mappedObject) {
        super(mappedObject);
    }

    @Override
    public ElementObjectType geefObjectType() {
        return ElementObjectType.PersoonGeslachtsnaamcomponent;
    }

    @Override
    public int geefObjectSleutel() {
        return getMappedObject().getID();
    }

    @Override
    public List<AbstractGroepMapper> geefGroepMappers() {
        return Arrays.<AbstractGroepMapper>asList(
            new GroepGeslachtsnaamcomponentStandaardMapper()
        );
    }

    @Override
    public List<AbstractObjectMapper> geefObjectMappers() {
        return Collections.emptyList();
    }


}
