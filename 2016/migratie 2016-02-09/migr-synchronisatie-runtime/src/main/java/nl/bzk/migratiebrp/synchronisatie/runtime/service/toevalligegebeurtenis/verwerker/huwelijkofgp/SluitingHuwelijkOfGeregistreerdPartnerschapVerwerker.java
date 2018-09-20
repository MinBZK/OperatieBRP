/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBASluitingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerRelatieBetrokkenheden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBASluitingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBetrokkenheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePartner;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;

import org.springframework.stereotype.Component;

/**
 * Klasse voor het verwerken van toevallige gebeurtenissen m.b.t. het sluiten van een huwelijk/geregistreerd
 * partnerschap (3/5A).
 */
@Component(value = "sluitingHuwelijkOfGeregistreerdPartnerschapVerwerker")
public final class SluitingHuwelijkOfGeregistreerdPartnerschapVerwerker extends AbstractHuwelijkOfGeregistreerdPartnerschapVerwerker {

    @Inject
    private BrpDalService brpDalService;

    @Override
    protected void maakBrpOpdrachtInhoud(
        final MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding opdracht,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final HandelingGBASluitingHuwelijkGeregistreerdPartnerschap handeling = new HandelingGBASluitingHuwelijkGeregistreerdPartnerschap();
        handeling.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen = maakAdministratieveHandelingBronnen(verzoek);
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));

        final ObjecttypeActie actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschap();
        actie.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();
        actieBron.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        final RelatieSluitingGroepType sluiting = verzoek.getRelatie().getSluiting().getSluiting();

        // RELATIE vullen
        final GroepRelatieRelatie relatie =
                maakHuwelijkOfGeregistreerdPartnerschapRelatie(true, sluiting.getDatum(), sluiting.getPlaats(), sluiting.getLand(), null, null);

        //
        //
        // Betrokkenheden vullen
        //
        //

        final ContainerRelatieBetrokkenheden relatieBetrokkenheden = new ContainerRelatieBetrokkenheden();

        // Partner 1
        relatieBetrokkenheden.getErkennerOrInstemmerOrKind().add(maakBetrokkenheidPartner1(rootPersoon));

        // Zoek gerelateede op anummer;
        final List<Persoon> gerelateerdePersoon =
                brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Long.parseLong(verzoek.getRelatie()
                    .getPersoon()
                    .getIdentificatienummers()
                    .getANummer()));

        // Partner 2
        relatieBetrokkenheden.getErkennerOrInstemmerOrKind().add(maakBetrokkenheidPartner2(verzoek, gerelateerdePersoon));

        // ACTIE vullen

        final BrpSoortRelatieCode soortVerbintenis =
                getConverteerder().converteerLo3SoortVerbintenis(new Lo3SoortVerbintenis(verzoek.getRelatie().getSluiting().getSoort().getSoort().name()));

        vulActie(actie, verzoek.getGeldigheid().getDatumIngang().intValue(), actieBron, relatie, relatieBetrokkenheden, soortVerbintenis);

        // CONTAINER vullen
        final ContainerHandelingActiesGBASluitingHuwelijkGeregistreerdPartnerschap container =
                new ContainerHandelingActiesGBASluitingHuwelijkGeregistreerdPartnerschap();
        container.setRegistratieAanvangHuwelijkGeregistreerdPartnerschap((ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschap) actie);

        // HANDELING vullen
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setActies(OBJECT_FACTORY.createHandelingGBASluitingHuwelijkGeregistreerdPartnerschapActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);

        // UITGAAND BERICHT vullen
        opdracht.setGBASluitingHuwelijkGeregistreerdPartnerschap(OBJECT_FACTORY.createObjecttypeBerichtGBASluitingHuwelijkGeregistreerdPartnerschap(handeling));
    }

    private ObjecttypeBetrokkenheid maakBetrokkenheidPartner2(
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final List<Persoon> gerelateerdePersoon)
    {
        final ObjecttypeBetrokkenheid betrokkenheidPartner2 = new ObjecttypePartner();
        betrokkenheidPartner2.setObjecttype(OBJECT_TYPE_BETROKKENHEID);
        betrokkenheidPartner2.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        final ObjecttypePersoon persoonBetrokkenheid2;
        if (gerelateerdePersoon.isEmpty() || gerelateerdePersoon.size() > 1) {
            // Gerelateerde o.b.v. bericht.
            persoonBetrokkenheid2 = maakPersoon(verzoek.getRelatie().getPersoon());
        } else {
            // Gerelateerde o.b.v. gevonden persoon.
            persoonBetrokkenheid2 = maakPersoon(gerelateerdePersoon.get(0));
        }
        betrokkenheidPartner2.setPersoon(OBJECT_FACTORY.createObjecttypeBetrokkenheidPersoon(persoonBetrokkenheid2));
        return betrokkenheidPartner2;
    }

    private ObjecttypeBetrokkenheid maakBetrokkenheidPartner1(final Persoon rootPersoon) {
        final ObjecttypeBetrokkenheid betrokkenheidPartner1 = new ObjecttypePartner();

        // Partner 1
        final ObjecttypePersoon persoonBetrokkenheid1 = new ObjecttypePersoon();
        persoonBetrokkenheid1.setObjecttype(OBJECT_TYPE_BETROKKENHEID);
        persoonBetrokkenheid1.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        persoonBetrokkenheid1.setObjecttype(OBJECT_TYPE_PERSOON_TECHNISCH_ID);
        persoonBetrokkenheid1.setObjectSleutel(String.valueOf(rootPersoon.getId()));
        betrokkenheidPartner1.setPersoon(OBJECT_FACTORY.createObjecttypeBetrokkenheidPersoon(persoonBetrokkenheid1));
        return betrokkenheidPartner1;
    }
}
