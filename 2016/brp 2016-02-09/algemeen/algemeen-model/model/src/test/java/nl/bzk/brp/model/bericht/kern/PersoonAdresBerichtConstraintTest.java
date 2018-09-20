/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;


public class PersoonAdresBerichtConstraintTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * PersoonAdres combinaties:
     * In alle gevallen verplicht : Soort, Reden Wijziging (NRI: ?)
     * Aangever moet ingevuld zijn als Reden Wijziging = 'P', anders moet null.
     * <p/>
     * In alle nederlandse adressen: Land=NL, DatumAanvang Adreshouding
     * BAG: Gem, NOR, Afk.NOR, HuisNr, Plaats [GemDeel, Huiletter, HuisnrToev]
     * AdressObject, IdentificatieCode
     * tov = null, locatieomsch = null
     * A1: Gem, Afk.NOR, HuisNr, [GemDeel, Huiletter, HuisnrToev, tov]
     * AdressObject=null, IdentificatieCode = null, NOR = null, Plaats = null
     * locatieomsch = null
     * A2: Gem, [GemDeel]
     * AdressObject=null, IdentificatieCode = null, NOR = null, Afk.NOR = null, Plaats = null
     * Huiletter=null, HuisnrToev=null, tov = null, locatieomsch = null
     * A3: Gem, [GemDeel], locatieomsch
     * AdressObject=null, IdentificatieCode = null, NOR = null, Afk.NOR = null, Plaats = null
     * Huiletter=null, HuisnrToev=null, tov=null
     * <p/>
     * Buitenlandsadres: regel 2 [regel 1 [regel 3]] [regel 4] [regel 5] [regel 6] [Datum vertrek+]
     * Alle andere binnenlandse velden null.
     * Datum vertrek mag niet nul zijn voor de buitenlands adres in de reeks, daarna moet null.
     * DAA moet null, Land= Niet NL
     */

    @Test
    public void testBRAL1118RedenWijzigingNull() {
        /** P[ersoon] A[mbtshalve] M[initeriele Besluit B[AG wijziging] I[nfrastructurele wijziging]. */
        // bij rdnWijzingAdres = 'P', dan moet AangeverAdreshouding ingevuld zijn (en anders moet leeg)
        PersoonAdresStandaardGroepBericht groep = new PersoonAdresStandaardGroepBericht();
        groep.setRedenWijziging(null);
        groep.setRedenWijzigingCode(null);

        // moet null zijn.
        groep.setAangeverAdreshoudingCode(null);
        groep.setAangeverAdreshouding(null);
        Assert.assertEquals(0, validator.validate(groep, Default.class).size());

        // moet null zijn.
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_PARTNER);
        groep.setAangeverAdreshoudingCode(
                StatischeObjecttypeBuilder.AANGEVER_PARTNER.getWaarde().getCode().getWaarde());
        Assert.assertEquals(1, validator.validate(groep, Default.class).size());
    }

    @Test
    public void testBRAL1118RedenWijzigingPersoon() {
        /** P[ersoon] A[mbtshalve] M[initeriele Besluit B[AG wijziging] I[nfrastructurele wijziging]. */
        // bij rdnWijzingAdres = 'P', dan moet AangeverAdreshouding ingevuld zijn (en anders moet leeg)

        PersoonAdresStandaardGroepBericht groep = new PersoonAdresStandaardGroepBericht();
        groep.setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_PERSOON);
        groep.setRedenWijzigingCode(
                StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_PERSOON.getWaarde().getCode().getWaarde());

        // mag niet null zijn.
        groep.setAangeverAdreshoudingCode(null);
        groep.setAangeverAdreshouding(null);
        Assert.assertEquals(1, validator.validate(groep, Default.class).size());

        // mag niet null zijn; 'P" is OK
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_PARTNER);
        groep.setAangeverAdreshoudingCode(
                StatischeObjecttypeBuilder.AANGEVER_PARTNER.getWaarde().getCode().getWaarde());
        Assert.assertEquals(0, validator.validate(groep, Default.class).size());
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_VERZORGER);
        groep.setAangeverAdreshoudingCode(StatischeObjecttypeBuilder.AANGEVER_VERZORGER.getWaarde().getCode()
                .getWaarde());
        Assert.assertEquals(0, validator.validate(groep, Default.class).size());
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_GEZAGHOUDER);
        groep.setAangeverAdreshoudingCode(StatischeObjecttypeBuilder.AANGEVER_GEZAGHOUDER.getWaarde().getCode()
                .getWaarde());
        Assert.assertEquals(0, validator.validate(groep, Default.class).size());

    }

    @Test
    public void testBRAL1118RedenWijzigingGeenPersoon() {
        /** P[ersoon] A[mbtshalve] M[initeriele Besluit B[AG wijziging] I[nfrastructurele wijziging]. */
        // bij rdnWijzingAdres = 'P', dan moet AangeverAdreshouding ingevuld zijn (en anders moet leeg)

        PersoonAdresStandaardGroepBericht groep = new PersoonAdresStandaardGroepBericht();
        groep.setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_MINISTER);
        groep.setRedenWijzigingCode(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_MINISTER.getWaarde().getCode()
                .getWaarde());

        // moet null zijn.
        groep.setAangeverAdreshoudingCode(null);
        groep.setAangeverAdreshouding(null);
        Assert.assertEquals(0, validator.validate(groep, Default.class).size());

        // fout bij elk NIET null, omdat reden is geen persoon (spec vs 1.1).
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_PARTNER);
        groep.setAangeverAdreshoudingCode(
                StatischeObjecttypeBuilder.AANGEVER_PARTNER.getWaarde().getCode().getWaarde());
        Assert.assertEquals(1, validator.validate(groep, Default.class).size());
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_VERZORGER);
        groep.setAangeverAdreshoudingCode(StatischeObjecttypeBuilder.AANGEVER_VERZORGER.getWaarde().getCode()
                .getWaarde());
        Assert.assertEquals(1, validator.validate(groep, Default.class).size());
        // etc, etc ...

        // extra testen voor andere redenen
        groep.setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_BAG);
        groep.setRedenWijzigingCode(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_BAG.getWaarde().getCode().getWaarde());

        groep.setAangeverAdreshoudingCode(null);
        groep.setAangeverAdreshouding(null);
        Assert.assertEquals(0, validator.validate(groep, Default.class).size());
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_PARTNER);
        groep.setAangeverAdreshoudingCode(
                StatischeObjecttypeBuilder.AANGEVER_PARTNER.getWaarde().getCode().getWaarde());
        Assert.assertEquals(1, validator.validate(groep, Default.class).size());
        // etc ...

        groep.setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_MINISTER);
        groep.setRedenWijzigingCode(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_MINISTER.getWaarde().getCode()
                .getWaarde());
        groep.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_PARTNER);
        groep.setAangeverAdreshoudingCode(
                StatischeObjecttypeBuilder.AANGEVER_PARTNER.getWaarde().getCode().getWaarde());
        Assert.assertEquals(1, validator.validate(groep, Default.class).size());
        // de rest geloof ik wel.
    }

    @Test
    public void testBRAL2025() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setLocatieomschrijving(new LocatieomschrijvingAttribuut("omschr"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        Assert.assertEquals(0, overtredingen.size());

        // Opmerking: er wordt niet op land gechecked in de annotation constraint, dit wordt afgeschermd door de xsd.
        persoonAdresBericht.getStandaard().setAfgekorteNaamOpenbareRuimte(
                new AfgekorteNaamOpenbareRuimteAttribuut("afg"));
        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        overtredingen = validator.validate(persoon, Default.class);
        Assert.assertEquals(1, overtredingen.size());
        ConstraintViolation cv = overtredingen.iterator().next();
        Assert.assertEquals("BRAL2025", cv.getMessage());
        Assert.assertEquals(Regel.BRAL2025, haalRegelCodeOp(cv));
    }

    @Test
    public void testBRAL2027VoorwaardeVoldoetNLenAangifteDoorPersoon() {
        PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"), null)));

        // BRAL1118
        adresStandaardGroepBericht.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_INGESCHREVENE);

        // BRAL2033
        adresStandaardGroepBericht.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        adresStandaardGroepBericht.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20130524));
        adresStandaardGroepBericht.setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);

        // BRAL2032
        adresStandaardGroepBericht
                .setIdentificatiecodeAdresseerbaarObject(new IdentificatiecodeAdresseerbaarObjectAttribuut("abc"));

        // BRAL2095
        adresStandaardGroepBericht.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduidingAttribuut(
                "b"));
        adresStandaardGroepBericht.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("abc"));
        adresStandaardGroepBericht.setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);

        Set<ConstraintViolation<PersoonAdresStandaardGroepBericht>> overtredingen =
                validator.validate(adresStandaardGroepBericht, Default.class);
        Assert.assertEquals(1, overtredingen.size());
        ConstraintViolation cv = overtredingen.iterator().next();
        Assert.assertEquals("BRAL2027", cv.getMessage());
        Assert.assertEquals(Regel.BRAL2027, haalRegelCodeOp(cv));

        adresStandaardGroepBericht.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut("abc"));
        // BRAL2083
        adresStandaardGroepBericht.setHuisnummer(new HuisnummerAttribuut(1));

        overtredingen = validator.validate(adresStandaardGroepBericht, Default.class);
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2027VoorwaardeVoldoetNiet() {
        PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_BELGIE);
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"), null)));

        // BRAL1118
        adresStandaardGroepBericht.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_INGESCHREVENE);

        Set<ConstraintViolation<PersoonAdresStandaardGroepBericht>> overtredingen =
                validator.validate(adresStandaardGroepBericht, Default.class);
        Assert.assertEquals(0, overtredingen.size());

        // -- Nederland maar geen "Aangifte door persoon"
        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("A"), null)));

        // BRAL1118
        adresStandaardGroepBericht.setAangeverAdreshouding(null);

        // BRAL2033
        adresStandaardGroepBericht.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        adresStandaardGroepBericht.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20130524));
        adresStandaardGroepBericht.setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);

        // BRAL2032
        adresStandaardGroepBericht
                .setIdentificatiecodeAdresseerbaarObject(new IdentificatiecodeAdresseerbaarObjectAttribuut("abc"));

        // BRAL2095
        adresStandaardGroepBericht.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduidingAttribuut(
                "b"));
        adresStandaardGroepBericht.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("abc"));
        adresStandaardGroepBericht.setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);

        overtredingen = validator.validate(adresStandaardGroepBericht, Default.class);
        Assert.assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRBY0172() {
        LandGebied land = TestLandGebiedBuilder.maker()
            .metCode(10)
            .metNaam("naam")
            .metAlpha2Naam("naam")
            .metAanvangGeldigheid(20130101)
            .metEindeGeldigheid(20130201).maak();

        PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setLandGebied(new LandGebiedAttribuut(land));
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"), null)));
        adresStandaardGroepBericht.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20120101));

        // BRAL1118
        adresStandaardGroepBericht.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_INGESCHREVENE);

        Set<ConstraintViolation<PersoonAdresStandaardGroepBericht>> overtredingen =
                validator.validate(adresStandaardGroepBericht, Default.class);
        Assert.assertEquals(1, overtredingen.size());

        // Test binnen de range
        adresStandaardGroepBericht.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20130110));

        overtredingen = validator.validate(adresStandaardGroepBericht, Default.class);
        Assert.assertEquals(0, overtredingen.size());
    }

    private Regel haalRegelCodeOp(final ConstraintViolation cv) {
        return (Regel) cv.getConstraintDescriptor().getAttributes().get("code");
    }
}
