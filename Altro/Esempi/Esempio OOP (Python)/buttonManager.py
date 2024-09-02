import customtkinter as ctk

i = 0
class ButtonManager:
        def __init__(self, container, lblMan):
            self._lblMan = lblMan

            self.btn1 = ctk.CTkButton(container, text="get i",
                                      command=take_i, width=40, height=20)

            self.btn2 = ctk.CTkButton(container, text="update i",
                                      command=lambda:self._lblMan.update_text(i), width=40, height=20)

            self.btn3 = ctk.CTkButton(container, text="switch",
                                      command=self._lblMan.switchLabels, width=40, height=20)


        def placeButtons(self):
            self.btn1.place(relx=0.5, rely=0.4, anchor=ctk.CENTER)
            self.btn2.place(relx=0.5, rely=0.5, anchor=ctk.CENTER)
            self.btn3.place(relx=0.5, rely=0.6, anchor=ctk.CENTER)


def take_i():
    global i
    i = int(input("inserire i:\n-> "))