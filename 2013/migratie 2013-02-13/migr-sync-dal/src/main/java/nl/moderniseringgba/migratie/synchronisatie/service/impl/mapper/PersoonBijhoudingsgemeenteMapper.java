/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsgemeenteHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpBijhoudingsgemeenteInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonBijhoudingsgemeenteHistorie} en vice versa.
 */
public final class PersoonBijhoudingsgemeenteMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpBijhoudingsgemeenteInhoud, PersoonBijhoudingsgemeenteHistorie> {

    /**
     * Maakt een PersoonBijhoudingsgemeenteMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonBijhoudingsgemeenteMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            voegHistorieToeAanEntiteit(final PersoonBijhoudingsgemeenteHistorie historie, final Persoon persoon) {
        persoon.addPersoonBijhoudingsgemeenteHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonBijhoudingsgemeenteHistorie historie,
            final Persoon persoon) {
        persoon.setDatumInschrijvingInGemeente(historie.getDatumInschrijvingInGemeente());
        persoon.setIndicatieOnverwerktDocumentAanwezig(historie.getIndicatieOnverwerktDocumentAanwezig());
        persoon.setBijhoudingsgemeente(historie.getPartij());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonBijhoudingsgemeenteHistorie mapHistorischeGroep(final BrpBijhoudingsgemeenteInhoud groepInhoud) {
        final PersoonBijhoudingsgemeenteHistorie result = new PersoonBijhoudingsgemeenteHistorie();
        result.setDatumInschrijvingInGemeente(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumInschrijvingInGemeente()));
        result.setIndicatieOnverwerktDocumentAanwezig(groepInhoud.getOnverwerktDocumentAanwezig());
        result.setPartij(getStamtabelMapping().findPartijByGemeentecode(groepInhoud.getBijhoudingsgemeenteCode()));
        return result;
    }
}
