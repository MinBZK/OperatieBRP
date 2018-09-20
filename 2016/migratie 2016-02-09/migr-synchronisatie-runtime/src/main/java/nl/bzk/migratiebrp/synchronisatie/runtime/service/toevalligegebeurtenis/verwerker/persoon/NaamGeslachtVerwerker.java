/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieNaamGeslacht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAWijzigingNaamGeslacht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAWijzigingNaamGeslacht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerNaamGeslachtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoonWijzigingNaamGeslachtGBABijhouding;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGeslachtType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.AbstractToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Verwerker voor toevallige gebeurtenis m.b.t. de geslachtsnaam (1H).
 */
@Component(value = "naamGeslachtVerwerker")
public final class NaamGeslachtVerwerker extends AbstractToevalligeGebeurtenisVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    @Override
    protected ObjecttypeBerichtBijhouding maakBrpToevalligeGebeurtenisOpdracht(
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final MigratievoorzieningRegistreerNaamGeslachtBijhouding opdracht = new MigratievoorzieningRegistreerNaamGeslachtBijhouding();

        // Zet de bericht stuurgegevens op de opdracht.
        LOG.debug("Zetten stuurgegevens opdracht.");
        opdracht.setStuurgegevens(OBJECT_FACTORY.createObjecttypeBerichtStuurgegevens(maakBerichtStuurgegevens(verzoek)));

        // Zet de bericht parameters op de opdracht.
        LOG.debug("Zetten parameters opdracht.");
        opdracht.setParameters(OBJECT_FACTORY.createObjecttypeBerichtParameters(maakBerichtParameters(rootPersoon)));

        // Zet de inhoud van de opdracht.
        LOG.debug("Zetten inhoud opdracht.");
        maakBrpOpdrachtInhoud(opdracht, verzoek);

        return opdracht;
    }

    private void maakBrpOpdrachtInhoud(
        final MigratievoorzieningRegistreerNaamGeslachtBijhouding opdracht,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek)
    {
        final NaamGeslachtType updatePersoon = verzoek.getUpdatePersoon();

        final HandelingGBAWijzigingNaamGeslacht handeling = new HandelingGBAWijzigingNaamGeslacht();
        handeling.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen = maakAdministratieveHandelingBronnen(verzoek);
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));

        final ActieRegistratieNaamGeslacht actie = new ActieRegistratieNaamGeslacht();
        actie.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        // WIJZIGING NAAM GESLACHT BIJHOUDING vullen
        final ObjecttypePersoon persoonWijzigingNaamGeslacht = new ObjecttypePersoonWijzigingNaamGeslachtGBABijhouding();
        persoonWijzigingNaamGeslacht.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        if (updatePersoon.getGeslacht() != null) {
            final BrpGeslachtsaanduidingCode geslachtsaanduidingVerzoek =
                    converteerder.converteerLo3Geslachtsaanduiding(new Lo3Geslachtsaanduiding(updatePersoon.getGeslacht().getGeslachtsaanduiding().value()));

            persoonWijzigingNaamGeslacht.getGeslachtsaanduiding().add(maakPersoonGeslachtsaanduiding(geslachtsaanduidingVerzoek.getWaarde()));
        }

        if (updatePersoon.getNaam() != null) {
            persoonWijzigingNaamGeslacht.getSamengesteldeNaam().add(maakPersoonSamengesteldeNaam(updatePersoon.getNaam()));
        }

        // ACTIE vullen
        vulActie(actie, verzoek.getGeldigheid().getDatumIngang().intValue(), actieBron, null, null, null);
        actie.setPersoon(OBJECT_FACTORY.createObjecttypeActiePersoon(persoonWijzigingNaamGeslacht));

        // CONTAINER vullen
        final ContainerHandelingActiesGBAWijzigingNaamGeslacht container = new ContainerHandelingActiesGBAWijzigingNaamGeslacht();
        container.setRegistratieNaamGeslacht(actie);

        // HANDELING vullen
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAWijzigingNaamGeslachtActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);
        opdracht.setGBAWijzigingNaamGeslacht(OBJECT_FACTORY.createObjecttypeBerichtGBAWijzigingNaamGeslacht(handeling));
    }
}
