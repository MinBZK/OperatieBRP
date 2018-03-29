/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.algemeen;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.junit.Test;

public class MetaObjectLiteralTest {
    @Test
    public void getType() throws Exception {
        final MetaObjectLiteral metaObjectLiteral = new MetaObjectLiteral(maakPersoon(ZonedDateTime.now()), ExpressieType.DATUMTIJD);
        assertEquals(ExpressieType.DATUMTIJD, metaObjectLiteral.getType(new Context()));
    }

    @Test
    public void alsString() throws Exception {
        final MetaObjectLiteral metaObjectLiteral = new MetaObjectLiteral(maakPersoon(ZonedDateTime.now()), ExpressieType.DATUMTIJD);
        assertEquals("@Persoon", metaObjectLiteral.toString());
    }

    @Test
    public void equals() throws Exception {
        final MetaObjectLiteral metaObjectLiteral1 = new MetaObjectLiteral(maakPersoon(ZonedDateTime.now()), ExpressieType.DATUMTIJD);
        final MetaObjectLiteral metaObjectLiteral2 = new MetaObjectLiteral(maakPersoon(ZonedDateTime.now()), ExpressieType.DATUMTIJD);
        final MetaObjectLiteral metaObjectLiteral3 = new MetaObjectLiteral(maakPersoon(ZonedDateTime.now().plusYears(1)), ExpressieType.DATUMTIJD);

        assertTrue(metaObjectLiteral1.equals(metaObjectLiteral1));
        //geen deep equals, inhoudelijk gelijk wordt op andere plek geimplementeerd
        assertFalse(metaObjectLiteral1.equals(metaObjectLiteral2));
        assertFalse(metaObjectLiteral1.equals(metaObjectLiteral3));
        assertFalse(metaObjectLiteral1.equals(null));
        assertFalse(metaObjectLiteral1.equals(maakPersoon(ZonedDateTime.now())));
    }

    private MetaObject maakPersoon(final ZonedDateTime tijdstipLaatsteWijzigingGBASystematiek) {
        final Actie actieInhoud = TestVerantwoording.maakActie(1, tijdstipLaatsteWijzigingGBASystematiek);
        //@formatter:off
        MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(),
                            tijdstipLaatsteWijzigingGBASystematiek)
                    .eindeRecord()
            .eindeGroep()
            .build();
        //@formatter:on
        return persoon;
    }
}
