/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import com.google.common.primitives.Booleans;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.persoonselectie.SelecteerPersoonService;
import nl.bzk.brp.service.algemeen.util.ANummerValidator;
import nl.bzk.brp.service.algemeen.util.BsnValidator;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link BevragingSelecteerPersoonService}.
 */
@Bedrijfsregel(Regel.R1585)
@Bedrijfsregel(Regel.R1587)
@Bedrijfsregel(Regel.R1833)
@Bedrijfsregel(Regel.R2192)
@Service
public final class BevragingSelecteerPersoonServiceImpl implements BevragingSelecteerPersoonService {

    @Inject
    private SelecteerPersoonService selecteerPersoonService;
    @Inject
    private ObjectSleutelService objectSleutelService;

    private BevragingSelecteerPersoonServiceImpl() {
    }

    @Override
    public Persoonslijst selecteerPersoon(final String bsn, final String anummer, final String persoonId, final String partijCode,
                                          final Autorisatiebundel autorisatiebundel) throws StapMeldingException {
        if (!controleerCorrectGevuld(bsn, anummer, persoonId)) {
            throw new StapMeldingException(Regel.R2192);
        }
        Persoonslijst persoonslijst = null;
        if (bsn != null) {
            persoonslijst = selecteerPersoonMetBsn(bsn, autorisatiebundel);
        } else if (anummer != null) {
            persoonslijst = selecteerPersoonMetAnr(anummer, autorisatiebundel);
        } else if (persoonId != null) {
            persoonslijst = selecteerPersoonMetId(persoonId, autorisatiebundel);
        }
        return persoonslijst;
    }

    private Persoonslijst selecteerPersoonMetBsn(final String bsn, final Autorisatiebundel autorisatiebundel) throws StapMeldingException {
        if (!BsnValidator.isGeldigeBsn(bsn)) {
            throw new StapMeldingException(Regel.R1587);
        } else {
            return selecteerPersoonService.selecteerPersoonMetBsn(bsn, autorisatiebundel);
        }
    }

    private Persoonslijst selecteerPersoonMetAnr(final String anummer, final Autorisatiebundel autorisatiebundel) throws StapMeldingException {
        if (!ANummerValidator.isGeldigANummer(anummer)) {
            throw new StapMeldingException(Regel.R1585);
        } else {
            return selecteerPersoonService.selecteerPersoonMetANummer(anummer, autorisatiebundel);
        }
    }

    private Persoonslijst selecteerPersoonMetId(final String persoonId, final Autorisatiebundel autorisatiebundel)
            throws StapMeldingException {
        try {
            final ObjectSleutel persoonObjectSleutel = objectSleutelService.maakPersoonObjectSleutel(persoonId);
            final Persoonslijst result = selecteerPersoonService.selecteerPersoonMetId(persoonObjectSleutel.getDatabaseId(), autorisatiebundel);
            final long versieInResultaat = result.getModelIndex().geefAttributen(Element.PERSOON_VERSIE_LOCK).iterator().next().getWaarde();
            if (versieInResultaat == persoonObjectSleutel.getVersie()) {
                return result;
            } else {
                throw new StapMeldingException(Regel.R1833);
            }
        } catch (OngeldigeObjectSleutelException e) {
            throw new StapMeldingException(Regel.R1833, e);
        }
    }

    private boolean controleerCorrectGevuld(final String bsn, final String anummer, final String persoonId) {
        final int booleanCount = Booleans.countTrue(bsn != null, anummer != null, persoonId != null);
        return booleanCount == 1;
    }

}
