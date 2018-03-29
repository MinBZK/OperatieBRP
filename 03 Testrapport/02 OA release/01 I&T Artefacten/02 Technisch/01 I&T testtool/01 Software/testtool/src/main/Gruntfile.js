/*global module:false*/
module.exports = function(grunt) {	
  // Project configuration.
  grunt.initConfig({
    pkg: 
	grunt.file.readJSON('package.json'),
    bowerInstall: {
      target: {
        src: [ 'webapp/index.html' ],

        cwd: '',
        dependencies: true,
        devDependencies: false,
        exclude: [],
        fileTypes: {},
        ignorePath: '',
        overrides: {}
      }
    },
  });

  // These plugins provide necessary tasks.
  grunt.loadNpmTasks('grunt-bower-install');

  // Default task.
  grunt.registerTask('default', ['' ]);

};
