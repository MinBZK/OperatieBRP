/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * Class met constanten voor veel gebruikte Elementen.
 */
public final class ElementConstants {

    /**
     * Waarde voor: Persoon - geboorte.
     */
    public static final GroepElement PERSOON_GEBOORTE = ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE);


    /**
     * Waarde voor: Persoon - indentificatienummers.
     */
    public static final GroepElement PERSOON_IDENTIFICATIENUMMERS = ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS);

    /**
     * Waarde voor: Persoon - samengesteldenaam.
     */
    public static final GroepElement PERSOON_SAMENGESTELDENAAM = ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM);

    /**
     * Waarde voor: Persoon.
     */
    public static final ObjectElement PERSOON = ElementHelper.getObjectElement(Element.PERSOON);

    /**
     * Waarde voor: Relatie.
     */
    public static final ObjectElement RELATIE = ElementHelper.getObjectElement(Element.RELATIE);

    /**
     * Waarde voor: Betrokkenheid.
     */
    public static final ObjectElement BETROKKENHEID = ElementHelper.getObjectElement(Element.BETROKKENHEID);


    private ElementConstants() {

    }


}
