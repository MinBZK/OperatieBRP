/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unittest voor {@link TransformatieDw901}.
 */
public class TransformatieDw901Test extends AbstractTransformatieTest {
    private Transformatie transformer;
    private BRPActie actie;

    @Before
    public void setup() {
        transformer = new TransformatieDw901();
        actie = new BRPActie(SoortActie.CONVERSIE_GBA, getAdministratieveHandeling(), getAdministratieveHandeling().getPartij(),
                getAdministratieveHandeling().getDatumTijdRegistratie());
    }

    @Test
    public void testAcceptOk() throws Exception {
        assertTrue(transformer.accept(maakVerschilGroep()));
    }

    @Test
    public void testAcceptNok() throws Exception {
        assertFalse(transformer.accept(maakVerschilGroepAndersDanCat07Of13()));

        final VerschilGroep verschilgroep = maakVerschilGroep();
        verschilgroep.addVerschil(new Verschil(maakEntiteitSleutel("indicatie"), null, true, null, null));
        assertFalse(transformer.accept(verschilgroep));

        assertFalse(transformer.accept(maakVerschilGroepNietOk(true)));
        assertFalse(transformer.accept(maakVerschilGroepNietOk(false)));
    }

    @Test
    public void testExecute() throws Exception {
        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep verschillen = maakVerschilGroep();
        final VerschilGroep verschilGroep = transformer.execute(verschillen, actieVervalTbvLeveringMuts, context);
        assertTrue(verschilGroep.getVerschillen().isEmpty());
        assertEquals(verschillen.getFormeleHistorie(), verschilGroep.getFormeleHistorie());
    }

    @Test
    public void testGetDeltaWijziging() throws Exception {
        assertEquals(DeltaWijziging.DW_901, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakVerschilGroep() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie =
                new PersoonPersoonskaartHistorie(persoon, true);

        final Verschil actieInhoudVerschil =
                new Verschil(maakEntiteitSleutel("actieInhoud"), actie, actie, persoonPersoonskaartHistorie, persoonPersoonskaartHistorie);
        final Timestamp timestamp = maakTimestamp("2000-01-02 02");
        final Verschil datumTijdRegistratieVerschil =
                new Verschil(
                        maakEntiteitSleutel("datumTijdRegistratie"),
                        timestamp,
                        timestamp,
                        persoonPersoonskaartHistorie,
                        persoonPersoonskaartHistorie);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonPersoonskaartHistorie);
        verschilGroep.addVerschillen(Arrays.asList(actieInhoudVerschil, datumTijdRegistratieVerschil));
        return verschilGroep;
    }

    private VerschilGroep maakVerschilGroepNietOk(boolean actieOfTsRegNieuw) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie =
                new PersoonPersoonskaartHistorie(persoon, true);

        final Timestamp timestamp = maakTimestamp("2000-01-02 02");
        final Verschil actieInhoudVerschil;
        final Verschil datumTijdRegistratieVerschil;
        if (actieOfTsRegNieuw) {
            // actie inhoud is nieuw
            actieInhoudVerschil = new Verschil(maakEntiteitSleutel("actieInhoud"), null, actie, persoonPersoonskaartHistorie, persoonPersoonskaartHistorie);
            datumTijdRegistratieVerschil =
                    new Verschil(maakEntiteitSleutel("datumTijdRegistratie"), timestamp, timestamp, persoonPersoonskaartHistorie, persoonPersoonskaartHistorie);
        } else {
            // datum/tijd registratie is nieuw
            actieInhoudVerschil = new Verschil(maakEntiteitSleutel("actieInhoud"), actie, actie, persoonPersoonskaartHistorie, persoonPersoonskaartHistorie);
            datumTijdRegistratieVerschil = new Verschil(maakEntiteitSleutel("datumTijdRegistratie"), null, timestamp,
                    persoonPersoonskaartHistorie, persoonPersoonskaartHistorie);
        }

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonPersoonskaartHistorie);
        verschilGroep.addVerschillen(Arrays.asList(actieInhoudVerschil, datumTijdRegistratieVerschil));
        return verschilGroep;
    }

    private VerschilGroep maakVerschilGroepAndersDanCat07Of13() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie =
                new PersoonNaamgebruikHistorie(persoon, "stam", true, Naamgebruik.EIGEN);

        final Verschil actieInhoudVerschil =
                new Verschil(maakEntiteitSleutelNaamgebruik("actieInhoud"), null, actie, persoonNaamgebruikHistorie, persoonNaamgebruikHistorie);
        final Timestamp timestamp = maakTimestamp("2000-01-02 02");
        final Verschil datumTijdRegistratieVerschil =
                new Verschil(
                        maakEntiteitSleutelNaamgebruik("datumTijdRegistratie"),
                        timestamp,
                        timestamp,
                        persoonNaamgebruikHistorie,
                        persoonNaamgebruikHistorie);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonNaamgebruikHistorie);
        verschilGroep.addVerschillen(Arrays.asList(actieInhoudVerschil, datumTijdRegistratieVerschil));
        return verschilGroep;
    }

    private EntiteitSleutel maakEntiteitSleutel(final String veld) {
        final EntiteitSleutel persoonSleutel = new EntiteitSleutel(Persoon.class, "persoonPersoonskaartHistorieSet", null);
        final EntiteitSleutel result = new EntiteitSleutel(PersoonPersoonskaartHistorie.class, veld, persoonSleutel);
        result.addSleuteldeel("dataanvgel", 19980622);
        result.addSleuteldeel("tsreg", maakTimestamp("2011-03-16 02"));
        return result;
    }

    private EntiteitSleutel maakEntiteitSleutelNaamgebruik(final String veld) {
        final EntiteitSleutel persoonSleutel = new EntiteitSleutel(Persoon.class, "persoonNaamgebruikHistorieSet", null);
        final EntiteitSleutel result = new EntiteitSleutel(PersoonNaamgebruikHistorie.class, veld, persoonSleutel);
        result.addSleuteldeel("dataanvgel", 19980622);
        result.addSleuteldeel("tsreg", maakTimestamp("2011-03-16 02"));
        return result;
    }
}
