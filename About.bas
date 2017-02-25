Type=Activity
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim su As StringUtils 
	Dim p As PhoneIntents 
	Dim lstOne As ListView 
	Dim AdView1 as AdView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.Title = "About"
	Activity.Color = Colors.RGB (235,235,235)
	
	Dim imvLogo As ImageView 
	imvLogo.Initialize ("")
	imvLogo.Bitmap = LoadBitmap(File.DirAssets , "logo.png")
	imvLogo.Gravity = Gravity.FILL 
	Activity.AddView ( imvLogo , 50%x - 50dip  , 20dip ,  100dip  ,  100dip )
	
	Dim lblName As  Label 
	Dim bg As ColorDrawable 
	bg.Initialize (Colors.DarkGray , 10dip)
	lblName.Initialize ("")
	lblName.Background = bg
	lblName.Gravity = Gravity.CENTER 
	lblName.Text = "SMS Unlocker"
	lblName.TextSize = 13
	lblName.TextColor = Colors.White 
	Activity.AddView (lblName , 100%x / 2 - 90dip , 130dip , 180dip , 50dip)
	lblName.Height = su.MeasureMultilineTextHeight (lblName, lblName.Text ) + 5dip
	
	
	Dim c As ColorDrawable 
	c.Initialize (Colors.White , 10dip )
	lstOne.Initialize ("lstOnes")
	lstOne.Background = c
	lstOne.SingleLineLayout .Label.TextSize = 12
	lstOne.SingleLineLayout .Label .TextColor = Colors.DarkGray 
	lstOne.SingleLineLayout .Label .Gravity = Gravity.CENTER 
	lstOne.SingleLineLayout .ItemHeight = 40dip
	lstOne.AddSingleLine2 ("Developed By : Khun Htetz Naing    ", 1)
	lstOne.AddSingleLine2 ("Powered By : Myanmar Android Apps    ",2)
	lstOne.AddSingleLine2 ("Facebook : www.facebook.com/MmFreeAndroidApps  ", 3)
	lstOne.AddSingleLine2 ("Website : www.HtetzNaing.com",4)
	Activity.AddView ( lstOne, 30dip , 170dip , 100%x -  60dip, 162dip)
		
	AdView1.Initialize2("AdView1","ca-app-pub-4173348573252986/4080989757",AdView1.SIZE_SMART_BANNER)
	Dim height As Int
	If GetDeviceLayoutValues.ApproximateScreenSize < 6 Then
		'phones
		If 100%x > 100%y Then height = 32dip Else height = 50dip
	Else
		'tablets
		height = 90dip
	End If
	Activity.AddView(AdView1, 0dip, 100%y - height, 100%x, height)
	AdView1.LoadAd
End Sub

Sub lblCredit_Click
	Try
 
		Dim Facebook As Intent
 
		Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
		StartActivity(Facebook)
 
	Catch
 
		Dim i As Intent
		i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
		StartActivity(i)
 
	End Try
End Sub
Sub Activity_Resume
     
End Sub

Sub Activity_Pause (UserClosed As Boolean)
     
End Sub

Sub lstOnes_ItemClick (Position As Int, Value As Object)
     Select Value
		Case 1
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://profile/100006126339714")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MgHtetzNaing")
 
				StartActivity(i)
 
			End Try
		Case 2
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
 
			End Try
		Case 3
			Try
 
				Dim Facebook As Intent
 
				Facebook.Initialize(Facebook.ACTION_VIEW, "fb://page/627699334104477")
				StartActivity(Facebook)
 
			Catch
 
				Dim i As Intent
				i.Initialize(i.ACTION_VIEW, "https://m.facebook.com/MmFreeAndroidApps")
 
				StartActivity(i)
 
			End Try
			Case 4
				StartActivity(p.OpenBrowser("http://www.htetznaing.com"))
	 End Select
End Sub


