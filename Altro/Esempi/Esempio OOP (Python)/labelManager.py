import customtkinter as ctk

class LabelManager:
    def __init__(self, container, txt="label"):
        self.txt_lbl1 = txt
        self.visible = False
        self.lbl1 = ctk.CTkLabel(container, text=self.txt_lbl1)

    def update_text(self, i):
        self.txt_lbl1 = f"Ciao {i}"
        self.lbl1.configure(text=self.txt_lbl1)

    def switchLabels(self):
        if not self.visible:
            self.lbl1.place(relx=0.5, rely=0.8, anchor=ctk.CENTER)
            self.visible = True
        else:
            self.lbl1.place_forget()
            self.visible = False



