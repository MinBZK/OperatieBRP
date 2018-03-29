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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;

/**
 * Deze mapper mapped de BrpIstRelatieGroepInhoud op Stapel en StapelVoorkomen uit het BRP operationele model.
 */
public class IstRelatieMapper extends AbstractIstMapper<BrpIstRelatieGroepInhoud> {

    /**
     * Maakt een IstMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param administratieveHandeling de administratieve handeling voor de IST stapel
     */
    public IstRelatieMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final AdministratieveHandeling administratieveHandeling) {
        super(dynamischeStamtabelRepository, administratieveHandeling);
    }

    /* mapStapelVoorkomen wordt overriden in de sub class IstHuwelijkOfGpMapper */
    @Override
    protected final StapelVoorkomen mapStapelVoorkomen(final Stapel stapel, final BrpIstRelatieGroepInhoud inhoud) {
        // Identiteit
        final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, inhoud.getVoorkomen(), getAdministratieveHandeling());

        // Standaard
        vulStandaard(voorkomen, inhoud.getStandaardGegevens());

        // Categorie gerelateerden
        vulGerelateerden(voorkomen, inhoud);

        return voorkomen;
    }
}
