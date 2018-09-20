/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;


/**
 * Een voorkomen (of het ontbreken daarvan) van een
 * <p/>
 * Een LO3 bericht bevat ��n of meer categorie�n, sommige daarvan repeterend (te zien aan LO3 stapelvolgnummer); elke (repetitie van een) categori� bevat
 * ��n of meer voorkomens. In gevallen waarbij het LO3 bericht een voorkomen h�d moeten hebben, maar dit niet heeft, wordt dit ontbrekende voorkomen hier
 * alsnog vastgelegd, zodat een LO3 melding hierover kan worden vastgelegd.
 */
public interface LO3Voorkomen extends LO3VoorkomenBasis {

}
