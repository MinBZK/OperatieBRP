/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Reisdocument converteerder.
 */
@Component
public final class BrpReisdocumentConverteerder extends BrpImmaterieleCategorienConverteerder<Lo3ReisdocumentInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private ReisdocumentConverteerder reisdocumentConverteerder;
    @Inject
    private SignaleringConverteerder signaleringConverteerder;
    @Inject
    private BuitenlandsConverteerder bitenlandsConverteerder;
    @Inject
    private BrpAttribuutConverteerder converteerder;

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> bepaalConverteerder(
            final T inhoud) {
        BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> result;

        if (inhoud instanceof BrpReisdocumentInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) reisdocumentConverteerder;
        } else if (inhoud instanceof BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) signaleringConverteerder;
        } else if (inhoud instanceof BrpBezitBuitenlandsReisdocumentIndicatieInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) bitenlandsConverteerder;
        } else {
            throw new IllegalArgumentException("BrpReisdocumentConverteerder bevat geen Groep converteerder voor: "
                    + inhoud);
        }

        return result;
    }

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        LOG.debug("bepaalDocumentatie(#groepen={})", groepen.size());

        // Dit zou er steeds maar een moeten zijn.
        if (groepen == null || groepen.isEmpty() || groepen.get(0) == null) {
            return null;
        }

        return BrpGroepConverteerder.maakDocumentatie(groepen.get(0).getActieInhoud());
    }

    @Override
    protected Lo3Historie bepaalHistorie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        LOG.debug("bepaalHistorie(#groepen={})", groepen.size());

        // Dit zou er steeds maar een moeten zijn.
        if (groepen == null || groepen.isEmpty() || groepen.get(0) == null) {
            return null;
        }

        final BrpGroep<?> groep = groepen.get(0);
        final Lo3Datum opneming = groep.getHistorie().getDatumTijdRegistratie().converteerNaarLo3Datum();

        final Lo3Datum ingang;
        if (groep.getInhoud() instanceof BrpReisdocumentInhoud) {
            final BrpReisdocumentInhoud inhoud = (BrpReisdocumentInhoud) groep.getInhoud();

            ingang =inhoud.getDatumUitgifte().converteerNaarLo3Datum();
        } else if (groep.getInhoud() instanceof BrpBezitBuitenlandsReisdocumentIndicatieInhoud) {
            ingang = converteerder.converteerDatum(groep.getHistorie().getDatumAanvangGeldigheid());
        } else {
            ingang = opneming;
        }

        return new Lo3Historie(null, ingang, opneming);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Converteerder die weet hoe Lo3ReisdocumentInhoud rijen aangemaakt moeten worden.
     */
    public abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> {

        @Override
        protected final Lo3ReisdocumentInhoud maakNieuweInhoud() {
            return new Lo3ReisdocumentInhoud(null, null, null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpReisdocumentInhoud omgezet moet worden naar Lo3ReisdocumentInhoud.
     */
    @Component
    private static final class ReisdocumentConverteerder extends AbstractConverteerder<BrpReisdocumentInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3ReisdocumentInhoud vulInhoud(
                final Lo3ReisdocumentInhoud lo3Inhoud,
                final BrpReisdocumentInhoud brpInhoud,
                final BrpReisdocumentInhoud brpVorigeInhoud) {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setSoortNederlandsReisdocument(null);
                builder.setNummerNederlandsReisdocument(null);
                builder.setDatumUitgifteNederlandsReisdocument(null);
                builder.setAutoriteitVanAfgifteNederlandsReisdocument(null);
                builder.setDatumEindeGeldigheidNederlandsReisdocument(null);
                builder.setDatumInhoudingVermissingNederlandsReisdocument(null);
                builder.setAanduidingInhoudingNederlandsReisdocument(null);
                builder.setLengteHouder(null);
            } else {
                builder.setSoortNederlandsReisdocument(converteerder.converteerSoortNederlandsResidocument(brpInhoud
                        .getSoort()));
                builder.setNummerNederlandsReisdocument(brpInhoud.getNummer());
                builder.setDatumUitgifteNederlandsReisdocument(converteerder.converteerDatum(brpInhoud
                        .getDatumIngangDocument()));
                builder.setAutoriteitVanAfgifteNederlandsReisdocument(converteerder
                        .converteerAutoriteitVanAfgifte(brpInhoud.getAutoriteitVanAfgifte()));
                builder.setDatumEindeGeldigheidNederlandsReisdocument(converteerder.converteerDatum(brpInhoud
                        .getDatumVoorzieneEindeGeldigheid()));
                builder.setDatumInhoudingVermissingNederlandsReisdocument(converteerder.converteerDatum(brpInhoud
                        .getDatumInhoudingVermissing()));
                builder.setAanduidingInhoudingNederlandsReisdocument(converteerder
                        .converteerAanduidingInhoudingNederlandsReisdocument(brpInhoud.getRedenOntbreken()));
                builder.setLengteHouder(converteerder.converteerLengteHouder(brpInhoud.getLengteHouder()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud omgezet moet worden naar
     * Lo3ReisdocumentInhoud.
     */
    @Component
    private static final class SignaleringConverteerder extends
            AbstractConverteerder<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3ReisdocumentInhoud vulInhoud(
                final Lo3ReisdocumentInhoud lo3Inhoud,
                final BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud brpInhoud,
                final BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud brpVorigeInhoud) {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setSignalering(null);
            } else {
                builder.setSignalering(converteerder.converteerSignalering(brpInhoud.getHeeftIndicatie()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud omgezet moet worden naar
     * Lo3ReisdocumentInhoud.
     */
    @Component
    private static final class BuitenlandsConverteerder extends
            AbstractConverteerder<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3ReisdocumentInhoud vulInhoud(
                final Lo3ReisdocumentInhoud lo3Inhoud,
                final BrpBezitBuitenlandsReisdocumentIndicatieInhoud brpInhoud,
                final BrpBezitBuitenlandsReisdocumentIndicatieInhoud brpVorigeInhoud) {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingBezitBuitenlandsReisdocument(null);
            } else {
                builder.setAanduidingBezitBuitenlandsReisdocument(converteerder
                        .converteerAanduidingBezitBuitenlandsReisdocument(brpInhoud.getHeeftIndicatie()));
            }

            return builder.build();
        }
    }

}
