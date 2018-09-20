/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Kiesrecht converteerder.
 */
@Component
@Requirement(Requirements.CCA13)
public final class BrpKiesrechtConverteerder extends AbstractBrpImmaterieleCategorienConverteerder<Lo3KiesrechtInhoud> {

    @Inject
    private UitsluitingKiesrechtInhoudConverteerder uitsluitingKiesrechtConverteerder;
    @Inject
    private DeelnameEuropeesVerkiezingenInhoudConverteerder deelnameEuVerkiezingenConverteerder;
    @Inject
    private BrpDocumentatieConverteerder brpDocumentatieConverteerder;

    @Override
    protected <T extends BrpGroepInhoud> BrpGroep<T> bepaalGroep(final BrpStapel<T> brpStapel) {
        return bepaalActueleGroep(brpStapel);
    }

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        Lo3Documentatie documentatie = null;
        // Kiesrecht gebruikt altijd het document uit Europees Kiesrecht
        for (final BrpGroep<? extends BrpGroepInhoud> groep : groepen) {
            if (groep.getInhoud() instanceof BrpDeelnameEuVerkiezingenInhoud) {
                documentatie = brpDocumentatieConverteerder.maakDocumentatie(groep.getActieInhoud());
                break;
            }
        }
        if (documentatie == null) {
            for (final BrpGroep<? extends BrpGroepInhoud> groep : groepen) {
                if (groep.getInhoud() instanceof BrpUitsluitingKiesrechtInhoud) {
                    documentatie = brpDocumentatieConverteerder.maakDocumentatie(groep.getActieInhoud());
                    break;
                }
            }
        }
        return documentatie;
    }

    @Override
    protected Lo3Historie bepaalHistorie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Kiesrecht heeft geen historie
        return Lo3Historie.NULL_HISTORIE;
    }

    @Override
    protected Lo3Herkomst bepaalHerkomst(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Gebruik de herkomst uit de eerste groep
        return groepen.get(0).getActieInhoud().getLo3Herkomst();
    }

    @Override
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3KiesrechtInhoud> bepaalConverteerder(final B inhoud) {
        if (inhoud instanceof BrpUitsluitingKiesrechtInhoud) {
            return (BrpGroepConverteerder<B, Lo3KiesrechtInhoud>) uitsluitingKiesrechtConverteerder;
        }
        if (inhoud instanceof BrpDeelnameEuVerkiezingenInhoud) {
            return (BrpGroepConverteerder<B, Lo3KiesrechtInhoud>) deelnameEuVerkiezingenConverteerder;
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
    public abstract static class AbstractKiesrechtConverteerder<T extends BrpGroepInhoud> extends BrpGroepConverteerder<T, Lo3KiesrechtInhoud> {

        @Override
        public final Lo3KiesrechtInhoud maakNieuweInhoud() {
            return new Lo3KiesrechtInhoud(null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpUitsluitingKiesrechtInhoud om te zetten naar Lo3KiesrechtInhoud.
     */
    @Component
    @Requirement(Requirements.CCA13_BL01)
    public static final class UitsluitingKiesrechtInhoudConverteerder extends AbstractKiesrechtConverteerder<BrpUitsluitingKiesrechtInhoud> {
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
        public Lo3KiesrechtInhoud vulInhoud(
            final Lo3KiesrechtInhoud lo3Inhoud,
            final BrpUitsluitingKiesrechtInhoud brpInhoud,
            final BrpUitsluitingKiesrechtInhoud brpVorigeInhoud)
        {
            final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingUitgeslotenKiesrecht(null);
                builder.setEinddatumUitsluitingKiesrecht(null);
            } else {
                builder.setAanduidingUitgeslotenKiesrecht(converteerder.converteerAanduidingUitgeslotenKiesrecht(brpInhoud.getIndicatieUitsluitingKiesrecht()));
                builder.setEinddatumUitsluitingKiesrecht(converteerder.converteerDatum(brpInhoud.getDatumVoorzienEindeUitsluitingKiesrecht()));
            }

            return builder.build();
        }

    }

    /**
     * Converteerder die weet hoe DeelnameEuropeesVerkiezingenInhoudConverteerder om te zetten naar Lo3KiesrechtInhoud.
     */
    @Component
    @Requirement(Requirements.CCA13_BL02)
    public static final class DeelnameEuropeesVerkiezingenInhoudConverteerder extends AbstractKiesrechtConverteerder<BrpDeelnameEuVerkiezingenInhoud> {
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
        public Lo3KiesrechtInhoud vulInhoud(
            final Lo3KiesrechtInhoud lo3Inhoud,
            final BrpDeelnameEuVerkiezingenInhoud brpInhoud,
            final BrpDeelnameEuVerkiezingenInhoud brpVorigeInhoud)
        {
            final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingEuropeesKiesrecht(null);
                builder.setDatumEuropeesKiesrecht(null);
                builder.setEinddatumUitsluitingEuropeesKiesrecht(null);
            } else {
                builder.setAanduidingEuropeesKiesrecht(converteerder.converteerAanduidingEuropeesKiesrecht(brpInhoud.getIndicatieDeelnameEuVerkiezingen()));
                builder.setDatumEuropeesKiesrecht(converteerder.converteerDatum(brpInhoud.getDatumAanleidingAanpassingDeelnameEuVerkiezingen()));
                builder.setEinddatumUitsluitingEuropeesKiesrecht(converteerder.converteerDatum(brpInhoud.getDatumVoorzienEindeUitsluitingEuVerkiezingen()));
            }

            return builder.build();
        }

    }

}
