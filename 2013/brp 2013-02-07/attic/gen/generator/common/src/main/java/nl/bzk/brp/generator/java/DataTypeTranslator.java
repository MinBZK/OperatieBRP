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
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UnknownFormatConversionException;


/**
 * 
 *
 */
public class DataTypeTranslator {

    private static final String                _GROOT               = "_groot";
    private static final String                _MIDDEL              = "_middel";
    private static final Map<String, Class<?>> basisTypeMappingJava = new TreeMap<String, Class<?>>();

    enum BasisType {
        GEHEEL_GETAL("geheel getal"), DECIMAAL_GETAL("decimaal getal"), TEKST("tekst"), ONBEPERKTE_TEKST(
                "onbeperkte tekst"), ONGESTUCTUREERDE_DATA("ongestructureerde data"), DATUM("datum"), TIJD("tijd"),
                DATUM_TIJD("datum/tijd"), IP_ADRES("ip adres"), GROOT_GEHEEL_GETAL("groot geheel getal"), KLEIN_GEHEEL_GETAL(
                        "klein geheel getal"), NUMERIEKE_CODE("numerieke code"),
                        DATUM_MET_ONZEKERHEID("datum met onzekerheid"), BOOLEAN("boolean"), ID_KLEIN("id klein"), ID_NORMAAL(
                                "id normaal"), ID_GROOT("id groot");

        private final String typeAanduiding;

        private BasisType(String aanduidingInMetaregister) {
            typeAanduiding = aanduidingInMetaregister;
        }

        public String getBasisType() {
            return typeAanduiding;
        }

    }

    static {
        basisTypeMappingJava.put(BasisType.GEHEEL_GETAL.getBasisType(), Integer.class);
        basisTypeMappingJava.put(BasisType.DECIMAAL_GETAL.getBasisType(), Short.class);
        basisTypeMappingJava.put(BasisType.TEKST.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.ONBEPERKTE_TEKST.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.ONGESTUCTUREERDE_DATA.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.DATUM.getBasisType(), Integer.class);
        basisTypeMappingJava.put(BasisType.TIJD.getBasisType(), Date.class);
        basisTypeMappingJava.put(BasisType.DATUM_TIJD.getBasisType(), Date.class);
        basisTypeMappingJava.put(BasisType.IP_ADRES.getBasisType(), String.class);
        basisTypeMappingJava.put(BasisType.GROOT_GEHEEL_GETAL.getBasisType(), Long.class);
        basisTypeMappingJava.put(BasisType.KLEIN_GEHEEL_GETAL.getBasisType(), Short.class);
        // TODO: deze mapping is niet correct, meerdere mogelijkheden
        basisTypeMappingJava.put(BasisType.NUMERIEKE_CODE.getBasisType(), Short.class);
        basisTypeMappingJava.put((BasisType.NUMERIEKE_CODE.getBasisType().concat(_MIDDEL)), Integer.class);
        basisTypeMappingJava.put((BasisType.NUMERIEKE_CODE.getBasisType().concat(_GROOT)), Long.class);
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

    public static String getJavaType(String basisType, String length) {
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

    public static Class<?> getJavaClass(String basisType, Integer length) {
        String inputType = basisType.toLowerCase();
        if (basisTypeMappingJava.containsKey(inputType)) {
            if (length != null && length > 0) {
                inputType = determineBasisTypeWithLength(length, inputType);
            }
            return basisTypeMappingJava.get(inputType.toLowerCase());
        } else {
            throw new UnknownFormatConversionException("Basistype " + basisType
                    + " is niet bekend en kan niet naar een Java type worden omgezet");
        }
    }

    /**
     * Afhankelijk van de lengthe wordt het basistype hier verandert door er middel of groot aan te plakken.
     * @param length
     * @param inputType
     * @return
     */
    private static String determineBasisTypeWithLength(Integer length, String inputType) {
        if (inputType.equalsIgnoreCase(BasisType.NUMERIEKE_CODE.getBasisType())) {
            if (length > 4 && length < 10) {
                inputType = inputType.concat(_MIDDEL);
            }
            if (length > 9) {
                inputType = inputType.concat(_GROOT);
            }
        }
        return inputType;
    }

    public static String getImportForBasisType(String basisType, Integer length) {
        if (basisTypeMappingJava.containsKey(basisType.toLowerCase())) {
            String inputType = determineBasisTypeWithLength(length, basisType);
            return basisTypeMappingJava.get(inputType.toLowerCase()).getName();
        } else {
            throw new UnknownFormatConversionException("Basistype " + basisType
                    + " is niet bekend en kan niet naar een Java type worden omgezet");
        }
    }
}
