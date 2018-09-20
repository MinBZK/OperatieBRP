/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * 
 */
package nl.bzk.brp.generator.java;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UnknownFormatConversionException;


/**
 * 
 *
 */
public class DataTypeTranslator {

    private static final Map<String, Class<?>> basisTypeMappingJava = new TreeMap<String, Class<?>>();

    enum BasisType {
        GEHEEL_GETAL("geheel getal"),
        DECIMAAL_GETAL("decimaal getal"),
        TEKST("tekst"),
        ONBEPERKTE_TEKST("onbeperkte tekst"),
        ONGESTUCTUREERDE_DATA("ongestructureerde data"),
        DATUM("datum"),
        TIJD("tijd"),
        DATUM_TIJD("datum/tijd"),
        IP_ADRES("ip adres"),
        GROOT_GEHEEL_GETAL("groot geheel getal"),
        KLEIN_GEHEEL_GETAL("klein geheel getal"),
        NUMERIEKE_CODE("numerieke code"),
        DATUM_MET_ONZEKERHEID("datum met onzekerheid"),
        BOOLEAN("boolean"),
        ID_KLEIN("id klein"),
        ID_NORMAAL("id normaal"),
        ID_GROOT("id groot");

        private String typeAanduiding;

        private BasisType(String aanduidingInMetaregister) {
            typeAanduiding = aanduidingInMetaregister;
        }

        public String getBasisType() {
            return typeAanduiding;
        }

    }

    static {
        basisTypeMappingJava.put(BasisType.GEHEEL_GETAL.getBasisType(), Integer.class);
        basisTypeMappingJava.put(BasisType.DECIMAAL_GETAL.getBasisType(), Float.class);
        basisTypeMappingJava.put(BasisType.TEKST.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.ONBEPERKTE_TEKST.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.ONGESTUCTUREERDE_DATA.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.DATUM.getBasisType(), Integer.class);
        // TODO: mapping zo goed?
        basisTypeMappingJava.put(BasisType.TIJD.getBasisType(), Date.class);
        basisTypeMappingJava.put(BasisType.DATUM_TIJD.getBasisType(), Date.class);
        basisTypeMappingJava.put(BasisType.IP_ADRES.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.GROOT_GEHEEL_GETAL.getBasisType(), Long.class);
        basisTypeMappingJava.put(BasisType.KLEIN_GEHEEL_GETAL.getBasisType(), Short.class);
        // TODO: mapping zo goed?
        basisTypeMappingJava.put(BasisType.NUMERIEKE_CODE.getBasisType(), String.class);
        // TODO: mapping zo goed?
        basisTypeMappingJava.put(BasisType.DATUM_MET_ONZEKERHEID.getBasisType(), Integer.class);
        basisTypeMappingJava.put(BasisType.BOOLEAN.getBasisType(), Boolean.class);
        basisTypeMappingJava.put(BasisType.ID_KLEIN.getBasisType(), Short.class);
        basisTypeMappingJava.put(BasisType.ID_NORMAAL.getBasisType(), Integer.class);
        basisTypeMappingJava.put(BasisType.ID_GROOT.getBasisType(), BigInteger.class);
    }

    public static String getJavaType(String basisType) {
        if (basisTypeMappingJava.containsKey(basisType.toLowerCase())) {
            return basisTypeMappingJava.get(basisType.toLowerCase()).getSimpleName();
        } else {
            throw new UnknownFormatConversionException("Basistype " + basisType
                + " is niet bekend en kan niet naar een Java type worden omgezet");
        }
    }

    public static Class<?> getJavaClass(String basisType) {
        if (basisTypeMappingJava.containsKey(basisType.toLowerCase())) {
            return basisTypeMappingJava.get(basisType.toLowerCase());
        } else {
            throw new UnknownFormatConversionException("Basistype " + basisType
                + " is niet bekend en kan niet naar een Java type worden omgezet");
        }
    }

    public static String getImportForBasisType(String basisType) {
        if (basisTypeMappingJava.containsKey(basisType.toLowerCase())) {
            return basisTypeMappingJava.get(basisType.toLowerCase()).getName();
        } else {
            throw new UnknownFormatConversionException("Basistype " + basisType
                + " is niet bekend en kan niet naar een Java type worden omgezet");
        }
    }
}
