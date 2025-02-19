; ------------------
;  Create the frame
; ------------------
Function Gui_CreateFrame.GuiObject(Parent.GuiObject,Px, Py, Sx, Sy, Caption$ , Style% = 1)

	G.GuiObject = New GuiObject
	
	G\Family = Gui_ObjectTypeFrame%
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
	G\Caption$ = Caption$
	
	Return G
	
End Function

; -----------------
;  Redraw Gui Frame
; -----------------
Function Gui_RefreshFrame(G.GuiObject)

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
Function Gui_RedrawFrame(G.GuiObject)

	Local NewPx = G\Parent\Px + G\Px
	Local NewPy = G\Parent\Py + G\Py
	
	Local StringSize%
	Local StringOffset% = StringHeight ("A") / 2
	
	; ----------------------------------
	; Draw the background and the border
	; ----------------------------------
	Select G\Style
		
		Case 1
		
			StringSize = StringWidth (G\Caption$ )
		
			Gui_DrawRect(NewPx , NewPy , G\Sx ,  G\Sy, True, 20 , 20 , 20)
			Gui_DrawRect(NewPx + 1 , NewPy + 1 ,  G\Sx -2 ,  G\Sy - 2, False ,150,150,150)	
			
			Gui_DrawText(NewPx + (G\Sx / 2) -  (StringSize / 2), NewPy + (G\Sy / 2) - StringOffset%, G\Caption$,230,230,230)
				
		Case 2
			
			StringSize = StringWidth (G\Caption$ )
			
			Gui_DrawRect(NewPx , NewPy , G\Sx ,  G\Sy, False, 150 , 150 , 150)
			Gui_DrawRect(NewPx + 1 , NewPy + 1 ,  G\Sx -2 ,  G\Sy - 2, False ,150,150,150)
			
			Gui_DrawText(NewPx + (G\Sx / 2) -  (StringSize / 2), NewPy + (G\Sy / 2) - StringOffset%, G\Caption$,230,230,230)
			
		Case 3
		
			StringSize = StringWidth (G\Caption$ + "***")
			
			Gui_DrawRect(NewPx , NewPy ,2, G\Sy , 1  ,150 , 150 , 150)
			Gui_DrawRect(NewPx + G\Sx - 2, NewPy , 2 , G\Sy , 1 , 150 , 150 , 150)
			
			Gui_DrawRect(NewPx , NewPy + G\Sy - 2 , G\Sx , 2 , 1 , 150 , 150 , 150)

			Gui_DrawRect(NewPx , NewPy , (G\Sx / 2) - (StringSize / 2) , 2 , 1  ,150 , 150 , 150)
			Gui_DrawRect(NewPx + (G\Sx / 2) + (StringSize / 2 ) , NewPy , (G\Sx / 2) - (StringSize / 2) , 2 , 1  ,150 , 150 , 150)

			Gui_DrawText(NewPx + (G\Sx / 2) -  (StringWidth (G\Caption$) / 2) , NewPy - StringOffset%, G\Caption$,230,230,230)
	
	End Select

End Function