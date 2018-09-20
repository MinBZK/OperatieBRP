/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Inschrijving converteerder.
 */
@Requirement(Requirements.CCA07)
@Component
public final class BrpInschrijvingConverteerder extends BrpImmaterieleCategorienConverteerder<Lo3InschrijvingInhoud> {
    @Inject
    private OpschortingConverteerder opschortingConverteerder;
    @Inject
    private InschrijvingConverteerder inschrijvingConverteerder;
    @Inject
    private PersoonskaartConverteerder persoonskaartConverteerder;
    @Inject
    private VerstrekkingbeperkingConverteerder verstrekkingbeperkingConverteerder;
    @Inject
    private AfgeleidAdministratiefConverteerder afgeleidAdministratiefConverteerder;

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Inschrijving heeft geen documentatie
        return null;
    }

    @Override
    protected Lo3Historie bepaalHistorie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Inschrijving heeft geen historie
        return Lo3Historie.NULL_HISTORIE;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3InschrijvingInhoud> bepaalConverteerder(
            final B inhoud) {
        final BrpGroepConverteerder<B, Lo3InschrijvingInhoud> result;

        if (inhoud instanceof BrpOpschortingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) opschortingConverteerder;
        } else if (inhoud instanceof BrpInschrijvingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) inschrijvingConverteerder;
        } else if (inhoud instanceof BrpPersoonskaartInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) persoonskaartConverteerder;
        } else if (inhoud instanceof BrpVerstrekkingsbeperkingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) verstrekkingbeperkingConverteerder;
        } else if (inhoud instanceof BrpAfgeleidAdministratiefInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) afgeleidAdministratiefConverteerder;
        } else {
            throw new IllegalArgumentException("BrpInschrijvingConverteerder bevat geen Groep converteerder voor: "
                    + inhoud);
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Converteerder die weet hoe je een Lo3InschrijvingInhoud rij moet aanmaken.
     */

    private abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3InschrijvingInhoud> {

        @Override
        protected Lo3InschrijvingInhoud maakNieuweInhoud() {
            return new Lo3InschrijvingInhoud(null, null, null, null, null,
                    Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(), 0, null, null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpOpschortingInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Component
    @Requirement(Requirements.CCA07_BL05)
    private static final class OpschortingConverteerder extends AbstractConverteerder<BrpOpschortingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie(Definities.DEF058)
        protected Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpOpschortingInhoud brpInhoud,
                final BrpOpschortingInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumOpschortingBijhouding(null);
                builder.setRedenOpschortingBijhoudingCode(null);
            } else {
                builder.setDatumOpschortingBijhouding(converteerder.converteerDatum(brpInhoud.getDatumOpschorting()));
                builder.setRedenOpschortingBijhoudingCode(converteerder
                        .converteerRedenOpschortingBijhouding(brpInhoud.getRedenOpschortingBijhoudingCode()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpPersoonskaartInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Component
    @Requirement(Requirements.CCA07_BL02)
    private static final class PersoonskaartConverteerder extends AbstractConverteerder<BrpPersoonskaartInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpPersoonskaartInhoud brpInhoud,
                final BrpPersoonskaartInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setGemeentePKCode(null);
                builder.setIndicatiePKVolledigGeconverteerdCode(null);
            } else {
                builder.setGemeentePKCode(converteerder.converteerGemeenteCode(brpInhoud.getGemeentePKCode()));
                builder.setIndicatiePKVolledigGeconverteerdCode(converteerder
                        .converteerIndicatiePKVolledigGeconverteerd(brpInhoud.getIndicatiePKVolledigGeconverteerd()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpVerstrekkingsbeperkingInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Component
    @Requirement(Requirements.CCA07_BL04)
    private static final class VerstrekkingbeperkingConverteerder extends
            AbstractConverteerder<BrpVerstrekkingsbeperkingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpVerstrekkingsbeperkingInhoud brpInhoud,
                final BrpVerstrekkingsbeperkingInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setIndicatieGeheimCode(null);
            } else {
                builder.setIndicatieGeheimCode(converteerder.converteerIndicatieGeheim(brpInhoud.getHeeftIndicatie()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpAfgeleidAdministratiefInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Component
    @Requirement(Requirements.CCA07_BL03)
    private static final class AfgeleidAdministratiefConverteerder extends
            AbstractConverteerder<BrpAfgeleidAdministratiefInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpAfgeleidAdministratiefInhoud brpInhoud,
                final BrpAfgeleidAdministratiefInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumtijdstempel(null);
            } else {
                builder.setDatumtijdstempel(converteerder.converteerDatumtijdstempel(brpInhoud.getLaatsteWijziging()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpInschrijvingInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Component
    @Requirement(Requirements.CCA07_BL01)
    private static final class InschrijvingConverteerder extends AbstractConverteerder<BrpInschrijvingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpInschrijvingInhoud brpInhoud,
                final BrpInschrijvingInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumEersteInschrijving(null);
                builder.setVersienummer(0);
            } else {
                builder.setDatumEersteInschrijving(converteerder.converteerDatum(brpInhoud.getDatumInschrijving()));
                builder.setVersienummer(brpInhoud.getVersienummer());
            }

            return builder.build();
        }
    }

}
