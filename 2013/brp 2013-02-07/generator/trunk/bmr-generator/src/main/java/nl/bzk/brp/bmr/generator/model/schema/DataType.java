/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.Map;
import java.util.TreeMap;


public class DataType {

    public static class DatumMetOnzekerheid extends DataType {

        public DatumMetOnzekerheid(final String postgresType) {
            super(postgresType);
        }

        @Override
        public String getPostgresType(final Integer max, final Integer decimalen) {
            return super.getPostgresType(8, 0);
        }
    }
    public static class DecimaalGetal extends DataType {

        public DecimaalGetal(final String postgresType) {
            super(postgresType);
        }

    }

    public static class GeheelGetal extends DataType {

        public GeheelGetal(final String postgresType) {
            super(postgresType);
        }

        @Override
        public String getPostgresType(final Integer max, final Integer decimalen) {
            return super.getPostgresType();
        }
    }

    public static class NumeriekeCode extends DataType {

        public NumeriekeCode(final String postgresType) {
            super(postgresType);
        }
    }

    public static class Tekst extends DataType {

        public Tekst(final String postgresType) {
            super(postgresType);
        }
    }

    private static final Map<String, DataType> dataTypes = new TreeMap<String, DataType>();

    static {
        dataTypes.put("geheel getal", new GeheelGetal("Integer"));
        dataTypes.put("decimaal getal", new DecimaalGetal("Numeric"));
        dataTypes.put("tekst", new Tekst("Varchar"));
        dataTypes.put("onbeperkte tekst", new DataType("Text"));
        dataTypes.put("ongestructureerde data", new DataType("OID"));
        dataTypes.put("datum", new DataType("Date"));
        dataTypes.put("tijd", new DataType("Time"));
        dataTypes.put("datum/tijd", new DataType("Timestamp"));
        dataTypes.put("ip adres", new DataType("inet"));
        dataTypes.put("groot geheel getal", new DataType("Bigint"));
        dataTypes.put("klein geheel getal", new DataType("Smallint"));
        dataTypes.put("numerieke code", new NumeriekeCode("Varchar"));
        dataTypes.put("datum met onzekerheid", new DatumMetOnzekerheid("Numeric"));
        dataTypes.put("boolean", new DataType("boolean"));
        dataTypes.put("id klein", new GeheelGetal("smallint"));
        dataTypes.put("id normaal", new GeheelGetal("Integer"));
        dataTypes.put("id groot", new GeheelGetal("bigint"));
    }

    public static DataType getDataType(final String naam) {
        return dataTypes.get(naam);
    }

    private final String                             postgresType;

    private DataType(final String postgresType) {
        this.postgresType = postgresType;
    }

    public String getPostgresType() {
        return postgresType;
    }

    /**
     * Geef het postgresType.
     *
     * @return the postgresType
     */
    public String getPostgresType(final Integer max, final Integer decimalen) {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(postgresType);
        if ((max != null) || (decimalen != null)) {
            resultaat.append("(");
            if (max != null) {
                resultaat.append(max);
            }
            if ((decimalen != null) && (decimalen > 0)) {
                if (max != null) {
                    resultaat.append(", ");
                }
                resultaat.append(decimalen);
            }
            resultaat.append(")");
        }
        return resultaat.toString();
    }
}
