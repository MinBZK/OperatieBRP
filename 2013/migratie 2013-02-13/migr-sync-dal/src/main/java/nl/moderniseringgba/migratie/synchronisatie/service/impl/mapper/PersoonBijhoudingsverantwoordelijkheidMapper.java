/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerantwoordelijkeCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsverantwoordelijkheidHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verantwoordelijke;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud>} gemapt kan worden op een verzameling
 * van {@link PersoonBijhoudingsverantwoordelijkheidHistorie} en vice versa.
 */
public final class PersoonBijhoudingsverantwoordelijkheidMapper
        extends
        AbstractPersoonHistorieMapperStrategie<BrpBijhoudingsverantwoordelijkheidInhoud, PersoonBijhoudingsverantwoordelijkheidHistorie> {

    /**
     * Maakt een PersoonBijhoudingsverantwoordelijkheidMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonBijhoudingsverantwoordelijkheidMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(
            final PersoonBijhoudingsverantwoordelijkheidHistorie historie,
            final Persoon persoon) {
        persoon.addPersoonBijhoudingsverantwoordelijkheidHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonBijhoudingsverantwoordelijkheidHistorie historie,
            final Persoon persoon) {
        persoon.setVerantwoordelijke(historie.getVerantwoordelijke());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonBijhoudingsverantwoordelijkheidHistorie mapHistorischeGroep(
            final BrpBijhoudingsverantwoordelijkheidInhoud groepInhoud) {
        final PersoonBijhoudingsverantwoordelijkheidHistorie result =
                new PersoonBijhoudingsverantwoordelijkheidHistorie();

        final BrpVerantwoordelijkeCode verantwoordelijkeCode = groepInhoud.getVerantwoordelijkeCode();
        if (verantwoordelijkeCode != null) {
            result.setVerantwoordelijke(Verantwoordelijke.valueOf(verantwoordelijkeCode.getCode()));
        }
        return result;
    }
}
