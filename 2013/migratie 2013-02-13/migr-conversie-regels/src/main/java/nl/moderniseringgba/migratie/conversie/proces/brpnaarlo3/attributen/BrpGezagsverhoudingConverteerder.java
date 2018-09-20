/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder1GezagInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpOuder2GezagInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Gezagsverhouding converteerder.
 */
@Component
public final class BrpGezagsverhoudingConverteerder extends BrpCategorieConverteerder<Lo3GezagsverhoudingInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private CurateleConverteerder curateleConverteerder;
    @Inject
    private DerdeConverteerder derdeConverteerder;
    @Inject
    private Gezag1Converteerder gezag1Converteerder;
    @Inject
    private Gezag2Converteerder gezag2Converteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud> bepaalConverteerder(
            final T inhoud) {
        final BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud> result;
        if (inhoud instanceof BrpOnderCurateleIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) curateleConverteerder;
        } else if (inhoud instanceof BrpDerdeHeeftGezagIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) derdeConverteerder;
        } else if (inhoud instanceof BrpOuder1GezagInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) gezag1Converteerder;
        } else if (inhoud instanceof BrpOuder2GezagInhoud) {
            result = (BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud>) gezag2Converteerder;
        } else {
            throw new IllegalArgumentException(
                    "BrpGezagsverhoudingConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Converteerder die weet hoe een Lo3GezagsverhoudingInhoud rij gemaakt moet worden.
     * 
     * @param <T>
     *            brp groep inhoud type
     */
    private abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3GezagsverhoudingInhoud> {

        @Override
        protected Lo3GezagsverhoudingInhoud maakNieuweInhoud() {
            return new Lo3GezagsverhoudingInhoud(null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpOnderCurateleIndicatieInhoud omgezet moeten worden naar
     * Lo3GezagsverhoudingInhoud.
     */
    @Component
    private static final class CurateleConverteerder extends AbstractConverteerder<BrpOnderCurateleIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpOnderCurateleIndicatieInhoud brpInhoud,
                final BrpOnderCurateleIndicatieInhoud brpVorigeInhoud) {
            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setIndicatieCurateleregister(null);
            } else {
                builder.setIndicatieCurateleregister(converteerder.converteerIndicatieCurateleRegister(brpInhoud
                        .getOnderCuratele()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpDerdeHeeftGezagIndicatieInhoud omgezet moeten worden naar
     * Lo3GezagsverhoudingInhoud.
     */
    @Component
    private static final class DerdeConverteerder extends AbstractConverteerder<BrpDerdeHeeftGezagIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpDerdeHeeftGezagIndicatieInhoud brpInhoud,
                final BrpDerdeHeeftGezagIndicatieInhoud brpVorigeInhoud) {
            final IndicatieGezagMinderjarigeBuilder indicatieBuilder =
                    new IndicatieGezagMinderjarigeBuilder(lo3Inhoud.getIndicatieGezagMinderjarige());

            if (brpInhoud == null) {
                indicatieBuilder.setDerde(null);
            } else {
                indicatieBuilder.setDerde(brpInhoud.getDerdeHeeftGezag());
            }

            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);
            builder.setIndicatieGezagMinderjarige(indicatieBuilder.build());
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpOuder1GezagInhoud omgezet moeten worden naar Lo3GezagsverhoudingInhoud.
     */
    @Component
    private static final class Gezag1Converteerder extends AbstractConverteerder<BrpOuder1GezagInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpOuder1GezagInhoud brpInhoud,
                final BrpOuder1GezagInhoud brpVorigeInhoud) {
            final IndicatieGezagMinderjarigeBuilder indicatieBuilder =
                    new IndicatieGezagMinderjarigeBuilder(lo3Inhoud.getIndicatieGezagMinderjarige());

            if (brpInhoud == null) {
                indicatieBuilder.setOuder1(null);
            } else {
                indicatieBuilder.setOuder1(brpInhoud.getOuderHeeftGezag());
            }

            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);
            builder.setIndicatieGezagMinderjarige(indicatieBuilder.build());
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpOuder1GezagInhoud omgezet moeten worden naar Lo3GezagsverhoudingInhoud.
     */
    @Component
    private static final class Gezag2Converteerder extends AbstractConverteerder<BrpOuder2GezagInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3GezagsverhoudingInhoud vulInhoud(
                final Lo3GezagsverhoudingInhoud lo3Inhoud,
                final BrpOuder2GezagInhoud brpInhoud,
                final BrpOuder2GezagInhoud brpVorigeInhoud) {
            final IndicatieGezagMinderjarigeBuilder indicatieBuilder =
                    new IndicatieGezagMinderjarigeBuilder(lo3Inhoud.getIndicatieGezagMinderjarige());

            if (brpInhoud == null) {
                indicatieBuilder.setOuder2(null);
            } else {
                indicatieBuilder.setOuder2(brpInhoud.getOuderHeeftGezag());
            }

            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder(lo3Inhoud);
            builder.setIndicatieGezagMinderjarige(indicatieBuilder.build());
            return builder.build();
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Builder voor de indicatie gezag minderjarige.
     */
    private static final class IndicatieGezagMinderjarigeBuilder {
        private boolean ouder1;
        private boolean ouder2;
        private boolean derde;

        /**
         * Maak een builder.
         * 
         * @param inhoud
         *            initiele inhoud
         */
        public IndicatieGezagMinderjarigeBuilder(final Lo3IndicatieGezagMinderjarige inhoud) {

            ouder1 =
                    Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.equalsElement(inhoud)
                            || Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.equalsElement(inhoud)
                            || Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2.equalsElement(inhoud);
            ouder2 =
                    Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.equalsElement(inhoud)
                            || Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.equalsElement(inhoud)
                            || Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2.equalsElement(inhoud);
            derde =
                    Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.equalsElement(inhoud)
                            || Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.equalsElement(inhoud)
                            || Lo3IndicatieGezagMinderjarigeEnum.DERDE.equalsElement(inhoud);
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        @Preconditie(Precondities.PRE059)
        public Lo3IndicatieGezagMinderjarige build() {
            final String code = (ouder1 ? "1" : "") + (ouder2 ? "2" : "") + (derde ? "D" : "");
            if ("".equals(code)) {
                return null;
            }
            if ("12D".equals(code)) {
                FoutmeldingUtil.gooiValidatieExceptie(
                        "Ouder 1, Ouder 2 en een derde hebben gezag. Dit is niet mogelijk in het LO3 model",
                        Precondities.PRE059);
                throw new IllegalStateException();
            }

            return new Lo3IndicatieGezagMinderjarige(code);
        }

        /**
         * Zet de indicatie voor ouder 1.
         * 
         * @param ouder1
         *            indicatie voor ouder 1
         */
        public void setOuder1(final Boolean ouder1) {
            this.ouder1 = Boolean.TRUE.equals(ouder1);
        }

        /**
         * Zet de indicatie voor ouder 2.
         * 
         * @param ouder2
         *            indicatie voor ouder 2
         */
        public void setOuder2(final Boolean ouder2) {
            this.ouder2 = Boolean.TRUE.equals(ouder2);
        }

        /**
         * Zet de indicatie voor gezag derde.
         * 
         * @param derde
         *            indicatie voor gezag derde
         */
        public void setDerde(final Boolean derde) {
            this.derde = Boolean.TRUE.equals(derde);
        }

    }
}
