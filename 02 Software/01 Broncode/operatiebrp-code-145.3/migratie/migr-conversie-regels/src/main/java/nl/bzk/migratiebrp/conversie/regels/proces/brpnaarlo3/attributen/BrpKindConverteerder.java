/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;

/**
 * Kind converteerder.
 */
public final class BrpKindConverteerder extends AbstractBrpCategorieConverteerder<Lo3KindInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpAttribuutConverteerder attribuutConverteerder;
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpKindConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3KindInhoud> bepaalConverteerder(final T inhoud) {
        final BrpGroepConverteerder<T, Lo3KindInhoud> result;
        if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<T, Lo3KindInhoud>) new GeboorteConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<T, Lo3KindInhoud>) new SamengesteldeNaamConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<T, Lo3KindInhoud>) new IdentificatienumersConverteerder(attribuutConverteerder);
        } else {
            throw new IllegalArgumentException("BrpKindConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /**
     * Converteerder die weet hoe een Lo3KindInhoud rij gemaakt moet worden.
     * @param <T> brp groep inhoud type
     */
    public abstract static class AbstractKindConverteerder<T extends BrpGroepInhoud> extends AbstractBrpGroepConverteerder<T, Lo3KindInhoud> {

        AbstractKindConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        public final Lo3KindInhoud maakNieuweInhoud() {
            return new Lo3KindInhoud(null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeboorteInhoud omgezet moeten worden naar Lo3KindInhoud.
     */
    public static final class GeboorteConverteerder extends AbstractKindConverteerder<BrpGeboorteInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public GeboorteConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3KindInhoud vulInhoud(final Lo3KindInhoud lo3Inhoud, final BrpGeboorteInhoud brpInhoud, final BrpGeboorteInhoud brpVorigeInhoud) {
            final Lo3KindInhoud.Builder builder = new Lo3KindInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.geboortedatum(null);
                builder.geboorteGemeenteCode(null);
                builder.geboorteLandCode(null);
            } else {
                final BrpGemeenteCode gemeenteCode = brpInhoud.getGemeenteCode();
                final BrpString buitenlandsePlaatsGeboorte = brpInhoud.getBuitenlandsePlaatsGeboorte();
                final BrpString buitenlandseRegioGeboorte = brpInhoud.getBuitenlandseRegioGeboorte();
                final BrpLandOfGebiedCode landCode = brpInhoud.getLandOfGebiedCode();
                final BrpString omschrijvingGeboortelocatie = brpInhoud.getOmschrijvingGeboortelocatie();

                final Lo3GemeenteLand locatie =
                        getAttribuutConverteerder().converteerLocatie(
                                gemeenteCode,
                                buitenlandsePlaatsGeboorte,
                                buitenlandseRegioGeboorte,
                                landCode,
                                omschrijvingGeboortelocatie);

                builder.geboortedatum(getAttribuutConverteerder().converteerDatum(brpInhoud.getGeboortedatum()));
                builder.geboorteGemeenteCode(locatie.getGemeenteCode());
                builder.geboorteLandCode(locatie.getLandCode());
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3KindInhoud.
     */
    public static final class SamengesteldeNaamConverteerder extends AbstractKindConverteerder<BrpSamengesteldeNaamInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public SamengesteldeNaamConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3KindInhoud vulInhoud(
                final Lo3KindInhoud lo3Inhoud,
                final BrpSamengesteldeNaamInhoud brpInhoud,
                final BrpSamengesteldeNaamInhoud brpVorigeInhoud) {
            final Lo3KindInhoud.Builder builder = new Lo3KindInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.adellijkeTitelPredikaatCode(null);
                builder.voornamen(null);
                builder.voorvoegselGeslachtsnaam(null);
                builder.geslachtsnaam(null);
            } else {
                builder.adellijkeTitelPredikaatCode(
                        getAttribuutConverteerder().converteerAdellijkeTitelPredikaatCode(brpInhoud.getAdellijkeTitelCode(), brpInhoud.getPredicaatCode()));
                builder.voornamen(getAttribuutConverteerder().converteerString(brpInhoud.getVoornamen()));
                builder.voorvoegselGeslachtsnaam(getAttribuutConverteerder().converteerVoorvoegsel(brpInhoud.getVoorvoegsel(), brpInhoud.getScheidingsteken()));
                builder.geslachtsnaam(getAttribuutConverteerder().converteerString(brpInhoud.getGeslachtsnaamstam()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpIdentificatienummersInhoud omgezet moeten worden naar Lo3KindInhoud.
     */
    public static final class IdentificatienumersConverteerder extends AbstractKindConverteerder<BrpIdentificatienummersInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public IdentificatienumersConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3KindInhoud vulInhoud(
                final Lo3KindInhoud lo3Inhoud,
                final BrpIdentificatienummersInhoud brpInhoud,
                final BrpIdentificatienummersInhoud brpVorigeInhoud) {
            final Lo3KindInhoud.Builder builder = new Lo3KindInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.aNummer(null);
                builder.burgerservicenummer(null);
            } else {
                builder.aNummer(getAttribuutConverteerder().converteerAdministratieNummer(brpInhoud.getAdministratienummer()));
                builder.burgerservicenummer(getAttribuutConverteerder().converteerBurgerservicenummer(brpInhoud.getBurgerservicenummer()));
            }
            return builder.build();
        }
    }

}
