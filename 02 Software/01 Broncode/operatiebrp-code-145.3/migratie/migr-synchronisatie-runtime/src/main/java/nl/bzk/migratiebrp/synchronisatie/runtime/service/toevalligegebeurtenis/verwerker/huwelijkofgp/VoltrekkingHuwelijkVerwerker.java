/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieAanvangHuwelijk;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAVoltrekkingHuwelijkInNederland;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerRelatieBetrokkenheden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAVoltrekkingHuwelijkInNederland;
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
public final class VoltrekkingHuwelijkVerwerker implements RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final VerbintenisMaker verbintenisMaker;

    private final BerichtMaker berichtMaker;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken bericht
     */
    @Inject
    public VoltrekkingHuwelijkVerwerker(final BerichtMaker berichtMaker) {
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

        final ObjecttypeActie actie = new ActieRegistratieAanvangHuwelijk();
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());

        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());

        GroepRelatieRelatie groepRelatie = verbintenisMaker.maakNieuweRelatie(idMaker, verzoek);

        // Betrokkenheden vullen
        final ContainerRelatieBetrokkenheden relatieBetrokkenheden = new ContainerRelatieBetrokkenheden();

        // Ik
        relatieBetrokkenheden.getKindOrOuderOrPartner().add(verbintenisMaker.maakIkBetrokkenheid(idMaker, rootPersoon));

        // Partner
        relatieBetrokkenheden.getKindOrOuderOrPartner().add(verbintenisMaker.maakBetrokkenheidPartner(idMaker, verzoek));

        // ACTIE vullen
        berichtMaker.vulActie(idMaker, actie, verzoek.getVerbintenis().getSluiting().getDatum(), actieBron, null, groepRelatie, relatieBetrokkenheden,
                verzoek.getVerbintenis().getSluiting().getRelatieCode());

        final HandelingGBAVoltrekkingHuwelijkInNederland handeling = new HandelingGBAVoltrekkingHuwelijkInNederland();
        berichtMaker.vulHandeling(idMaker, verzoek, administratieveHandelingBronnen, handeling);

        // CONTAINER vullen
        final ContainerHandelingActiesGBAVoltrekkingHuwelijkInNederland container = new ContainerHandelingActiesGBAVoltrekkingHuwelijkInNederland();
        container.setRegistratieAanvangHuwelijk((ActieRegistratieAanvangHuwelijk) actie);

        // HANDELING vullen
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAVoltrekkingHuwelijkInNederlandActies(container));

        // UITGAAND BERICHT vullen
        opdracht.setGBAVoltrekkingHuwelijkInNederland(OBJECT_FACTORY.createObjecttypeBerichtGBAVoltrekkingHuwelijkInNederland(handeling));
    }
}
