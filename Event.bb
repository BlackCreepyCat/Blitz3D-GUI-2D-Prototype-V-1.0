; ------------
; Events class
; ------------
Type GuiEvent
	Field Id.GuiObject
	Field Event%
End Type

Global Event_GadgetClick% = 4000
Global Event_SizeWindow% = 4100
Global Event_MoveWindow% = 4200
Global Event_MaximizeWindow% = 4300
Global Event_MinimizeWindow% = 4400

; -----------------------------------------------------
; Create a event with a source GuiObject and a event ID
; -----------------------------------------------------
Function Gui_CreateEvent(Id.GuiObject , Event%)
	E.GuiEvent = New GuiEvent
	E\Id = Id : E\Event% = Event%
End Function

; ------------------------------------------------------
; Check the event source GuiObject and return a event ID
; ------------------------------------------------------
Function Gui_GetEvent%(Id.GuiObject)
	For E.GuiEvent=Each GuiEvent
		If (E\Id) = Id Then Return E\Event%           
	Next
	
	Return False
End Function

; ----------------
; Purge all events
; ----------------
Function Gui_FlushEvent()
	For E.GuiEvent=Each GuiEvent
		Delete E
	Next
End Function
