/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroepReferentie;
import nl.bzk.brp.bijhouding.bericht.model.BooleanElement;
import nl.bzk.brp.bijhouding.bericht.model.CharacterElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.bericht.model.Element;
import nl.bzk.brp.bijhouding.bericht.model.IntegerElement;
import nl.bzk.brp.bijhouding.bericht.model.ObjectSleutelIndex;
import nl.bzk.brp.bijhouding.bericht.model.ObjectSleutelIndexImpl;
import nl.bzk.brp.bijhouding.bericht.model.OngeldigeWaardeException;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;

/**
 * De context die gedeeld wordt tussen parsers gedurerende het parse proces van een XML bijhoudingsbericht.
 */
public final class ParserContext {

    private static final String FOUTMELDING = "Fout bij het verwerken van het XML document.";
    private final XMLStreamReader reader;
    private final Map<String, BmrGroep> groepMap;
    private final List<Element> elementen;
    private final List<BmrGroepReferentie> referenties;
    private final ObjectSleutelIndex objectSleutelIndex;

    /**
     * Maakt een nieuw ParserContext object.
     *
     * @param reader de XMLStreamReader
     */
    public ParserContext(final XMLStreamReader reader) {
        this.reader = reader;
        groepMap = new HashMap<>();
        elementen = new ArrayList<>();
        referenties = new ArrayList<>();
        objectSleutelIndex = new ObjectSleutelIndexImpl();
    }

    /**
     * Voegt een element - dat een groep representeerd - toe aan de context zodat deze later via de communicatieId weer
     * is op te vragen. Daarnaast wordt het element - als deze een objectsleutel bevat - toegevoegd aan de objectsleutel
     * index.
     * 
     * @param element het element dat geregisteerd moet worden
     * @param <T> het element type
     * @return het gegeven element
     * @throws ParseException wanneer er al een element is toegevoegd met hetzelfde id
     */
    public <T extends Element> T registeerElement(final T element) throws ParseException {
        elementen.add(element);
        if ((element instanceof BmrGroep) && ((BmrGroep) element).getCommunicatieId() != null) {
            final BmrGroep groep = (BmrGroep) element;
            if (groepMap.containsKey(groep.getCommunicatieId())) {
                throw new ParseException(
                    String.format("Er komende meerdere elementen in het bericht voor met communicatieId: %s", groep.getCommunicatieId()));
            }
            groepMap.put(groep.getCommunicatieId(), groep);
            if (groep instanceof BmrGroepReferentie) {
                referenties.add((BmrGroepReferentie) groep);
            }
        }
        objectSleutelIndex.voegToe(element);
        return element;
    }

    /**
     * Geef de mapping van communicatieIds en groepen. Deze Map is niet wijzigbaar.
     *
     * @return groepMap
     */
    public Map<String, BmrGroep> getGroepMap() {
        return Collections.unmodifiableMap(groepMap);
    }

    /**
     * Geef de lijst van alle elementen die gemaakt tijdens het parsen met deze context.
     * 
     * @return de lijst met elementen
     */
    public List<Element> getElementen() {
        return Collections.unmodifiableList(elementen);
    }

    /**
     * Geef de lijst van referenties die zijn aangemaakt tijdens het parsen van het bijhoudingsbericht.
     *
     * @return referenties
     */
    public List<BmrGroepReferentie> getReferenties() {
        return Collections.unmodifiableList(referenties);
    }

    /**
     * Geef de waarde van objectSleutelIndex.
     *
     * @return objectSleutelIndex
     */
    public ObjectSleutelIndex getObjectSleutelIndex() {
        return objectSleutelIndex;
    }

    /**
     * De context is klaar wanneer alle XML inhoud is verwerkt. Zolang er nog onverwerkte inhoud is zijn er nog één of
     * meerdere events.
     *
     * @return true is er nog te verwerken events zijn, anders false
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public boolean heeftVolgendeEvent() throws ParseException {
        try {
            return reader.hasNext();
        } catch (XMLStreamException e) {
            throw new ParseException(FOUTMELDING, e);
        }
    }

    /**
     * Het volgende event dat verwerkt moet worden.
     *
     * @return het volgende event
     * @see javax.xml.stream.events.XMLEvent
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public int volgendeEvent() throws ParseException {
        try {
            return reader.next();
        } catch (XMLStreamException e) {
            throw new ParseException(FOUTMELDING, e);
        }
    }

    /**
     * Het aantal attributen.
     * 
     * @return aantal
     */
    public int getAantalAttributen() {
        return reader.getAttributeCount();
    }

    /**
     * Of de context verwijst naar een start element tag.
     *
     * @return true als start element tag, anders false
     */
    public boolean isStartElement() {
        return reader.isStartElement();
    }

    /**
     * Geeft de naam van het huidige event dat wordt verwerkt. Dit kan de naam van het element zijn ingeval van een
     * start of einde event van een element.
     *
     * @return de event naam
     */
    public String getEventNaam() {
        return reader.getLocalName();
    }

    /**
     * Geef de localName van het attribuut horende bij de gegeven index.
     *
     * @param attribuutIndex index
     * @return localName
     */
    public String getAttribuutNaam(final int attribuutIndex) {
        return reader.getAttributeLocalName(attribuutIndex);
    }

    /**
     * Geef de waarde van het attribuut horende bij de gegeven index.
     *
     * @param attribuutIndex index
     * @return waarde
     */
    public String getAttribuutWaarde(final int attribuutIndex) {
        return reader.getAttributeValue(attribuutIndex);
    }

    /**
     * Leest de text inhoud van een element. Alvorens deze methode aan te roepen moet de context verwijzen naar een
     * start element tag. Na de verwerking van deze methode zal de context verwijzen naar het corresponderende einde
     * tag.
     *
     * @return de text inhoud van het element
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public StringElement getStringElement() throws ParseException {
        return new StringElement(getElementText());
    }

    /**
     * Leest de karakter (string lengte == 1) inhoud van een element. Alvorens deze methode aan te roepen moet de
     * context verwijzen naar een start element tag. Na de verwerking van deze methode zal de context verwijzen naar het
     * corresponderende einde tag.
     *
     * @return de karakter inhoud van het element
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public CharacterElement getCharacterElement() throws ParseException {
        try {
            return CharacterElement.parseWaarde(getElementText());
        } catch (OngeldigeWaardeException e) {
            throw new ParseException("Fout bij parsen character element.", e);
        }
    }

    /**
     * Leest de datum inhoud van een element. Alvorens deze methode aan te roepen moet de context verwijzen naar een
     * start element tag. Na de verwerking van deze methode zal de context verwijzen naar het corresponderende einde
     * tag.
     *
     * @return de datum inhoud van het element
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public DatumElement getDatumElement() throws ParseException {
        try {
            return DatumElement.parseWaarde(getElementText());
        } catch (OngeldigeWaardeException e) {
            throw new ParseException("Fout bij parsen datum element.", e);
        }
    }

    /**
     * Leest de datum / tijd inhoud van een element. Alvorens deze methode aan te roepen moet de context verwijzen naar
     * een start element tag. Na de verwerking van deze methode zal de context verwijzen naar het corresponderende einde
     * tag.
     *
     * @return de datum / tijd inhoud van het element
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public DatumTijdElement getDatumTijdElement() throws ParseException {
        try {
            return DatumTijdElement.parseWaarde(getElementText());
        } catch (OngeldigeWaardeException e) {
            throw new ParseException("Fout bij parsen datum / tijd element.", e);
        }
    }

    /**
     * Leest de Ja/Nee inhoud van een element. Alvorens deze methode aan te roepen moet de context verwijzen naar een
     * start element tag. Na de verwerking van deze methode zal de context verwijzen naar het corresponderende einde
     * tag.
     *
     * @return de datum / tijd inhoud van het element
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public BooleanElement getBooleanElement() throws ParseException {
        try {
            return BooleanElement.parseWaarde(getElementText());
        } catch (OngeldigeWaardeException e) {
            throw new ParseException("Fout bij parsen boolean element.", e);
        }
    }

    /**
     * Leest de Integer inhoud van een element. Alvorens deze methode aan te roepen moet de context verwijzen naar een
     * start element tag. Na de verwerking van deze methode zal de context verwijzen naar het corresponderende einde
     * tag.
     *
     * @return de integer inhoud van het element
     * @throws ParseException als het verwerken van het XML document fout gaat
     */
    public IntegerElement getIntegerElement() throws ParseException {
        try {
            return IntegerElement.parseWaarde(getElementText());
        } catch (OngeldigeWaardeException e) {
            throw new ParseException("Fout bij parsen integer element.", e);
        }
    }

    /**
     * Geeft de map van attributen voor het huidige element binnen de parser context.
     *
     * @return de map van attributen
     */
    public Map<String, String> getAttributenMap() {
        final Map<String, String> result = new LinkedHashMap<>();
        final int aantalAttributen = getAantalAttributen();
        for (int attribuutIndex = 0; attribuutIndex < aantalAttributen; attribuutIndex++) {
            result.put(getAttribuutNaam(attribuutIndex), getAttribuutWaarde(attribuutIndex));
        }
        return result;
    }

    private String getElementText() throws ParseException {
        try {
            return reader.getElementText();
        } catch (XMLStreamException e) {
            throw new ParseException("Er kan geen element text inhoud worden gelezen.", e);
        }
    }
}
