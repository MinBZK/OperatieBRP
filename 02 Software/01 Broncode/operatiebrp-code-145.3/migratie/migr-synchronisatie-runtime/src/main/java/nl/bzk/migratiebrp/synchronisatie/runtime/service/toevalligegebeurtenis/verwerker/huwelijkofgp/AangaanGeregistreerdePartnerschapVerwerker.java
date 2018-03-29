/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieAanvangGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAAangaanGeregistreerdPartnerschapInNederland;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerRelatieBetrokkenheden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAAangaanGeregistreerdPartnerschapInNederland;
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
 * Klasse voor het verwerken van toevallige gebeurtenissen m.b.t. het sluiten van een huwelijk/geregistreerd
 * partnerschap (3/5A).
 */
@Component
public final class AangaanGeregistreerdePartnerschapVerwerker implements RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final VerbintenisMaker verbintenisMaker;

    private final BerichtMaker berichtMaker;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken berichten
     */
    @Inject
    public AangaanGeregistreerdePartnerschapVerwerker(final BerichtMaker berichtMaker) {
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

        // ACTIE vullen
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen =
                berichtMaker.maakAdministratieveHandelingBronnen(idMaker, verzoek);
        final ObjecttypeActie actie = new ActieRegistratieAanvangGeregistreerdPartnerschap();
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());

        final GroepRelatieRelatie groepRelatie = verbintenisMaker.maakNieuweRelatie(idMaker, verzoek);

        // Betrokkenheden vullen
        final ContainerRelatieBetrokkenheden relatieBetrokkenheden = new ContainerRelatieBetrokkenheden();

        // Ik
        relatieBetrokkenheden.getKindOrOuderOrPartner().add(verbintenisMaker.maakIkBetrokkenheid(idMaker, rootPersoon));

        // Partner
        relatieBetrokkenheden.getKindOrOuderOrPartner().add(verbintenisMaker.maakBetrokkenheidPartner(idMaker, verzoek));

        // ACTIE vullen
        berichtMaker.vulActie(idMaker, actie, verzoek.getVerbintenis().getSluiting().getDatum(), actieBron, null, groepRelatie, relatieBetrokkenheden,
                verzoek.getVerbintenis().getSluiting().getRelatieCode());

        // HANDELING vullen
        final HandelingGBAAangaanGeregistreerdPartnerschapInNederland handeling = new HandelingGBAAangaanGeregistreerdPartnerschapInNederland();
        berichtMaker.vulHandeling(idMaker, verzoek, administratieveHandelingBronnen, handeling);

        // CONTAINER vullen
        final ContainerHandelingActiesGBAAangaanGeregistreerdPartnerschapInNederland container =
                new ContainerHandelingActiesGBAAangaanGeregistreerdPartnerschapInNederland();
        container.setRegistratieAanvangGeregistreerdPartnerschap((ActieRegistratieAanvangGeregistreerdPartnerschap) actie);

        // HANDELING vullen
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAAangaanGeregistreerdPartnerschapInNederlandActies(container));

        // UITGAAND BERICHT vullen
        opdracht.setGBAAangaanGeregistreerdPartnerschapInNederland(
                OBJECT_FACTORY.createObjecttypeBerichtGBAAangaanGeregistreerdPartnerschapInNederland(handeling));
    }

}
