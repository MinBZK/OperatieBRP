/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.binding.AbstractBindingTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/** Binding test voor een {@link BijhoudingsBericht} met daarin een inschrijving geboorte bericht. */
public class InschrijvingGeboorteBindingTest extends AbstractBindingTest<BijhoudingsBericht> {

    @Test
    public void testBindingMetAlleenEenKind() throws JiBXException {
        String xml = "<brp:afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0100 "
            + "        xmlns:brp=\"http://www.brp.nl/brp/0001\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + "        xsi:schemaLocation=\"http://www.brp.nl/brp/0001 "
            + "        ../../Berichtenmodel/BRPXML/BijhoudingAfstamming/brp0300_msg_bijhoudingAfstamming.xsd\">\n"
            + "    <!-- StUF kent in Stuurgegevens nog 'berichtCode' en 'functie' als verplichte elementen -->\n"
            + "    <!-- Aandacht voor eventuele aanvullende elementen vanuit BRP-verwerking (TT) -->\n"
            + "    <brp:stuurgegevens>\n"
            + "        <brp:zender>\n"
            + "            <brp:organisatie>0530</brp:organisatie>\n"
            + "            <brp:applicatie>LeverancierProduct</brp:applicatie>\n"
            + "        </brp:zender>\n"
            + "        <brp:referentienummer>afsB201111210000326</brp:referentienummer>\n"
            + "    </brp:stuurgegevens>\n"
            + "    <brp:bijhouding>\n"
            + "        <!-- Representatie van de Actie -->\n"
            + "        <brp:InschrijvingGeboorte brp:cIDVerzendend=\"identificatie01\">\n"
            + "            <brp:datumAanvangGeldigheid>20111120</brp:datumAanvangGeldigheid>\n"
            + "            <brp:tijdstipOntlening>2011112109051235</brp:tijdstipOntlening>\n"
            + "            <brp:toelichtingOntlening>Vader niet ingeschreven BRP; inschrijving mede o.b.v. toepassing "
            + "VreemdRecht (Brazilië)</brp:toelichtingOntlening>\n"
            + "            <brp:bronnen>\n"
            + "                <brp:bron brp:cIDVerzendend=\"identificatie02\">\n"
            + "                    <!-- Reguliere akte BS; verplicht -->\n"
            + "                    <brp:document brp:cIDVerzendend=\"identificatie03\">\n"
            + "                        <brp:soort>Geboorteakte</brp:soort>\n"
            + "                        <brp:identificatie>GA0530-1A0001</brp:identificatie>\n"
            + "                        <brp:aktenummer>1AA0001</brp:aktenummer>\n"
            + "                        <brp:partij>0530</brp:partij>\n"
            + "                    </brp:document>\n"
            + "                </brp:bron>\n"
            + "            </brp:bronnen>\n"
            + "            <brp:familierechtelijkeBetrekking>\n"
            + "                <brp:betrokkenheden>\n"
            + "                    <brp:kind brp:cIDVerzendend=\"identificatie04\">\n"
            + "                        <brp:persoon brp:cIDVerzendend=\"identificatie05\">\n"
            + "                            <brp:identificatieNummers brp:cIDVerzendend=\"identificatie06\">\n"
            + "                                <brp:burgerservicenummer>103962438</brp:burgerservicenummer>\n"
            + "                                <brp:administratienummer>2301342693</brp:administratienummer>\n"
            + "                            </brp:identificatieNummers>\n"
            + "                            <brp:geboorte brp:cIDVerzendend=\"identificatie07\">\n"
            + "                                <brp:datum>20111120</brp:datum>\n"
            + "                                <brp:gemeentecode>0530</brp:gemeentecode>\n"
            + "                                <brp:woonplaatscode>2137</brp:woonplaatscode>\n"
            + "                                <brp:landcode>6030</brp:landcode>\n"
            + "                            </brp:geboorte>\n"
            + "                            <brp:geslachtsAanduiding brp:cIDVerzendend=\"identificatie08\">\n"
            + "                                <brp:geslachtsAanduidingcode>V</brp:geslachtsAanduidingcode>\n"
            + "                            </brp:geslachtsAanduiding>\n"
            + "                            <brp:voornamen>\n"
            + "                                <brp:voornaam brp:cIDVerzendend=\"identificatie09\">\n"
            + "                                    <brp:volgnummer>1</brp:volgnummer>\n"
            + "                                    <brp:naam>Françoise</brp:naam>\n"
            + "                                </brp:voornaam>\n"
            + "                                <brp:voornaam brp:cIDVerzendend=\"identificatie10\">\n"
            + "                                    <brp:volgnummer>2</brp:volgnummer>\n"
            + "                                    <brp:naam>Isabella</brp:naam>\n"
            + "                                </brp:voornaam>\n"
            + "                            </brp:voornamen>\n"
            + "                            <brp:geslachtsnaamComponenten>\n"
            + "                                <brp:geslachtsnaamComponent brp:cIDVerzendend=\"identificatie11\">\n"
            + "                                    <brp:volgnummer>1</brp:volgnummer>\n"
            + "                                    <brp:voorvoegsel>dos</brp:voorvoegsel>\n"
            + "                                    <brp:naam>Santos da Victória</brp:naam>\n"
            + "                                </brp:geslachtsnaamComponent>\n"
            + "                            </brp:geslachtsnaamComponenten>\n"
            + "                            <brp:indicaties>\n"
            + "                                <brp:verstrekkingsBeperking brp:cIDVerzendend=\"identificatie12\">\n"
            + "                                    <brp:waarde>J</brp:waarde>\n"
            + "                                </brp:verstrekkingsBeperking>\n"
            + "                            </brp:indicaties>\n"
            + "                        </brp:persoon>\n"
            + "                    </brp:kind>\n"
//            "                    <!-- Ingeschreven ouder BRP -->\n" +
//            "                    <brp:ouder brp:cIDVerzendend=\"identificatie13\" brp:adresgevendeOuder=\"true\">\n" +
//            "                        <brp:datumIngangFamilierechtelijkeBetrekking>2011-11-20</brp" +
//            ":datumIngangFamilierechtelijkeBetrekking>\n"
//            +
//            "                        <brp:persoon brp:cIDVerzendend=\"identificatie14\">\n" +
//            "                            <brp:identificatieNummers brp:cIDVerzendend=\"identificatie15\">\n" +
//            "                                <brp:burgerservicenummer>238651974</brp:burgerservicenummer>\n" +
//            "                            </brp:identificatieNummers>\n" +
//            "                            <!-- Laatste wijziging (versienummer) bijgehouden persoon; eerder
// opgehaald " +
//            "bij bevraging -->\n"
//            +
//            "                            <brp:afgeleidAdministratief brp:cIDVerzendend=\"identificatie16\">\n" +
//            "                                " +
//            "<brp:tijdstipLaatsteWijziging>2008112109054712</brp:tijdstipLaatsteWijziging>\n"
//            +
//            "                            </brp:afgeleidAdministratief>\n" +
//            "                        </brp:persoon>\n" +
//            "                    </brp:ouder>\n" +
//            "                    <!-- Niet-ingeschreven vader -->\n" +
//            "                    <brp:ouder brp:cIDVerzendend=\"identificatie17\">\n" +
//            "                        <brp:datumIngangFamilierechtelijkeBetrekking>2011-11-20</brp" +
//            ":datumIngangFamilierechtelijkeBetrekking>\n"
//            +
//            "                        <brp:persoon brp:cIDVerzendend=\"identificatie18\">\n" +
//            "                            <brp:geboorte brp:cIDVerzendend=\"identificatie19\">\n" +
//            "                                <brp:datum>1985-09-19</brp:datum>\n" +
//            "                                <brp:landcode>6008</brp:landcode>\n" +
//            "                                <brp:buitenlandsePlaats>Maceió</brp:buitenlandsePlaats>\n" +
//            "                            </brp:geboorte>\n" +
//            "                            <brp:geslachtsAanduiding brp:cIDVerzendend=\"identificatie20\">\n" +
//            "                                <brp:geslachtsAanduidingcode>M</brp:geslachtsAanduidingcode>\n" +
//            "                            </brp:geslachtsAanduiding>\n" +
//            "                            <brp:samengesteldeNaam brp:cIDVerzendend=\"identificatie21\">\n" +
//            "                                <brp:voornamen>Willy </brp:voornamen>\n" +
//            "                                <brp:voorvoegsel>dos</brp:voorvoegsel>\n" +
//            "                                <brp:geslachtsnaam>Santos da Victória</brp:geslachtsnaam>\n" +
//            "                            </brp:samengesteldeNaam>\n" +
//            "                        </brp:persoon>\n" +
//            "                    </brp:ouder>\n" +
            + "                </brp:betrokkenheden>\n"
            + "            </brp:familierechtelijkeBetrekking>\n"
            + "        </brp:InschrijvingGeboorte>\n"
            + "        <!--<brp:RegistratieNationaliteit brp:cIDVerzendend=\"identificatie22\">-->\n"
            + "            <!--<brp:datumAanvangGeldigheid>20111120</brp:datumAanvangGeldigheid>-->\n"
            + "            <!--<brp:tijdstipOntlening>2011112109051235</brp:tijdstipOntlening>-->\n"
            + "            <!--<brp:toelichtingOntlening>Moeder 'behandeld als NL'; toepassing VreemdRecht Vader. Kind "
            + "beide obv 'Samensmelting'</brp:toelichtingOntlening>-->\n"
            + "            <!--&lt;!&ndash; Bronnen triviaal; geen registratie in BRP &ndash;&gt;-->\n"
            + "            <!--&lt;!&ndash; Let op! SleutelVerzendendVluchtig verwijst naar eerder in bericht opgenomen"
            + " persoon bij Kind &ndash;&gt;-->\n"
            + "            <!--<brp:persoon brp:cIDVerzendend=\"identificatie05\">-->\n"
            + "                <!--<brp:identificatieNummers brp:cIDVerzendend=\"identificatie23\">-->\n"
            + "                    <!--<brp:burgerservicenummer>103962438</brp:burgerservicenummer>-->\n"
            + "                <!--</brp:identificatieNummers>-->\n"
            + "                <!--&lt;!&ndash; Uniformiteitsbeginsel Identificatie Personen; betekenis: 'Zender weet "
            + "dat er een waarde is; is hem echter onbekend' &ndash;&gt;-->\n"
            + "                <!--<brp:afgeleidAdministratief brp:cIDVerzendend=\"identificatie24\">-->\n"
            + "                    <!--<brp:tijdstipLaatsteWijziging xsi:nil=\"true\" brp:noValue=\"waardeOnbekend"
            + "\"/>-->\n"
            + "                <!--</brp:afgeleidAdministratief>-->\n"
            + "                <!--<brp:indicaties>-->\n"
            + "                    <!--<brp:behandeldAlsNederlander brp:cIDVerzendend=\"identificatie26\">-->\n"
            + "                        <!--<brp:waarde>J</brp:waarde>-->\n"
            + "                    <!--</brp:behandeldAlsNederlander>-->\n"
            + "                <!--</brp:indicaties>-->\n"
            + "                <!--<brp:nationaliteiten>-->\n"
            + "                    <!--<brp:nationaliteit brp:cIDVerzendend=\"identificatie25\">-->\n"
            + "                        <!--<brp:nationaliteitcode>0253</brp:nationaliteitcode>-->\n"
            + "                    <!--</brp:nationaliteit>-->\n"
            + "                <!--</brp:nationaliteiten>-->\n"
            + "\n"
            + "            <!--</brp:persoon>-->\n"
            + "        <!--</brp:RegistratieNationaliteit>-->\n"
            + "    </brp:bijhouding>\n"
            + "</brp:afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0100>\n";


//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> "
//            + "<bev:OpvragenPersoon xmlns:bev=\"http://www.brp.nl/brp/0001\">\n"
//            + "       <bev:afzenderId>1</bev:afzenderId>\n"
//            + "       <bev:tijdstipVerzonden>2008-09-29T03:49:45</bev:tijdstipVerzonden>\n"
//            + "       <bev:opvragenPersoonCriteria>\n"
//            + "          <bev:bsn>111222333</bev:bsn>\n"
//            + "       </bev:opvragenPersoonCriteria>\n"
//            + "</bev:OpvragenPersoon>";
        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
//        Assert.assertEquals(1, bericht.getAfzender().getId().intValue());
//        Assert.assertEquals(29, bericht.getTijdstipVerzonden().get(Calendar.DAY_OF_MONTH));
//        Assert.assertEquals(9 - 1, bericht.getTijdstipVerzonden().get(Calendar.MONTH));
//        Assert.assertEquals(2008, bericht.getTijdstipVerzonden().get(Calendar.YEAR));
//        //UTC tijd
//        Assert.assertEquals(3 + 2, bericht.getTijdstipVerzonden().get(Calendar.HOUR));
//        Assert.assertEquals(49, bericht.getTijdstipVerzonden().get(Calendar.MINUTE));
//        Assert.assertEquals(45, bericht.getTijdstipVerzonden().get(Calendar.SECOND));
//        OpvragenPersoonCriteria crit = bericht.getOpvragenPersoonCriteria();
//        Assert.assertNotNull(crit);
//        Assert.assertEquals("111222333", crit.getIdentificatienummers().getBurgerservicenummer());
//        Assert.assertNull(crit.getIdentificatienummers().getAdministratienummer());
    }

    @Override
    protected Class<BijhoudingsBericht> getBindingClass() {
        return BijhoudingsBericht.class;
    }

}
