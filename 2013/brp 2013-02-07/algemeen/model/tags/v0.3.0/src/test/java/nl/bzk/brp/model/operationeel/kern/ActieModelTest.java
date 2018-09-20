/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Aktenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijving;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;

public class ActieModelTest {

    @Test
    public void copyActieBerichtNaarModel() {
        ActieBericht actieBericht = maakActie();
        ActieModel actieModel = new ActieModel(actieBericht, null);
        Assert.assertEquals(0, actieModel.getBronnen().size());
        //Bronnen worden niet meer door constructor gekopieerd.
        /*for (Bron bron : actieModel.getBronnen()) {
            Assert.assertEquals(StatischeObjecttypeBuilder.GEBOORTE_AKTE, bron.getDocument().getSoort());
            Assert.assertEquals(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                bron.getDocument().getStandaard().getPartij());
            Assert.assertEquals("AkteNr", bron.getDocument().getStandaard().getAktenummer().getOmschrijving());
        }*/
    }

    private ActieBericht maakActie() {
        ActieBericht actieBericht = new ActieRegistratieNationaliteitBericht();
        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setPartij(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        List<ActieBronBericht> bronnen = new ArrayList<ActieBronBericht>();
        bronnen.add(maakBronBericht());
        actieBericht.setBronnen(bronnen);
        return actieBericht;
    }

    private ActieBronBericht maakBronBericht() {
        ActieBronBericht bron = new ActieBronBericht();
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
