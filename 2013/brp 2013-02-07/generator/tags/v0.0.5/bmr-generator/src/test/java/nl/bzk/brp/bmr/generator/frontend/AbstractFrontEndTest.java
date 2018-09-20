/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import java.util.Arrays;

import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.BmrFactory;
import nl.bzk.brp.ecore.bmr.Bron;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;
import nl.bzk.brp.ecore.bmr.SoortInhoud;
import nl.bzk.brp.ecore.bmr.Versie;

import org.junit.Before;


public abstract class AbstractFrontEndTest {

    private Applicatie applicatie;

    @Before
    public void setup() {
        // Formulier 1
        Formulier formulier1 = BmrFactory.eINSTANCE.createFormulier();

        Bron frame1Bron = BmrFactory.eINSTANCE.createBron();
        frame1Bron.setObjectType(maakObjectType(SoortInhoud.D, "Partij"));

        Frame frame1 = BmrFactory.eINSTANCE.createFrame();
        frame1.getVelden().addAll(
                Arrays.asList(maakVeld("Partij", null, frame1), maakVeld("Soort", SoortInhoud.X, frame1),
                        maakVeld("Certificaat1", SoortInhoud.S, frame1),
                        maakVeld("Certificaat2", SoortInhoud.S, frame1),
                        maakVeld("Certificaat1", SoortInhoud.S, frame1)));
        frame1.setBron(frame1Bron);
        frame1.setNaam("frame1");
        frame1.setFormulier(formulier1);

        Bron frame2Bron = BmrFactory.eINSTANCE.createBron();
        frame2Bron.setObjectType(maakObjectType(SoortInhoud.D, "Bericht"));

        Frame frame2 = BmrFactory.eINSTANCE.createFrame();
        frame2.getVelden().addAll(
                Arrays.asList(maakVeld("Partij", null, frame2), maakVeld("Soort", SoortInhoud.X, frame2),
                        maakVeld("Certificaat1", SoortInhoud.S, frame2),
                        maakVeld("Certificaat2", SoortInhoud.S, frame2),
                        maakVeld("Certificaat1", SoortInhoud.S, frame2)));
        frame2.setBron(frame2Bron);
        frame2.setNaam("frame2");
        frame2.setFormulier(formulier1);

        formulier1.getFrames().addAll(Arrays.asList(frame1, frame2));
        formulier1.setNaam("formulier1");

        // Formulier 2
        Formulier formulier2 = BmrFactory.eINSTANCE.createFormulier();
        Bron frame3Bron = BmrFactory.eINSTANCE.createBron();
        frame3Bron.setObjectType(maakObjectType(SoortInhoud.D, "Partij"));

        Frame frame3 = BmrFactory.eINSTANCE.createFrame();
        frame3.getVelden().addAll(
                Arrays.asList(maakVeld("Partij", null, frame3), maakVeld("Soort", SoortInhoud.X, frame3),
                        maakVeld("Certificaat1", SoortInhoud.S, frame3),
                        maakVeld("Certificaat2", SoortInhoud.S, frame3),
                        maakVeld("Certificaat1", SoortInhoud.S, frame3)));
        frame3.setBron(frame3Bron);
        frame3.setNaam("frame3");
        frame3.setFormulier(formulier1);

        Bron frame4Bron = BmrFactory.eINSTANCE.createBron();
        frame4Bron.setObjectType(maakObjectType(SoortInhoud.D, "Bericht"));

        Frame frame4 = BmrFactory.eINSTANCE.createFrame();
        frame4.getVelden().addAll(
                Arrays.asList(maakVeld("Partij", null, frame4), maakVeld("Soort", SoortInhoud.X, frame4),
                        maakVeld("Certificaat1", SoortInhoud.S, frame4),
                        maakVeld("Certificaat2", SoortInhoud.S, frame4),
                        maakVeld("Certificaat1", SoortInhoud.S, frame4)));
        frame4.setBron(frame4Bron);
        frame4.setNaam("frame4");
        frame4.setFormulier(formulier1);

        formulier2.getFrames().addAll(Arrays.asList(frame3, frame4));
        formulier2.setNaam("formulier2");

        // Applicatie
        applicatie = BmrFactory.eINSTANCE.createApplicatie();
        applicatie.getFormulieren().addAll(Arrays.asList(formulier1, formulier2));
    }

    private FrameVeld maakVeld(final String identifierCode, final SoortInhoud soortInhoud, final Frame frame) {
        Attribuut attribuut = BmrFactory.eINSTANCE.createAttribuut();
        attribuut.setIdentifierCode("Attribuut-" + identifierCode);

        ObjectType objectType = maakObjectType(soortInhoud, identifierCode);
        attribuut.setObjectType(objectType);

        if (soortInhoud == SoortInhoud.X) {
            attribuut.setType(maakEnumeratieType(identifierCode));
        } else if (soortInhoud == SoortInhoud.S) {
            attribuut.setType(maakObjectType(soortInhoud, "type-" + identifierCode));
        } else {
            attribuut.setType(maakAttribuutType(identifierCode));
        }

        FrameVeld veld = BmrFactory.eINSTANCE.createFrameVeld();
        veld.setAttribuut(attribuut);
        veld.setFrame(frame);

        return veld;
    }

    private ObjectType maakObjectType(final SoortInhoud soortInhoud, final String identifierCode) {
        Schema schema = BmrFactory.eINSTANCE.createSchema();
        schema.setNaam("schemaNaam");

        Versie versie = BmrFactory.eINSTANCE.createVersie();
        versie.setSchema(schema);

        ObjectType objectType = BmrFactory.eINSTANCE.createObjectType();

        objectType.setSoortInhoud(soortInhoud);
        objectType.setIdentifierCode("ObjectType-" + identifierCode);
        objectType.setVersie(versie);

        Attribuut otAttribuut1 = BmrFactory.eINSTANCE.createAttribuut();
        otAttribuut1.setIdentifierCode(identifierCode + "otAttribuut1");
        Attribuut otAttribuut2 = BmrFactory.eINSTANCE.createAttribuut();
        otAttribuut2.setIdentifierCode(identifierCode + "otAttribuut2");
        Attribuut otAttribuut3 = BmrFactory.eINSTANCE.createAttribuut();
        otAttribuut3.setIdentifierCode(identifierCode + "otAttribuut3");

        objectType.getAttributen().addAll(Arrays.asList(otAttribuut1, otAttribuut2, otAttribuut3));

        return objectType;
    }

    private AttribuutType maakAttribuutType(final String identifierCode) {
        AttribuutType attribuutType = BmrFactory.eINSTANCE.createAttribuutType();
        attribuutType.setIdentifierCode("attribuutType-" + identifierCode);

        return attribuutType;
    }

    private ObjectType maakEnumeratieType(final String identifierCode) {
        Schema schema = BmrFactory.eINSTANCE.createSchema();
        schema.setNaam("schemaNaam");

        Versie versie = BmrFactory.eINSTANCE.createVersie();
        versie.setSchema(schema);

        ObjectType objectType = BmrFactory.eINSTANCE.createObjectType();
        objectType.setSoortInhoud(SoortInhoud.X);
        objectType.setIdentifierCode("enum-" + identifierCode);
        objectType.setVersie(versie);

        Attribuut otAttribuut1 = BmrFactory.eINSTANCE.createAttribuut();
        otAttribuut1.setIdentifierCode("ID");
        Attribuut otAttribuut2 = BmrFactory.eINSTANCE.createAttribuut();
        otAttribuut2.setIdentifierCode("naam");

        objectType.getAttributen().addAll(Arrays.asList(otAttribuut1, otAttribuut2));

        return objectType;
    }

    public Applicatie getApplicatie() {
        return applicatie;
    }

    public Formulier getFormulier() {
        return applicatie.getFormulieren().get(0);
    }

    public Frame getFrame(final int frameNummer) {
        return getFormulier().getFrames().get(frameNummer);
    }

    public FrameVeld getVeld(final int veldnummer) {
        return getFrame(0).getVelden().get(veldnummer);
    }
}
