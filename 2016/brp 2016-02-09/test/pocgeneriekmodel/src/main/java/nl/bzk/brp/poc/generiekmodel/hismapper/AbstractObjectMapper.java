/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
abstract class AbstractObjectMapper<T> {

    private final T mappedObject;

    /**
     *
     * @param mappedObject
     */
    protected AbstractObjectMapper(final T mappedObject) {
        this.mappedObject = mappedObject;
    }

    public T getMappedObject() {
        return mappedObject;
    }

    /**
     *
     * @return
     */
    public final BrpObject mapObject() {
        final BrpObject brpObject = new BrpObject();
        brpObject.setElement(geefObjectType());
        brpObject.setObjectsleutel(geefObjectSleutel());


        if (geefGroepMappers() != null) {
            for (final AbstractGroepMapper groepMapper : geefGroepMappers()) {
                final BrpGroep groep = groepMapper.mapGroep(this, brpObject);
                if (groep != null) {
                    groep.setParent(brpObject);
                    brpObject.getGroepen().add(groep);
                }
            }
        }

        if (geefObjectMappers() != null) {
            for (final AbstractObjectMapper objectMapper : geefObjectMappers()) {
                final BrpObject kindObject = objectMapper.mapObject();
                if (kindObject != null) {
                    kindObject.setParent(brpObject);
                    final ElementObjectType kindObjectType = objectMapper.geefObjectType();
                    if (!brpObject.getObjecten().containsKey(kindObjectType)) {
                        brpObject.getObjecten().put(kindObjectType, new HashSet<BrpObject>());
                    }
                    brpObject.getObjecten().get(kindObjectType).add(kindObject);
                }
            }
        }
        return brpObject;
    }

    /**
     *
     * @return
     */
    public abstract ElementObjectType geefObjectType();

    /**
     *
     * @return
     */
    public abstract int geefObjectSleutel();

    /**
     *
     * @return
     */
    public abstract List<AbstractGroepMapper> geefGroepMappers();

    /**
     *
     * @return
     */
    public abstract List<AbstractObjectMapper> geefObjectMappers();

}
