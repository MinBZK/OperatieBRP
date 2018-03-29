/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.conversie.model.Sortable;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een BRP actie.
 *
 * De id (technische sleutel) wordt hier tweeledig gebruikt. Bij de conversie van Lo3 -&lt; BRP wordt de ID gebuikt een
 * een enkele logische actie te identificeren (die technisch meerdere keren gebruikt kan worden; lees meerdere
 * instanties kan hebben). De Sync DAL zal dus een tijdelijk vertaling van conversie-actie-id naar echte-actie-id moeten
 * hebben. Bij de conversie van BRP -&lt; Lo3 wordt het id gebruikt om te bepalen welke wijzigingen door dezelfde actie
 * zijn gedaan.
 *
 * Deze klass is immutable en thread-safe.
 */
public final class BrpActie implements Sortable{

    // Aktie
    private final Long id;
    private final BrpSoortActieCode soortActieCode;
    private final BrpPartijCode partijCode;
    private final BrpDatumTijd datumTijdRegistratie;
    private final BrpDatum datumOntlening;
    // Attribuut wat gebruikt kan worden voor de sortering indien datumTijdRegistratie gelijk is
    private final int sortering;

    private final List<BrpActieBron> actieBronnen;
    private final Lo3Herkomst lo3Herkomst;

    // Attribuut wat gebruikt wordt om aan te geven dat deze actie ook daadwerkelijk wordt gebruikt tijdens het aanmaken van een BrpGroep.
    private boolean gebruikt;

    // Attribuut wat gebruikt wordt om de actuele rij te bepalen tijdens terugconversie en dan wss alleen bij Nationaliteit icm einde bijhouding en daarna
    // vervallen.
    private boolean wasActieVerval = false;

    /**
     * Maakt een BrpActie object.
     * @param identificatie technisch id, mag niet null zijn
     * @param soortActie soort actie
     * @param partij partij, mag niet null zijn
     * @param registratieDatumTijd datum tijd registratie
     * @param ontleningDatum datum ontlening
     * @param actieBronnenLijst de actieBronnen
     * @param actieSortering een int die de sortering bepaald bij het serializeren van een BRPActie
     * @param herkomstLo3 de herkomst van de BRP actie
     * @throws NullPointerException als id null is
     */
    public BrpActie(
            @Attribute(name = "id") final Long identificatie,
            @Element(name = "soortActieCode") final BrpSoortActieCode soortActie,
            @Element(name = "partijCode") final BrpPartijCode partij,
            @Element(name = "datumTijdRegistratie") final BrpDatumTijd registratieDatumTijd,
            @Element(name = "datumOntlening") final BrpDatum ontleningDatum,
            @ElementList(name = "actieBronnen", type = BrpActieBron.class) final List<BrpActieBron> actieBronnenLijst,
            @Element(name = "sortering") final int actieSortering,
            @Element(name = "lo3Herkomst") final Lo3Herkomst herkomstLo3) {
        if (identificatie == null) {
            throw new NullPointerException("id mag niet null zijn");
        }
        if (partij == null) {
            throw new NullPointerException("partijCode mag niet null zijn");
        }
        id = identificatie;
        soortActieCode = soortActie;
        partijCode = partij;
        datumTijdRegistratie = registratieDatumTijd;
        datumOntlening = ontleningDatum;
        actieBronnen = kopieerActieBronEnZetActieInhoudInDocumenten(actieBronnenLijst);
        sortering = actieSortering;
        lo3Herkomst = herkomstLo3;
        gebruikt = false;
    }

    /**
     * Deze methode zet de terugverwijzing naar deze actie in de documenten binnen de actieBronnen. Dit is een grote
     * kopieer-actie vanwege het immutable model.
     * @param actieBronnenParam de actieBronnen
     * @return een kopie van de meegegeven actieBronnen met actie inhoud gezet op alle documenten
     */
    private List<BrpActieBron> kopieerActieBronEnZetActieInhoudInDocumenten(final List<BrpActieBron> actieBronnenParam) {
        List<BrpActieBron> result = null;

        if (actieBronnenParam != null) {
            result = new LinkedList<>();
            for (final BrpActieBron actieBron : actieBronnenParam) {
                final List<BrpGroep<BrpDocumentInhoud>> groepen = koppelActieAanDocument(actieBron);
                result.add(new BrpActieBron(groepen == null ? null : new BrpStapel<>(groepen), actieBron.getRechtsgrondOmschrijving()));
            }
        }
        return result;
    }

    private List<BrpGroep<BrpDocumentInhoud>> koppelActieAanDocument(final BrpActieBron actieBron) {
        List<BrpGroep<BrpDocumentInhoud>> groepen = null;
        if (actieBron.getDocumentStapel() != null) {
            groepen = new ArrayList<>();
            for (final BrpGroep<BrpDocumentInhoud> brpGroep : actieBron.getDocumentStapel()) {
                groepen.add(
                        new BrpGroep<>(brpGroep.getInhoud(), brpGroep.getHistorie(), this, brpGroep.getActieVerval(), brpGroep.getActieGeldigheid()));
            }
        }
        return groepen;
    }

    /**
     * Geef de waarde van id.
     * @return het id
     */
    @Attribute(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Geef de waarde van soort actie code.
     * @return de soortActieCode
     */
    @Element(name = "soortActieCode")
    public BrpSoortActieCode getSoortActieCode() {
        return soortActieCode;
    }

    /**
     * Geef de waarde van partij code.
     * @return de partijCode
     */
    @Element(name = "partijCode")
    public BrpPartijCode getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van datum tijd registratie.
     * @return de datumTijdRegistratie
     */
    @Element(name = "datumTijdRegistratie")
    public BrpDatumTijd getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Geef de waarde van datum ontlening.
     * @return de datum ontlening
     */
    @Element(name = "datumOntlening")
    public BrpDatum getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * Geef de waarde van sortering.
     * @return de sortering
     */
    @Element(name = "sortering")
    public int getSortering() {
        return sortering;
    }

    /**
     * Geef de waarde van actie bronnen.
     * @return de actieBronnen
     */
    @ElementList(name = "actieBronnen", type = BrpActieBron.class)
    public List<BrpActieBron> getActieBronnen() {
        return actieBronnen == null ? null : new LinkedList<>(actieBronnen);
    }

    /**
     * Geef de waarde van document stapels via actie bron.
     * @return de document stapels uit de actieBronnen
     */
    public List<BrpStapel<BrpDocumentInhoud>> getDocumentStapelsViaActieBron() {
        final List<BrpStapel<BrpDocumentInhoud>> result = new ArrayList<>();

        if (actieBronnen != null) {
            for (final BrpActieBron actieBron : actieBronnen) {
                if (actieBron.getDocumentStapel() != null) {
                    result.add(actieBron.getDocumentStapel());
                }
            }
        }

        return result;
    }

    /**
     * Geef de waarde van lo3 herkomst.
     * @return de herkomst van deze brp actie
     */
    @Element(name = "lo3Herkomst")
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * Bij deze equals en hashCode wordt de ID niet meegenomen. Deze vergelijkers geven hierdoor het verwachte resultaat
     * wanneer bijvoorbeeld twee persoonslijsten inhoudelijk worden vergeleken (origineel en terugconversie), maar niet
     * wanneer je wilt weten of twee acties exact hetzelfde zijn. (Er kunnen meerdere acties met gelijke data voorkomen,
     * maar met een verschillend ID.) In deze gevallen moeten de acties worden vergeleken op ID.
     * @param other Het object waarmee vergeleken wordt
     * @return Of de BrpActie *inhoudelijk* gelijk is aan other
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof BrpActie && vergelijk((BrpActie) other, true);
    }

    /**
     * Controleert de inhoud van deze actie. ID wordt niet gecontroleerd (zie ook {@link #equals(Object)}).
     * @param other het object waarmee vergeleken wordt
     * @param checkDatumTijdRegistratie true als datum/tijd registratie ook vergeleken moet worden
     * @return true als de acties overeenkomen
     */
    public boolean vergelijk(final BrpActie other, final boolean checkDatumTijdRegistratie) {
        final EqualsBuilder equalsBuilder =
                new EqualsBuilder().append(soortActieCode, other.soortActieCode)
                        .append(partijCode, other.partijCode)
                        .append(datumOntlening, other.datumOntlening)
                        .append(actieBronnen, other.actieBronnen);
        if (checkDatumTijdRegistratie) {
            equalsBuilder.append(datumTijdRegistratie, other.datumTijdRegistratie);
        }
        return equalsBuilder.isEquals();
    }

    /**
     * Geeft aan of de acties hetzelfde zijn op basis van het ID.
     * @param actie de andere actie
     * @return true als id gelijk is aan id van meegegeven actie
     */
    public boolean heeftActieZelfdeId(final BrpActie actie) {
        return id == actie.getId();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortActieCode)
                .append(partijCode)
                .append(datumTijdRegistratie)
                .append(datumOntlening)
                .append(actieBronnen)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortActieCode", soortActieCode)
                .append("partijCode", partijCode)
                .append("datumTijdRegistratie", datumTijdRegistratie)
                .append("datumOntlening", datumOntlening)
                .append("sortering", sortering)
                .append("actieBronnen", actieBronnen)
                .append("lo3Herkomst", lo3Herkomst)
                .toString();
    }

    /**
     * Geef de conversie actie.
     * @return true als deze actie door de conversiesoftware is aan gemaakt.
     */
    public boolean isConversieActie() {
        return BrpSoortActieCode.CONVERSIE_GBA.equals(soortActieCode)
                || BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(soortActieCode)
                || BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST.equals(soortActieCode);
    }

    /**
     * Controleert of de ID's van de {@link BrpActie} gelijk zijn.
     * @param andereActie een andere actie
     * @return true als de ID's gelijk zijn. Als de andereActie null is of als de ID's niet gelijk zijn dan false.
     */
    public boolean equalsId(final BrpActie andereActie) {
        return andereActie != null && Objects.equals(id, andereActie.id);
    }

    /**
     * Geeft aan of deze actie tijdens conversie is gebruikt.
     * @return true als de actie is gebruikt tijdens conversie, anders false.
     */
    public boolean isGebruikt() {
        return gebruikt;
    }

    /**
     * Markeer de actie als wordt gebruikt tijdens conversie.
     */
    public void setWordtGebruikt() {
        gebruikt = true;
    }

    /**
     * Markeer de actie als was gebruikt als Actie Verval.
     */
    public void setWasActieVerval() {
        wasActieVerval = true;
    }

    /**
     * Geeft aan of deze actie als actie verval is gebruikt.
     * @return true als deze actie oorspronkelijk een actie verval was.
     */
    public boolean wasActieVerval() {
        return wasActieVerval;
    }

    @Override
    public void sorteer() {
        if (actieBronnen == null || actieBronnen.isEmpty()) {
            return;
        }
        actieBronnen.sort((o1, o2) -> {
            int result = SortUtil.compareNulls(o1.getRechtsgrondOmschrijving(), o2.getRechtsgrondOmschrijving());
            if (result == 0) {
                if (o1.getDocumentStapel() == null) {
                    result = o2.getDocumentStapel() == null ? 0 : -1;
                } else {
                    if (o2.getDocumentStapel() == null) {
                        result = 1;
                    } else {
                        result = Integer.compare(o1.getDocumentStapel().hashCode(), o2.getDocumentStapel().hashCode());
                    }
                }
            }
            return result;
        });
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Long id;
        private BrpSoortActieCode soortActieCode;
        private BrpPartijCode partijCode;
        private BrpDatumTijd datumTijdRegistratie;
        private BrpDatum datumOntlening;
        private int sortering;
        private List<BrpActieBron> actieBronnen;
        private Lo3Herkomst lo3Herkomst;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            // Lege builder
        }

        /**
         * Maak een initieel gevulde builder.
         * @param inhoud initiele vulling
         */
        public Builder(final BrpActie inhoud) {
            id = inhoud.id;
            soortActieCode = inhoud.soortActieCode;
            partijCode = inhoud.partijCode;
            datumTijdRegistratie = inhoud.datumTijdRegistratie;
            datumOntlening = inhoud.datumOntlening;
            sortering = inhoud.sortering;
            actieBronnen = inhoud.actieBronnen;
            lo3Herkomst = inhoud.lo3Herkomst;
        }

        /**
         * @return inhoud
         */
        public BrpActie build() {
            return new BrpActie(id, soortActieCode, partijCode, datumTijdRegistratie, datumOntlening, actieBronnen, sortering, lo3Herkomst);
        }

        /**
         * @param param the id to set
         * @return de builder
         */
        public Builder id(final Long param) {
            id = param;
            return this;
        }

        /**
         * @param param the soortActieCode to set
         * @return de builder
         */
        public Builder soortActieCode(final BrpSoortActieCode param) {
            soortActieCode = param;
            return this;
        }

        /**
         * @param param the partijCode to set
         * @return de builder
         */
        public Builder partijCode(final BrpPartijCode param) {
            partijCode = param;
            return this;
        }

        /**
         * @param param the datumTijdRegistratie to set
         * @return de builder
         */
        public Builder datumTijdRegistratie(final BrpDatumTijd param) {
            datumTijdRegistratie = param;
            return this;
        }

        /**
         * @param param the datumOntlening to set
         * @return de builder
         */
        public Builder datumOntlening(final BrpDatum param) {
            datumOntlening = param;
            return this;
        }

        /**
         * @param param the actieBronnen to set
         * @return de builder
         */
        public Builder actieBronnen(final List<BrpActieBron> param) {
            actieBronnen = param;
            return this;
        }

        /**
         * @param param the sortering to set
         * @return de builder
         */
        public Builder sortering(final int param) {
            sortering = param;
            return this;
        }

        /**
         * @param param the lo3Herkomst to set
         * @return de builder
         */
        public Builder lo3Herkomst(final Lo3Herkomst param) {
            lo3Herkomst = param;
            return this;
        }
    }
}
