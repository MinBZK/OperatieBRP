%cd%
SET AFLEVERPUNT=http://localhost/abc
SET WORKDIR=.\afnemerindicatieworkdir

java -cp ".;autaut-conversie.jar" -Dbrp.afleverpunt=%AFLEVERPUNT% -Dworkdir=%cd%\afnemerindicatieworkdir nl.bzk.brp.util.autconv.lo3naarbrp.Main