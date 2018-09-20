/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AbstractHisPartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijAttribuut;
import nl.bzk.brp.model.basis.BestaansperiodeFormeelImplicietMaterieelAutaut;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import nl.bzk.brp.model.bericht.kern.AbstractAdministratieveHandelingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.*;
import nl.bzk.brp.model.operationeel.ber.AbstractBerichtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.ber.AbstractBerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.operationeel.kern.AbstractActieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.hibernate.annotations.Fetch;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Tests die handmatige aanpassingen aan de gegenereerde code valideren.
 */
public class HandmatigeAanpassingenGeneratieTest {

    private static final String JSON_PROPERTY = "JsonProperty";

    @Test
    public void testOnderzoekHisVolledigImplHeeftJuisteJSONAnnotaties() {
        assertVeldBevatNietAnnotatie(OnderzoekHisVolledigImpl.class, "personenInOnderzoek", JsonManagedReference.class);
    }

    @Test
    public void testOnderzoekHisVolledigImplInAbstractPersoonOnderzoekHisVolledigImplHeeftJuisteJSONAnnotaties() {
        assertVeldBevatNietAnnotatie(AbstractPersoonOnderzoekHisVolledigImpl.class, "onderzoek", JsonBackReference.class);
        assertVeldBevatNietAnnotatie(AbstractPersoonOnderzoekHisVolledigImpl.class, "persoon", JsonBackReference.class);
    }

    @Test
    public void testAbstractPersoonOnderzoekHisVolledigImplHeeftBenodigdeSetters() throws ClassNotFoundException {
        final Map<String, Class<?>> setters = new HashMap<>();
        setters.put("setPersoon", PersoonHisVolledigImpl.class);

        final Class<?> clazz =
            Class.forName("nl.bzk.brp.model.hisvolledig.impl.kern.AbstractPersoonOnderzoekHisVolledigImpl");

        for (final String setter : setters.keySet()) {
            try {
                clazz.getDeclaredMethod(setter, setters.get(setter));
            } catch (final NoSuchMethodException e) {
                fail(String.format("AbstractPersoonOnderzoekHisVolledigImpl mist methode: %1$s()", setter));
            }
        }
    }

    @Test
    public void testAbstractActieModelHeeftJuisteJSONAnnotaties() {
        assertVeldBevatAnnotatie(AbstractActieModel.class, "administratieveHandeling", JsonProperty.class);
    }

    @Test
    public void referentieleLosheidInAbstractBerichtModel() {
        final Field administratieveHandelingId =
                ReflectionUtils.findField(AbstractBerichtStandaardGroepModel.class, "administratieveHandelingId");

        assertThat(administratieveHandelingId.getDeclaredAnnotations().length, is(2));
        for (final Annotation annotation : administratieveHandelingId.getDeclaredAnnotations()) {
            assertThat(annotation.annotationType().getSimpleName(), isOneOf(javax.persistence.Column.class.getSimpleName(),
                com.fasterxml.jackson.annotation.JsonProperty.class.getSimpleName()));
        }
    }

    @Test
    public void referentieleLosheidInAbstractBerichtModelPartijZendend() {
        final Field zendendePartijId =
                ReflectionUtils.findField(AbstractBerichtStuurgegevensGroepModel.class, "zendendePartijId");

        assertThat(zendendePartijId.getDeclaredAnnotations().length, is(2));
        for (final Annotation annotation : zendendePartijId.getDeclaredAnnotations()) {
            assertThat(annotation.annotationType().getSimpleName(), isOneOf(javax.persistence.Column.class.getSimpleName(), JSON_PROPERTY));
        }
    }

    @Test
    public void referentieleLosheidInAbstractBerichtModelPartijOntvangend() {
        final Field ontvangendePartijId =
                ReflectionUtils.findField(AbstractBerichtStuurgegevensGroepModel.class, "ontvangendePartijId");

        assertThat(ontvangendePartijId.getDeclaredAnnotations().length, is(2));
        for (final Annotation annotation : ontvangendePartijId.getDeclaredAnnotations()) {
            assertThat(annotation.annotationType().getSimpleName(), isOneOf(javax.persistence.Column.class.getSimpleName(), JSON_PROPERTY));
        }
    }

    @Test
    public void testAbstractBerichtStuurgegevensGroepModelHeeftBenodigdeSetters() throws ClassNotFoundException {
        final Map<String, Class<?>> setters = new HashMap<>();
        setters.put("setZendendeSysteem", SysteemNaamAttribuut.class);
        setters.put("setOntvangendeSysteem", SysteemNaamAttribuut.class);
        setters.put("setReferentienummer", ReferentienummerAttribuut.class);
        setters.put("setCrossReferentienummer", ReferentienummerAttribuut.class);
        setters.put("setDatumTijdVerzending", DatumTijdAttribuut.class);
        setters.put("setDatumTijdOntvangst", DatumTijdAttribuut.class);

        final Class<?> clazz =
                Class.forName("nl.bzk.brp.model.operationeel.ber.AbstractBerichtStuurgegevensGroepModel");

        for (final String setter : setters.keySet()) {
            try {
                clazz.getDeclaredMethod(setter, setters.get(setter));
            } catch (final NoSuchMethodException e) {
                fail(String.format("AbstractBerichtStuurgegevensGroepModel mist methode: %1$s()", setter));
            }
        }
    }

    @Test
    public void testAbstractPersoonHisVolledigImplHeeftJuisteJSONAnnotaties() {
        assertVeldBevatNietAnnotatie(AbstractPersoonHisVolledigImpl.class, "afnemerindicaties", JsonProperty.class);
    }

    @Test
    public void testAbstractBetrokkenheidHisVolledigImplHeeftJuisteJSONAnnotaties() {
        assertVeldBevatNietAnnotatie(AbstractBetrokkenheidHisVolledigImpl.class, "persoon", JsonBackReference.class);
    }

    @Test
    public void testAbstractAdministratieveHandelingBerichtHeeftValidAnnotatie() {
        assertMethodBevatAnnotatie(AbstractAdministratieveHandelingBericht.class, "getActies", Valid.class);
    }

    @Test
    public void testSoortPartijAttribuutJuisteAnnotaties() {
        assertMethodBevatAnnotatie(SoortPartijAttribuut.class, "getWaarde", Enumerated.class);
        assertMethodBevatNietAnnotatie(SoortPartijAttribuut.class, "getWaarde", Fetch.class);
        assertMethodBevatNietAnnotatie(SoortPartijAttribuut.class, "getWaarde", ManyToOne.class);
    }

    /**
     * AbstractDienstModel is komen te vervallen, in generatoren zat mogelijk een uitzondering om deze interface hier op te plaatsen
     */
    @Test
    public void testAbstractDienstImplementeertVerantwoordingsEntiteit() {
        assertHeeftInterface(AbstractDienst.class, VerantwoordingsEntiteit.class);
    }

    @Test
    public void testAbstractHisDienstImplementeertBestaansperiodeFormeelImplicietMaterieelAutaut() {
        assertHeeftInterface(AbstractHisDienst.class, BestaansperiodeFormeelImplicietMaterieelAutaut.class);
    }

    @Test
    public void testAbstractHisDienstbundelImplementeertBestaansperiodeFormeelImplicietMaterieelAutaut() {
        assertHeeftInterface(AbstractHisDienstbundel.class, BestaansperiodeFormeelImplicietMaterieelAutaut.class);
    }

    @Test
    public void testAbstractHisLeveringsautorisatieImplementeertBestaansperiodeFormeelImplicietMaterieelAutaut() {
        assertHeeftInterface(AbstractHisLeveringsautorisatie.class, BestaansperiodeFormeelImplicietMaterieelAutaut.class);
    }
    @Test
    public void testAbstractHisToegangLeveringsautorisatieImplementeertBestaansperiodeFormeelImplicietMaterieelAutaut() {
        assertHeeftInterface(AbstractHisToegangLeveringsautorisatie.class, BestaansperiodeFormeelImplicietMaterieelAutaut.class);
    }
    @Test
    public void testAbstractHisPartijRolImplementeertBestaansperiodeFormeelImplicietMaterieelAutaut() {
        assertHeeftInterface(AbstractHisPartijRol.class, BestaansperiodeFormeelImplicietMaterieelAutaut.class);
    }

    private void assertHeeftInterface(Class<?> aClass, Class<?> aInterface) {
        boolean gevonden = false;
        for (Class<?> eenInterface : aClass.getInterfaces()) {
            if (eenInterface.equals(aInterface)) {
                gevonden = true;
            }
        }
        assertTrue(gevonden);
    }

    private void assertVeldBevatNietAnnotatie(Class klasseOnderTest, String veldnaam, Class annotationType) {
        if (getVeldAnnotatie(klasseOnderTest, veldnaam, annotationType) != null) {
            fail(String.format("veld '%s' in %s is per abuis geannoteerd met %s",
                veldnaam,
                klasseOnderTest.getSimpleName(),
                annotationType.getSimpleName()));
        }
    }

    private Annotation assertVeldBevatAnnotatie(Class klasseOnderTest, String veldnaam, Class annotationType) {
        final Annotation annotatie = getVeldAnnotatie(klasseOnderTest, veldnaam, annotationType);
        return assertBevatAnnotatie("veld", klasseOnderTest, veldnaam, annotationType, annotatie);
    }

    private Annotation assertMethodBevatAnnotatie(Class klasseOnderTest, String methodenaam, Class annotationType) {
        final Annotation annotatie = getMethodeAnnotatie(klasseOnderTest, methodenaam, annotationType);
        return assertBevatAnnotatie("methode", klasseOnderTest, methodenaam, annotationType, annotatie);
    }

    private Annotation assertMethodBevatNietAnnotatie(Class klasseOnderTest, String methodenaam, Class annotationType) {
        final Annotation annotatie = getMethodeAnnotatie(klasseOnderTest, methodenaam, annotationType);
        return assertBevatNietAnnotatie("methode", klasseOnderTest, methodenaam, annotationType, annotatie);
    }

    private Annotation assertBevatAnnotatie(String type, Class klasseOnderTest, String naam, Class annotationType, Annotation annotatie) {
        if (annotatie == null) {
            fail(String.format("%s '%s' in %s mist annotatie %s",
                type,
                naam,
                klasseOnderTest.getSimpleName(),
                annotationType.getSimpleName()));
        }
        return annotatie;
    }

    private Annotation assertBevatNietAnnotatie(String type, Class klasseOnderTest, String naam, Class annotationType, Annotation annotatie) {
        if (annotatie != null) {
            fail(String.format("%s '%s' in %s mist annotatie %s",
                    type,
                    naam,
                    klasseOnderTest.getSimpleName(),
                    annotationType.getSimpleName()));
        }
        return annotatie;
    }

    private Annotation getVeldAnnotatie(final Class klasseOnderTest, final String veldnaam, final Class annotationType) {
        final Field veld = ReflectionUtils.findField(klasseOnderTest, veldnaam);
        return veld.getAnnotation(annotationType);
    }

    private Annotation getMethodeAnnotatie(final Class klasseOnderTest, final String methodenaam, final Class annotationType) {
        Method methode;
        try {
            // ReflectionUtils.findMethod geeft soms de methode van de interface, vandaar hier getMethod.
            methode = klasseOnderTest.getMethod(methodenaam);
        } catch (NoSuchMethodException e) {
            methode = null;
        }

        return methode.getAnnotation(annotationType);
    }

    @Test
    public void testAbstractHisRelatieModelDefensiveVoorLegeStandaardGroepFamilieRechtelijkeBetrekking() {
        final ActieModel actieModel = new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                                                     DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        // De tweede parameter mag null zijn, zonder handmatige wijziging of generatieaanpassing (toekomst) geeft dit een nullpointer.
        new HisRelatieModel(new FamilierechtelijkeBetrekkingHisVolledigImpl(), null, actieModel);
    }
}
