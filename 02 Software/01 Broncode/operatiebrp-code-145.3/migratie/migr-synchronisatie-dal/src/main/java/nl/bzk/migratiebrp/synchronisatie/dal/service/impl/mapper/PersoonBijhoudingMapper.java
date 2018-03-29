/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpBijhoudingInhoud>} gemapt kan worden
 * op een verzameling van {@link PersoonBijhoudingHistorie} en vice versa.
 */
public final class PersoonBijhoudingMapper extends AbstractPersoonHistorieMapperStrategie<BrpBijhoudingInhoud, PersoonBijhoudingHistorie> {

    /**
     * Maakt een PersoonBijhoudingMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonBijhoudingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonBijhoudingHistorie historie, final Persoon persoon) {
        persoon.addPersoonBijhoudingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonBijhoudingHistorie mapHistorischeGroep(final BrpBijhoudingInhoud groepInhoud, final Persoon persoon) {
        final PersoonBijhoudingHistorie result =
                new PersoonBijhoudingHistorie(
                        persoon,
                        getStamtabelMapping().findPartijByCode(groepInhoud.getBijhoudingspartijCode()),
                        Bijhoudingsaard.parseCode(groepInhoud.getBijhoudingsaardCode().getWaarde()),
                        NadereBijhoudingsaard.parseCode(groepInhoud.getNadereBijhoudingsaardCode().getWaarde()));

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBijhoudingspartijCode(), Element.PERSOON_BIJHOUDING_PARTIJCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBijhoudingsaardCode(), Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getNadereBijhoudingsaardCode(), Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE);

        return result;
    }
}
