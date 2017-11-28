/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoonopadres;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.springframework.stereotype.Service;

/**
 * ValideerZoekCriteriaServiceImpl.
 */
@Bedrijfsregel(Regel.R2373)
@Bedrijfsregel(Regel.R2374)
@Bedrijfsregel(Regel.R2375)
@Service
final class ValideerZoekPersoonOpAdresZoekCriteriaServiceImpl implements ValideerZoekCriteriaService<ZoekPersoonOpAdresVraag> {

    private static final Set<Integer> IDENTIFICERENDE_ADRES_ELEMENTEN = Sets.newHashSet(
            Element.PERSOON_ADRES_POSTCODE.getId(),
            Element.PERSOON_ADRES_GEMEENTECODE.getId(),
            Element.PERSOON_ADRES_GEMEENTEDEEL.getId(),
            Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(),
            Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(),
            Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT.getId(),
            Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId());

    private static final Set<Integer> HUISNUMMER_ADRES_ELEMENTEN = Sets.newHashSet(
            Element.PERSOON_ADRES_HUISNUMMER.getId(),
            Element.PERSOON_ADRES_HUISLETTER.getId(),
            Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId());

    private static final Set<Integer> HUISNUMMER_ADRES_VERPLICHT_ELEMENTEN = Sets.newHashSet(
            Element.PERSOON_ADRES_POSTCODE.getId(),
            Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId(),
            Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId());

    private static final Set<Integer> STRAAT_NAAM_ELEMENTEN = Sets.newHashSet(
            Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId(),
            Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId());

    private static final Set<Integer> STRAAT_NAAM_VERPLICHT_ELEMENTEN = Sets.newHashSet(
            Element.PERSOON_ADRES_GEMEENTECODE.getId(),
            Element.PERSOON_ADRES_WOONPLAATSNAAM.getId());

    /**
     * @param zoekPersoonOpAdresVerzoek zoekPersoonOpAdresVerzoek
     * @param autorisatiebundel autorisatiebundel
     * @return meldingen
     */
    @Override
    public Set<Melding> valideerZoekCriteria(ZoekPersoonOpAdresVraag zoekPersoonOpAdresVerzoek, Autorisatiebundel autorisatiebundel) {
        final Set<Melding> meldingen = new HashSet<>();
        final ValidatieCounters validatieCounters = bepaalValidatieCounters(zoekPersoonOpAdresVerzoek);
        if (validatieCounters.identificerendeAdresElementenCount == 0) {
            meldingen.add(new Melding(Regel.R2373));
        }
        if (validatieCounters.huisnummerAdresElementenCount > 0 && validatieCounters.huisnummerAdresVerplichtElementenCount == 0) {
            meldingen.add(new Melding(Regel.R2374));
        }
        if (validatieCounters.straatnaamElementenCount > 0 && validatieCounters.straatnaamVerplichtElementenCount == 0) {
            meldingen.add(new Melding(Regel.R2375));
        }
        return meldingen;
    }

    private ValidatieCounters bepaalValidatieCounters(
            final ZoekPersoonGeneriekVerzoek bevragingVerzoek) {
        final ValidatieCounters validatieCounters = new ValidatieCounters();
        for (ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium : bevragingVerzoek.getZoekCriteriaPersoon()) {
            final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(zoekCriterium.getElementNaam());
            if (IDENTIFICERENDE_ADRES_ELEMENTEN.contains(attribuutElement.getId()) && isGeldigeZoekOptie(zoekCriterium, attribuutElement)) {
                validatieCounters.identificerendeAdresElementenCount++;
            }
            if (HUISNUMMER_ADRES_ELEMENTEN.contains(attribuutElement.getId()) && isGeldigeZoekOptie(zoekCriterium, attribuutElement)) {
                validatieCounters.huisnummerAdresElementenCount++;
            }
            if (STRAAT_NAAM_ELEMENTEN.contains(attribuutElement.getId()) && isGeldigeZoekOptie(zoekCriterium, attribuutElement)) {
                validatieCounters.straatnaamElementenCount++;
            }
            bepaalVerplichteMatchingCounters(validatieCounters, zoekCriterium, attribuutElement);
        }
        return validatieCounters;
    }

    private void bepaalVerplichteMatchingCounters(final ValidatieCounters validatieCounters,
                                                  final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium, final AttribuutElement attribuutElement) {
        if (STRAAT_NAAM_VERPLICHT_ELEMENTEN.contains(attribuutElement.getId()) && isGeldigeZoekOptie(zoekCriterium, attribuutElement)) {
            validatieCounters.straatnaamVerplichtElementenCount++;
        }
        if (HUISNUMMER_ADRES_VERPLICHT_ELEMENTEN.contains(attribuutElement.getId()) && isGeldigeZoekOptie(zoekCriterium, attribuutElement)) {
            validatieCounters.huisnummerAdresVerplichtElementenCount++;
        }
    }

    private boolean isGeldigeZoekOptie(final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium, AttribuutElement attribuutElement) {
        if (Element.PERSOON_ADRES_POSTCODE.getId() == attribuutElement.getId() || Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId() == attribuutElement
                .getId()) {
            return zoekCriterium.getZoekOptie() != Zoekoptie.LEEG;
        } else {
            return zoekCriterium.getZoekOptie() == Zoekoptie.EXACT || zoekCriterium.getZoekOptie() == Zoekoptie.KLEIN;
        }
    }

    /**
     * ValidatieCounters.
     */
    private static final class ValidatieCounters {
        private int identificerendeAdresElementenCount;
        private int huisnummerAdresElementenCount;
        private int huisnummerAdresVerplichtElementenCount;
        private int straatnaamElementenCount;
        private int straatnaamVerplichtElementenCount;
    }
}
