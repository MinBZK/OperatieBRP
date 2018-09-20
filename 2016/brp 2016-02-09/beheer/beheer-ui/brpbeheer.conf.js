// Karma configuration
// Generated on Mon Mar 23 2015 13:12:11 GMT+0100 (CET)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
      'app/ea/jquery/dist/jquery.min.js',
      'app/ea/bootstrap/dist/js/bootstrap.min.js',
      'app/ea/angular/angular.min.js',
      'app/ea/angular-resource/angular-resource.min.js',
      'app/ea/angular-route/angular-route.min.js',
      'app/ea/angular-local-storage/dist/angular-local-storage.min.js',
      'app/ea/angular-bootstrap/ui-bootstrap-tpls.min.js',
      'app/ea/moment/min/moment.min.js',
      'app/ea/angular-bootstrap-datetimepicker/js/datetimepicker.js',
      'node_modules/angular-mocks/angular-mocks.js',
      'app/js/utils/json-toon.js',
      'app/js/directives/Keybind.js',
      'app/js/directives/BrpLijstveld.js',
      'app/js/directives/BrpVeld.js',
      'app/js/directives/BrpZoekveld.js',
      'app/js/directives/DatumFormats.js',
      'app/js/services/AuthenticationService.js',
      'app/js/services/Base64Service.js',

      'app/js/controllers/InfoController.js',
      'app/js/controllers/ItemController.js',
      'app/js/controllers/ItemInstanceController.js',
      'app/js/controllers/ItemJsonController.js',
      'app/js/controllers/ListController.js',
      'app/js/controllers/Lo3FilterRubriekInstanceController.js',
      'app/js/controllers/SecurityController.js',
      'app/js/beheer-applicatie.js',
      'app/conf/beheer-settings.js',

      'app/js/configuration/beheer-security.js',
      'app/js/configuration/beheer-configuratie.js',
      'app/js/configuration/beheer-configuratie-autaut.js',
      'app/js/configuration/beheer-configuratie-ber.js',
      'app/js/configuration/beheer-configuratie-brm.js',
      'app/js/configuration/beheer-configuratie-conv.js',
      'app/js/configuration/beheer-configuratie-ist.js',
      'app/js/configuration/beheer-configuratie-kern.js',
      'app/js/configuration/beheer-configuratie-migblok.js',
      'app/js/configuration/beheer-configuratie-verconv.js',
      'test/js/controllers/*.js',
      'test/js/utils/*.js',
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Firefox'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
