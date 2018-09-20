/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.migratiebrp.bericht.model.sync.generated.GeboorteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatienummersGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.ControleUtils;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor het inhoudelijk controleren van een persoon tegen de persoon uit het verzoekbericht.
 */
@Component("persoonControle")
public final class PersoonControle implements ToevalligeGebeurtenisControle {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    @Named("geslachtsaanduidingControle")
    private ToevalligeGebeurtenisControle geslachtsaanduidingControle;

    @Inject
    @Named("geslachtsnaamComponentenControle")
    private ToevalligeGebeurtenisControle geslachtsnaamComponentenControle;

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        if (verzoek.getPersoon() == null) {
            LOG.info("Persoon in verzoek is leeg");
            return false;
        }

        return controleerIdentificerendeGegevens(rootPersoon, verzoek.getPersoon().getIdentificatienummers())
               && controleerPersoonGeboorte(rootPersoon, verzoek.getPersoon().getGeboorte())
               && geslachtsaanduidingControle.controleer(rootPersoon, verzoek)
               && geslachtsnaamComponentenControle.controleer(rootPersoon, verzoek);

    }

    private boolean controleerIdentificerendeGegevens(final Persoon rootPersoon, final IdentificatienummersGroepType identificatienummersGroepType) {

        Long anummer;
        Integer bsn;

        try {
            anummer = Long.valueOf(identificatienummersGroepType.getANummer());
        } catch (final NumberFormatException exception) {
            LOG.info("Persoon in verzoek heeft geen geldig anummer.");
            anummer = null;
        }

        try {
            bsn = Integer.valueOf(identificatienummersGroepType.getBurgerservicenummer());
        } catch (final NumberFormatException exception) {
            LOG.info("Persoon in verzoek heeft geen bsn.");
            bsn = null;
        }

        final boolean anummerGelijk = ControleUtils.equalsNullSafe(rootPersoon.getAdministratienummer(), anummer);
        LOG.info("Administratienummer komt overeen: " + anummerGelijk);
        final boolean bsnGelijk = ControleUtils.equalsNullSafe(rootPersoon.getBurgerservicenummer(), bsn);
        LOG.info("Burgerservicenummer komt overen: " + bsnGelijk);

        return anummerGelijk && bsnGelijk;

    }

    private boolean controleerPersoonGeboorte(final Persoon rootPersoon, final GeboorteGroepType geboorteGroepType) {

        final Short geboorteLandBrp = rootPersoon.getLandOfGebiedGeboorte() != null ? rootPersoon.getLandOfGebiedGeboorte().getCode() : null;

        Short geboorteLandVerzoek;
        try {
            geboorteLandVerzoek = Short.valueOf(geboorteGroepType.getLand());
        } catch (final NumberFormatException exception) {
            LOG.info("Persoon in verzoek heeft geen geldig geboorteland.");
            geboorteLandVerzoek = null;
        }

        final Short geboortePlaatsBrp = rootPersoon.getGemeenteGeboorte() != null ? rootPersoon.getGemeenteGeboorte().getCode() : null;

        final Integer geboorteDatumVerzoek = geboorteGroepType.getDatum() != null ? geboorteGroepType.getDatum().intValue() : null;

        Short plaatscode = null;
        try {
            plaatscode = Short.valueOf(geboorteGroepType.getPlaats());
        } catch (NumberFormatException nfe) {
            LOG.debug("Plaatsnaam is geen code, maar een buitenlandse plaats");
        }

        final boolean persoonGeboorteGelijk = ControleUtils.equalsNullSafe(rootPersoon.getDatumGeboorte(), geboorteDatumVerzoek)
                && ControleUtils.equalsNullSafe(geboorteLandBrp, geboorteLandVerzoek)
                && (ControleUtils.equalsNullSafe(geboortePlaatsBrp, plaatscode) || ControleUtils.equalsNullSafe(
                    rootPersoon.getBuitenlandsePlaatsGeboorte(),
                   geboorteGroepType.getPlaats()));

        LOG.info("Geboortegegevens komen overeen: " + persoonGeboorteGelijk);

        return persoonGeboorteGelijk;
    }
}
