/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.repository.PersoonRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.RelatieRepository;

import org.springframework.stereotype.Component;

/**
 * Bevat de functionaliteit om een persoon die is geconverteerd vanuit een LO3 persoonslijst (de zogenaamde kluizenaar)
 * aan te passen o.b.v. van de huidige situatie in de BRP zodat deze persoon inclusief de relaties het gewenste beeld
 * weergeeft van deze persoon.
 * 
 */
@Component
public final class PersoonRelateerderImpl implements PersoonRelateerder {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final PersoonRepository persoonRepository;
    private final PersoonRelateerderHelper persoonRelateerderHelper;
    private final RelatieRepository relatieRepository;

    /**
     * Maakt een PersoonRelateerderImpl object.
     * 
     * @param persoonRepository
     *            de repostitory voor het query-en van persoon gerelateerde gegevens
     * @param persoonRelateerderHelper
     *            de persoon relateerder helper class
     * @param relatieRepository
     *            de repository voor het verwijderen van relaties
     */
    @Inject
    PersoonRelateerderImpl(
            final PersoonRepository persoonRepository,
            final PersoonRelateerderHelper persoonRelateerderHelper,
            final RelatieRepository relatieRepository) {
        this.persoonRepository = persoonRepository;
        this.persoonRelateerderHelper = persoonRelateerderHelper;
        this.relatieRepository = relatieRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Persoon updateRelatiesVanPersoon(final Persoon persoon, final BigDecimal aNummerIstPersoon) {
        if (persoon == null) {
            throw new NullPointerException("persoon mag niet null zijn");
        }
        relateerBetrokkenheden(persoon);
        if (aNummerIstPersoon == null) {
            return vergelijkEnVervangRelaties(persoon, persoon.getAdministratienummer());
        } else {
            return vergelijkEnVervangRelaties(persoon, aNummerIstPersoon);
        }
    }

    /**
     * Stap 1: Zoek voor alle relaties of de gerelateerde personen al voorkomen in de BRP en als dit zo is vervang de
     * referentie dan door de bestaande persoon in de BRP.
     * 
     * @param persoon
     *            de persoon waarvan alle gerelateerde personen moeten worden gerelateerd
     */
    private void relateerBetrokkenheden(final Persoon persoon) {
        for (final Betrokkenheid betrokkenheid : persoon.getAlleGerelateerdeBetrokkenheden()) {
            updateRelatieVanBetrokkenheid(betrokkenheid);
        }
    }

    /**
     * Stap 2: Vergelijk de SOLL situatie m.b.t. de relaties met de IST situatie van deze relaties in de BRP en merge de
     * verschillen. Deze methode dient te worden gebruikt wanneer het actuele a-nummer van IST persoon niet gelijk is
     * aan het actuele a-nummer van de SOLL persoon.
     * 
     * @param sollPersoon
     *            de persoon waarvan de relaties moeten worden gemerged
     * @param aNummerIstPersoon
     *            het a-nummer van de persoon die 'vervangen' moet worden
     * @return de persoon die de SOLL situatie is
     */
    private Persoon vergelijkEnVervangRelaties(final Persoon sollPersoon, final BigDecimal aNummerIstPersoon) {
        final BigDecimal nieuwActueelANummer = sollPersoon.getAdministratienummer();
        final boolean isANummerWijziging = isANummerWijziging(sollPersoon, aNummerIstPersoon);
        if (isANummerWijziging) {
            valideerDatANummerNogNietBestaat(nieuwActueelANummer);
        }
        final Persoon istPersoon = bepaalIstPersoon(aNummerIstPersoon);
        if (istPersoon != null) {
            if (isANummerWijziging) {
                // 1)relateer op basis van het vorig a-nummer
                sollPersoon.setAdministratienummer(aNummerIstPersoon);
            }
            persoonRelateerderHelper.mergeRelatiesVoorHuwelijkOfGp(sollPersoon, istPersoon);
            persoonRelateerderHelper.mergeRelatiesVoorOuder(sollPersoon, istPersoon);
            persoonRelateerderHelper.mergeRelatiesVoorKind(sollPersoon, istPersoon);
            persoonRelateerderHelper.mergeRelatiesVoorGerelateerdeKinderen(sollPersoon);
            if (!istPersoon.getBetrokkenheidSet().isEmpty()) {
                throw new IllegalStateException(
                        "Er mogen geen betrokkenheden meer verwijzen naar het bestaand persoon!");
            }
            // Update van persoon ipv remove + insert
            persoonRepository.remove(istPersoon);
            if (isANummerWijziging) {
                // 2) voer a-nummer wijziging door
                sollPersoon.setAdministratienummer(nieuwActueelANummer);
            }
        } else if (isANummerWijziging) {
            throw new IllegalArgumentException(String.format(
                    "Er bestaat geen ingeschreven persoon voor het te vervangen a-nummer: %d", aNummerIstPersoon));
        } else {
            persoonRelateerderHelper.mergeRelatiesVoorGerelateerdeKinderen(sollPersoon);
            /*
             * huidige persoon bestaat niet in db, dus geen relaties naar deze persoon, dus alle relaties ontkennen
             * vanuit betrokken personen
             */
            sollPersoon.ontkenAlleRelatiesVanuitBetrokkenen();
        }
        return sollPersoon;
    }

    private boolean isANummerWijziging(final Persoon sollPersoon, final BigDecimal aNummerIstPersoon) {
        return aNummerIstPersoon != null && !aNummerIstPersoon.equals(sollPersoon.getAdministratienummer());
    }

    private void valideerDatANummerNogNietBestaat(final BigDecimal administratienummer) {
        final Persoon result = zoekBestaandIngeschrevenPersoon(administratienummer);
        if (result != null) {
            throw new IllegalArgumentException(String.format(
                    "Er bestaat al een ingeschreven persoon met a-nummer %s", administratienummer));
        }
    }

    /**
     * Zoekt naar een bestaand ingeschreven of niet-ingeschreven persoon met het meegegeven a-nummer.
     * 
     * @param sollPersoon
     * @return
     */
    private Persoon bepaalIstPersoon(final BigDecimal administratienummer) {
        Persoon result = zoekBestaandIngeschrevenPersoon(administratienummer);
        if (result == null) {
            result = mergeNietIngeschrevenPersonen(zoekBestaandNietIngeschrevenPersonen(administratienummer));
        }
        return result;
    }

    private Persoon zoekBestaandIngeschrevenPersoon(final BigDecimal administratienummer) {
        LOG.debug("Zoek of er al een ingeschreven persoon bestaat voor a-nummer {}", administratienummer);
        final List<Persoon> ingeschrevenen =
                persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.INGESCHREVENE);
        LOG.debug("Aantal bestaande ingeschreven personen gevonden met a-nummer {}: {}", administratienummer,
                ingeschrevenen.size());
        if (ingeschrevenen.size() > 1) {
            throw new IllegalStateException("Er zijn meerdere ingeschrevenen gevonden voor administrienummer: "
                    + administratienummer);
        }
        Persoon result = null;
        if (ingeschrevenen.size() == 1) {
            result = ingeschrevenen.get(0);
        }
        return result;
    }

    private List<Persoon> zoekBestaandNietIngeschrevenPersonen(final BigDecimal administratienummer) {
        final List<Persoon> result =
                persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.NIET_INGESCHREVENE);
        LOG.debug("Aantal bestaande NIET ingeschreven personen gevonden met a-nummer {}: {}", administratienummer,
                result.size());
        return result;
    }

    private Persoon mergeNietIngeschrevenPersonen(final List<Persoon> nietIngeschrevenPersonen) {
        if (nietIngeschrevenPersonen.isEmpty()) {
            return null;
        }
        final Persoon result = nietIngeschrevenPersonen.get(0);
        for (int index = 1; index < nietIngeschrevenPersonen.size(); index++) {
            final Persoon teMergenPersoon = nietIngeschrevenPersonen.get(index);
            for (final Betrokkenheid ikBetrokkenheid : teMergenPersoon.getBetrokkenheidSet()) {
                teMergenPersoon.removeBetrokkenheid(ikBetrokkenheid);
                result.addBetrokkenheid(ikBetrokkenheid);
                persoonRepository.remove(teMergenPersoon);
            }
        }
        final List<Betrokkenheid> kindBetrokkenheden = new ArrayList<Betrokkenheid>(result.getKindBetrokkenheidSet());
        sorteerBetrokkenhedenOpId(kindBetrokkenheden);
        if (kindBetrokkenheden.size() > 1) {
            // er mag maar 1 kind betrokkenheid zijn -> mergen
            final Betrokkenheid doelKindBetrokkenheid = kindBetrokkenheden.get(0);
            for (int index = 1; index < kindBetrokkenheden.size(); index++) {
                final Betrokkenheid teMergenKindBetrokkenheid = kindBetrokkenheden.get(index);
                final Relatie bronRelatie = teMergenKindBetrokkenheid.getRelatie();
                final Relatie doelRelatie = doelKindBetrokkenheid.getRelatie();
                for (final Betrokkenheid overTeNemenOuderBetrokkenheid : teMergenKindBetrokkenheid
                        .getAndereBetrokkenhedenVanRelatie()) {
                    bronRelatie.removeBetrokkenheid(overTeNemenOuderBetrokkenheid);
                    doelRelatie.addBetrokkenheid(overTeNemenOuderBetrokkenheid);
                }
                result.removeBetrokkenheid(teMergenKindBetrokkenheid);
                relatieRepository.removeRelatie(bronRelatie);
            }
        }
        return result;
    }

    private void sorteerBetrokkenhedenOpId(final List<Betrokkenheid> betrokkenheden) {
        Collections.sort(betrokkenheden, new Comparator<Betrokkenheid>() {

            @Override
            public int compare(final Betrokkenheid b1, final Betrokkenheid b2) {
                return b1.getId().compareTo(b2.getId());
            }

        });
    }

    /**
     * Relateer betrokken persoon van deze betrokkenheid als deze al in de BRP voorkomt.
     * 
     * @param betrokkenheid
     *            de betrokkenheid waarvan geprobeerd moet worden om de persoon te relateren
     */
    private void updateRelatieVanBetrokkenheid(final Betrokkenheid betrokkenheid) {
        if (betrokkenheid.getPersoon() != null && betrokkenheid.getPersoon().getAdministratienummer() != null) {
            final BigDecimal administratienummer = betrokkenheid.getPersoon().getAdministratienummer();
            LOG.debug("Probeer betrokkenheid met a-nummer {} te relateren in de BRP.", administratienummer);
            final List<Persoon> gerelateerdePersonenInDb =
                    persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.INGESCHREVENE);
            final int aantalGevondenPersonen = gerelateerdePersonenInDb.size();
            LOG.debug("Aantal ingeschreven personsen gevonden met a-nummer {}: {}", administratienummer,
                    aantalGevondenPersonen);
            if (aantalGevondenPersonen == 1) {
                LOG.debug("Betrokkenheid gerelateerd met bestaande persoon in de BRP.");
                betrokkenheid.setPersoon(gerelateerdePersonenInDb.get(0));
            }
        }
    }
}
