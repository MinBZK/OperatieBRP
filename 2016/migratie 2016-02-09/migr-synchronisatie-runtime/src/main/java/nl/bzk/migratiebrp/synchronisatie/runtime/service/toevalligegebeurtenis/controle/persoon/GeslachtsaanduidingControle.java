/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.ControleUtils;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor het inhoudelijk controleren van de geslachtsaanduiding van een persoon tegen de persoon uit het
 * verzoekbericht.
 */
@Component("geslachtsaanduidingControle")
public final class GeslachtsaanduidingControle implements ToevalligeGebeurtenisControle {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        final String geslachtsaanduidingBrp = ControleUtils.geefNullSafeCodeUitEnumeratie(rootPersoon.getGeslachtsaanduiding());

        String geslachtsaanduidingVerzoek;
        if (verzoek.getPersoon() != null
            && verzoek.getPersoon().getGeslacht() != null
            && verzoek.getPersoon().getGeslacht().getGeslachtsaanduiding() != null)
        {
            geslachtsaanduidingVerzoek = verzoek.getPersoon().getGeslacht().getGeslachtsaanduiding().value();
        } else {
            LOG.info("Persoon in verzoek heeft geen geslachtsaanduiding.");
            geslachtsaanduidingVerzoek = null;
        }

        return ControleUtils.equalsNullSafe(
            geslachtsaanduidingBrp,
            converteerder.converteerLo3Geslachtsaanduiding(new Lo3Geslachtsaanduiding(geslachtsaanduidingVerzoek)).getWaarde());
    }
}
