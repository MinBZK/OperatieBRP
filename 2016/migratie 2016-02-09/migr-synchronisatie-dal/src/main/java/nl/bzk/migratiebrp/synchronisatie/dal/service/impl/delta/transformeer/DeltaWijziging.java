/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.util.ArrayList;
import java.util.List;

/**
 * De verschillen tussen BRP voorkomens kunnen worden onderverdeeld in een aantal soorten wijzigingen. Deze delta
 * wijzigingen staan hieronder opgesomd.
 */
public enum DeltaWijziging {

    /** DW-001, een verwijderde rij die een M-rij wordt. */
    DW_001(),
    /** DW-002, een toegevoegde rij. */
    DW_002(),
    /** DW-002-ACT, een toegevoegde rij waarbij DEG en tsverval beide leeg zijn. */
    DW_002_ACT(),
    /** DW-003, een combinatie van DW-001 en DW-002. */
    DW_003(),
    /** DW-011, actualisering waardoor rij vervalt. */
    DW_011(),
    /** DW-012, correctie waardoor rij vervalt. */
    DW_012(),
    /** DW-021, actualisering, waardoor rij materieel eindigt. */
    DW_021(),
    /** DW-022, correctie waardoor rij vervalt. */
    DW_022(),
    /** DW-023, correctie waardoor DEG gevuld wordt obv andere rij. */
    DW_023(),
    /** DW-024, actualisering waardoor actuele rij vervalt vervalt. */
    DW_024(),
    /** DW-025, correctie waardoor 'stapel' vervalt. */
    DW_025(),
    /** DW-031, actualisering uit categorie 08 (binnengemeentelijke verhuizing). */
    DW_031(),
    /** DW-032, actualisering uit categorie 08 (intergemeentelijke verhuizing). */
    DW_032(),
    /** DW-034, correctie uit categorie 08. */
    DW_034(),
 /** DW-041 Bijhouding van nationaliteit wordt beeindigd. */
    DW_041(),
    /**
     * DW-901, een wijziging van alleen de tsreg in een voorkomen die volgt uit categorie 07 of 13 die vervolgens wordt
     * genegeerd.
     */
    DW_901(false);

    private final boolean isNodigVoorSyncResyncBeslissing;

    /**
     * default Constructor.
     */
    DeltaWijziging() {
        this(true);
    }

    /**
     * Constructor.
     * @param isNodigVoorSyncResyncBeslissing
     *              zet indicator
     *
     */
    DeltaWijziging(final boolean isNodigVoorSyncResyncBeslissing) {
        this.isNodigVoorSyncResyncBeslissing = isNodigVoorSyncResyncBeslissing;
    }

    /**
     * @return de lijst met DeltaWijzigingen die genegeerd kunnen worden in de sync/resync beslissing.
     */
    public static DeltaWijziging[] teNegerenDeltaWijzigingen() {
        final List<DeltaWijziging> result = new ArrayList<>();
        for (DeltaWijziging deltaWijziging : DeltaWijziging.values()) {
            if (!deltaWijziging.isNodigVoorSyncResyncBeslissing()) {
                result.add(deltaWijziging);
            }
        }
        return result.toArray(new DeltaWijziging[result.size()]);
    }

    @Override
    public final String toString() {
        return name().replace('_', '-');
    }

    /**
     * @return true als deze deltawijziging gebruikt moet worden in de keuze sync/resync, anders false.
     */
    public boolean isNodigVoorSyncResyncBeslissing() {
        return isNodigVoorSyncResyncBeslissing;
    }
}
