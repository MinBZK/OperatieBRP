/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpInschrijvingInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonInschrijvingHistorie} en vice versa.
 */
public final class PersoonInschrijvingMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpInschrijvingInhoud, PersoonInschrijvingHistorie> {

    /**
     * Maakt een PersoonInschrijvingMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonInschrijvingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonInschrijvingHistorie historie, final Persoon persoon) {
        persoon.addPersoonInschrijvingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonInschrijvingHistorie historie, final Persoon persoon) {
        persoon.setDatumInschrijving(historie.getDatumInschrijving());
        persoon.setVersienummer(historie.getVersienummer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonInschrijvingHistorie mapHistorischeGroep(final BrpInschrijvingInhoud groepInhoud) {
        // Datuminschrijving en versienummer zijn verplicht
        final PersoonInschrijvingHistorie result = new PersoonInschrijvingHistorie();
        result.setDatumInschrijving(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getDatumInschrijving()));
        result.setVersienummer(Long.valueOf(groepInhoud.getVersienummer()));
        return result;
    }
}
