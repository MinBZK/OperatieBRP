/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import java.util.List;

/**
 * Interface voor het vinden van elementen.
 */
interface ElementLocator {

    /**
     * Geeft het object obv het element id.
     * @param elementId het elementId
     * @return het object element, of null indien niet gevonden.
     */
    ObjectElement getObject(int elementId);

    /**
     * Geeft de attributen behorende bij de gegeven groep.
     * @param elementId het elementId van de groep
     * @return een lijst met attribuut elementen
     */
    List<AttribuutElement> getAttributenInGroep(int elementId);

    /**
     * Geeft de groepen behorende bij het gegeven object.
     * @param elementId het elementId van het object
     * @return een lijst met groep elementen
     */
    List<GroepElement> getGroepenInObject(int elementId);

    /**
     * Geeft de groep waarin de sorteer attributen zich bevinden indien aanwezig.
     * @param objectElement objectElement
     * @return de groep waarin de sorteer attributen zich bevinden indien aanwezig
     */
    GroepElement getSorteerGroep(ObjectElement objectElement);

    /**
     * Geeft de sorteerattributen terug voor een object indien aanwezig.
     * @param objectElement het objectelement waarvoor sorteer attributen opgehaald worden
     * @return de attribuutelementen waarop object gesorteerd moet worden. In sorteervolgorde als er meerdere zijn.
     */
    List<AttribuutElement> getSorteerElementen(ObjectElement objectElement);

    /**
     * Geeft het groepelement dat hoort bij het gegeven elementId.
     * @param elementId element id van de groep
     * @return een groep element
     */
    GroepElement getGroep(int elementId);

    /**
     * Geeft het attribuutelement dat hoort bij het gegeven elementId.
     * @param elementId element id van het attribuut
     * @return een attribuut element
     */
    AttribuutElement getAttribuut(int elementId);
}
