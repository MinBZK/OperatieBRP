/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AdellijkeTitelPredicaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.ControleUtils;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor het inhoudelijk controleren van een persoon tegen de persoon uit het verzoekbericht.
 */
@Component("geslachtsnaamComponentenControle")
public final class GeslachtsnaamComponentenControle implements ToevalligeGebeurtenisControle {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        final NaamGroepType naam = verzoek.getPersoon() != null ? verzoek.getPersoon().getNaam() : null;
        final String voornamenVerzoek;
        final String voorvoegselVerzoek;
        final String geslachtsnaamVerzoek;
        final AdellijkeTitelPredicaatType adellijkeTitelPredicaatType;

        if (naam != null) {
            voornamenVerzoek = verzoek.getPersoon().getNaam().getVoornamen();
        } else {
            LOG.info("Persoon in verzoek heeft geen voornamen.");
            voornamenVerzoek = null;
        }

        if (naam != null) {
            voorvoegselVerzoek = verzoek.getPersoon().getNaam().getVoorvoegsel();
        } else {
            LOG.info("Persoon in verzoek heeft geen voorvoegsel.");
            voorvoegselVerzoek = null;
        }

        if (naam != null) {
            geslachtsnaamVerzoek = verzoek.getPersoon().getNaam().getGeslachtsnaam();
        } else {
            LOG.info("Persoon in verzoek heeft geen geslachtsnaam.");
            geslachtsnaamVerzoek = null;
        }

        if (naam != null) {
            adellijkeTitelPredicaatType = verzoek.getPersoon().getNaam().getAdellijkeTitelPredicaat();
        } else {
            LOG.info("Persoon in verzoek heeft geen adellijke titel of predicaat.");
            adellijkeTitelPredicaatType = null;
        }

        return ControleUtils.equalsNullSafe(rootPersoon.getVoornamen(), voornamenVerzoek)
               && ControleUtils.equalsNullSafe(rootPersoon.getVoorvoegsel(), voorvoegselVerzoek)
               && ControleUtils.equalsNullSafe(rootPersoon.getGeslachtsnaamstam(), geslachtsnaamVerzoek)
               && controleerAdellijkeTitelEnPredicaat(rootPersoon, adellijkeTitelPredicaatType);
    }

    private boolean controleerAdellijkeTitelEnPredicaat(final Persoon rootPersoon, final AdellijkeTitelPredicaatType adellijkeTitelPredicaatType) {

        final String adellijkeTitel;
        final String predicaat;

        if (adellijkeTitelPredicaatType != null) {
            final BrpAdellijkeTitelCode adellijkeTitelBrp =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(new Lo3AdellijkeTitelPredikaatCode(
                        adellijkeTitelPredicaatType.value()));
            final BrpPredicaatCode predicaatBrp =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(new Lo3AdellijkeTitelPredikaatCode(
                        adellijkeTitelPredicaatType.value()));

            adellijkeTitel = adellijkeTitelBrp != null ? adellijkeTitelBrp.getWaarde() : null;
            predicaat = predicaatBrp != null ? predicaatBrp.getWaarde() : null;

        } else {
            adellijkeTitel = null;
            predicaat = null;
        }

        return ControleUtils.equalsNullSafe(ControleUtils.geefNullSafeCodeUitEnumeratie(rootPersoon.getAdellijkeTitel()), adellijkeTitel)
               && ControleUtils.equalsNullSafe(ControleUtils.geefNullSafeCodeUitEnumeratie(rootPersoon.getPredicaat()), predicaat);
    }
}
