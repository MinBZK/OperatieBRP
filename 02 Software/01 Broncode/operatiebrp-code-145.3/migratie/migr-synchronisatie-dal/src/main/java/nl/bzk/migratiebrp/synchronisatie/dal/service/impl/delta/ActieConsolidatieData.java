/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Deze class bevat gegevens die nodig zijn om acties te consolideren.
 */
public final class ActieConsolidatieData implements ConsolidatieData {

    private final Map<BRPActie, Set<BRPActie>> nieuweActieOudeActieMap;
    private final Map<BRPActie, Set<FormeleHistorie>> actieVoorkomenMap;
    private final Map<FormeleHistorie, DeltaStapelMatch> voorkomenStapelMap;

    /**
     * Maakt een nieuwe ActieConsolidatieData object.
     */
    public ActieConsolidatieData() {
        this(new HashMap<BRPActie, Set<BRPActie>>(), new HashMap<BRPActie, Set<FormeleHistorie>>(), new HashMap<FormeleHistorie, DeltaStapelMatch>());
    }

    private ActieConsolidatieData(
            final Map<BRPActie, Set<BRPActie>> nieuweActieOudeActieMap,
            final Map<BRPActie, Set<FormeleHistorie>> actieVoorkomenMap,
            final Map<FormeleHistorie, DeltaStapelMatch> voorkomenStapelMap) {
        this.nieuweActieOudeActieMap = nieuweActieOudeActieMap;
        this.actieVoorkomenMap = actieVoorkomenMap;
        this.voorkomenStapelMap = voorkomenStapelMap;
    }

    @Override
    public void koppelNieuweActieMetOudeActie(final BRPActie nieuweActieParam, final BRPActie oudeActieParam) {
        final BRPActie nieuweActie = getPojoFromObject(nieuweActieParam);
        final BRPActie oudeActie = getPojoFromObject(oudeActieParam);
        if (!nieuweActieOudeActieMap.containsKey(nieuweActie)) {
            nieuweActieOudeActieMap.put(nieuweActie, new HashSet<BRPActie>());
        }
        nieuweActieOudeActieMap.get(nieuweActie).add(oudeActie);
    }

    @Override
    public void koppelActieMetVoorkomen(final BRPActie actieParam, final FormeleHistorie voorkomenParam) {
        final BRPActie actie = getPojoFromObject(actieParam);
        final FormeleHistorie voorkomen = getPojoFromObject(voorkomenParam);

        if (!actieVoorkomenMap.containsKey(actie)) {
            actieVoorkomenMap.put(actie, new HashSet<FormeleHistorie>());
        }
        actieVoorkomenMap.get(actie).add(voorkomen);
    }

    @Override
    public void koppelVoorkomenMetStapel(final FormeleHistorie voorkomenParam, final DeltaStapelMatch deltaStapelMatchParam) {
        final FormeleHistorie voorkomen = getPojoFromObject(voorkomenParam);
        voorkomenStapelMap.put(voorkomen, deltaStapelMatchParam);
    }

    @Override
    public ConsolidatieData verwijderActieInMRijen(final Set<FormeleHistorie> mRijenParam, final List<Verschil> nieuweRijAanpassingen) {
        final Collection<FormeleHistorie> mRijen = convertObjectInCollectionToPojo(mRijenParam);
        final Map<BRPActie, Set<BRPActie>> aangepasteNieuweActieOudeActieMap = kopieer(nieuweActieOudeActieMap);
        // verwijder alle acties die alleen in m-rijen voorkomen
        for (final Set<BRPActie> brpActies : aangepasteNieuweActieOudeActieMap.values()) {
            for (final Iterator<BRPActie> actieIterator = brpActies.iterator(); actieIterator.hasNext(); ) {
                final BRPActie actie = actieIterator.next();
                if (mRijen.containsAll(actieVoorkomenMap.get(actie)) && !bevatNieuweWaarde(actie, nieuweRijAanpassingen)) {
                    actieIterator.remove();
                }
            }
        }
        return new ActieConsolidatieData(aangepasteNieuweActieOudeActieMap, actieVoorkomenMap, voorkomenStapelMap);
    }

    @Override
    public ConsolidatieData voegNieuweActiesToe(final Set<BRPActie> nieuweActiesParam) {
        final Collection<BRPActie> nieuweActies = convertObjectInCollectionToPojo(nieuweActiesParam);
        final Map<BRPActie, Set<BRPActie>> aangepasteNieuweActieOudeActieMap = kopieer(nieuweActieOudeActieMap);
        for (final BRPActie actie : nieuweActies) {
            if (aangepasteNieuweActieOudeActieMap.containsKey(actie)) {
                aangepasteNieuweActieOudeActieMap.get(actie).add(actie);
            }
        }
        return new ActieConsolidatieData(aangepasteNieuweActieOudeActieMap, actieVoorkomenMap, voorkomenStapelMap);
    }

    @Override
    public ConsolidatieData verwijderCat07Cat13Acties() {
        final Map<BRPActie, Set<BRPActie>> aangepasteNieuweActieOudeActieMap = kopieer(nieuweActieOudeActieMap);

        for (final Iterator<Map.Entry<BRPActie, Set<BRPActie>>> mapIterator =
             aangepasteNieuweActieOudeActieMap.entrySet().iterator(); mapIterator.hasNext(); ) {
            final Map.Entry<BRPActie, Set<BRPActie>> entry = mapIterator.next();
            if (entry.getKey().isCat07Of13Actie()) {
                mapIterator.remove();
            } else {
                filterCat07Of13Actie(entry);
            }
        }
        return new ActieConsolidatieData(aangepasteNieuweActieOudeActieMap, actieVoorkomenMap, voorkomenStapelMap);
    }

    private void filterCat07Of13Actie(final Map.Entry<BRPActie, Set<BRPActie>> entry) {
        for (final Iterator<BRPActie> setIterator = entry.getValue().iterator(); setIterator.hasNext(); ) {
            final BRPActie actie = setIterator.next();
            if (actie.isCat07Of13Actie()) {
                setIterator.remove();
            }
        }
    }

    @Override
    public Set<DeltaStapelMatch> bepaalTeVervangenStapels() {
        final Set<DeltaStapelMatch> teVervangenStapels = new HashSet<>();
        for (final Map.Entry<BRPActie, Set<BRPActie>> actieSetEntry : nieuweActieOudeActieMap.entrySet()) {
            final Set<BRPActie> oudeActies = actieSetEntry.getValue();
            if (oudeActies.size() > 1) {
                verzamelTeVervangenStapels(teVervangenStapels, oudeActies);
                SynchronisatieLogging.addMelding("De stapel wordt opnieuw opgebouwd vanwege meerdere actie met hetzelfde tijdstip registratie");

            }
        }
        return convertObjectInDeltaStapelMatchSetToPojo(teVervangenStapels);
    }

    private void verzamelTeVervangenStapels(final Set<DeltaStapelMatch> teVervangenStapels, final Set<BRPActie> acties) {
        for (final BRPActie actie : acties) {
            final Set<FormeleHistorie> histories = actieVoorkomenMap.get(actie);
            if (histories != null) {
                verwerkHistories(teVervangenStapels, actie, histories);
            }
        }
    }

    private void verwerkHistories(final Set<DeltaStapelMatch> teVervangenStapels, final BRPActie actie, final Set<FormeleHistorie> histories) {
        for (final FormeleHistorie historie : histories) {
            final DeltaStapelMatch deltaStapelMatch = voorkomenStapelMap.get(historie);
            if (!teVervangenStapels.contains(deltaStapelMatch)) {
                teVervangenStapels.add(deltaStapelMatch);
                // Zoek de acties in het bestaande historie en verzamel de stapels waar de acties ook in voor
                // komen.
                verzamelTeVervangenStapels(teVervangenStapels, verzamelActies(deltaStapelMatch, actie));
            }
        }
    }

    private Set<BRPActie> verzamelActies(final DeltaStapelMatch match, final BRPActie huidigeActie) {
        final Set<BRPActie> acties = new LinkedHashSet<>();
        for (final FormeleHistorie historie : match.getOpgeslagenRijen()) {
            voegActieToe(acties, historie.getActieInhoud(), huidigeActie);
            if (historie.getActieVerval() != null) {
                voegActieToe(acties, historie.getActieVerval(), huidigeActie);
            }

            if (historie instanceof MaterieleHistorie && ((MaterieleHistorie) historie).getActieAanpassingGeldigheid() != null) {
                voegActieToe(acties, ((MaterieleHistorie) historie).getActieAanpassingGeldigheid(), huidigeActie);
            }
        }
        // Actie tbv levering mutaties wordt niet mee genomen. Dit is een "dummy"-actie en komt alleen voor in M-rijen.
        return acties;
    }

    private void voegActieToe(final Set<BRPActie> acties, final BRPActie actie, final BRPActie huidigeActie) {
        final BRPActie pojoActie = getPojoFromObject(actie);
        if (!huidigeActie.equals(pojoActie) && !acties.contains(pojoActie) && !actie.isCat07Of13Actie()) {
            acties.add(pojoActie);
        }
    }

    @Override
    public String toString() {
        return nieuweActieOudeActieMap.toString();
    }

    private <T> T getPojoFromObject(final T object) {
        return Entiteit.convertToPojo(object);
    }

    private Set<DeltaStapelMatch> convertObjectInDeltaStapelMatchSetToPojo(final Set<DeltaStapelMatch> deltaStapelMatches) {
        final Set<DeltaStapelMatch> results = new HashSet<>(deltaStapelMatches.size());
        for (final DeltaStapelMatch deltaStapelMatch : deltaStapelMatches) {
            results.add(convertObjectInDeltaStapelMatchToPojo(deltaStapelMatch));
        }
        return results;
    }

    private DeltaStapelMatch convertObjectInDeltaStapelMatchToPojo(final DeltaStapelMatch deltaStapelMatchParam) {
        final Collection<FormeleHistorie> opgeslagenRijen = convertObjectInCollectionToPojo(deltaStapelMatchParam.getOpgeslagenRijen());
        final Collection<FormeleHistorie> nieuweRijen = convertObjectInCollectionToPojo(deltaStapelMatchParam.getNieuweRijen());
        final Entiteit eigenaarDeltaEntiteit = getPojoFromObject(deltaStapelMatchParam.getEigenaarDeltaEntiteit());
        return new DeltaStapelMatch(
                opgeslagenRijen,
                nieuweRijen,
                eigenaarDeltaEntiteit,
                deltaStapelMatchParam.getEigenaarSleutel(),
                deltaStapelMatchParam.getEigenaarVeld());
    }

    private <T> Collection<T> convertObjectInCollectionToPojo(final Collection<T> objecten) {
        final Collection<T> result = new ArrayList<>();
        for (final T object : objecten) {
            result.add(getPojoFromObject(object));
        }
        return result;
    }

    private boolean bevatNieuweWaarde(final BRPActie actie, final List<Verschil> nieuweRijAanpassingen) {
        boolean result = false;
        for (final Verschil verschil : nieuweRijAanpassingen) {
            if (getPojoFromObject(verschil.getNieuweWaarde()) == actie) {
                result = true;
            }
        }
        return result;
    }

    private Map<BRPActie, Set<BRPActie>> kopieer(final Map<BRPActie, Set<BRPActie>> bronMap) {
        final Map<BRPActie, Set<BRPActie>> result = new HashMap<>(bronMap.size());

        for (final Map.Entry<BRPActie, Set<BRPActie>> bronEntry : bronMap.entrySet()) {
            result.put(bronEntry.getKey(), new HashSet<>(bronEntry.getValue()));
        }
        return result;
    }

    @Override
    public void addConsoldatieData(final ConsolidatieData data) {
        if (data instanceof ActieConsolidatieData) {
            final ActieConsolidatieData actieConsolidatieData = (ActieConsolidatieData) data;
            voegResultaatToeAanMap(nieuweActieOudeActieMap, actieConsolidatieData.nieuweActieOudeActieMap);
            voegResultaatToeAanMap(actieVoorkomenMap, actieConsolidatieData.actieVoorkomenMap);

            voorkomenStapelMap.putAll(actieConsolidatieData.voorkomenStapelMap);
        }
    }

    private <T> void voegResultaatToeAanMap(final Map<BRPActie, Set<T>> bestaandeMap, final Map<BRPActie, Set<T>> nieuweMap) {
        for (final Map.Entry<BRPActie, Set<T>> entry : nieuweMap.entrySet()) {
            final BRPActie key = entry.getKey();
            final Set<T> value = entry.getValue();
            if (bestaandeMap.containsKey(key)) {
                bestaandeMap.get(key).addAll(value);
            } else {
                bestaandeMap.put(key, value);
            }
        }
    }
}
