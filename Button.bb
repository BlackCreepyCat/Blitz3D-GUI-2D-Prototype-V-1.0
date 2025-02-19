; ------------------
;  Create the button
; ------------------
Function Gui_CreateButton.GuiObject(Parent.GuiObject,Px, Py, Sx, Sy, Caption$, Style% = 1)

	G.GuiObject = New GuiObject
	
	G\Family = Gui_ObjectTypeButton%
	G\Parent = Parent
	G\Id = Handle(G)
	
	G\Px = Px
	G\Py = Py
	G\Sx = Sx
	G\Sy = Sy
	
	G\R% = 55
	G\G% = 55
	G\B% = 55
	
	G\MinSx = Sx
	G\MinSy = Sy
	
	G\OldSx = Sx
	G\OldSy = Sy
	
	G\Caption$ = Caption$
	G\Style% = Style%
	
	Return G
	
End Function

; ------------------
;  Redraw Gui button
; ------------------
Function Gui_RefreshButton(G.GuiObject)

	Local NewPx = G\Parent\Px + G\Px
	Local NewPy = G\Parent\Py + G\Py
		
	; ------------------------------------------------------
	; If there is a window to front, so i can click controls
	; ------------------------------------------------------
	If Gui_CurrentWindow = G\Parent Then
	
	
	
		; -----------------------
		; If gadgettype is button
		; -----------------------
		If G\Family = Gui_ObjectTypeButton% Then
			
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

; ------------------
;  Redraw Gui button
; ------------------
Function Gui_RedrawButton(G.GuiObject)

	Local NewPx = G\Parent\Px + G\Px
	Local NewPy = G\Parent\Py + G\Py
	
	Local StringSize% = StringWidth ( G\Caption$) / 2 
	Local StringOffset% = StringHeight ("A") / 2
	
	; ----------------------
	; Button style selection
	; ----------------------
	Select G\Style%
		
		Case 1
				
			; ----------------------------------
			; Draw the background and the border
			; ----------------------------------
			Gui_DrawRect(NewPx , NewPy , G\Sx ,  G\Sy, True, 20 , 20 , 20)
			Gui_DrawRect(NewPx + 1 , NewPy + 1 ,  G\Sx -2 ,  G\Sy - 2, False ,150,150,150)
			
			; -----------------------------
			; Select the background drawing
			; -----------------------------
			Select G\State
				
				Case 1
					Gui_DrawRect(NewPx + 2 , NewPy + 2 ,  G\Sx - 4 ,  G\Sy - 4, True ,80,80,80)
					
				Case 2
					Gui_DrawRect(NewPx + 2 , NewPy + 2 ,  G\Sx - 4 ,  G\Sy - 4, True ,200,00,0)
				
				Default
					Gui_DrawRect(NewPx + 2 , NewPy + 2 ,  G\Sx - 4 ,  G\Sy - 4, True , G\R , G\G , G\B)
				
			End Select
			
			; ----------------
			; Draw the caption
			; ----------------
			Gui_DrawText(NewPx + (G\Sx / 2) -  StringSize% , NewPy + (G\Sy / 2) - StringOffset% , G\Caption$,230,230,230)
			
		Case 2
		
			; ----------------------------------
			; Draw the background and the border
			; ----------------------------------
			Gui_DrawRect(NewPx , NewPy , G\Sx ,  G\Sy, True, 20 , 20 , 20)
			Gui_DrawRect(NewPx + 1 , NewPy + 1 ,  G\Sx -2 ,  G\Sy - 2, False ,150,150,150)
			
			; -----------------------------
			; Select the background drawing
			; -----------------------------
			Select G\State
				
				Case 1
					Gui_DrawRect(NewPx + 2 , NewPy + 2 ,  G\Sx - 4 ,  G\Sy - 4, True ,80,80,80)
					
				Case 2
					Gui_DrawRect(NewPx + 2 , NewPy + 2 ,  G\Sx - 4 ,  G\Sy - 4, True ,200,00,0)
				
				Default
					Gui_DrawRect(NewPx + 2 , NewPy + 2 ,  G\Sx - 4 ,  G\Sy - 4, True , G\R , G\G , G\B)
				
			End Select
			
			; ----------------
			; Draw the caption
			; ----------------
			Gui_DrawText(NewPx + 10 , NewPy + (G\Sy / 2) - StringOffset% , G\Caption$,230,230,230)			

		Case 3
		
			; -----------------------------
			; Select the background drawing
			; -----------------------------
			Select G\State
				
				Case 1
					Gui_DrawRect(NewPx  , NewPy  ,  G\Sx ,  G\Sy , True ,80,80,80)
					
				Case 2
					Gui_DrawRect(NewPx  , NewPy  ,  G\Sx  ,  G\Sy , True ,200,00,0)
				
				Default
					Gui_DrawRect(NewPx  , NewPy  ,  G\Sx  ,  G\Sy , True , G\R , G\G , G\B)
				
			End Select
			
			; ----------------
			; Draw the caption
			; ----------------
			Gui_DrawText(NewPx + 10 , NewPy + (G\Sy / 2) - StringOffset% , G\Caption$,230,230,230)	
						
	End Select
	

	
End Function