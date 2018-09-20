/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AdellijkeTitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpSamengesteldeNaamInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonSamengesteldeNaamHistorie} en vice versa.
 */
public final class PersoonSamengesteldeNaamMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpSamengesteldeNaamInhoud, PersoonSamengesteldeNaamHistorie> {

    /**
     * Maakt een PersoonSamengesteldeNaamMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonSamengesteldeNaamMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonSamengesteldeNaamHistorie historie, final Persoon persoon) {
        persoon.addPersoonSamengesteldeNaamHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonSamengesteldeNaamHistorie historie,
            final Persoon persoon) {
        persoon.setAdellijkeTitel(historie.getAdellijkeTitel());
        persoon.setPredikaat(historie.getPredikaat());
        persoon.setVoornamen(historie.getVoornamen());
        persoon.setGeslachtsnaam(historie.getGeslachtsnaam());
        persoon.setVoorvoegsel(historie.getVoorvoegsel());
        persoon.setScheidingsteken(historie.getScheidingsteken());
        persoon.setIndicatieAlgoritmischAfgeleid(historie.getIndicatieAlgoritmischAfgeleid());
        persoon.setIndicatieNamenreeksAlsGeslachtsnaam(historie.getIndicatieNamenreeksAlsGeslachtsnaam());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonSamengesteldeNaamHistorie mapHistorischeGroep(final BrpSamengesteldeNaamInhoud groepInhoud) {
        final PersoonSamengesteldeNaamHistorie result = new PersoonSamengesteldeNaamHistorie();

        final BrpAdellijkeTitelCode adellijkeTitelCode = groepInhoud.getAdellijkeTitelCode();
        if (adellijkeTitelCode != null) {
            result.setAdellijkeTitel(AdellijkeTitel.valueOf(adellijkeTitelCode.getCode()));
        }
        final BrpPredikaatCode predikaatCode = groepInhoud.getPredikaatCode();
        if (predikaatCode != null) {
            result.setPredikaat(Predikaat.valueOf(predikaatCode.getCode()));
        }
        result.setVoornamen(groepInhoud.getVoornamen());
        result.setGeslachtsnaam(groepInhoud.getGeslachtsnaam());
        result.setVoorvoegsel(groepInhoud.getVoorvoegsel());
        result.setScheidingsteken(groepInhoud.getScheidingsteken());
        result.setIndicatieAlgoritmischAfgeleid(groepInhoud.getIndicatieAfgeleid());
        result.setIndicatieNamenreeksAlsGeslachtsnaam(groepInhoud.getIndicatieNamenreeks());

        return result;
    }
}
