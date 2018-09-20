/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonImmigratieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpImmigratieInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonImmigratieHistorie} en vice versa.
 */
public final class PersoonImmigratieMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpImmigratieInhoud, PersoonImmigratieHistorie> {

    /**
     * Maakt een PersoonImmigratieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonImmigratieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonImmigratieHistorie historie, final Persoon persoon) {
        persoon.addPersoonImmigratieHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonImmigratieHistorie historie, final Persoon persoon) {
        persoon.setDatumVestigingInNederland(historie.getDatumVestigingInNederland());
        persoon.setLandVanWaarGevestigd(historie.getLandVanWaarGevestigd());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonImmigratieHistorie mapHistorischeGroep(final BrpImmigratieInhoud groepInhoud) {
        final PersoonImmigratieHistorie result = new PersoonImmigratieHistorie();
        result.setDatumVestigingInNederland(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumVestigingInNederland()));
        result.setLandVanWaarGevestigd(getStamtabelMapping().findLandByLandcode(
                groepInhoud.getLandVanwaarIngeschreven()));
        return result;
    }

}
