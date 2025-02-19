; ---------------
; Create a window
; ---------------
Function Gui_CreateWindow.GuiObject(Px, Py, Sx, Sy, Caption$, Close = False , Maximize = False)

	G.GuiObject = New GuiObject
	
	G\Family = Gui_ObjectTypeWindow%
	G\Id = Handle(G)
	
	G\Px = Px
	G\Py = Py
	
	G\OldPx = Px
	G\OldPy = Py
		
	G\Sx = Sx
	G\Sy = Sy
	
	G\MinSx = Sx
	G\MinSy = Sy
	
	G\OldSx = Sx
	G\OldSy = Sy
	
	G\Caption$ = Caption$
	
	G\TitleHeight = 26
	
	G\Draggable% = True
	G\Movable% = True
	
	G\Sizable = False
	
	If Close = True Then 
		G\CloseGadget = Gui_CreateButton(G , G\Sx - (G\TitleHeight + 2) , 2 , G\TitleHeight , G\TitleHeight , "X")
		
		G\CloseGadget\R = 200
		G\CloseGadget\G = 0
		G\CloseGadget\B = 0
	EndIf
	
	If Maximize = True Then
		G\MaximizeGadget = Gui_CreateButton(G , G\Sx - (G\TitleHeight * 2 + 2) , 2 , G\TitleHeight , G\TitleHeight , "=")
		G\MinimizeGadget = Gui_CreateButton(G , G\Sx - (G\TitleHeight * 3 + 2) , 2 , G\TitleHeight , G\TitleHeight , "-")
	EndIf
		
	Gui_CurrentWindow = G

	Return G
	
End Function

; ---------------------
; Bring window to front
; ---------------------
Function Gui_SwapWindow()

	G.GuiObject= Last GuiObject			
	
	While G <> Null 
	
		If G\Family = Gui_ObjectTypeWindow% Then
	
			; -----------------------
			; Put the window in front
			; -----------------------
			If Gui_TestZone(G\Px% , G\Py% , G\Sx% , G\Sy%)
			
				Insert G After Last GuiObject
				
				Gui_CurrentWindow = G
				G\DragMode% = False

				; -----------------------------
				; Refresh dragging informations
				; -----------------------------
				If G\Movable% = True
					
					Local DragWidth% = G\Sx%
					
					If G\CloseGadget<>Null Then DragWidth = DragWidth - (G\TitleHeight + 2) 
					If G\MaximizeGadget<>Null Then DragWidth = DragWidth - (G\TitleHeight * 2 ) - 2			
					
					If Gui_TestZone(G\Px% , G\Py% , DragWidth% , G\TitleHeight%) And G\Draggable% = True  Then
						G\DragMode% = True
					
						G\DragPx% = GUI_MouseX% - G\Px%
						G\DragPy% = GUI_MouseY% - G\Py%
					EndIf
				
				EndIf

				; ---------------------------
				; Refresh resize informations
				; ---------------------------
				If G\Sizable% = True
				
					If Gui_TestZone(G\Px% + G\Sx% - 20 , G\Py% +G\Sy% - 20 , 20 , 20, False, False)   Then
						G\ResizeMode% = True
						
						G\OldSx% = Gui_MouseX% - G\Sx%
						G\OldSy% = Gui_MouseY% - G\Sy%	
					EndIf
				
				EndIf
					
				Return True
			EndIf
		
		EndIf
		
		; --------------------------------------
		; Loop the Type from backward to forward
		; --------------------------------------
		G = Before G
	
	Wend	
			
End Function

; ----------------
;  Move the window
; ----------------
Function Gui_MoveWindow()

	If Gui_CurrentWindow<>Null Then
		
		If  Gui_CurrentWindow\DragMode=True 		
		
			Gui_CurrentWindow\Px% = GUI_MouseX% - Gui_CurrentWindow\DragPx%
			Gui_CurrentWindow\Py% = GUI_MouseY% - Gui_CurrentWindow\DragPy%
			
			Gui_CurrentWindow\OldPx% = Gui_CurrentWindow\Px%
			Gui_CurrentWindow\OldPy% = Gui_CurrentWindow\Py%
			
		Else
		
			Gui_CurrentWindow\DragPx% = GUI_MouseX% - Gui_CurrentWindow\Px%
			Gui_CurrentWindow\DragPy% = GUI_MouseY% - Gui_CurrentWindow\Py%
			
		EndIf

	EndIf

End Function

; ------------------
;  Resize the window
; ------------------
Function Gui_ResizeWindow()

	If Gui_CurrentWindow<>Null Then	
	
		If (Gui_CurrentWindow\Sizable=True) Then
		
			If (Gui_CurrentWindow\ResizeMode=True ) Then
			
				Gui_CurrentWindow\Sx% = Gui_MouseX% - Gui_CurrentWindow\OldSx%
				Gui_CurrentWindow\Sy% = Gui_MouseY% - Gui_CurrentWindow\OldSy%

				; -----------------------------------------
				; Limit the resize by the window limit size
				; -----------------------------------------
				If Gui_CurrentWindow\Sx% < Gui_CurrentWindow\MinSx% Then Gui_CurrentWindow\Sx% = Gui_CurrentWindow\MinSx%
				If Gui_CurrentWindow\Sy% < Gui_CurrentWindow\MinSy% Then Gui_CurrentWindow\Sy% = Gui_CurrentWindow\MinSy%

				; ------------------------------------
				; Relocate the window gadgets position
				; ------------------------------------
				Gui_RefreshControl(Gui_CurrentWindow)
				
			EndIf
		
		EndIf
		
	EndIf
	
End Function

; ------------------------------
; Close a window and they childs
; ------------------------------
Function Gui_CloseWindow(G.GuiObject)

	Gui_FlushEvent()
	Gui_DeleteChild(G)
	Delete G
	
End Function

; -----------------------------------
; Refresh the windows controls button
; -----------------------------------
Function Gui_RefreshControl(G.GuiObject)

	If G\CloseGadget<>Null Then G\CloseGadget\Px = G\Sx - (G\TitleHeight + 2)
	If G\MaximizeGadget<>Null Then G\MaximizeGadget\Px = G\Sx - (G\TitleHeight * 2 + 2)
	If G\MinimizeGadget<>Null Then G\MinimizeGadget\Px = G\Sx - (G\TitleHeight * 3 + 2)
				
End Function

; ------------------
;  Redraw Gui System
; ------------------
Function Gui_RedrawWindow()
	For G.GuiObject=Each GuiObject
	
		If G\Family = Gui_ObjectTypeWindow% Then

			; -----------------
			; Window background
			; -----------------
			Gui_DrawRect(G\Px,G\Py, G\Sx ,  G\Sy, True, 55,55,55)
			Gui_DrawRect(G\Px + 1,G\Py + 1,  G\Sx -2 ,  G\Sy - 2, False ,150,150,150)
			
			; ----------------------
			; Titlebar color setting
			; ----------------------
			If Gui_CurrentWindow = G Then
				Gui_DrawRect(G\Px + 2,G\Py + 2,  G\Sx - 4 , G\TitleHeight , True ,200,200,200)
			Else
				Gui_DrawRect(G\Px + 2,G\Py + 2,  G\Sx - 4 , G\TitleHeight , True ,130,130,130)
			EndIf
			
			; ---------------
			; Draw title text
			; ---------------
			Gui_DrawText(G\Px + 10,G\Py + (G\TitleHeight / 2) - (StringHeight ("A") / 2-2), G\Caption$,30,30,30)
			
			
			If G\Sizable = True Then 
				Gui_DrawLine(G\Px + G\Sx - 5 , G\Py + G\Sy - 15, G\Px + G\Sx - 15 , G\Py + G\Sy - 5 , 160,160,160)
				Gui_DrawLine(G\Px + G\Sx - 4 , G\Py + G\Sy - 15, G\Px + G\Sx - 14 , G\Py + G\Sy - 5 , 130,130,130)
				
				
				Gui_DrawLine(G\Px + G\Sx - 9 , G\Py + G\Sy - 5 , G\Px + G\Sx - 5 , G\Py + G\Sy - 9 , 160,160,160)
				Gui_DrawLine(G\Px + G\Sx - 8 , G\Py + G\Sy - 5 , G\Px + G\Sx - 4 , G\Py + G\Sy - 9 , 130,130,130)
			EndIf
			
			; ---------------------------
			; I refresh the window childs
			; ---------------------------
			Gui_RefreshChild(G)

			; ----------------------------------
			; If the user click the close button
			; ----------------------------------			
			If Gui_GetEvent(G\CloseGadget) = Event_GadgetClick Then
	
				Gui_CloseWindow(G)
				Return True
				
			EndIf
			
			; -------------------------------------
			; If the user click the minimize button
			; -------------------------------------			
			If Gui_GetEvent(G\MinimizeGadget) = Event_GadgetClick Then
			
				G\Px% = G\OldPx%
				G\Py% = G\OldPy%
				
				G\Sx% = G\MinSx%
				G\Sy% = G\MinSy%
				
				Gui_RefreshControl(G)
				
				Gui_CreateEvent(G , Event_MinimizeWindow%)
				
			EndIf
			
			; -------------------------------------
			; If the user click the maximize button
			; -------------------------------------			
			If Gui_GetEvent(G\MaximizeGadget) = Event_GadgetClick Then
			
				G\Px% = 0
				G\Py% = 0

				G\Sx% = GraphicsWidth()
				G\Sy% = GraphicsHeight()

				Gui_RefreshControl(G)
				
				Gui_CreateEvent(G , Event_MaximizeWindow%)

			EndIf
			
		EndIf
	
	Next	
End Function

; --------------
;  Redraw childs
; --------------
Function Gui_RefreshChild(P.GuiObject)
	For G.GuiObject=Each GuiObject
	
		If G\Family <> Gui_ObjectTypeWindow% Then

			If G\Parent = P Then

				Select G\Family
				
					Case Gui_ObjectTypeButton%
					
						Gui_RefreshButton(G)
						Gui_RedrawButton(G)	
										
					Case Gui_ObjectTypeFrame%
					
						Gui_RefreshFrame(G)
						Gui_RedrawFrame(G)	

					Case Gui_ObjectTypeGrid%
					
						Gui_RefreshGrid(G)
						Gui_RedrawGrid(G)	
																
				End Select

			EndIf

		EndIf
	
	Next	
End Function

; --------------
;  Delete childs
; --------------
Function Gui_DeleteChild(P.GuiObject)
	For G.GuiObject=Each GuiObject

		If G\Parent = P Then
		
			Select G\Family
				Case Gui_ObjectTypeButton%
					
					;DebugLog "Object ID:"+Handle(G)+" / Caption:"+ G\Caption$ +" deleted"
					Delete G
					
			End Select

		EndIf

	Next	
End Function