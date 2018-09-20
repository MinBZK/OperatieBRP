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

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.brp0200.BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek;
import nl.bzk.brp.brp0200.GroepBerichtZoekcriteriaPersoonBijhoudingBevragingPersonenOpAdresMetBetrokkenheden;
import nl.bzk.brp.brp0200.Huisletter;
import nl.bzk.brp.brp0200.Huisnummer;
import nl.bzk.brp.brp0200.Huisnummertoevoeging;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.Postcode;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bevraging.PersonenOpAdresCommand;


/** De Class VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper. */
public class VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper extends AbstractBevragingAanroeper {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()

    /**
     * Parameter voor huisnummer.
     */
    public static final  String PARAMETER_HUISNUMMER           = "HUISNUMMER";
    /**
     * Parameter voor huisletter.
     */
    public static final  String PARAMETER_HUISLETTER           = "HUISLETTER";
    /**
     * Parameter voor huisnrtoevoeging.
     */
    public static final  String PARAMETER_HUISNUMMERTOEVOEGING = "HUISNUMMERTOEVOEGING";
    /**
     * Parameter voor postcode.
     */
    public static final  String PARAMETER_POSTCODE             = "POSTCODE";
    private static final String HUISNUMMER                     = "5";
    private static final String HUISLETTER                     = "";
    private static final String HUISNUMMERTOEVOEGING           = "";
    private static final String POSTCODE                       = "3572AA";

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe vraag personen op adres inclusief betrokkenheden aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param bevragingPortType bevraging port type
     * @param parameterMap de parameter map
     * @throws Exception exception
     */
    public VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper(final Eigenschappen eigenschappen,
                                                                final BhgBevraging bevragingPortType,
                                                                final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, bevragingPortType, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        final BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek vraag =
            creeerBevragingPersonenOpAdresInclusiefBetrokkenheden();
        final PersonenOpAdresCommand command = new PersonenOpAdresCommand(vraag);
        roepAan(command);
    }

    /**
     * Creeer bevraging personen op adres inclusief betrokkenheden.
     *
     * @return de bevraging personen op adres inclusief betrokkenheden
     */
    private BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek creeerBevragingPersonenOpAdresInclusiefBetrokkenheden() {
        final BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek bevraging =
            new BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek();

        // Stuurgegevens-element
        zetStuurgegevens(bevraging);

        // Vraag-element toevoegen
        final GroepBerichtZoekcriteriaPersoonBijhoudingBevragingPersonenOpAdresMetBetrokkenheden zoekcriteriaPersoon =
                objectFactory.createGroepBerichtZoekcriteriaPersoonBijhoudingBevragingPersonenOpAdresMetBetrokkenheden();
        bevraging.setZoekcriteriaPersoon(objectFactory.createObjecttypeBerichtZoekcriteriaPersoon(zoekcriteriaPersoon));

        zoekcriteriaPersoon.setCommunicatieID("zoekcriteriaComId");

        // Adres toevoegen aan zoekcriteria
        final Huisnummer huisnummer = new Huisnummer();
        huisnummer.setValue(getValueFromValueMap(PARAMETER_HUISNUMMER, HUISNUMMER));
        final JAXBElement<Huisnummer> huisnummerElement =
            objectFactory.createGroepBerichtZoekcriteriaPersoonHuisnummer(huisnummer);
        zoekcriteriaPersoon.getRest().add(huisnummerElement);

        // Huisletter
        if (!getValueFromValueMap(PARAMETER_HUISLETTER, HUISLETTER).equals("")) {
            final Huisletter huisletter = new Huisletter();
            huisletter.setValue(getValueFromValueMap(PARAMETER_HUISLETTER, HUISLETTER));
            final JAXBElement<Huisletter> huisletterElement =
                objectFactory.createGroepBerichtZoekcriteriaPersoonHuisletter(huisletter);
            zoekcriteriaPersoon.getRest().add(huisletterElement);
        }

        // Huisnummertoevoeging
        if (!getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, HUISNUMMERTOEVOEGING).equals("")) {
            final Huisnummertoevoeging huisnummertoevoeging = new Huisnummertoevoeging();
            huisnummertoevoeging.setValue(getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, HUISNUMMERTOEVOEGING));
            final JAXBElement<Huisnummertoevoeging> huisnummertoevoegingElement =
                objectFactory.createGroepBerichtZoekcriteriaPersoonHuisnummertoevoeging(huisnummertoevoeging);
            zoekcriteriaPersoon.getRest().add(huisnummertoevoegingElement);
        }

        // Postcode
        final Postcode postcode = new Postcode();
        postcode.setValue(getValueFromValueMap(PARAMETER_POSTCODE, POSTCODE));
        final JAXBElement<Postcode> postcodeElement =
                objectFactory.createGroepBerichtZoekcriteriaPersoonPostcode(postcode);
        zoekcriteriaPersoon.getRest().add(postcodeElement);

        return bevraging;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterLijst()
     */
    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_HUISNUMMER, PARAMETER_HUISLETTER, PARAMETER_HUISNUMMERTOEVOEGING,
                             PARAMETER_POSTCODE);
    }
    @Override
    public BERICHT getBericht() {
        return BERICHT.VRAAG_PERSONEN_ADRES;
    }
}
