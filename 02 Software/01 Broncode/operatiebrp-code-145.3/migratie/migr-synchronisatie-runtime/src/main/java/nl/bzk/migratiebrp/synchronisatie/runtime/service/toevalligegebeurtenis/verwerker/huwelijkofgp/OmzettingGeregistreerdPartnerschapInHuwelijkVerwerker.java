/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieEindeGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerActieBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAOmzettingGeregistreerdPartnerschapInHuwelijk;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAOmzettingGeregistreerdPartnerschapInHuwelijk;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeHuwelijk;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.AttribuutMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtIdentificatieMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.RelatieMaker;
import org.springframework.stereotype.Component;

/**
 * Klasse voor het verwerken van toevallige gebeurtenissen m.b.t. het omzetten van een huwelijk/geregistreerd
 * partnerschap naar een geregistreerd partnerschap/huwelijk (3/5H).
 */
@Component
public final class OmzettingGeregistreerdPartnerschapInHuwelijkVerwerker implements RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker {

    private static final String OBJECT_TYPE_ACTIE = "Actie";
    private static final String OBJECT_TYPE_ACTIE_BRON = "ActieBron";
    private static final BrpRedenEindeRelatieCode REDEN_EINDE_OMZETTING = BrpRedenEindeRelatieCode.OMZETTING;
    private static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";
    private static final String OBJECT_TYPE_RELATIE = "Relatie";
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private BerichtMaker berichtMaker;

    private RelatieMaker relatieMaker;

    private AttribuutMaker attribuutMaker;

    private ObjectSleutelService objectSleutelService;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken berichten
     */
    @Inject
    public OmzettingGeregistreerdPartnerschapInHuwelijkVerwerker(final BerichtMaker berichtMaker) {
        this.berichtMaker = berichtMaker;
        this.relatieMaker = berichtMaker.getRelatieMaker();
        this.objectSleutelService = berichtMaker.getObjectSleutelService();
        this.attribuutMaker = berichtMaker.getAttribuutMaker();
    }

    @Override
    public void maakBrpOpdrachtInhoud(
            final BerichtIdentificatieMaker idMaker,
            final BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz opdracht,
            final BrpToevalligeGebeurtenis verzoek,
            final BrpPersoonslijst rootPersoon,
            final BrpRelatie relatie) {
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting = verzoek.getVerbintenis().getOmzetting();

        // RELATIE vullen
        final GroepRelatieRelatie relatieVorigeRelatie =
                relatieMaker.maakHuwelijkOfGeregistreerdPartnerschapRelatie(
                        idMaker.volgendIdentificatieId(),
                        false,
                        sluiting.getDatum(),
                        sluiting.getGemeenteCode(),
                        sluiting.getBuitenlandsePlaats(),
                        sluiting.getOmschrijvingLocatie(),
                        REDEN_EINDE_OMZETTING
                );

        // ACTIE vullen
        final ObjecttypeActie actie = new ActieRegistratieEindeGeregistreerdPartnerschap();
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();

        actie.setObjecttype(OBJECT_TYPE_ACTIE);
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());

        actieBron.setObjecttype(OBJECT_TYPE_ACTIE_BRON);
        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());

        final ContainerActieBronnen actieBronnen = new ContainerActieBronnen();
        actieBronnen.getBron().add(actieBron);
        actie.setBronnen(OBJECT_FACTORY.createObjecttypeActieBronnen(actieBronnen));

        // datum aanvang geldigheid
        final DatumMetOnzekerheid datumAanvangGeldigheid = attribuutMaker.maakDatumMetOnzekerheid(sluiting.getDatum());
        actie.setDatumAanvangGeldigheid(OBJECT_FACTORY.createObjecttypeActieDatumAanvangGeldigheid(datumAanvangGeldigheid));

        // Beeindiging vorige
        voegVorigeRelatieToeAanActie(idMaker, relatie, relatieVorigeRelatie, actie, relatie.getSoortRelatieCode().getWaarde());

        // CONTAINER vullen
        final ContainerHandelingActiesGBAOmzettingGeregistreerdPartnerschapInHuwelijk container =
                new ContainerHandelingActiesGBAOmzettingGeregistreerdPartnerschapInHuwelijk();
        container.setRegistratieEindeGeregistreerdPartnerschap((ActieRegistratieEindeGeregistreerdPartnerschap) actie);

        // HANDELING vullen
        final HandelingGBAOmzettingGeregistreerdPartnerschapInHuwelijk handeling = new HandelingGBAOmzettingGeregistreerdPartnerschapInHuwelijk();

        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(berichtMaker.maakHandelingRegisterGemeente(verzoek)));
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen =
                berichtMaker.maakAdministratieveHandelingBronnen(idMaker, verzoek);
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAOmzettingGeregistreerdPartnerschapInHuwelijkActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);
        handeling.setCommunicatieID(idMaker.volgendIdentificatieId());

        // UITGAAND BERICHT vullen
        opdracht.setGBAOmzettingGeregistreerdPartnerschapInHuwelijk(
                OBJECT_FACTORY.createObjecttypeBerichtGBAOmzettingGeregistreerdPartnerschapInHuwelijk(handeling));

    }

    private void voegVorigeRelatieToeAanActie(
            final BerichtIdentificatieMaker idMaker,
            final BrpRelatie brpRelatie,
            final GroepRelatieRelatie relatieVorigeRelatie,
            final ObjecttypeActie actie,
            final String soortRelatie) {
        if (BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.getWaarde().equals(soortRelatie)) {
            // Huwelijk naar geregistreerd partnerschap
            final ObjecttypeGeregistreerdPartnerschap geregistreerdPartnerschap = new ObjecttypeGeregistreerdPartnerschap();
            geregistreerdPartnerschap.setObjecttype(OBJECT_TYPE_RELATIE);
            geregistreerdPartnerschap.setCommunicatieID(idMaker.volgendIdentificatieId());
            if (brpRelatie != null) {
                geregistreerdPartnerschap.setObjectSleutel(objectSleutelService.maakRelatieObjectSleutel(brpRelatie.getRelatieId()).maskeren());
            }
            geregistreerdPartnerschap.getRelatie().add(relatieVorigeRelatie);
            actie.setGeregistreerdPartnerschap(OBJECT_FACTORY.createObjecttypeActieGeregistreerdPartnerschap(geregistreerdPartnerschap));
        } else {
            // Geregistreerd partnerschap naar huwelijk
            final ObjecttypeHuwelijk huwelijk = new ObjecttypeHuwelijk();
            huwelijk.setObjecttype(OBJECT_TYPE_RELATIE);
            huwelijk.setCommunicatieID(idMaker.volgendIdentificatieId());
            if (brpRelatie != null) {
                huwelijk.setObjectSleutel(objectSleutelService.maakRelatieObjectSleutel(brpRelatie.getRelatieId()).maskeren());
            }
            huwelijk.getRelatie().add(relatieVorigeRelatie);
            actie.setHuwelijk(OBJECT_FACTORY.createObjecttypeActieHuwelijk(huwelijk));
        }
    }
}
