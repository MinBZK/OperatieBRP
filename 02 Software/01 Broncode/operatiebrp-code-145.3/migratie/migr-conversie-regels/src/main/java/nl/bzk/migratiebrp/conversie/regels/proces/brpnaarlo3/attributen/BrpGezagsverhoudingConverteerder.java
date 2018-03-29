/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpOuder1GezagInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpOuder2GezagInhoud;

/**
 * Gezagsverhouding converteerder.
 */
@Requirement(Requirements.CCA11)
public final class BrpGezagsverhoudingConverteerder extends AbstractBrpCategorieConverteerder<Lo3GezagsverhoudingInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpAttribuutConverteerder attribuutConverteerder;

    /**
     * constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpGezagsverhoudingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud> bepaalConverteerder(final T inhoud) {
        final BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud> result;
        if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) new GezagOuder1Converteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpNaamgebruikInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) new GezagOuder2Converteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) new DerdeHeeftGezagIndicatieConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) new OnderCurateleIndicatieConverteerder(attribuutConverteerder);
        } else {
            throw new IllegalArgumentException("BrpGezagsverhoudingConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /**
     * Converteerder die weet hoe een Lo3PersoonInhoud rij gemaakt moet worden.
     * @param <T> brp groep inhoud type
     */
    public abstract static class AbstractGezagsverhoudingConverteerder<T extends BrpGroepInhoud>
            extends AbstractBrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud> {
        /**
         * Deel indicatie '1' (Ouder 1 heeft gezag).
         */
        static final String DEEL_1 = "1";
        /**
         * Deel indicatie '2' (Ouder 2 heeft gezag).
         */
        static final String DEEL_2 = "2";
        /**
         * Deel indicatie '3' (Derde heeft gezag).
         */
        static final String DEEL_D = "D";

        AbstractGezagsverhoudingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        public final Lo3GezagsverhoudingInhoud maakNieuweInhoud() {
            return new Lo3GezagsverhoudingInhoud(null, null);
        }

        /**
         * Bepaal of de indicatie gezag (32.10) een deel indicatie heeft.
         * @param indicatie indicatie gezag
         * @param deel deel indicatie (1, 2, of D)
         * @return true als de indicatie gezag de deel indicatie bevat.
         */
        final boolean heeftDeelIndicatie(final Lo3IndicatieGezagMinderjarige indicatie, final String deel) {
            return indicatie != null && indicatie.getWaarde() != null && indicatie.getWaarde().contains(deel);
        }

        /**
         * Verwerk de deel indicaties naar een complete indicatie gezag en zet dat in de builder (inclusief
         * onderzoeken).
         * @param lo3Indicatie huidige lo3 indicatie
         * @param brpIndicatie brp indicatie
         * @param builder lo3 builder
         * @param ouder1Gezag deel indicatie ouder 1 heeft gezag
         * @param ouder2Gezag deel indicatie ouder 2 heeft gezag
         * @param derdeGezag deel indicatie derde heeft gezag
         */
        final void verwerkDeelIndicaties(
                final Lo3IndicatieGezagMinderjarige lo3Indicatie,
                final BrpBoolean brpIndicatie,
                final Lo3GezagsverhoudingInhoud.Builder builder,
                final boolean ouder1Gezag,
                final boolean ouder2Gezag,
                final boolean derdeGezag) {
            final boolean hasOuderlijkGezag = ouder1Gezag || ouder2Gezag || derdeGezag;
            if (hasOuderlijkGezag) {
                final StringBuilder code = new StringBuilder();

                if (ouder1Gezag) {
                    code.append(DEEL_1);
                }
                if (ouder2Gezag) {
                    code.append(DEEL_2);
                }
                if (derdeGezag) {
                    code.append(DEEL_D);
                }

                builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(code.toString(), bepaalOnderzoek(lo3Indicatie, brpIndicatie)));
            }
        }

        private Lo3Onderzoek bepaalOnderzoek(final Lo3IndicatieGezagMinderjarige lo3Indicatie, final BrpBoolean brpIndicatie) {
            final List<Lo3Onderzoek> onderzoeken = new ArrayList<>();
            if (lo3Indicatie != null && lo3Indicatie.getOnderzoek() != null) {
                onderzoeken.add(lo3Indicatie.getOnderzoek());
            }
            if (brpIndicatie != null && brpIndicatie.getOnderzoek() != null) {
                onderzoeken.add(brpIndicatie.getOnderzoek());
            }
            return Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        }
    }

    /**
     * Converteerder die weet hoe een BrpOuder1GezagInhoud omgezet moeten worden naar Lo3GezagsverhoudingInhoud.
     */
    public static final class GezagOuder1Converteerder extends AbstractGezagsverhoudingConverteerder<BrpOuder1GezagInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        /**
         * constructor.
         * @param attribuutConverteerder
         */
        public GezagOuder1Converteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpOuder1GezagInhoud brpInhoud,
                final BrpOuder1GezagInhoud brpVorigeInhoud) {
            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);

            // mogelijke waarden: 1, 2, D, 1D, 2D, 12
            boolean ouder1Gezag;
            final boolean ouder2Gezag = heeftDeelIndicatie(lo3Inhoud.getIndicatieGezagMinderjarige(), DEEL_2);
            final boolean derdeGezag = heeftDeelIndicatie(lo3Inhoud.getIndicatieGezagMinderjarige(), DEEL_D);

            if (brpInhoud == null) {
                ouder1Gezag = false;
            } else {
                ouder1Gezag = Boolean.TRUE.equals(brpInhoud.getOuderHeeftGezag().getWaarde());
            }

            verwerkDeelIndicaties(
                    lo3Inhoud.getIndicatieGezagMinderjarige(),
                    brpInhoud == null ? null : brpInhoud.getOuderHeeftGezag(),
                    builder,
                    ouder1Gezag,
                    ouder2Gezag,
                    derdeGezag);

            return builder.build();
        }

    }

    /**
     * Converteerder die weet hoe een BrpOuder2GezagInhoud omgezet moeten worden naar Lo3GezagsverhoudingInhoud.
     */
    public static final class GezagOuder2Converteerder extends AbstractGezagsverhoudingConverteerder<BrpOuder2GezagInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        /**
         * construcotr.
         * @param attribuutConverteerder
         */
        public GezagOuder2Converteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpOuder2GezagInhoud brpInhoud,
                final BrpOuder2GezagInhoud brpVorigeInhoud) {
            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);

            // mogelijke waarden: 1, 2, D, 1D, 2D, 12
            final boolean ouder1Gezag = heeftDeelIndicatie(lo3Inhoud.getIndicatieGezagMinderjarige(), DEEL_1);
            boolean ouder2Gezag;
            final boolean derdeGezag = heeftDeelIndicatie(lo3Inhoud.getIndicatieGezagMinderjarige(), DEEL_D);

            if (brpInhoud == null) {
                ouder2Gezag = false;
            } else {
                ouder2Gezag = Boolean.TRUE.equals(brpInhoud.getOuderHeeftGezag().getWaarde());
            }

            verwerkDeelIndicaties(
                    lo3Inhoud.getIndicatieGezagMinderjarige(),
                    brpInhoud == null ? null : brpInhoud.getOuderHeeftGezag(),
                    builder,
                    ouder1Gezag,
                    ouder2Gezag,
                    derdeGezag);

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpDerdeHeeftGezagIndicatieInhoud omgezet moeten worden naar
     * Lo3GezagsverhoudingInhoud.
     */
    public static final class DerdeHeeftGezagIndicatieConverteerder extends AbstractGezagsverhoudingConverteerder<BrpDerdeHeeftGezagIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        /**
         * constructor.
         * @param attribuutConverteerder
         */
        public DerdeHeeftGezagIndicatieConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpDerdeHeeftGezagIndicatieInhoud brpInhoud,
                final BrpDerdeHeeftGezagIndicatieInhoud brpVorigeInhoud) {
            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);

            // mogelijke waarden: 1, 2, D, 1D, 2D, 12
            final boolean ouder1Gezag = heeftDeelIndicatie(lo3Inhoud.getIndicatieGezagMinderjarige(), DEEL_1);
            final boolean ouder2Gezag = heeftDeelIndicatie(lo3Inhoud.getIndicatieGezagMinderjarige(), DEEL_2);
            boolean derdeGezag;

            if (brpInhoud == null) {
                derdeGezag = false;
            } else {
                derdeGezag = Boolean.TRUE.equals(brpInhoud.getIndicatie().getWaarde());
            }

            verwerkDeelIndicaties(
                    lo3Inhoud.getIndicatieGezagMinderjarige(),
                    brpInhoud == null ? null : brpInhoud.getIndicatie(),
                    builder,
                    ouder1Gezag,
                    ouder2Gezag,
                    derdeGezag);

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpOnderCurateleIndicatieInhoud omgezet moeten worden naar
     * Lo3GezagsverhoudingInhoud.
     */
    public static final class OnderCurateleIndicatieConverteerder extends AbstractGezagsverhoudingConverteerder<BrpOnderCurateleIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        /**
         * constructor.
         * @param attribuutConverteerder
         */
        public OnderCurateleIndicatieConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpOnderCurateleIndicatieInhoud brpInhoud,
                final BrpOnderCurateleIndicatieInhoud brpVorigeInhoud) {
            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.indicatieCurateleregister(null);
            } else {
                builder.indicatieCurateleregister(getAttribuutConverteerder().converteerIndicatieCurateleRegister(brpInhoud.getIndicatie()));
            }

            return builder.build();
        }
    }
}
