/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;

/**
 * Deze mapper mapped de {@link BrpIstGezagsVerhoudingGroepInhoud} op Stapel en StapelVoorkomen uit het BRP operationele
 * model.
 */
public class IstGezagsverhoudingMapper extends AbstractIstMapper<BrpIstGezagsVerhoudingGroepInhoud> {

    /**
     * Maakt een IstMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param administratieveHandeling de administratieve handeling voor de IST stapel
     */
    public IstGezagsverhoudingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final AdministratieveHandeling administratieveHandeling) {
        super(dynamischeStamtabelRepository, administratieveHandeling);
    }

    @Override
    protected final StapelVoorkomen mapStapelVoorkomen(final Stapel stapel, final BrpIstGezagsVerhoudingGroepInhoud inhoud) {
        // Identiteit
        final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, inhoud.getVoorkomen(), getAdministratieveHandeling());

        // Standaard
        vulStandaard(voorkomen, inhoud.getStandaardGegevens());

        // Categorie gezagsverhouding
        vulGezagsverhouding(voorkomen, inhoud);

        return voorkomen;
    }

    private void vulGezagsverhouding(final StapelVoorkomen voorkomen, final BrpIstGezagsVerhoudingGroepInhoud inhoud) {
        voorkomen.setIndicatieOuder1HeeftGezag(BrpBoolean.unwrap(inhoud.getIndicatieOuder1HeeftGezag()));
        voorkomen.setIndicatieOuder2HeeftGezag(BrpBoolean.unwrap(inhoud.getIndicatieOuder2HeeftGezag()));
        voorkomen.setIndicatieDerdeHeeftGezag(BrpBoolean.unwrap(inhoud.getIndicatieDerdeHeeftGezag()));
        voorkomen.setIndicatieOnderCuratele(BrpBoolean.unwrap(inhoud.getIndicatieOnderCuratele()));
    }
}
