/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;


import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatiePersoonBerichtFactory;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.springframework.stereotype.Component;

/**
 * Deze class 'overschrijft' de klasse 'nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatiePersoonBerichtFactoryImpl'.
 */
@Component
public class OnderhoudAfnemerindicatiePersoonGbaBerichtFactoryImpl implements OnderhoudAfnemerindicatiePersoonBerichtFactory {

    private PartijService partijService;

    /**
     * Constructor.
     * @param partijService partij service
     */
    @Inject
    public OnderhoudAfnemerindicatiePersoonGbaBerichtFactoryImpl(final PartijService partijService) {
        this.partijService = partijService;
    }

    @Override
    public VerwerkPersoonBericht maakBericht(Persoonslijst teLeverenPersoon, Autorisatiebundel autorisatiebundel, Integer damp,
                                             ZonedDateTime tijdstipRegistratie, SoortAdministratieveHandeling soortAdministratieveHandeling,
                                             String berichtReferentie) {
        final Partij zendendePartij = partijService.geefBrpPartij();
        final Partij ontvangendePartij = autorisatiebundel.getPartij();

        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metParameters()
                .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                .metDienst(autorisatiebundel.getDienst())
                .eindeParameters()
                .metTijdstipRegistratie(tijdstipRegistratie)
                .metSoortNaam(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE.getNaam())
                .metCategorieNaam(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE.getCategorie().getNaam())
                .metPartijCode(ontvangendePartij.getCode())
                .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metCrossReferentienummer(berichtReferentie)
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(zendendePartij)
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
                .eindeStuurgegevens()
                .build();

        return new VerwerkPersoonBericht(basisBerichtGegevens,
                autorisatiebundel, Collections.singletonList(new BijgehoudenPersoon.Builder(teLeverenPersoon, null).build())
        );
    }
}
