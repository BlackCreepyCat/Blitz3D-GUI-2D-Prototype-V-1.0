; ----------------
; Master GUI Class
; ----------------
Type GuiObject
	
	Field Family
	Field Parent.GuiObject
	
	Field Id
	
	Field R%
	Field G%
	Field B%
	
	Field Px%
	Field Py%
	
	Field OldPx%
	Field OldPy%
	
	Field Sx%
	Field Sy%

	Field MinSx%
	Field MinSy%
	
	Field OldSx%
	Field OldSy%
	
	Field State%
	Field Style%
	
	Field TitleHeight%
	
	Field Draggable%
	Field DragMode%
	Field DragPx%
	Field DragPy%
	
	Field Movable%
	Field Sizable%
	Field ResizeMode%

	Field MinimizeGadget.GuiObject	
	Field MaximizeGadget.GuiObject	
	Field CloseGadget.GuiObject

	Field Caption$
	
End Type

; -----------------
; Internal Gui vars
; -----------------
Global Gui_ObjectTypeWindow% = 1001
Global Gui_ObjectTypeButton% = 1002
Global Gui_ObjectTypeFrame% = 1003
Global Gui_ObjectTypeGrid% = 1004

Global Gui_SystemFont% 

Global Gui_CurrentWindow.GuiObject
Global Gui_PreviousWidget.GuiObject

; ----------------
;  Init Gui System
; ----------------
Function Gui_Init()
	Gui_SystemFont% = LoadFont("Arial",15,True)
End Function

; -------------------
;  Refresh Gui System
; -------------------
Function Gui_Refresh()

	Gui_FlushEvent()
	Gui_RefreshMouse()

	; -------------------------------------------
	; If the user click/press or release a button
	; -------------------------------------------
	If GUI_MouseClickLeft Then
		Gui_SwapWindow()
	EndIf

	If GUI_MousePressLeft  Then
		Gui_MoveWindow()
		Gui_ResizeWindow()
	EndIf	
		
	If GUI_MouseReleaseLeft Then
		; ---------------------------------------
		; Event creation for window move / resize
		; ---------------------------------------
		If Gui_CurrentWindow\DragMode=True Then Gui_CreateEvent(Gui_CurrentWindow , Event_MoveWindow%)
		If Gui_CurrentWindow\ResizeMode=True Then Gui_CreateEvent(Gui_CurrentWindow , Event_SizeWindow%)
		
		; -------------------
		; Reset window status
		; -------------------
		Gui_CurrentWindow\DragMode=False
		Gui_CurrentWindow\ResizeMode=False
	EndIf	
	
	; ------------------
	; Redraw the windows 
	; ------------------
	Gui_RedrawWindow()
	
End Function

; -------------------------------
; Gfx Functions to make interface
; -------------------------------
Function Gui_DrawRect(Px,Py,Tx,Ty,Fill,Red,Green,Blue)
	
	Color Red,Green,Blue
	Rect(Px,Py,Tx,Ty,Fill)
	
End Function

Function Gui_DrawLine(Px,Py,Tx,Ty,Red,Green,Blue)
	
	Color Red,Green,Blue
	Line(Px,Py,Tx,Ty)
	
End Function

Function Gui_DrawText(Px,Py,Caption$,Red,Green,Blue)
	
	SetFont Gui_SystemFont%
	Color Red,Green,Blue
	Text(Px,Py,Caption$)
	
End Function