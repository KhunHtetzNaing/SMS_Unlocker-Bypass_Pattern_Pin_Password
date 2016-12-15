package com.htetznaing.smsunlocker;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.htetznaing.smsunlocker", "com.htetznaing.smsunlocker.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.htetznaing.smsunlocker", "com.htetznaing.smsunlocker.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.smsunlocker.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static int _theme_value = 0;
public static anywheresoftware.b4a.objects.Timer _t = null;
public static anywheresoftware.b4a.objects.Timer _t1 = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _res = null;
public MLfiles.Fileslib.MLfiles _ml = null;
public anywheresoftware.b4a.objects.ButtonWrapper _start = null;
public anywheresoftware.b4a.objects.ButtonWrapper _stop = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfooter = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _adview1 = null;
public mobi.mindware.admob.interstitial.AdmobInterstitialsAds _adview2 = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public com.htetznaing.smsunlocker.smsunlocker _smsunlocker = null;
public com.htetznaing.smsunlocker.about _about = null;
public com.htetznaing.smsunlocker.ads _ads = null;
public com.htetznaing.smsunlocker.s1 _s1 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (about.mostCurrent != null);
vis = vis | (ads.mostCurrent != null);
return vis;}
public static String  _about_click() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub About_Click";
 //BA.debugLineNum = 69;BA.debugLine="If p.SdkVersion > 19 Then";
if (mostCurrent._p.getSdkVersion()>19) { 
 //BA.debugLineNum = 70;BA.debugLine="t1.Enabled = True";
_t1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 72;BA.debugLine="StartActivity(About)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._about.getObject()));
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 32;BA.debugLine="Activity.LoadLayout(\"1\")";
mostCurrent._activity.LoadLayout("1",mostCurrent.activityBA);
 //BA.debugLineNum = 33;BA.debugLine="start.Text = \"Start\"";
mostCurrent._start.setText((Object)("Start"));
 //BA.debugLineNum = 34;BA.debugLine="stop.Text = \"Stop\"";
mostCurrent._stop.setText((Object)("Stop"));
 //BA.debugLineNum = 36;BA.debugLine="lblfooter.Initialize(\"lblfooter\")";
mostCurrent._lblfooter.Initialize(mostCurrent.activityBA,"lblfooter");
 //BA.debugLineNum = 37;BA.debugLine="lblfooter.Text = \"Developed By Khun Htetz Naing\"";
mostCurrent._lblfooter.setText((Object)("Developed By Khun Htetz Naing"));
 //BA.debugLineNum = 38;BA.debugLine="lblfooter.TextColor = Colors.Green";
mostCurrent._lblfooter.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 //BA.debugLineNum = 39;BA.debugLine="lblfooter.Gravity = Gravity.CENTER";
mostCurrent._lblfooter.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 40;BA.debugLine="Activity.AddView(lblfooter,10dip, 100%y - 35dip ,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lblfooter.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 42;BA.debugLine="If p.SdkVersion > 19 Then";
if (mostCurrent._p.getSdkVersion()>19) { 
 //BA.debugLineNum = 43;BA.debugLine="AdView1.Initialize(\"ad\",\"ca-app-pub-41733485732529";
mostCurrent._adview1.Initialize(mostCurrent.activityBA,"ad","ca-app-pub-4173348573252986/5103755751");
 //BA.debugLineNum = 44;BA.debugLine="Activity.AddView(AdView1,0%x,(stop.Height+stop.Top";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._adview1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._stop.getHeight()+mostCurrent._stop.getTop())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 45;BA.debugLine="AdView1.LoadAd";
mostCurrent._adview1.LoadAd();
 //BA.debugLineNum = 47;BA.debugLine="AdView2.Initialize(\"AdView2\",\"ca-app-pub-417334857";
mostCurrent._adview2.Initialize(mostCurrent.activityBA,"AdView2","ca-app-pub-4173348573252986/3909332151");
 //BA.debugLineNum = 48;BA.debugLine="AdView2.LoadAd";
mostCurrent._adview2.LoadAd(mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="t.Initialize(\"t\",20000)";
_t.Initialize(processBA,"t",(long) (20000));
 //BA.debugLineNum = 51;BA.debugLine="t.Enabled = True";
_t.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 52;BA.debugLine="t1.Initialize(\"t1\",100)";
_t1.Initialize(processBA,"t1",(long) (100));
 //BA.debugLineNum = 53;BA.debugLine="t1.Enabled = False";
_t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 56;BA.debugLine="Activity.AddMenuItem3(\"About\",\"About\",LoadBitmap(F";
mostCurrent._activity.AddMenuItem3("About","About",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 58;BA.debugLine="Activity.Title = \"SMS Bypasser\"";
mostCurrent._activity.setTitle((Object)("SMS Bypasser"));
 //BA.debugLineNum = 59;BA.debugLine="Activity.AddMenuItem(\"Holo\",\"btnHolo\")";
mostCurrent._activity.AddMenuItem("Holo","btnHolo");
 //BA.debugLineNum = 60;BA.debugLine="Activity.AddMenuItem(\"Holo Light\",\"btnHoloLight\")";
mostCurrent._activity.AddMenuItem("Holo Light","btnHoloLight");
 //BA.debugLineNum = 61;BA.debugLine="Activity.AddMenuItem(\"Holo Light Dark\",\"btnHoloLi";
mostCurrent._activity.AddMenuItem("Holo Light Dark","btnHoloLightDark");
 //BA.debugLineNum = 62;BA.debugLine="Activity.AddMenuItem(\"Old Android\",\"btnOld\")";
mostCurrent._activity.AddMenuItem("Old Android","btnOld");
 //BA.debugLineNum = 63;BA.debugLine="Activity.AddMenuItem(\"Material Light Dark\",\"btnMa";
mostCurrent._activity.AddMenuItem("Material Light Dark","btnMaterialLightDark");
 //BA.debugLineNum = 64;BA.debugLine="Activity.AddMenuItem(\"Material Light\",\"btnMateria";
mostCurrent._activity.AddMenuItem("Material Light","btnMaterialLight");
 //BA.debugLineNum = 65;BA.debugLine="Activity.AddMenuItem(\"Material\",\"btnMaterial\")";
mostCurrent._activity.AddMenuItem("Material","btnMaterial");
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _btnholo_click() throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub btnHolo_Click";
 //BA.debugLineNum = 98;BA.debugLine="SetTheme(res.GetResourceId(\"style\", \"android:styl";
_settheme(mostCurrent._res.GetResourceId("style","android:style/Theme.Holo"));
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _btnhololight_click() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub btnHoloLight_Click";
 //BA.debugLineNum = 102;BA.debugLine="SetTheme(res.GetResourceId(\"style\", \"android:styl";
_settheme(mostCurrent._res.GetResourceId("style","android:style/Theme.Holo.Light"));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _btnhololightdark_click() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub btnHoloLightDark_Click";
 //BA.debugLineNum = 106;BA.debugLine="SetTheme(res.GetResourceId(\"style\", \"android:styl";
_settheme(mostCurrent._res.GetResourceId("style","android:style/Theme.Holo.Light.DarkActionBar"));
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _btnmaterial_click() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub btnMaterial_Click";
 //BA.debugLineNum = 122;BA.debugLine="SetTheme(res.GetResourceId(\"style\", \"android:styl";
_settheme(mostCurrent._res.GetResourceId("style","android:style/Theme.Material"));
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _btnmateriallight_click() throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Sub btnMaterialLight_Click";
 //BA.debugLineNum = 118;BA.debugLine="SetTheme(res.GetResourceId(\"style\", \"android:styl";
_settheme(mostCurrent._res.GetResourceId("style","android:style/Theme.Material.Light"));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _btnmateriallightdark_click() throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub btnMaterialLightDark_Click";
 //BA.debugLineNum = 114;BA.debugLine="SetTheme(res.GetResourceId(\"style\", \"android:styl";
_settheme(mostCurrent._res.GetResourceId("style","android:style/Theme.Material.Light.DarkActionBar"));
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _btnold_click() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub btnOld_Click";
 //BA.debugLineNum = 110;BA.debugLine="SetTheme(16973829)";
_settheme((int) (16973829));
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Dim res As XmlLayoutBuilder";
mostCurrent._res = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 23;BA.debugLine="Dim ml As MLfiles";
mostCurrent._ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 24;BA.debugLine="Dim start,stop As Button";
mostCurrent._start = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._stop = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim lblfooter As Label";
mostCurrent._lblfooter = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim AdView1 As AdView";
mostCurrent._adview1 = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim AdView2 As mwAdmobInterstitial";
mostCurrent._adview2 = new mobi.mindware.admob.interstitial.AdmobInterstitialsAds();
 //BA.debugLineNum = 28;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
smsunlocker._process_globals();
about._process_globals();
ads._process_globals();
s1._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim Theme_Value As Int";
_theme_value = 0;
 //BA.debugLineNum = 18;BA.debugLine="Dim t,t1 As Timer";
_t = new anywheresoftware.b4a.objects.Timer();
_t1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _settheme(int _theme) throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Private Sub SetTheme (Theme As Int)";
 //BA.debugLineNum = 126;BA.debugLine="If Theme = 0 Then";
if (_theme==0) { 
 //BA.debugLineNum = 127;BA.debugLine="ToastMessageShow(\"Theme not available.\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Theme not available.",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 128;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 130;BA.debugLine="If Theme = Theme_Value Then Return";
if (_theme==_theme_value) { 
if (true) return "";};
 //BA.debugLineNum = 131;BA.debugLine="Theme_Value = Theme";
_theme_value = _theme;
 //BA.debugLineNum = 132;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 133;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,main.getObject());
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _start_click() throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub start_Click";
 //BA.debugLineNum = 75;BA.debugLine="If p.SdkVersion > 19 Then";
if (mostCurrent._p.getSdkVersion()>19) { 
 //BA.debugLineNum = 76;BA.debugLine="t1.Enabled = True";
_t1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 78;BA.debugLine="StartService(SmsUnlocker)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._smsunlocker.getObject()));
 //BA.debugLineNum = 79;BA.debugLine="ml.GetRoot";
mostCurrent._ml.GetRoot();
 //BA.debugLineNum = 80;BA.debugLine="If ml.HaveRoot Then";
if (mostCurrent._ml.HaveRoot) { 
 }else {
 //BA.debugLineNum = 82;BA.debugLine="Msgbox(\"No have Root Access!\" ,\"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("No have Root Access!","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static String  _stop_click() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub stop_Click";
 //BA.debugLineNum = 86;BA.debugLine="If p.SdkVersion > 19 Then";
if (mostCurrent._p.getSdkVersion()>19) { 
 //BA.debugLineNum = 87;BA.debugLine="t1.Enabled = True";
_t1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 89;BA.debugLine="StopService(SmsUnlocker)";
anywheresoftware.b4a.keywords.Common.StopService(mostCurrent.activityBA,(Object)(mostCurrent._smsunlocker.getObject()));
 //BA.debugLineNum = 90;BA.debugLine="Msgbox(\"SMS-Unlocker Stopped\",\"Attention\")";
anywheresoftware.b4a.keywords.Common.Msgbox("SMS-Unlocker Stopped","Attention",mostCurrent.activityBA);
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _t_tick() throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Sub t_Tick";
 //BA.debugLineNum = 162;BA.debugLine="If p.SdkVersion > 19 Then";
if (mostCurrent._p.getSdkVersion()>19) { 
 //BA.debugLineNum = 163;BA.debugLine="If AdView2.Status=AdView2.Status_AdReadyToShow Th";
if (mostCurrent._adview2.Status==mostCurrent._adview2.Status_AdReadyToShow) { 
 //BA.debugLineNum = 164;BA.debugLine="AdView2.Show";
mostCurrent._adview2.Show(mostCurrent.activityBA);
 };
 //BA.debugLineNum = 167;BA.debugLine="If AdView2.Status=AdView2.Status_Dismissed Then";
if (mostCurrent._adview2.Status==mostCurrent._adview2.Status_Dismissed) { 
 //BA.debugLineNum = 168;BA.debugLine="AdView2.LoadAd";
mostCurrent._adview2.LoadAd(mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _t1_tick() throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub t1_Tick";
 //BA.debugLineNum = 149;BA.debugLine="If p.SdkVersion > 19 Then";
if (mostCurrent._p.getSdkVersion()>19) { 
 //BA.debugLineNum = 150;BA.debugLine="If AdView2.Status=AdView2.Status_AdReadyToShow Th";
if (mostCurrent._adview2.Status==mostCurrent._adview2.Status_AdReadyToShow) { 
 //BA.debugLineNum = 151;BA.debugLine="AdView2.Show";
mostCurrent._adview2.Show(mostCurrent.activityBA);
 };
 //BA.debugLineNum = 154;BA.debugLine="If AdView2.Status=AdView2.Status_Dismissed Then";
if (mostCurrent._adview2.Status==mostCurrent._adview2.Status_Dismissed) { 
 //BA.debugLineNum = 155;BA.debugLine="AdView2.LoadAd";
mostCurrent._adview2.LoadAd(mostCurrent.activityBA);
 };
 //BA.debugLineNum = 157;BA.debugLine="t1.Enabled = False";
_t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public void _onCreate() {
	if (_theme_value != 0)
		setTheme(_theme_value);
}
}
