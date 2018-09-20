/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieEindeHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieOntbindingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

import org.springframework.stereotype.Component;

/**
 * Klasse voor het verwerken van toevallige gebeurtenissen m.b.t. het ontbinden van een huwelijk/geregistreerd
 * partnerschap (3/5B).
 */
@Component("ontbindingHuwelijkOfGeregistreerdPartnerschapVerwerker")
public final class OntbindingHuwelijkOfGeregistreerdPartnerschapVerwerker extends AbstractHuwelijkOfGeregistreerdPartnerschapVerwerker {

    @Override
    protected void maakBrpOpdrachtInhoud(
        final MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding opdracht,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final RelatieOntbindingGroepType ontbinding = verzoek.getRelatie().getOntbinding().getOntbinding();

        // RELATIE vullen
        final GroepRelatieRelatie relatie =
                maakHuwelijkOfGeregistreerdPartnerschapRelatie(
                    false,
                    ontbinding.getDatum(),
                    ontbinding.getPlaats(),
                    ontbinding.getLand(),
                    ontbinding.getReden(),
                    rootPersoon.getRelaties().iterator().next());

        // ACTIE vullen
        final ObjecttypeActie actie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschap();
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();

        final BrpSoortRelatieCode soortVerbintenis =
                getConverteerder().converteerLo3SoortVerbintenis(new Lo3SoortVerbintenis(verzoek.getRelatie().getSluiting().getSoort().getSoort().name()));

        vulActie(actie, verzoek.getGeldigheid().getDatumIngang().intValue(), actieBron, relatie, null, soortVerbintenis);

        // CONTAINER vullen
        final ContainerHandelingActiesGBAOntbindingHuwelijkGeregistreerdPartnerschap container =
                new ContainerHandelingActiesGBAOntbindingHuwelijkGeregistreerdPartnerschap();
        container.setRegistratieEindeHuwelijkGeregistreerdPartnerschap((ActieRegistratieEindeHuwelijkGeregistreerdPartnerschap) actie);

        // HANDELING vullen
        final HandelingGBAOntbindingHuwelijkGeregistreerdPartnerschap handeling = new HandelingGBAOntbindingHuwelijkGeregistreerdPartnerschap();

        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen = maakAdministratieveHandelingBronnen(verzoek);
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAOntbindingHuwelijkGeregistreerdPartnerschapActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);

        // UITGAAND BERICHT vullen
        opdracht.setGBAOntbindingHuwelijkGeregistreerdPartnerschap(OBJECT_FACTORY
            .createObjecttypeBerichtGBAOntbindingHuwelijkGeregistreerdPartnerschap(handeling));

    }
}
