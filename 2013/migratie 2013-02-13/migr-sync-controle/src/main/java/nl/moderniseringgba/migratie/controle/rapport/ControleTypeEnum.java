/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

/**
 * Enum voor de mogelijke controle types.
 */
public enum ControleTypeEnum {

    /**
     * Controle Type: 1 persoon controleren.
     */
    EEN_PERSOON,
    /**
     * Controle Type: alle persoon controleren.
     */
    ALLE_PERSONEN,
    /**
     * Controle Type: alle personen van een bepaalde gemeente controleren.
     */
    GEMEENTE,
    /**
     * Controle Type: alle RNI personen controleren.
     */
    RNI
}
