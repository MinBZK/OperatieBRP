/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Overlijden converteerder.
 */
@Component
public final class BrpVerblijfstitelConverteerder extends BrpCategorieConverteerder<Lo3VerblijfstitelInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private VerblijfstitelConverteerder verblijfstitelConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3VerblijfstitelInhoud> bepaalConverteerder(
            final B inhoud) {
        if (inhoud instanceof BrpVerblijfsrechtInhoud) {
            return (BrpGroepConverteerder<B, Lo3VerblijfstitelInhoud>) verblijfstitelConverteerder;
        }

        throw new IllegalArgumentException("BrpOverlijdenConverteerder bevat geen Groep converteerder voor: "
                + inhoud);
    }

    /**
     * Converteerder die weet hoe je een Lo3VerblijfstitelInhoud rij moet aanmaken en hoe een BrpOverlijdenInhoud
     * omgezet moet worden naar Lo3VerblijfstitelInhoud.
     */
    @Component
    private static final class VerblijfstitelConverteerder extends
            BrpGroepConverteerder<BrpVerblijfsrechtInhoud, Lo3VerblijfstitelInhoud> {

        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3VerblijfstitelInhoud maakNieuweInhoud() {
            return new Lo3VerblijfstitelInhoud(null, null, null);
        }

        @Override
        protected Lo3VerblijfstitelInhoud vulInhoud(
                final Lo3VerblijfstitelInhoud lo3Inhoud,
                final BrpVerblijfsrechtInhoud brpInhoud,
                final BrpVerblijfsrechtInhoud brpVorigeInhoud) {

            final Lo3VerblijfstitelInhoud.Builder builder = new Lo3VerblijfstitelInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setAanduidingVerblijfstitelCode(null);
                builder.setDatumEindeVerblijfstitel(null);
                builder.setIngangsdatumVerblijfstitel(null);
            } else {
                builder.setAanduidingVerblijfstitelCode(converteerder.converteerAanduidingVerblijfstitel(brpInhoud
                        .getVerblijfsrechtCode()));
                builder.setDatumEindeVerblijfstitel(converteerder.converteerDatum(brpInhoud
                        .getVoorzienEindeVerblijfsrecht()));
                builder.setIngangsdatumVerblijfstitel(converteerder.converteerDatum(brpInhoud
                        .getAanvangVerblijfsrecht()));
            }

            return builder.build();
        }

    }
}
