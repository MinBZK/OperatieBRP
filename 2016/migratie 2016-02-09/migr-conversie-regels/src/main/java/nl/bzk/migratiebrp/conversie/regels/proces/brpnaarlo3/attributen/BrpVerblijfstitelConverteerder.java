/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Overlijden converteerder.
 */
@Component
public final class BrpVerblijfstitelConverteerder extends AbstractBrpImmaterieleCategorienConverteerder<Lo3VerblijfstitelInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private VerblijfstitelConverteerder verblijfstitelConverteerder;

    @Inject
    private BrpDocumentatieConverteerder brpDocumentatieConverteerder;

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

        final BrpVerblijfsrechtInhoud inhoud = (BrpVerblijfsrechtInhoud) groep.getInhoud();

        final Lo3Datum ingang = inhoud.getDatumMededelingVerblijfsrecht().converteerNaarLo3Datum();

        return new Lo3Historie(null, ingang, opneming);
    }







    @Override
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3VerblijfstitelInhoud> bepaalConverteerder(final B inhoud) {
        if (inhoud instanceof BrpVerblijfsrechtInhoud) {
            return (BrpGroepConverteerder<B, Lo3VerblijfstitelInhoud>) verblijfstitelConverteerder;
        }

        throw new IllegalArgumentException("BrpOverlijdenConverteerder bevat geen Groep converteerder voor: " + inhoud);
    }


    /**
     * Converteerder die weet hoe je een Lo3VerblijfstitelInhoud rij moet aanmaken en hoe een BrpOverlijdenInhoud
     * omgezet moet worden naar Lo3VerblijfstitelInhoud.
     */
    @Component
    public static final class VerblijfstitelConverteerder extends BrpGroepConverteerder<BrpVerblijfsrechtInhoud, Lo3VerblijfstitelInhoud> {

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
        public Lo3VerblijfstitelInhoud maakNieuweInhoud() {
            return new Lo3VerblijfstitelInhoud(null, null, null);
        }

        @Override
        public Lo3VerblijfstitelInhoud vulInhoud(
            final Lo3VerblijfstitelInhoud lo3Inhoud,
            final BrpVerblijfsrechtInhoud brpInhoud,
            final BrpVerblijfsrechtInhoud brpVorigeInhoud)
        {

            final Lo3VerblijfstitelInhoud.Builder builder = new Lo3VerblijfstitelInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingVerblijfstitelCode(null);
                builder.setDatumEindeVerblijfstitel(null);
                builder.setDatumAanvangVerblijfstitel(null);
            } else {
                builder.setAanduidingVerblijfstitelCode(converteerder.converteerAanduidingVerblijfsrecht(brpInhoud.getAanduidingVerblijfsrechtCode()));
                builder.setDatumEindeVerblijfstitel(converteerder.converteerDatum(brpInhoud.getDatumVoorzienEindeVerblijfsrecht()));
                builder.setDatumAanvangVerblijfstitel(converteerder.converteerDatum(brpInhoud.getDatumAanvangVerblijfstitel()));
            }

            return builder.build();
        }



    }
}
