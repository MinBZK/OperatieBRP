/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.util.Assert;

/**
 * Factory voor het maken van Persoonsgevens obv data uit de Blob.
 */
public final class PersoonslijstFactory {

    private PersoonslijstFactory() {
    }

    /**
     * Factory method voor het maken van Persoonsgegevens obv data uit de BLOB.
     * @param persData persData
     * @return een Persoonsgegevens object
     */
    public static Persoonslijst maak(final PersoonData persData) {
        final PersoonBlob persoonBlob = persData.getPersoonBlob();
        final BlobRoot persoonRoot = persoonBlob.getPersoonsgegevens();
        final List<BlobRoot> verantwoording = persoonBlob.getVerantwoording();
        //maak de set van handelingen met acties van zowel de hoofdpersoon als gerelateerden
        final Set<AdministratieveHandeling> handelingen = VerantwoordingConverter.map(verantwoording);
        final PersoonRecordMapper recordMapper = new PersoonRecordMapper(handelingen);
        // Maak een MetaObject.Builder van de hoofdpersoon
        final BlobTerugConverter persoonConverter = new BlobTerugConverter(persoonRoot, recordMapper);
        final Collection<MetaObject.Builder> persBuilders = persoonConverter.geefRootMetaObjectBuilders();
        Assert.isTrue(persBuilders.size() == 1, "Terugconversie moet 1 MetaObject (Persoon) opleveren");
        final MetaObject.Builder persoonBuilder = persBuilders.iterator().next();
        if (persData.getAfnemerindicatiesBlob() != null) {
            voegAfnemerIndicatiesToeAanPersoonBuilder(persData.getAfnemerindicatiesBlob().getAfnemerindicaties(), persoonBuilder);
        }

        // Build de persoon
        return new Persoonslijst(persoonBuilder.build(), persData.getLockversieAfnemerindicatie());
    }

    private static void voegAfnemerIndicatiesToeAanPersoonBuilder(final List<BlobRoot> afnemerindicaties, final MetaObject.Builder persoonBuilder) {
        // Maak MetaObject.Builder objecten van de afnemerindicaties en hang deze onder de hoofdpersoon
        if (afnemerindicaties != null) {
            for (final BlobRoot afnemerindicatieBlob : afnemerindicaties) {
                final BlobTerugConverter blobConverter = new BlobTerugConverter(afnemerindicatieBlob, AttribuutMapper.INSTANCE);
                final Collection<MetaObject.Builder> builders = blobConverter.geefRootMetaObjectBuilders();
                Assert.isTrue(builders.size() == 1, "Terugconversie moet 1 MetaObject (Afnemerindicatie) opleveren");
                final MetaObject.Builder afnemerindicatieBuilder = builders.iterator().next();
                persoonBuilder.metObject(afnemerindicatieBuilder);
            }
        }
    }

    /**
     * RecordMapper voor rijen op de persoon.
     */
    private static final class PersoonRecordMapper implements BiConsumer<MetaRecord.Builder, BlobRecord> {

        /**
         * De RecordMapper die gebruikt wordt Mapt de actie verantwoording en attributen op de MetaRecord.Builder.
         */
        private final Map<Long, Actie> actieMap = Maps.newHashMap();

        /**
         * Constructor.
         * @param handelingen de handelingen
         */
        PersoonRecordMapper(final Set<AdministratieveHandeling> handelingen) {
            for (final AdministratieveHandeling administratieveHandeling : handelingen) {
                for (final Actie actie : administratieveHandeling.getActies()) {
                    actieMap.put(actie.getId(), actie);
                }
            }
        }

        @Override
        public void accept(final MetaRecord.Builder builder, final BlobRecord blobRecord) {
            builder.metId(blobRecord.getVoorkomenSleutel());

            //map groep-specifieke attributen
            AttribuutMapper.INSTANCE.accept(builder, blobRecord);

            //map 'generieke' attributen
            builder.metActieInhoud(getActie(blobRecord.getActieInhoud()));
            builder.metActieVerval(getActie(blobRecord.getActieVerval()));
            builder.metActieAanpassingGeldigheid(getActie(blobRecord.getActieAanpassingGeldigheid()));
            builder.metActieVervalTbvLeveringMutaties(getActie(blobRecord.getActieVervalTbvLeveringMutaties()));
            builder.metDatumAanvangGeldigheid(blobRecord.getDatumAanvangGeldigheid());
            builder.metDatumEindeGeldigheid(blobRecord.getDatumEindeGeldigheid());
            builder.metNadereAanduidingVerval(blobRecord.getNadereAanduidingVerval());
            builder.metIndicatieTbvLeveringMutaties(blobRecord.isIndicatieTbvLeveringMutaties());
        }

        private Actie getActie(final Long actieId) {
            return actieMap.get(actieId);
        }
    }
}


