/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.metaregister.model.GeneriekElement;

/**
 * Interface voor een naamgeving strategie die methodes biedt om Java Type en package namen te bouwen op basis van
 * BMR elementen waarvoor de code moet worden gegenereerd. De verschillende modellen houden hun eigen naamgeving
 * strategie en package structuur er op na en dat is opgenomen in model specifieke implementaties van deze interface.
 */
public interface NaamgevingStrategie {

    /**
     * Retourneert het java type voor het opgegeven element als object.
     * Dat object kan verder ondervraagt worden naar klasse naam, package pad of beiden.
     *
     * @param element het element
     * @return het java type
     */
    JavaType getJavaTypeVoorElement(GeneriekElement element);

}
