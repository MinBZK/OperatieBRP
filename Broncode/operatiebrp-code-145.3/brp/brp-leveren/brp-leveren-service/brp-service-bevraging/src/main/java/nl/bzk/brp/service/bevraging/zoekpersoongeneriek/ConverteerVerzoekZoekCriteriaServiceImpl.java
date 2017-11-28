/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.algemeen.StapException;
import org.springframework.stereotype.Service;

/**
 * ConverteerVerzoekZoekCriteriaServiceImpl.
 */
@Service
final class ConverteerVerzoekZoekCriteriaServiceImpl implements ConverteerVerzoekZoekCriteriaService {

    private static final EnumMap<Element, SoortIndicatieLookup> INDICATIE_LOOKUP_MAP = new EnumMap<>(Element.class);

    static {
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_SOORTNAAM,
                        SoortIndicatie.BEHANDELD_ALS_NEDERLANDER));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_STAATLOOS,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_STAATLOOS_SOORTNAAM,
                        SoortIndicatie.STAATLOOS));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_SOORTNAAM,
                        SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_SOORTNAAM,
                        SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_ONDERCURATELE,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM,
                        SoortIndicatie.ONDER_CURATELE));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM,
                        SoortIndicatie.DERDE_HEEFT_GEZAG));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_SOORTNAAM,
                        SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT,
                new SoortIndicatieLookup(
                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_SOORTNAAM,
                        SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT));
        INDICATIE_LOOKUP_MAP.put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG,
                new SoortIndicatieLookup(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_SOORTNAAM,
                        SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG));
    }

    @Inject
    private ZoekCriteriaWaardeConverteerService zoekCriteriaConverteerService;

    private ConverteerVerzoekZoekCriteriaServiceImpl() {

    }

    @Override
    public Set<ZoekCriterium> maakZoekCriteria(final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> criteria) throws StapException {
        final Set<ZoekCriterium> zoekCriteria = new HashSet<>();
        for (ZoekPersoonGeneriekVerzoek.ZoekCriteria criterium : criteria) {
            final ZoekCriterium zoekCriterium = maakZoekCriterium(criterium);
            zoekCriteria.add(zoekCriterium);
            zetIndicatieSoort(zoekCriterium);
        }
        return zoekCriteria;
    }

    private void zetIndicatieSoort(ZoekCriterium zoekCriterium) {
        final ObjectElement objectElement = zoekCriterium.getElement().getGroep().getObjectElement();

        if (objectElement.isAliasVan(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE))) {
            final Element indicatieElement = objectElement.getElement();
            final SoortIndicatieLookup soortIndicatieAttrElement = INDICATIE_LOOKUP_MAP.get(indicatieElement);
            final ZoekCriterium
                    zoekCriteriumSoortIndicatie =
                    new ZoekCriterium(soortIndicatieAttrElement.attribuutElement,
                            Zoekoptie.EXACT, soortIndicatieAttrElement.soortIndicatie.getId());
            zoekCriterium.setAdditioneel(zoekCriteriumSoortIndicatie);
        }
    }

    private ZoekCriterium maakZoekCriterium(final ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria) throws StapException {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(criteria.getElementNaam());
        final Object waarde = zoekCriteriaConverteerService.converteerWaarde(attribuutElement, criteria.getWaarde());
        ZoekCriterium zoekCriteriumOf = null;
        if (criteria.getOf() != null) {
            zoekCriteriumOf = maakZoekCriterium(criteria.getOf());
        }
        return new ZoekCriterium(attribuutElement, criteria.getZoekOptie(), waarde, zoekCriteriumOf);
    }

    private static class SoortIndicatieLookup {
        private AttribuutElement attribuutElement;
        private SoortIndicatie soortIndicatie;

        SoortIndicatieLookup(Element element, SoortIndicatie soortIndicatie) {
            this.attribuutElement = ElementHelper.getAttribuutElement(element);
            this.soortIndicatie = soortIndicatie;
        }
    }
}
