/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FormeleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAanschrijvingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsgemeenteHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsverantwoordelijkheidHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonEUVerkiezingenHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIDHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonImmigratieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonOpschortingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonPersoonskaartHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonUitsluitingNLKiesrechtHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVerblijfsrechtHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaam;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.Lo3Herkomst;

import org.junit.Assert;
import org.junit.Test;

/**
 * De inhoudelijke attributen, actie en documenten zijn allemaal al getest in de andere (specifieke) tests. Deze test is
 * alleen voor de aantallen records om te testen dat de loops goed gaan.
 */
public class BrpPersoonslijstMapperTest extends BrpAbstractTest {
    private static final Timestamp TIMESTAMP_REGISTRATIE = timestamp("197001010000000");

    @Inject
    private BrpPersoonslijstMapper mapper;

    @Test
    public void testNull() {
        final BrpPersoonslijst result = mapper.mapNaarMigratie(null);
        Assert.assertNull(result);
    }

    @Test
    public void testEmpty() {
        final BrpPersoonslijst result = mapper.mapNaarMigratie(new Persoon());
        Assert.assertNotNull(result);
    }

    @Test
    public void testBasisStapels() {
        final Persoon persoon = new Persoon();
        persoon.addPersoonAanschrijvingHistorie(vul(new PersoonAanschrijvingHistorie()));
        persoon.addPersoonAanschrijvingHistorie(vul(new PersoonAanschrijvingHistorie()));
        persoon.addPersoonAanschrijvingHistorie(vul(new PersoonAanschrijvingHistorie()));
        final PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.addPersoonAdresHistorie(vul(new PersoonAdresHistorie()));
        persoonAdres.addPersoonAdresHistorie(vul(new PersoonAdresHistorie()));
        persoon.addPersoonAdres(persoonAdres);
        persoon.addPersoonBijhoudingsgemeenteHistorie(vul(new PersoonBijhoudingsgemeenteHistorie()));
        persoon.addPersoonBijhoudingsgemeenteHistorie(vul(new PersoonBijhoudingsgemeenteHistorie()));
        persoon.addPersoonBijhoudingsverantwoordelijkheidHistorie(vul(new PersoonBijhoudingsverantwoordelijkheidHistorie()));
        persoon.addPersoonBijhoudingsverantwoordelijkheidHistorie(vul(new PersoonBijhoudingsverantwoordelijkheidHistorie()));
        persoon.addPersoonBijhoudingsverantwoordelijkheidHistorie(vul(new PersoonBijhoudingsverantwoordelijkheidHistorie()));
        persoon.addPersoonEUVerkiezingenHistorie(vul(new PersoonEUVerkiezingenHistorie()));
        persoon.addPersoonEUVerkiezingenHistorie(vul(new PersoonEUVerkiezingenHistorie()));
        persoon.addPersoonGeboorteHistorie(vul(new PersoonGeboorteHistorie()));
        persoon.addPersoonGeslachtsaanduidingHistorie(vul(new PersoonGeslachtsaanduidingHistorie()));
        persoon.addPersoonGeslachtsaanduidingHistorie(vul(new PersoonGeslachtsaanduidingHistorie()));
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent(1, "Gesl"));
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent(2, "acht"));
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent(3, "snaam"));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie()));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie()));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie()));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie()));
        persoon.addPersoonImmigratieHistorie(vul(new PersoonImmigratieHistorie()));
        persoon.addPersoonInschrijvingHistorie(vulInschrijving(new PersoonInschrijvingHistorie(), 1L));
        persoon.addPersoonInschrijvingHistorie(vulInschrijving(new PersoonInschrijvingHistorie(), 2L));
        persoon.addPersoonInschrijvingHistorie(vulInschrijving(new PersoonInschrijvingHistorie(), 3L));
        persoon.addPersoonNationaliteit(nationaliteit("0002"));
        persoon.addPersoonNationaliteit(nationaliteit("0055"));
        persoon.addPersoonOpschortingHistorie(vul(new PersoonOpschortingHistorie()));
        persoon.addPersoonOpschortingHistorie(vul(new PersoonOpschortingHistorie()));
        persoon.addPersoonOpschortingHistorie(vul(new PersoonOpschortingHistorie()));
        persoon.addPersoonOpschortingHistorie(vul(new PersoonOpschortingHistorie()));
        persoon.addPersoonOverlijdenHistorie(vul(new PersoonOverlijdenHistorie()));
        persoon.addPersoonOverlijdenHistorie(vul(new PersoonOverlijdenHistorie()));
        persoon.addPersoonOverlijdenHistorie(vul(new PersoonOverlijdenHistorie()));
        persoon.addPersoonPersoonskaartHistorie(vul(new PersoonPersoonskaartHistorie()));
        persoon.addPersoonPersoonskaartHistorie(vul(new PersoonPersoonskaartHistorie()));
        persoon.addPersoonReisdocument(reisdocument("Reisdoc-1"));
        persoon.addPersoonReisdocument(reisdocument("Reisdoc-2"));
        persoon.addPersoonReisdocument(reisdocument("Reisdoc-3"));
        persoon.addPersoonSamengesteldeNaamHistorie(vulSamengesteldeNaam(new PersoonSamengesteldeNaamHistorie()));
        persoon.addPersoonSamengesteldeNaamHistorie(vulSamengesteldeNaam(new PersoonSamengesteldeNaamHistorie()));
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(vul(new PersoonUitsluitingNLKiesrechtHistorie()));
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(vul(new PersoonUitsluitingNLKiesrechtHistorie()));
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(vul(new PersoonUitsluitingNLKiesrechtHistorie()));
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(vul(new PersoonUitsluitingNLKiesrechtHistorie()));
        persoon.addPersoonVerblijfsrechtHistorie(vul(new PersoonVerblijfsrechtHistorie()));
        persoon.addPersoonVerblijfsrechtHistorie(vul(new PersoonVerblijfsrechtHistorie()));
        persoon.addPersoonVoornaam(voornaam(1));
        persoon.addPersoonVoornaam(voornaam(2));
        persoon.addPersoonVoornaam(voornaam(3));
        persoon.addPersoonVoornaam(voornaam(4));

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon);

        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getAanschrijvingStapel().size());
        Assert.assertEquals(2, result.getAdresStapel().size());
        Assert.assertEquals(1, result.getAfgeleidAdministratiefStapel().size());
        Assert.assertEquals(2, result.getBijhoudingsgemeenteStapel().size());
        Assert.assertEquals(3, result.getBijhoudingsverantwoordelijkheidStapel().size());
        Assert.assertEquals(2, result.getEuropeseVerkiezingenStapel().size());
        Assert.assertEquals(1, result.getGeboorteStapel().size());
        Assert.assertEquals(2, result.getGeslachtsaanduidingStapel().size());
        Assert.assertEquals(3, result.getGeslachtsnaamcomponentStapels().size());
        Assert.assertEquals(4, result.getIdentificatienummerStapel().size());
        Assert.assertEquals(1, result.getImmigratieStapel().size());
        Assert.assertEquals(3, result.getInschrijvingStapel().size());
        Assert.assertEquals(2, result.getNationaliteitStapels().size());
        Assert.assertEquals(4, result.getOpschortingStapel().size());
        Assert.assertEquals(3, result.getOverlijdenStapel().size());
        Assert.assertEquals(2, result.getPersoonskaartStapel().size());
        Assert.assertEquals(3, result.getReisdocumentStapels().size());
        Assert.assertEquals(2, result.getSamengesteldeNaamStapel().size());
        Assert.assertEquals(4, result.getUitsluitingNederlandsKiesrechtStapel().size());
        Assert.assertEquals(2, result.getVerblijfsrechtStapel().size());
        Assert.assertEquals(4, result.getVoornaamStapels().size());
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_01, result.getVoornaamStapels().get(0)
                .getMeestRecenteElement().getActieInhoud().getLo3Herkomst().getCategorie());
        Assert.assertEquals(0, result.getVoornaamStapels().get(0).getMeestRecenteElement().getActieInhoud()
                .getLo3Herkomst().getVoorkomen());
    }

    private PersoonInschrijvingHistorie vulInschrijving(
            final PersoonInschrijvingHistorie historie,
            final long versienummer) {
        historie.setVersienummer(versienummer);
        historie.setDatumInschrijving(new BigDecimal("20040101"));
        return vul(historie);
    }

    private PersoonGeslachtsnaamcomponent geslachtsnaamcomponent(final int volgnummer, final String naam) {
        final PersoonGeslachtsnaamcomponent result = new PersoonGeslachtsnaamcomponent();
        result.setVolgnummer(volgnummer);
        final PersoonGeslachtsnaamcomponentHistorie historie = vul(new PersoonGeslachtsnaamcomponentHistorie());
        historie.setNaam(naam);
        result.addPersoonGeslachtsnaamcomponentHistorie(historie);
        result.addPersoonGeslachtsnaamcomponentHistorie(historie);
        return result;
    }

    private PersoonNationaliteit nationaliteit(final String nationaliteitCode) {
        final PersoonNationaliteit result = new PersoonNationaliteit();
        result.setNationaliteit(new Nationaliteit());
        result.getNationaliteit().setNationaliteitcode(new BigDecimal(nationaliteitCode));
        result.addPersoonNationaliteitHistorie(vul(new PersoonNationaliteitHistorie()));
        return result;
    }

    private PersoonReisdocument reisdocument(final String soortNederlandsReisdocument) {
        final PersoonReisdocument result = new PersoonReisdocument();
        result.setSoortNederlandsReisdocument(new SoortNederlandsReisdocument());
        result.getSoortNederlandsReisdocument().setCode(soortNederlandsReisdocument);

        final PersoonReisdocumentHistorie historie = vul(new PersoonReisdocumentHistorie());
        historie.setNummer("2342-RE-XXX1");
        historie.setDatumIngang(new BigDecimal("20120101"));
        historie.setDatumUitgifte(new BigDecimal("20120101"));
        historie.setAutoriteitVanAfgifteReisdocument(new AutoriteitVanAfgifteReisdocument());
        historie.getAutoriteitVanAfgifteReisdocument().setCode("0518");
        historie.setDatumVoorzieneEindeGeldigheid(new BigDecimal("20170101"));

        result.addPersoonReisdocumentHistorieSet(historie);
        return result;
    }

    private PersoonSamengesteldeNaamHistorie vulSamengesteldeNaam(final PersoonSamengesteldeNaamHistorie historie) {
        historie.setIndicatieAlgoritmischAfgeleid(Boolean.TRUE);
        historie.setIndicatieNamenreeksAlsGeslachtsnaam(Boolean.FALSE);
        return vul(historie);
    }

    private PersoonVoornaam voornaam(final int volgnummer) {
        final PersoonVoornaam result = new PersoonVoornaam();
        result.setVolgnummer(volgnummer);
        final BRPActie brpActie1 = new BRPActie();
        brpActie1.setId(1L);
        brpActie1.setDatumTijdOntlening(TIMESTAMP_REGISTRATIE);
        brpActie1.setDatumTijdRegistratie(TIMESTAMP_REGISTRATIE);
        final Partij partij = new Partij();
        partij.setGemeentecode(BigDecimal.valueOf(9999));
        brpActie1.setPartij(partij);
        final BRPActie brpActie2 = new BRPActie();
        brpActie2.setId(2L);
        brpActie2.setDatumTijdOntlening(TIMESTAMP_REGISTRATIE);
        brpActie2.setDatumTijdRegistratie(TIMESTAMP_REGISTRATIE);
        brpActie2.setPartij(partij);
        final Lo3Herkomst lo3Herkomst1 = new Lo3Herkomst(1, 0, 0);
        final Lo3Herkomst lo3Herkomst2 = new Lo3Herkomst(1, 0, 1);
        brpActie1.setLo3Herkomst(lo3Herkomst1);
        brpActie2.setLo3Herkomst(lo3Herkomst2);

        final PersoonVoornaamHistorie persoon1 = new PersoonVoornaamHistorie();
        persoon1.setActieInhoud(brpActie1);
        final PersoonVoornaamHistorie persoon2 = new PersoonVoornaamHistorie();
        persoon2.setActieInhoud(brpActie1);

        result.addPersoonVoornaamHistorie(vul(persoon1));
        result.addPersoonVoornaamHistorie(vul(persoon2));
        return result;
    }

    @Test
    public void testIndicatieStapels() {
        final Persoon persoon = new Persoon();
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER,
                new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT,
                new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT,
                new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.DERDE_HEEFT_GEZAG, new PersoonIndicatieHistorie(),
                new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.GEPRIVILEGIEERDE, new PersoonIndicatieHistorie(),
                new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.ONDER_CURATELE, new PersoonIndicatieHistorie(),
                new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.STATENLOOS, new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER,
                new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie()));
        persoon.addPersoonIndicatie(indicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, new PersoonIndicatieHistorie(),
                new PersoonIndicatieHistorie(), new PersoonIndicatieHistorie()));

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getBehandeldAlsNederlanderIndicatieStapel().size());
        Assert.assertEquals(2, result.getBelemmeringVerstrekkingReisdocumentIndicatieStapel().size());
        Assert.assertEquals(3, result.getBezitBuitenlandsReisdocumentIndicatieStapel().size());
        Assert.assertEquals(4, result.getDerdeHeeftGezagIndicatieStapel().size());
        Assert.assertEquals(3, result.getGeprivilegieerdeIndicatieStapel().size());
        Assert.assertEquals(2, result.getOnderCurateleIndicatieStapel().size());
        Assert.assertEquals(1, result.getStatenloosIndicatieStapel().size());
        Assert.assertEquals(2, result.getVastgesteldNietNederlanderIndicatieStapel().size());
        Assert.assertEquals(3, result.getVerstrekkingsbeperkingStapel().size());
    }

    private PersoonIndicatie indicatie(
            final SoortIndicatie soortIndicatie,
            final PersoonIndicatieHistorie... histories) {
        final PersoonIndicatie result = new PersoonIndicatie();
        result.setSoortIndicatie(soortIndicatie);
        for (final PersoonIndicatieHistorie historie : histories) {
            result.addPersoonIndicatieHistorie(vul(historie));
        }
        return result;
    }

    private static <T extends FormeleHistorie> T vul(final T data) {
        data.setDatumTijdRegistratie(TIMESTAMP_REGISTRATIE);
        return data;
    }

}
