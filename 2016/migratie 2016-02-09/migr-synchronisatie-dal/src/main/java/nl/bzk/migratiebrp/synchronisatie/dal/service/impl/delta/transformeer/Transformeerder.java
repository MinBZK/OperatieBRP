/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.ALaagHistorieVerzameling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Verwerkt verschilgroepen door middel van een lijst transformaties en log de resultaten. De eerste toepasbare
 * transformatie wordt gebruikt om een verschilgroep te tranformeren.
 */
public final class Transformeerder {

    private final List<Transformatie> transformaties;
    private final StapelDeltaWijzigingenMap stapelDeltaWijzigingenMap;

    private Transformeerder(final List<Transformatie> transformaties) {
        this.transformaties = Collections.unmodifiableList(transformaties);
        stapelDeltaWijzigingenMap = new StapelDeltaWijzigingenMap();
    }

    /**
     * Maak een Transformeerder instantie met alle normale transformaties.
     *
     * @return de nieuwe Tranformeerder
     */
    public static Transformeerder maakTransformeerder() {
        final List<Transformatie> transformaties = new ArrayList<>();

        transformaties.add(new TransformatieDw011());
        transformaties.add(new TransformatieDw012());
        transformaties.add(new TransformatieDw021());
        transformaties.add(new TransformatieDw022());
        transformaties.add(new TransformatieDw023());
        transformaties.add(new TransformatieDw024());
        transformaties.add(new TransformatieDw025());
        transformaties.add(new TransformatieDw031());
        transformaties.add(new TransformatieDw032());
        transformaties.add(new TransformatieDw034());
        transformaties.add(new TransformatieDw041());
        transformaties.add(new TransformatieDw901());

        // Algemene transformaties voor situaties die niet specifiek door eerder transformaties worden verwerkt
        transformaties.add(new TransformatieDw003());
        transformaties.add(new TransformatieDw002Actueel());
        transformaties.add(new TransformatieDw002());
        transformaties.add(new TransformatieDw001());

        return new Transformeerder(transformaties);
    }

    /**
     * Verwerk een verzameling verschilgroepen door middel van de beschikbare tranformaties. De resultaten van de
     * transformaties worden gelogd in de SynchronisatieLogging. De eerste toepasbare transformatie wordt gebruikt om
     * een verschilgroep te transformeren.
     *
     * @param verschilGroepen
     *            de te transformeren verschilgroepen
     * @param actieVervalTbvLeveringMuts
     *            de actie te gebruiken als actie verval t.b.v. levering mutaties
     * @param deltaBepalingContext
     *            de delta context
     * @return de getransformeerde verschilgroepen
     */
    public List<VerschilGroep> transformeer(
        final Collection<VerschilGroep> verschilGroepen,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final List<VerschilGroep> result = new ArrayList<>();
        final List<String> logList = new ArrayList<>();

        for (final VerschilGroep verschilGroep : verschilGroepen) {
            if (verschilGroep.isHistorieVerschil()) {
                result.add(transformeerCDLaag(verschilGroep, actieVervalTbvLeveringMuts, logList, deltaBepalingContext));
            } else {
                result.add(transformeerALaag(verschilGroep));
            }
        }
        result.removeAll(stapelDeltaWijzigingenMap.verwijderTeNegerenVerschilGroepenEnDw901Wijzigingen());
        logResultaten(logList);

        return result;
    }

    /**
     * Geeft een {@link StapelDeltaWijzigingenMap} terug.
     *
     * @return een @{link StapelDeltaWijzigingenMap}
     */
    public StapelDeltaWijzigingenMap getStapelDeltaWijzigingenMap() {
        return stapelDeltaWijzigingenMap;
    }

    private VerschilGroep transformeerALaag(final VerschilGroep verschilGroep) {
        final List<DeltaWijziging> deltaWijzigingen = new ArrayList<>();
        for (Verschil verschil : verschilGroep.getVerschillen()) {
            if (VerschilType.RIJ_TOEGEVOEGD.equals(verschil.getVerschilType()) && (verschil.getNieuweWaarde() instanceof ALaagHistorieVerzameling)) {
                final ALaagHistorieVerzameling aLaagWaarde = (ALaagHistorieVerzameling) verschil.getNieuweWaarde();
                for (FormeleHistorie formeleHistorie : StapelDeltaWijzigingenMap.verzamelAlleFormeleHistorie(aLaagWaarde)) {
                    if (formeleHistorie.isActueel()) {
                        deltaWijzigingen.add(DeltaWijziging.DW_002_ACT);
                    } else {
                        deltaWijzigingen.add(DeltaWijziging.DW_002);
                    }
                }
            } else if (VerschilType.RIJ_VERWIJDERD.equals(verschil.getVerschilType()) && (verschil.getOudeWaarde() instanceof ALaagHistorieVerzameling)) {
                final int aantalVerwijderdeRijen =
                        StapelDeltaWijzigingenMap.verzamelAlleFormeleHistorie((ALaagHistorieVerzameling) verschil.getOudeWaarde()).size();
                for (int index = 0; index < aantalVerwijderdeRijen; index++) {
                    deltaWijzigingen.add(DeltaWijziging.DW_001);
                }
            }
        }
        if (!deltaWijzigingen.isEmpty()) {
            stapelDeltaWijzigingenMap.addHistorieDeltaWijziging(verschilGroep, deltaWijzigingen);
        }
        return verschilGroep;
    }

    private VerschilGroep transformeerCDLaag(
        final VerschilGroep verschilGroep,
        final BRPActie actieVervalTbvLeveringMuts,
        final List<String> logList,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final VerschilGroep result;
        final Transformatie transformatie = bepaalTransformatie(verschilGroep);

        if (transformatie != null) {
            result = transformatie.execute(verschilGroep, actieVervalTbvLeveringMuts, deltaBepalingContext);
            final VerschilGroep teRegistrerenVerschilGroep = !result.isEmpty() ? result : verschilGroep;
            logTransformatieResultaat(teRegistrerenVerschilGroep, transformatie.getDeltaWijziging(), logList);
            if (teRegistrerenVerschilGroep.isHistorieVerschil()) {
                stapelDeltaWijzigingenMap.addHistorieDeltaWijziging(teRegistrerenVerschilGroep, transformatie.getDeltaWijziging());
            }
        } else {
            result = verschilGroep;
        }

        return result;
    }

    private Transformatie bepaalTransformatie(final VerschilGroep verschilGroep) {
        // Onderzoek verschillen uit de verschilgroep halen, anders kloppen de accept-implementaties van de
        // transformeerders niet meer. Na het bepalen van de transformeerder, de verschillen wel weer terug plaatsen.
        final List<Verschil> onderzoekVerschillen = verschilGroep.filterOnderzoekVerschillen();

        for (final Transformatie transformatie : transformaties) {
            if (transformatie.accept(verschilGroep)) {
                return transformatie;
            }
        }

        verschilGroep.addVerschillen(onderzoekVerschillen);
        return null;
    }

    private void logTransformatieResultaat(final VerschilGroep verschilGroep, final DeltaWijziging deltaWijziging, final List<String> logList) {
        final Class<? extends FormeleHistorie> groepClass = verschilGroep.getHistorieGroepClass();
        final String prefix = groepClass.getSimpleName() + " (" + deltaWijziging + ") ";

        for (final Verschil verschil : verschilGroep) {
            final String melding = prefix + verschil.getSleutel().toShortString() + " " + verschil.getVerschilType();
            logList.add(melding);
        }
    }

    private void logResultaten(final List<String> logList) {
        Collections.sort(logList);
        for (final String logRegel : logList) {
            SynchronisatieLogging.addMelding(logRegel);
        }
    }
}
