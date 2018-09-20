/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoLogType;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.NaamUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Melding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3SoortMelding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import org.springframework.stereotype.Component;

/**
 * Converteer LogRegel objecten naar GgoFoutRegel objecten.
 */
@Component
public class LogRegelConverter {
    /**
     * Converteer een lijst met DB LogRegels naar een lijst met GgoFoutRegels.
     * 
     * @param voorkomens
     *            de LogRegels.
     * @return de lijst met GgoFoutRegel.
     */
    public final List<GgoFoutRegel> converteerDBNaarGgoFoutRegelList(final Set<Lo3Voorkomen> voorkomens) {

        final List<GgoFoutRegel> out = new ArrayList<>();
        for (final Lo3Voorkomen voorkomen : voorkomens) {
            for (final Lo3Melding melding : voorkomen.getMeldingen()) {
                out.add(converteerDBNaarGgoFoutRegel(melding));
            }
        }
        return out;
    }

    /**
     * Converteer een Lo3Melding naar een GgoFoutRegel.
     * 
     * @param melding
     *            de Lo3Melding.
     * @return de GgoFoutRegel.
     */
    public final GgoFoutRegel converteerDBNaarGgoFoutRegel(final Lo3Melding melding) {

        final Lo3Voorkomen voorkomen = melding.getVoorkomen();
        GgoVoorkomen herkomst = null;

        if (voorkomen.getCategorie() != null || voorkomen.getStapelvolgnummer() != null || voorkomen.getVoorkomenvolgnummer() != null) {
            herkomst = new GgoVoorkomen();
            if (voorkomen.getCategorie() != null) {
                final int catNr = Integer.parseInt(voorkomen.getCategorie());
                herkomst.setCategorieNr(catNr);
                herkomst.setLabel(NaamUtil.createLo3CategorieLabel(catNr));
            }
            if (voorkomen.getStapelvolgnummer() != null) {
                herkomst.setStapelNr(voorkomen.getStapelvolgnummer());
            }
            if (voorkomen.getVoorkomenvolgnummer() != null) {
                herkomst.setVoorkomenNr(voorkomen.getVoorkomenvolgnummer());
            }
        }

        return new GgoFoutRegel(
            herkomst,
            LogSeverity.valueOf(melding.getLogSeverity().toString()),
            GgoLogType.valueOf(melding.getSoortMelding().getCategorieMelding().toString()),
            melding.getSoortMelding().getCode(),
            melding.getSoortMelding().getOmschrijving());
    }

    /**
     * Converteert de Model LogRegels naar GgoFoutRegels.
     * 
     * @param regels
     *            De logRegels.
     * @return Omgezette GgoFoutRegels.
     */
    public final List<GgoFoutRegel> converteerModelNaarGgoFoutRegelList(final Set<LogRegel> regels) {
        final List<GgoFoutRegel> out = new ArrayList<>();
        for (final LogRegel regel : regels) {
            out.add(converteerModelNaarGgoFoutRegel(regel));
        }
        return out;
    }

    /**
     * Converteer een model LogRegel naar een GgoFoutRegel.
     * 
     * @param logRegel
     *            de LogRegel.
     * @return de GgoFoutRegel.
     */
    public final GgoFoutRegel converteerModelNaarGgoFoutRegel(final LogRegel logRegel) {
        // lo3Herkomst kan niet null zijn in LogRegel
        final GgoVoorkomen herkomst = new GgoVoorkomen();
        final int catNr = logRegel.getLo3Herkomst().getCategorie().getCategorieAsInt();
        herkomst.setCategorieNr(catNr);
        herkomst.setStapelNr(logRegel.getLo3Herkomst().getStapel());
        herkomst.setVoorkomenNr(logRegel.getLo3Herkomst().getVoorkomen());
        herkomst.setLabel(NaamUtil.createLo3CategorieLabel(catNr));

        final Lo3SoortMelding lo3SoortMelding = Lo3SoortMelding.valueOf(logRegel.getSoortMeldingCode().toString());

        return new GgoFoutRegel(
            herkomst,
            LogSeverity.valueOfSeverity(logRegel.getSeverity().getSeverity()),
            GgoLogType.valueOf(lo3SoortMelding.getCategorieMelding().toString()),
            lo3SoortMelding.getCode(),
            lo3SoortMelding.getOmschrijving());
    }
}
