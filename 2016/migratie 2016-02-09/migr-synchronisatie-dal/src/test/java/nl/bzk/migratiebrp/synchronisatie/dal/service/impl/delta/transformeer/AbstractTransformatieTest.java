/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import org.junit.Before;

public abstract class AbstractTransformatieTest {

    private AdministratieveHandeling administratieveHandeling;
    private SimpleDateFormat dateFormat;
    protected Persoon persoonOud;
    protected Persoon persoonNieuw;
    protected DeltaBepalingContext context;

    @Before
    public final void abstractSetup() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        administratieveHandeling = new AdministratieveHandeling(new Partij("naam", 0), SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        administratieveHandeling.setDatumTijdRegistratie(maakTimestamp("1990-01-02 02"));
        persoonOud = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonNieuw = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonNieuw.setAdministratieveHandeling(administratieveHandeling);
        context = new DeltaBepalingContext(persoonNieuw, persoonOud, null, false);
    }

    protected VerschilGroep maakPersoonIDVerschilGroep(final VerschilType verschilType) {
        return maakPersoonIDVerschilGroep(verschilType, true, 1);
    }

    protected VerschilGroep maakPersoonIDVerschilGroep(final VerschilType verschilType, final boolean isActueel) {
        return maakPersoonIDVerschilGroep(verschilType, isActueel, 1);
    }

    protected VerschilGroep maakPersoonIDVerschilGroep(final VerschilType verschilType, final boolean isActueel, final int aantalVerschillen) {
        final Persoon testPersoon = new Persoon(SoortPersoon.INGESCHREVENE);

        VerschilGroep verschilGroep = null;
        for (int index = 0; index < aantalVerschillen; index++) {
            final PersoonIDHistorie historieRij = new PersoonIDHistorie(testPersoon);
            historieRij.setDatumTijdRegistratie(new Timestamp(1000L));
            if (!isActueel) {
                historieRij.setDatumTijdVerval(new Timestamp(System.currentTimeMillis()));
            }

            if (verschilGroep == null) {
                verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historieRij);
            }

            verschilGroep.addVerschil(
                new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "veld"), new Object(), null, verschilType, historieRij, historieRij));
        }
        return verschilGroep;
    }

    protected final AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    protected final void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    protected BRPActie maakDummyActie() {
        final AdministratieveHandeling bijhouding =
                new AdministratieveHandeling(new Partij("testPartij", 606), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        bijhouding.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        return new BRPActie(SoortActie.CONVERSIE_GBA, bijhouding, bijhouding.getPartij(), bijhouding.getDatumTijdRegistratie());
    }

    protected Verschil zoekNieuweRijVerschil(final List<Verschil> verschillen, final String historieSet) {
        return zoekVerschil(verschillen, historieSet);
    }

    protected Verschil zoekActieTbvLeveringMutatiesVerschil(final List<Verschil> verschillen) {
        return zoekVerschil(verschillen, "actieVervalTbvLeveringMutaties");
    }

    protected Verschil zoekIndicatieTbvLeveringVerschil(final List<Verschil> verschillen) {
        return zoekVerschil(verschillen, "indicatieVoorkomenTbvLeveringMutaties");
    }

    protected Verschil zoekTsVervalVerschil(final List<Verschil> verschillen) {
        return zoekVerschil(verschillen, "datumTijdVerval");
    }

    protected Verschil zoekActieVervalVerschil(final List<Verschil> verschillen) {
        return zoekVerschil(verschillen, "actieVerval");
    }

    protected Verschil zoekVerschil(final List<Verschil> verschillen, final String veld) {
        Verschil result = null;
        for (final Verschil verschil : verschillen) {
            if (veld.equals(verschil.getSleutel().getVeld())) {
                result = verschil;
            }
        }
        return result;
    }

    protected Verschil zoekNieuweRijAanpassingVerschil(final ArrayList<Verschil> verschillen, final String veld) {
        Verschil result = null;
        for (final Verschil verschil : verschillen) {
            if (veld.equals(verschil.getSleutel().getVeld()) && VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST.equals(verschil.getVerschilType())) {
                result = verschil;
            }
        }
        return result;
    }

    protected BRPActie maakActieVerval(final String categorie, final int stapel, final int voorkomen) {
        final BRPActie result =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    administratieveHandeling,
                    administratieveHandeling.getPartij(),
                    administratieveHandeling.getDatumTijdRegistratie());
        result.setLo3Voorkomen(
            new Lo3Voorkomen(
                new Lo3Bericht(
                    "ActualiseringVanFormeelHistorischeRijTest",
                    Lo3BerichtenBron.INITIELE_VULLING,
                    new Timestamp(System.currentTimeMillis()),
                    "testdata",
                    true),
                categorie,
                stapel,
                voorkomen));
        return result;
    }

    protected Timestamp maakTimestamp(final String ts) {
        try {
            return new Timestamp(dateFormat.parse(ts).getTime());
        } catch (final ParseException e) {
            throw new IllegalArgumentException("ongeldige datum: " + ts, e);
        }
    }
}
