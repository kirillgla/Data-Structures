#include "math.h"

int min(int a, int b) {
    return a < b ? a : b;
}

int max(int a, int b) {
    return a < b ? b : a;
}

int sgn(int arg) {
    return arg < 0 ? -1: arg > 0? 1 : 0;
}