/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.stereotype.Component;

/**
 * Mapt de identificatienummers (voor een geregistreerd partnerschap).
 */
@Component
public final class GeregistreerdPartnerschapIdentificatienummersMapper extends AbstractIdentificatienummersMapper {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS.getId());

    /**
     * Constructor.
     */
    public GeregistreerdPartnerschapIdentificatienummersMapper() {
        super(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(
                        Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()));
    }
}
