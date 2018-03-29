/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ALaagHistorieVerzameling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Bevat de mapping tussen stapels en de delta wijzigingen die betrekking hebben op een stapel. Een stapel wordt
 * geidentificeerd ahdv een entiteitsleutel die een stapel uniek identificeert voor een persoon.
 */
public final class StapelDeltaWijzigingenMap {

    private static final Set<Class<? extends FormeleHistorie>> GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE = new HashSet<>();
    private static final Set<SoortIndicatie> INDICATIES_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE =
            EnumSet.of(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING, SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT,
                    SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
    private static final int INT_3 = 3;
    private static final int INT_2 = 2;

    static {
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonInschrijvingHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonPersoonskaartHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonUitsluitingKiesrechtHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonDeelnameEuVerkiezingenHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonAfgeleidAdministratiefHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonVerificatieHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonReisdocumentHistorie.class);
        GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.add(PersoonVerblijfsrechtHistorie.class);
    }

    private final InterneMap interneMap;

    /**
     * Maakt een nieuwe StapelDeltaWijzigingenMap object.
     */
    StapelDeltaWijzigingenMap() {
        interneMap = new InterneMap();
    }

    /**
     * Verzameld alle formele historie voor het A Laag object.
     * @param aLaagEntiteit het a laag object
     * @return de lijst met formele historie
     */
    static Collection<FormeleHistorie> verzamelAlleFormeleHistorie(final ALaagHistorieVerzameling aLaagEntiteit) {
        final Collection<FormeleHistorie> result = new ArrayList<>();
        for (Collection<FormeleHistorie> formeleHistorieMap : aLaagEntiteit.verzamelHistorie().values()) {
            result.addAll(formeleHistorieMap);
        }
        return result;
    }

    /**
     * Voegt de delta wijziging toe aan de de lijst horende bij de stapel van deze verschil groep.
     * @param verschilGroep de verschillen per voorkomen
     * @param deltaWijziging de gedetecteerde delta wijziging voor de betreffende verschilgroep
     * @throws IllegalArgumentException als de verschilgroep geen betrekking heeft op een historisch voorkomen
     */
    void addHistorieDeltaWijziging(final VerschilGroep verschilGroep, final DeltaWijziging deltaWijziging) {
        addHistorieDeltaWijziging(verschilGroep, Collections.singletonList(deltaWijziging));
    }

    /**
     * Voegt de delta wijziging toe aan de de lijst horende bij de stapel van deze verschil groep.
     * @param verschilGroep de verschillen per voorkomen
     * @param deltaWijzigingen de gedetecteerde delta wijzigingen voor de betreffende verschilgroep
     * @throws IllegalArgumentException als de verschilgroep geen betrekking heeft op een historisch voorkomen
     */
    void addHistorieDeltaWijziging(final VerschilGroep verschilGroep, final List<DeltaWijziging> deltaWijzigingen) {
        if (verschilGroep.getVerschillen().isEmpty()) {
            return;
        }
        final Verschil eersteVerschil = verschilGroep.getVerschillen().get(0);
        final Sleutel verschilSleutel = eersteVerschil.getSleutel();
        if (!(verschilSleutel instanceof EntiteitSleutel)) {
            throw new IllegalArgumentException("de verschilgroep moet betrekking hebben op een entiteit");
        }
        final EntiteitSleutel stapelSleutel;
        if (FormeleHistorie.class.isAssignableFrom(((EntiteitSleutel) verschilSleutel).getEntiteit())) {
            stapelSleutel = maakKopieVanHistorieElementSleutel((EntiteitSleutel) verschilSleutel);
        } else {
            stapelSleutel = maakKopieVanHistorieRijSleutel((EntiteitSleutel) verschilSleutel);
        }
        interneMap.voegSleutelToe(stapelSleutel);
        interneMap.voegVerschilGroepToe(stapelSleutel, verschilGroep, deltaWijzigingen);
    }

    /**
     * @return true als de wijzigingen in deze map betrekking hebben op een bijhouding actueel, anders false.
     */
    public boolean isBijhoudingActueel() {
        return getEersteOverigeBijhouding() == null;
    }

    private Map.Entry<EntiteitSleutel, List<DeltaWijziging>> getEersteOverigeBijhouding() {
        Map.Entry<EntiteitSleutel, List<DeltaWijziging>> result = null;
        for (final EntiteitSleutel stapelSleutel : interneMap.verschilGroepMap.keySet()) {
            final List<DeltaWijziging> deltaWijzigingen = interneMap.getDeltaWijzigingen(stapelSleutel);
            if (!deltaWijzigingen.isEmpty() && !isActueleBijhouding(stapelSleutel, deltaWijzigingen)) {
                final Map<EntiteitSleutel, List<DeltaWijziging>> tempResult = new HashMap<>();
                tempResult.put(stapelSleutel, deltaWijzigingen);
                result = tempResult.entrySet().iterator().next();
                break;
            }
        }
        return result;
    }

    private boolean isActueleBijhouding(final EntiteitSleutel stapelSleutel, final List<DeltaWijziging> deltaWijzigingen) {
        boolean result = false;
        if (isActualiseringNieuweGegevens(deltaWijzigingen)
                || isActualiseringBestaandeGegevens(deltaWijzigingen)
                || isActualiseringBeeindigdeGegevens(deltaWijzigingen)
                || isCorrectieInActueleGegevens(deltaWijzigingen)
                || isCorrectieOnterechtOpgenomenGegevens(deltaWijzigingen)
                || isWijzigingGroepenZonderGBAHistorie(stapelSleutel)
                || isWijzigingBijhoudingGegevens(deltaWijzigingen)
                || isWijzigingVolledigGenegeerd(deltaWijzigingen)
                || isBetrokkenheidRelatieOfGerelateerde(stapelSleutel)
                || isWijzigingVanVoornaamOfGeslachtsnaamcomponent(stapelSleutel)
                || isWijzigingBijhoudingAfkomstigUitCat08(stapelSleutel)
                || isBijhoudingNationaliteitWordtBeeindigd(deltaWijzigingen)) {
            result = true;
        }
        return result;
    }

    private boolean isBijhoudingNationaliteitWordtBeeindigd(final List<DeltaWijziging> deltaWijzigingen) {
        return deltaWijzigingen.size() == 1 && DeltaWijziging.DW_041.equals(deltaWijzigingen.get(0));
    }

    private boolean isActualiseringNieuweGegevens(final List<DeltaWijziging> deltaWijzigingen) {
        return deltaWijzigingen.size() == 1 && DeltaWijziging.DW_002_ACT.equals(deltaWijzigingen.get(0));
    }

    private boolean isActualiseringBestaandeGegevens(final List<DeltaWijziging> deltaWijzigingen) {
        boolean result = false;
        if (deltaWijzigingen.size() == INT_2) {
            if (deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_011))
                    || deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_021))
                    || deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_024))) {
                result = true;
            }
        } else if (deltaWijzigingen.size() == INT_3
                && deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_024, DeltaWijziging.DW_023))) {
            result = true;
        }
        return result;
    }

    private boolean isActualiseringBeeindigdeGegevens(final List<DeltaWijziging> deltaWijzigingen) {
        return deltaWijzigingen.size() == 1 && DeltaWijziging.DW_021.equals(deltaWijzigingen.get(0));
    }

    private boolean isCorrectieInActueleGegevens(final List<DeltaWijziging> deltaWijzigingen) {
        boolean result = false;
        if (deltaWijzigingen.size() == INT_2) {
            if (deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_012))
                    || deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_022))) {
                result = true;
            }
        } else if (deltaWijzigingen.size() == INT_3
                && deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_022, DeltaWijziging.DW_023))) {
            result = true;
        }
        return result;
    }

    private boolean isCorrectieOnterechtOpgenomenGegevens(final List<DeltaWijziging> deltaWijzigingen) {
        return deltaWijzigingen.size() == 1
                && (DeltaWijziging.DW_012.equals(deltaWijzigingen.get(0)) || DeltaWijziging.DW_025.equals(deltaWijzigingen.get(0)));
    }

    private boolean isWijzigingGroepenZonderGBAHistorie(final EntiteitSleutel stapelSleutel) {
        final List<FormeleHistorie> gewijzigdeEntiteitLijst = interneMap.getGewijzigdeEntiteiten(stapelSleutel);
        boolean groepOfIndicatieGevondenMetHistorie = false;
        for (FormeleHistorie gewijzigdeEntiteit : gewijzigdeEntiteitLijst) {
            final boolean isGroepZonderHistorie;
            final Class<? extends FormeleHistorie> entiteitClass = gewijzigdeEntiteit.getClass();
            isGroepZonderHistorie =
                    GROEPEN_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.contains(entiteitClass)
                            || (gewijzigdeEntiteit instanceof PersoonIndicatieHistorie)
                            && INDICATIES_AFKOMSTIG_UIT_CATEGORIE_ZONDER_HISTORIE.contains(
                            ((PersoonIndicatieHistorie) gewijzigdeEntiteit).getPersoonIndicatie().getSoortIndicatie());
            groepOfIndicatieGevondenMetHistorie |= !isGroepZonderHistorie;
        }
        return !gewijzigdeEntiteitLijst.isEmpty() && !groepOfIndicatieGevondenMetHistorie;
    }

    private boolean isWijzigingBijhoudingGegevens(final List<DeltaWijziging> deltaWijzigingen) {
        boolean result = false;
        if (deltaWijzigingen.size() == INT_2) {
            if (deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_031))
                    || deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_032))
                    || deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_034))) {
                result = true;
            }
        } else if (deltaWijzigingen.size() == INT_3
                && (deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_031, DeltaWijziging.DW_023))
                || deltaWijzigingen.containsAll(maakDeltaWijzigingLijst(DeltaWijziging.DW_002_ACT, DeltaWijziging.DW_034, DeltaWijziging.DW_023)))) {
            result = true;
        }
        return result;
    }

    private boolean isWijzigingVolledigGenegeerd(final List<DeltaWijziging> deltaWijzigingen) {
        final Set<DeltaWijziging> uniekeDeltaWijzigingen = EnumSet.noneOf(DeltaWijziging.class);
        uniekeDeltaWijzigingen.addAll(deltaWijzigingen);
        return uniekeDeltaWijzigingen.size() == 1 && DeltaWijziging.DW_901.equals(uniekeDeltaWijzigingen.iterator().next());
    }

    private boolean isBetrokkenheidRelatieOfGerelateerde(final EntiteitSleutel stapelSleutel) {
        final List<FormeleHistorie> gewijzigdeEntiteitLijst = interneMap.getGewijzigdeEntiteiten(stapelSleutel);
        boolean anderSoortEntiteitGevonden = false;
        for (FormeleHistorie gewijzigdeEntiteit : gewijzigdeEntiteitLijst) {
            final boolean isBetrokkenheidHistorie =
                    (gewijzigdeEntiteit instanceof BetrokkenheidHistorie)
                            || (gewijzigdeEntiteit instanceof BetrokkenheidOuderHistorie)
                            || (gewijzigdeEntiteit instanceof BetrokkenheidOuderlijkGezagHistorie);
            anderSoortEntiteitGevonden |=
                    !(isBetrokkenheidHistorie || (gewijzigdeEntiteit instanceof RelatieHistorie) || (isGerelateerdePersoonGroep(gewijzigdeEntiteit)));
        }
        return !gewijzigdeEntiteitLijst.isEmpty() && !anderSoortEntiteitGevonden;
    }

    private boolean isGerelateerdePersoonGroep(final FormeleHistorie gewijzigdeEntiteit) {
        final boolean result;
        if (gewijzigdeEntiteit instanceof PersoonIDHistorie) {
            result = SoortPersoon.PSEUDO_PERSOON.equals(((PersoonIDHistorie) gewijzigdeEntiteit).getPersoon().getSoortPersoon());
        } else if (gewijzigdeEntiteit instanceof PersoonSamengesteldeNaamHistorie) {
            result = SoortPersoon.PSEUDO_PERSOON.equals(((PersoonSamengesteldeNaamHistorie) gewijzigdeEntiteit).getPersoon().getSoortPersoon());
        } else if (gewijzigdeEntiteit instanceof PersoonGeboorteHistorie) {
            result = SoortPersoon.PSEUDO_PERSOON.equals(((PersoonGeboorteHistorie) gewijzigdeEntiteit).getPersoon().getSoortPersoon());
        } else {
            result =
                    gewijzigdeEntiteit instanceof PersoonGeslachtsaanduidingHistorie
                            && SoortPersoon.PSEUDO_PERSOON.equals(((PersoonGeslachtsaanduidingHistorie) gewijzigdeEntiteit).getPersoon().getSoortPersoon());
        }
        return result;
    }

    private boolean isWijzigingVanVoornaamOfGeslachtsnaamcomponent(final EntiteitSleutel stapelSleutel) {
        final List<FormeleHistorie> gewijzigdeEntiteitLijst = interneMap.getGewijzigdeEntiteiten(stapelSleutel);
        final Set<Class<? extends FormeleHistorie>> typeWijzigingen = getUniekeTypesVoorObjecten(gewijzigdeEntiteitLijst);
        final boolean isVoornaamOfGeslachtsnaamWijziging = typeWijzigingen.size() == 1
                && (typeWijzigingen.contains(PersoonVoornaamHistorie.class)
                || typeWijzigingen.contains(PersoonGeslachtsnaamcomponentHistorie.class));
        final boolean isVoornaamEnGeslachtsnaamWijziging = typeWijzigingen.size() == INT_2
                && typeWijzigingen.contains(PersoonVoornaamHistorie.class)
                && typeWijzigingen.contains(PersoonGeslachtsnaamcomponentHistorie.class);
        return isVoornaamOfGeslachtsnaamWijziging || isVoornaamEnGeslachtsnaamWijziging;
    }

    private boolean isWijzigingBijhoudingAfkomstigUitCat08(final EntiteitSleutel stapelSleutel) {
        boolean result = false;
        final List<VerschilGroepDeltaWijziging> verschilGroepDeltaWijzigingList = interneMap.verschilGroepMap.get(stapelSleutel);
        if (verschilGroepDeltaWijzigingList.size() == 1) {
            final VerschilGroepDeltaWijziging verschilGroepDeltaWijziging = verschilGroepDeltaWijzigingList.get(0);
            final List<Verschil> verschillen = verschilGroepDeltaWijziging.getVerschilGroep().getVerschillen();
            if (DeltaWijziging.DW_002.equals(verschilGroepDeltaWijziging.getDeltaWijziging()) && verschillen.size() == 1) {
                final Verschil dw002Verschil = verschillen.get(0);
                final FormeleHistorie gewijzgdeEntiteit = dw002Verschil.getNieuweHistorieRij();
                if (gewijzgdeEntiteit instanceof PersoonBijhoudingHistorie) {
                    final BRPActie actieInhoud = gewijzgdeEntiteit.getActieInhoud();
                    result = actieInhoud.getLo3Voorkomen() != null && "08".equals(actieInhoud.getLo3Voorkomen().getCategorie());
                }
            }
        }
        return result;
    }

    private Set<Class<? extends FormeleHistorie>> getUniekeTypesVoorObjecten(final List<FormeleHistorie> gewijzigdeEntiteitLijst) {
        final Set<Class<? extends FormeleHistorie>> typeWijzigingen = new HashSet<>();
        for (FormeleHistorie gewijzigdeEntiteit : gewijzigdeEntiteitLijst) {
            typeWijzigingen.add(gewijzigdeEntiteit.getClass());
        }
        return typeWijzigingen;
    }

    private List<DeltaWijziging> maakDeltaWijzigingLijst(final DeltaWijziging... deltaWijzigings) {
        return Arrays.asList(deltaWijzigings);
    }

    /*
     * Sleutels die wijzen naar een element bevatten de identificerende delen van de bijbehorende rij. De parent
     * (eigenaar) sleutel verwijst naar de stapel en bevat verder geen identificerende delen voor de rij en kan dus zo
     * worden gebruikt om de stapel te identificeren, zolang de gegevens die de stapel identificeren mee worden
     * gekopieerd naar de nieuwe stapel sleutel.
     */
    private EntiteitSleutel maakKopieVanHistorieElementSleutel(final EntiteitSleutel sleutel) {
        return AbstractTransformatie.maakSleutelZonderRijIdentificatie(sleutel.getEigenaarSleutel());
    }

    /*
     * Een sleutel die gebruikt wordt om een rij die is verwijderd of toegevoegd te identificeren bevat ook de sleutel
     * delen om deze rij uniek te kunnen identificeren. Deze moeten worden verwijderd zodat de sleutel de stapel
     * identificeerd en niet de rij.
     */
    private EntiteitSleutel maakKopieVanHistorieRijSleutel(final EntiteitSleutel sleutel) {
        return AbstractTransformatie.maakSleutelZonderRijIdentificatie(sleutel);
    }

    /**
     * @return de stapel sleutels in deze map, of een lege set als deze niet bestaan
     */
    Set<EntiteitSleutel> getStapelSleutels() {
        return Collections.unmodifiableSet(interneMap.verschilGroepMap.keySet());
    }

    /**
     * @param stapelSleutel de entiteitsleutel die de stapel identificeert
     * @return de lijst met wijzigingen die gelden voor de stapel die geidentificeerd kan worden met de gegeven sleutel
     */
    List<DeltaWijziging> getDeltaWijzigingen(final EntiteitSleutel stapelSleutel) {
        if (interneMap.verschilGroepMap.containsKey(stapelSleutel)) {
            return Collections.unmodifiableList(interneMap.getDeltaWijzigingen(stapelSleutel));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Logt de DeltaWijzigingen per stapel in de SynchronisatieLog.
     * @param deltaRootEntiteitMatchNaam de naam van de DeltaRootEntiteitMatch waarvoor deze wijzigingen gelogd worden
     */
    public void log(final String deltaRootEntiteitMatchNaam) {
        for (final EntiteitSleutel stapelSleutel : interneMap.verschilGroepMap.keySet()) {
            final List<DeltaWijziging> deltaWijzigingen = interneMap.getDeltaWijzigingen(stapelSleutel);
            if (!deltaWijzigingen.isEmpty()) {
                SynchronisatieLogging.addMelding(
                        String.format("%s: DW-logging: %s=%s", deltaRootEntiteitMatchNaam, sleutelToString(stapelSleutel), deltaWijzigingen));
            }
        }
        final Map.Entry<EntiteitSleutel, List<DeltaWijziging>> eersteOverigeBijhouding = getEersteOverigeBijhouding();
        String bijhoudingOverigString = "";
        if (eersteOverigeBijhouding != null) {
            bijhoudingOverigString =
                    String.format(
                            ", eerste bijhouding overig: %s=%s",
                            sleutelToString(eersteOverigeBijhouding.getKey()),
                            eersteOverigeBijhouding.getValue());
        }
        SynchronisatieLogging.addMelding(
                String.format(
                        "%s: Voorlopige conclusie: Is bijhouding actueel: %b%s",
                        deltaRootEntiteitMatchNaam,
                        eersteOverigeBijhouding == null,
                        bijhoudingOverigString));
    }

    /**
     * Verwijdert de verschilgroepen in een Bijhouding stapel als deze bestaat uit een {@link DeltaWijziging#DW_901} en
     * {@link DeltaWijziging#DW_023} en verwijdert de {@link DeltaWijziging#DW_901} DeltaWijzigingen.
     * @param deltaBepalingContext de {@link DeltaBepalingContext}
     * @return de lijst met verschilgroepen waarbij sprake is van een {@link DeltaWijziging#DW_901} en {@link DeltaWijziging#DW_023} wijziging in de groep
     * Bijhouding, als niets gevonden wordt wordt een lege lijst geretourneerd.
     */
    List<VerschilGroep> verwijderTeNegerenVerschilGroepenEnDw901Wijzigingen(final DeltaBepalingContext deltaBepalingContext) {
        final List<VerschilGroep> result = new ArrayList<>();
        final List<EntiteitSleutel> teVerwijderenDeltaWijzigingen = new ArrayList<>();
        for (final Map.Entry<EntiteitSleutel, List<VerschilGroepDeltaWijziging>> deltaWijzigingenVoorStapelEntry : interneMap.verschilGroepMap.entrySet()) {
            final EntiteitSleutel stapelSleutel = deltaWijzigingenVoorStapelEntry.getKey();
            final List<VerschilGroepDeltaWijziging> deltaWijzigingenVoorStapel = deltaWijzigingenVoorStapelEntry.getValue();
            final FormeleHistorie aangepastBrpVoorkomen = interneMap.getGewijzigdeEntiteiten(stapelSleutel).get(0);
            if (isWijzigingOpBijhoudingWaarbijAlleWijzigingenMoetenWordenGenegeerd(deltaWijzigingenVoorStapel, aangepastBrpVoorkomen)) {
                for (VerschilGroepDeltaWijziging verschilGroepDeltaWijziging : deltaWijzigingenVoorStapel) {
                    deltaBepalingContext.demarkeerBestaandeRijAlsMRij(verschilGroepDeltaWijziging.getVerschilGroep().getFormeleHistorie());
                    result.add(verschilGroepDeltaWijziging.getVerschilGroep());
                }
                teVerwijderenDeltaWijzigingen.add(stapelSleutel);
            } else if (isWijzigingOpBijhoudingWaarbijDw023MoetWordenGenegeerd(deltaWijzigingenVoorStapel, aangepastBrpVoorkomen)) {
                final VerschilGroepDeltaWijziging dw023VerschilGroep = zoekVerschilGroep(deltaWijzigingenVoorStapel, DeltaWijziging.DW_023);
                if (dw023VerschilGroep != null) {
                    deltaWijzigingenVoorStapel.remove(dw023VerschilGroep);
                    interneMap.removeDeltaWijziging(stapelSleutel, DeltaWijziging.DW_023);
                    result.add(dw023VerschilGroep.getVerschilGroep());
                }
            }
        }
        for (final EntiteitSleutel teVerwijderenStapelSleutel : teVerwijderenDeltaWijzigingen) {
            interneMap.verwijder(teVerwijderenStapelSleutel);
        }
        interneMap.verwijderTeNegerenDeltaWijzigingen();
        return result;
    }

    private boolean isWijzigingOpBijhoudingWaarbijAlleWijzigingenMoetenWordenGenegeerd(
            final List<VerschilGroepDeltaWijziging> deltaWijzigingenVoorStapel,
            final FormeleHistorie aangepastBrpVoorkomen) {
        return deltaWijzigingenVoorStapel.size() == INT_2
                && aangepastBrpVoorkomen instanceof PersoonBijhoudingHistorie
                && zoekVerschilGroep(deltaWijzigingenVoorStapel, DeltaWijziging.DW_901) != null
                && zoekVerschilGroep(deltaWijzigingenVoorStapel, DeltaWijziging.DW_023) != null;
    }

    private boolean isWijzigingOpBijhoudingWaarbijDw023MoetWordenGenegeerd(
            final List<VerschilGroepDeltaWijziging> deltaWijzigingenVoorStapel,
            final FormeleHistorie aangepastBrpVoorkomen) {
        final boolean zijnJuisteVerschillen =
                zoekVerschilGroep(deltaWijzigingenVoorStapel, DeltaWijziging.DW_021) != null
                        && zoekVerschilGroep(deltaWijzigingenVoorStapel, DeltaWijziging.DW_023) != null
                        && zoekVerschilGroep(deltaWijzigingenVoorStapel, DeltaWijziging.DW_002_ACT) != null;
        return deltaWijzigingenVoorStapel.size() == INT_3 && aangepastBrpVoorkomen instanceof PersoonBijhoudingHistorie && zijnJuisteVerschillen;
    }

    private VerschilGroepDeltaWijziging zoekVerschilGroep(
            final List<VerschilGroepDeltaWijziging> deltaWijzigingenVoorStapel,
            final DeltaWijziging deltaWijziging) {
        for (final VerschilGroepDeltaWijziging verschilGroepDeltaWijziging : deltaWijzigingenVoorStapel) {
            if (deltaWijziging.equals(verschilGroepDeltaWijziging.getDeltaWijziging())) {
                return verschilGroepDeltaWijziging;
            }
        }
        return null;
    }

    private String sleutelToString(final EntiteitSleutel sleutel) {
        final StringBuilder logString = new StringBuilder();
        logString.append(String.format("Stapel[%s.%s", sleutel.getEntiteit().getSimpleName(), sleutel.getVeld()));
        for (final Map.Entry<String, Object> sleutelEntry : sleutel.getDelen().entrySet()) {
            logString.append(String.format(", %s=%s", sleutelEntry.getKey(), sleutelEntry.getValue()));
        }
        logString.append("]");
        return logString.toString();
    }

    /**
     * Combinatie van Verschilgroep en bijbehorende deltawijziging.
     */
    private static final class VerschilGroepDeltaWijziging {
        private final VerschilGroep verschilGroep;
        private final DeltaWijziging deltaWijziging;

        private VerschilGroepDeltaWijziging(final VerschilGroep verschilGroep, final DeltaWijziging deltaWijziging) {
            this.verschilGroep = verschilGroep;
            this.deltaWijziging = deltaWijziging;
        }

        private VerschilGroep getVerschilGroep() {
            return verschilGroep;
        }

        private DeltaWijziging getDeltaWijziging() {
            return deltaWijziging;
        }
    }

    /**
     * Groep van interne maps.
     */
    private static final class InterneMap {

        private final Map<EntiteitSleutel, List<VerschilGroepDeltaWijziging>> verschilGroepMap;

        private InterneMap() {
            verschilGroepMap = new HashMap<>();
        }

        private void voegSleutelToe(final EntiteitSleutel stapelSleutel) {
            if (!verschilGroepMap.containsKey(stapelSleutel)) {
                verschilGroepMap.put(stapelSleutel, new ArrayList<>());
            }
        }

        private List<DeltaWijziging> getDeltaWijzigingen(final EntiteitSleutel stapelSleutel) {
            final List<DeltaWijziging> result = new ArrayList<>();
            for (final VerschilGroepDeltaWijziging verschilGroepDeltaWijziging : verschilGroepMap.get(stapelSleutel)) {
                result.add(verschilGroepDeltaWijziging.getDeltaWijziging());
            }
            return result;
        }

        private List<FormeleHistorie> getGewijzigdeEntiteiten(final EntiteitSleutel stapelSleutel) {
            final List<FormeleHistorie> result = new ArrayList<>();
            for (final VerschilGroepDeltaWijziging verschilGroepDeltaWijziging : verschilGroepMap.get(stapelSleutel)) {
                for (final Verschil verschil : verschilGroepDeltaWijziging.getVerschilGroep().getVerschillen()) {
                    result.addAll(getFormeleHistorieVoorVerschil(verschil));
                }
            }
            return result;
        }

        private List<FormeleHistorie> getFormeleHistorieVoorVerschil(final Verschil verschil) {
            final List<FormeleHistorie> result = new ArrayList<>();
            if (verschil.getBestaandeHistorieRij() != null) {
                result.add(Entiteit.convertToPojo(verschil.getBestaandeHistorieRij()));
            } else if (verschil.getNieuweHistorieRij() != null) {
                result.add(Entiteit.convertToPojo(verschil.getNieuweHistorieRij()));
            } else if (verschil.getNieuweWaarde() instanceof ALaagHistorieVerzameling) {
                result.addAll(verzamelAlleFormeleHistorie((ALaagHistorieVerzameling) verschil.getNieuweWaarde()));
            } else if (verschil.getOudeWaarde() instanceof ALaagHistorieVerzameling) {
                result.addAll(verzamelAlleFormeleHistorie((ALaagHistorieVerzameling) verschil.getOudeWaarde()));
            } else {
                throw new IllegalArgumentException("Kan niet bepalen welke entiteiten van BRP voorkomens zijn gewijzigd.");
            }
            return result;
        }

        private void voegVerschilGroepToe(
                final EntiteitSleutel stapelSleutel,
                final VerschilGroep verschilGroep,
                final List<DeltaWijziging> deltaWijzigingen) {
            for (final DeltaWijziging deltaWijziging : deltaWijzigingen) {
                verschilGroepMap.get(stapelSleutel).add(new VerschilGroepDeltaWijziging(verschilGroep, deltaWijziging));
            }
        }

        private void verwijder(final EntiteitSleutel teVerwijderenStapelSleutel) {
            verschilGroepMap.remove(teVerwijderenStapelSleutel);
        }

        private void verwijderTeNegerenDeltaWijzigingen() {
            for (List<VerschilGroepDeltaWijziging> verschilGroepDeltaWijzigingList : verschilGroepMap.values()) {
                verschilGroepDeltaWijzigingList
                        .removeIf(verschilGroepDeltaWijziging -> !verschilGroepDeltaWijziging.getDeltaWijziging().isNodigVoorSyncResyncBeslissing());
            }
        }

        private void removeDeltaWijziging(final EntiteitSleutel stapelSleutel, final DeltaWijziging deltaWijziging) {
            verschilGroepMap.get(stapelSleutel).removeIf(verschilGroepDeltaWijziging -> deltaWijziging.equals(verschilGroepDeltaWijziging.getDeltaWijziging()));
        }
    }
}
