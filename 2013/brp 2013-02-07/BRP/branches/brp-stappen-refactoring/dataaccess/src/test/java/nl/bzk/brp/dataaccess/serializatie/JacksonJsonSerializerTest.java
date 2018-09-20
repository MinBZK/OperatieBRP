/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PersoonVolledigRepository;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * tests voor {@link JacksonJsonSerializer}.
 */
public class JacksonJsonSerializerTest extends AbstractRepositoryTestCase {

    Logger log = LoggerFactory.getLogger(JacksonJsonSerializerTest.class);

    @Inject
    @Named("jacksonJsonSerializer")
    private JacksonJsonSerializer serializer;

    @Inject
    private PersoonVolledigRepository repository;

    @Test
    public void kanEenLeegObjectSerializeren() throws Exception {
        // given
        PersoonVolledig persoon = new PersoonVolledig(1);

        // when
        String data = new String(serializer.serializeer(persoon));

        // then
        JSONAssert.assertEquals("{\"id\":1,\"versie\":1}", data, true);
    }

    @Test
    public void kanEenObjectUitDatabaseSerializeren() throws Exception {
        // given
        PersoonVolledig persoon = repository.haalPersoonOp(1001);

        // when
        String data = new String(serializer.serializeer(persoon));
        log.info("JSON String: \n-----------------------\n{}\n----------------------", data);

        // then
        JSONAssert.assertEquals(
                "{\"adressen\":[{\"adresseerbaarObject\":\"1492\",\"afgekorteNaamOpenbareRuimte\":\"Damstr\",\"datumAanvangAdreshouding\":20120101,\"gemeente\":{},\"huisletter\":\"a\",\"huisnummer\":1,\"huisnummertoevoeging\":\"II\",\"iD\":1203,\"identificatiecodeNummeraanduiding\":\"1581\",\"indicatiePersoonNietAangetroffenOpAdres\":false,\"land\":{},\"naamOpenbareRuimte\":\"Damstraat\",\"postcode\":\"3984NX\",\"woonplaats\":{}},{\"afgekorteNaamOpenbareRuimte\":\"Kerkln\",\"datumAanvangAdreshouding\":20110101,\"gemeente\":{},\"huisletter\":\"b\",\"huisnummer\":5,\"iD\":1202,\"identificatiecodeNummeraanduiding\":\"2012\",\"indicatiePersoonNietAangetroffenOpAdres\":false,\"land\":{},\"naamOpenbareRuimte\":\"Kerklaan\",\"postcode\":\"3971AB\",\"woonplaats\":{}}],\"id\":1001,\"soort\":\"INGESCHREVENE\",\"versie\":1}",
                data, true);
    }

    @Test
    public void kanEenObjectMetBetrokkenheidUitDatabaseSerializeren() throws Exception {
        // given
        PersoonVolledig persoon = repository.haalPersoonOp(8731137);

        // when
        String data = new String(serializer.serializeer(persoon));
        log.info("JSON String: \n-----------------------\n{}\n----------------------", data);

        // then
        JSONAssert.assertEquals(
                "{\"id\":8731137,\"soort\":\"INGESCHREVENE\",\"versie\":1}",
                data, true);
    }
}
