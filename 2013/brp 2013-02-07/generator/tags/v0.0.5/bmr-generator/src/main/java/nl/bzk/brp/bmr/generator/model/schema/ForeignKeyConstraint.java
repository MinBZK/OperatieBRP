/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;


public class ForeignKeyConstraint extends SchemaElement {

    private Tabel       primaryKeyTabel;
    private Tabel       foreignKeyTabel;
    private List<Kolom> foreignKeyKolommen = new ArrayList<Kolom>();

    public ForeignKeyConstraint(final Tabel primaryKeyTabel, final Tabel foreignKeyTabel) {
        super(null);
        this.primaryKeyTabel = primaryKeyTabel;
        this.foreignKeyTabel = foreignKeyTabel;
    }

    public void addForeignKeyKolom(final Kolom kolom) {
        foreignKeyKolommen.add(kolom);
    }

    /**
     * @return the foreignKeyKolommen
     */
    public String getForeignKeyKolommenAlsString() {
        return Joiner.on(", ").skipNulls()
                .join(Collections2.transform(foreignKeyKolommen, new Function<Kolom, String>() {

                    @Override
                    public String apply(final Kolom kolom) {
                        return kolom.getNaam().trim();
                    }
                }));
    }

    /**
     * @return the foreignKeyTabel
     */
    public Tabel getForeignKeyTabel() {
        return foreignKeyTabel;
    }

    /**
     * @return the primaryKeyTabel
     */
    public Tabel getPrimaryKeyTabel() {
        return primaryKeyTabel;
    }
}
