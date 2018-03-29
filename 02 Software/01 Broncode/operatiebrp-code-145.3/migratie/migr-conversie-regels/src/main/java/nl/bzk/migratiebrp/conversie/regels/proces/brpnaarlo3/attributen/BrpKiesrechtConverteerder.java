/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
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

/**
 * Kiesrecht converteerder.
 */
@Requirement(Requirements.CCA13)
public final class BrpKiesrechtConverteerder extends AbstractBrpImmaterieleCategorienConverteerder<Lo3KiesrechtInhoud> {

    private final BrpAttribuutConverteerder attribuutConverteerder;
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpKiesrechtConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    @Override
    protected <T extends BrpGroepInhoud> BrpGroep<T> bepaalGroep(final BrpStapel<T> brpStapel) {
        return bepaalActueleGroep(brpStapel);
    }

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        final BrpDocumentatieConverteerder brpDocumentatieConverteerder = new BrpDocumentatieConverteerder(attribuutConverteerder);
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
        return new Lo3Historie(null, null, null);
    }

    @Override
    protected Lo3Herkomst bepaalHerkomst(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Gebruik de herkomst uit de eerste groep
        return groepen.get(0).getActieInhoud().getLo3Herkomst();
    }

    @Override
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3KiesrechtInhoud> bepaalConverteerder(final B inhoud) {
        if (inhoud instanceof BrpUitsluitingKiesrechtInhoud) {
            return (BrpGroepConverteerder<B, Lo3KiesrechtInhoud>) new UitsluitingKiesrechtInhoudConverteerder(attribuutConverteerder);
        }
        if (inhoud instanceof BrpDeelnameEuVerkiezingenInhoud) {
            return (BrpGroepConverteerder<B, Lo3KiesrechtInhoud>) new DeelnameEuropeesVerkiezingenInhoudConverteerder(attribuutConverteerder);
        }

        throw new IllegalArgumentException("BrpKiesrechtConverteerder bevat geen Groep converteerder voor: " + inhoud);
    }

    /**
     * Converteerder die weet hoe een nieuwe inhoud aan te maken.
     * @param <T> brp groep inhoud type
     */
    public abstract static class AbstractKiesrechtConverteerder<T extends BrpGroepInhoud> extends AbstractBrpGroepConverteerder<T, Lo3KiesrechtInhoud> {

        AbstractKiesrechtConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        public final Lo3KiesrechtInhoud maakNieuweInhoud() {
            return new Lo3KiesrechtInhoud(null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpUitsluitingKiesrechtInhoud om te zetten naar Lo3KiesrechtInhoud.
     */
    @Requirement(Requirements.CCA13_BL01)
    public static final class UitsluitingKiesrechtInhoudConverteerder extends AbstractKiesrechtConverteerder<BrpUitsluitingKiesrechtInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public UitsluitingKiesrechtInhoudConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

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
                final BrpUitsluitingKiesrechtInhoud brpVorigeInhoud) {
            final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingUitgeslotenKiesrecht(null);
                builder.setEinddatumUitsluitingKiesrecht(null);
            } else {
                builder.setAanduidingUitgeslotenKiesrecht(
                        getAttribuutConverteerder().converteerAanduidingUitgeslotenKiesrecht(brpInhoud.getIndicatieUitsluitingKiesrecht()));
                builder.setEinddatumUitsluitingKiesrecht(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumVoorzienEindeUitsluitingKiesrecht()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe DeelnameEuropeesVerkiezingenInhoudConverteerder om te zetten naar Lo3KiesrechtInhoud.
     */
    @Requirement(Requirements.CCA13_BL02)
    public static final class DeelnameEuropeesVerkiezingenInhoudConverteerder extends AbstractKiesrechtConverteerder<BrpDeelnameEuVerkiezingenInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public DeelnameEuropeesVerkiezingenInhoudConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

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
                final BrpDeelnameEuVerkiezingenInhoud brpVorigeInhoud) {
            final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingEuropeesKiesrecht(null);
                builder.setDatumEuropeesKiesrecht(null);
                builder.setEinddatumUitsluitingEuropeesKiesrecht(null);
            } else {
                builder.setAanduidingEuropeesKiesrecht(
                        getAttribuutConverteerder().converteerAanduidingEuropeesKiesrecht(brpInhoud.getIndicatieDeelnameEuVerkiezingen()));
                builder.setDatumEuropeesKiesrecht(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumAanleidingAanpassingDeelnameEuVerkiezingen()));
                builder.setEinddatumUitsluitingEuropeesKiesrecht(
                        getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumVoorzienEindeUitsluitingEuVerkiezingen()));
            }

            return builder.build();
        }

    }

}
