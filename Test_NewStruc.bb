Include "Timer.bb"
Include "Mouse.bb"
Include "Kernel.bb"
Include "Event.bb"
Include "Window.bb"
Include "Frame.bb"
Include "Grid.bb"
Include "Button.bb"

;Graphics3D 1280,720,32,0
Graphics3D 1920,1080,32,2 

Gui_Init()


T.GuiObject = Gui_CreateWindow(50, 50, 550, 450, "Window 01",True,True)
T\Sizable = True

FrmA.GuiObject = Gui_CreateFrame(T,10, 55, T\Sx - 20, T\Sy - 100, "This is a frame, clickable",3)

GridA.GuiObject = Gui_CreateGrid(T,20, 75, T\Sx - 40, T\Sy - 140)


U.GuiObject = Gui_CreateWindow(250,150, 450, 250, "Window 02", True, True)
;U\Sizable = True
;U\Movable = False

ButA.GuiObject = Gui_CreateButton(U,10, 30, 120, 35, "Button A")
ButB.GuiObject = Gui_CreateButton(U,10, 70, 120, 35, "Button B",2)
ButC.GuiObject = Gui_CreateButton(U,10, 110, 120, 35, "Button C",3)

While Not KeyHit(1)
	
	Cls

	If Gui_GetEvent(T)=Event_MinimizeWindow% Then
		DebugLog "Window Minimized " + T\Px + " / " + T\Py + " / " + T\Sx +  " / " + T\Sy
	EndIf
	
	If Gui_GetEvent(T)=Event_MaximizeWindow% Then
		DebugLog "Window Maximized " + T\Px + " / " + T\Py + " / " + T\Sx +  " / " + T\Sy
	EndIf
		
	If Gui_GetEvent(ButA)=Event_GadgetClick Then
		DebugLog "Button A clicked " + Rand(2000,4000)
	;Exit
	EndIf
	
	If Gui_GetEvent(FrmA)=Event_GadgetClick Then
		DebugLog "Frame clicked " + Rand(2000,4000)
	EndIf	


	If Gui_GetEvent(U)=Event_SizeWindow Then
		DebugLog "Window Sized: " + U\Px + " / " + U\Py + " / " + U\Sx +  " / " + U\Sy
	EndIf

	If Gui_GetEvent(U)=Event_MoveWindow Then
		DebugLog "Window moved: " + U\Px + " / " + U\Py + " / " + U\Sx +  " / " + U\Sy
	EndIf
		
	
	
	If t<>Null Then	
		FrmA\Sx = T\Sx - 20
		FrmA\Sy =  T\Sy - 100
		
	;	GridA\Sx =  T\Sx - 40
	;	GridA\Sy =  T\Sy - 140
	EndIf
	
	Gui_Refresh()
		
	; -----------
	; Debug Lines
	; -----------
	Color(255,200,100)
	Line (Gui_MouseX , 0 , Gui_MouseX , GraphicsHeight() )
	Line (0 , Gui_MouseY , GraphicsWidth() , Gui_MouseY)

	RenderWorld	

	Flip
Wend


Function check_test$(obj)
	t.GuiObject=Object.GuiObject(obj)

	Return T\Caption$
End Function 
;~IDEal Editor Parameters:
;~C#Blitz3D