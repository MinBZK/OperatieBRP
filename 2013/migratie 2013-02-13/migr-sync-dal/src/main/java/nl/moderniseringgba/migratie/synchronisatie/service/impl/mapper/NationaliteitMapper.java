/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapped BrpNationaliteitInhoud op de PersoonNationaliteit entity inclusief historie.
 */
public final class NationaliteitMapper extends
        AbstractHistorieMapperStrategie<BrpNationaliteitInhoud, PersoonNationaliteitHistorie, PersoonNationaliteit> {

    /**
     * Maakt een NationaliteitMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public NationaliteitMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(
            final PersoonNationaliteitHistorie historie,
            final PersoonNationaliteit entiteit) {
        entiteit.addPersoonNationaliteitHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonNationaliteitHistorie historie,
            final PersoonNationaliteit entiteit) {
        entiteit.setRedenVerkrijgingNLNationaliteit(historie.getRedenVerkrijgingNLNationaliteit());
        entiteit.setRedenVerliesNLNationaliteit(historie.getRedenVerliesNLNationaliteit());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonNationaliteitHistorie mapHistorischeGroep(final BrpNationaliteitInhoud groepInhoud) {
        final PersoonNationaliteitHistorie result = new PersoonNationaliteitHistorie();

        result.setRedenVerkrijgingNLNationaliteit(getStamtabelMapping().findRedenVerkrijgingNLNationaliteitByCode(
                groepInhoud.getRedenVerkrijgingNederlandschapCode()));
        result.setRedenVerliesNLNationaliteit(getStamtabelMapping().findRedenVerliesNLNationaliteitByCode(
                groepInhoud.getRedenVerliesNederlandschapCode()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(
            final BrpStapel<BrpNationaliteitInhoud> brpStapel,
            final PersoonNationaliteit entiteit) {
        final BrpNationaliteitCode brpNationaliteitCode =
                brpStapel.getMeestRecenteElement().getInhoud().getNationaliteitCode();

        entiteit.setNationaliteit(getStamtabelMapping().findNationaliteitByNationaliteitcode(brpNationaliteitCode));
        entiteit.setPersoonNationaliteitStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
    }
}
