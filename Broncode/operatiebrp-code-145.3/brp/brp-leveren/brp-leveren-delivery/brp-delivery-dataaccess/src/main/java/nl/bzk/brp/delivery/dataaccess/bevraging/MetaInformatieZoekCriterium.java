/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * MetaInformatieZoekCriterium.
 */
final class MetaInformatieZoekCriterium {
    private final Set<MetaInformatieZoekCriterium> zoekCriteriaOrClauses = new HashSet<>();
    private final Element databaseInfoAttribuut;
    private final Element tabelDatabaseInfo;
    private final Element objectDatabaseInfo;
    private final boolean bekijkHistorisch;
    private final ObjectElement objectElementVanAttribuut;
    private ZoekCriterium zoekCriterium;
    private MetaInformatieZoekCriterium additioneel;

    /**
     * @param zoekCriterium zoekCriterium
     * @param historischeZoekvraag historischeZoekvraag
     */
    private MetaInformatieZoekCriterium(final ZoekCriterium zoekCriterium, final boolean historischeZoekvraag) {
        final AttribuutElement attribuutElement = zoekCriterium.getElement();
        this.databaseInfoAttribuut = attribuutElement.getElement();
        //historisch zoeken gaat alleen over materiele historie, als groep alleen formeel dan moet deze actuele zijn.
        //Dus kijken we daar naar indag indicator
        this.bekijkHistorisch = historischeZoekvraag && attribuutElement.getGroep().isMaterieel()
                && databaseInfoAttribuut.getElementWaarde().getHistabel() != null;
        final int tabelId = bekijkHistorisch ? databaseInfoAttribuut.getElementWaarde().getHistabel()
                : databaseInfoAttribuut.getElementWaarde().getTabel();
        this.objectElementVanAttribuut = ElementHelper.getObjectElement(attribuutElement.getObjectType());
        this.tabelDatabaseInfo = ElementHelper.getElementMetId(tabelId).getElement();
        this.objectDatabaseInfo = objectElementVanAttribuut.getElement();
        this.zoekCriterium = zoekCriterium;
    }


    static MetaInformatieZoekCriterium maak(final ZoekCriterium zoekCriterium, final boolean historischeZoekvraag) {
        final MetaInformatieZoekCriterium metaInformatieZoekCriterium = new MetaInformatieZoekCriterium(zoekCriterium, historischeZoekvraag);
        MetaInformatieZoekCriterium.voegOfCriteriumToe(metaInformatieZoekCriterium, zoekCriterium);
        if (zoekCriterium.getAdditioneel() != null) {
            metaInformatieZoekCriterium.additioneel =
                    new MetaInformatieZoekCriterium(zoekCriterium.getAdditioneel(), metaInformatieZoekCriterium.bekijkHistorisch);
        }
        return metaInformatieZoekCriterium;
    }

    private static void voegOfCriteriumToe(final MetaInformatieZoekCriterium metaInformatieZoekCriterium, final ZoekCriterium zoekCriterium) {
        if (zoekCriterium.getOf() == null) {
            return;
        }
        final ZoekCriterium of = zoekCriterium.getOf();
        metaInformatieZoekCriterium.zoekCriteriaOrClauses.add(new MetaInformatieZoekCriterium(of, metaInformatieZoekCriterium.bekijkHistorisch));
        voegOfCriteriumToe(metaInformatieZoekCriterium, of);
    }

    public MetaInformatieZoekCriterium getAdditioneel() {
        return additioneel;
    }

    Set<MetaInformatieZoekCriterium> getZoekCriteriaOrClauses() {
        return zoekCriteriaOrClauses;
    }

    Element getDatabaseInfoAttribuut() {
        return databaseInfoAttribuut;
    }

    Element getTabelDatabaseInfo() {
        return tabelDatabaseInfo;
    }

    Element getObjectDatabaseInfo() {
        return objectDatabaseInfo;
    }

    ObjectElement getObjectElementVanAttribuut() {
        return objectElementVanAttribuut;
    }

    public AttribuutElement getAttribuutElement() {
        return zoekCriterium.getElement();
    }

    boolean isBekijkHistorisch() {
        return bekijkHistorisch;
    }

    ZoekCriterium getZoekCriterium() {
        return zoekCriterium;
    }
}
