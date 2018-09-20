liquibase --defaultsFile=bmr.properties --logFile=bmr-export.log generateChangeLog
liquibase --defaultsFile=bmr.properties --logFile=bmr-export.log updateSQL > bmr.sql