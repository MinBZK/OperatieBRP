/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.AbstractBrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.AdellijkeTitelCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.AdellijkeTitelCodeS;
import nl.moderniseringgba.isc.esb.message.brp.generated.Burgerservicenummer;
import nl.moderniseringgba.isc.esb.message.brp.generated.ContainerPersoonGeslachtsnaamcomponenten;
import nl.moderniseringgba.isc.esb.message.brp.generated.ContainerPersoonVoornamen;
import nl.moderniseringgba.isc.esb.message.brp.generated.ContainerRelatieBetrokkenheden;
import nl.moderniseringgba.isc.esb.message.brp.generated.Datum;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsaanduidingCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsaanduidingCodeS;
import nl.moderniseringgba.isc.esb.message.brp.generated.Geslachtsnaam;
import nl.moderniseringgba.isc.esb.message.brp.generated.Geslachtsnaamcomponent;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepBetrokkenheidOuderschap;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonGeboorte;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonGeslachtsaanduiding;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonIdentificatienummers;
import nl.moderniseringgba.isc.esb.message.brp.generated.GroepPersoonSamengesteldeNaam;
import nl.moderniseringgba.isc.esb.message.brp.generated.Ja;
import nl.moderniseringgba.isc.esb.message.brp.generated.JaS;
import nl.moderniseringgba.isc.esb.message.brp.generated.Landcode;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoon;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypePersoonVoornaam;
import nl.moderniseringgba.isc.esb.message.brp.generated.PredikaatCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.PredikaatCodeS;
import nl.moderniseringgba.isc.esb.message.brp.generated.Scheidingsteken;
import nl.moderniseringgba.isc.esb.message.brp.generated.ViewFamilierechtelijkeBetrekkingAfstamming;
import nl.moderniseringgba.isc.esb.message.brp.generated.ViewKind;
import nl.moderniseringgba.isc.esb.message.brp.generated.ViewOuder;
import nl.moderniseringgba.isc.esb.message.brp.generated.Volgnummer;
import nl.moderniseringgba.isc.esb.message.brp.generated.Voornaam;
import nl.moderniseringgba.isc.esb.message.brp.generated.Voornamen;
import nl.moderniseringgba.isc.esb.message.brp.generated.Voorvoegsel;
import nl.moderniseringgba.isc.esb.message.brp.generated.Woonplaatscode;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * MvGeboorteVerzoekbericht.
 */
// CHECKSTYLE:OFF Gezien de hoeveelheid aan object die we gebruiken om het MvGeboorteVerzoekType te kunnen vullen, kan
// er niet geconformeerd worden aan de 'class fan out <= 20' controle.
public final class MvGeboorteVerzoekBericht extends AbstractBrpBericht implements BrpBericht, Serializable {
    // CHECKSTYLE:ON
    private static final long serialVersionUID = 1L;

    private static final String KIND_PERSOONSLIJST = "kindPersoonslijst";
    private static final String MOEDER_PERSOONSLIJST = "moederPersoonslijst";

    private transient BrpPersoonslijst kindPersoonslijst;
    private transient BrpPersoonslijst moederPersoonslijst;

    private MvGeboorteVerzoekType mvGeboorteVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public MvGeboorteVerzoekBericht() {
        mvGeboorteVerzoekType = new MvGeboorteVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param mvGeboorteVerzoekType
     *            het mvGeboorteVerzoek type
     */
    public MvGeboorteVerzoekBericht(final MvGeboorteVerzoekType mvGeboorteVerzoekType) {
        this.mvGeboorteVerzoekType = mvGeboorteVerzoekType;
    }

    /**
     * Convenient constructor.
     * 
     * @param kindPersoonsLijst
     *            De BRP PL van het kind.
     * @param moederPersoonslijst
     *            De BRP PL van de moeder.
     */
    public MvGeboorteVerzoekBericht(
            final BrpPersoonslijst kindPersoonsLijst,
            final BrpPersoonslijst moederPersoonslijst) {
        this();
        setKindPersoonslijst(kindPersoonsLijst);
        setMoederPersoonslijst(moederPersoonslijst);
    }

    /**
     * Convenient constructor.
     * 
     * @param mvGeboorteVerzoekType
     *            het mvGeboorteVerzoek type
     * @param kindPersoonsLijst
     *            De BRP PL van het kind.
     * @param moederPersoonslijst
     *            De BRP PL van de moeder.
     */
    public MvGeboorteVerzoekBericht(
            final MvGeboorteVerzoekType mvGeboorteVerzoekType,
            final BrpPersoonslijst kindPersoonsLijst,
            final BrpPersoonslijst moederPersoonslijst) {
        this(mvGeboorteVerzoekType);
        setKindPersoonslijst(kindPersoonsLijst);
        setMoederPersoonslijst(moederPersoonslijst);
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de persoonlijst van het kind terug.
     * 
     * @return De persoonlijst van het kind.
     */
    public BrpPersoonslijst getKindPersoonslijst() {
        return kindPersoonslijst;
    }

    /**
     * Zet de persoonslijst van het kind.
     * 
     * @param kindPersoonslijst
     *            De persoonslijst van het kind.
     */
    public void setKindPersoonslijst(final BrpPersoonslijst kindPersoonslijst) {
        this.kindPersoonslijst = kindPersoonslijst;
    }

    /**
     * Geeft de persoonslijst van de moeder terug.
     * 
     * @return De persoonslijst van de moeder.
     */
    public BrpPersoonslijst getMoederPersoonslijst() {
        return moederPersoonslijst;
    }

    /**
     * Zet de persoonslijst van de moeder.
     * 
     * @param moederPersoonslijst
     *            De persoonslijst van de moeder.
     */
    public void setMoederPersoonslijst(final BrpPersoonslijst moederPersoonslijst) {
        this.moederPersoonslijst = moederPersoonslijst;
    }

    @Override
    public String getBerichtType() {
        return "MvGeboorteVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(mvGeboorteVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        mvGeboorteVerzoekType.setIscGemeenten(setBrpGemeente(mvGeboorteVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(mvGeboorteVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        mvGeboorteVerzoekType.setIscGemeenten(setLo3Gemeente(mvGeboorteVerzoekType.getIscGemeenten(), gemeente));
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        if (kindPersoonslijst != null) {
            vulMvGeboorteVerzoekAanOpBasisVanKindPersoonslijst();
        }

        if (moederPersoonslijst != null) {
            vulMvGeboorteVerzoekAanOpBasisVanMoederPersoonslijst();
        }

        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createMvGeboorteVerzoek(mvGeboorteVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            mvGeboorteVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, MvGeboorteVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een GeboorteVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {

        if (is.available() > 0) {
            kindPersoonslijst = PersoonslijstDecoder.decodeBrpPersoonslijst(is);
        }

        // always perform the default de-serialization first
        is.defaultReadObject();

        if (is.available() > 0) {
            moederPersoonslijst = PersoonslijstDecoder.decodeBrpPersoonslijst(is);
        }
    }

    private void writeObject(final ObjectOutputStream os) throws IOException {
        if (kindPersoonslijst != null) {
            PersoonslijstEncoder.encodePersoonslijst(kindPersoonslijst, os);
        }

        // perform the default serialization for all non-transient, non-static fields
        os.defaultWriteObject();

        if (moederPersoonslijst != null) {
            PersoonslijstEncoder.encodePersoonslijst(moederPersoonslijst, os);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(KIND_PERSOONSLIJST, kindPersoonslijst).append(MOEDER_PERSOONSLIJST, moederPersoonslijst)
                .toString();
    }

    /**
     * Vult in het MvGeboorteVerzoek de gegevens van het kind in.
     * 
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private void vulMvGeboorteVerzoekAanOpBasisVanKindPersoonslijst() throws BerichtInhoudException {

        try {

            final ViewFamilierechtelijkeBetrekkingAfstamming familierechtelijkeBetrekkingen =
                    new ViewFamilierechtelijkeBetrekkingAfstamming();
            final ContainerRelatieBetrokkenheden relatieBetrokkenheden = new ContainerRelatieBetrokkenheden();

            /**
             * Gebruikte objecten vanuit de persoonslijst:
             */
            // Geboorteinhoud
            final BrpGeboorteInhoud kindGeboorteInhoud = kindPersoonslijst.getGeboorteStapel().get(0).getInhoud();
            // Geslachtsnaamcomponentinhoud
            final BrpGeslachtsnaamcomponentInhoud kindGeslachtnaamcomponentInhoud =
                    kindPersoonslijst.getGeslachtsnaamcomponentStapels().get(0).getMeestRecenteElement().getInhoud();
            // Voornaaminhoud
            final BrpVoornaamInhoud kindVoornaamInhoud =
                    kindPersoonslijst.getVoornaamStapels().get(0).getMeestRecenteElement().getInhoud();
            // Geslachtsaanduidinginhoud
            final BrpGeslachtsaanduidingInhoud kindGeslachtsaanduidingInhoud =
                    kindPersoonslijst.getGeslachtsaanduidingStapel().get(0).getInhoud();

            /**
             * Doel persoonobject
             */
            final ObjecttypePersoon kindPersoon = new ObjecttypePersoon();

            /**
             * Gebruikte objecten vanuit de persoonslijst:
             */
            // Groep geslachtsaanduiding
            final GroepPersoonGeslachtsaanduiding groepGeslachtsaanduiding =
                    maakEnVulGeslachtsaanduidingGroep(kindGeslachtsaanduidingInhoud);
            // Groep geboorte
            final GroepPersoonGeboorte groepGeboorte = maakEnVulGeboorteGroep(kindGeboorteInhoud);
            // Groep voornamen
            final ContainerPersoonVoornamen groepVoornamen = maakEnVulVoornamenGroep(kindVoornaamInhoud);
            // Groep geslachtsnaamcomponenten
            final ContainerPersoonGeslachtsnaamcomponenten groepGeslachtsnaamcomponenten =
                    maakEnVulGeslachtsnaamcomponentenGroep(kindGeslachtnaamcomponentInhoud);

            /**
             * Voeg groepen toe aan persoon object.
             */
            kindPersoon.getGeslachtsaanduiding().add(groepGeslachtsaanduiding);
            kindPersoon.getGeboorte().add(groepGeboorte);
            kindPersoon.setVoornamen(groepVoornamen);
            kindPersoon.setGeslachtsnaamcomponenten(groepGeslachtsnaamcomponenten);

            final ViewKind kind = new ViewKind();
            kind.setPersoon(kindPersoon);
            relatieBetrokkenheden.getKind().add(kind);
            familierechtelijkeBetrekkingen.setBetrokkenheden(relatieBetrokkenheden);

            mvGeboorteVerzoekType.setFamilierechtelijkeBetrekking(familierechtelijkeBetrekkingen);
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException("Onvoldoende informatie in persoonslijst voor het vullen van de "
                    + "kindgegevens op het MvGeboorteBericht.", exception);
        }
    }

    /**
     * Vult in het MvGeboorteVerzoek de gegevens van de moeder in.
     * 
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private void vulMvGeboorteVerzoekAanOpBasisVanMoederPersoonslijst() throws BerichtInhoudException {

        try {
            // Controleer of de gegevens van het kind al zijn ingevuld.
            if (mvGeboorteVerzoekType.getFamilierechtelijkeBetrekking() == null
                    || mvGeboorteVerzoekType.getFamilierechtelijkeBetrekking().getBetrokkenheden() == null
                    || mvGeboorteVerzoekType.getFamilierechtelijkeBetrekking().getBetrokkenheden().getKind() == null) {
                return;
            }

            /**
             * Gebruikte objecten vanuit de persoonslijst:
             */
            // Geboorteinhoud
            final BrpGeboorteInhoud moederGeboorteInhoud = moederPersoonslijst.getGeboorteStapel().get(0).getInhoud();
            // Geslachtsnaamcomponentinhoud
            final BrpGeslachtsnaamcomponentInhoud moederGeslachtnaamcomponentInhoud =
                    moederPersoonslijst.getGeslachtsnaamcomponentStapels().get(0).getMeestRecenteElement()
                            .getInhoud();
            // Voornaaminhoud
            final BrpVoornaamInhoud moederVoornaamInhoud =
                    moederPersoonslijst.getVoornaamStapels().get(0).getMeestRecenteElement().getInhoud();
            // Geslachtsaanduidinginhoud
            final BrpGeslachtsaanduidingInhoud moederGeslachtsaanduidingInhoud =
                    moederPersoonslijst.getGeslachtsaanduidingStapel().get(0).getInhoud();
            // Identificatienummersinhoud
            final BrpIdentificatienummersInhoud moederIdentificatienummers =
                    moederPersoonslijst.getIdentificatienummerStapel().getMeestRecenteElement().getInhoud();

            /**
             * Doel persoonobject
             */
            final ObjecttypePersoon moederPersoon = new ObjecttypePersoon();

            /**
             * Gebruikte objecten vanuit de persoonslijst:
             */
            // Groep identificatienummers
            final GroepPersoonIdentificatienummers groepIdentificatieNummers =
                    maakEnVulIndentificatienummersGroep(moederIdentificatienummers);
            // Groep geslachtsaanduiding
            final GroepPersoonGeslachtsaanduiding groepGeslachtsaanduiding =
                    maakEnVulGeslachtsaanduidingGroep(moederGeslachtsaanduidingInhoud);
            // Groep samengesteldenaam
            final GroepPersoonSamengesteldeNaam groepSamengesteldeNaam =
                    maakEnVulSamengesteldeNaamGroep(moederGeslachtnaamcomponentInhoud, moederVoornaamInhoud);
            // Groep geboorte
            final GroepPersoonGeboorte groepGeboorte = maakEnVulGeboorteGroep(moederGeboorteInhoud);

            // Vul het persoon object met de groepen.
            moederPersoon.getIdentificatienummers().add(groepIdentificatieNummers);
            moederPersoon.getGeslachtsaanduiding().add(groepGeslachtsaanduiding);
            moederPersoon.getSamengesteldeNaam().add(groepSamengesteldeNaam);
            moederPersoon.getGeboorte().add(groepGeboorte);

            // Vul de groep ouderschap.
            final GroepBetrokkenheidOuderschap groepOuderschap = new GroepBetrokkenheidOuderschap();
            final Ja ja = new Ja();
            ja.setValue(JaS.J);
            groepOuderschap.setDatumAanvang(mvGeboorteVerzoekType.getFamilierechtelijkeBetrekking()
                    .getBetrokkenheden().getKind().get(0).getPersoon().getGeboorte().get(0).getDatum());
            groepOuderschap.setIndicatieAdresgevend(ja);

            // Zet de gevulde groepen op de ouder.
            final ViewOuder ouder = new ViewOuder();
            ouder.setPersoon(moederPersoon);
            ouder.getOuderschap().add(groepOuderschap);

            mvGeboorteVerzoekType.getFamilierechtelijkeBetrekking().getBetrokkenheden().getOuder().add(ouder);
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException("Onvoldoende informatie in persoonslijst voor het vullen "
                    + "de moedergegevens op het MvGeboorteBericht.", exception);
        }
    }

    /**
     * Maakt en vult een geslachtsaanduiding groep voor het MvGeboorteVerzoekType.
     * 
     * @param geslachtsaanduidingInhoud
     *            De inhoud (PL) waaruit de gegevens worden overgenomen.
     * @return De gevulde geslachtsaanduiding groep.
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private GroepPersoonGeslachtsaanduiding maakEnVulGeslachtsaanduidingGroep(
            final BrpGeslachtsaanduidingInhoud geslachtsaanduidingInhoud) throws BerichtInhoudException {

        try {

            // Groep geslachtsaanduiding
            final GroepPersoonGeslachtsaanduiding groepGeslachtsaanduiding = new GroepPersoonGeslachtsaanduiding();

            // Maak attributen aan.
            final GeslachtsaanduidingCode code = new GeslachtsaanduidingCode();

            // Vul de attributen met de waarden uit de inhoud.
            code.setValue(GeslachtsaanduidingCodeS.valueOf(geslachtsaanduidingInhoud.getGeslachtsaanduiding()
                    .getCode()));

            // Zet de attributen in de groep
            groepGeslachtsaanduiding.setCode(code);

            return groepGeslachtsaanduiding;
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException(
                    "Onvoldoende informatie in persoonslijst voor het vullen van de geslachtsaanduiding groep.",
                    exception);
        }
    }

    /**
     * Maakt en vult een geboorte groep voor het MvGeboorteVerzoekType.
     * 
     * @param geboorteInhoud
     *            De inhoud (PL) waaruit de gegevens worden overgenomen.
     * @return De gevulde geboorte groep.
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private GroepPersoonGeboorte maakEnVulGeboorteGroep(final BrpGeboorteInhoud geboorteInhoud)
            throws BerichtInhoudException {

        try {

            // Maak attributen aan.
            final GroepPersoonGeboorte groepGeboorte = new GroepPersoonGeboorte();
            final Datum geboorteDatum = new Datum();
            final Landcode landcode = new Landcode();
            final Woonplaatscode woonplaatscode = new Woonplaatscode();

            // Vul de attributen met de waarden uit de inhoud.
            geboorteDatum.setValue(new BigInteger(String.valueOf(geboorteInhoud.getGeboortedatum().getDatum())));
            landcode.setValue(geboorteInhoud.getLandCode().getCode().toString());
            woonplaatscode.setValue(geboorteInhoud.getGemeenteCode() != null ? geboorteInhoud.getGemeenteCode()
                    .getFormattedStringCode() : geboorteInhoud.getPlaatsCode().getNaam());

            // Zet de attributen in de groep
            groepGeboorte.setDatum(geboorteDatum);
            groepGeboorte.setWoonplaatsCode(woonplaatscode);
            groepGeboorte.setLandCode(landcode);

            return groepGeboorte;
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException(
                    "Onvoldoende informatie in persoonslijst voor het vullen van de geboorte groep.", exception);
        }
    }

    /**
     * Maakt en vult een voornamen groep voor het MvGeboorteVerzoekType.
     * 
     * @param voornaamInhoud
     *            De inhoud (PL) waaruit de gegevens worden overgenomen.
     * @return De gevulde voornamen groep.
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private ContainerPersoonVoornamen maakEnVulVoornamenGroep(final BrpVoornaamInhoud voornaamInhoud)
            throws BerichtInhoudException {

        try {
            final ContainerPersoonVoornamen groepVoornamen = new ContainerPersoonVoornamen();

            int index = 1;

            // Loop door alle voornamen heen en voeg deze met een volgnummer toe aan de groep (splits of een spatie).
            for (final String huidigeVoornaam : voornaamInhoud.getVoornaam().split(" ")) {

                // Maak attributen aan.
                final Voornaam voornaamKind = new Voornaam();
                final Volgnummer volgnummer = new Volgnummer();
                final ObjecttypePersoonVoornaam objectTypeVoornaamKind = new ObjecttypePersoonVoornaam();

                // Vul de attributen met de waarden uit de inhoud.
                voornaamKind.setValue(huidigeVoornaam);
                volgnummer.setValue(new BigInteger(String.valueOf(index)));
                objectTypeVoornaamKind.setNaam(voornaamKind);
                objectTypeVoornaamKind.setVolgnummer(volgnummer);

                // Zet de attributen in de groep
                groepVoornamen.getVoornaam().add(objectTypeVoornaamKind);
                index++;
            }

            return groepVoornamen;
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException(
                    "Onvoldoende informatie in persoonslijst voor het vullen van de voornamen groep.", exception);
        }
    }

    /**
     * Maakt en vult een geslachtsnaamcomponenten groep voor het MvGeboorteVerzoekType.
     * 
     * @param geslachtsnaamcomponentInhoud
     *            De inhoud (PL) waaruit de gegevens worden overgenomen.
     * @return De gevulde geslachtsnaamcomponenten groep.
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private ContainerPersoonGeslachtsnaamcomponenten maakEnVulGeslachtsnaamcomponentenGroep(
            final BrpGeslachtsnaamcomponentInhoud geslachtsnaamcomponentInhoud) throws BerichtInhoudException {

        try {
            final ContainerPersoonGeslachtsnaamcomponenten groepGeslachtsnaamcomponenten =
                    new ContainerPersoonGeslachtsnaamcomponenten();

            // Maak attributen aan.
            AdellijkeTitelCode adellijkeTitelCode = null;
            final Geslachtsnaamcomponent naam = new Geslachtsnaamcomponent();
            PredikaatCode predikaatCode = null;
            final Scheidingsteken scheidingsteken = new Scheidingsteken();
            final Volgnummer volgnummer = new Volgnummer();
            final Voorvoegsel voorvoegsel = new Voorvoegsel();
            final ObjecttypePersoonGeslachtsnaamcomponent geslachtsnaamcomponent =
                    new ObjecttypePersoonGeslachtsnaamcomponent();

            // Vul de attributen met de waarden uit de inhoud.
            if (geslachtsnaamcomponentInhoud.getAdellijkeTitelCode() != null) {
                adellijkeTitelCode = new AdellijkeTitelCode();
                adellijkeTitelCode.setValue(AdellijkeTitelCodeS.valueOf(geslachtsnaamcomponentInhoud
                        .getAdellijkeTitelCode().getCode()));
            }

            naam.setValue(geslachtsnaamcomponentInhoud.getNaam());

            if (geslachtsnaamcomponentInhoud.getPredikaatCode() != null) {
                predikaatCode = new PredikaatCode();
                predikaatCode.setValue(PredikaatCodeS.valueOf(geslachtsnaamcomponentInhoud.getPredikaatCode()
                        .getCode()));
            }

            scheidingsteken.setValue(Character.toString(geslachtsnaamcomponentInhoud.getScheidingsteken()));
            volgnummer.setValue(new BigInteger("1")); // Er is er altijd maar één, daarom hard-coded.
            voorvoegsel.setValue(geslachtsnaamcomponentInhoud.getVoorvoegsel());

            // Zet de attributen in de groep
            geslachtsnaamcomponent.setAdellijkeTitelCode(adellijkeTitelCode);
            geslachtsnaamcomponent.setNaam(naam);
            geslachtsnaamcomponent.setPredikaatCode(predikaatCode);
            geslachtsnaamcomponent.setScheidingsteken(scheidingsteken);
            geslachtsnaamcomponent.setVoorvoegsel(voorvoegsel);
            geslachtsnaamcomponent.setVolgnummer(volgnummer);
            groepGeslachtsnaamcomponenten.getGeslachtsnaamcomponent().add(geslachtsnaamcomponent);

            return groepGeslachtsnaamcomponenten;
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException(
                    "Onvoldoende informatie in persoonslijst voor het vullen van de geslachtscomponenten groep.",
                    exception);
        }
    }

    /**
     * Maakt en vult een samengesteldenaam groep voor het MvGeboorteVerzoekType.
     * 
     * @param geslachtnaamcomponentInhoud
     *            De inhoud (PL) waaruit de gegevens worden overgenomen.
     * @param voornaamInhoud
     *            De inhoud (PL) waaruit de voornaamgegevens worden overgenomen.
     * @return De gevulde samengesteldenaam groep.
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private GroepPersoonSamengesteldeNaam maakEnVulSamengesteldeNaamGroep(
            final BrpGeslachtsnaamcomponentInhoud geslachtnaamcomponentInhoud,
            final BrpVoornaamInhoud voornaamInhoud) throws BerichtInhoudException {

        try {
            final GroepPersoonSamengesteldeNaam groepSamengesteldeNaam = new GroepPersoonSamengesteldeNaam();

            // Maak attributen aan.
            AdellijkeTitelCode adellijkeTitelCode = null;
            final Geslachtsnaam geslachtsnaam = new Geslachtsnaam();
            PredikaatCode predikaatCode = null;
            final Scheidingsteken scheidingsteken = new Scheidingsteken();
            final Voornamen voornamen = new Voornamen();
            final Voorvoegsel voorvoegsel = new Voorvoegsel();

            // Vul de attributen met de waarden uit de inhoud.
            if (geslachtnaamcomponentInhoud.getAdellijkeTitelCode() != null) {
                adellijkeTitelCode = new AdellijkeTitelCode();
                adellijkeTitelCode.setValue(AdellijkeTitelCodeS.valueOf(geslachtnaamcomponentInhoud
                        .getAdellijkeTitelCode().getCode()));
            }
            geslachtsnaam.setValue(geslachtnaamcomponentInhoud.getNaam());
            if (geslachtnaamcomponentInhoud.getPredikaatCode() != null) {
                predikaatCode = new PredikaatCode();
                predikaatCode.setValue(PredikaatCodeS.valueOf(geslachtnaamcomponentInhoud.getPredikaatCode()
                        .getCode()));
            }
            if (geslachtnaamcomponentInhoud.getScheidingsteken() != null) {
                scheidingsteken.setValue(Character.toString(geslachtnaamcomponentInhoud.getScheidingsteken()));
            }
            voornamen.setValue(voornaamInhoud.getVoornaam());
            voorvoegsel.setValue(geslachtnaamcomponentInhoud.getVoorvoegsel());

            // Zet de attributen in de groep
            groepSamengesteldeNaam.setAdellijkeTitelCode(adellijkeTitelCode);
            groepSamengesteldeNaam.setGeslachtsnaam(geslachtsnaam);
            groepSamengesteldeNaam.setPredikaatCode(predikaatCode);
            groepSamengesteldeNaam.setScheidingsteken(scheidingsteken);
            groepSamengesteldeNaam.setVoornamen(voornamen);
            groepSamengesteldeNaam.setVoorvoegsel(voorvoegsel);

            return groepSamengesteldeNaam;
        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException(
                    "Onvoldoende informatie in persoonslijst voor het vullen van de samengesteldenaam groep.",
                    exception);
        }
    }

    /**
     * Maakt en vult een identificatienummers groep voor het MvGeboorteVerzoekType.
     * 
     * @param identificatienummersInhoud
     *            De inhoud (PL) waaruit de gegevens worden overgenomen.
     * @return De gevulde identificatienummers groep.
     * @exception BerichtInhoudException
     *                In het geval dat de PL ongeldige/onvoldoende informatie heeft.
     */
    private GroepPersoonIdentificatienummers maakEnVulIndentificatienummersGroep(
            final BrpIdentificatienummersInhoud identificatienummersInhoud) throws BerichtInhoudException {

        try {
            final GroepPersoonIdentificatienummers groepIdentificatieNummers = new GroepPersoonIdentificatienummers();

            // Maak attributen aan.
            final Burgerservicenummer bsn = new Burgerservicenummer();

            // Vul de attributen met de waarden uit de inhoud.
            bsn.setValue(Long.toString(identificatienummersInhoud.getBurgerservicenummer()));

            // Zet de attributen in de groep
            groepIdentificatieNummers.setBurgerservicenummer(bsn);

            return groepIdentificatieNummers;

        } catch (final NullPointerException exception) {
            throw new BerichtInhoudException(
                    "Onvoldoende informatie in persoonslijst voor het vullen van de indentificatienummers groep.",
                    exception);
        }
    }
}
