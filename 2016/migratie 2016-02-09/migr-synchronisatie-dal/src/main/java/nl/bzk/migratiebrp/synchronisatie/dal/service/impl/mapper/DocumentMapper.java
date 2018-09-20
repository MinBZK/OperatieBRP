/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapped een stapel met BrpDocumentInhoud naar de corresponderen BRP entiteiten uit het operationele gevevensmodel van
 * de BRP.
 */
public final class DocumentMapper extends AbstractHistorieMapperStrategie<BrpDocumentInhoud, DocumentHistorie, Document> {

    /**
     * Maakt een DocumentMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public DocumentMapper(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final BRPActieFactory brpActieFactory,
        final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDocumentInhoud> brpStapel, final Document entiteit) {
        final BrpSoortDocumentCode soortDocumentCode = brpStapel.getLaatsteElement().getInhoud().getSoortDocumentCode();
        entiteit.setSoortDocument(getStamtabelMapping().findSoortDocumentByCode(soortDocumentCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final DocumentHistorie historie, final Document entiteit) {
        entiteit.addDocumentHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final DocumentHistorie historie, final Document entiteit) {
        entiteit.setAktenummer(historie.getAktenummer());
        entiteit.setIdentificatie(historie.getIdentificatie());
        entiteit.setOmschrijving(historie.getOmschrijving());
        entiteit.setPartij(historie.getPartij());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DocumentHistorie mapHistorischeGroep(final BrpDocumentInhoud groepInhoud, final Document entiteit) {
        final DocumentHistorie result = new DocumentHistorie(entiteit, getStamtabelMapping().findPartijByCode(groepInhoud.getPartijCode()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getPartijCode(), Element.DOCUMENT_PARTIJCODE);
        getOnderzoekMapper().mapOnderzoek(entiteit, groepInhoud.getSoortDocumentCode(), Element.DOCUMENT_SOORTNAAM);

        result.setAktenummer(BrpString.unwrap(groepInhoud.getAktenummer()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getAktenummer(), Element.DOCUMENT_AKTENUMMER);
        result.setIdentificatie(BrpString.unwrap(groepInhoud.getIdentificatie()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIdentificatie(), Element.DOCUMENT_IDENTIFICATIE);
        result.setOmschrijving(BrpString.unwrap(groepInhoud.getOmschrijving()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getOmschrijving(), Element.DOCUMENT_OMSCHRIJVING);
        return result;
    }

}
