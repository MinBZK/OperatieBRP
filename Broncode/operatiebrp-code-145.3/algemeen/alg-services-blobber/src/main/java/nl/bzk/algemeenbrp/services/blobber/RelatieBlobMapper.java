/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import com.google.common.collect.Iterables;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map Relatie entiteit op BlobRecords.
 */
final class RelatieBlobMapper {

    private final PersoonBlobber blobber;
    private final Betrokkenheid betrokkenheid;
    private final Relatie relatie;
    private final Element typeBetrokkenheid;

    /**
     * Constructor.
     *
     * @param blobber de blob
     * @param betrokkenheid de betrokkenheid op de hoofdpersoon
     * @param typeBetrokkenheid het type betrokkenheid
     */
    RelatieBlobMapper(final PersoonBlobber blobber, final Betrokkenheid betrokkenheid, final Element typeBetrokkenheid) {
        this.blobber = blobber;
        this.betrokkenheid = betrokkenheid;
        relatie = betrokkenheid.getRelatie();
        this.typeBetrokkenheid = typeBetrokkenheid;
    }

    /**
     * Map relaties.
     */
    public void map() {

        final SoortRelatie soortRelatie = relatie.getSoortRelatie();
        switch (soortRelatie) {
            case HUWELIJK:
                mapHuwelijk();
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                mapGeregistreerdPartnerschap();
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                mapFamilierechtelijkeBetrekking();
                break;
            default:
                throw new IllegalArgumentException("Kan type relatie niet mappen: " + soortRelatie);
        }
        mapGerelateerdeBetrokkenheden();
    }

    private void mapFamilierechtelijkeBetrekking() {
        // identiteit (historie patroon G)
        final BlobRecord idRecord = blobber.maakBlobRecord();
        idRecord.setParentObjectElement(typeBetrokkenheid);
        idRecord.setParentObjectSleutel(betrokkenheid.getId());
        idRecord.setObjectSleutel(relatie.getId());
        idRecord.setObjectElement(Element.FAMILIERECHTELIJKEBETREKKING);
        idRecord.setVoorkomenSleutel(relatie.getId() /* dummy */);
        idRecord.setGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT);
        idRecord.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE, relatie.getSoortRelatie().getCode());

        for (final RelatieHistorie his : relatie.getRelatieHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            vulHisRecordFamrechtelijkeBetrekking(his, record);
        }
    }

    private void vulHisRecordFamrechtelijkeBetrekking(final RelatieHistorie his, final BlobRecord record) {
        record.setParentObjectElement(typeBetrokkenheid);
        record.setParentObjectSleutel(betrokkenheid.getId());
        record.setObjectSleutel(relatie.getId());
        record.setObjectElement(Element.FAMILIERECHTELIJKEBETREKKING);
        record.setGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD);
        record.setVoorkomenSleutel(his.getId());
        if (his.getGemeenteAanvang() != null) {
            record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_GEMEENTEAANVANGCODE, his.getGemeenteAanvang().getCode());
        }
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMAANVANG, his.getWoonplaatsnaamAanvang());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG, his.getBuitenlandsePlaatsAanvang());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG, his.getBuitenlandseRegioAanvang());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEAANVANG, his.getOmschrijvingLocatieAanvang());
        if (his.getLandOfGebiedAanvang() != null) {
            record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDAANVANGCODE, his.getLandOfGebiedAanvang().getCode());
        }
        if (his.getRedenBeeindigingRelatie() != null) {
            record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_REDENEINDECODE, String.valueOf(his.getRedenBeeindigingRelatie().getCode()));
        }
        if (his.getGemeenteEinde() != null) {
            record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_GEMEENTEEINDECODE, his.getGemeenteEinde().getCode());
        }
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMEINDE, his.getWoonplaatsnaamEinde());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSEINDE, his.getBuitenlandsePlaatsEinde());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOEINDE, his.getBuitenlandseRegioEinde());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEEINDE, his.getOmschrijvingLocatieEinde());
        if (his.getLandOfGebiedEinde() != null) {
            record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDEINDECODE, his.getLandOfGebiedEinde().getCode());
        }
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG, his.getDatumAanvang());
        record.addAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_DATUMEINDE, his.getDatumEinde());
    }

    private void mapGeregistreerdPartnerschap() {
        // identiteit (historie patroon G)
        final BlobRecord idRecord = blobber.maakBlobRecord();
        idRecord.setParentObjectElement(typeBetrokkenheid);
        idRecord.setParentObjectSleutel(betrokkenheid.getId());
        idRecord.setObjectSleutel(relatie.getId());
        idRecord.setObjectElement(Element.GEREGISTREERDPARTNERSCHAP);
        idRecord.setVoorkomenSleutel(relatie.getId() /* dummy */);
        idRecord.setGroepElement(Element.GEREGISTREERDPARTNERSCHAP_IDENTITEIT);
        idRecord.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_SOORTCODE, relatie.getSoortRelatie().getCode());

        for (final RelatieHistorie his : relatie.getRelatieHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            vulHisRecordGeregistreerdPartnerschap(his, record);
        }
    }

    private void vulHisRecordGeregistreerdPartnerschap(final RelatieHistorie his, final BlobRecord record) {
        record.setParentObjectElement(typeBetrokkenheid);
        record.setParentObjectSleutel(betrokkenheid.getId());
        record.setObjectSleutel(relatie.getId());
        record.setObjectElement(Element.GEREGISTREERDPARTNERSCHAP);
        record.setGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD);
        record.setVoorkomenSleutel(his.getId());
        if (his.getGemeenteAanvang() != null) {
            record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE, his.getGemeenteAanvang().getCode());
        }
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG, his.getWoonplaatsnaamAanvang());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG, his.getBuitenlandsePlaatsAanvang());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG, his.getBuitenlandseRegioAanvang());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG, his.getOmschrijvingLocatieAanvang());
        if (his.getLandOfGebiedAanvang() != null) {
            record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE, his.getLandOfGebiedAanvang().getCode());
        }
        if (his.getRedenBeeindigingRelatie() != null) {
            record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE, String.valueOf(his.getRedenBeeindigingRelatie().getCode()));
        }
        if (his.getGemeenteEinde() != null) {
            record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE, his.getGemeenteEinde().getCode());
        }
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE, his.getWoonplaatsnaamEinde());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE, his.getBuitenlandsePlaatsEinde());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE, his.getBuitenlandseRegioEinde());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE, his.getOmschrijvingLocatieEinde());
        if (his.getLandOfGebiedEinde() != null) {
            record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE, his.getLandOfGebiedEinde().getCode());
        }
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG, his.getDatumAanvang());
        record.addAttribuut(Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE, his.getDatumEinde());
    }

    private void mapHuwelijk() {

        // identiteit (historie patroon G)
        final BlobRecord idRecord = blobber.maakBlobRecord();
        idRecord.setParentObjectElement(typeBetrokkenheid);
        idRecord.setParentObjectSleutel(betrokkenheid.getId());
        idRecord.setObjectSleutel(relatie.getId());
        idRecord.setObjectElement(Element.HUWELIJK);
        idRecord.setVoorkomenSleutel(relatie.getId() /* dummy */);
        idRecord.setGroepElement(Element.HUWELIJK_IDENTITEIT);
        idRecord.addAttribuut(Element.HUWELIJK_SOORTCODE, relatie.getSoortRelatie().getCode());

        for (final RelatieHistorie his : relatie.getRelatieHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            vulHisRecordHuwelijk(his, record);
        }
    }

    private void vulHisRecordHuwelijk(final RelatieHistorie his, final BlobRecord record) {
        record.setParentObjectElement(typeBetrokkenheid);
        record.setParentObjectSleutel(betrokkenheid.getId());
        record.setObjectSleutel(relatie.getId());
        record.setObjectElement(Element.HUWELIJK);
        record.setGroepElement(Element.HUWELIJK_STANDAARD);
        record.setVoorkomenSleutel(his.getId());
        if (his.getGemeenteAanvang() != null) {
            record.addAttribuut(Element.HUWELIJK_GEMEENTEAANVANGCODE, his.getGemeenteAanvang().getCode());
        }
        record.addAttribuut(Element.HUWELIJK_WOONPLAATSNAAMAANVANG, his.getWoonplaatsnaamAanvang());
        record.addAttribuut(Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG, his.getBuitenlandsePlaatsAanvang());
        record.addAttribuut(Element.HUWELIJK_BUITENLANDSEREGIOAANVANG, his.getBuitenlandseRegioAanvang());
        record.addAttribuut(Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG, his.getOmschrijvingLocatieAanvang());
        if (his.getLandOfGebiedAanvang() != null) {
            record.addAttribuut(Element.HUWELIJK_LANDGEBIEDAANVANGCODE, his.getLandOfGebiedAanvang().getCode());
        }
        if (his.getRedenBeeindigingRelatie() != null) {
            record.addAttribuut(Element.HUWELIJK_REDENEINDECODE, String.valueOf(his.getRedenBeeindigingRelatie().getCode()));
        }
        if (his.getGemeenteEinde() != null) {
            record.addAttribuut(Element.HUWELIJK_GEMEENTEEINDECODE, his.getGemeenteEinde().getCode());
        }
        record.addAttribuut(Element.HUWELIJK_WOONPLAATSNAAMEINDE, his.getWoonplaatsnaamEinde());
        record.addAttribuut(Element.HUWELIJK_BUITENLANDSEPLAATSEINDE, his.getBuitenlandsePlaatsEinde());
        record.addAttribuut(Element.HUWELIJK_BUITENLANDSEREGIOEINDE, his.getBuitenlandseRegioEinde());
        record.addAttribuut(Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE, his.getOmschrijvingLocatieEinde());
        if (his.getLandOfGebiedEinde() != null) {
            record.addAttribuut(Element.HUWELIJK_LANDGEBIEDEINDECODE, his.getLandOfGebiedEinde().getCode());
        }
        record.addAttribuut(Element.HUWELIJK_DATUMAANVANG, his.getDatumAanvang());
        record.addAttribuut(Element.HUWELIJK_DATUMEINDE, his.getDatumEinde());
    }

    private void mapGerelateerdeBetrokkenheden() {
        final Iterable<Betrokkenheid> gerelateerdeBetrokkenheden = Iterables.filter(relatie.getBetrokkenheidSet(),
                betr -> betr.getPersoon() == null || !betr.getPersoon().getId().equals(blobber.getPersoon().getId()));
        for (final Betrokkenheid gerelateerdeBetrokkenheid : gerelateerdeBetrokkenheden) {
            new GerelateerdeBetrokkenheidBlobMapper(blobber, gerelateerdeBetrokkenheid).map();
        }
    }
}
