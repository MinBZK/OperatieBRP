/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.ArrayList;
import java.util.List;


public class Database extends SchemaElement {

    private List<DatabaseSchema> schemas = new ArrayList<DatabaseSchema>();

    public Database(final String naam) {
        super(naam);
    }

    public DatabaseSchema addSchema(final DatabaseSchema schema) {
        schemas.add(schema);
        return schema;
    }

    /**
     * @return the schemas
     */
    public List<DatabaseSchema> getSchemas() {
        return schemas;
    }
}
