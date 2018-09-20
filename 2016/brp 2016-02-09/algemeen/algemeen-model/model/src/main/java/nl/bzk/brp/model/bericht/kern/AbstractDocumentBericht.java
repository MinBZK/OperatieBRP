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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.DocumentBasis;

/**
 * Het Brondocument op basis waarvan bijhouding heeft plaatsgevonden.
 *
 * Bijhouding in de BRP vindt vaak plaats naar aanleiding van ontlening uit brondocumenten. Dit kunnen akten zijn, maar
 * zijn dat niet altijd. Vanuit een BRP Actie zijn de documenten - waaraan de gegevens zijn ontleend - raadpleegbaar.
 * Hierbij is het mogelijk dat eenzelfde Document later (nogmaals) wordt gebruikt voor de ontlening; er is dat geval in
 * principe sprake van één-en-hetzelfde document.
 *
 * 1. Het omgaan met documenten 'erft' de BRP uit het GBA tijdperk. De richting waarin de BRP opgaat is echter één
 * waarin we het specifieke document willen kennen. Dat betekent dat - in tegenstelling tot de 'definitie' in het LO 3.x
 * - het objecttype Document in essentie overeenkomt met het daadwerkelijke fysieke en/of digitale document waar de
 * gegevens op staan die eraan ontleend zijn. Dat betekent dat indien eenzelfde document is gebruikt voor twee
 * verschillende administratieve handelingen (bijvoorbeeld het registreren van het Huwelijk en het registreren van de
 * Geboorte door twee verschillende ambtenaren bij twee verschillende gemeenten) er vanuit de corresponderende acties
 * verwezen zal worden naar dat ENE document. De toelichting bij Document is hierop aangepast.
 *
 * E-mail conversatie aangaande regelgeving: Voor archiveerbare digitale documenten schrijft het nationaal archief PDF/A
 * voor. Dit is een beperkt PDF formaat die blijkbaar de gewenste toekomstvaste mogelijkheden biedt. Voor afbeeldingen
 * kiezen ze voor TIFF. Uiteraard is alles altijd voorzien van meta-informatie. Op zich komt vanuit de wet- en
 * regelgeving niet een voorgeschreven formaat naar voren, dus OOXML of ODF et cetera zou ook kunnen. Ik kan me
 * herinneren dat de NORA hier ook iets over zegt. Even opgezocht, op pagina 154 van NORA 2.0 staat de beschrijving.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractDocumentBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, DocumentBasis {

    private static final Integer META_ID = 3135;
    private String soortNaam;
    private SoortDocumentAttribuut soort;
    private DocumentStandaardGroepBericht standaard;

    /**
     * Retourneert Soort van Identiteit.
     *
     * @return Soort.
     */
    public String getSoortNaam() {
        return soortNaam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocumentAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Soort van Identiteit.
     *
     * @param soortNaam Soort.
     */
    public void setSoortNaam(final String soortNaam) {
        this.soortNaam = soortNaam;
    }

    /**
     * Zet Soort van Document.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortDocumentAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Standaard van Document.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final DocumentStandaardGroepBericht standaard) {
        this.standaard = standaard;
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
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        return berichtGroepen;
    }

}
