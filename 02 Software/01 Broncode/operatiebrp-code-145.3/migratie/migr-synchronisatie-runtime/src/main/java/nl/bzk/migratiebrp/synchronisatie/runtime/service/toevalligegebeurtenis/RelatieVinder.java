/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import java.util.Objects;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeboorteVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeslachtVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.IdentificatieNummerVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.SamengesteldeNaamVergelijker;
import org.springframework.stereotype.Component;

/**
 * Vind de juiste relatie binnen een brppersoon op basis van een toevalligegebeurtenisverzoek.
 */
@Component
public class RelatieVinder {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private IdentificatieNummerVergelijker identificatieNummerVergelijker;

    private SamengesteldeNaamVergelijker samengesteldeNaamVergelijker;

    private GeboorteVergelijker geboorteVergelijker;

    private GeslachtVergelijker geslachtVergelijker;

    /**
     * Constructor.
     * @param identificatieNummerVergelijker vergelijker van identificatie nummers
     * @param samengesteldeNaamVergelijker vergelijker van samengestelde naam
     * @param geboorteVergelijker vergelijker van geboorte
     * @param geslachtVergelijker vergelijker van geslacht
     */
    @Inject
    public RelatieVinder(final IdentificatieNummerVergelijker identificatieNummerVergelijker,
                         final SamengesteldeNaamVergelijker samengesteldeNaamVergelijker,
                         final GeboorteVergelijker geboorteVergelijker,
                         final GeslachtVergelijker geslachtVergelijker) {
        this.identificatieNummerVergelijker = identificatieNummerVergelijker;
        this.samengesteldeNaamVergelijker = samengesteldeNaamVergelijker;
        this.geboorteVergelijker = geboorteVergelijker;
        this.geslachtVergelijker = geslachtVergelijker;
    }

    /**
     * Vindt de correcte relatie obv toevallige gebeurtenis.
     * @param brpPersoon persoonslijst van brp
     * @param brpToevalligeGebeurtenis toevallige gebeurtenis
     * @return de gevonden relatie of null indien geen relatie gevonden is.
     * @throws nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException Indien relatie niet gevonden wordt
     */
    public final BrpRelatie vindRelatie(final BrpPersoonslijst brpPersoon, final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis)
            throws ControleException {
        BrpRelatie brpRelatie = null;

        for (final BrpRelatie relatie : brpPersoon.getRelaties()) {
            LOGGER.debug("vindRelatie: beoordeel relatie {}", relatie);
            if (controleerSoortRelatie(relatie, brpToevalligeGebeurtenis)
                    && relatie.getRelatieStapel().bevatActueel()
                    && controleerRelatie(relatie, brpToevalligeGebeurtenis)) {
                LOGGER.debug("vindRelatie: relatie voldoet aan controles {}", relatie);
                brpRelatie = relatie;
                break;
            } else {
                LOGGER.debug("Relatie bevat geen actuele relatie gegevens");
            }
        }

        if (brpRelatie == null) {
            LOGGER.debug("Geen relatie gevonden");
            throw new ControleException(FoutredenType.N);
        }
        return brpRelatie;

    }

    private boolean controleerRelatie(final BrpRelatie relatie, final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis) {
        final BrpRelatieInhoud inhoud = relatie.getRelatieStapel().getActueel().getInhoud();
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting = brpToevalligeGebeurtenis.getVerbintenis().getSluiting();
        final BrpToevalligeGebeurtenisPersoon partner = brpToevalligeGebeurtenis.getVerbintenis().getPartner();
        LOGGER.debug("redenEindeRelatie -> " + inhoud.getRedenEindeRelatieCode());
        final boolean result =
                inhoud.getRedenEindeRelatieCode() == null && controleerSluiting(sluiting, inhoud) && controleerBetrokkenheid(relatie, partner);
        LOGGER.debug("controleerRelatie -> " + result);
        return result;
    }

    private boolean controleerBetrokkenheid(final BrpRelatie relatie, final BrpToevalligeGebeurtenisPersoon partner) {
        LOGGER.debug("controleerBetrokkenheid(relatie={}, partner={})", relatie, partner);
        boolean result = false;
        for (final BrpBetrokkenheid brpBetrokkenheid : relatie.getBetrokkenheden()) {
            LOGGER.debug("beoordeel betrokkenheid {}", brpBetrokkenheid);
            result = !brpBetrokkenheid.equals(relatie.getIkBetrokkenheid());
            result = result && brpBetrokkenheid.getSamengesteldeNaamStapel().bevatActueel();
            result = result && controleerIdentificatienummers(brpBetrokkenheid, partner);
            result = result && controleerNaam(brpBetrokkenheid, partner);
            result = result && controleerGeboorte(brpBetrokkenheid, partner);
            result = result && controleerGeslacht(brpBetrokkenheid, partner);
            if (result) {
                break;
            }
        }
        LOGGER.debug("beoordeel betrokkenheid -> {}", result);
        return result;
    }

    private boolean controleerIdentificatienummers(final BrpBetrokkenheid brpBetrokkenheid, final BrpToevalligeGebeurtenisPersoon partner) {
        if (partner.getAdministratienummer() == null && partner.getBurgerservicenummer() == null) {
            // Geen identificatiegegevens meegegeven, dan niet gebruiken in controle
            return true;
        }
        boolean result = false;
        if (brpBetrokkenheid.getIdentificatienummersStapel() != null && brpBetrokkenheid.getIdentificatienummersStapel().bevatActueel()) {
            final BrpIdentificatienummersInhoud identificatienummers = brpBetrokkenheid.getIdentificatienummersStapel().getActueel().getInhoud();
            result = identificatieNummerVergelijker.vergelijk(partner, identificatienummers);
        }
        LOGGER.debug("controleerIdentificatienummers -> {}", result);
        return result;
    }

    private boolean controleerGeslacht(final BrpBetrokkenheid brpBetrokkenheid, final BrpToevalligeGebeurtenisPersoon partner) {
        boolean result = false;
        if (brpBetrokkenheid.getGeslachtsaanduidingStapel().bevatActueel()) {
            final BrpGeslachtsaanduidingInhoud geslacht = brpBetrokkenheid.getGeslachtsaanduidingStapel().getActueel().getInhoud();
            result = geslachtVergelijker.vergelijk(partner, geslacht);
        }
        LOGGER.debug("controleerGeslacht -> ", result);
        return result;
    }

    private boolean controleerGeboorte(final BrpBetrokkenheid brpBetrokkenheid, final BrpToevalligeGebeurtenisPersoon partner) {
        boolean result = false;
        if (brpBetrokkenheid.getGeboorteStapel().bevatActueel()) {
            final BrpGeboorteInhoud geboorte = brpBetrokkenheid.getGeboorteStapel().getActueel().getInhoud();
            result = geboorteVergelijker.vergelijk(partner, geboorte);
        }
        LOGGER.debug("controleerGeboorte -> ", result);
        return result;
    }

    private boolean controleerNaam(final BrpBetrokkenheid brpBetrokkenheid, final BrpToevalligeGebeurtenisPersoon partner) {
        final BrpSamengesteldeNaamInhoud samengesteldeNaam = brpBetrokkenheid.getSamengesteldeNaamStapel().getActueel().getInhoud();
        final boolean result = samengesteldeNaamVergelijker.vergelijk(partner, samengesteldeNaam);
        LOGGER.debug("controleerNaam -> ", result);
        return result;
    }

    private boolean controleerSluiting(final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting, final BrpRelatieInhoud inhoud) {
        boolean resultGelijk = Objects.equals(sluiting.getDatum(), inhoud.getDatumAanvang());
        resultGelijk &= Objects.equals(sluiting.getGemeenteCode(), inhoud.getGemeenteCodeAanvang());
        resultGelijk &= Objects.equals(sluiting.getLandOfGebiedCode(), inhoud.getLandOfGebiedCodeAanvang());
        resultGelijk &= Objects.equals(sluiting.getBuitenlandsePlaats(), inhoud.getBuitenlandsePlaatsAanvang());
        resultGelijk &= Objects.equals(sluiting.getOmschrijvingLocatie(), inhoud.getOmschrijvingLocatieAanvang());
        LOGGER.debug("controleerSluiting {} <- {}, {}", resultGelijk, sluiting, inhoud);
        return resultGelijk;
    }

    private boolean controleerSoortRelatie(final BrpRelatie relatie, final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis) {
        final BrpSoortRelatieCode soortRelatieCode = relatie.getSoortRelatieCode();
        final BrpSoortRelatieCode relatieCode = brpToevalligeGebeurtenis.getVerbintenis().getSluiting().getRelatieCode();
        final boolean result = soortRelatieCode.equals(relatieCode);
        LOGGER.debug("controleerSoortRelatie {} <- {}, {}", result, soortRelatieCode, relatieCode);
        return result;
    }

}
