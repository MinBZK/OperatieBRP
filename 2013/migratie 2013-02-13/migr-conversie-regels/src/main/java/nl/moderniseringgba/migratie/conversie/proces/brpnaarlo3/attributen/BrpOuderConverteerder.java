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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Ouder converteerder.
 */
@Component
public final class BrpOuderConverteerder extends BrpCategorieConverteerder<Lo3OuderInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private IdentificatienummersConverteerder identificatienummersConverteerder;
    @Inject
    private GeslachtsaanduidingConverteerder geslachtsaanduidingConverteerder;
    @Inject
    private GeboorteConverteerder geboorteConverteerder;
    @Inject
    private SamengesteldeNaamConverteerder samengesteldeNaamConverteerder;
    @Inject
    private OuderConverteerder ouderConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3OuderInhoud> bepaalConverteerder(final B inhoud) {
        final BrpGroepConverteerder<B, Lo3OuderInhoud> result;
        if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) identificatienummersConverteerder;
        } else if (inhoud instanceof BrpGeslachtsaanduidingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) geslachtsaanduidingConverteerder;
        } else if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) geboorteConverteerder;
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) samengesteldeNaamConverteerder;
        } else if (inhoud instanceof BrpOuderInhoud) {
            result = (BrpGroepConverteerder<B, Lo3OuderInhoud>) ouderConverteerder;
        } else {
            throw new IllegalArgumentException("BrpOuderConverteerder bevat geen Groep converteerder voor: " + inhoud);
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
            BrpGroepConverteerder<T, Lo3OuderInhoud> {

        @Override
        protected Lo3OuderInhoud maakNieuweInhoud() {
            return new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpIdentificatienummersInhoud omgezet moeten worden naar Lo3OuderInhoud.
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
        protected Lo3OuderInhoud vulInhoud(
                final Lo3OuderInhoud lo3Inhoud,
                final BrpIdentificatienummersInhoud brpInhoud,
                final BrpIdentificatienummersInhoud brpVorigeInhoud) {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpGeslachtsaanduidingInhoud omgezet moeten worden naar Lo3OuderInhoud.
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
        protected Lo3OuderInhoud vulInhoud(
                final Lo3OuderInhoud lo3Inhoud,
                final BrpGeslachtsaanduidingInhoud brpInhoud,
                final BrpGeslachtsaanduidingInhoud brpVorigeInhoud) {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpGeboorteInhoud omgezet moeten worden naar Lo3OuderInhoud.
     */
    @Component
    @Requirement({ Requirements.CAP001, Requirements.CAP001_BL01, Requirements.CAP001_BL02, Requirements.CGR03,
            Requirements.CGR03_BL01, Requirements.CGR03_BL02, Requirements.CGR03_BL03 })
    private static final class GeboorteConverteerder extends AbstractConverteerder<BrpGeboorteInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3OuderInhoud vulInhoud(
                final Lo3OuderInhoud lo3Inhoud,
                final BrpGeboorteInhoud brpInhoud,
                final BrpGeboorteInhoud brpVorigeInhoud) {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3OuderInhoud.
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
        protected Lo3OuderInhoud vulInhoud(
                final Lo3OuderInhoud lo3Inhoud,
                final BrpSamengesteldeNaamInhoud brpInhoud,
                final BrpSamengesteldeNaamInhoud brpVorigeInhoud) {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpOuderInhoud omgezet moeten worden naar Lo3OuderInhoud.
     */
    @Component
    private static final class OuderConverteerder extends AbstractConverteerder<BrpOuderInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3OuderInhoud vulInhoud(
                final Lo3OuderInhoud lo3Inhoud,
                final BrpOuderInhoud brpInhoud,
                final BrpOuderInhoud brpVorigeInhoud) {
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setFamilierechtelijkeBetrekking(null);

            } else {
                builder.setFamilierechtelijkeBetrekking(converteerder.converteerDatum(brpInhoud.getDatumAanvang()));
                if (builder.getGeslachtsnaam() == null) {
                    builder.setGeslachtsnaam(".");
                }
            }
            return builder.build();
        }
    }

}
