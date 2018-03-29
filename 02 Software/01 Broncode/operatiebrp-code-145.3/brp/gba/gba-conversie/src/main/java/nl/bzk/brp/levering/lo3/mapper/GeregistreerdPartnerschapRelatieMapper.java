/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.stereotype.Component;

/**
 * Mapt een relatie (van een geregistreerd partnerschap).
 */
@Component
public final class GeregistreerdPartnerschapRelatieMapper extends AbstractRelatieMapper {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD.getId());
    /**
     * Reden einde attribuut element.
     */
    public static final AttribuutElement
            REDEN_EINDE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE.getId());

    /**
     * Constructor.
     */
    public GeregistreerdPartnerschapRelatieMapper() {
        super(IDENTITEIT_GROEP_ELEMENT,
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG.getId()),
                REDEN_EINDE_ELEMENT,
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE.getId()),
                ElementHelper.getAttribuutElement(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE.getId()));
    }
}
