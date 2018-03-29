/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * BasisBerichtGegevensTest.
 */
public class BasisBerichtGegevensTest {

    private static final Dienst DIENST = maakDienst();
    private static final ZonedDateTime TIJD = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
    private static final List<Melding> MELDINGEN = Collections.singletonList(new Melding(Regel.R1321));
    private static final String REFERENTIENUMMER = "referentienummer";
    private static final String CROSSREFERENTIENUMMER = "crossReferentienummer";
    private static final String SYSTEEM = "BRP";
    private static final SoortSynchronisatie SOORT_SYNCHRONISATIE = SoortSynchronisatie.MUTATIE_BERICHT;


    private static final Dienst maakDienst() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        leveringsautorisatie.setId(4);
        return new Dienst(dienstbundel, SoortDienst.ATTENDERING);
    }

    @Test
    public void testBasisBerichtGegevens() {
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                //@formatter:off
            .metTijdstipRegistratie(TIJD)
            .metParameters()
                .metDienst(DIENST)
                .metSoortSynchronisatie(SOORT_SYNCHRONISATIE)
            .eindeParameters()
            .metStuurgegevens()
                .metReferentienummer(REFERENTIENUMMER)
                .metCrossReferentienummer(CROSSREFERENTIENUMMER)
                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .metZendendeSysteem(SYSTEEM)
                .metTijdstipVerzending(TIJD)
            .eindeStuurgegevens()
            .metMeldingen(MELDINGEN)
            .metCategorieNaam("categorieNaam")
            .metSoortNaam("soortNaam")
            .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
            ).build();
         //@formatter:on
        Assert.assertEquals(1, basisBerichtGegevens.getMeldingen().size());
        Assert.assertEquals("categorieNaam", basisBerichtGegevens.getCategorieNaam());
        Assert.assertEquals("soortNaam", basisBerichtGegevens.getSoortNaam());
        Assert.assertEquals(CROSSREFERENTIENUMMER, basisBerichtGegevens.getStuurgegevens().getCrossReferentienummer());
        Assert.assertEquals(REFERENTIENUMMER, basisBerichtGegevens.getStuurgegevens().getReferentienummer());
        Assert.assertEquals(SYSTEEM, basisBerichtGegevens.getStuurgegevens().getZendendeSysteem());
        Assert.assertNotNull(basisBerichtGegevens.getResultaat());
        Assert.assertEquals(SOORT_SYNCHRONISATIE, basisBerichtGegevens.getParameters().getSoortSynchronisatie());
        Assert.assertEquals(DIENST, basisBerichtGegevens.getParameters().getDienst());
        Assert.assertEquals(TIJD, basisBerichtGegevens.getTijdstipRegistratie());
    }

    @Test
    public void testBasisBerichtGegevens_LeverAlias() {
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                //@formatter:off
            .metTijdstipRegistratie(TIJD)
            .metParameters()
                .metDienst(DIENST)
                .metSoortSynchronisatie(SOORT_SYNCHRONISATIE)
            .eindeParameters()
            .metStuurgegevens()
                .metReferentienummer(REFERENTIENUMMER)
                .metCrossReferentienummer(CROSSREFERENTIENUMMER)
                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .metZendendeSysteem(SYSTEEM)
                .metTijdstipVerzending(TIJD)
            .eindeStuurgegevens()
            .metMeldingen(MELDINGEN)
            .metSoortNaam("soortNaam")
            .metCategorieNaam("categorieNaam")
            .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
            ).build();
         //@formatter:on
        Assert.assertEquals(1, basisBerichtGegevens.getMeldingen().size());
        Assert.assertEquals("soortNaam", basisBerichtGegevens.getSoortNaam());
        Assert.assertEquals("categorieNaam", basisBerichtGegevens.getCategorieNaam());
        Assert.assertEquals(CROSSREFERENTIENUMMER, basisBerichtGegevens.getStuurgegevens().getCrossReferentienummer());
        Assert.assertEquals(REFERENTIENUMMER, basisBerichtGegevens.getStuurgegevens().getReferentienummer());
        Assert.assertEquals(SYSTEEM, basisBerichtGegevens.getStuurgegevens().getZendendeSysteem());
        Assert.assertNotNull(basisBerichtGegevens.getResultaat());
        Assert.assertEquals(SOORT_SYNCHRONISATIE, basisBerichtGegevens.getParameters().getSoortSynchronisatie());
        Assert.assertEquals(DIENST, basisBerichtGegevens.getParameters().getDienst());
        Assert.assertEquals(TIJD, basisBerichtGegevens.getTijdstipRegistratie());
    }


    @Test
    public void testBasisBerichtGegevens_LeverAlias_AliasNull() {
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                //@formatter:off
            .metTijdstipRegistratie(TIJD)
            .metParameters()
                .metDienst(DIENST)
                .metSoortSynchronisatie(SOORT_SYNCHRONISATIE)
            .eindeParameters()
            .metStuurgegevens()
                .metReferentienummer(REFERENTIENUMMER)
                .metCrossReferentienummer(CROSSREFERENTIENUMMER)
                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .metZendendeSysteem(SYSTEEM)
                .metTijdstipVerzending(TIJD)
            .eindeStuurgegevens()
            .metMeldingen(MELDINGEN)
            .metSoortNaam("soortNaam")
                .metCategorieNaam("categorieNaam")
            .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
            ).build();
         //@formatter:on
        Assert.assertEquals(1, basisBerichtGegevens.getMeldingen().size());
        Assert.assertEquals("categorieNaam", basisBerichtGegevens.getCategorieNaam());
        Assert.assertEquals("soortNaam", basisBerichtGegevens.getSoortNaam());
        Assert.assertEquals(CROSSREFERENTIENUMMER, basisBerichtGegevens.getStuurgegevens().getCrossReferentienummer());
        Assert.assertEquals(REFERENTIENUMMER, basisBerichtGegevens.getStuurgegevens().getReferentienummer());
        Assert.assertEquals(SYSTEEM, basisBerichtGegevens.getStuurgegevens().getZendendeSysteem());
        Assert.assertNotNull(basisBerichtGegevens.getResultaat());
        Assert.assertEquals(SOORT_SYNCHRONISATIE, basisBerichtGegevens.getParameters().getSoortSynchronisatie());
        Assert.assertEquals(DIENST, basisBerichtGegevens.getParameters().getDienst());
        Assert.assertEquals(TIJD, basisBerichtGegevens.getTijdstipRegistratie());
    }

    @Test
    public void testBasisBerichtGegevensZonderStuurGegegevensParameters() {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metTijdstipRegistratie(TIJD)
            .metMeldingen(MELDINGEN)
            .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
            ).build();
         //@formatter:on

        Assert.assertEquals(1, basisBerichtGegevens.getMeldingen().size());
        Assert.assertNotNull(basisBerichtGegevens.getResultaat());

    }
}
