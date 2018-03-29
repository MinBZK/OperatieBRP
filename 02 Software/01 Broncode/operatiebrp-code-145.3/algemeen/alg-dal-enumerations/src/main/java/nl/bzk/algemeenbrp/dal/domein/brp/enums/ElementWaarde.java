/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * Bevat de waarde van een element uit de Element tabel.
 */
public final class ElementWaarde implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int PROPERTY_ID = 0;
    private static final int PROPERTY_SOORT = 1;
    private static final int PROPERTY_GROEP = 2;
    private static final int PROPERTY_SOORT_AUTORISATIE = 3;
    private static final int PROPERTY_ELEMENTNAAM = 4;
    private static final int PROPERTY_VOLGNUMMER = 5;
    private static final int PROPERTY_HISTORIEPATROON = 6;
    private static final int PROPERTY_TYPE = 7;
    private static final int PROPERTY_BASISTYPE = 8;
    private static final int PROPERTY_ALIAS = 9;
    private static final int PROPERTY_ALIASVAN = 10;
    private static final int PROPERTY_MINLENGTE = 11;
    private static final int PROPERTY_MAXLENGTE = 12;
    private static final int PROPERTY_INVERSECODE = 13;
    private static final int PROPERTY_OBJECT = 14;
    private static final int PROPERTY_VERANTWOORDING = 15;
    private static final int PROPERTY_IDENTXSD = 16;
    private static final int PROPERTY_DATUMAANVANG = 17;
    private static final int PROPERTY_DATUMEINDE = 18;
    private static final int PROPERTY_IDENTDB = 19;
    private static final int PROPERTY_HISIDENTDB = 20;
    private static final int PROPERTY_TABEL = 21;
    private static final int PROPERTY_HISTABEL = 22;
    private static final int PROPERTY_IDENTDBSCHEMA = 23;
    private static final int PROPERTY_TYPEIDENTDB = 24;
    private static final int PROPERTY_INBERICHT = 25;
    private static final int PROPERTY_SOORTINHOUD = 26;
    private static final int PROPERTY_SORTEERVOLGORDE = 27;
    private static final int PROPERTY_ACTUEEL_GELDIG_ATTRIBUUT = 28;
    private static final int PROPERTY_IDENTEXPRESSIE = 29;

    private static final int AANTAL_KOLOMMEN = 30;

    /**
     * De map waarin de elementwaarde gemapped is op de element naam.
     */
    private static final Map<String, ElementWaarde> ELEMENT_NAAM_MAP = new HashMap<>();
    /**
     * De map waarin de elementwaarde gemapped is op de element id.
     */
    private static final Map<Integer, ElementWaarde> ELEMENT_ID_MAP = new HashMap<>();
    /**
     * Map met elementId naar {@link SoortBericht}.
     */
    private static final Map<Integer, EnumSet<SoortBericht>> ELEMENT_SOORTBERICHT_MAP = maakBerichtElementMap();

    static {
        ElementWaarde.vulElementen();
    }

    private String naam;
    private int id;
    private SoortElement soort;
    private SoortElementAutorisatie soortAutorisatie;
    private ElementWaarde groep;
    private String elementNaam;
    private Integer volgnummer;
    private HistoriePatroon historiePatroon;
    private Integer type;
    private ElementBasisType basisType;
    private String naamAlias;
    private Integer typeAlias;
    private Integer minimumlengte;
    private Integer maximumlengte;
    private String inverseassociatieIdentcode;
    private Integer objecttype;
    private VerantwoordingCategorie verantwoordingcategorie;
    private String xmlNaam;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private String identdb;
    private String hisidentdb;
    private Integer tabel;
    private Integer histabel;
    private String identdbschema;
    private DatabaseType typeidentdb;
    private boolean inBericht;
    private String soortInhoud;
    private Integer sorteervolgorde;
    private Integer actueelGeldigAttribuut;
    private String identiteitExpressie;

    /*
     * Explicit private constructor.
     */
    private ElementWaarde() {
    }

    /**
     * Geef de waarde van naam van ElementWaarde.
     * @return de waarde van naam van ElementWaarde
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van id van ElementWaarde.
     * @return de waarde van id van ElementWaarde
     */
    public int getId() {
        return id;
    }

    /**
     * Geef de waarde van soort van ElementWaarde.
     * @return de waarde van soort van ElementWaarde
     */
    public SoortElement getSoort() {
        return soort;
    }

    /**
     * Geef de waarde van groep van ElementWaarde.
     * @return de waarde van groep van ElementWaarde
     */
    public ElementWaarde getGroep() {
        return groep;
    }

    /**
     * Geef de soort element autorisatie.
     * @return de soort element autorisatie van ElementWaarde
     */
    public SoortElementAutorisatie getSoortAutorisatie() {
        return soortAutorisatie;
    }

    /**
     * Geef de element naam.
     * @return de element naam van ElementWaarde
     */
    public String getElementNaam() {
        return elementNaam;
    }

    /**
     * Geef het volgnummer.
     * @return het volgnummer van ElementWaarde
     */

    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Geef het historie patroon.
     * @return het historie patroon van ElementWaarde
     */
    public HistoriePatroon getHistoriePatroon() {
        return historiePatroon;
    }

    /**
     * Geef het element id van het type.
     * @return het element id van het type van ElementWaarde
     */

    public Integer getType() {
        return type;
    }

    /**
     * Geef het element id van het type.
     * @return het element id van het type van ElementWaarde
     */

    public ElementBasisType getBasisType() {
        return basisType;
    }

    /**
     * Geeft de naam alias van het element.
     * @return de naam alias van het element
     */
    public String getNaamAlias() {
        return naamAlias;
    }

    /**
     * Geeft de alias van het type.
     * @return de alias van het type
     */
    public Integer getTypeAlias() {
        return typeAlias;
    }

    /**
     * Geeft de minimumlengte van de waarde.
     * @return de minimumlengte van de waarde
     */
    public Integer getMinimumlengte() {
        return minimumlengte;
    }

    /**
     * Geeft de maximumlengte van de waarde.
     * @return de maximumlengte van de waarde
     */
    public Integer getMaximumlengte() {
        return maximumlengte;
    }

    /**
     * Geeft de inverse associatie code van het element.
     * @return de inverse associatie code van het element.
     */
    public String getInverseassociatieIdentcode() {
        return inverseassociatieIdentcode;
    }

    /**
     * Geeft het id van het object.
     * @return het id van het object.
     */
    public Integer getObjecttype() {
        return objecttype;
    }

    /**
     * Geeft de  VerantwoordingCategorie van het element (indien van type groep).
     * @return de verantwoordingcategorie.
     */
    public VerantwoordingCategorie getVerantwoordingcategorie() {
        return verantwoordingcategorie;
    }

    /**
     * De naam van het element in bericht.
     * @return naam van het element in bericht
     */
    public String getXmlNaam() {
        return xmlNaam;
    }

    /**
     * De datum aanvang geldigheid van het element.
     * @return de datum aanvang geldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * De datum einde geldigheid van het element.
     * @return de datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * De database identifier van het element.
     * @return de database identifier
     */
    public String getIdentdb() {
        return identdb;
    }

    /**
     * De database identifier van de his tabel van het element.
     * @return de database identifier van de his tabel
     */
    public String getHisidentdb() {
        return hisidentdb;
    }

    /**
     * Id van het tabel element.
     * @return id van tabel element.
     */
    public Integer getTabel() {
        return tabel;
    }

    /**
     * Id van het his_tabel element.
     * @return id van his_tabel element.
     */
    public Integer getHistabel() {
        return histabel;
    }

    /**
     * Het database schema waar dit element opgeslagen wordt
     * @return naam van database schema.
     */
    public String getIdentdbschema() {
        return identdbschema;
    }

    /**
     * Het datatypeaanduiding van de databasekolom
     * @return datatypeaanduiding naam
     */
    public DatabaseType getTypeidentdb() {
        return typeidentdb;
    }

    /**
     * Geeft indicatie of het element in het bericht opgenomen wordt.
     * @return boolean indicatie of het element in bericht staat
     */
    public boolean isInBericht() {
        return inBericht;
    }

    /**
     * Geeft het soort inhoud ?
     * @return het soort inhoud
     */
    public String getSoortInhoud() {
        return soortInhoud;
    }

    /**
     * Geeft de sorteervolgorde.
     * @return de sorteervolgorde
     */
    public Integer getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * Geeft het elementId van het actueel/geldig attribuut.
     * @return een elementId
     */
    public Integer getActueelGeldigAttribuut() {
        return actueelGeldigAttribuut;
    }

    /**
     * Geeft de waarde van identiteitExpressie.
     * @return identiteitExpressie
     */
    public String getIdentiteitExpressie() {
        return identiteitExpressie;
    }

    /**
     * @param soortBericht een {@link SoortBericht}
     * @return indicatie of dit element voorkomt in het gegeven bericht
     */
    public boolean inBericht(SoortBericht soortBericht) {
        final EnumSet<SoortBericht> soortBerichtenSet = ELEMENT_SOORTBERICHT_MAP.get(getId());
        return soortBerichtenSet != null && soortBerichtenSet.contains(soortBericht);
    }

    private static void vulElementen() {
        final Properties elementProperties = leesProperties();
        final Enumeration<?> elementNamen = elementProperties.propertyNames();
        final Map<String, String[]> metVerwijzing = new HashMap<>();
        final Map<String, String[]> zonderVerwijzing = new HashMap<>();

        while (elementNamen.hasMoreElements()) {
            final String elementNaam = (String) elementNamen.nextElement();
            final String[] elementPropertyWaarde = parsePropertyWaarde(elementProperties.getProperty(elementNaam));
            final String groepVerwijzing = elementPropertyWaarde[PROPERTY_GROEP];
            if (isLeeg(groepVerwijzing)) {
                zonderVerwijzing.put(elementNaam, elementPropertyWaarde);
            } else {
                metVerwijzing.put(elementNaam, elementPropertyWaarde);
            }
        }
        vulElementen(zonderVerwijzing);
        vulElementen(metVerwijzing);
    }

    private static boolean isLeeg(final String waarde) {
        return waarde == null || "".equals(waarde);
    }

    private static void vulElementen(final Map<String, String[]> propertyWaardeMap) {
        for (final Map.Entry<String, String[]> propertyWaardeEntry : propertyWaardeMap.entrySet()) {
            final ElementWaarde elementWaarde = maakElementWaarde(propertyWaardeEntry.getKey(), propertyWaardeEntry.getValue());
            ELEMENT_NAAM_MAP.put(elementWaarde.getNaam(), elementWaarde);
            ELEMENT_ID_MAP.put(elementWaarde.getId(), elementWaarde);
        }
    }

    private static ElementWaarde maakElementWaarde(final String naam, final String[] elementPropertyWaarde) {
        final int id = Integer.parseInt(elementPropertyWaarde[PROPERTY_ID]);
        final SoortElement soort = SoortElement.valueOf(elementPropertyWaarde[PROPERTY_SOORT]);
        final ElementWaarde groep;
        final SoortElementAutorisatie soortAutorisatie;
        if (!isLeeg(elementPropertyWaarde[PROPERTY_GROEP])) {
            if (!ELEMENT_NAAM_MAP.containsKey(elementPropertyWaarde[PROPERTY_GROEP])) {
                throw new IllegalArgumentException("Er kon geen groep element gevonden worden met naam: " + elementPropertyWaarde[PROPERTY_GROEP]);
            }
            groep = ELEMENT_NAAM_MAP.get(elementPropertyWaarde[PROPERTY_GROEP]);
            soortAutorisatie =
                    isLeeg(elementPropertyWaarde[PROPERTY_SOORT_AUTORISATIE]) ? null
                            : SoortElementAutorisatie.valueOf(
                                    elementPropertyWaarde[PROPERTY_SOORT_AUTORISATIE]);
        } else {
            groep = null;
            soortAutorisatie = null;
        }
        final String elementNaam = elementPropertyWaarde[PROPERTY_ELEMENTNAAM];
        final Integer volgnummer = toInteger(elementPropertyWaarde[PROPERTY_VOLGNUMMER]);
        final HistoriePatroon historiePatroon = toHistoriePatroon(elementPropertyWaarde[PROPERTY_HISTORIEPATROON]);
        final Integer type = toInteger(elementPropertyWaarde[PROPERTY_TYPE]);
        final ElementBasisType basisType =
                toElementBasisType(elementPropertyWaarde[PROPERTY_BASISTYPE]);

        if (naam == null || soort == null) {
            throw new NullPointerException("naam en soort zijn verplicht");
        }

        final ElementWaarde waarde = new ElementWaarde();
        waarde.naam = naam;
        waarde.id = id;
        waarde.soort = soort;
        waarde.groep = groep;
        waarde.soortAutorisatie = soortAutorisatie;
        waarde.elementNaam = elementNaam;
        waarde.volgnummer = volgnummer;
        waarde.historiePatroon = historiePatroon;
        waarde.type = type;
        waarde.basisType = basisType;
        waarde.naamAlias = elementPropertyWaarde[PROPERTY_ALIAS];
        waarde.typeAlias = toInteger(elementPropertyWaarde[PROPERTY_ALIASVAN]);
        waarde.minimumlengte = toInteger(elementPropertyWaarde[PROPERTY_MINLENGTE]);
        waarde.maximumlengte = toInteger(elementPropertyWaarde[PROPERTY_MAXLENGTE]);
        waarde.inverseassociatieIdentcode = elementPropertyWaarde[PROPERTY_INVERSECODE];
        waarde.objecttype = toInteger(elementPropertyWaarde[PROPERTY_OBJECT]);
        waarde.verantwoordingcategorie = isLeeg(elementPropertyWaarde[PROPERTY_VERANTWOORDING])
                ? null : VerantwoordingCategorie.parseCode(elementPropertyWaarde[PROPERTY_VERANTWOORDING]);
        waarde.xmlNaam = elementPropertyWaarde[PROPERTY_IDENTXSD];
        waarde.datumAanvangGeldigheid = toInteger(elementPropertyWaarde[PROPERTY_DATUMAANVANG]);
        waarde.datumEindeGeldigheid = toInteger(elementPropertyWaarde[PROPERTY_DATUMEINDE]);
        waarde.identdb = elementPropertyWaarde[PROPERTY_IDENTDB];
        waarde.hisidentdb = elementPropertyWaarde[PROPERTY_HISIDENTDB];
        waarde.tabel = toInteger(elementPropertyWaarde[PROPERTY_TABEL]);
        waarde.histabel = toInteger(elementPropertyWaarde[PROPERTY_HISTABEL]);
        waarde.identdbschema = elementPropertyWaarde[PROPERTY_IDENTDBSCHEMA];
        waarde.typeidentdb = toDatabaseType(elementPropertyWaarde);
        waarde.inBericht = !isLeeg(elementPropertyWaarde[PROPERTY_INBERICHT])
                && Boolean.TRUE.toString().equalsIgnoreCase(elementPropertyWaarde[PROPERTY_INBERICHT]);
        waarde.soortInhoud = elementPropertyWaarde[PROPERTY_SOORTINHOUD];
        waarde.sorteervolgorde = toInteger(elementPropertyWaarde[PROPERTY_SORTEERVOLGORDE]);
        waarde.actueelGeldigAttribuut = toInteger(elementPropertyWaarde[PROPERTY_ACTUEEL_GELDIG_ATTRIBUUT]);
        waarde.identiteitExpressie = elementPropertyWaarde[PROPERTY_IDENTEXPRESSIE];
        return waarde;
    }

    private static DatabaseType toDatabaseType(final String[] elementPropertyWaarde) {
        return isLeeg(elementPropertyWaarde[PROPERTY_TYPEIDENTDB]) ? null : DatabaseType.parseCode(elementPropertyWaarde[PROPERTY_TYPEIDENTDB]);
    }

    private static ElementBasisType toElementBasisType(final String waarde) {
        return isLeeg(waarde) ? null : ElementBasisType.valueOf(waarde);
    }

    private static HistoriePatroon toHistoriePatroon(final String waarde) {
        return isLeeg(waarde) ? null : HistoriePatroon.valueOf(waarde);
    }

    private static Integer toInteger(final String waarde) {
        return isLeeg(waarde) ? null : Integer.valueOf(waarde);
    }

    private static String[] parsePropertyWaarde(final String propertyWaarde) {
        return Arrays.copyOf(propertyWaarde.split(","), AANTAL_KOLOMMEN);
    }

    private static Properties leesProperties() {
        final Properties elementProperties = new Properties();
        try (InputStream propertiesStream = ElementWaarde.class.getResourceAsStream("ElementWaarde.properties")) {
            elementProperties.load(propertiesStream);
            return elementProperties;
        } catch (final IOException e) {
            throw new IllegalStateException("De element properties konden niet worden geladen.", e);
        }
    }

    private static Map<Integer, EnumSet<SoortBericht>> maakBerichtElementMap() {
        final Map<Integer, EnumSet<SoortBericht>> berichtElementMap = new HashMap<>();
        try (InputStream propertiesStream = ElementWaarde.class.getResourceAsStream("BerichtElement.properties")) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(propertiesStream, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                if (!StringUtils.isEmpty(line)) {
                    addSoortBericht(berichtElementMap, line);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("De BerichtElement.properties konden niet worden geladen.", e);
        }
        return berichtElementMap;
    }

    private static void addSoortBericht(final Map<Integer, EnumSet<SoortBericht>> berichtElementMap, final String regel) {
        final String[] split = StringUtils.split(regel, ",");
        final Integer elementId = Integer.valueOf(split[0]);
        final SoortBericht soortBericht = SoortBericht.parseId(Integer.valueOf(split[1]));
        berichtElementMap.compute(elementId, (integer, soortBerichts) -> {
            if (soortBerichts == null) {
                return EnumSet.of(soortBericht);
            } else {
                soortBerichts.add(soortBericht);
                return soortBerichts;
            }
        });
    }

    /**
     * Geef de ElementWaarde horende bij het gegeven id.
     * @param id id
     * @return de element waarde
     */
    public static ElementWaarde getInstance(final int id) {
        return ElementWaarde.ELEMENT_ID_MAP.get(id);
    }

    /**
     * Geef de ElementWaarde horende bij het gegeven id.
     * @param naam naam
     * @return de element waarde
     */
    public static ElementWaarde getInstance(final String naam) {
        return ElementWaarde.ELEMENT_NAAM_MAP.get(naam);
    }

    /**
     * Geef de waarde van ids van ElementWaarde.
     * @return de waarde van ids van ElementWaarde
     */
    public static Set<Integer> getIds() {
        return Collections.unmodifiableSet(ElementWaarde.ELEMENT_ID_MAP.keySet());
    }

    /**
     * Geef de waarde van namen van ElementWaarde.
     * @return de waarde van namen van ElementWaarde
     */
    public static Set<String> getNamen() {
        return Collections.unmodifiableSet(ElementWaarde.ELEMENT_NAAM_MAP.keySet());
    }

    @Override
    public String toString() {
        return "ElementWaarde{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                '}';
    }

    /**
     * Geef een detail weergave van de element waarde.
     * @return detail omschrijving
     */
    public String toDetailsString() {
        return "ElementWaarde{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", soort=" + soort +
                ", soortAutorisatie=" + soortAutorisatie +
                ", groep=" + groep +
                ", elementNaam='" + elementNaam + '\'' +
                ", volgnummer=" + volgnummer +
                ", historiePatroon=" + historiePatroon +
                ", type=" + type +
                ", basisType=" + basisType +
                ", naamAlias='" + naamAlias + '\'' +
                ", typeAlias=" + typeAlias +
                ", minimumlengte=" + minimumlengte +
                ", maximumlengte=" + maximumlengte +
                ", inverseassociatieIdentcode='" + inverseassociatieIdentcode + '\'' +
                ", objecttype=" + objecttype +
                ", verantwoordingcategorie=" + verantwoordingcategorie +
                ", xmlNaam='" + xmlNaam + '\'' +
                ", datumAanvangGeldigheid=" + datumAanvangGeldigheid +
                ", datumEindeGeldigheid=" + datumEindeGeldigheid +
                ", identdb='" + identdb + '\'' +
                ", hisidentdb='" + hisidentdb + '\'' +
                ", tabel=" + tabel +
                ", histabel=" + histabel +
                ", identdbschema='" + identdbschema + '\'' +
                ", typeidentdb=" + typeidentdb +
                ", inBericht=" + inBericht +
                ", soortInhoud='" + soortInhoud + '\'' +
                ", sorteervolgorde=" + sorteervolgorde +
                ", actueelGeldigAttribuut=" + actueelGeldigAttribuut +
                ", identiteitExpressie=" + identiteitExpressie +
                '}';
    }
}
