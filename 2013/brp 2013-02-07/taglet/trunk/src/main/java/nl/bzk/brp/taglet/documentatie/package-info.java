/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Alle classes die nodig zijn voor de documentatie javadoc taglet.
 * Deze taglet kan gebruikt worden in javadoc commentaar.
 * De syntax is "brp.documentatie" en daarachter de titel van de documentatie dat door die methode of class
 * geimplementeerd wordt.
 * Er kunnen meerdere bedrijfsregel tags gebruikt worden per javadoc commentaar.
 * Ook kunnen er meerdere bedrijfsregel titels worden gebruikt per tag gescheiden door komma's.
 * Tijdens het genereren van de javadoc wordt er ook een CSV bestand aangemaakt met de naam "documentatie-export.csv".
 * Dit bestand komt in dezelfde directory als de gegenereerde javadoc en bevat alle referenties die voorkomen in de
 * broncode.
 */
package nl.bzk.brp.taglet.documentatie;
