/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import org.springframework.stereotype.Service;

/**
 * VrijBerichtVerzoekServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2452)
@Bedrijfsregel(Regel.R2453)
public final class VrijBerichtServiceImpl implements VrijBerichtService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijService partijService;
    @Inject
    private VrijBerichtAutorisatieService autorisatieService;
    @Inject
    private PlaatsVerwerkVrijBerichtService plaatsVerwerkVrijBerichtService;
    @Inject
    private MaakVerwerkVrijBerichtService maakVerwerkVrijBerichtService;
    @Inject
    private VrijBerichtBerichtFactory vrijBerichtBerichtFactory;
    @Inject
    private VrijBerichtInhoudControleService vrijBerichtInhoudControleService;
    @Inject
    private BeheerRepository beheerRepository;

    private VrijBerichtServiceImpl() {
    }

    @Override
    public VrijBerichtResultaat verwerkVerzoek(final VrijBerichtVerzoek verzoek) {
        final VrijBerichtResultaat resultaat = new VrijBerichtResultaat(verzoek);
        try {
            final Partij ontvangendePartij =
                    this.partijService.vindPartijOpCode(resultaat.getVerzoek().getParameters().getOntvangerVrijBericht());
            final Partij zenderVrijBericht = partijService.vindPartijOpCode(resultaat.getVerzoek().getParameters().getZenderVrijBericht());
            resultaat.setBrpPartij(partijService.geefBrpPartij());
            autorisatieService.valideerAutorisatie(resultaat.getVerzoek());
            final boolean zenderBrp = !isPartijInGbaStelsel(zenderVrijBericht);
            final boolean ontvangerBrp = !isPartijInGbaStelsel(ontvangendePartij);
            if (zenderBrp) {
                autorisatieService.valideerAutorisatieBrpZender(resultaat.getVerzoek());
            }
            if (ontvangerBrp) {
                autorisatieService.valideerAutorisatieBrpOntvanger(resultaat.getVerzoek());
            }
            vrijBerichtInhoudControleService.controleerInhoud(resultaat.getVerzoek());

            final VrijBerichtVerwerkBericht verwerkVrijBericht = vrijBerichtBerichtFactory.maakVerwerkBericht(resultaat, ontvangendePartij, zenderVrijBericht);
            final String vrijBerichtStr = maakVerwerkVrijBerichtService.maakVerwerkVrijBericht(verwerkVrijBericht);
            final VrijBerichtGegevens berichtGegevens =
                    maakBerichtGegevens(vrijBerichtStr, ontvangendePartij, verwerkVrijBericht, ontvangerBrp ? Stelsel.BRP : Stelsel.GBA);

            if (isOntvangendePartijBrpVoorziening(ontvangendePartij)) {
                beheerRepository.opslaanNieuwVrijBericht(maakVrijBericht(verwerkVrijBericht));
            } else {
                plaatsVerwerkVrijBerichtService.plaatsVrijBericht(berichtGegevens);
            }

        } catch (final AutorisatieException e) {
            LOGGER.info("Autorisatiefout opgetreden bij verwerken vrij bericht: " + e);
            resultaat.getMeldingen().add(new Melding(Regel.R2343));
        } catch (final StapMeldingException e) {
            LOGGER.warn("Functionele fout bij verwerken vrij bericht: " + e);
            resultaat.getMeldingen().addAll(e.getMeldingen());
        } catch (final StapException e) {
            LOGGER.error("Algemene fout bij verwerken vrij bericht", e);
            resultaat.getMeldingen().add(e.maakFoutMelding());
        }
        return resultaat;
    }

    private static boolean isPartijInGbaStelsel(final Partij ontvangendePartij) {
        final Integer datumOvergangNaarBrp = ontvangendePartij.getDatumOvergangNaarBrp();
        return datumOvergangNaarBrp == null || datumOvergangNaarBrp > DatumUtil.vandaag();
    }

    private static boolean isOntvangendePartijBrpVoorziening(final Partij ontvangendePartij) {
        return Partij.PARTIJ_CODE_BRP.equals(ontvangendePartij.getCode());
    }

    private VrijBerichtGegevens maakBerichtGegevens(final String vrijBerichtStr, final Partij ontvangendePartij,
                                                    final VrijBerichtVerwerkBericht verwerkVrijBericht, final Stelsel stelsel) {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setSoortBericht(SoortBericht.VRB_VRB_VERWERK_VRIJ_BERICHT);
        archiveringOpdracht.setData(vrijBerichtStr);
        archiveringOpdracht.setZendendePartijId(partijService.geefBrpPartij().getId());
        archiveringOpdracht.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        archiveringOpdracht.setOntvangendePartijId(ontvangendePartij.getId());
        archiveringOpdracht.setReferentienummer(verwerkVrijBericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer());
        archiveringOpdracht.setTijdstipVerzending(verwerkVrijBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        if (verwerkVrijBericht.getBasisBerichtGegevens().getMeldingen() == null) {
            archiveringOpdracht.setVerwerkingsresultaat(VerwerkingsResultaat.GESLAAGD);
        } else {
            archiveringOpdracht.setHoogsteMeldingsNiveau(
                    MeldingUtil.bepaalHoogsteMeldingNiveau(verwerkVrijBericht.getBasisBerichtGegevens().getMeldingen()));
            archiveringOpdracht.setVerwerkingsresultaat(MeldingUtil.bepaalVerwerking(verwerkVrijBericht.getBasisBerichtGegevens().getMeldingen()));
        }
        //@formatter:off
        return VrijBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metStelsel(stelsel)
                .metPartij(ontvangendePartij)
                .metBrpEndpointUrl(ontvangendePartij.getAfleverpuntVrijBericht())
                .build();
        //@formatter:on
    }

    private VrijBericht maakVrijBericht(final VrijBerichtVerwerkBericht verwerkVrijBericht) {
        final VrijBericht vrijBericht = new VrijBericht(
                SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT,
                verwerkVrijBericht.getBerichtVrijBericht().getVrijBericht().getSoortVrijBericht(),
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime()),
                verwerkVrijBericht.getBerichtVrijBericht().getVrijBericht().getInhoud(),
                false);
        final VrijBerichtPartij vrijBerichtPartij = new VrijBerichtPartij(vrijBericht, verwerkVrijBericht.getVrijBerichtParameters().getZenderVrijBericht());
        vrijBericht.getVrijBerichtPartijen().add(vrijBerichtPartij);
        return vrijBericht;
    }
}
