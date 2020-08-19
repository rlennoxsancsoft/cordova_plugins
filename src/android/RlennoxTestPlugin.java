package com.sancsoft.cordova.plugin;
// The native Toast API
import android.widget.Toast;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.eloview.sdk.EloSecureUtil;
import android.widget.Toast;
import android.content.Intent;
import android.provider.Settings;
public class RlennoxTestPlugin extends CordovaPlugin {
  private static final String DURATION_LONG = "long";

  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      if (action.equals("setScreenBrightness")) {
        Integer brightness = 0;
        try {
            JSONObject options = args.getJSONObject(0);
            brightness = Integer.parseInt(options.getString("brightness"));
        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }

        this.setScreenBrightness(brightness);
        // Send a positive result to the callbackContext
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(pluginResult);
        return true;
      }
      else if (action.equals("getScreenBrightness")) {
            Integer brightness = this.getScreenBrightness();
            if (brightness != null) {
                // Send a positive result to the callbackContext
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
                callbackContext.sendPluginResult(pluginResult);
                return true;
            }
            else {
                callbackContext.error("Error encountered, brightness is null");
                return false;
            }
            
      }
      else if (action.equals("show")) {
        String message;
        String duration;
        try {
            JSONObject options = args.getJSONObject(0);
            message = options.getString("message");
            duration = options.getString("duration");
        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }
        // Create the toast
        Toast toast = Toast.makeText(cordova.getActivity(), message,
        DURATION_LONG.equals(duration) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        // Display toast
        toast.show();
        // Send a positive result to the callbackContext
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(pluginResult);
        return true;
      }
      else {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }
  }

  public void setScreenBrightness(Integer  brightness) {
      if (!Settings.System.canWrite(this.cordova.getActivity().getApplicationContext())) {
          Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
          this.cordova.getActivity().startActivity(intent);
      }
      else {
            if (brightness <= 100 && brightness >= 0) {
            try{
                Toast.makeText(this.cordova.getActivity().getApplicationContext(), "SETTING BRIGHTNESS: " + brightness, Toast.LENGTH_SHORT).show();
                EloSecureUtil.setBrightness(this.cordova.getActivity().getApplicationContext(), brightness);
            } catch (Exception e) {
                Toast.makeText(this.cordova.getActivity().getApplicationContext(), "SETTING BRIGHTNESS EXCEPTION: " + e, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this.cordova.getActivity().getApplicationContext(), "Please enter valid range", Toast.LENGTH_SHORT).show();
        }
      }
  }

  public Integer getScreenBrightness() {
    Integer currentBrightness = null;
    if (!Settings.System.canWrite(this.cordova.getActivity().getApplicationContext())) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        this.cordova.getActivity().startActivity(intent);
    }
    else {
        try {
            currentBrightness = EloSecureUtil.getBrightness(this.cordova.getActivity().getApplicationContext());
            Toast.makeText(this.cordova.getActivity().getApplicationContext(), "CURRENT BRIGHTNESS: " + currentBrightness, Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this.cordova.getActivity().getApplicationContext(), "ERROR GETTING BRIGHTNESS", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    return null;
  }
}