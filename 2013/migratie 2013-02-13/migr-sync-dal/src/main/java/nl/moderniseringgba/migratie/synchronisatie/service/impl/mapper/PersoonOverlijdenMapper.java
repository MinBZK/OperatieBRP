/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpOverlijdenInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonOverlijdenHistorie} en vice versa.
 */
public final class PersoonOverlijdenMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpOverlijdenInhoud, PersoonOverlijdenHistorie> {

    /**
     * Maakt een PersoonOverlijdenMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonOverlijdenMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonOverlijdenHistorie historie, final Persoon persoon) {
        persoon.addPersoonOverlijdenHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonOverlijdenHistorie historie, final Persoon persoon) {
        persoon.setWoonplaatsOverlijden(historie.getPlaats());
        persoon.setLandOverlijden(historie.getLand());
        persoon.setOmschrijvingLocatieOverlijden(historie.getOmschrijvingLocatieOverlijden());
        persoon.setDatumOverlijden(historie.getDatumOverlijden());
        persoon.setBuitenlandsePlaatsOverlijden(historie.getBuitenlandsePlaatsOverlijden());
        persoon.setBuitenlandseRegioOverlijden(historie.getBuitenlandseRegioOverlijden());
        persoon.setGemeenteOverlijden(historie.getPartij());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonOverlijdenHistorie mapHistorischeGroep(final BrpOverlijdenInhoud groepInhoud) {
        final PersoonOverlijdenHistorie result = new PersoonOverlijdenHistorie();

        result.setPlaats(getStamtabelMapping().findPlaatsByCode(groepInhoud.getPlaatsCode()));
        result.setLand(getStamtabelMapping().findLandByLandcode(groepInhoud.getLandCode(), Precondities.PRE010));
        result.setOmschrijvingLocatieOverlijden(groepInhoud.getOmschrijvingLocatie());
        result.setDatumOverlijden(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getDatum()));
        result.setBuitenlandsePlaatsOverlijden(groepInhoud.getBuitenlandsePlaats());
        result.setBuitenlandseRegioOverlijden(groepInhoud.getBuitenlandseRegio());
        result.setPartij(getStamtabelMapping().findPartijByGemeentecode(groepInhoud.getGemeenteCode(),
                Precondities.PRE026));
        return result;
    }
}
