/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Converteer LO3Rubrieken naar BRP groep en attributen.
 */
@Component
final class RubriekConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;
    @Inject
    private RubriekMap rubriekMap;

    private RubriekConversie() {
    }

    void converteerRubrieken(final Dienstbundel dienstbundel, final Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet) {
        LOGGER.info("Start omzetten Rubrieken");
        final Map<Element, DienstbundelGroep> groepMap = Maps.newHashMap();
        for (DienstbundelLo3Rubriek lo3rubriek : dienstbundelLo3RubriekSet) {
            final String rubrieknaam = lo3rubriek.getLo3Rubriek().getNaam();
            converteerRubriek(dienstbundel, groepMap, rubrieknaam);
        }
        pasUitzonderingregelsToe(dienstbundel, groepMap);
        for (DienstbundelGroep dienstbundelGroep : groepMap.values()) {
            entityManager.merge(dienstbundelGroep);
        }
    }

    private void converteerRubriek(final Dienstbundel dienstbundel, final Map<Element, DienstbundelGroep> groepMap, final String rubrieknaam) {
        LOGGER.debug("converteer rubriek {}", rubrieknaam);
        final String[] rubrieknaamSplit = StringUtils.split(rubrieknaam, ".");
        final int rubrieknaamX = Integer.parseInt(rubrieknaamSplit[0]);
        final int rubrieknaamY = Integer.parseInt(rubrieknaamSplit[1]);
        final int rubrieknaamZ = Integer.parseInt(rubrieknaamSplit[2]);
        final int categorie = rubrieknaamX > 50 ? rubrieknaamX - 50 : rubrieknaamX;
        final boolean rubriekIsMaterieel = rubrieknaamX > 50;
        final boolean rubriekMetVerantwoording = rubrieknaamY == 72 || rubrieknaamY == 81 || rubrieknaamY == 82 || rubrieknaamY == 86;
        LOGGER.debug("materieel: {}", rubriekIsMaterieel);
        LOGGER.debug("rubriekMetVerantwoording: {}", rubriekMetVerantwoording);

        if (rubriekMetVerantwoording) {
            rubriekMap.getGroepen(categorie).forEach(groepElement -> {
                DienstbundelGroep dienstbundelGroep = groepMap.get(groepElement.getElement());
                if (dienstbundelGroep == null) {
                    dienstbundelGroep = new DienstbundelGroep(dienstbundel, groepElement.getElement(), false, rubriekIsMaterieel, true);
                    groepMap.put(dienstbundelGroep.getGroep(), dienstbundelGroep);
                    LOGGER.debug("+G[{}] tbv verantwoordingscategorie", dienstbundelGroep.getGroep().getNaam());
                    voegIdentiteitgroepToe(dienstbundel, groepMap, groepElement);
                }
                if (rubriekIsMaterieel) {
                    dienstbundelGroep.setIndicatieMaterieleHistorie(true);
                }
                dienstbundelGroep.setIndicatieVerantwoording(true);
            });
        } else {
            final String queryrubriek = String.format("%02d.%02d.%02d", categorie, rubrieknaamY, rubrieknaamZ);
            final Collection<AttribuutElement> attributen = rubriekMap.getAttributen(queryrubriek);
            for (AttribuutElement attribuutElement : attributen) {
                final GroepElement groep = attribuutElement.getGroep();
                DienstbundelGroep dienstbundelGroep = groepMap.get(groep.getElement());
                if (dienstbundelGroep == null) {
                    dienstbundelGroep = new DienstbundelGroep(dienstbundel, groep.getElement(), false, rubriekIsMaterieel, false);
                    groepMap.put(groep.getElement(), dienstbundelGroep);
                    LOGGER.debug("+G[{}]", groep.getElement().getNaam());
                    voegIdentiteitgroepToe(dienstbundel, groepMap, groep);
                }
                if (rubriekIsMaterieel) {
                    dienstbundelGroep.setIndicatieMaterieleHistorie(true);
                }
                voegAttribuutToe(groepMap, attribuutElement.getElement());
            }
        }
    }

    private void pasUitzonderingregelsToe(final Dienstbundel dienstbundel, final Map<Element, DienstbundelGroep> groepMap) {
        uitzonderingPersoonSoortCode(dienstbundel, groepMap);
        uitzonderingBijhoudingsaard(dienstbundel, groepMap);
        //uitzondering: Persoon.Adres.IndicatiePersoonAangetroffenOpAdres
        // opnemen als er iets voorkomt binnen Persoon.Adres
        voegAttribuutToe(groepMap, Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES);
        //uitzondering: GerelateerdeKind.Persoon.SoortCode opnemen als
        // er iets voorkomt binnen GerelateerdeKind.Persoon
        voegAttribuutToe(groepMap, Element.GERELATEERDEKIND_PERSOON_SOORTCODE);
        //uitzondering: GerelateerdeOuder.Persoon.SoortCode opnemen als
        // er iets voorkomt binnen GerelateerdeOuder.Persoon
        voegAttribuutToe(groepMap, Element.GERELATEERDEOUDER_PERSOON_SOORTCODE);
        //uitzondering: GerelateerdeHuwelijkspartner.Persoon.SoortCode
        // opnemen als er iets voorkomt binnen GerelateerdeHuwelijkspartner.Persoon
        voegAttribuutToe(groepMap, Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SOORTCODE);
        //uitzondering: GerelateerdeGeregistreerdePartner.Persoon.SoortCode opnemen
        // als er iets voorkomt binnen GerelateerdeGeregistreerdePartner.Persoon
        voegAttribuutToe(groepMap, Element.GERELATEERDEKIND_PERSOON_SOORTCODE);
        //uitzondering: voeg onderzoeksgroepen toe
        uitzonderingOnderzoek(dienstbundel, groepMap);
    }

    private void uitzonderingOnderzoek(final Dienstbundel dienstbundel, final Map<Element, DienstbundelGroep> groepMap) {
        LOGGER.debug("Uitzondering mbt onderzoek");
        final Element[] elements = {
                Element.ONDERZOEK_IDENTITEIT,
                Element.ONDERZOEK_STANDAARD,
                Element.GEGEVENINONDERZOEK_IDENTITEIT
        };
        Arrays.stream(elements).forEach(groep -> {
            if (!groepMap.containsKey(groep)) {
                groepMap.put(groep, new DienstbundelGroep(dienstbundel, groep, false, false, false));
                LOGGER.debug("+G[{}]", groep.getNaam());
            }
            for (AttribuutElement attribuutElement : ElementHelper.getGroepElement(groep).getAttributenInGroep()) {
                switch (attribuutElement.getAutorisatie()) {
                    case OPTIONEEL:
                    case VERPLICHT:
                    case AANBEVOLEN:
                        voegAttribuutToe(groepMap, attribuutElement.getElement());
                        break;
                    default:
                        //helemaal niets
                }
            }
        });
    }

    private void voegIdentiteitgroepToe(final Dienstbundel dienstbundel,
                                        final Map<Element, DienstbundelGroep> groepMap, final GroepElement groep) {
        if (!groep.isIdentiteitGroep()) {
            //identiteitgroepen nemen we altijd op
            final Optional<GroepElement> optionalIdenteit = groep.getObjectElement().getGroepen()
                    .stream().filter(GroepElement::isIdentiteitGroep).findFirst();
            if (optionalIdenteit.isPresent() && !groepMap.containsKey(optionalIdenteit.get().getElement())) {
                //voor nu alle vlaggen op true
                groepMap.put(optionalIdenteit.get().getElement(), new DienstbundelGroep(dienstbundel,
                        optionalIdenteit.get().getElement(), true, true, true));
                LOGGER.debug("+G[{}] toegevoegd als afgeleide identiteit van object [{}]",
                        optionalIdenteit.get().getNaam(), groep.getObjectElement().getNaam());
            }
        }
    }

    /**
     * uitzondering: Persoon.SoortCode altijd opnemen
     */
    private void uitzonderingPersoonSoortCode(final Dienstbundel dienstbundel, final Map<Element, DienstbundelGroep> groepMap) {
        if (groepMap.get(Element.PERSOON_IDENTITEIT) == null) {
            //voor nu alle vlaggen op true
            final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(dienstbundel, Element.PERSOON_IDENTITEIT, true, true, true);
            groepMap.put(dienstbundelGroep.getGroep(), dienstbundelGroep);
            LOGGER.debug("+G[{}] tbv uitzondering Persoon.SoortCode", dienstbundelGroep.getGroep().getNaam());
        }
        voegAttribuutToe(groepMap, Element.PERSOON_SOORTCODE);
    }

    /**
     * uitzondering: Persoon.Bijhouding.BijhoudingsaardCode en Persoon.Bijhouding.NadereBijhoudingsaardCode altijd opnemen
     */
    private void uitzonderingBijhoudingsaard(final Dienstbundel dienstbundel, final Map<Element, DienstbundelGroep> groepMap) {
        DienstbundelGroep groep = groepMap.get(Element.PERSOON_BIJHOUDING);
        if (groep == null) {
            //voor nu alle vlaggen op true
            groep = new DienstbundelGroep(dienstbundel, Element.PERSOON_BIJHOUDING, true, true, true);
            groepMap.put(groep.getGroep(), groep);
            LOGGER.debug("+G[{}] tbv uitzondering bijhoudingsaard", groep.getGroep().getNaam());
        }
        final Element[] elements = {
                Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE,
                Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE
        };
        Arrays.stream(elements).forEach(element -> voegAttribuutToe(groepMap, element));
    }

    private void voegAttribuutToe(final Map<Element, DienstbundelGroep> groepMap, Element attribuutElement) {
        DienstbundelGroep groep = groepMap.get(attribuutElement.getGroep());
        if (groep != null && !groep.getDienstbundelGroepAttribuutSet().stream()
                .anyMatch(dienstbundelGroepAttribuut -> dienstbundelGroepAttribuut.getAttribuut() == attribuutElement)) {
            final DienstbundelGroepAttribuut attribuut = new DienstbundelGroepAttribuut(groep, attribuutElement);
            groep.getDienstbundelGroepAttribuutSet().add(attribuut);
            LOGGER.debug("+A[{}]", attribuutElement.getNaam());
        }
    }
}
