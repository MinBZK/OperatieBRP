/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.BETROKKENHEID;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__OUDER_OUDERLIJK_GEZAG;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__OUDER_OUDERLIJK_GEZAG__INDICATIE_OUDER_HEEFT_GEZAG;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_ADRES;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_ADRES__HUISLETTER;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_ADRES__HUISNUMMER;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_GEBOORTE;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_GEBOORTE__GEMEENTE_GEBOORTE;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_GEBOORTE__LAND_GEBIED_GEBOORTE;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_INSCHRIJVING;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_INSCHRIJVING__VERSIENUMMER;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_NAAMGEBRUIK;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_NAAMGEBRUIK__NAAMGEBRUIK;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.HIS__PERSOON_NAAMGEBRUIK__PREDICAAT_NAAMGEBRUIK;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.PERSOON;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.PERSOON_ADRES;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.PERSOON_VOORNAAM;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern.RELATIE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de methodes in de (@link AbstractBerichtEntiteit} klasse.
 */
public class GeneriekeAbstractBerichtEntiteitTest {

    @Test
    public void gettersEnSettersTest() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwBerichtEntiteit(1, "obj.id");
        Assert.assertNull(berichtEntiteit.getObjecttype());
        Assert.assertNull(berichtEntiteit.getReferentieID());
        Assert.assertNull(berichtEntiteit.getObjectSleutel());

        berichtEntiteit.setObjecttype("Test");
        Assert.assertEquals("Test", berichtEntiteit.getObjecttype());
        berichtEntiteit.setReferentieID("Ref.id");
        Assert.assertEquals("Ref.id", berichtEntiteit.getReferentieID());
        final String objectSleutel1 = "123";
        berichtEntiteit.setObjectSleutel(objectSleutel1);
        Assert.assertEquals(objectSleutel1, berichtEntiteit.getObjectSleutel());
        final String objectSleutel2 = "122";
        berichtEntiteit.setObjectSleutel(objectSleutel2);
        Assert.assertEquals(objectSleutel2, berichtEntiteit.getObjectSleutel());
    }

    @Test
    public void testbevatElementMetMetaId() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwStandaardEntiteit();

        // Test eigen id
        Assert.assertTrue(berichtEntiteit.getMetaId().equals(PERSOON.getId()));
        Assert.assertFalse(berichtEntiteit.getMetaId().equals(RELATIE.getId()));

        // Test groep id
        Assert.assertTrue(berichtEntiteit.bevatElementMetMetaId(HIS__PERSOON_GEBOORTE.getId()));
        Assert.assertFalse(berichtEntiteit.bevatElementMetMetaId(HIS__OUDER_OUDERLIJK_GEZAG.getId()));

        // Test attribuut id
        Assert.assertTrue(berichtEntiteit.bevatElementMetMetaId(HIS__PERSOON_GEBOORTE__LAND_GEBIED_GEBOORTE.getId()));
        Assert.assertFalse(berichtEntiteit
                .bevatElementMetMetaId(HIS__OUDER_OUDERLIJK_GEZAG__INDICATIE_OUDER_HEEFT_GEZAG.getId()));

        // Test sub entiteit id
        Assert.assertTrue(berichtEntiteit.bevatElementMetMetaId(PERSOON_ADRES.getId()));
        Assert.assertFalse(berichtEntiteit.bevatElementMetMetaId(BETROKKENHEID.getId()));

        // Test sub entiteit groep id
        Assert.assertTrue(berichtEntiteit.bevatElementMetMetaId(HIS__PERSOON_ADRES.getId()));

        // Test sub entiteit attribuut id
        Assert.assertTrue(berichtEntiteit.bevatElementMetMetaId(HIS__PERSOON_ADRES__HUISNUMMER.getId()));
    }

    @Test
    public void testCommunicatieIdGetterVoorObject() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwStandaardEntiteit();

        Assert.assertEquals("obj.id", berichtEntiteit.getCommunicatieIdVoorElement(PERSOON.getId()));
        Assert.assertEquals("subobj1.id", berichtEntiteit.getCommunicatieIdVoorElement(PERSOON_ADRES.getId()));
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(PERSOON_VOORNAAM.getId()));
    }

    @Test
    public void testCommunicatieIdGetterVoorGroep() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwStandaardEntiteit();

        Assert.assertEquals("grp1.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_GEBOORTE.getId()));
        Assert.assertEquals("grp2.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_NAAMGEBRUIK.getId()));
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_INSCHRIJVING.getId()));
    }

    @Test
    public void testCommunicatieIdGetterVoorAttribuut() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwStandaardEntiteit();

        Assert.assertEquals("grp1.id",
                berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_GEBOORTE__GEMEENTE_GEBOORTE.getId()));
        Assert.assertEquals("grp2.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_NAAMGEBRUIK.getId()));
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_INSCHRIJVING__VERSIENUMMER.getId()));
    }

    @Test
    public void testCommunicatieIdGetterVoorAttribuutInSubEntiteit() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwStandaardEntiteit();

        Assert.assertEquals("subobj1.grp.id",
                berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_ADRES__HUISNUMMER.getId()));
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_ADRES__HUISLETTER.getId()));
    }

    @Test
    public void testCommunicatieIdBijNietIngevuldeCommunicatieId() {
        AbstractBerichtEntiteit berichtEntiteit;

        // Test op hoofd entiteit
        berichtEntiteit = bouwStandaardEntiteit();
        Assert.assertEquals("obj.id", berichtEntiteit.getCommunicatieIdVoorElement(PERSOON.getId()));
        berichtEntiteit.setCommunicatieID(null);
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(PERSOON.getId()));

        // Test op sub entiteit
        berichtEntiteit = bouwStandaardEntiteit();
        Assert.assertEquals("subobj1.id", berichtEntiteit.getCommunicatieIdVoorElement(PERSOON_ADRES.getId()));
        berichtEntiteit.getBerichtEntiteiten().get(0).setCommunicatieID(null);
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(PERSOON_ADRES.getId()));

        // Test op groep; verwacht dan communicatie id van bovenliggende entiteit
        berichtEntiteit = bouwStandaardEntiteit();
        Assert.assertEquals("grp1.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_GEBOORTE.getId()));
        berichtEntiteit.getBerichtEntiteitGroepen().get(0).setCommunicatieID(null);
        berichtEntiteit.getBerichtEntiteitGroepen().get(1).setCommunicatieID(null);
        Assert.assertEquals("obj.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_GEBOORTE.getId()));

        // Test op groep in subentiteit; verwacht dan communicatie id van de subentiteit
        berichtEntiteit = bouwStandaardEntiteit();
        Assert.assertEquals("subobj1.grp.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_ADRES.getId()));
        berichtEntiteit.getBerichtEntiteiten().get(0).getBerichtEntiteitGroepen().get(0).setCommunicatieID(null);
        Assert.assertEquals("subobj1.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_ADRES.getId()));

        // Test op attribuut; verwacht dan communicatie id van bovenliggende entiteit
        berichtEntiteit = bouwStandaardEntiteit();
        Assert.assertEquals("grp1.id",
                berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_GEBOORTE__GEMEENTE_GEBOORTE.getId()));
        berichtEntiteit.getBerichtEntiteitGroepen().get(0).setCommunicatieID(null);
        berichtEntiteit.getBerichtEntiteitGroepen().get(1).setCommunicatieID(null);
        Assert.assertEquals("obj.id",
                berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_GEBOORTE__GEMEENTE_GEBOORTE.getId()));

        // Test op groep in subentiteit; verwacht dan communicatie id van de subentiteit
        berichtEntiteit = bouwStandaardEntiteit();
        Assert.assertEquals("subobj1.grp.id",
                berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_ADRES__HUISNUMMER.getId()));
        berichtEntiteit.getBerichtEntiteiten().get(0).getBerichtEntiteitGroepen().get(0).setCommunicatieID(null);
        Assert.assertEquals("subobj1.id", berichtEntiteit.getCommunicatieIdVoorElement(HIS__PERSOON_ADRES__HUISNUMMER.getId()));
    }

    @Test
    public void testCommunicatieIdGetterVoorLeegObjectId() {
        final AbstractBerichtEntiteit berichtEntiteit = bouwBerichtEntiteit(PERSOON.getId(), "obj.id");
        Assert.assertNull(berichtEntiteit.getCommunicatieIdVoorElement(null));
    }

    /**
     * Retourneert een nieuwe {@link AbstractBerichtEntiteit} instantie met de volgende kenmerken:<br/>
     * - Meta ID: 3010 (DatabaseObject.PERSOON) <br/>
     * - Communicatie ID: obj.id <br/>
     * - SubEntiteit: Adres <br/>
     * - Meta ID: 3237 (DatabaseObject.PERSOON_ADRES) <br/>
     * - Communicatie ID: subobj1.id <br/>
     * - Groep Meta ID: 6063 <br/>
     * - Groep Communicati ID: subobj1.grp.id <br/>
     * - Groep Attributen: 9781 <br/>
     * - Groep1: Geboorte <br/>
     * - Meta ID: 3514 (DatabaseObject.HIS__PERSOON_GEBOORTE) <br/>
     * - Communicatie ID: grp1.id <br/>
     * - Attributen: 9726, 9731 <br/>
     * - Groep2: Aanschrijving <br/>
     * - Meta ID: 3487 (DatabaseObject.HIS__PERSOON_AANSCHRIJVING) <br/>
     * - Communicatie ID: grp2.id <br/>
     * - Attributen: 9749, 9752 <br/>
     *
     * @return een standaard {@link AbstractBerichtEntiteit} instantie om mee te testen.
     */
    private AbstractBerichtEntiteit bouwStandaardEntiteit() {
        return bouwBerichtEntiteit(PERSOON.getId(), "obj.id", Arrays.asList((BerichtEntiteit) bouwBerichtEntiteit(
                PERSOON_ADRES.getId(),
                "subobj1.id",
                Collections.<BerichtEntiteit>emptyList(),
                Arrays.asList(bouwBerichtEntiteitGroep(HIS__PERSOON_ADRES.getId(), "subobj1.grp.id",
                        Arrays.asList(HIS__PERSOON_ADRES__HUISNUMMER.getId()))))), Arrays.asList(
                bouwBerichtEntiteitGroep(HIS__PERSOON_GEBOORTE.getId(), "grp1.id", Arrays.asList(
                        HIS__PERSOON_GEBOORTE__LAND_GEBIED_GEBOORTE.getId(),
                        HIS__PERSOON_GEBOORTE__GEMEENTE_GEBOORTE.getId())),
                bouwBerichtEntiteitGroep(HIS__PERSOON_NAAMGEBRUIK.getId(), "grp2.id", Arrays.asList(
                        HIS__PERSOON_NAAMGEBRUIK__NAAMGEBRUIK.getId(),
                        HIS__PERSOON_NAAMGEBRUIK__PREDICAAT_NAAMGEBRUIK.getId()))));
    }

    /**
     * Retourneert een nieuwe {@link AbstractBerichtEntiteit} instantie met opgegeven Meta Id, maar zonder
     * onderliggende sub entiteiten en/of groepen.
     *
     * @param metaId de Meta ID van de te retourneren bericht entiteit.
     * @param commId de communicatie id van de entiteit.
     * @return een nieuwe {@link AbstractBerichtEntiteit}.
     */
    private AbstractBerichtEntiteit bouwBerichtEntiteit(final Integer metaId, final String commId) {
        return bouwBerichtEntiteit(metaId, commId, Collections.<BerichtEntiteit>emptyList(),
                Collections.<BerichtEntiteitGroep>emptyList());
    }

    /**
     * Retourneert een nieuwe {@link AbstractBerichtEntiteit} instantie met opgegeven Meta Id, onderliggende
     * (sub)entiteiten en groepen.
     *
     * @param metaId        de Meta ID van de te retourneren bericht entiteit.
     * @param commId        de communicatie id van de entiteit.
     * @param subEntiteiten de onderliggende (sub)entiteiten.
     * @param groepen       de onderliggende groepen.
     * @return een nieuwe {@link AbstractBerichtEntiteit}.
     */
    private AbstractBerichtEntiteit bouwBerichtEntiteit(final Integer metaId, final String commId,
            final List<BerichtEntiteit> subEntiteiten, final List<BerichtEntiteitGroep> groepen)
    {
        final AbstractBerichtEntiteit entiteit = new AbstractBerichtEntiteit() {

            @Override
            public List<BerichtEntiteit> getBerichtEntiteiten() {
                return subEntiteiten;
            }

            @Override
            public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
                return groepen;
            }

            @Override
            public Integer getMetaId() {
                return metaId;
            }
        };
        entiteit.setCommunicatieID(commId);
        return entiteit;
    }

    /**
     * Retourneert een nieuwe {@link AbstractBerichtEntiteitGroep} instantie met opgegeven Meta Id. Tevens zal deze
     * instantie de opgegeven attribuut Ids zien als 'onderliggende/bevattende' attributen in de
     * {@link AbstractBerichtEntiteitGroep#bevatElementMetMetaId(Integer)} methode.
     *
     * @param metaId       de Meta ID van de te retourneren groep.
     * @param commId       de communicatie id van de groep.
     * @param attribuutIds de Meta IDs van de attributen in de groep.
     * @return een nieuwe {@link AbstractBerichtEntiteitGroep} instantie.
     */
    private BerichtEntiteitGroep bouwBerichtEntiteitGroep(final Integer metaId, final String commId,
            final List<Integer> attribuutIds)
    {
        final AbstractBerichtEntiteitGroep groep = new AbstractBerichtEntiteitGroep() {

            @Override
            public List<Attribuut> getAttributen() {
                return new ArrayList<>();
            }

            @Override
            public Integer getMetaId() {
                return metaId;
            }

            @Override
            public boolean bevatElementMetMetaId(final Integer id) {
                return attribuutIds.contains(id);
            }
        };
        groep.setCommunicatieID(commId);
        return groep;
    }

}
