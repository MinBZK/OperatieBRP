/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.BevragingPersonenOpAdresInclusiefBetrokkenheden;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepBerichtVraagBasis;
import nl.bprbzk.brp._0001.GroepBerichtZoekcriteria;
import nl.bprbzk.brp._0001.Huisletter;
import nl.bprbzk.brp._0001.Huisnummer;
import nl.bprbzk.brp._0001.Huisnummertoevoeging;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.Postcode;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.
 */
public class VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper extends BevragingAanroeper {

    private static final Logger LOG                 = LoggerFactory.getLogger(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.class);

    // NB: Let op dat je bij het toeveogen van parameters, deze ook toevoegt in getParameterList()

    /** De Constante PARAMETER_HUISNUMMER. */
    public static final String  PARAMETER_HUISNUMMER           = "HUISNUMMER";

    public static final String  PARAMETER_HUISLETTER           = "HUISLETTER";

    /** De Constante PARAMETER_HUISNUMMERTOEVOEGING. */
    public static final String  PARAMETER_HUISNUMMERTOEVOEGING = "HUISNUMMERTOEVOEGING";

    /** De Constante PARAMETER_POSTCODE. */
    public static final String  PARAMETER_POSTCODE             = "POSTCODE";

    /** De Constante ORGANISATIE. */
    private static final String ORGANISATIE                    = "364";

    /** De Constante APPLICATIE. */
    private static final String APPLICATIE                     = "Testclient";

    /** De Constante REFERENTIENUMMER. */
    private static final String REFERENTIENUMMER               = "12345";

    /** De Constante HUISNUMMER. */
    private static final String HUISNUMMER                     = "5";

    private static final String HUISLETTER                     = "";

    /** De Constante HUISNUMMERTOEVOEGING. */
    private static final String HUISNUMMERTOEVOEGING           = "AA";

    /** De Constante POSTCODE. */
    private static final String POSTCODE                       = "3572AA";

    /**
     * Instantieert een nieuwe vraag personen op adres inclusief betrokkenheden aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper(final Eigenschappen eigenschappen,
            final Map<String, String> parameterMap) throws Exception
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
        BevragingPersonenOpAdresInclusiefBetrokkenheden vraag = creeerBevragingPersonenOpAdresInclusiefBetrokkenheden();
        VraagPersonenOpAdresInclusiefBetrokkenhedenCommand command =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenCommand(vraag);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.VRAAG_PERSONEN_OP_ADRES, getStatus(), duur);
    }

    /**
     * Creeer bevraging personen op adres inclusief betrokkenheden.
     *
     * @return de bevraging personen op adres inclusief betrokkenheden
     */
    private BevragingPersonenOpAdresInclusiefBetrokkenheden creeerBevragingPersonenOpAdresInclusiefBetrokkenheden() {
        ObjectFactory objectFactory = new ObjectFactory();
        BevragingPersonenOpAdresInclusiefBetrokkenheden bevraging =
            new BevragingPersonenOpAdresInclusiefBetrokkenheden();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList = bevraging.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = new GroepBerichtStuurgegevens();

        Organisatienaam organisatienaam = new Organisatienaam();
        organisatienaam.setValue(ORGANISATIE);
        groepBerichtStuurgegevens.setOrganisatie(organisatienaam);

        Applicatienaam applicatienaam = new Applicatienaam();
        applicatienaam.setValue(APPLICATIE);
        groepBerichtStuurgegevens.setApplicatie(applicatienaam);

        Random random = new Random();
        int referentieNr = random.nextInt(1000);

        Sleutelwaardetekst sleutelwaardetekst = new Sleutelwaardetekst();
        sleutelwaardetekst.setValue("" + referentieNr);
        groepBerichtStuurgegevens.setReferentienummer(sleutelwaardetekst);

        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Vraag-element
        GroepBerichtVraagBasis groepBerichtVraagBasis = new GroepBerichtVraagBasis();

        // Zoekcriteria
        GroepBerichtZoekcriteria groepBerichtZoekcriteria = new GroepBerichtZoekcriteria();
        JAXBElement<GroepBerichtZoekcriteria> jaxbGroepBerichtZoekcriteria =
            objectFactory.createGroepBerichtVraagBasisZoekcriteria(groepBerichtZoekcriteria);
        groepBerichtVraagBasis.setZoekcriteria(jaxbGroepBerichtZoekcriteria);
        bevraging.setVraag(groepBerichtVraagBasis);

        // Vullen van zoekcriteria
        // Huisnummer
        Huisnummer huisnummer = new Huisnummer();
        huisnummer.setValue(getValueFromValueMap(PARAMETER_HUISNUMMER, HUISNUMMER));
        JAXBElement<Huisnummer> jaxbHuisnummer = objectFactory.createGroepBerichtZoekcriteriaHuisnummer(huisnummer);
        groepBerichtZoekcriteria.setHuisnummer(jaxbHuisnummer);

        // Huisletter
        if (getValueFromValueMap(PARAMETER_HUISLETTER, HUISLETTER) != "") {
            Huisletter huisletter = new Huisletter();
            huisletter.setValue(getValueFromValueMap(PARAMETER_HUISLETTER, HUISLETTER));
            JAXBElement<Huisletter> jaxbHuisletter =
                objectFactory.createGroepBerichtZoekcriteriaHuisletter(huisletter);
            groepBerichtZoekcriteria.setHuisletter(jaxbHuisletter);
            }

        // Huisnummertoevoeging
        if (getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, HUISNUMMERTOEVOEGING) != "") {
        Huisnummertoevoeging huisnummertoevoeging = new Huisnummertoevoeging();
        huisnummertoevoeging.setValue(getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, HUISNUMMERTOEVOEGING));
        JAXBElement<Huisnummertoevoeging> jaxbHuisnummertoevoeging =
            objectFactory.createGroepBerichtZoekcriteriaHuisnummertoevoeging(huisnummertoevoeging);
        groepBerichtZoekcriteria.setHuisnummertoevoeging(jaxbHuisnummertoevoeging);
        }

        // Postcode
        Postcode postcode = new Postcode();
        postcode.setValue(getValueFromValueMap(PARAMETER_POSTCODE, POSTCODE));
        JAXBElement<Postcode> jaxbPostcode = objectFactory.createGroepBerichtZoekcriteriaPostcode(postcode);
        groepBerichtZoekcriteria.setPostcode(jaxbPostcode);

        return bevraging;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterList()
     */
    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[] { PARAMETER_HUISNUMMER, PARAMETER_HUISLETTER, PARAMETER_HUISNUMMERTOEVOEGING, PARAMETER_POSTCODE });
    }
}
