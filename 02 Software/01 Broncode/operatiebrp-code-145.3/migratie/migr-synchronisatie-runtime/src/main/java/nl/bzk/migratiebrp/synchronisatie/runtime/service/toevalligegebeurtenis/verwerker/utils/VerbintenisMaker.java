/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBetrokkenheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePartner;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisOntbinding;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import org.springframework.stereotype.Component;

/**
 * Voor het maken van specifieke acties tbv verbintenissen.
 */
@Component
public class VerbintenisMaker {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private static final String OBJECT_TYPE_BETROKKENHEID = "Betrokkenheid";
    private static final String OBJECT_TYPE_PERSOON = "Persoon";

    private final RelatieMaker relatieMaker;

    private final PersoonMaker persoonMaker;

    private final ObjectSleutelService objectSleutelService;

    /**
     * Constructor.
     * @param relatieMaker utility om een relatie te maken
     * @param persoonMaker utility om een persoon te maken
     * @param objectSleutelService service voor een objectsleutel
     */
    @Inject
    public VerbintenisMaker(final RelatieMaker relatieMaker,
                            final PersoonMaker persoonMaker,
                            final ObjectSleutelService objectSleutelService) {
        this.relatieMaker = relatieMaker;
        this.persoonMaker = persoonMaker;
        this.objectSleutelService = objectSleutelService;
    }

    /**
     * Maak nieuwe relatie aan.
     * @param idMaker identificatieMaker
     * @param verzoek het orginele verzoek
     * @return de nieweu relatie
     */
    public final GroepRelatieRelatie maakNieuweRelatie(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenis verzoek) {

        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting = verzoek.getVerbintenis().getSluiting();

        // RELATIE vullen
        return relatieMaker.maakHuwelijkOfGeregistreerdPartnerschapRelatie(
                idMaker.volgendIdentificatieId(),
                true,
                sluiting.getDatum(),
                sluiting.getGemeenteCode(),
                sluiting.getBuitenlandsePlaats(),
                sluiting.getOmschrijvingLocatie(),
                null
        );
    }

    /**
     * Maak vorige relatie aan.
     * @param idMaker identificatieMaker
     * @param verzoek het orginele verzoek
     * @return GroepRelatieRelatie
     */
    public final GroepRelatieRelatie maakVorigeRelatie(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenis verzoek) {
        final BrpToevalligeGebeurtenisVerbintenisOntbinding ontbinding = verzoek.getVerbintenis().getOntbinding();
        // RELATIE vullen
        return relatieMaker.maakHuwelijkOfGeregistreerdPartnerschapRelatie(
                idMaker.volgendIdentificatieId(),
                false,
                ontbinding.getDatum(),
                ontbinding.getGemeenteCode(),
                ontbinding.getBuitenlandsePlaats(),
                null,
                ontbinding.getRedenEindeRelatieCode());
    }

    /**
     * Maak betrokkenheid voor partner.
     * @param idMaker utility voor id's
     * @param verzoek verzoek waarvoor betrokkenheid wordt gemaakt
     * @return partner betrokkenheid
     */
    public ObjecttypeBetrokkenheid maakBetrokkenheidPartner(final BerichtIdentificatieMaker idMaker, final BrpToevalligeGebeurtenis verzoek) {
        final ObjecttypeBetrokkenheid betrokkenheidPartner2 = new ObjecttypePartner();
        betrokkenheidPartner2.setObjecttype(OBJECT_TYPE_BETROKKENHEID);
        betrokkenheidPartner2.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ObjecttypePersoon persoonBetrokkenheid2;
        persoonBetrokkenheid2 = persoonMaker.maakPseudoPersoon(idMaker, verzoek.getVerbintenis().getPartner());
        betrokkenheidPartner2.setPersoon(OBJECT_FACTORY.createObjecttypeBetrokkenheidPersoon(persoonBetrokkenheid2));
        return betrokkenheidPartner2;
    }

    /**
     * Maak betrokkenheid voor Ik.
     * @param idMaker utility voor id's
     * @param rootPersoon de ik persoon
     * @return ik betrokkenheid
     */
    public ObjecttypeBetrokkenheid maakIkBetrokkenheid(
            final BerichtIdentificatieMaker idMaker,
            final BrpPersoonslijst rootPersoon) {
        final ObjecttypeBetrokkenheid betrokkenheidPartner1 = new ObjecttypePartner();
        betrokkenheidPartner1.setObjecttype(OBJECT_TYPE_BETROKKENHEID);
        betrokkenheidPartner1.setCommunicatieID(idMaker.volgendIdentificatieId());

        // Partner 1
        final ObjecttypePersoon persoonBetrokkenheid1 = new ObjecttypePersoon();
        persoonBetrokkenheid1.setObjecttype(OBJECT_TYPE_BETROKKENHEID);
        persoonBetrokkenheid1.setCommunicatieID(idMaker.volgendIdentificatieId());
        persoonBetrokkenheid1.setObjecttype(OBJECT_TYPE_PERSOON);
        final String gemaskeerdeObjectSleutel =
                objectSleutelService.maakPersoonObjectSleutel(
                        rootPersoon.getPersoonId(),
                        rootPersoon.getPersoonVersie()).maskeren();
        persoonBetrokkenheid1.setObjectSleutel(gemaskeerdeObjectSleutel);
        betrokkenheidPartner1.setPersoon(OBJECT_FACTORY.createObjecttypeBetrokkenheidPersoon(persoonBetrokkenheid1));
        return betrokkenheidPartner1;
    }
}
