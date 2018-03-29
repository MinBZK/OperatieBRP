/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon;

import java.math.BigInteger;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieNaamGeslacht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAWijzigingGeslachtsaanduiding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerPersoonVoornamen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAWijzigingGeslachtsaanduiding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonGBAWijzigingGeslachtsaanduidingBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonVoornaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Volgnummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Voornaam;
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
public final class GeslachtsaanduidingVerwerker implements RegistreerNaamGeslachtBijhoudingVerwerker {

    private static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";
    private static final String OBJECT_TYPE_PERSOON = "Persoon";
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final BerichtMaker berichtMaker;

    private final PersoonMaker persoonMaker;

    private final ObjectSleutelService objectSleutelService;

    /**
     * Constructor.
     * @param berichtMaker utility voor maken bericht
     */
    @Inject
    public GeslachtsaanduidingVerwerker(final BerichtMaker berichtMaker) {
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
        final BrpToevalligeGebeurtenisNaamGeslacht updatePersoon = verzoek.getGeslachtsaanduiding();

        final HandelingGBAWijzigingGeslachtsaanduiding handeling = new HandelingGBAWijzigingGeslachtsaanduiding();

        handeling.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen =
                berichtMaker.maakAdministratieveHandelingBronnen(idMaker, verzoek);
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));

        final ActieRegistratieNaamGeslacht actie = new ActieRegistratieNaamGeslacht();
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());

        // WIJZIGING NAAM GESLACHT BIJHOUDING vullen
        final ObjecttypePersoon persoonWijzigingGeslachtsaanduiding = new ObjecttypePersoonGBAWijzigingGeslachtsaanduidingBijhouding();
        persoonWijzigingGeslachtsaanduiding.setCommunicatieID(idMaker.volgendIdentificatieId());
        final String gemaskeerdeObjectSleutel =
                objectSleutelService.maakPersoonObjectSleutel(
                        rootPersoon.getPersoonId(),
                        rootPersoon.getPersoonVersie()).maskeren();
        persoonWijzigingGeslachtsaanduiding.setObjectSleutel(gemaskeerdeObjectSleutel);
        persoonWijzigingGeslachtsaanduiding.setObjecttype(OBJECT_TYPE_PERSOON);

        if (updatePersoon.getGeslachtsaanduidingCode() != null) {
            persoonWijzigingGeslachtsaanduiding.getGeslachtsaanduiding()
                    .add(persoonMaker.maakPersoonGeslachtsaanduiding(idMaker, updatePersoon.getGeslachtsaanduidingCode()));
        }

        if (updatePersoon.getGeslachtsnaamstam() != null) {
            persoonWijzigingGeslachtsaanduiding.getSamengesteldeNaam().add(persoonMaker.maakPersoonSamengesteldeNaam(idMaker, updatePersoon));
        }
        if (updatePersoon.getVoornamen() != null) {
            final ContainerPersoonVoornamen containerPersoonVoornamen = new ContainerPersoonVoornamen();
            long volgnr = 0L;
            for (final String voornaamDeel : updatePersoon.getVoornamen().getWaarde().split(" ")) {
                final ObjecttypePersoonVoornaam objecttypePersoonVoornaam = new ObjecttypePersoonVoornaam();
                final Voornaam voornaam = new Voornaam();
                voornaam.setValue(voornaamDeel);
                objecttypePersoonVoornaam.setNaam(OBJECT_FACTORY.createObjecttypePersoonVoornaamNaam(voornaam));
                objecttypePersoonVoornaam.setObjecttype("PersoonVoornaam");
                objecttypePersoonVoornaam.setCommunicatieID(idMaker.volgendIdentificatieId());
                final Volgnummer volgnummer = new Volgnummer();
                volgnummer.setValue(BigInteger.valueOf(volgnr));
                volgnr++;
                objecttypePersoonVoornaam.setVolgnummer(OBJECT_FACTORY.createObjecttypePersoonVoornaamVolgnummer(volgnummer));
                containerPersoonVoornamen.getVoornaam().add(objecttypePersoonVoornaam);
            }
            persoonWijzigingGeslachtsaanduiding.setVoornamen(OBJECT_FACTORY.createObjecttypePersoonVoornamen(containerPersoonVoornamen));
        }

        // ACTIE vullen
        berichtMaker.vulActie(idMaker, actie, verzoek.getDatumAanvang(), actieBron, null, null, null, null);
        actie.setPersoon(OBJECT_FACTORY.createObjecttypeActiePersoon(persoonWijzigingGeslachtsaanduiding));

        // CONTAINER vullen
        final ContainerHandelingActiesGBAWijzigingGeslachtsaanduiding container = new ContainerHandelingActiesGBAWijzigingGeslachtsaanduiding();
        container.setRegistratieNaamGeslacht(actie);

        // HANDELING vullen
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(berichtMaker.maakHandelingRegisterGemeente(verzoek)));
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAWijzigingGeslachtsaanduidingActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);
        opdracht.setGBAWijzigingGeslachtsaanduiding(OBJECT_FACTORY.createObjecttypeBerichtGBAWijzigingGeslachtsaanduiding(handeling));
    }
}
