import customtkinter as ctk
from buttonManager import ButtonManager
from labelManager import LabelManager



root = ctk.CTk()

root.geometry("400x400")
root.title("Esempio CustomTkinter")

lblMan = LabelManager(root)
btnMan = ButtonManager(root, lblMan)


btnMan.placeButtons()

root.mainloop()
