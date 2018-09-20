/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpGeslachtsaanduidingInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonGeslachtsaanduidingHistorie} en vice versa.
 */
public final class PersoonGeslachtsaanduidingMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpGeslachtsaanduidingInhoud, PersoonGeslachtsaanduidingHistorie> {

    /**
     * Maakt een PersoonGeslachtsaanduidingMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonGeslachtsaanduidingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            voegHistorieToeAanEntiteit(final PersoonGeslachtsaanduidingHistorie historie, final Persoon persoon) {
        persoon.addPersoonGeslachtsaanduidingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonGeslachtsaanduidingHistorie historie,
            final Persoon persoon) {
        persoon.setGeslachtsaanduiding(historie.getGeslachtsaanduiding());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonGeslachtsaanduidingHistorie mapHistorischeGroep(final BrpGeslachtsaanduidingInhoud groepInhoud) {
        final PersoonGeslachtsaanduidingHistorie result = new PersoonGeslachtsaanduidingHistorie();
        if (groepInhoud.getGeslachtsaanduiding() != null) {
            final String code = String.valueOf(groepInhoud.getGeslachtsaanduiding().getCode());
            result.setGeslachtsaanduiding(Geslachtsaanduiding.parseCode(code));
        }
        return result;
    }
}
