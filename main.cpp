#include<windows.h>
#include<stdio.h>
#include <string>
#include <sstream>
#include <iostream>

typedef WINAPI COLORREF (*GETPIXEL)(HDC, int, int);
/*
 * tl tr
 * bl br
 *
 *
 *
 */
void run() {
    int r,g,b;
    while(true) {
        HDC hdc = GetDC(NULL);
        for(int x = (2560/2)-10; x<=(2560/2)+20; x++) {
            for(int y = (1440/2)-10; y<=(1440/2)+20; y++) {
                COLORREF color = GetPixel(hdc, x, y);
                r = (int)GetRValue(color);
                g = (int)GetGValue(color);
                b = (int)GetBValue(color);
                std::cout << "X:" << x << " Y:" << y << " R: " << r << "|" << "G: " << g << "|" << "B: " << b << "\n";
                // ReleaseDC(NULL, hdc);
                Sleep(1000);
            }
        }

    }
}
void keyClick() {
    INPUT ip;
    // Set up a generic keyboard event.
    ip.type = INPUT_KEYBOARD;
    ip.ki.wScan = 0; // hardware scan code for key
    ip.ki.time = 0;
    ip.ki.dwExtraInfo = 0;

    // Press the "A" key
    ip.ki.wVk = 0x75; // virtual-key code for the "a" key
    SendInput(1, &ip, sizeof(INPUT));
    ip.ki.dwFlags = 0; // 0 for key press
}
int main() {

    // x, y
    run();
    return 0;
}
