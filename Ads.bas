Type=Activity
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	Dim t As Timer
End Sub

Sub Globals
	Dim AdView2 As InterstitialAd
End Sub

Sub Activity_Create(FirstTime As Boolean)
	t.Initialize("t",100)
	t.Enabled = True
	
	AdView2.Initialize("AdView2","ca-app-pub-4173348573252986/8511189350")
	AdView2.LoadAd
End Sub


Sub t_tick
	If AdView2.Ready Then AdView2.Show Else AdView2.LoadAd
	t.Enabled = False
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub