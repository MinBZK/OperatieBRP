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
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteerder voor BrpReisdocument/Signalering/Buitenlands reisdocument naar Lo3Reisdocument.
 */
@Requirement(Requirements.CCA12)
@Component
public final class BrpReisdocumentConverteerder extends AbstractBrpImmaterieleCategorienConverteerder<Lo3ReisdocumentInhoud> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private ReisdocumentConverteerder reisdocumentConverteerder;
    @Inject
    private SignaleringConverteerder signaleringConverteerder;
    @Inject
    private BrpDocumentatieConverteerder brpDocumentatieConverteerder;

    @Override
    protected <T extends BrpGroepInhoud> BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> bepaalConverteerder(final T inhoud) {
        final BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> result;

        if (inhoud instanceof BrpReisdocumentInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) reisdocumentConverteerder;
        } else if (inhoud instanceof BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud) {
            result = (BrpGroepConverteerder<T, Lo3ReisdocumentInhoud>) signaleringConverteerder;
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

        return brpDocumentatieConverteerder.maakDocumentatie(groepen.get(0).getActieInhoud());
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

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Converteerder die weet hoe Lo3ReisdocumentInhoud rijen aangemaakt moeten worden.
     */
    public abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends BrpGroepConverteerder<T, Lo3ReisdocumentInhoud> {

        @Override
        public final Lo3ReisdocumentInhoud maakNieuweInhoud() {
            return new Lo3ReisdocumentInhoud(null, null, null, null, null, null, null, null);
        }
    }

    /**
     * Converteerder die weet hoe BrpReisdocumentInhoud omgezet moet worden naar Lo3ReisdocumentInhoud.
     */
    @Component
    public static final class ReisdocumentConverteerder extends AbstractConverteerder<BrpReisdocumentInhoud> {
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
        public Lo3ReisdocumentInhoud vulInhoud(
            final Lo3ReisdocumentInhoud lo3Inhoud,
            final BrpReisdocumentInhoud brpInhoud,
            final BrpReisdocumentInhoud brpVorigeInhoud)
        {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);

            builder.resetNederlandsReisdocumentVelden();
            if (brpInhoud != null) {
                builder.soortNederlandsReisdocument(converteerder.converteerSoortNederlandsResidocument(brpInhoud.getSoort()));
                builder.nummerNederlandsReisdocument(converteerder.converteerString(brpInhoud.getNummer()));
                builder.datumUitgifteNederlandsReisdocument(converteerder.converteerDatum(brpInhoud.getDatumUitgifte()));
                builder.autoriteitVanAfgifteNederlandsReisdocument(converteerder.converteerAutoriteitVanAfgifte(brpInhoud.getAutoriteitVanAfgifte()));
                builder.datumEindeGeldigheidNederlandsReisdocument(converteerder.converteerDatum(brpInhoud.getDatumEindeDocument()));
                builder.datumInhoudingVermissingNederlandsReisdocument(converteerder.converteerDatum(brpInhoud.getDatumInhoudingOfVermissing()));
                final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument;
                final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAanduidingInhouding = brpInhoud.getAanduidingInhoudingOfVermissing();
                aanduidingInhoudingNederlandsReisdocument = converteerder.converteerAanduidingInhoudingNederlandsReisdocument(brpAanduidingInhouding);
                builder.aanduidingInhoudingNederlandsReisdocument(aanduidingInhoudingNederlandsReisdocument);
            }
            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud omgezet moet worden naar
     * Lo3ReisdocumentInhoud.
     */
    @Component
    public static final class SignaleringConverteerder extends AbstractConverteerder<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> {
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
        public Lo3ReisdocumentInhoud vulInhoud(
            final Lo3ReisdocumentInhoud lo3Inhoud,
            final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud brpInhoud,
            final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud brpVorigeInhoud)
        {
            final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder(lo3Inhoud);
            builder.resetSignalering();
            if (brpInhoud != null) {
                builder.signalering(converteerder.converteerSignalering(brpInhoud.getIndicatie()));

            }
            return builder.build();
        }
    }
}
