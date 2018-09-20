/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.ControleUtils;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor controle op het soort verbintenis ongelijk.
 */
@Component(value = "soortVerbintenisOngelijkControle")
public final class SoortVerbintenisOngelijkControle implements ToevalligeGebeurtenisControle {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        final RelatieType relatie = verzoek.getRelatie();

        final String soortRelatieVerzoek;
        if (relatie != null && relatie.getSluiting() != null && relatie.getSluiting().getSoort() != null) {
            final SoortRelatieType soortRelatie = relatie.getSluiting().getSoort().getSoort();
            soortRelatieVerzoek = soortRelatie != null ? soortRelatie.value() : null;
        } else {
            LOG.info("Er is geen soort verbintenis meegegeven in het verzoek.");
            soortRelatieVerzoek = null;
        }

        final String soortRelatieBrp;

        if (rootPersoon.getRelaties() != null && rootPersoon.getRelaties().iterator().hasNext()) {
            soortRelatieBrp = ControleUtils.geefNullSafeCodeUitEnumeratie(rootPersoon.getRelaties().iterator().next().getSoortRelatie());
        } else {
            LOG.info("Er is geen soort verbintenis opgegeven in de PL van de persoon.");
            soortRelatieBrp = null;
        }

        return !ControleUtils.equalsNullSafe(soortRelatieVerzoek, soortRelatieBrp);
    }
}
