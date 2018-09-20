/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Aktenummer;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.model.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BronBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.logisch.Bron;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;

public class ActieModelTest {

    @Test
    public void copyActieBerichtNaarModel() {
        ActieBericht actieBericht = maakActie();
        ActieModel actieModel = new ActieModel(actieBericht);
        Assert.assertEquals(1, actieModel.getBronnen().size());
        for (Bron bron : actieModel.getBronnen()) {
            Assert.assertEquals(StatischeObjecttypeBuilder.GEBOORTE_AKTE, bron.getDocument().getSoort());
            Assert.assertEquals(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, bron.getDocument().getStandaard().getPartij());
            Assert.assertEquals("AkteNr", bron.getDocument().getStandaard().getAktenummer().getWaarde());
        }
    }

    private ActieBericht maakActie() {
        ActieBericht actieBericht = new ActieBericht();
        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        actieBericht.setPartij(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        List<BronBericht> bronnen = new ArrayList<BronBericht>();
        bronnen.add(maakBronBericht());
        actieBericht.setBronnen(bronnen);
        return actieBericht;
    }

    private BronBericht maakBronBericht() {
        BronBericht bron = new BronBericht();
        bron.setDocument(new DocumentBericht());
        bron.getDocument().setSoort(StatischeObjecttypeBuilder.GEBOORTE_AKTE);
        bron.getDocument().setStandaard(new DocumentStandaardGroepBericht());
        bron.getDocument().getStandaard().setAktenummer(new Aktenummer("AkteNr"));
        bron.getDocument().getStandaard().setIdentificatie(new DocumentIdentificatie("docIds"));
        bron.getDocument().getStandaard().setOmschrijving(new DocumentOmschrijving("omsch"));
        bron.getDocument().getStandaard().setPartij(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        return bron;
    }

}
