/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.BevragingDetailsPersoon;
import nl.bprbzk.brp._0001.Burgerservicenummer;
import nl.bprbzk.brp._0001.GroepBerichtOpties;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepBerichtVraagBasis;
import nl.bprbzk.brp._0001.GroepBerichtZoekcriteria;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bevraging.VraagDetailsPersoonCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class VraagDetailsPersoonAanroeper.
 */
public class VraagDetailsPersoonAanroeper extends BevragingAanroeper {

    /** De Constante LOG. */
    private static final Logger LOG                 = LoggerFactory.getLogger(VraagDetailsPersoonAanroeper.class);

    // NB: Let op dat je bij het toeveogen van parameters, deze ook toevoegt in getParameterList()

    /** De Constante PARAMETER_BSN. */
    public static final String     PARAMETER_BSN                        = "BSN";

    /** De Constante parameterList. */
    private static final List<String> parameterList = Arrays.asList(new String[]{PARAMETER_BSN});

    /** De Constante BSN. */
    private static final String BSN              = "100000009";

    /** De Constante ORGANISATIE. */
    private static final String ORGANISATIE      = "364";

    /** De Constante APPLICATIE. */
    private static final String APPLICATIE       = "Testclient";

    /** De Constante REFERENTIENUMMER. */
    private static final String REFERENTIENUMMER = "12345";

    /**
     * Instantieert een nieuwe vraag details persoon aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public VraagDetailsPersoonAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap) throws Exception {
        super(eigenschappen, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        BevragingDetailsPersoon vraag = creeerBevragingDetailsPersoon();
        VraagDetailsPersoonCommand command = new VraagDetailsPersoonCommand(vraag);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.VRAAG_DETAILS, getStatus(), duur);
    }

    /**
     * Creeer bevraging details persoon.
     *
     * @return de bevraging details persoon
     */
    private BevragingDetailsPersoon creeerBevragingDetailsPersoon() {
        ObjectFactory objectFactory = new ObjectFactory();
        BevragingDetailsPersoon bevragingDetailsPersoon = new BevragingDetailsPersoon();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList = bevragingDetailsPersoon.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = new GroepBerichtStuurgegevens();

        Organisatienaam organisatienaam = new Organisatienaam();
        organisatienaam.setValue(ORGANISATIE);
        groepBerichtStuurgegevens.setOrganisatie(organisatienaam);

        Applicatienaam applicatienaam = new Applicatienaam();
        applicatienaam.setValue(APPLICATIE);
        groepBerichtStuurgegevens.setApplicatie(applicatienaam);

        Sleutelwaardetekst sleutelwaardetekst = new Sleutelwaardetekst();
        sleutelwaardetekst.setValue(REFERENTIENUMMER);
        groepBerichtStuurgegevens.setReferentienummer(sleutelwaardetekst);

        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Vraag-element
        GroepBerichtVraagBasis groepBerichtVraagBasis = new GroepBerichtVraagBasis();

        // Vullen van opties
        GroepBerichtOpties groepBerichtOpties = new GroepBerichtOpties();
        JAXBElement<GroepBerichtOpties> jaxbGroepBerichtOpties =
            objectFactory.createGroepBerichtVraagBasisOpties(groepBerichtOpties);
        groepBerichtVraagBasis.setOpties(jaxbGroepBerichtOpties);

        // Vullen van zoekcriteria
        Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
        burgerservicenummer.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer =
            objectFactory.createGroepBerichtZoekcriteriaBurgerservicenummer(burgerservicenummer);

        GroepBerichtZoekcriteria groepBerichtZoekcriteria = new GroepBerichtZoekcriteria();
        groepBerichtZoekcriteria.setBurgerservicenummer(jaxbBurgerservicenummer);

        JAXBElement<GroepBerichtZoekcriteria> jaxbGroepBerichtZoekcriteria =
            objectFactory.createGroepBerichtVraagBasisZoekcriteria(groepBerichtZoekcriteria);

        groepBerichtVraagBasis.setZoekcriteria(jaxbGroepBerichtZoekcriteria);
        bevragingDetailsPersoon.setVraag(groepBerichtVraagBasis);

        return bevragingDetailsPersoon;
    }

    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[]{PARAMETER_BSN});
    }
}
