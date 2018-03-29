/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ALaagHistorieVerzameling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Afleidbaar;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DynamischeStamtabel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaStapelMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.HistorieContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;

/**
 * Abstract implementatie voor de vergelijker implementaties voor deltabepaling. Deze class bevat de logica om d.m.v.
 * reflectie de verschillen uit 2 entiteiten te bepalen.
 */
abstract class AbstractDeltaVergelijker implements DeltaVergelijker {

    private static final String DATUM_TIJD_REGISTRATIE = "datumTijdRegistratie";
    private static final String DATUM_TIJD_VERVAL = "datumTijdVerval";
    private static final Long SECONDE_MILLIS = 1000L;
    private static final int INT_2 = 2;
    private final DeltaRootEntiteitModus modus;

    /**
     * Maakt een vergelijker en zet de modus waar deze vergelijker in werkt.
     * @param modus een {@link DeltaRootEntiteitModus} waarmee aangegeven wordt voor welke {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit} deze
     * vergelijker werkt.
     */

    AbstractDeltaVergelijker(final DeltaRootEntiteitModus modus) {
        this.modus = modus;
    }

    /**
     * Zoekt de verschillen tussen 2 verschillende versies van hetzelfde entiteit.
     * @param result Vergelijker resultaat object waarin de verschillen worden opgeslagen
     * @param bestaandeDeltaEntiteit bestaande versie van de entiteit
     * @param nieuweDeltaEntiteit nieuwe versie van de entiteit
     * @param eigenaar eigenaar class van de entiteit, null als de entiteit een top-level entiteit is (bv persoon of relatie)
     * @param eigenaarSleutel sleutel van de eigenaar
     * @throws ReflectiveOperationException wordt gegooid als er een probleem ontstaan is met reflectie
     */
    final void zoekVerschillen(
            final VergelijkerResultaat result,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final Entiteit eigenaar,
            final EntiteitSleutel eigenaarSleutel) throws ReflectiveOperationException {
        // A-laag moet niet vergeleken worden aangezien de afleiding van deze laag pas gebeurd bij opslag. Daarom wordt
        // de bestaande A-laag nu eerst leeg gemaakt.
        if (bestaandeDeltaEntiteit instanceof Afleidbaar) {
            ALaagAfleidingsUtil.leegALaag((Afleidbaar) bestaandeDeltaEntiteit);
        }
        zoekVerschillen(result, bestaandeDeltaEntiteit, nieuweDeltaEntiteit, eigenaar, eigenaarSleutel, null);
    }

    private void zoekVerschillen(
            final VergelijkerResultaat result,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final Entiteit eigenaar,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final Entiteit bestaandePojoDeltaEntiteit = Entiteit.convertToPojo(bestaandeDeltaEntiteit);
        final Entiteit nieuwePojoDeltaEntiteit = Entiteit.convertToPojo(nieuweDeltaEntiteit);
        final HistorieContext entiteitHistorieContext =
                HistorieContext.bepaalNieuweHistorieContext(historieContext, bestaandePojoDeltaEntiteit, nieuwePojoDeltaEntiteit);

        maakDeltaEntiteitPaar(result, bestaandePojoDeltaEntiteit, nieuwePojoDeltaEntiteit);
        for (final Field veld : DeltaUtil.getDeclaredEntityFields(bestaandePojoDeltaEntiteit.getClass())) {
            veld.setAccessible(true);
            final List<Class<?>> skippableClasses = getSkippableEntiteiten();
            if (!DeltaUtil.isSkipableVeld(veld, bestaandePojoDeltaEntiteit, eigenaar, skippableClasses)
                    && !DeltaUtil.isSkipableVeld(veld, nieuwePojoDeltaEntiteit, eigenaar, skippableClasses)) {
                if (DeltaUtil.isWaardeVeld(veld)) {
                    // simpel waarde veld
                    bepaalVerschillenWaardeVeld(
                            result,
                            veld,
                            bestaandePojoDeltaEntiteit,
                            nieuwePojoDeltaEntiteit,
                            eigenaarSleutel,
                            entiteitHistorieContext);
                } else if (DeltaUtil.isDynamischeStamtabelVeld(veld)) {
                    // referentie naar een dynamische stamtabel
                    bepaalVerschillenStamtabelVeld(
                            result,
                            veld,
                            bestaandePojoDeltaEntiteit,
                            nieuwePojoDeltaEntiteit,
                            eigenaarSleutel,
                            entiteitHistorieContext);
                } else if (DeltaUtil.isEntiteitReferentieVeld(eigenaar, veld)) {
                    // referentie naar een andere entiteit
                    bepaalVerschillenEntiteitVeld(
                            result,
                            veld,
                            bestaandePojoDeltaEntiteit,
                            nieuwePojoDeltaEntiteit,
                            eigenaarSleutel,
                            entiteitHistorieContext);
                } else if (DeltaUtil.isEntiteitVerzamelingVeld(veld)) {
                    verwerkVerzamelingen(result, eigenaarSleutel, bestaandePojoDeltaEntiteit, nieuwePojoDeltaEntiteit, entiteitHistorieContext, veld);

                }
            }
        }

        herbruikActieInhoudVoorVervallenRij(result, bestaandePojoDeltaEntiteit, nieuwePojoDeltaEntiteit, eigenaarSleutel, entiteitHistorieContext);
    }

    private void verwerkVerzamelingen(VergelijkerResultaat result, EntiteitSleutel eigenaarSleutel, Entiteit bestaandePojoDeltaEntiteit,
                                      Entiteit nieuwePojoDeltaEntiteit, HistorieContext entiteitHistorieContext, Field veld)
            throws ReflectiveOperationException {
        // verwerk verzamelingen
        // noinspection unchecked
        final Collection<Entiteit> bestaandeVeldWaarde = (Collection<Entiteit>) veld.get(bestaandePojoDeltaEntiteit);
        // noinspection unchecked
        final Collection<Entiteit> nieuweVeldWaarde = (Collection<Entiteit>) veld.get(nieuwePojoDeltaEntiteit);
        if (!bestaandeVeldWaarde.isEmpty() || !nieuweVeldWaarde.isEmpty()) {
            bepaalVerschillenCollection(
                    result,
                    veld,
                    bestaandeVeldWaarde,
                    nieuweVeldWaarde,
                    bestaandePojoDeltaEntiteit,
                    eigenaarSleutel,
                    entiteitHistorieContext);
        }
    }

    private void maakDeltaEntiteitPaar(
            final VergelijkerResultaat result,
            final Entiteit bestaandePojoDeltaEntiteit,
            final Entiteit nieuwePojoDeltaEntiteit) {
        result.addDeltaEntiteitPaar(bestaandePojoDeltaEntiteit, nieuwePojoDeltaEntiteit);
        if (bestaandePojoDeltaEntiteit instanceof Betrokkenheid && nieuwePojoDeltaEntiteit instanceof Betrokkenheid) {
            // Betrokkenheid historie wordt geskipped. Wel is er een koppeling nodig voor onderzoek op 86.10. Vandaar
            // dat er handmatig een koppeling wordt gelegd. Er is altijd maar 1 historie.
            result.addDeltaEntiteitPaar(
                    ((Betrokkenheid) bestaandePojoDeltaEntiteit).getBetrokkenheidHistorieSet().iterator().next(),
                    ((Betrokkenheid) nieuwePojoDeltaEntiteit).getBetrokkenheidHistorieSet().iterator().next());
        }
    }

    private void bepaalVerschillenWaardeVeld(
            final VergelijkerResultaat result,
            final Field veld,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final Object bestaandeWaarde = veld.get(bestaandeDeltaEntiteit);
        final Object nieuweWaarde = veld.get(nieuweDeltaEntiteit);
        final boolean verschilGevonden;

        if ((DATUM_TIJD_REGISTRATIE.equals(veld.getName()) || DATUM_TIJD_VERVAL.equals(veld.getName()))
                && bestaandeWaarde instanceof Timestamp
                && nieuweWaarde instanceof Timestamp) {
            final long bestaandeTijd = ((Timestamp) bestaandeWaarde).getTime();
            final long nieuweTijd = ((Timestamp) nieuweWaarde).getTime();
            verschilGevonden = bestaandeTijd - bestaandeTijd % SECONDE_MILLIS != nieuweTijd - nieuweTijd % SECONDE_MILLIS;
        } else {
            verschilGevonden = !Objects.equals(bestaandeWaarde, nieuweWaarde);
        }

        if (verschilGevonden) {
            result.voegToeOfVervangVerschil(
                    new Verschil(
                            SleutelUtil.maakSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, veld),
                            bestaandeWaarde,
                            nieuweWaarde,
                            historieContext.getBestaandeHistorieRij(),
                            historieContext.getNieuweHistorieRij()));
        }
    }

    private void bepaalVerschillenStamtabelVeld(
            final VergelijkerResultaat result,
            final Field veld,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final DynamischeStamtabel bestaandeWaarde = (DynamischeStamtabel) veld.get(bestaandeDeltaEntiteit);
        final Number bestaandeId = bestaandeWaarde != null ? bestaandeWaarde.getId() : null;
        final DynamischeStamtabel nieuweWaarde = (DynamischeStamtabel) veld.get(nieuweDeltaEntiteit);
        final Number nieuweId = nieuweWaarde != null ? nieuweWaarde.getId() : null;
        if (!Objects.equals(bestaandeId, nieuweId)) {
            result.voegToeOfVervangVerschil(
                    new Verschil(
                            SleutelUtil.maakSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, veld),
                            bestaandeWaarde,
                            nieuweWaarde,
                            historieContext.getBestaandeHistorieRij(),
                            historieContext.getNieuweHistorieRij()));
        }
    }

    private void bepaalVerschillenEntiteitVeld(
            final VergelijkerResultaat result,
            final Field veld,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        if (DeltaUtil.isVeldAdministratieveHandelingVeld(veld) || DeltaUtil.isVeldTbvLeveringMutaties(veld)) {
            return;
        }

        final Entiteit bestaandeWaarde = (Entiteit) Entiteit.convertToPojo(veld.get(bestaandeDeltaEntiteit));
        final Entiteit nieuweWaarde = (Entiteit) Entiteit.convertToPojo(veld.get(nieuweDeltaEntiteit));
        final EntiteitSleutel bestaandeEntiteitSleutel = SleutelUtil.maakSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, veld);

        if (bestaandeWaarde instanceof BRPActie || nieuweWaarde instanceof BRPActie) {
            bepaalVerschillenActie(
                    result,
                    (BRPActie) bestaandeWaarde,
                    (BRPActie) nieuweWaarde,
                    bestaandeEntiteitSleutel,
                    bestaandeDeltaEntiteit,
                    nieuweDeltaEntiteit,
                    historieContext);
        } else {
            if (bestaandeWaarde != null) {
                if (nieuweWaarde != null) {
                    zoekVerschillen(result, bestaandeWaarde, nieuweWaarde, bestaandeDeltaEntiteit, bestaandeEntiteitSleutel, historieContext);
                } else {
                    result.voegToeOfVervangVerschil(
                            new Verschil(
                                    bestaandeEntiteitSleutel,
                                    bestaandeWaarde,
                                    null,
                                    historieContext.getBestaandeHistorieRij(),
                                    historieContext.getNieuweHistorieRij()));
                }
            } else if (nieuweWaarde != null) {
                result.voegToeOfVervangVerschil(
                        new Verschil(
                                bestaandeEntiteitSleutel,
                                null,
                                nieuweWaarde,
                                historieContext.getBestaandeHistorieRij(),
                                historieContext.getNieuweHistorieRij()));
            }
        }
    }

    private void bepaalVerschillenActie(
            final VergelijkerResultaat result,
            final BRPActie bestaandeActie,
            final BRPActie nieuweActie,
            final EntiteitSleutel bestaandeEntiteitSleutel,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final boolean zijnBeideActiesAanwezig = bestaandeActie != null && nieuweActie != null;
        final boolean alleenNieuweActie = bestaandeActie == null && nieuweActie != null;
        final boolean alleenBestaandeActie = bestaandeActie != null && nieuweActie == null;

        if (zijnBeideActiesAanwezig) {
            if (zijnBRPActiesVerschillend(bestaandeActie, nieuweActie)) {
                result.voegToeOfVervangVerschil(
                        new Verschil(
                                bestaandeEntiteitSleutel,
                                bestaandeActie,
                                nieuweActie,
                                historieContext.getBestaandeHistorieRij(),
                                historieContext.getNieuweHistorieRij()));
                if (bestaandeActie.isCat13Actie()) {
                    // Controleer of er behalve de actie ook nog documentatie anders is. Anders wordt dit verschil
                    // mogelijk genegeerd
                    controleerExtraVerschillenCat13(
                            result,
                            bestaandeActie,
                            nieuweActie,
                            bestaandeEntiteitSleutel,
                            bestaandeDeltaEntiteit,
                            historieContext);
                }
            } else {
                bewaarActieHerkomstKoppeling(result, bestaandeActie, nieuweActie);
                bewaarActieActieKoppeling(result, bestaandeActie, nieuweActie);
                zoekVerschillen(result, bestaandeActie, nieuweActie, bestaandeDeltaEntiteit, bestaandeEntiteitSleutel, historieContext);
            }
        } else if (alleenBestaandeActie) {
            result.voegToeOfVervangVerschil(
                    new Verschil(
                            bestaandeEntiteitSleutel,
                            bestaandeActie,
                            null,
                            historieContext.getBestaandeHistorieRij(),
                            historieContext.getNieuweHistorieRij()));
            bewaarActieHerkomstKoppeling(result, bestaandeActie, null);
            bewaarActieActieKoppeling(result, bestaandeActie, null);
        } else if (alleenNieuweActie) {
            result.voegToeOfVervangVerschil(
                    new Verschil(
                            bestaandeEntiteitSleutel,
                            null,
                            nieuweActie,
                            historieContext.getBestaandeHistorieRij(),
                            historieContext.getNieuweHistorieRij()));
        }

        bewaarActieVoorkomenKoppeling(result, bestaandeDeltaEntiteit, nieuweDeltaEntiteit, bestaandeActie, nieuweActie);
    }

    private void controleerExtraVerschillenCat13(
            final VergelijkerResultaat result,
            final BRPActie bestaandeActie,
            final BRPActie nieuweActie,
            final EntiteitSleutel bestaandeEntiteitSleutel,
            final Entiteit bestaandeDeltaEntiteit,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final VergelijkerResultaat extraControle = new DeltaVergelijkerResultaat();
        zoekVerschillen(extraControle, bestaandeActie, nieuweActie, bestaandeDeltaEntiteit, bestaandeEntiteitSleutel, historieContext);
        if (extraControle.getVerschillen().size() > INT_2) {
            // Er zijn altijd 2 verschillen mbt aangepast datum/tijd registratie (document en actie). Als er meer dan 2
            // verschillen zijn, dan is er iets gewijzigd in de documentatie van cat13.
            result.voegVerschillenToe(extraControle.getVerschillen());
        }
    }

    private boolean zijnBRPActiesVerschillend(final BRPActie bestaandeWaarde, final BRPActie nieuweWaarde) {
        final Timestamp datumTijdRegistratieOud = bestaandeWaarde.getDatumTijdRegistratie();
        final Timestamp datumTijdRegistratieNieuw = nieuweWaarde.getDatumTijdRegistratie();
        return !datumTijdRegistratieOud.equals(datumTijdRegistratieNieuw);
    }

    private void bewaarActieActieKoppeling(final VergelijkerResultaat result, final BRPActie oudeActie, final BRPActie nieuweActie) {
        if (nieuweActie != null && oudeActie != null) {
            result.koppelNieuweActieMetBestaandeActie(nieuweActie, oudeActie);
        }
    }

    private void bewaarActieVoorkomenKoppeling(
            final VergelijkerResultaat result,
            final Object bestaandeEntiteit,
            final Object nieuweEntiteit,
            final BRPActie bestaandeActie,
            final BRPActie nieuweActie) {
        if (nieuweActie != null && nieuweEntiteit instanceof FormeleHistorie) {
            result.koppelVoorkomenAanActie(nieuweActie, (FormeleHistorie) nieuweEntiteit);
        }

        if (bestaandeActie != null && bestaandeEntiteit instanceof FormeleHistorie) {
            result.koppelVoorkomenAanActie(bestaandeActie, (FormeleHistorie) bestaandeEntiteit);
        }
    }

    private void bewaarActieHerkomstKoppeling(final VergelijkerResultaat result, final BRPActie bestaandeActie, final BRPActie nieuweActie) {
        final Lo3Voorkomen nieuweHerkomst = nieuweActie != null ? nieuweActie.getLo3Voorkomen() : null;
        result.koppelHerkomstAanActie(bestaandeActie, nieuweHerkomst);
    }

    private void bepaalVerschillenCollection(
            final VergelijkerResultaat result,
            final Field veld,
            final Collection<Entiteit> bestaandeCollection,
            final Collection<Entiteit> nieuweCollection,
            final Entiteit bestaandeDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final String veldName = veld.getName();
        switch (veldName) {
            case Persoon.PERSOON_REISDOCUMENT_SET:
                bepaalVerschillenReisdocumenten(result, veld, bestaandeCollection, nieuweCollection, bestaandeDeltaEntiteit, eigenaarSleutel, historieContext);
                break;
            case Persoon.PERSOON_NAAMGEBRUIK_HISTORIE_SET:
                bepaalVerschillenNaamgebruikHistorie(result, veld, bestaandeCollection, nieuweCollection, bestaandeDeltaEntiteit, eigenaarSleutel,
                        historieContext);
                break;
            default:
                bepaalVerschillenOverigeCollections(result, veld, bestaandeCollection, nieuweCollection, bestaandeDeltaEntiteit, eigenaarSleutel,
                        historieContext);
                break;
        }
    }

    private void bepaalVerschillenNaamgebruikHistorie(
            final VergelijkerResultaat result,
            final Field veld,
            final Collection<Entiteit> bestaandeCollectie,
            final Collection<Entiteit> nieuweCollectie,
            final Entiteit bestaandeDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final Set<PersoonNaamgebruikHistorie> bestaandeHistorie = converteerNaarNaamgebruikHistorie(bestaandeCollectie);
        final Set<PersoonNaamgebruikHistorie> nieuweHistorie = converteerNaarNaamgebruikHistorie(nieuweCollectie);

        // Naamgebruik historie kan nooit leeg zijn of worden.
        // Alleen wijzigen qua aantal (nieuwe cat01) of qua inhoud (nieuw cat05)
        if (bestaandeHistorie.size() != nieuweHistorie.size()) {
            // Cat01 is gewijzigd. Het maakt niet uit of cat05 is toegevoegd of niet. De 'normale' flow volgen.
            bepaalVerschillenOverigeCollections(
                    result,
                    veld,
                    bestaandeCollectie,
                    nieuweCollectie,
                    bestaandeDeltaEntiteit,
                    eigenaarSleutel,
                    historieContext);
        } else {
            // Of er is niets veranderd, of er is een andere cat05 gekozen tijdens conversie om de naamgebruik mee af te
            // leiden.
            final VergelijkerResultaat controleResultaat = new DeltaVergelijkerResultaat();
            bepaalVerschillenOverigeCollections(
                    controleResultaat,
                    veld,
                    bestaandeCollectie,
                    nieuweCollectie,
                    bestaandeDeltaEntiteit,
                    eigenaarSleutel,
                    historieContext);
            result.addVergelijkerResultaat(controleResultaat);
            if (controleResultaat.isLeeg()) {
                // Niets veranderd, maar misschien wel in cat05 ivm met naamgebruik
                final PersoonNaamgebruikHistorie bestaandeActueleVoorkomen =
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandeHistorie);
                final PersoonNaamgebruikHistorie nieuweActueleVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweHistorie);
                final EntiteitSleutel rijEigenaarSleutel =
                        SleutelUtil.maakRijSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, bestaandeActueleVoorkomen, veld.getName());
                if (!Objects.equals(
                        bestaandeActueleVoorkomen.getGeslachtsnaamstamNaamgebruik(),
                        nieuweActueleVoorkomen.getGeslachtsnaamstamNaamgebruik())) {
                    result.voegToeOfVervangVerschil(
                            new Verschil(
                                    new EntiteitSleutel(bestaandeActueleVoorkomen.getClass(), PersoonNaamgebruikHistorie.GESLACHTSNAAMSTAM, rijEigenaarSleutel),
                                    bestaandeActueleVoorkomen.getGeslachtsnaamstamNaamgebruik(),
                                    nieuweActueleVoorkomen.getGeslachtsnaamstamNaamgebruik(),
                                    bestaandeActueleVoorkomen,
                                    nieuweActueleVoorkomen));
                }
                if (!Objects.equals(bestaandeActueleVoorkomen.getVoorvoegselNaamgebruik(), nieuweActueleVoorkomen.getVoorvoegselNaamgebruik())) {
                    result.voegToeOfVervangVerschil(
                            new Verschil(
                                    new EntiteitSleutel(bestaandeActueleVoorkomen.getClass(), PersoonNaamgebruikHistorie.VOORVOEGSEL, rijEigenaarSleutel),
                                    bestaandeActueleVoorkomen.getVoorvoegselNaamgebruik(),
                                    nieuweActueleVoorkomen.getVoorvoegselNaamgebruik(),
                                    bestaandeActueleVoorkomen,
                                    nieuweActueleVoorkomen));
                }
                if (!Objects.equals(bestaandeActueleVoorkomen.getScheidingstekenNaamgebruik(), nieuweActueleVoorkomen.getScheidingstekenNaamgebruik())) {
                    result.voegToeOfVervangVerschil(
                            new Verschil(
                                    new EntiteitSleutel(bestaandeActueleVoorkomen.getClass(), PersoonNaamgebruikHistorie.SCHEIDINGSTEKEN, rijEigenaarSleutel),
                                    bestaandeActueleVoorkomen.getScheidingstekenNaamgebruik(),
                                    nieuweActueleVoorkomen.getScheidingstekenNaamgebruik(),
                                    bestaandeActueleVoorkomen,
                                    nieuweActueleVoorkomen));
                }
            }
        }
    }

    private void bepaalVerschillenOverigeCollections(
            final VergelijkerResultaat result,
            final Field veld,
            final Collection<Entiteit> bestaandeCollection,
            final Collection<Entiteit> nieuweCollection,
            final Entiteit bestaandeDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        if (nieuweCollection.isEmpty()) {
            // alle rijen uit de bestaande lijst verwijderd
            markeerCollectieAlsVerwijderd(result, veld, bestaandeCollection, bestaandeDeltaEntiteit, eigenaarSleutel, historieContext);
        } else {
            // Controleren welke rijen zijn toegevoegd/verwijderd dan wel gewijzigd zijn
            final Map<EntiteitSleutel, Entiteit> nieuweRijen = new HashMap<>(nieuweCollection.size());
            for (final Entiteit rij : nieuweCollection) {
                final Entiteit rijPojo = Entiteit.convertToPojo(rij);
                nieuweRijen.put(SleutelUtil.maakRijSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, rijPojo, veld.getName()), rijPojo);
            }

            final DeltaStapelMatch stapelsMatch = bepaalStapelMatch(veld, bestaandeCollection, nieuweCollection, bestaandeDeltaEntiteit, eigenaarSleutel);

            final List<Entiteit> bestaandeRijen = new LinkedList<>(bestaandeCollection);
            for (final Entiteit rij : bestaandeRijen) {
                final Entiteit pojoRij = Entiteit.convertToPojo(rij);
                if (!DeltaUtil.isMRij(pojoRij)) {
                    final EntiteitSleutel sleutel = SleutelUtil.maakRijSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, pojoRij, veld.getName());
                    if (nieuweRijen.containsKey(sleutel)) {
                        final Entiteit nieuweRij = nieuweRijen.get(sleutel);
                        zoekVerschillen(result, pojoRij, nieuweRij, bestaandeDeltaEntiteit, sleutel, historieContext);
                        nieuweRijen.remove(sleutel);

                        bewaarStapelMatch(result, stapelsMatch, pojoRij, nieuweRij);
                    } else if (pojoRij instanceof ALaagHistorieVerzameling) {
                        final Set<Verschil> verschillen =
                                DeltaUtil.bepaalVerschillenVoorVerwijderdeALaagEntiteit((ALaagHistorieVerzameling) pojoRij, sleutel, historieContext);
                        result.voegVerschillenToe(verschillen);
                    } else {
                        final HistorieContext rijVerwijderdContext = HistorieContext.bepaalNieuweHistorieContext(historieContext, pojoRij, null);
                        result.voegToeOfVervangVerschil(
                                new Verschil(
                                        sleutel,
                                        pojoRij,
                                        null,
                                        VerschilType.RIJ_VERWIJDERD,
                                        rijVerwijderdContext.getBestaandeHistorieRij(),
                                        rijVerwijderdContext.getNieuweHistorieRij()));
                    }
                }
            }

            for (final Map.Entry<EntiteitSleutel, Entiteit> rij : nieuweRijen.entrySet()) {
                final HistorieContext rijToegevoegdContext = HistorieContext.bepaalNieuweHistorieContext(historieContext, null, rij.getValue());
                result.voegToeOfVervangVerschil(
                        new Verschil(
                                rij.getKey(),
                                null,
                                rij.getValue(),
                                VerschilType.RIJ_TOEGEVOEGD,
                                rijToegevoegdContext.getBestaandeHistorieRij(),
                                rijToegevoegdContext.getNieuweHistorieRij()));
            }
        }
    }

    private void markeerCollectieAlsVerwijderd(
            final VergelijkerResultaat result,
            final Field veld,
            final Collection<Entiteit> bestaandeCollection,
            final Entiteit bestaandeDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        for (final Entiteit rij : bestaandeCollection) {
            final Entiteit rijPojo = Entiteit.convertToPojo(rij);
            final EntiteitSleutel sleutel = SleutelUtil.maakRijSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, rijPojo, veld.getName());
            if (rijPojo instanceof ALaagHistorieVerzameling) {
                final Set<Verschil> verschillen =
                        DeltaUtil.bepaalVerschillenVoorVerwijderdeALaagEntiteit((ALaagHistorieVerzameling) rijPojo, sleutel, historieContext);
                result.voegVerschillenToe(verschillen);
            } else if (!DeltaUtil.isMRij(rijPojo)) {
                final HistorieContext rijVerwijderdContext = HistorieContext.bepaalNieuweHistorieContext(historieContext, rijPojo, null);
                result.voegToeOfVervangVerschil(
                        new Verschil(
                                sleutel,
                                rijPojo,
                                null,
                                VerschilType.RIJ_VERWIJDERD,
                                rijVerwijderdContext.getBestaandeHistorieRij(),
                                rijVerwijderdContext.getNieuweHistorieRij()));
            }
        }
    }

    private void bewaarStapelMatch(final VergelijkerResultaat result, final DeltaStapelMatch stapelsMatch, final Object rij, final Object nieuweRij) {
        if (rij instanceof FormeleHistorie && nieuweRij instanceof FormeleHistorie) {
            result.koppelStapelMatchAanVoorkomen((FormeleHistorie) rij, stapelsMatch);
            result.koppelStapelMatchAanVoorkomen((FormeleHistorie) nieuweRij, stapelsMatch);
        }
    }

    private DeltaStapelMatch bepaalStapelMatch(
            final Field veld,
            final Collection<? extends Entiteit> bestaandeCollection,
            final Collection<? extends Entiteit> nieuweCollection,
            final Entiteit bestaandeDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel) {
        final DeltaStapelMatch stapelsMatch;
        if (nieuweCollection.iterator().next() instanceof FormeleHistorie) {
            stapelsMatch =
                    new DeltaStapelMatch(
                            (Collection<FormeleHistorie>) bestaandeCollection,
                            (Collection<FormeleHistorie>) nieuweCollection,
                            bestaandeDeltaEntiteit,
                            eigenaarSleutel,
                            veld);
        } else {
            stapelsMatch = null;
        }
        return stapelsMatch;
    }

    private void herbruikActieInhoudVoorVervallenRij(
            final VergelijkerResultaat result,
            final Entiteit bestaandeDeltaEntiteit,
            final Entiteit nieuweDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) {
        if (!(nieuweDeltaEntiteit instanceof FormeleHistorie)) {
            return;
        }

        final FormeleHistorie bestaandeHistorie = (FormeleHistorie) bestaandeDeltaEntiteit;
        final FormeleHistorie nieuweHistorie = (FormeleHistorie) nieuweDeltaEntiteit;

        if (nieuweHistorie.isVervallen() && nieuweHistorie.getActieInhoud().equals(nieuweHistorie.getActieVerval())) {
            final EntiteitSleutel actieInhoudSleutel = SleutelUtil.maakSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, "actieInhoud");
            final BRPActie actieInhoud;
            if (result.bevatSleutel(actieInhoudSleutel)) {
                actieInhoud = nieuweHistorie.getActieInhoud();
            } else {
                actieInhoud = bestaandeHistorie.getActieInhoud();
            }
            final EntiteitSleutel actieVervalSleutel = SleutelUtil.maakSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, "actieVerval");
            final Verschil actieVervalVerschil = result.zoekVerschil(actieVervalSleutel);
            if (actieVervalVerschil != null) {
                result.voegToeOfVervangVerschil(
                        new Verschil(
                                actieVervalSleutel,
                                actieVervalVerschil.getOudeWaarde(),
                                actieInhoud,
                                actieVervalVerschil.getVerschilType(),
                                historieContext.getBestaandeHistorieRij(),
                                historieContext.getNieuweHistorieRij()));
            }
        }
    }

    private List<Class<?>> getSkippableEntiteiten() {
        return DeltaUtil.getSkippableEntiteiten(modus, false);
    }

    private void bepaalVerschillenReisdocumenten(
            final VergelijkerResultaat result,
            final Field veld,
            final Collection<Entiteit> bestaandeCollection,
            final Collection<Entiteit> nieuweCollection,
            final Entiteit bestaandeDeltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final HistorieContext historieContext) throws ReflectiveOperationException {
        final Set<PersoonReisdocument> bestaandeReisdocumenten = converteerNaarReisdocumenten(bestaandeCollection);
        final Set<PersoonReisdocument> nieuweReisdocumenten = converteerNaarReisdocumenten(nieuweCollection);

        for (final PersoonReisdocument bestaandReisdocument : bestaandeReisdocumenten) {
            if (bestaandReisdocument.bevatAlleenMrijen()) {
                continue;
            }

            PersoonReisdocument match = null;

            for (final PersoonReisdocument nieuwReisdocument : nieuweReisdocumenten) {
                if (bestaandReisdocument.isVolledigeMatchMet(nieuwReisdocument)) {
                    match = nieuwReisdocument;
                    break;
                } else if (bestaandReisdocument.isMatchMet(nieuwReisdocument)) {
                    match = nieuwReisdocument;
                }
            }

            final PersoonReisdocumentHistorie bestaandReisdocumentHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandReisdocument.getPersoonReisdocumentHistorieSet());

            final EntiteitSleutel sleutel = SleutelUtil.maakRijSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, bestaandReisdocument, veld.getName());
            sleutel.vulSleutelAanVoorReisdocument(bestaandReisdocument.getId(), bestaandReisdocument, bestaandReisdocumentHistorie);

            if (match != null) {
                final DeltaStapelMatch stapelsMatch =
                        bepaalStapelMatch(veld, bestaandeCollection, nieuweCollection, bestaandeDeltaEntiteit, eigenaarSleutel);
                bewaarStapelMatch(result, stapelsMatch, bestaandReisdocument, match);

                zoekVerschillen(result, bestaandReisdocument, match, bestaandeDeltaEntiteit, sleutel, historieContext);

                nieuweReisdocumenten.remove(match);
            } else {
                final Set<Verschil> verschillen = DeltaUtil.bepaalVerschillenVoorVerwijderdeALaagEntiteit(bestaandReisdocument, sleutel, historieContext);
                result.voegVerschillenToe(verschillen);
            }
        }

        // Wat we nu nog overhouden zijn nieuwe reisdocumenten.
        long nieuwTechnischId = -1L;
        for (final PersoonReisdocument reisdocument : nieuweReisdocumenten) {
            final PersoonReisdocumentHistorie reisdocumentHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(reisdocument.getPersoonReisdocumentHistorieSet());

            final EntiteitSleutel sleutel = SleutelUtil.maakRijSleutel(bestaandeDeltaEntiteit, eigenaarSleutel, reisdocument, veld.getName());
            sleutel.vulSleutelAanVoorReisdocument(nieuwTechnischId, reisdocument, reisdocumentHistorie);
            nieuwTechnischId--;

            result.voegToeOfVervangVerschil(
                    new Verschil(
                            sleutel,
                            null,
                            reisdocument,
                            VerschilType.RIJ_TOEGEVOEGD,
                            historieContext.getBestaandeHistorieRij(),
                            historieContext.getNieuweHistorieRij()));
        }
    }

    private Set<PersoonReisdocument> converteerNaarReisdocumenten(final Collection<Entiteit> collectie) {
        final Set<PersoonReisdocument> reisdocumenten = new HashSet<>();
        for (final Entiteit rij : collectie) {
            final Entiteit deltaEntiteit = Entiteit.convertToPojo(rij);
            reisdocumenten.add((PersoonReisdocument) deltaEntiteit);
        }
        return reisdocumenten;
    }

    private Set<PersoonNaamgebruikHistorie> converteerNaarNaamgebruikHistorie(final Collection<Entiteit> collectie) {
        final Set<PersoonNaamgebruikHistorie> naamgebruikHistorieSet = new HashSet<>();
        for (final Entiteit rij : collectie) {
            final Entiteit deltaEntiteit = Entiteit.convertToPojo(rij);
            naamgebruikHistorieSet.add((PersoonNaamgebruikHistorie) deltaEntiteit);
        }
        return naamgebruikHistorieSet;
    }
}
