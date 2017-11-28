/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map Reisdocument op Blob-records.
 */
final class PersoonReisdocumentBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonReisdocumentBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map reisdocumenten.
     */
    void map() {
        for (final PersoonReisdocument persoonReisdocument : blobber.getPersoon().getPersoonReisdocumentSet()) {
            mapIdentiteitGroep(persoonReisdocument);
            mapStandaardGroep(persoonReisdocument);
        }
    }

    private void mapIdentiteitGroep(final PersoonReisdocument persoonReisdocument) {
        // identiteitsgroep, geen historie
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(persoonReisdocument.getPersoon().getId());
        record.setObjectSleutel(persoonReisdocument.getId());
        record.setObjectElement(Element.PERSOON_REISDOCUMENT);
        record.setVoorkomenSleutel(persoonReisdocument.getId()/* dummy */);
        record.setGroepElement(Element.PERSOON_REISDOCUMENT_IDENTITEIT);
        record.addAttribuut(Element.PERSOON_REISDOCUMENT_SOORTCODE, persoonReisdocument.getSoortNederlandsReisdocument().getCode());
    }

    private void mapStandaardGroep(final PersoonReisdocument persoonReisdocument) {
        for (final PersoonReisdocumentHistorie his : persoonReisdocument.getPersoonReisdocumentHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonReisdocument.getPersoon().getId());
            record.setObjectSleutel(persoonReisdocument.getId());
            record.setObjectElement(Element.PERSOON_REISDOCUMENT);
            record.setGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_NUMMER, his.getNummer());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, his.getAutoriteitVanAfgifte());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT, his.getDatumIngangDocument());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT, his.getDatumEindeDocument());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE, his.getDatumUitgifte());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING, his.getDatumInhoudingOfVermissing());
            record.addAttribuut(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT, his.getDatumIngangDocument());
            if (his.getAanduidingInhoudingOfVermissingReisdocument() != null) {
                record.addAttribuut(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE,
                        String.valueOf(his.getAanduidingInhoudingOfVermissingReisdocument().getCode()));
            }
        }
    }
}
