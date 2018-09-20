/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAangeverBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieMigratieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;


public class BRBY05111Test {

    private static final int                GEBOORTE_DATUM_JONGER_DAN_ZESTIEN        = 19980102;

    private static final int                GEBOORTE_DATUM_ZESTIEN_JAAR_OUD          = 19980101;
    private static final int                GEBOORTE_DATUM_OUDER_DAN_ZESTIEN         = 19971231;
    private static final int                REGISTRATIE_DATUM                        = 20140101;

    private static final int                REGISTRATIE_JAAR                         = 2014;
    private static final int                REGISTRATIE_MAAND                        = 1;
    private static final int                REGISTRATIE_DAG                          = 1;
    @SuppressWarnings("deprecation")
    private static final DatumAttribuut ACTIE_DATUM = new DatumAttribuut(new Date(REGISTRATIE_JAAR - 1900, REGISTRATIE_MAAND - 1, REGISTRATIE_DAG));

    private static final DatumTijdAttribuut TIJDSTIP_REGISTRATIE =
        DatumTijdAttribuut.bouwDatumTijd(REGISTRATIE_JAAR, REGISTRATIE_MAAND, REGISTRATIE_DAG, 12, 0, 0);
    private static final String             AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE = "I";

    private static final String             AANGEVER_ADRESHOUDING_CODE_GEZAGHOUDER   = "G";
    private final BRBY05111                 brby05111                              = new BRBY05111();

    @Test
    public void testIngeschrevenePersoonJongerDanZestienJaarOud() {

        final DatumAttribuut geboorteDatum = DatumAttribuut.vandaag();
        geboorteDatum.voegJaarToe(-DatumEvtDeelsOnbekendAttribuut.ZESTIEN_JAAR);
        geboorteDatum.voegDagToe(-1);

        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetAdres(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_JONGER_DAN_ZESTIEN,
                            SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER), nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void tesIngeschrevenetPersoonOnderCurateleOuderDanZestien() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetAdres(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_OUDER_DAN_ZESTIEN, SoortIndicatie.INDICATIE_ONDER_CURATELE),
                    nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testIngeschrevenePersoonOnderCurateleJongerDanZestienJaarOud() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetAdres(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_JONGER_DAN_ZESTIEN, SoortIndicatie.INDICATIE_ONDER_CURATELE),
                    nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testGezaghouderPersoonOnderCurateleJongerDanZestienJaarOud() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetAdres(AANGEVER_ADRESHOUDING_CODE_GEZAGHOUDER);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_JONGER_DAN_ZESTIEN, SoortIndicatie.INDICATIE_ONDER_CURATELE),
                    nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testIngeschrevenePersoonNietOnderCurateleOuderDanZestienJaarOud() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetAdres(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_OUDER_DAN_ZESTIEN, SoortIndicatie.INDICATIE_STAATLOOS),
                    nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonZonderAangeverAdreshouding() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetAdres(null);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS),
                    nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonZonderAdres() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonZonderAdres(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
                brby05111.voerRegelUit(
                        maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS),
                        nieuweSituatie, bouwActieRegistratieAdres(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testZonderNieuweSituatie() {
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS), null,
                    bouwActieRegistratieAdres(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMigratiePersoonZonderAangeverAdreshouding() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetMigratie(null);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS),
                    nieuweSituatie, bouwActieRegistratieMigratie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMigratiePersoonZonderNieuweSituatie() {
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS), null,
                    bouwActieRegistratieMigratie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMigratiePersoonMetPersoonNietOnderCurateleOuderDanZestienJaarOud() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonMetMigratie(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_OUDER_DAN_ZESTIEN, SoortIndicatie.INDICATIE_STAATLOOS),
                    nieuweSituatie, bouwActieRegistratieMigratie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMigratiePersoonZonderMigratie() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonZonderMigratie(AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE);
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS),
                    nieuweSituatie, bouwActieRegistratieMigratie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMetNietGerelateerdeActieSoort() {
        final List<BerichtEntiteit> overtreders =
            brby05111.voerRegelUit(
                    maakHuidigePersoon(GEBOORTE_DATUM_ZESTIEN_JAAR_OUD, SoortIndicatie.INDICATIE_STAATLOOS), null,
                    bouwActieRegistratieGeboorte(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY05111, brby05111.getRegel());
    }

    private PersoonBericht maakNieuwePersoonMetAdres(final String aangeverAdreshoudingCode) {
        return maakNieuwePersoon(false, true, aangeverAdreshoudingCode);
    }

    private PersoonBericht maakNieuwePersoonZonderAdres(final String aangeverAdreshoudingCode) {
        return maakNieuwePersoon(false, false, aangeverAdreshoudingCode);
    }

    private PersoonBericht maakNieuwePersoon(final boolean persoonHeeftMigratie, final boolean persoonHeeftAdres, final String aangeverCode) {
        final PersoonBericht persoon = new PersoonBericht();

        if (persoonHeeftMigratie) {
            final PersoonMigratieGroepBericht migratie = new PersoonMigratieGroepBericht();
            AangeverAttribuut aangeverAdreshouding = null;
            if (aangeverCode != null) {
                aangeverAdreshouding =
                    new AangeverAttribuut(
                        TestAangeverBuilder.maker().metCode(aangeverCode).metNaam(new NaamEnumeratiewaardeAttribuut(null)).maak());
            }

            migratie.setAangeverMigratie(aangeverAdreshouding);
            persoon.setMigratie(migratie);
        } else {
            persoon.setMigratie(null);
        }

        if (persoonHeeftAdres) {
            persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
            final PersoonAdresBericht adres = new PersoonAdresBericht();
            AangeverAttribuut aangeverAdreshouding = null;
            if (aangeverCode != null) {
                aangeverAdreshouding =
                    new AangeverAttribuut(
                        TestAangeverBuilder.maker().metCode(aangeverCode).metNaam(new NaamEnumeratiewaardeAttribuut(null)).maak());
            }

            adres.setStandaard(new PersoonAdresStandaardGroepBericht());
            adres.getStandaard().setAangeverAdreshouding(aangeverAdreshouding);
            persoon.getAdressen().add(adres);
        } else {
            persoon.setAdressen(null);
        }
        return persoon;
    }

    private PersoonBericht maakNieuwePersoonMetMigratie(final String aangeverAdreshoudingCode) {
        return maakNieuwePersoon(true, false, aangeverAdreshoudingCode);
    }

    private PersoonBericht maakNieuwePersoonZonderMigratie(final String aangeverAdreshoudingCode) {
        return maakNieuwePersoon(false, false, aangeverAdreshoudingCode);
    }

    private PersoonView maakHuidigePersoon(final int geboorteDatum, final SoortIndicatie soortIndicatie) {
        final PersoonHisVolledigImplBuilder persoonBuilder =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwGeboorteRecord(geboorteDatum)
                    .datumGeboorte(geboorteDatum).eindeRecord();
        if (null != soortIndicatie) {
            if (soortIndicatie == SoortIndicatie.INDICATIE_ONDER_CURATELE) {
                persoonBuilder.voegPersoonIndicatieOnderCurateleToe(
                        new PersoonIndicatieOnderCurateleHisVolledigImplBuilder()
                                .nieuwStandaardRecord(REGISTRATIE_DATUM, null, REGISTRATIE_DATUM).waarde(Ja.J).eindeRecord().build());
            } else {
                persoonBuilder.voegPersoonIndicatieStaatloosToe(
                        new PersoonIndicatieStaatloosHisVolledigImplBuilder()
                                .nieuwStandaardRecord(REGISTRATIE_DATUM, null, REGISTRATIE_DATUM).waarde(Ja.J).eindeRecord().build());
            }
        }
        final PersoonHisVolledigImpl persoon = persoonBuilder.build();
        return new PersoonView(persoon);
    }

    private ActieBericht bouwActieRegistratieAdres() {
        final ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setTijdstipRegistratie(TIJDSTIP_REGISTRATIE);
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(ACTIE_DATUM));
        return actie;
    }

    private ActieBericht bouwActieRegistratieMigratie() {
        final ActieBericht actie = new ActieRegistratieMigratieBericht();
        actie.setTijdstipRegistratie(TIJDSTIP_REGISTRATIE);
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(ACTIE_DATUM));
        return actie;
    }

    private ActieBericht bouwActieRegistratieGeboorte() {
        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setTijdstipRegistratie(TIJDSTIP_REGISTRATIE);
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(ACTIE_DATUM));
        return actie;
    }

}
