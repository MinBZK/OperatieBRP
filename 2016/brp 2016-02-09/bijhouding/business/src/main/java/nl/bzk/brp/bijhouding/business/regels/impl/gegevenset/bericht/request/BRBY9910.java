/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.BetrokkenheidView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.RelatieView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Verschillende ObjectIDs in bericht moet verwijzen naar gerelateerde objecten in de BRP.
 *
 * Alle aangrenzende objecten in het bericht met een technische sleutel moeten ook
 * daadwerkelijk gerelateerd zijn in de database.
 *
 * Ivm nieuwe relaties met bestaande personen kunnen er ook 'ontbrekende schakels' in de technische sleutel
 * ketting zijn, maar dan gaat het checken toch door naar de 'diepere lagen'.
 *
 * In het geval van onmogelijke combinaties (bij nieuwe persoon, bestaande betrokkenheid) wordt een exceptie gegooid.
 *
 * Deze regel geldt voor elke actie!
 *
 * @brp.bedrijfsregel BRBY9910
 */
@Named("BRBY9910")
public class BRBY9910 implements VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY9910;
    }

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final ModelRootObject modelRootObject,
            final BerichtRootObject berichtRootObject,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (berichtRootObject instanceof PersoonBericht) {
            controleerPersoon(new PersoonContext((PersoonBericht) berichtRootObject,
                    (PersoonView) modelRootObject, objectenDieDeRegelOvertreden, bestaandeBetrokkenen));
        } else if (berichtRootObject instanceof RelatieBericht) {
            controleerRelatie(new RelatieContext((RelatieBericht) berichtRootObject,
                    (RelatieView) modelRootObject, objectenDieDeRegelOvertreden, bestaandeBetrokkenen));
        } else {
            throw new IllegalArgumentException("Ongeldig type root object");
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleer of de betrokkenheden van een persoon in het bericht wel matchen met die in de database.
     * Ga per betrokkenheid vervolgens door met checken richting de relatie.
     *
     * @param persoonContext de context
     */
    private void controleerPersoon(final PersoonContext persoonContext) {
        if (persoonContext.getModel() != null) {
//            controleerGroepenVanPersoon(persoonContext);
            controleerObjectenVanPersoon(persoonContext);
        }

        for (final BetrokkenheidBericht betrokkenheidBericht : dezeOfLeeg(persoonContext.getBericht().getBetrokkenheden())) {
            if (persoonContext.getBericht().getObjectSleutel() != null) {
                if (betrokkenheidBericht.getObjectSleutel() == null) {
                    throw new IllegalStateException("Ongeldige constructie: bestaande persoon, nieuwe betrokkenheid");
                }
                final BetrokkenheidView betrokkenheidModel = zoekBetrokkenheidMetSleutel(
                        persoonContext.getModel().getBetrokkenheden(), betrokkenheidBericht.getObjectSleutel());
                if (betrokkenheidModel == null) {
                    persoonContext.getObjectenDieDeRegelOvertreden().add(betrokkenheidBericht);
                } else {
                    controleerBetrokkenheidRichtingRelatie(new BetrokkenheidContext(
                            betrokkenheidBericht, betrokkenheidModel, persoonContext));
                }
            } else {
                if (betrokkenheidBericht.getObjectSleutel() != null) {
                    throw new IllegalStateException("Ongeldige constructie: nieuwe persoon, bestaande betrokkenheid");
                }
            }
        }
    }

    /**
     * Controleer of de relatie van een betrokkenheid in het bericht wel matcht met die in de database.
     * Ga vervolgens door met checken richting de betrokkenheden van de relatie.
     *
     * @param context de context
     */
    private void controleerBetrokkenheidRichtingRelatie(final BetrokkenheidContext context) {
        // TODO: Groepen van specifieke betrokkenheid checken?
        // (indien zo: 1 methode samen met controleerBetrokkenheidRichtingPersoon)

        final RelatieBericht relatieBericht = context.getBericht().getRelatie();
        final RelatieView relatieModel = context.getModel().getRelatie();
        if (relatieBericht.getObjectSleutel() == null) {
            throw new IllegalStateException("Ongeldige constructie: bestaande betrokkenheid, nieuwe relatie");
        }
        if (!relatieModel.getID().toString().equals(relatieBericht.getObjectSleutel())) {
            context.getObjectenDieDeRegelOvertreden().add(relatieBericht);
        } else {
            controleerRelatie(new RelatieContext(relatieBericht, relatieModel, context));
        }
    }

    /**
     * Controleer of de persoon van een betrokkenheid in het bericht wel matcht met die in de database.
     * Ga vervolgens door met checken richting de betrokkenheden van de persoon.
     *
     * @param betrokkenheidContext de context
     */
    private void controleerBetrokkenheidRichtingPersoon(final BetrokkenheidContext betrokkenheidContext) {
        // TODO: Groepen van specifieke betrokkenheid checken?
        // (indien zo: 1 methode samen met controleerBetrokkenheidRichtingRealtie)

        final PersoonBericht persoonBericht = betrokkenheidContext.getBericht().getPersoon();
        final PersoonView persoonModel;
        if (betrokkenheidContext.getModel() != null) {
            persoonModel = betrokkenheidContext.getModel().getPersoon();
            if (persoonBericht.getObjectSleutel() == null) {
                throw new IllegalStateException("Ongeldige constructie: bestaande betrokkenheid, nieuwe persoon");
            }
            if (!isDezelfdePersoon(persoonBericht, persoonModel)) {
                betrokkenheidContext.getObjectenDieDeRegelOvertreden().add(persoonBericht);
            } else {
                controleerPersoon(new PersoonContext(persoonBericht, persoonModel, betrokkenheidContext));
            }
        } else {
            // De ketting is 'gebroken', maar we kunnen de persoon weer terug vinden in de bestaande betrokkenen.
            persoonModel = betrokkenheidContext.getBestaandeBetrokkenen().get(
                    persoonBericht.getIdentificerendeSleutel());
            // Als er wel een persoon verwacht werd, maar niet is gevonden: fout.
            // Een niet ingeschrevenen worden niet verwacht, heeft dus ook geen tech.sleutel in bericht.
            if (persoonBericht.getObjectSleutel() != null && persoonModel == null) {
                throw new IllegalStateException("Persoon met identificerende sleutel: '"
                        + persoonBericht.getIdentificerendeSleutel() + "' niet gevonden in bestaande betrokkenen.");
            }
            controleerPersoon(new PersoonContext(persoonBericht, persoonModel, betrokkenheidContext));
        }
    }

    /**
     * Bekijkt of meegegeven persoon uit het bericht dezelfde is als die uit de database.
     * De check is voor niet ingeschrevenen op 'db' + id en voor ingeschrevenen op BSN.
     *
     * @param persoonBericht persoon uit het bericht
     * @param persoonModel persoon in de database
     * @return of het dezelfde persoon is of niet
     */
    private boolean isDezelfdePersoon(final PersoonBericht persoonBericht, final PersoonView persoonModel) {
        return persoonModel.getID().equals(persoonBericht.getObjectSleutelDatabaseID());
    }

    /**
     * Zoek de betrokkenheid in de meegegeven lijst met als id de technische sleutel.
     *
     * @param betrokkenen de betrokkenheid lijst
     * @param technischeSleutel de technische sleutel
     * @return de gevonden betrokkenheid of null
     */
    private BetrokkenheidView zoekBetrokkenheidMetSleutel(
            final Collection<BetrokkenheidView> betrokkenen, final String technischeSleutel)
    {
        BetrokkenheidView betrokkenheidModel = null;
        for (final BetrokkenheidView eenBetrokkenheid : betrokkenen) {
            if (eenBetrokkenheid.getID().toString().equals(technischeSleutel)) {
                betrokkenheidModel = eenBetrokkenheid;
            }
        }
        return betrokkenheidModel;
    }

    /**
     * Controleer of de betrokkenheden van een relatie in het bericht wel matchen met die in de database.
     * Ga per betrokkenheid vervolgens door met checken richting de persoon.
     *
     * @param relatieContext de context
     */
    private void controleerRelatie(final RelatieContext relatieContext) {
        // TODO: Groepen van specifieke relatie checken?

        for (final BetrokkenheidBericht betrokkenheidBericht : dezeOfLeeg(relatieContext.getBericht().getBetrokkenheden())) {
            BetrokkenheidView betrokkenheidModel = null;
            boolean doorgaan = true;
            if (relatieContext.getModel() != null) {
                if (betrokkenheidBericht.getObjectSleutel() != null) {
                    betrokkenheidModel = zoekBetrokkenheidMetSleutel(relatieContext.getModel().getBetrokkenheden(),
                            betrokkenheidBericht.getObjectSleutel());
                    if (betrokkenheidModel == null) {
                        relatieContext.getObjectenDieDeRegelOvertreden().add(betrokkenheidBericht);
                        doorgaan = false;
                    }
                }
            } else {
                if (betrokkenheidBericht.getObjectSleutel() != null) {
                    throw new IllegalStateException("Ongeldige constructie: nieuwe relatie, bestaande betrokkenheid");
                }
            }
            if (doorgaan) {
                controleerBetrokkenheidRichtingPersoon(new BetrokkenheidContext(
                        betrokkenheidBericht, betrokkenheidModel, relatieContext));
            }
        }
    }

    /**
     * Controleer voor alle 1-op-N associaties of alle technische sleutels uit het bericht overeen komen met
     * een bestaand id in de database, behorende tot de betreffende persoon.
     *
     * @param context de context
     */
    private void controleerObjectenVanPersoon(final PersoonContext context) {
        controleerObjectLijstVanPersoon(context,
                context.getBericht().getAdressen(), context.getModel().getAdressen());
        controleerObjectLijstVanPersoon(context,
                context.getBericht().getVoornamen(), context.getModel().getVoornamen());
        controleerObjectLijstVanPersoon(context,
                context.getBericht().getGeslachtsnaamcomponenten(), context.getModel().getGeslachtsnaamcomponenten());
        controleerObjectLijstVanPersoon(context,
                context.getBericht().getNationaliteiten(), context.getModel().getNationaliteiten());
        controleerObjectLijstVanPersoon(context,
                context.getBericht().getReisdocumenten(), context.getModel().getReisdocumenten());
    }

    /**
     * Controleer voor een specifieke 1-op-N associatie of alle technische sleutels uit het bericht overeen komen met
     * een bestaand id in de database, behorende tot de betreffende persoon.
     *
     * @param context de context
     * @param berichtEntiteiten de lijst uit het bericht
     * @param objectLijst de lijst in de database
     */
    private void controleerObjectLijstVanPersoon(final PersoonContext context,
            final List<? extends BerichtEntiteit> berichtEntiteiten,
            final Collection<? extends ModelMoment> objectLijst)
    {
        for (final BerichtEntiteit berichtEntiteit : dezeOfLeeg(berichtEntiteiten)) {
            boolean matchGevonden = false;
            if (berichtEntiteit.getObjectSleutel() != null) {
                for (final ModelMoment modelObject : objectLijst) {
                    if (modelObject.getID().toString().equals(berichtEntiteit.getObjectSleutel())) {
                        matchGevonden = true;
                    }
                }
                if (!matchGevonden) {
                    context.getObjectenDieDeRegelOvertreden().add(berichtEntiteit);
                }
            }
        }
    }

//    /**
//     * Controleer voor alle groepen of de technische sleutel uit het bericht overeen komt met
//     * een bestaand id in de database, behorende tot de betreffende persoon.
//     *
//     * @param context de context
//     */
//    private void controleerGroepenVanPersoon(final PersoonContext context) {
//        // TODO: Genereer technische sleutel fields en getters op 'simpele' groepen.
//        //controleerPersoonGeboorteGroep(persoonBericht, persoonModel, objectenDieDeRegelOvertreden);
//        //controleerPersoonIdentificatienummersGroep(persoonBericht, persoonModel, objectenDieDeRegelOvertreden);
//        //etc.
//    }

//    private void controleerPersoonGeboorteGroep(final PersoonContext context) {
//        TODO: Genereer technische sleutel fields en getters op 'simpele' groepen.
//        if (persoonBericht.getObjectSleutel() != null && persoonBericht.getGeboorte() != null
//                && persoonBericht.getGeboorte().getObjectSleutel() != null)
//        {
//        }
//    }

    /**
     * Utility methode die indien null een lege lijst terug geeft of anders de lijst zelf.
     *
     * @param lijst de lijst
     * @param <T> het type element in de lijst
     * @return een lege lijst (indien input null) of andere de lijst zelf
     */
    private <T> List<T> dezeOfLeeg(final List<T> lijst) {
        List<T> dezeOfLeeg = lijst;
        if (dezeOfLeeg == null) {
            dezeOfLeeg = new ArrayList<>();
        }
        return dezeOfLeeg;
    }

    /**
     * Context object waarin de benodigde data gebundeld wordt.
     *
     * @param <B> het type bericht object
     * @param <M> het type model object
     */
    private class Context<B, M> {
        private final B bericht;
        private final M model;
        private final List<BerichtEntiteit> objectenDieDeRegelOvertreden;
        private final Map<String, PersoonView> bestaandeBetrokkenen;

        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param context context
         */
        public Context(final B bericht, final M model, final Context<?, ?> context) {
            this(bericht, model, context.getObjectenDieDeRegelOvertreden(), context.bestaandeBetrokkenen);
        }

        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param objectenDieDeRegelOvertreden objectenDieDeRegelOvertreden
         * @param bestaandeBetrokkenen bestaandeBetrokkenen
         */
        public Context(final B bericht, final M model, final List<BerichtEntiteit> objectenDieDeRegelOvertreden,
                       final Map<String, PersoonView> bestaandeBetrokkenen)
        {
            super();
            this.bericht = bericht;
            this.model = model;
            this.objectenDieDeRegelOvertreden = objectenDieDeRegelOvertreden;
            this.bestaandeBetrokkenen = bestaandeBetrokkenen;
        }

        public B getBericht() {
            return bericht;
        }
        public M getModel() {
            return model;
        }
        public List<BerichtEntiteit> getObjectenDieDeRegelOvertreden() {
            return objectenDieDeRegelOvertreden;
        }
        public Map<String, PersoonView> getBestaandeBetrokkenen() {
            return bestaandeBetrokkenen;
        }
    }

    /**
     * Context object voor persoon.
     */
    private class PersoonContext extends Context<PersoonBericht, PersoonView> {
        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param context context
         */
        public PersoonContext(final PersoonBericht bericht, final PersoonView model, final Context<?, ?> context) {
            super(bericht, model, context);
        }
        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param objectenDieDeRegelOvertreden objectenDieDeRegelOvertreden
         * @param bestaandeBetrokkenen bestaandeBetrokkenen
         */
        public PersoonContext(final PersoonBericht bericht, final PersoonView model,
                final List<BerichtEntiteit> objectenDieDeRegelOvertreden,
                final Map<String, PersoonView> bestaandeBetrokkenen)
        {
            super(bericht, model, objectenDieDeRegelOvertreden, bestaandeBetrokkenen);
        }
    }

    /**
     * Context object voor relatie.
     */
    private class RelatieContext extends Context<RelatieBericht, RelatieView> {
        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param context context
         */
        public RelatieContext(final RelatieBericht bericht, final RelatieView model, final Context<?, ?> context) {
            super(bericht, model, context);
        }
        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param objectenDieDeRegelOvertreden objectenDieDeRegelOvertreden
         * @param bestaandeBetrokkenen bestaandeBetrokkenen
         */
        public RelatieContext(final RelatieBericht bericht, final RelatieView model,
                final List<BerichtEntiteit> objectenDieDeRegelOvertreden,
                final Map<String, PersoonView> bestaandeBetrokkenen)
        {
            super(bericht, model, objectenDieDeRegelOvertreden, bestaandeBetrokkenen);
        }
    }

    /**
     * Context object voor relatie.
     */
    private class BetrokkenheidContext extends Context<BetrokkenheidBericht, BetrokkenheidView> {
        /**
         * Constructor.
         *
         * @param bericht bericht
         * @param model model
         * @param context context
         */
        public BetrokkenheidContext(final BetrokkenheidBericht bericht, final BetrokkenheidView model,
                final Context<?, ?> context)
        {
            super(bericht, model, context);
        }
    }
}
