/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.sync.generated.JaType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LogRegelType;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Abstract sync bericht.
 */
public abstract class AbstractSyncBericht extends AbstractBericht implements SyncBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private final String berichtType;
    private final String startCyclus;

    /**
     * Constructor.
     *
     * @param berichtType
     *            berichtType
     */
    public AbstractSyncBericht(final String berichtType) {
        this(berichtType, null);
    }

    /**
     * Constructor.
     *
     * @param berichtType
     *            berichtType
     * @param startCyclus
     *            te starten cyclus
     */
    public AbstractSyncBericht(final String berichtType, final String startCyclus) {
        this.berichtType = berichtType;
        this.startCyclus = startCyclus;

    }

    @Override
    public final String getBerichtType() {
        return berichtType;
    }

    @Override
    public final String getStartCyclus() {
        return startCyclus;
    }

    /**
     * Geeft de Boolean representatie van de JaType waarde terug.
     *
     * @param value
     *            De JaType waarde.
     * @return De Boolean representatie van de JaType waarde.
     */
    @SuppressFBWarnings(value = "NP_BOOLEAN_RETURN_NULL", justification = "Input die vertaald wordt kan ook NULL zijn")
    protected static Boolean asBoolean(final JaType value) {
        return value == JaType.J ? Boolean.TRUE : null;
    }

    /**
     * Geeft de JaType representatie van de Boolean waarde terug.
     *
     * @param value
     *            De Boolean waarde.
     * @return De JaType representatie van de Boolean waarde.
     */
    protected static JaType asJaType(final Boolean value) {
        return Boolean.TRUE.equals(value) ? JaType.J : null;
    }

    /**
     * Geeft de string representatie van de long waarde terug.
     *
     * @param value
     *            De long waarde.
     * @return De string representatie van de long waarde.
     */
    protected static String asString(final Integer value) {
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
    protected static Lo3Persoonslijst asLo3Persoonslijst(final String value) {
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
    protected static String asString(final Lo3Persoonslijst lo3Persoonslijst) {
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
    protected final LogRegelType asLogRegelType(final Set<LogRegel> logRegels) {
        if (logRegels == null || logRegels.isEmpty()) {
            return null;
        } else {
            final List<LogRegelType.LogRegel> logRegelTypeList = new ArrayList<>();
            for (final LogRegel logRegel : logRegels) {
                final LogRegelType.LogRegel logRegelResult = new LogRegelType.LogRegel();
                logRegelResult.setCode(logRegel.getSoortMeldingCode().toString());
                if (logRegel.getLo3Herkomst() != null) {
                    final Lo3Herkomst lo3Herkomst = logRegel.getLo3Herkomst();
                    logRegelResult.setLo3Categorie(lo3Herkomst.getCategorie().getCategorieAsInt());
                    logRegelResult.setLo3Stapel(lo3Herkomst.getStapel());
                    logRegelResult.setLo3Voorkomen(lo3Herkomst.getVoorkomen());
                }
                logRegelResult.setSeverity(logRegel.getSeverity().getSeverity());
                logRegelResult.setLo3Element(logRegel.getLo3ElementNummer() == null ? null : logRegel.getLo3ElementNummer().getElementNummer());
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
            final List<LogRegel> result = new ArrayList<>();
            for (final LogRegelType.LogRegel logRegel : logRegelType.getLogRegel()) {
                final Lo3Herkomst herkomst =
                        new Lo3Herkomst(Lo3CategorieEnum.getLO3Categorie(logRegel.getLo3Categorie()), logRegel.getLo3Stapel(), logRegel.getLo3Voorkomen());

                final Lo3ElementEnum lo3Element;
                try {
                    lo3Element = logRegel.getLo3Element() == null ? null : Lo3ElementEnum.getLO3Element(logRegel.getLo3Element());
                } catch (final Lo3SyntaxException e) {
                    throw new IllegalArgumentException(logRegel.getLo3Element() + " is niet een geldige lo3 element code", e);
                }
                final LogRegel logRegelResult =
                        new LogRegel(
                            herkomst,
                            LogSeverity.valueOfSeverity(logRegel.getSeverity()),
                            SoortMeldingCode.valueOf(logRegel.getCode()),
                            lo3Element);
                result.add(logRegelResult);
            }
            return result;
        }
    }

}
