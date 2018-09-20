/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;


public class DatabaseSchema extends SchemaElement {

    private List<Tabel> tabellen = new ArrayList<Tabel>();

    public DatabaseSchema(final Schema schema) {
        super(schema.getNaam());
    }

    public Tabel addTabel(final ObjectType objectType) {
        Tabel tabel = new Tabel(objectType, this);
        tabellen.add(tabel);
        return tabel;
    }

    /**
     * @return the tabellen
     */
    public List<Tabel> getTabellen() {
        return tabellen;
    }
}
