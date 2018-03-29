/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.overlijden;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieOverlijden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerOverlijdenMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAOverlijdenInNederland;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GemeenteCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonOverlijdenGBAMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAOverlijdenInNederland;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonGBAOverlijdenInNederlandMigVrz;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.RegistreerOverlijdenBijhoudingVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.AttribuutMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtIdentificatieMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import org.springframework.stereotype.Component;

/**
 * Verwerker voor toevallige gebeurtenis m.b.t. het overlijden (2A/G).
 */
@Component
public final class OverlijdenVerwerker implements RegistreerOverlijdenBijhoudingVerwerker {

    private static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";
    private static final String OBJECT_TYPE_PERSOON = "Persoon";
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final BerichtMaker berichtMaker;

    private final ObjectSleutelService objectSleutelService;

    private final AttribuutMaker attribuutMaker;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken berichten
     */
    @Inject
    public OverlijdenVerwerker(final BerichtMaker berichtMaker) {
        this.berichtMaker = berichtMaker;
        this.objectSleutelService = berichtMaker.getObjectSleutelService();
        this.attribuutMaker = berichtMaker.getAttribuutMaker();
    }


    @Override
    public void maakBrpOpdrachtInhoud(
            final BerichtIdentificatieMaker idMaker,
            final BijhoudingRegistreerOverlijdenMigVrz opdracht,
            final BrpToevalligeGebeurtenis verzoek,
            final BrpPersoonslijst rootPersoon) {
        final BrpToevalligeGebeurtenisOverlijden overlijdenPersoon = verzoek.getOverlijden();

        final HandelingGBAOverlijdenInNederland handeling = new HandelingGBAOverlijdenInNederland();
        handeling.setCommunicatieID(idMaker.volgendIdentificatieId());

        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(berichtMaker.maakHandelingRegisterGemeente(verzoek)));
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen =
                berichtMaker.maakAdministratieveHandelingBronnen(idMaker, verzoek);
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));

        final ActieRegistratieOverlijden actie = new ActieRegistratieOverlijden();
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());

        // OVERLIJDEN vullen
        final ObjecttypePersoon persoonObject = new ObjecttypePersoonGBAOverlijdenInNederlandMigVrz();
        persoonObject.setCommunicatieID(idMaker.volgendIdentificatieId());

        final String gemaskeerdeObjectSleutel =
                objectSleutelService.maakPersoonObjectSleutel(
                        rootPersoon.getPersoonId(),
                        rootPersoon.getPersoonVersie()).maskeren();
        persoonObject.setObjectSleutel(gemaskeerdeObjectSleutel);
        persoonObject.setObjecttype(OBJECT_TYPE_PERSOON);
        persoonObject.getOverlijden().add(maakGroepPersoonOverlijdenGBABijhouding(idMaker, overlijdenPersoon));

        // ACTIE vullen
        berichtMaker.vulActie(idMaker, actie, null, actieBron, null, null, null, null);
        actie.setPersoon(OBJECT_FACTORY.createObjecttypeActiePersoon(persoonObject));

        // CONTAINER vullen
        final ContainerHandelingActiesGBAOverlijdenInNederland container = new ContainerHandelingActiesGBAOverlijdenInNederland();
        container.setRegistratieOverlijden(actie);

        // HANDELING vullen
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(berichtMaker.maakHandelingRegisterGemeente(verzoek)));
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAOverlijdenInNederlandActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);

        // UITGAAND BERICHT vullen
        opdracht.setGBAOverlijdenInNederland(OBJECT_FACTORY.createObjecttypeBerichtGBAOverlijdenInNederland(handeling));
    }

    private GroepPersoonOverlijdenGBAMigVrz maakGroepPersoonOverlijdenGBABijhouding(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenisOverlijden overlijdenPersoon) {
        final GroepPersoonOverlijdenGBAMigVrz groep = new GroepPersoonOverlijdenGBAMigVrz();

        final DatumMetOnzekerheid datumOverlijden = attribuutMaker.maakDatumMetOnzekerheid(overlijdenPersoon.getDatum());
        groep.setDatum(OBJECT_FACTORY.createGroepPersoonOverlijdenDatum(datumOverlijden));
        groep.setCommunicatieID(idMaker.volgendIdentificatieId());

        if (overlijdenPersoon.getGemeenteCode() != null) {
            final GemeenteCode gemeenteOverlijden = new GemeenteCode();
            gemeenteOverlijden.setValue(overlijdenPersoon.getGemeenteCode().getWaarde());
            groep.setGemeenteCode(OBJECT_FACTORY.createGroepPersoonOverlijdenGemeenteCode(gemeenteOverlijden));
        }

        return groep;

    }
}
