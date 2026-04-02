module.exports = function(config) {
  config.set({
    browsers: ['ChromeHeadless'],
    frameworks: ['cljs-test'],
    files: ['target/karma/test.js'],
    preprocessors: {},
    plugins: [
      'karma-cljs-test',
      'karma-chrome-launcher'
    ],
    client: {
      args: ['shadow.test.karma.init']
    },
    colors: true,
    logLevel: config.LOG_INFO,
    singleRun: true
  });
};
