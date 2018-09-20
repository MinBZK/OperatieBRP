/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;


import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsaanduiding.GeslachtsaanduidingGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.GeslachtsnaamcomponentVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.voornaam.VoornamenVerwerker;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieNaamGeslachtUitvoerderTest {

    //Bestaande situatie
    private PersoonHisVolledigImpl persoonHisVolledig;
    private PersoonVoornaamHisVolledigImpl persoonVoornaamHisVolledig1;
    private PersoonVoornaamHisVolledigImpl persoonVoornaamHisVolledig2;
    private PersoonGeslachtsnaamcomponentHisVolledigImpl persoonGeslachtsnaamHisVolledig;

    @Before
    public void init() {
        bereidBestaandeSituatieVoor();
    }

    @Test
    public void testBestaandeVoornaamAanpassen() {
        RegistratieNaamGeslachtUitvoerder uitvoerder = maakUitvoerder(persoonHisVolledig, maakNieuwSituatieVoornaam(2));
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(uitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(uitvoerder).get(0) instanceof VoornamenVerwerker);
    }

    @Test
    public void testNieuweVoornaam() {
        RegistratieNaamGeslachtUitvoerder uitvoerder = maakUitvoerder(persoonHisVolledig, maakNieuwSituatieVoornaam(3));
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(uitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(uitvoerder).get(0) instanceof VoornamenVerwerker);
    }

    @Test
    public void testGeslachtsnaamcomponentAanpassen() {
        RegistratieNaamGeslachtUitvoerder uitvoerder = maakUitvoerder(persoonHisVolledig, maakNieuwSituatieGeslachtsnaam());
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(uitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(uitvoerder).get(0) instanceof GeslachtsnaamcomponentVerwerker);
        Assert.assertEquals(persoonGeslachtsnaamHisVolledig, getModelUitRegel(getVerwerkingsregels(uitvoerder).get(0)));
    }

    @Test
    public void testGeslachtsaanduidingAanpassen() {
        RegistratieNaamGeslachtUitvoerder uitvoerder = maakUitvoerder(persoonHisVolledig, maakNieuwSituatieGeslachtsaanduiding());
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(uitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(uitvoerder).get(0) instanceof GeslachtsaanduidingGroepVerwerker);
        Assert.assertEquals(persoonHisVolledig, getModelUitRegel(getVerwerkingsregels(uitvoerder).get(0)));
    }

    @Test
    public void testAllesAanpassen() {
        RegistratieNaamGeslachtUitvoerder uitvoerder = maakUitvoerder(persoonHisVolledig, maakNieuwSituatieCompleet());
        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(3, getVerwerkingsregels(uitvoerder).size());
    }

    /**
     * Maakt de uitvoerder.
     *
     * @param bestaandeSituatie de bestaande situatie die door middel van getModelRootObject wordt teruggegeven
     * @param nieuweSituatie    de nieuwe situatie die door middel van getBerichtRootObject wordt teruggegeven
     * @return de uitvoerder
     */
    private RegistratieNaamGeslachtUitvoerder maakUitvoerder(final PersoonHisVolledigImpl bestaandeSituatie,
                                                         final PersoonBericht nieuweSituatie)
    {
        return new RegistratieNaamGeslachtUitvoerder() {
            @Override
            public PersoonBericht getBerichtRootObject() {
                return nieuweSituatie;
            }

            @Override
            public PersoonHisVolledigImpl getModelRootObject() {
                return bestaandeSituatie;
            }
        };
    }

    /**
     * Zet de bestaande situatie klaar.
     */
    private void bereidBestaandeSituatieVoor() {
        ActieModel actieModel = maakActie();
        persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeslachtsaanduidingRecord(actieModel)
                    .geslachtsaanduiding(Geslachtsaanduiding.MAN)
                .eindeRecord()
                .build();

        persoonVoornaamHisVolledig1 =
                new PersoonVoornaamHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(1))
                        .nieuwStandaardRecord(actieModel)
                        .naam("abc").eindeRecord().build();

        persoonVoornaamHisVolledig2 =
                new PersoonVoornaamHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(2))
                        .nieuwStandaardRecord(actieModel)
                        .naam("abc").eindeRecord().build();

        persoonGeslachtsnaamHisVolledig =
                new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoonHisVolledig, new VolgnummerAttribuut(1))
                        .nieuwStandaardRecord(actieModel)
                        .stam("xyz").eindeRecord().build();

        persoonHisVolledig.getVoornamen().add(persoonVoornaamHisVolledig1);
        persoonHisVolledig.getVoornamen().add(persoonVoornaamHisVolledig2);

        persoonHisVolledig.getGeslachtsnaamcomponenten().add(persoonGeslachtsnaamHisVolledig);
    }

    private PersoonBericht maakNieuwSituatieVoornaam(final Integer volgnummer) {
        PersoonVoornaamBericht persoonVoornaamBericht = new PersoonVoornaamBericht();
        persoonVoornaamBericht.setVolgnummer(new VolgnummerAttribuut(volgnummer));
        persoonVoornaamBericht.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        persoonVoornaamBericht.getStandaard().setNaam(new VoornaamAttribuut("zzz"));
        persoonVoornaamBericht.getStandaard().setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20100101));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setObjectSleutel("111");
        persoonBericht.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        persoonBericht.getVoornamen().add(persoonVoornaamBericht);

        return persoonBericht;
    }

    private PersoonBericht maakNieuwSituatieGeslachtsnaam() {
        PersoonGeslachtsnaamcomponentBericht persoonGeslachtsnaamBericht = new PersoonGeslachtsnaamcomponentBericht();
        // Moet altijd volgnummer 1 hebben.
        persoonGeslachtsnaamBericht.setVolgnummer(new VolgnummerAttribuut(1));
        persoonGeslachtsnaamBericht.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        persoonGeslachtsnaamBericht.getStandaard().setStam(new GeslachtsnaamstamAttribuut("zzz"));
        persoonGeslachtsnaamBericht.getStandaard().setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20100101));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setObjectSleutel("111");
        persoonBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoonBericht.getGeslachtsnaamcomponenten().add(persoonGeslachtsnaamBericht);

        return persoonBericht;
    }

    private PersoonBericht maakNieuwSituatieGeslachtsaanduiding() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setObjectSleutel("111");
        PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding = new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduiding.setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));
        persoonBericht.setGeslachtsaanduiding(geslachtsaanduiding);
        return persoonBericht;
    }

    private PersoonBericht maakNieuwSituatieCompleet() {
        PersoonBericht persoonBericht = maakNieuwSituatieGeslachtsaanduiding();
        persoonBericht.setVoornamen(maakNieuwSituatieVoornaam(1).getVoornamen());
        persoonBericht.setGeslachtsnaamcomponenten(maakNieuwSituatieGeslachtsnaam().getGeslachtsnaamcomponenten());
        return persoonBericht;
    }

    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel maakActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.DUMMY), null,
                        null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null, DatumTijdAttribuut.nu(), null);
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieNaamGeslachtUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }

    /**
     * Haalt het Model uit de verwerkingsregel.
     *
     * @param verwerkingsregel de verwerkings regel
     * @return een object
     */
    private Object getModelUitRegel(final Verwerkingsregel verwerkingsregel) {
        return ReflectionTestUtils.getField(verwerkingsregel, "model");
    }
}
