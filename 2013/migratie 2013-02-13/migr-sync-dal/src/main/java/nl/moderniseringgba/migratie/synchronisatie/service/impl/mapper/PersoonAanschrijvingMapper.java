/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAanschrijvingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.WijzeGebruikGeslachtsnaam;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpAanschrijvingInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonAanschrijvingHistorie} en vice versa.
 */
public final class PersoonAanschrijvingMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpAanschrijvingInhoud, PersoonAanschrijvingHistorie> {

    /**
     * Maakt een PersoonAanschrijvingMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonAanschrijvingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonAanschrijvingHistorie historie, final Persoon persoon) {
        persoon.addPersoonAanschrijvingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            kopieerActueleGroepNaarEntiteit(final PersoonAanschrijvingHistorie historie, final Persoon persoon) {
        persoon.setGeslachtsnaamAanschrijving(historie.getGeslachtsnaamAanschrijving());
        persoon.setVoornamenAanschrijving(historie.getVoornamenAanschrijving());
        persoon.setScheidingstekenAanschrijving(historie.getScheidingstekenAanschrijving());
        persoon.setVoorvoegselAanschrijving(historie.getVoorvoegselAanschrijving());
        persoon.setIndicatieAanschrijvingAlgoritmischAfgeleid(historie
                .getIndicatieAanschrijvingAlgoritmischAfgeleid());
        persoon.setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(historie
                .getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten());
        persoon.setWijzeGebruikGeslachtsnaam(historie.getWijzeGebruikGeslachtsnaam());
        persoon.setPredikaatAanschrijving(historie.getPredikaat());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonAanschrijvingHistorie mapHistorischeGroep(final BrpAanschrijvingInhoud groepInhoud) {
        final PersoonAanschrijvingHistorie result = new PersoonAanschrijvingHistorie();
        result.setGeslachtsnaamAanschrijving(groepInhoud.getGeslachtsnaam());
        result.setVoornamenAanschrijving(groepInhoud.getVoornamen());
        result.setScheidingstekenAanschrijving(groepInhoud.getScheidingsteken());
        result.setVoorvoegselAanschrijving(groepInhoud.getVoorvoegsel());
        result.setIndicatieAanschrijvingAlgoritmischAfgeleid(groepInhoud.getIndicatieAfgeleid());
        result.setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(groepInhoud
                .getIndicatieAanschrijvenMetTitels());
        if (groepInhoud.getWijzeGebruikGeslachtsnaamCode() != null) {
            result.setWijzeGebruikGeslachtsnaam(WijzeGebruikGeslachtsnaam.parseCode(groepInhoud
                    .getWijzeGebruikGeslachtsnaamCode().name()));
        }
        if (groepInhoud.getPredikaatCode() != null) {
            result.setPredikaat(Predikaat.parseCode(groepInhoud.getPredikaatCode().getCode()));
        }

        return result;
    }
}
