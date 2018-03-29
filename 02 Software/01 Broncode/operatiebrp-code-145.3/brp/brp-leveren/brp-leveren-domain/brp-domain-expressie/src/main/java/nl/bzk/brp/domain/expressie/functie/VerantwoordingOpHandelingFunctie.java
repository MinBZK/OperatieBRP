/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.EnumSet;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Functie voor het opvragen van verantwoordingsgegevens binnen de context
 * van een {@link AdministratieveHandeling Administratievehandeling}
 * van de geselecteerde {@link Actie}.
 * <p>
 * De functie vereist twee parameters:
 * <p>
 * Parameter 1: Een expressie op de persoonslijst welke een verantwoordingsattribuut
 * aanwijst (dit zijn de actieInhoud, actieVerval en actieAanpassingGeldigheids attributen).
 * <p>
 * Parameter 2: Een expressie op de het geselecteerde {@link Actie} object. Geldige expressies zijn
 * attributen onder de elementen {@link Element#ADMINISTRATIEVEHANDELING}, {@link Element#ACTIE},
 * {@link Element#ACTIEBRON} en {@link Element#DOCUMENT}
 * <p>
 * Voorbeelden:
 * <br>AH(Persoon.Identificatienummers.ActieInhoud, [Document.Aktenummer])
 * <br>AH(Persoon.Identificatienummers.ActieInhoud, [ActieBron.RechtsgrondCode])
 * <br>AH(Persoon.Identificatienummers.ActieVerval, [Actie.TijdstipRegistratie])
 * <br>AH(Persoon.Identificatienummers.ActieVerval, [AdministratieveHandeling.PartijCode]])
 * <p>
 * Het resultaat van de functie is een lijst met de geselecteerde attributen.
 */
@Component
@FunctieKeyword("AH")
final class VerantwoordingOpHandelingFunctie extends AbstractVerantwoordingFunctie {

    private static final EnumSet<Element> VERANTWOORDINGS_ELEMENTEN = EnumSet.of(
            Element.ADMINISTRATIEVEHANDELING, Element.ACTIE, Element.ACTIEBRON, Element.DOCUMENT);

    @Override
    protected MetaObject bepaalMetaObject(final Actie actie) {
        return actie.getMetaObject().getParentObject();
    }

    @Override
    protected boolean isGeldigVerantwoordingElement(final ElementObject element) {
        if (!(element instanceof AttribuutElement)) {
            return false;
        }
        final ObjectElement objectElement = ((AttribuutElement) element).getGroep().getObjectElement();
        return VERANTWOORDINGS_ELEMENTEN.contains(objectElement.getElement());
    }
}
