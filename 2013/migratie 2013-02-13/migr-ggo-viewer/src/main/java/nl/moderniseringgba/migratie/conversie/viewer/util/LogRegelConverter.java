/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.springframework.stereotype.Component;

/**
 * Converteer DB LogRegel objecten naar Model LogRegel objecten.
 */
@Component
public class LogRegelConverter {
    /**
     * Converteer een Set<db LogRegel> naar een List<model LogRegel>.
     * 
     * @param in
     *            de Set<db LogRegel>
     * @return de List<model LogRegel>
     */
    public final List<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel> converteerNaarModelLogRegelList(
            final Set<nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel> in) {

        final List<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel> out =
                new ArrayList<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel>();

        for (final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel regel : in) {
            out.add(converteerNaarModelLogRegel(regel));
        }

        return out;
    }

    /**
     * Converteer een db LogRegel naar een model LogRegel.
     * 
     * @param in
     *            de model LogRegel
     * @return de db LogRegel
     */
    public final nl.moderniseringgba.migratie.conversie.model.logging.LogRegel converteerNaarModelLogRegel(
            final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel in) {

        Lo3Herkomst lo3Herkomst = null;

        if (in.getCategorie() != null || in.getStapel() != null || in.getVoorkomen() != null) {
            lo3Herkomst =
                    new Lo3Herkomst(Lo3CategorieEnum.valueOfCategorie(in.getCategorie()), in.getStapel(),
                            in.getVoorkomen());
        }

        return new nl.moderniseringgba.migratie.conversie.model.logging.LogRegel(lo3Herkomst,
                LogSeverity.valueOfSeverity(in.getSeverity()), LogType.valueOf(in.getType()), in.getCode(),
                in.getOmschrijving());
    }
}
