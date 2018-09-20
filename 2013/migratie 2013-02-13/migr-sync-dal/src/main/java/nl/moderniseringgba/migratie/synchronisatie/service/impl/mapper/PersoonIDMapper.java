/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIDHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpIdentificatienummersInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonIDHistorie} en vice versa.
 */
public final class PersoonIDMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpIdentificatienummersInhoud, PersoonIDHistorie> {

    /**
     * Maakt een PersoonIDMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonIDMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonIDHistorie historie, final Persoon persoon) {
        persoon.addPersoonIDHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonIDHistorie historie, final Persoon persoon) {
        persoon.setAdministratienummer(historie.getAdministratienummer());
        persoon.setBurgerservicenummer(historie.getBurgerservicenummer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonIDHistorie mapHistorischeGroep(final BrpIdentificatienummersInhoud groepInhoud) {
        final PersoonIDHistorie result = new PersoonIDHistorie();
        if (groepInhoud.getAdministratienummer() != null) {
            result.setAdministratienummer(new BigDecimal(groepInhoud.getAdministratienummer()));
        }
        if (groepInhoud.getBurgerservicenummer() != null) {
            result.setBurgerservicenummer(new BigDecimal(groepInhoud.getBurgerservicenummer()));
        }
        return result;
    }
}
