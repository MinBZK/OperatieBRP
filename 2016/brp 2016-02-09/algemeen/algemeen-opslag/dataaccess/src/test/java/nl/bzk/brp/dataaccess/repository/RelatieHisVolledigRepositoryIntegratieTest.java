/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test die de functionaliteit van de {@link RelatieHisVolledigRepository} test.
 */
@Data(resources = {
        "classpath:/data/stamgegevensStatisch.xml",
        "classpath:/data/stamgegevensLandGebied.xml",
        "classpath:/data/stamgegevensNationaliteit.xml",
        "classpath:/data/testdata.xml",
        "classpath:/data/testdata-autaut.xml",
        // Leegt de conversie tabellen met stamgegevens (vanwege foreign keys)
        // Let op: het bestand heet 'pre-', maar staat aan het eind, vanwege de dbunit afhandelvolgorde.
        "classpath:/data/pre-conv-clean.xml",
        "classpath:/data/testdata-relaties.xml" })
public class RelatieHisVolledigRepositoryIntegratieTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Inject
    private RelatieHisVolledigRepository relatieHisVolledigRepository;

    @Test
    public void testLezenGenormaliseerdModelBestaandeRelatie() {
        final RelatieHisVolledig relatie = relatieHisVolledigRepository.leesGenormalizeerdModel(101);

        Assert.assertNotNull(relatie);
        Assert.assertEquals(Integer.valueOf(101), relatie.getID());
        Assert.assertEquals(SoortRelatie.HUWELIJK, relatie.getSoort().getWaarde());
        Assert.assertEquals(2, relatie.getBetrokkenheden().size());
    }

    @Test
    public void testLezenGenormaliseerdModelNietBestaandeRelatie() {
        final RelatieHisVolledig relatie = relatieHisVolledigRepository.leesGenormalizeerdModel(999);

        Assert.assertNull(relatie);
    }

    @Test
    public void testSchrijvenGenormaliseerdModelBestaandeRelatie() {
        final AdministratieveHandelingModel handelingModel =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
                                                  new PartijAttribuut(em.find(Partij.class, (short) 2)),
                                                  new OntleningstoelichtingAttribuut("De toeliching"),
                                                  new DatumTijdAttribuut(new Date()));
        em.persist(handelingModel);

        // Bouw Actie
        final ActieModel actieModel =
                new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES), handelingModel,
                               new PartijAttribuut(em.find(Partij.class, (short) 2)),
                               new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                               new DatumTijdAttribuut(new Date()), null);
        em.persist(actieModel);
        em.flush();

        // Bouw relatie
        final HuwelijkHisVolledigImplBuilder builder = new HuwelijkHisVolledigImplBuilder();

        // Bouw de relatie op (met een operationele gemeente en partij.
        final RelatieHisVolledig relatie = builder.nieuwStandaardRecord(actieModel)
                .datumAanvang(20120202)
                .landGebiedAanvang(em.find(LandGebied.class, 2))
                .gemeenteAanvang(em.find(Gemeente.class, (short) 7))
                .eindeRecord().build();

        // Schrijf de relatie weg.
        relatieHisVolledigRepository.schrijfGenormalizeerdModel(relatie);
        em.flush();

        // Haal opgeslagen huwelijk op en valideer inhoud
        final HuwelijkHisVolledigImpl huwelijkOpgeslagen = em.find(HuwelijkHisVolledigImpl.class, relatie.getID());
        final HuwelijkView huwelijkView = new HuwelijkView(huwelijkOpgeslagen);

        Assert.assertNotNull(huwelijkOpgeslagen);
        Assert.assertEquals(Integer.valueOf(20120202), huwelijkView.getStandaard().getDatumAanvang().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 4),
                            huwelijkView.getStandaard().getGemeenteAanvang().getWaarde().getCode().getWaarde());
    }

}
