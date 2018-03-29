/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieGeslachtsnaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.AdellijkeTitelCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.AdellijkeTitelCodeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAWijzigingGeslachtsnaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerPersoonGeslachtsnaamcomponentenGBAMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Geslachtsnaamstam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAWijzigingGeslachtsnaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonGBAWijzigingGeslachtsnaamMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PredicaatCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PredicaatCodeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Scheidingsteken;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Voorvoegsel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.RegistreerNaamGeslachtBijhoudingVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtIdentificatieMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.PersoonMaker;
import org.springframework.stereotype.Component;

/**
 * Verwerker voor toevallige gebeurtenis m.b.t. de geslachtsnaam (1H).
 */
@Component
public final class GeslachtsnaamVerwerker implements RegistreerNaamGeslachtBijhoudingVerwerker {

    private static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";
    private static final String OBJECT_TYPE_PERSOON = "Persoon";
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private BerichtMaker berichtMaker;

    private PersoonMaker persoonMaker;

    private ObjectSleutelService objectSleutelService;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken berichten
     */
    @Inject
    public GeslachtsnaamVerwerker(final BerichtMaker berichtMaker) {
        this.berichtMaker = berichtMaker;
        this.persoonMaker = berichtMaker.getPersoonMaker();
        this.objectSleutelService = berichtMaker.getObjectSleutelService();
    }

    @Override
    public void maakBrpOpdrachtInhoud(
            final BerichtIdentificatieMaker idMaker,
            final BijhoudingRegistreerNaamGeslachtMigVrz opdracht,
            final BrpToevalligeGebeurtenis verzoek,
            final BrpPersoonslijst rootPersoon) {
        final BrpToevalligeGebeurtenisNaamGeslacht updatePersoon = verzoek.getGeslachtsnaam();

        final HandelingGBAWijzigingGeslachtsnaam handeling = new HandelingGBAWijzigingGeslachtsnaam();
        handeling.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen =
                berichtMaker.maakAdministratieveHandelingBronnen(idMaker, verzoek);
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));

        final ActieRegistratieGeslachtsnaam actie = new ActieRegistratieGeslachtsnaam();
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());

        // WIJZIGING NAAM GESLACHT BIJHOUDING vullen
        final ObjecttypePersoon persoonWijzigingNaamGeslacht = new ObjecttypePersoonGBAWijzigingGeslachtsnaamMigVrz();
        persoonWijzigingNaamGeslacht.setCommunicatieID(idMaker.volgendIdentificatieId());
        final String gemaskeerdeObjectSleutel =
                objectSleutelService.maakPersoonObjectSleutel(
                        rootPersoon.getPersoonId(),
                        rootPersoon.getPersoonVersie()).maskeren();
        persoonWijzigingNaamGeslacht.setObjectSleutel(gemaskeerdeObjectSleutel);
        persoonWijzigingNaamGeslacht.setObjecttype(OBJECT_TYPE_PERSOON);

        if (updatePersoon.getGeslachtsnaamstam() != null) {
            persoonWijzigingNaamGeslacht.getSamengesteldeNaam().add(persoonMaker.maakPersoonSamengesteldeNaam(idMaker, updatePersoon));
            final ContainerPersoonGeslachtsnaamcomponentenGBAMigVrz containerPersoonGeslachtsnaamcomponentenGBABijhouding =
                    new ContainerPersoonGeslachtsnaamcomponentenGBAMigVrz();
            final ObjecttypePersoonGeslachtsnaamcomponent objecttypePersoonGeslachtsnaamcomponent = new ObjecttypePersoonGeslachtsnaamcomponent();
            if (updatePersoon.getAdellijkeTitelCode() != null) {
                final AdellijkeTitelCode adellijkeTitelCode = new AdellijkeTitelCode();
                adellijkeTitelCode.setValue(AdellijkeTitelCodeS.valueOf(updatePersoon.getAdellijkeTitelCode().getWaarde()));
                objecttypePersoonGeslachtsnaamcomponent.setAdellijkeTitelCode(
                        OBJECT_FACTORY.createObjecttypePersoonGeslachtsnaamcomponentAdellijkeTitelCode(adellijkeTitelCode));
            }
            if (updatePersoon.getGeslachtsnaamstam() != null) {
                final Geslachtsnaamstam geslachtsnaamstam = new Geslachtsnaamstam();
                geslachtsnaamstam.setValue(updatePersoon.getGeslachtsnaamstam().getWaarde());
                objecttypePersoonGeslachtsnaamcomponent.setStam(OBJECT_FACTORY.createObjecttypePersoonGeslachtsnaamcomponentStam(geslachtsnaamstam));
            }
            if (updatePersoon.getPredicaatCode() != null) {
                final PredicaatCode predicaatCode = new PredicaatCode();
                predicaatCode.setValue(PredicaatCodeS.valueOf(updatePersoon.getPredicaatCode().getWaarde()));
                objecttypePersoonGeslachtsnaamcomponent.setPredicaatCode(
                        OBJECT_FACTORY.createObjecttypePersoonGeslachtsnaamcomponentPredicaatCode(predicaatCode));
            }
            if (updatePersoon.getScheidingsteken() != null) {
                final Scheidingsteken scheidingsteken = new Scheidingsteken();
                scheidingsteken.setValue(String.valueOf(updatePersoon.getScheidingsteken().getWaarde()));
                objecttypePersoonGeslachtsnaamcomponent.setScheidingsteken(
                        OBJECT_FACTORY.createObjecttypePersoonGeslachtsnaamcomponentScheidingsteken(scheidingsteken));
            }
            if (updatePersoon.getVoorvoegsel() != null) {
                final Voorvoegsel voorvoegsel = new Voorvoegsel();
                voorvoegsel.setValue(updatePersoon.getVoorvoegsel().getWaarde());
                objecttypePersoonGeslachtsnaamcomponent.setVoorvoegsel(
                        OBJECT_FACTORY.createObjecttypePersoonGeslachtsnaamcomponentVoorvoegsel(voorvoegsel));
            }
            objecttypePersoonGeslachtsnaamcomponent.setObjecttype("PersoonGeslachtsnaamcomponent");
            objecttypePersoonGeslachtsnaamcomponent.setCommunicatieID(idMaker.volgendIdentificatieId());
            containerPersoonGeslachtsnaamcomponentenGBABijhouding.getGeslachtsnaamcomponent().add(objecttypePersoonGeslachtsnaamcomponent);
            persoonWijzigingNaamGeslacht.setGeslachtsnaamcomponenten(
                    OBJECT_FACTORY.createObjecttypePersoonGeslachtsnaamcomponenten(containerPersoonGeslachtsnaamcomponentenGBABijhouding));
        }

        // ACTIE vullen
        berichtMaker.vulActie(idMaker, actie, verzoek.getDatumAanvang(), actieBron, null, null, null, null);
        actie.setPersoon(OBJECT_FACTORY.createObjecttypeActiePersoon(persoonWijzigingNaamGeslacht));

        // CONTAINER vullen
        final ContainerHandelingActiesGBAWijzigingGeslachtsnaam container = new ContainerHandelingActiesGBAWijzigingGeslachtsnaam();
        container.setRegistratieGeslachtsnaam(actie);

        // HANDELING vullen
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(berichtMaker.maakHandelingRegisterGemeente(verzoek)));
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAWijzigingGeslachtsnaamActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);
        opdracht.setGBAWijzigingGeslachtsnaam(OBJECT_FACTORY.createObjecttypeBerichtGBAWijzigingGeslachtsnaam(handeling));
    }
}
