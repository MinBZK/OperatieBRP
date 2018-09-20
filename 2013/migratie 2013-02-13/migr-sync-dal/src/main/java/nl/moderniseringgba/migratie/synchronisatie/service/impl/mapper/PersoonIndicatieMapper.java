/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpIndicatieGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Een mapper voor een BRP indicatie die een van de Indicatie stapels uit het migratie model mapped op PersoonIndicatie.
 * 
 * @param <T>
 *            het type indicatie die hier gemapped wordt
 */
public final class PersoonIndicatieMapper<T extends BrpIndicatieGroepInhoud> extends
        AbstractHistorieMapperStrategie<T, PersoonIndicatieHistorie, PersoonIndicatie> {

    /**
     * Maakt een PersoonIndicatieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonIndicatieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            voegHistorieToeAanEntiteit(final PersoonIndicatieHistorie historie, final PersoonIndicatie entiteit) {
        entiteit.addPersoonIndicatieHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonIndicatieHistorie historie,
            final PersoonIndicatie entiteit) {
        entiteit.setWaarde(historie.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonIndicatieHistorie mapHistorischeGroep(final T groepInhoud) {
        final PersoonIndicatieHistorie result = new PersoonIndicatieHistorie();
        result.setWaarde(groepInhoud.getHeeftIndicatie());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<T> brpStapel, final PersoonIndicatie entiteit) {
        entiteit.setPersoonIndicatieStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
        entiteit.setSoortIndicatie(mapBrpClassOpIndicatie(brpStapel.getMeestRecenteElement().getInhoud().getClass()));
    }

    private SoortIndicatie mapBrpClassOpIndicatie(final Class<? extends BrpIndicatieGroepInhoud> brpIndicatieClass) {
        SoortIndicatie result;
        if (BrpBehandeldAlsNederlanderIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.BEHANDELD_ALS_NEDERLANDER;
        } else if (BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT;
        } else if (BrpBezitBuitenlandsReisdocumentIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT;
        } else if (BrpDerdeHeeftGezagIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.DERDE_HEEFT_GEZAG;
        } else if (BrpGeprivilegieerdeIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.GEPRIVILEGIEERDE;
        } else if (BrpOnderCurateleIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.ONDER_CURATELE;
        } else if (BrpStatenloosIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.STATENLOOS;
        } else if (BrpVerstrekkingsbeperkingInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.VERSTREKKINGSBEPERKING;
        } else if (BrpVastgesteldNietNederlanderIndicatieInhoud.class.equals(brpIndicatieClass)) {
            result = SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER;
        } else {
            throw new IllegalStateException("Onbekende indicatie stapel: " + brpIndicatieClass);
        }
        return result;
    }
}
