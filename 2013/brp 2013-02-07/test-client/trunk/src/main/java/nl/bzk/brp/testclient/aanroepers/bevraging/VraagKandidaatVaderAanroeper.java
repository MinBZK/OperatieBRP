/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.BevragingKandidaatVader;
import nl.bprbzk.brp._0001.Burgerservicenummer;
import nl.bprbzk.brp._0001.Datum;
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
import nl.bzk.brp.testclient.soapcommands.bevraging.KandidaatVaderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class VraagKandidaatVaderAanroeper.
 */
public class VraagKandidaatVaderAanroeper extends BevragingAanroeper {

    private static final Logger         LOG            = LoggerFactory.getLogger(VraagKandidaatVaderAanroeper.class);

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterList()

    public static final String      PARAMETER_BSN     = "BSN";
    private static final String     BSN               = "100000034";
    private static final String     ORGANISATIE       = "364";
    private static final String     APPLICATIE        = "Testclient";
    private static final String     REFERENTIENUMMER  = "12345";

    /**
     * Instantieert een nieuwe vraag kandidaat vader aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public VraagKandidaatVaderAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap)
            throws Exception
    {
        super(eigenschappen, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        BevragingKandidaatVader vraag = creeerBevragingKandidaatVader();
        KandidaatVaderCommand command = new KandidaatVaderCommand(vraag);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.VRAAG_KANDIDAAT_VADER, getStatus(), duur);
    }

    /**
     * Creeer bevraging kandidaat vader.
     *
     * @return de bevraging kandidaat vader
     */
    private BevragingKandidaatVader creeerBevragingKandidaatVader() {
        ObjectFactory objectFactory = new ObjectFactory();
        BevragingKandidaatVader bevragingKandidaatVader = new BevragingKandidaatVader();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList = bevragingKandidaatVader.getStuurgegevens();
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
        GroepBerichtZoekcriteria groepBerichtZoekcriteria = new GroepBerichtZoekcriteria();

        Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
        burgerservicenummer.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer =
            objectFactory.createGroepBerichtZoekcriteriaBurgerservicenummer(burgerservicenummer);
        groepBerichtZoekcriteria.setBurgerservicenummer(jaxbBurgerservicenummer);
        JAXBElement<GroepBerichtZoekcriteria> jaxbGroepBerichtZoekcriteria =
            objectFactory.createGroepBerichtVraagBasisZoekcriteria(groepBerichtZoekcriteria);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumNu = sdf.format(new Date());

        Datum geboortedatumKind = objectFactory.createDatum();
        geboortedatumKind.setValue(BigInteger.valueOf(Long.valueOf(datumNu)));
        JAXBElement<Datum> jaxbGeboorteDatumKind =
            objectFactory.createGroepBerichtZoekcriteriaGeboortedatumKind(geboortedatumKind);
        groepBerichtZoekcriteria.setGeboortedatumKind(jaxbGeboorteDatumKind);
        groepBerichtVraagBasis.setZoekcriteria(jaxbGroepBerichtZoekcriteria);

        bevragingKandidaatVader.setVraag(groepBerichtVraagBasis);

        return bevragingKandidaatVader;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterList()
     */
    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[] { PARAMETER_BSN });
    }
}
