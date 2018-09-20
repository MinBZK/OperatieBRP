/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Document;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.DocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapped een stapel met BrpDocumentInhoud naar de corresponderen BRP entiteiten uit het operationele gevevensmodel van
 * de BRP.
 */
public final class DocumentMapper extends
        AbstractHistorieMapperStrategie<BrpDocumentInhoud, DocumentHistorie, Document> {

    /**
     * Maakt een DocumentMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public DocumentMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDocumentInhoud> brpStapel, final Document entiteit) {
        final BrpSoortDocumentCode soortDocumentCode =
                brpStapel.getMeestRecenteElement().getInhoud().getSoortDocumentCode();
        entiteit.setSoortDocument(getStamtabelMapping().findSoortDocumentByCode(soortDocumentCode));
        entiteit.setDocumentStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
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
    protected DocumentHistorie mapHistorischeGroep(final BrpDocumentInhoud groepInhoud) {
        final DocumentHistorie result = new DocumentHistorie();
        result.setAktenummer(groepInhoud.getAktenummer());
        result.setIdentificatie(groepInhoud.getIdentificatie());
        result.setOmschrijving(groepInhoud.getOmschrijving());
        result.setPartij(getStamtabelMapping().findPartijByPartijcode(groepInhoud.getPartijCode()));
        return result;
    }

}
