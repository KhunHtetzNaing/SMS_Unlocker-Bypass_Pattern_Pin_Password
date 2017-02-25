Type=Service
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: True
#End Region

Sub Process_Globals

  Type Message (Address As String, Body As String)
	Dim Intent As Intent
End Sub
Sub Service_Create
  ToastMessageShow("SMS Unlocker was Started successfully",False)
End Sub

Sub Service_Start(startingIntent As Intent)
	Log("Service_Start("&startingIntent&")")
	If startingIntent <> Intent Then 
		Log("New intent")
		Intent = startingIntent
		If startingIntent.Action = "android.provider.Telephony.SMS_RECEIVED" Then
      Dim messages() As Message
      messages = ParseSmsIntent(startingIntent)
      For i = 0 To messages.Length - 1
	  	
	   If messages(i).Body = "Unlock" Then
	   Dim ml As MLfiles
	   ml.GetRoot
	   If ml.HaveRoot Then ml.rm("/data/system/gesture.key")
	   ml.rm("/data/system/password.key")
	   ml.RootCmd("reboot","",Null,Null,False)
	Else
	  ToastMessageShow("No comand",False)
	 End If
      Next
   	End If
	End If
End Sub

Sub ParseSmsIntent (In As Intent) As Message()
   Dim messages() As Message
   If In.HasExtra("pdus") = False Then Return messages
   Dim pdus() As Object
   Dim r As Reflector
   pdus = In.GetExtra("pdus")
   If pdus.Length > 0 Then
      Dim messages(pdus.Length) As Message
      For i = 0 To pdus.Length - 1
         r.Target = r.RunStaticMethod("android.telephony.SmsMessage", "createFromPdu", _
            Array As Object(pdus(i)), Array As String("[B"))
         messages(i).Body = r.RunMethod("getMessageBody")
         messages(i).Address = r.RunMethod("getOriginatingAddress")
      Next
   End If
   Return messages
End Sub