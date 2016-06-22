UPDATE---------

Purpose -
This is forked from original  repository and modified to clear the saved credentials from ios
when login in to url which asks for authentication .
Used along with 

https://github.com/MSOpenTech/cordova-plugin-auth-dialog


Problem --

we are trying to login to secure url , which asks us for credentials (Basic, NTLM).
even after clearing the caches using below js api, credentials were still saved in Keychain.
So, Solution was is to clear the saved password from keychain.
And It Works.

It is only for iOS,
For Android, 
1.https://code.google.com/p/android/issues/detail?id=22272
I've also worked around this by only using my WebView that uses HTTP Auth in an Activity running in a separate process. When I want to reset HTTP Auth, I finish that Activity, kill its process, and then delete the WebView database files with:
 
deleteDatabase("webview.db");
deleteDatabase("webviewCache.db");

But we didn't get time to do so, workaround implemented was to restart the android app.







# Cache Clear


This is a WebView cache plugin for Cordova 5.4.0 supporting Android (>=2.3.3) and iOS(>=6.0)
It allows the app to use javascript to initiate a cordova webview cache clear

There are two methods:

`clear(successCallback, errorCallback)`
`cleartemp()`

#### Manual Installation

You may use `cordova-cli` as follows:

```shell
cordova plugin add https://github.com/andxyz/cordova-plugin-cache.git
```

#### Usage

```javascript
document.addEventListener('deviceready', onDeviceReady);

function onDeviceReady() {
  var success = function(status) {
    alert('Message: ' + status);
  }

  var error = function(status) {
    alert('Error: ' + status);
  }

  window.cache.clear(success, error);
  window.cache.cleartemp();
}

```
