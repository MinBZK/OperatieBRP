/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenDocumentBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenPersoonBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBronBericht;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingBasis;

/**
 * Een door het bijhoudingsorgaan geïnitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 *
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeriële verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in één of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractAdministratieveHandelingBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        AdministratieveHandelingBasis
{

    private static final Integer META_ID = 9018;
    private SoortAdministratieveHandelingAttribuut soort;
    private String partijCode;
    private PartijAttribuut partij;
    private OntleningstoelichtingAttribuut toelichtingOntlening;
    private DatumTijdAttribuut tijdstipRegistratie;
    private AdministratieveHandelingStandaardGroepBericht standaard;
    private AdministratieveHandelingLeveringGroepBericht levering;
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen;
    private List<ActieBericht> acties;
    private List<AdministratieveHandelingBronBericht> bronnen;
    private List<AdministratieveHandelingBijgehoudenPersoonBericht> bijgehoudenPersonen;
    private List<AdministratieveHandelingBijgehoudenDocumentBericht> bijgehoudenDocumenten;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractAdministratieveHandelingBericht(final SoortAdministratieveHandelingAttribuut soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortAdministratieveHandelingAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Partij van Identiteit.
     *
     * @return Partij.
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OntleningstoelichtingAttribuut getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingLeveringGroepBericht getLevering() {
        return levering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getGedeblokkeerdeMeldingen() {
        return gedeblokkeerdeMeldingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // Handmatige wijziging
    @Valid
    public List<ActieBericht> getActies() {
        return acties;
    }

    /**
     * Retourneert Administratieve handeling \ Bronnen van Administratieve handeling.
     *
     * @return Administratieve handeling \ Bronnen van Administratieve handeling.
     */
    public List<AdministratieveHandelingBronBericht> getBronnen() {
        return bronnen;
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
     * Retourneert Administratieve handeling \ Bijgehouden documenten van Administratieve handeling.
     *
     * @return Administratieve handeling \ Bijgehouden documenten van Administratieve handeling.
     */
    public List<AdministratieveHandelingBijgehoudenDocumentBericht> getBijgehoudenDocumenten() {
        return bijgehoudenDocumenten;
    }

    /**
     * Zet Partij van Identiteit.
     *
     * @param partijCode Partij.
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Zet Partij van Administratieve handeling.
     *
     * @param partij Partij.
     */
    public void setPartij(final PartijAttribuut partij) {
        this.partij = partij;
    }

    /**
     * Zet Toelichting ontlening van Administratieve handeling.
     *
     * @param toelichtingOntlening Toelichting ontlening.
     */
    public void setToelichtingOntlening(final OntleningstoelichtingAttribuut toelichtingOntlening) {
        this.toelichtingOntlening = toelichtingOntlening;
    }

    /**
     * Zet Tijdstip registratie van Administratieve handeling.
     *
     * @param tijdstipRegistratie Tijdstip registratie.
     */
    public void setTijdstipRegistratie(final DatumTijdAttribuut tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Zet Standaard van Administratieve handeling.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final AdministratieveHandelingStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Levering van Administratieve handeling.
     *
     * @param levering Levering.
     */
    public void setLevering(final AdministratieveHandelingLeveringGroepBericht levering) {
        this.levering = levering;
    }

    /**
     * Zet Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     *
     * @param gedeblokkeerdeMeldingen Administratieve handeling \ Gedeblokkeerde Meldingen.
     */
    public void setGedeblokkeerdeMeldingen(final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen) {
        this.gedeblokkeerdeMeldingen = gedeblokkeerdeMeldingen;
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
     * Zet Administratieve handeling \ Bronnen van Administratieve handeling.
     *
     * @param bronnen Administratieve handeling \ Bronnen.
     */
    public void setBronnen(final List<AdministratieveHandelingBronBericht> bronnen) {
        this.bronnen = bronnen;
    }

    /**
     * Zet Administratieve handeling \ Bijgehouden personen van Administratieve handeling.
     *
     * @param bijgehoudenPersonen Administratieve handeling \ Bijgehouden personen.
     */
    public void setBijgehoudenPersonen(final List<AdministratieveHandelingBijgehoudenPersoonBericht> bijgehoudenPersonen) {
        this.bijgehoudenPersonen = bijgehoudenPersonen;
    }

    /**
     * Zet Administratieve handeling \ Bijgehouden documenten van Administratieve handeling.
     *
     * @param bijgehoudenDocumenten Administratieve handeling \ Bijgehouden documenten.
     */
    public void setBijgehoudenDocumenten(final List<AdministratieveHandelingBijgehoudenDocumentBericht> bijgehoudenDocumenten) {
        this.bijgehoudenDocumenten = bijgehoudenDocumenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        final List<BerichtEntiteit> berichtEntiteiten = new ArrayList<>();
        if (acties != null) {
            berichtEntiteiten.addAll(getActies());
        }
        if (bronnen != null) {
            berichtEntiteiten.addAll(getBronnen());
        }
        return berichtEntiteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }

}
