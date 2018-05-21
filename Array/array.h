#ifndef ARRAY_H
#define ARRAY_H

#ifndef ARRAY_TYPE
#define ARRAY_TYPE

typedef int arrayContent_t;

#endif

typedef struct {
    arrayContent_t *data;
    int size;
} Array;

Array *newArray(int size);

void deleteArray(Array *this);

int getElementAt(Array *this, int index, arrayContent_t *result);

int setElementAt(Array *this, int index, arrayContent_t value);

#endif // ARRAY_H