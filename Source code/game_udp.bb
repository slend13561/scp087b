;TODO:
; - !lenny
; - Add customizable player speed
; - Make monsters unable to spawn right next to players
; - Make visible monsters slightly faster to compensate for the nerf
; - Add monster teleportation
; - Add configurable controls
; - Add "behind you" taunt (b)
; - Add clipboard support
; - Improve input boxes (left and right arrow keys to move the cursor)
; - Let servers select their own port instead of forcing them to use 8730
; - Let clients connect to any port
; - Add player list (hold tab to see?)

Const GAME_SOLO%=0
Const GAME_SERVER%=1
Const GAME_CLIENT%=2

Const PLAYER_CLASSD%=0
Const PLAYER_MENTAL%=1
Const PLAYER_REDMIST%=2

Const UDP_FREQUENCY = 20

Global ChatLogFile% = 0
;Const PLAYER_DIE%=0
;Const PLAYER_MOVE%=1
;Const PLAYER_JOIN%=2

;Launcher
Graphics3D 640,480,32,2

AppTitle "SCP-087-B"

Global MapString$
MapString = ""

Global SelectedInputBox = 0

Global MenuFont1%,MenuFont2%
Global MouseHit1%,MouseHit2%
Global PointerImg%
Global launchertex%
MenuFont1 = LoadFont("verdana",16)

Dim FloorEntities%(1)

Type floorWaypointSet
	Field x#[10]
	Field z#[10]
	
	Field angle1#[10]
	Field angle2#[10]
	
	Field ents%[10]
End Type

Dim FloorWaypoints.floorWaypointSet(128)


FloorWaypoints(0)=New floorWaypointSet
FloorWaypoints(0)\x[0]=0.477725
FloorWaypoints(0)\z[0]=-6.60918
FloorWaypoints(0)\angle1[0]=360.0
FloorWaypoints(0)\angle2[0]=360.0

FloorWaypoints(1)=New floorWaypointSet
FloorWaypoints(1)\x[0]=7.45225
FloorWaypoints(1)\z[0]=-6.60161
FloorWaypoints(1)\angle1[0]=86.7004
FloorWaypoints(1)\angle2[0]=86.7004
FloorWaypoints(1)\x[1]=0.500868
FloorWaypoints(1)\z[1]=-6.29742
FloorWaypoints(1)\angle1[1]=2.10038
FloorWaypoints(1)\angle2[1]=2.10038

FloorWaypoints(2)=New floorWaypointSet
FloorWaypoints(2)\x[0]=7.44111
FloorWaypoints(2)\z[0]=-6.51792
FloorWaypoints(2)\angle1[0]=87.6004
FloorWaypoints(2)\angle2[0]=87.6004
FloorWaypoints(2)\x[1]=5.49305
FloorWaypoints(2)\z[1]=-6.70282
FloorWaypoints(2)\angle1[1]=176.7
FloorWaypoints(2)\angle2[1]=176.7
FloorWaypoints(2)\x[2]=5.32103
FloorWaypoints(2)\z[2]=-10.4728
FloorWaypoints(2)\angle1[2]=129.6
FloorWaypoints(2)\angle2[2]=129.6
FloorWaypoints(2)\x[3]=4.11701
FloorWaypoints(2)\z[3]=-11.5982
FloorWaypoints(2)\angle1[3]=89.1004
FloorWaypoints(2)\angle2[3]=89.1004
FloorWaypoints(2)\x[4]=1.44579
FloorWaypoints(2)\z[4]=-11.4232
FloorWaypoints(2)\angle1[4]=-3.5996
FloorWaypoints(2)\angle2[4]=-3.5996
FloorWaypoints(2)\x[5]=1.46896
FloorWaypoints(2)\z[5]=-6.68307
FloorWaypoints(2)\angle1[5]=47.1004
FloorWaypoints(2)\angle2[5]=47.1004
FloorWaypoints(2)\x[6]=0.348719
FloorWaypoints(2)\z[6]=-5.75582
FloorWaypoints(2)\angle1[6]=3.00042
FloorWaypoints(2)\angle2[6]=3.00042

FloorWaypoints(3)=New floorWaypointSet
FloorWaypoints(3)\x[0]=7.53462
FloorWaypoints(3)\z[0]=-9.89649
FloorWaypoints(3)\angle1[0]=75.2999
FloorWaypoints(3)\angle2[0]=75.2999
FloorWaypoints(3)\x[1]=2.08849
FloorWaypoints(3)\z[1]=-8.45393
FloorWaypoints(3)\angle1[1]=89.4004
FloorWaypoints(3)\angle2[1]=89.4004
FloorWaypoints(3)\x[2]=0.46489
FloorWaypoints(3)\z[2]=-8.32402
FloorWaypoints(3)\angle1[2]=-1.19963
FloorWaypoints(3)\angle2[2]=-1.19963

FloorWaypoints(4)=New floorWaypointSet
FloorWaypoints(4)\x[0]=7.53804
FloorWaypoints(4)\z[0]=-7.63233
FloorWaypoints(4)\angle1[0]=218.1
FloorWaypoints(4)\angle2[0]=148.2
FloorWaypoints(4)\x[1]=8.52101
FloorWaypoints(4)\z[1]=-9.30065
FloorWaypoints(4)\angle1[1]=180.601
FloorWaypoints(4)\angle2[1]=180.601
FloorWaypoints(4)\x[2]=8.43972
FloorWaypoints(4)\z[2]=-15.5573
FloorWaypoints(4)\angle1[2]=90.6005
FloorWaypoints(4)\angle2[2]=90.6005
FloorWaypoints(4)\x[3]=0.432063
FloorWaypoints(4)\z[3]=-15.4977
FloorWaypoints(4)\angle1[3]=359.701
FloorWaypoints(4)\angle2[3]=359.701

FloorWaypoints(5)=New floorWaypointSet
FloorWaypoints(5)\x[0]=7.57936
FloorWaypoints(5)\z[0]=-7.35415
FloorWaypoints(5)\angle1[0]=148.2
FloorWaypoints(5)\angle2[0]=218.1
FloorWaypoints(5)\x[1]=6.52312
FloorWaypoints(5)\z[1]=-9.08641
FloorWaypoints(5)\angle1[1]=-179.4
FloorWaypoints(5)\angle2[1]=-179.4
FloorWaypoints(5)\x[2]=6.51048
FloorWaypoints(5)\z[2]=-12.5392
FloorWaypoints(5)\angle1[2]=89.3998
FloorWaypoints(5)\angle2[2]=89.3998
FloorWaypoints(5)\x[3]=0.480379
FloorWaypoints(5)\z[3]=-12.2509
FloorWaypoints(5)\angle1[3]=-2.40018
FloorWaypoints(5)\angle2[3]=-2.40018

FloorWaypoints(6)=New floorWaypointSet
FloorWaypoints(6)\x[0]=7.53383
FloorWaypoints(6)\z[0]=-6.53836
FloorWaypoints(6)\angle1[0]=90.9003
FloorWaypoints(6)\angle2[0]=90.9003
FloorWaypoints(6)\x[1]=7.56575
FloorWaypoints(6)\z[1]=-8.57009
FloorWaypoints(6)\angle1[1]=90.9003
FloorWaypoints(6)\angle2[1]=90.9003
FloorWaypoints(6)\x[2]=7.59141
FloorWaypoints(6)\z[2]=-10.5219
FloorWaypoints(6)\angle1[2]=89.1003
FloorWaypoints(6)\angle2[2]=89.1003
FloorWaypoints(6)\x[3]=0.452351
FloorWaypoints(6)\z[3]=-10.4933
FloorWaypoints(6)\angle1[3]=-1.19968
FloorWaypoints(6)\angle2[3]=-1.19968
FloorWaypoints(6)\x[4]=0.496736
FloorWaypoints(6)\z[4]=-8.37375
FloorWaypoints(6)\angle1[4]=-1.19968
FloorWaypoints(6)\angle2[4]=-1.19968
FloorWaypoints(6)\x[5]=0.536933
FloorWaypoints(6)\z[5]=-6.45416
FloorWaypoints(6)\angle1[5]=-1.19968
FloorWaypoints(6)\angle2[5]=-1.19968

FloorWaypoints(7)=New floorWaypointSet
FloorWaypoints(7)\x[0]=7.62923
FloorWaypoints(7)\z[0]=-9.44652
FloorWaypoints(7)\angle1[0]=-87.4199
FloorWaypoints(7)\angle2[0]=-87.4199
FloorWaypoints(7)\x[1]=10.5374
FloorWaypoints(7)\z[1]=-9.67154
FloorWaypoints(7)\angle1[1]=-180.0
FloorWaypoints(7)\angle2[1]=-180.0
FloorWaypoints(7)\x[2]=10.3884
FloorWaypoints(7)\z[2]=-14.5735
FloorWaypoints(7)\angle1[2]=89.4001
FloorWaypoints(7)\angle2[2]=89.4001
FloorWaypoints(7)\x[3]=4.47687
FloorWaypoints(7)\z[3]=-14.5516
FloorWaypoints(7)\angle1[3]=-0.29991
FloorWaypoints(7)\angle2[3]=-0.29991
FloorWaypoints(7)\x[4]=4.41257
FloorWaypoints(7)\z[4]=-9.56716
FloorWaypoints(7)\angle1[4]=91.8
FloorWaypoints(7)\angle2[4]=91.8
FloorWaypoints(7)\x[5]=2.52773
FloorWaypoints(7)\z[5]=-9.59552
FloorWaypoints(7)\angle1[5]=178.2
FloorWaypoints(7)\angle2[5]=178.2
FloorWaypoints(7)\x[6]=2.36552
FloorWaypoints(7)\z[6]=-12.5101
FloorWaypoints(7)\angle1[6]=87.9
FloorWaypoints(7)\angle2[6]=87.9
FloorWaypoints(7)\x[7]=0.457288
FloorWaypoints(7)\z[7]=-12.439
FloorWaypoints(7)\angle1[7]=-0.300004
FloorWaypoints(7)\angle2[7]=-0.300004

FloorWaypoints(127)=New floorWaypointSet
FloorWaypoints(127)\x[0]=7.46688
FloorWaypoints(127)\z[0]=-6.49421
FloorWaypoints(127)\angle1[0]=91.5004
FloorWaypoints(127)\angle2[0]=91.5004
FloorWaypoints(127)\x[1]=0.48451
FloorWaypoints(127)\z[1]=-6.40802
FloorWaypoints(127)\angle1[1]=358.2
FloorWaypoints(127)\angle2[1]=358.2

Global screenwidth = GetINIInt("options.ini","options","width")
Global screenheight = GetINIInt("options.ini","options","height")
Global colordepth = GetINIInt("options.ini","options","colordepth")
Global fullscreen = max(GetINIInt("options.ini","options","fullscreen"),(Not Windowed3D()))
Global InvertMouse =  GetINIInt("options.ini","options","invert mouse y")
Global Brightness=max(min(GetINIInt("options.ini","options","brightness"),255),0)

SetBuffer BackBuffer()

SetFont MenuFont1
ClsColor 0,0,0
Cls
Color 255,255,255
Text 320,240,"Checking for updates...",True,True
Flip
Download("http://undertowgames.com/forum/viewtopic.php?f=9&t=4742","tmp","thread.tmp")
If FileType("tmp/thread.tmp")=1 Then
	Local threadFile% = ReadFile("tmp/thread.tmp")
	If threadFile<>0 Then
		While Not Eof(threadFile)
			Local ln$ = ReadLine(threadFile)
			If Instr(ln,"##LATEST_VERSION") Then
				CloseFile threadFile
				threadFile = 0
				DeleteFile "tmp/thread.tmp"
				DeleteDir "tmp"
				Local latestVersion$ = Mid(ln,Instr(ln,"##LATEST_VERSION")+16,Instr(ln,"##",Instr(ln,"##LATEST_VERSION")+16)-(Instr(ln,"##LATEST_VERSION")+16))
				If latestVersion<>"v0.4" Then
					While True
						MouseHit1 = MouseHit(1)
						Cls
						Color 255,255,255
						Text 320,200,"An update is available: "+latestVersion,True,True
						Text 320,220,"What would you like to do?",True,True
						Color 30,30,30
						If Button(200,250,240,20,"Get download link") Then
							ExecFile("http://undertowgames.com/forum/viewtopic.php?f=9&t=4742")
							End
						EndIf
						Color 30,30,30
						If Button(200,275,240,20,"Ignore") Then
							Exit
						EndIf
						Flip
					Wend
				EndIf
				Exit
			EndIf
		Wend
		If threadFile<>0 Then
			CloseFile threadFile
			DeleteFile "tmp/thread.tmp"
			DeleteDir "tmp"
		EndIf
	EndIf
EndIf

Local selectedGFXmode%
selectedGFXmode=1

For i=1 To CountGfxModes3D()
	If (GfxMode3D(i) And GfxModeDepth(i)=colordepth) Then
		If (GfxModeWidth(i)>=screenwidth And GfxModeHeight(i)>=screenheight) Then
			selectedGFXmode=i
			Exit
		EndIf
		selectedGFXmode=i
	EndIf
Next
screenwidth=GfxModeWidth(selectedGFXmode)
screenheight=GfxModeHeight(selectedGFXmode)
PutINIValue("options.ini","options","width",Str(screenwidth))
PutINIValue("options.ini","options","height",Str(screenheight))



launchertex = LoadImage("GFX\launcher.jpg")


While True
	Local StrTemp$
	SetFont MenuFont1
	MouseHit1 = MouseHit(1)
	MouseHit2 = MouseHit(2)
	Cls
	DrawImage launchertex,0,0
	Color 255,255,255
	
	Local dx%,dy%
	dx = 0 : dy = 0
	
	Color 0,0,0
	Text 36,101,"Resolution:"
	Color 255,255,255
	Text 35,100,"Resolution:"
	
	For i=1 To CountGfxModes3D()
		If (GfxMode3D(i)) Then
			If (GfxModeDepth(i)=colordepth) Then
				If selectedGFXmode = i Then Color 30,30,100 Else Color 30,30,30
				If (Button(dx+35,dy+120,100,20,Str(GfxModeWidth(i))+"x"+Str(GfxModeHeight(i)),False)) Then
					selectedGFXmode=i
					screenwidth=GfxModeWidth(i)
					screenheight=GfxModeHeight(i)
					PutINIValue("options.ini","options","width",Str(screenwidth))
					PutINIValue("options.ini","options","height",Str(screenheight))
				EndIf
				dy=dy+20
				If (dy>=300) Then
					dx=dx+100
					dy=0
				EndIf
			EndIf
		EndIf
	Next
	
	Color 0,0,0
	Text 36,480-53,"Brightness:"
	Color 255,255,255
	Text 35,480-54,"Brightness:"
	
	Rect 35,480-22,255,5,False
	Color 0,0,0
	Rect 34,480-23,257,7,False
	
	Color 0,0,50
	Button(Brightness+20,480-30,30,20,Str(Brightness))
	If (MouseX()>=35 And MouseX()<=35+255 And MouseY()>=480-30 And MouseY()<=480-10 And MouseDown(1)) Then
		Brightness=MouseX()-35
		PutINIValue("options.ini","options","brightness",Str(Brightness))
	EndIf
	
	;Color 30,100,200
	;Rect(Brightness+30,480-30,8,20,False)
	;Color 30,30,100
	;Rect(Brightness+31,480-29,6,18,True)
	
	Color 0,0,0
	Text 640-129,480-95,"Color depth:"
	If fullscreen Then Color 255,255,255 Else Color 100,100,100
	Text 640-130,480-96,"Color depth:"
	Color 30,30,30
	If (Button(640-46,480-100,44,24,Str(colordepth)+"-bit",(Not fullscreen))) Then
		If (colordepth=32) Then
			colordepth=16
		Else
			colordepth=32
		EndIf
		PutINIValue("options.ini","options","colordepth",Str(colordepth))
		For i=1 To CountGfxModes3D()
			If (GfxMode3D(i) And GfxModeDepth(i)=colordepth) Then
				If (GfxModeWidth(i)>=screenwidth And GfxModeHeight(i)>=screenheight) Then
					selectedGFXmode=i
					Exit
				EndIf
				selectedGFXmode=i
			EndIf
		Next
	EndIf
	
	Color 0,0,0
	Text 640-129,480-65,"Mode:"
	Color 255,255,255
	Text 640-130,480-66,"Mode:"
	Color 30,30,30
	If (fullscreen) Then StrTemp = "Fullscreen" Else StrTemp = "Window"
	If (Button(640-84,480-70,82,24,StrTemp,(Not Windowed3D()))) Then
		fullscreen=(Not fullscreen)
		PutINIValue("options.ini","options","fullscreen",Str(fullscreen))
	EndIf
	
	Color 30,30,30
	If Button(640-100,480-32,94,24,"PLAY") Then Flip : Delay 8 : Exit
	Color 30,30,30
	If Button(640-200,480-32,94,24,"EXIT") Then Flip : Delay 8 : End
	
	Color 255,255,255
	
	Flip
Wend

;f = ReadFile("options.txt")

If fullscreen Then
	Graphics3D (screenwidth,screenheight,colordepth)
Else
	Graphics3D (screenwidth,screenheight,colordepth,2)
EndIf

PointerImg = LoadImage("GFX\cursor.PNG")
MaskImage PointerImg,0,0,0
MenuFont1 = LoadFont("verdana",16)
MenuFont2 = LoadFont("GFX\pretext.ttf",64)

AppTitle "SCP-087-B"
	
AntiAlias True 
HidePointer 
SetBuffer BackBuffer()

Global font1=LoadFont( "GFX\Courier.ttf",18 ) 
Global font=LoadFont( "GFX\pretext.ttf",128 ) 

Const hit_map = 1
Const hit_map2 = 5
Const hit_monster = 2
Const hit_friendly = 3
Const hit_invisible = 4

; -- Viewport.
Global viewport_center_x = GraphicsWidth () / 2
Global viewport_center_y = GraphicsHeight () / 2
;^^^^^^

; -- Mouselook.
Global mouselook_x_inc# = 0.3 ; This sets both the sensitivity and direction (+/-) of the mouse on the X axis.
Global mouselook_y_inc# = 0.3 ; This sets both the sensitivity and direction (+/-) of the mouse on the Y axis.
Global mouse_left_limit = 250 ; Used to limit the mouse movement to within a certain number of pixels (250 is used here) from the center of the screen. This produces smoother mouse movement than continuously moving the mouse back to the center each loop.
Global mouse_right_limit = GraphicsWidth () - 250 ; As above.
Global mouse_top_limit = 250 ; As above.
Global mouse_bottom_limit = GraphicsHeight () - 250 ; As above.
;^^^^^^

; -- Mouse smoothing que.
Global mouse_x_speed_1#, mouse_x_speed_2#,mouse_x_speed_3#,mouse_x_speed_4#,mouse_x_speed_5#
Global mouse_y_speed_1#,mouse_y_speed_2#,mouse_y_speed_3#,mouse_y_speed_4#,mouse_y_speed_5#

; -- User.
Global user_camera_pitch#
Global up#, side#

Global DropSpeed#
;^^^^^^

Global flooramount = 210
Dim FloorActions(flooramount)
Dim FloorTimer(flooramount)

Type ENEMIES
	Field obj 		; objekti
	Field collider	; "törmäyspallo"
	Field yspeed#	;pudotusnopeus
	Field speed#
	Field oldX 
	Field oldZ
	Field texture$
	Field inst%
End Type

Type GLIMPSES
	Field obj
End Type 

Global ID% = 0

Global camera
camera = CreateCamera()
CameraRange camera, 0.001, 3.0
CameraFogMode camera, 1
CameraFogRange camera, 1, 2.5
CameraFogColor camera,0,0,0
microphone=CreateListener(camera) ; Create listener, make it child of camera

Global BlurTimer
Include "dreamfilter.bb"
CreateBlurImage()

Global PlayerFloor, KillTimer%

AmbientLight Brightness,Brightness,Brightness

;Dim FloorNumberTexture(flooramount+1)
Global brickwalltexture = LoadTexture("GFX\brickwall.jpg")
Global sign = LoadImage("GFX\sign.jpg")
Global map, map1, map2, map3


Global CurrEnemy.ENEMIES, CurrObject, SoundEmitter = CreatePivot()
ScaleEntity SoundEmitter, 0.1,0.1,0.1

Const ACT_STEPS = 1, ACT_LIGHTS = 2, ACT_FLASH = 3, ACT_WALK = 4, ACT_RUN = 5, ACT_KALLE = 6, ACT_BREATH = 7
Const ACT_PROCEED = 8, ACT_TRAP=9, ACT_173 = 11, ACT_CELL = 12, ACT_LOCK = 13
Const ACT_RADIO2 = 15, ACT_RADIO3 = 16, ACT_RADIO4 = 17, ACT_TRICK1 = 18, ACT_TRICK2 = 19
Const ACT_ROAR = 20, ACT_DARKNESS = 21

Collisions hit_monster,hit_map, 2,3
Collisions hit_friendly,hit_map, 2,2
		
Global stepsound=Load3DSound("SFX\step.wav") ; Load 3D sound
Global loudstepsound=Load3DSound("SFX\loudstep.ogg") ; Load 3D sound
Dim HorrorSFX(3)
For i = 0 To 2
	HorrorSFX(i)=LoadSound("SFX\horror"+(i+1)+".ogg")
Next
Global DeathSFX = LoadSound("SFX\death.ogg")
Global RoarSFX = LoadSound("SFX\roar.ogg")
Global BreathSFX = LoadSound("SFX\breath.ogg")
Global StoneSFX = LoadSound("SFX\stone.ogg")
Global NoSFX = LoadSound("SFX\no.ogg")


Dim AmbientSFX(10)
For i = 0 To 8
	AmbientSFX(i)=Load3DSound("SFX\ambient"+(i+1)+".ogg")
Next

Global DontlookSFX = LoadSound("SFX\dontlook.ogg")
Dim RadioSFX(5)
For i = 0 To 3
	RadioSFX(i)=LoadSound("SFX\radio"+(i+1)+".ogg")
Next

Global Music = LoadSound("SFX\music.ogg"), MusicChannel

Global FireOn = LoadSound("SFX\match.ogg")
Global FireOff = LoadSound("SFX\fireout.ogg")

;Global mentalmesh = LoadAnimMesh("GFX\mental.b3d")
Global PlayerMesh% = LoadAnimMesh("GFX\player.b3d")
Global PlayerTex% = LoadTexture("GFX\player.jpg")
Global mental = LoadTexture("GFX\mental.jpg")
Global tex173 = LoadTexture("GFX\173.jpg")

Dim GlimpseTextures(5)
GlimpseTextures(0)= LoadTexture("GFX\glimpse1.png",1+2)
GlimpseTextures(1)= LoadTexture("GFX\glimpse2.png",1+2)

;Collisions hit_monster,hit_monster, 1,3
Collisions hit_friendly,hit_monster, 1,3
Collisions hit_monster,hit_friendly, 1,3
Collisions hit_monster,hit_map, 2,2 
Collisions hit_invisible,hit_map2, 2,2 

Global Seed% = MilliSecs()
Global MapResult%

SeedRnd Seed
MapResult = CreateMap(flooramount)
CreateGlimpses()

;damnCube% = CreateCube()
;ScaleMesh damnCube,0.1,0.1,0.1
;EntityOrder damnCube,-3

;FloorWaypoints(0)\ents[0] = CopyEntity(damnCube)

;FloorWaypoints(1)\ents[0] = CopyEntity(damnCube)
;FloorWaypoints(1)\ents[1] = CopyEntity(damnCube)

;FloorWaypoints(2)\ents[0] = CopyEntity(damnCube)
;FloorWaypoints(2)\ents[1] = CopyEntity(damnCube)
;FloorWaypoints(2)\ents[2] = CopyEntity(damnCube)
;FloorWaypoints(2)\ents[3] = CopyEntity(damnCube)
;FloorWaypoints(2)\ents[4] = CopyEntity(damnCube)
;FloorWaypoints(2)\ents[5] = CopyEntity(damnCube)
;FloorWaypoints(2)\ents[6] = CopyEntity(damnCube)

;FloorWaypoints(3)\ents[0] = CopyEntity(damnCube)
;FloorWaypoints(3)\ents[1] = CopyEntity(damnCube)
;FloorWaypoints(3)\ents[2] = CopyEntity(damnCube)

;FloorWaypoints(4)\ents[0] = CopyEntity(damnCube)
;FloorWaypoints(4)\ents[1] = CopyEntity(damnCube)
;FloorWaypoints(4)\ents[2] = CopyEntity(damnCube)
;FloorWaypoints(4)\ents[3] = CopyEntity(damnCube)

;FloorWaypoints(6)\ents[0] = CopyEntity(damnCube)
;FloorWaypoints(6)\ents[1] = CopyEntity(damnCube)
;FloorWaypoints(6)\ents[2] = CopyEntity(damnCube)
;FloorWaypoints(6)\ents[3] = CopyEntity(damnCube)
;FloorWaypoints(6)\ents[4] = CopyEntity(damnCube)
;FloorWaypoints(6)\ents[5] = CopyEntity(damnCube)

;FloorWaypoints(127)\ents[0] = CopyEntity(damnCube)
;FloorWaypoints(127)\ents[1] = CopyEntity(damnCube)

;FreeEntity damnCube

Global collider = CreatePivot()
PositionEntity collider,-2.5,-1.3,-0.5
EntityType collider, hit_friendly
EntityRadius collider, 0.3

framelimit = 60

MusicChannel = PlaySound(Music)

;-------------------------------------------------------------------------------
;-------------------------------------------------------------------------------

Global Server%,Stream%,PlayState%,IncomingStream%,PlayerCount%,PlayerID%,UDPSend%,LastMsgID%,SentMsgID%
Global PlayerName$,ServerAddress$
ServerAddress = "127.0.0.1"
Global SentChatMsg$,SentChatMsgID%
Global TypedChatMsg$,ChatOpen%
Global AdminSpyChat%
Global RecvChatID%

Global PlayingAs%
Global SpookTimer%,SpookCooldown%,SpookCount%,VisibilityTimer%,LastRecvSpook%,LastSentSpook%

LastMsgID = 0

Global MoveForw%,MoveBack%,MoveLeft%,MoveRight%

Global DirListShift%
Global DirList%[60]
Global DirListAngle#[60]
Global MemX#[60]
Global MemY#[60]
Global MemZ#[60]

Type Player
	Field IP%,Port%,Pivot%
	Field FallSpeed#
	Field MLeft%,MRight%,MForw%,MBack%,MClick1%,MClick2%
	Field Name$
	Field DirList%[60]
	Field DirListAngle#[60]
	Field DirsToRead%
	Field LastMsgTime%,LastClientMsg%
	Field TexturePath$
	Field LoadedTexture%
	Field LastChatMsgID%
	Field LastRecvChatMsg%
	Field Connected%
	Field PlayingAs%
	Field SpookCount%
	Field SpookCooldown%
	Field VisibilityTimer%
	Field LastSpookSent%
	Field IsVisible%
	Field KillTimer%
	Field IsBot%
	Field BotAngle#
	Field LastChosenAngle%
End Type

Dim Players.Player(32)
;Dim Player_Names$(4)
;Dim Player_Speed#(4)

PlayerCount = 0

PlayerID = 0

PlayState = GAME_SOLO

UDPSend = 0

Cls

SetBuffer BackBuffer()
ClsColor 0,0,0
Cls
Local tempImg%
tempImg = LoadImage("GFX\scp.jpg")
DrawImage(tempImg,(GraphicsWidth()/2)-(ImageWidth(tempImg)/2),(GraphicsHeight()/2)-(ImageHeight(tempImg)/2))
Flip
Delay 3000
FreeImage tempImg

Global Paused = False

Global KeyHit1% = False

Color 255,255,255
While True
	Select PlayingAs
		Case PLAYER_CLASSD
			CameraFogRange camera, 1, 2.5
			CameraRange camera, 0.001, 3.0
			CameraFogColor camera,0,0,0
			AmbientLight Brightness,Brightness,Brightness
			EntityType collider,hit_friendly
			For i=(PlayState=GAME_SERVER) To 31
				If Players(i)<>Null Then
					If Players(i)\PlayingAs<>PLAYER_CLASSD And Players(i)\VisibilityTimer<0 Then
						If PlayState=GAME_SERVER Then EntityAlpha(GetChild(Players(i)\Pivot,1),0.5*AdminSpyChat)
					Else
						EntityAlpha(GetChild(Players(i)\Pivot,1),1.0)
						If EntityVisible(collider,Players(i)\Pivot) And EntityDistance(collider,Players(i)\Pivot)<4.0 Then BlurTimer = 200
					EndIf
				EndIf
			Next
		Case PLAYER_MENTAL,PLAYER_REDMIST
			CameraFogRange camera, 2, 50
			CameraRange camera, 0.001, 55.0
			If VisibilityTimer<=0 Then
				CameraFogColor camera,50,50,255
				AmbientLight 150,150,255
				EntityType collider,hit_invisible
			Else
				CameraFogColor camera,150,0,0
				AmbientLight 255,50,50
				EntityType collider,hit_monster
			EndIf
			For i=(PlayState=GAME_SERVER) To 31
				If Players(i)<>Null Then
					If Players(i)\PlayingAs<>PLAYER_CLASSD And Players(i)\VisibilityTimer<0 Then
						EntityAlpha(GetChild(Players(i)\Pivot,1),0.5)
					Else
						EntityAlpha(GetChild(Players(i)\Pivot,1),1.0)
					EndIf
				EndIf
			Next
	End Select
	
	MouseHit1 = MouseHit(1)
	MouseHit2 = MouseHit(2)
	
	Local isForw%,posx#,posy#,posz#,pyaw#,isFloor% ;isBack%,isLeft%,isRight%,
	Local getconn%
	If PlayState<>GAME_SOLO Then
		If (MilliSecs()>=UDPSend) Then
			UDPSend=MilliSecs()+UDP_FREQUENCY
		EndIf
	EndIf
	
	If PlayState=GAME_SERVER Then
		Players(0)\PlayingAs = PlayingAs
	
		getconn = RecvUDPMsg(Server) ;AcceptTCPStream(Server)
		While getconn ;The server has received a message from a client
			TempRead$ = ReadLine(Server)
			If TempRead="joined" Then ;the player is new to the server, initialize it
				For giveID=1 To 32
					If Players(giveID)<>Null Then
						If (Players(giveID)\IP = getconn) And (Players(giveID)\Port = UDPMsgPort(Server)) Then
							If Players(giveID)\Connected Then giveID = -1
							Exit
						EndIf
					EndIf
				Next
				If giveID>-1 Then
					For giveID=1 To 32
						If giveID >=32 Then giveID = -1 : Exit
						If (Players(giveID)=Null) Then Exit
					Next
				EndIf
				If giveID<32 And giveID>0 Then ;server can accept another player
					If Players(giveID)=Null Then
						Players(giveID)=New Player
						Players(giveID)\IP=getconn
						Players(giveID)\Port=UDPMsgPort(Server)
						Players(giveID)\Pivot=CreatePivot()
						Local tempMesh% = CopyEntity(PlayerMesh)
						ScaleEntity tempMesh, 0.15,0.15,0.15
						PositionEntity tempMesh,0.0,-0.3,0.0,True
						EntityParent tempMesh,Players(giveID)\Pivot
						EntityTexture tempMesh,PlayerTex
						PositionEntity Players(giveID)\Pivot,-2.5,-1.3,-0.5,True
						EntityRadius Players(giveID)\Pivot, 0.3
						Players(giveID)\PlayingAs = Rand(0,2)
						
						Select Players(giveID)\PlayingAs
							Case PLAYER_CLASSD
								EntityTexture GetChild(Players(giveID)\Pivot,1),PlayerTex
								EntityType Players(giveID)\Pivot, hit_friendly
							Case PLAYER_MENTAL
								EntityTexture GetChild(Players(giveID)\Pivot,1),mental
								EntityType Players(giveID)\Pivot, hit_invisible;monster
							Case PLAYER_REDMIST
								EntityTexture GetChild(Players(giveID)\Pivot,1),tex173
								EntityType Players(giveID)\Pivot, hit_invisible;monster
						End Select
					EndIf
					Players(giveID)\LastMsgTime=MilliSecs()
					Players(giveID)\Name = ReadLine(Server)
					;Players(giveID)\TexturePath = ReadLine(Server)
					;Download("http://i.imgur.com/"+Players(giveID)\TexturePath,"player_textures",Players(giveID)\TexturePath)
					;Players(giveID)\LoadedTexture = LoadTexture("player_textures/"+Players(giveID)\TexturePath)
					;If Players(giveID)\LoadedTexture<>0 Then
					;	EntityTexture GetChild(Players(giveID)\Pivot,1),Players(giveID)\LoadedTexture
					;EndIf
					WriteLine(Server,MapString) ;give the player the server's map arrangement
					WriteByte(Server,giveID) ;give the player their ID
					SendUDPMsg Server,getconn,UDPMsgPort(Server) ;give the message to the player
					PlayerCount=PlayerCount+1
				Else ;server is full
					WriteLine Server,"KICK" ;kick the player
					SendUDPMsg Server,getconn,UDPMsgPort(Server) ;tell the client to disconnect
				EndIf
			Else If TempRead="disconnect" Then ;player has "cleanly" disconnected
				TempID%=ReadByte(Server)
				If Players(TempID)<>Null Then ;this player exists: remove it
					If Players(TempID)\IP = getconn And Players(TempID)\Port = UDPMsgPort(Server) Then
						FreeEntity GetChild(Players(TempID)\Pivot,1)
						FreeEntity Players(TempID)\Pivot
						AddChatMsg(Players(TempID)\Name+" has disconnected.",255,255,0,TempID,True)
						PlayerCount=PlayerCount-1
						Delete Players(TempID)
						Players(TempID)=Null
					Else
						
					EndIf
				EndIf
			Else
				TempID%=Int(TempRead)
				If Players(TempID)<>Null Then ;player exists
					If Players(TempID)\IP = getconn Then
						If Players(TempID)\Connected = False Then
							AddChatMsg(Players(TempID)\Name+" has joined the server.",0,255,0,TempID,True)
						EndIf
						Players(TempID)\Connected = True
						Local newMsg%=ReadInt(Server)
						If Players(TempID)\LastClientMsg<newMsg Then
							Players(TempID)\LastMsgTime=MilliSecs()
							;[Block]
;							isForw = ReadByte(Server)
;							If isForw>=8 Then
;								isForw=isForw-8 : Players(TempID)\MRight = True
;							Else
;								Players(TempID)\MRight = False
;							EndIf
;							If isForw>=4 Then
;								isForw=isForw-4 : Players(TempID)\MLeft = True
;							Else
;								Players(TempID)\MLeft = False
;							EndIf
;							If isForw>=2 Then
;								isForw=isForw-2 : Players(TempID)\MBack = True
;							Else
;								Players(TempID)\MBack = False
;							EndIf
;							If isForw>=1 Then
;								isForw=isForw-1 : Players(TempID)\MForw = True
;							Else
;								Players(TempID)\MForw = False
;							EndIf
							;[End Block]
							For j%=0 To 59
								Players(TempID)\DirList[j] = ReadByte(Server)
								Players(TempID)\DirListAngle[j] = ReadFloat(Server)
							Next
							Players(TempID)\DirsToRead=max(Players(TempID)\DirsToRead+(newMsg-Players(TempID)\LastClientMsg),0)
							;pyaw# = ReadFloat(Server)
							;RotateEntity Players(TempID)\Pivot,0.0,pyaw,0.0,True
							Players(TempID)\LastClientMsg=newMsg
							Local chatID% = ReadInt(Server)
							Local chatMsg$ = ReadLine(Server)
							If chatID>Players(TempID)\LastChatMsgID Then
								Players(TempID)\LastChatMsgID = chatID
								If Len(chatMsg)>0 Then
									If Left(Lower(Trim(chatMsg)),7)="!mental" Then
										If Players(TempID)\PlayingAs<>PLAYER_CLASSD Then
											Players(TempID)\PlayingAs = PLAYER_MENTAL
											EntityTexture GetChild(Players(giveID)\Pivot,1),mental
										EndIf
									ElseIf Left(Lower(Trim(chatMsg)),8)="!redmist" Then
										If Players(TempID)\PlayingAs<>PLAYER_CLASSD Then
											Players(TempID)\PlayingAs = PLAYER_REDMIST
											EntityTexture GetChild(Players(TempID)\Pivot,1),tex173
										EndIf
									ElseIf Players(TempID)\PlayingAs=PLAYER_CLASSD Then
										AddChatMsg(Players(TempID)\Name+": "+chatMsg,50,70,255,TempID,False)
									Else
										AddChatMsg(Players(TempID)\Name+": "+chatMsg,150,0,0,TempID,True)
									EndIf
								EndIf
							EndIf
							Players(TempID)\LastRecvChatMsg = ReadInt(Server)
						EndIf
					EndIf
				EndIf
			EndIf
			getconn = RecvUDPMsg(Server)
		Wend
		
		For i=1 To 31
			isFloor=False
			If (Players(i)<>Null) Then
				If Not Players(i)\IsBot Then
					If Players(i)\Connected Then
						WriteInt(Server,SentMsgID)
						For j=0 To 31
							If (Players(j)<>Null) Then
								WriteByte(Server,j+1)
								WriteInt(Server,Players(j)\LastClientMsg)
								If Players(j)\Pivot<>collider Then
									WriteByte(Server,(Players(j)\MForw)+(Players(j)\MBack*2)+(Players(j)\MLeft*4)+(Players(j)\MRight*8))
									WriteByte(Server,Players(j)\PlayingAs)
									WriteByte(Server,(Players(j)\KillTimer)>0)
									WriteInt(Server,Players(j)\VisibilityTimer)
									If Players(i)\PlayingAs<>PLAYER_CLASSD Or Players(j)\PlayingAs=PLAYER_CLASSD Or Players(j)\VisibilityTimer>0 Then
										WriteFloat(Server,EntityX(Players(j)\Pivot,True))
										WriteFloat(Server,EntityY(Players(j)\Pivot,True))
										WriteFloat(Server,EntityZ(Players(j)\Pivot,True))
										WriteFloat(Server,EntityYaw(Players(j)\Pivot,True))
									Else
										WriteFloat(Server,0.0)
										WriteFloat(Server,100.0)
										WriteFloat(Server,0.0)
										WriteFloat(Server,0.0)
									EndIf
								Else
									WriteByte(Server,MoveForw+(MoveBack*2)+(MoveLeft*4)+(MoveRight*8))
									WriteByte(Server,PlayingAs)
									WriteByte(Server,KillTimer>0)
									WriteInt(Server,VisibilityTimer)
									If Players(i)\PlayingAs<>PLAYER_CLASSD Or PlayingAs=PLAYER_CLASSD Or VisibilityTimer>0 Then
										WriteFloat(Server,EntityX(collider,True))
										WriteFloat(Server,EntityY(collider,True))
										WriteFloat(Server,EntityZ(collider,True))
										WriteFloat(Server,EntityYaw(collider,True))
									Else
										WriteFloat(Server,0.0)
										WriteFloat(Server,100.0)
										WriteFloat(Server,0.0)
										WriteFloat(Server,0.0)
									EndIf
								EndIf
								
								;WriteLine(Server,Players(j)\TexturePath)
							Else
								WriteByte(Server,0)
							EndIf
						Next
						
	;					If CurrEnemy <> Null Then
	;						WriteByte(Server,1)
	;						WriteFloat(Server,EntityX(CurrEnemy\collider,True))
	;						WriteFloat(Server,EntityY(CurrEnemy\collider,True))
	;						WriteFloat(Server,EntityZ(CurrEnemy\collider,True))
	;						WriteLine(Server,CurrEnemy\texture)
	;						WriteFloat(Server,EntityYaw(CurrEnemy\collider,True))
	;					EndIf
	;					
	;					WriteByte(Server,0)
						
						WriteInt(Server,Players(i)\LastSpookSent)
						
						Local cm.ChatMessage
						Local messagesToSend% = 0
						For cm = Each ChatMessage
							If cm\ID>Players(i)\LastRecvChatMsg And cm\SendTo[i] Then messagesToSend=messagesToSend+1
						Next
						WriteByte(Server,messagesToSend)
						For cm = Each ChatMessage
							If cm\ID>Players(i)\LastRecvChatMsg And cm\SendTo[i] Then
								WriteInt(Server,cm\ID)
								WriteLine(Server,cm\Txt)
								WriteByte(Server,cm\R)
								WriteByte(Server,cm\G)
								WriteByte(Server,cm\B)
							EndIf
						Next
						
						SendUDPMsg Server,Players(i)\IP,Players(i)\Port
					EndIf
					
					pyaw = EntityYaw(Players(i)\Pivot,True)
					If Players(i)\KillTimer<=0 Then
						Local animmed% = Players(i)\DirList[0]<>0
						While Players(i)\DirsToRead>0
							isForw = Players(i)\DirList[Players(i)\DirsToRead]
							Players(i)\DirsToRead=Players(i)\DirsToRead-1
							If isForw>=32 Then
								isForw=isForw-32 : Players(i)\MClick2 = True
							Else
								Players(i)\MClick2 = False
							EndIf
							If isForw>=16 Then
								isForw=isForw-16 : Players(i)\MClick1 = True
							Else
								Players(i)\MClick1 = False
							EndIf
							If isForw>=8 Then
								isForw=isForw-8 : Players(i)\MRight = True
							Else
								Players(i)\MRight = False
							EndIf
							If isForw>=4 Then
								isForw=isForw-4 : Players(i)\MLeft = True
							Else
								Players(i)\MLeft = False
							EndIf
							If isForw>=2 Then
								isForw=isForw-2 : Players(i)\MBack = True
							Else
								Players(i)\MBack = False
							EndIf
							If isForw>=1 Then
								isForw=isForw-1 : Players(i)\MForw = True
							Else
								Players(i)\MForw = False
							EndIf
							
							Local speedMult# = 1.0
							If Players(i)\PlayingAs<>PLAYER_CLASSD And Players(i)\VisibilityTimer<0 Then
								speedMult = 2.0
							EndIf
							
							Players(i)\SpookCooldown=max(Players(i)\SpookCooldown-1,0)
							Players(i)\VisibilityTimer=max(Players(i)\VisibilityTimer-1,-1200)
							
							If Players(i)\VisibilityTimer+1>0 And Players(i)\VisibilityTimer<=0 Then
								EntityType Players(i)\Pivot,hit_invisible
							EndIf
							
							If Players(i)\VisibilityTimer<0 And Players(i)\SpookCount>0 Then Players(i)\SpookCount=3
							If Players(i)\VisibilityTimer<=-1200 Then Players(i)\SpookCount=0
							
							If Players(i)\PlayingAs<>PLAYER_CLASSD Then
								If Players(i)\MClick1 Then
									DebugLog "client lclick"
									If Players(i)\VisibilityTimer>0 Then
										For j=1 To 31
											If Players(j)<>Null Then
												If Players(j)\PlayingAs=PLAYER_CLASSD And i<>j Then
													Local vX# = EntityX(Players(j)\Pivot)-EntityX(Players(i)\Pivot)
													Local vZ# = EntityZ(Players(j)\Pivot)-EntityZ(Players(i)\Pivot)
													Local vLen# = Sqr((vX*vX)+(vZ*vZ))
													Local vYaw# = VectorYaw(vX/vLen,0.0,vZ/vLen)-Players(i)\DirListAngle[Players(i)\DirsToRead]
													While vYaw<-180.0
														vYaw=vYaw+360.0
													Wend
													While vYaw>=180.0
														vYaw=vYaw-360.0
													Wend
													;DebugLog "ANGLE "+Abs(vYaw)
													If EntityDistance(Players(j)\Pivot,Players(i)\Pivot)<0.8 And Abs(vYaw)<45 Then
														If Players(j)\KillTimer<=0 Then AddChatMsg(Players(i)\Name+" killed "+Players(j)\Name+"!",255,100,0,i,True)
														Players(j)\KillTimer = max(Players(j)\KillTimer,1)
													EndIf
												EndIf
											EndIf
										Next
										If PlayingAs=PLAYER_CLASSD Then
											Local vXb# = EntityX(collider)-EntityX(Players(i)\Pivot)
											Local vZb# = EntityZ(collider)-EntityZ(Players(i)\Pivot)
											Local vLenb# = Sqr((vXb*vXb)+(vZb*vZb))
											Local vYawb# = VectorYaw(vXb/vLenb,0.0,vZb/vLenb)-Players(i)\DirListAngle[Players(i)\DirsToRead]
											While vYawb<-180.0
												vYawb=vYawb+360.0
											Wend
											While vYaw>=180.0
												vYawb=vYawb-360.0
											Wend
											If EntityDistance(collider,Players(i)\Pivot)<0.8 And Abs(vYawb)<45 And KillTimer<=0 Then
												KillTimer = max(KillTimer,1)
												AddChatMsg(Players(i)\Name+" killed "+PlayerName+"!",255,100,0,i,True)
											EndIf
										EndIf
									EndIf
								EndIf
								
								If Players(i)\MClick2 Then
									DebugLog "client rclick"
									If Players(i)\SpookCount<3 And Players(i)\SpookCooldown<=0 Then
										Players(i)\SpookCount=Players(i)\SpookCount+1
										;Players(i)\SpookTimer=40
										Players(i)\SpookCooldown=95
										If Players(i)\VisibilityTimer<0 Then
											Players(i)\VisibilityTimer = 150
											EntityType Players(i)\Pivot,hit_monster
										Else
											Players(i)\VisibilityTimer=Players(i)\VisibilityTimer+150
										EndIf
										LastSentSpook=LastSentSpook+1
										For j=1 To 31
											If Players(j)<>Null Then
												If EntityDistance(Players(j)\Pivot,Players(i)\Pivot)<4.0 And EntityVisible(Players(j)\Pivot,Players(i)\Pivot) Then 
													Players(j)\LastSpookSent = LastSentSpook
												EndIf
											EndIf
										Next
										If EntityDistance(collider,Players(i)\Pivot)<4.0 And EntityVisible(collider,Players(i)\Pivot) Then 
											SpookTimer = 40
										EndIf
									EndIf
								EndIf
							EndIf
							
							If Players(i)\MForw Then MoveEntity Players(i)\Pivot,0,0,0.02*speedMult
							If Players(i)\MBack Then MoveEntity Players(i)\Pivot,0,0,-0.015*speedMult
							If Players(i)\MLeft Then MoveEntity Players(i)\Pivot,-0.008*speedMult, 0, 0
							If Players(i)\MRight Then MoveEntity Players(i)\Pivot,0.008*speedMult,0,0
							RotateEntity Players(i)\Pivot,0.0,Players(i)\DirListAngle[Players(i)\DirsToRead],0.0,True
							If Players(i)\MForw+Players(i)\MBack+Players(i)\MLeft+Players(i)\MRight>0 Then
								SetAnimTime GetChild(Players(i)\Pivot,1),AnimTime(GetChild(Players(i)\Pivot,1))+0.1
								If AnimTime(GetChild(Players(i)\Pivot,1)) >= 14.0 Then
									SetAnimTime GetChild(Players(i)\Pivot,1),0
								EndIf
								animmed = True
							Else
								SetAnimTime GetChild(Players(i)\Pivot,1),15
							EndIf
							
							Players(i)\FallSpeed = Players(i)\FallSpeed-0.004
								
							If Players(i)\FallSpeed < -0.18 And EntityY(Players(i)\Pivot,True)<-1 Then
								Players(i)\KillTimer=max(1,Players(i)\KillTimer)
								AddChatMsg(Players(i)\Name+" fell to their death!",255,0,0,i,True)
								Players(i)\FallSpeed = 0
							EndIf
							
							MoveEntity Players(i)\Pivot,0,Players(i)\FallSpeed,0	
							
							UpdateWorld ;the server needs to be good enough to run this multiple times
							
							isFloor = False
							For j=1 To CountCollisions(Players(i)\Pivot)
								If CollisionY(Players(i)\Pivot,j) < EntityY(Players(i)\Pivot) - 0.1 Then isFloor = True : Exit
							Next
							If isFloor = True Then
								If Players(i)\FallSpeed < -0.09 And EntityY(Players(i)\Pivot)<-1 Then
									Players(i)\KillTimer=max(1,Players(i)\KillTimer)
									AddChatMsg(Players(i)\Name+" fell to their death!",255,0,0,i,True)
								EndIf
								Players(i)\FallSpeed = 0
							Else
								
							EndIf
						Wend
						If Not animmed Then
							SetAnimTime GetChild(Players(i)\Pivot,1),15
						EndIf
					EndIf
					
					If Players(i)\KillTimer>0 Then
						Players(i)\DirsToRead = 0
						Players(i)\FallSpeed = 0.0
						Players(i)\SpookCooldown = 10.0
						Players(i)\KillTimer=min(Players(i)\KillTimer+1,100)
						If Players(i)\KillTimer>95 Then
							Local FloorZ#,FloorY#,StartX#,EndX#
							Local thisPlayerFloor% = (-EntityY(Players(i)\Pivot)-0.5)/2
							thisPlayerFloor=max(thisPlayerFloor/2,1)
							FloorY#=-(thisPlayerFloor-1)*2-1.0
							If Floor(thisPlayerFloor/2.0)=Ceil(thisPlayerFloor/2.0) Then ;parillinen
								FloorZ#=-6.54
								StartX# = 7.2 
								EndX# = 0.8
							Else ;pariton
								FloorZ#=-0.31
								StartX# = 0.8
								EndX# = 7.2
							EndIf
							
							PositionEntity Players(i)\Pivot,Rnd(StartX,EndX),FloorY,FloorZ,True
							ResetEntity Players(i)\Pivot
							If LinePick(EntityX(Players(i)\Pivot,True),EntityY(Players(i)\Pivot,True),EntityZ(Players(i)\Pivot,True),0.0,-40.0,0.0,0.0)<>0 Then
								PositionEntity Players(i)\Pivot,EntityX(Players(i)\Pivot,True),PickedY()+0.35,EntityZ(Players(i)\Pivot,True),True
								ResetEntity Players(i)\Pivot
								DebugLog "PICKED!"
							EndIf
							UpdateWorld
							Players(i)\KillTimer = 0
						EndIf
						SetAnimTime GetChild(Players(i)\Pivot,1),min(max(AnimTime(GetChild(Players(i)\Pivot,1))+0.1,16),22)
					EndIf
					
					PositionEntity GetChild(Players(i)\Pivot,1),EntityX(Players(i)\Pivot,True),EntityY(Players(i)\Pivot,True),EntityZ(Players(i)\Pivot,True),True
					ScaleEntity GetChild(Players(i)\Pivot,1),0.15,0.15,0.15,True
					TranslateEntity GetChild(Players(i)\Pivot,1),0.0,-0.3,0.0,True
					;RotateEntity Players(i)\Pivot,0.0,pyaw,0.0,True
					
					If (MilliSecs()-Players(i)\LastMsgTime>5000) Then ;remove client after 5 seconds of inactivity: assume connection was unexpectedly lost
						FreeEntity GetChild(Players(i)\Pivot,1)
						FreeEntity Players(i)\Pivot
						AddChatMsg(Players(i)\Name+" timed out!",255,0,0,i,True)
						PlayerCount=PlayerCount-1
						Delete Players(i)
						Players(i)=Null
					EndIf
				Else
					If Players(i)\KillTimer<=0 Then
						animmed% = True
						
						speedMult# = 1.0
						If Players(i)\PlayingAs<>PLAYER_CLASSD And Players(i)\VisibilityTimer<0 Then
							speedMult = 2.0
						EndIf
						
						Players(i)\SpookCooldown=max(Players(i)\SpookCooldown-1,0)
						Players(i)\VisibilityTimer=max(Players(i)\VisibilityTimer-1,-1200)
						
						If Players(i)\VisibilityTimer+1>0 And Players(i)\VisibilityTimer<=0 Then
							EntityType Players(i)\Pivot,hit_invisible
						EndIf
						
						If Players(i)\VisibilityTimer<0 And Players(i)\SpookCount>0 Then Players(i)\SpookCount=3
						If Players(i)\VisibilityTimer<=-1200 Then Players(i)\SpookCount=0
						
						If Players(i)\PlayingAs<>PLAYER_CLASSD Then
							If Players(i)\MClick1 Then
								DebugLog "bot lclick"
								If Players(i)\VisibilityTimer>0 Then
									For j=1 To 31
										If Players(j)<>Null Then
											If Players(j)\PlayingAs=PLAYER_CLASSD And i<>j Then
												vX# = EntityX(Players(j)\Pivot)-EntityX(Players(i)\Pivot)
												vZ# = EntityZ(Players(j)\Pivot)-EntityZ(Players(i)\Pivot)
												vLen# = Sqr((vX*vX)+(vZ*vZ))
												vYaw# = VectorYaw(vX/vLen,0.0,vZ/vLen)-EntityYaw(Players(i)\Pivot)
												While vYaw<-180.0
													vYaw=vYaw+360.0
												Wend
												While vYaw>=180.0
													vYaw=vYaw-360.0
												Wend
												;DebugLog "ANGLE "+Abs(vYaw)
												If EntityDistance(Players(j)\Pivot,Players(i)\Pivot)<0.8 And Abs(vYaw)<45 Then
													If Players(j)\KillTimer<=0 Then AddChatMsg(Players(i)\Name+" killed "+Players(j)\Name+"!",255,100,0,i,True)
													Players(j)\KillTimer = max(Players(j)\KillTimer,1)
												EndIf
											EndIf
										EndIf
									Next
									If PlayingAs=PLAYER_CLASSD Then
										vXb# = EntityX(collider)-EntityX(Players(i)\Pivot)
										vZb# = EntityZ(collider)-EntityZ(Players(i)\Pivot)
										vLenb# = Sqr((vXb*vXb)+(vZb*vZb))
										vYawb# = VectorYaw(vXb/vLenb,0.0,vZb/vLenb)-EntityYaw(Players(i)\Pivot)
										While vYawb<-180.0
											vYawb=vYawb+360.0
										Wend
										While vYaw>=180.0
											vYawb=vYawb-360.0
										Wend
										If EntityDistance(collider,Players(i)\Pivot)<0.8 And Abs(vYawb)<45 And KillTimer<=0 Then
											KillTimer = max(KillTimer,1)
											AddChatMsg(Players(i)\Name+" killed "+PlayerName+"!",255,100,0,i,True)
										EndIf
									EndIf
								EndIf
							EndIf
							
							If Players(i)\MClick2 Then
								DebugLog "bot rclick"
								Players(i)\MClick2 = False
								If Players(i)\SpookCount<3 And Players(i)\SpookCooldown<=0 Then
									Players(i)\SpookCount=Players(i)\SpookCount+1
									;Players(i)\SpookTimer=40
									Players(i)\SpookCooldown=95
									If Players(i)\VisibilityTimer<0 Then
										Players(i)\VisibilityTimer = 150
										EntityType Players(i)\Pivot,hit_monster
									Else
										Players(i)\VisibilityTimer=Players(i)\VisibilityTimer+150
									EndIf
									LastSentSpook=LastSentSpook+1
									For j=1 To 31
										If Players(j)<>Null Then
											If EntityDistance(Players(j)\Pivot,Players(i)\Pivot)<4.0 And EntityVisible(Players(j)\Pivot,Players(i)\Pivot) Then 
												Players(j)\LastSpookSent = LastSentSpook
											EndIf
										EndIf
									Next
									If EntityDistance(collider,Players(i)\Pivot)<4.0 And EntityVisible(collider,Players(i)\Pivot) Then 
										SpookTimer = 40
									EndIf
								EndIf
							EndIf
						EndIf
						
						If Players(i)\MForw Then MoveEntity Players(i)\Pivot,0,0,0.02*speedMult
						If Players(i)\MBack Then MoveEntity Players(i)\Pivot,0,0,-0.015*speedMult
						If Players(i)\MLeft Then MoveEntity Players(i)\Pivot,-0.008*speedMult, 0, 0
						If Players(i)\MRight Then MoveEntity Players(i)\Pivot,0.008*speedMult,0,0
						
						botAngle# = EntityYaw(Players(i)\Pivot,True)
						While botAngle<0.0
							botAngle=botAngle+360.0
						Wend
						While botAngle>=360.0
							botAngle=botAngle-360.0
						Wend
						
						;If EntityZ(Players(i)\Pivot,True)>-1.0 Then
						;	If EntityX(Players(i)\Pivot,True)>7.0 Then
						;		botAngle = CurveAngle(180.0,botAngle,10.0)
						;	ElseIf EntityX(Players(i)\Pivot,True)<1.0 Then
						;		botAngle = CurveAngle(270.0,botAngle,10.0)
						;	EndIf
						;ElseIf EntityZ(Players(i)\Pivot,True)<-6.0 Then
						;	If EntityX(Players(i)\Pivot,True)>7.0 Then
						;		botAngle = CurveAngle(90.0,botAngle,10.0)
						;	ElseIf EntityX(Players(i)\Pivot,True)<1.0 Then
						;		botAngle = CurveAngle(0.0,botAngle,10.0)
						;	EndIf
						;EndIf
						
						flrInd% = Int(Floor((-EntityY(Players(i)\Pivot,True)-1.55)/2.0))+1
						If flrInd<1 Then flrInd=1
						floorNum% = flrInd
						flrInd = Asc(Mid(MapString,flrInd,1))
						Local inWPRange% = False
						
						If (Players(i)\SpookCount<3 Or Players(i)\VisibilityTimer>0) And (Players(i)\PlayingAs<>PLAYER_CLASSD) And (EntityY(Players(i)\Pivot,True)<-8.0) Then ;bot stuff
							If (Players(i)\VisibilityTimer<=0) Then
								Players(i)\MClick1 = False
							Else
								Players(i)\MClick1 = True
							EndIf
							
							If Players(i)\SpookCooldown<=0 Then
								dist# = 2.0
								For j=0 To 31
									If Players(j)<>Null Then
										If Players(j)\PlayingAs=PLAYER_CLASSD Then
											If EntityDistance(Players(i)\Pivot,Players(j)\Pivot)<dist Then
												oldAngle# = EntityYaw(Players(i)\Pivot,True)
												PointEntity Players(i)\Pivot,Players(j)\Pivot
												botAngle = CurveAngle(EntityYaw(Players(i)\Pivot,True),oldAngle,10.0)
												If Players(i)\VisibilityTimer<=0 Then
													botAngle = EntityYaw(Players(i)\Pivot,True)
												EndIf
												RotateEntity Players(i)\Pivot,0.0,oldAngle,0.0,True
												Players(i)\MClick2 = True
												DebugLog "OH NO 1"
											EndIf
										EndIf
									EndIf
								Next
								If Not Players(i)\MClick2 Then
									If Players(i)\SpookCount>0 Then
										DebugLog "OH NO 2"
										Players(i)\MClick2 = True
									EndIf
								EndIf
							EndIf
							
							If Players(i)\SpookCount>0 Then
								Players(i)\MForw = True
							Else
								Players(i)\MForw = False
							EndIf
						Else
							Players(i)\MForw = True
							Players(i)\MClick1 = False
							Players(i)\MClick2 = False
						
							For j%=0 To 9
								wpX# = 0.0 : wpZ# = 0.0 : wpYaw# = 0.0
								
								If EntityYaw(FloorEntities(floorNum))=0.0 Then
									wpX# = FloorWaypoints(flrInd)\x[j]
									wpZ# = FloorWaypoints(flrInd)\z[j]
								Else
									wpX# = EntityX(FloorEntities(floorNum),True)-FloorWaypoints(flrInd)\x[j]
									wpZ# = EntityZ(FloorEntities(floorNum),True)-FloorWaypoints(flrInd)\z[j]
								EndIf
								
								If Abs(EntityX(Players(i)\Pivot,True)-wpX)<0.5 And Abs(EntityZ(Players(i)\Pivot,True)-wpZ)<0.5 Then
									inWPRange = True
									If Players(i)\LastChosenAngle=0 Then
										Players(i)\LastChosenAngle=Rand(1,2)
									EndIf
									
									If Players(i)\LastChosenAngle=1 Or Players(i)\PlayingAs<>PLAYER_CLASSD Then
										If EntityYaw(FloorEntities(floorNum))=0.0 Then
											wpYaw# = FloorWaypoints(flrInd)\angle1[j]
										Else
											wpYaw# = FloorWaypoints(flrInd)\angle1[j]+180.0
										EndIf
									Else
										If EntityYaw(FloorEntities(floorNum))=0.0 Then
											wpYaw# = FloorWaypoints(flrInd)\angle2[j]
										Else
											wpYaw# = FloorWaypoints(flrInd)\angle2[j]+180.0
										EndIf
									EndIf
	
									While wpYaw<0.0
										wpYaw=wpYaw+360.0
									Wend
									While wpYaw>=360.0
										wpYaw=wpYaw-360.0
									Wend
								
									Players(i)\BotAngle = wpYaw
								EndIf
							Next
							
							botAngle = CurveAngle(Players(i)\botAngle,botAngle,10.0)
						EndIf
						
						If Not inWPRange Then
							Players(i)\LastChosenAngle=0
						EndIf
						
						
						RotateEntity Players(i)\Pivot,0.0,botAngle,0.0,True
						If Players(i)\MForw+Players(i)\MBack+Players(i)\MLeft+Players(i)\MRight>0 Then
							SetAnimTime GetChild(Players(i)\Pivot,1),AnimTime(GetChild(Players(i)\Pivot,1))+0.1
							If AnimTime(GetChild(Players(i)\Pivot,1)) >= 14.0 Then
								SetAnimTime GetChild(Players(i)\Pivot,1),0
							EndIf
							animmed = True
							
							Players(i)\FallSpeed = Players(i)\FallSpeed-0.004
							MoveEntity Players(i)\Pivot,0,Players(i)\FallSpeed,0	
							
							UpdateWorld ;the server needs to be good enough to run this multiple times
							
							isFloor = False
							For j=1 To CountCollisions(Players(i)\Pivot)
								If CollisionY(Players(i)\Pivot,j) < EntityY(Players(i)\Pivot) - 0.1 Then isFloor = True : Exit
							Next
							If isFloor = True Then
								If Players(i)\FallSpeed < -0.09 And EntityY(Players(i)\Pivot)<-1 Then
									Players(i)\KillTimer=max(1,Players(i)\KillTimer)
									AddChatMsg(Players(i)\Name+" fell to their death!",255,0,0,i,True)
								EndIf
								Players(i)\FallSpeed = 0
							Else
								
							EndIf
						Else
							SetAnimTime GetChild(Players(i)\Pivot,1),15
						EndIf
						If Not animmed Then
							SetAnimTime GetChild(Players(i)\Pivot,1),15
						EndIf
					EndIf
					
					If Players(i)\KillTimer>0 Then
						Players(i)\FallSpeed = 0.0
						Players(i)\KillTimer=min(Players(i)\KillTimer+1,100)
						If Players(i)\KillTimer>95 Then
							thisPlayerFloor% = (-EntityY(Players(i)\Pivot)-0.5)/2
							thisPlayerFloor=max(thisPlayerFloor/2,1)
							FloorY#=-(thisPlayerFloor-1)*2-1.0
							If Floor(thisPlayerFloor/2.0)=Ceil(thisPlayerFloor/2.0) Then ;parillinen
								FloorZ#=-6.54
								StartX# = 7.2 
								EndX# = 0.8
							Else ;pariton
								FloorZ#=-0.31
								StartX# = 0.8
								EndX# = 7.2
							EndIf
							
							PositionEntity Players(i)\Pivot,Rnd(StartX,EndX),FloorY,FloorZ,True
							ResetEntity Players(i)\Pivot
							If LinePick(EntityX(Players(i)\Pivot,True),EntityY(Players(i)\Pivot,True),EntityZ(Players(i)\Pivot,True),0.0,-40.0,0.0,0.0)<>0 Then
								PositionEntity Players(i)\Pivot,EntityX(Players(i)\Pivot,True),PickedY()+0.35,EntityZ(Players(i)\Pivot,True),True
								ResetEntity Players(i)\Pivot
								If Players(i)\IsBot Then
									RotateEntity Players(i)\Pivot,0.0,270.0,0.0,True
									Players(i)\BotAngle = 270.0
								EndIf
								DebugLog "PICKED!"
							EndIf
							UpdateWorld
							Players(i)\KillTimer = 0
						EndIf
						SetAnimTime GetChild(Players(i)\Pivot,1),min(max(AnimTime(GetChild(Players(i)\Pivot,1))+0.1,16),22)
					EndIf
					
					PositionEntity GetChild(Players(i)\Pivot,1),EntityX(Players(i)\Pivot,True),EntityY(Players(i)\Pivot,True),EntityZ(Players(i)\Pivot,True),True
					ScaleEntity GetChild(Players(i)\Pivot,1),0.15,0.15,0.15,True
					TranslateEntity GetChild(Players(i)\Pivot,1),0.0,-0.3,0.0,True
				EndIf
				
			EndIf
		Next
		If PlayerCount>1 Then SentMsgID=SentMsgID+1
		;[Block]
		;If (getconn) Then
		;	For giveID=1 To 4
		;		If giveID >= 4 Then giveID = -1 : Exit
		;		If (Not Players(giveID,0)) Then Exit
		;	Next
		;	If giveID<4 And giveID>0 Then
		;		Players(giveID,0)=getconn
		;		Players(giveID,2)=CreatePivot()
		;		Local tempMesh% = CopyEntity(PlayerMesh);CreateSphere()
		;		ScaleEntity tempMesh, 0.15,0.15,0.15
		;		PositionEntity tempMesh,0.0,-0.3,0.0,True
		;		EntityParent tempMesh,Players(giveID,1)
		;		;EntityFX tempMesh,1
		;		EntityTexture tempMesh,PlayerTex
		;		PositionEntity Players(giveID,2),-2.5,-1.3,-0.5,True
		;		EntityType Players(giveID,2), hit_monster
		;		EntityRadius Players(giveID,2), 0.3
		;		Player_Names(giveID) = ReadLine(getconn)
		;		WriteLine(getconn,MapString) ;give the player the server's map arrangement
		;		WriteByte(getconn,giveID) ;give the player their ID
		;		PlayerCount=PlayerCount+1
		;	Else
		;		WriteLine getconn,"KICK"
				;Delay 2
		;		CloseTCPStream(getconn)
		;	EndIf
		;EndIf
		
		;For i=0 To 3
		;	If Players(i,0) And Players(i,2) Then
		;		While (ReadAvail(Players(i,0)))
		;			posx = ReadFloat(Players(i,0))
		;			posy = ReadFloat(Players(i,0))
		;			posz = ReadFloat(Players(i,0))
		;			pyaw = ReadFloat(Players(i,0))
		;			If (posx<>EntityX(Players(i,2),True) Or posz<>EntityZ(Players(i,2),True)) Then
		;				SetAnimTime GetChild(Players(i,2),1),AnimTime(GetChild(Players(i,2),1))+0.1
		;				If AnimTime(GetChild(Players(i,2),1)) >= 15 Then
		;					SetAnimTime GetChild(Players(i,2),1),0
		;				EndIf
		;			Else
		;				SetAnimTime GetChild(Players(i,2),1),15
		;			EndIf
		;			ScaleEntity GetChild(Players(i,2),1),0.15,0.15,0.15,True
		;			TranslateEntity GetChild(Players(i,2),1),0.0,-0.3,0.0,True
		;			PositionEntity Players(i,2),posx,posy,posz,True
		;			RotateEntity Players(i,2),0.0,pyaw,0.0,True
		;			ResetEntity Players(i,2)
		;		Wend
		;		For j=0 To 3
		;			If (Players(j,0) And Players(j,2)) Or (Players(j,2)=collider) Then
		;				WriteByte(Players(i,0),j+1)
		;				WriteFloat(Players(i,0),EntityX(Players(j,2)))
		;				WriteFloat(Players(i,0),EntityY(Players(j,2)))
		;				WriteFloat(Players(i,0),EntityZ(Players(j,2)))
		;				WriteFloat(Players(i,0),EntityYaw(Players(j,2)))
		;			Else
		;				WriteByte(Players(i,0),0)
		;			EndIf
		;		Next
		;		If Eof(Players(i,0))<>0 Then ;player has disconnected
		;			PlayerCount = PlayerCount-1
		;			FreeEntity Players(i,1)
		;			Players(i,0) = 0
		;			Players(i,1) = 0
		;		EndIf
		;	EndIf
		;Next
		;[End Block]
	Else If PlayState=GAME_CLIENT Then
		getconn = RecvUDPMsg(Server)
		While getconn ;the server has given you a message
			Local ttmp% = ReadInt(Server)
			If ttmp>LastMsgID Then
				Players(0)\LastMsgTime = MilliSecs()
				LastMsgID = ttmp
				For i=0 To 31
					ttmp% = ReadByte(Server)
					;DebugLog "ttmp "+(ttmp-1)
					If ttmp Then
						Local lastClientMsg% = ReadInt(Server)
						isForw = ReadByte(Server)
						Local character% = ReadByte(Server)
						Local isDead% = ReadByte(Server)
						Local isVisible% = ReadInt(Server)
						If Players(ttmp-1)<>Null Then
							If isForw>=8 Then
								isForw=isForw-8 : Players(ttmp-1)\MRight = True
							Else
								Players(ttmp-1)\MRight = False
							EndIf
							If isForw>=4 Then
								isForw=isForw-4 : Players(ttmp-1)\MLeft = True
							Else
								Players(ttmp-1)\MLeft = False
							EndIf
							If isForw>=2 Then
								isForw=isForw-2 : Players(ttmp-1)\MBack = True
							Else
								Players(ttmp-1)\MBack = False
							EndIf
							If isForw>=1 Then
								isForw=isForw-1 : Players(ttmp-1)\MForw = True
							Else
								Players(ttmp-1)\MForw = False
							EndIf
						EndIf
						posx# = ReadFloat(Server)
						posy# = ReadFloat(Server)
						posz# = ReadFloat(Server)
						pyaw# = ReadFloat(Server)
						If ttmp-1<>ID Then
							;If posx<>EntityX(Players(ttmp-1)\Pivot,True) Or posz<>EntityZ(Players(ttmp-1)\Pivot,True) Then
							;	SetAnimTime GetChild(Players(ttmp-1)\Pivot,1),AnimTime(GetChild(Players(ttmp-1)\Pivot,1))+0.1
							;	If AnimTime(GetChild(Players(ttmp-1)\Pivot,1)) >= 14.0 Then
							;		SetAnimTime GetChild(Players(ttmp-1)\Pivot,1),0
							;	EndIf
							;Else
							;	SetAnimTime GetChild(Players(ttmp-1)\Pivot,1),15.0
							;EndIf
							If Players(ttmp-1)<>Null Then
								PositionEntity Players(ttmp-1)\Pivot,posx,posy,posz,True
								RotateEntity Players(ttmp-1)\Pivot,0.0,pyaw,0.0,True
								ResetEntity Players(ttmp-1)\Pivot
								If Players(ttmp-1)\PlayingAs<>character Then
									Select character
										Case PLAYER_CLASSD
											EntityTexture GetChild(Players(ttmp-1)\Pivot,1),PlayerTex
											EntityType Players(ttmp-1)\Pivot,hit_friendly
										Case PLAYER_MENTAL
											EntityTexture GetChild(Players(ttmp-1)\Pivot,1),mental
											EntityType Players(ttmp-1)\Pivot,hit_monster
										Case PLAYER_REDMIST
											EntityTexture GetChild(Players(ttmp-1)\Pivot,1),tex173
											EntityType Players(ttmp-1)\Pivot,hit_monster
									End Select
								EndIf
								If character<>PLAYER_CLASSD Then
									If isVisible<0 Then
										EntityAlpha GetChild(Players(ttmp-1)\Pivot,1),0.5
									Else
										EntityAlpha GetChild(Players(ttmp-1)\Pivot,1),1.0
									EndIf
								Else
									EntityAlpha GetChild(Players(ttmp-1)\Pivot,1),1.0
								EndIf
								Players(ttmp-1)\PlayingAs = character
								Players(ttmp-1)\KillTimer = isDead
								Players(ttmp-1)\VisibilityTimer = isVisible
							EndIf
							
							;Local tpath$ = ReadLine(Server)
;							If tpath<>Players(ttmp-1)\TexturePath Then
;								Players(ttmp-1)\TexturePath = tpath
;								Download("http://i.imgur.com/"+Players(ttmp-1)\TexturePath,"player_textures",Players(ttmp-1)\TexturePath)
;								Players(ttmp-1)\LoadedTexture = LoadTexture("player_textures/"+Players(ttmp-1)\TexturePath)
;								If Players(ttmp-1)\LoadedTexture<>0 Then
;									EntityTexture GetChild(Players(ttmp-1)\Pivot,1),Players(ttmp-1)\LoadedTexture
;								EndIf
;							EndIf
						Else
							If PlayingAs<>PLAYER_CLASSD Then
								VisibilityTimer=isVisible
							Else
								VisibilityTimer=0
							EndIf
							
							If isDead Then
								Kill()
								PositionEntity collider,posx,posy,posz
							Else
								If KillTimer>0 Then PositionEntity collider,posx,posy,posz
								KillTimer=0
							EndIf
							PlayingAs = character
							;ReadLine(Server)
							Local bbb% = DirListShift-(SentMsgID-lastClientMsg)
							If bbb<0 Then bbb=bbb+60
							If bbb>=0 And (SentMsgID-lastClientMsg)<60 And (SentMsgID-lastClientMsg)>0 Then
								If (Not FloatEquals(MemX[bbb],posx)) Or (Not FloatEquals(MemZ[bbb],posz)) Then
									PositionEntity collider,posx,posy,posz
									ResetEntity collider
									;DebugLog "Correction! "+(SentMsgID-lastClientMsg)
								EndIf
							Else
								;DebugLog "VERY HIGH CLIENT PACKET LOSS"
							EndIf
						EndIf
					Else
						PositionEntity Players(i)\Pivot,0.0,100.0,0.0,True
						RotateEntity Players(i)\Pivot,0.0,0.0,0.0,True
						ResetEntity Players(i)\Pivot
					EndIf
				Next
;				ttmp% = ReadByte(Server)
;				While ttmp
;					If ttmp=1 Then ;enemy position
;						posx# = ReadFloat(Server)
;						posy# = ReadFloat(Server)
;						posz# = ReadFloat(Server)
;						tempStr$ = ReadLine(Server)
;						pyaw# = ReadFloat(Server)
;						If CurrEnemy=Null Then 
;							tempTex%=LoadTexture(tempStr)
;							CreateEnemy(posx,posy,posz,tempTex)
;							FreeTexture tempTex
;						EndIf
;						PositionEntity CurrEnemy\collider,posx,posy,posz,True
;						If tempStr<>CurrEnemy\texture Then
;							tempTex%=LoadTexture(tempStr)
;							EntityTexture CurrEnemy\obj,tempTex
;							FreeTexture tempTex
;							CurrEnemy\texture=tempStr
;						EndIf
;						RotateEntity CurrEnemy\collider,0.0,pyaw,0.0,True
;					EndIf
;					ttmp% = ReadByte(Server)
;				Wend
				
				Local latestSpook% = ReadInt(Server)
				If latestSpook>LastRecvSpook Then
					DebugLog "AHHHH "+LastRecvSpook+" "+latestSpook
					LastRecvSpook = latestSpook
					SpookTimer = 40
				EndIf
				
				ttmp% = ReadByte(Server)
				For i=1 To ttmp
					Local msgID% = ReadInt(Server)
					Local msgTxt$ = ReadLine(Server)
					Local red% = ReadByte(Server)
					Local green% = ReadByte(Server)
					Local blue% = ReadByte(Server)
					If msgID>RecvChatID Then
						RecvChatID = msgID
						AddChatMsg(msgTxt,red,green,blue)
					EndIf
				Next
			EndIf
			getconn = RecvUDPMsg(Server)
		Wend
		For i=0 To 31
			If Players(i)<>Null And i<>ID Then
				If Players(i)\KillTimer<=0 Then
					If Players(i)\MForw+Players(i)\MBack+Players(i)\MLeft+Players(i)\MRight>0 Then
						SetAnimTime GetChild(Players(i)\Pivot,1),AnimTime(GetChild(Players(i)\Pivot,1))+0.1
						If AnimTime(GetChild(Players(i)\Pivot,1)) >= 14.0 Then
							SetAnimTime GetChild(Players(i)\Pivot,1),0
						EndIf
					Else
						SetAnimTime GetChild(Players(i)\Pivot,1),15
					EndIf
				Else
					SetAnimTime GetChild(Players(i)\Pivot,1),min(max(AnimTime(GetChild(Players(i)\Pivot,1))+0.1,16),22)
				EndIf
				ScaleEntity GetChild(Players(i)\Pivot,1),0.15,0.15,0.15,True
				TranslateEntity GetChild(Players(i)\Pivot,1),0.0,-0.3,0.0,True
			EndIf
		Next
		WriteLine Server,Str(ID)
		WriteInt Server,SentMsgID
		;WriteByte Server,(MoveForw)+(MoveBack*2)+(MoveLeft*4)+(MoveRight*8) ;send all movement keypresses in one byte
		For i=0 To 59
			Local aaa% = DirListShift-i
			If aaa<0 Then aaa=aaa+60
			WriteByte Server,DirList[aaa]
			WriteFloat Server,DirListAngle[aaa]
		Next
		;WriteFloat Server,EntityYaw(collider,True)
		WriteInt Server,SentChatMsgID
		WriteLine Server,SentChatMsg
		WriteInt Server,RecvChatID
		SendUDPMsg(Server,Players(0)\IP,Players(0)\Port)
		SentMsgID=SentMsgID+1
		If (MilliSecs()-Players(0)\LastMsgTime>5000) Then ;disconnect after 5 seconds of inactivity: assume connection was unexpectedly lost
			AddChatMsg("Lost connection to the server!",255,0,0)
			For i=0 To 31
				If i<>ID Then
					If Players(i)<>Null Then
						FreeEntity GetChild(Players(i)\Pivot,1)
						FreeEntity Players(i)\Pivot
						Delete Players(i)
						Players(i)=Null
					EndIf
				EndIf
				PlayState = GAME_SOLO
			Next
			Paused = True
			KeyHit1 = True
		EndIf
		;[Block]
		;While (ReadAvail(Server))
		;	For i=0 To 3
		;		Local ttmp% = ReadByte(Server)
		;		If (ttmp) Then
		;			posx = ReadFloat(Server)
		;			posy = ReadFloat(Server)
		;			posz = ReadFloat(Server)
		;			pyaw = ReadFloat(Server)
		;			If ttmp-1<>ID Then
		;				If (posx<>EntityX(Players(ttmp-1,1),True) Or posz<>EntityZ(Players(ttmp-1,1),True)) Then
		;					SetAnimTime GetChild(Players(ttmp-1,1),1),AnimTime(GetChild(Players(ttmp-1,1),1))+0.1
		;					If AnimTime(GetChild(Players(ttmp-1,1),1)) >= 15 Then
		;						SetAnimTime GetChild(Players(ttmp-1,1),1),0
		;					EndIf
		;				Else
		;					SetAnimTime GetChild(Players(ttmp-1,1),1),15
		;				EndIf
		;				ScaleEntity GetChild(Players(ttmp-1,1),1),0.15,0.15,0.15,True
		;				TranslateEntity GetChild(Players(ttmp-1,1),1),0.0,-0.3,0.0,True
		;				PositionEntity Players(ttmp-1,1),posx,posy,posz,True
		;				RotateEntity Players(ttmp-1,1),0.0,pyaw,0.0,True
		;				ResetEntity Players(ttmp-1,1)
		;			EndIf
		;		Else
		;			If Players(i,1) Then PositionEntity Players(i,1),-200.5,-1.5,-30.0 : ResetEntity Players(i,1)
		;		EndIf
		;	Next
		;Wend
		;If (Not (Eof(Server)<>0)) Then ;server is still available
		;	WriteFloat Server,EntityX(collider,True)
		;	WriteFloat Server,EntityY(collider,True)
		;	WriteFloat Server,EntityZ(collider,True)
		;	WriteFloat Server,EntityYaw(collider,True)
		;Else ;connection with server has been lost
		;	For i=0 To 3
		;		If (Players(i,1)) Then
		;			If i<>ID Then
		;				FreeEntity Players(i,1)
		;				Players(i,1)=0
		;			EndIf
		;		EndIf
		;	Next
		;	If Paused Then KeyHit1 = True
		;	Paused = False
		;	PlayState = GAME_SOLO
		;EndIf
		;[End Block]
	EndIf
	
	If KeyHit(21) And (PlayState<>GAME_SOLO) And (Not ChatOpen) And (Not Paused) Then
		ChatOpen = True
		FlushKeys()
	EndIf
	
	If KeyHit(37) And (Not ChatOpen) And (Not Paused) Then
		Kill()
	EndIf
	
	If (KeyHit1) Then
		Local togglePause% = True
		If PlayState<>GAME_SOLO Then
			If ChatOpen Then
				ChatOpen = False
				togglePause = False
			EndIf
		EndIf
		KeyHit1 = 0
		MoveMouse GraphicsWidth()/2,GraphicsHeight()/2
		If togglePause Then
			SelectedInputBox = False
			Paused = True
			If (Not fullscreen) Then ShowPointer
			FlushKeys()
			While True
				MouseHit1 = MouseHit(1)
				MouseHit2 = MouseHit(2)
				If PlayState Then Exit
				Cls
				RenderWorld
				SetFont MenuFont2
				If (Rand(5)=1) Then
					Local tmpp%
					tmpp = Rand(100)
					Color tmpp,tmpp,tmpp
					Text GraphicsWidth()/2-Rand(50)+Rand(50),GraphicsHeight()/2-Rand(50)+Rand(50)-200,"PAUSED",True,False
				EndIf
				Color 255,255,255
				Text GraphicsWidth()/2,GraphicsHeight()/2-200,"PAUSED",True,False
				SetFont MenuFont1
				Color 30,30,30
				If (KeyHit(1)) Or (Button(GraphicsWidth()/2-200,GraphicsHeight()/2,400,20,"Resume")) Then
					Flip
					MoveMouse GraphicsWidth()/2,GraphicsHeight()/2
					Paused = 0
					Exit
				EndIf
				Color 30,30,30
				
				If (Button(GraphicsWidth()/2-200,GraphicsHeight()/2+35,400,20,"Create Server",(PlayState<>GAME_SOLO))) Then
					PlayState=GAME_SERVER ;let the game know you're a server
					Server% = CreateUDPStream(8730) ;create a UDP stream with a specific port
					Players(0)=New Player
					Players(0)\IP=0 ;there is no IP for this "client"
					Players(0)\Port=0 ;there is no port for this "client"
					Players(0)\Pivot=collider ;player 1 is yourself
					Players(0)\Name = PlayerName ;this is the host's name
					Players(0)\LastMsgTime = MilliSecs() ;this sets a timer to detect if the connection with the server is still established
					PlayerCount=PlayerCount+1
					PlayingAs = PLAYER_MENTAL
					SpookCount = 3
				EndIf
				
				Color 0,0,0
				Text GraphicsWidth()/2-200+1,GraphicsHeight()/2-35+1,"Name:"
				Color 255,255,255
				Text GraphicsWidth()/2-200,GraphicsHeight()/2-35,"Name:"
				PlayerName = InputBox(GraphicsWidth()/2-150,GraphicsHeight()/2-35,350,20,PlayerName,1)
				Color 0,0,0
				Text GraphicsWidth()/2-200+1,GraphicsHeight()/2+65+1,"IP:"
				Color 255,255,255
				Text GraphicsWidth()/2-200,GraphicsHeight()/2+65,"IP:"
				ServerAddress = InputBox(GraphicsWidth()/2-150,GraphicsHeight()/2+65,350,20,ServerAddress,2)
				
				Color 30,30,30
				If (Button(GraphicsWidth()/2-200,GraphicsHeight()/2+100,400,20,"Join Server",(PlayState<>GAME_SOLO))) Then
					PlayingAs = PLAYER_CLASSD
					JoinServer(ServerAddress)
				EndIf
				Color 30,30,30
				If (Button(GraphicsWidth()/2-200,GraphicsHeight()/2+135,400,20,"Quit")) Then
					If PlayState=GAME_CLIENT Then
						WriteLine(Server,"disconnect") : WriteByte(Server,ID)
						SendUDPMsg(Server,Players(0)\IP,Players(0)\Port) : Delay 100
					EndIf
					Flip: Delay 8 : End
				EndIf
				
				Color 255,255,255
				If (PlayState=GAME_SERVER) Then
					Text 5,5,"You are hosting a server"
				ElseIf (PlayState=GAME_CLIENT) Then
					Text 5,5,"You are connected to a server"
				EndIf
				
				If fullscreen Then DrawImage PointerImg,MouseX(),MouseY()
				
				Flip
				Delay 8
			Wend
			HidePointer
		EndIf
	EndIf

	If FrameLimit > 0 Or FrameLimit < 255
	   	; Framelimit
		WaitingTime = (1000.0 / FrameLimit) - (MilliSecs() - LoopDelay)
		Delay WaitingTime
	   LoopDelay = MilliSecs()
	EndIf 

	elapsedloops = elapsedloops + 1
	If timer < MilliSecs() Then
		loops = elapsedloops	
		elapsedloops = 0
		timer = MilliSecs() + 1000 
	EndIf 
	
	If Not ChannelPlaying (MusicChannel) Then MusicChannel = PlaySound(Music)
	
	PlayerFloor = (-EntityY(collider)-0.5)/2
	
	Local speedMultiplier# = 1.0
	If PlayingAs<>PLAYER_CLASSD And VisibilityTimer<0 Then
		speedMultiplier = 2.0
	EndIf
	
	If (KeyDown(208) Or KeyDown(31)) And (Not Paused) And (Not ChatOpen) Then 
		MoveEntity collider,0,0,-0.015*speedMultiplier ;If PlayState<>GAME_CLIENT Then 
		MoveBack = True
	Else
		MoveBack = False
	EndIf
	If (KeyDown(200) Or KeyDown(17)) And (Not Paused) And (Not ChatOpen) Then
		MoveEntity collider,0,0,0.02*speedMultiplier ;If PlayState<>GAME_CLIENT Then 
		MoveForw = True
	Else
		MoveForw = False
	EndIf

	If ((KeyDown(208)Or KeyDown(31)) Xor KeyDown(200) Or  KeyDown(17)) Or ((KeyDown(203) Or KeyDown(30) ) Xor (KeyDown(205)Or KeyDown(32))) Then
		shake# = shake + 0.5
		If shake = 10.0 Then shake = -10.0 : EmitSound(stepsound, collider)

		If shake < 0.0 Then 	
 			up = up - 0.005
		Else
			up = up + 0.005
		EndIf 
		
		;----	
		shakeX# = shakeX + 0.5
		If shakeX = 20.0 Then shakeX = -20.0
		
		If shakeX < 0.0 Then
			side = side - 0.002
		Else
			side = side + 0.002
		EndIf
		
	EndIf 
	
	For i=0 To 9
		Exit ;skip this because it's for debugging purposes
		If KeyHit(i+2) Then
			flrInd% = Int(Floor((-EntityY(collider,True)-1.55)/2.0))+1
			If flrInd<1 Then flrInd=1
			floorNum% = flrInd
			flrInd = Asc(Mid(MapString,flrInd,1))
			DebugLog "aaa "+floorNum
			
			If (FloorWaypoints(flrInd)=Null) Then
				FloorWaypoints(flrInd) = New floorWaypointSet
			EndIf
			FloorWaypoints(flrInd)\x[i] = EntityX(collider,True)
			FloorWaypoints(flrInd)\z[i] = EntityZ(collider,True)
			FloorWaypoints(flrInd)\angle1[i] = EntityYaw(collider,True)
			FloorWaypoints(flrInd)\angle2[i] = EntityYaw(collider,True)
			If EntityYaw(FloorEntities(floorNum),True)<>0.0 Then
				FloorWaypoints(flrInd)\x[i] = EntityX(FloorEntities(floorNum),True)-FloorWaypoints(flrInd)\x[i]
				FloorWaypoints(flrInd)\z[i] = EntityZ(FloorEntities(floorNum),True)-FloorWaypoints(flrInd)\z[i]
				FloorWaypoints(flrInd)\angle1[i] = FloorWaypoints(flrInd)\angle1[i]+180.0
				FloorWaypoints(flrInd)\angle2[i] = FloorWaypoints(flrInd)\angle2[i]+180.0
			EndIf
			
			If FloorWaypoints(flrInd)\ents[i]=0 Then
				DebugLog "NEW CUBE"
				FloorWaypoints(flrInd)\ents[i]=CreateCube()
				ScaleEntity FloorWaypoints(flrInd)\ents[i],0.1,0.1,0.1
				EntityOrder FloorWaypoints(flrInd)\ents[i],-3
			EndIf
		EndIf
		
		lal% = WriteFile("floorwaypoints.txt")
		For j%=0 To 127
			If (FloorWaypoints(j)<>Null) Then
				WriteLine(lal,"FloorWaypoints("+j+")=New floorWaypointSet")
				For k%=0 To 9
					If FloorWaypoints(j)\ents[k]<>0 Then
						WriteLine(lal,"FloorWaypoints("+j+")\x["+k+"]="+FloorWaypoints(j)\x[k])
						WriteLine(lal,"FloorWaypoints("+j+")\z["+k+"]="+FloorWaypoints(j)\z[k])
						WriteLine(lal,"FloorWaypoints("+j+")\angle1["+k+"]="+FloorWaypoints(j)\angle1[k])
						WriteLine(lal,"FloorWaypoints("+j+")\angle2["+k+"]="+FloorWaypoints(j)\angle2[k])
					EndIf
				Next
				WriteLine(lal,"")
			EndIf
		Next
		CloseFile(lal)
	Next
	
	If SpookTimer = 40 Then
		PlaySound(HorrorSFX(0))
	EndIf
	
	If SpookTimer>0 Then
		CameraFogRange camera, 1, 20.0
		CameraRange camera, 0.001, 25.0
		AmbientLight 255,255,255
	EndIf
	
	SpookTimer=max(SpookTimer-1,0)
	If PlayState=GAME_SERVER And PlayingAs<>PLAYER_CLASSD Then
		SpookCooldown=max(SpookCooldown-1,0)
		VisibilityTimer=max(VisibilityTimer-1,-1200)
		
		If VisibilityTimer<0 And SpookCount>0 Then SpookCount=3
		If VisibilityTimer<=-1200 Then SpookCount=0
		
		If MouseHit1 Then
			If VisibilityTimer>0 Then
				DebugLog "lclick"
				For i=1 To 31
					If Players(i)<>Null Then
						If Players(i)\PlayingAs=PLAYER_CLASSD Then
							Local vXa# = EntityX(Players(i)\Pivot)-EntityX(collider)
							Local vZa# = EntityZ(Players(i)\Pivot)-EntityZ(collider)
							Local vLena# = Sqr((vXa*vXa)+(vZa*vZa))
							Local vYawa# = VectorYaw(vXa/vLena,0.0,vZa/vLena)-EntityYaw(collider)
							While vYawa<-180.0
								vYawa=vYawa+360.0
							Wend
							While vYaw>=180.0
								vYawa=vYawa-360.0
							Wend
							;DebugLog "ANGLE "+Abs(vYawa)
							If EntityInView(GetChild(Players(i)\Pivot,1),camera) And EntityDistance(Players(i)\Pivot,collider)<0.8 And Abs(vYawa)<45 Then
								If Players(i)\KillTimer<=0 Then AddChatMsg(PlayerName+" killed "+Players(i)\Name+"!",255,100,0,i,True)
								Players(i)\KillTimer = max(Players(i)\KillTimer,1)
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		EndIf
		
		If MouseHit2 Then
			DebugLog "rclick"
			If SpookCount<3 And SpookCooldown<=0 Then
				SpookCount=SpookCount+1
				SpookTimer=40
				SpookCooldown=95
				If VisibilityTimer<0 Then
					VisibilityTimer = 150
				Else
					VisibilityTimer=VisibilityTimer+150
				EndIf
				LastSentSpook=LastSentSpook+1
				For i=1 To 31
					If Players(i)<>Null Then
						If EntityDistance(Players(i)\Pivot,collider)<4.0 And EntityVisible(Players(i)\Pivot,collider) Then 
							Players(i)\LastSpookSent = LastSentSpook
						EndIf
					EndIf
				Next
			EndIf
		EndIf
	EndIf
	
	If (KeyDown(203) Or KeyDown(30)) And (Not Paused) And (Not ChatOpen) Then
		MoveEntity collider,-0.008*speedMultiplier, 0, 0 ;If PlayState<>GAME_CLIENT Then 
		MoveLeft = True
	Else
		MoveLeft = False
	EndIf
	If (KeyDown(205) Or KeyDown(32)) And (Not Paused) And (Not ChatOpen) Then
		MoveEntity collider,0.008*speedMultiplier, 0, 0 ;If PlayState<>GAME_CLIENT Then 
		MoveRight = True
	Else
		MoveRight = False
	EndIf
	
	DirListShift=(DirListShift+1) Mod 60
	DirList[DirListShift] = (MoveForw)+(MoveBack*2)+(MoveLeft*4)+(MoveRight*8)+(MouseHit1*16)+(MouseHit2*32)
	DirListAngle[DirListShift] = EntityYaw(collider,True)
	
	UpdateEnemies()
	
	If Rand(1000) < 2 Then
		PositionEntity(SoundEmitter, EntityX(collider)+Rand(-1,1), EntityY(collider)+Rand(-2,-10), EntityZ(collider)+Rand(-1,1))
		EmitSound(AmbientSFX(Rand(0,8)),SoundEmitter) 
	EndIf
	
	;If PlayState<>GAME_CLIENT Then
	
	;EndIf
	
	UpdateFloors()
	UpdateGlimpses()
	
	If KillTimer>0 Then Kill()
	
	DropSpeed# = DropSpeed-0.004
	MoveEntity collider,0,DropSpeed,0	
	
	UpdateWorld 
	
	Local CollidedFloor% = False
	For i = 1 To CountCollisions(collider)
		If CollisionY(collider,i) < EntityY(collider) - 0.1 Then CollidedFloor = True 
	Next
	If CollidedFloor = True Then
		If PlayState=GAME_SERVER Then
			If DropSpeed# < -0.09 And EntityY(collider)<-1 Then
				KillTimer = max(1,KillTimer)
				AddChatMsg(PlayerName+" fell to their death!",255,0,0,i,True)
			EndIf
		EndIf
		DropSpeed# = 0
	Else
		
	EndIf
	
	If PlayState=GAME_SERVER Then
		If DropSpeed# < -0.18 And EntityY(collider)<-1 Then
			KillTimer = max(1,KillTimer)
			AddChatMsg(PlayerName+" fell to their death!",255,0,0,i,True)
			DropSpeed# = 0
		EndIf
	EndIf
	
	MemX[DirListShift] = EntityX(collider,True)
	MemY[DirListShift] = EntityY(collider,True)
	MemZ[DirListShift] = EntityZ(collider,True)
	
	RenderWorld
	UpdateChatMsgs()
	Color 255,255,255
	flrInd% = Int(Floor((-EntityY(collider,True)-1.55)/2.0))+1
	If flrInd<1 Then flrInd=1
	floorNum% = flrInd
	flrInd = Asc(Mid(MapString,flrInd,1))
	
	If FloorWaypoints(flrInd)<>Null Then
		For i=0 To 9
			If FloorWaypoints(flrInd)\ents[i]<>0 Then
				If EntityYaw(FloorEntities(floorNum),True)<>0.0 Then
					PositionEntity FloorWaypoints(flrInd)\ents[i],EntityX(FloorEntities(floorNum),True)-FloorWaypoints(flrInd)\x[i],EntityY(FloorEntities(floorNum),True),EntityZ(FloorEntities(floorNum),True)-FloorWaypoints(flrInd)\z[i]
				Else
					PositionEntity FloorWaypoints(flrInd)\ents[i],FloorWaypoints(flrInd)\x[i],EntityY(FloorEntities(floorNum),True),FloorWaypoints(flrInd)\z[i]					
				EndIf
			EndIf
		Next
	EndIf
	
	If KillTimer > 0 Then
		DropSpeed = 0.0
		UpdateBlur(0.93)
		SpookCooldown = 10.0
	Else
		If BlurTimer < 50 Then 
			UpdateBlur(0.7+(BlurTimer/50.0)*0.2)
		Else
			UpdateBlur(0.7+0.2)	
		EndIf		
	EndIf
	
	BlurTimer = max(BlurTimer-1,0)
	
	If ((Not Paused) And (Not ChatOpen)) Or (Not PlayState) Then
		MouseLook() : HidePointer
		SelectedInputBox = 0
	ElseIf ChatOpen Then
		SelectedInputBox = 1
		TypedChatMsg = InputBox(0,GraphicsHeight()-20,300,20,TypedChatMsg,1,False)
		If Len(TypedChatMsg)>200 Then
			TypedChatMsg = Left(TypedChatMsg,200)
		EndIf
		If KeyHit(28) Then
			If PlayState=GAME_CLIENT Then
				SentChatMsgID=SentChatMsgID+1
				SentChatMsg = TypedChatMsg
			Else
				If Lower(Left(TypedChatMsg,6))="!admin" Then
					AdminSpyChat = Not AdminSpyChat
				ElseIf Lower(Left(TypedChatMsg,7))="!mental" Then
					TypedChatMsg = Trim(Replace(TypedChatMsg,"!mental",""))
					If Int(TypedChatMsg)=0 Then
						PlayingAs = PLAYER_MENTAL
					ElseIf Players(Int(TypedChatMsg))<>Null Then
						If Players(Int(TypedChatMsg))\PlayingAs = PLAYER_CLASSD Then
							EntityType Players(Int(TypedChatMsg))\Pivot,hit_invisible
							Players(Int(TypedChatMsg))\VisibilityTimer = 0
						EndIf
						Players(Int(TypedChatMsg))\PlayingAs = PLAYER_MENTAL
						EntityTexture GetChild(Players(Int(TypedChatMsg))\Pivot,1),mental
					EndIf
				ElseIf Lower(Left(TypedChatMsg,8))="!redmist" Then
					TypedChatMsg = Trim(Replace(TypedChatMsg,"!redmist",""))
					If Int(TypedChatMsg)=0 Then
						PlayingAs = PLAYER_REDMIST
					ElseIf Players(Int(TypedChatMsg))<>Null Then
						If Players(Int(TypedChatMsg))\PlayingAs = PLAYER_CLASSD Then
							EntityType Players(Int(TypedChatMsg))\Pivot,hit_invisible
							Players(Int(TypedChatMsg))\VisibilityTimer = 0
						EndIf
						Players(Int(TypedChatMsg))\PlayingAs = PLAYER_REDMIST
						EntityTexture GetChild(Players(Int(TypedChatMsg))\Pivot,1),tex173
					EndIf
				ElseIf Lower(Left(TypedChatMsg,7))="!classd" Then
					TypedChatMsg = Trim(Replace(TypedChatMsg,"!classd",""))
					If Int(TypedChatMsg)=0 Then
						PlayingAs = PLAYER_CLASSD
					ElseIf Players(Int(TypedChatMsg))<>Null Then
						If Players(Int(TypedChatMsg))\PlayingAs <> PLAYER_CLASSD Then
							EntityType Players(Int(TypedChatMsg))\Pivot,hit_friendly
						EndIf
						Players(Int(TypedChatMsg))\PlayingAs = PLAYER_CLASSD
						EntityTexture GetChild(Players(Int(TypedChatMsg))\Pivot,1),PlayerTex
					EndIf
				ElseIf Lower(TypedChatMsg)="!bot" Then
					For giveID=1 To 32
						If Players(giveID)<>Null Then
							Exit
						EndIf
					Next
					If giveID>-1 Then
						For giveID=1 To 32
							If giveID >=32 Then giveID = -1 : Exit
							If (Players(giveID)=Null) Then Exit
						Next
					EndIf
					If giveID<32 And giveID>0 Then ;server can accept another player
						If Players(giveID)=Null Then
							Players(giveID)=New Player
							Players(giveID)\IsBot = True
							Players(giveID)\BotAngle = 270.0
							Players(giveID)\IP=0
							Players(giveID)\Port=0
							Players(giveID)\Pivot=CreatePivot()
							RotateEntity Players(giveID)\Pivot,0.0,-90.0,0.0
							tempMesh% = CopyEntity(PlayerMesh)
							ScaleEntity tempMesh, 0.15,0.15,0.15
							PositionEntity tempMesh,0.0,-0.3,0.0,True
							EntityParent tempMesh,Players(giveID)\Pivot
							EntityTexture tempMesh,PlayerTex
							PositionEntity Players(giveID)\Pivot,-2.5,-1.3,-0.5,True
							EntityRadius Players(giveID)\Pivot, 0.3
							Players(giveID)\PlayingAs = Rand(0,2)
							
							Select Players(giveID)\PlayingAs
								Case PLAYER_CLASSD
									EntityTexture GetChild(Players(giveID)\Pivot,1),PlayerTex
									EntityType Players(giveID)\Pivot, hit_friendly
								Case PLAYER_MENTAL
									EntityTexture GetChild(Players(giveID)\Pivot,1),mental
									EntityType Players(giveID)\Pivot, hit_invisible;monster
								Case PLAYER_REDMIST
									EntityTexture GetChild(Players(giveID)\Pivot,1),tex173
									EntityType Players(giveID)\Pivot, hit_invisible;monster
							End Select
						EndIf
						Players(giveID)\Name = "BOT (player "+Str(giveID)+")"
					EndIf

				ElseIf Lower(Left(TypedChatMsg,7))="!rename" Then
					Local renameID% = Int(Mid(TypedChatMsg,9,Instr(TypedChatMsg," ",9)-9))
					
					Local newName$ = Right(TypedChatMsg,Len(TypedChatMsg)-Instr(TypedChatMsg," ",9))
					;AddChatMsg("RENAMING '"+renameID+"' TO '"+newName+"'",255,255,255)
					If renameID = 0 Then
						PlayerName = newName
					ElseIf Players(renameID)<>Null Then
						Players(renameID)\Name = newName
					EndIf
				ElseIf Lower(Left(TypedChatMsg,7))="!global" Then
					TypedChatMsg = Right(TypedChatMsg,Len(TypedChatMsg)-7)
					AddChatMsg("[GLOBAL] "+PlayerName+": "+TypedChatMsg,0,255,0,0,True)
				Else
					If PlayingAs=PLAYER_CLASSD Then
						AddChatMsg(PlayerName+": "+TypedChatMsg,50,70,255,0,False)
					Else
						AddChatMsg(PlayerName+": "+TypedChatMsg,150,0,0,0,True)
					EndIf
				EndIf
			EndIf
			TypedChatMsg = ""
			ChatOpen = False
		EndIf
	Else
		PositionEntity camera, EntityX(collider), EntityY(collider)+1, EntityZ(collider)
		MoveEntity camera, side, up, 0
		If (Not fullscreen) Then ShowPointer
		;FlushKeys()
		;Cls
		;RenderWorld
		SetFont MenuFont2
		If (Rand(5)=1) Then
			Local tmpp2%
			tmpp2 = Rand(100)
			Color tmpp2,tmpp2,tmpp2
			Text GraphicsWidth()/2-Rand(50)+Rand(50),GraphicsHeight()/2-Rand(50)+Rand(50)-200,"PAUSED",True,False
		EndIf
		Color 255,255,255
		Text GraphicsWidth()/2,GraphicsHeight()/2-200,"PAUSED",True,False
		SetFont MenuFont1
		Color 30,30,30
		If (KeyHit(1)) Or (Button(GraphicsWidth()/2-200,GraphicsHeight()/2,400,20,"Resume")) Then
			Flip
			MoveMouse GraphicsWidth()/2,GraphicsHeight()/2
			Paused = 0
		EndIf
		
		Color 0,0,0
		Text GraphicsWidth()/2-200+1,GraphicsHeight()/2-35+1,"Name: "+PlayerName
		Color 255,255,255
		Text GraphicsWidth()/2-200,GraphicsHeight()/2-35,"Name: "+PlayerName
		
		If PlayState=GAME_CLIENT Then
			Color 0,0,0
			Text GraphicsWidth()/2-200+1,GraphicsHeight()/2+65+1,"IP: "+ServerAddress
			Color 255,255,255
			Text GraphicsWidth()/2-200,GraphicsHeight()/2+65,"IP: "+ServerAddress
		Else
			Color 0,0,0
			Text GraphicsWidth()/2-200+1,GraphicsHeight()/2+65+1,"Hosting; share your IP with your friends"
			Color 255,255,255
			Text GraphicsWidth()/2-200,GraphicsHeight()/2+65,"Hosting; share your IP with your friends"
		EndIf
		
		Color 30,30,30
		If (Button(GraphicsWidth()/2-200,GraphicsHeight()/2+35,400,20,"Create Server",(PlayState<>GAME_SOLO))) Then
;			PlayState=GAME_SERVER
;			Server% = CreateTCPServer(8734)
;			Players(0)=New Player
;			Players(0)\IP=0
;			Players(0)\Port=collider
;			Players(0)\Name = "yolo_swag"
;			PlayerCount=PlayerCount+1
		EndIf
		Color 30,30,30
		If (Button(GraphicsWidth()/2-200,GraphicsHeight()/2+100,400,20,"Join Server",(PlayState<>GAME_SOLO))) Then
			;JoinServer("localhost");localhost
		EndIf
		Color 30,30,30
		If (Button(GraphicsWidth()/2-200,GraphicsHeight()/2+135,400,20,"Quit")) Then
			If PlayState=GAME_CLIENT Then
				WriteLine(Server,"disconnect") : WriteInt(Server,ID)
				SendUDPMsg(Server,Players(0)\IP,Players(0)\Port) : Delay 100
			EndIf
			Flip: Delay 8 : End
		EndIf
		
		Color 255,255,255
		If (PlayState=GAME_SERVER) Then
			Text 5,5,"You are hosting a server"
		ElseIf (PlayState=GAME_CLIENT) Then
			Text 5,5,"You are connected to a server"
		EndIf
		
		If fullscreen Then DrawImage PointerImg,MouseX(),MouseY()
		Flip
		;Delay 8
	EndIf
	;Text 5,5,Str(MapResult)
	Flip
	
	KeyHit1 = KeyHit(1)
	
Wend 

;---------------------------------------------------------------------------------
;---------------------------------------------------------------------------------

Function FloatEquals%(a#,b#)
	Return Abs(a-b)<1.0
End Function

Function min#(n1#, n2#)
	If n1 < n2 Then Return n1 Else Return n2
End Function

Function max#(n1#, n2#)
	If n1 > n2 Then Return n1 Else Return n2
End Function

Function CurveValue#(number#, oldn#, smooth#)
    Return oldn + (number - oldn) * (1.0 / smooth)
End Function

Function CurveAngle#(number#,oldn#,smooth#)
	d# = number-oldn
	While d<-180.0
		d=d+360.0
	Wend
	While d>=180.0
		d=d-360.0
	Wend
	v# = oldn+(d/smooth)
	Return v
End Function

Function MouseLook()

	PositionEntity camera, EntityX(collider), EntityY(collider)+1, EntityZ(collider)
	MoveEntity camera, side, up, 0
	;MoveEntity player, side, up, 0	

	; -- Update the smoothing que to smooth the movement of the mouse.
	mouse_x_speed_5# = mouse_x_speed_4#
	mouse_x_speed_4# = mouse_x_speed_3#
	mouse_x_speed_3# = mouse_x_speed_2#
	mouse_x_speed_2# = mouse_x_speed_1#
	mouse_x_speed_1# = MouseXSpeed ( )
	mouse_y_speed_5# = mouse_y_speed_4#
	mouse_y_speed_4# = mouse_y_speed_3#
	mouse_y_speed_3# = mouse_y_speed_2#
	mouse_y_speed_2# = mouse_y_speed_1#
	
	If InvertMouse Then
		mouse_y_speed_1# = -MouseYSpeed ( )	
	Else
		mouse_y_speed_1# = MouseYSpeed ( )	
	EndIf
	
	
	the_yaw# = ( ( mouse_x_speed_1# + mouse_x_speed_2# + mouse_x_speed_3# + mouse_x_speed_4# + mouse_x_speed_5# ) / 5.0 ) * mouselook_x_inc#
	the_pitch# = ( ( mouse_y_speed_1# + mouse_y_speed_2# + mouse_y_speed_3# + mouse_y_speed_4# + mouse_y_speed_5# ) / 5.0 ) * mouselook_y_inc#
	;^^^^^^

	TurnEntity collider, 0.0, -the_yaw#, 0.0 ; Turn the user on the Y (yaw) axis.
	user_camera_pitch# = user_camera_pitch# + the_pitch#
	; -- Limit the user's camera to within 180 degrees of pitch rotation. 'EntityPitch()' returns useless values so we need to use a variable to keep track of the camera pitch.
	If user_camera_pitch# > 70.0 Then user_camera_pitch# = 70.0
	If user_camera_pitch# < -70.0 Then user_camera_pitch# = -70.0
	;^^^^^^
	RotateEntity camera, user_camera_pitch, EntityYaw(collider),0 ; Pitch the user's camera up and down.
	
	; -- Limit the mouse's movement. Using this method produces smoother mouselook movement than centering the mouse each loop.
	If ( MouseX() > mouse_right_limit ) Or ( MouseX() < mouse_left_limit ) Or ( MouseY() > mouse_bottom_limit ) Or ( MouseY() < mouse_top_limit )
		MoveMouse viewport_center_x, viewport_center_y
	EndIf
	;^^^^^^

End Function

Function UpdateEnemies() 
	For enemy.ENEMIES = Each ENEMIES
		
		enemy\oldX = EntityX(enemy\collider)
		enemy\oldZ = EntityZ(enemy\collider)
		
		PositionEntity enemy\obj, EntityX(enemy\collider), EntityY(enemy\collider) - 0.4, EntityZ(enemy\collider)	
		RotateEntity enemy\obj, 0, EntityYaw(enemy\collider), EntityRoll(enemy\collider)
		
		MoveEntity(enemy\collider, 0,1,0)
		MoveEntity(collider, 0,1,0)		
		
		If EntityVisible(enemy\collider, camera) Then
			PointEntity enemy\collider, camera
			RotateEntity enemy\collider, 0, EntityYaw(enemy\collider), EntityRoll(enemy\collider)
			If EntityDistance(enemy\obj, camera) > 1.5 Then 
				MoveEntity enemy\collider, 0, 0, enemy\speed
				;Animate2(enemy\obj, AnimTime(enemy\obj), 0, 14, 0.3)
			Else
				;Animate2(enemy\obj, AnimTime(enemy\obj), 32, 44, 0.2)			
			EndIf
			BlurTimer = 200
		EndIf 
		
		MoveEntity(enemy\collider, 0,-1,0)
		MoveEntity(collider, 0,-1,0)	
		
		If EntityCollided(enemy\collider,hit_map) Then
			enemy\yspeed = 0
		Else
			enemy\yspeed = enemy\yspeed - 0.004
			TranslateEntity enemy\collider,0,enemy\yspeed,0	
		EndIf
	Next
End Function 

Function CreateEnemy.ENEMIES(nx#,ny#,nz#,texture) 
	If CurrEnemy <> Null Then
		FreeEntity CurrEnemy\collider
		FreeEntity CurrEnemy\obj
		Delete CurrEnemy
		
		CurrEnemy = Null
	EndIf
	
	enemy.ENEMIES = New ENEMIES
	
	enemy\obj = LoadAnimMesh("GFX\mental.b3d");CopyMesh(mentalmesh)
	ScaleEntity enemy\obj, 0.17,0.17,0.17
	
	enemy\collider = CreatePivot()
	PositionEntity enemy\collider, nx,ny,nz
	PositionEntity enemy\obj, nx,ny,nz	
	EntityType enemy\collider, hit_monster
	EntityRadius enemy\collider, 0.3
	
	;enemy\collider2 = CreatePivot()
	;EntityRadius enemy\collider2, 2	
	
	EntityTexture enemy\obj, texture 
	enemy\texture = texture
	
	Return enemy.ENEMIES
End Function



Function CreateGlimpse.GLIMPSES(nx#,ny#,nz#,texture)
	g.glimpses = New GLIMPSES
	g\obj= CreateSprite()
	EntityTexture g\obj, texture
	ScaleSprite g\obj, 0.3,0.3
	PositionEntity g\obj ,nx,ny,nz
End Function

Function UpdateGlimpses()
	If PlayingAs<>PLAYER_CLASSD Then
		For g.glimpses = Each GLIMPSES
			HideEntity g\obj
		Next
	Else
		For g.glimpses = Each GLIMPSES
			ShowEntity g\obj
			If PlayerFloor-1 = Int((-EntityY(g\obj)-0.5)/2) Then 
				If Distance2(EntityX(g\obj),EntityY(collider),EntityZ(g\obj))<2.3 And EntityVisible(g\obj, camera) Then
					EmitSound(NoSFX, g\obj)
					
					FreeEntity g\obj
					Delete g				
				EndIf
			EndIf
		Next
	EndIf
	
End Function

Function CreateGlimpses()
	Local FloorX#, FloorY#, FloorZ#
	
	
	For i = 1 To flooramount-1
		If FloorActions(i) = 0 And Rand(7)=1 Then 
			FloorX# =4
			FloorY#=-(i-1)*2-1.0
			
			If Floor(i/2.0)=Ceil(i/2.0) Then ;parillinen
				FloorZ=-6.55
				StartX = 7.2 
				EndX = 0.8
			Else ;pariton
				FloorZ=-0.3
				StartX = 0.8
				EndX = 7.2
			EndIf
			
			CreateGlimpse(Rand(startx,endx), FloorY,FloorZ, GlimpseTextures(Rand(0,1)))
			
		EndIf 
	Next
	
	
End Function 



Function Animate2(ent, curr#, start, quit, speed#)
	SetAnimTime ent, curr + speed
	If AnimTime(ent) > quit Then SetAnimTime ent, start
	If AnimTime(ent) < start Then SetAnimTime ent, quit
End Function 

Function GetMentalRoomMesh(ent%)
	newMesh% = CreateMesh(ent)
	EntityType newMesh,hit_map2
	For i%=1 To CountSurfaces(ent)
		surf% = GetSurface(ent,i)
		brush% = GetSurfaceBrush(surf)
		If brush<>0 Then
			tex% = GetBrushTexture(brush)
			If tex<>0 Then
				If Instr(TextureName(tex),"concretefloor") Then
					newSurf = CreateSurface(newMesh)
					For j=0 To CountVertices(surf)-1
						AddVertex(newSurf,VertexX(surf,j),VertexY(surf,j),VertexZ(surf,j))
					Next
					For j=0 To CountTriangles(surf)-1
						AddTriangle(newSurf,TriangleVertex(surf,j,0),TriangleVertex(surf,j,1),TriangleVertex(surf,j,2))
					Next
				EndIf
				FreeTexture tex
			EndIf
			FreeBrush brush
		EndIf
	Next
	EntityParent newMesh,ent
	EntityAlpha newMesh,0.0
End Function

Function LoadMap()
	Dim FloorEntities%(Len(MapString))
	flooramount = Len(MapString)
	
	map0 = LoadMesh("GFX\map0.x") ;0
	GetMentalRoomMesh(map0)
	map = LoadMesh("GFX\map.x") ;127
	GetMentalRoomMesh(map)
	map1 = LoadMesh("GFX\map1.x") ;1
	GetMentalRoomMesh(map1)
	map2 = LoadMesh("GFX\map2.x") ;2
	GetMentalRoomMesh(map2)
	map3 = LoadMesh("GFX\map3.x") ;3
	GetMentalRoomMesh(map3)
	map4 = LoadMesh("GFX\map4.x") ;4
	GetMentalRoomMesh(map4)
	map5 = LoadMesh("GFX\map5.x") ;5
	GetMentalRoomMesh(map5)
	map6 = LoadMesh("GFX\map6.x") ;6
	GetMentalRoomMesh(map6)
	map7 = LoadMesh("GFX\maze.x") ;7
	GetMentalRoomMesh(map7)
	
	door = CreateCube()
	ScaleEntity(door, 0.5,1,0.5)
	EntityType door, hit_map
	PositionEntity(door, -3.5, -1, -0.5)
	
	doortexture = LoadTexture("GFX\door.jpg")
	EntityTexture(door, doortexture)
	
	Local temp%
	
	For i=1 To Len(MapString)
		FloorActions(i-1)=0
		FloorTimer(i-1)=0
		Select Asc(Mid(MapString,i,1))
			Case 0
				temp=CopyEntity(map0)
			Case 1
				temp=CopyEntity(map1)
			Case 2
				temp=CopyEntity(map2)
			Case 3
				temp=CopyEntity(map3)
			Case 4
				temp=CopyEntity(map4)
			Case 5
				temp=CopyEntity(map5)
			Case 6
				temp=CopyEntity(map6)
			Case 7
				temp=CopyEntity(map7)
			Default
				temp=CopyEntity(map)
		End Select
		
		FloorEntities(i-1) = temp
		EntityPickMode temp, 2
		EntityType temp, hit_map
		
		If Floor((i-1)/2.0)=Ceil((i-1)/2.0) Then ;parillinen
			PositionEntity(temp, 0,-(i-1)*2,0)		
		Else ;pariton
			TurnEntity temp, 0, 180, 0
			PositionEntity(temp,8,-(i-1)*2,-7)		
		EndIf
	Next
	
	DrawFloorMarkers()
	
	FreeEntity map0
	FreeEntity map
	FreeEntity map1
	FreeEntity map2
	FreeEntity map3
	FreeEntity map4
	FreeEntity map5
	FreeEntity map6
	FreeEntity map7
		
End Function

Function CreateMap%(floors)
	
	Dim FloorEntities%(floors)
	
	MapString = ""
	
	map0 = LoadMesh("GFX\map0.x") ;0
	GetMentalRoomMesh(map0)
	map = LoadMesh("GFX\map.x") ;127
	GetMentalRoomMesh(map)
	map1 = LoadMesh("GFX\map1.x") ;1
	GetMentalRoomMesh(map1)
	map2 = LoadMesh("GFX\map2.x") ;2
	GetMentalRoomMesh(map2)
	map3 = LoadMesh("GFX\map3.x") ;3
	GetMentalRoomMesh(map3)
	map4 = LoadMesh("GFX\map4.x") ;4
	GetMentalRoomMesh(map4)
	map5 = LoadMesh("GFX\map5.x") ;5
	GetMentalRoomMesh(map5)
	map6 = LoadMesh("GFX\map6.x") ;6
	GetMentalRoomMesh(map6)
	map7 = LoadMesh("GFX\maze.x") ;7
	GetMentalRoomMesh(map7)
	
	;EntityTexture map, tex1
	
	door = CreateCube()
	ScaleEntity(door, 0.5,1,0.5)
	EntityType door, hit_map
	PositionEntity(door, -3.5, -1, -0.5)
	
	doortexture = LoadTexture("GFX\door.jpg")
	EntityTexture(door, doortexture)
	
	FloorActions(1)=ACT_PROCEED
	FloorTimer(1)=1
	
	If Rand(2)=1 Then
		temp=Rand(3,4)
		FloorActions(temp)=ACT_RADIO2
		FloorTimer(temp)=1			
	EndIf
	
	If Rand(3)<3 Then
		temp=Rand(5,6)
		FloorActions(temp)=ACT_RADIO3
		FloorTimer(temp)=1
	EndIf	
	
	FloorActions(7)=ACT_LOCK
	FloorTimer(7)=1	
	
	If Rand(2)=1 Then
		temp=Rand(8,9)
		FloorActions(temp)=ACT_RADIO4
		FloorTimer(temp)=1
	EndIf
	
	temp=Rand(10,11)
	FloorActions(temp)=ACT_BREATH
	FloorTimer(temp)=1
	
	temp=Rand(12,13)
	FloorActions(temp)=ACT_STEPS
	FloorTimer(temp)=1
	
	;If Rand(5)<5 Then 
		temp = Rand(10,19)
		FloorActions(temp)=ACT_FLASH
		FloorTimer(temp)=Rand(1,3)
	;EndIf
	
	temp = Rand(20,22)
	FloorActions(temp)=ACT_LIGHTS
	FloorTimer(temp)=1
	
	Select Rand(4)
		Case 1
			temp = Rand(25,28)
			FloorActions(temp)=ACT_TRICK1
			FloorTimer(temp)=1		
		Case 2
			temp = Rand(25,28)
			FloorActions(temp)=ACT_TRICK2
			FloorTimer(temp)=1		
	End Select 
	
	
	temp = Rand(29,33)
	FloorActions(temp)=ACT_RUN
	FloorTimer(temp)=1
	
	temp = Rand(34,37)
	FloorActions(temp)=ACT_173
	FloorTimer(temp)=1
	
	temp = Rand(40,60)
	FloorActions(temp)=ACT_RUN
	FloorTimer(temp)=1
	temp = Rand(40,60)
	FloorActions(temp)=ACT_ROAR
	FloorTimer(temp)=1		
	temp = Rand(40,60)
	FloorActions(temp)=ACT_TRAP
	FloorTimer(temp)=1			
	temp = Rand(40,60)
	FloorActions(temp)=ACT_FLASH
	FloorTimer(temp)=1
	
	randact=0
	For i = 0 To 8
		Select Rand(10)
			Case 1,9
				randact=ACT_CELL 
			Case 2
				randact=ACT_FLASH
			Case 3
				randact = ACT_TRICK1
			Case 4
				randact = ACT_TRICK2
			Case 5
				randact = ACT_BREATH
			Case 6
				randact= ACT_STEPS
			Case 7
				randact = ACT_TRAP
			Case 8
				randact = ACT_ROAR
		End Select 
	
		temper = False
		Repeat
			temp = Rand(25,69)
			If FloorActions(temp)=0 Then
				FloorActions(temp)=randact
				FloorTimer(temp)=1
				temper = True
			EndIf
		Until temper=True
	Next
	
	randact=0
	For i = 0 To 60
		Select Rand(10)
			Case 1,9
				randact=ACT_CELL 
			Case 2
				randact=ACT_LIGHTS
			Case 3
				randact = ACT_RUN
			Case 3
				randact = ACT_TRICK1
			Case 4
				randact = ACT_TRICK2
			Case 5
				randact = ACT_BREATH
			Case 6
				randact= ACT_STEPS
			Case 7
				randact = ACT_TRAP
			Case 8
				randact = ACT_ROAR
		End Select 
		
		temper = False
		Repeat
			temp = Rand(75,200)
			If FloorActions(temp)=0 Then
				FloorActions(temp)=randact
				FloorTimer(temp)=1
				temper = True
			EndIf
		Until temper=True
	Next	
	
	temp = Rand(150,200)
	FloorActions(temp)=ACT_DARKNESS
	FloorTimer(temp)=1
	
	For i = 0 To floors-1
		If i = 0 Then
			temp = CopyEntity(map0) : MapString = MapString+Chr(0)
		Else
			Select FloorActions(i+1)
				Case ACT_173
					temp = CopyEntity(map2) : MapString = MapString+Chr(2)
				Case ACT_CELL
					temp = CopyEntity(map1) : MapString = MapString+Chr(1)	
				Case ACT_TRICK1
					temp = CopyEntity(map4) : MapString = MapString+Chr(4)	
				Case ACT_TRICK2
					temp = CopyEntity(map5) : MapString = MapString+Chr(5)
				Case ACT_FLASH, ACT_RUN, ACT_WALK, ACT_LIGHTS, ACT_TRAP, ACT_LOCK
					temp  = CopyEntity(map) : MapString = MapString+Chr(127)
				Case 0
					Select Rand(20)
						Case 1,2
							temp = CopyEntity(map1) : MapString = MapString+Chr(1)
						Case 3,4
							temp = CopyEntity(map2) : MapString = MapString+Chr(2)
						Case 5,6
							temp = CopyEntity(map3) : MapString = MapString+Chr(3)
						Case 7
							temp = CopyEntity(map4) : MapString = MapString+Chr(4)
						Case 8
							temp = CopyEntity(map5) : MapString = MapString+Chr(5)
						Case 9
							temp = CopyEntity(map6) : MapString = MapString+Chr(6)
						Case 10
							If i > 40 Then temp = CopyEntity(map7) : MapString = MapString+Chr(7) Else temp = CopyEntity(map) : MapString = MapString+Chr(127)
						Default
							temp = CopyEntity(map) : MapString = MapString+Chr(127)
					End Select
				Default 
					temp = CopyEntity(map) : MapString = MapString+Chr(127)		
			End Select 
		EndIf
		FloorEntities(i) = temp
		
		EntityPickMode temp, 2
		EntityType temp, hit_map

		If Floor(i/2.0)=Ceil(i/2.0) Then ;parillinen
			PositionEntity(temp, 0,-i*2,0)		
		Else ;pariton
			TurnEntity temp, 0, 180, 0
			PositionEntity(temp,8,-i*2,-7)		
		EndIf
	Next
	
	DrawFloorMarkers()
	
	FreeEntity map0
	FreeEntity map
	FreeEntity map1
	FreeEntity map2
	FreeEntity map3
	FreeEntity map4
	FreeEntity map5
	FreeEntity map6
	FreeEntity map7
	
	Return RndSeed()
	
End Function

Function Round#(number#)
	If (number-Floor(number)>=0.5) Then Return Ceil(number)
	Return Floor(number)
End Function

;Function Rand%(from,tto=0)
;	Local rett%,prevseed%
;	prevseed = RndSeed()
;	If (tto<from) Then
;		rett = Int(Round(Rnd(1,from)))
;	Else
;		rett = Int(Round(Rnd(from,tto)))
;	EndIf
;	SeedRnd prevseed+2
;	Return rett
;End Function

Function DrawFloorMarkers()
	
	Local RenderTex%
	
	SetFont font 
	For i = 1 To flooramount
		RenderTex = CreateTexture(512,512,1+256)
		number$=""
		
		Select Rand(600)
			Case 1
				number = ""
			Case 2
				number = Rand(33,122)
			Case 3
				number = "NIL"	
			Case 4
				number = "?"
			Case 5
				number = "NO"
			Case 6
				number = "stop"
			Default
				number = (i+1)
		End Select
		
		If i > 140 Then
			number =""
			For n = 1 To Rand(4)
				number = number + Chr(Rand(33,122))
			Next
		EndIf
		
		;FloorNumberTexture(i)=CreateTexture(512,512) 	
		
		cube = CreateCube()
		ScaleEntity cube, 0.25,0.25,0.25
		
		SetBuffer TextureBuffer(RenderTex)
		
		Cls
		DrawImage sign, 0,0
		Color 0,0,0
		
		Text(256,256,number,True,True)
		
		SetBuffer BackBuffer()
		
		;CopyRect(0,0,512,512,0,0,TextureBuffer(RenderTex),TextureBuffer(FloorNumberTexture(i)) )
		
		EntityTexture cube,RenderTex ;FloorNumberTexture(i)
		
		FreeTexture RenderTex
		
		If Floor(i/2.0)=Ceil(i/2.0) Then 
			PositionEntity(cube, -0.24,-i*2-0.6,-0.5)
		Else
			PositionEntity(cube,7.4+0.6+0.24,-i*2-0.6,-7+0.5)
		EndIf
		
		;FreeTexture FloorNumberTexture(i)
		
	Next
	SetFont font1
	
	SetBuffer BackBuffer() 	
	
End Function 


Function CheckHit()
	For enemy.ENEMIES = Each ENEMIES
		
		If EntityDistance(enemy\obj, collider) < 30 Then
			If playerweapon <> 0 Then
				If EntityCollided(Righthand, hit_head) Then End 
			Else
				If EntityCollided(RightHand, hit_head) Then End
			EndIf 
		EndIf 
			 
	Next	
End Function 

Function UpdateFloors()
	
	For i% = 0 To flooramount-1
		HideEntity FloorEntities(i)
	Next
	If PlayerFloor-1>=0 Then ShowEntity FloorEntities(PlayerFloor-1)
	If PlayerFloor-1>0 Then ShowEntity FloorEntities(PlayerFloor-2)
	If PlayerFloor-1<flooramount-1 And PlayerFloor-1>=0 Then ShowEntity FloorEntities(PlayerFloor)
	If PlayState=GAME_SERVER Then
		For j% = 1 To 31
			If Players(j)<>Null Then
				Local otherPlayerFloor% = (-EntityY(Players(j)\Pivot)-0.5)/2
				If otherPlayerFloor-1>=0 Then ShowEntity FloorEntities(otherPlayerFloor-1)
				If otherPlayerFloor-1>0 Then ShowEntity FloorEntities(otherPlayerFloor-2)
				If otherPlayerFloor-1<flooramount-1 And otherPlayerFloor-1>=0 Then ShowEntity FloorEntities(otherPlayerFloor)
			EndIf
		Next
	EndIf
	
	If PlayState<>GAME_SOLO Then Return
	
	Local FloorX#, FloorY#, FloorZ#

	For i = 0 To flooramount-1
		If FloorTimer(i)>0 Then
		
			FloorX# =4
			FloorY#=-1-(i-1)*2
			
			If Floor(i/2.0)=Ceil(i/2.0) Then ;parillinen
				FloorZ=-6.75
				StartX = 7.5 
				EndX = 0.5
			Else ;pariton
				FloorZ=-0.25
				StartX = 0.5 
				EndX = 7.5
			EndIf
			
			Select FloorActions(i)

				Case ACT_LIGHTS
					If FloorTimer(i)>1 Then
						FloorTimer(i) = FloorTimer(i)+1
						
						If FloorTimer(i) >100 Then
							Animate2(CurrEnemy\obj,AnimTime(CurrEnemy\obj),1,14,0.15)	
							If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.8 Then
								KillTimer = max(KillTimer,1)
							EndIf
						EndIf
						
						If FloorTimer(i) = 100 Then CurrEnemy = CreateEnemy(EndX, FloorY-0.5, FloorZ, mental) : CurrEnemy\speed = 0.01
						If FloorTimer(i) = 210 Then PlaySound (FireOn)
						If FloorTimer(i) = 250 Then AmbientLight Brightness,Brightness,Brightness
						If FloorTimer(i) = 290  Then PlaySound(HorrorSFX(2))
						If FloorTimer(i) = 450 Then
							FreeEntity CurrEnemy\collider
							FreeEntity CurrEnemy\obj
							Delete CurrEnemy
							
							FloorTimer(i)=0
						EndIf
					EndIf
				Case ACT_RUN
					If FloorTimer(i) > 1 Then
						FloorTimer(i) = FloorTimer(i)+1
						
						If FloorTimer(i) >100 Then
							If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.8 Then
								KillTimer = max(KillTimer,1)
							EndIf
						EndIf
						
						If FloorTimer(i) = 100 Then 
							CurrEnemy = CreateEnemy(EndX, FloorY-0.5, FloorZ,mental) : CurrEnemy\speed = 0.035
						ElseIf FloorTimer(i) = 130 Or FloorTimer(i) = 260 Or FloorTimer(i) = 380 ;valot syttyy
							PlaySound(HorrorSFX(0))
							CameraFogRange camera, 1, 20
							AmbientLight Brightness,Brightness,Brightness
							CurrEnemy\speed = 0.0
						ElseIf FloorTimer(i) = 170 Or FloorTimer(i) = 300 ; valot sammuu
							CurrEnemy\speed = 0.03
							CameraFogRange camera, 1, 2.5
							AmbientLight 15,15,15
						ElseIf FloorTimer(i) = 450
							CameraFogRange camera, 1, 2.5
							AmbientLight Brightness,Brightness,Brightness
							FreeEntity CurrEnemy\collider
							FreeEntity CurrEnemy\obj
							Delete CurrEnemy
							
							FloorTimer(i)=0	
						EndIf
					
					EndIf
				Case ACT_173
					If FloorTimer(i) > 1 Then
						FloorTimer(i) = FloorTimer(i)+1
						
						If EntityVisible(CurrEnemy\collider, camera) And FloorTimer(i)>150 Then
							If EntityInView(CurrEnemy\collider, camera) Then
								CurrEnemy\speed = 0.0
								Animate2(CurrEnemy\obj,AnimTime(CurrEnemy\obj),206,250,0.05)
								If FloorTimer(i)<10000 Then PlaySound(HorrorSFX(2)) : FloorTimer(i)=10001
							Else
								CurrEnemy\speed = 0.02
								If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.8 Then
									KillTimer = max(KillTimer,1)
									
									FloorTimer(i)=0
								EndIf
							EndIf	
						Else
							CurrEnemy\speed = 0.0
						EndIf
						If (FloorTimer(i) Mod 660) = 15 Then EmitSound(DontlookSFX, CurrEnemy\collider)
					EndIf				
					
					If PlayerFloor > i Then
						FreeEntity CurrEnemy\collider
						FreeEntity CurrEnemy\obj
						Delete CurrEnemy
						
						FloorTimer(i)=0
					EndIf
				Case ACT_TRAP
					If FloorTimer(i) > 2 Then 
						DebugLog  FloorTimer(i)
						FloorTimer(i) = FloorTimer(i) +1
						If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.8 Then
							KillTimer = max(KillTimer,1)
						EndIf	
						If FloorTimer(i) = 500 Then
							FreeEntity CurrObject
							PlaySound StoneSFX
						ElseIf FloorTimer(i) = 1000
							FreeEntity CurrEnemy\collider
							FreeEntity CurrEnemy\obj
							Delete CurrEnemy
							FloorTimer(i) = 0
						EndIf
					EndIf
			End Select
		EndIf
	Next

		
	If FloorTimer(PlayerFloor)>0 Then
		FloorX# =4
		FloorY#=-1-(PlayerFloor-1)*2
		
		If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen
			FloorZ=-6.5
			StartX = 7.5 
			EndX = 0.5
		Else ;pariton
			FloorZ=-0.5
			StartX = 0.5 
			EndX = 7.5	
		EndIf
		
		Select FloorActions(PlayerFloor)
			Case ACT_PROCEED
				FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
				If FloorTimer(PlayerFloor) =150 Then
					PlaySound RadioSFX(0)
					FloorTimer(PlayerFloor)=0
				EndIf
			Case ACT_RADIO2
				PlaySound RadioSFX(1)	;signal seems to be getting weaker
				FloorTimer(PlayerFloor)=0
			Case ACT_RADIO3		
				PlaySound RadioSFX(2)	;good luck
				FloorTimer(PlayerFloor)=0
			Case ACT_RADIO4		
				PlaySound RadioSFX(3)	;MÖRKÖILYÄ	
				FloorTimer(PlayerFloor)=0
			Case ACT_FLASH ;mörkö vilahtaa käytävän päässä
				If FloorTimer(PlayerFloor)= 1 Then
					If Distance2(EndX, FloorY, FloorZ)<1.5 Then
						CurrEnemy = CreateEnemy(EndX, FloorY-0.5, FloorZ,mental)
						PlaySound(HorrorSFX(Rand(0,2)))
						FloorTimer(PlayerFloor) = 5
					EndIf
				ElseIf FloorTimer(PlayerFloor)= 2
					If Distance2(FloorX, FloorY, FloorZ)<1.5 Then
						CurrEnemy = CreateEnemy(FloorX, FloorY-0.5, FloorZ,mental)
						PlaySound(HorrorSFX(Rand(0,2)))
						FloorTimer(PlayerFloor) = 5
					EndIf
				ElseIf FloorTimer(PlayerFloor)= 3
					If Distance2(startX, FloorY, FloorZ)<1.5 Then
						CurrEnemy = CreateEnemy(startX, FloorY-0.5, FloorZ,mental)
						PlaySound(HorrorSFX(Rand(0,2)))
						FloorTimer(PlayerFloor) = 5
					EndIf	
				Else
					FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
					If FloorTimer(PlayerFloor) > 30 Then
					 	FreeEntity CurrEnemy\collider
						FreeEntity CurrEnemy\obj						
						Delete CurrEnemy
						
						FloorTimer(PlayerFloor)=0
					EndIf
				EndIf
			Case ACT_LIGHTS
				If FloorTimer(PlayerFloor)= 1 Then
					If Distance2(FloorX, FloorY, FloorZ)<1.0 Then
						PlaySound(HorrorSFX(1))
						PlaySound(FireOff)
						FloorTimer(PlayerFloor) = 2
						AmbientLight 25,25,25
					EndIf
				EndIf
			Case ACT_STEPS
				If FloorTimer(PlayerFloor)= 1 Then
					FloorTimer(PlayerFloor)= 2
				ElseIf FloorTimer(PlayerFloor)< 3000
					If Distance2(EndX, FloorY, FloorZ)<6 Then 
						PositionEntity SoundEmitter,FloorX+(FloorX-EndX)*1.1,FloorY,FloorZ
						FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
						If FloorTimer(PlayerFloor) Mod 150 < Rand(50) Then
							EmitSound(loudstepsound, SoundEmitter)
							FloorTimer(PlayerFloor) = 51
						EndIf
					EndIf
				EndIf		
			Case ACT_BREATH  
				If FloorTimer( PlayerFloor)= 1 Then
					FloorTimer(PlayerFloor)= 2
				ElseIf FloorTimer(PlayerFloor)< 3000
					If Distance2(EndX, FloorY, FloorZ)<7 Then 
						PositionEntity SoundEmitter,FloorX+(FloorX-EndX)*1.1,FloorY,FloorZ
						FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
						If (FloorTimer(PlayerFloor) Mod 600) < 10 Then
							EmitSound(BreathSFX, SoundEmitter)
							FloorTimer(PlayerFloor) = 11
						EndIf
					EndIf
				EndIf	
			Case ACT_RUN
				If FloorTimer(PlayerFloor)= 1 Then
					If Distance2(FloorX, FloorY, FloorZ)<3.0 Then
						PlaySound(HorrorSFX(1))
						PlaySound(FireOff)
						FloorTimer(PlayerFloor) = 2
						AmbientLight 25,25,25
					EndIf
				EndIf
			Case ACT_173
				If FloorTimer(PlayerFloor)= 1 Then
					If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen 
						CurrEnemy = CreateEnemy(startx-1.8,FloorY-0.5,FloorZ-6.0,tex173)  
					Else ;pariton
						CurrEnemy = CreateEnemy(startx+1.8,FloorY-0.5,FloorZ+6.0,tex173)  
					EndIf
					EntityFX CurrEnemy\obj, 8
					CurrEnemy\speed = 0.0
					FloorTimer(PlayerFloor) = 2
				EndIf
			Case ACT_CELL
				If FloorTimer(PlayerFloor)= 1 Then
					If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen 
						CurrEnemy = CreateEnemy(startx-4.5,FloorY-0.5,FloorZ+3.2,tex173)  
					Else ;pariton
						CurrEnemy = CreateEnemy(startx+4.5,FloorY-0.5,FloorZ-3.2,tex173)  
					EndIf
					EntityFX CurrEnemy\obj, 8
					CurrEnemy\speed = 0.0
					FloorTimer(PlayerFloor) = 2
				Else
					FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
					If CurrEnemy <> Null Then 
						
						Animate2(CurrEnemy\obj,AnimTime(CurrEnemy\obj),206,250,0.05)
						
						If Abs(EntityX(collider)-EntityX(CurrEnemy\collider))<0.025 And Rand(40)=1 Then 
							If CurrEnemy\speed = 0 Then PlaySound (HorrorSFX(2))
							SetAnimTime(CurrEnemy\obj, 0)
							MoveEntity CurrEnemy\collider, 0, 0, 1.5
							FloorTimer(PlayerFloor)= 0
						EndIf
					EndIf
					If (FloorTimer(PlayerFloor) Mod 610)= 5 Then 
						If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen 
							PositionEntity(SoundEmitter,startx-4.5,FloorY-0.5,FloorZ+3.2)
						Else ;pariton
							PositionEntity(SoundEmitter,startx+4.5,FloorY-0.5,FloorZ-3.2)  
						EndIf
						EmitSound(BreathSFX, SoundEmitter)
					EndIf 
				EndIf
			Case ACT_LOCK			
				If Distance2(FloorX, FloorY, FloorZ)<1.0 Then
					wall = CreateCube()
					ScaleEntity(wall, 0.5,1,0.5)
					If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen
						PositionEntity (wall, startx-0.5, FloorY, FloorZ)
					Else ;pariton
						PositionEntity (wall, startx+0.5, FloorY, FloorZ)	
					EndIf
					EntityTexture wall,brickwalltexture	
					EntityType wall, hit_map
					EmitSound (StoneSFX,wall)
					FloorTimer(PlayerFloor)= 0
				EndIf
			Case ACT_TRICK1
				If FloorTimer(PlayerFloor)=1 Then 
					If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen 
						If Distance2(startx-1.5,FloorY-0.5,FloorZ-5.0)<0.25 Then
							CurrEnemy = CreateEnemy(startx-1.5,FloorY-0.5,FloorZ-2.0,tex173)  
							CurrEnemy\speed = 0.01
							EntityFX CurrEnemy\obj, 8
							FloorTimer(PlayerFloor)=2
							PlaySound(HorrorSFX(2))
						EndIf
					Else ;pariton
						If	Distance2(startx+1.5,FloorY-0.5,FloorZ+5.0)<0.25 Then
							CurrEnemy = CreateEnemy(startx+1.5,FloorY-0.5,FloorZ+2.0,tex173)  
							CurrEnemy\speed = 0.01
							EntityFX CurrEnemy\obj, 8
							FloorTimer(PlayerFloor)=2
							PlaySound(HorrorSFX(2))
						EndIf
					EndIf
				Else
					If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.8 Then
						KillTimer = max(KillTimer,1)
					EndIf
				EndIf 
			Case ACT_TRICK2
				If FloorTimer(PlayerFloor)=1 Then 	
					If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen 
						If	Distance2(startx+0.5,FloorY-0.5,FloorZ-5.0)<0.25 Then
							CurrEnemy = CreateEnemy(startx+0.5,FloorY-0.5,FloorZ-2.0,tex173)  
							CurrEnemy\speed = 0.01
							EntityFX CurrEnemy\obj, 8
							FloorTimer(PlayerFloor)=2
							PlaySound(HorrorSFX(2))
						EndIf
					Else ;pariton
						If Distance2(startx-0.5,FloorY-0.5,FloorZ+5.0)<0.25 Then
							CurrEnemy = CreateEnemy(startx-0.5,FloorY-0.5,FloorZ+2.0,tex173)  
							CurrEnemy\speed = 0.01
							EntityFX CurrEnemy\obj, 8
							FloorTimer(PlayerFloor)=2
							PlaySound(HorrorSFX(2))
						EndIf
					EndIf		
				Else
					If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.8 Then
						KillTimer = max(KillTimer,1)
					EndIf	
				EndIf
			Case ACT_TRAP
				If FloorTimer(PlayerFloor)=1 Then 
					CurrObject = CreateCube()
					ScaleEntity(CurrObject, 0.5,1,0.5)
					If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen
						PositionEntity (CurrObject, endx+0.5, FloorY, FloorZ)
					Else ;pariton
						PositionEntity (CurrObject, endx-0.5, FloorY, FloorZ)	
					EndIf
					EntityTexture CurrObject,brickwalltexture	
					EntityType CurrObject, hit_map
					FloorTimer(PlayerFloor)= 2
				ElseIf FloorTimer(PlayerFloor)=2
					If Distance2(FloorX, FloorY, FloorZ)<1.0 Then	
						CurrEnemy = CreateEnemy(startX, FloorY-0.5, FloorZ,mental)
						CurrEnemy\speed = 0.01
						PlaySound(HorrorSFX(Rand(0,2)))
						FloorTimer(PlayerFloor) = 3
					EndIf
				EndIf
			Case ACT_ROAR
				If FloorTimer(PlayerFloor)= 1 Then
					If Distance2(EndX, FloorY, FloorZ)<6 Then 
						PositionEntity SoundEmitter,FloorX,FloorY-3,FloorZ
						EmitSound(RoarSFX, SoundEmitter)
						FloorTimer(PlayerFloor) = 51
					EndIf
				Else
					FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
					If FloorTimer(PlayerFloor)<370 Then
						TranslateEntity(collider, Rnd(-0.005,0.005),Rnd(-0.005,0.005),Rnd(-0.005,0.005))
						TurnEntity(camera, Rnd(-1,1), Rnd(-1,1), Rnd(-1,1))
					Else
						FloorTimer(PlayerFloor) = 0
					EndIf
				EndIf
			Case ACT_DARKNESS
				If FloorTimer(PlayerFloor)= 1 Then
					If Distance2(FloorX, FloorY, FloorZ)<1.0 Then
						wall = CreateCube()
						ScaleEntity(wall, 0.5,1,0.5)
						If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen
							PositionEntity (wall, startx-0.5, FloorY, FloorZ)
						Else ;pariton
							PositionEntity (wall, startx+0.5, FloorY, FloorZ)	
						EndIf
						EntityTexture wall,brickwalltexture	
						EntityType wall, hit_map
						
						wall = CreateCube()
						ScaleEntity(wall, 0.5,1,0.5)
						If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen
							PositionEntity (wall, ENDX+0.5, FloorY, FloorZ)
						Else ;pariton
							PositionEntity (wall, endx-0.5, FloorY, FloorZ)	
						EndIf
						EntityTexture wall,brickwalltexture	
						EntityType wall, hit_map					
						
						EmitSound (StoneSFX,wall)
						FloorTimer(PlayerFloor)= 2
					EndIf		
				ElseIf FloorTimer(PlayerFloor)<600
					FloorTimer(PlayerFloor) = FloorTimer(PlayerFloor)+1
					temp#=max(Brightness-(FloorTimer(PlayerFloor)/600.0)*Brightness,10)
					AmbientLight temp,temp,temp
					
					If FloorTimer(PlayerFloor) = 600 Then
						CurrEnemy = CreateEnemy(FloorX, FloorY-0.5, FloorZ,mental)
						CurrEnemy\speed = 0.01
						PlaySound(HorrorSFX(Rand(0,2)))
						FloorTimer(PlayerFloor) = 601
					EndIf
				Else
					If Distance2(EntityX(CurrEnemy\collider),EntityY(CurrEnemy\collider),EntityZ(CurrEnemy\collider))<0.7 Then
						KillTimer = max(KillTimer,1)
					EndIf
				EndIf
		End Select
		
	EndIf
End Function

Function Distance2#(x1#, y1#, z1#) 
	Local vxcomp# = Abs(x1 - EntityX(collider))
	Local vycomp# = Abs(y1 - EntityY(collider))
	Local vzcomp# = Abs(z1 - EntityZ(collider))
	Return Sqr(vxcomp * vxcomp + vycomp * vycomp + vzcomp * vzcomp)
End Function

Function Kill()
	If KillTimer = 1 Then PlaySound DeathSFX
	
	KillTimer=min(KillTimer+1,100)
	
	AmbientLight 255-KillTimer, 100-KillTimer, 100-KillTimer
	RotateEntity camera, -KillTimer, EntityYaw(camera), EntityRoll(collider)-(KillTimer/2)
	
	If KillTimer>90 Then 
		If PlayState = GAME_SOLO Then
			If PlayerFloor > 130 Then
				Select Rand(7)
					Case 1
						RuntimeError("NO")
					Case 2
						RuntimeError("It's not about whether you die or not, it's about when you die.")
					Case 3
						RuntimeError("NICE")
					Case 4
						RuntimeError("welcome to NIL")	
				End Select 
			EndIf
			
			End
		End If
	EndIf
	
	If KillTimer>95 Then
		If PlayState=GAME_SERVER Then
			Local FloorZ#,FloorY#,StartX#,EndX#
			PlayerFloor=max(PlayerFloor/2,1)
			FloorY#=-(PlayerFloor-1)*2-1.0
			If Floor(PlayerFloor/2.0)=Ceil(PlayerFloor/2.0) Then ;parillinen
				FloorZ#=-6.54
				StartX# = 7.2 
				EndX# = 0.8
			Else ;pariton
				FloorZ#=-0.31
				StartX# = 0.8
				EndX# = 7.2
			EndIf
			
			PositionEntity collider,Rnd(StartX,EndX),FloorY,FloorZ,True
			ResetEntity collider
			If LinePick(EntityX(collider,True),EntityY(collider,True),EntityZ(collider,True),0.0,-40.0,0.0,0.0)<>0 Then
				PositionEntity collider,EntityX(collider,True),PickedY()+0.35,EntityZ(collider,True),True
				ResetEntity collider
				DebugLog "PICKED!"
			EndIf
			
			UpdateWorld
			KillTimer = 0
		EndIf
	EndIf
End Function 

Function GetINIString$(file$, section$, parameter$)
	Local TemporaryString$ = ""
	Local f = ReadFile(file)
	
	While Not Eof(f)
		If Lower(ReadLine(f)) = "[" + Lower(section) + "]" Then
			Repeat
				TemporaryString = ReadLine(f)
			 	If Lower(Trim(Left(TemporaryString, max(Instr(TemporaryString, "=") - 1, 0)))) = Lower(parameter) Then
					CloseFile f
					Return Trim( Right(TemporaryString,Len(TemporaryString)-Instr(TemporaryString,"=")) )
				EndIf
			Until Left(TemporaryString, 1) = "[" Or Eof(f)
			CloseFile f
			Return ""
		EndIf
	Wend
	
	CloseFile f
End Function

Function GetINIInt$(file$, section$, parameter$)
	Local Stri$= Trim(GetINIString(file$, section$, parameter$))
	If Lower(Stri) = "true" Then
		Return 1
	ElseIf Lower(Stri) = "false"
		Return 0
	Else
		Return Int(Stri)
	EndIf
End Function

Function GetINIFloat$(file$, section$, parameter$)
	Return Float(GetINIString(file$, section$, parameter$))
End Function

Function INI_FileToString$(INI_sFilename$)
	
	Local INI_sString$ = ""
	Local INI_lFileHandle%= ReadFile(INI_sFilename)
	If INI_lFileHandle <> 0 Then
		While Not(Eof(INI_lFileHandle))
			INI_sString = INI_sString + ReadLine$(INI_lFileHandle) + Chr$(0)
		Wend
		CloseFile INI_lFileHandle
	End If
	Return INI_sString
	
End Function

Function INI_CreateSection$(INI_lFileHandle%, INI_sNewSection$)
	
	If FilePos(INI_lFileHandle) <> 0 Then WriteLine INI_lFileHandle, "" ; Blank Line between sections
	WriteLine INI_lFileHandle, INI_sNewSection
	Return INI_sNewSection
	
End Function

Function INI_CreateKey%(INI_lFileHandle%, INI_sKey$, INI_sValue$)
	
	WriteLine INI_lFileHandle, INI_sKey + " = " + INI_sValue
	Return True
	
End Function

Function PutINIValue%(file$, INI_sSection$, INI_sKey$, INI_sValue$)
	
	; Returns: True (Success) Or False (Failed)
	
	INI_sSection = "[" + Trim$(INI_sSection) + "]"
	Local INI_sUpperSection$ = Upper$(INI_sSection)
	INI_sKey = Trim$(INI_sKey)
	INI_sValue = Trim$(INI_sValue)
	Local INI_sFilename$ = file$
	
	; Retrieve the INI Data (If it exists)
	
	Local INI_sContents$ = INI_FileToString(INI_sFilename)
	
		; (Re)Create the INI file updating/adding the SECTION, KEY And VALUE
	
	Local INI_bWrittenKey% = False
	Local INI_bSectionFound% = False
	Local INI_sCurrentSection$ = ""
	
	Local INI_lFileHandle% = WriteFile(INI_sFilename)
	If INI_lFileHandle = 0 Then Return False ; Create file failed!
	
	Local INI_lOldPos% = 1
	Local INI_lPos% = Instr(INI_sContents, Chr$(0))
	
	While (INI_lPos <> 0)
		
		Local INI_sTemp$ = Mid$(INI_sContents, INI_lOldPos, (INI_lPos - INI_lOldPos))
		
		If (INI_sTemp <> "") Then
			
			If Left$(INI_sTemp, 1) = "[" And Right$(INI_sTemp, 1) = "]" Then
				
					; Process SECTION
				
				If (INI_sCurrentSection = INI_sUpperSection) And (INI_bWrittenKey = False) Then
					INI_bWrittenKey = INI_CreateKey(INI_lFileHandle, INI_sKey, INI_sValue)
				End If
				INI_sCurrentSection = Upper$(INI_CreateSection(INI_lFileHandle, INI_sTemp))
				If (INI_sCurrentSection = INI_sUpperSection) Then INI_bSectionFound = True
				
			Else
				DebugLog INI_sTemp
				If Left(INI_sTemp, 1) = ":" Then
					WriteLine INI_lFileHandle, INI_sTemp
				Else
						; KEY=VALUE				
					Local lEqualsPos% = Instr(INI_sTemp, "=")
					If (lEqualsPos <> 0) Then
						If (INI_sCurrentSection = INI_sUpperSection) And (Upper$(Trim$(Left$(INI_sTemp, (lEqualsPos - 1)))) = Upper$(INI_sKey)) Then
							If (INI_sValue <> "") Then INI_CreateKey INI_lFileHandle, INI_sKey, INI_sValue
							INI_bWrittenKey = True
						Else
							WriteLine INI_lFileHandle, INI_sTemp
						End If
					End If
				EndIf
				
			End If
			
		End If
		
			; Move through the INI file...
		
		INI_lOldPos = INI_lPos + 1
		INI_lPos% = Instr(INI_sContents, Chr$(0), INI_lOldPos)
		
	Wend
	
		; KEY wasn;t found in the INI file - Append a New SECTION If required And create our KEY=VALUE Line
	
	If (INI_bWrittenKey = False) Then
		If (INI_bSectionFound = False) Then INI_CreateSection INI_lFileHandle, INI_sSection
		INI_CreateKey INI_lFileHandle, INI_sKey, INI_sValue
	End If
	
	CloseFile INI_lFileHandle
	
	Return True ; Success
	
End Function


Function WordWrap%(A$,X,Y,W,H,Leading=0, center = 0)
	;Display A$ starting at X,Y - no wider than W and no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	;To Do (if needed): force break if single word is too big to fit on line (currently function will hang if this happens)
	LinesShown=0
	StrHeight=StringHeight(A$)+Leading
	FittedText$ = ""
	While Len(A)>0
		space=Instr(A$," ")
		If space=0 Then space=Len(A$)
		temp$=Left$(A$,space)
		trimmed$=Trim$(temp);we might ignore a final space
		extra=0;we haven't ignored it yet
		;ignore final space if doing so would make a word fit at end of line:
		If (StringWidth (b$+temp$)>W) And (StringWidth (b$+trimmed$)<=W) Then temp=trimmed:extra=1
		If StringWidth (b$+temp$)>W Then;too big, so print what will fit
			If center Then Text X + (W Shr 1),LinesShown * StrHeight + Y,b$,True Else Text X,LinesShown * StrHeight + Y,b$
			FittedText = FittedText + b
			LinesShown=LinesShown+1
			b$=""
		Else;append it to B$ (which will eventually be printed) and remove it from A$
			b$=b$+temp$
			A$=Right$(A$,Len(A$)-(Len(temp$)+extra))
		EndIf
		If ((LinesShown+1)*StrHeight)>H Then Exit;the next line would be too tall, so leave
	Wend
	If (b$<>"")And((LinesShown+1)<=H) Then 
		If center Then Text X + (W Shr 1),LinesShown * StrHeight + Y,b$,True Else Text X,LinesShown * StrHeight + Y,b$
		;Text X,LinesShown*StrHeight+Y,b$;print any remaining text if it'll fit vertically
		FittedText = FittedText + b
	EndIf
	Return Len(FittedText)
End Function

;misc functions
Function Button%(x,y,width,height,txt$, disabled%=False)
	Local Pushed = False
	
	Local ClrR = ColorRed()
	Local ClrG = ColorGreen()
	Local ClrB = ColorBlue()
	
	;Color ClrR, ClrG, ClrB
	If Not disabled Then 
		If MouseX() > x And MouseX() < x+width Then
			If MouseY() > y And MouseY() < y+height Then
				If MouseDown(1) Then
					Pushed = True
					Color ClrR*0.6, ClrG*0.6, ClrB*0.6
				Else
					Color min(ClrR*1.4,255),min(ClrG*1.4,255),min(ClrB*1.4,255)
				EndIf
			EndIf
		EndIf
	EndIf
	
	If Pushed Then 
		Rect x,y,width,height
		Color 133,130,125
		Rect x+1,y+1,width-1,height-1,False	
		Color 10,10,10
		Rect x,y,width,height,False
		Color 250,250,250
		Line x,y+height-1,x+width-1,y+height-1
		Line x+width-1,y,x+width-1,y+height-1
	Else
		Rect x,y,width,height
		Color 133,130,125
		Rect x,y,width-1,height-1,False	
		Color 250,250,250
		Rect x,y,width,height,False
		Color 10,10,10
		Line x,y+height-1,x+width-1,y+height-1
		Line x+width-1,y,x+width-1,y+height-1		
	EndIf
	
	Color 255,255,255
	If disabled Then Color 70,70,70
	Text x+width/2, y+height/2-1, txt, True, True
	
	Color 0,0,0
	
	If Pushed And MouseHit1 Then Return True;PlaySound ButtonSFX : Return True
End Function

Function TextBox(x,y,width,height,Txt$)
	Color 255,255,255
	Rect x,y,width,height
	
	Color 128,128,128
	Rect x,y,width,height,False
	Color 64,64,64
	Rect x+1,y+1,width-2,height-2,False	
	Color 255,255,255
	Line x+width-1,y,x+width-1, y+height-1
	Line x, y+height-1, x+width-1, y+height-1	
	Color 212, 208, 199
	Line x+width-2,y+1,x+width-2, y+height-2
	Line x+1, y+height-2, x+width-2, y+height-2
	
	Color 0,0,0
	Text x+width/2, y+height/2, Txt, True, True
End Function

Function MouseOn%(x%, y%, width%, height%)
	If MouseX() > x And MouseX() < x + width Then
		If MouseY() > y And MouseY() < y + height Then
			Return True
		End If
	End If
	Return False
End Function

Function rInput$(aString$)
	Local value% = GetKey()
	Local length% = Len(aString$)
	
	If value = 8 Then
		value = 0
		If length > 0 Then aString$ = Left(aString, length - 1)
	EndIf
	
	If value = 13 Or value = 0 Then
		Return aString$
	ElseIf value > 0 And value < 7 Or value > 26 And value < 32 Or value = 9
		Return aString$
	Else
		aString$ = aString$ + Chr(value)
		Return aString$
	End If
End Function

Function Tick(x,y,selected%)
	;TextBox(x,y,13,13,"")
	Rect x,y,13,13,False
	
	If selected Then
		Color 255,255,255
		Rect x+2,y+2,9,9,True;DrawImage TickIMG, x, y
	EndIf
	
	If MouseX() > x And MouseX() < x+13 Then
		If MouseY() > y And MouseY() < y+13 Then
			If MouseHit1 Then PlaySound ButtonSFX : Return (Not selected)
		EndIf
	EndIf	
	
	Return selected
	
End Function

Function JoinServer%(Addr$="")
	Server% = CreateUDPStream() ;create a UDP stream with any free port
	
	Local waitForCo% = MilliSecs()+10000
	
	TempStr$ = ""
	
	If (Not Server) Then
		PlayState = GAME_SOLO
	Else
		WriteLine Server,"joined"
		WriteLine Server,PlayerName
		;WriteLine Server,"AMYSUi1.jpg"
		CountHostIPs(Addr) ;get host ips from server
		SendUDPMsg(Server,HostIP(1),8730)
		Stream = RecvUDPMsg(Server)
		Local prevSendTime% = MilliSecs()
		While Not Stream
			SetBuffer BackBuffer()
			Stream = RecvUDPMsg(Server)
			Cls
			Color 255,255,255
			Text 5,5,"Waiting... ("+Str(Int((MilliSecs()-(waitForCo-10000))/1000))+" second(s) have passed)"
			Flip
			Delay 10
			If MilliSecs()>prevSendTime+1000 Then
				WriteLine Server,"joined"
				WriteLine Server,PlayerName
				;WriteLine Server,"AMYSUi1.jpg"
				SendUDPMsg(Server,HostIP(1),8730)
				prevSendTime% = MilliSecs()
			EndIf
		Wend
		PlayState=GAME_CLIENT ;let the game know you're a client
		Players(0)=New Player
		Players(0)\IP = UDPMsgIP(Server)
		Players(0)\Port = UDPMsgPort(Server) ;save the server's information
		Players(0)\LastMsgTime = MilliSecs()
		TempStr = ReadLine(Server)
		If TempStr = "KICK" Then
			PlayState = GAME_SOLO
			CloseUDPStream(Server)
		Else
			MapString = TempStr
			ID = ReadByte(Server)
			;RuntimeError Str(ID)
			For gl.GLIMPSES = Each GLIMPSES
				FreeEntity gl\obj
				Delete gl
			Next
			For en.ENEMIES = Each ENEMIES
				FreeEntity en\obj
				FreeEntity en\collider
				Delete en
			Next
			CurrEnemy = Null
			ClearWorld True,False,False
			LoadMap()
			CreateGlimpses()
			collider = CreatePivot() ;reset erased objects
			PlayerMesh = LoadAnimMesh("GFX\player.b3d")
			For j=0 To 31
				If Players(j)=Null Then Players(j)=New Player
				Players(j)\Pivot = CreatePivot()
				PositionEntity Players(j)\Pivot,-200.5,-1.3,-0.5
				EntityType Players(j)\Pivot, hit_friendly
				EntityRadius Players(j)\Pivot, 0.3
				tmps%=CopyEntity(PlayerMesh,Players(j)\Pivot)
				ScaleEntity tmps, 0.15,0.5,0.15
				PositionEntity tmps,-200.5,-1.6,-0.5,True
				EntityTexture tmps,PlayerTex
			Next
			
			PositionEntity collider,-2.5,-1.3,-0.5
			EntityType collider, hit_friendly
			EntityRadius collider, 0.3
			SoundEmitter = CreatePivot()
			camera = CreateCamera()
			CameraRange camera, 0.001, 3.0
			CameraFogMode camera, 1
			CameraFogRange camera, 1, 2.5
			CameraFogColor camera,0,0,0
			microphone=CreateListener(camera) ; Create listener, make it child of camera
			ScaleEntity SoundEmitter, 0.1,0.1,0.1
			CreateBlurImage()
		EndIf
		;[Block]
					;WriteLine(Server,"server_client") ;give the server your name
					;While (Not ReadAvail(Server))
					;	If Eof(Server) Then TempStr = "KICK" : Exit
					;	SetBuffer BackBuffer()
					;wait for the server's message to reach the player
					;	Cls
					;	Color 255,255,255
					;	Text 5,5,"Waiting... ("+Str(Int((MilliSecs()-(waitForCo-10000))/1000))+" seconds have passed)"
					;	Flip
					;	If (MilliSecs()>=waitForCo) Then TempStr = "KICK" : Exit
					;	Delay 10
					;Wend
					;TempStr = ReadLine(Server) ;get the server's map arrangement
					;If (Not Eof(Server)) And (TempStr<>"KICK") Then
					;	If (TempStr="KICK") Then
					;		PlayState=GAME_SOLO
					;		CloseTCPStream(Server)
					;		Server = 0
					;	Else
					;		ID = ReadByte(Server) ;get the ID the server has given you
					;		For gl.GLIMPSES = Each GLIMPSES ;remove glimpses
					;			FreeEntity gl\obj
					;			Delete gl
					;		Next
					;		For en.ENEMIES = Each ENEMIES ;remove enemies
					;			FreeEntity en\obj
					;			FreeEntity en\collider
					;			Delete en
					;		Next
					;		CurrEnemy = Null
					;		ClearWorld(True,False,False) ;remove rooms
					;		LoadMap() ;recreate the server's map on the client
					;		CreateGlimpses()
					;		collider = CreatePivot() ;reset erased objects
					;		PlayerMesh = LoadAnimMesh("GFX\player.b3d")
					;		
					;		For j=0 To 3
					;			Players(j,1) = CreatePivot();CreateSphere(8,Players(j,1))
					;			PositionEntity Players(j,1),-200.5,-1.3,-0.5
					;			EntityType Players(j,1), hit_monster
					;			EntityRadius Players(j,1), 0.3
					;			tmps%=CopyEntity(PlayerMesh,Players(j,1));CreateSphere(8,Players(j,1))
					;			ScaleEntity tmps, 0.15,0.15,0.15
					;			PositionEntity tmps,-200.5,-1.6,-0.5,True
					;			EntityTexture tmps,PlayerTex
					;		Next
					;		
					;		PositionEntity collider,-2.5,-1.3,-0.5
					;		EntityType collider, hit_monster
					;		EntityRadius collider, 0.3
					;		SoundEmitter = CreatePivot()
					;		camera = CreateCamera()
					;		CameraRange camera, 0.001, 100
					;		CameraFogMode camera, 1
					;		CameraFogRange camera, 1, 2.5
					;		CameraFogColor camera,0,0,0
					;		microphone=CreateListener(camera) ; Create listener, make it child of camera
					;		ScaleEntity SoundEmitter, 0.1,0.1,0.1
					;		CreateBlurImage()
					;	EndIf
					;Else
					;	PlayState = GAME_SOLO
					;	If (Server) Then
					;		CloseTCPStream(Server)
					;		Server=0
					;	EndIf
					;EndIf
		;[End block]
	EndIf
End Function

Function Download(link$, savepath$ = "", savefile$ = "")
	;Strip protocol and return false if not "http"
	inst = Instr(link$, "://")
	If inst Then
		If Lower(Trim(Left(link$, inst - 1))) <> "http" Then Return False
		link$ = Right(link$, Len(link$) - inst - 2)
	EndIf
	
	;Seperate host from link
	inst = Instr(link$, "/")
	If inst = 0 Then Return False
	host$ = Trim(Left(link$, inst - 1))
	link$ = Trim(Right(link$, Len(link$) - inst + 1))
	
	;Seperate path and file from the link
	For i = Len(link$) To 1 Step -1
		If Mid(link$, i, 1) = "/" Then
			link_path$ = Trim(Left(link$, i))
			link_file$ = Right(link$, Len(link$) - i)
			Exit
		EndIf
	Next
	If link_file$ = "" Then Return False
	If savefile$ = "" Then savefile$ = link_file$
	
	;Open TCP stream
	TCPTimeouts 2000,0
	tcp = OpenTCPStream(host$, 80)
	If tcp = 0 Then Return False
	WriteLine tcp, "GET " + link_path$ + link_file$ + " HTTP/1.1" + Chr(13) + Chr(10) + "Host: " + host$ + Chr(13) + Chr(10) + "User-Agent: Download_Function_By_bytecode77" + Chr(13) + Chr(10)
	
	If Right(savepath,1)<>"/" And Right(savepath,1)<>"\" Then
		savepath=savepath+"/"
	EndIf
	If FileType(Left(savepath,Len(savepath)-1))=0 Then
		CreateDir(savepath)
	EndIf
	
	;Download file
	l$ = ReadLine(tcp)
	inst1 = Instr(l$, " ")
	inst2 = Instr(l$, " ", inst1 + 1)
	num = Mid(l$, inst1, inst2 - inst1)
	Select num
		Case 200
			conlen = -1
			chunk = False
			
			Repeat
				l$ = Trim(ReadLine(tcp))
				If l$ = "" Then Exit
				
				inst = Instr(l$, ":")
				l1$ = Trim(Left(l$, inst - 1))
				l2$ = Trim(Right(l$, Len(l$) - inst))
				Select Lower(l1$)
					Case "content-length"
						conlen = l2$
					Case "transfer-encoding"
						If Lower(l2$) = "chunked" Then chunk = True
				End Select
			Forever
			
			If conlen = 0 Then
				file = WriteFile(savepath$ + savefile$)
				CloseFile file
				CloseTCPStream tcp
				Return True
			ElseIf conlen > 0 Then
				file = WriteFile(savepath$ + savefile$)
				bnk = CreateBank(4096)
				pos = 0
				Repeat
					avail = conlen - pos
					If avail > 4096 Then
						ReadBytes bnk, tcp, 0, 4096
						WriteBytes bnk, file, 0, 4096
						pos = pos + 4096
					Else
						ReadBytes bnk, tcp, 0, avail
						WriteBytes bnk, file, 0, avail
						Exit
					EndIf
				Forever
				FreeBank bnk
				CloseFile file
				CloseTCPStream tcp
				Return True
			ElseIf chunk Then
				file = WriteFile(savepath$ + savefile$)
				bnk = CreateBank(4096)
				
				Repeat
					l$ = Trim(Upper(ReadLine(tcp)))
					ln = 0
					For i = 1 To Len(l$)
						ln = 16 * ln + Instr("123456789ABCDEF", Mid$(l$, i, 1))
					Next
					If ln = 0 Then Exit
					
					If BankSize(bnk) < ln Then ResizeBank bnk, ln
					ReadBytes bnk, tcp, 0, ln
					WriteBytes bnk, file, 0, ln
					ReadShort(tcp)
				Forever
				
				FreeBank bnk
				CloseFile file
				CloseTCPStream tcp
				Return True
			Else
				CloseTCPStream tcp
				Return False
			EndIf
		Case 301, 302
			Repeat
				l$ = Trim(ReadLine(tcp))
				If l$ = "" Then Exit
				
				inst = Instr(l$, ":")
				l1$ = Trim(Left(l$, inst - 1))
				l2$ = Trim(Right(l$, Len(l$) - inst))
				Select Lower(l1$)
					Case "location"
						CloseTCPStream tcp
						Return Download(l2$, savepath$, savefile$)
				End Select
			Forever
		Default
			CloseTCPStream tcp
			Return False
	End Select
End Function

Function InputBox$(x%, y%, width%, height%, Txt$, ID% = 1, canclickoff%=True)
	;TextBox(x,y,width,height,Txt$)
	Color (255, 255, 255)
	Rect(x,y,width,height,True)
	Color (0, 0, 0)
	
	Local MouseOnBox% = False
	If MouseOn(x, y, width, height) Then
		Color(50, 50, 50)
		MouseOnBox = True
		If MouseHit1 Then SelectedInputBox = ID : FlushKeys
	EndIf
	
	DebugLog SelectedInputBox+"   "+ID
	
	Rect(x + 2, y + 2, width - 4, height - 4)
	Color (255, 255, 255)
	
	If (Not MouseOnBox) And MouseHit1 And canclickoff And SelectedInputBox = ID Then SelectedInputBox = 0
	
	If SelectedInputBox = ID Then
		DebugLog "OOOOOOOOOOOOO"
		Txt = rInput(Txt)
		If (MilliSecs() Mod 800) < 400 Then Rect (x + StringWidth(Txt) + 2, y + height / 2 - 5, 2, 12)
	EndIf	
	
	Color 255,255,255
	Text(x + 2, y + height / 2, Txt, False, True)
	
	Return Txt
End Function

Global MsgCount%

Type ChatMessage
	Field Txt$
	Field R%,G%,B%
	Field ID%
	Field Timer%
	Field SendTo%[32]
	Field Sender%
End Type

Function AddChatMsg(txt$,r%,g%,b%,player%=-1,glbal%=False)
	MsgCount=MsgCount+1
	If Len(txt)>200 Then
		txt = Left(txt,188)+"- [REDACTED]"
	EndIf
	Local cm.ChatMessage
	Local cmCount% = 0
	For cm = Each ChatMessage
		If PlayState=GAME_SERVER Then
			If cm\SendTo[0] Then cmCount=cmCount-1
		EndIf
		cmCount=cmCount+1
	Next
	While cmCount>=20
		Delete (First ChatMessage)
		cmCount=cmCount-1
	Wend
	cm = New ChatMessage
	cm\ID = MsgCount
	cm\Txt = txt
	cm\R = r
	cm\G = g
	cm\B = b
	cm\Timer = MilliSecs()+15000
	cm\Sender = player
	
	For i%=0 To 31
		cm\SendTo[i]=False
	Next
	If player<0 Or glbal Then
		For i%=0 To 31
			cm\SendTo[i]=True
		Next
	Else
		If player>0 Then
			If Players(player)<>Null Then
				If Players(player)\PlayingAs = PLAYER_CLASSD Then
					cm\SendTo[player] = True
					For i%=1 To 31
						If Players(i)<>Null And i<>player Then
							If EntityDistance(Players(i)\Pivot,Players(player)\Pivot)<4.0 Or Players(i)\PlayingAs<>PLAYER_CLASSD Then
								cm\SendTo[i]=True
							EndIf
						EndIf
					Next
					If EntityDistance(collider,Players(player)\Pivot)<4.0 Or PlayingAs<>PLAYER_CLASSD Then
						cm\SendTo[0]=True
					EndIf
				Else
					For i%=0 To 31
						cm\SendTo[i]=True
					Next
				EndIf
			EndIf
		Else
			If PlayingAs = PLAYER_CLASSD Then
				cm\SendTo[0] = True
				For i%=1 To 31
					If Players(i)<>Null Then
						If EntityDistance(Players(i)\Pivot,collider)<4.0 Or Players(i)\PlayingAs<>PLAYER_CLASSD Then
							cm\SendTo[i]=True
						EndIf
					EndIf
				Next
			Else
				For i%=0 To 31
					cm\SendTo[i]=True
				Next
			EndIf
		EndIf
	EndIf
	
	If ChatLogFile=0 Then
		Local clogID%=1
		While FileType("chatlogs\chatlog"+clogID+".txt")<>0
			clogID=clogID+1
		Wend
		ChatLogFile = WriteFile("chatlogs\chatlog"+clogID+".txt")
	EndIf
	
	WriteLine(ChatLogFile,cm\Txt)
End Function

Function UpdateChatMsgs()
	Local cm.ChatMessage
	Local msgAmount% = 1
	Local y% = 0
	For cm = Each ChatMessage
		If cm\Timer<MilliSecs() Then
			Delete cm
		Else
			msgAmount=msgAmount+1
		EndIf
	Next
	For cm = Each ChatMessage
		If PlayState=GAME_SERVER Then
			Local txt$ = cm\Txt
			If AdminSpyChat Then
				txt="("+cm\Sender+") "+txt
			EndIf
			Color 0,0,0
			Text 6,GraphicsHeight()-(20*msgAmount)+y+1,txt
			If cm\SendTo[0] Or AdminSpyChat Then
				If cm\SendTo[0] Then
					Color cm\R,cm\G,cm\B
				Else
					Color 150,150,150
				EndIf
				Text 5,GraphicsHeight()-(20*msgAmount)+y,txt
				Color 255,255,255
				y=y+20
			EndIf
		Else
			Color 0,0,0
			Text 6,GraphicsHeight()-(20*msgAmount)+y+1,cm\Txt
			Color cm\R,cm\G,cm\B
			Text 5,GraphicsHeight()-(20*msgAmount)+y,cm\Txt
			Color 255,255,255
			y=y+20
		EndIf
	Next
End Function
;~IDEal Editor Parameters:
;~F#4E3
;~C#Blitz3D