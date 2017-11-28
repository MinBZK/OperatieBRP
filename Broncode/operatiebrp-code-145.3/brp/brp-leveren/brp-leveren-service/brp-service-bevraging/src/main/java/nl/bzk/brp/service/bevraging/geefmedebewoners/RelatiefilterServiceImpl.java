/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;

/**
 * Het resultaatbericht bevat alleen gegevens van een Relatie indien
 * de gerelateerde ook als Persoon voorkomt in het resultaatbericht.
 * Toelichting: de gerelateerde kan ook een niet-ingeschrevene zijn; 'Niet-ingeschrevene' (R2272). In dit geval moet op gelijk
 * Persoon.Burgerservicenummer de gerelateerde als Persoon voorkomen in het resultaatbericht.
 */
@Component
@Bedrijfsregel(Regel.R2385)
final class RelatiefilterServiceImpl implements RelatiefilterService {

    private static final AttribuutElement BSN_ATTRIBUUT = ElementHelper
            .getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    private static final ObjectElement[] GERELATEERDE_PERSONEN = {
            ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON),
            ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON),
            ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON),
            ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON),
    };

    private static final AttribuutElement[] GERELATEERDE_BSN_ATTR = {
            ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
            ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
            ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
            ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
    };

    @Override
    public List<Persoonslijst> filterRelaties(final List<Persoonslijst> persoonslijstList, final int peilmomentMaterieel) {

        //verzamel het burgerservicenummer van de gevonden personen
        final Set<String> persBsnSet = Sets.newHashSet();
        for (final Persoonslijst persoonslijst : persoonslijstList) {
            final Persoonslijst persoonslijstOpMaterieelPeilmoment = persoonslijst.beeldVan().materieelPeilmoment(peilmomentMaterieel);
            final String bsn = persoonslijstOpMaterieelPeilmoment.<String>getActueleAttribuutWaarde(BSN_ATTRIBUUT).orElse(null);
            persBsnSet.add(bsn);
        }
        //maak een resultaatlijst waarbij gerelateerde betrokkenheden potentieel weggefilterd zijn
        final List<Persoonslijst> gefilterdePersoonslijst = Lists.newArrayListWithCapacity(persoonslijstList.size());
        for (Persoonslijst persoonslijst : persoonslijstList) {
            final Persoonslijst e = filterPersoonslijst(persBsnSet, persoonslijst);
            gefilterdePersoonslijst.add(e);
        }
        return gefilterdePersoonslijst;
    }

    private Persoonslijst filterPersoonslijst(final Set<String> persBsnSet, final Persoonslijst persoonslijst) {
        //bepaal alle gerelateerde personen
        final Set<MetaObject> alleGerelateerdePersonen = Sets.newHashSet();
        for (ObjectElement objectElement : GERELATEERDE_PERSONEN) {
            alleGerelateerdePersonen.addAll(persoonslijst.getModelIndex().geefObjecten(objectElement));
        }
        //bepaal alle gerelateerde personen met een bsn match
        final Set<MetaObject> gerelateerdePersonenMetBsnMatch = Sets.newHashSet();
        for (AttribuutElement attribuutElement : GERELATEERDE_BSN_ATTR) {
            final Set<MetaAttribuut> metaAttribuuts = persoonslijst.getModelIndex().geefAttributen(attribuutElement);
            for (MetaAttribuut metaAttribuut : metaAttribuuts) {
                if (persoonslijst.isActueel(metaAttribuut.getParentRecord()) && persBsnSet.contains(metaAttribuut.<String>getWaarde())) {
                    gerelateerdePersonenMetBsnMatch.add(metaAttribuut.getParentRecord().getParentGroep().getParentObject());
                }
            }
        }
        //bepaal de te verwijderen objecten
        final Set<MetaObject> teVerwijderenPersonen = Sets.difference(alleGerelateerdePersonen, gerelateerdePersonenMetBsnMatch);
        final Set<MetaObject> teVerwijderenGerelateerdeBetrokkenheden = teVerwijderenPersonen.stream()
                .map(MetaObject::getParentObject).collect(Collectors.toSet());
        final Set<MetaObject> teVerwijderenRelaties = teVerwijderenGerelateerdeBetrokkenheden.stream()
                .map(MetaObject::getParentObject).filter(metaObject ->
                        teVerwijderenGerelateerdeBetrokkenheden.containsAll(metaObject.getObjecten())).collect(Collectors.toSet());
        final Set<MetaObject> teVerwijderenBetrokkenheden = teVerwijderenRelaties.stream()
                .map(MetaObject::getParentObject).collect(Collectors.toSet());

        //optimalisatie
        if (teVerwijderenBetrokkenheden.isEmpty() && teVerwijderenGerelateerdeBetrokkenheden.isEmpty()) {
            return persoonslijst;
        }
        //transformeer de PL en filter daarbij de (gerelateerde) betrokkenheden weg
        return persoonslijst.beeldVan().filter(filter ->
                filter.addObjectFilter(metaObject -> !(teVerwijderenBetrokkenheden.contains(metaObject)
                        || teVerwijderenGerelateerdeBetrokkenheden.contains(metaObject))));
    }
}
