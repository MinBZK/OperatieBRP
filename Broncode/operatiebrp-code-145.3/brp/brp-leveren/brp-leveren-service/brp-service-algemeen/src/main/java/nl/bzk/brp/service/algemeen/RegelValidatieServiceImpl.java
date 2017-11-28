/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Melding;
import org.springframework.stereotype.Service;

/**
 * RegelValidatieServiceImpl.
 */
@Service
final class RegelValidatieServiceImpl implements RegelValidatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Enum voor de te volgen strategie wanneer er fouten optreden tijdens validatie.
     */
    private enum Strategie {

        /**
         * Valideert de regels zonder een Exceptie te gooien.
         */
        DOE_NIETS_BIJ_MELDING,
        /**
         * Gooit direct een exceptie bij de eerste melding.
         */
        DIRECT_FOUT,
        /**
         * Verzamelt alle meldigen en combineert deze in één foutmelding.
         */
        VERZAMEL_EN_GOOI_FOUT

    }

    @Override
    public void valideer(final Iterable<RegelValidatie> regels) throws StapMeldingException {
        valideer(regels, Strategie.VERZAMEL_EN_GOOI_FOUT);
    }

    @Override
    public void valideerFailfast(final Iterable<RegelValidatie> regels) throws StapMeldingException {
        valideer(regels, Strategie.DIRECT_FOUT);
    }

    @Override
    public List<Melding> valideerEnGeefMeldingen(final Iterable<RegelValidatie> regels) {
        try {
            return valideer(regels, Strategie.DOE_NIETS_BIJ_MELDING);
        } catch (final StapMeldingException e) {
            LOGGER.debug("Fout bij uitvoeren regelvalidatie : ", e);
            // niet relevant
            return Collections.emptyList();
        }
    }

    private List<Melding> valideer(final Iterable<RegelValidatie> regels, final Strategie strategie) throws StapMeldingException {
        final List<Melding> meldingen = new LinkedList<>();
        for (final RegelValidatie regel : regels) {
            final Melding melding = regel.valideer();
            if (melding != null) {
                if (strategie == Strategie.DIRECT_FOUT) {
                    throw new StapMeldingException(melding);
                }
                meldingen.add(melding);
            }
        }
        if (strategie == Strategie.VERZAMEL_EN_GOOI_FOUT && !meldingen.isEmpty()) {
            throw new StapMeldingException(meldingen);
        }
        return meldingen;
    }
}
