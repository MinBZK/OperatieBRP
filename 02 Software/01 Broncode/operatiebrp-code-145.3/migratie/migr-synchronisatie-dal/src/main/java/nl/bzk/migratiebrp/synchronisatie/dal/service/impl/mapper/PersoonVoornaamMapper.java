/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Deze mapper mapped een BrpVoornaamInhoud op PersoonVoornaam en PersoonVoornaamHistorie.
 */
public final class PersoonVoornaamMapper extends AbstractHistorieMapperStrategie<BrpVoornaamInhoud, PersoonVoornaamHistorie, PersoonVoornaam> {

    /**
     * Maakt een PersoonVoornaamMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonVoornaamMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonVoornaamHistorie historie, final PersoonVoornaam entiteit) {
        entiteit.addPersoonVoornaamHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonVoornaamHistorie mapHistorischeGroep(final BrpVoornaamInhoud groepInhoud, final PersoonVoornaam entiteit) {
        final PersoonVoornaamHistorie result = new PersoonVoornaamHistorie(entiteit, BrpString.unwrap(groepInhoud.getVoornaam()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVoornaam(), Element.PERSOON_VOORNAAM_NAAM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVolgnummer(), Element.PERSOON_VOORNAAM_VOLGNUMMER);
        return result;
    }

}
