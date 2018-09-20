/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.binding;

import java.util.Iterator;
import java.util.Set;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.CategorieSoortDocument;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.FunctieAdres;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Geslachtsaanduiding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortRelatie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonBijhoudingsgemeente;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonBijhoudingsverantwoordelijkheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonInschrijving;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Bron;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonVoornaam;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonGeslachtsnaamcomponent;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class ActieBindingTest extends AbstractBindingTest<BRPActie> {

    @Override
    protected Class<BRPActie> getBindingClass() {
        return BRPActie.class;
    }

    @Test
    public void testNieuwGeboreneMetErkenningActieUnmarshalling() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "\t\t<brp:actie xmlns:brp=\"" + BRP_NAMESPACE + "\">\n" +
                "\t\t\t<brp:soort>Dummy waarde</brp:soort>\n" +
                "\t\t\t<brp:partij>0530</brp:partij>\n" +
                "\t\t\t<brp:datumAanvangGeldigheid>20111120</brp:datumAanvangGeldigheid>\n" +
                "\t\t\t<brp:tijdstipOntlening>2011-11-21T09:05:12</brp:tijdstipOntlening>\n" +
                "\t\t\n" +
                "\t\t\t<!-- Onderstaande onder voorbehoud verdere uitwerking onderwerp Verantwoording -->\n" +
                "\t\t\t<!-- Voor bronnen en verwijzingen verderop in bericht ID/REF-constructie (Tim)  implementeren " +
                "-->\t\n"
                +
                "\t\t\t<brp:bronnen>\n" +
                "\t\t\t\t<!-- Reguliere akte BS -->\n" +
                "\t\t\t\t<brp:bron>\t\t\t\n" +
                "\t\t\t\t\t<brp:identificatieBronInBericht>1</brp:identificatieBronInBericht>\n" +
                "\t\t\t\t\t<brp:soort>Nederlandse Akte</brp:soort>\n" +

                "\t\t\t\t\t<brp:akteNummer>1AA0001</brp:akteNummer>\n" +
                "\t\t\t\t\t<brp:partij>0530</brp:partij>\t\n" +

                "\t\t\t\t</brp:bron>\n" +
                "\t\t\t\t<!-- Toepassing Nederlands Recht - Nationaliteit -->\n" +

                "\t\t\t\t<brp:bron>\n" +
                "\t\t\t\t\t<brp:identificatieBronInBericht>2</brp:identificatieBronInBericht>\n" +
                "\t\t\t\t\t<brp:soort>Nederlandse Akte</brp:soort>\n" +
                "\t\t\t\t\t<brp:identificatie>NR001</brp:identificatie>\n" +
                "\t\t\t\t\t<brp:omschrijving>Nederlands recht; Nationaliteit</brp:omschrijving>\n" +
                "\t\t\t\t\t<brp:partij>0530</brp:partij>\t\n" +
                "\t\t\t\t</brp:bron>\n" +
                "\t\t\t\t<!-- Toepassing Nederlands Recht - Adres moeder -->\t\t\t\n" +
                "\t\t\t\t<brp:bron>\n" +
                "\t\t\t\t\t<brp:identificatieBronInBericht>3</brp:identificatieBronInBericht>\n" +
                "\t\t\t\t\t<brp:soort>Nederlandse Akte</brp:soort>\n" +
                "\t\t\t\t\t<brp:identificatie>NR021</brp:identificatie>\n" +
                "\t\t\t\t\t<brp:omschrijving>Nederlands recht; Inschrijving</brp:omschrijving>\n" +
                "\t\t\t\t\t<brp:partij>0530</brp:partij>\t\n" +
                "\t\t\t\t</brp:bron>\n" +

                "\t\t\t</brp:bronnen>\n" +
                "\t\t\t\n" +
                "\t\t\t<brp:familierechtelijkeBetrekking>\n" +
                "\t\t\t\t<brp:kind>\n" +
                "\t\t\t\t\t<brp:identificatieNummer>\n" +
                "\t\t\t\t\t\t<brp:burgerservicenummer>103962438</brp:burgerservicenummer>\n" +
                "\t\t\t\t\t\t<brp:administratienummer>2301342693</brp:administratienummer>\n" +
                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t</brp:identificatieNummer>\n" +
                "\n" +
                "\t\t\t\t\t<brp:geboorte>\n" +
                "\t\t\t\t\t\t<brp:datumGeboorte>20111120</brp:datumGeboorte>\n" +
                "\t\t\t\t\t\t<brp:gemeente>0530</brp:gemeente>\n" +
                "\t\t\t\t\t\t<brp:woonplaats>Hellevoetsluis</brp:woonplaats>\n" +
                "\t\t\t\t\t\t<brp:land>6030</brp:land>\n" +
                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t</brp:geboorte>\n" +
                "\n" +
                "\t\t\t\t\t<brp:geslachtsAanduiding>\n" +
                "\t\t\t\t\t\t<brp:geslachtsAanduiding>M</brp:geslachtsAanduiding>\n" +
                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t</brp:geslachtsAanduiding>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t<brp:voornamen>\n" +
                "\t\t\t\t\t\t<brp:voornaam>\n" +
                "\t\t\t\t\t\t\t<brp:volgnummer>1</brp:volgnummer>\n" +
                "\t\t\t\t\t\t\t<brp:naam>Francois</brp:naam>\n" +
                "\t\t\t\t\t\t</brp:voornaam>\n" +
                "\t\t\t\t\t\t<brp:voornaam>\n" +
                "\t\t\t\t\t\t\t<brp:volgnummer>2</brp:volgnummer>\n" +
                "\t\t\t\t\t\t\t<brp:naam>Samuel</brp:naam>\n" +
                "\t\t\t\t\t\t</brp:voornaam>\n" +

                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +

                "\t\t\t\t\t</brp:voornamen>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t<brp:geslachtsnaamComponenten>\t\t\t\n" +
                "\t\t\t\t\t\t<brp:geslachtsnaamComponent>\n" +
                "\t\t\t\t\t\t\t<brp:volgnummer>1</brp:volgnummer>\n" +
                "\t\t\t\t\t\t\t<brp:voorvoegsel>van</brp:voorvoegsel>\n" +
                "\t\t\t\t\t\t\t<brp:geslachtsnaam>Persona</brp:geslachtsnaam>\n" +
                "\t\t\t\t\t\t</brp:geslachtsnaamComponent>\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t</brp:geslachtsnaamComponenten>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t<brp:nationaliteiten>\n" +
                "\t\t\t\t\t\t<brp:nationaliteit>\n" +
                "\t\t\t\t\t\t\t<brp:nationaliteit>0001</brp:nationaliteit>\n" +
                "\t\t\t\t\t\t\t<brp:redenVerkrijging>001</brp:redenVerkrijging>\n" +
                "\t\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>2</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t\t</brp:nationaliteit>\n" +
                "\t\t\t\t\t</brp:nationaliteiten>\n" +
                "\t\t\t\t</brp:kind>\n" +
                "\t\t\n" +
                "\t\t\t\t<!-- Aandachtspunt: onderscheid/herkenbaarheid moeder/vader!  -->\t\n" +
                "\t\t\t\t<brp:ouderBetrokkenheid>\n" +

                "\t\t\t\t\t<brp:ouder>\n" +
                "\t\t\t\t\t\t<brp:identificatieNummer>\n" +
                "\t\t\t\t\t\t\t<brp:burgerservicenummer>238651974</brp:burgerservicenummer>\n" +
                "\t\t\t\t\t\t</brp:identificatieNummer>\n" +
                "\t\t\t\t\t\t<!-- Laatste wijziging (versienummer) bijgehouden persoon; eerder opgehaald bij " +
                "bevraging -->\n"
                +
                "\t\t\t\t\t\t<brp:afgeleidAdministratief>\n" +
                "\t\t\t\t\t\t\t<brp:laatsteWijziging>2008112109051303</brp:laatsteWijziging>\n" +
                "\t\t\t\t\t\t</brp:afgeleidAdministratief>\n" +
                "\t\t\t\t\t\t<brp:datumIngangFamiliaireBetrekking>20111120</brp:datumIngangFamiliaireBetrekking>\n" +
                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t</brp:ouder>\n" +
                "\t\t\t\n" +

                "\t\t\t\t\t<!-- Tweede ouder (Vader) is niet ingeschrevene; indien niet gewenst dan constructie " +
                "eerste ouder volgen-->\n"
                +
                "\t\t\t\t\t<brp:ouder>\n" +
                "\t\t\t\t\t\t<brp:geboorte>\n" +
                "\t\t\t\t\t\t\t<brp:datumGeboorte>19850919</brp:datumGeboorte>\n" +
                "\t\t\t\t\t\t\t<brp:buitenlandseGeboorteplaats>Maceio</brp:buitenlandseGeboorteplaats>\n" +
                "\t\t\t\t\t\t\t<brp:landGeboorte>6008</brp:landGeboorte>\n" +
                "\t\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t\t</brp:geboorte>\n" +
                "\t\t\t\t\t\t<brp:geslachtsAanduiding>\n" +
                "\t\t\t\t\t\t\t<brp:geslachtsAanduiding>M</brp:geslachtsAanduiding>\n" +
                "\t\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t\t</brp:geslachtsAanduiding>\n" +
                "\t\t\t\t\t\t<brp:samengesteldeNaam> \n" +
                "\t\t\t\t\t\t\t<brp:voornamen>Willy</brp:voornamen>\n" +
                "\t\t\t\t\t\t\t<brp:voorvoegsel>dos</brp:voorvoegsel>\n" +
                "\t\t\t\t\t\t\t<brp:geslachtsnaam>Santos da Vitoria</brp:geslachtsnaam>\n" +
                "\t\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t\t</brp:samengesteldeNaam>\n" +
                "\t\t\t\t\t\t<brp:datumIngangFamiliaireBetrekking>20111122</brp:datumIngangFamiliaireBetrekking>\n" +
                "\t\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t\t</brp:verantwoordingen>\t\n" +
                "\t\t\t\t\t</brp:ouder>\n" +

                "\t\t\t\t</brp:ouderBetrokkenheid>\n" +

                "\t\t\t</brp:familierechtelijkeBetrekking>\n" +
                "\t\t</brp:actie>\t";

        BRPActie actie = unmarshalObject(xml);

        Assert.assertEquals(SoortActie.DUMMY_WAARDE, actie.getIdentiteit().getSoort());
        Assert.assertEquals(Integer.valueOf(530), actie.getIdentiteit().getPartij().getIdentiteit().getID().getWaarde());
        Assert.assertEquals(Integer.valueOf(20111120), actie.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(3, actie.getBronnen().size());

        //1 root object
        Assert.assertEquals(1, actie.getRootObjecten().size());
        Assert.assertTrue(actie.getRootObjecten().get(0) instanceof Relatie);
        final Relatie relatie = (Relatie) actie.getRootObjecten().get(0);

        //3 betrokkenheden (Kind, Ouder, Ouder)
        Assert.assertEquals(3, relatie.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getIdentiteit().getSoort());
        //todo datum ingang relatie

        Persoon kind = null;
        Persoon moeder = null;
        Persoon vader = null;
        for (Betrokkenheid betr : relatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betr.getIdentiteit().getRol()) {
                kind = betr.getIdentiteit().getBetrokkene();
                //Assert.assertEquals(kind.getDatumGeboorte(), betr.getDatumAanvang());
            } else if (SoortBetrokkenheid.OUDER == betr.getIdentiteit().getRol()) {
                if (betr.getIdentiteit().getBetrokkene().getIdentificatienummers() != null &&
                        betr.getIdentiteit().getBetrokkene().getIdentificatienummers().getBurgerservicenummer() != null)
                {
                    moeder = betr.getIdentiteit().getBetrokkene();
                    //datum ingang betrokkenheid:
                    Assert.assertEquals(Integer.valueOf(20111120), betr.getOuder().getDatumAanvang().getWaarde());
                } else {
                    vader = betr.getIdentiteit().getBetrokkene();
                    //datum ingang betrokkenheid
                    Assert.assertEquals(Integer.valueOf(20111122), betr.getOuder().getDatumAanvang().getWaarde());
                }
            }
        }

        //Kind, moeder, vader
        Assert.assertNotNull(kind);
        Assert.assertNotNull(moeder);
        Assert.assertNotNull(vader);

        if (kind != null) {

            PersoonInschrijving persoonInschrijving = kind.getInschrijving();
            Assert.assertNull(persoonInschrijving);

            PersoonBijhoudingsverantwoordelijkheid bijhoudingsverantwoordelijkheid = kind.getBijhoudingsverantwoordelijkheid();
            Assert.assertNull(bijhoudingsverantwoordelijkheid);

            PersoonBijhoudingsgemeente bijhoudingsgemeente = kind.getBijhoudingsgemeente();
            Assert.assertNull(bijhoudingsgemeente);

            Assert.assertEquals("103962438", kind.getIdentificatienummers().getBurgerservicenummer().getWaarde());
            Assert.assertEquals("2301342693", kind.getIdentificatienummers().getAdministratienummer().getWaarde());
            Assert.assertEquals(Integer.valueOf(20111120), kind.getGeboorte().getDatumGeboorte().getWaarde());
            Assert.assertEquals("0530", kind.getGeboorte().getGemeenteGeboorte().getGemeenteStandaard().getGemeentecode().getWaarde());
            Assert.assertEquals("Hellevoetsluis", kind.getGeboorte().getWoonplaatsGeboorte().getIdentiteit().getNaam().getWaarde());
            Assert.assertEquals("6030", kind.getGeboorte().getLandGeboorte().getIdentiteit().getLandcode().getWaarde());
            Assert.assertEquals(Geslachtsaanduiding.MAN, kind.getGeslachtsaanduiding().getGeslachtsaanduiding());

            Assert.assertEquals(2, kind.getVoornamen().size());
            Iterator<PersoonVoornaam> voorNamenIterator = kind.getVoornamen().iterator();
            PersoonVoornaam voornaam1 = voorNamenIterator.next();
            Assert.assertEquals(Integer.valueOf(1), voornaam1.getIdentiteit().getVolgnummer().getWaarde());
            Assert.assertEquals("Francois", voornaam1.getStandaard().getNaam().getWaarde());

            PersoonVoornaam voornaam2 = voorNamenIterator.next();
            Assert.assertEquals(Integer.valueOf(2), voornaam2.getIdentiteit().getVolgnummer().getWaarde());
            Assert.assertEquals("Samuel", voornaam2.getStandaard().getNaam().getWaarde());

            Assert.assertTrue(kind.getGeslachtsnaamcomponenten().size() == 1);
            PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = kind.getGeslachtsnaamcomponenten().iterator().next();
            Assert.assertEquals(Integer.valueOf(1), persoonGeslachtsnaamcomponent.getIdentiteit().getVolgnummer().getWaarde());
            Assert.assertEquals("van", persoonGeslachtsnaamcomponent.getStandaard().getVoorvoegsel().getWaarde());
            Assert.assertEquals("Persona", persoonGeslachtsnaamcomponent.getStandaard().getNaam().getWaarde());

            Assert.assertEquals(1, kind.getNationaliteiten().size());
            final PersoonNationaliteit persoonNationaliteit = kind.getNationaliteiten().iterator().next();
            Assert.assertEquals("0001", 
                    persoonNationaliteit.getIdentiteit().getNationaliteit().getIdentiteit().getNationaliteitcode().getWaarde());
            Assert.assertEquals(Integer.valueOf("1"),
                    persoonNationaliteit.getStandaard().getRedenVerkrijging().getIdentiteit().getID().getWaarde());

            Set<PersoonAdres> adressen = kind.getAdressen();
            Assert.assertNull(adressen);
        }

        if (moeder != null) {
            Assert.assertEquals("238651974", moeder.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        }

        if (vader != null) {
            Assert.assertEquals(Integer.valueOf(19850919), vader.getGeboorte().getDatumGeboorte().getWaarde());
            Assert.assertEquals("Maceio", vader.getGeboorte().getBuitenlandseGeboorteplaats().getWaarde());
            Assert.assertEquals("6008", vader.getGeboorte().getLandGeboorte().getIdentiteit().getLandcode().getWaarde());
            Assert.assertEquals(Geslachtsaanduiding.MAN, vader.getGeslachtsaanduiding().getGeslachtsaanduiding());
            /*
            Assert.assertEquals("Willy", vader.getVoornamen());
            Assert.assertEquals("dos", vader.getVoorvoegsel());
            Assert.assertEquals("Santos da Vitoria", vader.getGeslachtsnaam());
        }*/
        }
    }

    @Test
    @Ignore
    public void testNieuwGeboreneZonderErkenningActie() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<brp:actie xmlns:brp=\"" + BRP_NAMESPACE + "\">\n" +
                " <brp:soort>Inschrijving door Aangifte geboorte</brp:soort>\n" +
                " <brp:partij>0530</brp:partij>\n" +
                " <brp:datumAanvangGeldigheid>20111120</brp:datumAanvangGeldigheid>\n" +
                " <brp:tijdstipOntlening>2011-11-21T09:05:12</brp:tijdstipOntlening>\n" +
                "\n" +
                " <!-- Onderstaande onder voorbehoud verdere uitwerking onderwerp Verantwoording -->\n" +
                " <!-- Voor bronnen en verwijzingen verderop in bericht ID/REF-constructie (Tim)  implementeren -->\n" +
                " <brp:bronnen>\n" +
                " <!-- Reguliere akte BS -->\n" +
                "   <brp:bron>\n" +
                "       <brp:identificatieBronInBericht>1</brp:identificatieBronInBericht>\n" +
                "       <brp:soort>Nederlandse Akte</brp:soort>\n" +
                "       <brp:akteNummer>1AA0001</brp:akteNummer>\n" +
                "       <brp:partij>0530</brp:partij>\n" +
                "   </brp:bron>\n" +
                "   <!-- Toepassing Nederlands Recht - Nationaliteit -->\n" +
                "   <brp:bron>\n" +
                "       <brp:identificatieBronInBericht>2</brp:identificatieBronInBericht>\n" +
                "       <brp:soort>Nederlandse Wet</brp:soort>\n" +
                "       <brp:identificatie>NR001</brp:identificatie>\n" +
                "       <brp:omschrijving>Nederlands recht; Nationaliteit</brp:omschrijving>\n" +
                "       <brp:partij>0530</brp:partij>\n" +
                "   </brp:bron>\n" +
                "   <!-- Toepassing Nederlands Recht - Adres moeder -->\n" +
                "   <brp:bron>\n" +
                "       <brp:identificatieBronInBericht>3</brp:identificatieBronInBericht>\n" +
                "       <brp:soort>Nederlandse Wet</brp:soort>\n" +
                "       <brp:identificatie>NR021</brp:identificatie>\n" +
                "       <brp:omschrijving>Nederlands recht; Inschrijving</brp:omschrijving>\n" +
                "       <brp:partij>0530</brp:partij>\n" +
                "   </brp:bron>\n" +
                " </brp:bronnen>\n" +
                "\n" +
                " <brp:familierechtelijkeBetrekking>\n" +
                "   <brp:kind>\n" +
                "       <brp:inschrijving>\n" +
                "           <brp:datumInschrijving>20111120</brp:datumInschrijving>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "       </brp:inschrijving>\n" +
                "\n" +
                "       <brp:bijhoudingsVerantwoordelijkheid>\n" +
                "           <brp:code>C</brp:code>\n" +
                "           <brp:datumAanvangGeldigheid>20111120</brp:datumAanvangGeldigheid>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "       </brp:bijhoudingsVerantwoordelijkheid>\n" +
                "\n" +
                "       <brp:bijhoudingsGemeente>\n" +
                "           <brp:gemeente>0530</brp:gemeente>\n" +
                "           <brp:datumInschrijvingGemeente>20111120</brp:datumInschrijvingGemeente>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "               <brp:bron.identificatieBronInBericht>3</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "       </brp:bijhoudingsGemeente>\n" +
                "\n" +
                "       <brp:identificatieNummer>\n" +
                "           <brp:burgerservicenummer>103962438</brp:burgerservicenummer>\n" +
                "           <brp:administratienummer>2301342693</brp:administratienummer>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "       </brp:identificatieNummer>\n" +
                "\n" +
                "       <brp:geboorte>\n" +
                "           <brp:datumGeboorte>20111120</brp:datumGeboorte>\n" +
                "           <brp:gemeente>0530</brp:gemeente>\n" +
                "           <brp:woonplaats>Hellevoetsluis</brp:woonplaats>\n" +
                "           <brp:land>6030</brp:land>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "       </brp:geboorte>\n" +
                "\n" +
                "       <brp:geslachtsAanduiding>\n" +
                "           <brp:geslachtsAanduiding>M</brp:geslachtsAanduiding>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "       </brp:geslachtsAanduiding>\n" +
                "\n" +
                "       <brp:voornamen>\n" +
                "           <brp:voornaam>\n" +
                "               <brp:volgnummer>1</brp:volgnummer>\n" +
                "               <brp:naam>Francois</brp:naam>\n" +
                "           </brp:voornaam>\n" +
                "           <brp:voornaam>\n" +
                "              <brp:volgnummer>2</brp:volgnummer>\n" +
                "              <brp:naam>Samuel</brp:naam>\n" +
                "           </brp:voornaam>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "        </brp:voornamen>\n" +
                "\n" +
                "        <brp:geslachtsnaamComponenten>\n" +
                "           <brp:geslachtsnaamComponent>\n" +
                "               <brp:volgnummer>1</brp:volgnummer>\n" +
                "               <brp:voorvoegsel>van</brp:voorvoegsel>\n" +
                "               <brp:geslachtsnaam>Persona</brp:geslachtsnaam>\n" +
                "           </brp:geslachtsnaamComponent>\n" +
                "           <brp:verantwoordingen>\n" +
                "               <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "           </brp:verantwoordingen>\n" +
                "        </brp:geslachtsnaamComponenten>\n" +
                "\n" +
                "        <brp:nationaliteiten>\n" +
                "           <brp:nationaliteit>\n" +
                "               <brp:nationaliteit>0001</brp:nationaliteit>\n" +
                "               <brp:redenVerkrijging>001</brp:redenVerkrijging>\n" +
                "               <brp:verantwoordingen>\n" +
                "                   <brp:bron.identificatieBronInBericht>2</brp:bron.identificatieBronInBericht>\n" +
                "               </brp:verantwoordingen>\n" +
                "           </brp:nationaliteit>\n" +
                "        </brp:nationaliteiten>\n" +
                "\n" +
                "        <brp:adressen>\n" +
                "           <brp:adres>\n" +
                "               <brp:soort>W</brp:soort>\n" +
                "               <brp:redenWijziging>P</brp:redenWijziging>\n" +
                "               <brp:aangeverAdreshouding>G</brp:aangeverAdreshouding>\n" +
                "               <brp:datumAanvangAdreshouding>20111120</brp:datumAanvangAdreshouding>\n" +
                "               <brp:adresseerbaarObject>0530209876543210</brp:adresseerbaarObject>\n" +
                "               <brp:identificatieNummeraanduiding>0530209876543210</brp:identificatieNummeraanduiding>\n" +
                "               <brp:land>6030</brp:land>\n" +
                "               <brp:gemeente>0530</brp:gemeente>\n" +
                "               <brp:woonplaats>Hellevoetsluis</brp:woonplaats>\n" +
                "               <brp:naamOpenbareruimte>Martin Luther Kinglaan</brp:naamOpenbareruimte>\n" +
                "               <brp:afgekorteNaamOpenbareruimte>ML Kinglaan</brp:afgekorteNaamOpenbareruimte>\n" +
                "               <brp:huisnummer>130</brp:huisnummer>\n" +
                "               <brp:huisletter>A</brp:huisletter>\n" +
                "               <brp:postcode>3223RD</brp:postcode>\n" +
                "               <brp:verantwoordingen>\n" +
                "                   <brp:bron.identificatieBronInBericht>3</brp:bron.identificatieBronInBericht>\n" +
                "               </brp:verantwoordingen>\n" +
                "            </brp:adres>\n" +
                "          </brp:adressen>\n" +
                "                    </brp:kind>\n" +
                "\n" +
                "                    <!-- Aandachtspunt: onderscheid/herkenbaarheid moeder/vader!  -->\n" +
                "          <brp:ouderBetrokkenheid>\n" +
                "               <brp:ouder>\n" +
                "                   <brp:identificatieNummer>\n" +
                "                       <brp:burgerservicenummer>238651974</brp:burgerservicenummer>\n" +
                "                   </brp:identificatieNummer>\n" +
                "                   <!-- Laatste wijziging (versienummer) bijgehouden persoon; eerder opgehaald bij bevraging -->\n" +
                "                   <brp:afgeleidAdministratief>\n" +
                "                       <brp:laatsteWijziging>2008112109051303</brp:laatsteWijziging>\n" +
                "                   </brp:afgeleidAdministratief>\n" +
                "                   <brp:datumIngangFamiliaireBetrekking>20111120</brp:datumIngangFamiliaireBetrekking>\n" +
                "                   <brp:verantwoordingen>\n" +
                "                       <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "                   </brp:verantwoordingen>\n" +
                "               </brp:ouder>\n" +
                "            </brp:ouderBetrokkenheid>\n" +
                "        </brp:familierechtelijkeBetrekking>\n" +
                "</brp:actie>";

        BRPActie actie = unmarshalObject(xml);

        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getIdentiteit().getSoort());
        Assert.assertEquals(Integer.valueOf(530), actie.getIdentiteit().getPartij().getIdentiteit().getID().getWaarde());
        Assert.assertEquals(Integer.valueOf(20111120), actie.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(3, actie.getBronnen().size());

        //1 root object
        Assert.assertEquals(1, actie.getRootObjecten().size());
        Assert.assertTrue(actie.getRootObjecten().get(0) instanceof Relatie);
        final Relatie relatie = (Relatie) actie.getRootObjecten().get(0);

        //2 betrokkenheden (Kind, Moeder)
        Assert.assertEquals(2, relatie.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getIdentiteit().getSoort());
        //todo datum ingang relatie

        Persoon kind = null;
        Persoon moeder = null;
        Persoon vader = null;

        for (Betrokkenheid betr : relatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betr.getIdentiteit().getRol()) {
                kind = betr.getIdentiteit().getBetrokkene();
                //Assert.assertEquals(kind.getDatumGeboorte(), betr.getDatumAanvang());
            } else if (SoortBetrokkenheid.OUDER == betr.getIdentiteit().getRol()) {
                if (betr.getIdentiteit().getBetrokkene().getIdentificatienummers() != null &&
                        betr.getIdentiteit().getBetrokkene().getIdentificatienummers().getBurgerservicenummer() != null)
                {
                    moeder = betr.getIdentiteit().getBetrokkene();
                    //datum ingang betrokkenheid:
                    Assert.assertEquals(Integer.valueOf(20111120), betr.getOuder().getDatumAanvang().getWaarde());
                } else {
                    vader = betr.getIdentiteit().getBetrokkene();
                    //datum ingang betrokkenheid
                    Assert.assertEquals(Integer.valueOf(20111122), betr.getOuder().getDatumAanvang().getWaarde());
                }
            }
        }

        Assert.assertNotNull(kind);
        Assert.assertNotNull(moeder);
        //Er is dus geen vader
        Assert.assertNull(vader);

        if (kind != null) {

            PersoonInschrijving persoonInschrijving = kind.getInschrijving();
            Assert.assertNotNull(persoonInschrijving);
            Assert.assertEquals(Integer.valueOf(20111120), persoonInschrijving.getDatumInschrijving().getWaarde());

            PersoonBijhoudingsverantwoordelijkheid bijhoudingsverantwoordelijkheid = kind.getBijhoudingsverantwoordelijkheid();
            Assert.assertNotNull(bijhoudingsverantwoordelijkheid);
            Assert.assertEquals(Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WET, bijhoudingsverantwoordelijkheid.getVerantwoordelijke());
            Assert.assertEquals(Integer.valueOf(20111120), bijhoudingsverantwoordelijkheid.getDatumBijhoudingsverantwoordelijkheid().getWaarde());

            PersoonBijhoudingsgemeente bijhoudingsgemeente = kind.getBijhoudingsgemeente();
            Assert.assertNotNull(bijhoudingsgemeente);
            Assert.assertEquals("0530", bijhoudingsgemeente.getBijhoudingsgemeente().getGemeenteStandaard().getGemeentecode().getWaarde());
            Assert.assertEquals(Integer.valueOf(20111120), bijhoudingsgemeente.getDatumInschrijvingInGemeente().getWaarde());

            Assert.assertEquals("103962438", kind.getIdentificatienummers().getBurgerservicenummer().getWaarde());
            Assert.assertEquals("2301342693", kind.getIdentificatienummers().getAdministratienummer().getWaarde());

            Assert.assertEquals(Long.valueOf(20111120), kind.getGeboorte().getDatumGeboorte().getWaarde());
            Assert.assertEquals("0530", kind.getGeboorte().getGemeenteGeboorte().getGemeenteStandaard().getGemeentecode().getWaarde());
            Assert.assertEquals("Hellevoetsluis", kind.getGeboorte().getWoonplaatsGeboorte().getIdentiteit().getNaam().getWaarde());
            Assert.assertEquals("6030", kind.getGeboorte().getLandGeboorte().getIdentiteit().getLandcode().getWaarde());
            Assert.assertEquals(Geslachtsaanduiding.MAN, kind.getGeslachtsaanduiding().getGeslachtsaanduiding());

            Assert.assertEquals(2, kind.getVoornamen().size());
            Iterator<PersoonVoornaam> voorNamenIterator = kind.getVoornamen().iterator();
            PersoonVoornaam voornaam1 = voorNamenIterator.next();
            Assert.assertEquals(Integer.valueOf(1), voornaam1.getIdentiteit().getVolgnummer().getWaarde());
            Assert.assertEquals("Francois", voornaam1.getStandaard().getNaam().getWaarde());

            PersoonVoornaam voornaam2 = voorNamenIterator.next();
            Assert.assertEquals(Integer.valueOf(2), voornaam2.getIdentiteit().getVolgnummer().getWaarde());
            Assert.assertEquals("Samuel", voornaam2.getStandaard().getNaam().getWaarde());

            Assert.assertEquals("van", kind.getSamengesteldeNaam().getVoorvoegsel().getWaarde());
            Assert.assertEquals("Persona", kind.getSamengesteldeNaam().getGeslachtsnaam().getWaarde());

            Assert.assertEquals(1, kind.getNationaliteiten().size());
            final PersoonNationaliteit persoonNationaliteit = kind.getNationaliteiten().iterator().next();
            Assert.assertEquals("0001",
                    persoonNationaliteit.getIdentiteit().getNationaliteit().getIdentiteit().getNationaliteitcode().getWaarde());
            Assert.assertEquals(Short.valueOf("1"),
                    persoonNationaliteit.getStandaard().getRedenVerkrijging().getIdentiteit().getID().getWaarde());

            Set<PersoonAdres> adressen = kind.getAdressen();
            Assert.assertNotNull(adressen);
            Assert.assertFalse(adressen.isEmpty());

            PersoonAdres woonAdres = null;
            for (PersoonAdres persoonAdres : adressen) {
                if (FunctieAdres.WOONADRES == persoonAdres.getStandaard().getSoort()) {
                    woonAdres = persoonAdres;
                }
            }

            Assert.assertNotNull(woonAdres);
        }

        if (moeder != null) {
            Assert.assertEquals("238651974", moeder.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        }
    }

    @Test
    @Ignore
    public void testErkenningGeboreneActieUnmarshalling() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<brp:actie xmlns:brp=\"" + BRP_NAMESPACE + "\">\n" +
                "                <brp:soort>Erkenning</brp:soort>\n" +
                "                <brp:partij>0530</brp:partij>\n" +
                "                <brp:datumAanvangGeldigheid>20111121</brp:datumAanvangGeldigheid>\n" +
                "                <brp:tijdstipOntlening>2011-11-21T09:05:12</brp:tijdstipOntlening>\n" +
                "\n" +
                "                <!-- Onderstaande onder voorbehoud verdere uitwerking onderwerp Verantwoording -->\n" +
                "                <!-- Voor bronnen en verwijzingen verderop in bericht ID/REF-constructie (Tim)  implementeren -->\n" +
                "                <brp:bronnen>\n" +
                "                    <!-- Reguliere akte BS -->\n" +
                "                    <brp:bron>\n" +
                "                        <brp:identificatieBronInBericht>1</brp:identificatieBronInBericht>\n" +
                "                        <brp:soort>Nederlandse Akte</brp:soort>\n" +
                "                        <brp:akteNummer>1AA0001</brp:akteNummer>\n" +
                "                        <brp:partij>0530</brp:partij>\n" +
                "                    </brp:bron>\n" +
                "                </brp:bronnen>\n" +
                "\n" +
                "                <brp:familierechtelijkeBetrekking>\n" +
                "                    <brp:kind>\n" +
                "                        <brp:geslachtsnaamComponenten>\n" +
                "                            <brp:geslachtsnaamComponent>\n" +
                "                                <brp:volgnummer>1</brp:volgnummer>\n" +
                "                                <brp:voorvoegsel>dos</brp:voorvoegsel>\n" +
                "                                <brp:geslachtsnaam>Santos da Vitoria</brp:geslachtsnaam>\n" +
                "                            </brp:geslachtsnaamComponent>\n" +
                "                            <brp:verantwoordingen>\n" +
                "                                <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "                            </brp:verantwoordingen>\n" +
                "                        </brp:geslachtsnaamComponenten>\n" +
                "                    </brp:kind>\n" +
                "\n" +
                "                    <brp:ouderBetrokkenheid>\n" +
                "                        <!-- Tweede ouder (Vader) is niet ingeschrevene; indien niet gewenst dan constructie eerste ouder volgen-->\n" +
                "                        <brp:ouder>\n" +
                "                            <brp:identificatieNummer>\n" +
                "                                <brp:burgerservicenummer>78401999</brp:burgerservicenummer>\n" +
                "                            </brp:identificatieNummer>\n" +
                "                            <!-- Laatste wijziging (versienummer) bijgehouden persoon; eerder opgehaald bij bevraging -->\n" +
                "                            <brp:afgeleidAdministratief>\n" +
                "                                <brp:laatsteWijziging>2009103109251303</brp:laatsteWijziging>\n" +
                "                            </brp:afgeleidAdministratief>\n" +
                "                            <brp:datumIngangFamiliaireBetrekking>20111121</brp:datumIngangFamiliaireBetrekking>\n" +
                "                            <brp:verantwoordingen>\n" +
                "                                <brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "                            </brp:verantwoordingen>\n" +
                "                        </brp:ouder>\n" +
                "                    </brp:ouderBetrokkenheid>\n" +
                "                </brp:familierechtelijkeBetrekking>\n" +
                "            </brp:actie>";

        BRPActie actie = unmarshalObject(xml);

        Assert.assertEquals(SoortActie.ERKENNING_GEBOORTE, actie.getIdentiteit().getSoort());
        Assert.assertEquals(Integer.valueOf(530), actie.getIdentiteit().getPartij().getIdentiteit().getID().getWaarde());
        Assert.assertEquals(Integer.valueOf(20111121), actie.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(1, actie.getBronnen().size());

        //1 root object
        Assert.assertEquals(1, actie.getRootObjecten().size());
        Assert.assertTrue(actie.getRootObjecten().get(0) instanceof Relatie);
        final Relatie relatie = (Relatie) actie.getRootObjecten().get(0);

        //2 betrokkenheden (Kind, Vader)
        Assert.assertEquals(2, relatie.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getIdentiteit().getSoort());
        //todo datum ingang relatie

        //Er kan alleen een vader in zitten.
        Persoon kind = null;
        Persoon vader = null;

        for (Betrokkenheid betr : relatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betr.getIdentiteit().getRol()) {
                kind = betr.getIdentiteit().getBetrokkene();
                //Assert.assertEquals(kind.getDatumGeboorte(), betr.getDatumAanvang());
            } else if (SoortBetrokkenheid.OUDER == betr.getIdentiteit().getRol()) {
                if (betr.getIdentiteit().getBetrokkene().getIdentificatienummers() != null &&
                        betr.getIdentiteit().getBetrokkene().getIdentificatienummers().getBurgerservicenummer() != null)
                {
                    vader = betr.getIdentiteit().getBetrokkene();
                    //datum ingang betrokkenheid
                    Assert.assertEquals(Integer.valueOf(20111121), betr.getOuder().getDatumAanvang().getWaarde());
                }
            }
        }

        Assert.assertNotNull(kind);
        //Er is dus alleen een vader
        Assert.assertNotNull(vader);

        if (kind != null) {
            Assert.assertEquals("dos", kind.getSamengesteldeNaam().getVoorvoegsel().getWaarde());
            Assert.assertEquals("Santos da Vitoria", kind.getSamengesteldeNaam().getGeslachtsnaam().getWaarde());
        }

        if (vader != null) {
            Assert.assertEquals("78401999", vader.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        }
    }

    @Test
    public void testVerhuisberichtUnmarshallingTest() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "\t\t<brp:actie xmlns:brp=\"" + BRP_NAMESPACE + "\">\n" +
                "\t\t\t<brp:soort>Dummy waarde</brp:soort>\n" +
                "\t\t\t<brp:partij>0599</brp:partij>\n" +
                "\t\t\t<brp:datumAanvangGeldigheid>20120131</brp:datumAanvangGeldigheid>\n" +
                /*"\t\t\t<brp:tijdstipOntlening>2012013113255200</brp:tijdstipOntlening>\n" +*/
                "\n" +
                "\t\t\t<brp:bronnen>\n" +
                "\t\t\t\t<!-- Aangifteformulier verhuizing -->\n" +
                "\t\t\t\t<brp:bron>\n" +
                "\t\t\t\t\t<brp:identificatieBronInBericht>1</brp:identificatieBronInBericht>\n" +
                "\t\t\t\t\t<brp:soort>Nederlandse Akte</brp:soort>\n" +
                "\t\t\t\t\t<brp:identificatie>VRH201201-0021</brp:identificatie>\n" +
                "\t\t\t\t\t<brp:omschrijving>Aangifte van adreswijziging</brp:omschrijving>\n" +
                "\t\t\t\t\t<brp:partij>0599</brp:partij>\n" +
                "\t\t\t\t</brp:bron>\n" +
                "\t\t\t</brp:bronnen>\n" +
                "\n" +
                "\t\t\t<!-- Identificatie verhuizende persoon; Identificatienummergroep en Afgeleid administratief groep -->\n"
                +
                "\t\t\t<brp:persoon>\n" +
                "\t\t\t\t<brp:identificatieNummer>\n" +
                "\t\t\t\t\t<brp:burgerservicenummer>78401999</brp:burgerservicenummer>\n" +
                "\t\t\t\t</brp:identificatieNummer>\n" +
                "\t\t\t\t<!-- Laatste wijziging (versienummer) bijgehouden persoon; eerder opgehaald bij bevraging -->\n"
                +
                "\t\t\t\t<brp:afgeleidAdministratief>\n" +
                "\t\t\t\t\t<brp:laatsteWijziging>2006050311091903</brp:laatsteWijziging>\n" +
                "\t\t\t\t</brp:afgeleidAdministratief>\n" +
                "\n" +
                "\t\t\t\t<!-- Nieuwe adresgegevens -->\n" +
                "\t\t\t\t<brp:adres>\n" +
                "\t\t\t\t\t<brp:soort>W</brp:soort>\n" +
                "\t\t\t\t\t<brp:redenWijziging>P</brp:redenWijziging>\n" +
                "\t\t\t\t\t<brp:aangeverAdreshouding>I</brp:aangeverAdreshouding>\n" +
                "\t\t\t\t\t<brp:datumAanvangAdreshouding>20120131</brp:datumAanvangAdreshouding>\n"
                +
                "\t\t\t\t\t<brp:adresseerbaarObject>0599209876543210</brp:adresseerbaarObject>\n" +
                "\t\t\t\t\t<brp:identificatieNummeraanduiding>0599209876543210</brp:identificatieNummeraanduiding>\n" +
                "\t\t\t\t\t<brp:land>6030</brp:land>\n" +
                "\t\t\t\t\t<brp:gemeente>0599</brp:gemeente>\n" +
                "\t\t\t\t\t<brp:woonplaats>Rotterdam</brp:woonplaats>\n" +
                "\t\t\t\t\t<brp:naamOpenbareruimte>Walenburgerweg</brp:naamOpenbareruimte>\n" +
                "\t\t\t\t\t<brp:afgekorteNaamOpenbareruimte>Walenburgerweg</brp:afgekorteNaamOpenbareruimte>\n" +
                "\t\t\t\t\t<brp:huisnummer>299</brp:huisnummer>\n" +
                "\t\t\t\t\t<brp:huisletter>A</brp:huisletter>\n" +
                "\t\t\t\t\t<brp:huisnummerToevoeging>2HBA</brp:huisnummerToevoeging>\n" +
                "\t\t\t\t\t<brp:postcode>3033AK</brp:postcode>\n" +
                "\t\t\t\t\t<brp:verantwoordingen>\n" +
                "\t\t\t\t\t\t<brp:bron.identificatieBronInBericht>1</brp:bron.identificatieBronInBericht>\n" +
                "\t\t\t\t\t</brp:verantwoordingen>\n" +
                "\t\t\t\t</brp:adres>\n" +
                "\t\t\t</brp:persoon>\n" +
                "\t\t</brp:actie>\n";

        BRPActie actie = unmarshalObject(xml);

        Assert.assertNotNull(actie);
        Assert.assertEquals(1, actie.getBronnen().size());
        Assert.assertEquals(new Integer(599), actie.getIdentiteit().getPartij().getIdentiteit().getID().getWaarde());
        Assert.assertEquals(Integer.valueOf(20120131), actie.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(SoortActie.DUMMY_WAARDE, actie.getIdentiteit().getSoort());

        Bron bron = actie.getBronnen().iterator().next();
        Assert.assertEquals("1", bron.getIdentificatieBronInBericht());
        Assert.assertEquals(CategorieSoortDocument.NEDERLANDSE_AKTE,
                bron.getIdentiteit().getDocument().getIdentiteit().getSoort().getIdentiteit().getCategorieSoortDocument());
        Assert.assertEquals("VRH201201-0021",
                bron.getIdentiteit().getDocument().getStandaard().getIdentificatie().getWaarde());
        Assert.assertEquals("Aangifte van adreswijziging",
                bron.getIdentiteit().getDocument().getStandaard().getOmschrijving().getWaarde());

        Assert.assertTrue(actie.getRootObjecten().size() == 1);

        Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        Assert.assertEquals("78401999", persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());

        Assert.assertTrue(persoon.getAdressen().size() == 1);
        PersoonAdres adres = persoon.getAdressen().iterator().next();

        Assert.assertEquals(FunctieAdres.WOONADRES, adres.getStandaard().getSoort());
        Assert.assertEquals("P", adres.getStandaard().getRedenWijziging().getIdentiteit().getCode().getWaarde());
        Assert.assertEquals("I", adres.getStandaard().getAangeverAdreshouding().getIdentiteit().getCode().getWaarde());
        Assert.assertEquals(Integer.valueOf(20120131), adres.getStandaard().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals("0599209876543210", adres.getStandaard().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("0599209876543210", adres.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("6030", adres.getStandaard().getLand().getIdentiteit().getLandcode().getWaarde());
        Assert.assertEquals("0599", adres.getStandaard().getGemeente().getGemeenteStandaard().getGemeentecode().getWaarde());
        Assert.assertEquals("Rotterdam", adres.getStandaard().getWoonplaats().getIdentiteit().getNaam().getWaarde());
        Assert.assertEquals("Walenburgerweg", adres.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Walenburgerweg", adres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("299", adres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("A", adres.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals("2HBA", adres.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3033AK", adres.getStandaard().getPostcode().getWaarde());
    }
}
