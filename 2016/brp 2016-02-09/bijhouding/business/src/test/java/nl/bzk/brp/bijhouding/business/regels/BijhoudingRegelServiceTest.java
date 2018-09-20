/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0105M;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0106;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0107;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0126;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0129;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0134;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0187;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning.BRBY0136;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning.BRBY0143;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning.BRBY0189;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0033;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0036;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0103;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0168;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0169;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0170;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0177;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRPUC00112;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRPUC00120;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0401;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0402;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0403;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0409;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0417;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0454;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap.BRBY0443;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap.BRBY0445;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap.BRBY0455;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratiehuwelijkpartnerschap.BRBY0437;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres.BRBY0024;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres.BRBY0502;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0151;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0153;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0157;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0158;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0162;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0907;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0908;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0909;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0913;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY0152;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9901;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9902;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9905;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9906;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9910;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.betrokkenheid.identiteitbetrokkenheid.BRAL2010;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.groep.BRAL9003;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRAL2203;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRBY0011;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRBY0012;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRBY0032;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag.BRBY2013;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag.BRBY2016;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag.BRBY2017;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag.BRBY2018;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.BRBY05111;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.groep3.BRBY0175;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander.BRAL2018;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele.BRBY2012;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele.BRBY2014;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.BRBY0132;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.BRBY0133;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.BRBY0135;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.BRBY0137;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.BRBY01032;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.BRBY01037;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.BRBY0166;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.BRBY0183;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.gezagderde.BRBY2015;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.gezagderde.BRBY2019;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers.BRAL0004;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers.BRAL0013;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit.BRAL0205;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0208;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0209;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0210;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0216;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0218;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie.BRBY0180;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie.BRBY0540;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie.BRBY0543;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie.BRBY0593;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0141;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0163;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0164;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0173;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0176;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0178;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0902;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0903;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0904;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0906;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0037;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0040;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0042;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0044;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0045;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY1026;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.BRAL0502;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.BRAL0505;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos.BRAL0219;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos.BRAL2013;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos.BRBY0148;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.uitsluitingkiesrecht.BRBY0131;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander.BRAL2017;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0167;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0179;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0182;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0191;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0192;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0193;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek.BRBY0194;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie.BRAL0202;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie.BRAL2111;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2104;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2110;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2112;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2113;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0429;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0430;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0438;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0442;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0446;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0451;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0452;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
public class BijhoudingRegelServiceTest {

    @Inject
    private BijhoudingRegelService bijhoudingRegelService;

    @Test
    public void testGetVoorBerichtRegels() {
        List<VoorBerichtRegel> regels = bijhoudingRegelService.getVoorBerichtRegels(SoortBericht.BHG_VBA_CORRIGEER_ADRES);
        verifieerRegels(regels,
            BRBY9901.class,
            BRBY9902.class,
            BRBY9905.class,
            BRBY9906.class,
            BRBY0152.class,
            BRBY0024.class);
    }

    @Test
    public void testGetVoorActieRegels() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.DUMMY),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieVerstrekkingsbeperking() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0167.class, BRBY0179.class, BRBY0182.class, BRBY0191.class, BRBY0192.class, BRBY0193.class, BRBY0194.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieIdentificatienummers() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL0004.class, BRAL0013.class);

    }

    @Test
    public void testGetVoorActieRegelsBijCorrectieAdres() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.CORRECTIE_ADRES),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.CORRECTIE_ADRES,
                SoortActie.CORRECTIE_ADRES),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0011.class, BRBY0012.class, BRBY0032.class, BRAL9003.class, BRBY0175.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieAdres() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_ADRES),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY05111.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK,
                SoortActie.REGISTRATIE_ADRES),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY05111.class,
            BRBY0175.class, BRBY0502.class, BRAL9003.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK,
                SoortActie.REGISTRATIE_ADRES),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY05111.class,
            BRBY0175.class, BRBY0502.class, BRAL9003.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieGeboorte() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL0205.class, BRBY0169.class,
            BRPUC00112.class, BRPUC00120.class, BRBY0134.class, BRBY0033.class, BRBY0187.class, BRBY0036.class, BRBY0129.class,
            BRBY01032.class, BRBY01037.class, BRBY0166.class, BRBY0177.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL0205.class, BRBY0169.class,
            BRPUC00112.class, BRPUC00120.class, BRBY0134.class, BRBY0033.class, BRBY0187.class, BRBY0036.class, BRBY0129.class,
            BRBY01032.class, BRBY01037.class, BRBY0166.class, BRBY0177.class,
            BRBY0168.class, BRBY0170.class, BRBY0103.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND_MET_ERKENNING,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL0205.class, BRBY0169.class,
            BRPUC00112.class, BRPUC00120.class, BRBY0134.class, BRBY0033.class, BRBY0187.class, BRBY0036.class, BRBY0129.class,
            BRBY01032.class, BRBY01037.class, BRBY0166.class, BRBY0177.class,
            BRBY0168.class, BRBY0170.class, BRBY0103.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieOuder() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_OUDER),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0136.class, BRBY0143.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieOverlijden() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_OVERLIJDEN),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRBY0913.class, BRBY0011.class,
            BRBY0012.class, BRBY0902.class, BRBY0903.class, BRBY0904.class, BRBY0907.class, BRBY0908.class, BRBY0909.class,
            BRBY0913.class, BRAL9003.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.OVERLIJDEN_IN_BUITENLAND,
                SoortActie.REGISTRATIE_OVERLIJDEN),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRBY0913.class, BRBY0011.class,
            BRBY0012.class, BRBY0902.class, BRBY0903.class, BRBY0904.class, BRBY0907.class, BRBY0908.class, BRBY0909.class,
            BRBY0913.class, BRAL9003.class,
            BRBY0906.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieAanvangHuwelijkGeregistreerdPartnerschap() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,

            BRAL0202.class, BRAL0205.class, BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class,
            BRAL2104.class, BRAL2110.class, BRAL2111.class, BRBY0401.class, BRBY0402.class, BRBY0403.class, BRBY0409.class,
            BRBY0417.class, BRBY0429.class, BRBY0430.class, BRBY0437.class, BRBY0438.class, BRBY0442.class, BRBY0454.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieEindeHuwelijkGeregistreerdPartnerschap() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,

            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL2104.class, BRAL2113.class,
            BRAL2112.class, BRBY0437.class, BRBY0443.class, BRBY0455.class, BRBY0451.class, BRBY0452.class, BRBY0446.class,
            BRBY0445.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
                SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,

            BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL2104.class, BRAL2113.class,
            BRAL2112.class, BRBY0437.class, BRBY0443.class, BRBY0455.class, BRBY0451.class, BRBY0452.class, BRBY0446.class,
            BRBY0445.class,

            BRBY0403.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieNaamGeslacht() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_NAAM_GESLACHT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM,
                SoortActie.REGISTRATIE_NAAM_GESLACHT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL9003.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSAANDUIDING,
                SoortActie.REGISTRATIE_NAAM_GESLACHT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL9003.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieVoornaam() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_VOORNAAM),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM,
                SoortActie.REGISTRATIE_VOORNAAM),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL9003.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.WIJZIGING_VOORNAAM,
                SoortActie.REGISTRATIE_VOORNAAM),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL9003.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieGeslachtsaanduiding() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_GESLACHTSAANDUIDING),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM,
                SoortActie.REGISTRATIE_GESLACHTSAANDUIDING),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRAL9003.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieNationaliteit() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_NATIONALITEIT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0141.class, BRBY0151.class, BRBY0153.class, BRBY0157.class,
            BRBY0158.class, BRBY0162.class, BRBY0173.class, BRBY0176.class, BRBY0178.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieNationaliteitNaam() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_NATIONALITEIT_NAAM),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0141.class, BRBY0151.class, BRBY0153.class, BRBY0157.class,
            BRBY0158.class, BRBY0162.class, BRBY0173.class, BRBY0176.class, BRBY0178.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERKRIJGING_NEDERLANDSE_NATIONALITEIT,
                SoortActie.REGISTRATIE_NATIONALITEIT_NAAM),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0141.class, BRBY0151.class, BRBY0153.class, BRBY0157.class,
            BRBY0158.class, BRBY0162.class, BRBY0173.class, BRBY0176.class, BRBY0178.class,
            BRBY0163.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERKRIJGING_VREEMDE_NATIONALITEIT,
                SoortActie.REGISTRATIE_NATIONALITEIT_NAAM),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0141.class, BRBY0151.class, BRBY0153.class, BRBY0157.class,
            BRBY0158.class, BRBY0162.class, BRBY0173.class, BRBY0176.class, BRBY0178.class,
            BRBY0164.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieReisdocument() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_REISDOCUMENT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY1026.class, BRBY0037.class, BRBY0045.class
        );

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERKRIJGING_REISDOCUMENT,
                SoortActie.REGISTRATIE_REISDOCUMENT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY1026.class, BRBY0037.class, BRBY0045.class,
            BRBY0040.class, BRBY0042.class);

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.ONTTREKKING_REISDOCUMENT,
                SoortActie.REGISTRATIE_REISDOCUMENT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY1026.class, BRBY0037.class, BRBY0045.class,
            BRBY0044.class);

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieCuratele() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_CURATELE),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY2012.class, BRBY2014.class
        );

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieGezag() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_GEZAG),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY2013.class, BRBY2015.class, BRBY2016.class
        );

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieUitsluitingKiesrecht() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_UITSLUITING_KIESRECHT),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0131.class
        );

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieDeelnameEuVerkiezingen() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_DEELNAME_E_U_VERKIEZINGEN),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0137.class, BRBY0135.class, BRBY0133.class, BRBY0132.class
        );

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieStaatloos() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_STAATLOOS),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY0148.class
        );

    }

    @Test
    public void testGetVoorActieRegelsBijRegistratieMigratie() throws Exception {

        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_MIGRATIE),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY05111.class);


        verifieerRegels(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND,
                SoortActie.REGISTRATIE_MIGRATIE),
            BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class,
            BRBY05111.class,
            BRBY0180.class, BRBY0540.class, BRBY0543.class, BRBY0593.class
        );

    }

    @Test
    public void testGetNaActieRegels() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.DUMMY),
            BRAL0502.class);

    }

    @Test
    public void testGetNaActieRegelsBijRegistratieGeboorte() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL0502.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL0502.class,
            BRBY0126.class, BRAL0505.class, BRBY0105M.class, BRBY0106.class, BRBY0107.class, BRAL0219.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND_MET_ERKENNING,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL0502.class,
            BRBY0126.class, BRAL0505.class, BRBY0105M.class, BRBY0106.class, BRBY0107.class, BRAL0219.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.TOEVOEGING_GEBOORTEAKTE,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL0502.class,
            BRAL0505.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.VERBETERING_GEBOORTEAKTE,
                SoortActie.REGISTRATIE_GEBOORTE),
            BRAL0502.class,
            BRAL0505.class);

    }

    @Test
    public void testGetNaActieRegelsBijRegistratieOuder() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_OUDER),
            BRAL0502.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND_MET_ERKENNING,
                SoortActie.REGISTRATIE_OUDER),
            BRAL0502.class,
            BRBY0189.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE,
                SoortActie.REGISTRATIE_OUDER),
            BRAL0502.class,
            BRAL0505.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.ERKENNING_NA_GEBOORTE,
                SoortActie.REGISTRATIE_OUDER),
            BRAL0502.class,
            BRAL0505.class);

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.VASTSTELLING_OUDERSCHAP,
                SoortActie.REGISTRATIE_OUDER),
            BRAL0502.class,
            BRAL0505.class);

    }

    @Test
    public void testGetNaActieRegelsBijRegistratieStaatloos() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_STAATLOOS),
            BRAL0502.class, BRAL2013.class);

    }

    @Test
    public void testGetNaActieRegelsBijRegistratieVastgesteldNietNederlander() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER),
            BRAL0502.class, BRAL2017.class);

    }

    @Test
    public void testGetNaActieRegelsBijRegistratieBehandeldAlsNederlander() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER),
            BRAL0502.class, BRAL2018.class);

    }

    @Test
    public void testGetNaActieRegelsBijRegistratieGezag() throws Exception {

        verifieerRegels(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.DUMMY,
                SoortActie.REGISTRATIE_GEZAG),
            BRAL0502.class, BRBY2017.class, BRBY2018.class, BRBY2019.class);

    }

    private void verifieerRegels(List<? extends RegelInterface> regels, Class<? extends RegelInterface>... regelClass) {
        assertEquals(regelClass.length, regels.size());
        for (int i = 0; i < regelClass.length; i++) {
            assertEquals(regelClass[i], regels.get(i).getClass());
        }
    }
}
