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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Converteerder voor BrpReisdocument/Signalering/Buitenlands reisdocument naar Lo3Reisdocument.
 */
@Requirement(Requirements.CCA12)
public final class BrpReisdocumentConverteerder extends AbstractBrpImmaterieleCategorienConverteerder<Lo3ReisdocumentInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpAttribuutConverteerder attribuutConverteerder;
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpReisdocumentConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    @Override
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> bepaalConverteerder(final T inhoud) {
        final BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> result;

        if (inhoud instanceof BrpReisdocumentInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) new ReisdocumentConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) new SignaleringConverteerder(attribuutConverteerder);
        } else {
            throw new IllegalArgumentException("BrpReisdocumentConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    @Override
    protected <T extends BrpGroepInhoud> BrpGroep<T> bepaalGroep(final BrpStapel<T> brpStapel) {
        return bepaalActueleGroep(brpStapel);
    }

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        LOG.debug("bepaalDocumentatie(#groepen={})", groepen.size());

        // Dit zou er steeds maar een moeten zijn.
        if (groepen.isEmpty() || groepen.get(0) == null) {
            return null;
        }

        return new BrpDocumentatieConverteerder(attribuutConverteerder).maakDocumentatie(groepen.get(0).getActieInhoud());
    }

    @Override
    protected Lo3Herkomst bepaalHerkomst(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        LOG.debug("bepaalHerkomst(#groepen={})", groepen.size());

        if (groepen.isEmpty() || groepen.get(0) == null) {
            // Dit zou er steeds maar een moeten zijn.
            return null;
        }
        return groepen.get(0).getActieInhoud().getLo3Herkomst();
    }

    @Override
    protected Lo3Historie bepaalHistorie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        LOG.debug("bepaalHistorie(#groepen={})", groepen.size());

        // Dit zou er steeds maar een moeten zijn.
        if (groepen.isEmpty() || groepen.get(0) == null) {
            return null;
        }

        final BrpGroep<?> groep = groepen.get(0);
        final Lo3Datum opneming = groep.getHistorie().getDatumTijdRegistratie().converteerNaarLo3Datum();

        final Lo3Datum ingang;
        if (groep.getInhoud() instanceof BrpReisdocumentInhoud) {
            final BrpReisdocumentInhoud inhoud = (BrpReisdocumentInhoud) groep.getInhoud();

            ingang = inhoud.getDatumIngangDocument().converteerNaarLo3Datum();
        } else {
            ingang = opneming;
        }

        return new Lo3Historie(null, ingang, opneming);
    }

    /**
     * Converteerder die weet hoe Lo3ReisdocumentInhoud rijen aangemaakt moeten worden.
     * @param <T> {@link BrpGroepInhoud}
     */
    public abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends AbstractBrpGroepConverteerder<T, Lo3ReisdocumentInhoud> {
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public AbstractConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        public final Lo3ReisdocumentInhoud maakNieuweInhoud() {
            return new Lo3ReisdocumentInhoud(null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpReisdocumentInhoud omgezet moet worden naar Lo3ReisdocumentInhoud.
     */
    public static final class ReisdocumentConverteerder extends AbstractConverteerder<BrpReisdocumentInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public ReisdocumentConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
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
        public Lo3ReisdocumentInhoud vulInhoud(
                final Lo3ReisdocumentInhoud lo3Inhoud,
                final BrpReisdocumentInhoud brpInhoud,
                final BrpReisdocumentInhoud brpVorigeInhoud) {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);

            builder.resetNederlandsReisdocumentVelden();
            if (brpInhoud != null) {
                builder.soortNederlandsReisdocument(getAttribuutConverteerder().converteerSoortNederlandsResidocument(brpInhoud.getSoort()));
                builder.nummerNederlandsReisdocument(getAttribuutConverteerder().converteerString(brpInhoud.getNummer()));
                builder.datumUitgifteNederlandsReisdocument(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumUitgifte()));
                builder.autoriteitVanAfgifteNederlandsReisdocument(
                        getAttribuutConverteerder().converteerAutoriteitVanAfgifte(brpInhoud.getAutoriteitVanAfgifte()));
                builder.datumEindeGeldigheidNederlandsReisdocument(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumEindeDocument()));
                builder.datumInhoudingVermissingNederlandsReisdocument(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumInhoudingOfVermissing()));
                final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument;
                final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAanduidingInhouding = brpInhoud.getAanduidingInhoudingOfVermissing();
                aanduidingInhoudingNederlandsReisdocument =
                        getAttribuutConverteerder().converteerAanduidingInhoudingNederlandsReisdocument(brpAanduidingInhouding);
                builder.aanduidingInhoudingNederlandsReisdocument(aanduidingInhoudingNederlandsReisdocument);
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud omgezet moet worden naar
     * Lo3ReisdocumentInhoud.
     */
    public static final class SignaleringConverteerder extends AbstractConverteerder<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public SignaleringConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
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
        public Lo3ReisdocumentInhoud vulInhoud(
                final Lo3ReisdocumentInhoud lo3Inhoud,
                final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud brpInhoud,
                final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud brpVorigeInhoud) {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);
            builder.resetSignalering();
            if (brpInhoud != null) {
                builder.signalering(getAttribuutConverteerder().converteerSignalering(brpInhoud.getIndicatie()));

            }
            return builder.build();
        }
    }
}
