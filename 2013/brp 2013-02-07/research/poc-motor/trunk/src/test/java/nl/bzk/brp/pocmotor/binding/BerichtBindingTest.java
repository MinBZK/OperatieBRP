/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.binding;

import java.util.Arrays;
import java.util.Calendar;

import nl.bzk.brp.pocmotor.model.Bericht;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test voor het testen van de binding van {@link Bericht}.
 */
public class BerichtBindingTest extends AbstractBindingTest<Bericht> {

    @Test
    public void testAfstammingsBerichtUnmarshallingMetAlleenId() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                "<afstamming01>" +
                "<stuurgegevens>" +
                "<berichtIdentificatie>12</berichtIdentificatie>" +
                "</stuurgegevens>" +
                "<bijhoudingen/>" +
                "</afstamming01>" +
                "</BRPBericht>";

        Bericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("12", bericht.getBerichtId());
        Assert.assertNull(bericht.getTijdstipVerzonden());
        Assert.assertTrue(bericht.getBijhoudingen().isEmpty());
    }

    @Test
    public void testAfstammingsBerichtUnmarshallingMetAlleenStuurgegevens() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                "<afstamming01>" +
                "<stuurgegevens>" +
                "<berichtIdentificatie>12</berichtIdentificatie>" +
                "<tijdstipVerzonden>2012-02-02T10:04:23</tijdstipVerzonden>" +
                "</stuurgegevens>" +
                "<bijhoudingen/>" +
                "</afstamming01>" +
                "</BRPBericht>";

        Bericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("12", bericht.getBerichtId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bericht.getTijdstipVerzonden());
        Assert.assertEquals(2012, calendar.get(Calendar.YEAR));
        Assert.assertEquals(4, calendar.get(Calendar.MINUTE));
        Assert.assertTrue(bericht.getBijhoudingen().isEmpty());
    }

    @Test
    public void testAfstammingsBerichtUnmarshallingMetActie() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                "<afstamming01>" +
                "<stuurgegevens>" +
                "<berichtIdentificatie>12</berichtIdentificatie>" +
                "<tijdstipVerzonden>2012-02-02T10:04:23</tijdstipVerzonden>" +
                "</stuurgegevens>" +
                "<bijhoudingen>" +
                "<actie>" +
                "<soort>" + SoortActie.values()[1].getNaam() + "</soort>" +
                "<partij>6030</partij>" +
                "<datumAanvangGeldigheid>20111120</datumAanvangGeldigheid>" +
                "<tijdstipOntlening>2011-11-21T09:05:12</tijdstipOntlening>" +
                "</actie>" +
                "</bijhoudingen>" +
                "</afstamming01>" +
                "</BRPBericht>";

        Bericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("12", bericht.getBerichtId());
        Calendar tijdstipVerzonden = Calendar.getInstance();
        tijdstipVerzonden.setTime(bericht.getTijdstipVerzonden());
        Assert.assertEquals(2012, tijdstipVerzonden.get(Calendar.YEAR));
        Assert.assertEquals(4, tijdstipVerzonden.get(Calendar.MINUTE));
        Assert.assertFalse(bericht.getBijhoudingen().isEmpty());
        
        BRPActie actie = bericht.getBijhoudingen().get(0);
        Assert.assertEquals(SoortActie.values()[1], actie.getIdentiteit().getSoort());
        Assert.assertEquals(Integer.valueOf(20111120), actie.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(new Integer(6030), actie.getIdentiteit().getPartij().getIdentiteit().getID().getWaarde());
        Calendar tijdstipOntlening = Calendar.getInstance();
        tijdstipOntlening.setTime(actie.getIdentiteit().getDatumTijdOntlening().getWaarde());
        Assert.assertEquals(2011, tijdstipOntlening.get(Calendar.YEAR));
        Assert.assertEquals(5, tijdstipOntlening.get(Calendar.MINUTE));
    }

    @Test
    public void testVerhuisBerichtUnmarshallingMetAlleenId() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                "<verhuizing01>" +
                "<stuurgegevens>" +
                "<berichtIdentificatie>12</berichtIdentificatie>" +
                "</stuurgegevens>" +
                "<bijhoudingen/>" +
                "</verhuizing01>" +
                "</BRPBericht>";

        Bericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("12", bericht.getBerichtId());
        Assert.assertNull(bericht.getTijdstipVerzonden());
        Assert.assertTrue(bericht.getBijhoudingen().isEmpty());
    }

    @Test
    public void testVerhuisBerichtUnmarshallingMetActie() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                "<verhuizing01>" +
                "<stuurgegevens>" +
                "<berichtIdentificatie>12</berichtIdentificatie>" +
                "<tijdstipVerzonden>2012-02-02T10:04:23</tijdstipVerzonden>" +
                "</stuurgegevens>" +
                "<bijhoudingen>" +
                "<actie>" +
                "<soort>" + SoortActie.values()[1].getNaam() + "</soort>" +
                "<partij>6030</partij>" +
                "<datumAanvangGeldigheid>20111120</datumAanvangGeldigheid>" +
                "<tijdstipOntlening>2011-11-21T09:05:12</tijdstipOntlening>" +
                "</actie>" +
                "</bijhoudingen>" +
                "</verhuizing01>" +
                "</BRPBericht>";

        Bericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("12", bericht.getBerichtId());
        Calendar tijdstipVerzonden = Calendar.getInstance();
        tijdstipVerzonden.setTime(bericht.getTijdstipVerzonden());
        Assert.assertEquals(2012, tijdstipVerzonden.get(Calendar.YEAR));
        Assert.assertEquals(4, tijdstipVerzonden.get(Calendar.MINUTE));
        Assert.assertFalse(bericht.getBijhoudingen().isEmpty());

        BRPActie actie = bericht.getBijhoudingen().get(0);
        Assert.assertEquals(SoortActie.values()[1], actie.getIdentiteit().getSoort());
        Assert.assertEquals(Integer.valueOf(20111120), actie.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(Integer.valueOf(6030), actie.getIdentiteit().getPartij().getIdentiteit().getID().getWaarde());
        Calendar tijdstipOntlening = Calendar.getInstance();
        tijdstipOntlening.setTime(actie.getIdentiteit().getDatumTijdOntlening().getWaarde());
        Assert.assertEquals(2011, tijdstipOntlening.get(Calendar.YEAR));
        Assert.assertEquals(5, tijdstipOntlening.get(Calendar.MINUTE));
    }

    @Override
    protected Class<Bericht> getBindingClass() {
        return Bericht.class;
    }

    @Test
    @Ignore //Momenteel wordt alleen unmarshalling ondersteund door de binding
    public void testBerichtMarshallingMetAlleenId() throws JiBXException {
        Bericht bericht = new Bericht();
        bericht.setBerichtId("12");

        Assert.assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                "<afstamming01>" +
                "<stuurgegevens>" +
                "<berichtIdentificatie>12</berichtIdentificatie>" +
                "<tijdstipVerzonden/>" +
                "</stuurgegevens>" +
                "<bijhoudingen/>" +
                "</afstamming01>" +
                "</BRPBericht>", marshalObject(bericht));
    }

    @Test
    @Ignore //Momenteel wordt alleen unmarshalling ondersteund door de binding
    public void testBerichtMarshallingMetActie() throws JiBXException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 1, 2, 10, 04, 23);

        BRPActie actie = new BRPActie();
//        actie.setSoortActie("TEST");
//        actie.setPartijCode("0603");

        Bericht bericht = new Bericht();
        bericht.setBerichtId("12");
        bericht.setTijdstipVerzonden(calendar.getTime());
        bericht.setBijhoudingen(Arrays.asList(actie));

        Assert.assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                        "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                        "<afstamming01>" +
                        "<stuurgegevens>" +
                        "<berichtIdentificatie>12</berichtIdentificatie>" +
                        "<tijdstipVerzonden>2012-02-02T10:04:23</tijdstipVerzonden>" +
                        "</stuurgegevens>" +
                        "<bijhoudingen>" +
                        "<actie><soort>TEST</soort><partij>0603</partij><bronnen/></actie>" +
                        "</bijhoudingen>" +
                        "</afstamming01>" +
                        "</BRPBericht>", marshalObject(bericht));
    }

    @Test
    @Ignore //Momenteel wordt alleen unmarshalling ondersteund door de binding
    public void testBerichtMarshallingMetAlleenStuurgegevens() throws JiBXException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 1, 2, 10, 04, 23);

        Bericht bericht = new Bericht();
        bericht.setBerichtId("12");
        bericht.setTijdstipVerzonden(calendar.getTime());

        Assert.assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                        "<BRPBericht xmlns=\""+BRP_NAMESPACE+"\">" +
                        "<afstamming01>" +
                        "<stuurgegevens>" +
                        "<berichtIdentificatie>12</berichtIdentificatie>" +
                        "<tijdstipVerzonden>2012-02-02T10:04:23</tijdstipVerzonden>" +
                        "</stuurgegevens>" +
                        "<bijhoudingen/>" +
                        "</afstamming01>" +
                        "</BRPBericht>", marshalObject(bericht));
    }

}
