/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.ActieBronBasis;

/**
 * De Verantwoording van een Actie door een bron, hetzij een Document hetzij een vooraf bekende Rechtsgrond, hetzij de
 * omschrijving van een (niet vooraf bekende) rechtsgrond.
 *
 * Een BRP Actie wordt verantwoord door nul, één of meer Documenten en nul, één of meer Rechtsgronden. Elke combinatie
 * van de Actie enerzijds en een bron (een Document of een Rechtsgrond) anderzijds, wordt vastgelegd.
 *
 * De naam is een tijdje 'verantwoording' geweest. Het is echter niet meer dan een koppeltabel tussen een actie
 * enerzijds, en een document of rechtsgrond anderzijds. Een generalisatie van document en rechtsgrond zou 'bron' zijn.
 * Passend in het BMR toegepaste patroon is dan om de koppeltabel - die actie enerzijds en bron anderzijds koppelt - dan
 * de naam Actie/Bron te noemen. Hiervoor is uiteindelijk gekozen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractActieBronBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, ActieBronBasis
{

    private static final Integer META_ID = 8118;
    private ActieBericht actie;
    private DocumentBericht document;
    private String rechtsgrondCode;
    private RechtsgrondAttribuut rechtsgrond;
    private OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving;

    /**
     * {@inheritDoc}
     */
    @Override
    public ActieBericht getActie() {
        return actie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentBericht getDocument() {
        return document;
    }

    /**
     * Retourneert Rechtsgrond van Identiteit.
     *
     * @return Rechtsgrond.
     */
    public String getRechtsgrondCode() {
        return rechtsgrondCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RechtsgrondAttribuut getRechtsgrond() {
        return rechtsgrond;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OmschrijvingEnumeratiewaardeAttribuut getRechtsgrondomschrijving() {
        return rechtsgrondomschrijving;
    }

    /**
     * Zet Actie van Actie \ Bron.
     *
     * @param actie Actie.
     */
    public void setActie(final ActieBericht actie) {
        this.actie = actie;
    }

    /**
     * Zet Document van Actie \ Bron.
     *
     * @param document Document.
     */
    public void setDocument(final DocumentBericht document) {
        this.document = document;
    }

    /**
     * Zet Rechtsgrond van Identiteit.
     *
     * @param rechtsgrondCode Rechtsgrond.
     */
    public void setRechtsgrondCode(final String rechtsgrondCode) {
        this.rechtsgrondCode = rechtsgrondCode;
    }

    /**
     * Zet Rechtsgrond van Actie \ Bron.
     *
     * @param rechtsgrond Rechtsgrond.
     */
    public void setRechtsgrond(final RechtsgrondAttribuut rechtsgrond) {
        this.rechtsgrond = rechtsgrond;
    }

    /**
     * Zet Rechtsgrondomschrijving van Actie \ Bron.
     *
     * @param rechtsgrondomschrijving Rechtsgrondomschrijving.
     */
    public void setRechtsgrondomschrijving(final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving) {
        this.rechtsgrondomschrijving = rechtsgrondomschrijving;
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
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }

}
