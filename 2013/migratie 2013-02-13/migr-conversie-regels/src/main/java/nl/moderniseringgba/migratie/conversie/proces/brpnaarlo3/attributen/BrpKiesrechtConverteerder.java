/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Kiesrecht converteerder.
 */
@Component
@Requirement(Requirements.CCA13)
public final class BrpKiesrechtConverteerder extends BrpImmaterieleCategorienConverteerder<Lo3KiesrechtInhoud> {

    @Inject
    private NederlandsKiesrechtConverteerder nederlandsKiesrechtConverteerder;
    @Inject
    private EuropeesKiesrechtConverteerder europeesKiesrechtConverteerder;

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Kiesrecht gebruikt altijd het document uit Europees Kiesrecht
        for (final BrpGroep<? extends BrpGroepInhoud> groep : groepen) {
            if (groep.getInhoud() instanceof BrpEuropeseVerkiezingenInhoud) {
                return BrpGroepConverteerder.maakDocumentatie(groep.getActieInhoud());
            }
        }

        return null;
    }

    @Override
    protected Lo3Historie bepaalHistorie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Kiesrecht heeft geen historie
        return Lo3Historie.NULL_HISTORIE;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3KiesrechtInhoud> bepaalConverteerder(
            final B inhoud) {
        if (inhoud instanceof BrpUitsluitingNederlandsKiesrechtInhoud) {
            return (BrpGroepConverteerder<B, Lo3KiesrechtInhoud>) nederlandsKiesrechtConverteerder;
        }
        if (inhoud instanceof BrpEuropeseVerkiezingenInhoud) {
            return (BrpGroepConverteerder<B, Lo3KiesrechtInhoud>) europeesKiesrechtConverteerder;
        }

        throw new IllegalArgumentException("BrpKiesrechtConverteerder bevat geen Groep converteerder voor: " + inhoud);
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
    private abstract static class KiesrechtConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3KiesrechtInhoud> {

        @Override
        protected Lo3KiesrechtInhoud maakNieuweInhoud() {
            return new Lo3KiesrechtInhoud(null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpUitsluitingNederlandsKiesrechtInhoud om te zetten naar Lo3KiesrechtInhoud.
     */
    @Component
    @Requirement(Requirements.CCA13_BL01)
    public static final class NederlandsKiesrechtConverteerder extends
            KiesrechtConverteerder<BrpUitsluitingNederlandsKiesrechtInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3KiesrechtInhoud vulInhoud(
                final Lo3KiesrechtInhoud lo3Inhoud,
                final BrpUitsluitingNederlandsKiesrechtInhoud brpInhoud,
                final BrpUitsluitingNederlandsKiesrechtInhoud brpVorigeInhoud) {
            final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingUitgeslotenKiesrecht(null);
                builder.setEinddatumUitsluitingKiesrecht(null);
            } else {
                builder.setAanduidingUitgeslotenKiesrecht(converteerder
                        .converteerAanduidingUitgeslotenKiesrecht(brpInhoud
                                .getIndicatieUitsluitingNederlandsKiesrecht()));
                builder.setEinddatumUitsluitingKiesrecht(converteerder.converteerDatum(brpInhoud
                        .getDatumEindeUitsluitingNederlandsKiesrecht()));
            }

            return builder.build();
        }

    }

    /**
     * Converteerder die weet hoe EuropeesKiesrechtConverteerder om te zetten naar Lo3KiesrechtInhoud.
     */
    @Component
    @Requirement(Requirements.CCA13_BL02)
    public static final class EuropeesKiesrechtConverteerder extends
            KiesrechtConverteerder<BrpEuropeseVerkiezingenInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3KiesrechtInhoud vulInhoud(
                final Lo3KiesrechtInhoud lo3Inhoud,
                final BrpEuropeseVerkiezingenInhoud brpInhoud,
                final BrpEuropeseVerkiezingenInhoud brpVorigeInhoud) {
            final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingEuropeesKiesrecht(null);
                builder.setDatumEuropeesKiesrecht(null);
                builder.setEinddatumUitsluitingEuropeesKiesrecht(null);
            } else {
                builder.setAanduidingEuropeesKiesrecht(converteerder.converteerAanduidingEuropeesKiesrecht(brpInhoud
                        .getDeelnameEuropeseVerkiezingen()));
                builder.setDatumEuropeesKiesrecht(converteerder.converteerDatum(brpInhoud
                        .getDatumAanleidingAanpassingDeelnameEuropeseVerkiezingen()));
                builder.setEinddatumUitsluitingEuropeesKiesrecht(converteerder.converteerDatum(brpInhoud
                        .getDatumEindeUitsluitingEuropeesKiesrecht()));

            }

            return builder.build();
        }

    }

}
