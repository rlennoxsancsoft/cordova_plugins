
// Empty constructor
function RlennoxTestPlugin() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
RlennoxTestPlugin.prototype.show = function(message, duration, successCallback, errorCallback) {
  var options = {};
  options.message = message;
  options.duration = duration;
  cordova.exec(successCallback, errorCallback, 'RlennoxTestPlugin', 'show', [options]);
}

// Set the brightness of the elo device given from the app
RlennoxTestPlugin.prototype.setScreenBrightness = function(brightness, successCallback, errorCallback) {
    var options = {};
    options.brightness = brightness;
    console.log(brightness);
    cordova.exec(successCallback, errorCallback, 'RlennoxTestPlugin', 'setScreenBrightness', [options]);
}

// Grab the brightness from the elo device
RlennoxTestPlugin.prototype.getScreenBrightness = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'RlennoxTestPlugin', 'getScreenBrightness', []);
}

// Installation constructor that binds RlennoxTestPlugin to window
RlennoxTestPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.rlennoxTestPlugin = new RlennoxTestPlugin();
  return window.plugins.rlennoxTestPlugin;
};
cordova.addConstructor(RlennoxTestPlugin.install);