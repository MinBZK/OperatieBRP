/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.BinaireRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.repository.BetrokkenheidRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.MultiRealiteitRegelRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.PersoonRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.RelatieRepository;

import org.springframework.stereotype.Component;

/**
 * Deze class biedt extra functionaliteit ter ondersteuning van de PersoonRelateerder.
 * 
 * @see PersoonRelateerder
 */
@Component
public final class PersoonRelateerderHelperImpl implements PersoonRelateerderHelper {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final MultiRealiteitRegelRepository multiRealiteitRegelRepository;
    private final RelatieRepository relatieRepository;
    private final BetrokkenheidRepository betrokkenheidRepository;
    private final PersoonRepository persoonRepository;

    /**
     * Maakt een PersoonRelateerderHelperImpl object.
     * 
     * @param multiRealiteitRegelRepository
     *            de repository voor verwijderen van MultiRealiteitRegels
     * @param relatieRepository
     *            de repository voor het verwijderen van relaties
     * @param betrokkenheidRepository
     *            de repository voor het verwijderen van betrokkenheden
     * @param persoonRepository
     *            de repostitory voor het query-en van persoon gerelateerde gegevens
     */
    @Inject
    public PersoonRelateerderHelperImpl(
            final MultiRealiteitRegelRepository multiRealiteitRegelRepository,
            final RelatieRepository relatieRepository,
            final BetrokkenheidRepository betrokkenheidRepository,
            final PersoonRepository persoonRepository) {
        this.multiRealiteitRegelRepository = multiRealiteitRegelRepository;
        this.relatieRepository = relatieRepository;
        this.betrokkenheidRepository = betrokkenheidRepository;
        this.persoonRepository = persoonRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeRelatiesVoorKind(final Persoon geconverteerdePersoon, final Persoon bestaandPersoon) {
        checkPreconditiesMergeRelaties(geconverteerdePersoon, bestaandPersoon);
        LOG.debug("Mergen van kind relatie voor geconverteerd persoon (anr:{}) en bestaand persoon (anr:{})",
                geconverteerdePersoon.getAdministratienummer(), bestaandPersoon.getAdministratienummer());
        final Set<BinaireRelatie> teVerwijderenBinaireRelaties = new LinkedHashSet<BinaireRelatie>();
        final Set<Betrokkenheid> teOvernemenBetrokkenheden = new LinkedHashSet<Betrokkenheid>();
        final Set<BinaireRelatie> teOntkennenBinaireRelaties =
                new LinkedHashSet<BinaireRelatie>(bestaandPersoon.getBinaireRelatiesVoorKind());
        for (final BinaireRelatie binaireKindRelatie : geconverteerdePersoon.getBinaireRelatiesVoorKind()) {
            final BinaireRelatie gelijkeBinaireRelatie =
                    bestaandPersoon.getGelijkeBinaireRelatieVoorKind(binaireKindRelatie, false);
            if (gelijkeBinaireRelatie == null) {
                LOG.debug("Kind relatie({}) gevonden op geconverteerde persoon die niet bestaat "
                        + "vanuit gerelateerd persoon: MR aanmaken", binaireKindRelatie.getRelatie()
                        .getSoortRelatie());
                MultiRealiteitRegel.maakMultiRealiteitRegel(binaireKindRelatie.inverse());
            } else {
                LOG.debug("Kind relatie({}) gevonden op geconverteerde persoon die "
                        + "ook bestaat vanuit gerelateerd persoon(relatie id:{}).", gelijkeBinaireRelatie
                        .getRelatie().getSoortRelatie(), gelijkeBinaireRelatie.getRelatie().getId());
                final boolean isOuderBetrokkenheidVerschillend =
                        bestaandPersoon.getGelijkeBinaireRelatieVoorKind(binaireKindRelatie, true) == null;
                if (isOuderBetrokkenheidVerschillend) {
                    mergeBinaireRelaties(binaireKindRelatie, gelijkeBinaireRelatie);
                }
                teVerwijderenBinaireRelaties.add(binaireKindRelatie);
                teOvernemenBetrokkenheden.add(gelijkeBinaireRelatie.getIkBetrokkenheid());
                teOntkennenBinaireRelaties.remove(gelijkeBinaireRelatie);
                // eventueel opheffen MR op betrokkenheid ouder
                final MultiRealiteitRegel teVerwijderenMultiRealiteitRegel =
                        gelijkeBinaireRelatie.getAndereBetrokkenheid()
                                .removeMultiRealiteitRegelDieGeldigIsVoorPersoon(bestaandPersoon);
                if (teVerwijderenMultiRealiteitRegel != null) {
                    LOG.debug("Opheffen van MR op bestaande betrokkenheid ouder omdat deze nu erkend wordt.");
                    multiRealiteitRegelRepository.remove(teVerwijderenMultiRealiteitRegel);
                }
            }
        }
        for (final BinaireRelatie teVerwijderenBinaireRelatie : teVerwijderenBinaireRelaties) {
            teVerwijderenBinaireRelatie.getRelatie().removeBetrokkenheid(
                    teVerwijderenBinaireRelatie.getAndereBetrokkenheid());
            if (teVerwijderenBinaireRelatie.getIkBetrokkenheid().getAndereBetrokkenhedenVanRelatie().isEmpty()) {
                geconverteerdePersoon.removeBetrokkenheid(teVerwijderenBinaireRelatie.getIkBetrokkenheid());
            }
        }
        for (final Betrokkenheid teOvernemenBetrokkenheid : teOvernemenBetrokkenheden) {
            overnemenVanBetrokkenheid(bestaandPersoon, geconverteerdePersoon, teOvernemenBetrokkenheid);
        }
        for (final BinaireRelatie binaireRelatie : teOntkennenBinaireRelaties) {
            overnemenVanBetrokkenheid(bestaandPersoon, geconverteerdePersoon, binaireRelatie.getIkBetrokkenheid());
            ontkenBinaireKindOfOuderRelatie(bestaandPersoon, geconverteerdePersoon, binaireRelatie);
        }
        voegDubbeleKindRelatiesSamen(geconverteerdePersoon);
        verwijderOverbodigeOuderRelaties(geconverteerdePersoon);
        verwijderOverbodigeRelaties(geconverteerdePersoon);
    }

    private void mergeBinaireRelaties(
            final BinaireRelatie nieuweBinaireRelatie,
            final BinaireRelatie bestaandeBinaireRelatie) {
        final Betrokkenheid teMergenBetrokkenheid = bestaandeBinaireRelatie.getAndereBetrokkenheid();
        betrokkenheidRepository.overschrijfBetrokkenheid(teMergenBetrokkenheid,
                nieuweBinaireRelatie.getAndereBetrokkenheid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeRelatiesVoorOuder(final Persoon geconverteerdePersoon, final Persoon bestaandPersoon) {
        checkPreconditiesMergeRelaties(geconverteerdePersoon, bestaandPersoon);
        LOG.debug("Mergen van ouder relatie voor geconverteerd persoon (anr:{}) en bestaand persoon (anr:{})",
                geconverteerdePersoon.getAdministratienummer(), bestaandPersoon.getAdministratienummer());
        final Set<BinaireRelatie> teVerwijderenBinaireRelaties = new LinkedHashSet<BinaireRelatie>();
        final Set<Betrokkenheid> teOvernemenBetrokkenheden = new LinkedHashSet<Betrokkenheid>();
        final Set<BinaireRelatie> teOntkennenBinaireRelaties =
                new LinkedHashSet<BinaireRelatie>(bestaandPersoon.getBinaireRelatiesVoorOuder());
        for (final BinaireRelatie binaireOuderRelatie : geconverteerdePersoon.getBinaireRelatiesVoorOuder()) {
            final BinaireRelatie gelijkeBinaireRelatie =
                    bestaandPersoon.getGelijkeBinaireRelatieVoorOuder(binaireOuderRelatie);
            if (gelijkeBinaireRelatie == null) {
                LOG.debug("Ouder relatie({}) gevonden op geconverteerde persoon die niet bestaat vanuit gerelateerd "
                        + "persoon: MR aanmaken", binaireOuderRelatie.getRelatie().getSoortRelatie());
                MultiRealiteitRegel.maakMultiRealiteitRegel(binaireOuderRelatie.inverse());
            } else {
                LOG.debug("Ouder relatie({}) gevonden op geconverteerde persoon "
                        + "die ook bestaat vanuit gerelateerd persoon(relatie id:{}).", gelijkeBinaireRelatie
                        .getRelatie().getSoortRelatie(), gelijkeBinaireRelatie.getRelatie().getId());
                teVerwijderenBinaireRelaties.add(binaireOuderRelatie);
                teOvernemenBetrokkenheden.add(gelijkeBinaireRelatie.getIkBetrokkenheid());
                teOntkennenBinaireRelaties.remove(gelijkeBinaireRelatie);
                // eventueel opheffen MR op betrokkenheid ouder
                final MultiRealiteitRegel teVerwijderenMultiRealiteitRegel =
                        gelijkeBinaireRelatie.getIkBetrokkenheid().removeMultiRealiteitRegelDieGeldigIsVoorPersoon(
                                bestaandPersoon);
                if (teVerwijderenMultiRealiteitRegel != null) {
                    LOG.debug("Opheffen van MR op bestaande betrokkenheid kind omdat deze nu erkend wordt.");
                    multiRealiteitRegelRepository.remove(teVerwijderenMultiRealiteitRegel);
                }
            }
        }
        for (final BinaireRelatie teVerwijderenBinaireRelatie : teVerwijderenBinaireRelaties) {
            teVerwijderenBinaireRelatie.getRelatie().removeBetrokkenheid(
                    teVerwijderenBinaireRelatie.getAndereBetrokkenheid());
            if (teVerwijderenBinaireRelatie.getIkBetrokkenheid().getAndereBetrokkenhedenVanRelatie().isEmpty()) {
                geconverteerdePersoon.removeBetrokkenheid(teVerwijderenBinaireRelatie.getIkBetrokkenheid());
            }
        }
        for (final Betrokkenheid teOvernemenBetrokkenheid : teOvernemenBetrokkenheden) {
            overnemenVanBetrokkenheid(bestaandPersoon, geconverteerdePersoon, teOvernemenBetrokkenheid);
        }
        for (final BinaireRelatie binaireRelatie : teOntkennenBinaireRelaties) {
            overnemenVanBetrokkenheid(bestaandPersoon, geconverteerdePersoon, binaireRelatie.getIkBetrokkenheid());
            ontkenBinaireKindOfOuderRelatie(bestaandPersoon, geconverteerdePersoon, binaireRelatie);
        }
        verwijderOverbodigeRelaties(geconverteerdePersoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeRelatiesVoorHuwelijkOfGp(final Persoon geconverteerdePersoon, final Persoon bestaandPersoon) {
        checkPreconditiesMergeRelaties(geconverteerdePersoon, bestaandPersoon);
        LOG.debug("Mergen van huwelijk/gp voor geconverteerd persoon (anr:{}) en bestaand persoon (anr:{})",
                geconverteerdePersoon.getAdministratienummer(), bestaandPersoon.getAdministratienummer());
        final Set<Betrokkenheid> teVerwijderenBetrokkenheden = new LinkedHashSet<Betrokkenheid>();
        final Set<Betrokkenheid> teOvernemenBetrokkenheden = new LinkedHashSet<Betrokkenheid>();
        final Set<BinaireRelatie> teOntkennenBinaireRelaties =
                new LinkedHashSet<BinaireRelatie>(bestaandPersoon.getBinaireRelatiesVoorHuwelijkOfGp());
        for (final BinaireRelatie binairHuwelijkOfGp : geconverteerdePersoon.getBinaireRelatiesVoorHuwelijkOfGp()) {
            final BinaireRelatie gelijkeBinaireRelatie =
                    bestaandPersoon.getGelijkeBinaireRelatieVoorHuwelijkOfGp(binairHuwelijkOfGp);
            if (gelijkeBinaireRelatie == null) {
                LOG.debug("Relatie({}) gevonden op geconverteerde persoon die niet "
                        + "bestaat vanuit gerelateerd persoon: MR aanmaken", binairHuwelijkOfGp.getRelatie()
                        .getSoortRelatie());
                MultiRealiteitRegel.maakMultiRealiteitRegel(binairHuwelijkOfGp.inverse());
            } else {
                LOG.debug("Relatie({}) gevonden op geconverteerde persoon die ook "
                        + "bestaat vanuit gerelateerd persoon(relatie id:{}).", gelijkeBinaireRelatie.getRelatie()
                        .getSoortRelatie(), gelijkeBinaireRelatie.getRelatie().getId());
                teVerwijderenBetrokkenheden.add(binairHuwelijkOfGp.getIkBetrokkenheid());
                teOvernemenBetrokkenheden.add(gelijkeBinaireRelatie.getIkBetrokkenheid());
                teOntkennenBinaireRelaties.remove(gelijkeBinaireRelatie);
                // eventueel opheffen MR
                final MultiRealiteitRegel teVerwijderenMultiRealiteitRegel =
                        gelijkeBinaireRelatie.getRelatie().removeMultiRealiteitRegelDieGeldigIsVoorPersoon(
                                bestaandPersoon);
                if (teVerwijderenMultiRealiteitRegel != null) {
                    LOG.debug("Opheffen van MR op bestaande relatie omdat deze nu erkend wordt.");
                    multiRealiteitRegelRepository.remove(teVerwijderenMultiRealiteitRegel);
                }
            }
        }
        for (final Betrokkenheid teVerwijderenBetrokkenheid : teVerwijderenBetrokkenheden) {
            geconverteerdePersoon.removeBetrokkenheid(teVerwijderenBetrokkenheid);
        }
        for (final Betrokkenheid teOvernemenBetrokkenheid : teOvernemenBetrokkenheden) {
            overnemenVanBetrokkenheid(bestaandPersoon, geconverteerdePersoon, teOvernemenBetrokkenheid);
        }
        for (final BinaireRelatie binairHuwelijkOfGp : teOntkennenBinaireRelaties) {
            overnemenVanBetrokkenheid(bestaandPersoon, geconverteerdePersoon, binairHuwelijkOfGp.getIkBetrokkenheid());
            ontkenBinairHuwelijkOfGp(bestaandPersoon, geconverteerdePersoon, binairHuwelijkOfGp);
        }
        verwijderOverbodigeRelaties(geconverteerdePersoon);
    }

    private void voegDubbeleKindRelatiesSamen(final Persoon geconverteerdePersoon) {
        Betrokkenheid bestaandeKindBetrokkenheid = null;
        Betrokkenheid nieuweKindBetrokkenheid = null;

        for (final Betrokkenheid kindBetrokkenheid : geconverteerdePersoon.getKindBetrokkenheidSet()) {
            if (kindBetrokkenheid.getId() != null) {
                bestaandeKindBetrokkenheid = kindBetrokkenheid;
            } else {
                nieuweKindBetrokkenheid = kindBetrokkenheid;
            }
        }
        if (bestaandeKindBetrokkenheid != null && nieuweKindBetrokkenheid != null) {
            final Set<Betrokkenheid> nieuweOuderBetrokkenheden =
                    new LinkedHashSet<Betrokkenheid>(nieuweKindBetrokkenheid.getAndereBetrokkenhedenVanRelatie());
            for (final Betrokkenheid nieuweOuderBetrokkenheid : nieuweOuderBetrokkenheden) {
                nieuweKindBetrokkenheid.getRelatie().removeBetrokkenheid(nieuweOuderBetrokkenheid);
                bestaandeKindBetrokkenheid.getRelatie().addBetrokkenheid(nieuweOuderBetrokkenheid);
            }
            geconverteerdePersoon.removeBetrokkenheid(nieuweKindBetrokkenheid);
        }
    }

    private void verwijderOverbodigeOuderRelaties(final Persoon kind) {
        for (final Betrokkenheid kindBetrokkenheid : kind.getKindBetrokkenheidSet()) {
            for (final Betrokkenheid ouderBetrokkenheid : kindBetrokkenheid.getAndereBetrokkenhedenVanRelatie()) {
                final Persoon ouder = ouderBetrokkenheid.getPersoon();
                final MultiRealiteitRegel kindMR =
                        ouderBetrokkenheid.getMultiRealiteitRegelDieGeldigIsVoorPersoon(kind);
                MultiRealiteitRegel ouderMR = null;
                if (ouder != null) {
                    ouderMR = ouderBetrokkenheid.getMultiRealiteitRegelDieGeldigIsVoorPersoon(ouder);
                }
                final boolean kindOntkendOuder = kindMR != null;
                final boolean ouderIsNietIngeschreven =
                        ouder == null || ouder.getSoortPersoon().equals(SoortPersoon.NIET_INGESCHREVENE);
                final boolean ouderOntkendKind = ouderMR != null;

                if (kindOntkendOuder && (ouderIsNietIngeschreven || ouderOntkendKind)) {
                    kind.removeMultiRealiteitRegelGeldigVoorPersoon(kindMR);
                    ouderBetrokkenheid.removeMultiRealiteitRegel(kindMR);
                    multiRealiteitRegelRepository.remove(kindMR);
                    if (ouderOntkendKind) {
                        ouder.removeMultiRealiteitRegelGeldigVoorPersoon(ouderMR);
                        ouderBetrokkenheid.removeMultiRealiteitRegel(ouderMR);
                        multiRealiteitRegelRepository.remove(ouderMR);
                    }
                    if (ouder != null) {
                        ouder.removeBetrokkenheid(ouderBetrokkenheid);
                    }
                    kindBetrokkenheid.getRelatie().removeBetrokkenheid(ouderBetrokkenheid);
                    betrokkenheidRepository.remove(ouderBetrokkenheid);
                    if (ouder != null && ouderIsNietIngeschreven) {
                        persoonRepository.remove(ouder);
                    }
                }
            }
        }
    }

    private void verwijderOverbodigeRelaties(final Persoon geconverteerdePersoon) {
        for (final Relatie relatie : geconverteerdePersoon.getRelaties()) {
            if (relatie.isDoorAlleBetrokkenPersonenOntkend()) {
                relatieRepository.removeRelatie(relatie);
            }
        }
    }

    private void ontkenBinaireKindOfOuderRelatie(
            final Persoon bronPersoon,
            final Persoon doelPersoon,
            final BinaireRelatie binaireKindOfOuderRelatie) {
        MultiRealiteitRegel multiRealiteitRegel;
        if (binaireKindOfOuderRelatie.getIkBetrokkenheid().getSoortBetrokkenheid().equals(SoortBetrokkenheid.KIND)) {
            multiRealiteitRegel =
                    binaireKindOfOuderRelatie.getAndereBetrokkenheid().getMultiRealiteitRegelDieGeldigIsVoorPersoon(
                            bronPersoon);
        } else {
            multiRealiteitRegel =
                    binaireKindOfOuderRelatie.getIkBetrokkenheid().getMultiRealiteitRegelDieGeldigIsVoorPersoon(
                            bronPersoon);
        }

        if (multiRealiteitRegel != null) {
            bronPersoon.removeMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
            doelPersoon.addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
        } else {
            MultiRealiteitRegel.maakMultiRealiteitRegel(binaireKindOfOuderRelatie);
        }
    }

    private void ontkenBinairHuwelijkOfGp(
            final Persoon bronPersoon,
            final Persoon doelPersoon,
            final BinaireRelatie binairHuwelijkOfGp) {
        final MultiRealiteitRegel multiRealiteitRegel =
                binairHuwelijkOfGp.getRelatie().getMultiRealiteitRegelDieGeldigIsVoorPersoon(bronPersoon);
        if (multiRealiteitRegel != null) {
            bronPersoon.removeMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
            doelPersoon.addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
        } else {
            MultiRealiteitRegel.maakMultiRealiteitRegel(binairHuwelijkOfGp);
        }
    }

    private void overnemenVanBetrokkenheid(
            final Persoon bronPersoon,
            final Persoon doelPersoon,
            final Betrokkenheid teOvernemenBetrokkenheid) {
        bronPersoon.removeBetrokkenheid(teOvernemenBetrokkenheid);
        doelPersoon.addBetrokkenheid(teOvernemenBetrokkenheid);
    }

    private void checkPreconditiesMergeRelaties(final Persoon geconverteerdePersoon, final Persoon bestaandPersoon) {
        if (geconverteerdePersoon == null) {
            throw new NullPointerException("geconverteerdePersoon mag niet null zijn");
        }
        if (bestaandPersoon == null) {
            throw new NullPointerException("bestaandPersoon mag niet null zijn");
        }
        if (geconverteerdePersoon.getAdministratienummer() == null) {
            throw new IllegalArgumentException("geconverteerdePersoon moet een a-nummer hebben");
        }
        if (bestaandPersoon.getAdministratienummer() == null) {
            throw new IllegalArgumentException("bestaandPersoon moet een a-nummer hebben");
        }
        if (!bestaandPersoon.getAdministratienummer().equals(geconverteerdePersoon.getAdministratienummer())) {
            throw new IllegalArgumentException(String.format(
                    "de a-nummers van de te mergen personen zijn niet gelijk (geconverteerd:%s, bestaand:%s",
                    geconverteerdePersoon.getAdministratienummer(), bestaandPersoon.getAdministratienummer()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeRelatiesVoorGerelateerdeKinderen(final Persoon geconverteerdePersoon) {
        for (final BinaireRelatie binaireOuderRelatie : geconverteerdePersoon.getBinaireRelatiesVoorOuder()) {
            final Betrokkenheid kindBetrokkenheid = binaireOuderRelatie.getAndereBetrokkenheid();
            final boolean isKindBetrokkenheidNieuw = kindBetrokkenheid.getId() == null;
            if (isKindBetrokkenheidNieuw) {
                final Persoon kind = binaireOuderRelatie.getAndereBetrokkenheid().getPersoon();
                final Betrokkenheid bestaandeKindBetrokkenheid = getBestaandeKindBetrokkenheid(kind);
                if (bestaandeKindBetrokkenheid != null) {
                    final Betrokkenheid ouderBetrokkenheid = binaireOuderRelatie.getIkBetrokkenheid();
                    binaireOuderRelatie.getRelatie().removeBetrokkenheid(ouderBetrokkenheid);
                    bestaandeKindBetrokkenheid.getRelatie().addBetrokkenheid(ouderBetrokkenheid);
                }
            }
        }
    }

    private Betrokkenheid getBestaandeKindBetrokkenheid(final Persoon kind) {
        Betrokkenheid result = null;
        for (final BinaireRelatie binaireKindRelatie : kind.getBinaireRelatiesVoorKind()) {
            if (binaireKindRelatie.getIkBetrokkenheid().getId() != null) {
                result = binaireKindRelatie.getIkBetrokkenheid();
                break;
            }
        }
        return result;
    }
}
