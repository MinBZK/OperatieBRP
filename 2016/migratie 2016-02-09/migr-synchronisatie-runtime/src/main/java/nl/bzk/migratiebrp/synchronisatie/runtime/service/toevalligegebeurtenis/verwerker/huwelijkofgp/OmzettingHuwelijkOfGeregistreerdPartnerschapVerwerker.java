/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import nl.bzk.migratiebrp.bericht.model.brp.generated.ActieRegistratieEindeHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerHandelingActiesGBAOmzettingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBAOmzettingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeHuwelijk;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

import org.springframework.stereotype.Component;

/**
 * Klasse voor het verwerken van toevallige gebeurtenissen m.b.t. het omzetten van een huwelijk/geregistreerd
 * partnerschap naar een geregistreerd partnerschap/huwelijk (3/5H).
 */
@Component(value = "omzettingHuwelijkOfGeregistreerdPartnerschapVerwerker")
public final class OmzettingHuwelijkOfGeregistreerdPartnerschapVerwerker extends AbstractHuwelijkOfGeregistreerdPartnerschapVerwerker {

    private static final String REDEN_EINDE_OMZETTING = "Z";

    @Override
    protected void maakBrpOpdrachtInhoud(
        final MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding opdracht,
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon)
    {
        final RelatieSluitingGroepType sluiting = verzoek.getRelatie().getOmzetting().getSluiting();

        // RELATIE vullen
        final GroepRelatieRelatie relatieSluiting =
                maakHuwelijkOfGeregistreerdPartnerschapRelatie(true, sluiting.getDatum(), sluiting.getLand(), sluiting.getPlaats(), null, null);
        final GroepRelatieRelatie relatieVorigeRelatie =
                maakHuwelijkOfGeregistreerdPartnerschapRelatie(
                    false,
                    sluiting.getDatum(),
                    sluiting.getLand(),
                    sluiting.getPlaats(),
                    REDEN_EINDE_OMZETTING,
                    rootPersoon.getRelaties().iterator().next());

        // ACTIE vullen
        final ObjecttypeActie actie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschap();
        final ObjecttypeActieBron actieBron = new ObjecttypeActieBron();

        final BrpSoortRelatieCode soortVerbintenis =
                getConverteerder().converteerLo3SoortVerbintenis(new Lo3SoortVerbintenis(verzoek.getRelatie().getSluiting().getSoort().getSoort().name()));

        // Nieuwe sluiting
        vulActie(actie, verzoek.getGeldigheid().getDatumIngang().intValue(), actieBron, relatieSluiting, null, soortVerbintenis);

        // Beeindiging vorige
        voegVorigeRelatieToeAanActie(relatieVorigeRelatie, actie);

        // CONTAINER vullen
        final ContainerHandelingActiesGBAOmzettingHuwelijkGeregistreerdPartnerschap container =
                new ContainerHandelingActiesGBAOmzettingHuwelijkGeregistreerdPartnerschap();
        container.setRegistratieEindeHuwelijkGeregistreerdPartnerschap((ActieRegistratieEindeHuwelijkGeregistreerdPartnerschap) actie);

        // HANDELING vullen
        final HandelingGBAOmzettingHuwelijkGeregistreerdPartnerschap handeling = new HandelingGBAOmzettingHuwelijkGeregistreerdPartnerschap();

        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen = maakAdministratieveHandelingBronnen(verzoek);
        actieBron.setReferentieID(administratieveHandelingBronnen.getBron().iterator().next().getCommunicatieID());
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));
        handeling.setActies(OBJECT_FACTORY.createHandelingGBAOmzettingHuwelijkGeregistreerdPartnerschapActies(container));
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);

        // UITGAAND BERICHT vullen
        opdracht.setGBAOmzettingHuwelijkGeregistreerdPartnerschap(OBJECT_FACTORY
            .createObjecttypeBerichtGBAOmzettingHuwelijkGeregistreerdPartnerschap(handeling));

    }

    private void voegVorigeRelatieToeAanActie(final GroepRelatieRelatie relatieVorigeRelatie, final ObjecttypeActie actie) {
        if (actie.getHuwelijk() != null) {
            // Huwelijk naar geregistreerd partnerschap
            final ObjecttypeGeregistreerdPartnerschap geregistreerdPartnerschap = new ObjecttypeGeregistreerdPartnerschap();
            geregistreerdPartnerschap.setObjecttype(OBJECT_TYPE_RELATIE);
            geregistreerdPartnerschap.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
            geregistreerdPartnerschap.getRelatie().add(relatieVorigeRelatie);
            actie.setGeregistreerdPartnerschap(OBJECT_FACTORY.createObjecttypeActieGeregistreerdPartnerschap(geregistreerdPartnerschap));
        } else {
            // Geregistreerd partnerschap naar huwelijk
            final ObjecttypeHuwelijk huwelijk = new ObjecttypeHuwelijk();
            huwelijk.setObjecttype(OBJECT_TYPE_RELATIE);
            huwelijk.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
            huwelijk.getRelatie().add(relatieVorigeRelatie);
            actie.setHuwelijk(OBJECT_FACTORY.createObjecttypeActieHuwelijk(huwelijk));
        }
    }
}
