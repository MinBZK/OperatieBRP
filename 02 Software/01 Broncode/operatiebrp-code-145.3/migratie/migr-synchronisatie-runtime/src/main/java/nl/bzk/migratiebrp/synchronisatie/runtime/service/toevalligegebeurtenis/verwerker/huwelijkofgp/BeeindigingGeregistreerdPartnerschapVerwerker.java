/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieEindeGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBABeeindigingGeregistreerdPartnerschapInNederland;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBABeeindigingGeregistreerdPartnerschapInNederland;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtIdentificatieMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.VerbintenisMaker;
import org.springframework.stereotype.Component;

/**
 * Klasse voor het verwerken van toevallige gebeurtenissen m.b.t. het ontbinden van een huwelijk/geregistreerd
 * partnerschap (3/5B).
 */
@Component
public final class BeeindigingGeregistreerdPartnerschapVerwerker implements RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final BerichtMaker berichtMaker;

    private final VerbintenisMaker verbintenisMaker;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken berichten
     */
    @Inject
    public BeeindigingGeregistreerdPartnerschapVerwerker(final BerichtMaker berichtMaker) {
        this.verbintenisMaker = berichtMaker.getVerbintenisMaker();
        this.berichtMaker = berichtMaker;
    }

    @Override
    public void maakBrpOpdrachtInhoud(
            final BerichtIdentificatieMaker idMaker,
            final BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz opdracht,
            final BrpToevalligeGebeurtenis verzoek,
            final BrpPersoonslijst rootPersoon,
            final BrpRelatie relatie) {
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen =
                berichtMaker.maakAdministratieveHandelingBronnen(idMaker, verzoek);
        final ObjecttypeActie actie = new ActieRegistratieEindeGeregistreerdPartnerschap();

        GroepRelatieRelatie vorigeRelatie = verbintenisMaker.maakVorigeRelatie(idMaker, verzoek);

        // ACTIE vullen
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        berichtMaker.vulActie(idMaker, actie, verzoek.getVerbintenis().getOntbinding().getDatum(), actieBron, relatie, vorigeRelatie, null,
                verzoek.getVerbintenis().getSluiting().getRelatieCode());

        // CONTAINER vullen
        final ContainerHandelingActiesGBABeeindigingGeregistreerdPartnerschapInNederland container =
                new ContainerHandelingActiesGBABeeindigingGeregistreerdPartnerschapInNederland();
        container.setRegistratieEindeGeregistreerdPartnerschap((ActieRegistratieEindeGeregistreerdPartnerschap) actie);

        // HANDELING vullen
        final HandelingGBABeeindigingGeregistreerdPartnerschapInNederland handeling = new HandelingGBABeeindigingGeregistreerdPartnerschapInNederland();
        berichtMaker.vulHandeling(idMaker, verzoek, administratieveHandelingBronnen, handeling);
        handeling.setActies(OBJECT_FACTORY.createHandelingGBABeeindigingGeregistreerdPartnerschapInNederlandActies(container));

        // UITGAAND BERICHT vullen
        opdracht.setGBABeeindigingGeregistreerdPartnerschapInNederland(
                OBJECT_FACTORY.createObjecttypeBerichtGBABeeindigingGeregistreerdPartnerschapInNederland(handeling));
    }

}
