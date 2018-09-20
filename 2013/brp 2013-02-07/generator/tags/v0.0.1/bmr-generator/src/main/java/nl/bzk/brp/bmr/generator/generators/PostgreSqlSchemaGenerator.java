/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Joiner;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.model.schema.AbstractTabelConstraint;
import nl.bzk.brp.bmr.generator.model.schema.DataRegel;
import nl.bzk.brp.bmr.generator.model.schema.Database;
import nl.bzk.brp.bmr.generator.model.schema.DatabaseBuilder;
import nl.bzk.brp.bmr.generator.model.schema.DatabaseSchema;
import nl.bzk.brp.bmr.generator.model.schema.ForeignKeyConstraint;
import nl.bzk.brp.bmr.generator.model.schema.Kolom;
import nl.bzk.brp.bmr.generator.model.schema.Tabel;
import nl.bzk.brp.bmr.generator.model.schema.TabelFeature;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.ModelElement;

import org.springframework.stereotype.Component;


/**
 * {@link Generator} implementatie de een PostgreSQL-specifiek database schema genereert.
 */
@Component
public class PostgreSqlSchemaGenerator extends AbstractGenerator implements Generator {

    private ArtifactBuilder r;

    @Override
    public void generate(final ModelElement element, final FileSystemAccess file) {
        if (!(element instanceof Domein)) {
            throw new IllegalArgumentException("Kan alleen schema's genereren uit een Domein element.");
        }
        Domein domein = (Domein) element;
        Database database = new DatabaseBuilder(domein).buildDatabase();
        file.generateFile(database.getNaam().toLowerCase() + ".sql", genereerDatabase(database));
        file.generateFile(database.getNaam().toLowerCase() + "-stamgegevensStatisch.sql",
                genereerStamgegevens(database));
    }

    /**
     * Genereer de database schema.
     *
     * @param database de Database
     * @return CharSequence met de statements om tabellen te creeëren.
     */
    private CharSequence genereerDatabase(final Database database) {
        r = new ArtifactBuilder();
        databaseHeader();
        r.regel();
        r.regel();
        r.regel("-- Schemas ---------------------------------------------------------------------");
        for (DatabaseSchema schema : database.getSchemas()) {
            genereerCreeerSchema(schema);
        }
        r.regel();
        r.regel();
        r.regel("-- Actual tabellen--------------------------------------------------------------");
        for (DatabaseSchema schema : database.getSchemas()) {
            for (Tabel tabel : schema.getTabellen()) {
                genereerTabel(tabel);
            }
        }
        genereerPersoonsLockTabel();
        r.regel();
        r.regel("-- Foreign keys ----------------------------------------------------------------");
        for (DatabaseSchema schema : database.getSchemas()) {
            for (Tabel tabel : schema.getTabellen()) {
                for (ForeignKeyConstraint constraint : tabel.getForeignKeyConstraints()) {
                    genereerForeignKeyConstraint(constraint);
                }
            }
        }
        return r;
    }

    /**
     * Genereer de stamgegevens inhoud.
     *
     * @param database de Database
     * @return CharSequence met insert statements
     */
    private CharSequence genereerStamgegevens(final Database database) {
        r = new ArtifactBuilder();
        r.regel();
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- Begin van insertcode---------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- Gegenereerd door BRP Meta Register.");
        r.regel("-- Gegenereerd op: ", new Date().toString());
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        for (DatabaseSchema schema : database.getSchemas()) {
            for (Tabel tabel : schema.getTabellen()) {
                if (tabel.heeftTupels()) {
                    genereerData(tabel);
                }
            }
        }

        // Genereer Object data
        for (DatabaseSchema schema : database.getSchemas()) {
            for (Tabel tabel : schema.getTabellen()) {
                genereerDbObjectData(tabel);
            }
        }

        return r;
    }

    /**
     * Deze methode genereerd insert statements voor een tabel.
     *
     * @param tabel Tabel
     */
    private void genereerData(final Tabel tabel) {
        String schemaNaam = tabel.getSchema().getNaam();
        for (DataRegel dataRegel : tabel.getDataRegels()) {
            StringBuilder kolommen = new StringBuilder();
            for (Kolom kolom : dataRegel.getWaarden().keySet()) {
                if (dataRegel.getWaarden().get(kolom) != null) {
                    kolommen.append(kolom.getNaam());
                    kolommen.append(", ");
                }
            }
            // Verwijder laatste komma
            kolommen.delete(kolommen.length() - 2, kolommen.length());

            StringBuilder waarden = new StringBuilder();
            for (Kolom kolom : dataRegel.getWaarden().keySet()) {
                boolean isVarCharType = kolom.getDataType().toString().startsWith("Varchar");
                if (dataRegel.getWaarden().get(kolom) != null) {
                    if (isVarCharType) {
                        waarden.append("'");
                    }
                    waarden.append(dataRegel.getWaarden().get(kolom));
                    if (isVarCharType) {
                        waarden.append("'");
                    }

                    waarden.append(", ");
                }
            }
            // Verwijder laatste comma
            waarden.delete(waarden.length() - 2, waarden.length());

            r.regel("INSERT INTO ", schemaNaam, ".", tabel.getNaam(), " (", kolommen, ") VALUES (", waarden, ");");
        }
        r.regel();
    }

    /**
     * Genereer data van de tabel.
     *
     * @param tabel de Tabel
     */
    private void genereerDbObjectData(final Tabel tabel) {
        if ("Kern".equals(tabel.getSchema().getNaam())) {
            r.regel("INSERT INTO Kern.DbObject (ID, Naam, DatAanvGel, Srt, JavaIdentifier) VALUES(", tabel.getSyncID(),
                    ", $$", tabel.getNaam(), "$$, 20120101, 1, $$", tabel.getJavaIdentifier(), "$$ ) ; ");

            for (Kolom kolom : tabel.getKolommen()) {
                r.regel("INSERT INTO Kern.DbObject (ID, Naam, DatAanvGel, Srt, Ouder, JavaIdentifier ) VALUES(",
                        kolom.getSyncID(), ", $$", kolom.getNaam(), "$$, 20120101, 2, ", tabel.getSyncID(), ", $$",
                        kolom.getJavaIdentifier(), "$$); ");
            }
        }
    }

    /**
     * Genereer PersoonsLock tabel.
     */
    private void genereerPersoonsLockTabel() {
        r.regel();
        r.regel("-- Deze tabel komt NIET uit het BRP metaregister.");
        r.regel("CREATE TABLE Kern.PersoonsLock (BSN Varchar(9) PRIMARY KEY);");
    }

    /**
     * Genereer Foreign key constraint.
     *
     * @param constraint ForeignKeyConstraint
     */
    private void genereerForeignKeyConstraint(final ForeignKeyConstraint constraint) {
        r.regel("ALTER TABLE ", constraint.getPrimaryKeyTabel().getQualifiedNaam(), " ADD FOREIGN KEY (", constraint
                .getForeignKeyKolommenAlsString(), ") REFERENCES ", constraint.getForeignKeyTabel().getQualifiedNaam(),
                ";");
    }

    /**
     * Genereer tabel.
     *
     * @param tabel de Tabel
     */
    private void genereerTabel(final Tabel tabel) {
        String schemaNaam = tabel.getSchema().getNaam();
        r.regel();
        if (tabel.heeftKunstmatigeSleutel()) {
            r.regel("CREATE SEQUENCE ", tabel.getSequence().getNaam(), ";");
        }
        r.regel("CREATE TABLE ", schemaNaam, ".", tabel.getNaam(), " (");
        r.incr();
        r.regel(Joiner.on("," + r.getNieuweRegel()).join(genereerFeatures(tabel)));
        r.decr();
        r.regel(");");
        if (tabel.heeftKunstmatigeSleutel()) {
            r.regel("ALTER SEQUENCE ", tabel.getSequence().getNaam(), " OWNED BY ", schemaNaam, ".", tabel.getNaam(),
                    ".", tabel.getIdentifierKolom().getNaam(), ";");
        }
    }

    /**
     * Genereer features.
     * @param tabel Tabel
     * @return lijst met features in CharSequence
     */
    private Iterable<CharSequence> genereerFeatures(final Tabel tabel) {
        List<CharSequence> resultaat = new ArrayList<CharSequence>();
        for (TabelFeature feature : tabel.getFeatures()) {
            if (feature instanceof Kolom) {
                resultaat.add(genereerKolom((Kolom) feature));
            } else if (feature instanceof AbstractTabelConstraint) {
                resultaat.add(genereerTabelConstraint((AbstractTabelConstraint) feature));
            }
        }
        return resultaat;
    }

    /**
     * Genereer tabel constraint.
     * @param feature AbstractTabelConstraint
     * @return CharSequence met de constraints
     */
    private CharSequence genereerTabelConstraint(final AbstractTabelConstraint feature) {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("CONSTRAINT ");
        resultaat.append(feature.getNaam());
        resultaat.append(" ").append(feature.getKeywords()).append(" ");
        resultaat.append("(").append(feature.getKolommenAlsString()).append(")");
        return resultaat;
    }

    /**
     * Genereer kolom.
     * @param kolom Kolom
     * @return CharSequence met de kolom
     */
    private CharSequence genereerKolom(final Kolom kolom) {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(kolom.getNaam());
        resultaat.append(" ");
        resultaat.append(kolom.getDataType());
        resultaat.append(kolom.getNullability());
        if (kolom.isIdentifierKolom() && kolom.getTabel().heeftKunstmatigeSleutel()) {
            resultaat.append("DEFAULT nextval('").append(kolom.getTabel().getSequence().getNaam()).append("') ");
        }
        resultaat.append(kolom.getAttribuutTypeAlsCommentaar());
        return resultaat;
    }

    /**
     * Genereer de header voor het domein.
     *
     */
    private void databaseHeader() {
        r.regel("--------------------------------------------------------------------------------");
        r.regel("-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)");
        r.regel("--------------------------------------------------------------------------------");
        r.regel("--");
        r.regel("-- Gegenereerd door BRP Meta Register.");
        r.regel("-- Gegenereerd op: ", new Date().toString());
        r.regel("--");
        r.regel("--------------------------------------------------------------------------------");
    }

    /**
     * Genereer DDL voor het gegeven schema.
     *
     * @param schema het schema waarvoor DDL wordt gegenereerd.
     */
    private void genereerCreeerSchema(final DatabaseSchema schema) {
        r.regel("drop schema IF EXISTS ", schema.getNaam(), " CASCADE;");
        r.regel("CREATE SCHEMA ", schema.getNaam(), ";");
    }
}
