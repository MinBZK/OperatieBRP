/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync;

/**
 * Enum voor SyncBerichtType. Gebruikt als prefix (messageId) van sync berichten vanuit InitVulling.
 */
public enum SyncBerichtType {
    /**
     * Persoon (IV.PERS).
     */
    PERSOON("IV.PERS"),

    /**
     * Afnemerindicatie (IV.IND).
     */
    AFNEMERINDICATIE("IV.IND"),

    /**
     * Autorisatie (IV.AUT).
     */
    AUTORISATIE("IV.AUT");

    private final String type;

    private SyncBerichtType(final String type) {
        this.type = type;
    }

    /**
     * Geef de waarde van type.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Bepaal de waarde op basis van de type.
     * 
     * @param type
     *            code
     * @return een SyncBerichtType object
     */
    public static final SyncBerichtType valueOfType(final String type) {
        for (final SyncBerichtType waarde : SyncBerichtType.values()) {
            if (type.equals(waarde.getType())) {
                return waarde;
            }
        }
        throw new IllegalArgumentException("Onbekend type voor SyncBerichtType: " + type);
    }

}
