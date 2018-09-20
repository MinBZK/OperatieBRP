/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;


/**
 * Domein metaelement.
 */
@Entity
@DiscriminatorValue("D")
public class Domein extends Element {

    @Transient
    private Map<String, Schema> schemaMap;

    @OneToMany(mappedBy = "domein")
    @OrderBy("naam")
    private List<Schema>        schemas = new ArrayList<Schema>();

    public Schema getSchema(final String naam) {
        if (schemaMap == null) {
            schemaMap = new TreeMap<String, Schema>();
            for (Schema schema : schemas) {
                schemaMap.put(schema.getNaam(), schema);
            }
        }
        Schema schema = schemaMap.get(naam);
        if (schema != null) {
            return schema;
        }
        return null;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public Iterable<Schema> getNonEmptySchemas(final InSetOfModel inSom) {
        return Iterables.filter(schemas, new Predicate<Schema>() {

            @Override
            public boolean apply(final Schema schema) {
                return !schema.getWerkVersie().getObjectTypes().isEmpty();
            }
        });
    }

}
