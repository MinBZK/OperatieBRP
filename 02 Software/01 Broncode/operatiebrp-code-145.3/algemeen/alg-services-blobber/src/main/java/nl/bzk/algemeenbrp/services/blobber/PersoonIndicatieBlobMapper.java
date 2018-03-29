/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map Indicatie op Blob-records.
 */
final class PersoonIndicatieBlobMapper {

    private final PersoonBlobber blobber;

    private final Map<SoortIndicatie, Consumer<PersoonIndicatie>> consumerMap = new EnumMap<>(SoortIndicatie.class);

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonIndicatieBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
        consumerMap.put(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, this::mapBehandeldAlsNederlander);
        consumerMap.put(SoortIndicatie.STAATLOOS, this::mapStaatloos);
        consumerMap.put(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER, this::mapVastgesteldNietNederlander);
        consumerMap.put(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING, this::mapVolledigeVerstrekkingsbeperking);
        consumerMap.put(SoortIndicatie.ONDER_CURATELE, this::mapOnderCuratele);
        consumerMap.put(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE, this::mapBijzondereVerblijfsrechtelijkPositie);
        consumerMap.put(SoortIndicatie.DERDE_HEEFT_GEZAG, this::mapDerdeHeeftGezag);
        consumerMap.put(SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT, this::mapSignaleringMetBetrekkingTotVerstrekkenReisdocument);
        consumerMap.put(SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG, this::mapOnverwerktDocumentAanwezig);
    }

    /**
     * Map indicaties.
     */
    void map() {
        for (final PersoonIndicatie persoonIndicatie : blobber.getPersoon().getPersoonIndicatieSet()) {
            mapAlsGroepOpPersoon(persoonIndicatie);
        }
    }

    private void mapAlsGroepOpPersoon(final PersoonIndicatie persoonIndicatie) {
        consumerMap.getOrDefault(persoonIndicatie.getSoortIndicatie(), this::mapOnbekendeIndicatie).accept(persoonIndicatie);
    }

    private void mapOnbekendeIndicatie(final PersoonIndicatie persoonIndicatie) {
        throw new IllegalStateException("Kan indicatie niet mappen: " + persoonIndicatie.getSoortIndicatie());
    }

    private void mapBehandeldAlsNederlander(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                    his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_MIGRATIEREDENOPNAMENATIONALITEIT, his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapStaatloos(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_STAATLOOS);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_STAATLOOS_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_STAATLOOS_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_STAATLOOS);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_STAATLOOS_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_STAATLOOS_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_STAATLOOS_MIGRATIEREDENBEEINDIGENNATIONALITEIT, his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_STAATLOOS_MIGRATIEREDENOPNAMENATIONALITEIT, his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapVastgesteldNietNederlander(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                    his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_MIGRATIEREDENOPNAMENATIONALITEIT,
                    his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapVolledigeVerstrekkingsbeperking(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                    his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_MIGRATIEREDENOPNAMENATIONALITEIT,
                    his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private BlobRecord maakBlobRecordVoorIndicatie(PersoonIndicatieHistorie his) {
        if (his.getPersoonIndicatie().getSoortIndicatie().isMaterieleHistorieVanToepassing()) {
            return blobber.maakBlobRecord(his);
        } else {
            return blobber.maakBlobRecord((FormeleHistorie) his);
        }
    }

    private void mapOnderCuratele(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_ONDERCURATELE_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_ONDERCURATELE_MIGRATIEREDENBEEINDIGENNATIONALITEIT, his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_ONDERCURATELE_MIGRATIEREDENOPNAMENATIONALITEIT, his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapBijzondereVerblijfsrechtelijkPositie(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                    his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_MIGRATIEREDENOPNAMENATIONALITEIT,
                    his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapOnverwerktDocumentAanwezig(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                    his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_MIGRATIEREDENOPNAMENATIONALITEIT,
                    his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapDerdeHeeftGezag(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM, persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_MIGRATIEREDENBEEINDIGENNATIONALITEIT, his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_MIGRATIEREDENOPNAMENATIONALITEIT, his.getMigratieRedenOpnameNationaliteit());
        }
    }

    private void mapSignaleringMetBetrekkingTotVerstrekkenReisdocument(final PersoonIndicatie persoonIndicatie) {
        // map identiteit
        final BlobRecord identiteitRecord = blobber.maakBlobRecord();
        identiteitRecord.setParentObjectElement(Element.PERSOON);
        identiteitRecord.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
        identiteitRecord.setObjectSleutel(persoonIndicatie.getId());
        identiteitRecord.setObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT);
        identiteitRecord.setGroepElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_IDENTITEIT);
        identiteitRecord.setVoorkomenSleutel(persoonIndicatie.getId()/* dummy */);
        identiteitRecord.addAttribuut(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_SOORTNAAM,
                persoonIndicatie.getSoortIndicatie().getId());
        // map his
        for (final PersoonIndicatieHistorie his : persoonIndicatie.getPersoonIndicatieHistorieSet()) {
            final BlobRecord record = maakBlobRecordVoorIndicatie(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonIndicatie.getPersoon().getId());
            record.setObjectElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT);
            record.setObjectSleutel(persoonIndicatie.getId());
            record.setGroepElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE, his.getWaarde());
            record.addAttribuut(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                    his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_MIGRATIEREDENOPNAMENATIONALITEIT,
                    his.getMigratieRedenOpnameNationaliteit());
        }
    }
}
