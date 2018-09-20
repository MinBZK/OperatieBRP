/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.AbstractBericht;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.generated.LogRegelType;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

/**
 * Abstract sync bericht.
 */
public abstract class AbstractSyncBericht extends AbstractBericht implements SyncBericht, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Geeft de long representatie van de string waarde terug.
     * 
     * @param value
     *            De string waarde.
     * @return De long representatie van de string waarde.
     */
    protected static final Long asLong(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Long.parseLong(value);
        }
    }

    /**
     * Geeft de string representatie van de long waarde terug.
     * 
     * @param value
     *            De long waarde.
     * @return De string representatie van de long waarde.
     */
    protected static final String asString(final Long value) {
        if (value == null) {
            return null;
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Converteert een string naar een Lo3Persoonslijst.
     * 
     * @param value
     *            De string waarde.
     * @return De Lo3Persoonslijst.
     */
    protected static final Lo3Persoonslijst asLo3Persoonslijst(final String value) {
        if (value == null) {
            return null;
        } else {
            try {
                return new Lo3PersoonslijstParser().parse(Lo3Inhoud.parseInhoud(value));
            } catch (final BerichtSyntaxException e) {
                throw new IllegalStateException("SyncBericht bevat ongeldige LO3 inhoud", e);
            }
        }
    }

    /**
     * Geeft de string representatie van de Lo3Persoonslijst terug.
     * 
     * @param lo3Persoonslijst
     *            De te converteren Lo3Persoonslijst.
     * @return De string representatie van de Lo3Persoonslijst.
     */
    protected static final String asString(final Lo3Persoonslijst lo3Persoonslijst) {
        if (lo3Persoonslijst == null) {
            return null;
        } else {
            return Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(lo3Persoonslijst));
        }
    }

    /**
     * Converteert logregel objecten {@link LogRegel} naar een XML LogRegelType {@link LogRegelType}.
     * 
     * @param logRegels
     *            De te converteren logregels.
     * @return Het XML LogRegelType {@link LogRegelType} met daarin de logregels.
     */
    protected final LogRegelType asLogRegelType(final List<LogRegel> logRegels) {
        if (logRegels == null || logRegels.isEmpty()) {
            return null;
        } else {
            final List<LogRegelType.LogRegel> logRegelTypeList = new ArrayList<LogRegelType.LogRegel>();
            for (final LogRegel logRegel : logRegels) {
                final LogRegelType.LogRegel logRegelResult = new LogRegelType.LogRegel();
                logRegelResult.setCode(logRegel.getCode());
                if (logRegel.getLo3Herkomst() instanceof Lo3Herkomst) {
                    final Lo3Herkomst lo3Herkomst = (Lo3Herkomst) logRegel.getLo3Herkomst();
                    logRegelResult.setLo3Categorie(lo3Herkomst.getCategorie().getCategorieAsInt());
                    logRegelResult.setLo3Stapel(lo3Herkomst.getStapel());
                    logRegelResult.setLo3Voorkomen(lo3Herkomst.getVoorkomen());
                }
                logRegelResult.setOmschrijving(logRegel.getOmschrijving());
                logRegelResult.setSeverity(logRegel.getSeverity().getSeverity());
                logRegelResult.setType(logRegel.getType().toString());
                logRegelTypeList.add(logRegelResult);
            }
            final LogRegelType result = new LogRegelType();
            result.getLogRegel().addAll(logRegelTypeList);
            return result;
        }
    }

    /**
     * Converteert een XML LogRegelType {@link LogRegelType} naar logregel objecten {@link LogRegel}.
     * 
     * @param logRegelType
     *            Het te converteren LogRegelType {@link LogRegelType}.
     * @return Het de logregels met daarin de waarden uit het XML LogRegelType {@link LogRegelType}.
     */
    protected final List<LogRegel> asLogRegelList(final LogRegelType logRegelType) {
        if (logRegelType == null) {
            return null;
        } else {
            final List<LogRegel> result = new ArrayList<LogRegel>();
            for (final LogRegelType.LogRegel logRegel : logRegelType.getLogRegel()) {
                final Lo3Herkomst herkomst =
                        new Lo3Herkomst(Lo3CategorieEnum.valueOfCategorie(logRegel.getLo3Categorie()),
                                logRegel.getLo3Stapel(), logRegel.getLo3Voorkomen());

                final LogRegel logRegelResult =
                        new LogRegel(herkomst, LogSeverity.valueOfSeverity(logRegel.getSeverity()),
                                LogType.valueOf(logRegel.getType()), logRegel.getCode(), logRegel.getOmschrijving());

                result.add(logRegelResult);
            }
            return result;
        }
    }
}
