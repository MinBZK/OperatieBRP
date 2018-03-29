/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;


/**
 * Maak een een blob van een persoonsbeeld.
 */
final class PersoonBlobber {

    private final Persoon persoon;

    private final List<BlobRecord> persRecords = Lists.newLinkedList();
    private final Multimap<Long, BRPActie> actieMap = HashMultimap.create();

    /**
     * Constructor.
     *
     * @param persoon de persoon
     */
    PersoonBlobber(final Persoon persoon) {
        this.persoon = persoon;
        new PersoonBlobMapper(this).map();
    }

    /**
     * @return de persoon
     */
    Persoon getPersoon() {
        return persoon;
    }

    /**
     * @return de lijst van blob records voor de persoon
     */
    List<BlobRecord> getPersRecords() {
        return ImmutableList.copyOf(persRecords);
    }

    /**
     * @return actieId op Actie map
     */
    Multimap<Long, BRPActie> getActieMap() {
        return ImmutableMultimap.copyOf(actieMap);
    }

    /**
     * Voegt een nieuw blobrecord toe.
     *
     * @return het blobrecord
     */
    public BlobRecord maakBlobRecord() {
        final BlobRecord blobRecord = new BlobRecord();
        persRecords.add(blobRecord);
        return blobRecord;
    }


    /**
     * Maakt een materieel record obv het gegevens historierecord.
     *
     * @param his het historie record
     * @return het blobrecord
     */
    BlobRecord maakBlobRecord(final MaterieleHistorie his) {
        final BlobRecord record = maakBlobRecord();
        final BRPActie actieAanpassingGeldigheid = his.getActieAanpassingGeldigheid();
        if (actieAanpassingGeldigheid != null) {
            record.setActieAanpassingGeldigheid(actieAanpassingGeldigheid.getId());
            actieMap.put(actieAanpassingGeldigheid.getAdministratieveHandeling().getId(), actieAanpassingGeldigheid);
        }
        record.setDatumAanvangGeldigheid(his.getDatumAanvangGeldigheid());
        record.setDatumEindeGeldigheid(his.getDatumEindeGeldigheid());
        vulFormeel(record, his);
        return record;
    }

    /**
     * Maakt een formeel record obv het gegeven historierecord.
     *
     * @param his historie record
     * @return het blobrecord
     */
    BlobRecord maakBlobRecord(final FormeleHistorie his) {
        final BlobRecord record = maakBlobRecord();
        vulFormeel(record, his);
        return record;
    }

    private void vulFormeel(final BlobRecord record, final FormeleHistorie his) {
        final BRPActie actieInhoud = his.getActieInhoud();
        if (actieInhoud != null) {
            record.setActieInhoud(actieInhoud.getId());
            actieMap.put(actieInhoud.getAdministratieveHandeling().getId(), actieInhoud);
        }
        final BRPActie actieVerval = his.getActieVerval();
        if (actieVerval != null) {
            record.setActieVerval(actieVerval.getId());
            actieMap.put(actieVerval.getAdministratieveHandeling().getId(), actieVerval);
        }
        final BRPActie actieVervalTbvLeveringMutaties = his.getActieVervalTbvLeveringMutaties();
        if (actieVervalTbvLeveringMutaties != null) {
            record.setActieVervalTbvLeveringMutaties(actieVervalTbvLeveringMutaties.getId());
            actieMap.put(actieVervalTbvLeveringMutaties.getAdministratieveHandeling().getId(), actieVervalTbvLeveringMutaties);
        }
        if (his.getNadereAanduidingVerval() != null) {
            record.setNadereAanduidingVerval(his.getNadereAanduidingVerval().toString());
        }
        if (his.getIndicatieVoorkomenTbvLeveringMutaties() != null) {
            record.setIndicatieTbvLeveringMutaties(his.getIndicatieVoorkomenTbvLeveringMutaties());
        }
    }
}
