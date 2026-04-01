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
    colors: true,
    logLevel: config.LOG_INFO,
    singleRun: true
  });
};
