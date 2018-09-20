/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import javax.xml.bind.JAXBElement;

import nl.bzk.brp.brp0200.Administratienummer;
import nl.bzk.brp.brp0200.Aktenummer;
import nl.bzk.brp.brp0200.Burgerservicenummer;
import nl.bzk.brp.brp0200.ContainerActieBronnen;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingBijgehoudenDocumenten;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.DocumentIdentificatie;
import nl.bzk.brp.brp0200.GroepBerichtParameters;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeActie;
import nl.bzk.brp.brp0200.ObjecttypeActieBron;
import nl.bzk.brp.brp0200.ObjecttypeDocument;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.VerwerkingswijzeNaam;
import nl.bzk.brp.brp0200.VerwerkingswijzeNaamS;
import org.apache.commons.lang.StringUtils;

public final class BijhoudingUtil {

    /** Lengte van een BSN. */
    protected static final Integer BSN_LENGTE = 9;
    /** Lengte van een Administratienummer. */
    protected static final Integer ANR_LENGTE = 10;

    /** Standaard parameter naam voor prevalidatie. */
    public static final String PARAMETER_PREVALIDATIE = "PREVALIDATIE";
    /** Standaard parameter naam voor entiteit type document. */
    public static final String OBJECTTYPE_DOCUMENT = "Document";


    private static final ObjectFactory objectFactory = new ObjectFactory();

    /**
     * Bouwt de groep bericht parameters op en retourneert deze.
     *
     * @param prevalidatie prevalidatie
     * @return een groep bericht parameters.
     */
    public static JAXBElement<GroepBerichtParameters> maakBerichtParameters(final Boolean prevalidatie) {
        // Parameters
        final GroepBerichtParameters groepBerichtParameters = objectFactory.createGroepBerichtParameters();
        groepBerichtParameters.setCommunicatieID("COMM_ID_PARAMS");

        final VerwerkingswijzeNaam verwerkingswijze = objectFactory.createVerwerkingswijzeNaam();

        if (prevalidatie != null && !prevalidatie) {
            verwerkingswijze.setValue(VerwerkingswijzeNaamS.BIJHOUDING);
        } else {
            verwerkingswijze.setValue(VerwerkingswijzeNaamS.PREVALIDATIE);
        }
        groepBerichtParameters.setVerwerkingswijze(
                objectFactory.createGroepBerichtParametersVerwerkingswijze(verwerkingswijze));
        return objectFactory.createObjecttypeBerichtParameters(groepBerichtParameters);
    }

    /**
     * Creeer jaxb aanvang geldigheid.
     *
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @return de jAXB element
     */
    public static JAXBElement<DatumMetOnzekerheid> maakJaxbDatumAanvangGeldigheid(final String datumAanvangGeldigheid) {
        final DatumMetOnzekerheid datumMetOnzekerheid = new DatumMetOnzekerheid();
        datumMetOnzekerheid.setValue(datumAanvangGeldigheid);
        return objectFactory.createDatumAanvangGeldigheid(datumMetOnzekerheid);
    }

    /**
     * Voegt aan de opgegeven administratieve handeling een 'bijgehouden documenten' container toe met daarin een
     * 'bijgehouden document' element met opgegeven soort aktenaam en partijcode.
     *
     *
     * @param soortAkteNaam de naam van de soort van akte.
     * @param partijCode de partij code.
     * @return de communicatie id van het toegevoegde document.
     */
    protected ContainerAdministratieveHandelingBijgehoudenDocumenten maakAdministratieveHandelingBijgehoudenDocumenten(
            final String soortAkteNaam, final PartijCode partijCode, final String communicatieId)
    {
        final ContainerAdministratieveHandelingBijgehoudenDocumenten administratieveHandelingBijgehoudenDocumenten =
                new ContainerAdministratieveHandelingBijgehoudenDocumenten();

        final ObjecttypeDocument documentBijhouding = new ObjecttypeDocument();
        documentBijhouding.setSoortNaam(creeerJaxbSoortDocumentNaam(OBJECTTYPE_DOCUMENT));
        documentBijhouding.setCommunicatieID(communicatieId);

        final NaamEnumeratiewaarde soortnaam = new NaamEnumeratiewaarde();
        soortnaam.setValue(soortAkteNaam);
        documentBijhouding.setSoortNaam(objectFactory.createObjecttypeDocumentSoortNaam(soortnaam));
        final DocumentIdentificatie identificatie = new DocumentIdentificatie();
        identificatie.setValue("HA0530-3AA0001");
        documentBijhouding.setIdentificatie(objectFactory.createObjecttypeDocumentIdentificatie(identificatie));

        final Aktenummer aktenummer = new Aktenummer();
        aktenummer.setValue("3AA0001");
        final JAXBElement<Aktenummer> jaxbAktenummer = objectFactory.createObjecttypeDocumentAktenummer(aktenummer);
        documentBijhouding.setAktenummer(jaxbAktenummer);
        documentBijhouding.setPartijCode(objectFactory.createObjecttypeDocumentPartijCode(partijCode));

        administratieveHandelingBijgehoudenDocumenten.getDocument().add(documentBijhouding);
//        administratieveHandeling.setBijgehoudenDocumenten(
//                objectFactory.createObjecttypeAdministratieveHandelingBijgehoudenDocumenten(
//                        administratieveHandelingBijgehoudenDocumenten)
//                                                         );
        return administratieveHandelingBijgehoudenDocumenten;
    }

    /**
     * Plaatst de bijgehouden documenten.
     *
     * @param partijCode partij code
     * @param soortAkteNaam soort akte naam
     * @param communicatieId the communicatie id
     * @return bijgehouden documenten
     */
    public static ObjecttypeDocument maakDocument(final PartijCode partijCode, final String soortAkteNaam,
                                                  final String communicatieId)
    {
        final ObjecttypeDocument document = objectFactory.createObjecttypeDocument();
        document.setObjecttype(OBJECTTYPE_DOCUMENT);
        document.setPartijCode(objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));
        document.setSoortNaam(creeerJaxbSoortDocumentNaam(soortAkteNaam));
        document.setCommunicatieID(communicatieId);
        document.setAktenummer(creeerJaxbAktenummer("3AA0001"));
        document.setIdentificatie(creeerJaxbIdentificatie("HA0530-3AA0001"));

        return document;
    }

    /**
     * Creeert jaxb soort document naam.
     *
     * @param docSoortNaam doc soort naam
     * @return JAXB element
     */
    private static JAXBElement<NaamEnumeratiewaarde> creeerJaxbSoortDocumentNaam(final String docSoortNaam) {
        NaamEnumeratiewaarde  attribuut = objectFactory.createNaamEnumeratiewaarde();
        attribuut.setValue(docSoortNaam);
        return objectFactory.createObjecttypeDocumentSoortNaam(attribuut);
    }

    /**
     * Creeert jaxb aktenummer.
     *
     * @param aktenummer aktenummer
     * @return jAXB element
     */
    private static JAXBElement<Aktenummer> creeerJaxbAktenummer(final String aktenummer) {
        Aktenummer attribuut = objectFactory.createAktenummer();
        attribuut.setValue(aktenummer);
        return objectFactory.createObjecttypeDocumentAktenummer(attribuut);
    }

    /**
     * Creeert jaxb identificatie.
     *
     * @param identificatienummer identificatienummer
     * @return jAXB element
     */
    private static JAXBElement<DocumentIdentificatie> creeerJaxbIdentificatie(final String identificatienummer) {
        DocumentIdentificatie attribuut = objectFactory.createDocumentIdentificatie();
        attribuut.setValue(identificatienummer);
        return objectFactory.createObjecttypeDocumentIdentificatie(attribuut);
    }


    /**
     * Zet bronnen.
     *
     * @param actie actie
     * @param refId ref id
     */
    public static void setBronnen(final ObjecttypeActie actie, final String refId, final String communicatieIdDoc,
                                  final String communicatieIdBron) {
        actie.setBronnen(objectFactory.createObjecttypeActieBronnen(
                objectFactory.createContainerActieBronnen()));
        ObjecttypeDocument document = objectFactory.createObjecttypeDocument();
        document.setReferentieID(refId);
        document.setCommunicatieID(communicatieIdDoc);
        document.setObjecttype("Document");

        final ObjecttypeActieBron bron = new ObjecttypeActieBron();
        bron.setObjecttype("ActieBron");
        bron.setReferentieID("comid.bron1");
        bron.setCommunicatieID(communicatieIdBron);
        actie.getBronnen().getValue().getBron().add(bron);
    }

    /**
     * Bouwt bronnen.
     *
     * @param refId ref id
     * @return jAXB element
     */
    protected JAXBElement<ContainerActieBronnen> bouwBronnen(final String refId, final String communicatieId) {
        JAXBElement<ContainerActieBronnen> bronnen = objectFactory.createObjecttypeActieBronnen(
                objectFactory.createContainerActieBronnen());
        ObjecttypeDocument document = objectFactory.createObjecttypeDocument();
        document.setReferentieID(refId);
        document.setCommunicatieID(communicatieId);
        document.setObjecttype("Document");

        ObjecttypeActieBron objecttypeActieBron = new ObjecttypeActieBron();
        JAXBElement<ObjecttypeDocument> documentJAXBElement =
                objectFactory.createObjecttypeActieBronDocument(document);

        objecttypeActieBron.setDocument(documentJAXBElement);

        bronnen.getValue().getBron().add(objecttypeActieBron);
        return bronnen;
    }

    /**
     * Bouwt een bsn element.
     *
     * @param bsn bsn
     * @return jAXB element
     */
    public static JAXBElement<Burgerservicenummer> maakBsnElement(final String bsn) {
        if (StringUtils.isEmpty(bsn)) {
            return null;
        } else {
            final Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
            burgerservicenummer.setValue(StringUtils.rightPad(bsn, BSN_LENGTE, '0'));
            return objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummer);
        }
    }

    /**
     * Bouwt een anr element.
     *
     * @param anr anr
     * @return jAXB element
     */
    public static JAXBElement<Administratienummer> maakAnrElement(final String anr) {
        if (StringUtils.isEmpty(anr)) {
            return null;
        } else {
            final Administratienummer administratienummer = new Administratienummer();
            administratienummer.setValue(StringUtils.rightPad(anr, ANR_LENGTE, '0'));
            return objectFactory.createGroepBerichtZoekcriteriaPersoonAdministratienummer(administratienummer);
        }
    }
}
