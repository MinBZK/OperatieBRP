/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeratie met alle ondersteunde bestand extensies voor de GGO-viewer.
 */
public enum OndersteundeFormatenEnum {
    /**
     * Extensie voor AM-bestanden.
     */
    GBA("gba"),
    /**
     * Extensie voor Excel bestanden.
     */
    XLS("xls"),
    /**
     * Extensie voor Lg01 bestanden in lelijk (1 regel bericht) formaat.
     */
    TXT("txt"),
    /**
     * Extensie voor Lg01 bestanden in lelijk (1 regel bericht) formaat.
     */
    LG01("lg01"),
    /**
     * Extensie voor Lg01 bestanden in mooi (leesbaar bericht) formaat.
     */
    NIC("nic");

    private static final Map<String, OndersteundeFormatenEnum> FORMATEN_MAP = new HashMap<>();

    private String bestandExtensie;

    static {
        for (final OndersteundeFormatenEnum ondersteundFormaat : values()) {
            FORMATEN_MAP.put(ondersteundFormaat.bestandExtensie, ondersteundFormaat);
        }
    }

    /**
     * Constructor om de enumeratie te maken.
     * 
     * @param bestandExtensie
     *            extensie van het bestand
     */
    OndersteundeFormatenEnum(final String bestandExtensie) {
        this.bestandExtensie = bestandExtensie;
    }

    /**
     * Geeft de extensies terug die ondersteund worden.
     * 
     * @return een array met de ondersteunde extensies
     */
    public static String[] getBestandExtensies() {
        final List<String> extensieList = new ArrayList<>();
        for (final OndersteundeFormatenEnum ondersteundFormaat : values()) {
            extensieList.add(ondersteundFormaat.bestandExtensie);
        }
        return extensieList.toArray(new String[extensieList.size()]);
    }

    /**
     * Geef de enumeratie waarde voor de gegeven code.
     *
     * @param extensie
     *            de extensie van een bestand
     * @return de enumeratie waarde, null als de extensie niet gevonden kan worden
     */
    public static OndersteundeFormatenEnum getByExtensie(final String extensie) {
        return FORMATEN_MAP.get(extensie);
    }

    /**
     * Geeft de bijbehorende bestand extensie terug.
     * 
     * @return de bestand extensie
     */
    public String getBestandExtensie() {
        return bestandExtensie;
    }
}
