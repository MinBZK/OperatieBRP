/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ontleningstoelichting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenPersoonBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingDocumentBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.basis.AdministratieveHandelingBasis;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 *
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeri�le verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligt.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:47 CET 2013.
 */
public abstract class AbstractAdministratieveHandelingBericht extends AbstractObjectTypeBericht implements
        AdministratieveHandelingBasis
{

    private SoortAdministratieveHandeling                              soort;
    private String                                                     partijCode;
    private Partij                                                     partij;
    private DatumTijd                                                  tijdstipOntlening;
    private Ontleningstoelichting                                      toelichtingOntlening;
    private DatumTijd                                                  tijdstipRegistratie;
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen;
    private List<AdministratieveHandelingBijgehoudenPersoonBericht>    bijgehoudenPersonen;
    private List<ActieBericht>                                         acties;
    private List<AdministratieveHandelingDocumentBericht>              bijgehoudenDocumenten;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractAdministratieveHandelingBericht(final SoortAdministratieveHandeling soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortAdministratieveHandeling getSoort() {
        return soort;
    }

    /**
     *
     *
     * @return
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ontleningstoelichting getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Retourneert Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     *
     * @return Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     */
    public List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getGedeblokkeerdeMeldingen() {
        return gedeblokkeerdeMeldingen;
    }

    /**
     * Retourneert Administratieve handeling \ Bijgehouden personen van Administratieve handeling.
     *
     * @return Administratieve handeling \ Bijgehouden personen van Administratieve handeling.
     */
    public List<AdministratieveHandelingBijgehoudenPersoonBericht> getBijgehoudenPersonen() {
        return bijgehoudenPersonen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActieBericht> getActies() {
        return acties;
    }

    /**
     * Retourneert Administratieve handeling \ Documenten van Administratieve handeling.
     *
     * @return Administratieve handeling \ Documenten van Administratieve handeling.
     */
    public List<AdministratieveHandelingDocumentBericht> getBijgehoudenDocumenten() {
        return bijgehoudenDocumenten;
    }

    /**
     *
     *
     * @param partijCode
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Zet Partij van Administratieve handeling.
     *
     * @param partij Partij.
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * Zet Tijdstip ontlening van Administratieve handeling.
     *
     * @param tijdstipOntlening Tijdstip ontlening.
     */
    public void setTijdstipOntlening(final DatumTijd tijdstipOntlening) {
        this.tijdstipOntlening = tijdstipOntlening;
    }

    /**
     * Zet Toelichting ontlening van Administratieve handeling.
     *
     * @param toelichtingOntlening Toelichting ontlening.
     */
    public void setToelichtingOntlening(final Ontleningstoelichting toelichtingOntlening) {
        this.toelichtingOntlening = toelichtingOntlening;
    }

    /**
     * Zet Tijdstip registratie van Administratieve handeling.
     *
     * @param tijdstipRegistratie Tijdstip registratie.
     */
    public void setTijdstipRegistratie(final DatumTijd tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Zet Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     *
     * @param gedeblokkeerdeMeldingen Administratieve handeling \ Gedeblokkeerde Meldingen.
     */
    public void setGedeblokkeerdeMeldingen(
            final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen)
    {
        this.gedeblokkeerdeMeldingen = gedeblokkeerdeMeldingen;
    }

    /**
     * Zet Administratieve handeling \ Bijgehouden personen van Administratieve handeling.
     *
     * @param bijgehoudenPersonen Administratieve handeling \ Bijgehouden personen.
     */
    public void
            setBijgehoudenPersonen(final List<AdministratieveHandelingBijgehoudenPersoonBericht> bijgehoudenPersonen)
    {
        this.bijgehoudenPersonen = bijgehoudenPersonen;
    }

    /**
     * Zet Acties van Administratieve handeling.
     *
     * @param acties Acties.
     */
    public void setActies(final List<ActieBericht> acties) {
        this.acties = acties;
    }

    /**
     * Zet Administratieve handeling \ Documenten van Administratieve handeling.
     *
     * @param bijgehoudenDocumenten Administratieve handeling \ Documenten.
     */
    public void setBijgehoudenDocumenten(final List<AdministratieveHandelingDocumentBericht> bijgehoudenDocumenten) {
        this.bijgehoudenDocumenten = bijgehoudenDocumenten;
    }

}
