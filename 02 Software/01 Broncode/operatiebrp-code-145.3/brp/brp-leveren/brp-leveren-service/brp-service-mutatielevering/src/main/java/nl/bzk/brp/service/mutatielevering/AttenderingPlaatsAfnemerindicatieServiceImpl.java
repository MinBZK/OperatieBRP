/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.AfnemerindicatieParameters;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import org.springframework.stereotype.Component;

/**
 * PlaatsAfnemerIndicatiesStapImpl.
 */
@Component
@Bedrijfsregel(Regel.R1335)
@Bedrijfsregel(Regel.R1336)
@Bedrijfsregel(Regel.R1408)
final class AttenderingPlaatsAfnemerindicatieServiceImpl implements VerwerkHandelingService.AttenderingPlaatsAfnemerindicatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger();


    @Inject
    private PersoonAfnemerindicatieService persoonAfnemerindicatieService;

    private AttenderingPlaatsAfnemerindicatieServiceImpl() {

    }

    @Override
    public void plaatsAfnemerindicaties(final Long handeling, final List<Mutatiebericht> berichten) throws BlobException {
        final SetMultimap<Autorisatiebundel, Persoonslijst> personenPerAutorisatie
                = verzamelPersonenPerAutorisatie(berichten);

        //set met unieke parameters, uniek op persoonsinformatie
        final Set<AfnemerindicatieParameters> afnemerindicatieParametersSet = new HashSet<>();
        for (final Autorisatiebundel autorisatiebundel : personenPerAutorisatie.keySet()) {
            if (!isDienstAttenderingMetPlaatsing(autorisatiebundel)) {
                continue;
            }
            final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = autorisatiebundel.getToegangLeveringsautorisatie();
            final int leveringsautorisatieId = autorisatiebundel.getLeveringsautorisatieId();
            final String partijCode = autorisatiebundel.getPartij().getCode();
            for (final Persoonslijst persoonslijst : personenPerAutorisatie.get(autorisatiebundel)) {
                if (afnemerindicatieBestaat(partijCode, leveringsautorisatieId, persoonslijst)) {
                    continue;
                }
                LOGGER.debug("Afnemerindicatie obv attendering wordt geplaatst voor de persoon met id: {}, partij"
                                + " met code: {} en leveringsautorisatie met naam: {}.",
                        persoonslijst.getId(),
                        autorisatiebundel.getPartij().getCode(),
                        toegangLeveringsautorisatie.getLeveringsautorisatie().getNaam());
                try {
                    final ZonedDateTime tsReg = persoonslijst.getAdministratieveHandeling().getTijdstipRegistratie();
                    final AfnemerindicatieParameters
                            afnemerindicatieParameters =
                            new AfnemerindicatieParameters(persoonslijst.getId(), persoonslijst.getPersoonLockVersie(),
                                    persoonslijst.getAfnemerindicatieLockVersie());
                    persoonAfnemerindicatieService.plaatsAfnemerindicatie(
                            afnemerindicatieParameters,
                            autorisatiebundel.getPartij(),
                            toegangLeveringsautorisatie.getLeveringsautorisatie().getId(),
                            autorisatiebundel.getDienst().getId(), null, null, tsReg);
                    afnemerindicatieParametersSet.add(afnemerindicatieParameters);
                } catch (final OptimisticLockException exceptie) {
                    // Deze situatie negeren we.
                    LOGGER.debug("Optimistic lock exception bij plaatsen", exceptie);
                    LOGGER.info("{} Indicatie bestond al voor persoon of persoon gewijzigd [{}]. Melding: {}", Regel.R1336.getMelding(),
                            persoonslijst.getMetaObject().getObjectsleutel(), exceptie.getMessage());
                }
            }
        }
        //werk alle blobs bij voor de geraakte personen
        for (AfnemerindicatieParameters afnemerindicatieParameters : afnemerindicatieParametersSet) {
            persoonAfnemerindicatieService.updateAfnemerindicatieBlob(afnemerindicatieParameters);
        }
    }

    /**
     * Verzamel per autorisatiebundel alle bijgehouden personen
     * @param berichten lijst met BRP en LO3 berichten
     */
    private SetMultimap<Autorisatiebundel, Persoonslijst> verzamelPersonenPerAutorisatie(final List<Mutatiebericht> berichten) {
        final SetMultimap<Autorisatiebundel, Persoonslijst> bijgehoudenPersonen = LinkedHashMultimap.create();
        for (final Mutatiebericht mutatiebericht : berichten) {
            bijgehoudenPersonen.putAll(mutatiebericht.getMutatielevering().getAutorisatiebundel(), mutatiebericht.getPersonenInBericht());
        }
        return bijgehoudenPersonen;
    }


    /**
     * Controleert voor een persoon of er (op dit moment) een afnemerindicatie is voor een partij / leveringsautorisatie combinatie.
     * @param partijCode De partijcode.
     * @param leveringsautorisatieId De id van de leveringsautorisatie.
     * @return True als de afnemerindicatie niet bestaat, anders false.
     */
    @Bedrijfsregel(Regel.R1336)
    private boolean afnemerindicatieBestaat(final String partijCode,
                                            final int leveringsautorisatieId,
                                            final Persoonslijst persoon) {
        for (final Afnemerindicatie afnemerindicatie : persoon.getGeldendeAfnemerindicaties()) {
            final boolean isZelfdeLeveringsautorisatie = afnemerindicatie.getLeveringsAutorisatieId() == leveringsautorisatieId;
            final boolean isZelfdeAfnemer = afnemerindicatie.getAfnemerCode().equals(partijCode);
            if (isZelfdeLeveringsautorisatie && isZelfdeAfnemer) {
                LOGGER.debug("Afnemerindicatie obv attendering wordt niet geplaatst, omdat deze al bestaat voor de"
                                + " persoon met id: {}, partij met code: {} en leveringsautorisatie met naam: {}.",
                        persoon.getMetaObject().getObjectsleutel(), partijCode, leveringsautorisatieId);
                return true;
            }
        }
        return false;
    }


    /**
     * Controleert of in deze stap de afnemerindicaties geplaatst dienen te worden.
     * @param lerAutorisatiebundel De lerAutorisatiebundel.
     * @return True als er afnemerindicatie geplaatst dienen te worden, anders false.
     */
    @Bedrijfsregel(Regel.R1335)
    private boolean isDienstAttenderingMetPlaatsing(final Autorisatiebundel lerAutorisatiebundel) {
        final SoortDienst soortDienst =
                lerAutorisatiebundel.getDienst().getSoortDienst();

        return soortDienst == SoortDienst.ATTENDERING
                && lerAutorisatiebundel.getDienst().getEffectAfnemerindicaties() == EffectAfnemerindicaties.PLAATSING;
    }
}
