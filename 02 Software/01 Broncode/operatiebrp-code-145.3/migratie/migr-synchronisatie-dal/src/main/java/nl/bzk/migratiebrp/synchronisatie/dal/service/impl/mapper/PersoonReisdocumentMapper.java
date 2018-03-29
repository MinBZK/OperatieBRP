/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapped de BrpReisdocumentInhoud op PersoonReisdocument en PersoonReisdocumentHistorie.
 */
public final class PersoonReisdocumentMapper
        extends AbstractHistorieMapperStrategie<BrpReisdocumentInhoud, PersoonReisdocumentHistorie, PersoonReisdocument> {

    /**
     * Maakt een PersoonReisdocumentMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonReisdocumentMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonReisdocumentHistorie historie, final PersoonReisdocument entiteit) {
        entiteit.addPersoonReisdocumentHistorieSet(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonReisdocumentHistorie mapHistorischeGroep(final BrpReisdocumentInhoud groepInhoud, final PersoonReisdocument entiteit) {
        final PersoonReisdocumentHistorie result =
                new PersoonReisdocumentHistorie(
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumIngangDocument()),
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumUitgifte()),
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEindeDocument()),
                        BrpString.unwrap(groepInhoud.getNummer()),
                        groepInhoud.getAutoriteitVanAfgifte().getWaarde(),
                        entiteit);
        result.setDatumInhoudingOfVermissing(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumInhoudingOfVermissing()));
        final AanduidingInhoudingOfVermissingReisdocument inhoudingOfVermissingReisdocumentByCode;
        final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAandInhoudingOfVermissing = groepInhoud.getAanduidingInhoudingOfVermissing();
        inhoudingOfVermissingReisdocumentByCode =
                getStamtabelMapping().findAanduidingInhoudingOfVermissingReisdocumentByCode(brpAandInhoudingOfVermissing);
        result.setAanduidingInhoudingOfVermissingReisdocument(inhoudingOfVermissingReisdocumentByCode);

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumIngangDocument(), Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumUitgifte(), Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumEindeDocument(), Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getAutoriteitVanAfgifte(), Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumInhoudingOfVermissing(), Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING);
        getOnderzoekMapper().mapOnderzoek(result, brpAandInhoudingOfVermissing, Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getNummer(), Element.PERSOON_REISDOCUMENT_NUMMER);
        getOnderzoekMapper().mapOnderzoek(entiteit, groepInhoud.getSoort(), Element.PERSOON_REISDOCUMENT_SOORTCODE);

        return result;
    }
}
