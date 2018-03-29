/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoon;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import org.springframework.stereotype.Service;

/**
 * ValideerZoekCriteriaServiceImpl.
 */
@Bedrijfsregel(Regel.R2288)
@Service
final class ValideerZoekPersoonZoekCriteriaServiceImpl implements ValideerZoekCriteriaService<ZoekPersoonVraag> {

    /**
     * @param bevragingVerzoek bevragingVerzoek
     * @param autorisatiebundel autorisatiebundel
     * @return meldingen
     */
    @Override
    public Set<Melding> valideerZoekCriteria(final ZoekPersoonVraag bevragingVerzoek, final Autorisatiebundel autorisatiebundel) {
        final Set<Melding> meldingen = new HashSet<>();
        int adresZoekCriteria = 0;
        for (ZoekPersoonVerzoek.ZoekCriteria zoekCriterium : bevragingVerzoek.getZoekCriteriaPersoon()) {
            if (isAdresElement(zoekCriterium)) {
                adresZoekCriteria++;
            }
        }
        if (bevragingVerzoek.getZoekCriteriaPersoon().size() == adresZoekCriteria) {
            meldingen.add(new Melding(Regel.R2288));
        }

        return meldingen;
    }


    private boolean isAdresElement(final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium) {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(zoekCriterium.getElementNaam());
        final ObjectElement objectElement = ElementHelper.getObjectElement(attribuutElement.getObjectType());

        return Element.PERSOON_ADRES == Element.parseId(objectElement.getTypeObjectElement().getId());
    }
}
