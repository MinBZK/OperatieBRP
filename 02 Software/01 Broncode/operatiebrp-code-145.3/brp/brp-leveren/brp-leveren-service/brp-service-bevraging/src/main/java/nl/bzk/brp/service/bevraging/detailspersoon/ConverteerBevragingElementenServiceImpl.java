/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.service.cache.GeldigeAttributenElementenCache;
import org.springframework.stereotype.Service;

/**
 * ConverteerBevragingElementenServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2215)
@Bedrijfsregel(Regel.R2216)
@Bedrijfsregel(Regel.R2400)
final class ConverteerBevragingElementenServiceImpl implements ConverteerBevragingElementenService {

    @Inject
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;

    private ConverteerBevragingElementenServiceImpl() {
    }

    @Override
    public Set<AttribuutElement> converteerBevragingElementen(final Set<String> bevragingElementen, final Autorisatiebundel autorisatiebundel) throws
            ConverteerBevragingVerzoekElementException {
        final Set<AttribuutElement> attribuutElementen = new HashSet<>(bevragingElementen.size());
        for (String bevragingElement : bevragingElementen) {
            final AttribuutElement attribuutElement = geefAttribuutElement(bevragingElement);
            attribuutElementen.add(attribuutElement);
        }

        if (!dienstIsGeautoriseerdVoorScopingElementen(autorisatiebundel.getDienst(), attribuutElementen)) {
            throw new ConverteerBevragingVerzoekElementException(new Melding(Regel.R2215));
        }
        return attribuutElementen;
    }

    private AttribuutElement geefAttribuutElement(final String bevragingElement) throws ConverteerBevragingVerzoekElementException {
        final AttribuutElement attribuutElement;
        try {
            final ElementObject elementObject = ElementHelper.getElement(bevragingElement);
            if (!(elementObject instanceof AttribuutElement)) {
                throw new ConverteerBevragingVerzoekElementException(new Melding(Regel.R2216));
            }
            attribuutElement = (AttribuutElement) elementObject;
            if (attribuutElement.getGroep().isVerantwoordingsgroep() || attribuutElement.getGroep().isOnderzoeksgroep()) {
                throw new ConverteerBevragingVerzoekElementException(new Melding(Regel.R2400));
            }
            if (!(geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(attribuutElement))) {
                throw new ConverteerBevragingVerzoekElementException(new Melding(Regel.R2216));
            }
        } catch (IllegalStateException e) {
            throw new ConverteerBevragingVerzoekElementException(new Melding(Regel.R2216), e);
        }
        return attribuutElement;
    }

    private boolean dienstIsGeautoriseerdVoorScopingElementen(final Dienst dienst, final Set<AttribuutElement> attribuutElementen) {
        if (!attribuutElementen.isEmpty() && dienst != null && dienst.getDienstbundel() != null) {
            final Set<AttribuutElement> geautoriseerdeElementen = new HashSet<>();
            for (DienstbundelGroep dienstbundelGroep : dienst.getDienstbundel().getDienstbundelGroepSet()) {
                for (DienstbundelGroepAttribuut dienstbundelGroepAttribuut : dienstbundelGroep.getDienstbundelGroepAttribuutSet()) {
                    geautoriseerdeElementen.add(ElementHelper.getAttribuutElement(dienstbundelGroepAttribuut.getAttribuut().getId()));
                }
            }
            return geautoriseerdeElementen.containsAll(attribuutElementen);
        }
        return true;
    }

}
