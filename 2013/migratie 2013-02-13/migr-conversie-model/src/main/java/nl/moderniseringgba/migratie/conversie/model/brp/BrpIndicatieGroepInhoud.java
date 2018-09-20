/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

/**
 * Deze interface typeert BrpGroepInhoud waarvoor geldt dat het indicaties zijn. Indicatie worden binnen het BRP
 * opertionele gegevens model op de PersoonIndicatie gemapped.
 */
public interface BrpIndicatieGroepInhoud extends BrpGroepInhoud {

    /**
     * @return true als er sprake is van een indicatie, anders false of null als de inhoud leeg is
     */
    Boolean getHeeftIndicatie();
}
