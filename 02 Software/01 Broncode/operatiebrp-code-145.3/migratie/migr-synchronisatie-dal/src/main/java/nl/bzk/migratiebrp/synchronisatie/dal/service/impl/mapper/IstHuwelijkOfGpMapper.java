/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.StamtabelMapping;

/**
 * Deze mapper mapped de BrpIstRelatieGroepInhoud op Stapel en StapelVoorkomen uit het BRP operationele model.
 */
class IstHuwelijkOfGpMapper extends AbstractIstMapper<BrpIstHuwelijkOfGpGroepInhoud> {

    /**
     * Maakt een IstMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param administratieveHandeling de administratieve handeling voor de IST stapel
     */
    IstHuwelijkOfGpMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final AdministratieveHandeling administratieveHandeling) {
        super(dynamischeStamtabelRepository, administratieveHandeling);
    }

    @Override
    protected final StapelVoorkomen mapStapelVoorkomen(final Stapel stapel, final BrpIstHuwelijkOfGpGroepInhoud inhoud) {
        // Identiteit
        final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, inhoud.getVoorkomen(), getAdministratieveHandeling());

        // Standaard
        vulStandaard(voorkomen, inhoud.getStandaardGegevens());

        // Categorie gerelateerden
        vulGerelateerden(voorkomen, inhoud.getRelatie());

        // Categorie huwelijk of geregistreerd partnerschap
        vulHuwelijkOfGp(voorkomen, inhoud);

        return voorkomen;
    }

    private void vulHuwelijkOfGp(final StapelVoorkomen voorkomen, final BrpIstHuwelijkOfGpGroepInhoud inhoud) {
        final StamtabelMapping stamtabelMapping = getStamtabelMapping();

        voorkomen.setDatumAanvang(BrpInteger.unwrap(inhoud.getDatumAanvang()));
        voorkomen.setGemeenteAanvang(stamtabelMapping.findGemeenteByCode(inhoud.getGemeenteCodeAanvang()));
        voorkomen.setBuitenlandsePlaatsAanvang(BrpString.unwrap(inhoud.getBuitenlandsePlaatsAanvang()));
        voorkomen.setOmschrijvingLocatieAanvang(BrpString.unwrap(inhoud.getOmschrijvingLocatieAanvang()));
        voorkomen.setLandOfGebiedAanvang(stamtabelMapping.findLandOfGebiedByCode(inhoud.getLandOfGebiedCodeAanvang()));
        voorkomen.setRedenBeeindigingRelatie(stamtabelMapping.findRedenBeeindigingRelatieByCode(inhoud.getRedenEindeRelatieCode()));
        voorkomen.setDatumEinde(BrpInteger.unwrap(inhoud.getDatumEinde()));
        voorkomen.setGemeenteEinde(stamtabelMapping.findGemeenteByCode(inhoud.getGemeenteCodeEinde()));
        voorkomen.setBuitenlandsePlaatsEinde(BrpString.unwrap(inhoud.getBuitenlandsePlaatsEinde()));
        voorkomen.setOmschrijvingLocatieEinde(BrpString.unwrap(inhoud.getOmschrijvingLocatieEinde()));
        voorkomen.setLandOfGebiedEinde(stamtabelMapping.findLandOfGebiedByCode(inhoud.getLandOfGebiedCodeEinde()));
        final BrpSoortRelatieCode soortRelatie = inhoud.getSoortRelatieCode();
        if (soortRelatie != null) {
            voorkomen.setSoortRelatie(SoortRelatie.parseCode(soortRelatie.getWaarde()));
        } else {
            voorkomen.setSoortRelatie(null);
        }
    }
}
