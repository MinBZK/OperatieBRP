/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.Map;
import java.util.TreeMap;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;


public class DatabaseBuilder {

    private static final int LAAG_OPERATIONEEL_MODEL = 1751;
    private Domein domein;

    public DatabaseBuilder(final Domein domein) {
        this.domein = domein;
    }

    private Map<Integer, Tabel> tabellen = new TreeMap<Integer, Tabel>();

    public Database buildDatabase() {
        Database database = new Database(domein.getNaam());

        for (Schema schema : domein.getSchemas()) {
            DatabaseSchema databaseSchema = database.addSchema(new DatabaseSchema(schema));
            for (ObjectType objectType : schema.getWerkVersie().getObjectTypes(LAAG_OPERATIONEEL_MODEL)) {
                Tabel tabel = databaseSchema.addTabel(objectType);
                tabel.setSequence(new Sequence(objectType));
                tabellen.put(objectType.getId(), tabel);
                for (Attribuut attribuut : objectType.getAttributen()) {
                    tabel.addKolom(attribuut);
                }
                for (BedrijfsRegel bedrijfsRegel : objectType.getBedrijfsRegels()) {
                    tabel.addConstraint(AbstractTabelConstraint.createConstraint(bedrijfsRegel));
                }
            }
        }
        /*
         * Het koppelen van de foreign key tabellen kan pas als we alle tabellen in de 'tabellen' map hebben
         * gestopt.
         */
        for (Tabel tabel : tabellen.values()) {
            for (Kolom kolom : tabel.getKolommen()) {
                if (kolom.isForeignKey()) {
                    ObjectType objectType = kolom.getForeignObjectType();
                    ForeignKeyConstraint constraint = tabel.addForeignKeyConstraint(tabellen.get(objectType.getId()));
                    constraint.addForeignKeyKolom(kolom);
                }
            }
        }

        return database;
    }
}
