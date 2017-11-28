/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;

/**
 * Element interface.
 */
public interface ElementObject {

    /**
     * Geeft onderliggend element.
     * @return het onderliggende element.
     */
    Element getElement();

    /**
     * Geeft het type element.
     * @param <T> het type element
     * @return geef het type element, of zichzelf indien null.
     */
    <T extends ElementObject> T getTypeObject();

    /**
     * Initialiseer voor een specifiek ElementObject, benodigde attributen ahv een {@link ElementLocator}.
     * @param elementLocator elementlocator
     */
    void postCreate(ElementLocator elementLocator);

    /**
     * @return de expressie voor het aanwijzen van dit element
     */

    default String getNaamAlias() {
        return getElement().getElementWaarde().getNaamAlias();
    }

    /**
     * @return id van het element
     */
    default Integer getId() {
        return getElement().getId();
    }

    /**
     * @return naam van het element
     */
    default String getNaam() {
        return getElement().getNaam();
    }

    /**
     * @return elementnaam van het element
     */
    default String getElementNaam() {
        return getElement().getElementNaam();
    }

    /**
     * @return elementId van het type element
     */
    default Integer getType() {
        return getElement().getType() == null ? null : getElement().getType().getId();
    }

    /**
     * Geeft het volgnummer vh element.
     * @return volgnummer van het element
     */
    default int getVolgnummer() {
        return getElement().getVolgnummer();
    }

    /**
     * @return indicatie of het element in berichten voorkomt
     */
    default boolean inBericht() {
        return getElement().getElementWaarde().isInBericht();
    }

    /**
     * @return naam van het element in bericht
     */
    default String getXmlNaam() {
        return getElement().getElementWaarde().getXmlNaam();
    }

    /**
     * @return de soort inhoud
     */
    default String getSoortInhoud() {
        return getElement().getElementWaarde().getSoortInhoud();
    }

    /**
     * @return indicatie of voor het element een expressie bestaat en het aanwijsbaar is middels de expressietaal
     */
    default boolean heeftExpressie() {
        return true;
    }

    /**
     * @return de dataum aanvang geldigheid van het element
     */

    default Integer getDatumAanvangGeldigheid() {
        return getElement().getElementWaarde().getDatumAanvangGeldigheid();
    }

    /**
     * @return de datum einde geldigheid van het element
     */
    default Integer getDatumEindeGeldigheid() {
        return getElement().getElementWaarde().getDatumEindeGeldigheid();
    }

    /**
     * @return de autorisatie, wie mogen het element geleverd krijgen.
     */
    default SoortElementAutorisatie getAutorisatie() {
        return getElement().getElementWaarde().getSoortAutorisatie();
    }

    /**
     * @param elementLocator de locator
     * @param <T> het type element
     * @return geef het type element, of zichzelf indien null.
     */
    default <T extends ElementObject> T getTypeObject(final ElementLocator elementLocator) {
        if (getElement().getElementWaarde().getType() == null) {
            return (T) this;
        }
        return (T) elementLocator.getObject(getElement().getElementWaarde().getType());
    }
}
