/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.springframework.stereotype.Component;


/**
 * Filter dat bepaalt of de levering door mag gaan voor diensten van de categorie {@link SoortDienst#ATTENDERING} en waar de attenderingsCriterium
 * expressie aangeeft dat attributen van een persoon zijn gewijzigd.
 */
@Component("PersoonAttenderingFilter")
@Bedrijfsregel(Regel.R1352)
final class PersoonAttenderingFilterImpl implements Leveringfilter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger();

    @Inject
    private ExpressieService expressieService;

    private PersoonAttenderingFilterImpl() {

    }

    @Override
    public boolean magLeveren(final Persoonslijst persoon, final Populatie populatie, final Autorisatiebundel autorisatiebundel)  {
        if (autorisatiebundel.getDienst().getSoortDienst() != SoortDienst.ATTENDERING) {
            return true;
        }

        final Expressie attenderingsCriterium = expressieService.geefAttenderingsCriterium(autorisatiebundel);
        final Leveringsautorisatie la = autorisatiebundel.getLeveringsautorisatie();
        boolean magLeveren = true;
        if (attenderingsCriterium == null) {
            magLeveren = false;
            LOGGER.info("Persoon {} zal niet geleverd worden voor dienst attendering daar leveringsautorisatie {}"
                    + "({}) geen attenderingscriterium heeft.", persoon.getId(), la.getId(), la.getNaam());
        } else {
            if (autorisatiebundel.getDienst().getEffectAfnemerindicaties() == EffectAfnemerindicaties.PLAATSING
                    && isIndicatieReedsGeplaatst(persoon.getNuNuBeeld(), autorisatiebundel)) {
                LOGGER.info("Persoon {} zal niet geleverd worden voor dienst attendering met plaatsing afnemerindicatie daar leveringsautorisatie "
                        + "({}) reeds een afnemerindicatie heeft.", persoon.getId(), la.getId(), la.getNaam());
                magLeveren = false;
            }
            if (magLeveren) {
                magLeveren = expressieService.bepaalPersoonGewijzigd(persoon.beeldVan().vorigeHandeling(), persoon, attenderingsCriterium);
                if (!magLeveren) {
                    LOGGER.debug("Persoon {} zal niet geleverd worden voor dienst attendering daar er geen attributen zijn gewijzigd.",
                            persoon.getId());
                }
            }
        }

        return magLeveren;
    }

    private boolean isIndicatieReedsGeplaatst(final Persoonslijst persoonslijst, final Autorisatiebundel autorisatiebundel) {
        final Set<Afnemerindicatie> afnemerindicaties = persoonslijst.getGeldendeAfnemerindicaties();
        for (final Afnemerindicatie indicatie : afnemerindicaties) {
            final Integer leveringsautorisatieId = autorisatiebundel.getLeveringsautorisatieId();
            final boolean leveringsautorisatieIdGelijk = leveringsautorisatieId.equals(indicatie.getLeveringsAutorisatieId());
            final boolean partijGelijk = indicatie.getAfnemerCode().equals(autorisatiebundel.getPartij().getCode());
            if (leveringsautorisatieIdGelijk && partijGelijk) {
                return true;
            }
        }
        return false;
    }
}
