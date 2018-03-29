/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Precondities basis voor toevallige gebeurtenis.
 */
public abstract class AbstractLo3ToevalligeGebeurtenisPrecondities extends AbstractLo3Precondities {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public AbstractLo3ToevalligeGebeurtenisPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controle op groep 81 Akte; de groep moet gevuld zijn in de actuele categorie en mag niet gevuld zijn in eventuele
     * historische categorieen.
     * @param stapel stapel
     */
    protected final void controleerGroep81Akte(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP81, categorie.getDocumentatie())) {
                if (categorie.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                    controleerGroep81Akte(categorie.getDocumentatie(), categorie.getLo3Herkomst());
                } else {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG047, null);
                }
            } else {
                if (categorie.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG048, null);
                }
            }
        }
    }

    /**
     * Controle op groep 82 Document; de groep mag niet gevuld zijn in de actuele of historische categorieen.
     * @param stapel stapel
     */
    protected final void controleerGroep82Document(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP82, categorie.getDocumentatie())) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG005, null);
            }
        }
    }

    /**
     * Controle op groep 83 Onderzoek; de groep mag niet gevuld zijn in de actuele of historische categorieen.
     * @param stapel stapel
     */
    final void controleerGroep83Procedure(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP83, categorie.getOnderzoek())) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG006, null);
            }
        }
    }

    /**
     * Controle op groep 84 Onjuist; de groep mag niet gevuld zijn in de actuele of historische categorieen.
     * @param stapel stapel
     */
    final void controleerGroep84Onjuist(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP84, categorie.getHistorie())) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG007, null);
            }
        }
    }

    /**
     * Controle op groep 85 Geldigheid; de groep moet gevuld zijn in de actuele categorie en mag niet gevuld zijn in
     * eventuele historische categorieen.
     * @param stapel stapel
     */

    final void controleerGroep85Geldigheid(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, categorie.getHistorie())) {
                if (categorie.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                    controleerDatum(
                            categorie.getHistorie().getIngangsdatumGeldigheid(),
                            Foutmelding.maakMeldingFout(
                                    categorie.getLo3Herkomst(),
                                    LogSeverity.ERROR,
                                    SoortMeldingCode.STRUC_DATUM,
                                    Lo3ElementEnum.ELEMENT_8510));
                } else {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG049, null);
                }
            } else {
                if (categorie.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG050, null);
                }
            }
        }
    }

    /**
     * Controle op groep 86 Opneming; de groep mag niet gevuld zijn in de actuele of historische categorieen.
     * @param stapel stapel
     */
    final void controleerGroep86Opneming(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            LOG.debug(categorie.toString());
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP86, categorie.getHistorie())) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG009, null);
            }
        }
    }

    /**
     * Controle op groep 88 RNI-deelnemer; de groep mag niet gevuld zijn in de actuele of historische categorieen.
     * @param stapel stapel
     */
    final void controleerGroep88RNIDeelnemer(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> categorie : stapel) {
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP88, categorie.getDocumentatie())) {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG003, null);
            }
        }
    }

    /**
     * Controle dat er naast een actueel maximaal 1 historische voorkomen is.
     * @param stapel stapel
     */
    final void controleerMaximaalTweeVoorkomens(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        final int max = 2;
        if (stapel.size() > max) {
            Foutmelding.logMeldingFout(maakHerkomstZonderVoorkomen(stapel), LogSeverity.ERROR, SoortMeldingCode.TG018, null);
        }
    }

    private Lo3Herkomst maakHerkomstZonderVoorkomen(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        return maakHerkomstZonderVoorkomen(stapel.get(0));
    }

    private Lo3Herkomst maakHerkomstZonderVoorkomen(final Lo3Categorie<? extends Lo3CategorieInhoud> categorie) {
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
        return new Lo3Herkomst(herkomst.getCategorie(), herkomst.getStapel(), -1);
    }

    /**
     * Controleer dat alleen een actuele categorie voorkomt in een stapel.
     * @param stapel stapel
     */
    final void controleerAlleenAktueel(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        if (stapel != null && stapel.size() > 1) {
            Foutmelding.logMeldingFout(maakHerkomstZonderVoorkomen(stapel), LogSeverity.ERROR, SoortMeldingCode.TG027, null);
        }
    }

    /**
     * Controleer dat stapel er niet is of leeg is.
     * @param stapel stapel
     */
    final void controleerNietAanwezig(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        if (stapel != null && !stapel.isEmpty()) {
            Foutmelding.logMeldingFout(maakHerkomstZonderVoorkomen(stapel), LogSeverity.ERROR, SoortMeldingCode.TG028, null);
        }
    }

    /**
     * Controleer dat de categorie er niet is.
     * @param categorie categorie
     */
    final void controleerNietAanwezig(final Lo3Categorie<? extends Lo3CategorieInhoud> categorie) {
        if (categorie != null) {
            Foutmelding.logMeldingFout(maakHerkomstZonderVoorkomen(categorie), LogSeverity.ERROR, SoortMeldingCode.TG028, null);
        }
    }

    /**
     * Controleer dat twee elementen inhoudelijk gelijk zijn.
     * @param element1 element
     * @param element2 element
     * @return true, als de twee elementen inhoudelijk gelijk zijn (null en null worden als gelijk beschouwd)
     */
    final boolean elementEquals(final Lo3Element element1, final Lo3Element element2) {
        return element1 == null ? element2 == null : element1.equalsWaarde(element2);
    }

    /**
     * Controleer dat een land 'Nederland' is.
     * @param landCode te controleren landcode
     * @param herkomst herkomst
     * @param element element van het te controleren land
     */
    final void controleerNederland(final Lo3LandCode landCode, final Lo3Herkomst herkomst, final Lo3ElementEnum element) {
        if (!Lo3LandCode.CODE_NEDERLAND.equals(landCode.getWaarde())) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG062, element);
        }
    }

}
