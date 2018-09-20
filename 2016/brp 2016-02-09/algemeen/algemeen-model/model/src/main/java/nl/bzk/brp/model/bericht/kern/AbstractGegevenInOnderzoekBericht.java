/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoekBasis;

/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
 *
 * Bij conversie tussen GBA-V en BRP formaat, maakt men graag gebruik gemaakt van bepaalde standaard ORM software voor
 * het schrijven en lezen van objecten uit de database, in dit geval specifiek voor het vastleggen van (tegelijk) een
 * inhoudelijk gegeven EN het registreren van een indicatie dat dit inhoudelijk gegeven in onderzoek is. Vooralsnog is
 * het gebruik van deze ORM mapping functionaliteit nog niet voorzien in de reguliere bijhouding van de BRP. Besloten is
 * om vooralsnog de "do instead" functionaliteit van Postgres te gebruiken. Hierbij ontstaat dus in essentie een
 * verschil tussen het "op basis van patronen gegenereerde Operationeel model", en het fysieke model zoals gegenereerd
 * met de sql DDL. Mocht in de toekomst blijken dat er meer afwijkingen ontstaan tussen operationeel model en fysiek
 * model, dan kan deze keuze herzien worden. (Met als mogelijke oplossingsrichtingen bijvoorbeeld het opnemen van deze
 * extra structuur in het logisch model, of bijvoorbeeld het expliciet onderkennen van een fysiek model dat NIET alleen
 * op basis van patronen wordt gegenereerd uit het logische model. De hiertoe gegeneerde sql code heeft (o.a.) het
 * volgende commentaar: -- Door het gebruikt van standaard ORM software om het opslaan en lezen van objecten uit de
 * database te realiseren, -- is er voor GegevenInOnderzoek behoefte aan een extra kolom 'TblGegeven' die een 1-op-1
 * mapping representeert -- tussen entiteit classen in de software en een waarde in de TblGegeven kolom in de database.
 * -- Deze kolom voegt geen extra informatie toe ten opzichte van de SrtGegeven kolom, en wordt daarom gerealiseerd --
 * als een 'writeable view' gebaseerd op de tabellen GegevenInOnderzoek en DbObject.
 *
 * Formeel zou er een Logische Identiteit zitten op SrtGegeven en de Object Sleutel / Voorkomen Sleutel. Maar aangezien
 * of de Object Sleutel of Voorkomen Sleutel worden ingevuld, is dit een zinloze UC op de database (de NULL zorgt immers
 * dat dubbele gewoon kunnen worden toegevoegd).
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractGegevenInOnderzoekBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        GegevenInOnderzoekBasis
{

    private static final Integer META_ID = 3863;
    private OnderzoekBericht onderzoek;
    private ElementAttribuut element;
    private SleutelwaardeAttribuut objectSleutelGegeven;
    private SleutelwaardeAttribuut voorkomenSleutelGegeven;

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekBericht getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ElementAttribuut getElement() {
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SleutelwaardeAttribuut getObjectSleutelGegeven() {
        return objectSleutelGegeven;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SleutelwaardeAttribuut getVoorkomenSleutelGegeven() {
        return voorkomenSleutelGegeven;
    }

    /**
     * Zet Onderzoek van Gegeven in onderzoek.
     *
     * @param onderzoek Onderzoek.
     */
    public void setOnderzoek(final OnderzoekBericht onderzoek) {
        this.onderzoek = onderzoek;
    }

    /**
     * Zet Element van Gegeven in onderzoek.
     *
     * @param element Element.
     */
    public void setElement(final ElementAttribuut element) {
        this.element = element;
    }

    /**
     * Zet Object sleutel gegeven van Gegeven in onderzoek.
     *
     * @param objectSleutelGegeven Object sleutel gegeven.
     */
    public void setObjectSleutelGegeven(final SleutelwaardeAttribuut objectSleutelGegeven) {
        this.objectSleutelGegeven = objectSleutelGegeven;
    }

    /**
     * Zet Voorkomen sleutel gegeven van Gegeven in onderzoek.
     *
     * @param voorkomenSleutelGegeven Voorkomen sleutel gegeven.
     */
    public void setVoorkomenSleutelGegeven(final SleutelwaardeAttribuut voorkomenSleutelGegeven) {
        this.voorkomenSleutelGegeven = voorkomenSleutelGegeven;
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
