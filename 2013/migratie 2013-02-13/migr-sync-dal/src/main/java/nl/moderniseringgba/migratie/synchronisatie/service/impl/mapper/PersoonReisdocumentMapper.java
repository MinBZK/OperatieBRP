/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapped de BrpReisdocumentInhoud op PersoonReisdocument en PersoonReisdocumentHistorie.
 */
public final class PersoonReisdocumentMapper extends
        AbstractHistorieMapperStrategie<BrpReisdocumentInhoud, PersoonReisdocumentHistorie, PersoonReisdocument> {

    /**
     * Maakt een PersoonReisdocumentMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonReisdocumentMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(
            final BrpStapel<BrpReisdocumentInhoud> brpStapel,
            final PersoonReisdocument entiteit) {
        entiteit.setPersoonReisdocumentStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
        entiteit.setSoortNederlandsReisdocument(getStamtabelMapping().findSoortNederlandsReisdocumentByCode(
                brpStapel.getMeestRecenteElement().getInhoud().getSoort()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(
            final PersoonReisdocumentHistorie historie,
            final PersoonReisdocument entiteit) {
        entiteit.addPersoonReisdocumentHistorieSet(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonReisdocumentHistorie historie,
            final PersoonReisdocument entiteit) {
        entiteit.setAutoriteitVanAfgifteReisdocument(historie.getAutoriteitVanAfgifteReisdocument());
        entiteit.setDatumIngang(historie.getDatumIngang());
        entiteit.setDatumInhoudingVermissing(historie.getDatumInhoudingVermissing());
        entiteit.setDatumUitgifte(historie.getDatumUitgifte());
        entiteit.setDatumVoorzieneEindeGeldigheid(historie.getDatumVoorzieneEindeGeldigheid());
        entiteit.setLengteHouder(historie.getLengteHouder());
        entiteit.setNummer(historie.getNummer());
        entiteit.setRedenVervallenReisdocument(historie.getRedenVervallenReisdocument());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonReisdocumentHistorie mapHistorischeGroep(final BrpReisdocumentInhoud groepInhoud) {
        final PersoonReisdocumentHistorie result = new PersoonReisdocumentHistorie();
        result.setAutoriteitVanAfgifteReisdocument(getStamtabelMapping().findAutoriteitVanAfgifteReisdocumentByCode(
                groepInhoud.getAutoriteitVanAfgifte()));
        result.setDatumIngang(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getDatumIngangDocument()));
        result.setDatumInhoudingVermissing(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumInhoudingVermissing()));
        result.setDatumUitgifte(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getDatumUitgifte()));
        result.setDatumVoorzieneEindeGeldigheid(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumVoorzieneEindeGeldigheid()));
        result.setLengteHouder(MapperUtil.mapIntegerToBigDecimal(groepInhoud.getLengteHouder()));
        result.setNummer(groepInhoud.getNummer());
        result.setRedenVervallenReisdocument(getStamtabelMapping().findRedenVervallenReisdocumentByCode(
                groepInhoud.getRedenOntbreken()));
        return result;
    }
}
