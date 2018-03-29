/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie van databasetypen.
 */
public enum DatabaseType {

    /**
     * Integer. (equivalent aan java.lang.Integer).
     */
    INTEGER("Integer"),
    /**
     * BigInt (equivalent aan java.lang.Long).
     */
    BIGINT("Bigint"),
    /**
     * Boolean.
     */
    BOOLEAN("Boolean"),
    /**
     * Smallint (equivalent aan java.lang.Short).
     */
    SMALLINT("Smallint"),
    /**
     * Text.
     */
    TEXT("Text"),
    /**
     * Timestamp.
     */
    TIMESTAMP("Timestamp with time zone"),
    /**
     * Char.
     */
    CHAR("Char"),
    /**
     * Varchar.
     */
    VARCHAR("Varchar");

    private String code;

    /**
     * Constructor.
     * @param code code
     */
    DatabaseType(final String code) {
        this.code = code;
    }

    /**
     * @return de code van het DatabaseType
     */
    public String getCode() {
        return code;
    }

    /**
     * Geeft de DatabaseType op code.
     * @param code een code
     * @return de DatabaseType
     * @throws IllegalArgumentException als de code niet als DatabaseType bestaat
     */
    public static DatabaseType parseCode(final String code) {
        for (DatabaseType databaseType : DatabaseType.values()) {
            if (databaseType.getCode().equals(code)) {
                return databaseType;
            }
        }
        throw new IllegalArgumentException(String.format("DatabaseType met code %s kan niet geparst worden.", code));
    }
}
