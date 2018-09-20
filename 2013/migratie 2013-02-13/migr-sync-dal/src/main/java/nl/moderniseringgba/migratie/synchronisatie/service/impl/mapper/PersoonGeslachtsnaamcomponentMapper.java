/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AdellijkeTitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpGeslachtsnaamcomponentInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonGeslachtsnaamcomponent} inclusief historie.
 * 
 */
public final class PersoonGeslachtsnaamcomponentMapper
        extends
        AbstractHistorieMapperStrategie<BrpGeslachtsnaamcomponentInhoud, PersoonGeslachtsnaamcomponentHistorie, PersoonGeslachtsnaamcomponent> {

    /**
     * Maakt een PersoonGeslachtsnaamcomponentMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonGeslachtsnaamcomponentMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(
            final PersoonGeslachtsnaamcomponentHistorie historie,
            final PersoonGeslachtsnaamcomponent entiteit) {
        entiteit.addPersoonGeslachtsnaamcomponentHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonGeslachtsnaamcomponentHistorie historie,
            final PersoonGeslachtsnaamcomponent entiteit) {
        entiteit.setAdellijkeTitel(historie.getAdellijkeTitel());
        entiteit.setNaam(historie.getNaam());
        entiteit.setPredikaat(historie.getPredikaat());
        entiteit.setScheidingsteken(historie.getScheidingsteken());
        entiteit.setVoorvoegsel(historie.getVoorvoegsel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonGeslachtsnaamcomponentHistorie mapHistorischeGroep(
            final BrpGeslachtsnaamcomponentInhoud groepInhoud) {
        final PersoonGeslachtsnaamcomponentHistorie result = new PersoonGeslachtsnaamcomponentHistorie();
        if (groepInhoud.getAdellijkeTitelCode() != null) {
            result.setAdellijkeTitel(AdellijkeTitel.parseCode(groepInhoud.getAdellijkeTitelCode().getCode()));
        }
        result.setNaam(groepInhoud.getNaam());
        if (groepInhoud.getPredikaatCode() != null) {
            result.setPredikaat(Predikaat.parseCode(groepInhoud.getPredikaatCode().getCode()));
        }
        result.setScheidingsteken(groepInhoud.getScheidingsteken());
        result.setVoorvoegsel(groepInhoud.getVoorvoegsel());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(
            final BrpStapel<BrpGeslachtsnaamcomponentInhoud> brpStapel,
            final PersoonGeslachtsnaamcomponent entiteit) {
        entiteit.setPersoonGeslachtsnaamcomponentStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
        entiteit.setVolgnummer(brpStapel.getMeestRecenteElement().getInhoud().getVolgnummer());
    }
}
