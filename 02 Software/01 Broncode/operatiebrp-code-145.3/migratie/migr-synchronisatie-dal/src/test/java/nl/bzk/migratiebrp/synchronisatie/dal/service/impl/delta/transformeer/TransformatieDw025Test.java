/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;

public class TransformatieDw025Test extends AbstractTransformatieTest {

    private TransformatieDw025 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw025();
    }

    @Test
    public void testAcceptNationaliteitWijziging() {
        assertTrue(transformer.accept(maakVerschilGroep()));
    }

    @Test
    public void testAcceptAdresWijziging() {
        assertTrue(transformer.accept(maakVerschilGroepAdres()));
    }

    @Test
    public void testExecute() {
        final VerschilGroep adresActualisering = maakVerschilGroep();
        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep aangepasteActualisering = transformer.execute(adresActualisering, actieVervalTbvLeveringMuts, context);
        assertNotNull(aangepasteActualisering);
        final ArrayList<Verschil> nieuweVerschillen = new ArrayList<>();
        for (final Verschil verschil : aangepasteActualisering) {
            nieuweVerschillen.add(verschil);
        }
        for (final Verschil verschil : adresActualisering) {
            nieuweVerschillen.remove(verschil);
        }
        assertEquals(1, nieuweVerschillen.size());
        // verify verschil voor actieVervalTbvLeveringMutaties
        final Verschil actieVervalTbvLeveringMutatiesVerschil = zoekVerschil(nieuweVerschillen, "actieVervalTbvLeveringMutaties");
        assertNotNull(actieVervalTbvLeveringMutatiesVerschil);
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_025, transformer.getDeltaWijziging());
    }

    private VerschilGroep maakVerschilGroep() {
        final BRPActie actie =
                new BRPActie(
                        SoortActie.CONVERSIE_GBA,
                        getAdministratieveHandeling(),
                        getAdministratieveHandeling().getPartij(),
                        getAdministratieveHandeling().getDatumTijdRegistratie());
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("NL", "0001"));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);

        final Verschil actieVervalVerschil =
                new Verschil(maakEntiteitSleutel("actieVerval"), null, actie, persoonNationaliteitHistorie, persoonNationaliteitHistorie);
        final Verschil datumTijdVervalVerschil =
                new Verschil(
                        maakEntiteitSleutel("datumTijdVerval"),
                        null,
                        maakTimestamp("2000-01-02 02"),
                        persoonNationaliteitHistorie,
                        persoonNationaliteitHistorie);
        final Verschil nadereAanduidingVervalVerschil =
                new Verschil(maakEntiteitSleutel("nadereAanduidingVerval"), null, 'O', persoonNationaliteitHistorie, persoonNationaliteitHistorie);
        final Verschil datumEindeGeldigheidVerschil =
                new Verschil(maakEntiteitSleutel("datumEindeGeldigheid"), null, 20000101, persoonNationaliteitHistorie, persoonNationaliteitHistorie);
        final Verschil actieAanpassingGeldigheidVerschil =
                new Verschil(maakEntiteitSleutel("actieAanpassingGeldigheid"), null, actie, persoonNationaliteitHistorie, persoonNationaliteitHistorie);
        final Verschil migratieRedenBeeindigingNationaliteit =
                new Verschil(
                        maakEntiteitSleutel("migratieRedenBeeindigenNationaliteit"),
                        null,
                        "401",
                        persoonNationaliteitHistorie,
                        persoonNationaliteitHistorie);
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonNationaliteitHistorie);
        verschilGroep.addVerschillen(
                Arrays.asList(
                        actieVervalVerschil,
                        datumTijdVervalVerschil,
                        nadereAanduidingVervalVerschil,
                        datumEindeGeldigheidVerschil,
                        actieAanpassingGeldigheidVerschil,
                        migratieRedenBeeindigingNationaliteit));
        return verschilGroep;
    }

    private VerschilGroep maakVerschilGroepAdres() {
        final BRPActie actie =
                new BRPActie(
                        SoortActie.CONVERSIE_GBA,
                        getAdministratieveHandeling(),
                        getAdministratieveHandeling().getPartij(),
                        getAdministratieveHandeling().getDatumTijdRegistratie());
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonAdres persoonAdres = new PersoonAdres(persoon);
        final PersoonAdresHistorie persoonAdresHistorie =
                new PersoonAdresHistorie(
                        persoonAdres,
                        SoortAdres.WOONADRES,
                        new LandOfGebied("0001", "Land"),
                        new RedenWijzigingVerblijf('T', "Test"));

        final Verschil actieVervalVerschil = new Verschil(maakEntiteitSleutel("actieVerval"), null, actie, persoonAdresHistorie, persoonAdresHistorie);
        final Verschil datumTijdVervalVerschil =
                new Verschil(maakEntiteitSleutel("datumTijdVerval"), null, maakTimestamp("2000-01-02 02"), persoonAdresHistorie, persoonAdresHistorie);
        final Verschil nadereAanduidingVervalVerschil =
                new Verschil(maakEntiteitSleutel("nadereAanduidingVerval"), null, 'O', persoonAdresHistorie, persoonAdresHistorie);
        final Verschil datumEindeGeldigheidVerschil =
                new Verschil(maakEntiteitSleutel("datumEindeGeldigheid"), null, 20000101, persoonAdresHistorie, persoonAdresHistorie);
        final Verschil actieAanpassingGeldigheidVerschil =
                new Verschil(maakEntiteitSleutel("actieAanpassingGeldigheid"), null, actie, persoonAdresHistorie, persoonAdresHistorie);
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(persoonAdresHistorie);
        verschilGroep.addVerschillen(
                Arrays.asList(
                        actieVervalVerschil,
                        datumTijdVervalVerschil,
                        nadereAanduidingVervalVerschil,
                        datumEindeGeldigheidVerschil,
                        actieAanpassingGeldigheidVerschil));
        return verschilGroep;
    }

    private EntiteitSleutel maakEntiteitSleutel(final String veld) {
        final EntiteitSleutel persoonSleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet", null);
        final EntiteitSleutel persoonNationaliteitSleutel =
                new EntiteitSleutel(PersoonNationaliteit.class, "persoonNationaliteitHistorieSet", persoonSleutel);
        return new EntiteitSleutel(PersoonNationaliteitHistorie.class, veld, persoonNationaliteitSleutel);
    }
}
