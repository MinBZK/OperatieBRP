/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpGeboorteInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonGeboorteHistorie} en vice versa.
 */
public final class PersoonGeboorteMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpGeboorteInhoud, PersoonGeboorteHistorie> {

    /**
     * Maakt een PersoonGeboorteMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonGeboorteMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonGeboorteHistorie historie, final Persoon persoon) {
        persoon.addPersoonGeboorteHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonGeboorteHistorie historie, final Persoon persoon) {
        persoon.setGemeenteGeboorte(historie.getPartij());
        persoon.setLandGeboorte(historie.getLand());
        persoon.setDatumGeboorte(historie.getDatumGeboorte());
        persoon.setOmschrijvingGeboortelocatie(historie.getOmschrijvingGeboortelocatie());
        persoon.setWoonplaatsGeboorte(historie.getPlaats());
        persoon.setBuitenlandseGeboorteplaats(historie.getBuitenlandseGeboorteplaats());
        persoon.setBuitenlandseRegioGeboorte(historie.getBuitenlandseRegioGeboorte());
        persoon.setLandGeboorte(historie.getLand());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonGeboorteHistorie mapHistorischeGroep(final BrpGeboorteInhoud groepInhoud) {
        final PersoonGeboorteHistorie result = new PersoonGeboorteHistorie();

        result.setPartij(getStamtabelMapping().findPartijByGemeentecode(groepInhoud.getGemeenteCode(),
                Precondities.PRE025));
        result.setLand(getStamtabelMapping().findLandByLandcode(groepInhoud.getLandCode(), Precondities.PRE008));
        result.setDatumGeboorte(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getGeboortedatum()));
        result.setOmschrijvingGeboortelocatie(groepInhoud.getOmschrijvingGeboortelocatie());
        result.setPlaats(getStamtabelMapping().findPlaatsByCode(groepInhoud.getPlaatsCode()));
        result.setBuitenlandseGeboorteplaats(groepInhoud.getBuitenlandseGeboorteplaats());
        result.setBuitenlandseRegioGeboorte(groepInhoud.getBuitenlandseRegioGeboorte());

        return result;
    }
}
