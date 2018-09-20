/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.jibx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

import nl.bzk.brp.poc.jibx.bericht.EersteInschrijving;
import nl.bzk.brp.poc.jibx.model.*;
import org.jibx.runtime.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor het testen van de marshalling en unmarshalling van een eerste inschrijving bericht. Hierbij gaat het
 * om een bepaalde Java structuur om te zetten naar een bepaalde XML structuur en vice versa.
 *
 * <pre>
 * <i>Java structuur:</i>
 * {@code EersteInschrijving
 *     + Persoon kind
 *     + Persoon ouder1
 *     + Persoon ouder2
 *
 *   Persoon
 *     + Long bsn
 *     + Set&lt;SamengesteldeNaam&gt; namen
 *
 *   SamengesteldeNaam
 *     + String voorNaam
 *     + String achterNaam
 *     + Set<Verantwoording> verantwoordingen
 *
 *   Verantwoording
 *     + Bron bron
 *     + String toelichting
 *
 *   Bron
 *     + BronType type
 *     + String code
 *     + String nummer
 * }
 *
 * <i>XML Structuur:</i>
 * {@code  <eersteInschrijving>
 *         <bronnen>
 *             <bron>
 *                 <type>AKTE</type>
 *                 <code>Geboorteakte</code>
 *                 <nummer>1AA0001</nummer>
 *             </bron>
 *         </bronnen>
 *         <kind>
 *             <bsn>123456789</bsn>
 *             <samengesteldeNaam>
 *                 <voornaam>Kindje</voornaam>
 *                 <achternaam>TestM</achternaam>
 *                 <verantwoording>
 *
 *                 </verantwoording>
 *             </samengesteldeNaam>
 *             <samengesteldeNaam>
 *                 <voornaam>Kindje</voornaam>
 *                 <achternaam>TestP</achternaam>
 *             </samengesteldeNaam>
 *         </kind>
 *         <ouders>
 *             <ouder>
 *                 <bsn>234567890</bsn>
 *                 <samengesteldeNaam>
 *                     <voornaam>Mama</voornaam>
 *                     <achternaam>TestM</achternaam>
 *                 </samengesteldeNaam>
 *             </ouder>
 *             <ouder>
 *                 <bsn>345678901</bsn>
 *                 <samengesteldeNaam>
 *                     <voornaam>Papa</voornaam>
 *                     <achternaam>TestP</achternaam>
 *                 </samengesteldeNaam>
 *             </ouder>
 *         </ouders>
 *     </eersteInschrijving>
 * }
 * </pre>
 *
 */
public class EersteInschrijvingTest {

    /**
     * Test of de marshalling (van Java Object naar XML) correct verloopt en verloopt zoals verwacht.
     * @throws JiBXException in het geval van een exceptie in de mapping.
     */
    //@Test
    public void testMarshalling() throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(EersteInschrijving.class);
        IMarshallingContext mctx = bfact.createMarshallingContext();

        OutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(bouwEersteInschrijving(), "UTF-8", true, os);

        Assert.assertEquals(getUnmarshallingContent(), os.toString());
    }

    /**
     * Retourneert een {@link EersteInschrijving} instantie, gevuld met een kind met twee samengestelde namen, en met
     * twee ouders.
     *
     * @return
     */
    private EersteInschrijving bouwEersteInschrijving() {
        Bron bron1 = new Bron(BronType.AKTE, "Geboorteakte");
        bron1.setNummer("1AA0001");
        Bron bron2 = new Bron(BronType.WET, "Nederlands recht; Nationaliteit");
        bron2.setNummer("1AA0002");

        Persoon kind = new Persoon(123456789L, new SamengesteldeNaam("Kindje", "TestM"));
        kind.getNamen().add(new SamengesteldeNaam("Kindje", "TestP"));
        Persoon ouder1 = new Persoon(234567890L, new SamengesteldeNaam("Papa", "TestP"));
        Persoon ouder2 = new Persoon(345678901L, new SamengesteldeNaam("Mama", "TestM"));

        EersteInschrijving eersteInschrijving = new EersteInschrijving();
        eersteInschrijving.setKind(kind);
        eersteInschrijving.setOuder1(ouder1);
        eersteInschrijving.setOuder2(ouder2);
        eersteInschrijving.getBronnen().add(bron1);
        eersteInschrijving.getBronnen().add(bron2);

        Verantwoording verantwoording1 = new Verantwoording(bron1);
        verantwoording1.setToelichting("Dit is een test");
        Set<Verantwoording> verantwoordingen1 = new HashSet<Verantwoording>();
        verantwoordingen1.add(verantwoording1);
        Verantwoording verantwoording2 = new Verantwoording(bron2);
        Set<Verantwoording> verantwoordingen2 = new HashSet<Verantwoording>();
        verantwoordingen2.add(verantwoording2);
                
        Iterator<SamengesteldeNaam> namenIterator = eersteInschrijving.getKind().getNamen().iterator();
        namenIterator.next().setVerantwoordingen(verantwoordingen1);
        namenIterator.next().setVerantwoordingen(verantwoordingen2);

        return eersteInschrijving;
    }
    
    @Test
    public void testUnmarshalling() throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(EersteInschrijving.class);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();

        EersteInschrijving eersteInschrijving = (EersteInschrijving) uctx.unmarshalDocument(
                new ByteArrayInputStream(getUnmarshallingContent().getBytes()), null);
        Assert.assertEquals(bouwEersteInschrijving(), eersteInschrijving);
        
        Iterator<SamengesteldeNaam> namenIterator = eersteInschrijving.getKind().getNamen().iterator();
        SamengesteldeNaam naam1 = namenIterator.next();
        SamengesteldeNaam naam2 = namenIterator.next();
        Assert.assertEquals("Geboorteakte", naam1.getVerantwoordingen().iterator().next().getBron().getCode());
        Assert.assertEquals("Nederlands recht; Nationaliteit", naam2.getVerantwoordingen().iterator().next().getBron().getCode());

        // Test of bron ook echt zelfde bron is en niet een andere instantie met dezelfde waardes
        Bron bron = eersteInschrijving.getBronnen().iterator().next();
        if (bron.getNummer().equals("1AA0001")) {
            Assert.assertEquals(bron, naam1.getVerantwoordingen().iterator().next().getBron());
            Assert.assertTrue(bron == naam1.getVerantwoordingen().iterator().next().getBron());
        } else {
            Assert.assertEquals(bron, naam2.getVerantwoordingen().iterator().next().getBron());
            Assert.assertTrue(bron == naam2.getVerantwoordingen().iterator().next().getBron());
        }
    }
    
    private String getUnmarshallingContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<eersteInschrijving xmlns=\"http://brp.bzk.nl/InschrijvingSchema\">");
        sb.append("<bronnen>");
        sb.append("<bron nummer=\"1AA0001\"><type>AKTE</type><code>Geboorteakte</code></bron>");
        sb.append("<bron nummer=\"1AA0002\"><type>WET</type><code>Nederlands recht; Nationaliteit</code></bron>");
        sb.append("</bronnen>");
        sb.append("<kind><bsn>123456789</bsn>");
        sb.append("<samengesteldeNaam><voornaam>Kindje</voornaam><achternaam>TestM</achternaam><verantwoordingen><verantwoording><bron>1AA0001</bron><toelichting>Dit is een test</toelichting></verantwoording></verantwoordingen></samengesteldeNaam>");
        sb.append("<samengesteldeNaam><voornaam>Kindje</voornaam><achternaam>TestP</achternaam><verantwoordingen><verantwoording><bron>1AA0002</bron></verantwoording></verantwoordingen></samengesteldeNaam>");
        sb.append("</kind>");
        sb.append("<ouders>");
        sb.append("<ouder><bsn>234567890</bsn><samengesteldeNaam><voornaam>Papa</voornaam><achternaam>TestP</achternaam></samengesteldeNaam></ouder>");
        sb.append("<ouder><bsn>345678901</bsn><samengesteldeNaam><voornaam>Mama</voornaam><achternaam>TestM</achternaam></samengesteldeNaam></ouder>");
        sb.append("</ouders>");
        sb.append("</eersteInschrijving>");
        return sb.toString();
    }

}
