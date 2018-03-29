/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerOverlijdenMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtIdentificatieMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import org.springframework.stereotype.Component;

/**
 * Interface voor het verwerken van toevallige gebeurtenis verzoeken.
 */
@Component
public final class ToevalligeGebeurtenisVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private final BerichtMaker berichtMaker;

    /**
     * Constructor.
     * @param berichtMaker utility om berichten te maken
     */
    @Inject
    public ToevalligeGebeurtenisVerwerker(final BerichtMaker berichtMaker) {
        this.berichtMaker = berichtMaker;
    }

    /**
     * Maakt op basis van een toevallige gebeurtenis verzoek een BRP equivalent opdracht bericht aan.
     * @param verzoek Het binnenkomende verzoek.
     * @param rootPersoon De persoon waar vanuit de bijhouding plaatsvindt.
     * @param verwerker verwerker om correcte bericht te maken
     * @return Het toevalligegebeurtenis bericht welke verstuurd moet worden naar brp
     * @throws IllegalArgumentException in het geval er geen opdracht o.b.v. het verzoek kan worden opgesteld.
     */
    public ObjecttypeBerichtBijhouding verwerkRegistreerNaamGeslachtBijhouding(final BrpToevalligeGebeurtenis verzoek,
                                                                               final BrpPersoonslijst rootPersoon,
                                                                               final RegistreerNaamGeslachtBijhoudingVerwerker verwerker) {
        final BerichtIdentificatieMaker berichtIdentificatieMaker = new BerichtIdentificatieMaker();
        final BijhoudingRegistreerNaamGeslachtMigVrz opdracht = new BijhoudingRegistreerNaamGeslachtMigVrz();
        plaatsAlgemeneStuurgegevens(opdracht, berichtIdentificatieMaker, verzoek);
        verwerker.maakBrpOpdrachtInhoud(berichtIdentificatieMaker, opdracht, verzoek, rootPersoon);
        return opdracht;
    }

    /**
     * Maakt op basis van een toevallige gebeurtenis verzoek een BRP equivalent opdracht bericht aan.
     * @param verzoek Het binnenkomende verzoek.
     * @param rootPersoon De persoon waar vanuit de bijhouding plaatsvindt.
     * @param relatie De te ontbinden relatie
     * @param verwerker verwerker om correcte bericht te maken
     * @return Het toevalligegebeurtenis bericht welke verstuurd moet worden naar brp
     * @throws IllegalArgumentException in het geval er geen opdracht o.b.v. het verzoek kan worden opgesteld.
     */
    public ObjecttypeBerichtBijhouding verwerkRegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
            final BrpToevalligeGebeurtenis verzoek,
            final BrpPersoonslijst rootPersoon,
            final BrpRelatie relatie,
            final RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingVerwerker verwerker) {
        final BerichtIdentificatieMaker berichtIdentificatieMaker = new BerichtIdentificatieMaker();
        final BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz opdracht =
                new BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz();
        plaatsAlgemeneStuurgegevens(opdracht, berichtIdentificatieMaker, verzoek);
        verwerker.maakBrpOpdrachtInhoud(berichtIdentificatieMaker, opdracht, verzoek, rootPersoon, relatie);
        return opdracht;
    }

    /**
     * Maakt op basis van een toevallige gebeurtenis verzoek een BRP equivalent opdracht bericht aan.
     * @param verzoek Het binnenkomende verzoek.
     * @param rootPersoon De persoon waar vanuit de bijhouding plaatsvindt.
     * @param verwerker verwerker om correcte bericht te maken
     * @return Het toevalligegebeurtenis bericht welke verstuurd moet worden naar brp
     * @throws IllegalArgumentException in het geval er geen opdracht o.b.v. het verzoek kan worden opgesteld.
     */
    public ObjecttypeBerichtBijhouding verwerkRegistreerOverlijdenBijhouding(final BrpToevalligeGebeurtenis verzoek,
                                                                             final BrpPersoonslijst rootPersoon,
                                                                             final RegistreerOverlijdenBijhoudingVerwerker verwerker) {
        final BerichtIdentificatieMaker berichtIdentificatieMaker = new BerichtIdentificatieMaker();
        final BijhoudingRegistreerOverlijdenMigVrz opdracht = new BijhoudingRegistreerOverlijdenMigVrz();
        plaatsAlgemeneStuurgegevens(opdracht, berichtIdentificatieMaker, verzoek);
        verwerker.maakBrpOpdrachtInhoud(berichtIdentificatieMaker, opdracht, verzoek, rootPersoon);
        return opdracht;
    }

    private void plaatsAlgemeneStuurgegevens(
            final ObjecttypeBerichtBijhouding bericht,
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenis verzoek) {
        // Zet de bericht stuurgegevens op de opdracht.
        LOG.debug("Zetten stuurgegevens opdracht.");
        bericht.setStuurgegevens(OBJECT_FACTORY.createObjecttypeBerichtStuurgegevens(berichtMaker.maakBerichtStuurgegevens(idMaker, verzoek)));

        // Zet de bericht parameters op de opdracht.
        LOG.debug("Zetten parameters opdracht.");
        bericht.setParameters(OBJECT_FACTORY.createObjecttypeBerichtParameters(berichtMaker.maakBerichtParameters(idMaker)));
    }
}
