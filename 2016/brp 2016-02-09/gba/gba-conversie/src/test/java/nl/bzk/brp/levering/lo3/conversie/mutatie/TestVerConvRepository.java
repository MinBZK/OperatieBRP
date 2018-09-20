/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

public class TestVerConvRepository implements VerConvRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<Long, LO3Voorkomen> voorkomens = new HashMap<>();

    @Override
    public LO3Voorkomen zoekLo3VoorkomenVoorActie(final Actie actie) {
        if (actie == null) {
            return null;
        }
        final Long actieId = ((ActieModel) actie).getID();
        LOGGER.debug("zoekLo3VoorkomenVoorActie; id={}", actieId);

        return voorkomens.get(actieId);
    }

    public void reset() {
        voorkomens.clear();
    }

    public void addVoorkomen(final Long actieId, final LO3Voorkomen voorkomen) {
        voorkomens.put(actieId, voorkomen);
    }

}
