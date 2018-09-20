/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaam;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Deze mapper mapped een BrpVoornaamInhoud op PersoonVoornaam en PersoonVoornaamHistorie.
 * 
 */
public final class PersoonVoornaamMapper extends
        AbstractHistorieMapperStrategie<BrpVoornaamInhoud, PersoonVoornaamHistorie, PersoonVoornaam> {

    /**
     * Maakt een PersoonVoornaamMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonVoornaamMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpVoornaamInhoud> brpStapel, final PersoonVoornaam entiteit) {
        entiteit.setPersoonVoornaamStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
        entiteit.setVolgnummer(brpStapel.getMeestRecenteElement().getInhoud().getVolgnummer());
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
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonVoornaamHistorie historie,
            final PersoonVoornaam entiteit) {
        entiteit.setNaam(historie.getNaam());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonVoornaamHistorie mapHistorischeGroep(final BrpVoornaamInhoud groepInhoud) {
        final PersoonVoornaamHistorie result = new PersoonVoornaamHistorie();
        result.setNaam(groepInhoud.getVoornaam());
        return result;
    }

}
