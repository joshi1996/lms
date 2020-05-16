package com.lms.interfaces;

public interface OnOrderListener {
    void Oncancel(int pos);
    void Onrepeat(int pos);
    void Onfeedback(int pos);
    void OnItemclick(int pos);

}
