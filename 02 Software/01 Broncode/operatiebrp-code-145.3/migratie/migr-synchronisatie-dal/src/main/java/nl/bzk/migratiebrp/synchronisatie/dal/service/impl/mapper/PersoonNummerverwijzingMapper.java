/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpNummerverwijzingInhoud>} gemapt kan
 * worden op een verzameling van
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie} en vice versa.
 */
public final class PersoonNummerverwijzingMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpNummerverwijzingInhoud, PersoonNummerverwijzingHistorie> {

    /**
     * Maakt een PersoonBijhoudingMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonNummerverwijzingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonNummerverwijzingHistorie historie, final Persoon persoon) {
        persoon.addPersoonNummerverwijzingHistorie(historie);
    }

    @Override
    protected PersoonNummerverwijzingHistorie mapHistorischeGroep(final BrpNummerverwijzingInhoud groepInhoud, final Persoon persoon) {
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(persoon);
        historie.setVolgendeAdministratienummer(BrpString.unwrap(groepInhoud.getVolgendeAdministratienummer()));
        historie.setVorigeAdministratienummer(BrpString.unwrap(groepInhoud.getVorigeAdministratienummer()));
        historie.setVolgendeBurgerservicenummer(BrpString.unwrap(groepInhoud.getVolgendeBurgerservicenummer()));
        historie.setVorigeBurgerservicenummer(BrpString.unwrap(groepInhoud.getVorigeBurgerservicenummer()));

        // onderzoek
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getVolgendeAdministratienummer(),
                Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVorigeAdministratienummer(), Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getVolgendeBurgerservicenummer(),
                Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVorigeBurgerservicenummer(), Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER);
        return historie;
    }
}
