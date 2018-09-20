/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Omzetting;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Ontbinding;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk toevallige gebeurtenis service. Voert een bijhouding uit in de BRP.
 */
public final class VerwerkToevalligeGebeurtenisService
        implements SynchronisatieBerichtService<VerwerkToevalligeGebeurtenisVerzoekBericht, VerwerkToevalligeGebeurtenisAntwoordBericht>
{

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    @Named("omzettingHuwelijkOfGeregistreerdPartnerschapControle")
    private ToevalligeGebeurtenisControle omzettingHuwelijkOfGeregistreerdPartnerschapControle;

    @Inject
    @Named("omzettingHuwelijkOfGeregistreerdPartnerschapVerwerker")
    private ToevalligeGebeurtenisVerwerker omzettingHuwelijkOfGeregistreerdPartnerschapVerwerker;

    @Inject
    @Named("ontbindingHuwelijkOfGeregistreerdPartnerschapControle")
    private ToevalligeGebeurtenisControle ontbindingHuwelijkOfGeregistreerdPartnerschapControle;

    @Inject
    @Named("ontbindingHuwelijkOfGeregistreerdPartnerschapVerwerker")
    private ToevalligeGebeurtenisVerwerker ontbindingHuwelijkOfGeregistreerdPartnerschapVerwerker;

    @Inject
    @Named("sluitingHuwelijkOfGeregistreerdPartnerschapControle")
    private ToevalligeGebeurtenisControle sluitingHuwelijkOfGeregistreerdPartnerschapControle;

    @Inject
    @Named("sluitingHuwelijkOfGeregistreerdPartnerschapVerwerker")
    private ToevalligeGebeurtenisVerwerker sluitingHuwelijkOfGeregistreerdPartnerschapVerwerker;

    @Inject
    @Named("naamGeslachtControle")
    private ToevalligeGebeurtenisControle naamGeslachtControle;

    @Inject
    @Named("naamGeslachtVerwerker")
    private ToevalligeGebeurtenisVerwerker naamGeslachtVerwerker;

    @Inject
    @Named("overlijdenControle")
    private ToevalligeGebeurtenisControle overlijdenControle;

    @Inject
    @Named("overlijdenVerwerker")
    private ToevalligeGebeurtenisVerwerker overlijdenVerwerker;

    @Inject
    private BrpDalService brpDalService;

    private boolean maakAntwoordBerichtMetFoutredenN;
    private String melding;

    @Override
    public Class<VerwerkToevalligeGebeurtenisVerzoekBericht> getVerzoekType() {
        return VerwerkToevalligeGebeurtenisVerzoekBericht.class;
    }

    private VerwerkToevalligeGebeurtenisAntwoordBericht maakFoutBericht(
        final FoutredenType foutredenType,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek)
    {
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);
        antwoord.setFoutreden(foutredenType);
        antwoord.setCorrelationId(verzoek.getMessageId());

        return antwoord;
    }

    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public VerwerkToevalligeGebeurtenisAntwoordBericht verwerkBericht(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        LOG.debug("Zoek persoon o.b.v. A-nummer.");

        // Zoek persoon op A-nummer.
        final List<Persoon> gevondenPersonen =
                brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(
                    Long.parseLong(verzoek.getPersoon().getIdentificatienummers().getANummer()));

        if (gevondenPersonen.isEmpty() || gevondenPersonen.size() > 1) {
            final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord;
            if (gevondenPersonen.isEmpty()) {
                // Geen persoon gevonden voor het meegegeven A-nummer.
                antwoord = maakFoutBericht(FoutredenType.G, verzoek);
                LOG.debug("Geen persoon gevonden voor het meegegeven A-nummer.");
            } else {
                // Meerdere personen gevonden voor het meegegeven A-nummer.
                antwoord = maakFoutBericht(FoutredenType.U, verzoek);
                LOG.debug("Meerdere personen gevonden voor het meegegeven A-nummer.");
            }
            return antwoord;
        }

        final Persoon rootPersoon = gevondenPersonen.get(0);

        final String bijhoudingsPartij = rootPersoon.getBijhoudingspartij() != null ? String.valueOf(rootPersoon.getBijhoudingspartij().getCode()) : null;
        final boolean maakAntwoordBerichtMetFoutredenV =
                verzoek.getDoelGemeente() != null ? verzoek.getDoelGemeente().equals(bijhoudingsPartij) : bijhoudingsPartij == null;

        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord;
        if (!maakAntwoordBerichtMetFoutredenV) {
            final boolean verwerkingGeslaagd = verwerkBerichtInhoudelijk(verzoek, rootPersoon);
            if (maakAntwoordBerichtMetFoutredenN) {
                // Maak een antwoordbericht met foutreden N aan indien inhoudelijke gegevens niet overeenkomen.
                antwoord = maakFoutBericht(FoutredenType.N, verzoek);
                LOG.debug("Meegegeven gegevens komen niet overeen met gegevens BRP van voor het rechtsfeit.");
            } else if (!verwerkingGeslaagd) {
                // Verwerking is mislukt, stuur een antwoord bericht met foutreden XXXXX.
                throw new IllegalArgumentException(melding);
            } else {
                LOG.debug("Tb02 correct verwerkt.");
                antwoord = null;
            }
        } else {
            // Maak een antwoordbericht met foutreden V aan indien de gemeente van inschrijving niet overeenkomt met de
            // doelgemeente.
            antwoord = maakFoutBericht(FoutredenType.V, verzoek);
            antwoord.setGemeentecode(bijhoudingsPartij);
            LOG.debug("Persoon uit Tb02 is verhuisd.");
        }

        return antwoord;
    }

    private boolean verwerkBerichtInhoudelijk(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek, final Persoon rootPersoon) {
        boolean verwerkingGeslaagd;

        // Switch actie persoon/huwelijk/overlijden/adoptie o.b.v. wat gevuld is.
        if (verzoek.getUpdatePersoon() != null) {
            // NaamGeslacht (1H/M/S)
            LOG.info("Tb02: Verwerk naamgeslacht.");

            verwerkingGeslaagd = verwerkNaamGeslachtBijhouding(verzoek, rootPersoon);

        } else if (verzoek.getOverlijden() != null) {
            // Overlijden
            LOG.info("Tb02: Verwerk overlijden persoon.");

            verwerkingGeslaagd = verwerkOverlijden(verzoek, rootPersoon);

        } else if (verzoek.getRelatie() != null) {
            // Huwelijk
            LOG.info("Tb02: Verwerk huwelijk of geregistreerd partnerschap.");

            verwerkingGeslaagd = verwerkHuwelijkOfGeregistreerdPartnerschap(verzoek, rootPersoon);

        } else if (verzoek.getFamilieRechtelijkeBetrekking() != null) {
            // Adoptie
            LOG.info("Tb02: Verwerk adoptie.");
            throw new UnsupportedOperationException();
        } else {
            throw new UnsupportedOperationException();
        }
        return verwerkingGeslaagd;
    }

    private boolean verwerkHuwelijkOfGeregistreerdPartnerschap(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek, final Persoon rootPersoon) {
        final Omzetting omzetting = verzoek.getRelatie().getOmzetting();
        final Ontbinding ontbinding = verzoek.getRelatie().getOntbinding();
        boolean resultaatVerwerking = true;

        if (omzetting == null && ontbinding == null) {
            // Sluiting (3/5A)

            // Controleer inhoudelijk of inhoud gelijk is aan PL van voor rechtsfeit.
            maakAntwoordBerichtMetFoutredenN = !sluitingHuwelijkOfGeregistreerdPartnerschapControle.controleer(rootPersoon, verzoek);

            if (!maakAntwoordBerichtMetFoutredenN && !sluitingHuwelijkOfGeregistreerdPartnerschapVerwerker.verwerk(verzoek, rootPersoon)) {
                melding =
                        "Verwerking van het verzoek voor het sluiten van een huwelijk/geregistreerd partnerschap voor verzoek met Id="
                          + verzoek.getMessageId();
                resultaatVerwerking = false;
            }

        } else if (ontbinding != null) {
            // Ontbinding (3/5B)

            // Controleer inhoudelijk of inhoud gelijk is aan PL van voor rechtsfeit.
            maakAntwoordBerichtMetFoutredenN = !ontbindingHuwelijkOfGeregistreerdPartnerschapControle.controleer(rootPersoon, verzoek);

            if (!maakAntwoordBerichtMetFoutredenN && !ontbindingHuwelijkOfGeregistreerdPartnerschapVerwerker.verwerk(verzoek, rootPersoon)) {
                melding =
                        "Verwerking van het verzoek voor het ontbinden van een huwelijk/geregistreerd partnerschap voor verzoek met Id="
                          + verzoek.getMessageId();
                resultaatVerwerking = false;
            }

        } else {
            // Omzetting (3/5H)

            // Controleer inhoudelijk of inhoud gelijk is aan PL van voor rechtsfeit.
            maakAntwoordBerichtMetFoutredenN = !omzettingHuwelijkOfGeregistreerdPartnerschapControle.controleer(rootPersoon, verzoek);

            if (!maakAntwoordBerichtMetFoutredenN && !omzettingHuwelijkOfGeregistreerdPartnerschapVerwerker.verwerk(verzoek, rootPersoon)) {
                melding =
                        "Verwerking van het verzoek voor het omzetten van een huwelijk/geregistreerd partnerschap voor verzoek met Id="
                          + verzoek.getMessageId();
                resultaatVerwerking = false;
            }

        }

        return resultaatVerwerking;
    }

    private boolean verwerkOverlijden(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek, final Persoon rootPersoon) {

        // Controleer inhoudelijk of inhoud gelijk is aan PL van voor rechtsfeit.
        maakAntwoordBerichtMetFoutredenN = !overlijdenControle.controleer(rootPersoon, verzoek);
        if (!maakAntwoordBerichtMetFoutredenN && !overlijdenVerwerker.verwerk(verzoek, rootPersoon)) {
            melding = "Verwerking van het verzoek voor het overlijden (2A/G)is mislukt voor verzoek met Id=" + verzoek.getMessageId();
            return false;
        }
        return true;
    }

    private boolean verwerkNaamGeslachtBijhouding(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek, final Persoon rootPersoon) {

        // Controleer inhoudelijk of inhoud gelijk is aan PL van voor rechtsfeit.
        maakAntwoordBerichtMetFoutredenN = !naamGeslachtControle.controleer(rootPersoon, verzoek);

        if (!maakAntwoordBerichtMetFoutredenN && !naamGeslachtVerwerker.verwerk(verzoek, rootPersoon)) {
            melding = "Verwerking van het verzoek voor bijwerken van naamgeslacht (1H/M/S) is mislukt verzoek met Id=" + verzoek.getMessageId();
            return false;
        }
        return true;
    }

    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

}
