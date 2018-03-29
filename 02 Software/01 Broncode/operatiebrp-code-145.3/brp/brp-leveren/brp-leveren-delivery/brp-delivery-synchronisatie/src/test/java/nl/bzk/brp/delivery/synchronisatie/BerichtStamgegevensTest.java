/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static java.util.Arrays.asList;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.delivery.algemeen.writer.BerichtTestUtil;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStamgegevens;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Test;

/**
 * BerichtStamgegevensTest.
 */
public class BerichtStamgegevensTest extends BerichtTestUtil {

    private BerichtStamgegevens berichtStamgegevens;

    @Test
    public void testWriteBerichtStamgegevens_Datum() throws Exception {
        //@formatter:off
        berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE))
            ), asList(
                ImmutableMap.<String, Object>builder().put("datuitgifte", 20140101).build()
            ))
        );
        //@formatter:on

        final String output = schrijfBericht();
        System.out.println(output);
        assertGelijk("stamgegevens3.xml", output);
    }


    @Test
    public void testWriteBerichtStamgegevens_StringMetMinLengte() throws Exception {
        //@formatter:off
        berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON_ADRES),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE))
            ), asList(
                ImmutableMap.<String, Object>builder().put("gem", "Amsterdam").build(),
                ImmutableMap.<String, Object>builder().put("gem", "Rotterdam").build(),
                ImmutableMap.<String, Object>builder().put("gem", "Utrecht").build()
            ))
        );
        //@formatter:on

        final String output = schrijfBericht();
        assertGelijk("stamgegevens1.xml", output);
    }


    @Test
    public void testWriteBerichtStamgegevens_GetalMetMinLengte() throws Exception {
        //@formatter:off
        berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON_VERSTREKKINGSBEPERKING),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE))
            ), Collections.singletonList(
                ImmutableMap.<String, Object>builder().put("gemverordening", 1).build()
            ))
        );
        //@formatter:on

        final String output = schrijfBericht();
        System.out.println(output);
        assertGelijk("stamgegevens6.xml", output);
    }

    @Test
    public void testWriteBerichtStamgegevens_AttribuutZonderMinLengte() throws Exception {
        //@formatter:off
        berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE))
            ), Collections.singletonList(
                ImmutableMap.<String, Object>builder().put("sorteervolgorde", 2).build()
            ))
        );
        //@formatter:on

        final String output = schrijfBericht();
        System.out.println(output);
        assertGelijk("stamgegevens4.xml", output);
    }

    @Test
    public void testWriteBerichtStamgegevens_AttribuutZonderIdentDb() throws Exception {
        //@formatter:off
        berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NADEREAANDUIDINGVERVAL)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NADEREAANDUIDINGVERVAL))
            ), Collections.singletonList(
                ImmutableMap.<String, Object>builder().put("bpnr", 2).build()
            ))
        );
        //@formatter:on

        final String output = schrijfBericht();
        System.out.println(output);
        assertGelijk("stamgegevens5.xml", output);
    }

    @Test
    public void testWriteBerichtStamgegevens_WaardeEmpty() throws Exception {
        //formatter:off
        berichtStamgegevens = new BerichtStamgegevens(
                new StamtabelGegevens(new StamgegevenTabel(
                        ElementHelper.getObjectElement(Element.PERSOON),
                        Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER)),
                        Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER))
                ), Collections.singletonList(ImmutableMap.<String, Object>builder().put("huisnr", "").build()
                ))
        );
        //@formatter:on

        final String output = schrijfBericht();
        System.out.println(output);
        assertGelijk("stamgegevens5.xml", output);
    }


    private String schrijfBericht() throws TransformerException, XMLStreamException {
        return geefOutput(writer ->
                BerichtStamgegevenWriter.write(berichtStamgegevens, writer));
    }
}
