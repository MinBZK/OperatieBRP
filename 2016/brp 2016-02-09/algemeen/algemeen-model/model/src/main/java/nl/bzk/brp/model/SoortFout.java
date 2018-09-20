/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

/**
 * Enumeratie die aangeeft wat voor een fout is opgetreden in relatie tot de verwerking.
 */
public enum SoortFout {

    /**
     * Niet van toepassing: het is geen fout.
     */
    NVT,
    /**
     * Het is een fout, maar de verwerking kan doorgaan.
     */
    VERWERKING_KAN_DOORGAAN,
    /**
     * Het is een fout en de fout verhindert verdere verwerking.
     */
    VERWERKING_VERHINDERD;

}
