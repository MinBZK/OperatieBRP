/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.ControleUtils;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor inhoudelijke controle bij acties op een huwelijk.
 */
@Component(value = "huwelijkControle")
public final class HuwelijkControle implements ToevalligeGebeurtenisControle {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        final RelatieSluitingGroepType relatieVerzoek = geefRelatieVerzoek(verzoek);

        final Integer relatieIngangsdatumVerzoek = relatieVerzoek != null ? relatieVerzoek.getDatum().intValue() : null;
        final String relatiePlaatsVerzoek = relatieVerzoek != null ? relatieVerzoek.getPlaats() : null;
        final String relatieLandVerzoek = relatieVerzoek != null ? relatieVerzoek.getLand() : null;

        final Relatie relatieBrp = geefRelatieBrp(rootPersoon);

        final Integer relatieIngangsdatumBrp;
        final String relatiePlaatsBrp;
        final String relatiePlaatsBuitenlandBrp;
        final String relatieLandBrp;
        if (relatieBrp != null) {
            relatieIngangsdatumBrp = relatieBrp.getDatumAanvang();

            if (relatieBrp.getGemeenteAanvang() != null) {
                relatiePlaatsBrp = String.valueOf(relatieBrp.getGemeenteAanvang().getCode());
                relatiePlaatsBuitenlandBrp = null;
            } else {
                relatiePlaatsBrp = null;
                relatiePlaatsBuitenlandBrp = relatieBrp.getBuitenlandsePlaatsAanvang();
            }
            relatieLandBrp = relatieBrp.getLandOfGebiedAanvang() != null ? String.valueOf(relatieBrp.getLandOfGebiedAanvang().getCode()) : null;
        } else {
            relatieIngangsdatumBrp = null;
            relatiePlaatsBrp = null;
            relatiePlaatsBuitenlandBrp = null;
            relatieLandBrp = null;
        }

        return ControleUtils.equalsNullSafe(relatieIngangsdatumBrp, relatieIngangsdatumVerzoek)
                && ControleUtils.equalsNullSafe(relatieLandBrp, relatieLandVerzoek)
                && ((ControleUtils.equalsNullSafe(relatiePlaatsBrp, relatiePlaatsVerzoek)) || (ControleUtils.equalsNullSafe(
                    relatiePlaatsBuitenlandBrp,
                    relatiePlaatsVerzoek)));
    }

    private Relatie geefRelatieBrp(final Persoon rootPersoon) {
        final Relatie relatieBrp;
        if (rootPersoon.getRelaties() != null && rootPersoon.getRelaties().iterator().hasNext()) {
            relatieBrp = rootPersoon.getRelaties().iterator().next();
        } else {
            LOG.info("Er is geen relatie vastgelegd op de PL van de persoon.");
            relatieBrp = null;
        }
        return relatieBrp;
    }

    private RelatieSluitingGroepType geefRelatieVerzoek(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {
        final RelatieSluitingGroepType relatieVerzoek;
        if (verzoek.getRelatie() != null && verzoek.getRelatie().getSluiting() != null) {
            relatieVerzoek = verzoek.getRelatie().getSluiting().getSluiting();
        } else {
            LOG.info("Er is geen relatie meegegeven met het verzoek.");
            relatieVerzoek = null;
        }
        return relatieVerzoek;
    }
}
