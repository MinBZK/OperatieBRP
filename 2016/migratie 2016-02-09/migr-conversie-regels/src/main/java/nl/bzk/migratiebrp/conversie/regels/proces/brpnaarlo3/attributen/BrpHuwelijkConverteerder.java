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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpVerbintenisInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Huwelijk converteerder.
 */
@Component
public final class BrpHuwelijkConverteerder extends AbstractBrpCategorieConverteerder<Lo3HuwelijkOfGpInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private GeboorteConverteerder geboorteConverteerder;
    @Inject
    private SamengesteldeNaamConverteerder samengesteldeNaamConverteerder;
    @Inject
    private IdentificatienumersConverteerder identificatienumersConverteerder;
    @Inject
    private GeslachtsaanduidingConverteerder geslachtsaanduidingConverteerder;

    @Inject
    private RelatieConverteerder relatieConverteerder;
    @Inject
    private VerbintenisConverteerder verbintenisConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud> bepaalConverteerder(final B inhoud) {
        final BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud> result;
        if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) geboorteConverteerder;
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) samengesteldeNaamConverteerder;
        } else if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) identificatienumersConverteerder;
        } else if (inhoud instanceof BrpGeslachtsaanduidingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) geslachtsaanduidingConverteerder;
        } else if (inhoud instanceof BrpRelatieInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) relatieConverteerder;
        } else if (inhoud instanceof BrpVerbintenisInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) verbintenisConverteerder;
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
    public abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends BrpGroepConverteerder<T, Lo3HuwelijkOfGpInhoud> {

        @Override
        public final Lo3HuwelijkOfGpInhoud maakNieuweInhoud() {
            return new Lo3HuwelijkOfGpInhoud(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeboorteInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
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
        public Lo3HuwelijkOfGpInhoud vulInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final BrpGeboorteInhoud brpInhoud,
            final BrpGeboorteInhoud brpVorigeInhoud)
        {
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
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
        public Lo3HuwelijkOfGpInhoud vulInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final BrpSamengesteldeNaamInhoud brpInhoud,
            final BrpSamengesteldeNaamInhoud brpVorigeInhoud)
        {
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpIdentificatienummersInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
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
        public Lo3HuwelijkOfGpInhoud vulInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final BrpIdentificatienummersInhoud brpInhoud,
            final BrpIdentificatienummersInhoud brpVorigeInhoud)
        {
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.aNummer(null);
                builder.burgerservicenummer(null);
            } else {
                builder.aNummer(converteerder.converteerAdministratieNummer(brpInhoud.getAdministratienummer()));
                builder.burgerservicenummer(converteerder.converteerBurgerservicenummer(brpInhoud.getBurgerservicenummer()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeslachtsaanduidingInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    public static final class GeslachtsaanduidingConverteerder extends AbstractConverteerder<BrpGeslachtsaanduidingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3HuwelijkOfGpInhoud vulInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final BrpGeslachtsaanduidingInhoud brpInhoud,
            final BrpGeslachtsaanduidingInhoud brpVorigeInhoud)
        {
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.geslachtsaanduiding(null);
            } else {
                builder.geslachtsaanduiding(converteerder.converteerGeslachtsaanduiding(brpInhoud.getGeslachtsaanduidingCode()));
            }
            return builder.build();
        }

    }

    /**
     * Converteerder die weet hoe een BrpRelatieInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    public static final class RelatieConverteerder extends AbstractConverteerder<BrpRelatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3HuwelijkOfGpInhoud vulInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final BrpRelatieInhoud brpInhoud,
            final BrpRelatieInhoud brpVorigeInhoud)
        {
            LOG.debug("RelatieConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.datumSluitingHuwelijkOfAangaanGp(null);
                builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(null);
                builder.landCodeSluitingHuwelijkOfAangaanGp(null);
                builder.datumOntbindingHuwelijkOfGp(null);
                builder.gemeenteCodeOntbindingHuwelijkOfGp(null);
                builder.landCodeOntbindingHuwelijkOfGp(null);
                builder.redenOntbindingHuwelijkOfGpCode(null);
            } else {
                // Sluiting huwelijk
                final Lo3GemeenteLand locatieAanvang =
                        converteerder.converteerLocatie(
                            brpInhoud.getGemeenteCodeAanvang(),
                            brpInhoud.getBuitenlandsePlaatsAanvang(),
                            brpInhoud.getBuitenlandseRegioAanvang(),
                            brpInhoud.getLandOfGebiedCodeAanvang(),
                            brpInhoud.getOmschrijvingLocatieAanvang());

                builder.datumSluitingHuwelijkOfAangaanGp(converteerder.converteerDatum(brpInhoud.getDatumAanvang()));
                builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(locatieAanvang.getGemeenteCode());
                builder.landCodeSluitingHuwelijkOfAangaanGp(locatieAanvang.getLandCode());

                // Ontbinding huwelijk
                final Lo3GemeenteLand locatieEinde =
                        converteerder.converteerLocatie(
                            brpInhoud.getGemeenteCodeEinde(),
                            brpInhoud.getBuitenlandsePlaatsEinde(),
                            brpInhoud.getBuitenlandseRegioEinde(),
                            brpInhoud.getLandOfGebiedCodeEinde(),
                            brpInhoud.getOmschrijvingLocatieEinde());

                builder.datumOntbindingHuwelijkOfGp(converteerder.converteerDatum(brpInhoud.getDatumEinde()));
                builder.gemeenteCodeOntbindingHuwelijkOfGp(locatieEinde.getGemeenteCode());
                builder.landCodeOntbindingHuwelijkOfGp(locatieEinde.getLandCode());
                builder.redenOntbindingHuwelijkOfGpCode(converteerder.converteerRedenOntbindingHuwelijk(brpInhoud.getRedenEindeRelatieCode()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpVerbintenisInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    public static final class VerbintenisConverteerder extends AbstractConverteerder<BrpVerbintenisInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3HuwelijkOfGpInhoud vulInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final BrpVerbintenisInhoud brpInhoud,
            final BrpVerbintenisInhoud brpVorigeInhoud)
        {
            LOG.debug("VerbintenisConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.soortVerbintenis(null);
            } else {
                builder.soortVerbintenis(converteerder.converteerSoortVerbintenis(brpInhoud.getSoortRelatieCode()));
            }
            return builder.build();
        }
    }
}
