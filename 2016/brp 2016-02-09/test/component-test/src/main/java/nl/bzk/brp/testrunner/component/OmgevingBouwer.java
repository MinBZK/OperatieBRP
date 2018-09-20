/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.services.gbasync.GbaSyncHelper;
import nl.bzk.brp.testrunner.component.util.CacheHelper;
import nl.bzk.brp.testrunner.component.util.HandelingHelper;
import nl.bzk.brp.testrunner.component.util.LeveringautorisatieHelper;
import nl.bzk.brp.testrunner.component.util.PersoonDslHelper;
import nl.bzk.brp.testrunner.omgeving.Component;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.omgeving.StandaardOmgeving;

/**
 * Helper klasse om een omgeving op te bouwen.
 */
public final class OmgevingBouwer {


    private StandaardOmgeving omgeving = new StandaardOmgeving();

    public OmgevingBouwer metLegeDatabase() {
//        omgeving.add(new DatabaseComponentImpl(omgeving));
        return metArtDataDatabase();
    }

    public OmgevingBouwer metArtDataDatabase() {
        omgeving.add(new TestDatabaseComponentImpl(omgeving));
        return this;
    }

    @Deprecated
    public OmgevingBouwer metDummyRouteringCentrale() {
        omgeving.add(new DummyRouteringCentraleComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metRouteringCentrale() {
        omgeving.add(new RouteringCentraleComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metMutatielevering() {
        omgeving.add(new MutatieLeveringComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metVerzending() {
        omgeving.add(new VerzendingComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metAfnemer() {
        omgeving.add(new AfnemerVoorbeeldImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metSynchronisatieBerichtAfnemer() {
        omgeving.add(new SynchronisatieBerichtAfnemerImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metSynchronisatie() {
        metSleutelbos();
        omgeving.add(new SynchronisatieComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metOnderhoudAfnemerindicaties() {
        metSleutelbos();
        omgeving.add(new OnderhoudAfnemerindicatieComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metSleutelbos() {
        omgeving.add(new SleutelbosComponentImpl(omgeving));
        return this;
    }

    public OmgevingBouwer metRelateren() {
        omgeving.add(new RelaterenComponentImpl(omgeving));
        return this;
    }

    public BrpOmgeving maak() {
        return new BrpOmgevingImpl(omgeving.maak());
    }

    private static class BrpOmgevingImpl implements BrpOmgeving {

        private static final Logger LOGGER = LoggerFactory.getLogger();
        private final Omgeving omgeving;
        private final GbaSyncHelper gbaSyncHelper;

        public BrpOmgevingImpl(final Omgeving omgeving) {
            this.omgeving = omgeving;
            gbaSyncHelper = new GbaSyncHelper(this);
        }

        @Override
        public void start() {
            omgeving.start();
        }

        @Override
        public void stop() {
            omgeving.stop();
        }

        @Override
        public boolean bevat(final String logischeNaam) {
            return omgeving.bevat(logischeNaam);
        }

        @Override
        public Component geefComponent(final String logischeNaam) {
            return omgeving.geefComponent(logischeNaam);
        }

        @Override
        public <T> List<T> geefComponenten(final Class<T> type) {
            return omgeving.geefComponenten(type);
        }

        @Override
        public <T> T geefComponent(final Class<T> type) {
            return omgeving.geefComponent(type);
        }

        @Override
        public String geefOmgevingHost() {
            return omgeving.geefOmgevingHost();
        }

        @Override
        public boolean isGestart() {
            return omgeving.isGestart();
        }

        @Override
        public boolean isGestopt() {
            return omgeving.isGestopt();
        }

        @Override
        public Iterable<Component> geefComponenten() {
            return omgeving.geefComponenten();
        }

        /**
         * Wacht tot alle componenten functioneel beschikbaar zijn.
         */
        @Override
        public void wachtTotFunctioneelBeschikbaar() throws InterruptedException {
            LOGGER.info("Start wacht tot omgeving functioneel beschikbaar is.");
            // TODO beter nog om in aparte thread te doen, met timeout
            while (true) {
                boolean componentNietBeschikbaar = false;
                for (final Component component : geefComponenten()) {
                    if (!component.isFunctioneelBeschikbaar()) {
                        LOGGER.info("Component {} nog niet beschikbaar...", component.getLogischeNaam());
                        componentNietBeschikbaar = true;
                        break;
                    }
                }
                if (componentNietBeschikbaar) {
                    LOGGER.info("Omgeving nog niet beschikbaar, even wachten...");
                    TimeUnit.SECONDS.sleep(1);
                    continue;
                }
                LOGGER.info("Omgeving beschikbaar!");
                break;
            }
        }

        @Override
        public BrpDatabase database() {
            return geefComponent(BrpDatabase.class);
        }

        @Override
        public CacheHelper cache() {
            return new CacheHelper(omgeving);
        }

        @Override
        public HandelingHelper handeling() {
            return new HandelingHelper(this);
        }

        @Override
        public LeveringautorisatieHelper leveringautorisaties() {
            return new LeveringautorisatieHelper(this);
        }

        @Override
        public PersoonDslHelper persoonDsl() {
            return new PersoonDslHelper(this);
        }

        @Override
        public Synchronisatie synchronisatie() {
            return geefComponent(Synchronisatie.class);
        }

        @Override
        public GbaSyncHelper gba() {
            return gbaSyncHelper;
        }

        @Override
        public RouteringCentrale routering() {
            return geefComponent(RouteringCentrale.class);
        }

        @Override
        public OnderhoudAfnemerindicatie afnemerindicaties() {
            return geefComponent(OnderhoudAfnemerindicatie.class);
        }

        @Override
        public Relateren relateren() {
            return geefComponent(Relateren.class);
        }
    }

}
