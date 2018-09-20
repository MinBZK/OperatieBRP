/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Test;

/** Unit test klasse ten behoeve van de {@link ActieRepository} klasse. */
public class ActieRepositoryTest extends AbstractRepositoryTestCase {

    private static final int PARTIJ_ID = 4;

    @Inject
    private ActieRepository actieRepository;
    @Inject
    private AdministratieveHandelingRepository handelingRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    // NOTE: Opslaan van acties met bronnen gebeurt niet meer in de actie repository, daar is
    // de aparte verantwoording service voor gemaakt!

    @Test
    public void opslaanActieZonderBron() {
        final AdministratieveHandelingModel handeling = handelingRepository.haalAdministratieveHandeling(1000L);

        // Opbouw Actie Model zonder bronnen
        final ActieModel actieModel = new ActieModel(maakActie(PARTIJ_ID), handeling);

        // Opslag actie
        final ActieModel opgeslagenActie = actieRepository.opslaanNieuwActie(actieModel);

        // Controle op opslag van de bronnen
        final List<ActieBronModel> bronnen =
            em.createQuery("select brn FROM ActieBronModel brn WHERE brn.actie.id=:actieId", ActieBronModel.class)
                .setParameter("actieId", opgeslagenActie.getID()).getResultList();

        Assert.assertEquals(0, bronnen.size());
    }

    /**
     * Instantieert en retourneert een {@link nl.bzk.brp.model.bericht.kern.ActieBericht} met opgegeven bronnen.
     *
     * @param bronnen de bronnen die bij een actie behoren.
     * @return een nieuw {@link nl.bzk.brp.model.bericht.kern.ActieBericht}.
     */
    private ActieBericht maakActie(final int partijId, final ActieBronBericht... bronnen) {
        final ActieBericht actieBericht = new ActieRegistratieNationaliteitBericht();
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setPartij(new PartijAttribuut(em.find(Partij.class, (short) partijId)));

        final List<ActieBronBericht> listBronnen = new ArrayList<>();
        if (bronnen != null) {
            for (final ActieBronBericht b : bronnen) {
                if (b != null) {
                    listBronnen.add(b);
                }
            }
        }
        actieBericht.setBronnen(listBronnen);
        return actieBericht;
    }

}
