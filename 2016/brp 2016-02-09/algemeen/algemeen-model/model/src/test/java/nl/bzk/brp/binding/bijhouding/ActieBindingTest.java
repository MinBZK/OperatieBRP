/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test die test of de actie gegevens correct worden gebind en direct op de verschillende objecttypes en groepen
 * in het bericht worden gezet.
 */
public class ActieBindingTest extends AbstractBindingInIntegratieTest<RegistreerGeboorteBericht> {

    @Test
    public void testActieOpRootObject() throws IOException, JiBXException {
        final BijhoudingsBericht bericht =
                unmarshalObject(leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MAX.xml"));

        // Test of bericht goed is ingelezen en er ook echt twee acties aanwezig zijn
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertEquals(7, bericht.getAdministratieveHandeling().getActies().size());

        // Test eerste actie (de inschrijving)
        checkDatumsGroepenOpInschrijvingActie(bericht.getAdministratieveHandeling().getActies().get(0), 20120101);

        // Test tweede actie (de nationaliteit)
        checkDatumsGroepenOpNationaliteitActie(bericht.getAdministratieveHandeling().getActies().get(3), 20120101);
    }

    /**
     * Checkt of de DAGs en DEGs zijn zoals verwacht voor de inschrijving actie.
     *
     * @param actieInschrijving de actie die gecontroleerd dient te worden.
     * @param dag               de verwachte dag voor de groepen in deze actie.
     */
    private void checkDatumsGroepenOpInschrijvingActie(final Actie actieInschrijving, final Integer dag) {
        Assert.assertEquals(dag, actieInschrijving.getDatumAanvangGeldigheid().getWaarde());

        final FamilierechtelijkeBetrekkingBericht relatie =
                (FamilierechtelijkeBetrekkingBericht) actieInschrijving.getRootObject();
        Assert.assertNotNull(relatie);

        // Conroleer het kind
        final PersoonBericht kind = relatie.getKindBetrokkenheid().getPersoon();
        Assert.assertNotNull(kind);

        checkGroepVoorDagEnDeg(kind.getSamengesteldeNaam(), dag);
        checkGroepVoorDagEnDeg(kind.getGeslachtsaanduiding(), dag);
        for (PersoonVoornaamBericht voornaam : kind.getVoornamen()) {
            checkGroepVoorDagEnDeg(voornaam.getStandaard(), dag);
        }
        for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent : kind.getGeslachtsnaamcomponenten()) {
            checkGroepVoorDagEnDeg(geslachtsnaamcomponent.getStandaard(), dag);
        }

        // Controleer een ouder
        final OuderBericht ouder = relatie.getOuderBetrokkenheden().iterator().next();
        Assert.assertNotNull(ouder);

        checkGroepVoorDagEnDeg(ouder.getOuderschap(), dag);
    }

    /**
     * Checkt of de DAGs en DEGs zijn zoals verwacht voor de registratie nationaliteit actie.
     *
     * @param actieNationaliteit de actie die gecontroleerd dient te worden.
     * @param dag                de verwachte DAG voor de groepen in deze actie.
     */
    private void checkDatumsGroepenOpNationaliteitActie(final Actie actieNationaliteit, final Integer dag) {
        Assert.assertEquals(dag, actieNationaliteit.getDatumAanvangGeldigheid().getWaarde());

        final PersoonBericht persoon = (PersoonBericht) actieNationaliteit.getRootObject();
        Assert.assertNotNull(persoon);

        checkGroepVoorDagEnDeg(persoon.getNationaliteiten().get(0).getStandaard(), dag);
    }

    /**
     * Controleert of de opgegeven materieel historische groep bestaat en een DAG heeft zoals opgegeven.
     *
     * @param groep de groep die gecontroleerd dient te worden.
     * @param dag   de verwachte DAG.
     */
    private void checkGroepVoorDagEnDeg(final MaterieleHistorie groep, final Integer dag) {
        Assert.assertNotNull(groep);
        Assert.assertEquals(dag, groep.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(groep.getDatumEindeGeldigheid());
    }

    @Override
    public Class<RegistreerGeboorteBericht> getBindingClass() {
        return RegistreerGeboorteBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdAfstammingBerichten();
    }

}
