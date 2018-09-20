/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.overlijden;

import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieOverlijden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BuitenlandsePlaats;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAOverlijden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GemeenteCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonOverlijdenGBABijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAOverlijden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.LandGebiedCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerOverlijdenBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonOverlijdenGBA;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.AbstractToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Verwerker voor toevallige gebeurtenis m.b.t. het overlijden (2A/G).
 */
@Component(value = "overlijdenVerwerker")
public final class OverlijdenVerwerker extends AbstractToevalligeGebeurtenisVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    protected ObjecttypeBerichtBijhouding maakBrpToevalligeGebeurtenisOpdracht(
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final MigratievoorzieningRegistreerOverlijdenBijhouding opdracht = new MigratievoorzieningRegistreerOverlijdenBijhouding();

        // Zet de bericht stuurgegevens op de opdracht.
        LOG.debug("Zetten stuurgegevens opdracht.");
        opdracht.setStuurgegevens(OBJECT_FACTORY.createObjecttypeBerichtStuurgegevens(maakBerichtStuurgegevens(verzoek)));

        // Zet de bericht parameters op de opdracht.
        LOG.debug("Zetten parameters opdracht.");
        opdracht.setParameters(OBJECT_FACTORY.createObjecttypeBerichtParameters(maakBerichtParameters(rootPersoon)));

        // Zet de inhoud van de opdracht.
        LOG.debug("Zetten inhoud opdracht.");
        maakBrpOpdrachtInhoud(opdracht, verzoek, rootPersoon);

        return opdracht;
    }

    private void maakBrpOpdrachtInhoud(
        final MigratievoorzieningRegistreerOverlijdenBijhouding opdracht,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final OverlijdenGroepType overlijdenPersoon = verzoek.getOverlijden().getOverlijden();

        final HandelingGBAOverlijden handeling = new HandelingGBAOverlijden();
        handeling.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen = maakAdministratieveHandelingBronnen(verzoek);
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));

        final ActieRegistratieOverlijden actie = new ActieRegistratieOverlijden();
        actie.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        // OVERLIJDEN vullen
        final ObjecttypePersoon persoonObject = new ObjecttypePersoonOverlijdenGBA();
        persoonObject.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        persoonObject.setObjectSleutel(String.valueOf(rootPersoon.getId()));
        persoonObject.setObjecttype(OBJECT_TYPE_PERSOON);
        persoonObject.getOverlijden().add(maakGroepPersoonOverlijdenGBABijhouding(overlijdenPersoon));

        // ACTIE vullen
        vulActie(actie, verzoek.getGeldigheid().getDatumIngang().intValue(), actieBron, null, null, null);
        actie.setPersoon(OBJECT_FACTORY.createObjecttypeActiePersoon(persoonObject));

        // CONTAINER vullen
        final ContainerHandelingActiesGBAOverlijden container = new ContainerHandelingActiesGBAOverlijden();
        container.setRegistratieOverlijden(actie);

        // HANDELING vullen
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAOverlijdenActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);

        // UITGAAND BERICHT vullen
        opdracht.setGBAOverlijden(OBJECT_FACTORY.createObjecttypeBerichtGBAOverlijden(handeling));
    }

    private GroepPersoonOverlijdenGBABijhouding maakGroepPersoonOverlijdenGBABijhouding(final OverlijdenGroepType overlijdenPersoon) {
        final GroepPersoonOverlijdenGBABijhouding groep = new GroepPersoonOverlijdenGBABijhouding();

        final DatumMetOnzekerheid datumOverlijden = new DatumMetOnzekerheid();
        datumOverlijden.setValue(String.valueOf(overlijdenPersoon.getDatum()));
        groep.setDatum(OBJECT_FACTORY.createGroepPersoonOverlijdenDatum(datumOverlijden));
        groep.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        if (overlijdenPersoon.getPlaats().length() == LENGTE_GEMEENTE_CODE && StringUtils.isNumeric(overlijdenPersoon.getPlaats())) {
            final GemeenteCode gemeenteOverlijden = new GemeenteCode();
            gemeenteOverlijden.setValue(getConverteerder().converteerLo3GemeenteCode(new Lo3GemeenteCode(overlijdenPersoon.getPlaats()))
                .getFormattedStringCode());
            groep.setGemeenteCode(OBJECT_FACTORY.createGroepPersoonOverlijdenGemeenteCode(gemeenteOverlijden));
        } else {
            final BuitenlandsePlaats buitenlandsePlaatsOverlijden = new BuitenlandsePlaats();
            buitenlandsePlaatsOverlijden.setValue(overlijdenPersoon.getPlaats());
            groep.setBuitenlandsePlaats(OBJECT_FACTORY.createGroepPersoonOverlijdenBuitenlandsePlaats(buitenlandsePlaatsOverlijden));
        }

        final LandGebiedCode landGebiedOverlijden = new LandGebiedCode();
        landGebiedOverlijden.setValue(String.valueOf(getConverteerder().converteerLo3LandCode(new Lo3LandCode(overlijdenPersoon.getLand())).getWaarde()));
        groep.setLandGebiedCode(OBJECT_FACTORY.createGroepPersoonOverlijdenLandGebiedCode(landGebiedOverlijden));

        return groep;

    }
}
