/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Ouder converteerder.
 */
@Component
public final class BrpOuderConverteerder extends AbstractBrpCategorieConverteerder<Lo3OuderInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private GeboorteConverteerder geboorteConverteerder;
    @Inject
    private SamengesteldeNaamConverteerder samengesteldeNaamConverteerder;
    @Inject
    private IdentificatienumersConverteerder identificatienumersConverteerder;
    @Inject
    private GeslachtsaanduidingConverteerder geslachtsaanduidingConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3OuderInhoud> bepaalConverteerder(final B inhoud) {
        final BrpGroepConverteerder<B, Lo3OuderInhoud> result;
        if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) geboorteConverteerder;
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) samengesteldeNaamConverteerder;
        } else if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) identificatienumersConverteerder;
        } else if (inhoud instanceof BrpGeslachtsaanduidingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) geslachtsaanduidingConverteerder;
        } else {
            throw new IllegalArgumentException("HuwelijkConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Converteerder die weet hoe een nieuwe inhoud aan te maken.
     *
     * @param <T>
     *            brp groep inhoud type
     */
    public abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends BrpGroepConverteerder<T, Lo3OuderInhoud> {

        @Override
        public final Lo3OuderInhoud maakNieuweInhoud() {
            return new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeboorteInhoud omgezet moeten worden naar Lo3OuderInhoud.
     */
    @Component
    @Requirement({Requirements.CAP001, Requirements.CAP001_BL01, Requirements.CAP001_BL02 })
    public static final class GeboorteConverteerder extends AbstractConverteerder<BrpGeboorteInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3OuderInhoud vulInhoud(final Lo3OuderInhoud lo3Inhoud, final BrpGeboorteInhoud brpInhoud, final BrpGeboorteInhoud brpVorigeInhoud) {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

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
                        converteerder.converteerLocatie(
                            gemeenteCode,
                            buitenlandsePlaatsGeboorte,
                            buitenlandseRegioGeboorte,
                            landCode,
                            omschrijvingGeboortelocatie);

                builder.geboortedatum(converteerder.converteerDatum(brpInhoud.getGeboortedatum()));
                builder.geboorteGemeenteCode(locatie.getGemeenteCode());
                builder.geboorteLandCode(locatie.getLandCode());
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3OuderInhoud.
     */
    @Component
    public static final class SamengesteldeNaamConverteerder extends AbstractConverteerder<BrpSamengesteldeNaamInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3OuderInhoud vulInhoud(
            final Lo3OuderInhoud lo3Inhoud,
            final BrpSamengesteldeNaamInhoud brpInhoud,
            final BrpSamengesteldeNaamInhoud brpVorigeInhoud)
        {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.adellijkeTitelPredikaatCode(null);
                builder.voornamen(null);
                builder.voorvoegselGeslachtsnaam(null);
                builder.geslachtsnaam(null);
            } else {
                builder.adellijkeTitelPredikaatCode(converteerder.converteerAdellijkeTitelPredikaatCode(
                    brpInhoud.getAdellijkeTitelCode(),
                    brpInhoud.getPredicaatCode()));
                builder.voornamen(converteerder.converteerString(brpInhoud.getVoornamen()));
                builder.voorvoegselGeslachtsnaam(converteerder.converteerVoorvoegsel(brpInhoud.getVoorvoegsel(), brpInhoud.getScheidingsteken()));
                builder.geslachtsnaam(converteerder.converteerString(brpInhoud.getGeslachtsnaamstam()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpIdentificatienummersInhoud omgezet moeten worden naar Lo3OuderInhoud.
     */
    @Component
    public static final class IdentificatienumersConverteerder extends AbstractConverteerder<BrpIdentificatienummersInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3OuderInhoud vulInhoud(
            final Lo3OuderInhoud lo3Inhoud,
            final BrpIdentificatienummersInhoud brpInhoud,
            final BrpIdentificatienummersInhoud brpVorigeInhoud)
        {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.anummer(null);
                builder.burgerservicenummer(null);
            } else {
                builder.anummer(converteerder.converteerAdministratieNummer(brpInhoud.getAdministratienummer()));
                builder.burgerservicenummer(converteerder.converteerBurgerservicenummer(brpInhoud.getBurgerservicenummer()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeslachtsaanduidingInhoud omgezet moeten worden naar Lo3OuderInhoud.
     */
    @Component
    public static final class GeslachtsaanduidingConverteerder extends AbstractConverteerder<BrpGeslachtsaanduidingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3OuderInhoud vulInhoud(
            final Lo3OuderInhoud lo3Inhoud,
            final BrpGeslachtsaanduidingInhoud brpInhoud,
            final BrpGeslachtsaanduidingInhoud brpVorigeInhoud)
        {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.geslachtsaanduiding(null);
            } else {
                builder.geslachtsaanduiding(converteerder.converteerGeslachtsaanduiding(brpInhoud.getGeslachtsaanduidingCode()));
            }
            return builder.build();
        }

    }

}
