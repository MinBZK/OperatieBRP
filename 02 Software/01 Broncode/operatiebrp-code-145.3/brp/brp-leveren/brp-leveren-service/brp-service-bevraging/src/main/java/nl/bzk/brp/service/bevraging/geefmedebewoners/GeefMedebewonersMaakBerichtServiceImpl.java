/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import com.google.common.primitives.Booleans;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.AbstractMaakBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingSelecteerPersoonService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoon;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonBerichtFactory;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link nl.bzk.brp.service.bevraging.algemeen.Bevraging.MaakBerichtService} voor Geef Medebewoners.
 */
@Service
final class GeefMedebewonersMaakBerichtServiceImpl extends AbstractMaakBerichtServiceImpl<GeefMedebewonersVerzoek, BevragingResultaat> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private BevragingSelecteerPersoonService bevragingSelecteerPersoonService;
    private GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService;
    private ZoekPersoon.OphalenPersoonService<ZoekPersoonOpAdresVerzoek> geefMedebewonersOphalenPersoonService;
    private ZoekPersoonBerichtFactory berichtFactory;
    private RelatiefilterService relatiefilterService;
    private PeilmomentValidatieService peilmomentValidatieService;
    private DatumService datumService;

    /**
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     */
    @Inject
    GeefMedebewonersMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService, final PartijService partijService) {
        super(leveringsautorisatieService, partijService);
    }

    @Override
    protected BevragingResultaat maakResultaatObject() {
        return new BevragingResultaat();
    }

    @Override
    protected void voerDienstSpecifiekeStappenUit(final GeefMedebewonersVerzoek verzoek, final BevragingResultaat resultaat)
            throws StapException {
        controleerVerzoekparameters(verzoek);
        final int peilmomentMaterieel = bepaalPeilmomentMaterieel(verzoek);
        // Converteer GeefMedebewonersVerzoek naar ZoekPersoonOpAdresVerzoek. Zoek persoon is generiek en geef medebewoners specifiek.
        final ZoekPersoonOpAdresVerzoek zoekPersoonOpAdresVerzoek = new ZoekPersoonOpAdresVerzoek();
        zoekPersoonOpAdresVerzoek.getParameters().setZoekBereik(Zoekbereik.PEILMOMENT);
        zoekPersoonOpAdresVerzoek.getParameters().setPeilmomentMaterieel(verzoek.getParameters().getPeilmomentMaterieel());
        if (verzoek.geefMedebewonersGelijkeBAG()) {
            zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon()
                    .addAll(ZoekCriteriaConverteerUtil.converteerIdentificatieCriteria(verzoek.getIdentificatiecriteria(), ZoekCriteriaType.BAG));
        } else if (verzoek.geefMedebewonersVanPersoon()) {
            final Persoonslijst persoonslijst = bevragingSelecteerPersoonService
                    .selecteerPersoon(verzoek.getIdentificatiecriteria().getBurgerservicenummer(), verzoek.getIdentificatiecriteria().getAdministratienummer(),
                            verzoek.getIdentificatiecriteria().getObjectSleutel(), verzoek.getStuurgegevens().getZendendePartijCode(),
                            resultaat.getAutorisatiebundel());
            // BAG sleutel is altijd gevuld, anders wordt er een melding gegeven.
            final String bagSleutel = bepaalBAGSleutelService.bepaalBAGSleutel(persoonslijst, peilmomentMaterieel);
            final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteria = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
            zoekCriteria.setElementNaam(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getNaam());
            zoekCriteria.setWaarde(bagSleutel);
            zoekCriteria.setZoekOptie(Zoekoptie.EXACT);
            zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon().add(zoekCriteria);
        } else {
            zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon()
                    .addAll(ZoekCriteriaConverteerUtil.converteerIdentificatieCriteria(verzoek.getIdentificatiecriteria(), ZoekCriteriaType.ADRES));
        }

        voegIngeschreveneZoekCriteriaToe(zoekPersoonOpAdresVerzoek);
        // Haal personen op, zoekcriteria zou hier altijd gevuld moeten zijn.
        final List<Persoonslijst> persoonslijsten = geefMedebewonersOphalenPersoonService
                .voerStapUit(zoekPersoonOpAdresVerzoek, resultaat.getAutorisatiebundel());
        if (persoonslijsten.isEmpty()) {
            // Niets gevonden, dus BAG-sleutel bestaat niet.
            throw new StapMeldingException(Regel.R2383);
        }
        final List<Persoonslijst> gefilterdePersoonslijsten = relatiefilterService.filterRelaties(persoonslijsten, peilmomentMaterieel);
        // Maak bericht.
        final VerwerkPersoonBericht bericht = berichtFactory
                .maakZoekPersoonBericht(gefilterdePersoonslijsten, resultaat.getAutorisatiebundel(), verzoek, peilmomentMaterieel);
        // Misschien beter het resultaat retourneren, ipv een contextobject gebruiken?
        // We hebben nu ook twee resultaat objecten, zou er één moeten worden.
        resultaat.setBericht(bericht);
    }

    @Bedrijfsregel(Regel.R1274)
    @Bedrijfsregel(Regel.R2377)
    @Bedrijfsregel(Regel.R2295)
    private void controleerVerzoekparameters(final GeefMedebewonersVerzoek verzoek) throws StapMeldingException {
        //Persoonsidentificatie OF Identificatiecode nummeraanduiding OF Adrescriteria  moet zijn ingevuld
        final int booleanCount = Booleans.countTrue(
                verzoek.geefMedebewonersGelijkeBAG(),
                verzoek.geefMedebewonersGelijkAdres(),
                verzoek.geefMedebewonersVanPersoon(),
                verzoek.geefMedebewonersGelijkeIDcodeAdresseerbaarObject());
        if (booleanCount != 1) {
            throw new StapMeldingException(Regel.R2377);
        }

        //valideer peilmoment
        if (verzoek.getParameters() != null && (verzoek.getParameters().getPeilmomentMaterieel() != null)) {
            peilmomentValidatieService.valideerMaterieel(verzoek.getParameters().getPeilmomentMaterieel());
        }
    }


    @Bedrijfsregel(Regel.R2397)
    private void voegIngeschreveneZoekCriteriaToe(final ZoekPersoonOpAdresVerzoek zoekPersoonOpAdresVerzoek) {
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria nbaActueel = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        nbaActueel.setElementNaam(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam());
        nbaActueel.setWaarde(NadereBijhoudingsaard.ACTUEEL.getCode());
        zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon().add(nbaActueel);
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria nbaOnbekend = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        nbaOnbekend.setElementNaam(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam());
        nbaOnbekend.setWaarde(NadereBijhoudingsaard.ONBEKEND.getCode());
        nbaActueel.setOf(nbaOnbekend);
    }


    private int bepaalPeilmomentMaterieel(final GeefMedebewonersVerzoek verzoek) throws StapMeldingException {
        final String peilmomentMaterieelVerzoek = verzoek.getParameters().getPeilmomentMaterieel();
        return peilmomentMaterieelVerzoek == null ? BrpNu.get().alsIntegerDatumNederland()
                : DatumUtil.vanDatumNaarInteger(datumService.parseDate(peilmomentMaterieelVerzoek));
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getDienstSpecifiekeLoggingString() {
        return "Geef Medebewoners";
    }

    @Inject
    void setBevragingSelecteerPersoonService(final BevragingSelecteerPersoonService bevragingSelecteerPersoonService) {
        this.bevragingSelecteerPersoonService = bevragingSelecteerPersoonService;
    }

    @Inject
    void setBepaalBAGSleutelService(final GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService) {
        this.bepaalBAGSleutelService = bepaalBAGSleutelService;
    }

    @Inject
    void setGeefMedebewonersOphalenPersoonService(
            final ZoekPersoon.OphalenPersoonService<ZoekPersoonOpAdresVerzoek> geefMedebewonersOphalenPersoonService) {
        this.geefMedebewonersOphalenPersoonService = geefMedebewonersOphalenPersoonService;
    }

    @Inject
    void setBerichtFactory(final ZoekPersoonBerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    @Inject
    void setRelatiefilterService(final RelatiefilterService relatiefilterService) {
        this.relatiefilterService = relatiefilterService;
    }

    @Inject
    void setPeilmomentValidatieService(final PeilmomentValidatieService peilmomentValidatieService) {
        this.peilmomentValidatieService = peilmomentValidatieService;
    }

    @Inject
    void setDatumService(final DatumService datumService) {
        this.datumService = datumService;
    }
}
