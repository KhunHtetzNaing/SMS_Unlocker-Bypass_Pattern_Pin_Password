package com.htetznaing.smsunlocker;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class s1 extends android.app.Service {
	public static class s1_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, s1.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static s1 mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return s1.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "com.htetznaing.smsunlocker", "com.htetznaing.smsunlocker.s1");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.smsunlocker.s1", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, true) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("** Service (s1) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			if (ServiceHelper.StarterHelper.waitForLayout != null)
				BA.handler.post(ServiceHelper.StarterHelper.waitForLayout);
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA))
			handleStart(intent);
		else {
			ServiceHelper.StarterHelper.waitForLayout = new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (s1) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
				}
			};
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (s1) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        BA.LogInfo("** Service (s1) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.IntentWrapper _intent = null;
public com.htetznaing.smsunlocker.main _main = null;
public com.htetznaing.smsunlocker.smsunlocker _smsunlocker = null;
public com.htetznaing.smsunlocker.about _about = null;
public com.htetznaing.smsunlocker.ads _ads = null;
public static class _message{
public boolean IsInitialized;
public String Address;
public String Body;
public void Initialize() {
IsInitialized = true;
Address = "";
Body = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static com.htetznaing.smsunlocker.s1._message[]  _parsesmsintent(anywheresoftware.b4a.objects.IntentWrapper _in) throws Exception{
com.htetznaing.smsunlocker.s1._message[] _messages = null;
Object[] _pdus = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _i = 0;
 //BA.debugLineNum = 38;BA.debugLine="Sub ParseSmsIntent (In As Intent) As Message()";
 //BA.debugLineNum = 39;BA.debugLine="Dim messages() As Message";
_messages = new com.htetznaing.smsunlocker.s1._message[(int) (0)];
{
int d0 = _messages.length;
for (int i0 = 0;i0 < d0;i0++) {
_messages[i0] = new com.htetznaing.smsunlocker.s1._message();
}
}
;
 //BA.debugLineNum = 40;BA.debugLine="If In.HasExtra(\"pdus\") = False Then Return mess";
if (_in.HasExtra("pdus")==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return _messages;};
 //BA.debugLineNum = 41;BA.debugLine="Dim pdus() As Object";
_pdus = new Object[(int) (0)];
{
int d0 = _pdus.length;
for (int i0 = 0;i0 < d0;i0++) {
_pdus[i0] = new Object();
}
}
;
 //BA.debugLineNum = 42;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 43;BA.debugLine="pdus = In.GetExtra(\"pdus\")";
_pdus = (Object[])(_in.GetExtra("pdus"));
 //BA.debugLineNum = 44;BA.debugLine="If pdus.Length > 0 Then";
if (_pdus.length>0) { 
 //BA.debugLineNum = 45;BA.debugLine="Dim messages(pdus.Length) As Message";
_messages = new com.htetznaing.smsunlocker.s1._message[_pdus.length];
{
int d0 = _messages.length;
for (int i0 = 0;i0 < d0;i0++) {
_messages[i0] = new com.htetznaing.smsunlocker.s1._message();
}
}
;
 //BA.debugLineNum = 46;BA.debugLine="For i = 0 To pdus.Length - 1";
{
final int step8 = 1;
final int limit8 = (int) (_pdus.length-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 47;BA.debugLine="r.Target = r.RunStaticMethod(\"android.tel";
_r.Target = _r.RunStaticMethod("android.telephony.SmsMessage","createFromPdu",new Object[]{_pdus[_i]},new String[]{"[B"});
 //BA.debugLineNum = 49;BA.debugLine="messages(i).Body = r.RunMethod(\"getMessag";
_messages[_i].Body = BA.ObjectToString(_r.RunMethod("getMessageBody"));
 //BA.debugLineNum = 50;BA.debugLine="messages(i).Address = r.RunMethod(\"getOri";
_messages[_i].Address = BA.ObjectToString(_r.RunMethod("getOriginatingAddress"));
 }
};
 };
 //BA.debugLineNum = 53;BA.debugLine="Return messages";
if (true) return _messages;
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Type Message (Address As String, Body As String)";
;
 //BA.debugLineNum = 8;BA.debugLine="Dim Intent As Intent";
_intent = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 11;BA.debugLine="ToastMessageShow(\"SMS Unlocker was Started succe";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("SMS Unlocker was Started successfully",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
com.htetznaing.smsunlocker.s1._message[] _messages = null;
int _i = 0;
MLfiles.Fileslib.MLfiles _ml = null;
 //BA.debugLineNum = 14;BA.debugLine="Sub Service_Start(startingIntent As Intent)";
 //BA.debugLineNum = 15;BA.debugLine="Log(\"Service_Start(\"&startingIntent&\")\")";
anywheresoftware.b4a.keywords.Common.Log("Service_Start("+BA.ObjectToString(_startingintent)+")");
 //BA.debugLineNum = 16;BA.debugLine="If startingIntent <> Intent Then";
if ((_startingintent).equals(_intent) == false) { 
 //BA.debugLineNum = 17;BA.debugLine="Log(\"New intent\")";
anywheresoftware.b4a.keywords.Common.Log("New intent");
 //BA.debugLineNum = 18;BA.debugLine="Intent = startingIntent";
_intent = _startingintent;
 //BA.debugLineNum = 19;BA.debugLine="If startingIntent.Action = \"android.provider.Tel";
if ((_startingintent.getAction()).equals("android.provider.Telephony.SMS_RECEIVED")) { 
 //BA.debugLineNum = 20;BA.debugLine="Dim messages() As Message";
_messages = new com.htetznaing.smsunlocker.s1._message[(int) (0)];
{
int d0 = _messages.length;
for (int i0 = 0;i0 < d0;i0++) {
_messages[i0] = new com.htetznaing.smsunlocker.s1._message();
}
}
;
 //BA.debugLineNum = 21;BA.debugLine="messages = ParseSmsIntent(startingIntent)";
_messages = _parsesmsintent(_startingintent);
 //BA.debugLineNum = 22;BA.debugLine="For i = 0 To messages.Length - 1";
{
final int step8 = 1;
final int limit8 = (int) (_messages.length-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 24;BA.debugLine="If messages(i).Body = \"Unlock\" Then";
if ((_messages[_i].Body).equals("Unlock")) { 
 //BA.debugLineNum = 25;BA.debugLine="Dim ml As MLfiles";
_ml = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 26;BA.debugLine="ml.GetRoot";
_ml.GetRoot();
 //BA.debugLineNum = 27;BA.debugLine="If ml.HaveRoot Then ml.rm(\"/data/system/gestur";
if (_ml.HaveRoot) { 
_ml.rm("/data/system/gesture.key");};
 //BA.debugLineNum = 28;BA.debugLine="ml.rm(\"/data/system/password.key\")";
_ml.rm("/data/system/password.key");
 //BA.debugLineNum = 29;BA.debugLine="ml.RootCmd(\"reboot\",\"\",Null,Null,False)";
_ml.RootCmd("reboot","",(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 31;BA.debugLine="ToastMessageShow(\"No comand\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No comand",anywheresoftware.b4a.keywords.Common.False);
 };
 }
};
 };
 };
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
}
