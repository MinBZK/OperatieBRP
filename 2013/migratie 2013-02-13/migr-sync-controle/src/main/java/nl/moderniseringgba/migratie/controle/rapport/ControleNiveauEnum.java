/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

/**
 * Enum met de mogelijke waarden voor controle niveau.
 */
public enum ControleNiveauEnum {

    /**
     * Controle niveau waar alleen versie en datum gecontroleerd worden.
     */
    VERSIE_EN_DATUM,
    /**
     * Controle niveau waar de volledige PL gecontroleerd wordt.
     */
    VOLLEDIGE_PL
}
