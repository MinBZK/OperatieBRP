Eclipse setup
+++++++++++++

1) Pas maven settings.xml door de volgende configuratie toe te voegen:

        <server>
                <id>libs-release-migratie</id>
                <username>$USERNAME</username>
                <password>$ENCRYPTED_PASSWORD</password>
        </server>
        <server>
                <id>libs-snapshot-migratie</id>
                <username>$USERNAME</username>
                <password>$ENCRYPTED_PASSWORD</password>
        </server>
        
   Gebruikt je username en wachtwoord voor modernodam. Het wachtwoord moet wel eerst worden versleuteld.
   Informatie over het verkrijgen van de encrypted versie van je wachtwoord kun je vinden op:
   http://maven.apache.org/guides/mini/guide-encryption.html.

2) Stel encoding in: Eclipse Preferences -> General / Workspace / Text file encoding = UTF-8
3) Voeg de ISC SVN repository toe aan Eclipse: https://www.modernodam.nl/svn/isc-code/
4) Check alle projecten onder de trunk uit
5) Maak een Java Working Set genaamd 'Migratie' en voeg daar de Migratie projecten aan toe
6) Converteer alle migratie projecten naar Maven projecten (Configure / Convert to Maven project)

7) Configureer een Eclipse formatter (Eclipse Preferences -> Java / Code Style / Formatter)
   gebruik hiervoor migratie/src/main/resources/eclipse_formatter.xml
8) Installeer de eclipse-cs plugin
9) Configureer checkstyle, gebruik hiervoor migratie/src/main/resources/checkstyle_migratie.xml (set als default)

10) Configureer de save actions: Eclipse Preferences -> Java / Editor / Save actions

 a) format all lines
 b) organize imports
 c) additional actions:
 	Add 'this' qualifier to unqualified field accesses
 	Change non static accesses to static members using declaring type
 	Change indirect accesses to static members to direct accesses (accesses through subtypes)
 	Convert control statement bodies to block
 	Add final modifier to private fields
 	Add final modifier to method parameters
 	Add final modifier to local variables
 	Remove unused imports
 	Remove unused private methods
 	Remove unused private constructors
 	Remove unused private types
 	Remove unused private fields
 	Remove unused local variables
 	Add missing '@Override' annotations
 	Add missing '@Override' annotations to implementations of interface methods
 	Add missing '@Deprecated' annotations
 	Remove unnecessary casts
 	Remove unnecessary '$NON-NLS$' tags
 	Remove trailing white spaces on all lines
 	Correct indentation


11) Configureer de XML catalog binnen Eclipse t.b.v. de DAL-testen

 a) Ga naar Workspace Preferences en zoek naar ‘XML catalog’. Voor daar een User Specified Entry toe
 b) Zoek binnen het nieuwe scherm de DTD-file met de naam brp_kern.dtd op (in /src/main/resources/schema van project synchronisatie)
 c) Kies vervolgens bij ‘Key type’ voor Public ID en vul bij Key 'brp_kern' in
 d) Druk op 'OK'

 