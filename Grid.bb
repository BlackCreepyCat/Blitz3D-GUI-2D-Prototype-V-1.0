; ------------------
;  Create the frame
; ------------------
Function Gui_CreateGrid.GuiObject(Parent.GuiObject,Px, Py, Sx, Sy, Style% = 1)

	G.GuiObject = New GuiObject
	
	G\Family = Gui_ObjectTypeGrid%
	G\Parent = Parent
	G\Id = Handle(G)
	
	G\Px = Px
	G\Py = Py
	G\Sx = Sx
	G\Sy = Sy
	
	G\R% = 55
	G\G% = 55
	G\B% = 55
	
	G\Style% = Style%
	
	Return G
	
End Function

; -----------------
;  Redraw Gui Frame
; -----------------
Function Gui_RefreshGrid(G.GuiObject)

	Local NewPx = G\Parent\Px + G\Px
	Local NewPy = G\Parent\Py + G\Py
		
	; ------------------------------------------------------
	; If there is a window to front, so i can click controls
	; ------------------------------------------------------
	If Gui_CurrentWindow = G\Parent Then

		; ----------------------
		; If gadgettype is frame
		; ----------------------
		If G\Family = Gui_ObjectTypeFrame% Then
			
			; ---------------------------		
			; If the mouse is on a widget
			; ---------------------------
			If Gui_TestZone(NewPx , NewPy ,  G\Sx , G\Sy  )   Then
				
				; Hightlight state
				G\State = 1
				
				; ------------------------------------
				; I save the current clicked gadget id
				; ------------------------------------
				If GUI_MouseClickLeft Then
					Gui_PreviousWidget = G
				EndIf
	
				; -----------------------------------------
				; If i release the mouse on the good gadget
				; -----------------------------------------
				If GUI_MousePressLeft And Gui_PreviousWidget = G Then
					G\State = 2	
				EndIf
				
				If GUI_MouseReleaseLeft And Gui_PreviousWidget = G Then
					Gui_CreateEvent(G , Event_GadgetClick%)
					G\State = 3
				EndIf
		
			Else
				
				G\State = 0
				
			EndIf
		
		EndIf

	EndIf
		
End Function

; -----------------
;  Redraw Gui Fram
; ------------------
Function Gui_RedrawGrid(G.GuiObject)

	Local NewPx = G\Parent\Px + G\Px
	Local NewPy = G\Parent\Py + G\Py
	

	
	; ----------------------------------
	; Draw the background and the border
	; ----------------------------------
	Select G\Style
		
		Case 1
		
			StringSize = StringWidth (G\Caption$ )
		
			Gui_DrawRect(NewPx , NewPy , G\Sx ,  G\Sy, True, 20 , 20 , 20)
			Gui_DrawRect(NewPx , NewPy ,  G\Sx ,  G\Sy , False , 255 , 150 , 0)
				
			Local StepingX# = G\Sx# / 20
			Local StepingY# = G\Sy# / 10
			
			Local NewX#
			Local NewY#

			While NewX#  <= G\Sx# - (StepingX# * 2)
				NewX# = NewX# + StepingX#
				Gui_DrawLine(NewPx + NewX , NewPy , NewPx + NewX , NewPy + G\Sy - 1 , 255 , 150 , 0)
				
			Wend

			While NewY#  <= G\Sy# - (StepingY# * 2)
			
				NewY# = NewY# + StepingY#
				Gui_DrawLine(NewPx , NewPy + NewY , NewPx + G\Sx - 1 , NewPy + NewY , 255 , 150 , 0)
				
				
			Wend
			
	End Select

End Function