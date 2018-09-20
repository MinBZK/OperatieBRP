/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;
import nl.moderniseringgba.migratie.conversie.viewer.util.LogRegelConverter;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

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
    private LogRegelConverter logRegelConverter;

    /**
     * Zoekt de laatste BerichtLog uit de BRP.
     * 
     * @param aNummer
     *            Long
     * @return lo3Persoonslijst
     */
    @Override
    public final BerichtLog zoekBerichtLog(final Long aNummer) {
        return brpDalService.zoekBerichtLogOpAnummer(aNummer);
    }

    /**
     * Haalt het Lg01 bericht uit de BerichtLog.
     * 
     * @param berichtLog
     *            de BerichtLog
     * @return lo3Persoonslijst
     */
    @Override
    public final Lo3Persoonslijst haalLo3PersoonslijstUitBerichtLog(final BerichtLog berichtLog) {
        // Converteer naar een Lo3Persoonslijst
        Lo3Persoonslijst lo3Persoonslijst = null;

        try {
            final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(berichtLog.getBerichtData());
            final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
            lo3Persoonslijst = parser.parse(categorieen);
        } catch (final BerichtSyntaxException bse) {
            LOG.warn("Fout bij het ophalen van BerichtLog - Bericht Syntax", bse);
        }

        return lo3Persoonslijst;
    }

    /**
     * Zoekt de (meest recente) berichtlog op op aNummer. Converteert de Set DB Logregels naar een List model-LogRegels
     * 
     * @param berichtLog
     *            de BerichtLog
     * @return de lijst met LogRegels, of null als niet gevonden
     */
    @Override
    public final List<LogRegel> haalLogRegelsUitBerichtLog(final BerichtLog berichtLog) {
        return logRegelConverter.converteerNaarModelLogRegelList(berichtLog.getLogRegelSet());
    }

    /**
     * Zoekt de BRP Persoonslijst op basis van ANummer.
     * 
     * @param aNummer
     *            Long
     * @return brpPersoonslijst
     */
    @Override
    public final BrpPersoonslijst zoekBrpPersoonsLijst(final Long aNummer) {
        return brpDalService.zoekPersoonOpAnummer(aNummer);
    }
}
