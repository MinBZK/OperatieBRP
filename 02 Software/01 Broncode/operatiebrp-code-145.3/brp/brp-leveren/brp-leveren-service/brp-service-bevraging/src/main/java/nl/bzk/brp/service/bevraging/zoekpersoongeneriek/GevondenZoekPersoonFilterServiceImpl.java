/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.springframework.stereotype.Service;

/**
 * GevondenZoekPersoonFilterServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2287)
@Bedrijfsregel(Regel.R1983)
final class GevondenZoekPersoonFilterServiceImpl implements GevondenZoekPersoonFilterService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ExpressieService expressieService;

    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    private GevondenZoekPersoonFilterServiceImpl() {

    }

    @Override
    public List<Persoonslijst> filterPersoonslijst(final Autorisatiebundel autorisatiebundel,
                                                   final List<Persoonslijst> persoonsgegevens) {
        final List<Persoonslijst> gefilterdePersoonsGegevens = new ArrayList<>();
        final Partij partij = autorisatiebundel.getPartij();
        for (Persoonslijst persoonsgegeven : persoonsgegevens) {
            final Persoonslijst persoonNu = persoonsgegeven.getNuNuBeeld();
            final boolean valide = controleerPersoon(persoonNu, autorisatiebundel, partij);
            if (!valide) {
                continue;
            }
            gefilterdePersoonsGegevens.add(persoonsgegeven);
        }
        return gefilterdePersoonsGegevens;
    }

    private boolean controleerPersoon(final Persoonslijst persoonslijst, final Autorisatiebundel autorisatiebundel, final Partij partij) {
        try {
            final Persoonslijst persoonNu = persoonslijst.getNuNuBeeld();
            final Expressie expressieParsed = expressieService.geefPopulatiebeperking(autorisatiebundel);
            final boolean expressieResultaat = expressieService.evalueer(expressieParsed, persoonNu);
            return expressieResultaat && !verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoonNu, partij);
        } catch (ExpressieException expressieExceptie) {
            LOGGER.error("Er is een fout opgetreden tijdens de populatiebepaling van de persoon.", expressieExceptie);
            return false;
        }
    }
}
