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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Persoon converteerder.
 */
@Component
public final class BrpPersoonConverteerder extends BrpCategorieConverteerder<Lo3PersoonInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private GeboorteConverteerder geboorteConverteerder;
    @Inject
    private AanschrijvingConverteerder aanschrijvingConverteerder;
    @Inject
    private SamengesteldeNaamConverteerder samengesteldeNaamConverteerder;
    @Inject
    private IdentificatienumersConverteerder identificatienumersConverteerder;
    @Inject
    private GeslachtsaanduidingConverteerder geslachtsaanduidingConverteerder;
    @Inject
    private InschrijvingConverteerder inschrijvingConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3PersoonInhoud>
            bepaalConverteerder(final T inhoud) {
        final BrpGroepConverteerder<T, Lo3PersoonInhoud> result;
        if (inhoud instanceof BrpGeboorteInhoud) {
            result = (BrpGroepConverteerder<T, Lo3PersoonInhoud>) geboorteConverteerder;
        } else if (inhoud instanceof BrpAanschrijvingInhoud) {
            result = (BrpGroepConverteerder<T, Lo3PersoonInhoud>) aanschrijvingConverteerder;
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            result = (BrpGroepConverteerder<T, Lo3PersoonInhoud>) samengesteldeNaamConverteerder;
        } else if (inhoud instanceof BrpIdentificatienummersInhoud) {
            result = (BrpGroepConverteerder<T, Lo3PersoonInhoud>) identificatienumersConverteerder;
        } else if (inhoud instanceof BrpGeslachtsaanduidingInhoud) {
            result = (BrpGroepConverteerder<T, Lo3PersoonInhoud>) geslachtsaanduidingConverteerder;
        } else if (inhoud instanceof BrpInschrijvingInhoud) {
            result = (BrpGroepConverteerder<T, Lo3PersoonInhoud>) inschrijvingConverteerder;
        } else {
            throw new IllegalArgumentException("BrpPersoonConverteerder bevat geen Groep converteerder voor: "
                    + inhoud);
        }

        return result;
    }

    /**
     * Converteerder die weet hoe een Lo3PersoonInhoud rij gemaakt moet worden.
     * 
     * @param <T>
     *            brp groep inhoud type
     */
    private abstract static class PersoonConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3PersoonInhoud> {

        @Override
        protected Lo3PersoonInhoud maakNieuweInhoud() {
            return new Lo3PersoonInhoud(null, null, null, null, null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpGeboorteInhoud omgezet moeten worden naar Lo3PersoonInhoud.
     */
    @Component
    @Requirement({ Requirements.CAP001, Requirements.CAP001_BL01, Requirements.CAP001_BL02 })
    private static final class GeboorteConverteerder extends PersoonConverteerder<BrpGeboorteInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3PersoonInhoud vulInhoud(
                final Lo3PersoonInhoud lo3Inhoud,
                final BrpGeboorteInhoud brpInhoud,
                final BrpGeboorteInhoud brpVorigeInhoud) {
            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpAanschrijvingInhoud omgezet moeten worden naar Lo3PersoonInhoud.
     */
    @Component
    private static final class AanschrijvingConverteerder extends PersoonConverteerder<BrpAanschrijvingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3PersoonInhoud vulInhoud(
                final Lo3PersoonInhoud lo3Inhoud,
                final BrpAanschrijvingInhoud brpInhoud,
                final BrpAanschrijvingInhoud brpVorigeInhoud) {
            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingNaamgebruikCode(null);
            } else {
                builder.setAanduidingNaamgebruikCode(converteerder.converteerAanduidingNaamgebruik(brpInhoud
                        .getWijzeGebruikGeslachtsnaamCode()));
            }
            return builder.build();
        }

    }

    /**
     * Converteerder die weet hoe een BrpSamengesteldeNaamInhoud omgezet moeten worden naar Lo3PersoonInhoud.
     */
    @Component
    @Requirement({ Requirements.CEL0210_BL01, Requirements.CEL0240, Requirements.CEL0240_BL01 })
    private static final class SamengesteldeNaamConverteerder extends
            PersoonConverteerder<BrpSamengesteldeNaamInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3PersoonInhoud vulInhoud(
                final Lo3PersoonInhoud lo3Inhoud,
                final BrpSamengesteldeNaamInhoud brpInhoud,
                final BrpSamengesteldeNaamInhoud brpVorigeInhoud) {
            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpIdentificatienummersInhoud omgezet moeten worden naar Lo3PersoonInhoud.
     */
    @Component
    @Requirement({ Requirements.CGR01, Requirements.CGR01_BL01 })
    private static final class IdentificatienumersConverteerder extends
            PersoonConverteerder<BrpIdentificatienummersInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3PersoonInhoud vulInhoud(
                final Lo3PersoonInhoud lo3Inhoud,
                final BrpIdentificatienummersInhoud brpInhoud,
                final BrpIdentificatienummersInhoud brpVorigeInhoud) {
            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpGeslachtsaanduidingInhoud omgezet moeten worden naar Lo3PersoonInhoud.
     */
    @Component
    private static final class GeslachtsaanduidingConverteerder extends
            PersoonConverteerder<BrpGeslachtsaanduidingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3PersoonInhoud vulInhoud(
                final Lo3PersoonInhoud lo3Inhoud,
                final BrpGeslachtsaanduidingInhoud brpInhoud,
                final BrpGeslachtsaanduidingInhoud brpVorigeInhoud) {
            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(lo3Inhoud);

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
     * Converteerder die weet hoe een BrpInschrijvingInhoud omgezet moeten worden naar Lo3PersoonInhoud.
     */
    @Component
    private static final class InschrijvingConverteerder extends PersoonConverteerder<BrpInschrijvingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3PersoonInhoud vulInhoud(
                final Lo3PersoonInhoud lo3Inhoud,
                final BrpInschrijvingInhoud brpInhoud,
                final BrpInschrijvingInhoud brpVorigeInhoud) {
            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setVolgendANummer(null);
                builder.setVorigANummer(null);
            } else {
                builder.setVolgendANummer(brpInhoud.getVolgendAdministratienummer());
                builder.setVorigANummer(brpInhoud.getVorigAdministratienummer());
            }
            return builder.build();
        }

    }

}
