/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpAfgeleidAdministratiefInhoud>} gemapt
 * kan worden op een verzameling van
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie}.
 */
public final class PersoonAfgeleidAdministratiefMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpPersoonAfgeleidAdministratiefInhoud, PersoonAfgeleidAdministratiefHistorie> {

    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een PersoonAfgeleidAdministratiefMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonAfgeleidAdministratiefMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        this.brpActieFactory = brpActieFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonAfgeleidAdministratiefHistorie historie, final Persoon persoon) {
        persoon.addPersoonAfgeleidAdministratiefHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonAfgeleidAdministratiefHistorie mapHistorischeGroep(final BrpPersoonAfgeleidAdministratiefInhoud groepInhoud, final Persoon persoon) {

        final AdministratieveHandeling administratieveHandeling = brpActieFactory.getAdministratieveHandeling();

        final PersoonAfgeleidAdministratiefHistorie result;
        result =
                new PersoonAfgeleidAdministratiefHistorie(
                        (short) 1,
                        persoon,
                        administratieveHandeling,
                        administratieveHandeling.getDatumTijdRegistratie());
        result.setDatumTijdLaatsteWijzigingGba(administratieveHandeling.getDatumTijdRegistratie());

        return result;
    }
}
