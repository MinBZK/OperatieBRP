/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVerblijfsrechtHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpVerblijfsrechtInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonVerblijfsrechtHistorie} en vice versa.
 * 
 */
public final class PersoonVerblijfsrechtHistorieMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpVerblijfsrechtInhoud, PersoonVerblijfsrechtHistorie> {

    /**
     * Maakt een PersoonVerblijfsrechtHistorieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonVerblijfsrechtHistorieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonVerblijfsrechtHistorie historie, final Persoon persoon) {
        persoon.addPersoonVerblijfsrechtHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            kopieerActueleGroepNaarEntiteit(final PersoonVerblijfsrechtHistorie historie, final Persoon persoon) {
        persoon.setVerblijfsrecht(historie.getVerblijfsrecht());
        persoon.setDatumAanvangVerblijfsrecht(historie.getDatumAanvangVerblijfsrecht());
        persoon.setDatumVoorzienEindeVerblijfsrecht(historie.getDatumVoorzienEindeVerblijfsrecht());
        persoon.setDatumAanvangAaneensluitendVerblijfsrecht(historie.getDatumAanvangAaneensluitendVerblijfsrecht());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonVerblijfsrechtHistorie mapHistorischeGroep(final BrpVerblijfsrechtInhoud groepInhoud) {
        final PersoonVerblijfsrechtHistorie result = new PersoonVerblijfsrechtHistorie();

        result.setDatumAanvangAaneensluitendVerblijfsrecht(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getAanvangAaneensluitendVerblijfsrecht()));
        result.setDatumAanvangVerblijfsrecht(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getAanvangVerblijfsrecht()));
        result.setDatumVoorzienEindeVerblijfsrecht(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getVoorzienEindeVerblijfsrecht()));
        result.setVerblijfsrecht(getStamtabelMapping().findVerblijfsrechtByCode(groepInhoud.getVerblijfsrechtCode()));
        return result;
    }
}
