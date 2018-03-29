/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.operatie;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Ondersteunt het (beperkt) herhalen van operaties, optioneel met vertraging, totdat de operatie successvol is, of
 * totdat het maximum aantal pogingen wordt overschreden. Success is gedefinieerd als 'geen exception'.
 * <p>
 * </p>
 * Er kan gewerkt worden zowel met Callable instances als met Runnable instances. Callable heeft de voorkeur, aangezien
 * die checked exceptions mag gooien, en Runnable alleen runtime exceptions kan gooien.
 */
public class Herhaal {

    /**
     * De standaard basis vertraging: 1 seconde.
     */
    public static final long STANDAARD_VERTRAGING_MILLIS = 1000L;

    /**
     * Het standaard maximum aantal pogingen: 10.
     */
    public static final int STANDAARD_MAXIMUM_AANTAL_POGINGEN = 10;

    /**
     * De standaard wacht-strategie: Willekeurig tussen 0 en 2 keer de basis vertraging.
     */
    public static final Strategie STANDAARD_STRATEGIE = Strategie.WILLEKEURIG;
    public static final int INT_2 = 2;

    /**
     * De strategie voor het bepalen van de vertraging tussen herhaalde pogingen.
     */
    public enum Strategie {
        /**
         * Gebruik vast de basis vertraging.
         */
        REGELMATIG {
            @Override
            public long berekenWachttijd(final long basis, final int poging) {
                return basis;
            }
        },
        /**
         * Gebruik een vertraging willekeurig tussen de 0 en 2 keer de basis vertraging.
         */
        WILLEKEURIG {
            @Override
            public long berekenWachttijd(final long basis, final int poging) {
                SecureRandom random = new SecureRandom();
                return Math.round(basis * random.nextDouble() * INT_2);
            }
        },
        /**
         * Gebruik een lineair toenemende vertraging. De eerste keer de basis vertraging, de tweede keer 2 keer de basis
         * vertraging, etc.
         */
        LINEAIR {
            @Override
            public long berekenWachttijd(final long basis, final int poging) {
                return basis * poging;
            }
        },
        /**
         * Gebruik een exponentieel toenemende vertraging. De eerste keer de basis vertraging, en bij elke volgende
         * poging verdubbelt de vertraging.
         */
        EXPONENTIEEL {
            @Override
            public long berekenWachttijd(final long basis, final int poging) {
                return Math.round(basis * Math.pow(INT_2, poging - 1D));
            }
        };

        /**
         * Bereken de wachttijd voor de vertraging strategie op basis van een basis wachttijd en het aantal al
         * ondernomen pogingen.
         * @param basis De basis wachttijd
         * @param poging Het aantal al ondernomen pogingen.
         * @return De wachttijd voor de volgede poging.
         */
        abstract long berekenWachttijd(final long basis, final int poging);
    }

    private static final Herhaal STANDAARD = new Herhaal(STANDAARD_VERTRAGING_MILLIS, STANDAARD_MAXIMUM_AANTAL_POGINGEN, STANDAARD_STRATEGIE);

    private static final Logger LOG = LoggerFactory.getLogger();

    private final long basisVertragingMillis;
    private final int maximumAantalPogingen;
    private final Strategie strategie;

    /**
     * Maak een Herhaal object aan met gegeven basis vertraging, maximum aantal pogingen, en een wachtstrategie.
     * @param basisVertragingMillis Het basis aantal milliseconden om te wachten tussen pogingen.
     * @param maximumAantalPogingen Het maximum aantal pogingen om te doen alvorens op te geven.
     * @param strategie De wachtstrategie om te gebruiken tussen de pogingen.
     */
    public Herhaal(final long basisVertragingMillis, final int maximumAantalPogingen, final Strategie strategie) {
        this.basisVertragingMillis = basisVertragingMillis;
        this.maximumAantalPogingen = maximumAantalPogingen;
        this.strategie = strategie;
    }

    /**
     * Herhaal de operatie met de standaard instellinging. Dat wil zeggen probeer het maximaal 10 keer met tussen elke
     * poging een willekeurige vertraging tussen de 0 en 2 seconden.
     * @param operatie De operatie om uit te voeren.
     * @param <R> Het return-type van de operatie
     * @return Het resultaat van de geslaagde uitvoering van de operatie.
     * @throws HerhaalException Als alle pogingen falen.
     */
    public static <R> R herhaalOperatie(final Callable<R> operatie) throws HerhaalException {
        return STANDAARD.herhaal(operatie);
    }

    /**
     * Herhaal de operatie met de standaard instellinging. Dat wil zeggen probeer het maximaal 10 keer met tussen elke
     * poging een willekeurige vertraging tussen de 0 en 2 seconden.
     * @param operatie De operatie om uit te voeren.
     * @throws HerhaalException Als alle pogingen falen.
     */
    public static void herhaalOperatie(final Runnable operatie) throws HerhaalException {
        STANDAARD.herhaal(operatie);
    }

    /**
     * Geeft het maximum aantal pogingen terug.
     * @return Het maximum aantal pogingen.
     */
    public final int getMaximumAantalPogingen() {
        return maximumAantalPogingen;
    }

    /**
     * Herhaal de operatie met de instellinging van de Herhaal instantie.
     * @param operatie De operatie om uit te voeren.
     * @param <R> Het return-type van de operatie
     * @return Het resultaat van de geslaagde uitvoering van de operatie.
     * @throws HerhaalException Als alle pogingen falen.
     */
    public final <R> R herhaal(final Callable<R> operatie) throws HerhaalException {
        int pogingNummer = 1;
        final List<Exception> pogingExcepties = new ArrayList<>(maximumAantalPogingen);
        while (pogingNummer <= maximumAantalPogingen) {
            boolean laatstePogingOk = true;
            try {
                return operatie.call();
            } catch (final Exception e /* Catch Exception is nodig voor correcte functionaliteit */) {
                laatstePogingOk = false;
                if (e instanceof ExceptionWrapper) {
                    pogingExcepties.add(((ExceptionWrapper) e).getWrapped());
                } else {
                    pogingExcepties.add(e);
                }

                if (e instanceof StopHerhalingMarker) {
                    pogingNummer = maximumAantalPogingen;
                }

                if (pogingNummer < maximumAantalPogingen) {
                    Herhaal.wacht(strategie.berekenWachttijd(basisVertragingMillis, pogingNummer));
                }
                pogingNummer += 1;
            } finally {
                // Log eerdere excepties bij uiteindelijk succes
                // Bij uitendelijk falen wordt hier niet gelogd maar worden de excepties naar boven doorgegeven.
                if (laatstePogingOk && pogingNummer > 1) {
                    LOG.debug("Operatie succesvol bij {}e poging. Excepties bij eerdere pogingen:", pogingNummer);
                    for (int i = 0; i < pogingExcepties.size(); i++) {
                        final Exception exception = pogingExcepties.get(i);
                        LOG.debug("Exceptie bij " + (i + 1) + "e poging:", exception);
                    }
                }
            }
        }
        throw new HerhaalException(pogingExcepties);
    }

    /**
     * Herhaal de operatie met de instellinging van de Herhaal instantie.
     * @param operatie De operatie om uit te voeren.
     * @throws HerhaalException Als alle pogingen falen.
     */
    public final void herhaal(final Runnable operatie) throws HerhaalException {
        herhaal(() -> {
            operatie.run();
            return null;
        });
    }

    private static void wacht(final long milliseconden) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconden);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.info("Wachten verstoord", e);
        }
    }

    @Override
    public final String toString() {
        return "Herhaal [basisVertragingMillis="
                + basisVertragingMillis
                + ", maximumAantalPogingen="
                + maximumAantalPogingen
                + ", strategie="
                + strategie
                + "]";
    }

}
