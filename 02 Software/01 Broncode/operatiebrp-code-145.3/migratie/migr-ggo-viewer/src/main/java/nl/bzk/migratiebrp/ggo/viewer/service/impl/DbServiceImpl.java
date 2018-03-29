/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;

import org.springframework.stereotype.Component;

/**
 * Verzorgt de databaseverbinding(en).
 */
@Component
public class DbServiceImpl implements DbService {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private BrpPersoonslijstService brpPersoonslijstService;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private LogRegelConverter logRegelConverter;

    /**
     * Zoekt de laatste BerichtLog uit de BRP.
     * @param aNummer String
     * @return lo3Persoonslijst
     */
    @Override
    public final Lo3Bericht zoekLo3Bericht(final String aNummer) {
        return brpDalService.zoekLo3PeroonslijstBerichtOpAnummer(aNummer);
    }

    /**
     * Haalt het Lg01 bericht uit de Lo3Bericht.
     * @param lo3Bericht de BerichtLog
     * @return lo3Persoonslijst
     */
    @Override
    public final Lo3Persoonslijst haalLo3PersoonslijstUitLo3Bericht(final Lo3Bericht lo3Bericht) {
        // Converteer naar een Lo3Persoonslijst
        Lo3Persoonslijst lo3Persoonslijst = null;
        if (lo3Bericht != null) {
            try {
                final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Bericht.getBerichtdata());
                final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
                lo3Persoonslijst = parser.parse(categorieen);
            } catch (final BerichtSyntaxException bse) {
                LOG.warn("Fout bij het ophalen van BerichtLog - Bericht Syntax", bse);
            }
        }
        return lo3Persoonslijst;
    }

    /**
     * Zoekt de (meest recente) berichtlog op op aNummer. Converteert de Set DB Logregels naar een List model-LogRegels
     * @param lo3Bericht de BerichtLog
     * @return de lijst met LogRegels, of null als niet gevonden
     */
    @Override
    public final List<GgoFoutRegel> haalLogRegelsUitLo3Bericht(final Lo3Bericht lo3Bericht) {
        return logRegelConverter.converteerDBNaarGgoFoutRegelList(lo3Bericht.getVoorkomens());
    }

    /**
     * Zoekt de BRP Persoonslijst op basis van ANummer.
     * @param aNummer String
     * @return brpPersoonslijst
     */
    @Override
    public final BrpPersoonslijst zoekBrpPersoonsLijst(final String aNummer) {
        return brpPersoonslijstService.zoekPersoonOpAnummer(aNummer);
    }

    @Override
    public final Persoon zoekPersoon(final String administratienummer) {
        final List<Persoon> personen = persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.INGESCHREVENE, false);

        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format("Meer dan 1 persoon gevonden voor (historisch) a-nummer '%s'.", administratienummer));
        } else {
            result = personen.get(0);
        }

        return result;
    }
}
