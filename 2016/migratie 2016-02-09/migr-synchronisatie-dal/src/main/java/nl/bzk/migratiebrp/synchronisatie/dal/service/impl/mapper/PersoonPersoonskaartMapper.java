/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonPersoonskaartHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpPersoonskaartInhoud>} gemapt kan
 * worden op een verzameling van {@link PersoonPersoonskaartHistorie} en vice versa.
 */
public final class PersoonPersoonskaartMapper extends AbstractPersoonHistorieMapperStrategie<BrpPersoonskaartInhoud, PersoonPersoonskaartHistorie> {

    /**
     * Maakt een PersoonPersoonskaartMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonPersoonskaartMapper(
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
    protected void voegHistorieToeAanEntiteit(final PersoonPersoonskaartHistorie historie, final Persoon persoon) {
        persoon.addPersoonPersoonskaartHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonPersoonskaartHistorie historie, final Persoon persoon) {
        persoon.setIndicatiePersoonskaartVolledigGeconverteerd(historie.getIndicatiePersoonskaartVolledigGeconverteerd());
        persoon.setGemeentePersoonskaart(historie.getPartij());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonPersoonskaartHistorie mapHistorischeGroep(final BrpPersoonskaartInhoud groepInhoud, final Persoon persoon) {
        final PersoonPersoonskaartHistorie result =
                new PersoonPersoonskaartHistorie(persoon, BrpBoolean.unwrap(groepInhoud.getIndicatiePKVolledigGeconverteerd()));
        result.setPartij(getStamtabelMapping().findPartijByCode(groepInhoud.getGemeentePKCode()));

        getOnderzoekMapper().mapOnderzoek(
            result,
            groepInhoud.getIndicatiePKVolledigGeconverteerd(),
            Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGemeentePKCode(), Element.PERSOON_PERSOONSKAART_PARTIJCODE);

        return result;
    }
}
