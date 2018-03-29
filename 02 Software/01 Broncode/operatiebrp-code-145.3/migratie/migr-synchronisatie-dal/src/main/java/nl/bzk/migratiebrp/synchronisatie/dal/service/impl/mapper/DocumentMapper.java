/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapped een stapel met BrpDocumentInhoud naar de corresponderen BRP entiteiten uit het operationele gevevensmodel van
 * de BRP.
 */
public final class DocumentMapper extends AbstractMapperStrategie<BrpDocumentInhoud, Document> {

    /**
     * Maakt een DocumentMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public DocumentMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, onderzoekMapper);
    }

    @Override
    protected void mapHistorischeGegevens(final BrpStapel<BrpDocumentInhoud> brpStapel, final Document entiteit, final Element objecttype) {
        final BrpDocumentInhoud documentInhoud = brpStapel.getLaatsteElement().getInhoud();
        getOnderzoekMapper().mapOnderzoek(entiteit, documentInhoud.getPartijCode(), Element.DOCUMENT_PARTIJCODE);
        getOnderzoekMapper().mapOnderzoek(entiteit, documentInhoud.getSoortDocumentCode(), Element.DOCUMENT_SOORTNAAM);

        entiteit.setAktenummer(BrpString.unwrap(documentInhoud.getAktenummer()));
        getOnderzoekMapper().mapOnderzoek(entiteit, documentInhoud.getAktenummer(), Element.DOCUMENT_AKTENUMMER);
        entiteit.setOmschrijving(BrpString.unwrap(documentInhoud.getOmschrijving()));
        getOnderzoekMapper().mapOnderzoek(entiteit, documentInhoud.getOmschrijving(), Element.DOCUMENT_OMSCHRIJVING);
    }

}
