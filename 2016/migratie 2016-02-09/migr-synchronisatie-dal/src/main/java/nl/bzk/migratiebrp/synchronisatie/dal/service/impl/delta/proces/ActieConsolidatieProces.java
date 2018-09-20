/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.ALaagHistorieVerzameling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractMaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaSubRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.ConsolidatieData;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaStapelMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer.AbstractTransformatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Deze class consolideert wijzigingen die resulteren in het aanmaken van meerdere actie objecten voor hetzelfde oude
 * actie object.
 */
public final class ActieConsolidatieProces implements DeltaProces {

    @Override
    public void bepaalVerschillen(final DeltaBepalingContext context) {
        for (final DeltaRootEntiteitMatch deltaRootEntiteitMatch : context.getDeltaRootEntiteitMatches()) {
            if (deltaRootEntiteitMatch.getVergelijkerResultaat() != null) {
                consolideerActies(deltaRootEntiteitMatch, context);
            }
        }
    }

    @Override
    public void verwerkVerschillen(final DeltaBepalingContext context) {
        // Nothing to do here
    }

    private void consolideerActies(final DeltaRootEntiteitMatch match, final DeltaBepalingContext context) {
        final VergelijkerResultaat vergelijkerResultaat = match.getVergelijkerResultaat();
        final List<VerschilGroep> verschilGroepen = vergelijkerResultaat.getVerschilGroepen();
        final Set<FormeleHistorie> mRijen = bepaalMRijen(verschilGroepen);
        final Set<BRPActie> nieuweActies = bepaalNieuweActies(verschilGroepen);
        final List<Verschil> aanpassingenInNieuweRijen = bepaalNieuweRijAanpassingen(verschilGroepen);

        final ConsolidatieData gefilterdeActieConsolidatieData =
                context.getActieConsolidatieData()
                       .verwijderActieInMRijen(mRijen, aanpassingenInNieuweRijen)
                       .voegNieuweActiesToe(nieuweActies)
                       .verwijderCat07Cat13Acties();
        final Set<DeltaStapelMatch> teVervangenStapels = gefilterdeActieConsolidatieData.bepaalTeVervangenStapels();

        final Map<DeltaStapelMatch, List<Verschil>> teVervangenStapelVerschillen = new HashMap<>();
        try {
            for (final DeltaStapelMatch teVervangenStapel : teVervangenStapels) {
                final List<Verschil> verschillen = new ArrayList<>();
                markeerBestaandeRijenAlsTeVerwijderen(context, vergelijkerResultaat, verschillen, teVervangenStapel);
                markeerNieuweRijenAlsToeTeVoegen(vergelijkerResultaat, verschillen, teVervangenStapel);

                final String groepnaam = teVervangenStapel.getOpgeslagenRijen().iterator().next().getClass().getSimpleName();
                SynchronisatieLogging.addMelding(String.format("%s.%s: stapel verwerkt als re-sync", match, groepnaam));
                context.setBijhoudingOverig();
                teVervangenStapelVerschillen.put(teVervangenStapel, verschillen);
            }
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
        if (!teVervangenStapelVerschillen.isEmpty()) {
            for (final Map.Entry<DeltaStapelMatch, List<Verschil>> entry : teVervangenStapelVerschillen.entrySet()) {
                final DeltaRootEntiteitMatch entiteitMatch = zoekEntiteitMatchVoorDeltaStapelMatch(context, entry.getKey());
                entiteitMatch.getVergelijkerResultaat().voegVerschillenToe(entry.getValue());
            }
        }
    }

    private DeltaRootEntiteitMatch zoekEntiteitMatchVoorDeltaStapelMatch(final DeltaBepalingContext context, final DeltaStapelMatch stapelMatch) {
        final Object eigenaarEntiteit = stapelMatch.getEigenaarDeltaEntiteit();

        DeltaRootEntiteitMatch entiteitMatch = null;
        DeltaRootEntiteit deltaRootEntiteit;

        if (eigenaarEntiteit instanceof DeltaSubRootEntiteit) {
            deltaRootEntiteit = ((DeltaSubRootEntiteit) eigenaarEntiteit).getPersoon();
        } else if (eigenaarEntiteit instanceof DeltaRootEntiteit) {
            deltaRootEntiteit = (DeltaRootEntiteit) eigenaarEntiteit;
        } else {
            throw new IllegalStateException("eigenaar entiteit geen delta (sub)root entiteit");
        }

        for (final DeltaRootEntiteitMatch match : context.getDeltaRootEntiteitMatches()) {
            if (deltaRootEntiteit.equals(match.getBestaandeDeltaRootEntiteit())) {
                entiteitMatch = match;
                break;
            }
        }
        return entiteitMatch;
    }

    private void markeerNieuweRijenAlsToeTeVoegen(
        final VergelijkerResultaat vergelijkerResultaat,
        final List<Verschil> stapelsVervangenVerschillen,
        final DeltaStapelMatch teVervangenStapel) throws ReflectiveOperationException
    {
        for (final FormeleHistorie historie : teVervangenStapel.getNieuweRijen()) {
            // verwijderen van gevonden verschillen waar de nieuwe stapels in voorkomen en de nieuwe stapels als
            // "nieuw-toe-te-voegen-rij"-verschil toevoegen
            vergelijkerResultaat.verwijderVerschillenVoorHistorie(historie);
            final EntiteitSleutel nieuweRijSleutel =
                    SleutelUtil.maakRijSleutel(
                        teVervangenStapel.getEigenaarDeltaEntiteit(),
                        teVervangenStapel.getEigenaarSleutel(),
                        historie,
                        teVervangenStapel.getEigenaarVeld().getName());
            stapelsVervangenVerschillen.add(Verschil.maakConsolidatieVerschilRijToevoegen(nieuweRijSleutel, historie));
        }
    }

    private void markeerBestaandeRijenAlsTeVerwijderen(
        final DeltaBepalingContext context,
        final VergelijkerResultaat vergelijkerResultaat,
        final List<Verschil> stapelsVervangenVerschillen,
        final DeltaStapelMatch teVervangenStapel) throws ReflectiveOperationException
    {
        for (final FormeleHistorie historie : teVervangenStapel.getOpgeslagenRijen()) {
            if (!DeltaUtil.isMRij(historie)) {
                // verwijderen van de verschillen op bestaande stapels en bestaande stapels markeren als M-rijen
                vergelijkerResultaat.verwijderVerschillenVoorHistorie(historie);
                context.verbreekDeltaEntiteitPaar(historie);

                final EntiteitSleutel verwijderdeRijSleutel =
                        SleutelUtil.maakRijSleutel(
                            teVervangenStapel.getEigenaarDeltaEntiteit(),
                            teVervangenStapel.getEigenaarSleutel(),
                            historie,
                            teVervangenStapel.getEigenaarVeld().getName());
                final Verschil historieContextVerschil = Verschil.maakConsolidatieHistorieContextVerschil(verwijderdeRijSleutel, historie);
                stapelsVervangenVerschillen.addAll(
                    AbstractTransformatie.maakMRijVerschillen(
                        historie,
                        AbstractTransformatie.maakRijInhoudSleutel(verwijderdeRijSleutel, historie),
                        historieContextVerschil,
                        context.getActieVervalTbvLeveringMuts(),
                        context));
            }
        }
    }

    private List<Verschil> bepaalNieuweRijAanpassingen(final List<VerschilGroep> verschilGroepen) {
        final List<Verschil> result = new ArrayList<>();

        for (final VerschilGroep verschilGroep : verschilGroepen) {
            for (final Verschil verschil : verschilGroep) {
                if (isVerschilGeschiktVoorConsolidatie(verschil) && VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST.equals(verschil.getVerschilType())) {
                    result.add(verschil);
                }
            }
        }

        return result;
    }

    private Set<BRPActie> bepaalNieuweActies(final List<VerschilGroep> verschilGroepen) {
        final Set<BRPActie> nieuweActies = new HashSet<>();

        for (final VerschilGroep verschilGroep : verschilGroepen) {
            for (final Verschil verschil : verschilGroep) {
                if (isVerschilGeschiktVoorConsolidatie(verschil)) {

                    switch (verschil.getVerschilType()) {
                        case ELEMENT_NIEUW:
                        case ELEMENT_AANGEPAST:
                            if (isNieuweActieVerschil(verschil)) {
                                nieuweActies.add((BRPActie) verschil.getNieuweWaarde());
                            }
                            break;
                        case RIJ_TOEGEVOEGD:
                            if (verschil.getNieuweHistorieRij() != null) {
                                nieuweActies.addAll(verzamelNieuweActies(verschil.getNieuweHistorieRij(), verschilGroep));
                            } else if (verschil.getNieuweWaarde() instanceof ALaagHistorieVerzameling) {
                                verzamelNieuweActies(nieuweActies, (ALaagHistorieVerzameling) verschil.getNieuweWaarde(), verschilGroep);
                            }
                            break;
                        default:
                    }
                }
            }
        }

        return nieuweActies;
    }

    private boolean isNieuweActieVerschil(final Verschil verschil) {
        return isActieVeld(verschil.getSleutel().getVeld()) && ((BRPActie) verschil.getNieuweWaarde()).getId() == null;
    }

    private boolean isVerschilGeschiktVoorConsolidatie(final Verschil verschil) {
        final Class<?> sleutelClass = verschil.getSleutel().getClass();
        return sleutelClass.isAssignableFrom(IstSleutel.class)
               || (sleutelClass.isAssignableFrom(EntiteitSleutel.class) && !verschil.isConsolidatieVerschil());
    }

    private Set<FormeleHistorie> bepaalMRijen(final List<VerschilGroep> verschilGroepen) {
        final Set<FormeleHistorie> mRijen = new HashSet<>();

        for (final VerschilGroep verschilGroep : verschilGroepen) {
            for (final Verschil verschil : verschilGroep) {
                if (isVerschilGeschiktVoorConsolidatie(verschil)
                    && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType())
                    && AbstractFormeleHistorie.INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES.equals(verschil.getSleutel().getVeld()))
                {
                    mRijen.add(verschil.getBestaandeHistorieRij());
                }
            }
        }
        return mRijen;
    }

    private boolean isActieVeld(final String veldNaam) {
        return AbstractFormeleHistorie.ACTIE_INHOUD.equals(veldNaam)
               || AbstractFormeleHistorie.ACTIE_VERVAL.equals(veldNaam)
               || AbstractMaterieleHistorie.ACTIE_AANPASSING_GELDIGHEID.equals(veldNaam);
    }

    private void verzamelNieuweActies(
        final Set<BRPActie> nieuweActies,
        final ALaagHistorieVerzameling historieVerzameling,
        final VerschilGroep verschilGroep)
    {
        for (final Collection<? extends FormeleHistorie> formeleHistorieVerzameling : historieVerzameling.verzamelHistorie().values()) {
            for (final FormeleHistorie historie : formeleHistorieVerzameling) {
                nieuweActies.addAll(verzamelNieuweActies(historie, verschilGroep));
            }
        }
    }

    private Collection<BRPActie> verzamelNieuweActies(final FormeleHistorie historie, final VerschilGroep verschilGroep) {
        final List<BRPActie> nieuweActies = new ArrayList<>(3);

        if (isNieuweActie(historie.getActieInhoud()) && !wordtActieVervangenInNieuweRij(historie, AbstractFormeleHistorie.ACTIE_INHOUD, verschilGroep)) {
            nieuweActies.add(historie.getActieInhoud());
        }

        if (isNieuweActie(historie.getActieVerval()) && !wordtActieVervangenInNieuweRij(historie, AbstractFormeleHistorie.ACTIE_VERVAL, verschilGroep)) {
            nieuweActies.add(historie.getActieVerval());
        }

        if (historie instanceof MaterieleHistorie) {
            final MaterieleHistorie materieleHistorie = (MaterieleHistorie) historie;
            if (isNieuweActie(materieleHistorie.getActieAanpassingGeldigheid())
                && !wordtActieVervangenInNieuweRij(historie, AbstractMaterieleHistorie.ACTIE_AANPASSING_GELDIGHEID, verschilGroep))
            {
                nieuweActies.add(materieleHistorie.getActieAanpassingGeldigheid());
            }
        }

        return nieuweActies;
    }

    private boolean isNieuweActie(final BRPActie actie) {
        return actie != null && actie.getId() == null;
    }

    private boolean wordtActieVervangenInNieuweRij(final FormeleHistorie historie, final String actieVeldnaam, final VerschilGroep verschilGroep) {
        boolean result = false;

        for (final Verschil verschil : verschilGroep) {
            if (isVerschilGeschiktVoorConsolidatie(verschil)
                && VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST.equals(verschil.getVerschilType())
                && PersistenceUtil.getPojoFromObject(historie) == PersistenceUtil.getPojoFromObject(verschil.getNieuweHistorieRij())
                && actieVeldnaam.equals(verschil.getSleutel().getVeld()))
            {
                result = true;
            }
        }

        return result;
    }
}
