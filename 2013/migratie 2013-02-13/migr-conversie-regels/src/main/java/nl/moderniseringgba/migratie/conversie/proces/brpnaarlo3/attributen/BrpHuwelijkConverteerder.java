/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.groep.BrpVerbintenisInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Huwelijk converteerder.
 */
@Component
public final class BrpHuwelijkConverteerder extends BrpCategorieConverteerder<Lo3HuwelijkOfGpInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private RelatieConverteerder relatieConverteerder;
    @Inject
    private IdentificatienummersConverteerder identificatieNummerConverteerder;
    @Inject
    private GeslachtsaanduidingConverteerder geslachtsaanduidingConverteerder;
    @Inject
    private GeboorteConverteerder geboorteConverteerder;
    @Inject
    private SamengesteldeNaamConverteerder samengesteldeNaamConverteerder;
    @Inject
    private VerbintenisConverteerder verbintenisConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud> bepaalConverteerder(
            final B inhoud) {
        final BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud> result;
        if (inhoud instanceof BrpRelatieInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) relatieConverteerder;
        } else if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) identificatieNummerConverteerder;
        } else if (inhoud instanceof BrpGeslachtsaanduidingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) geslachtsaanduidingConverteerder;
        } else if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) geboorteConverteerder;
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<B, Lo3HuwelijkOfGpInhoud>) samengesteldeNaamConverteerder;
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
    private abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3HuwelijkOfGpInhoud> {

        @Override
        protected Lo3HuwelijkOfGpInhoud maakNieuweInhoud() {
            return new Lo3HuwelijkOfGpInhoud(null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpRelatieInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    @Requirement({ Requirements.CAP001, Requirements.CAP001_BL01, Requirements.CAP001_BL02 })
    private static final class RelatieConverteerder extends AbstractConverteerder<BrpRelatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3HuwelijkOfGpInhoud vulInhoud(
                final Lo3HuwelijkOfGpInhoud lo3Inhoud,
                final BrpRelatieInhoud brpInhoud,
                final BrpRelatieInhoud brpVorigeInhoud) {
            LOG.debug("RelatieConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumSluitingHuwelijkOfAangaanGp(null);
                builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(null);
                builder.setLandCodeSluitingHuwelijkOfAangaanGp(null);
                builder.setDatumOntbindingHuwelijkOfGp(null);
                builder.setGemeenteCodeOntbindingHuwelijkOfGp(null);
                builder.setLandCodeOntbindingHuwelijkOfGp(null);
                builder.setRedenOntbindingHuwelijkOfGpCode(null);
            } else {
                // Sluiting huwelijk
                final Lo3GemeenteLand locatieAanvang =
                        converteerder.converteerLocatie(brpInhoud.getGemeenteCodeAanvang(),
                                brpInhoud.getPlaatsCodeAanvang(), brpInhoud.getBuitenlandsePlaatsAanvang(),
                                brpInhoud.getBuitenlandseRegioAanvang(), brpInhoud.getLandCodeAanvang(),
                                brpInhoud.getOmschrijvingLocatieAanvang());

                builder.setDatumSluitingHuwelijkOfAangaanGp(converteerder.converteerDatum(brpInhoud.getDatumAanvang()));
                builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(locatieAanvang.getGemeenteCode());
                builder.setLandCodeSluitingHuwelijkOfAangaanGp(locatieAanvang.getLandCode());

                // Ontbinding huwelijk
                final Lo3GemeenteLand locatieEinde =
                        converteerder.converteerLocatie(brpInhoud.getGemeenteCodeEinde(),
                                brpInhoud.getPlaatsCodeEinde(), brpInhoud.getBuitenlandsePlaatsEinde(),
                                brpInhoud.getBuitenlandseRegioEinde(), brpInhoud.getLandCodeEinde(),
                                brpInhoud.getOmschrijvingLocatieEinde());

                builder.setDatumOntbindingHuwelijkOfGp(converteerder.converteerDatum(brpInhoud.getDatumEinde()));
                builder.setGemeenteCodeOntbindingHuwelijkOfGp(locatieEinde.getGemeenteCode());
                builder.setLandCodeOntbindingHuwelijkOfGp(locatieEinde.getLandCode());
                builder.setRedenOntbindingHuwelijkOfGpCode(converteerder.converteerRedenOntbindingHuwelijk(brpInhoud
                        .getRedenEinde()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpIdentificatienummersInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    @Requirement({ Requirements.CGR01, Requirements.CGR01_BL01 })
    private static final class IdentificatienummersConverteerder extends
            AbstractConverteerder<BrpIdentificatienummersInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3HuwelijkOfGpInhoud vulInhoud(
                final Lo3HuwelijkOfGpInhoud lo3Inhoud,
                final BrpIdentificatienummersInhoud brpInhoud,
                final BrpIdentificatienummersInhoud brpVorigeInhoud) {
            LOG.debug("IdentificatienummersConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setaNummer(null);
                builder.setBurgerservicenummer(null);
            } else {
                builder.setaNummer(brpInhoud.getAdministratienummer());
                builder.setBurgerservicenummer(brpInhoud.getBurgerservicenummer());
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeslachtsaanduidingInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    private static final class GeslachtsaanduidingConverteerder extends
            AbstractConverteerder<BrpGeslachtsaanduidingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3HuwelijkOfGpInhoud vulInhoud(
                final Lo3HuwelijkOfGpInhoud lo3Inhoud,
                final BrpGeslachtsaanduidingInhoud brpInhoud,
                final BrpGeslachtsaanduidingInhoud brpVorigeInhoud) {
            LOG.debug("GeslachtsaanduidingConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setGeslachtsaanduiding(null);
            } else {
                builder.setGeslachtsaanduiding(converteerder.converteerGeslachtsaanduiding(brpInhoud
                        .getGeslachtsaanduiding()));
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeboorteInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    @Requirement({ Requirements.CAP001, Requirements.CAP001_BL01, Requirements.CAP001_BL02 })
    private static final class GeboorteConverteerder extends AbstractConverteerder<BrpGeboorteInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3HuwelijkOfGpInhoud vulInhoud(
                final Lo3HuwelijkOfGpInhoud lo3Inhoud,
                final BrpGeboorteInhoud brpInhoud,
                final BrpGeboorteInhoud brpVorigeInhoud) {
            LOG.debug("GeboorteConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setGeboortedatum(null);
                builder.setGeboorteGemeenteCode(null);
                builder.setGeboorteLandCode(null);
            } else {
                builder.setGeboortedatum(converteerder.converteerDatum(brpInhoud.getGeboortedatum()));

                final Lo3GemeenteLand locatie =
                        converteerder.converteerLocatie(brpInhoud.getGemeenteCode(), brpInhoud.getPlaatsCode(),
                                brpInhoud.getBuitenlandseGeboorteplaats(), brpInhoud.getBuitenlandseRegioGeboorte(),
                                brpInhoud.getLandCode(), brpInhoud.getOmschrijvingGeboortelocatie());

                builder.setGeboorteGemeenteCode(locatie.getGemeenteCode());
                builder.setGeboorteLandCode(locatie.getLandCode());
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    @Requirement(Requirements.CEL0210_BL01)
    private static final class SamengesteldeNaamConverteerder extends
            AbstractConverteerder<BrpSamengesteldeNaamInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3HuwelijkOfGpInhoud vulInhoud(
                final Lo3HuwelijkOfGpInhoud lo3Inhoud,
                final BrpSamengesteldeNaamInhoud brpInhoud,
                final BrpSamengesteldeNaamInhoud brpVorigeInhoud) {
            LOG.debug("SamengesteldeNaamConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAdellijkeTitelPredikaatCode(null);
                builder.setVoornamen(null);
                builder.setVoorvoegselGeslachtsnaam(null);
                builder.setGeslachtsnaam(null);
            } else {
                builder.setAdellijkeTitelPredikaatCode(converteerder.converteerAdellijkeTitelPredikaatCode(
                        brpInhoud.getAdellijkeTitelCode(), brpInhoud.getPredikaatCode()));
                builder.setVoornamen(brpInhoud.getVoornamen());
                builder.setVoorvoegselGeslachtsnaam(converteerder.converteerVoorvoegsel(brpInhoud.getVoorvoegsel(),
                        brpInhoud.getScheidingsteken()));
                builder.setGeslachtsnaam(brpInhoud.getGeslachtsnaam());
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3HuwelijkOfGpInhoud.
     */
    @Component
    private static final class VerbintenisConverteerder extends AbstractConverteerder<BrpVerbintenisInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3HuwelijkOfGpInhoud vulInhoud(
                final Lo3HuwelijkOfGpInhoud lo3Inhoud,
                final BrpVerbintenisInhoud brpInhoud,
                final BrpVerbintenisInhoud brpVorigeInhoud) {
            LOG.debug("VerbintenisConverteerder.vulInhoud(lo3Inhoud=<>, brpInhoud={}", brpInhoud);
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setSoortVerbintenis(null);
            } else {
                builder.setSoortVerbintenis(converteerder.converteerSoortVerbintenis(brpInhoud.getSoortRelatieCode()));
            }
            return builder.build();
        }
    }

}
