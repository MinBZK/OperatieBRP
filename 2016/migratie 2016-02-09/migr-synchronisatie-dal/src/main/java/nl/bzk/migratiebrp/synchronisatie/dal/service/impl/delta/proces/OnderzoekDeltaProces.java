/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.OnderzoekUtil;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Implementatie voor het bepalen en verwerken van de delta op onderzoek.
 */
public class OnderzoekDeltaProces implements DeltaProces {

    /**
     * {@inheritDoc}
     */
    @Override
    public void bepaalVerschillen(final DeltaBepalingContext context) {
        // Niet geimplementeerd voor onderzoek. Onderzoek verschillen worden bij de DeltaEntiteiten gevonden.
    }

    /**
     * {@inheritDoc}
     * @param context
     */
    @Override
     public final void verwerkVerschillen(final DeltaBepalingContext context) {
        final Persoon bestaandePersoon = context.getBestaandePersoon();
        final Collection<OnderzoekPaar> onderzoekPaarSet = matchOnderzoeken(context);

        for (final OnderzoekPaar onderzoekPaar : onderzoekPaarSet) {
            verwerkOnderzoek(context, bestaandePersoon, onderzoekPaar);
        }
    }

    private void verwerkOnderzoek(final DeltaBepalingContext context, final Persoon persoon, final OnderzoekPaar onderzoekPaar) {
        final boolean onderzoekIsBijhoudingActueel = onderzoekPaar.isBijhoudingActueel();

        final boolean isOnderzoekMutatie;
        switch (onderzoekPaar.getStatus()) {
            case GEWIJZIGD:
                isOnderzoekMutatie = true;
                wijzigOnderzoek(context, onderzoekPaar.getBestaandOnderzoek(), onderzoekPaar.getNieuwOnderzoek());
                break;
            case VERWIJDERD:
                isOnderzoekMutatie = true;
                verwijderOnderzoek(context, onderzoekPaar.getBestaandOnderzoek());
                break;
            case NIEUW:
                isOnderzoekMutatie = true;
                toevoegenOnderzoek(context, persoon, onderzoekPaar.getNieuwOnderzoek());
                break;
            default:
                isOnderzoekMutatie = false;
        }

        if (!onderzoekIsBijhoudingActueel && isOnderzoekMutatie) {
            context.setBijhoudingOverig();
        }
    }

    private Collection<OnderzoekPaar> matchOnderzoeken(final DeltaBepalingContext context) {
        final Persoon nieuwePersoon = context.getNieuwePersoon();
        final Persoon bestaandePersoon = context.getBestaandePersoon();
        final Collection<DeltaEntiteitPaar> deltaEntiteitPaarCollection = context.getDeltaEntiteitPaarSet();

        final Collection<Onderzoek> nieuweOnderzoeken =
                OnderzoekUtil.transformeerPersoonOnderzoekenNaarOnderzoeken(nieuwePersoon.getPersoonOnderzoekSet());
        final Collection<Onderzoek> bestaandeOnderzoeken =
                OnderzoekUtil.transformeerPersoonOnderzoekenNaarOnderzoeken(bestaandePersoon.getPersoonOnderzoekSet());

        final Collection<OnderzoekPaar> onderzoekPaarSet = new LinkedHashSet<>();
        for (final Onderzoek nieuwOnderzoek : nieuweOnderzoeken) {
            final Onderzoek gevondenOnderzoek = zoekOnderzoekInSet(bestaandeOnderzoeken, nieuwOnderzoek, deltaEntiteitPaarCollection);
            onderzoekPaarSet.add(new OnderzoekPaar(gevondenOnderzoek, nieuwOnderzoek));
        }

        // Alle gematchde bestaande onderzoek zijn uit de set van bestaande onderzoeken verwijderd. Dus nu alleen nog
        // verwijderde onderzoeken over.
        for (final Onderzoek onderzoek : bestaandeOnderzoeken) {
            onderzoekPaarSet.add(new OnderzoekPaar(onderzoek, null));
        }

        return onderzoekPaarSet;
    }

    private Onderzoek zoekOnderzoekInSet(
        final Collection<Onderzoek> onderzoeken,
        final Onderzoek onderzoek,
        final Collection<DeltaEntiteitPaar> deltaEntiteitPaarCollection)
    {
        Onderzoek resultaat = null;
        final Iterator<Onderzoek> onderzoekIterator = onderzoeken.iterator();
        while (resultaat == null && onderzoekIterator.hasNext()) {
            final Onderzoek mogelijkeMatch = onderzoekIterator.next();
            if (zijnGegevenInOnderzoekEenMatch(
                mogelijkeMatch.getGegevenInOnderzoekSet(),
                onderzoek.getGegevenInOnderzoekSet(),
                deltaEntiteitPaarCollection))
            {
                resultaat = mogelijkeMatch;
                onderzoekIterator.remove();
            }
        }
        return resultaat;
    }

    private boolean zijnGegevenInOnderzoekEenMatch(
        final Set<GegevenInOnderzoek> mogelijkeMatchGios,
        final Set<GegevenInOnderzoek> zoekGios,
        final Collection<DeltaEntiteitPaar> deltaEntiteitPaarCollection)
    {
        final Iterator<GegevenInOnderzoek> zoekGiosIterator = zoekGios.iterator();
        boolean result = mogelijkeMatchGios.size() == zoekGios.size();
        while (result && zoekGiosIterator.hasNext()) {
            final GegevenInOnderzoek zoekGio = zoekGiosIterator.next();
            result = bestaatGioInSet(mogelijkeMatchGios, zoekGio, deltaEntiteitPaarCollection);
        }
        return result;
    }

    private boolean bestaatGioInSet(
        final Set<GegevenInOnderzoek> gegevenInOnderzoekSet,
        final GegevenInOnderzoek zoekGegevenInOnderzoek,
        final Collection<DeltaEntiteitPaar> deltaEntiteitPaarCollection)
    {
        final DeltaEntiteit deltaEntiteit = PersistenceUtil.getPojoFromObject(zoekGegevenInOnderzoek.getObjectOfVoorkomen());

        boolean result = false;
        final Iterator<GegevenInOnderzoek> gegevenInOnderzoekIterator = gegevenInOnderzoekSet.iterator();
        while (!result && gegevenInOnderzoekIterator.hasNext()) {
            final GegevenInOnderzoek gegevenInOnderzoek = gegevenInOnderzoekIterator.next();
            final DeltaEntiteit pojoGio = PersistenceUtil.getPojoFromObject(gegevenInOnderzoek.getObjectOfVoorkomen());
            if (pojoGio.getClass().isAssignableFrom(deltaEntiteit.getClass())) {
                // Het is dezelfde class.
                result =
                        deltaEntiteitPaarCollection.contains(new DeltaEntiteitPaar(pojoGio, deltaEntiteit))
                         && zoekGegevenInOnderzoek.getSoortGegeven().equals(gegevenInOnderzoek.getSoortGegeven());
            }
        }
        return result;
    }

    private void toevoegenOnderzoek(final DeltaBepalingContext context, final Persoon persoon, final Onderzoek onderzoek) {
        SynchronisatieLogging.addMelding(onderzoek.getClass().getSimpleName() + " (DW-002-ACT)");
        // Vanuit conversie heeft onderzoek maar 1 persoononderzoek
        persoon.addPersoonOnderzoek(onderzoek.getPersoonOnderzoekSet().iterator().next());
        controleerOnderzoekToegevoegdOpBestaandeEntiteiten(context, onderzoek);
    }

    private void wijzigOnderzoek(final DeltaBepalingContext context, final Onderzoek bestaandOnderzoek, final Onderzoek nieuwOnderzoek) {
        SynchronisatieLogging.addMelding(bestaandOnderzoek.getClass().getSimpleName() + " (DW-003)");

        // Vanuit conversie heeft onderzoek maar 1 historie rij.
        converteerNaarMRij(context, bestaandOnderzoek.getOnderzoekHistorieSet());
        converteerNaarMRij(context, bestaandOnderzoek.getOnderzoekAfgeleidAdministratiefHistorieSet());

        // Eerst onderzoek bijwerken, daarna historie toevoegen. Historie wordt gebaseerd op het Onderzoek object.
        bestaandOnderzoek.updateInhoud(nieuwOnderzoek);

        bestaandOnderzoek.addOnderzoekHistorie(nieuwOnderzoek.getOnderzoekHistorieSet().iterator().next());
        bestaandOnderzoek.addOnderzoekAfgeleidAdministratiefHistorie(nieuwOnderzoek.getOnderzoekAfgeleidAdministratiefHistorieSet().iterator().next());
    }

    private void verwijderOnderzoek(final DeltaBepalingContext context, final Onderzoek bestaandOnderzoek) {
        SynchronisatieLogging.addMelding(bestaandOnderzoek.getClass().getSimpleName() + " (DW-001)");
        // alleen de actuele historie rij naar een M-rij veranderen
        converteerNaarMRij(context, bestaandOnderzoek.getOnderzoekHistorieSet());
        converteerNaarMRij(context, bestaandOnderzoek.getOnderzoekAfgeleidAdministratiefHistorieSet());

        for (final PartijOnderzoek partijOnderzoek : bestaandOnderzoek.getPartijOnderzoekSet()) {
            converteerNaarMRij(context, partijOnderzoek.getPartijOnderzoekHistorieSet());
            partijOnderzoek.setSoortPartijOnderzoek(null);
        }

        for (final PersoonOnderzoek persoonOnderzoek : bestaandOnderzoek.getPersoonOnderzoekSet()) {
            converteerNaarMRij(context, persoonOnderzoek.getPersoonOnderzoekHistorieSet());
            persoonOnderzoek.setSoortPersoonOnderzoek(null);
        }
    }

    private void converteerNaarMRij(final DeltaBepalingContext context, final Set<? extends FormeleHistorie> historieSet) {
        for (final FormeleHistorie historie : historieSet) {
            if (!DeltaUtil.isMRij(historie)) {
                historie.setDatumTijdVerval(historie.getDatumTijdRegistratie());
                historie.setActieVerval(historie.getActieInhoud());
                historie.setIndicatieVoorkomenTbvLeveringMutaties(true);
                historie.setActieVervalTbvLeveringMutaties(context.getActieVervalTbvLeveringMuts());
            }
        }
    }

    private void controleerOnderzoekToegevoegdOpBestaandeEntiteiten(final DeltaBepalingContext context, final Onderzoek onderzoek) {
        for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevenInOnderzoekSet()) {
            for (final DeltaEntiteitPaar deltaEntiteitPaar : context.getDeltaEntiteitPaarSet()) {
                if (gegevenInOnderzoek.getObjectOfVoorkomen() == deltaEntiteitPaar.getNieuw() && !deltaEntiteitPaar.wordtBestaandMRij()) {
                    gegevenInOnderzoek.setObjectOfVoorkomen(deltaEntiteitPaar.getBestaand());
                }
            }
        }
    }
}
