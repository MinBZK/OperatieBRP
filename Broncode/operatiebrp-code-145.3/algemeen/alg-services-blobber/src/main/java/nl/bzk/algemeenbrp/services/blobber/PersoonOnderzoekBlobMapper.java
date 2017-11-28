/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map Onderzoek op Blob-records.
 */
final class PersoonOnderzoekBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonOnderzoekBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map onderzoek.
     */
    void map() {
        blobber.getPersoon().getOnderzoeken().forEach(this::map);
    }

    private void map(final Onderzoek onderzoek) {
        final BlobRecord recordIdentiteit = blobber.maakBlobRecord();
        recordIdentiteit.setParentObjectElement(Element.PERSOON);
        recordIdentiteit.setParentObjectSleutel(onderzoek.getPersoon().getId());
        recordIdentiteit.setObjectSleutel(onderzoek.getId().longValue());
        recordIdentiteit.setObjectElement(Element.ONDERZOEK);
        recordIdentiteit.setVoorkomenSleutel(onderzoek.getId().longValue() /* dummy */);
        recordIdentiteit.setGroepElement(Element.ONDERZOEK_IDENTITEIT);
        recordIdentiteit.addAttribuut(Element.ONDERZOEK_PARTIJCODE, onderzoek.getPartij().getCode());

        for (final OnderzoekHistorie his : onderzoek.getOnderzoekHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(onderzoek.getPersoon().getId());
            record.setObjectSleutel(onderzoek.getId().longValue());
            record.setObjectElement(Element.ONDERZOEK);
            record.setGroepElement(Element.ONDERZOEK_STANDAARD);
            record.setVoorkomenSleutel(his.getId().longValue());
            record.addAttribuut(Element.ONDERZOEK_DATUMAANVANG, his.getDatumAanvang());
            record.addAttribuut(Element.ONDERZOEK_DATUMEINDE, his.getDatumEinde());
            record.addAttribuut(Element.ONDERZOEK_OMSCHRIJVING, his.getOmschrijving());
            if (his.getStatusOnderzoek() != null) {
                record.addAttribuut(Element.ONDERZOEK_STATUSNAAM, his.getStatusOnderzoek().getNaam());
            }
        }

        for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevenInOnderzoekSet()) {
            mapGegegevenInOnderzoek(onderzoek, gegevenInOnderzoek);
        }
    }

    private void mapGegegevenInOnderzoek(final Onderzoek onderzoek, final GegevenInOnderzoek gegevenInOnderzoek) {
        for (final GegevenInOnderzoekHistorie his : gegevenInOnderzoek.getGegevenInOnderzoekHistorieSet()) {
            // identiteitsgroep met formele historie
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.ONDERZOEK);
            record.setParentObjectSleutel(onderzoek.getId().longValue());
            record.setObjectSleutel(gegevenInOnderzoek.getId());
            record.setObjectElement(Element.GEGEVENINONDERZOEK);
            record.setGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT);
            record.setVoorkomenSleutel(record.getObjectSleutel());
            record.addAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM, gegevenInOnderzoek.getSoortGegeven().getNaam());
            // bepaling gegeven : is voorkomen of object
            final Entiteit entiteit = gegevenInOnderzoek.getEntiteitOfVoorkomen();
            if (entiteit != null) {
                if (entiteit instanceof FormeleHistorie) {
                    record.addAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN, entiteit.getId());
                } else {
                    record.addAttribuut(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN, entiteit.getId());
                }
            }

        }
    }
}
